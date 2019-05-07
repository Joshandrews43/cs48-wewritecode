/**
 * @author Grant Clark
 */
package com.wewritecode.server.vault;

import com.wewritecode.pan.schedule.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, String> {}
