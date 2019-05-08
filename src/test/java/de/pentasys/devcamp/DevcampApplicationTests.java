package de.pentasys.devcamp;

import de.pentasys.devcamp.domain.Project;
import de.pentasys.devcamp.domain.Booking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import java.net.URI;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DevcampApplicationTests {

    final String entryUrl = "http://localhost:8080/api/projects";

    @Test
    public void givenProjectEntity_WhenGetSelfRel_ThenSuccess() throws Exception {
        final ParameterizedTypeReference<EntityModel<Project>> projectType =
                new ParameterizedTypeReference<EntityModel<Project>>() {
                };

        Traverson traverson = new Traverson(new URI(entryUrl), MediaTypes.HAL_JSON);
        final String jsonPathToFirstProject = "$._embedded.projectList[0]._links.self.href";
        Project project = traverson
                .follow(jsonPathToFirstProject)
                .toObject(projectType)
                .getContent();
        System.out.println(project);
        assertThat(project.getName()).isEqualTo("DevCamp 2019");
    }

    @Test
    public void testBookingCollection() throws Exception {
        final ParameterizedTypeReference<CollectionModel<Booking>> bookingType =
                new ParameterizedTypeReference<CollectionModel<Booking>>() {
                };
        Traverson traverson = new Traverson(new URI(entryUrl), MediaTypes.HAL_JSON);
        final String jsonPathToFirstProject = "$._embedded.projectList[0]._links.self.href";
        Collection<Booking> bookings = traverson
                .follow(jsonPathToFirstProject)
                .follow("getBookings")
                .toObject(bookingType)
                .getContent();
        System.out.println(bookings);
    }

}
