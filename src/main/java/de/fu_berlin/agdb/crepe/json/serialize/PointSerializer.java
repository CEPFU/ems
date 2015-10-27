package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Point;
import de.fu_berlin.agdb.crepe.json.util.SimplePoint;

import java.io.IOException;

/**
 * Class to serialize {@link com.vividsolutions.jts.geom.Point} objects to JSON.
 * Adapted from <a href="http://stackoverflow.com/questions/27624940/map-a-postgis-geometry-point-field-with-hibernate-on-spring-boot">Stackoverflow</a>.
 *
 * @author Simon Kalt
 */
public class PointSerializer extends JsonSerializer<Point> {
    @Override
    public void serialize(Point value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value != null) {
            double lat = value.getY();
            double lon = value.getX();

            SimplePoint jsonPoint = new SimplePoint(lat, lon);
            provider.defaultSerializeValue(jsonPoint, jgen);
        } else {
            jgen.writeString("null");
        }
    }
}
