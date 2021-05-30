<?php
class IdeamartSubs {

    public $ENDPOINT = "https://api.dialog.lk";
    public $APP_ID;
    public $APP_PASSWORD;

    function __construct($APP_ID, $APP_PASSWORD) {
        $this->APP_ID = $APP_ID;
        $this->APP_PASSWORD = $APP_PASSWORD;
    }

    function getOtp($subscriberId, $applicationHash, $applicationMetaData){
        $data = array(
            'applicationId'         => $this->APP_ID,
            'password'              => $this->APP_PASSWORD,
            'subscriberId'          => $subscriberId,
            'applicationHash'       => $applicationHash,
            'applicationMetaData'   => $applicationMetaData
        );
        return $this->sendRequest($this->ENDPOINT . "/subscription/otp/request", $data);
    }

    function verifyOtp($referenceNo, $otp){
        $data = array(
            'applicationId'         => $this->APP_ID,
            'password'              => $this->APP_PASSWORD,
            'referenceNo'           => $referenceNo,
            'otp'                   => $otp
        );
        return $this->sendRequest($this->ENDPOINT . "/subscription/otp/verify", $data);

    }

    function sendRequest($endPoint, $data){
        $payload = json_encode($data);
        $ch = curl_init($endPoint);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        $result = curl_exec($ch);
        curl_close($ch);
        error_log($result);
        return json_decode($result, true);
    }

}
?>