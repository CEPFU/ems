package de.fu_berlin.agdb.crepe.json.algebra.operators.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOp;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.BinaryOperatorType;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/**
 * @author Simon Kalt
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @Type(JSONOr.class),
        @Type(JSONXor.class),
        @Type(JSONAnd.class)
})
public abstract class JSONBinaryOp extends JSONOperator<BinaryOp> {
    protected final BinaryOperatorType type;
    protected List<JSONOperator<?>> ofOperands;

    public JSONBinaryOp(BinaryOperatorType type, List<JSONOperator<?>> ofOperands) {
        this.ofOperands = ofOperands;
        this.type = type;
    }

    public JSONBinaryOp(BinaryOperatorType type) {
        this(type, null);
    }

    @Override
    public BinaryOp getAlgebraElement() {
        if (ofOperands != null && type != null) {
            List<Operator> operands = new ArrayList<>(ofOperands.size());
            for (JSONOperator<?> operand : ofOperands) {
                operands.add(operand.getAlgebraElement());
            }
            return new BinaryOp(
                    type,
                    operands
            );
        } else // FIXME: Throw exception?
            throw new NullPointerException("Insufficient operands: " + this);
    }

    @JsonIgnore
    public BinaryOperatorType getType() {
        return type;
    }

    public List<JSONOperator<?>> getOfOperands() {
        return ofOperands;
    }

    public void setOfOperands(List<JSONOperator<?>> ofOperands) {
        this.ofOperands = ofOperands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONBinaryOp that = (JSONBinaryOp) o;

        if (ofOperands != null ? !ofOperands.equals(that.ofOperands) : that.ofOperands != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = ofOperands != null ? ofOperands.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
