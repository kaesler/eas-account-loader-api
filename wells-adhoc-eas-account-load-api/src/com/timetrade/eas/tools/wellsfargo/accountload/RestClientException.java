package com.timetrade.eas.tools.wellsfargo.accountload;

@SuppressWarnings("serial")
public class RestClientException extends Exception {

    public RestClientException() {
    }

    public RestClientException(String arg0) {
        super(arg0);
    }

    public RestClientException(Throwable arg0) {
        super(arg0);
    }

    public RestClientException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
