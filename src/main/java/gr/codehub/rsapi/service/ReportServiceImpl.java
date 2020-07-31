package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final MatchRepository matchRepository;

    @Autowired
    public ReportServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

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
