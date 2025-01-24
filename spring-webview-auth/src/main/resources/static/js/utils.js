// --------------------------------------------------------------------------------------------------------------------------------------------------------
// config
var optionsDateChart = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};
var numbIntervalLoad = 500;
var numbIntervalLoadData = 2000;
var apiPath = "";


// --------------------------------------------------------------------------------------------------------------------------------------------------------
//  date
function getStartDay() {
    var start = new Date();
    start.setHours(0, 0, 0, 0);
    return start;
}

function getEndDay() {
    var end = new Date();
    end.setHours(23, 59, 59, 999);
    return end;
}

function convertDateToString(date) {
    var string = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate()
        + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()
    return string;
}


function convertDateToStringInputFilter(date) {
    var string = date.getFullYear() + "-";
    if (date.getMonth() + 1 < 10) {
        string += '0' + (date.getMonth() + 1);
    } else {
        string += (date.getMonth() + 1);
    }

    if (date.getDate() < 10) {
        string += "-0" + date.getDate();
    } else {
        string += "-" + date.getDate();
    }

    return string;
}

function convertStringToDate(stringdate) {

    var string = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate()
        + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()

    return string;
}


// --------------------------------------------------------------------------------------------------------------------------------------------------------
// table

function appendTableContent(table) {
    appendTableHead(table);
    appendTableBodyAuto(table, null, null);
}

function appendTableHead(table) {
    table.find('thead').remove();
    table.append('<thead ></thead>');
    table.find('thead').append(createTableThDefault());

    table.find('tbody').remove();
    table.append('<tbody></tbody>');
}

function appendTableHeadAuto(table, transactionList) {
    table.find('thead').remove();
    table.append('<thead ></thead>');
    table.find('thead').append(createTableThAuto(transactionList));
}


function appendTableBodyAuto(table, list, state) {
    if (table.find('tbody').length == 0) {
        table.append('<tbody></tbody>');
    }
    table.find('tbody').find('tr').remove();

    if (list != null && list.length > 0) {
        $.each(list, function (key, value) {
            table.find('tbody').append(createTableTrAuto(value, key));
        });
        appendTableHeadAuto(table, list);
    }
}

function appendTableBodySelectAuto(table, list, state) {
    if (table.find('tbody').length == 0) {
        table.append('<tbody></tbody>');
    }
    table.find('tbody').find('tr').remove();

    if (list != null && list.length > 0) {
        $.each(list, function (key, value) {
            table.find('tbody').append(createTableTrSelectAuto(value, key));
        });
        appendTableHeadAuto(table, list);
    }
}

function createTableThDefault() {
    var stringTr = '<tr >';

    stringTr += createTableThValue('No');
    stringTr += createTableThValue('Transaction');
    stringTr += createTableThValue('Transaction Type');
    stringTr += createTableThValue('State');

    stringTr += '</tr>';

    return stringTr;
}

function createTableThAuto(transactionList) {
    var listHeadTable = [];
    $.each(transactionList[0], function (key, value) {
        listHeadTable.push(key);
    });


    var stringTr = '<tr >';
    stringTr += '<th >';
    stringTr += '<p style=" margin: 1px ; color: black">No</p>';
    stringTr += '</th >';

    listHeadTable.forEach(elem => {
        stringTr += createTableThValue(elem);
    });
    stringTr += '</tr>';
    return stringTr;
}


function createTableThValue(value) {
    var string = '<th >';
    string += '<p style=" margin: 1px ; color: black">' + value + '</p>';
    string += '</th >';
    return string;
}

function createTableTrAuto(transaction, number) {
    var stringTr = '';
    if (number % 2 == 0) {
        stringTr = '<tr class="tr-table">';
    } else {
        stringTr = '<tr class="tr-table tr-impaire">';
    }
    stringTr += '<td >';
    stringTr += '<p style=" margin: 1px ; color: black">' + number + '</p>';
    stringTr += '</td >';

    $.each(transaction, function (key, value) {
        stringTr += createTableTdValue(key, value);
    });
    stringTr += '</tr>';
    return stringTr;
}

function createTableTrSelectAuto(transaction, number) {
    var stringTr = '';
    if (number % 2 == 0) {
        stringTr = '<tr class="tr-table tr-table-pointer" onclick="selectTable(this)" >';
    } else {
        stringTr = '<tr class="tr-table tr-impaire tr-table-pointer" onclick="selectTable(this)">';
    }
    stringTr += '<td >';
    stringTr += '<p style=" margin: 1px ; color: black">' + number + '</p>';
    stringTr += '</td >';

    $.each(transaction, function (key, value) {
        stringTr += createTableTdValue(key, value);
    });
    stringTr += '</tr>';
    return stringTr;
}

function createTableTdValue(key, value) {
    var string = '<td >';
    string += '<p style=" margin: 1px ; color: black">' + value + '</p>';
    string += '</td >';
    return string;
}

function createTableTdValueClass(className, key, value) {
    var string = '<td >';
    string += '<p class="' + className + '" style=" margin: 1px ; color: black">' + value + '</p>';
    string += '</td >';
    return string;
}

// var listUnique = listData.filter(onlyUnique);
function onlyUnique(value, index, array) {
    return array.indexOf(value) === index;
}


function containsObject(obj, list) {
    var i;
    for (i = 0; i < list.length; i++) {
        if (list[i] === obj) {
            return true;
        }
    }

    return false;
}

// --------------------------------------------------------------------------------------------------------------------------------------------------------

function popitup(url, windowName) {
    newwindow = window.open(url, windowName, 'height=1000,width=1200');
    if (window.focus) {
        newwindow.focus()
    }
    return false;
}

// --------------------------------------------------------------------------------------------------------------------------------------------------------
function menuClick(menu) {
    location.href = menu.value;
}

// --------------------------------------------------------------------------------------------------------------------------------------------------------
setInterval(function () {
    drawDra();
}, 1000);

var isDrawDra = true;

function drawDra() {
    var canvas = document.getElementById('vietnam-flag');
    if (canvas != null && canvas.getContext) {
        var ctx = canvas.getContext('2d');
        ctx.fillStyle = "rgb(255,0,0)";
        ctx.fillRect(0, 0, 100, 60);
        if (isDrawDra) {
            ctx.beginPath();
            ctx.fillStyle = "yellow";
            ctx.moveTo(50, 16);
            ctx.lineTo(40, 40);
            ctx.lineTo(64, 25);
            ctx.lineTo(36, 25);
            ctx.lineTo(60, 40);
            ctx.lineTo(50, 16);
            ctx.closePath();
            ctx.fill();
        }
        isDrawDra = !isDrawDra;
    }
}

function drawlogo() {
    const canvas = document.getElementById("canvas-web-logo");
    const ctx = canvas.getContext("2d");
    const img = document.getElementById("img-web-logo");
    ctx.drawImage(img, 0, 0, 100, 100);
}

function saveTokenSession() {
    // window.sessionStorage.setItem('token', )

}

// --------------------------------------------------------------------------------------------------------------------------------------------------------
