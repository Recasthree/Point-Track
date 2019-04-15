import java.util.Scanner;
import java.io.FileNotFoundException;

public class TrackInfo{
	
	public static void main(String[] args) {
		
		//read the file
		Track walkTrack = new Track();
		System.out.println("Please input the name of the file you want to read: ");
		Scanner input = new Scanner(System.in);
		String fileName = input.nextLine();
		try {
			walkTrack.readFile(fileName);
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
			System.exit(1);
		}
		input.close();		//close the input
		
		//Set objects to store relevant values
		int number = 0;
		Point lowest_p = new Point();
		Point highest_p = new Point();
		double totalDistance = 0.0;
		double averageSpeed = 0.0;
		
		number = walkTrack.size();
		lowest_p = walkTrack.lowestPoint();
		highest_p = walkTrack.highestPoint();
		totalDistance = walkTrack.totalDistance()/1000;
		averageSpeed = walkTrack.averageSpeed();

        //print the information of the track
		System.out.println(number + " points in track\n");
		System.out.println("Lowest point is " + lowest_p.toString());
		System.out.println("Highest point is " + highest_p.toString());
		System.out.println("Total distance = " + String.format("%.3f", totalDistance) + " km");
		System.out.println("Average speed = " + String.format("%.3f", averageSpeed) + " m/s");
	}
}
