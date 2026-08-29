import sys
import json
import os
import pandas as pd


# ==========================================
# READ DATASET
# ==========================================

def read_dataset(file_path):

    if not os.path.exists(file_path):
        raise FileNotFoundError(
            "Dataset file not found."
        )

    extension = os.path.splitext(
        file_path
    )[1].lower()

    if extension == ".csv":

        df = pd.read_csv(
            file_path
        )

    elif extension in [".xlsx", ".xls"]:

        df = pd.read_excel(
            file_path
        )

    else:

        raise ValueError(
            "Unsupported file format. "
            "Only CSV and Excel files are supported."
        )

    return df


# ==========================================
# SAVE CLEANED DATASET
# ==========================================

def save_dataset(df, original_path):

    folder = os.path.dirname(
        original_path
    )

    file_name = os.path.basename(
        original_path
    )

    name, extension = os.path.splitext(
        file_name
    )

    cleaned_file_name = (
        name + "_cleaned" + extension
    )

    output_path = os.path.join(
        folder,
        cleaned_file_name
    )

    if extension == ".csv":

        df.to_csv(
            output_path,
            index=False
        )

    elif extension in [".xlsx", ".xls"]:

        df.to_excel(
            output_path,
            index=False
        )

    return output_path


# ==========================================
# DATASET OVERVIEW
# ==========================================

def get_dataset_overview(df):

    return {

        "rows": int(df.shape[0]),

        "columns": int(df.shape[1]),

        "column_names": df.columns.tolist(),

        # Added so the Data Cleaning page can show these
        # stat cards without needing a separate call.

        "duplicates": int(df.duplicated().sum()),

        "missing_total": int(df.isnull().sum().sum())

    }


# ==========================================
# PREVIEW FIRST 5 ROWS
# ==========================================

def get_preview(df):

    preview = df.head(5)

    preview = preview.where(
        pd.notnull(preview),
        None
    )

    return preview.to_dict(
        orient="records"
    )


# ==========================================
# MISSING VALUES
# ==========================================

def get_missing_values(df):

    missing = df.isnull().sum()

    result = {}

    for column, value in missing.items():

        result[column] = int(value)

    return result


# ==========================================
# BASIC STATISTICS
# ==========================================

def get_statistics(df):

    numeric_df = df.select_dtypes(
        include="number"
    )

    if numeric_df.empty:

        return {}

    statistics = {}

    for column in numeric_df.columns:

        statistics[column] = {

            "mean": round(
                float(
                    numeric_df[column].mean()
                ),
                2
            ),

            "median": round(
                float(
                    numeric_df[column].median()
                ),
                2
            ),

            "minimum": round(
                float(
                    numeric_df[column].min()
                ),
                2
            ),

            "maximum": round(
                float(
                    numeric_df[column].max()
                ),
                2
            )

        }

    return statistics


# ==========================================
# REMOVE DUPLICATES
# ==========================================

def remove_duplicates(df):

    rows_before = len(df)

    df = df.drop_duplicates()

    rows_after = len(df)

    removed = rows_before - rows_after

    return df, removed


# ==========================================
# DROP MISSING ROWS
# ==========================================

def drop_missing_rows(df):

    rows_before = len(df)

    df = df.dropna()

    rows_after = len(df)

    removed = rows_before - rows_after

    return df, removed


# ==========================================
# FILL NUMERIC VALUES WITH MEAN
# ==========================================

def fill_missing_with_mean(df):

    numeric_columns = df.select_dtypes(
        include="number"
    ).columns

    filled = 0

    for column in numeric_columns:

        missing_count = int(
            df[column].isnull().sum()
        )

        if missing_count > 0:

            mean_value = df[column].mean()

            df[column] = df[column].fillna(
                mean_value
            )

            filled += missing_count

    return df, filled


# ==========================================
# FILL TEXT VALUES WITH UNKNOWN
# ==========================================

def fill_missing_with_unknown(df):

    text_columns = df.select_dtypes(
        include=["object", "string"]
    ).columns

    filled = 0

    for column in text_columns:

        missing_count = int(
            df[column].isnull().sum()
        )

        if missing_count > 0:

            df[column] = df[column].fillna(
                "Unknown"
            )

            filled += missing_count

    return df, filled


# ==========================================
# REMOVE EMPTY COLUMNS
# ==========================================

def remove_empty_columns(df):

    empty_columns = []

    for column in df.columns:

        if df[column].isnull().all():

            empty_columns.append(
                column
            )

    df = df.drop(
        columns=empty_columns
    )

    return df, empty_columns


# ==========================================
# CLEAN ALL
# ==========================================

def clean_all(df):

    summary = {}

    # Remove duplicate rows

    df, duplicates_removed = (
        remove_duplicates(df)
    )

    summary[
        "duplicates_removed"
    ] = duplicates_removed


    # Fill numeric missing values

    df, numeric_filled = (
        fill_missing_with_mean(df)
    )

    summary[
        "numeric_values_filled"
    ] = numeric_filled


    # Fill text missing values

    df, text_filled = (
        fill_missing_with_unknown(df)
    )

    summary[
        "text_values_filled"
    ] = text_filled


    # Remove completely empty columns

    df, empty_columns = (
        remove_empty_columns(df)
    )

    summary[
        "empty_columns_removed"
    ] = empty_columns


    return df, summary


# ==========================================
# ANALYTICS: COLUMN DETECTION
# ==========================================

def detect_column(df, keywords, numeric_only=True):

    for col in df.columns:

        col_lower = str(col).lower()

        for keyword in keywords:

            if keyword in col_lower:

                if numeric_only:
                    if pd.api.types.is_numeric_dtype(df[col]):
                        return col
                else:
                    return col

    return None


def detect_id_column(df, exclude_columns):

    keywords = ["name", "student", "roll", "id"]

    for col in df.columns:

        if col in exclude_columns:
            continue

        col_lower = str(col).lower()

        for keyword in keywords:

            if keyword in col_lower:
                return col

    # fallback: first non-numeric column that isn't excluded

    for col in df.columns:

        if col in exclude_columns:
            continue

        if not pd.api.types.is_numeric_dtype(df[col]):
            return col

    return None


# ==========================================
# ANALYTICS: PERFORMANCE DISTRIBUTION
# ==========================================

def get_performance_distribution(df, performance_column):

    if performance_column is None:
        return None

    values = df[performance_column].dropna()

    if values.empty:
        return None

    # Assumes a 0-100 style marks/percentage scale.

    return {

        "Excellent": int((values >= 85).sum()),

        "Good": int(((values >= 70) & (values < 85)).sum()),

        "Average": int(((values >= 50) & (values < 70)).sum()),

        "At-Risk": int((values < 50).sum())

    }


# ==========================================
# ANALYTICS: ATTENDANCE SUMMARY
# ==========================================

def get_attendance_summary(df, attendance_column):

    if attendance_column is None:
        return None

    values = df[attendance_column].dropna()

    if values.empty:
        return None

    return {

        "average": round(float(values.mean()), 2),

        "minimum": round(float(values.min()), 2),

        "maximum": round(float(values.max()), 2)

    }


# ==========================================
# ANALYTICS: PERFORMANCE VS ATTENDANCE CORRELATION
# ==========================================

def get_correlation(df, col_a, col_b):

    if col_a is None or col_b is None:
        return None

    try:

        paired = df[[col_a, col_b]].dropna()

        if len(paired) < 2:
            return None

        value = paired.corr().iloc[0, 1]

        if pd.isna(value):
            return None

        return round(float(value), 2)

    except Exception:

        return None


# ==========================================
# ANALYTICS: TOP PERFORMERS / AT-RISK STUDENTS
# ==========================================

def get_top_and_risk_students(df, id_column, performance_column, attendance_column, top_n=5):

    if performance_column is None:
        return [], []

    working = df.dropna(subset=[performance_column])

    if working.empty:
        return [], []

    columns_to_show = []

    if id_column:
        columns_to_show.append(id_column)

    columns_to_show.append(performance_column)

    if attendance_column and attendance_column not in columns_to_show:
        columns_to_show.append(attendance_column)

    top = working.sort_values(
        by=performance_column, ascending=False
    ).head(top_n)[columns_to_show]

    risk = working.sort_values(
        by=performance_column, ascending=True
    ).head(top_n)[columns_to_show]

    top = top.where(pd.notnull(top), None)
    risk = risk.where(pd.notnull(risk), None)

    return top.to_dict(orient="records"), risk.to_dict(orient="records")


# ==========================================
# FULL ANALYTICS REPORT
# ==========================================

def get_full_analytics(file_path):

    df = read_dataset(file_path)

    performance_column = detect_column(
        df, ["marks", "score", "grade", "percentage", "result"]
    )

    attendance_column = detect_column(
        df, ["attendance"]
    )

    id_column = detect_id_column(
        df,
        exclude_columns=[c for c in [performance_column, attendance_column] if c]
    )

    performance_distribution = get_performance_distribution(df, performance_column)
    attendance_summary = get_attendance_summary(df, attendance_column)
    correlation = get_correlation(df, performance_column, attendance_column)

    top_performers, at_risk_students = get_top_and_risk_students(
        df, id_column, performance_column, attendance_column
    )

    return {

        "success": True,

        "file_name": os.path.basename(file_path),

        "overview": get_dataset_overview(df),

        "statistics": get_statistics(df),

        "performance_column": performance_column,

        "attendance_column": attendance_column,

        "id_column": id_column,

        "performance_distribution": performance_distribution,

        "attendance_summary": attendance_summary,

        "performance_attendance_correlation": correlation,

        "top_performers": top_performers,

        "at_risk_students": at_risk_students

    }


# ==========================================
# VISUALIZATION DATA
# ==========================================

def get_visualization_data(file_path):

    df = read_dataset(file_path)

    performance_column = detect_column(
        df, ["marks", "score", "grade", "percentage", "result"]
    )

    attendance_column = detect_column(
        df, ["attendance"]
    )

    performance_distribution = get_performance_distribution(df, performance_column)

    average_performance = None
    performance_values = []

    if performance_column is not None:

        perf_series = df[performance_column].dropna()

        if not perf_series.empty:

            average_performance = round(float(perf_series.mean()), 2)

            performance_values = [
                round(float(v), 2) for v in perf_series.tolist()
            ]

    average_attendance = None
    attendance_values = []

    if attendance_column is not None:

        att_series = df[attendance_column].dropna()

        if not att_series.empty:

            average_attendance = round(float(att_series.mean()), 2)

            attendance_values = [
                round(float(v), 2) for v in att_series.tolist()
            ]

    scatter_data = []

    if performance_column is not None and attendance_column is not None:

        paired = df[[performance_column, attendance_column]].dropna()

        for _, row in paired.iterrows():

            scatter_data.append([
                round(float(row[performance_column]), 2),
                round(float(row[attendance_column]), 2)
            ])

    return {

        "success": True,

        "file_name": os.path.basename(file_path),

        "overview": get_dataset_overview(df),

        "performance_column": performance_column,

        "attendance_column": attendance_column,

        "average_performance": average_performance,

        "average_attendance": average_attendance,

        "performance_distribution": performance_distribution,

        "performance_values": performance_values,

        "attendance_values": attendance_values,

        "scatter_data": scatter_data

    }


# ==========================================
# FULL AT-RISK STUDENT LIST
# ==========================================

def get_at_risk_students(file_path, threshold=50):

    df = read_dataset(file_path)

    performance_column = detect_column(
        df, ["marks", "score", "grade", "percentage", "result"]
    )

    attendance_column = detect_column(
        df, ["attendance"]
    )

    id_column = detect_id_column(
        df,
        exclude_columns=[c for c in [performance_column, attendance_column] if c]
    )

    if performance_column is None:

        return {

            "success": True,

            "file_name": os.path.basename(file_path),

            "performance_column": None,

            "attendance_column": attendance_column,

            "id_column": id_column,

            "threshold": threshold,

            "at_risk_count": 0,

            "at_risk_students": []

        }

    working = df.dropna(subset=[performance_column])

    at_risk = working[working[performance_column] < threshold]

    columns_to_show = []

    if id_column:
        columns_to_show.append(id_column)

    columns_to_show.append(performance_column)

    if attendance_column and attendance_column not in columns_to_show:
        columns_to_show.append(attendance_column)

    at_risk_sorted = at_risk.sort_values(
        by=performance_column, ascending=True
    )[columns_to_show]

    at_risk_sorted = at_risk_sorted.where(pd.notnull(at_risk_sorted), None)

    return {

        "success": True,

        "file_name": os.path.basename(file_path),

        "performance_column": performance_column,

        "attendance_column": attendance_column,

        "id_column": id_column,

        "threshold": threshold,

        "at_risk_count": int(len(at_risk_sorted)),

        "at_risk_students": at_risk_sorted.to_dict(orient="records")

    }


# ==========================================
# DATASET ANALYSIS
# ==========================================

def analyze_dataset(file_path):

    df = read_dataset(
        file_path
    )

    result = {

        "success": True,

        "file_name": os.path.basename(
            file_path
        ),

        "overview": get_dataset_overview(
            df
        ),

        "preview": get_preview(
            df
        ),

        "missing_values": get_missing_values(
            df
        ),

        "statistics": get_statistics(
            df
        )

    }

    return result


# ==========================================
# DATA CLEANING ENGINE
# ==========================================

def clean_dataset(
        file_path,
        action
):

    df = read_dataset(
        file_path
    )

    rows_before = len(df)

    columns_before = len(df.columns)

    duplicates_before = int(
        df.duplicated().sum()
    )

    missing_before = int(
        df.isnull().sum().sum()
    )

    cleaning_result = {}


    # --------------------------------------

    if action == "remove_duplicates":

        df, removed = remove_duplicates(
            df
        )

        cleaning_result = {

            "duplicates_removed": removed

        }


    # --------------------------------------

    elif action == "drop_missing":

        df, removed = drop_missing_rows(
            df
        )

        cleaning_result = {

            "rows_removed": removed

        }


    # --------------------------------------

    elif action == "fill_mean":

        df, filled = fill_missing_with_mean(
            df
        )

        cleaning_result = {

            "values_filled": filled

        }


    # --------------------------------------

    elif action == "fill_unknown":

        df, filled = (
            fill_missing_with_unknown(df)
        )

        cleaning_result = {

            "values_filled": filled

        }


    # --------------------------------------

    elif action == "remove_empty_columns":

        df, columns_removed = (
            remove_empty_columns(df)
        )

        cleaning_result = {

            "columns_removed":
                columns_removed

        }


    # --------------------------------------

    elif action == "clean_all":

        df, cleaning_result = clean_all(
            df
        )


    # --------------------------------------

    else:

        raise ValueError(
            "Invalid cleaning action: "
            + action
        )


    # Save cleaned dataset

    output_path = save_dataset(
        df,
        file_path
    )


    rows_after = len(df)

    columns_after = len(
        df.columns
    )

    duplicates_after = int(
        df.duplicated().sum()
    )

    missing_after = int(
        df.isnull().sum().sum()
    )


    # ======================================
    # RETURN RESULT
    # ======================================

    result = {

        "success": True,

        "action": action,

        "file_name": os.path.basename(
            file_path
        ),

        "cleaned_file": output_path,

        "rows_before": rows_before,

        "rows_after": rows_after,

        "columns_before": columns_before,

        "columns_after": columns_after,

        "duplicates_before":
            duplicates_before,

        "duplicates_after":
            duplicates_after,

        "missing_before":
            missing_before,

        "missing_after":
            missing_after,

        "cleaning_result":
            cleaning_result,

        "preview": get_preview(
            df
        )

    }

    return result


# ==========================================
# MAIN PROGRAM
# ==========================================

if __name__ == "__main__":

    try:

        if len(sys.argv) < 2:

            raise ValueError(
                "Please provide dataset file path."
            )


        file_path = sys.argv[1]


        # ==================================
        # ANALYSIS MODE
        # ==================================

        if len(sys.argv) == 2:

            result = analyze_dataset(
                file_path
            )


        # ==================================
        # ANALYTICS MODE
        # ==================================

        elif sys.argv[2] == "analytics":

            result = get_full_analytics(
                file_path
            )


        # ==================================
        # VISUALIZATION MODE
        # ==================================

        elif sys.argv[2] == "visualization":

            result = get_visualization_data(
                file_path
            )


        # ==================================
        # AT-RISK STUDENTS MODE
        # ==================================

        elif sys.argv[2] == "at_risk":

            result = get_at_risk_students(
                file_path
            )


        # ==================================
        # CLEANING MODE
        # ==================================

        else:

            action = sys.argv[2]

            result = clean_dataset(
                file_path,
                action
            )


        print(
            json.dumps(
                result,
                default=str
            )
        )


    except Exception as e:

        error_result = {

            "success": False,

            "error": str(e)

        }


        print(
            json.dumps(
                error_result
            )
        )