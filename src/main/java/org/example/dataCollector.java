package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class dataCollector {






    public  JsonObject getJObject(String Surl) throws IOException {
        URL url = new URL(Surl);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        return jsonobj;
    }

    public double[] getRate(JsonObject ob , ArrayList<String> ar){
        String[] curcodeARR = ar.toArray(new String[0]);

        JsonElement elkeys = ob.get("rates");
        JsonObject rates = elkeys.getAsJsonObject();
        double[] rate = new double[curcodeARR.length];

        int j = 0;
        for (String i : curcodeARR){

            rate[j] = rates.get(i).getAsDouble();
            j++;
        }

        return rate;
    }

    public ArrayList<String> getCode(JsonObject ob){

        JsonElement elkeys = ob.get("rates");
        JsonObject rates = elkeys.getAsJsonObject();
        Set<String> curKeys = rates.keySet();
        String[] curcodeARR = curKeys.toArray(new String[0]);

        ArrayList<String> arr  = new ArrayList<String>();

        for (String i : curcodeARR){
            arr.add(i);
        }



        return arr;



    }





}
