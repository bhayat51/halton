<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Halton Village Energy Project</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <?php
                    function add_button($title, $url) {
                        if ($_SERVER["PHP_SELF"] === $url) {
                            echo "<li class=\"active\"><a href=\"#\"'>" . $title . "</a></li>"; // This is the currently active page
                        } else {
                            echo "<li><a href=\"" . $url . "\">" . $title . "</a></li>";
                        }
                    }

                    add_button("About", "/about.php");
                    add_button("Energy", "/energy.php");
                    add_button("Appliances", "/appliances.php");
                ?>
                <li><a href="http://haltonvillageproject.wordpress.com/the-team/">Contact</a></li>
            </ul>
        </div>
    </div>
</div>