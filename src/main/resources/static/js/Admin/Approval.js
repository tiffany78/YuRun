function resetButtons(parentNode) {
    // Hapus semua kelas "approve" dan "reject"
    const buttons = parentNode.querySelectorAll('.button2');
    buttons.forEach(button => {
        button.classList.remove('approve', 'reject');
    });
}

function setApprove(element) {
    resetButtons(element.parentNode);

    // Tambahkan kelas "approve"
    element.classList.add('approve');

    // Set hidden field status to "true"
    const statusField = element.closest('.detailText').querySelector('.statusField');
    statusField.value = 'true';
}

function setReject(element) {
    resetButtons(element.parentNode);

    // Tambahkan kelas "reject"
    element.classList.add('reject');

    // Set hidden field status to "false"
    const statusField = element.closest('.detailText').querySelector('.statusField');
    statusField.value = 'false';
}

// Fungsi untuk menampilkan pop-up
function showPopup(event) {
    event.preventDefault(); // Mencegah form langsung dikirimkan
    const popup = document.getElementById('popup');
    popup.classList.remove('hidden');
}

// Fungsi untuk menutup pop-up
function closePopup() {
    const popup = document.getElementById('popup');
    popup.classList.add('hidden');
}

// Fungsi untuk mengonfirmasi "Yes" dan submit form
function confirmSubmit() {
    const form = document.querySelector('form.body'); // Ambil form
    form.submit(); // Kirim form
}