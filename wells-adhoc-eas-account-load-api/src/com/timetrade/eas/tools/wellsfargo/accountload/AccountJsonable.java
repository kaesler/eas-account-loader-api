package com.timetrade.eas.tools.wellsfargo.accountload;

import flexjson.JSONSerializer;

public class AccountJsonable {

    public final String licensee;
    public final String emailAddress;
    public final String externalID;
    public final String username;
    public final String domain;
    public final String password;
    public final String certificate;
    public final String certificatePassphrase;
    public final String mailHost;
    public final String notifierURI;

    public final String mailServerType = "MsExchange";
    public final boolean tentativeMeansFreeInFreeBusy = false;
    public final String[] storeFields = {};
    public final String bodyTemplate = "";


    /**
     * Construct an instance with username/domain/password creds.
     *
     * @param licensee
     * @param emailAddress
     * @param externalID
     * @param username
     * @param domain
     * @param password
     * @param mailHost
     * @param notifierURI
     * @return new instance
     */
    public static AccountJsonable
    withUsernameCreds(
        String licensee,
        String emailAddress,
        String externalID,
        String username,
        String domain,
        String password,
        String mailHost,
        String notifierURI) {

        return new AccountJsonable(
            licensee,
            emailAddress,
            externalID,
            username,
            domain,
            password,
            null, // cert
            null, // certPassphrase
            mailHost,
            notifierURI);
    }

    /**
     * Construct an instance with certificate creds.
     *
     * @param licensee
     * @param emailAddress
     * @param externalID
     * @param certificate
     * @param certificatePassphrase
     * @param mailHost
     * @param notifierURI
     * @return new instance
     */
    public static AccountJsonable
    withCertificateCreds(
        String licensee,
        String emailAddress,
        String externalID,
        String certificate,
        String certificatePassphrase,
        String mailHost,
        String notifierURI) {

        return new AccountJsonable(
            licensee,
            emailAddress,
            externalID,
            emailAddress, // use email address for logging
            null, // domain
            null, // password;
            certificate,
            certificatePassphrase,
            mailHost,
            notifierURI);
        }

    String
    toJSON() {
        return new JSONSerializer()
            .include("storeFields")
            .exclude("*.class")
            .serialize(this);
    }

    CredentialsValidationJsonable
    extractCredentials() {
        return new CredentialsValidationJsonable(
            mailHost,
            username,
            domain,
            password,
            certificate,
            certificatePassphrase);

    }

    private
    AccountJsonable(
        String licensee,
        String emailAddress,
        String externalID,
        String username,
        String domain,
        String password,
        String certificate,
        String certificatePassphrase,
        String mailHost,
        String notifierURI) {

        this.licensee = licensee;
        this.emailAddress = emailAddress;
        this.externalID = externalID;
        this.username = username;
        this.domain = domain;
        this.password = password;
        this.certificate = certificate;
        this.certificatePassphrase = certificatePassphrase;
        this.mailHost = mailHost;
        this.notifierURI = notifierURI;
    }
}
