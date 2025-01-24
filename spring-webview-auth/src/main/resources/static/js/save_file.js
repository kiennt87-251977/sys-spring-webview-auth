function callSaveFile(files) {
    var url = apiPath + "/upload-file";
    var dataInput = 'save-file';

    var token = null;
    var token_web = getTokenWeb();
    if (token_web != null && token_web.loginStatus) {
        token = token_web.token;
    }

    var formData = new FormData();
    formData.append("filename", files);

    // for (const file of files) {
    //     formData.append("files", file);
    // }


    $.ajax({
        url: url,
        type: "POST",
        data: formData,
        contentType: false,
        headers: {
            "Authorization": "Bearer " + token
        },
        cache: false,
        processData: false,
        success: function (data) {
            // data.json();
            // if (json != null && json != "") {
            //     var responseJson = json;
            //     if (responseJson.status != null && responseJson.status == 'Success') {
            //         return responseJson;
            //     }
            // }
            // return (data);
            $('#modal-save-file').find('.modal-message').html(data)
            onOpenModalSaveFile(null);
            clearFormFile();

        },
        error: function (error) {
            alert('Status code : ' + error.status);
        }
    });
};

function clearFormFile() {
    var $el = $('#input-file-form');
    $el.wrap('<form>').closest('form').get(0).reset();
    $el.unwrap();
    $el.css("background-color", "coral");
}

function createHtmlModalSaveFileSuccess() {
    var stringHtml = '<form class="modal-content animate" action="">\n' +
        '            <div class="imgcontainer">\n' +
        '                <span onclick="onCloseModalSaveFile(this)" class="close"\n' +
        '                      title="Close Modal">&times;</span>\n' +
        '            </div>\n' +
        '            <div class="container info" style="display: grid;">\n' +
        '                <label><b>Save Done, status : </b></label>\n' +
        '                <label class="modal-message"><b></b></label>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="container" style="text-align: center">\n' +
        '                <button type="button" onclick="onCloseModalSaveFile(this)"\n' +
        '                        class="button-cancel">\n' +
        '                    Close\n' +
        '                </button>\n' +
        '            </div>\n' +
        '        </form>';

    $("#modal-save-file").append(stringHtml);
}

function onOpenModalSaveFile(btn) {
    $("#modal-save-file").addClass("open-win");
    $("#modal-save-file").removeClass("close-win");
}

function onCloseModalSaveFile(btn) {
    $("#modal-save-file").removeClass("open-win");
    $("#modal-save-file").addClass("close-win");
}


// --------------------------------------------------------------------------------------------------------------------------------------------------------


function saveFile(btn) {
    const fileUpload = document.querySelector("#input-file-form");
    var file = fileUpload.files[0];
    callSaveFile(file);
}


// --------------------------------------------------------------------------------------------------------------------------------------------------------

$(document).ready(function () {
    createHtmlModalSaveFileSuccess();


    const fileUpload = document.querySelector("#input-file-form");

    fileUpload.addEventListener("change", (e) => {
        const files = e.target.files;
        for (const file of files) {
            const {name, type, size, lastModified} = file;
            // Làm gì đó với các thông tin trên

            console.log(file)
        }
        e.target.style.backgroundColor='#3c763d';
    })
});

