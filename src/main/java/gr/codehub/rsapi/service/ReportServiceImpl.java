package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
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
        log.info("Successfully getting most offered skills");
        return matchRepository.getMostOfferedSkills();
    }

    /**
     * Get the most requested skills
     *
     * @return list OfferedRequestedDto
     */
    @Override
    public List<OfferedRequestedDto> getMostRequestedSkills() {
        log.info("Successfully getting most requested skills");
        return matchRepository.getMostRequestedSkills();
    }

    /**
     * Get a list of applicants that do not match with JobOffer
     *
     * @return list ApplicantNotMatchedDto
     */
    @Override
    public List<ApplicantNotMatchedDto> getNotMatchedSkills() {
        log.info("Successfully getting not matched skills");
        return matchRepository.getNotMatchedSkills();
    }

}
