package dec82012;

/*
 *  This is a static class that parses timestamp strings, and outputs the time zone.
 */

public class TimeZoneAnalyzer {
	
	public static String[] timeZones;

	public static void startup(){
		timeZones = new String[4];
		timeZones[0] = "EST";
		timeZones[1] = "EDT";
		timeZones[2] = "CST";
		timeZones[3] = "CDT";
	}

	public static String extractTimeZone(String timestamp){
		String timeZone = null;
		for(int timeZoneCounter = 0; timeZoneCounter < timeZones.length; timeZoneCounter++){
			if(timestamp.toLowerCase().contains(timeZones[timeZoneCounter].toLowerCase())){
				timeZone = timeZones[timeZoneCounter];
				break;
			}
		}
		return timeZone;
	}
	
}

