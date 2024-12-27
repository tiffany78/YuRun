$(document).ready(function () {
    // Ambil data dari endpoint Spring Boot
    $.getJSON('/getGraphProgress', function (response) {
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
    // Ambil semua tombol filter
    const filterButtons = document.querySelectorAll('.filter-button');

    // Ambil elemen dropdown dan custom input
    const dropdown = document.querySelector('.dropdown');
    const customInput = document.querySelector('.custom-input');

    // Tambahkan event listener untuk setiap tombol
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            const target = button.getAttribute('data-target');

            // Sembunyikan semua elemen terlebih dahulu
            dropdown.classList.add('hidden');
            customInput.classList.add('hidden');

            // Tampilkan elemen sesuai tombol yang diklik
            if (target === 'dropdown') {
                dropdown.classList.remove('hidden');
            } else if (target === 'custom') {
                customInput.classList.remove('hidden');
            }
        });
    });
});