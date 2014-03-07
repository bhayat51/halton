
var api_hostname = "http://localhost:30000/api/";

// Setup date pickers
var options = {
    dateFormat: "dd-MM-yy",
    defaultDate: "+0",
    showOtherMonths: true,
};

var dt = new Date();
$("#start_date").datepicker(options).datepicker("setDate", dt);

dt.setDate(dt.getDate() + 1);
$("#end_date").datepicker(options).datepicker("setDate", dt);

// Setup initial chart state
google.load("visualization", "1", {packages:["corechart"]});

$("#view_btn").click(function(event) {
    displayChart();
});

function displayChart() {
    // TODO Finish this
    $.get(api_hostname + getStartDate() + "/" + getEndDate(), function(jsonString) {
        alert("Json: " + jsonString);
        var data = new google.visualization.DataTable();
        var chart = new google.visualization.PieChart(document.getElementById("chart"));
        chart.draw(data, {width: 400, height: 200});
    });
}

function getStartDate() {
    return $("start_date").datepicker("getDate");
}

function getEndDate() {
    return $("end_date").datepicker("getDate");
}