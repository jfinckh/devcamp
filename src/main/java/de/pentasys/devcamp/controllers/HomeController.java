package de.pentasys.devcamp.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping
    public EntityModel getEntryPoint() {
        Link getProjectsLink = linkTo(methodOn(ProjectController.class)
                .getProjects())
                .withRel("getProjects")
                .withType(RequestMethod.GET.toString());
        EntityModel result = new EntityModel("Welcome");
        result.add(getProjectsLink);
        return result;
    }

}
