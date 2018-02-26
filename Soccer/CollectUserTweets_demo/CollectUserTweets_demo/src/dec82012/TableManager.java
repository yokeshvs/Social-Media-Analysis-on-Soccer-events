package dec82012;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class TableManager {
	
	public static String tweetsUserTableName;
	public static Connection con = null; 
	private static Logger fileLogger = Logger.getLogger("fileLogger");
	private static Logger emailLogger = Logger.getLogger("emailLogger");
	
	TableManager() {
		//Does Nothing
	}
	
	TableManager(Connection someConnection, String someDate) throws SQLException {
		con = someConnection;
		tweetsUserTableName = "tweetsUser_"+someDate;
		try {
			
			DatabaseMetaData databaseMetaData = con.getMetaData();
			ResultSet resultSetTables = databaseMetaData.getTables(null, null, tweetsUserTableName, null);
			if(resultSetTables.next()){
				// If the table already exists, don't try to create it
			} else {
				this.makeTweetsUserTable();
			}
			
			
		} catch (Exception e) {
			fileLogger.error("There was a problem.",e);
			emailLogger.error("Error", e);
			e.printStackTrace();	
		}
	}
		
	public void makeTweetsUserTable() throws SQLException {
		Statement statement = null;
		String sql = "create table " + tweetsUserTableName + "(" + 
			"id integer(11), " + 
			"textOfTweet varchar(150), " +
			"userScreenName varchar(30), " +
			"followers varchar(30), " +
			"isRetweet varchar(5), " + 
			"statusId varchar(25), " + 
			"userId varchar(20), " + 
			"timestamp varchar (60)" +
			")";
		try {
			statement = con.createStatement();
			statement.execute(sql);
			con.commit();		
		} catch (SQLException e) {
			fileLogger.error("There was a problem.",e);
			emailLogger.error("Error", e);
			e.printStackTrace();
		}
	}
}
