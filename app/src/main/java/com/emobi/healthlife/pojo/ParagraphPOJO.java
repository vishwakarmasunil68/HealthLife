package com.emobi.healthlife.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 14-02-2017.
 */

public class ParagraphPOJO implements Serializable{
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<ParagraphResultPOJO> list_pojo;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ParagraphResultPOJO> getList_pojo() {
        return list_pojo;
    }

    public void setList_pojo(List<ParagraphResultPOJO> list_pojo) {
        this.list_pojo = list_pojo;
    }

    @Override
    public String toString() {
        return "ParagraphPOJO{" +
                "success='" + success + '\'' +
                ", list_pojo=" + list_pojo +
                '}';
    }
}
