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
    private ZonedDateTime timestamp;
    private double longitude;
    private double latitude;
    private double elevation;


    // TODO: Define a constructor
    public Point(ZonedDateTime timestamp, double longitude, double latitude, double elevation) {
        if (longitude < MIN_LATITUDE || longitude > MAX_LONGITUDE) {
            throw new GPSException("longitude is invaild");
        }
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new GPSException("latitude is invaild");
        }
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.latitude = latitude;
        this.elevation = elevation;
    }

    // TODO: Define getters for the fields
    public ZonedDateTime getTime() {
        return timestamp;
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
    @Override
    public String toString() {
        return String.format("(%.5f, %.5f), %.1f m", this.getLongitude(), this.getLatitude(), this.getElevation());
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

        double firstTerm = cos(phi2) * sin(delta);
        double secondTerm = cos(phi1) * sin(phi2) - sin(phi1) * cos(phi2) * cos(delta);
        double top = sqrt(firstTerm * firstTerm + secondTerm * secondTerm);

        double bottom = sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(delta);

        return MEAN_EARTH_RADIUS * atan2(top, bottom);
    }
}
