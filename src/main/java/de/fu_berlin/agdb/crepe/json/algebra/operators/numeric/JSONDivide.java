package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import java.util.List;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType.DIVIDE;

/**
 * @author Simon Kalt
 */
public class JSONDivide extends JSONNumericOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONDivide() {
        this(null, null);
    }

    public JSONDivide(String attribute, List<Object> objects) {
        super(DIVIDE, attribute, objects);
    }
}
