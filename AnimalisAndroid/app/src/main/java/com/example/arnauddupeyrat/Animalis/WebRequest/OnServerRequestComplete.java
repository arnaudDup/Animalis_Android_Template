package com.example.arnauddupeyrat.Animalis.WebRequest;

import java.util.Map;

/**
 * Created by arnauddupeyrat on 11/06/16.
 */
public interface OnServerRequestComplete {

        void onSucess(Map<String,Object> mapper);
        void onFailed(int status_code, String mesage, String url);
}
