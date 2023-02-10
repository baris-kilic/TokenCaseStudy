package com.example.last;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//SSL Class for disable SSL server verification
public class SSL {
    public static void ssl() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            @Override public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
            @Override public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
        } };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true
            );
        } catch (KeyManagementException e) {} catch (NoSuchAlgorithmException e) {}
    }
}
