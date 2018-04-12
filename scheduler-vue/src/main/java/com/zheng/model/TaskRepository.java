package com.zheng.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by on 2017/4/5.
 */
public interface  TaskRepository extends CrudRepository<Task, Integer> {

    List<Task> findByStatus(int status);

}
