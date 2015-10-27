package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import java.util.List;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType.SUBTRACT;

/**
 * @author Simon Kalt
 */
public class JSONSubtract extends JSONNumericOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONSubtract() {
        this(null, null);
    }

    public JSONSubtract(String attribute, List<Object> objects) {
        super(SUBTRACT, attribute, objects);
    }
}
