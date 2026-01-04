package com.etl.system.service.impl;

import com.etl.system.dto.DataAnalysisResultDTO;
import com.etl.system.dto.VisualizationDTO;
import com.etl.system.service.VisualizationService;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VisualizationServiceImpl implements VisualizationService {

    @Override
    public VisualizationDTO generateVisualizations(List<DataAnalysisResultDTO> analysisResults, 
                                                 List<Map<String, String>> rawData) {
        VisualizationDTO visualizationDTO = new VisualizationDTO();
        visualizationDTO.setChartData(generateChartData(analysisResults, rawData));
        return visualizationDTO;
    }

    private Map<String, Map<String, Object>> generateChartData(List<DataAnalysisResultDTO> results, 
                                                             List<Map<String, String>> rawData) {
        Map<String, Map<String, Object>> chartData = new LinkedHashMap<>();
        
        for (DataAnalysisResultDTO result : results) {
            if ("NUMERIC".equals(result.getDataType())) {
                Map<String, Object> columnCharts = new HashMap<>();
                columnCharts.put("histogram", prepareHistogramData(rawData, result.getColumnName()));
                columnCharts.put("boxplot", prepareBoxPlotData(rawData, result.getColumnName()));
                chartData.put(result.getColumnName(), columnCharts);
            }
        }
        
        return chartData;
    }

    private Map<String, Object> prepareHistogramData(List<Map<String, String>> rawData, String columnName) {
        List<Double> values = extractNumericValues(rawData, columnName);
        if (values.isEmpty()) {
            return Collections.emptyMap();
        }

        Collections.sort(values);
        final double min = values.get(0);
        final double max = values.get(values.size() - 1);
        final int binCount = Math.min(10, values.size());
        final double binSize = (max - min) / binCount;
        
        Map<String, Object> histogramData = new LinkedHashMap<>();
        List<String> labels = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        for (int i = 0; i < binCount; i++) {
            final double lowerBound = min + i * binSize;
            final double upperBound = lowerBound + binSize;
            final boolean isLastBin = (i == binCount - 1);
            
            long count = values.stream()
                .filter(v -> isLastBin ? 
                    (v >= lowerBound && v <= upperBound) : 
                    (v >= lowerBound && v < upperBound))
                .count();
            
            labels.add(String.format("%.2f-%.2f", lowerBound, upperBound));
            counts.add(count);
        }
        
        histogramData.put("labels", labels);
        histogramData.put("values", counts);
        return histogramData;
    }

    private Map<String, Object> prepareBoxPlotData(List<Map<String, String>> rawData, String columnName) {
        List<Double> values = extractNumericValues(rawData, columnName);
        if (values.isEmpty()) {
            return Collections.emptyMap();
        }
        
        DescriptiveStatistics stats = new DescriptiveStatistics();
        values.forEach(stats::addValue);
        
        Map<String, Object> boxPlotData = new LinkedHashMap<>();
        boxPlotData.put("min", stats.getMin());
        boxPlotData.put("q1", stats.getPercentile(25));
        boxPlotData.put("median", stats.getPercentile(50));
        boxPlotData.put("q3", stats.getPercentile(75));
        boxPlotData.put("max", stats.getMax());
        boxPlotData.put("outliers", identifyOutliers(values));
        
        return boxPlotData;
    }

    private List<Double> extractNumericValues(List<Map<String, String>> rawData, String columnName) {
        return rawData.stream()
            .map(row -> row.get(columnName))
            .filter(Objects::nonNull)
            .map(this::tryParseDouble)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private List<Double> identifyOutliers(List<Double> values) {
        if (values.size() < 4) {
            return Collections.emptyList();
        }
        
        DescriptiveStatistics stats = new DescriptiveStatistics();
        values.forEach(stats::addValue);
        
        double q1 = stats.getPercentile(25);
        double q3 = stats.getPercentile(75);
        double iqr = q3 - q1;
        double lowerBound = q1 - 1.5 * iqr;
        double upperBound = q3 + 1.5 * iqr;
        
        return values.stream()
            .filter(v -> v < lowerBound || v > upperBound)
            .collect(Collectors.toList());
    }
}