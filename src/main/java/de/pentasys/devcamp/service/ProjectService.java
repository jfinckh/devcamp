package de.pentasys.devcamp.service;

import de.pentasys.devcamp.domain.Booking;
import de.pentasys.devcamp.domain.Project;
import de.pentasys.devcamp.repositories.BookingRepository;
import de.pentasys.devcamp.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
public class ProjectService {

    final ProjectRepository projectRepository;
    final BookingRepository bookingRepository;

    public ProjectService(ProjectRepository projectRepository, BookingRepository bookingRepository) {
        this.projectRepository = projectRepository;
        this.bookingRepository = bookingRepository;
    }

    public Project findOne(Long id) {
        return projectRepository.findById(id).get();
    }

    public Iterable<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateOne(Project project, Long id) {
        return projectRepository.findById(id)
                .map(record -> {
                    record.setName(project.getName());
                    record.setManHour(project.getManHour());
                    return projectRepository.save(record);
                }).orElseGet(() -> {
                    return projectRepository.save(project);
                });
    }

    public Project createBooking(Booking booking, Long id) {
        if (booking.getDate() == null) {
            booking.setDate(LocalDate.now());
        }
        return projectRepository.findById(id)
                .map(record -> {
                    record.addBooking(booking);
                    return projectRepository.save(record);
                }).orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Booking> getBookings(Long id) {
        return bookingRepository.findAllByProjectId(id);
    }
}
