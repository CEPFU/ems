package de.fu_berlin.agdb.crepe.algebra.operators.logic;

import javax.annotation.Nonnull;
import java.util.function.BinaryOperator;

/**
 * @author Simon Kalt
 */
public enum BinaryOperatorType {
    AND(true, Boolean::logicalAnd),
    OR(false, Boolean::logicalOr),
    XOR(false, Boolean::logicalXor);

    private final boolean unit;
    @Nonnull
    private final BinaryOperator<Boolean> operator;

    BinaryOperatorType(boolean unit, @Nonnull BinaryOperator<Boolean> operator) {
        this.unit = unit;
        this.operator = operator;
    }

    /**
     * Returns the associated binary operator.
     */
    @Nonnull
    public BinaryOperator<Boolean> getOperator() {
        return operator;
    }

    /**
     * Returns the associated unit.
     */
    public boolean getUnit() {
        return unit;
    }
}
