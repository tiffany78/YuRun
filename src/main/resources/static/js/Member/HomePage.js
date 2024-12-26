$(document).ready(function () {
    // Ambil data dari endpoint Spring Boot
    $.getJSON('/getGraph1', function (response) {
        // Inisialisasi grafik setelah data diterima
        Highcharts.chart('container', {
            chart: {
                type: 'column',
                backgroundColor: null
            },
            title: {
                text: 'Partisipant Join The Race'
            },
            xAxis: {
                categories: response.categories, // Kategori dari backend
                crosshair: true
            },
            yAxis: {
                min: 0,
                max: 10
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