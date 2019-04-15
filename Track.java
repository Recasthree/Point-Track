import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.io.FileNotFoundException;

public class Track extends Point {
	
	// TODO: A array list for storing a sequence of Point objects
	
	ArrayList<Point> track=new ArrayList<Point>();
	Track() { }
	
	//A method that read the data of the track from the file
	
	void readFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if( !file.exists() )
			throw new FileNotFoundException("Error: The file does not exist");
		else {
			Scanner input = new Scanner(file);
			String listHead = input.nextLine();		// Skip the list head
			track.clear();		// Clear the old data of the track

			//Input the data of the file into the string
			while( input.hasNextLine() ) {
			    // Read by line and add data for each line to the list collection
			    String string = input.nextLine();
			    String[] dataCollection = string.split(",");
			    
				//Define the point according to the data of the input
				if( dataCollection.length < 4 )
					throw new GPSException("Error: The data is less");
				else {
					//Set a new point object and assign its value, then add it into the track
				    Point p = new Point();
				    p.time = ZonedDateTime.parse(dataCollection[0]);
				    p.longitude = Double.parseDouble(dataCollection[1]);
					p.latitude = Double.parseDouble(dataCollection[2]);
				  	p.elevation = Double.parseDouble(dataCollection[3]);
					add(p);
				}
			}
			input.close();		//Remember close the input
		}
	}

	// TODO: Adds a new point to the end of the track
	
	public void add(Point p) {
		track.add(p);
	}
	
	// TODO: Returns the number of points currently stored in the track
	
	public int size() {
		return track.size();
	}
	
	// TODO: Returns the Point object stored at a given position in the sequence
	// TODO: Specified as an int parameter
	
	public Point get(int index) throws GPSException {
		//track.size() return the number of the point
		//If we want to call the point of the track, we should use track.size()-1 (Because of the characteristics of arrays)
		if( index < 0 || index > track.size()-1 )
			throw new GPSException("Error: Input a wrong number");
		else
			return track.get(index);
	}
	
	// TODO: Return the Point objects having the lowest elevations
	
	public Point lowestPoint() throws GPSException {
		//Start from the first point, compare the elevations one by one
	    Point lowest_p = get(0);
	    if( track == null || ( track != null && track.size() == 0 ) )
	    	throw new GPSException("Error: The track is null");
	    else {
			for(int i = 0; i < track.size(); i++) {
				if( get(i).getElevation() < lowest_p.getElevation() )
					lowest_p = get(i);
			}	
		}
		return lowest_p;
	}

	// TODO: Return the Point objects having the highest elevations
	
	public Point highestPoint() throws GPSException {
		//Start from the first point, compare the elevations one by one
	    Point highest_p = get(0);
	    if( track == null || ( track != null && track.size() == 0 ) )
	    	throw new GPSException("Error: The track is null");
	    else {
	    	for(int i = 0; i < track.size(); i++) {
				if( get(i).getElevation() > highest_p.getElevation() )
					highest_p = get(i);
			}
		}
		return highest_p;		
	}
	
	// TODO: Returns the total distance travelled in metres
	
	double totalDistance() throws GPSException {
		double total_dis = 0;
		if( track == null || ( track != null && track.size() == 0 ) )
	    	throw new GPSException("Error: The track is null");
		else if( track.size() == 1 )
			//Distance cannot be calculated when there is only one point
			throw new GPSException("Error: The data is less");
		else {
			//Use track.size()-1 because of the characteristics of arrays
			for(int i = 0; i < track.size()-1; i++)
				total_dis += Point.greatCircleDistance( get(i), get(i+1) );
		}
		return total_dis;
	}
	
	// TODO: Returns the average speed along the track
	
	public double averageSpeed() throws GPSException {
		double distant = totalDistance();
		double totalTime = 0;
		if( track == null || ( track != null && track.size() == 0 ) )
	    	throw new GPSException("Error: The track is null");
		if( track.size() == 1 )
			//Distance cannot be calculated when there is only one point
			throw new GPSException("Error: The number of the point is too less");
		else
			totalTime = ChronoUnit.SECONDS.between( get(0).getTime(), get(track.size()-1).getTime() );
	    return distant/totalTime;
	}
	
}
