package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import de.fu_berlin.agdb.crepe.json.util.SimplePoint;

import java.io.IOException;

/**
 * Class to deserialize {@link com.vividsolutions.jts.geom.Point} objects from JSON.
 * Adapted from <a href="http://stackoverflow.com/questions/27624940/map-a-postgis-geometry-point-field-with-hibernate-on-spring-boot">Stackoverflow</a>.
 *
 * @author Simon Kalt
 */
public class PointDeserializer extends JsonDeserializer<Point> {

    /**
     * SRID 26910 is used here
     * that means:
     * PROJCS["NAD83 / UTM zone 10N",GEOGCS["NAD83",DATUM["North_American_Datum_1983",SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],TOWGS84[0,0,0,0,0,0,0],AUTHORITY["EPSG","6269"]],PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.0174532925199433,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4269"]],UNIT["metre",1,AUTHORITY["EPSG","9001"]],PROJECTION["Transverse_Mercator"],PARAMETER["latitude_of_origin",0],PARAMETER["central_meridian",-123],PARAMETER["scale_factor",0.9996],PARAMETER["false_easting",500000],PARAMETER["false_northing",0],AUTHORITY["EPSG","26910"],AXIS["Easting",EAST],AXIS["Northing",NORTH]]
     * TODO: externalize value?!
     */
    private static final int SRID = 26910;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);

    @Override
    public Point deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            SimplePoint jsonPoint = jp.readValueAs(SimplePoint.class);
            return geometryFactory.createPoint(
                    new Coordinate(jsonPoint.getLatitude(), jsonPoint.getLongitude())
            );
        } catch (Exception e) {
            return null;
        }
    }
}
