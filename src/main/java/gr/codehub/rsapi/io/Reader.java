package gr.codehub.rsapi.io;

import gr.codehub.rsapi.dto.ApplicantAndSkillsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface Reader<T> {
    public List<T> readFromExcel() throws FileNotFoundException;

}
