var numbIncre = 1;

var sys = {
    statusData: 0,
    statusDataDes: '',
}

var listDataTable = [];
var listDataTable = [];


// --------------------------------------------------------------------------------------------------------------------------------------------------------


function setInfoShow() {
    $(".table_content").find('.content-trans-accounting').removeClass("hidden");
}


async function callDataChartExt() {
    var url = apiPath + "/get-data-acc-table?numbRecord=" + 100
        + "&nameRecord=" + 'accUser'
    ;

    var token = null;
    var token_web = getTokenWeb();
    if (token_web != null && token_web.loginStatus) {
        token = token_web.token;
    }

    const config = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/text',
            "Authorization": "Bearer " + token
        },
        // body: dataInput
    };
    const response = await
        fetch(url, config);
    if (response != null && response.status == 200) {
        const data = await
            response.json(); //extract JSON from the http response
        return data;
    } else {
        return 'ERROR : ' + response.status;
    }
};


function callData() {
    callDataChartExt().then(
        function (value) {

            listDataTable = [];
            if (value != null) {
                if (value.includes('ERROR')) {
                    alert(value);
                    clearTokenError(value);
                } else {
                    listDataTable = value;
                }
            } else {
                alert('Data not found');
            }
        },
        function (error) {
            alert(error);
        }
    );

};

// --------------------------------------------------------------------------------------------------------------------------------------------------------

function updateDataTable(table, state) {
    if (state == 4) {
        appendTableHeadAuto(table, listDataTable);
        appendTableBodySelectAuto(table, listDataTable, state);
    }
}


// function appendTableHead4(table) {
//     table.find('thead').remove();
//     table.append('<thead ></thead>');
//     table.find('thead').append(createTableTh4());
//
//     table.find('tbody').remove();
//     table.append('<tbody></tbody>');
// }
//
// function appendTableBody4(table, transactionList, state) {
//     if (table.find('tbody').length == 0) {
//         table.append('<tbody></tbody>');
//     }
//     table.find('tbody').find('tr').remove();
//
//     if (transactionList != null && transactionList.length > 0) {
//         $.each(transactionList, function (key, value) {
//             table.find('tbody').append(createTableTr4(value, key));
//         });
//     }
// }

// function createTableTh4() {
//     var stringTr = '<tr class="trans-info-wallet">';
//
//     stringTr += createTableThValue('Step Numb');
//     stringTr += createTableThValue('From Wallet Id');
//     stringTr += createTableThValue('To Wallet Id');
//     stringTr += createTableThValue('Amount');
//     stringTr += createTableThValue('Currency Code');
//     stringTr += createTableThValue('Content');
//
//
//     stringTr += '</tr>';
//
//     return stringTr;
// }

// function createTableTr4(transaction, number) {
//     var stringTr = '';
//     if (number % 2 == 0) {
//         stringTr = '<tr class="tr-table" >';
//     } else {
//         stringTr = '<tr class="tr-table tr-impaire" >';
//     }
//     stringTr += createTableTdValue('step', transaction.transStepNumb);
//     stringTr += createTableTdValue('fromWalletId', transaction.fromWalletId);
//     stringTr += createTableTdValue('toWalletId', transaction.toWalletId);
//     stringTr += createTableTdValue('amount', transaction.amount);
//     stringTr += createTableTdValue('currencyCode', transaction.currencyCode);
//     stringTr += createTableTdValue('content', transaction.content);
//
//     stringTr += '</tr>';
//     return stringTr;
// }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------------------------------------------------------


function selectTable(row) {
    var tdValue = $(row).find('td')[1];
    var pvalue = $(tdValue).find('p').html();

    var tdValue2 = $(row).find('td')[2];
    var pvalue2 = $(tdValue2).find('p').html();

    var tdValue4 = $(row).find('td')[4];
    var pvalue4 = $(tdValue4).find('p').html();

    alert('Acc info : ' + pvalue
        + ' || Name : ' + pvalue2
        + ' || Status : ' + pvalue4
    )
}


// --------------------------------------------------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
    appendTableContent($(".content-pane").find('.content-trans-accounting').find('.table-value'));
});


setInterval(function () {
    var token_web = getTokenWeb();
    if (token_web != null && token_web.loginStatus) {
        callData();
    } else {
        listDataTable = [];
    }
}, numbIntervalLoadData); // Execute somethingElse() every 2 seconds.


setInterval(function () {
    updateDataTable($(".content-pane").find('.content-trans-accounting').find('.table-value'), 4);
    setInfoShow();
}, numbIntervalLoadData);