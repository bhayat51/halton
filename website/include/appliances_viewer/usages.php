<?php
include_once('../halton_api.php');

$usages = HaltonAPI::get_all_uses_for_appliance($_SERVER['QUERY_STRING']);

echo "<ul class=\"list-group\" id=\"usages-list\">";
foreach($usages as $usage) {
    echo sprintf("<a class='list-group-item' item_key='%s'>%s</a>\n", $usage["usageId"], $usage["name"]);
}
echo "</ul>";
?>