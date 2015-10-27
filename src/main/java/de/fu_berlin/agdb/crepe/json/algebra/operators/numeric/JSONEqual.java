package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Equal;
import de.fu_berlin.agdb.crepe.json.algebra.JSONMatch;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;
import de.fu_berlin.agdb.crepe.json.serialize.JSONEqualDeserializer;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonDeserialize(using = JSONEqualDeserializer.class)
public class JSONEqual extends JSONMatch<Equal> {
    private String attribute;
    private Object toObject;
    private List<JSONOperator<?>> ofOperands;

    public JSONEqual(String attribute, List<JSONOperator<?>> ofOperands) {
        this.attribute = attribute;
        this.ofOperands = ofOperands;
    }

    public JSONEqual(String attribute, Object toObject) {
        this.attribute = attribute;
        this.toObject = toObject;
    }

    @Override
    public Equal getAlgebraElement() {
        if (ofOperands != null && ofOperands.size() >= 2) {
            // TODO: When the underlying structure supports it, add all available operands
            return new Equal(
                    attribute,
                    ofOperands.get(0).getAlgebraElement(),
                    ofOperands.get(1).getAlgebraElement()
            );
        } else {
            return new Equal(attribute, toObject);
        }
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Object getToObject() {
        return toObject;
    }

    public void setToObject(Object toObject) {
        this.toObject = toObject;
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

        JSONEqual jsonEqual = (JSONEqual) o;

        if (attribute != null ? !attribute.equals(jsonEqual.attribute) : jsonEqual.attribute != null) return false;
        if (toObject != null ? !toObject.equals(jsonEqual.toObject) : jsonEqual.toObject != null) return false;
        return !(ofOperands != null ? !ofOperands.equals(jsonEqual.ofOperands) : jsonEqual.ofOperands != null);

    }

    @Override
    public int hashCode() {
        int result = attribute != null ? attribute.hashCode() : 0;
        result = 31 * result + (toObject != null ? toObject.hashCode() : 0);
        result = 31 * result + (ofOperands != null ? ofOperands.hashCode() : 0);
        return result;
    }

}
