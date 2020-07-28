package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicantController {

@Autowired
    private ApplicantService applicantService;


    @GetMapping("applicant") // end point, verb, parameters if they exist
    public List<Applicant> getApplicant() {
        return applicantService.getApplicants();
    }


    @GetMapping("applicant/{id}")
    public Applicant getApplicant(@PathVariable int id) throws ApplicantNotFoundException {
        return applicantService.getApplicant(id);
    }





}

