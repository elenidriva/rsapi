package gr.codehub.rsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<JobOfferSkill> jobOfferSkillList;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<ApplicantSkill> applicantSkillList;

    public Skill(String skillTitle) {
        this.title = skillTitle;
    }
}
