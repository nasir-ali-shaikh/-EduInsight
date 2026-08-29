<div align="center">

# 📊 EduInsight
### Student Performance & Attendance Analyzer

*A desktop analytics platform that turns raw student data into cleaned datasets, actionable insights, and exportable reports.*

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Python](https://img.shields.io/badge/Python-3.10%2B-3776AB?logo=python&logoColor=white)](https://www.python.org/)
[![pandas](https://img.shields.io/badge/pandas-data%20engine-150458?logo=pandas&logoColor=white)](https://pandas.pydata.org/)
[![Swing](https://img.shields.io/badge/UI-Java%20Swing-orange)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Status](https://img.shields.io/badge/status-active-brightgreen)]()
[![Made with GitHub Student Pack](https://img.shields.io/badge/Built%20with-GitHub%20Student%20Pack-181717?logo=github)](https://education.github.com/pack)

[Overview](#-overview) •
[Features](#-features) •
[Architecture](#-architecture) •
[Getting Started](#-getting-started) •
[Roadmap](#-roadmap) •
[Contact](#-contact)

</div>

---

## 📖 Overview

**EduInsight** helps educators and administrators go from a messy spreadsheet to real insight in a few clicks — upload a dataset, clean it, analyze performance & attendance patterns, spot at-risk students early, and export a shareable report. No coding required on the user's end; all the heavy lifting happens behind a clean, modern desktop UI.

The project is deliberately built as **two cooperating layers** rather than a monolith:

- 🖥️ **Java (Swing)** — the entire user experience: dashboard, upload flow, cleaning tools, analytics views, custom-painted charts, and reports.
- 🐍 **Python (pandas)** — a focused "data engine" that does the real data processing and returns structured JSON for Java to render.

This separation was a deliberate design choice: it keeps the UI layer thin and responsive while all statistical work happens in the language best suited for it.

---

## ✨ Features

<table>
<tr>
<td width="50%" valign="top">

**🏠 Dashboard**
At-a-glance KPIs — total students, average marks, attendance, at-risk count — with quick navigation to every module.

**📤 Upload Dataset**
Import CSV/Excel with a live preview, row/column counts, and file metadata before you commit to anything.

**🧹 Data Cleaning**
Remove duplicates, drop/fill missing values, remove empty columns, or run a one-click "Clean All" — with before/after stats.

**📈 Analytics**
Auto-detects marks/attendance columns, then produces a performance distribution, attendance summary, and correlation analysis.

</td>
<td width="50%" valign="top">

**📊 Visualizations**
Six chart types — bar, line trend, pie, histogram, scatter, attendance overview — all custom-painted in Swing.

**⚠️ At-Risk Students**
The complete, sortable list of every student below the risk threshold — not a capped preview.

**📄 Reports**
One-click formatted summary report, exportable as `.txt` for sharing or record-keeping.

**⚙️ Settings**
Swap or clear the active dataset and view app info without leaving the app.

</td>
</tr>
</table>

---

## 🏗️ Architecture

```
┌──────────────────────────┐        JSON over stdout        ┌───────────────────────────┐
│      Java (Swing UI)      │ ─────────────────────────────▶ │   Python (data_engine.py)  │
│                           │ ◀───────────────────────────── │         pandas             │
│  Dashboard · Upload ·     │                                 │  clean · analyze ·         │
│  Cleaning · Analytics ·   │                                 │  visualize · detect risk   │
│  Visualizations · Reports │                                 │                            │
└──────────────────────────┘                                 └───────────────────────────┘
```

- Java launches `data_engine.py` via `ProcessBuilder`, passing the dataset path and an action keyword (`analytics`, `visualization`, `at_risk`, `remove_duplicates`, …).
- The script processes the dataset with **pandas** and prints a single JSON object to stdout.
- Java parses it with a small, **dependency-free JSON parser** (`JsonUtil`) and renders the result as tables, KPI cards, and hand-painted charts.
- A shared `PythonEngine` helper locates the script and falls back across interpreter names (`python`, `python3`, `py`) so it runs reliably across different machine setups.

**Why this design?** No REST API, no database, no build-tool overhead for the data layer — just a clean, testable JSON contract between two languages, each doing what it's best at.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| UI | Java 17+, Swing (custom `Graphics2D` charts) |
| Data Engine | Python 3.10+, [pandas](https://pandas.pydata.org/) |
| JSON | Gson (Visualizations module) + a lightweight custom parser (`JsonUtil`) |
| Interop | `ProcessBuilder` + stdout JSON — no network layer required |

---

## 🚀 Getting Started

### Prerequisites

- **JDK 17+**
- **Python 3.10+** with dependencies:
  ```bash
  pip install pandas openpyxl
  ```
- **Gson** added to the Java project's classpath (used by `Visualizations.java`)

### Run it

```bash
git clone https://github.com/<your-username>/eduinsight.git
```

1. Open the project in your Java IDE (NetBeans / IntelliJ / Eclipse).
2. Confirm `data_engine.py` sits at the project root (same level as `src/`).
3. Run `Main.java`.
4. From the Dashboard → **Upload Dataset** → select a CSV/Excel file → explore.

---

## 📂 Project Structure

```
EduInsight/
├── src/eduinsight/
│   ├── Main.java              # Entry point
│   ├── Dashboard.java         # Navigation & KPI overview
│   ├── UploadDataset.java     # File import & preview
│   ├── DataCleaning.java      # Cleaning operations
│   ├── Analytics.java         # Performance & attendance analytics
│   ├── Visualizations.java    # Chart dashboard
│   ├── AtRiskStudents.java    # Full at-risk student list
│   ├── Reports.java           # Report generation & export
│   ├── Settings.java          # Dataset & app settings
│   ├── PythonEngine.java      # Shared engine-calling logic
│   ├── JsonUtil.java          # Lightweight JSON parser
│   └── DatasetManager.java    # Shares the active dataset across pages
├── data_engine.py             # Python/pandas data engine
└── README.md
```

---

## 📌 Roadmap

- [ ] Dark mode
- [ ] PDF export for reports
- [ ] Configurable risk threshold from Settings
- [ ] Multi-dataset comparison view

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome. Feel free to check the [issues page](../../issues) or open a pull request.

## 📄 License

Licensed under the [MIT License](LICENSE).

---

## 📬 Contact

<div align="center">

**[Nasir Ali Shaikh]**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?logo=linkedin&logoColor=white)](https://linkedin.com/www.linkedin.com/in/nasir-ali-shaikh-b8374832a)
[![GitHub](https://img.shields.io/badge/GitHub-181717?logo=github&logoColor=white)](https://github.com/nasir-ali-shaikh)
[![Email](https://img.shields.io/badge/Email-D14836?logo=gmail&logoColor=white)](mailto:mirnasiralishaikh@gmail.com)

<sub>Built with ☕ Java, 🐍 Python, and a lot of student-data curiosity.</sub>

</div>
