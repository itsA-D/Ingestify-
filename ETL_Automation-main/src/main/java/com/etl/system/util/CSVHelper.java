package com.etl.system.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVHelper {
    
    public static List<Map<String, String>> csvToMapList(MultipartFile file) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();
        
        try (InputStream is = file.getInputStream();
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, 
                 CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            
            List<String> headers = csvParser.getHeaderNames();
            
            for (CSVRecord csvRecord : csvParser) {
                Map<String, String> record = new HashMap<>();
                for (String header : headers) {
                    record.put(header, csvRecord.get(header));
                }
                records.add(record);
            }
        }
        
        return records;
    }
}