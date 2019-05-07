package de.pentasys.devcamp.controllers;

import de.pentasys.devcamp.domain.Booking;
import de.pentasys.devcamp.domain.Project;
import de.pentasys.devcamp.service.ProjectService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    final ProjectService projectService;
    final Class<ProjectController> controllerClass;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
        this.controllerClass = ProjectController.class;
    }

    @GetMapping("/{id}")
    public EntityModel<Project> getProject(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(controllerClass).getProject(id))
                .withSelfRel().withType(RequestMethod.GET.toString());
        Link getProjectsLink = linkTo(methodOn(controllerClass).getProjects())
                .withRel("getProjects").withType(RequestMethod.GET.toString());
        Link updateLink = linkTo(methodOn(controllerClass).putProject(null, id))
                .withRel("updateProject").withType(RequestMethod.PUT.toString());
        Link createBookingLink = linkTo(methodOn(controllerClass).postBooking(null, id))
                .withRel("postBooking").withType(RequestMethod.POST.toString());
        Link getBookingsLink = linkTo(methodOn(controllerClass).getBookings(id))
                .withRel("getBookings").withType(RequestMethod.GET.toString());

        EntityModel response = new EntityModel<>(projectService.findOne(id));
        response.add(selfLink, getProjectsLink, updateLink, getBookingsLink, createBookingLink);
        return response;
    }

    @GetMapping
    public CollectionModel getProjects() {
        Link createProjectLink = linkTo(methodOn(controllerClass).postProject(null))
                .withRel("postProject").withType(RequestMethod.POST.toString());
        List<EntityModel> entityModelList = new ArrayList<>();
        projectService.findAll().forEach(project ->
                entityModelList.add(new EntityModel<>
                        (project, linkTo(methodOn(controllerClass)
                                .getProject(project.getId()))
                                .withSelfRel()
                                .withType(RequestMethod.GET.toString()))));
        CollectionModel response = new CollectionModel<>(entityModelList);
        response.add(createProjectLink);
        return response;
    }

    @PostMapping
    public EntityModel<Project> postProject(@RequestBody Project project) {
        Project postedEntity = projectService.createProject(project);
        EntityModel response = new EntityModel<>(postedEntity);
        Link getProjectsLink = linkTo(methodOn(controllerClass).getProjects())
                .withRel("getProjects").withType(RequestMethod.GET.toString());
        Link selfLink = linkTo(methodOn(controllerClass).getProject(postedEntity.getId()))
                .withSelfRel().withType(RequestMethod.GET.toString());
        response.add(selfLink, getProjectsLink);
        return response;
    }

    @PutMapping("/{id}")
    public EntityModel<Project> putProject(@RequestBody Project project, @PathVariable Long id) {
        Link getProjects = linkTo(methodOn(controllerClass).getProjects())
                .withRel("getProjects").withType(RequestMethod.GET.toString());
        Link selfLink = linkTo(methodOn(controllerClass).getProject(id))
                .withSelfRel().withType(RequestMethod.GET.toString());
        EntityModel response = new EntityModel<>(projectService.updateOne(project, id));
        response.add(selfLink, getProjects);
        return response;
    }

    @PostMapping("/{id}/bookings")
    public EntityModel<Project> postBooking(@RequestBody Booking booking, @PathVariable Long id) {
        Link selfLink = linkTo(methodOn(controllerClass).getProject(id))
                .withSelfRel().withType(RequestMethod.GET.toString());
        Link createBookingLink = linkTo(methodOn(controllerClass).postBooking(booking, id))
                .withRel("postBooking").withType(RequestMethod.POST.toString());
        EntityModel response = new EntityModel<>(projectService.createBooking(booking, id));
        response.add(selfLink, createBookingLink);
        return response;
    }

    @GetMapping("/{id}/bookings")
    public CollectionModel getBookings(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(controllerClass).getProject(id))
                .withSelfRel().withType(RequestMethod.GET.toString());
        CollectionModel response = new CollectionModel<>(projectService.getBookings(id));
        response.add(selfLink);
        return response;
    }

}
