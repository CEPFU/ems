package de.fu_berlin.agdb.crepe.json.algebra.operators;

import de.fu_berlin.agdb.crepe.algebra.operators.MockOperator;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

/**
 * A mock operator that always an empty {@link MockOperator} as its algebra element.
 */
public class JSONMockOperator extends JSONOperator<MockOperator> {

    @Override
    public MockOperator getAlgebraElement() {
        return new MockOperator();
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
