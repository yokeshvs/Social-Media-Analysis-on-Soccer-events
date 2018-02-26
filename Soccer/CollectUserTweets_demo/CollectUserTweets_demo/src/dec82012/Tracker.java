package dec82012;

import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

// A Tracker is assigned to each stock to aggregate data in real-time
public class Tracker {

	private String userID;
	private int tweets = 0;
	private BigDecimal followers = new BigDecimal("0");
	
	private int timeIndex = -1;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	Tracker(String someUserID){
		setUserID(someUserID);
	}
	

	// Keeps the timeIndex consistent if the program is restarted on the same day
	public void setTimeIndex(int someTimeIndex){
		timeIndex = someTimeIndex;
	}
	

	public int getTimeIndex() {
		return timeIndex;
	}


	public void addTweet(){
		tweets++;
	}
	
	// Records the running total of followers for this scrape session
	public void recordFollowers(String someAmountOfFollowers){
		BigDecimal followersUpdate = new BigDecimal(someAmountOfFollowers);
		followers = followers.add(followersUpdate);
	}
	
	public String getAverageFollowers(){
		if(tweets > 0) {
			BigDecimal tweetCount = new BigDecimal(tweets + "");
			BigDecimal averageFollowers = followers.divide(tweetCount, 0, BigDecimal.ROUND_HALF_UP);
			followers = new BigDecimal("0");
			return averageFollowers.toString();
		} else {
			// to avoid a divide-by-zero problem
			return "mark as blank";
		}
		
	}

	
	// Calculates how much time passed between scraping sessions
	public Duration calculateDurationBetweenScrapes(DateTime someStartTime, DateTime someEndTime){
		Duration duration;
		duration = new Interval(someStartTime, someEndTime).toDuration();
		return duration;
	}


	
}
