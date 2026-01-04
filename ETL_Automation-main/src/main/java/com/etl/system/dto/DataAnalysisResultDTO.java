package com.etl.system.dto;

import java.math.BigDecimal;

public class DataAnalysisResultDTO {
    private String columnName;
    private String dataType;
    private BigDecimal mean;
    private BigDecimal median;
    private BigDecimal skewness;
    private BigDecimal kurtosis;
    private BigDecimal standardDeviation;
    private Integer distinctCount;
    private Integer nullCount;

    // Getters and Setters
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public BigDecimal getMean() {
        return mean;
    }

    public void setMean(BigDecimal mean) {
        this.mean = mean;
    }

    public BigDecimal getMedian() {
        return median;
    }

    public void setMedian(BigDecimal median) {
        this.median = median;
    }

    public BigDecimal getSkewness() {
        return skewness;
    }

    public void setSkewness(BigDecimal skewness) {
        this.skewness = skewness;
    }

    public BigDecimal getKurtosis() {
        return kurtosis;
    }

    public void setKurtosis(BigDecimal kurtosis) {
        this.kurtosis = kurtosis;
    }

    public BigDecimal getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(BigDecimal standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public Integer getDistinctCount() {
        return distinctCount;
    }

    public void setDistinctCount(Integer distinctCount) {
        this.distinctCount = distinctCount;
    }

    public Integer getNullCount() {
        return nullCount;
    }

    public void setNullCount(Integer nullCount) {
        this.nullCount = nullCount;
    }
}