package com.example.mobilebanking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class ConnectUtils {
	public HttpClient getNewHttpClient() {
	     try {
	         KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	         trustStore.load(null, null);
	         System.out.println("......................LOADING STRICT_HOSTNAME_VERIFIER");
	         SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        // sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	         sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
	        // sf.se
	         
	         HttpParams params = new BasicHttpParams();
	         
//	         HttpParams params = client.getParams();
//	 		
//			    HttpConnectionParams.setConnectionTimeout(params, 30000);
	         
	         HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	         HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	         int timeOut = 30000;
	         HttpConnectionParams.setConnectionTimeout(params, timeOut);

	         SchemeRegistry registry = new SchemeRegistry();
	         registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	         registry.register(new Scheme("https", sf, 443));

	         ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	         return new DefaultHttpClient(ccm, params);
	     } catch (Exception e) {
	         return new DefaultHttpClient();
	     }
	}
	
	public class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

   public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
       super(truststore);

       TrustManager tm = new X509TrustManager() {
           public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
           }

           public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
           }

           public X509Certificate[] getAcceptedIssuers() {
               return null;
           }
       };

       sslContext.init(null, new TrustManager[] { tm }, null);
   }

   @Override
   public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
       return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
   }

   @Override
   public Socket createSocket() throws IOException {
       return sslContext.getSocketFactory().createSocket();
   }
}
}
