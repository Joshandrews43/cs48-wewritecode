package com.wewritecode.application.pan.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/*
    To add a Filter, do the following:

    1) add @Type(value = SomeFilter.class) to the JsonSubTypes, replacing SomeFilter.class with your
        filter class name.

    2) in your filter class, add @JsonTypeName("FilterName"), replacing "FilterName" with whatever you
        want your filter to use in the Json serialized form under "type"
 */

/**
 * Interface for defining Filters for whatever schedule representation.
 *
 * To implement, directly implement or use an abstract class to define fields for serialization with Jackson.
 * See {@code AbstractScheduleFilter.java} for example using {@code Schedule.java}.
 *
 * Any implementing classes must be annotated with {@code @JsonTypeName()}, and the class name must be
 * added to the {@code @JsonSubTypes({})} annotation, using the {@code @Type()} annotation.
 *
 * @param <T> Object type representing a schedule
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @Type(value = AbstractScheduleFilter.class)
    })
public interface Filter<T> {
    double getFitness(T o) throws InvalidFilterOptionException;
}
