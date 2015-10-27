package de.fu_berlin.agdb.crepe.algebra.operators;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.OperatorNotSupportedException;
import de.fu_berlin.agdb.crepe.data.IEvent;

public class MockOperator extends Operator {
    @Override
    public boolean apply(IEvent event) throws OperatorNotSupportedException {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
