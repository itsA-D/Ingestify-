package com.etl.system.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CSVHelperTest {

    @Test
    void testCsvToMapList() throws Exception {
        String content = "id,name,age\n1,John,25\n2,Jane,30";
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.csv", "text/csv", content.getBytes());
        
        List<Map<String, String>> result = CSVHelper.csvToMapList(file);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).get("name"));
        assertEquals("30", result.get(1).get("age"));
    }
    
    @Test
    void testCsvToMapListWithEmptyFile() throws Exception {
        String content = "";
        MockMultipartFile file = new MockMultipartFile(
            "file", "empty.csv", "text/csv", content.getBytes());
        
        List<Map<String, String>> result = CSVHelper.csvToMapList(file);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}