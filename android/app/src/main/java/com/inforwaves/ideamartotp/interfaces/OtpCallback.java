package com.inforwaves.ideamartotp.interfaces;

import com.inforwaves.ideamartotp.data.model.Message;

public interface OtpCallback {
    void onSuccess(Message message);

    void onFailure(Message message);
}
