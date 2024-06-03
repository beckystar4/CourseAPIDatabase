package com.example.courseAPIData.Controller;

import com.example.courseAPIData.Model.TopicModel;
import com.example.courseAPIData.Service.TopicService;
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
* getAllTopics():
*   params: none returns: list of topic objects from TopicModel. Automatically converts to JSON
*   Uses constructor to create new topic objects
*
* getTopic(String id):
*   params: id that the user is looking for. Matches the one in the path
*   return: information about the topic
*
* addTopic(TopicModel topic):
*   params: topic to add
*   return: void
*
* updateTopic(TopicModel topic, String id):
*   params: topic to add, id of item to change
*   return: void
*
* deleteTopic(String id):
*   params: id that the user is looking to delete
*   return: void
*
* */

@RestController
public class TopicController {

    @Autowired //create dependency injection for TopicService
    private TopicService topicService; //gets topicService object


    @RequestMapping("/topics")
    public List<TopicModel> getAllTopics(){
        return topicService.getTopics();
    }

    @RequestMapping("/topics/{id}")
    public TopicModel getTopic(@PathVariable String id){
        return topicService.getTopic(id);
    }

    @RequestMapping(value="/topics", method= RequestMethod.POST)
    public void addTopic(@RequestBody TopicModel topic){
        topicService.addTopic(topic);
    }
    @RequestMapping(value="/topics/{id}", method= RequestMethod.PUT)
    public void updateTopic(@RequestBody TopicModel topic, @PathVariable String id){
        topicService.updateTopic(topic, id);
    }

    @RequestMapping(value="/topics/{id}", method= RequestMethod.DELETE)
    public void deleteTopic(@PathVariable String id){
        topicService.deleteTopic(id);
    }



}
