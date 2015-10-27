package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONNotification;

/**
 * This abstract class is implemented by all elements of the JSON Algebra.
 * The method {@link #getAlgebraElement()} is used to generate an element
 * usable in the original context of {@link de.fu_berlin.agdb.crepe.algebra}.
 *
 * @param <MappedElement> Algebra element specified in {@link de.fu_berlin.agdb.crepe.algebra}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(JSONOperator.class),
        @JsonSubTypes.Type(JSONNotification.class),
        @JsonSubTypes.Type(JSONProfile.class)
})
public abstract class JSONAlgebraElement<MappedElement> {
    /**
     * Generates an instance of the mapped element type.
     */
    @JsonIgnore
    public abstract MappedElement getAlgebraElement();

    /**
     * Declared as abstract so all subclasses have to implement it.
     */
    public abstract boolean equals(Object other);
}
