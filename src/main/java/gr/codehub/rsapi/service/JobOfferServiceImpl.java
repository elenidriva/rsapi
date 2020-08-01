package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.JobOfferDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferIsInactive;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.JobOfferUpdateException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.JobOfferRepository;
import gr.codehub.rsapi.repository.JobOfferSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private JobOfferSkillRepository jobOfferSkillRepository;

    @Override
    public JobOffer addJobOffer(JobOfferDto jobOfferDto) throws JobOfferCreationException {
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

        return jobOfferRepository.save(jobOffer);
    }


    @Override
    public JobOffer updateJobOffer(JobOfferDto jobOfferDto, int jobOfferId) throws JobOfferNotFoundException, JobOfferUpdateException {
        JobOffer jobOfferInDb = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("Not such job offer"));
        if (jobOfferInDb.getStatus() == Status.INACTIVE)
            throw new JobOfferUpdateException("Failed to update Job Offer, because Job is inactive");
        jobOfferInDb.setRegion(jobOfferDto.getRegion());
        jobOfferInDb.setExperienceLevel(jobOfferDto.getExperienceLevel());
        jobOfferInDb.setPositionTitle(jobOfferDto.getPositionTitle());
        jobOfferInDb.setCompany(jobOfferDto.getCompany());
        jobOfferInDb.setJobOfferSkillList(jobOfferDto.getJobOfferSkillList());

        return jobOfferRepository.save(jobOfferInDb);
    }


    @Override
    public List<JobOffer> getJobOffers() {

        return jobOfferRepository.findAll();
    }

    @Override
    public List<JobOffer> findJobOffersByCriteria
            (String positionTitle, Region region, LocalDate date, Skill skill) {
        return jobOfferRepository.findJobOffersByCriteria(positionTitle, region, date, skill);
    }

    @Override
    public JobOffer getJobOffer(int jobOfferId) {
        Optional<JobOffer> jobOfferInDbOptional = jobOfferRepository.findById(jobOfferId);
        JobOffer jobOfferInDb = jobOfferInDbOptional.get();

        return jobOfferInDb;
    }

    public boolean setJobOfferInactive(int jobOfferIndex) throws JobOfferNotFoundException, JobOfferIsInactive {
        JobOffer jobOfferInDb = jobOfferRepository.findById(jobOfferIndex).orElseThrow(() -> new JobOfferNotFoundException("Cannot find jobOffer with id:" + jobOfferIndex));
        JobOffer jobOffer;
        if (jobOfferInDb.getStatus().equals(Status.INACTIVE))
            throw new JobOfferIsInactive("JobOffer with id:" + jobOfferIndex + " is already inactive.");
        else jobOfferInDb.setStatus(Status.ACTIVE);
        jobOffer = jobOfferInDb;
        jobOffer.setStatus(Status.INACTIVE);
        jobOfferRepository.save(jobOffer);
        return true;
    }

    @Override
    public void addJobOfferSkills(List<JobOffer> jobOffers) {
        for (JobOffer jobOffer : jobOffers) {
            jobOfferSkillRepository.saveAll(jobOffer.getJobOfferSkillList());
        }
    }

    @Override
    public List<JobOffer> addJobOffers(List<JobOffer> jobOffers) {
        return jobOfferRepository.saveAll(jobOffers);
    }

}
