package de.fu_berlin.agdb.crepe.json.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.operators.logic.Not;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

/**
 * @author Simon Kalt
 */
public class JSONNot extends JSONOperator<Not> {

    private JSONOperator<?> ofOperand;

    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONNot() {

    }

    public JSONNot(JSONOperator<?> ofOperand) {
        this.ofOperand = ofOperand;
    }

    @Override
    public Not getAlgebraElement() {
        return ofOperand != null ? new Not(ofOperand.getAlgebraElement()) : null;
    }

    public JSONOperator<?> getOfOperand() {
        return ofOperand;
    }

    public void setOfOperand(JSONOperator<?> ofOperand) {
        this.ofOperand = ofOperand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONNot jsonNot = (JSONNot) o;

        return !(ofOperand != null ? !ofOperand.equals(jsonNot.ofOperand) : jsonNot.ofOperand != null);
    }

    @Override
    public int hashCode() {
        return ofOperand != null ? ofOperand.hashCode() : 0;
    }
}
