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

document.getElementById('download-pdf').addEventListener('click', function () {
    // Ambil elemen yang ingin didownload
    const elements = document.querySelectorAll('.activity, .graph, .contentBody');
    const container = document.createElement('div');

    // Atur container di luar viewport agar tidak terlihat
    container.style.position = 'absolute';
    container.style.top = '-10000px';
    container.style.left = '-10000px';
    document.body.appendChild(container);

    // Gabungkan elemen ke dalam container
    elements.forEach(el => {
        const clonedElement = el.cloneNode(true); // Salin elemen
        container.appendChild(clonedElement);
    });

    // Gunakan html2canvas untuk menangkap elemen gabungan
    html2canvas(container, {
        scale: 2, // Resolusi tinggi
        backgroundColor: null
    }).then((canvas) => {
        // Konversi canvas menjadi gambar PNG
        const imgData = canvas.toDataURL('image/png');

        // Buat dokumen PDF tanpa menyesuaikan ukuran A4
        const pdf = new jspdf.jsPDF({
            unit: 'px',  // Menggunakan piksel sebagai satuan
            format: [canvas.width, canvas.height] // Sesuaikan dengan ukuran gambar
        });

        // Menambahkan gambar PNG ke PDF dengan ukuran asli
        pdf.addImage(imgData, 'PNG', 0, 0, canvas.width, canvas.height);

        // Simpan PDF
        pdf.save('Progress_Report.pdf');

        // Hapus container sementara dari DOM
        document.body.removeChild(container);
    });
});