package main.java.org.ce.ap.Models;

/**
 * @author rbmoon
 * the model for Request object
 */
public class Request {

    private String method;
    private String description;
    private Object[] parameterValues;

    public Request(String method, String description, Object[] parameterValues) {
        this.method = method;
        this.description = description;
        this.parameterValues = parameterValues;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(Object[] parameterValues) {
        this.parameterValues = parameterValues;
    }
}
