package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperation;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.ComparisonOperationType;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

/**
 * @author Simon Kalt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @Type(JSONLess.class),
        @Type(JSONLessEqual.class),
        @Type(JSONGreaterEqual.class),
        @Type(JSONGreater.class)
})
public abstract class JSONComparisonOperation extends JSONOperator<ComparisonOperation> {
    protected final ComparisonOperationType operation;
    private final String attribute;
    @JsonInclude(NON_NULL)
    private JSONOperator<?> toOperator;
    @JsonInclude(NON_NULL)
    private Object toObject;

    public JSONComparisonOperation(ComparisonOperationType operation, String attribute, JSONOperator<?> toOperator) {
        this.operation = operation;
        this.attribute = attribute;
        this.toOperator = toOperator;
    }

    public JSONComparisonOperation(ComparisonOperationType operation, String attribute, Object toObject) {
        this.operation = operation;
        this.attribute = attribute;
        this.toObject = toObject;
    }

    @Override
    public ComparisonOperation getAlgebraElement() {
        if (toOperator != null) {
            return new ComparisonOperation(operation, attribute, toOperator.getAlgebraElement());
        } else if (toObject != null) {
            return new ComparisonOperation(operation, attribute, toObject);
        } else {
            throw new NullPointerException("Insufficient operands: " + this);
        }
    }

    public ComparisonOperationType getOperation() {
        return operation;
    }

    public String getAttribute() {
        return attribute;
    }

    public JSONOperator<?> getToOperator() {
        return toOperator;
    }

    public void setToOperator(JSONOperator<?> toOperator) {
        this.toOperator = toOperator;
    }

    public Object getToObject() {
        return toObject;
    }

    public void setToObject(Object toObject) {
        this.toObject = toObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONComparisonOperation that = (JSONComparisonOperation) o;

        if (operation != that.operation) return false;
        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;
        if (toOperator != null ? !toOperator.equals(that.toOperator) : that.toOperator != null) return false;
        return !(toObject != null ? !toObject.equals(that.toObject) : that.toObject != null);

    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (toOperator != null ? toOperator.hashCode() : 0);
        result = 31 * result + (toObject != null ? toObject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JSONComparisonOperation{" +
                "operation=" + operation +
                ", attribute='" + attribute + '\'' +
                ", toOperator=" + toOperator +
                ", toObject=" + toObject +
                '}';
    }
}
