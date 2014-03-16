<!DOCTYPE html>
<html lang="en">
<head>
    <title>Halton Village Energy Project - Energy</title>
    <?php include('include/head.php'); ?>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
</head>
<body>
    <?php include('include/navbar.php'); ?>
    <div class="container box">
        <div class="row">
            <div class="col-sm-12">
                <h2>Energy</h2>
                <noscript>
                    <div class="alert alert-warning"><p>Please enable Javascript to view the content on this page correctly.</p></div>
                </noscript>
                <p>This page displays energy statistics for the solar panels in the co-housing. You can view the amount of energy generated and used by the residents.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4">
                <input id="start_date" type="text" class="form-control" placeholder="dd/mm/yyyy" />
            </div>
            <div class="col-sm-4">
                <input id="end_date" type="text" class="form-control" placeholder="dd/mm/yyyy" />
            </div>
            <div class="col-sm-2">
                <button id="view_btn" type="button" class="btn btn-default btn-sm">View statistics</button>
            </div>
        </div>
        <div class="row" style="padding-bottom: 10px">
            <div id="energy-chart" />
        </div>
    </div>
    <?php 
        // include('footer.php');
        include('include/scripts.php');
    ?>
    <script src="js/energy.js"></script>
</body>
</html>
