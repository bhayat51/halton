<div class="navbar" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <img src="img/logo.png" width="64" height="64">
            <h6 class="nomarg">Halton Energy Project</h6>
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
            </ul>
        </div>
    </div>
</div>