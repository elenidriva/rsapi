package gr.codehub.rsapi.io;

import gr.codehub.rsapi.enums.DegreeLevel;
import gr.codehub.rsapi.enums.ExperienceLevel;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.utility.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelJobOfferReader {

//    public List<JobOffer> readFromExcel() throws FileNotFoundException {
//        Logger.log("Read JobOffer from Given Excel File");
//        List<JobOffer> jobOfferList = new ArrayList<>();
//        FileInputStream data = new FileInputStream(new File("datarsapi.xlsx"));
//
//        try {
//            //create a workbook instance to hold reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(data);
//            //Getting third sheet from workbook
//            XSSFSheet sheet = workbook.getSheetAt(1);
//
//            //iterate through each row
//            Iterator<Row> rowIterator = sheet.iterator();
//            boolean firstTime = true;
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                if (firstTime) {
//                    firstTime = false;
//                    continue;
//                }
//
//                Iterator<Cell> cellIterator = row.cellIterator();
//                List<String> cellValues = new ArrayList<>();
//                JobOffer jobOffer = new JobOffer();
//                jobOffer.setJobOfferSkillList(new ArrayList<>());
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    cellValues.add(cell.getStringCellValue());
//                    if (cell.getColumnIndex() >= 5) {
//                        JobOffer jobOffers = new JobOffer();
//                        jobOffers.setCompany(cell.getStringCellValue());
//                        JobOfferSkill jobOfferSkill = new JobOfferSkill();
//                        //jobOfferSkill.setSkill(jobOffer);
////                        jobOffer.se(applicant);
////                        applicant.getApplicantSkillList().add(applicantSkill);
////                        //applicantSkills.add(applicantSkill);
//                    }
//                }
////                extractApplicant(applicant, cellValues);
////                applicantList.add(applicant);
//            }
//
//            data.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return applicantList;
//    }
//
//    private Applicant extractJobOffer(JobOffer jobOffer, List<String> cellValues) {
//
//        jobOffer.setCompany(cellValues.get(0));
//        jobOffer.setPositionTitle(cellValues.get(1));
//        applicant.setAddress(cellValues.get(2));
//        applicant.setRegion(Region.findRegionByLocation(cellValues.get(3)));
//        applicant.setDegreeLevel(DegreeLevel.findDegreeLevel(cellValues.get(4)));
//        applicant.setExperienceLevel(ExperienceLevel.findDExpLevel(cellValues.get(5)));
//        return applicant;
//    }
}
