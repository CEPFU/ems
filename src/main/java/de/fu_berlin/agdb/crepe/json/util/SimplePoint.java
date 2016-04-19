package de.fu_berlin.agdb.crepe.json.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import de.fu_berlin.agdb.crepe.json.serialize.PointDeserializer;

/**
 * Simple latitude/longitude point
 *
 * @author Simon Kalt
 */
public class SimplePoint {
    private double latitude;
    private double longitude;

    public SimplePoint() {}

    public SimplePoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Point asGeometryPoint(GeometryFactory geometryFactory) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public Point asGeometryPoint() {
        return asGeometryPoint(PointDeserializer.GEOMETRY_FACTORY);
    }
}
