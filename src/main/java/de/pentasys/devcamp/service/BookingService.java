package de.pentasys.devcamp.service;

import de.pentasys.devcamp.repositories.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
}
