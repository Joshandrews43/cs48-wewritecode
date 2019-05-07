package com.wewritecode.pan.filter;

public interface Filter<T> {
    double getFitness(T o);
}
