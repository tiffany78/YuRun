$(document).ready(function () {
    // Ambil parameter filterType, startDate, dan endDate dari URL
    const params = new URLSearchParams(window.location.search);
    const filterType = params.get('filterType') || 'All';
    const startDate = params.get('startDate');
    const endDate = params.get('endDate');

    // Buat URL untuk request ke backend
    const url = `/getGraphProgress?filterType=${filterType}${startDate ? `&startDate=${startDate}` : ''}${endDate ? `&endDate=${endDate}` : ''}`;

    // Ambil data dari endpoint Spring Boot
    $.getJSON(url, function (response) {
        // Inisialisasi grafik setelah data diterima
        Highcharts.chart('container', {
            chart: {
                type: 'areaspline',
                backgroundColor: null
            },
            title: {
                text: null
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
                name: 'Distance (km)',
                data: response.data, // Data dari backend
                color: 'rgba(255, 111, 55, 0.5)'
            }],
            exporting: {
                enabled: false
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    // Ambil parameter dari URL
    const params = new URLSearchParams(window.location.search);
    const activeFilter = params.get('filterType') || 'All'; // Default ke "All"
    const startDate = params.get('startDate'); // Cek apakah startDate ada
    const endDate = params.get('endDate'); // Cek apakah endDate ada

    // Cari tombol yang sesuai dan tambahkan kelas "active"
    const buttons = document.querySelectorAll('.filter-button');
    buttons.forEach(button => {
        // Jika filter "Custom" aktif (startDate dan endDate ada), aktifkan tombol Custom
        if (startDate && endDate && button.textContent.trim() === 'Custom') {
            button.classList.add('active');
        } 
        // Jika tombol sesuai dengan activeFilter dan bukan Custom, aktifkan tombol tersebut
        else if (!startDate && !endDate && button.getAttribute('data-filter') === activeFilter) {
            button.classList.add('active');
        } 
        // Jika tidak memenuhi salah satu kondisi di atas, pastikan tombol tidak aktif
        else {
            button.classList.remove('active');
        }
    });
});