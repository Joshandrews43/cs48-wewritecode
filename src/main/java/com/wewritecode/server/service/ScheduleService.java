/**
 * @author Grant Clark
 */

package com.wewritecode.server.service;

import com.wewritecode.pan.filter.Filter;
import com.wewritecode.pan.schedule.Course;
import com.wewritecode.pan.schedule.Schedule;
import com.wewritecode.server.request.ScheduleRequest;
import com.wewritecode.server.response.ScheduleResponse;
import com.wewritecode.server.vault.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Service that handles the appropriate requests.
 */
@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public ScheduleResponse generateSchedules(ScheduleRequest request) {
        List<Course> mandatory = request.getMandatory();

        // Logic for combining courses to make a schedule with no-conflicts.
        // When creating a schedule, default fitness == 1 for non-conflicting schedules.
        // Add said schedule to scheduleRepository.

        // For each schedule in scheduleRepository, apply the filters and assign fitness.

        // Sort schedules by highest fitness level, and return all schedules in the repository.

        return new ScheduleResponse();
    }

    // Move below to a specific scheduler class, to allow for other ways to schedule.

    private void addViableSchedules(List<Course> courses) {
        // TODO: BruteForceAlgorithm
    }

    private void addFilters(List<Filter> filters) {
        // Get viable schedules from scheduleRepository
        List<Schedule> schedules = new ArrayList<>();
        scheduleRepository.findAll().forEach(schedule -> schedules.add(schedule));

        // For each schedule, apply each filter
        for (Schedule schedule : schedules) {
            double totalFitness = 0;
            for (Filter f : filters) {
                totalFitness += f.getFitness(schedule);
            }
            // FIXME: what if filters.size() == 0
            double scheduleFitness = totalFitness / filters.size();
        }
    }
}
