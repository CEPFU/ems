package de.fu_berlin.agdb.crepe.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;

import static java.util.Objects.requireNonNull;

/**
 * @author Simon Kalt
 */
public class BinaryOp extends Operator {
    @Nonnull
    private final List<Operator> operands;
    @Nonnull
    private final BinaryOperatorType type;

    /**
     * Creates a binary operator of the specified type and operands.
     *
     * @param ofOperands A list of operands for this operator
     * @param type       Type of the operator
     */
    public BinaryOp(@Nonnull BinaryOperatorType type, @Nonnull List<Operator> ofOperands) {
        this.operands = requireNonNull(ofOperands);
        this.type = requireNonNull(type);

        // For backwards compatibility:
        this.children = ofOperands.toArray(new Operator[ofOperands.size()]);
    }

    /**
     * Returns a new {@code and} operator of the specified operators.
     *
     * @param ofOperands A list of operands for this operator
     */
    @Nonnull
    public static BinaryOp and(@Nonnull List<Operator> ofOperands) {
        return new BinaryOp(BinaryOperatorType.AND, ofOperands);
    }

    /**
     * Returns a new {@code or} operator of the specified operators.
     *
     * @param ofOperands A list of operands for this operator
     */
    @Nonnull
    public static BinaryOp or(@Nonnull List<Operator> ofOperands) {
        return new BinaryOp(BinaryOperatorType.OR, ofOperands);
    }

    /**
     * Returns a new {@code xor} operator of the specified operators.
     *
     * @param ofOperands A list of operands for this operator
     */
    @Nonnull
    public static BinaryOp xor(@Nonnull List<Operator> ofOperands) {
        return new BinaryOp(BinaryOperatorType.XOR, ofOperands);
    }

    @Override
    public boolean apply(IEvent event) throws OperatorNotSupportedException {
        boolean lastResult = type.getUnit();
        BinaryOperator<Boolean> operator = type.getOperator();
        for (Operator operand : operands) {
            lastResult = operator.apply(lastResult, operand.apply(event));
        }

        return lastResult;
    }

    @Nonnull
    @Override
    public String toString() {
        return String.format("%s(%s)", type, operands);
    }

    @Override
    public void reset() {
        operands.forEach(Operator::reset);
    }

    @Nonnull
    public List<Operator> getOperands() {
        return operands;
    }

    @Nonnull
    public BinaryOperatorType getType() {
        return type;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryOp binaryOp = (BinaryOp) o;

        if (!operands.equals(binaryOp.operands)) return false;
        return type == binaryOp.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operands, type);
    }
}
