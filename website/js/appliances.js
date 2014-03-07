var applianceSelected;

/* Called when an appliance is selected */
$('#appliance-list > .list-group-item').click(function() {
    event.stopPropagation();

    var applianceId = $(this).attr("item_key");
    applianceSelected = applianceId;

    // Toggle active state
    $('#appliance-list > .list-group-item').removeClass("active");
    $(this).addClass("active");

    $("#usages").load("/include/appliances_viewer/usages.php?" + applianceId, function() { // Load possible usages for this appliance
        $('#usages-list > .list-group-item').click(function() { // On completion, set usage list click event
            event.stopPropagation();

            var usageId = $(this).attr("item_key");

            // Toggle active state
            $('#usages-list > .list-group-item').removeClass("active");
            $(this).addClass("active");

            $("#appliance_info").load("/include/appliances_viewer/appliance_info.php?appliance=" + applianceSelected + "&usage=" + usageId); // Load appliance info
        });
    });

    $("#appliance_info").load("/include/appliances_viewer/appliance_info.php?appliance=" + applianceId); // Load appliance info
});