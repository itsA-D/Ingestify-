package com.etl.system.service;

import com.etl.system.dto.DataAnalysisResultDTO;
import com.etl.system.dto.VisualizationDTO;
import com.etl.system.service.impl.VisualizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class VisualizationServiceTest {

    private VisualizationService visualizationService;
    
    @BeforeEach
    void setUp() {
        visualizationService = new VisualizationServiceImpl();
    }

    @Test
    void testGenerateVisualizations() {
        DataAnalysisResultDTO result = new DataAnalysisResultDTO();
        result.setColumnName("age");
        result.setDataType("NUMERIC");
        result.setMean(BigDecimal.valueOf(30.5));
        
        List<Map<String, String>> rawData = List.of(
            Map.of("age", "25"),
            Map.of("age", "32"),
            Map.of("age", "41")
        );
        
        VisualizationDTO visualizationDTO = visualizationService.generateVisualizations(
            List.of(result), rawData);
        
        assertNotNull(visualizationDTO);
        assertTrue(visualizationDTO.getChartData().containsKey("age"));
        assertNotNull(visualizationDTO.getChartData().get("age").get("histogram"));
        assertNotNull(visualizationDTO.getChartData().get("age").get("boxplot"));
    }
}