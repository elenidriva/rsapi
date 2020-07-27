package gr.codehub.rsapi.model;

import gr.codehub.rsapi.enums.MatchType;
import gr.codehub.rsapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Applicant applicant;
    private JobOffer jobOffer;
}
