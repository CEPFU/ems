package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType.GREATER_EQUAL;

/**
 * @author Simon Kalt
 */
public class JSONGreaterEqual extends JSONComparisonOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONGreaterEqual() {
        this(null, null);
    }

    public JSONGreaterEqual(String attribute, JSONOperator toOperator) {
        super(GREATER_EQUAL, attribute, toOperator);
    }

    public JSONGreaterEqual(String attribute, Object toObject) {
        super(GREATER_EQUAL, attribute, toObject);
    }
}
