package com.etl.system.service.analyzer;

import com.etl.system.dto.DataAnalysisResultDTO;
import java.util.List;
import java.util.Map;

public interface DataAnalyzer {
    DataAnalysisResultDTO analyze(List<Map<String, String>> data, String columnName);
}