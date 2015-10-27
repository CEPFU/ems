package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType;

import java.util.List;

/**
 * @author Simon Kalt
 */
public class JSONAdd extends JSONNumericOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONAdd() {
        this(null, null);
    }

    public JSONAdd(String attribute, List<Object> objects) {
        super(NumericOperationType.ADD, attribute, objects);
    }
}
