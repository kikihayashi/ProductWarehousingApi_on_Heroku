package com.woody.productwarehousingapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//若回傳參數有Null則忽略該參數(不轉成json)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadResponse {

    @JsonProperty("Result")
    private Result result;

    @JsonProperty("success")
    private String success;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class Result {
        @JsonProperty("IfSucceed")
        private String ifSucceed;
        @JsonProperty("ErrMessage")
        private String errMessage;
        @JsonProperty("WorkId")
        private String workId;


        public String getIfSucceed() {
            return ifSucceed;
        }

        public void setIfSucceed(String ifSucceed) {
            this.ifSucceed = ifSucceed;
        }

        public String getErrMessage() {
            return errMessage;
        }

        public void setErrMessage(String errMessage) {
            this.errMessage = errMessage;
        }

        public String getWorkId() {
            return workId;
        }

        public void setWorkId(String workId) {
            this.workId = workId;
        }
    }


}
