package gr.codehub.rsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto {
    private int id;
    private String title;

    public SkillDto(String title) {
        this.title = title;
    }
}
