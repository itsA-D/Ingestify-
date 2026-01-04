// Additional chart interactions
document.addEventListener('DOMContentLoaded', function() {
    // Add hover effects to charts
    const charts = document.querySelectorAll('canvas');
    charts.forEach(chart => {
        chart.addEventListener('mouseenter', function() {
            this.style.cursor = 'pointer';
        });
    });
    
    // Add export functionality
    const exportButtons = document.querySelectorAll('.export-btn');
    exportButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            const chartId = this.dataset.chartId;
            const canvas = document.getElementById(chartId);
            const url = canvas.toDataURL('image/png');
            const link = document.createElement('a');
            link.download = chartId + '.png';
            link.href = url;
            link.click();
        });
    });
});