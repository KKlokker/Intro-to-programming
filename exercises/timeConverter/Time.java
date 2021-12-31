public class Time {
	 	public static void main(String[] args){
			int hours = 4;
			int minutes = 29;
			int seconds = 21;
			int totalSeconds = secondsInTimeInterval(seconds, minutes, hours);
			System.out.println("There is " + totalSeconds + " seconds in " + hours + ":" + minutes + ":" + seconds);
			System.out.println(totalSeconds + " equals " + timeIntervalFromSeconds(totalSeconds));
		}
i		//Returns the time interval given in seconds. Precondition: positive arguments
		private static int secondsInTimeInterval(int seconds, int minutes, int hours){
			int secondsInHour = hours * 60 * 60;
		       	int secondsInMinutes = minutes * 60;
			int totalSeconds = seconds + secondsInMinutes + secondsInHour;
			return totalSeconds;
		}
		//Return a string with a time formattet version of the given seconds. Precondition: positive arguments
		private static String timeIntervalFromSeconds(int totalSeconds){
			int seconds = totalSeconds % 60;
			int hours = totalSeconds/(60*60);
			int minutes = (totalSeconds % 3600)/60; // Calculate the remaining seconds when hours is taken nad converting to minutes
			return hours + ":"+minutes+":"+seconds;
		}
}
