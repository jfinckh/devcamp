package de.pentasys.devcamp;

import de.pentasys.devcamp.domain.Booking;
import de.pentasys.devcamp.domain.Project;
import de.pentasys.devcamp.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DevcampApplicationTests {

    @Autowired
    ProjectRepository projectRepository;

    final String entryUrl = "http://localhost:8080/projects";

    @Test
    public void contextLoads() {
        Project p1 = Project.builder().name("Test Project 1").manHour(700L).bookings(new ArrayList<>()).build();
        Project p2 = Project.builder().name("Test Project 2").manHour(100L).bookings(new ArrayList<>()).build();

        Booking b1 = Booking.builder().start(LocalTime.of(8, 00)).end(LocalTime.of(15, 00)).build();
        Booking b2 = Booking.builder().start(LocalTime.of(1, 00)).end(LocalTime.of(16, 00)).build();
        Booking b3 = Booking.builder().start(LocalTime.of(2, 00)).end(LocalTime.of(17, 00)).build();
        Booking b4 = Booking.builder().start(LocalTime.of(3, 00)).end(LocalTime.of(18, 00)).build();

        p1.addBooking(b1);
        p1.addBooking(b2);

        p2.addBooking(b3);
        p2.addBooking(b4);

        projectRepository.save(p1);
        projectRepository.save(p2);


    }

    @Test
    public void givenEntitiesWithPathsWhenTraverseDownThenSuccess() throws Exception{
        Traverson traverson = new Traverson(new URI(entryUrl), MediaTypes.HAL_JSON);
//        String result = traverson.follow("self").toObject("$.manHour");
        String result = traverson.follow("$._embedded.projectList[0]").toObject("$.id");
        System.out.println(result);
    }

}
