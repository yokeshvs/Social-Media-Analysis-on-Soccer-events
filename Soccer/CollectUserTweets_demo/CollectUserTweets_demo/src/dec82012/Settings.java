package dec82012;

/* 
 * This class makes it easier to transition code from one computer to another.  
 */

public class Settings {
	
	// To update all local settings, all you will need to do is change the currentUser string
	private static String ryan = "ryan";
	private static String hari = "hari";
	private static String serv = "server";
	private static String yoki = "yoki";
	private static String currentUser = yoki;
			
	// Database settings
	private static String url = null;
	private static String dbName = null;
	private static String userName = null; 
	private static String password = null;
	
	// File path for log file
	private static String filePathForLogFile = null;
	
	// File path for .csv file
	private static String filePathForCsvFile = null;
	
	// The number of seconds in between each scrape session
	private static long scrapeTimer = 60L;
	
	private static int numberOfScrapingSessions = 1;
	
	public static void configure(){
		if(currentUser.equals(ryan)){
			url = "jdbc:mysql://localhost:3306/";
			dbName = "TaftiZotti";
			userName = "root"; 
			password = "";
			filePathForLogFile = "/Users/ryanzotti/Desktop/";
			filePathForCsvFile = "/Users/ryanzotti/Desktop/";
		} else if(currentUser.equals(hari)){
			url = "jdbc:mysql://localhost:3306/";
			dbName = "test";
			userName = "root"; 
			password = "hari";
			//filePathForLogFile = "C:\\JavaDev\\Tafti_Zotti_Research\\LogFiles\\";
			//filePathForCsvFile = "C:\\JavaDev\\Tafti_Zotti_Research\\TwitterScrapingOutput";
			filePathForLogFile= "D:\\RA";	
			filePathForCsvFile = "D:\\RA\\TwitterYahooScrapingOutput";		
		} else if(currentUser.equals(yoki)){
			url = "jdbc:mysql://localhost:3306/";
			dbName = "usertweetanalysis";
			userName = "root"; 
			password = "Yoki@123";
			//filePathForLogFile = "C:\\JavaDev\\Tafti_Zotti_Research\\LogFiles\\";
			//filePathForCsvFile = "C:\\JavaDev\\Tafti_Zotti_Research\\TwitterScrapingOutput";
			filePathForLogFile= "C:\\Users\\yogi8\\Documents\\Course Works\\IDS 521 - ADBMS\\Optional Hw2\\Log Files\\";	
			filePathForCsvFile = "C:\\Users\\yogi8\\Documents\\Course Works\\IDS 521 - ADBMS\\Optional Hw2\\CSV Files\\";		
		} else if(currentUser.equals(serv)){
			url = "";
			dbName = "";
			userName = ""; 
			password = "";
			filePathForLogFile= "D:\\RA";	
			filePathForCsvFile = "D:\\RA\\TwitterYahooScrapingOutput";		
		} 
	}
	
	public static int getNumberOfScrapingSessions() {
		return numberOfScrapingSessions;
	}
	
	public static long getScrapeTimer(){
		return scrapeTimer;
	}
	
	public static String getMySQLURL(){
		String input = null;
		input = url+dbName;
		return input;
	}
	
	public static String getMySQLUserName(){
		return userName;
	}
	
	public static String getMySQLPassword(){
		return password;
	}
	
	public static String getFilePathForLogFile(){
		return filePathForLogFile;
	}
	
	public static String getFilePathForCsvFile(){
		return filePathForCsvFile;
	}
	
}


