package WebRequest;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import WebRequest.OnServerRequestComplete;

/**
 * Created by yubraj on 7/30/15.
 */
public class ServiceHandler {

    private static final String TAG = "Server Request";
    OnServerRequestComplete listener;

    // constructor empty.
    public ServiceHandler (){}


    /**
     * Execute a request on server.
     * @param url         Url targeted by the request
     * @param requestType Type of the request
     * @param listener    Listerner of the request, define what to do in sucess case.
     */
    public void doServerRequest(Object params, String url, int requestType, OnServerRequestComplete listener, Class<Object> aClass){

        debug("ServerRequest", "server request called, url  = " + url);
        if(listener != null){
            this.listener = listener;
        }
        try {
            new BackgroundDataSync(params,url,requestType, this.listener,aClass).execute();
            debug(TAG , " asnyc task called");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doServerRequest(Object params, String url, int requestType, Class aClass){
        doServerRequest(params, url, requestType, this.listener,aClass);
    }


    public void setOnServerRequestCompleteListener(OnServerRequestComplete listener){
        this.listener = listener;
    }



    private void debug(String tag, String string) {
        Log.d(tag, string);
    }
}
