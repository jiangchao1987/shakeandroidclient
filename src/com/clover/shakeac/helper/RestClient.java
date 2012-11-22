package com.clover.shakeac.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.Context;
import android.util.Log;

import com.clover.shakeac.Constant;
import com.clover.shakeac.R;

public class RestClient {
	
	private static final int CONNECT_TIMEOUT = 20000;
	private static final int SO_TIMEOUT = 20000;
	private static final int SOCKET_BUFFER_SIZE = 8192;
	private static String contextPath = null;
	private HttpParams params = null;
	private HttpClient client = null;

	public RestClient(Context context) {
		params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		HttpConnectionParams.setSocketBufferSize(params, SOCKET_BUFFER_SIZE);
		HttpClientParams.setRedirecting(params, true);
		HttpProtocolParams.setUserAgent(params, Constant.AGENT);

		contextPath = context.getResources().getString(R.string.host_uri);
	}

	public static RestClient newInstance(Context context) {
		return new RestClient(context);
	}

	public void close() {
		if (client != null) {
			client.getConnectionManager().shutdown();
			client = null;
		}
	}

	private HttpClient getClient() {
		if (client == null) {
			client = new DefaultHttpClient(params);
		}
		return client;
	}
	
	public String login(String username, String password) {
        try {
            HttpGet get = new HttpGet(contextPath + String.format("/login/udid/%s/password/%s", username, password));
            HttpResponse resp = null;
            synchronized (this) {
                resp = getClient().execute(get);
            }
            int sc = resp.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == sc) {
                InputStream is = resp.getEntity().getContent();
                return inputStreamToString(is);
            }
            else {
                Log.e(Constant.TAG, "Failed to login - invalid HTTP status code: " + sc);
            }
        }
        catch (Exception e) {
            Log.e(Constant.TAG, "Failed to login: ", e);
        }
        return null;
    }
	
	public static String inputStreamToString(InputStream is) throws IOException {
        String json = null;
        try {
            json = new String();
            ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            byte[] str_b = new byte[1024];
            int i = -1;
            while ((i = is.read(str_b)) > 0) {
                outputstream.write(str_b, 0, i);
            }
            json = outputstream.toString();
            json = StringEscapeUtils.unescapeHtml(json);
        }
        catch (Exception e) {
            Log.e(Constant.TAG, "Failed to extrace inputstream to String: ", e);
        }
        return json;
    }
	
}
