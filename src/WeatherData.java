import java.text.DecimalFormat;
/**
 * this class is an object, day, and all the information needed in it
 * @author Mahan NourHosseinAlipour
 *
 */
public class WeatherData {
	//=== date ===//
	/**
	 * representing day in date
	 */
	private int day;
	/**
	 * representing month in date
	 */
	private int month;
	/**
	 * representing year in date
	 */
	private int year;
	/**
	 * representing date column data
	 */
	private String date;
	/**
	 * representing how the date is written
	 */
	private boolean americanStyleDate, rightToLeftDate;
	
	/**
	 * stores what data the object has to show N/A in toString() if it's not entered yet
	 */
	private boolean hasMaxTemp, hasMinTemp, hasAvgTemp, hasWindSpeed;
	
	//important weather data
	/**
	 * stores max temp of this day(object)
	 */
	private double maxTemp;
	/**
	 * stores min temp of this day(object)
	 */
	private double minTemp;
	/**
	 * stores avg temp of this day(object)
	 */
	private double avgTemp;
	/**
	 * stores wind speed of this day(object)
	 */
	private double windSpeed;
	/**
	 * stores status(i.e. Rainy, Sunny, Cloudy, etc...) of this day(object)
	 */
	private String status = "N/A";
	
	/**
	 * constructor(String date, boolean if it's MMDDYYYY)
	 * @param date in form MMDDYYYY or DDMMYYYY with any kind of separator
	 * @param americanStyleDate true if the form is MMDDYYYY, false otherwise
	 */
	public WeatherData(String date, boolean americanStyleDate){ //
		this.date = date;
		this.americanStyleDate = americanStyleDate;
		dateConverter();
	}
	/**
	 * constructor (String date, boolean if it's MMDDYYYY or YYYYDDMM, boolean if it's YYYYMMDD or YYYYDDMM)
	 * @param date in form MMDDYYYY, DDMMYYYY, YYYYMMDD, or YYYYDDMM with any kind of separator
	 * @param americanStyleDate true if the form is MMDDYYYY or YYYYDDMM, false otherwise
	 * @param rightToLeftDate  true if the form is YYYYDDMM or YYYYMMDD, false otherwise
	 */
	public WeatherData(String date, boolean americanStyleDate, boolean rightToLeftDate){ 
		this.date = date;
		this.americanStyleDate = americanStyleDate;
		this.rightToLeftDate = rightToLeftDate;
		dateConverter();
	}
	/**
	 * constructor(String date in DDMMYYYY)
	 * @param date in form DDMMYYYY
	 */
	public WeatherData(String date){
		this.date = date;
		dateConverter();
	}
	
	/**
	 * convert every style of date to DDMMYYYY and store it in corresponding variable
	 */
	private void dateConverter(){
		if(date.length()<8) {
			return;
		}
		String[] s1 = new String[3];
		s1[0] = "";
		s1[1] = "";
		s1[2] = "";
		
		if(!rightToLeftDate) {
			int index = 0;
			for(int i = 0; i < date.length(); i++) {
				if(Character.isDigit(date.charAt(i))){
					if(s1[index].length() != 2 || (index == 2 && s1[index].length() != 4)) {
						s1[index] += date.charAt(i);
					}
					else {
						index++;
						i--;
					}
				}
				else index++;
			}
		}
		else {
			int index = 0;
			for(int i = date.length()-1; i >= 0; i--) {
				if(Character.isDigit(date.charAt(i))){
					if((index == 2 && s1[index].length() != 4) || s1[index].length() != 2) {
						s1[index] += date.charAt(i);
					}
					else {
						index++;
						i++;
					}
				}
				else index++;
			}
			
			
			for(int i = 0; i < 3; i++) {
				String sTemp="";
				for(int j = s1[i].length()-1; j >= 0; j--) {
					sTemp += s1[i].charAt(j);
				}
				s1[i] = sTemp;
			}
			
		}
		year = Integer.parseInt(s1[2]);
		if(americanStyleDate) {
			day = Integer.parseInt(s1[1]);
			month = Integer.parseInt(s1[0]);
		}
		else {
			day = Integer.parseInt(s1[0]);
			month = Integer.parseInt(s1[1]);
		}
	}
	/**
	 * Customized toString method to return '| date | maxTemp | minTemp | avgTemp | windSpeed |'
	 */
	public String toString() {
		if(date.length() < 8) {
			return "--- Invalid Date ---";
		}
		DecimalFormat dateDf = new DecimalFormat("00-");
		DecimalFormat infoDf = new DecimalFormat("0.00");
		
		String returnString = "| " + dateDf.format(day) + dateDf.format(month) + year + " | "
		+ status + " | "+ (hasMaxTemp ? infoDf.format(maxTemp) : "N/A") + " | " + (hasMinTemp ? infoDf.format(minTemp) : "N/A") + " | " +
		(hasAvgTemp ? infoDf.format(avgTemp): "N/A") + " | "+ (hasWindSpeed ? infoDf.format(windSpeed) : "N/A") + " |";
		
		return returnString;
	}
	/**
	 * set maxTemp with a double value
	 * @param temp value to be set for maxTemp
	 */
	public void setMaxTemp(double temp) {
		maxTemp = temp;
		hasMaxTemp = true;
		autoSetAvgtemp();//set the avgTemp when max and min temps are available
	}
	/**
	 * set minTemp with a double value
	 * @param temp value to be set for minTemp
	 */
	public void setMinTemp(double temp) {
		minTemp = temp;
		hasMinTemp = true;
		autoSetAvgtemp();//set the avgTemp when max and min temps are available
	}
	/**
	 * set avgTemp with a double value
	 * @param temp value to be set for avgTemp
	 */
	public void setAvgTemp(double temp) {
		hasAvgTemp = true;
		avgTemp = temp;
	}
	/**
	 * set windSpeed with a double value
	 * @param speed value to be set for windSpeed
	 */
	public void setWindSpeed(double speed) {
		hasWindSpeed = true;
		windSpeed = speed;
	}
	/**
	 * set maxTemp with a String value
	 * @param temp value in string to be set for maxTemp
	 */
	public void setMaxTemp(String temp) {
		maxTemp = Double.parseDouble(temp);
		hasMaxTemp = true;
		autoSetAvgtemp();//set the avgTemp when max and min temps are available
	}
	/**
	 * set minTemp with a String value
	 * @param temp value in string to be set for minTemp
	 */
	public void setMinTemp(String temp) {
		minTemp = Double.parseDouble(temp);
		hasMinTemp = true;
		autoSetAvgtemp(); //set the avgTemp when max and min temps are available
	}
	/**
	 * set avgTemp with a String value
	 * @param temp value in string to be set for avgTemp
	 */
	public void setAvgTemp(String temp) {
		hasAvgTemp = true;
		avgTemp = Double.parseDouble(temp);
	}
	/**
	 * set windSpeed with a String value
	 * @param speed value in string to be set for windSpeed
	 */
	public void setWindSpeed(String speed) {
		hasWindSpeed = true;
		windSpeed = Double.parseDouble(speed);
	}
	/**
	 * set status with a String value
	 * @param status string status(Rainy, sunny, cloudy, etc...) to be set for status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * return maxTemp
	 * @return maxTemp
	 */
	public double getMaxTemp() {
		return maxTemp;
	}
	/**
	 * return minTemp
	 * @return minTemp
	 */
	public double getMinTemp() {
		return minTemp;
	}
	/**
	 * return avgTemp
	 * @return avgTemp
	 */
	public double getAvgTemp() {
		return avgTemp;
	}
	/**
	 * return windSpeed
	 * @return windSpeed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}
	/**
	 * return status
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * return integer day from date
	 * @return day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * return integer month from date
	 * @return month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * return integer year from date
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * return string date in form DD-MM-YYYY
	 * @return date String date in form DDMMYYYY
	 */
	public String getDate() {
		if(date.length() < 8) {
			return "--- Invalid Date ---";
		}
		DecimalFormat dateDf = new DecimalFormat("00-");
		return dateDf.format(day)+dateDf.format(month)+year;
	}
	/**
	 * set avgTemp automatically whenever both min and max temps are available
	 */
	private void autoSetAvgtemp(){
		if(hasMinTemp && hasMaxTemp) {
			avgTemp = (minTemp + maxTemp)/2;
		}
	}
	/**
	 * clear every data in object
	 */
	public void clear() {
		maxTemp = 0;
		minTemp = 0;
		avgTemp = 0;
		windSpeed = 0;
		hasMaxTemp = false;
		hasMinTemp = false;
		hasAvgTemp = false;
		hasWindSpeed = false;
		status = "N/A";
	}
}
