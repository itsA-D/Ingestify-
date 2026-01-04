package com.etl.system.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "processed_data")
public class ProcessedData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String sourceFileName;
    private String columnName;
    private BigDecimal meanValue;
    private BigDecimal medianValue;
    private BigDecimal skewness;
    private BigDecimal kurtosis;
    private BigDecimal standardDeviation;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedDate;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSourceFileName() { return sourceFileName; }
    public void setSourceFileName(String sourceFileName) { this.sourceFileName = sourceFileName; }
    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }
    public BigDecimal getMeanValue() { return meanValue; }
    public void setMeanValue(BigDecimal meanValue) { this.meanValue = meanValue; }
    public BigDecimal getMedianValue() { return medianValue; }
    public void setMedianValue(BigDecimal medianValue) { this.medianValue = medianValue; }
    public BigDecimal getSkewness() { return skewness; }
    public void setSkewness(BigDecimal skewness) { this.skewness = skewness; }
    public BigDecimal getKurtosis() { return kurtosis; }
    public void setKurtosis(BigDecimal kurtosis) { this.kurtosis = kurtosis; }
    public BigDecimal getStandardDeviation() { return standardDeviation; }
    public void setStandardDeviation(BigDecimal standardDeviation) { this.standardDeviation = standardDeviation; }
    public Date getProcessedDate() { return processedDate; }
    public void setProcessedDate(Date processedDate) { this.processedDate = processedDate; }
}