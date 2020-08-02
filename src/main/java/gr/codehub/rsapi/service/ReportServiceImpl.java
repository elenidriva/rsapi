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


    /**
     * Get the most offered skills
     *
     * @return list OfferedRequestedDto
     */
    @Override
    public List<OfferedRequestedDto> getMostOfferedSkills() {
        return matchRepository.getMostOfferedSkills();
    }

    /**
     * Get the most requested skills
     *
     * @return list OfferedRequestedDto
     */
    @Override
    public List<OfferedRequestedDto> getMostRequestedSkills() {
        return matchRepository.getMostRequestedSkills();
    }

    /**
     * Get a list of applicants that do not match with JobOffer
     *
     * @return list ApplicantNotMatchedDto
     */
    @Override
    public List<ApplicantNotMatchedDto> getNotMatchedSkills() {
        return matchRepository.getNotMatchedSkills();
    }

}
