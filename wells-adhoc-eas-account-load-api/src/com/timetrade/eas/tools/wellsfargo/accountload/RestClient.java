package com.timetrade.eas.tools.wellsfargo.accountload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class RestClient {

    private static final String POST = "POST";
    private static final String UTF8 = "UTF-8";

    static Response
    doPost(
        String uriStr,
        String content,
        String contentType
    ) throws RestClientException {

        return doHTTPRequest(uriStr, POST, content, contentType, "*/*");
    }

    private static Response
    doHTTPRequest(
        String uriStr,
        String operation,
        String content,
        String contentType,
        String acceptType
    ) throws RestClientException {

        URL url = null;
        StringBuilder output = new StringBuilder();
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        HttpURLConnection urlConn = null;
        try {
            url = new URL(uriStr);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod(operation);
            urlConn.setAllowUserInteraction(false);
            urlConn.setDoOutput(true);

            if (acceptType != null) {
                urlConn.setRequestProperty("Accept", acceptType);
            }

            if (contentType != null) {
                urlConn.setRequestProperty("Content-Type", contentType + "; charset=UTF-8");
            }

            urlConn.connect();

            if (content != null) {
                wr = new OutputStreamWriter(urlConn.getOutputStream(), UTF8);
                wr.write(content);
                wr.flush();
            }

            int status = urlConn.getResponseCode();
            if (status >= 200 && status <= 299) {
                // Success, read the result from the server
                rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), UTF8));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    output.append(line + '\n');
                }
                return new Response(status, output.toString());
            } else {
                return new Response(status);
            }
        } catch (MalformedURLException e) {
            String msg = String.format("Error making REST call. " +
                    "uriStr = %s, operation = %s, content = %s, contentType = %s, acceptType = %s",
                    uriStr, operation, content,	contentType, acceptType);
            throw new RestClientException(msg, e);
        } catch (IOException e) {
            String msg = String.format("Error making REST call. " +
                    "uriStr = %s, operation = %s, content = %s, contentType = %s, acceptType = %s",
                    uriStr, operation, content,	contentType, acceptType);
            throw new RestClientException(msg, e);
        }
        finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {} // ignore
            }
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {} // ignore
            }
            if (urlConn != null) {
                urlConn.disconnect();
                urlConn = null;
            }
        }
    }
}
