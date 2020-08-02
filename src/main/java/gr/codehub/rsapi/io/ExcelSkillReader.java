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


public class ExcelSkillReader implements Reader<Skill> {

    /**
     * Method to read from Excel the Skill data
     *
     * @return list of Skills
     * @thows FileNotFoundException
     */
    public List<Skill> readFromExcel() throws FileNotFoundException {
        Logger.log("Read Skills from Given Excel File");

        FileInputStream data = new FileInputStream(new File("datarsapi.xlsx"));
        List<Skill> skills = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(data);
            XSSFSheet sheet = workbook.getSheetAt(2);
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
