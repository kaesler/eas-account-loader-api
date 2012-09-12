package com.timetrade.eas.tools.wellsfargo.accountload;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Ad hoc class supplied to Wells Fargo to provide a programmatic way to:
 *   a. validate Exchange Active Sync server credentials.
 *   b. link an Eschange account to Timetrade EAD connector.
 */
public class EasLoader {

    private final URL restUrl;

    private static final String VALIDATION = "/api/credentials-validation";
    private static final String CREATION = "/api/%s/calendars";


    /**
     * Construct instance
     * @param restUrl the base URL for the EAS connector REST APi service.
     */
    public EasLoader(URL restUrl) {
        this.restUrl = restUrl;
    }

    /**
     * Validate EAS credentials.
     * @param account instance containing the credentials to validate.
     * @return an error message. Empty string means ok.
     * @throws RestClientException
     */
    public String
    validate(AccountJsonable account) throws RestClientException {
        return validate(account.extractCredentials());
    }

    /**
     * Connect an Exchange account.
     * @param account account to be connected.
     * @return an error message. Empty string means ok.
     * @throws RestClientException
     */
    public String
    connect(AccountJsonable account) throws RestClientException {

        String url = null;
        try {
            url = restUrl.toString()
                  + String.format(CREATION, URLEncoder.encode(account.licensee, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RestClientException(e);
        }

        Response resp =
            RestClient.doPost(
                url,
                account.toJSON(),
                "application/vnd.timetrade.calendar-connect.account+json"
                );
        if (resp.status != 201) {
            return String.format("Bad status: %d" + resp.status);
        }

        return "";
    }

    /**
     * Validate EAS credentials.
     * @param credentials the credentials to validate.
     * @return an error message. Empty string means ok.
     * @throws RestClientException
     */
    String
    validate(CredentialsValidationJsonable credentials) throws RestClientException {

        Response resp =
            RestClient.doPost(restUrl.toString() + VALIDATION,
                              credentials.toJSON(),
                              "application/vnd.timetrade.calendar-connect.credentials-validation+json"
                              );
        if (resp.status != 200) {
            return String.format("Bad status: %d", resp.status);
        }
        if (resp.body.isEmpty()) {
            return "Empty response";
        }

        String[] fields = resp.body.split("\\|");
        if (fields.length < 1) {
            return "Invalid response";
        }

        String code = fields[0];
        if (code.equals("OK")) {
            return "";
        }

        String details = (fields.length > 1) ? fields[1] : "";

        return code + " " + details;
    }
}
