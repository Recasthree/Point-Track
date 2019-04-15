import java.time.ZonedDateTime;

import static java.lang.Math.*;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Nick Efford & YOUR NAME
 */
public class Point {
  // Constants useful for bounds checking, etc

  private static final double MIN_LONGITUDE = -180.0;
  private static final double MAX_LONGITUDE = 180.0;
  private static final double MIN_LATITUDE = -90.0;
  private static final double MAX_LATITUDE = 90.0;
  private static final double MEAN_EARTH_RADIUS = 6.371009e+6;

  // TODO: Define fields for time, longitude, latitude and elevation

  ZonedDateTime time;
  double longitude;
  double latitude;
  double elevation;
  
  // TODO: Define a constructor

  public Point() {
	  // Create a point and initialize it
	  this.time = null;
	  this.longitude = 0;
	  this.latitude = 0;
	  this.elevation = 0;
  }
  
  public Point(ZonedDateTime t, double Longitude, double Latitude, double Elevation) {
	  this.time = t;
	  // Judge the validity of each data
	  // If the data is valid, take the data to create point
	  // And if it is invalid, prompt the exception
	  if( Longitude >= MIN_LONGITUDE && Longitude <= MAX_LONGITUDE )
		  this.longitude = Longitude;
	  else
		  throw new GPSException("Error: False longitude");
	  if( Latitude >= MIN_LATITUDE && Latitude <= MAX_LATITUDE )
		  this.latitude = Latitude;
	  else
		  throw new GPSException("Error: False latitude");
	  if( Elevation > -MEAN_EARTH_RADIUS )
		  this.elevation = Elevation;
	  else
		  throw new GPSException("Error: False elevation");
  }
  
  // TODO: Define getters for the fields

  public ZonedDateTime getTime() {
	  return time;
  }
  public double getLongitude() {
	  return longitude;
  }
  public double getLatitude() {
	  return latitude;
  }
  public double getElevation() {
	  return elevation;
  }
  
  // TODO: Define a toString() method that meets requirements

  public String toString() {
	  return "(" + String.format("%.5f", longitude) + ", " + String.format("%.5f", latitude) + "), " + String.format("%.1f", elevation) + " m"; 
  }
  
  // Do not alter anything beneath this comment

  /**
   * Computes the great-circle distance or orthodromic distance between
   * two points on a spherical surface, using Vincenty's formula.
   *
   * @param p First point
   * @param q Second point
   * @return Distance between the points, in metres
   */
  public static double greatCircleDistance(Point p, Point q) {
    double phi1 = toRadians(p.getLatitude());
    double phi2 = toRadians(q.getLatitude());

    double lambda1 = toRadians(p.getLongitude());
    double lambda2 = toRadians(q.getLongitude());
    double delta = abs(lambda1 - lambda2);

    double firstTerm = cos(phi2)*sin(delta);
    double secondTerm = cos(phi1)*sin(phi2) - sin(phi1)*cos(phi2)*cos(delta);
    double top = sqrt(firstTerm*firstTerm + secondTerm*secondTerm);

    double bottom = sin(phi1)*sin(phi2) + cos(phi1)*cos(phi2)*cos(delta);

    return MEAN_EARTH_RADIUS * atan2(top, bottom);
  }
}
