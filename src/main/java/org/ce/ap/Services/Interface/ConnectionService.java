package main.java.org.ce.ap.Services.Interface;

import main.java.org.ce.ap.Models.Request;
import main.java.org.ce.ap.Models.Response;

import java.io.IOException;

public interface ConnectionService {
    Response sendRequest(Request request) throws IOException;
    void sendResponse(Response response);
    void setupServer();
    void setupClient();
    //public void
}
