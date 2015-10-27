package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperation;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.NumericOperationType;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;
import de.fu_berlin.agdb.crepe.json.serialize.AlgebraElementListDeserializer;
import de.fu_berlin.agdb.crepe.json.serialize.GenericListSerializer;

import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/**
 * @author Simon Kalt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @Type(JSONAdd.class),
        @Type(JSONSubtract.class),
        @Type(JSONMultiply.class),
        @Type(JSONDivide.class)
})
public abstract class JSONNumericOperation extends JSONOperator<NumericOperation> {
    protected final NumericOperationType operation;
    private final String attribute;
    @JsonDeserialize(using = AlgebraElementListDeserializer.class)
    @JsonSerialize(using = GenericListSerializer.class)
    protected List<Object> ofOperands;

    public JSONNumericOperation(NumericOperationType operation, String attribute, List<Object> ofOperands) {
        this.operation = Objects.requireNonNull(operation);
        this.attribute = attribute;
        this.ofOperands = ofOperands;
    }

    @Override
    public NumericOperation getAlgebraElement() {
        if (attribute != null && ofOperands != null && ofOperands.size() == 2) {
            Object first = ofOperands.get(0);
            Object second = ofOperands.get(1);

            // Check of operator types and pass them in as such,
            // otherwise, the constructor defaults to (Object, Object)
            if (first instanceof JSONOperator) {
                JSONOperator firstOperator = (JSONOperator) first;
                if (second instanceof JSONOperator) {
                    JSONOperator secondOperator = (JSONOperator) second;
                    return new NumericOperation(operation, attribute,
                            (Operator) firstOperator.getAlgebraElement(),
                            (Operator) secondOperator.getAlgebraElement()
                    );
                } else {
                    return new NumericOperation(operation, attribute,
                            (Operator) firstOperator.getAlgebraElement(), second);
                }
            } else if (second instanceof JSONOperator) {
                JSONOperator secondOperator = (JSONOperator) second;
                return new NumericOperation(operation, attribute, first, (Operator) secondOperator.getAlgebraElement());
            } else
                return new NumericOperation(operation, attribute, first, second);
        } else
            throw new NullPointerException("Insufficient operands: " + this);
    }

    public NumericOperationType getOperation() {
        return operation;
    }

    public String getAttribute() {
        return attribute;
    }

    public List<Object> getOfOperands() {
        return ofOperands;
    }

    public void setOfOperands(List<Object> ofOperands) {
        this.ofOperands = ofOperands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONNumericOperation that = (JSONNumericOperation) o;

        if (operation != that.operation) return false;
        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;
        return !(ofOperands != null ? !ofOperands.equals(that.ofOperands) : that.ofOperands != null);

    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (ofOperands != null ? ofOperands.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JSONNumericOperation{" +
                "operation=" + operation +
                ", attribute='" + attribute + '\'' +
                ", ofOperands=" + ofOperands +
                '}';
    }
}
