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
