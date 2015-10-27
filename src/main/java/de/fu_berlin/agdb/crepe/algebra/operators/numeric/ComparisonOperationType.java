package de.fu_berlin.agdb.crepe.algebra.operators.numeric;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;

/**
 * Comparison operations on arbitrary objects
 *
 * @author Simon Kalt
 */
public enum ComparisonOperationType {
    LESS(x -> x < 0),
    LESS_EQUAL(x -> x <= 0),
    EQUAL(x -> x == 0),
    GREATER_EQUAL(x -> x >= 0),
    GREATER(x -> x > 0);

    private final IntFunction<Boolean> comparison;

    ComparisonOperationType(IntFunction<Boolean> comparison) {
        this.comparison = comparison;
    }

    /**
     * Adapted from: {@link NumericUtil#compare(String, IEvent, Object)}.
     * <p>
     * Compares two attributes to each other provided that they implement the comparable interface.
     * If not an Exception is thrown.
     *
     * @param attributeName attributeName that is compared
     * @param event         event which contains the attributeName
     * @param other         object to compare the attribute to
     * @return < 0 if a less b, = 0 if a == b, > 0 if a greater b.
     * @throws OperatorNotSupportedException if the event is {@code null}, the attributeName is not found,
     *                                       or if the attributeName is <em>not</em> equal to the provided
     *                                       object and doesn't implement the comparable interface.
     */
    public static int compare(String attributeName, @Nonnull IEvent event, @Nonnull Object other) throws OperatorNotSupportedException {
        // If the event is null or the attributeName doesn't exist, this throws the necessary exceptions for us.
        if (equalAttribute(attributeName, event, other)) {
            return 0;
        }

        Object attribute = event.getAttributeValue(attributeName).orElse(null);
        return compare(attribute, other);
    }

    /**
     * Adapted from: {@link NumericUtil#compare(String, IEvent, Object)}.
     * <p>
     * Compares the given values using the {@link Comparable} interface.
     *
     * @param first  first object to be compared
     * @param second second object to be compared
     * @return a negative integer, zero, or a positive integer as the first object
     * is less than, equal to, or greater than the specified object.
     * @throws OperatorNotSupportedException if the first value does not implement the {@link Comparable} interface
     * @throws NullPointerException          if either value is null
     * @see Comparable
     */
    @SuppressWarnings("unchecked")
    public static int compare(@Nonnull Object first, @Nonnull Object second) throws OperatorNotSupportedException {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);

        if (first instanceof Comparable) {
            return ((Comparable<Object>) first).compareTo(second);
        } else {
            throw new OperatorNotSupportedException(
                    String.format("Numeric comparison operator not supported between attribute types %s and %s",
                            first.getClass(), second.getClass())
            );
        }
    }

    /**
     * Checks if two attributes are equal.
     *
     * @param attributeName attributeName for which equality is checked
     * @param event         event which contains the attributeName
     * @param other         object to compare to
     * @return true if the attributeName exists in the event and is equal to the supplied object, false otherwise
     * @throws OperatorNotSupportedException if the attributeName is not found or the event is {@code null}.
     */
    public static boolean equalAttribute(String attributeName, @Nonnull IEvent event, Object other) throws OperatorNotSupportedException {
        IAttribute attribute = Optional.ofNullable(event)
                .orElseThrow(() -> new OperatorNotSupportedException("Null event."))
                .getAttributes()
                .get(attributeName);

        Object value = Optional.ofNullable(attribute)
                .orElseThrow(() -> new OperatorNotSupportedException("Attribute not found."))
                .getValue();

        return Objects.equals(value, other);
    }

    /**
     * Applies this comparison operator.
     *
     * @param attributeName attributeName that is compared
     * @param event         event which contains the attributeName
     * @param other         operator that has a matching event which contains
     *                      an attribute (of the same name) to compare to
     * @return {@code true} if the comparison matches, {@code false} otherwise
     * @throws OperatorNotSupportedException if the event is {@code null}, the attributeName is not found,
     *                                       or if the attributeName is <em>not</em> equal to the provided
     *                                       object and doesn't implement the comparable interface.
     */
    public boolean applyTo(String attributeName, @Nonnull IEvent event, @Nonnull Operator other) throws OperatorNotSupportedException {
        IEvent otherEvent = Optional.ofNullable(other)
                .map(Operator::getMatchingEvent)
                .orElseThrow(() -> new OperatorNotSupportedException("Operator does not contain event with appropriately named attribute."));
        return applyTo(attributeName, event, otherEvent);
    }

    /**
     * Applies this comparison operator.
     *
     * @param attributeName attributeName that is compared
     * @param event         event which contains the attributeName
     * @param other         event that contains an attribute (of the same name) to compare to
     * @return {@code true} if the comparison matches, {@code false} otherwise
     * @throws OperatorNotSupportedException if the event is {@code null}, the attributeName is not found,
     *                                       or if the attributeName is <em>not</em> equal to the provided
     *                                       object and doesn't implement the comparable interface.
     */
    public boolean applyTo(String attributeName, @Nonnull IEvent event, @Nonnull IEvent other) throws OperatorNotSupportedException {
        Object otherValue = Optional.ofNullable(other)
                .map(IEvent::getAttributes)
                .map((map) -> map.get(attributeName))
                .orElseThrow(() -> new OperatorNotSupportedException("Operator does not contain event with appropriately named attribute."))
                .getValue();
        return applyTo(attributeName, event, otherValue);
    }

    /**
     * Applies this comparison operator.
     *
     * @param attributeName attributeName that is compared
     * @param event         event which contains the attributeName
     * @param other         object to compare the attribute to
     * @return {@code true} if the comparison matches, {@code false} otherwise
     * @throws OperatorNotSupportedException if the event is {@code null}, the attributeName is not found,
     *                                       or if the attributeName is <em>not</em> equal to the provided
     *                                       object and doesn't implement the comparable interface.
     */
    public boolean applyTo(String attributeName, @Nonnull IEvent event, @Nonnull Object other) throws OperatorNotSupportedException {
        return comparison.apply(compare(attributeName, event, other));
    }
}
