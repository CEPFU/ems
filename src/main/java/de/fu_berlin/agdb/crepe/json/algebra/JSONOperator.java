package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.json.algebra.operators.JSONMatchToStation;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONBinaryOp;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONNot;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.JSONComparisonOperation;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.JSONNumericOperation;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @Type(JSONMatch.class),
        @Type(JSONMatchToStation.class),
        @Type(JSONBinaryOp.class),
        @Type(JSONNot.class),
        @Type(JSONNumericOperation.class),
        @Type(JSONComparisonOperation.class)
})
public abstract class JSONOperator<T extends Operator> extends JSONAlgebraElement<T> {
}
