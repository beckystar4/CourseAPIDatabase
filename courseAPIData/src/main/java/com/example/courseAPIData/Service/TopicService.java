package com.example.courseAPIData.Service;

/*
*
* Declares dependancy
* Calls the outward facing layer to get the data and perform logic on it.
*
* topics is a list of topics
*
* getTopics()
*   params: none, return: all topics
*
* getTopic(string id)
*   params: id that user wants returned, return: the first topic with matching id
*
* addTopic(topicmodel topic)
*   params: topic to add, return: void
*
* updateTopic(topicmodel topic, string id):
*   params: updated topic, id of topic to update, return: void
*
* deleteTopic(string id):
*   params: id of topic to delete, return: void
*
*/

import com.example.courseAPIData.Model.TopicModel;
import com.example.courseAPIData.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

//    private List<TopicModel> topics = new ArrayList<>(Arrays.asList(
//            new TopicModel("Spring", "Coding Framework: Spring", "Description"),
//            new TopicModel("Java", "Coding Language: Java", "Description"),
//            new TopicModel("Python", "Coding Language: Python", "Description")
//    ));

    public List<TopicModel> getTopics() {
//        return topics;
        List<TopicModel> topics = new ArrayList<>();
        topicRepository.findAll().forEach(topics::add);
        return topics;
    }

    //creates lamda expression that checks user id to every topic in the list
    public TopicModel getTopic(String id){
        return topicRepository.findById(id).get();
//        return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    public void addTopic(TopicModel topic){
        topicRepository.save(topic);
    }

    public void updateTopic(TopicModel topic, String id){
        topicRepository.save(topic);
        /*
        for(int i=0; i<topics.size(); i++){
            TopicModel t = topics.get(i);
            if(t.getId().equals(id)){
                topics.set(i, topic);
                return;
            }
        }
         */
    }

    public void deleteTopic(String id){
        //lambda function that says for each topic see if the id matches, if so delete it.
        topicRepository.deleteById(id);
    }

}
