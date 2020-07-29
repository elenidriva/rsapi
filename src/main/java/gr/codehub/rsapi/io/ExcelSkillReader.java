package gr.codehub.rsapi.io;

import gr.codehub.rsapi.model.Skill;
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

/**
 * Reading Skills from datarsapi.xlsx
 * Adding Skills into ArrayList
 */
public class ExcelSkillReader implements Reader<Skill> {

    public List<Skill> readFromExcel() throws FileNotFoundException {
        Logger.log("Read Skills from Given Excel File");

        FileInputStream data = new FileInputStream(new File("datarsapi.xlsx"));
        List<Skill> skills = new ArrayList<>();
        try {
            //create a workbook instance to hold reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(data);
            //Getting third sheet from workbook
            XSSFSheet sheet = workbook.getSheetAt(2);

            //iterate through each row
            Iterator<Row> rowIterator = sheet.iterator();
            boolean firstTime = true;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (firstTime) {
                    firstTime = false;
                    continue;
                }

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //check cell type and format accordingly
                    String skillName = cell.getStringCellValue();
                    Skill s = new Skill();
                    s.setTitle(skillName);
                    skills.add(s);
                }
            }

            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skills;
    }


}
