package com.etl.system.service.analyzer;

import com.etl.system.dto.DataAnalysisResultDTO;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class NumericDataAnalyzer implements DataAnalyzer {
    
    @Override
    public DataAnalysisResultDTO analyze(List<Map<String, String>> data, String columnName) {
        List<Double> values = data.stream()
            .map(row -> row.get(columnName))
            .filter(Objects::nonNull)
            .map(this::tryParseDouble)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        
        if (values.isEmpty()) {
            return createEmptyResult(columnName);
        }
        
        return analyzeNumericValues(values, columnName);
    }
    
    private Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private DataAnalysisResultDTO analyzeNumericValues(List<Double> values, String columnName) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        values.forEach(stats::addValue);
        
        DataAnalysisResultDTO result = new DataAnalysisResultDTO();
        result.setColumnName(columnName);
        result.setDataType("NUMERIC");
        result.setMean(BigDecimal.valueOf(stats.getMean()));
        result.setMedian(BigDecimal.valueOf(stats.getPercentile(50)));
        result.setSkewness(BigDecimal.valueOf(stats.getSkewness()));
        result.setKurtosis(BigDecimal.valueOf(stats.getKurtosis()));
        result.setStandardDeviation(BigDecimal.valueOf(stats.getStandardDeviation()));
        
        return result;
    }
    
    private DataAnalysisResultDTO createEmptyResult(String columnName) {
        DataAnalysisResultDTO result = new DataAnalysisResultDTO();
        result.setColumnName(columnName);
        result.setDataType("NON_NUMERIC");
        return result;
    }
}