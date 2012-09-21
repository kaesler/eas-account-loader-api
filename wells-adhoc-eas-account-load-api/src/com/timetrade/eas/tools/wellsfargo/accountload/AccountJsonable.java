package com.timetrade.eas.tools.wellsfargo.accountload;

import flexjson.JSONSerializer;

public class AccountJsonable {

    public String licensee;
    public String emailAddress;
    public String externalID;
    public String username;
    public String domain;
    public String password;
    public String certificate;
    public String certificatePassphrase;
    public String mailHost;
    public String notifierURI;

    public String mailServerType = "MsExchange";
    public boolean tentativeMeansFreeInFreeBusy = false;
    public String[] storeFields = {};
    public String bodyTemplate = "";


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

    public AccountJsonable() {}
}
