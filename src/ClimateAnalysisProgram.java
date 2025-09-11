import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
/**
 * runnable class to get user inputs
 * @author mahannourhosseinalipour
 *
 */
public class ClimateAnalysisProgram {
	public static void main(String[] args) {
		CSVReader r = new CSVReader("data.csv");
		r.setDateIndex(0);
		r.setMaxTempIndex(1);
		r.setMinTempIndex(4);
		r.setAvgTempIndex(3);
		r.setWindSpeedIndex(17);
		WeatherData[] day = new WeatherData[r.getNumRow()];
		WeatherData[][][] weatherOfDate = new WeatherData[32][13][3000];
		
		/*
		 * makes an object of WeatherDate type and stores important data from CSVReader in it
		 */
		for(int i = 1; i < day.length; i++) {
			day[i] = new WeatherData(r.getDate(i), false, true);
			day[i].setMaxTemp(r.getMaxTemp(i));
			day[i].setMinTemp(r.getMinTemp(i));
			day[i].setAvgTemp(r.getAvgTemp(i));
			day[i].setWindSpeed(r.getWindSpeed(i));
		    
		}
		/*
		 * makes another object to have WeatherData[DD][MM][YYYY] form
		 */
		for(int i = 1; i < day.length; i++) {
			weatherOfDate[day[i].getDay()][day[i].getMonth()][day[i].getYear()] = day[i];
			//System.out.println(weatherOfDate[day[i].getDay()][day[i].getMonth()][day[i].getYear()]);
		}
		//=======================output part============================
		System.out.print( "Hey! Welcome to Climate Analysis Program!\n"
						+ "Please select in what you are intrested to start:\n"
						+ "1: Maximum daily high temperature\n"
						+ "2: Minimum daily high temperature\n"
						+ "3: Average daily high temperature for each year\n"
						+ "4: Largest daily increase in temperature\n"
						+ "5: Largest daily decrease in temperature\n"
						+ "6: Creat daily moving average & average temp CSV\n"
						+ "7: Maximum daily wind high speed\n"
						+ "-1: Exit\n");
		
		//========================input part============================
		Scanner sc = new Scanner(System.in);
		int f;
		while(true) {
			System.out.print('>');
			if(sc.hasNextInt()) {
				f = sc.nextInt();
			} else continue;
			
			if(f==1) {
				System.out.format("%.2f\n",getMaximumDailyHighTemperature(day));
			}
			
			if(f==2) {
				System.out.format("%.2f\n",getMinimumDailyHighTemperature(day));
			}
			
			
			if(f==3) {
				for(var i: getAverageDailyHighTemperature(day).entrySet()) {
					System.out.format("%d: %.2f\n",i.getKey(), i.getValue());
				}
			}
			
			if(f==4) {
				System.out.format("%.2f\n",getLargestDailyIncreaseInTemperature(day));
			}
			
			if(f==5) {
				System.out.format("%.2f\n",getLargestDailyDecreaseInTemperature(day));
			}
			
			if(f==6) {
				int numDays;
				System.out.print( "Please enter the number of days in your moving average priods\n"
								+ "In case you selected wrong option enter -1 to exit:\n");
				while(true) {
					System.out.print('>');
					if(sc.hasNextInt()) {
						numDays = sc.nextInt();
						break;
					}
				}
				if(numDays == -1) {
					continue;
				}
				
				/*
				 * This part just makes a CSV file and makes it in Date, moving average, average temp form
				 * first and last priod/2 days have no moving average since moving average is average of all surrounding priod days
				 */
				File file = new File("importedData.csv");
				try {
				    file.createNewFile(); 
				} 
				catch (IOException e) {
					System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			    FileWriter w = null;
				try {
					w = new FileWriter(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				DecimalFormat infoDf = new DecimalFormat("0.00");
				try {
					w.write("Date,Moving Average,Average Temprature\n");
			    	for(int i = 1; i <= numDays/2; i++) {
			    		w.write(day[day.length-i].getDate()+",,"+day[day.length-i].getAvgTemp()+'\n');
			    	}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				for(int i = day.length-numDays-1; i > 0;i--) {
					double sum = 0;
					try {
				    	for(int j = numDays; j > 0; j--) {
				    		sum += day[i+j].getAvgTemp();
				    	}
				    	
				    	w.write(day[i+numDays/2].getDate()+','+infoDf.format(sum/numDays)+','+day[i+numDays/2].getAvgTemp()+'\n');
				    	  
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
			    	for(int i = numDays/2; i > 0; i--) {
			    		w.write(day[i].getDate()+",,"+day[i].getAvgTemp()+'\n');
			    	}
			    	System.out.println("Your file created.");
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				try {
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(f==7) {
				System.out.format("%.2f\n", getMaximumDailyHighWindSpeed(day));
			}
			if(f==-1) {
				break;
			}
		}
	}
	/**
	 * private method for finding Maximum Daily High Temperature
	 * @param day data
	 * @return highestTemp Maximum Daily High Temperature in data
	 */
	private static double getMaximumDailyHighTemperature(WeatherData[] day) {
		double highestTemp = Double.NEGATIVE_INFINITY;
		for(int i = 1; i < day.length; i++) {
			if(day[i].getMaxTemp() > highestTemp) {
				highestTemp = day[i].getMaxTemp();
			}
		}
		return highestTemp;
	}
	/**
	 * private method for finding Maximum Daily Wind Speed
	 * @param day data
	 * @return highestWindSpeed Maximum Daily Wind Speed in data
	 */
	private static double getMaximumDailyHighWindSpeed(WeatherData[] day) {
		double highestWindSpeed = Double.NEGATIVE_INFINITY;
		for(int i = 1; i < day.length; i++) {
			if(day[i].getWindSpeed() > highestWindSpeed) {
				highestWindSpeed = day[i].getWindSpeed();
			}
		}
		return highestWindSpeed;
	}
	/**
	 * private method for finding Minimum Daily High Temperature in data
	 * @param day data
	 * @return lowestTemp Minimum Daily High Temperature in data
	 */
	private static double getMinimumDailyHighTemperature(WeatherData[] day) {
		double lowestTemp = Double.POSITIVE_INFINITY;
		for(int i = 1; i < day.length; i++) {
			if(day[i].getMaxTemp() < lowestTemp) {
				lowestTemp = day[i].getMaxTemp();
			}
		}
		return lowestTemp;
	}
	/**
	 * private method for finding Average Daily High Temperature in each year with given data
	 * @param day data
	 * @return averageDailyHigh a map representing Average Daily High Temperature in map<year, avg> form
	 */
	private static Map<Integer,Double> getAverageDailyHighTemperature(WeatherData[] day) {
		double sum = 0;
		int year = day[1].getYear();
		int days = 0;
		Map<Integer, Double> averageDailyHigh = new HashMap<>();
		for(int i = 1; i < day.length; i++) {
			if(day[i].getYear() == year) {
				sum += day[i].getMaxTemp();
				days++;
			}
			else {
				averageDailyHigh.put(year, sum/days);
				sum = 0;
				days = 0;
				year = day[i].getYear();
				i--;
			}
		}
		if(days>0) {
			averageDailyHigh.put(year, sum/days);
		}
		return averageDailyHigh;
	}
	/**
	 * private method for finding Largest Daily Increase In Temperature 
	 * @param day data
	 * @return largestDiff Largest Daily Increase In Temperature in given data
	 */
	private static double getLargestDailyIncreaseInTemperature(WeatherData[] day) {
		double largestDiff = Double.NEGATIVE_INFINITY;
		for(int i = 1; i < day.length-1; i++) {
			if(day[i+1].getAvgTemp()-day[i].getAvgTemp() > 0 && day[i+1].getAvgTemp()-day[i].getAvgTemp() > largestDiff) {
				largestDiff = day[i+1].getAvgTemp()-day[i].getAvgTemp();
			}
		}
		return largestDiff;
	}
	/**
	 * private method for finding Largest Daily Decrease In Temperature 
	 * @param day data
	 * @return largestDiff Largest Daily Decrease In Temperature in given data
	 */
	private static double getLargestDailyDecreaseInTemperature(WeatherData[] day) {
		double largestDiff = Double.POSITIVE_INFINITY;
		for(int i = 1; i < day.length-1; i++) {
			if(day[i+1].getAvgTemp()-day[i].getAvgTemp() < 0 && day[i+1].getAvgTemp()-day[i].getAvgTemp() < largestDiff) {
				largestDiff = day[i+1].getAvgTemp()-day[i].getAvgTemp();
			}
		}
		return largestDiff;
	}
}
