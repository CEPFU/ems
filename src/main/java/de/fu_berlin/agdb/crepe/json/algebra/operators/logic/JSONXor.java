package de.fu_berlin.agdb.crepe.json.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOperatorType;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import java.util.List;

/**
 * @author Simon Kalt
 */
public class JSONXor extends JSONBinaryOp {
    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONXor() {
        this(null);
    }

    public JSONXor(List<JSONOperator<?>> ofOperands) {
        super(BinaryOperatorType.XOR, ofOperands);
    }
}
