<!DOCTYPE html>
<html lang="en">
<head>
    <title>Halton Village Energy Project - Appliances</title>
    <?php include('include/head.php'); ?>
</head>
<body>
    <?php include('include/navbar.php'); ?>
    <div class="container main">
        <div class="row">
            <div class="col-sm-12">
                <h2>Appliances</h2>
                <noscript>
                    <div class="alert alert-warning"><p>Please enable Javascript to view the content on this page correctly.</p></div>
                </noscript>
                <p>Here you can discover the energy impact of common appliances used around Halton. Select an appliance and a way to use it from the lists below.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3">
                <ul class="list-group" id="appliance-list">
                    <?php
                        include_once('include/halton_api.php');

                        $i = 0;
                        $appliances = HaltonAPI::get_all_appliances();
                        foreach($appliances as $appliance) {
                            echo sprintf("<a class='list-group-item' item_key='%s'>%s</a>\n", $appliance["appliance_id"], $appliance["name"]);
                            $i++;
                        }
                    ?>
                </ul>
            </div>
            <div class="col-sm-3" id="usages">
                
            </div>
            <div class="col-sm-6" id="appliance_info">
                
            </div>
        </div>
    </div>
    <?php 
        // include('footer.php');
        include('include/scripts.php');
    ?>
    <script src="js/appliances.js"></script>
</body>
</html>
