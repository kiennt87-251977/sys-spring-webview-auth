var isDrawTitle = true;

function drawTitle() {
    var canvas = document.getElementById('canvas-title-pane');
    if (canvas != null && canvas.getContext) {
        var ctx = canvas.getContext('2d');
        // ctx.shadowColor = "rgba(0, 0, 0, 0.5)";
        // ctx.shadowOffsetX = 4;
        // ctx.shadowOffsetY = 4;
        // ctx.shadowBlur = 5;
        // ctx.textAlign = "center";
        // ctx.font = "bold 35px Arial";
        // var message = "Welcome to vds!";
        // ctx.fillText(message, canvas.width / 2, canvas.height / 2);

        var img = document.getElementById("img-web-logo");
        ctx.drawImage(img, 0, 0, 320, 200);


        if (isDrawDra) {
        }
        isDrawDra = !isDrawDra;
    }
}


// --------------------------------------------------------------------------------------------------------------------------------------------------------

$(document).ready(function () {
    apiPath = $(".path-api").html();
    drawTitle();

    // drawlogo();
});

setInterval(function () {

}, numbIntervalLoadData);


setInterval(function () {
}, numbIntervalLoad);
