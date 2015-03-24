package com.bernabeborrero.bluetea;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bernabé Borrero on 23/03/15.
 */
public abstract class BlueTea {

    private static final String API_URL = "http://bluetea.tk/bluetea-api.php";

    private static HashMap<Integer, Integer> codes;
    private static int previousStepCode;
    private static String appName;

    public static void startSession(String applicationName) {
        previousStepCode = 0;
        codes = new HashMap<>();
        appName = applicationName;
    }

    public static void logStep(int code, String description) {
        if(codes != null) {
            Integer parent = codes.get(code);

            parent = (parent == null) ? previousStepCode : parent;
            codes.put(code, parent);
            previousStepCode++;

            new SendStepToAPI().execute(new Step(code, parent, description));
        }
    }

    private static class SendStepToAPI extends AsyncTask<Step, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Step... params) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(API_URL);
            HttpResponse httpResponse = null;
            int response = 0;
            try {
                List<NameValuePair> parameters = new ArrayList<>();
                parameters.add(new BasicNameValuePair("appName", appName));
                parameters.add(new BasicNameValuePair("code", String.valueOf(params[0].getCode())));
                parameters.add(new BasicNameValuePair("parentStep", String.valueOf(params[0].getParentStep())));
                parameters.add(new BasicNameValuePair("description", String.valueOf(params[0].getDescription())));

                httpPost.setEntity(new UrlEncodedFormEntity(parameters));

                httpResponse = httpClient.execute(httpPost);
                response = Integer.parseInt(EntityUtils.toString(httpResponse.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response == 1;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {

            }
        }
    }

    private static class Step {
        int code, parentStep;
        String description;

        public Step(int code, int parentStep, String description) {
            this.code = code;
            this.parentStep = parentStep;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getParentStep() {
            return parentStep;
        }

        public void setParentStep(int parentStep) {
            this.parentStep = parentStep;
        }
    }

}
