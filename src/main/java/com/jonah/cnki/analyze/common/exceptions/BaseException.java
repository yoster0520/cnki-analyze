package com.jonah.cnki.analyze.common.exceptions;

/**
 * the top level exceptions.
 *
 * @author jonah
 * @since 2017/3/3-17:43
 */
public class BaseException extends Exception{
    private String errorMsg;
    private String errorCode;
    private Object[] errorObject;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getErrorObject() {
        return errorObject;
    }

    public void setErrorObject(Object[] errorObject) {
        this.errorObject = errorObject;
    }
}
