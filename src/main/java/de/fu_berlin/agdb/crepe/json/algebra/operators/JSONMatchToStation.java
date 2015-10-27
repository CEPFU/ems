package de.fu_berlin.agdb.crepe.json.algebra.operators;

import de.fu_berlin.agdb.crepe.algebra.operators.core.ForEvent;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.And;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Equal;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

/**
 * Operator that constrains a supplied operator
 * to only match an event if it occurred at the specified station.
 */
public class JSONMatchToStation extends JSONOperator<And> {
    private JSONOperator<?> matchOperator;
    private int toStation;

    /**
     * Used for JSON deserialization.
     */
    @SuppressWarnings("unused")
    public JSONMatchToStation() {

    }

    public JSONMatchToStation(JSONOperator<?> matchOperator, int toStation) {
        this.matchOperator = matchOperator;
        this.toStation = toStation;
    }

    @Override
    public And getAlgebraElement() {
        // TODO: Check if this is correct
        final Equal match = new Equal("locationId", toStation);
        return new And(
                match,
                new ForEvent(match, matchOperator.getAlgebraElement())
        );
    }

    public JSONOperator<?> getMatchOperator() {
        return matchOperator;
    }

    public void setMatchOperator(JSONOperator<?> matchOperator) {
        this.matchOperator = matchOperator;
    }

    public int getToStation() {
        return toStation;
    }

    public void setToStation(int toStation) {
        this.toStation = toStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONMatchToStation that = (JSONMatchToStation) o;

        if (toStation != that.toStation) return false;
        return !(matchOperator != null ? !matchOperator.equals(that.matchOperator) : that.matchOperator != null);
    }

    @Override
    public int hashCode() {
        int result = matchOperator != null ? matchOperator.hashCode() : 0;
        result = 31 * result + toStation;
        return result;
    }
}
