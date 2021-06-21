package gr.codehub.rsapi.service;

import gr.codehub.rsapi.commons.ApplicantErrorCodes;
import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.ApplicantException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepository;
import gr.codehub.rsapi.repository.ApplicantSkillRepository;
import gr.codehub.rsapi.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static gr.codehub.rsapi.commons.ApplicantErrorCodes.APPLICANT_CREATION_INVALID_SKILL_EXCEPTION;
import static gr.codehub.rsapi.commons.ApplicantErrorCodes.APPLICANT_NOT_FOUND_EXCEPTION;
/*
TODO:
* 1. Exception Error Codes in Separate File
* 2. Declarative Errors
* 3. Separation of Concerns - Handlers Services etc
* 4. Deprecated methods
* 5.addApplicant logic when insufficient data
* 6. Fix JavaDocs
 */

/**
 * ApplicantServiceImpl: Responsible for handling the Applicant related functions
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantSkillRepository applicantSkillRepository;
    private final SkillRepository skillRepository;

    /**
     * This method takes the data from dto and passes the data needed to create the applicant and saves it in the base
     *
     * @param applicantDto - the request POJO
     * @return  the newly added applicant
     * @throws ApplicantException the user tried to create an applicant without the required fields
     */
    @Override
    public Applicant addApplicant(ApplicantDto applicantDto) throws ApplicantException {
        if (applicantDto == null)
            throw new ApplicantException(ApplicantErrorCodes.APPLICANT_CREATION_EXCEPTION.getCode(),
                    ApplicantErrorCodes.APPLICANT_CREATION_EXCEPTION.getMessage());
        Applicant applicant = Applicant.builder()
                .firstName(applicantDto.getFirstName())
                .lastName(applicantDto.getLastName())
                .address(applicantDto.getAddress())
                .region(applicantDto.getRegion())
                .experienceLevel(applicantDto.getExperienceLevel())
                .degreeLevel(applicantDto.getDegreeLevel())
                .applicantSkillList(applicantDto.getApplicantSkillList())
                .applicationDate(LocalDate.now())
                .status(Status.ACTIVE)
                .build();
        applicantRepository.save(applicant);

        Applicant applFromRep = applicantRepository.findById(applicant.getId()).orElseThrow(() ->
                new ApplicantException(APPLICANT_NOT_FOUND_EXCEPTION.getCode(),
                        APPLICANT_NOT_FOUND_EXCEPTION.getMessage()));

        ApplicantSkill appSkill = new ApplicantSkill();
        appSkill.setApplicant(applFromRep);
        applicantDto.getApplicantSkillList().forEach(o -> {
            Skill skillFromDb = skillRepository.findBySkillTitle(o.getSkill().getTitle());
            if (skillFromDb != null) {
                appSkill.setSkill(skillFromDb);
                applicantSkillRepository.save(appSkill);
            } else {
                try {
                    applicantRepository.deleteById(applicant.getId());
                    throw new ApplicantException(APPLICANT_CREATION_INVALID_SKILL_EXCEPTION.getCode(),
                            APPLICANT_CREATION_INVALID_SKILL_EXCEPTION.getMessage());
                } catch (ApplicantException e) {
                    log.info("Completely wrong logic...");
                }
            }
        });
        return applicant;
    }


    /**
     * This method takes an id from the user and searches the repository if it exists
     * If the status is inactive we tell him it's Inactive and he does it active
     *
     * @param applicantIndex the applicant id given by the user
     * @return true in case the applicant was set to inactive
     * @throws ApplicantException in case the applicant does not even exist
     */
    @Override
    public boolean setApplicantInactive(int applicantIndex) throws ApplicantException {
        Applicant applicantInDb = applicantRepository.findById(applicantIndex).
                orElseThrow(() -> new ApplicantException(ApplicantErrorCodes.APPLICANT_NOT_FOUND_EXCEPTION.getCode(),
                        ApplicantErrorCodes.APPLICANT_NOT_FOUND_EXCEPTION.getCode()));
        Applicant applicant;
        if (!applicantInDb.getStatus().equals(Status.INACTIVE)) {
            applicant = applicantInDb;
            applicant.setStatus(Status.INACTIVE);
            applicantRepository.save(applicant);
            log.info(String.format("Successfully set applicant with id [%s] to inactive.", applicant.getId()));
        }
        return true;
    }


    /**
     * Retrieves an applicant given an id
     *
     * @param applicantIndex the id of applicant given by the user
     * @return the applicant based on the id given by the user
     * @throws ApplicantException in case the user does not even exist in the DB
     */
    @Override
    public Applicant getApplicant(int applicantIndex) throws ApplicantException {
        return applicantRepository.findById(applicantIndex).orElseThrow(() ->
                new ApplicantException(ApplicantErrorCodes.APPLICANT_NOT_FOUND_EXCEPTION.getCode(),
                        ApplicantErrorCodes.APPLICANT_NOT_FOUND_EXCEPTION.getMessage()));
    }

    /**
     * Takes the data from dto and passes the data needed to make update the applicant
     *
     * @param applicantDto   - the request POJO
     * @param applicantIndex - the index of the applicant the update will be performed on
     * @return the applicant updated and saves it to the base
     * @throws ApplicantException in case the user does not even exist in the DB
     */
    @Override
    public Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws ApplicantException {

        Applicant applicantInDb = applicantRepository.findById(applicantIndex).orElseThrow(() ->
                new ApplicantException(String.format(ApplicantErrorCodes.APPLICANT_NOT_FOUND_EXCEPTION.getCode(), applicantIndex),
                        ApplicantErrorCodes.APPLICANT_NOT_FOUND_EXCEPTION.getMessage()));
        if (applicantInDb.getStatus() != Status.INACTIVE) {
            applicantInDb.toBuilder()
                    .firstName(applicantDto.getFirstName())
                    .lastName(applicantDto.getLastName())
                    .status(Status.ACTIVE)
                    .address(applicantDto.getAddress())
                    .degreeLevel(applicantDto.getDegreeLevel())
                    .experienceLevel(applicantDto.getExperienceLevel())
                    .applicantSkillList(applicantDto.getApplicantSkillList())
                    .region(applicantDto.getRegion())
                    .build();
            log.info(String.format("Successfully updating applicant with id: [%s]", applicantIndex));
            applicantRepository.save(applicantInDb);
        }
        return applicantInDb;
    }

    @Override
    public List<Applicant> getApplicants() {
        log.info("Successfully getting list of applicants");
        return applicantRepository.findAll();
    }

    /**
     * This method finds the applicants from the applicant
     * repository based on the criteria given by the user
     *
     * @param firstName the first name of the applicant
     * @param lastName  the last name of the applicant
     * @param region    the region of the applicant
     * @param date      the date that applicant made the application
     * @return a list of applicants based on the criteria that become from the user
     */
    @Override
    public List<Applicant> findApplicantsByCriteria(String firstName, String lastName, Region region, LocalDate date) {
        log.info("Successfully retrieve a list of applicants with the criteria given.");
        return applicantRepository.findApplicantByCriteria(firstName, lastName, region, date);
    }

    /**
     * Saves all available applicant skills into the DB
     *
     * @param applicants - a list of applicants whose skills will be saved
     */
    @Override
    public void addApplicantSkills(List<Applicant> applicants) {
        for (Applicant applicant : applicants) {
            applicantSkillRepository.saveAll(applicant.getApplicantSkillList());
            log.info("Successfully adding applicant skills");
        }
    }

    /**
     * Saves the list of applicants into the DB
     *
     * @param applicants - a list of applicants that will be saved
     */
    @Override
    public List<Applicant> addApplicants(List<Applicant> applicants) {
        log.info("Successfully add applicants");
        return applicantRepository.saveAll(applicants);
    }

    @Deprecated
    public List<ApplicantSkill> addAppSkillToApplicant(List<ApplicantSkill> applicantSkillList, Applicant applicant) {
        for (ApplicantSkill applicantSkill : applicantSkillList) {
            applicantSkill.setApplicant(applicant);
        }
        log.info(String.format("Successfully added new Skills to applicant with id %s", applicant.getId()));
        return applicantSkillRepository.saveAll(applicantSkillList);
    }
}
