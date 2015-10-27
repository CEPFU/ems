package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType.LESS_EQUAL;

/**
 * @author Simon Kalt
 */
public class JSONLessEqual extends JSONComparisonOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONLessEqual() {
        this(null, null);
    }

    public JSONLessEqual(String attribute, JSONOperator toOperator) {
        super(LESS_EQUAL, attribute, toOperator);
    }

    public JSONLessEqual(String attribute, Object toObject) {
        super(LESS_EQUAL, attribute, toObject);
    }
}
