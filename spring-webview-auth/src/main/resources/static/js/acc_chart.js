var currentTPS = 0;
var currentStatusValue = [];
var currentStatusName = [];


// --------------------------------------------------------------------------------------------------------------------------------------------------------


// --------------------------------------------------------------------------------------------------------------------------------------------------------

function Getdata() {
    callTpsChart();
    callStatusChart();
}

// --------------------------------------------------------------------------------------------------------------------------------------------------------

async function callStatusChartExt() {
    var url = apiPath + "/getStatusChart/" + convertDateToStringInputFilter(new Date())
        + "/" + convertDateToStringInputFilter(new Date())
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


function callStatusChart() {
    callStatusChartExt().then(
        function (value) {
            if (value.responseCode != null && value.responseCode == '00000') {
                var dataResponse = value.data;
                currentStatusValue = [];
                currentStatusName = [];
                dataResponse.forEach(elem => {
                    currentStatusName.push(elem.name);
                    currentStatusValue.push(elem.value);
                });


            } else {
                currentStatusValue = [];
                currentStatusName = [];

                if (value != null) {
                    if (value.includes('ERROR')) {
                        alert(value);
                        clearTokenError(value);
                    } else {

                    }
                } else {
                    alert('Data not found');
                }
            }
        },
        function (error) {
            alert(error);
        }
    );

};


async function callTpsChartExt() {
    var url = apiPath + "/getTpsChart/" + convertDateToStringInputFilter(new Date())
        + "/" + convertDateToStringInputFilter(new Date())
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


function callTpsChart() {
    callTpsChartExt().then(
        function (value) {
            if (value.responseCode != null && value.responseCode == '00000') {
                currentTPS = value.data;
            } else {
                currentTPS = 0;
                if (value != null) {
                    if (value.includes('ERROR')) {
                        alert(value);
                        clearTokenError(value);
                    } else {

                    }
                } else {
                    alert('Data not found');
                }
            }
        },
        function (error) {
            alert(error);
        }
    );

};

// --------------------------------------------------------------------------------------------------------------------------------------------------------
var barColors = [];

var chartTPS;
var dataChartTPS = [];
var xTPSValues = [];
var numbIntervalDataChart = 1000;
var maxIntervalChart = 20;


var chartState;
var xValues = [];
var yValues = [];

function configChart() {
    barColors = [
        "#00aba9",
        "#b91d47",
        "#2b5797",
        "#e8c3b9",
        "#e89600",
        "#1e7145"
    ];

    configChartTPS();
    configChartNumb();
}

function configChartTPS() {
    // ChartTPS
    for (var i = 0; i < maxIntervalChart; i++) {
        xTPSValues.push(numbIntervalDataChart / 1000 * i + "s");
        dataChartTPS.push(i);
    }
}

function configChartNumb() {
    // ChartNumb
    xValues = ["Success", "Fail", "Processing"];
    yValues = [12, 27, 61];
}


function createChartFirst() {
    configChart();
    createChartNumb();
    createChartTPS();
}


function createChartNumb() {
    chartState = new Chart("chart-state", {
        type: "pie",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: barColors,
                data: yValues
            }]
        },
        options: {
            animation: false,
            title: {
                display: true,
                text: "Number Transaction in " + new Date().toLocaleDateString("en-US", optionsDateChart)
            }
        }
    });

}


function createChartTPS() {
    chartTPS = new Chart("chart-tps", {
        type: "line",
        data: {
            labels: xTPSValues,
            datasets: [{
                label: 'TPS',
                data: dataChartTPS,
                borderColor: "#13b956",
                fill: false
            }
            ]
        },
        options: {
            legend: {display: true},
            animation: false,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    },
                    scaleLabel: {
                        display: true,
                        labelString: 'TPS'
                    }
                }],
                xAxes: [{
                    ticks: {
                        beginAtZero: true
                    },
                    scaleLabel: {
                        display: true,
                        labelString: 'In seconde'
                    }
                }]
            },
            title: {
                display: true,
                text: 'Period check total in ' + numbIntervalDataChart * maxIntervalChart / 1000 + 's'
            }
        }
    });
}

function updateDataChart() {
    updateDataChartTPS();
    updateDataChartNumb();
}

const updateDataChartTPS = () => {
    // ChartTPS
    var dataTemp = [];
    var numbStart = 0;
    var numbEnd = dataChartTPS.length;
    dataChartTPS.forEach(elem => {
        if (numbStart > 0) {
            dataTemp.push(elem);
        }
        numbStart++;
        if (numbStart == numbEnd) {
            dataTemp.push(Number(currentTPS));
        }
    });
    dataChartTPS = dataTemp;
}

const updateDataChartNumb = () => {
    // ChartNumb
    // yValues = [100 - currentTPS, currentTPS, currentTPS / 3];
    xValues = currentStatusName;
    yValues = currentStatusValue;
}

function updateChart() {
    updateChartTPS();
    updateChartNumb();
}

function updateChartTPS() {
    // ChartTPS
    chartTPS.data.datasets.forEach((dataset) => {
        dataset.data = dataChartTPS;
    });
    chartTPS.update('none');
}

function updateChartNumb() {
    // ChartNumb
    chartState.data.datasets.forEach((dataset) => {
        dataset.data = yValues;
    });
    chartState.data.labels = xValues;
    chartState.update('none');
}


//--------------------------------------------------------------------------------------------------------------------------------------------------------


// --------------------------------------------------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
    callTpsChart();
    createChartFirst();
});


setInterval(function () {
    var token_web = getTokenWeb();
    if (token_web != null && token_web.loginStatus) {
        Getdata();
    } else {
        currentStatusValue = [];
        currentStatusName = [];
        currentTPS = 0;
    }
}, numbIntervalDataChart); // Execute somethingElse() every 2 seconds.

setInterval(function () {
    updateDataChart();
    updateChart();
}, numbIntervalLoad);