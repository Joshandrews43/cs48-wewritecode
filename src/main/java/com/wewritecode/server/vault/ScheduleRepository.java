/**
 * @author Grant Clark
 */

package com.wewritecode.server.vault;

import com.wewritecode.pan.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {}
