package com.inforwaves.ideamartotp.data.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.StrictMode;

import com.inforwaves.ideamartotp.data.model.Message;
import com.inforwaves.ideamartotp.interfaces.OtpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginUtil {

    private Context context;
    private static final String BACKEND = "https://sv2.ideamarthosting.dialog.lk/sureshp72540920Apps/demo/";

    public LoginUtil(Context context) {
        this.context = context;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void requestOtp(String mobileNumber, OtpCallback callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"subscriberId\" : \"tel:94"+mobileNumber+"\",\r\n    \"applicationHash\" : \"1234dsadsadsa\",\r\n    \"device\" : \"Pixel\",\r\n    \"os\" : \"Android\",\r\n    \"appCode\" : \"10\"\r\n}");
        Request request = new Request.Builder()
                .url(BACKEND + "requestOtp.php")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 200){
                String jsonData = response.body().string();
                JSONObject object = new JSONObject(jsonData);
                String statusDetail = object.getString("statusDetail");
                String statusCode = object.getString("statusCode");
                if(statusCode.equals("S1000")){
                    String referenceNumber = object.getString("referenceNo");
                    callback.onSuccess(new Message(
                            statusDetail,
                            statusCode,
                            referenceNumber
                    ));
                } else {
                    callback.onFailure(new Message(
                            statusDetail,
                            statusCode
                    ));
                }
            } else {
                callback.onFailure(new Message(
                        "An error has occurred",
                        String.valueOf(response.code())
                ));
            }
        } catch (IOException | JSONException e) {
            callback.onFailure(new Message(
                    e.getLocalizedMessage(),
                    e.getMessage()
            ));
        }
    }

    public void verifyOtp(String refNumber, String otpCode, OtpCallback callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"referenceNo\" : \""+refNumber+"\",\r\n    \""+otpCode+"\" : \"1234\"\r\n}");
        Request request = new Request.Builder()
                .url(BACKEND + "verifyOtp.php")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 200){
                String jsonData = response.body().string();
                JSONObject object = new JSONObject(jsonData);
                String statusDetail = object.getString("statusDetail");
                String statusCode = object.getString("statusCode");
                if(statusCode.equals("S1000")){
                    callback.onSuccess(new Message(
                            statusDetail,
                            statusCode,
                            statusDetail
                    ));
                } else {
                    callback.onFailure(new Message(
                            statusDetail,
                            statusCode
                    ));
                }
            } else {
                callback.onFailure(new Message(
                        "An error has occurred",
                        String.valueOf(response.code())
                ));
            }
        } catch (IOException | JSONException e) {
            callback.onFailure(new Message(
                    e.getLocalizedMessage(),
                    e.getMessage()
            ));
        }
    }

}
