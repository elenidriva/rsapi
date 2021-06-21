package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.JobOfferDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferIsInactive;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.JobOfferUpdateException;
import gr.codehub.rsapi.exception.RCMRuntimeException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.JobOfferSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.JobOfferRepository;
import gr.codehub.rsapi.repository.JobOfferSkillRepository;
import gr.codehub.rsapi.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferSkillRepository jobOfferSkillRepository;
    private final SkillRepository skillRepository;

    /**
     * Takes the data from dto and passes the data needed to create the job offer and saves it in the base
     *
     * @param jobOfferDto gets from the user a dto object
     * @return job offer with id and saves to the data base
     * @throws JobOfferCreationException the user tried to create a job offer without the required fields
     */
    @Override
    public JobOffer addJobOffer(JobOfferDto jobOfferDto) throws RCMRuntimeException, JobOfferCreationException {
        JobOffer jobOffer = new JobOffer();
        if (jobOfferDto == null ||
                jobOfferDto.getCompany() == null ||
                jobOfferDto.getExperienceLevel() == null ||
                jobOfferDto.getPositionTitle() == null)
            throw new JobOfferCreationException("Failed to create Job Offer, since there was no sufficient input given");

        jobOffer.setPositionTitle(jobOfferDto.getPositionTitle());
        jobOffer.setCompany(jobOfferDto.getCompany());
        jobOffer.setExperienceLevel(jobOfferDto.getExperienceLevel());
        jobOffer.setRegion(jobOfferDto.getRegion());
        jobOffer.setStatus(Status.ACTIVE);
        jobOffer.setJobOfferSkillList(jobOfferDto.getJobOfferSkillList());
        jobOffer.setJobOfferDate(LocalDate.now());

        jobOfferRepository.save(jobOffer);

        JobOffer jobFromRep = jobOfferRepository.findById(jobOffer.getId()).orElseThrow(() -> new RCMRuntimeException("Cannot find applicant with id:" + jobOffer.getId()));
        JobOfferSkill jobSkill = new JobOfferSkill();
        jobSkill.setJobOffer(jobFromRep);
        jobOfferDto.getJobOfferSkillList().forEach(o -> {
            Skill skillFromDb = skillRepository.findBySkillTitle(o.getSkill().getTitle());
            if (skillFromDb != null) {
                jobSkill.setSkill(skillFromDb);
                jobOfferSkillRepository.save(jobSkill);
            } else {
                try {
                    jobOfferRepository.deleteById(jobOffer.getId());
                    throw new RCMRuntimeException("Please insert a skill that exists in the DB. Your job offer was not created.");

                } catch (RCMRuntimeException e) {
                    log.info("Successfully add Job Offer");
                }

            }
        });
        return jobOffer;

    }


    /**
     * Takes the data from dto and passes the data needed to make update the job offer
     *
     * @param jobOfferDto gets from the user a dto object
     * @param jobOfferId  takes the ID of a job offer and finds if exists
     * @return the job offer updated and saves it to the base
     * @throws JobOfferNotFoundException The user tried to update a Job offer that does not exist
     * @throws JobOfferUpdateException   The user tried to update the job offer but the job offer is inactive
     */
    @Override
    public JobOffer updateJobOffer(JobOfferDto jobOfferDto, int jobOfferId) throws RCMRuntimeException, JobOfferUpdateException {
        JobOffer jobOfferInDb = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(
                        () -> new RCMRuntimeException("Not such job offer"));
        if (jobOfferInDb.getStatus() == Status.INACTIVE)
            throw new JobOfferUpdateException("Failed to update Job Offer, because Job is inactive");
        jobOfferInDb.setRegion(jobOfferDto.getRegion());
        jobOfferInDb.setExperienceLevel(jobOfferDto.getExperienceLevel());
        jobOfferInDb.setPositionTitle(jobOfferDto.getPositionTitle());
        jobOfferInDb.setCompany(jobOfferDto.getCompany());
        jobOfferInDb.setJobOfferSkillList(jobOfferDto.getJobOfferSkillList());
        log.info("Successfully update Job Offer");
        return jobOfferRepository.save(jobOfferInDb);
    }


    @Override
    public List<JobOffer> getJobOffers() {
        log.info("Successfully list  Job Offers");
        return jobOfferRepository.findAll();
    }

    /**
     * This method finds the job offers from the job offer
     * repository based on the criteria given by the user
     *
     * @param positionTitle the title of the job offer position
     * @param region        the region for the job offer
     * @param date          the date that job offer was made
     * @return a list of job offers based on the criteria that become from the user
     */
    @Override
    public List<JobOffer> findJobOffersByCriteria
    (String positionTitle, Region region, LocalDate date) {
        log.info("Successfully adding job offer by criteria");
        return jobOfferRepository.findJobOffersByCriteria(positionTitle, region, date);
    }

    /**
     * This method searches the base if there is a job offer with this id and if there is it returns it
     *
     * @param jobOfferId the id of job offer given by the user
     * @return the job offer based on the id given by the user
     * @throws JobOfferNotFoundException The user tried to find a job offer that does not exist in the data base
     */
    @Override
    public JobOffer getJobOffer(int jobOfferId) {
        Optional<JobOffer> jobOfferInDbOptional = jobOfferRepository.findById(jobOfferId);
        JobOffer jobOfferInDb = jobOfferInDbOptional.get();
        log.info("Successfully getting job offer by id");
        return jobOfferInDb;
    }

    /**
     * This method takes an id from the user and searches the repository if it exists
     * If the status is inactive we tell him it's Inactive and he does it active
     *
     * @param jobOfferIndex the job offer id given by the user
     * @return to successfully change the status to inactive returns true
     * @throws JobOfferNotFoundException the user tried to find a job offer with id that does not exist
     * @throws JobOfferIsInactive        the user tried to do inactive a job offer that is already inactive
     */
    public boolean setJobOfferInactive(int jobOfferIndex) throws RCMRuntimeException, JobOfferIsInactive {
        JobOffer jobOfferInDb = jobOfferRepository.findById(jobOfferIndex).orElseThrow(() -> new RCMRuntimeException("Cannot find jobOffer with id:" + jobOfferIndex));
        JobOffer jobOffer;
        if (jobOfferInDb.getStatus().equals(Status.INACTIVE))
            throw new JobOfferIsInactive("JobOffer with id:" + jobOfferIndex + " is already inactive.");
        else jobOfferInDb.setStatus(Status.ACTIVE);
        jobOffer = jobOfferInDb;
        jobOffer.setStatus(Status.INACTIVE);
        jobOfferRepository.save(jobOffer);
        log.info("Successfully setting Job Offer inactive");
        return true;
    }

    @Override
    public void addJobOfferSkills(List<JobOffer> jobOffers) {
        for (JobOffer jobOffer : jobOffers) {
            jobOfferSkillRepository.saveAll(jobOffer.getJobOfferSkillList());
        }
        log.info("Successfully adding Job Offer skills");
    }

    @Override
    public List<JobOffer> addJobOffers(List<JobOffer> jobOffers) {
        log.info("Successfully add Job Offers");
        return jobOfferRepository.saveAll(jobOffers);
    }

}
