package com.example.arnauddupeyrat.Animalis.WebRequest;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

import com.example.arnauddupeyrat.Animalis.Setting.SettingGloblal;

/**
 * AsyncTasck allow to avoid to use thread, only for short operation.
 * Created by arnauddupeyrat on 11/06/16.
 */
class BackgroundDataSync extends AsyncTask<String, Void , Object> {
    String mUrl;
    int request_type;
    OnServerRequestComplete listener;
    Class classATT;
    Object params;

    /**
     * Construct the object.
     * @param url
     * @param request_type
     */
    public BackgroundDataSync(Object aParams, String url, int request_type, OnServerRequestComplete aListener,Class aClass){
        this.mUrl = url;
        this.request_type = request_type;
        this.listener = aListener;
        this.classATT = aClass;
        this.params = aParams;
    }

    /**
     * after execute task in background.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Action performed in background.
     * @param urls
     * @return
     */
    @Override
    protected Object doInBackground(String... urls) {

        debug("TAG", "in Background, urls = " + urls.length);
        debug("TAG", "in Background, url = " + mUrl);
        debug("TAG", "in Background, class = " + classATT);
        debug("TAG", "in Background, request Type = " + request_type);

        Object response = null;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // do request in background.
        switch (request_type) {

            // Do a post Request
            case 2:
                try {
                    response = restTemplate.postForObject(mUrl,params,classATT);
                    Log.i("doInBackground()","POST request");
                    break;
                } catch(Exception e) {
                Log.e("Probleme POST", e.getMessage(), e);}

            // Do a get resquest.
            case 1:
                try {
                    response = restTemplate.getForObject(mUrl, classATT).toString();
                    Log.i("doInBackground()","call get service");
                    break;
                } catch(Exception e) {
                    Log.e("Probleme GET", e.getMessage(), e);}

            // Do a put request
            case 3:
                try {
                    restTemplate.put(mUrl,params);
                    Log.i("doInBackground()","call put service");
                    break;
                } catch(Exception e) {
                    Log.e("Probleme PUT", e.getMessage(), e);}

            // Do a delete response
            case 4:
                try {
                    restTemplate.delete(mUrl);
                    Log.i("doInBackground()","call delete service");
                } catch(Exception e) {
                    Log.e("Probleme DELETE", e.getMessage(), e);}
        }
        // return the response of the server.
        return response;

    }

    /**
     * execute after receive the response of the server.
     * @param
     */
    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);

        // if the String is empty, the request failled.
        if(object == null){
            listener.onFailed(0, "Data not found", mUrl);
        }

        // if the request is sended correctly.
        else{
            Map<String,Object> mapper = new HashMap();
            mapper.put(SettingGloblal.CODE, 200);
            mapper.put(SettingGloblal.RESPONSE, object);
            mapper.put(SettingGloblal.MYURL, mUrl);
            listener.onSucess(mapper);
        }
    }

    private void debug(String tag, String string) {
        Log.d(tag, string);
    }

}