package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import java.util.List;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType.MULTIPLY;

/**
 * @author Simon Kalt
 */
public class JSONMultiply extends JSONNumericOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONMultiply() {
        this(null, null);
    }

    public JSONMultiply(String attribute, List<Object> objects) {
        super(MULTIPLY, attribute, objects);
    }
}
