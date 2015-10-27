package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType.LESS;

/**
 * @author Simon Kalt
 */
public class JSONLess extends JSONComparisonOperation {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONLess() {
        this(null, null);
    }

    public JSONLess(String attribute, JSONOperator toOperator) {
        super(LESS, attribute, toOperator);
    }

    public JSONLess(String attribute, Object toObject) {
        super(LESS, attribute, toObject);
    }
}
