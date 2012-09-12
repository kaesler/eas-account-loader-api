package com.timetrade.eas.tools.wellsfargo.accountload;

import flexjson.JSONSerializer;

/**
 * Class to contain creds for the purpose of validating them against an Exchange server.
 */
public class CredentialsValidationJsonable {

    public String mailHost;
    public String username;
    public String domain;
    public String password;
    public String certificate;
    public String certificatePassphrase;

    /**
     * Construct an instance for certificate based authentication.
     * @param mailHost name of Exchange server host
     * @param certificate
     * @param certificatePassphrase
     */
    CredentialsValidationJsonable(
        String mailHost,
        String certificate,
        String certificatePassphrase) {

        this(mailHost,
             "anyUserName",
             "",
             "",
             certificate,
             certificatePassphrase);
    }

    /**
     * Construct an instance for username/password based authentication.
     * @param mailHost name of Exchange server host
     * @param username username
     * @param domain   domain (may be left empty)
     * @param password password
     */
    CredentialsValidationJsonable(
        String mailHost,
        String username,
        String domain,
        String password
        ) {

        this(mailHost,
             username,
             domain,
             password,
             "",
             "");
    }

    CredentialsValidationJsonable(
        String mailHost,
        String username,
        String domain,
        String password,
        String certificate,
        String certificatePassphrase) {

        this.mailHost = mailHost;
        this.username = username;
        this.domain = domain;
        this.password = password;
        this.certificate = certificate;
        this.certificatePassphrase = certificatePassphrase;
    }

    /**
     * Convert to JSON representation.
     * @return JSON form.
     */
    String
    toJSON() {
        return new JSONSerializer()
            .exclude("*.class")
            .serialize(this);
    }
}
