package dec82012;

import java.util.ArrayList;


// TrackerManager: A smart hub for coordinating all Tracker activities
public class TrackerManager {

	// A master list of all Trackers -- one for each stock
	private static ArrayList<Tracker> trackers = new ArrayList<Tracker>();
	

	public static void addTracker(String someUserID){
		trackers.add(new Tracker(someUserID));
	}

	
	public static Tracker getTracker(int someTrackerIndex) {
		return trackers.get(someTrackerIndex);
	}
	
}


