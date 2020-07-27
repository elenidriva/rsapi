package gr.codehub.rsapi.model;

import gr.codehub.rsapi.enums.DegreeLevel;
import gr.codehub.rsapi.enums.ExperienceLevel;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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

    private String description;
    private Date jobOfferDate;


    private DegreeLevel degreeLevel;
    private ExperienceLevel experienceLevel;
    private Status status;
    private Region region;

    @OneToMany(mappedBy="jobOffer")
    private List<JobOfferSkill> jobOfferSkillList;



}
