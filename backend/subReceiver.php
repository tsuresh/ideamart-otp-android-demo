<?php

error_reporting(0);
header('Content-Type: application/json');

include("config.php");
require("lib/ideamartSubs.php");

$ideamart = new IdeamartSubs($APP_ID, $APP_PASSWORD);

//Input data from json body
$post = json_decode(file_get_contents('php://input'), true);

//Process subscription request here

?>