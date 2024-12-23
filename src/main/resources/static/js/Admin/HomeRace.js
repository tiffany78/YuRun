function showPopup(idRace) {
    const popup = document.getElementById('popup');
    popup.classList.remove('hidden');

    const confirmClose = document.getElementById('confirmClose');
    confirmClose.href = '/admin/race/close/' + idRace;
}

function closePopup() {
    const popup = document.getElementById('popup');
    popup.classList.add('hidden');
}