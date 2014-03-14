<?php

class HaltonAPI {
	private static $api_hostname = "http://haltonenergy.co.uk:30000/api/";

	public static function get_appliance_by_id($applianceId) {
		return json_decode(file_get_contents(self::$api_hostname . "appliances/" . $applianceId), true);
	}

	public static function get_all_appliances() {
		return json_decode(file_get_contents(self::$api_hostname . "appliances/all"), true);
	}

	public static function get_usage_by_id($usageId) {
		return json_decode(file_get_contents(self::$api_hostname . "usages/single/" . $usageId), true);
	}

	public static function get_all_uses_for_appliance($applianceId) {
		return json_decode(file_get_contents(self::$api_hostname . "usages/all/" . $applianceId), true);
	}

	public static function get_statistics($start, $end) {
		return json_decode(file_get_contents(self::$api_hostname . "statistics/" . $start . "/" . $end), true);
	}
}
?>
