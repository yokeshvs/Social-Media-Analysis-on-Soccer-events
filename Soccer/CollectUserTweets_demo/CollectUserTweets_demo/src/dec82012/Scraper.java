package dec82012;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Scraper {

	Connection con = null;
	PreparedStatement preparedStatementForStoringTweets = null;
	PreparedStatement preparedStatementForStoringYahooFinanceData = null;
	Statement statementToGetAStockSymbolToScrapeFromYahooFinance = null;
	PreparedStatement preparedStatementStoringAggregateData = null;
	PreparedStatement preparedStatementForStoringUserTweets = null;
	int quantityOfCompaniesToTrack = -1;
	
	private static final String CONSUMER_KEY = "XHjOCgNDjfYtrH0iwSCElRJXz";
	private static final String CONSUMER_SECRET = "CdAluXf9G96ICotIh3OydtSitvjgzE4epc0Lzo6Kyr8cy7Vigo";
	private static final String ACCESS_TOKEN = "572401887-hS4Zrnnp0Sdo566WCggFLyl3lftinqajfSOoiSec";
	private static final String ACCESS_TOKEN_SECRET = "MF4BZQiFP7B1PYqozt2lES0h2SGUguZPFMGkMAp5yk08m";
	
	private static Logger fileLogger = Logger.getLogger("fileLogger");
	private static Logger emailLogger = Logger.getLogger("emailLogger");
	
	public static int id = 1;

	private long[] UserIDToTrack;
	
	TwitterStream twitterStream;
	TwitterStream twitterStream2;
	
	
	 public static int increment() {
		 id++;
		 return id;
	  }
	 
	 Scraper(Connection someConnection, long[] someUserIDToTrack) throws SQLException {
			
			con = someConnection;
			UserIDToTrack = someUserIDToTrack;
			try {
				
				String queryForStoringUserTweets = "insert into " + TableManager.tweetsUserTableName + 
						"(" + 
						"id, " + 
						"textOfTweet, " +
						"userScreenName, " +
						"isRetweet, " + 
						"followers, " +
						"statusId, " + 
						"userId, " + 
						"timestamp" +  
						") " + 
						"values" +
						"(?,?,?,?,?,?,?,?)";
				
				preparedStatementForStoringUserTweets = con.prepareStatement(queryForStoringUserTweets);
				con.commit();
				
				
			} catch (SQLException e) {
				fileLogger.error("There was a problem.",e);
				e.printStackTrace();
				emailLogger.error("Error", e);
			} catch (Exception e) {
				fileLogger.error("There was a problem.",e);
				e.printStackTrace();
				emailLogger.error("Error", e);
			}
			
		}
		
	public void collectUserTweets() throws TwitterException, SQLException {
			
			/*
	    	 * This section takes the four variables and sends them to Twitter. Or it does something magical with them. 
	    	 * I don't know. It's not very well explained in the Twitter4J API.
	    	 * Just know that it's important. 
	    	 */
	    	ConfigurationBuilder cb = new ConfigurationBuilder(); 
			  cb.setDebugEnabled(true); 
			  cb.setOAuthConsumerKey(CONSUMER_KEY); 
			  cb.setOAuthConsumerSecret(CONSUMER_SECRET); 
			  cb.setOAuthAccessToken(ACCESS_TOKEN); 
			  cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET); 
			
			  /*
			   * When you want to get a Twitter Stream, you have to use the four credentials listed above.  
			   */
			  TwitterStreamFactory twitterStreamFactory2 = new TwitterStreamFactory( cb.build() ); 
			  twitterStream2 = twitterStreamFactory2.getInstance();
			  
	        StatusListener listener = new StatusListener() {
	            public void onStatus(Status status) {
	            	
	            	ScraperImplementation.incrementTotalUserTweets();
	            	
	            	
	            	// store each tweet that we get
	            	try {
	            		
	            		preparedStatementForStoringUserTweets.setInt(1, id);
	                	id++;
	                	//byte[] textOfTweet = status.getText().getBytes("UTF-8");
	                	//System.out.println(status.getText());
	                	String textOfTweet = status.getText();
	                	//System.out.println(textOfTweet);
	                	String userScreenName = status.getUser().getScreenName();
	                	String isRetweet = status.isRetweet() + "";
	                	String statusId = status.getId() + "";
	                	String userId = status.getUser().getId() + "";
	                	String followers = status.getUser().getFollowersCount() + "";
	                	java.sql.Timestamp timestamp = new java.sql.Timestamp(status.getCreatedAt().getTime());
	                	
	                	String textOfTweetStrippedOfAllNonAlphaNumericCharacters = null;
	                	
	                	textOfTweetStrippedOfAllNonAlphaNumericCharacters = textOfTweet.replaceAll("[^\\x00-\\x7F]", " ");
	                	
	                	preparedStatementForStoringUserTweets.setString(2, textOfTweetStrippedOfAllNonAlphaNumericCharacters);
	                	preparedStatementForStoringUserTweets.setString(3, userScreenName);
	                	preparedStatementForStoringUserTweets.setString(4, isRetweet);
	                	preparedStatementForStoringUserTweets.setString(5, followers);
	                	preparedStatementForStoringUserTweets.setString(6, statusId);
	                	preparedStatementForStoringUserTweets.setString(7, userId);
	                	preparedStatementForStoringUserTweets.setTimestamp(8, timestamp);
	                	preparedStatementForStoringUserTweets.executeUpdate();
	                    con.commit();
	                    
	                    //System.out.println(textOfTweet);
	                    //TrackerManager.checkTweet(textOfTweet, followers, userScreenName);
	                    
	                    //TrackerManager.checkTweet(textOfTweet.toString(), followers);
	                    
	            	} catch (SQLException e) {
	        			fileLogger.error("There was a problem.",e);
	        			e.printStackTrace();
	        			emailLogger.error("Error", e);
	            	} catch (Exception e) {
	        			fileLogger.error("There was a problem.",e);
	        			e.printStackTrace();
	        			emailLogger.error("Error", e);
	            	}
	            	
	            }

	            // The four lines of code below are require for the Twitter4J interface to work
	            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { 
	            }

	            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	            }

	            public void onScrubGeo(long userId, long upToStatusId) { 
	            }

	            public void onException(Exception ex) {
	           }

				@Override
				public void onStallWarning(StallWarning arg0) {
					// TODO Auto-generated method stub
					
				}
	        };
	        
	        /*
	         * These lasts lines down here are important. 
	         */
	        twitterStream2.addListener(listener);
	        FilterQuery filterQuery =new FilterQuery(0, UserIDToTrack, null); 
	        twitterStream2.filter(filterQuery);
	 
		}
}

