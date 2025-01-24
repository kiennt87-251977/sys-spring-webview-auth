//--------------------------------------------------------------------------------------------------------------------------------------------------------
var user = {
    uname: "",
    psw: "",
    nom: "",
    prenom: "",
};

var key_token = 'token_web';
var token_web = {
    token: "",
    user: "",
    expireTime: "",
    loginStatus: false,
}

var numbIntervalToken = 2000;


async function callLoginExt(apiPath, uname, psw) {
    var url = apiPath + "/call-login";
    var dataInput = 'login-user';

    var token = uname + ":" + psw;

    const config = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/text',
            "Authorization": "Basic " + Base64.encode(token)
        },

        body: dataInput
    };
    const response = await
        fetch(url, config);
    const json = await
        response.json();

    if (json != null && json != "") {
        var responseJson = json;
        if (responseJson.token != null) {
            return responseJson;
        }
    }
};

// --------------------------------------------------------------------------------------------------------------------------------------------------------

async function callLogoutExt(apiPath, uname, psw) {
    var url = apiPath + "/call-logout";
    var dataInput = 'logout-user';

    var token = uname + ":" + psw;

    const config = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/text',
            "Authorization": "Basic " + Base64.encode(token)
        },

        body: dataInput
    };
    const response = await
        fetch(url, config);
    const json = await
        response.json();

    if (json != null && json != "") {
        var responseJson = json;
        if (responseJson.status != null && responseJson.status == 'Success') {
            return responseJson;
        }
    }

};

// --------------------------------------------------------------------------------------------------------------------------------------------------------

function callLogin() {
    callLoginExt(apiPath, user.uname, user.psw).then(
        function (value) {
            if (value != null) {

                user.nom = value.nom;
                user.prenom = value.prenom;

                token_web.token = value.token;
                token_web.user = user;
                token_web.loginStatus = true;
                token_web.expireTime = value.expireTime;

                window.localStorage.setItem(key_token, JSON.stringify(token_web));

                onOpenModalSuccess(null);
                onCloseModalFail(null);

                createHtmlModalDetail();
                switchBtnLogin("detail");
            } else {
                onOpenModalFail(null);
                onCloseModalSuccess(null);

                switchBtnLogin("login");
            }
        },
        function (error) {
            alert(error);
        }
    );
}

function callRefeshToken() {
    callLoginExt(apiPath, user.uname, user.psw).then(
        function (value) {
            if (value != null) {

                user.nom = value.nom;
                user.prenom = value.prenom;

                token_web.token = value.token;
                token_web.user = user;
                token_web.loginStatus = true;
                token_web.expireTime = value.expireTime;

                window.localStorage.setItem(key_token, JSON.stringify(token_web));

                switchBtnLogin("detail");
            } else {
                switchBtnLogin("login");
            }
        },
        function (error) {
            alert(error);
        }
    );
}


function callLogout() {

    callLogoutExt(apiPath, user.uname, user.psw).then(
        function (value) {
            if (value != null) {
                clearToken();

                onOpenModalLogoutSuccess(null);
                onCloseModalFail(null);

                switchBtnLogin("login");
            } else {
                onOpenModalFail(null);
                onCloseModalLogoutSuccess(null);

                switchBtnLogin("detail");
            }
        },
        function (error) {
            alert(error);
        }
    );
}

function clearToken() {
    window.localStorage.removeItem(key_token);
    user.nom = "";
    user.prenom = "";

    token_web.loginStatus = false;

    switchBtnLogin("login");

}


// --------------------------------------------------------------------------------------------------------------------------------------------------------


function onSubmitLogin(btn) {
    $("#modal-login").find('.info').find('input').each(function (index) {
        if ($(this).attr('name') == 'uname') {
            user.uname = $(this).val();
        }
        if ($(this).attr('name') == 'psw') {
            user.psw = $(this).val();
        }
    });

    callLogin();

    onCloseLogin(btn)
}

function onSubmitLogout(btn) {
    callLogout();
    onCloseDetail(btn)
}


function onCloseLogin(btn) {
    $("#modal-login").removeClass("open-win");
    $("#modal-login").addClass("close-win");
}

function onOpenLogin(btn) {
    $("#modal-login").removeClass("close-win");
    $("#modal-login").addClass("open-win");
}


function onCloseDetail(btn) {
    $("#modal-detail").removeClass("open-win");
    $("#modal-detail").addClass("close-win");
}

function onOpenDetail(btn) {
    $("#modal-detail").removeClass("close-win");
    $("#modal-detail").addClass("open-win");
}

function onCloseHelp(btn) {
    $("#modal-help").removeClass("open-win");
    $("#modal-help").addClass("close-win");
}

function onOpenHelp(btn) {
    $("#modal-help").removeClass("close-win");
    $("#modal-help").addClass("open-win");
}


function onCloseModalSuccess(btn) {
    $("#modal-success").removeClass("open-win");
    $("#modal-success").addClass("close-win");
}

function onOpenModalSuccess(btn) {
    $("#modal-success").removeClass("close-win");
    $("#modal-success").addClass("open-win");
}

function onCloseModalLogoutSuccess(btn) {
    $("#modal-logout-success").removeClass("open-win");
    $("#modal-logout-success").addClass("close-win");
}

function onOpenModalLogoutSuccess(btn) {
    $("#modal-logout-success").removeClass("close-win");
    $("#modal-logout-success").addClass("open-win");
}

function onCloseModalFail(btn) {
    $("#modal-fail").removeClass("open-win");
    $("#modal-fail").addClass("close-win");
}

function onOpenModalFail(btn) {
    $("#modal-fail").removeClass("close-win");
    $("#modal-fail").addClass("open-win");
}


function createHtmlLogin() {
    var stringHtml = '<button id="btn-help" type="button" onclick="onOpenHelp(this)"\n' +
        '                    class="btn button-help"\n' +
        '                    style="width:auto; float: right;">\n' +
        '                Help\n' +
        '                <i class="fa fa-question-circle-o"></i>\n' +
        '            </button>\n' +
        '\n' +
        '            <button id="btn-login" onclick="onOpenLogin(this)"\n' +
        '                    class="btn button-login"\n' +
        '                    style="width:auto; float: right;">\n' +
        '                Login\n' +
        '            </button>\n' +
        '\n' +
        '            <button id="btn-detail" onclick="onOpenDetail(this)"\n' +
        '                    class="btn button-login"\n' +
        '                    style="width:auto; float: right; display: none;">\n' +
        '                Detail\n' +
        '            </button>';

    $("#content-btn-login").append(stringHtml);
}

function switchBtnLogin(btnName) {
    if (btnName == 'login') {
        document.getElementById('btn-login').style.display = 'block';
        document.getElementById('btn-detail').style.display = 'none';
    } else {
        document.getElementById('btn-login').style.display = 'none';
        document.getElementById('btn-detail').style.display = 'block';
    }

}

function createHtmlModalLogin() {
    var stringHtml = '<form class="modal-content animate" action="">\n' +
        '            <div class="imgcontainer">\n' +
        '                <span onclick="onCloseLogin(this)" class="close"\n' +
        '                      title="Close Modal">&times;</span>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="container info">\n' +
        '                <label for="uname"><b>Username</b></label>\n' +
        '                <input type="text" placeholder="Enter Username" name="uname" required>\n' +
        '\n' +
        '                <label for="psw"><b>Password</b></label>\n' +
        '                <input type="password" placeholder="Enter Password" name="psw" required>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="container" style="text-align: center">\n' +
        '                <button type="button" onclick="onSubmitLogin(this)" class="button-login">Login</button>\n' +
        '\n' +
        '                <button type="button" onclick="onCloseLogin(this)"\n' +
        '                        class="button-cancel">\n' +
        '                    Cancel\n' +
        '                </button>\n' +
        '            </div>\n' +
        '        </form>';

    $("#modal-login").append(stringHtml);
}

function createHtmlModalDetail() {
    var stringHtml = '<form class="modal-content animate" action="">\n' +
        '            <div class="imgcontainer">\n' +
        '                <span onclick="onCloseDetail(this)" class="close"\n' +
        '                      title="Close Modal">&times;</span>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="container info">\n' +
        '                <div style="display: flex; padding: 1%"><p>Username : </p>\n' +
        '                    <p>' + user.uname + '</p></div>\n' +
        '                <div style="display: flex; padding: 1%"><p>Nom : </p>\n' +
        '                    <p>' + user.nom + '</p></div>\n' +
        '                <div style="display: flex; padding: 1%"><p>Prenom : </p>\n' +
        '                    <p>' + user.prenom + '</p></div>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="container" style="text-align: center">\n' +
        '                <button type="button" onclick="onSubmitLogout(this)" class="button-login">logout</button>\n' +
        '            </div>\n' +
        '\n' +
        '        </form>';

    $("#modal-detail").empty();
    $("#modal-detail").append(stringHtml);
}


function createHtmlModalSuccess() {
    var stringHtml = '<form class="modal-content animate" action="">\n' +
        '            <div class="imgcontainer">\n' +
        '                <span onclick="onCloseModalSuccess(this)" class="close"\n' +
        '                      title="Close Modal">&times;</span>\n' +
        '            </div>\n' +
        '            <div class="container info">\n' +
        '                <label><b>Login success</b></label>\n' +
        '            </div>\n' +
        '        </form>';

    $("#modal-success").append(stringHtml);
}

function createHtmlModalLogoutSuccess() {
    var stringHtml = '<form class="modal-content animate" action="">\n' +
        '            <div class="imgcontainer">\n' +
        '                <span onclick="onCloseModalLogoutSuccess(this)" class="close"\n' +
        '                      title="Close Modal">&times;</span>\n' +
        '            </div>\n' +
        '            <div class="container info">\n' +
        '                <label><b>Logout success</b></label>\n' +
        '            </div>\n' +
        '        </form>';

    $("#modal-logout-success").append(stringHtml);
}

function createHtmlModalFail() {
    var stringHtml = '<form class="modal-content animate" action="">\n' +
        '            <div class="imgcontainer">\n' +
        '                <span onclick="onCloseModalFail(this)" class="close"\n' +
        '                      title="Close Modal">&times;</span>\n' +
        '            </div>\n' +
        '            <div class="container info">\n' +
        '                <label><b>Login fail</b></label>\n' +
        '                <label><b>User name or password is wrong !!!</b></label>\n' +
        '            </div>\n' +
        '        </form>';

    $("#modal-fail").append(stringHtml);
}

function createHtmlModalHelp() {
    var stringHtml = '<form class="modal-content animate" action="">\n' +
        '            <div class="imgcontainer">\n' +
        '                <span onclick="onCloseHelp(this)" class="close"\n' +
        '                      title="Close Modal">&times;</span>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="container info">\n' +
        '                <p><b>Exemple : </b></p>\n' +
        '                <p><b>Login to access get data from backend </b></p>\n' +
        '                <p><b>User = \"admin\" </b></p>\n' +
        '                <p><b>Pass = \"admin_123a@\"</b></p>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="container" style="text-align: center">\n' +
        '                <button type="button" onclick="onCloseHelp(this)"\n' +
        '                        class="button-cancel">\n' +
        '                    Cancel\n' +
        '                </button>\n' +
        '            </div>\n' +
        '        </form>';


    User = "admin";
    Pass = "admin_123a@";


    $("#modal-help").append(stringHtml);
}


function getTokenWeb() {
    if (window.localStorage.getItem(key_token) != null
    ) {
        token_web = JSON.parse(window.localStorage.getItem(key_token));
        if (token_web != null && token_web.loginStatus) {
            user = token_web.user;
            switchBtnLogin("detail");
        }
    } else {
        clearToken()
    }
    return token_web;
}


function clearTokenError(response) {
    if (response = 'ERROR : 401') {
        clearToken();
    }
}


// --------------------------------------------------------------------------------------------------------------------------------------------------------

$(document).ready(function () {
    apiPath = $(".path-api").html();
    createHtmlLogin();
    switchBtnLogin("login");

    getTokenWeb();

    createHtmlModalLogin();
    createHtmlModalDetail();
    createHtmlModalHelp();
    createHtmlModalSuccess();
    createHtmlModalFail();
    createHtmlModalLogoutSuccess()


// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == document.getElementById('modal-login')) {
            onCloseLogin(null);
        }
        if (event.target == document.getElementById('modal-detail')) {
            onCloseDetail(null);
        }
        if (event.target == document.getElementById('modal-help')) {
            onCloseHelp(null)
        }
        if (event.target == document.getElementById('modal-success')) {
            onCloseModalSuccess(null);
        }

        if (event.target == document.getElementById('modal-logout-success')) {
            onCloseModalLogoutSuccess(null);
        }
        if (event.target == document.getElementById('modal-fail')) {
            onCloseModalFail(null);
        }

        if (token_web != null
            && token_web.loginStatus
            && token_web.expireTime != null && new Date(token_web.expireTime) > new Date()
            && event.target.innerHTML != 'logout'
        ) {
            callRefeshToken();
        }
    }
});


setInterval(function () {
    if (token_web != null && token_web.expireTime != null && new Date(token_web.expireTime) < new Date()) {
        clearToken();
    }

}, numbIntervalToken);


