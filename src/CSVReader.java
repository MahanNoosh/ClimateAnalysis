import java.io. * ;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class will read the CSV file and import the needed data
 *
 * @author Mahan NourHosseinAliPour
 *
 */
public class CSVReader {
	/**
	 * stores the char or string that separated the items in CSV file
	 */
	private String delimiter;
	/**
	 * stores path of CSV file
	 */
	private String path;
	/**
	 * all the data from CSV stores here. data[ROW][COL]
	 */
	private String[][] data;
	
//======= Important data columns to import from =======//
	/**
	 * column of maxTemp in CSV file 0 base
	 */
	private int maxTempIndex;
	/**
	 * column of minTemp in CSV file 0 base
	 */
	private int minTempIndex;
	/**
	 * column of avgTemp in CSV file 0 base
	 */
	private int avgTempIndex;
	/**
	 * column of windSpeed in CSV file 0 base
	 */
	private int windSpeedIndex;
	/**
	 * column of status in CSV file 0 base
	 */
	private int statusIndex;
	/**
	 * column of date in CSV file 0 base
	 */
	private int dateIndex;
	
	/**
	 * constructor with file path and delimiter
	 * @param path file address
	 * @param delimiter with what the file items got separated 
	 */
	public CSVReader(String path, String delimiter){
		  this.path = path;
		  this.delimiter = delimiter;
		  read();
	}
	/**
	 * constructor with CSV path separated with ',' 
	 * @param path CSV file address
	 */
	public CSVReader(String path){
		this(path, ",");
	}
	/**
	 * constructor with file path delimiter and index of important data(0 base)
	 * @param path file address
	 * @param delimiter with what file items got separated
	 * @param dateIndex index of column including date(0 base)
	 * @param maxTempIndex index of column including maxTemp(0 base)
	 * @param minTempIndex index of column including minTemp(0 base)
	 * @param avgTempIndex index of column including avgTemp(0 base)
	 * @param windSpeedIndex index of column including windSpeed(0 base)
	 * @param statusIndex index of column including status(0 base)
	 */
	public CSVReader(String path, String delimiter, int dateIndex, int maxTempIndex, int minTempIndex, int avgTempIndex, int windSpeedIndex, int statusIndex){
		  this.path = path;
		  this.delimiter = delimiter;
		  this.dateIndex = dateIndex;
		  this.maxTempIndex = maxTempIndex;
		  this.minTempIndex = minTempIndex;
		  this.avgTempIndex = avgTempIndex;
		  this.windSpeedIndex = windSpeedIndex;
		  this.statusIndex = statusIndex;
		  read();
	}
	/**
	 * return data in lineNum mentioned, in an string array
	 * @param lineNum row number to get data from(0 base)
	 * @return data[lineNum]
	 */
	public String[] getLine(int lineNum) { 
		return data[lineNum];
	}
	/**
	 * return data in an 2d string array
	 * @return data
	 */
	public String[][] getData(){
	    return data;
	}
	/**
	 * return a string representing the date in the lineNum mentioned, if index of date has been set
	 * @param lineNum row number to get date from(0 base)
	 * @return data[lineNum][dateIndex]
	 */
	public String getDate(int lineNum) {
		if(dateIndex != -1) {
			return data[lineNum][dateIndex];
		}
		return null;
	}
	/**
	 * return a string representing the max temperature in the lineNum mentioned, if index of max temperature has been set
	 * @param lineNum row number to get maxTemp from(0 base)
	 * @return data[lineNum][maxTempIndex]
	 */
	public String getMaxTemp(int lineNum) {
		if(maxTempIndex != -1) {
			return data[lineNum][maxTempIndex];
		}
		return null;
	}
	/**
	 * return a string representing the min temperature in the lineNum mentioned, if index of min temperature has been set
	 * @param lineNum row number to get minTemp from(0 base)
	 * @return data[lineNum][minTempIndex]
	 */
	public String getMinTemp(int lineNum) {
		if(minTempIndex != -1) {
			return data[lineNum][minTempIndex];	
		}
		return null;
	}
	/**
	 * return a string representing the avg temperature in the lineNum mentioned, if index of avg temperature has been set
	 * @param lineNum row number to get avgTemp from(0 base)
	 * @return data[lineNum][avgTempIndex]
	 */
	public String getAvgTemp(int lineNum) {
		if(avgTempIndex != -1) {
			return data[lineNum][avgTempIndex];
		}
		return null;
	}
	/**
	 * return a string representing the wind speed in the lineNum mentioned, if index of wind speed has been set
	 * @param lineNum lineNum row number to get data from(0 base)
	 * @return data[lineNum][windSpeedIndex]
	 */
	public String getWindSpeed(int lineNum) {
		if(windSpeedIndex != -1) {
			return data[lineNum][windSpeedIndex];
		}
		return null;
	}
	/**
	 * return a string representing the status in the lineNum mentioned, if index of status has been set
	 * @param lineNum row number to get status from(0 base)
	 * @return data[lineNum][statusIndex]
	 */
	public String getStatus(int lineNum) {
		if(statusIndex != -1) {
			return data[lineNum][statusIndex];
		}
		return null;
	}
	/**
	 * set the column of date in each row 0 base
	 * @param index column index of date in each row(0 base)
	 */
	public void setDateIndex(int index) {
		dateIndex=index;
	}
	/**
	 * set the column of max temperature in each row 0 base
	 * @param index column index of maxTemp in each row(0 base)
	 */
	public void setMaxTempIndex(int index) {
		maxTempIndex=index;
	}
	/**
	 * set the column of min temperature in each row 0 base
	 * @param index column index of minTemp in each row(0 base)
	 */
	public void setMinTempIndex(int index) {
		minTempIndex=index;
	}
	/**
	 * set the column of avg temperature in each row 0 base
	 * @param index column index of avgTemp in each row(0 base)
	 */
	public void setAvgTempIndex(int index) {
		avgTempIndex=index;
	}
	/**
	 * set the column of wind speed in each row 0 base
	 * @param index column index of windSpeed in each row(0 base)
	 */
	public void setWindSpeedIndex(int index) {
		windSpeedIndex=index;
	}
	/**
	 * set the column of status in each row 0 base
	 * @param index column index of status in each row(0 base)
	 */
	public void setStatusIndex(int index) {
		statusIndex=index;
	}
	/**
	 * return number of rows in CSV file
	 * @return data.length
	 */
	public int getNumRow() {
		return data.length;
	}
	/**
	 * return number of columns in CSV file
	 * @return data[0].length
	 */
	public int getNumCol() {
		return data[0].length;
	}
	/**
	 * reads the CSV file and put it in 2d string array 'data'
	 */
	private void read() {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            List<String[]> dataList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line.split(delimiter));
            }
            data = dataList.toArray(new String[0][]);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}