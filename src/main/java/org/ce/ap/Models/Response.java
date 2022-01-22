package main.java.org.ce.ap.Models;

/**
 * @author rbmoon
 * the model for Response object
 */
public class Response {
    private boolean hasError;
    private int errorCode;
    private int count;
    private Object result;

    public boolean getHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Response(boolean hasError, int errorCode, int count, Object result) {
        this.hasError = hasError;
        this.errorCode = errorCode;
        this.count = count;
        this.result = result;
    }
}
