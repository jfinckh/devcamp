package de.pentasys.devcamp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long manHour;
    private Long totalHoursBooked;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();


    public Long getTotalHoursBooked() {
        Long totalMinutes = bookings.stream().mapToLong(Booking::getDurationInMinutes).sum();
        return totalMinutes/60;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setProject(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setProject(null);
    }
}

