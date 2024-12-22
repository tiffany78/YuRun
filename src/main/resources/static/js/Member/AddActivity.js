$(document).ready(function() {
    $('#fileImage').on('change', function() {
        showImageThumbnail(this);
    });
});

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];

    if (!file || !file.type.match('image.*')) {
        alert("Please select a valid image file.");
        return;
    }

    var reader = new FileReader();
    reader.onload = function(e) {
        $('#thumbnail').attr('src', e.target.result).show(); // Tampilkan gambar
    };

    reader.readAsDataURL(file);
}