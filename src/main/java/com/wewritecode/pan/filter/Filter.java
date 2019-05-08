package com.wewritecode.pan.filter;

import java.io.Serializable;

public interface Filter<T> extends Serializable {
    double getFitness(T o);
}
