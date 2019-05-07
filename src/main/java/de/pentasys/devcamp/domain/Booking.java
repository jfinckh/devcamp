package de.pentasys.devcamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Long durationInMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private Project project;

    public Long getDurationInMinutes() {
        return Duration.between(start, end).toMinutes();
    }

}
