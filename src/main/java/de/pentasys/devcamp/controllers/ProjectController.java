package de.pentasys.devcamp.controllers;

import de.pentasys.devcamp.domain.Booking;
import de.pentasys.devcamp.domain.Project;
import de.pentasys.devcamp.service.ProjectService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/projects")
public class ProjectController {

    final ProjectService projectService;
    final Class<ProjectController> controllerClass;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
        this.controllerClass = ProjectController.class;
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

    @GetMapping("/{id}")
    public EntityModel<Project> getProject(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(controllerClass).getProject(id))
                .withSelfRel().withType(RequestMethod.GET.toString());
        Link getProjectsLink = linkTo(methodOn(controllerClass).getProjects())
                .withRel("getProjects").withType(RequestMethod.GET.toString());
        Link putProjectLink = linkTo(methodOn(controllerClass).putProject(null, id))
                .withRel("putProject").withType(RequestMethod.PUT.toString());

        Link getBookingsLink = linkTo(methodOn(controllerClass).getBookings(id))
                .withRel("getBookings").withType(RequestMethod.GET.toString());

        EntityModel response = new EntityModel<>(projectService.findOne(id));
        response.add(selfLink, getProjectsLink, putProjectLink, getBookingsLink);
        return response;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getProject(@PathVariable Long id) {
//        Link selfLink = linkTo(methodOn(controllerClass).getProject(id))
//                .withSelfRel().withType(RequestMethod.GET.toString());
//        EntityModel entityModel;
//        if(projectService.existById(id)) {
//            entityModel = new EntityModel<>(projectService.findOne(id));
//            entityModel.add(selfLink);
//            return new ResponseEntity<>(entityModel, HttpStatus.OK);
//        }
//        entityModel = new EntityModel<>("ERROR Entity not found");
//        Link getProjectsLink = linkTo(methodOn(controllerClass).getProjects())
//                .withRel("getProjects").withType(RequestMethod.GET.toString());
//        entityModel.add(getProjectsLink);
//        return new ResponseEntity<>(entityModel, HttpStatus.NOT_FOUND);
//    }

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
        Link getProjectsLink = linkTo(methodOn(controllerClass).getProjects())
                .withRel("getProjects").withType(RequestMethod.GET.toString());
        Link selfLink = linkTo(methodOn(controllerClass).getProject(id))
                .withSelfRel().withType(RequestMethod.GET.toString());
        EntityModel response = new EntityModel<>(projectService.updateOne(project, id));
        response.add(selfLink, getProjectsLink);
        return response;
    }

    @DeleteMapping("/{id}")
    public EntityModel deleteProject(@PathVariable Long id) {
        Link getProjectsLink = linkTo(methodOn(controllerClass).getProjects())
                .withRel("getProjects").withType(RequestMethod.GET.toString());
        EntityModel response = new EntityModel<>(projectService.deleteBooking(id));
        response.add(getProjectsLink);
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
        Link backToProjectLink = linkTo(methodOn(controllerClass).getProject(id))
                .withRel("backToProject").withType(RequestMethod.GET.toString());
        Link createBookingLink = linkTo(methodOn(controllerClass).postBooking(null, id))
                .withRel("postBooking").withType(RequestMethod.POST.toString());
        CollectionModel response = new CollectionModel<>(projectService.getBookings(id));
        response.add(backToProjectLink, createBookingLink);
        return response;
    }

}
