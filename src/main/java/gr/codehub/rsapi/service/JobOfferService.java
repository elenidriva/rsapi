package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.JobOfferDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferIsInactive;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.JobOfferUpdateException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;

import java.time.LocalDate;
import java.util.List;

public interface JobOfferService {

    List<JobOffer> getJobOffers();

    JobOffer getJobOffer(int jobOfferId);

    JobOffer updateJobOffer(JobOfferDto jobOfferDto, int jobOfferId) throws JobOfferNotFoundException, JobOfferUpdateException;

    JobOffer addJobOffer(JobOfferDto jobOfferDto) throws JobOfferCreationException;

    List<JobOffer> findJobOffersByCriteria(String positionTitle, Region region, LocalDate date, Skill skill);

    boolean setJobOfferInactive(int jobOfferIndex) throws JobOfferNotFoundException, JobOfferIsInactive;

    void addJobOfferSkills(List<JobOffer> jobOffers);

    List<JobOffer> addJobOffers(List<JobOffer> jobOffers);

}


