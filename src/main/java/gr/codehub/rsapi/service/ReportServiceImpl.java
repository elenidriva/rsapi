package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.logging.SLF4JExample;
import gr.codehub.rsapi.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final MatchRepository matchRepository;
    private static final Logger logger = LoggerFactory.getLogger(SLF4JExample.class);

    /**
     * Get the most offered skills
     *
     * @return list OfferedRequestedDto
     */
    @Override
    public List<OfferedRequestedDto> getMostOfferedSkills() {
        logger.info("Successfully getting most offered skills");
        return matchRepository.getMostOfferedSkills();
    }

    /**
     * Get the most requested skills
     *
     * @return list OfferedRequestedDto
     */
    @Override
    public List<OfferedRequestedDto> getMostRequestedSkills() {
        logger.info("Successfully getting most requested skills");
        return matchRepository.getMostRequestedSkills();
    }

    /**
     * Get a list of applicants that do not match with JobOffer
     *
     * @return list ApplicantNotMatchedDto
     */
    @Override
    public List<ApplicantNotMatchedDto> getNotMatchedSkills() {
        logger.info("Successfully getting not matched skills");
        return matchRepository.getNotMatchedSkills();
    }

}
