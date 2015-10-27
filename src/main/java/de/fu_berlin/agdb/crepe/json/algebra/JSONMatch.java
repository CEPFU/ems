package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.JSONEqual;

import static com.fasterxml.jackson.annotation.JsonSubTypes.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({@Type(JSONEqual.class)})
public abstract class JSONMatch<T extends Match> extends JSONOperator<T> {
}
