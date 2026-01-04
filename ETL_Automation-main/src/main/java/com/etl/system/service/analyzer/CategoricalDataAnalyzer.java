package com.etl.system.service.analyzer;

import com.etl.system.dto.DataAnalysisResultDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoricalDataAnalyzer implements DataAnalyzer {
    
    @Override
    public DataAnalysisResultDTO analyze(List<Map<String, String>> data, String columnName) {
        List<String> values = data.stream()
            .map(row -> row.get(columnName))
            .filter(value -> value != null && !value.isEmpty())
            .collect(Collectors.toList());
        
        long distinctCount = values.stream().distinct().count();
        long nullCount = data.size() - values.size();
        
        DataAnalysisResultDTO result = new DataAnalysisResultDTO();
        result.setColumnName(columnName);
        result.setDataType("CATEGORICAL");
        result.setDistinctCount((int)distinctCount);
        result.setNullCount((int)nullCount);
        
        return result;
    }
}