package gr.codehub.rsapi.dto;

import gr.codehub.rsapi.enums.DegreeLevel;
import gr.codehub.rsapi.enums.ExperienceLevel;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.ApplicantSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDto {
    private String firstName;
    private String lastName;
    private String address;
    private Region region;
    private DegreeLevel degreeLevel;
    private ExperienceLevel experienceLevel;
    private List<ApplicantSkill> applicantSkillList;

}
