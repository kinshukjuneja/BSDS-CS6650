package edu.neu.client.test;

import com.opencsv.CSVReader;
import edu.neu.client.model.RFIDLiftData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVParser {
    private static final String CSV_FILE_DAY1 = "/Users/Kinsh/Desktop/BSDSAssignment2Day1.csv";
    private static final String CSV_FILE_DAY2 = "/Users/Kinsh/Desktop/BSDSAssignment2Day2.csv";
    private CSVReader reader;

    public void parseCSV(int dayNum) {
        try {
            if (dayNum == 1) initializeReader(CSV_FILE_DAY1);
            else if (dayNum == 2) initializeReader(CSV_FILE_DAY2);
            else return;
            String[] line;
            RFIDLiftData parseRFID;
            if((reader.readNext()) != null) {
                while ((line = reader.readNext()) != null) {
                    parseRFID = new RFIDLiftData(Integer.valueOf(line[0]), Integer.valueOf(line[1]), Integer.valueOf(line[2]), Integer.valueOf(line[3]), Integer.valueOf(line[4]));
                    TestDataUtil.addRFIDLiftData(parseRFID);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeReader(String fileReaderPath) {
        try {
            reader = new CSVReader(new FileReader(fileReaderPath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
