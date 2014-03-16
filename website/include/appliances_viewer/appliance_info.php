<?php
include_once('../halton_api.php');

parse_str($_SERVER["QUERY_STRING"], $args);

function echos($json, $key, $format) {
	if (isset($json[$key])) {
		echo sprintf($format, $json[$key]);
		echo "<br />";
	}
}

echo "<h4>Info</h4>";
if (isset($args["appliance"])) {
	$appliance = HaltonAPI::get_appliance_by_id($args["appliance"])[0];

	echo "<h3>" . $appliance["name"] . "</h3>";
	echos($appliance, "image_url", "<img src=\"%s\" width=\"256\" height=\"256\"></a>");
	echo "<p>";
	echos($appliance, "description", "%s");
	echos($appliance, "annual_consumption", "Annual Consumption: %dkWh");
	echos($appliance, "model", "Model: %s");
	echos($appliance, "size", "Size: %s");
	echo "</p>";
}
echo "<hr />";
if (isset($args["usage"])) {
	$usage = HaltonAPI::get_usage_by_id($args["usage"])[0];
	echo "<p><h3>" . $usage["name"] . "</h3>";
	echos($usage, "description", "%s");
	echos($usage, "used", "Consumption: %dkWh");
	echo "</p>";
}
?>