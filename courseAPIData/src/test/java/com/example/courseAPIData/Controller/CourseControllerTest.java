package com.example.courseAPIData.Controller;

import com.example.courseAPIData.Model.CourseModel;
import com.example.courseAPIData.Model.TopicModel;
import com.example.courseAPIData.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;



@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CourseController courseController;


    @Test
    void getAllCourses() throws Exception {
//        Checks to see if all courses are returned when all the topics are the same.
        List<CourseModel> courses = new ArrayList<>(Arrays.asList(
                new CourseModel("Spring", "Coding Framework: Spring", "Description", "Coding"),
                new CourseModel("Java", "Coding Language: Java", "Description", "Coding"),
                new CourseModel("Python", "Coding Language: Python", "Description", "Coding")
        ));

        given(courseController.getAllCourses("Coding")).willReturn(courses);
        mvc.perform(get("/topics/{topicID}/courses", "Coding").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(courses.size())))
                .andDo(print());

//        Makes sure only one of them returns when the topic only applies to one of the courses
        courses.getFirst().getTopic().setId("Spring");
        // Filter the courses to include only those with the topicID of "Spring"
        List<CourseModel> springCourses = courses.stream()
                .filter(course -> "Spring".equals(course.getTopic().getId()))
                .toList();

        given(courseController.getAllCourses("Spring")).willReturn(springCourses);
        mvc.perform(get("/topics/{topicID}/courses", "Spring").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(springCourses.size())))
                .andDo(print());

//        Tests what happens if none of the courses have that topicID
        List<CourseModel> otherCourses = courses.stream()
                .filter(course -> "Rails".equals(course.getTopic().getId()))
                .toList();

        given(courseController.getAllCourses("Rails")).willReturn(otherCourses);
        mvc.perform(get("/topics/{topicID}/courses", "Rails").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(otherCourses.size())))
                .andDo(print());


    }

    @Test
    void getCourse() throws Exception {
//        Checks to see if all courses are returned when all the topics are the same.
        List<CourseModel> courses = new ArrayList<>(Arrays.asList(
                new CourseModel("Spring", "Coding Framework: Spring", "Description", "Coding"),
                new CourseModel("Java", "Coding Language: Java", "Description", "Coding"),
                new CourseModel("Python", "Coding Language: Python", "Description", "Coding")
        ));

        given(courseController.getCourse("Spring")).willReturn(courses.getFirst());
        mvc.perform(get("/topics/{topicID}/courses/{id}", "Coding", "Spring").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

//        Tests to get another course in the middle of the list
        given(courseController.getCourse("Java")).willReturn(courses.stream().filter(course -> "Java".equals(course.getId())).toList().getFirst());
        mvc.perform(get("/topics/{topicID}/courses/{id}", "Coding", "Java").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void addCourse() {

    }

    @Test
    void updateCourse() {
    }

    @Test
    void deleteCourse() {
    }
}