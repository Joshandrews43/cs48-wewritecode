/**
 * @author Grant Clark
 */

package com.wewritecode.pan.scheduler;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.filter.InvalidFilterOptionException;
import com.wewritecode.common.schedule.Schedule;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BruteForceFilterer {

    public static void sort(List<Schedule> fullSchedules, Set<Filter> filterOptions) {
        try {
            for (Schedule s : fullSchedules)
                s.calcFitness(filterOptions);
        } catch (InvalidFilterOptionException e) {
            // TODO: Do something with this exception. i.e. Make status code or something.
            e.printStackTrace();
        }
        Collections.sort(fullSchedules, Collections.reverseOrder());
    }
}
