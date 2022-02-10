package com.mytests.spring.mvc.functionalRoutes.servletBased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;


@Configuration
public class RoutesConfig {

    @Autowired
    PersonRepository repository;
    @Autowired
    private PersonHandler handler;


    @Bean
    public RouterFunction<ServerResponse> renderViewRoute(){
        Map<String, String> map = new HashMap<>();
        map.put("test1_attr","ok!");
        return route(GET("/test1"),
                req -> ok().render("test1_view", map));
    }

    @Bean
    public RouterFunction<ServerResponse> simple() {
        return route(GET("/test2"),
                req -> ok().body("test passed"));
    }
    @Bean
    public RouterFunction<ServerResponse> withPathVar() {
        return route(GET("/test3/{path_var}"),
                req -> ok().body("with pathvariable " + req.pathVariable("path_var")));
    }
    @Bean
    public RouterFunction<ServerResponse> routerFunctionBuilderTest(){

        RouterFunction<ServerResponse> firstRoute = route()
                .GET("/personByLastName/{name}", handler::getByLastName)
                .build();
        return route()
                .GET("/personById/{id}", accept(APPLICATION_JSON), handler::getById)
                .GET("/allPersons", accept(APPLICATION_JSON), handler::listAll)
                .POST("/addPerson", handler::createPerson)
                .add(firstRoute)
                .build();
    }
}
