package de.pentasys.devcamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.pentasys.devcamp.serializers.LocalDateDeserializer;
import de.pentasys.devcamp.serializers.LocalTimeDeserializer;
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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime start;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
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
