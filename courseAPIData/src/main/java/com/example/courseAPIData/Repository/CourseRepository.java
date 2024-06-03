package com.example.courseAPIData.Repository;

import com.example.courseAPIData.Model.CourseModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
*
* Connects the DB and accesses data. Provides abstraction. Contains Create, Read, Update, Delete operations
*
* */

public interface CourseRepository extends CrudRepository<CourseModel, String>{
    public List<CourseModel> findByTopicId(String topicID);

}
