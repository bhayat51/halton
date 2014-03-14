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
                            echo sprintf("<li class='active'><a href='#'>%s</a></li>\n", $title); // This is the currently active page
                        } else {
                            echo sprintf("<li><a href='%s'>%s</a></li>\n", $url, $title);
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