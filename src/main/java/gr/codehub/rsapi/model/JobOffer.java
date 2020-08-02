package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.codehub.rsapi.enums.ExperienceLevel;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String positionTitle;
    private String company;

    private LocalDate jobOfferDate;


    private ExperienceLevel experienceLevel;
    private Status status;
    private Region region;

    @OneToMany(mappedBy = "jobOffer")
    @ToString.Exclude
    private List<JobOfferSkill> jobOfferSkillList;


}
