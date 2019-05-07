package de.pentasys.devcamp.repositories;

import de.pentasys.devcamp.domain.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    Iterable<Booking> findAllByProjectId(Long projectId);

}
