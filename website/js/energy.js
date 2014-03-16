
var api_hostname = "http://haltonenergy.co.uk:30000/api/";

// Setup date pickers
var options = {
    dateFormat: "dd-MM-yy",
    defaultDate: "+0",
    showOtherMonths: true,
};

var dt = new Date();
dt.setMonth(0);
dt.setDate(1);
$("#start_date").datepicker(options).datepicker("setDate", dt);

dt.setFullYear(dt.getFullYear() + 1);
$("#end_date").datepicker(options).datepicker("setDate", dt);

// Setup initial chart state
google.load("visualization", "1", {packages:["corechart"]});

jQuery(function($) {
    $("#view_btn").click(function(event) {
        onViewClicked();
    });

    function onViewClicked() {
        if (getStartDate() >= getEndDate()) {
            $("#start_date").effect("highlight", {}, 500);
        } else {
            updateChart();
        }
    }

    function updateChart() {
        // API requires dates in yy-mm-dd format for reasons
        var start = $.datepicker.formatDate("yy-mm-dd", getStartDate());
        var end = $.datepicker.formatDate("yy-mm-dd", getEndDate());

        $.ajax({
                type: "GET",
                url: api_hostname + "statistics/gchart/" + start + "/" + end,
                dataType: "text",
                success: function(data) {
                    var data = new google.visualization.DataTable(eval("(" + data + ")")); /* TODO Remove nasty eval */
                    var chart = new google.visualization.BarChart(document.getElementById("energy-chart"));
                    var options = {
                        title: "Energy use: " + $.datepicker.formatDate("dd-mm-yy", getStartDate()) + " to " + $.datepicker.formatDate("dd-mm-yy", getEndDate()),
                        "backgroundColor": { fill: "none" }
                    };
                    chart.draw(data, options);
                    $("#energy-chart").hide();
                    $("#energy-chart").fadeIn();
                }
            })
    }

    function getStartDate() {
        return $("#start_date").datepicker("getDate");
    }

    function getEndDate() {
        return $("#end_date").datepicker("getDate");
    }
});