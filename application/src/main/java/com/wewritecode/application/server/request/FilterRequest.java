/**
 * @author Grant Clark
 */

package com.wewritecode.application.server.request;

import com.wewritecode.application.pan.filter.Filter;

import java.util.HashSet;
import java.util.Set;

public class FilterRequest {
    private Set<Filter> filters;

    public FilterRequest() {
        filters = new HashSet<>();
    }

    public Set<Filter> getFilters() { return filters; }
    public void setFilters(Set<Filter> filters) { this.filters = filters; }
}
