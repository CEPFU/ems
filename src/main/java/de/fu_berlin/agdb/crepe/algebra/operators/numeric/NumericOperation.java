package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType.*;
import static java.util.Objects.requireNonNull;

/**
 * Match that performs numeric operations.
 *
 * @author Simon Kalt
 */
public class NumericOperation extends Match {
    @Nonnull
    private final NumericOperationType operation;
    @Nonnull
    private final String attribute;
    @Nonnull
    private Mode mode;
    private Operator firstOperator, secondOperator;
    private Object firstObject, secondObject;

    private NumericOperation(@Nonnull NumericOperationType operation, @Nonnull String attribute, @Nonnull Mode mode) {
        this.operation = requireNonNull(operation);
        this.attribute = requireNonNull(attribute);
        this.mode = requireNonNull(mode);
    }

    /**
     * Creates a numeric operation of the given type over two operators.
     *
     * @param operation      Type of the operation
     * @param firstOperator  First operand
     * @param secondOperator Second operand
     */
    public NumericOperation(@Nonnull NumericOperationType operation, @Nonnull String attribute, @Nonnull Operator firstOperator, @Nonnull Operator secondOperator) {
        this(operation, attribute, Mode.OPERATORS);
        this.firstOperator = requireNonNull(firstOperator);
        this.secondOperator = requireNonNull(secondOperator);

        // For backwards compatibility:
        this.setChildren(firstOperator, secondOperator);
    }

    /**
     * Creates a numeric operation of the given type over an object and an operator.
     *
     * @param operation Type of the operation
     * @param attribute attribute whose values the operation is performed on
     * @param first     first operand
     * @param second    second operand
     */
    public NumericOperation(@Nonnull NumericOperationType operation, @Nonnull String attribute, @Nonnull Object first, @Nonnull Operator second) {
        this(operation, attribute, Mode.OBJ_OP);

        this.firstObject = requireNonNull(first);
        this.secondOperator = requireNonNull(second);
    }

    /**
     * Creates a numeric operation of the given type over an operator and an object.
     *
     * @param operation Type of the operation
     * @param attribute attribute whose values the operation is performed on
     * @param first     first operand
     * @param second    second operand
     */
    public NumericOperation(@Nonnull NumericOperationType operation, @Nonnull String attribute, @Nonnull Operator first, @Nonnull Object second) {
        this(operation, attribute, Mode.OP_OBJ);

        this.firstOperator = requireNonNull(first);
        this.secondObject = requireNonNull(second);
    }

    /**
     * Creates a numeric operation of the given type over two objects.
     *
     * @param operation Type of the operation
     * @param attribute attribute whose values the operation is performed on
     * @param first     first operand
     * @param second    second operand
     */
    public NumericOperation(@Nonnull NumericOperationType operation, @Nonnull String attribute, @Nonnull Object first, @Nonnull Object second) {
        this(operation, attribute, Mode.OBJECTS);

        this.firstObject = requireNonNull(first);
        this.secondObject = requireNonNull(second);
    }

    /**
     * Creates an addition operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation add(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Operator second) {
        return new NumericOperation(ADD, attribute, first, second);
    }

    /**
     * Creates an addition operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation add(@Nonnull String attribute, @Nonnull Object first, @Nonnull Operator second) {
        return new NumericOperation(ADD, attribute, first, second);
    }

    /**
     * Creates an addition operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation add(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Object second) {
        return new NumericOperation(ADD, attribute, first, second);
    }

    /**
     * Creates an addition operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation add(@Nonnull String attribute, @Nonnull Object first, @Nonnull Object second) {
        return new NumericOperation(ADD, attribute, first, second);
    }

    /**
     * Creates an subtraction operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation subtract(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Operator second) {
        return new NumericOperation(SUBTRACT, attribute, first, second);
    }

    /**
     * Creates an subtraction operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation subtract(@Nonnull String attribute, @Nonnull Object first, @Nonnull Operator second) {
        return new NumericOperation(SUBTRACT, attribute, first, second);
    }

    /**
     * Creates an subtraction operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation subtract(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Object second) {
        return new NumericOperation(SUBTRACT, attribute, first, second);
    }

    /**
     * Creates an subtraction operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation subtract(@Nonnull String attribute, @Nonnull Object first, @Nonnull Object second) {
        return new NumericOperation(SUBTRACT, attribute, first, second);
    }

    /**
     * Creates an multiplication operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation multiply(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Operator second) {
        return new NumericOperation(MULTIPLY, attribute, first, second);
    }

    /**
     * Creates an multiplication operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation multiply(@Nonnull String attribute, @Nonnull Object first, @Nonnull Operator second) {
        return new NumericOperation(MULTIPLY, attribute, first, second);
    }

    /**
     * Creates an multiplication operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation multiply(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Object second) {
        return new NumericOperation(MULTIPLY, attribute, first, second);
    }

    /**
     * Creates an multiplication operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation multiply(@Nonnull String attribute, @Nonnull Object first, @Nonnull Object second) {
        return new NumericOperation(MULTIPLY, attribute, first, second);
    }

    /**
     * Creates an division operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation divide(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Operator second) {
        return new NumericOperation(DIVIDE, attribute, first, second);
    }

    /**
     * Creates an division operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation divide(@Nonnull String attribute, @Nonnull Object first, @Nonnull Operator second) {
        return new NumericOperation(DIVIDE, attribute, first, second);
    }

    /**
     * Creates an division operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation divide(@Nonnull String attribute, @Nonnull Operator first, @Nonnull Object second) {
        return new NumericOperation(DIVIDE, attribute, first, second);
    }

    /**
     * Creates an division operation of the given operands.
     *
     * @param first  First operand
     * @param second Second operand
     */
    @Nonnull
    public static NumericOperation divide(@Nonnull String attribute, @Nonnull Object first, @Nonnull Object second) {
        return new NumericOperation(DIVIDE, attribute, first, second);
    }

    // TODO: Move to more generic class?
    @Nullable
    public static Object getAttributeValue(@Nonnull Operator operator, String name) throws OperatorNotSupportedException {
        IEvent matchingEvent = operator.getMatchingEvent();

        if (matchingEvent == null)
            throw new OperatorNotSupportedException("Matching event is null.");

        IAttribute attr = matchingEvent
                .getAttributes()
                .get(name);

        if (attr == null)
            throw new OperatorNotSupportedException("Attribute not found");

        return attr.getValue();
    }

    @Override
    public boolean apply(IEvent event) throws OperatorNotSupportedException {
        Object first = firstObject;
        Object second = secondObject;
        switch (mode) {
            case OPERATORS:
                first = getAttributeValue(firstOperator, attribute);
                second = getAttributeValue(secondOperator, attribute);
                break;
            case OBJ_OP:
                second = getAttributeValue(secondOperator, attribute);
                break;
            case OP_OBJ:
                first = getAttributeValue(firstOperator, attribute);
                break;
            case OBJECTS:
                break;
        }

        Objects.requireNonNull(first, "First argument to numeric operation is null.");
        Objects.requireNonNull(second, "Second argument to numeric operation is null.");

        Number result = operation.applyObj(first, second);
        setMatchingEvent(new Event(attribute, result));

        return true;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumericOperation that = (NumericOperation) o;

        if (operation != that.operation) return false;
        if (!attribute.equals(that.attribute)) return false;
        if (mode != that.mode) return false;
        if (!Objects.equals(firstOperator, that.firstOperator))
            return false;
        if (!Objects.equals(secondOperator, that.secondOperator))
            return false;
        if (!Objects.equals(firstObject, that.firstObject))
            return false;
        return Objects.equals(secondObject, that.secondObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, attribute, firstOperator, secondOperator, firstObject, secondObject);
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(50);
        result.append(operation.toString())
                .append("(");

        switch (mode) {
            case OPERATORS:
                result.append(firstOperator).append(", ").append(secondOperator);
                break;
            case OBJECTS:
                result.append(firstObject).append(", ").append(secondObject);
                break;
            case OBJ_OP:
                result.append(firstObject).append(", ").append(secondOperator);
                break;
            case OP_OBJ:
                result.append(firstOperator).append(", ").append(secondObject);
                break;
        }

        result.append(")");

        return result.toString();
    }

    private enum Mode {
        OPERATORS, OBJECTS, OBJ_OP, OP_OBJ
    }
}
