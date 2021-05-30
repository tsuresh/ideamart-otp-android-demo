<?php

error_reporting(0);
header('Content-Type: application/json');

include("config.php");
require("lib/ideamartSubs.php");

$ideamart = new IdeamartSubs($APP_ID, $APP_PASSWORD);

//Input data from json body
$post = json_decode(file_get_contents('php://input'), true);

//Subsriber ID
$subscriberId = $post["subscriberId"];

//Application hash for Google SMS capture
$applicationHash = $post["applicationHash"];

//Application meta
$applicationMetaData = array(
    "client"    =>  "MOBILEAPP",
    "device"    =>  $post["device"],
    "os"        =>  $post["os"],
    "appCode"   =>  $post["appCode"]
);

$response = $ideamart->getOtp($subscriberId, $applicationHash, $applicationMetaData);

echo(json_encode($response));

?>