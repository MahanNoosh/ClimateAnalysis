# ClimateAnalysis 🌍

Java-based program for analyzing daily climate data.  
It processes a CSV dataset of weather measurements, computes statistics such as maximum, minimum, and average temperatures, largest daily temperature changes, and maximum wind speed.  
It can also generate a CSV file with moving averages of daily temperatures.

---

## Features
- Load and process climate data from `data.csv`  
- Compute maximum and minimum daily temperatures  
- Compute average daily temperature per year  
- Find largest daily increase and decrease in temperature  
- Compute maximum daily wind speed  
- Generate CSV of daily moving averages for average temperature  

---

## Getting Started

### Requirements
- **Java 17+** (works with Java 11+)  
- No external libraries required  

### Build & Run
1. Clone the repository:
   ```bash
   git clone https://github.com/MahanNoosh/ClimateAnalysis.git
   cd ClimateAnalysis
   ```
2. Compile the source code:
  ```bash
  javac -d bin src/climateanalysis/*.java
  ```
3. Run the program:
  ```bash
  java -cp bin climateanalysis.ClimateAnalysisProgram
  ```
4. Place your dataset file as data.csv in the project root.
The program will read the data and display statistics in the console.

---

Contributions, feedback, and improvements are welcome!
For questions, reach out via GitHub Issues or contact me directly.
