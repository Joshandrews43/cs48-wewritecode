/**
 * @author Grant Clark
 */

package com.wewritecode.server.response;

import com.wewritecode.common.full.FullQuarter;
import com.wewritecode.common.general.General;
import com.wewritecode.pan.filter.AbstractScheduleFilter;
import com.wewritecode.pan.filter.Filter;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GeneralResponse {
    private General general;
    private Map<String, FullQuarter> quarters;
    private Set<AbstractScheduleFilter> filters;

    public GeneralResponse() {
        general = new General();
        quarters = new HashMap<>();
        filters = new HashSet<>();
        try {
            Reflections reflections = new Reflections("com.wewritecode.pan.filter");
            Set<Class<? extends AbstractScheduleFilter>> classes = reflections.getSubTypesOf(AbstractScheduleFilter.class);
            for (Class<?> clazz : classes) {
                Constructor<AbstractScheduleFilter> cons = (Constructor<AbstractScheduleFilter>) clazz.getConstructor();
                AbstractScheduleFilter o = cons.newInstance();
                filters.add(o);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public General getGeneral() { return general; }
    public void setGeneral(General general) { this.general = general; }
    public Map<String, FullQuarter> getQuarters() { return quarters; }
    public void setQuarters(Map<String, FullQuarter> quarters) { this.quarters = quarters; }
    public Set<AbstractScheduleFilter> getFilters() { return filters; }
    public void setFilters(Set<AbstractScheduleFilter> filters) { this.filters = filters; }
}
