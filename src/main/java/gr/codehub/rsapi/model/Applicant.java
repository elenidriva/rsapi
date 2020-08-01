package gr.codehub.rsapi.model;

import gr.codehub.rsapi.enums.DegreeLevel;
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
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;


    private String lastName;
    private String address;

    private LocalDate applicationDate;

    private Region region;
    private DegreeLevel degreeLevel;
    private ExperienceLevel experienceLevel;
    private Status status;

    @OneToMany(mappedBy = "applicant")
    @ToString.Exclude
    private List<ApplicantSkill> applicantSkillList;


}
