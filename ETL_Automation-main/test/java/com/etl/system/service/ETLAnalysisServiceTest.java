package com.etl.system.service;

import com.etl.system.dto.DataAnalysisResultDTO;
import com.etl.system.service.impl.ETLAnalysisServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ETLAnalysisServiceTest {

    @Mock
    private ProcessedDataRepository processedDataRepository;
    
    @Mock
    private DataAnalyzerFactory analyzerFactory;
    
    @InjectMocks
    private ETLAnalysisServiceImpl etlAnalysisService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessCSVFile() throws Exception {
        String content = "id,age,income\n1,25,50000\n2,32,65000";
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.csv", "text/csv", content.getBytes());
        
        when(analyzerFactory.createAnalyzer(any())).thenReturn(new NumericDataAnalyzer());
        
        List<DataAnalysisResultDTO> results = etlAnalysisService.processCSVFile(file);
        
        assertNotNull(results);
        assertEquals(3, results.size()); // id, age, income columns
        verify(analyzerFactory, atLeastOnce()).createAnalyzer(any());
    }
    
    @Test
    void testSaveAnalysisResults() {
        DataAnalysisResultDTO result = new DataAnalysisResultDTO();
        result.setColumnName("test");
        result.setMean(BigDecimal.valueOf(10.5));
        
        etlAnalysisService.saveAnalysisResults(List.of(result));
        
        verify(processedDataRepository, times(1)).saveAll(any());
    }
}