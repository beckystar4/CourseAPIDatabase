package com.example.courseAPIData.Controller;

import com.example.courseAPIData.Model.TopicModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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

        mvc.perform(post("/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(new ObjectMapper().writeValueAsString(topic)))
                // Expect the response status to be OK
                .andExpect(status().isOk())
                // Print the response content for debugging purposes
                .andDo(print());

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
        TopicModel topic = new TopicModel("1", "Java", "Programming Language");
        doNothing().when(topicController).updateTopic(topic,topic.getId());
//        when(topicController.updateTopic(topic, topic.getId())).thenReturn(topic);

        mvc.perform(put("/topics/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Python\",\"description\":\"Programming Language\"}"))
                .andExpect(status().isOk());

//        List<TopicModel> topics = topicController.getAllTopics();
//        assertEquals(1, topics.size());
//        assertEquals("Java", topics.getFirst().getName());
    }


    @Test
    void deleteTopic() throws Exception {
        List<TopicModel> topics = new ArrayList<>(Arrays.asList(
                new TopicModel("Spring", "Coding Framework: Spring", "Description"),
                new TopicModel("Java", "Coding Language: Java", "Description"),
                new TopicModel("Python", "Coding Language: Python", "Description")
        ));
        given(topicController.getAllTopics()).willReturn(topics);

        mvc.perform(get("/topics").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());

        mvc.perform(delete("/topics/{id}", "Spring").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}