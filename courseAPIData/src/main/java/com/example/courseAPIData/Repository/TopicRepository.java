package com.example.courseAPIData.Repository;

import com.example.courseAPIData.Model.TopicModel;
import org.springframework.data.repository.CrudRepository;

/*
*
* Connects the DB and accesses data. Provides abstraction. Contains Create, Read, Update, Delete operations
*
* */

public interface TopicRepository extends CrudRepository<TopicModel, String>{


}
