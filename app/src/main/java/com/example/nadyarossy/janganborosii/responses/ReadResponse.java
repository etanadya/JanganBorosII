package com.example.nadyarossy.janganborosii.responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("datas")
    @Expose
    private List<Data> datas = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

}
