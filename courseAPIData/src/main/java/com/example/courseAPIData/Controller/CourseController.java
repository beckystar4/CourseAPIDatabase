package com.example.courseAPIData.Controller;

import com.example.courseAPIData.Model.CourseModel;
import com.example.courseAPIData.Service.CourseService;
import com.example.courseAPIData.Model.TopicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
*
* Connects to TopicModel to return objects in JSON format
*
* This is a GET request which is defaulted when using RequestMapping
* @PathVariable links the varible in the path to varible being passed through
* @RequestBody tells Spring MVC contains a JSON object that you will translate to an instance
*
*
* getAllCourses(String topicID):
*   params: searches for course by topic
*   return: courses
*
* getCourse(String id):
*   params: id that the user is looking for. Matches the one in the path
*   return: information about the course
*
* addCourse(CourseModel course, TopicModel topic):
*   params: course to add. topic in order to initialize the topic in the course description
*   return: void
*
* updateCourse(CourseModel course, String topicID):
*   params: course to update, topicID to initialize the topic for updated Course
*   return: void
*
* deleteCourse(String id):
*   params: id that the user is looking to delete
*   return: void
*
* */

@RestController
public class CourseController {

    @Autowired //create dependency injection for TopicService
    private CourseService courseService; //gets topicService object


    @RequestMapping("/topics/{topicID}/courses")
    public List<CourseModel> getAllCourses(@PathVariable String topicID){
        return courseService.getCourses(topicID);
    }

    @RequestMapping("topics/{topicID}/courses/{id}")
    public CourseModel getCourse(@PathVariable String id){
        return courseService.getCourse(id);
    }

    @RequestMapping(value="topics/{topicID}/courses", method= RequestMethod.POST)
    public void addCourse(@RequestBody CourseModel course, @PathVariable String topicID){
        course.setTopic(new TopicModel(topicID,"",""));
        courseService.addCourse(course);
    }
    @RequestMapping(value="topics/{topicID}/courses/{id}", method= RequestMethod.PUT)
    public void updateCourse(@RequestBody CourseModel course, @PathVariable String topicID){
        course.setTopic(new TopicModel(topicID,"",""));
        courseService.updateCourse(course);
    }

    @RequestMapping(value="topics/{topicID}/courses/{id}", method= RequestMethod.DELETE)
    public void deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
    }



}
