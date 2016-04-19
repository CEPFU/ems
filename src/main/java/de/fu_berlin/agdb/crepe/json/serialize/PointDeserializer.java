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
     * SRID 4326
     */
    private static final int SRID = 4326;
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
