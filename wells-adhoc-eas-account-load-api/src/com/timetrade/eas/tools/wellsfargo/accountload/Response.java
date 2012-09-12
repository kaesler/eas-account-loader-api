package com.timetrade.eas.tools.wellsfargo.accountload;

class Response {

    final int status;
    final String body;

    Response(int status, String body) {
        this.status = status;
        this.body = body;
    }

    Response(int status) {
        this(status, "");
    }
}
