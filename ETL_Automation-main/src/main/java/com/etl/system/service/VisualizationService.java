package com.etl.system.service;

import com.etl.system.dto.DataAnalysisResultDTO;
import com.etl.system.dto.VisualizationDTO;
import java.util.List;
import java.util.Map;

public interface VisualizationService {
    VisualizationDTO generateVisualizations(List<DataAnalysisResultDTO> analysisResults, 
                                         List<Map<String, String>> rawData);
}