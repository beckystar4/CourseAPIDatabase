package com.example.courseAPIData.Service;

/*
*
* Declares dependancy
* Calls the outward facing layer to get the data and perform logic on it.
*
* topics is a list of topics
*
* getCourses(String topicID)
*   params: find course by topicID, return: all topics
*
* getCourse(string id)
*   params: id that user wants returned, return: the first course with matching id
*
* addTopic(CourseModel course)
*   params: course to add, return: void
*
* updateTopic(CourseModel course):
*   params: updated course, ireturn: void
*
* deleteTopic(string id):
*   params: id of topic to delete, return: void
*
*/

import com.example.courseAPIData.Model.CourseModel;
import com.example.courseAPIData.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseModel> getCourses(String topicID) {
        List<CourseModel> courses = new ArrayList<>();
        courseRepository.findByTopicId(topicID).forEach(courses::add);
        return courses;
    }

    //creates lamda expression that checks user id to every course in the list
    public CourseModel getCourse(String id){
        return courseRepository.findById(id).get();
    }

    public void addCourse(CourseModel course){
        courseRepository.save(course);
    }

    public void updateCourse(CourseModel course){
        courseRepository.save(course);
    }

    public void deleteCourse(String id){
        //lambda function that says for each topic see if the id matches, if so delete it.
        courseRepository.deleteById(id);
    }

}
