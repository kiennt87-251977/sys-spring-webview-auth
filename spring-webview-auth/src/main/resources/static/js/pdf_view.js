// --------------------------------------------------------------------------------------------------------------------------------------------------------


$(document).ready(function () {

    const fileUpload = document.querySelector("#input-file-form");

    fileUpload.addEventListener('change', function () {
        var pdfFile = document.querySelector('#embed-id');
        var file = this.files[0];
        let reader = new FileReader();
        reader.onload = function (e) {
            pdfFile.src = reader.result;
        }
        reader.readAsDataURL(file);
    });
});