package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;

import javax.annotation.Nonnull;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

import static java.util.Objects.requireNonNull;

/**
 * Describes different types of numeric operations.
 *
 * @author Simon Kalt
 */
public enum NumericOperationType implements BinaryOperator<Number> {
    ADD(0,
            (x, y) -> x + y,
            (x, y) -> x + y,
            (x, y) -> x + y,
            (x, y) -> x + y
    ),
    SUBTRACT(0,
            (x, y) -> x - y,
            (x, y) -> x - y,
            (x, y) -> x - y,
            (x, y) -> x - y
    ),
    MULTIPLY(1,
            (x, y) -> x * y,
            (x, y) -> x * y,
            (x, y) -> x * y,
            (x, y) -> x * y
    ),
    DIVIDE(1,
            (x, y) -> x / y,
            (x, y) -> x / y,
            (x, y) -> x / y,
            (x, y) -> x / y
    );

    private final int unit;
    private final IntBinaryOperator integerOperator;
    private final LongBinaryOperator longOperator;
    private final BinaryOperator<Float> floatOperator;
    private final DoubleBinaryOperator doubleOperator;

    NumericOperationType(int unit,
                         IntBinaryOperator integerOperator,
                         LongBinaryOperator longOperator,
                         BinaryOperator<Float> floatOperator,
                         DoubleBinaryOperator doubleOperator) {
        this.unit = unit;
        this.integerOperator = integerOperator;
        this.longOperator = longOperator;
        this.floatOperator = floatOperator;
        this.doubleOperator = doubleOperator;
    }

    /**
     * Assets that the given object subclasses {@link Number}.
     *
     * @param obj The object to test
     * @throws OperatorNotSupportedException If the object isn't a number
     * @throws NullPointerException If the object is null
     */
    protected static void assertNumber(@Nonnull Object obj) throws OperatorNotSupportedException {
        requireNonNull(obj);
        if (!(obj instanceof Number))
            throw new OperatorNotSupportedException("Object " + obj + " is not a number.");
    }

    public int getUnit() {
        return unit;
    }

    public IntBinaryOperator getIntegerOperator() {
        return integerOperator;
    }

    public LongBinaryOperator getLongOperator() {
        return longOperator;
    }

    public BinaryOperator<Float> getFloatOperator() {
        return floatOperator;
    }

    public DoubleBinaryOperator getDoubleOperator() {
        return doubleOperator;
    }

    /**
     * Applies the operation to the given doubles.
     *
     * @param first  First operand
     * @param second Second operand
     * @return The result of applying the operation to the operands
     */
    public double apply(double first, double second) {
        return doubleOperator.applyAsDouble(first, second);
    }

    /**
     * Applies the operation to the given floats.
     *
     * @param first  First operand
     * @param second Second operand
     * @return The result of applying the operation to the operands
     */
    public float apply(float first, float second) {
        return floatOperator.apply(first, second);
    }

    /**
     * Applies the operation to the given longs.
     *
     * @param first  First operand
     * @param second Second operand
     * @return The result of applying the operation to the operands
     */
    public long apply(long first, long second) {
        return longOperator.applyAsLong(first, second);
    }

    /**
     * Applies the operation to the given integers.
     *
     * @param first  First operand
     * @param second Second operand
     * @return The result of applying the operation to the operands
     */
    public int apply(int first, int second) {
        return integerOperator.applyAsInt(first, second);
    }

    /**
     * Applies the operation to the given numbers.
     *
     * @param first  First operand
     * @param second Second operand
     * @return The result of applying the operation to the operands
     */
    public Number apply(Number first, Number second) {
        if (first instanceof Double || second instanceof Double) {
            return apply(first.doubleValue(), second.doubleValue());
        }

        if (first instanceof Float || second instanceof Float) {
            return apply(first.floatValue(), second.floatValue());
        }

        if (first instanceof Long || second instanceof Long) {
            return apply(first.longValue(), second.longValue());
        }

        return apply(first.intValue(), second.intValue());
    }

    /**
     * Applies the operation to the given objects.
     * Both objects need to be numbers.
     *
     * @param first  First operand
     * @param second Second operand
     * @return The result of applying the operation to the operands
     * @throws OperatorNotSupportedException If either parameter is not a number
     */
    public Number applyObj(@Nonnull Object first, @Nonnull Object second) throws OperatorNotSupportedException {
        assertNumber(first);
        assertNumber(second);
        return apply((Number) first, (Number) second);
    }
}
