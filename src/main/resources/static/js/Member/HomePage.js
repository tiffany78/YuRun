$(document).ready(function () {
    // Ambil data dari endpoint Spring Boot
    $.getJSON('/getGraph1', function (response) {
        // Inisialisasi grafik setelah data diterima
        Highcharts.chart('container', {
            chart: {
                type: 'areaspline',
                backgroundColor: null
            },
            title: {
                text: 'Activities During This Month'
            },
            xAxis: {
                categories: response.categories,
                crosshair: true,
                tickmarkPlacement: 'on'
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Distance (km)' 
                }
            },
            legend: {
                enabled: false // Menonaktifkan legenda
            },
            series: [{
                name: 'Distance',
                data: response.data, // Data dari backend
                color: 'rgba(255, 111, 55, 0.5)'
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