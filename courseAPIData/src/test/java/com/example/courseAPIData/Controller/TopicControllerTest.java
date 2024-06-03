package com.example.courseAPIData.Controller;

import com.example.courseAPIData.Model.TopicModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(TopicController.class)
class TopicControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TopicController topicController;


    @Test
    void getAllTopics() throws Exception{
        List<TopicModel> topics = new ArrayList<>(Arrays.asList(
                new TopicModel("Spring", "Coding Framework: Spring", "Description"),
                new TopicModel("Java", "Coding Language: Java", "Description"),
                new TopicModel("Python", "Coding Language: Python", "Description")
        ));
        given(topicController.getAllTopics()).willReturn(topics);

        mvc.perform(
                get("/topics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getTopic() throws Exception{
        TopicModel topic = new TopicModel("Spring", "Coding Framework: Spring", "Description");
        given(topicController.getTopic(topic.getId())).willReturn(topic);
        mvc.perform(get("/topics/{id}", topic.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());

    }

    @Test
    void addTopic() throws Exception {
        TopicModel topic = new TopicModel("Rails", "Coding Framework: Rails", "Description");
        mvc.perform(get("/topics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<TopicModel> topics = new ArrayList<>(Arrays.asList(
                new TopicModel("Spring", "Coding Framework: Spring", "Description"),
                new TopicModel("Java", "Coding Language: Java", "Description"),
                new TopicModel("Python", "Coding Language: Python", "Description"),
                topic
        ));

        given(topicController.getAllTopics()).willReturn(topics);
        mvc.perform(get("/topics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].id", is("Rails")))
                .andExpect(jsonPath("$[3].name", is("Coding Framework: Rails")))
                .andExpect(jsonPath("$[3].description", is("Description")))
                .andDo(print());


    }

    @Test
    void updateTopic() throws Exception {
        List<TopicModel> topics = new ArrayList<>(Arrays.asList(
                new TopicModel("Spring", "Coding Framework: Spring", "Description"),
                new TopicModel("Java", "Coding Language: Java", "Description"),
                new TopicModel("Python", "Coding Language: Python", "Description")
        ));

        topics.getFirst().setName("Spring1");
        mvc.perform(get("/topics/{id}", "Spring").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        given(topicController.getAllTopics()).willReturn(topics);
        mvc.perform(get("/topics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("Spring")))
                .andExpect(jsonPath("$[0].name", is("Spring1")))
                .andDo(print());
    }

    @Test
    void deleteTopic() throws Exception {
        List<TopicModel> topics = new ArrayList<>(Arrays.asList(
                new TopicModel("Spring", "Coding Framework: Spring", "Description"),
                new TopicModel("Java", "Coding Language: Java", "Description"),
                new TopicModel("Python", "Coding Language: Python", "Description")
        ));

        topics.removeFirst();

        mvc.perform(get("/topics/{id}", "Spring").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        given(topicController.getAllTopics()).willReturn(topics);
        mvc.perform(get("/topics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }
}