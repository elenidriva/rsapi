package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.codehub.rsapi.enums.MatchStatus;
import gr.codehub.rsapi.enums.MatchType;
import gr.codehub.rsapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Match {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate matchDate;
    @JsonIgnore
    private MatchType matchType;
    private MatchStatus matchStatus;
    private Status status;
    @ManyToOne
    private Applicant applicant;
    @ManyToOne
    private JobOffer jobOffer;
}
