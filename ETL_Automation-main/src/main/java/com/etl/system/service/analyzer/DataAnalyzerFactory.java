package com.etl.system.service.analyzer;

public class DataAnalyzerFactory {
    
    public DataAnalyzer createAnalyzer(String columnName) {
        // In a real implementation, we would determine the type based on data
        // For simplicity, we'll assume all columns are numeric
        return new NumericDataAnalyzer();
    }
}