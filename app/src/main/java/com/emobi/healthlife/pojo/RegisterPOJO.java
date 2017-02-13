package com.emobi.healthlife.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 13-02-2017.
 */

public class RegisterPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    RegisterResultPOJO result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public RegisterResultPOJO getResult() {
        return result;
    }

    public void setResult(RegisterResultPOJO result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RegisterPOJO{" +
                "success='" + success + '\'' +
                ", result=" + result +
                '}';
    }
}
