package gr.codehub.rsapi.dto;

import gr.codehub.rsapi.enums.ExperienceLevel;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.model.JobOfferSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferDto {

    private String positionTitle;
    private String company;
    private ExperienceLevel experienceLevel;
    private Status status;
    private Region region;
    private List<JobOfferSkill> jobOfferSkillList;
}
