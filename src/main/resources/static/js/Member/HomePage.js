$(document).ready(function () {
    $.getJSON('/getGraph1', function (response) {
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

// Fungsi untuk mengunduh PDF
document.getElementById('downloadPdf').addEventListener('click', function () {
    const element = document.getElementById('container');
    const elementWidth = element.offsetWidth;
    const elementHeight = element.offsetHeight;

    // Hitung ukuran PDF berdasarkan dimensi elemen (dalam mm)
    const pdfWidth = elementWidth * 0.264583; // Konversi px ke mm
    const pdfHeight = elementHeight * 0.264583; // Konversi px ke mm

    html2canvas(element).then(canvas => {
        const { jsPDF } = window.jspdf;
        const pdf = new jsPDF({
            orientation: pdfWidth > pdfHeight ? 'landscape' : 'portrait',
            unit: 'mm',
            format: [pdfWidth, pdfHeight], // Ukuran PDF sesuai elemen
        });

        pdf.addImage(canvas.toDataURL(), 'PNG', 0, 0, pdfWidth, pdfHeight);
        pdf.save('chart.pdf');
    }).catch(error => {
        console.error('Error during PDF download:', error);
    });
});