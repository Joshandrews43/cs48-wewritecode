package com.wewritecode.pan.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/*
    To add a Filter, do the following:

    1) add @Type(value = SomeFilter.class) to the JsonSubTypes, replacing SomeFilter.class with your
        filter class name.

    2) in your filter class, add @JsonTypeName("FilterName"), replacing "FilterName" with whatever you
        want your filter to use in the Json serialized form under "type"
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @Type(value = TimeFilter.class),
        @Type(value = DayFilter.class)
    })
public interface Filter<T> extends Serializable {
    double getFitness(T o) throws InvalidFilterOptionException;
}
