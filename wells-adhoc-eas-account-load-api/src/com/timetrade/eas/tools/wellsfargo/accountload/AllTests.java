package com.timetrade.eas.tools.wellsfargo.accountload;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Test;

import flexjson.JSONDeserializer;

public class AllTests {
    private static final String restUrl = "http://localhost:8184";

    public static final String DEVEXCH2010_DC = "devexch2010-dc"; // "192.168.168.173";

    @Test
    public void testJsonRoundTrip() throws Exception {
        AccountJsonable account = AccountJsonable.withCertificateCreds(
                "TimeTrade", //licensee,
                "relliott@devexchtest.local", //emailAddress,
                "TestAcc1", //externalID,
                CLIENT_KEYSTORE_DATA,
                CLIENT_KEYSTORE_PASSWORD,
                DEVEXCH2010_DC, //mailHost,
                "http://localhost/notifier"//notifierURI
            );

        String json = account.toJSON();
        JSONDeserializer<AccountJsonable> deserializer = new JSONDeserializer<AccountJsonable>();
        Object o = deserializer.deserialize(json, AccountJsonable.class);
        System.out.println(o.toString());
    }

    @Test
    public void testCertificateCreds() throws Exception {
        EasLoader loader = new EasLoader(new URL(restUrl));
        AccountJsonable account = AccountJsonable.withCertificateCreds(
            "TimeTrade", //licensee,
            "relliott@devexchtest.local", //emailAddress,
            "TestAcc1", //externalID,
            CLIENT_KEYSTORE_DATA,
            CLIENT_KEYSTORE_PASSWORD,
            DEVEXCH2010_DC, //mailHost,
            "http://localhost/notifier"//notifierURI
        );

        String result = loader.validate(account);
        System.out.println(result);
        assertTrue(result.isEmpty());

        result = loader.connect(account);
        System.out.println(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testNonCertificateCreds() throws Exception {
        EasLoader loader = new EasLoader(new URL(restUrl));
        AccountJsonable account = AccountJsonable.withUsernameCreds(
            "TimeTrade", //licensee,
            "devcalcon3@Timetrade.com", //emailAddress,
            "TestAcc1", //externalID,
            "devcalcon3@Timetrade.com",//username,
            "", // domain
            "3D3Vconcal!",// password,
            "mobile.collaborationhost.net", //mailHost,
            "http://localhost/notifier"//notifierURI
        );

        String result = loader.validate(account);
        System.out.println(result);
        assertTrue(result.isEmpty());

        result = loader.connect(account);
        System.out.println(result);
        assertTrue(result.isEmpty());
    }

    final String CLIENT_KEYSTORE_PASSWORD = "password";

    //the base 64 decoding of the file relliott.p12
    final String CLIENT_KEYSTORE_DATA =
            "MIACAQMwgAYJKoZIhvcNAQcBoIAkgASCEKcwgDCABgkqhkiG9w0BBwGggCSABIIFcDCCBWww"
            + "ggVoBgsqhkiG9w0BDAoBAqCCBPUwggTxMCMGCiqGSIb3DQEMAQMwFQQQGiew3kgE+bmG8vj+"
            + "TtsYtwIBAQSCBMieeg7fhexumwqIZEjNcHngf8kDBxMTPnEK3ykHjWvKFIvSIDlNaKKJK7v4"
            + "kYBZOGQgP9KFd/NwgkJPDkZZw9odeGZugwMJ67z79OTcNFH2pnYZdx+3FeQNLFSKNIxdmeq/"
            + "OBe8m+XOgrUNExNd+DxHjkPL19wI37SYFxXexhs7O1GF45weAIiMeJIrLlleDhhUdwu67eLg"
            + "/IcUqiWOHdAH6z37aCZZYvHe5rxzBNbdsm4XiFMtO/CXPfdsL9UaxzOYaJqwTyR6iPafA4bN"
            + "lMRX92W+OsdNpp23cdO3PmhdVZ9Xw02yjui76Fq/qAYYSUHEcjLd2oI+/LRHf8vyiDzjWANg"
            + "1aBbTcWIyrpq5HKyhpL1CWyS0ljNtPeMDO3iULRGlg/PIMEZK3CYi7/jwfTQ3x2lA/0796h5"
            + "eLpwYFWyYkUU9RrLyTSa4FWMR/FVe39QD3ttXxbSCzbS2HnX0scjkteEL818shRYqqRmaMEl"
            + "h6rjSTaA2+ohegoN8AkdIU7WKmjF4wfUyw/KwS24ECpKPXjefHLESe+M+xDizqDIns3RPdIl"
            + "Cyl0g84eJ2I3Nr8ubmaKQP2/QrCMODUeBmUWAUmRV/JLf0wJ4mlDnIMV1KB5eh78RLiY//Hc"
            + "AWIcZFo8R8ad3Tv9urgK9npvqckAPfnsN6PDq7dkA03PhU9EKmp93xIFfNtrUHFhJnwqK7Gh"
            + "7CLwPMV6j/vXS9XqkMk5vLInWXqCwSITiXkZPW1PFTkxH0acV0veih/fAtWpHTc9MuxorqV2"
            + "1YVtiTBqCwEL9lkZQM+MUhtuklXxxDcmYDvxFpBXd9glPPHeFioFu5yHTrcJsQOEGH/HKYut"
            + "2+ylCL/VP64vI3tSJQv5YsEqeHRVoXGje/qao+bSMvEVFCGPmvFlwc8/BbWiB1XLBsy2feJ1"
            + "VbPWfs2j8xem5L7//gZHNOXrCrqiI6ps+I4ZGSC1xrCCBGg7c7KYnGKy46EtLz9aGUhh8ODv"
            + "mArLQUjvODFjbxvXS7yW+/qAkDD82L4t10ZER6LC4aCyFICdwNFwfh/OGj0QVV7TcYJlYY3z"
            + "wFcxVrR7EYT6JZ0gwmPfVjBa5pfCApujQTPCbGSDqYLfG8PjiI2qhK4ykxtLgDFsTZTkQoC4"
            + "VSb3xgARxdPi5dlened0wjbLarAOVpucCT5HWu2ykSiS0cd3p1/Wlsnq+UGaskxcJ4ACcGDJ"
            + "0KsrIFnpdqVP/bMIjfYyIUJI9VrXDYZJw3TIL1wvofLb+sx6xqhw3heYcKxF6ISkkJFli3+w"
            + "Yes4C213d2cT9hcfJ39lD4wPJbasc05FpjVmt09CQpIYNsKKJlQkUflTWoTZh42qDoNvUVKR"
            + "yzSyRYSk3v1bzvOEdU6TH16tA02rxd9dVrdWGM+WMMdQDS4KBvcVOfUom9X2tRPnyCdfGXpd"
            + "SLZTbfnRUZEe5U7LiebJ4BuDq/QyJNE/wUg7QQzaqwhotkGNNdrw6AwW8y5o/6/OtzRU5Vgd"
            + "Y1o1ldMymBylG9U0YRIwVzCZKfKL5Ftgzj1oA0gIrN78WixWiPujDiKL4q+7w0RmIVJsvsmP"
            + "Gmgrs0R/SMbwSIBIbpZ9oZCMrKKUF0il+v3y4zgzio9tsXUi2wEBb6o1KU30tAwxYDA5Bgkq"
            + "hkiG9w0BCRQxLB4qAFIAaQBjAGgAYQByAGQAIABFAGwAbABpAG8AdAB0ACcAcwAgACAASQBE"
            + "MCMGCSqGSIb3DQEJFTEWBBR2AaPdv+MzgKK8mkeDE1ApPioXuwAAAAAAADCABgkqhkiG9w0B"
            + "BwaggDCAAgEAMIAGCSqGSIb3DQEHATAjBgoqhkiG9w0BDAEGMBUEEP3EnVF5cvNW6lKoFhFC"
            + "cNACAQGggASCCrghmthRaKZsWVJbkU0u41JUpzFfhA0y8OfYkwGYzzbWquhD1UtzpHVWm3eX"
            + "AnsftZlaVIZYzPLRLqK2kiBvRhyV2D3lA8tfOxowP6d8x9FKoC5vIDTJVo0pc+VDdP0mB5A3"
            + "sFxtJa8re0ntMkTN9NTin0f1Xvg2aW2TKmytWc2EhsyonEL/ln2TxIpYFhNi/Avc2pkzJFsl"
            + "qvi6vr807F8wZgiNNpNATJl+RMi7c6v3bwydYV1lEdc9009no19Q3ILssLL5EMgSMunUYCzP"
            + "oVCMxi4QSbTkSSe/PkyoGVWtuXT5yv9Oe+/ThAXw/iXXJbR7yoDptxztLfvLoO1a69+eq8Rg"
            + "ubL3Fe96X9lnru0asi9RbxJwpKF5sCo7frusBP6ArFH1e2j80IuxCCvx/PhPMK0WFYVm3FsN"
            + "dmLM3Op5VqkuL4NIUmcXpsHtrNv6dreqhg2QEU8LuZQ3qT8vf1lq4MWaJ2UF32sMojfSeHsM"
            + "4MirxDv4D6Jv7Hm09AadiBWoCS+aRgnWep7wv9N5boSBXUX2xkpUlFfonbD5ilxDgwSju5vU"
            + "B0/sPAq2smGToapwnOPMRNkJ2m5doY1i438JDzSkiLFIhPmuEnMNHV8eIvwRpvRTSYiDgUmB"
            + "9505EYLb4D+j/PPs7bgTV9dAcf3W416fXa83gAhHTRXvcHQpIYicW/FtIyvFAicuCx9LqJrv"
            + "91v5z+YoUre/bhBbXA2WLM+JVOxZ4hbwaCr2W4GcPeUPjo3AnTvY7p2g+54UmWdWmbXGLQW5"
            + "rKcmN3HY6IHx7zvup3t9G7whFyo/zMcsoF8pk42GhvLrHnX/Fzl+ZUrAiB75Y8ym6wn7Sb1c"
            + "MtNYL+ikvfa3JDZOHcvcnK97TC91Dhx1CPMN1dOpnZhZVqVl4VxR5gznq8U3/8uRv4i5iaxN"
            + "9srqkr0+0wKthrLlLNAAJ+2lBE7IX254jyOrPO1gPNfCtpM2d4f86UiqmNmlhHuHfPWsQlf3"
            + "qtGo7+6rDSHjc9TSkaQfKJr6ynYn/pYJjW51hI5bHqVwDa68szCiHZwgyLFpgi3i+1KF/93a"
            + "+ghIPiyY7JYKJQSPb60Ym599aTpsfd+TBR2QvPovGkvHyCM8wZG3NdfgjRgvahj2NP5e8kza"
            + "Jgl+wm6eyuX3mtxu2NGVmCxFzPCNIrSXby4qaQcSoFzRv442cdgAsMT5lsy5vPi5kPSY8RUm"
            + "Sxyk7jS1BnI+f98RhIheaR8byVH6j9AAXB6/5/mS2xMvls6OZndzBw12YQtNELKBxiDxb6Bz"
            + "hcwYqIhCl0QcBw8mhZzgtYkU5J2Wg12XgNm6/GB7LWGyODTcut7uJtSzd6Lb0k/V6K+MjuH0"
            + "ACZqxfnV2CggkVG+ssC2a3BpQi8SHV+cY/+460lQ9qcjjFPGg7rnu6IeSGeMK/JtT4N1uMd0"
            + "z6KgwkluJq5Hc+lTVpONHnOk/uuoVYgmL4RXtmvabeYXXQDYtUKcVFVM01bGOCYlpKQGFNGP"
            + "CY63HEXoiE+k1FngzRHZET64qitC7toi4jLNODyhVvG5Mar9O5bNO3c9V87k6IIqFOecwzp5"
            + "xjPuj6umlCKbDtM33G1L2OpbH8gZs0VkOaczNPhUvfkAJ1WZG44Jes/F0g7oWkp5WrtBXree"
            + "ZB7u52TM7aD/GB00hies0qKxnqDbV7HCIZ8tYtDrRolV89N/Vl7HvINPWic5HHYa8ReSmU9j"
            + "PAKrh5SMinJa8Jmm0hSnu7hLd+hw76zNgVrpjJYT2NiiFK0LMCzszKItcb0Tbb3cTmjkzQml"
            + "JStMQ+SxV0IscUqhKYNUBXgIufl/LIj07L5HYq2nUm02/js0hRYccOjuvfgAojTxAyqKvEF8"
            + "V8TmBHS0EW++krmmPtzENu+nRcrOTOg1g0A1QmvJbFxVUuT194NFa0j0Mwi9nzFQ4IZAy9+I"
            + "MfGW8iOiIydUOzShWeRjI356i0ZqTlNKohm1rPyJ6Qjk/nQAC6q0RVCNmaXva5O1TE99EaB5"
            + "UnrmqOc2LOz0K3zQi0+Gzaqw0xCW4Itcbzz2Spr64aRtHS7vvhPcAKeSNUsj1pnh4fgvyQnL"
            + "tMVWeZ67zehmTFxdqAmSN06Ho81aRqbZcpoKxpjBwHNpdWUzT7Z+kINHdAK+T3Opccz/9Aik"
            + "zhVUh7pyBWiXG097Uo4iI4JoVwB+iA1mDDg2GlXrsZgRVgGADI2sJFw/9kYXbWF1WO13kt1T"
            + "FHB/tKfSH9gNbbSlvJR8i1DCUlu8/4TLxl5tB7kyvgaS7OOPQkvDM7MCzIfm+GpW5Azy0gVU"
            + "s9QI+ydvOdQrtDLu+G4S61c0Zm1m8hpwDNOF/eWUieC5hvCktKqemuKC13nQzU4gISwlcjlL"
            + "oRPMOoFnj6WW1UEACdyT3PIUdVGbAvXHWqzbl2I+SrC/kaLb5FLQqt8eddZ0kYj72keqFx9+"
            + "dJK20mNjcq/LLOrAnt8t+5KL9Ykz96VdOAh+FFwPlsBTs4iTexjdr9/dcTtbw9UQmKKLASEI"
            + "LlyDeABvFkLiPqvKoB0UBbPTl23go3HJKR7jr3F1KizuKi6f3PVB5GPV7j1UfvkgE3Pf0UjE"
            + "YmAqOWkkFwt6fvP1SuRz7kxCY7zDyRXGnEVj+N9TLOSR+DBgQfNO33vVkFvkaGO8cYUhDhOY"
            + "/8U2N2pv1WhNO9Wqw3sRJ/VggSMeNVDOQXv0pTF07uBfAqOPmzHwqMKZ6Pq/KSLUz6ovkTzV"
            + "jM/nPeUzopMU6Wqq+mcU01uPo10dR6+bBrJKNe52ITEsVFAupAJGj7msj9kjRBZTBa/1T1NX"
            + "G3FDRZ2vxWtaCwbbCFVITzlZZu6ne1L2fErYNfWe2tdz+/bJYmSNDcknw6g0FAV5ycp5BGay"
            + "XVtn2UGAfnLakBH5dDXpu4Z8Vl+nvIXYMmMNMaMyMOxDyIULsOlfxSd8OKgs0Xn+z0N+VXIP"
            + "70shdjaKsUn7RKPdYNWM3KBjnKjwX1DHUl5b6qv7/RUGAGBQIJYxsdpaignTV00dMPLVTODG"
            + "0BSGAwIkGl7ftZDRnPDbW6GledDIS+7nYzpJoKAy4VrUufB0mMtMKe/onPMTyC4coRv/oifb"
            + "aEwrQgoYj6jcBSL7S8Q9Z3hPpKNbVmkjRnhYrP3QUY/TrlEsJ7eW5bztLxiveUWaFd2h174/"
            + "5hDH1p10D3Tr6Y01y9oRbHNKjungVOht7Ukg9c22643NtVtWJz6EbdWUwwQMKj35T37gQGhf"
            + "NHMt68B0cXGYA6C3l3yIhsPeIuj7ooL4im9wmnsvSjfsaiybdrkyXeWuOb5OQ6bi6nTY/BKY"
            + "aCpfB7wX49JBLetagQQzKwm4ZppHcJyCkrSEOncWUO6TsupmNE4cUoEqVaiqwSln+L/C03X9"
            + "iurRSbXPDWKt97HeOkez+7wZrLtspJAzHxKv0t6vJmivIcvTK5+81i7B3aNIv+YWaGoFk/wc"
            + "Tcf34sw2F5ng5cTITXXmtTqu0Q+/lMy1XT2RKK6BCJupC9nuGpn+OL6tlOigS6noFoPkhAwK"
            + "QNkXWrqg3o9UUaoqBAoI6J12RbC8G9deZBVsQTSC8CfQrsSX1mgsZNZnEwPRDJOikt9UN6s1"
            + "X1JAsIxwwboAmPjYEd+ZWLP9bS03bHeTqTqvJBZ0VPmhvTW8TiQJ4xRW8g0Y1U9awoDOoC0T"
            + "gAQIUGIm+86v41sAAAAAAAAAAAAAAAAAAAAAAAAwNTAhMAkGBSsOAwIaBQAEFPqZHAPYPtvD"
            + "wXRtWN8wYo0JtOzpBBDGk0OKTZpCSD+Byp8ptG/RAAA=";

}
