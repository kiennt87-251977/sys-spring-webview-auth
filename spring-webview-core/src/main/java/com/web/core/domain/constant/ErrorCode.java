package com.web.core.domain.constant;

public class ErrorCode {

    public enum TYPE {
        SUCCESS("00000", "Successfully"),
        ERROR_UNKNOWN("99999", "error unknown"),

        STATUS_SUCCESSING("79990", "STATUS_SUCCESSING"),
        FULL_BUSINESS_POOL("79991", "FULL_BUSINESS_POOL"),
        FULL_REQUEST_QUEUE("79992", "FULL_REQUEST_QUEUE"),

        CORE_APP_SUCCESSING("C00000", "Successfully"),
        CORE_APP_ERROR("C99999", "error core app"),
        CORE_APP_CONNECT_ERROR("C99999", "error connect core app"),

        ACCOUNT_DB("A00000", "Successfully"),


        ;

        //        ------------------------------------------------
        private String code;
        private String message;

        TYPE(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
