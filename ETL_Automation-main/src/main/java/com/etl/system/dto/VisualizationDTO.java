package com.etl.system.dto;

import java.util.Map;

public class VisualizationDTO {
    private Map<String, Map<String, Object>> chartData;

    public Map<String, Map<String, Object>> getChartData() {
        return chartData;
    }

    public void setChartData(Map<String, Map<String, Object>> chartData) {
        this.chartData = chartData;
    }
}