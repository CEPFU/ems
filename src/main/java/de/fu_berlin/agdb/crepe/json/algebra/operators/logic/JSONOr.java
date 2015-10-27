package de.fu_berlin.agdb.crepe.json.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOperatorType;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import java.util.List;

/**
 * @author Simon Kalt
 */
public class JSONOr extends JSONBinaryOp {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONOr() {
        this(null);
    }

    public JSONOr(List<JSONOperator<?>> ofOperands) {
        super(BinaryOperatorType.OR, ofOperands);
    }
}
