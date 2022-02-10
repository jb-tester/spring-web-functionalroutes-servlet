package com.mytests.spring.mvc.functionalRoutes.servletBased;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Component
public class PersonHandler {

	private final PersonRepository repository;

	public PersonHandler(PersonRepository repository) {
		this.repository = repository;
	}

	public ServerResponse listAll(ServerRequest request) {
		List<Person> people = repository.findAll();
		System.out.println(people);
		return ok().contentType(APPLICATION_JSON).body(people);
	}

	public ServerResponse createPerson(ServerRequest request) throws Exception { 
		Person person = request.body(Person.class);
		repository.save(person);
		return ok().build();
	}

	public ServerResponse getById(ServerRequest request) {
		int personId = Integer.parseInt(request.pathVariable("id"));
		Person person = repository.findById(personId);
		if (person != null) {
			return ok().contentType(APPLICATION_JSON).body(person);
		}
		else {
			return ServerResponse.notFound().build();
		}
	}

	public ServerResponse getByLastName(ServerRequest request) {
		String lastname = request.pathVariable("name");
		Person person = repository.findTopByLastName(lastname);
		if (person != null) {
			return ok().contentType(APPLICATION_JSON).body(person);
		}
		else {
			return ServerResponse.notFound().build();
		}
	}
}