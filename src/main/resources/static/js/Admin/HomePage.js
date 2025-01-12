$(document).ready(function () {
    $.getJSON('/admin/getGraph1Admin', function(response) {
        // Inisialisasi grafik setelah data diterima
        Highcharts.chart('container', {
            chart: {
                type: 'column',
                backgroundColor: null
            },
            title: {
                text: 'Race Participant'
            },
            xAxis: {
                categories: response.categories, // Kategori dari backend
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Count'
                }
            },
            legend: {
                enabled: false // Menonaktifkan legenda
            },
            series: [{
                name: 'Participants',
                data: response.data, // Data dari backend
                color: '#ff6f37'
            }],
            exporting: {
                enabled: false // Menonaktifkan opsi ekspor
            }
        });
    });

    $.getJSON('/admin/getGraph2Admin', function (response) {
        Highcharts.chart('container2', {
            chart: {
                type: 'line',
                width: 500,
                backgroundColor: null
            },
            title: {
                text: 'Monthly User Activities'
            },
            xAxis: {
                categories: response.categories,
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Number of Activities'
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                name: 'Activities',
                data: response.data,
                color: '#ff6f37'
            }],
            exporting: {
                enabled: false
            }
        });
    });

    $.getJSON('/admin/getGraph3Admin', function (response) {
        Highcharts.chart('container3', {
            chart: {
                type: 'pie',
                backgroundColor: null
            },
            title: {
                text: 'Activity Type Distribution'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    }
                }
            },
            series: [{
                name: 'Activities',
                data: response.data,
                colors: ['#ff6f37', '#ffa37f', '#ffcbb3']
            }],
            exporting: {
                enabled: false
            }
        });
    });
});

document.getElementById('download').addEventListener('click', function () {
    const element = document.getElementById('container');
    html2canvas(element).then(canvas => {
        const link = document.createElement('a');
        link.download = 'chart.png';
        link.href = canvas.toDataURL();
        link.click();
    });
});

document.getElementById('download2').addEventListener('click', function () {
    const element = document.getElementById('container2');
    html2canvas(element).then(canvas => {
        const link = document.createElement('a');
        link.download = 'chart.png';
        link.href = canvas.toDataURL();
        link.click();
    });
});

document.getElementById('download3').addEventListener('click', function () {
    const element = document.getElementById('container3');
    html2canvas(element).then(canvas => {
        const link = document.createElement('a');
        link.download = 'chart.png';
        link.href = canvas.toDataURL();
        link.click();
    });
});