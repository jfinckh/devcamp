package de.pentasys.devcamp;

import de.pentasys.devcamp.domain.Booking;
import de.pentasys.devcamp.domain.Project;
import de.pentasys.devcamp.repositories.BookingRepository;
import de.pentasys.devcamp.repositories.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@SpringBootApplication
public class DevcampApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevcampApplication.class, args);
    }

    @Bean
    public CommandLineRunner initTestData(ProjectRepository projectRepository, BookingRepository bookingRepository) {
        return args -> {

            Project project1 = Project.builder()
                    .name("DAIMLER Cockpit")
                    .manHour(500L)
                    .bookings(new ArrayList<>())
                    .build();
            Project project2 = Project.builder()
                    .name("Porsche Hybrid Drive")
                    .manHour(150L)
                    .bookings(new ArrayList<>())
                    .build();

            Booking booking1 = Booking.builder()
                    .date(LocalDate.now())
                    .start(LocalTime.of(8, 0))
                    .end(LocalTime.of(16,0))
                    .build();
            Booking booking2 = Booking.builder()
                    .date(LocalDate.of(2019, 2,3))
                    .start(LocalTime.of(8, 0))
                    .end(LocalTime.of(12,30))
                    .build();
            Booking booking3 = Booking.builder()
                    .date(LocalDate.of(2019, 2,3))
                    .start(LocalTime.of(8, 0))
                    .end(LocalTime.of(16,0))
                    .build();
            Booking booking4 = Booking.builder()
                    .date(LocalDate.of(2019, 2,3))
                    .start(LocalTime.of(8, 0))
                    .end(LocalTime.of(14,15))
                    .build();

            project1.addBooking(booking1);
            project1.addBooking(booking2);

            project2.addBooking(booking3);
            project2.addBooking(booking4);

            projectRepository.save(project1);
            projectRepository.save(project2);
        };
    }

}
