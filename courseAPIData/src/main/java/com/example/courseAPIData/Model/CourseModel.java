package com.example.courseAPIData.Model;


/*
*
* Provides outline for JSON objects
*
* @Entity tells JPA to create a table called topic with ID, name, and description
* @Id tells JPA what the PK is
*
*@ManyToOne: Many courses to one topic
*
* */


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CourseModel {

    @Id
    private String id;
    private String name;
    private String description;

    @ManyToOne
    private TopicModel topic;


    public CourseModel(){}

    //    Constructor
    public CourseModel(String id, String name, String description, String topicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.topic = new TopicModel(topicId, "", "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TopicModel getTopic() {
        return topic;
    }

    public void setTopic(TopicModel topic) {
        this.topic = topic;
    }
}
