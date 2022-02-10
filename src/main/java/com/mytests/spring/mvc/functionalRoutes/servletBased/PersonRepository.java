package com.mytests.spring.mvc.functionalRoutes.servletBased;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PersonRepository extends CrudRepository<Person,Integer> {
    List<Person> findAll();
    Person findById(long id);
    Person findTopByLastName(String lastName);
}
