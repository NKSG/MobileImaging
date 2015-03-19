package com.example.assios.mobimaging;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by assios on 3/18/15.
 */
public class URLFetch {

    /**
     * Used for getting information
     * from a Rest API
     */

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null) reader.close();
        }
    }


    public static String getMove(FEN fen) {

        String fen_string = fen.toString();

        String json_data = null;

        try {
            json_data = URLFetch.readUrl("localhost:8080" + fen_string);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        Gson gson = new Gson();
        HashMap < String, String > move = new Gson().fromJson(json_data, new TypeToken < HashMap < String, String >> () {}.getType());

        String random_move = move.get("random_move");

        return random_move;

    }
}
