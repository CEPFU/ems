package de.fu_berlin.agdb.crepe.json.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOperatorType;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import java.util.List;

/**
 * @author Simon Kalt
 */
public class JSONAnd extends JSONBinaryOp {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONAnd() {
        this(null);
    }

    public JSONAnd(List<JSONOperator<?>> ofOperands) {
        super(BinaryOperatorType.AND, ofOperands);
    }
}
