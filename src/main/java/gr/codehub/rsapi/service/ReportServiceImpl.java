package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final MatchRepository matchRepository;


    @Override
    public List<OfferedRequestedDto> getMostOfferedSkills() {
        return matchRepository.getMostOfferedSkills();
    }

    @Override
    public List<OfferedRequestedDto> getMostRequestedSkills() {
        return matchRepository.getMostRequestedSkills();
    }

    @Override
    public List<ApplicantNotMatchedDto> getNotMatchedSkills() {
        return matchRepository.getNotMatchedSkills();
    }

}
