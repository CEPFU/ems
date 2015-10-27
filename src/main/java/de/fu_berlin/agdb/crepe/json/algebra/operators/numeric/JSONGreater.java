package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType.GREATER;

/**
 * @author Simon Kalt
 */
public class JSONGreater extends JSONComparisonOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONGreater() {
        this(null, null);
    }

    public JSONGreater(String attribute, JSONOperator<?> toOperator) {
        super(GREATER, attribute, toOperator);
    }

    public JSONGreater(String attribute, Object toObject) {
        super(GREATER, attribute, toObject);
    }
}
