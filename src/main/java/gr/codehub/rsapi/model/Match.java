package gr.codehub.rsapi.model;

import gr.codehub.rsapi.enums.MatchType;
import gr.codehub.rsapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Match {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date matchDate;
    private MatchType matchType;
    private Status status;
    @ManyToOne
    private Applicant applicant;
    @ManyToOne
    private JobOffer jobOffer;
}
