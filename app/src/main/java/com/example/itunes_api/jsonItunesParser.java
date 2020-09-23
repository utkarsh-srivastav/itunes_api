package com.example.itunes_api;

import com.example.itunes_api.Model.iTuneStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JsonItunesParser {

    public static iTuneStuff getItunesStuff(String url) throws JSONException{
        iTuneStuff iTuneStuff = new iTuneStuff();

        JSONObject itunesStuffJsonObject = new JSONObject(url);

        JSONArray resultsJsonArray = itunesStuffJsonObject.getJSONArray("results");
        JSONObject artistObject = resultsJsonArray.getJSONObject(0);
        iTuneStuff.setType(getString("wrapperType", artistObject));
        iTuneStuff.setKind(getString("kind", artistObject));
        iTuneStuff.setArtistName(getString("artistName", artistObject));
        iTuneStuff.setCollectionName(getString("collectionName", artistObject));
        iTuneStuff.setArtistViewUrl(getString("artworkUrl100", artistObject));
        iTuneStuff.setTrackName(getString("trackName", artistObject));


        return iTuneStuff;
        // job of this method is to download thejson values and assign its values.
    }

    private static JSONObject getJsonObject(String tagName, JSONObject jsonObject) throws JSONException{
        return jsonObject.getJSONObject(tagName);
    }
    private static String getString(String tagName, JSONObject jsonObject) throws JSONException{
        return jsonObject.getString(tagName);
    }
    private static float getFloat(String tagName, JSONObject jsonObject) throws JSONException{
        return (float) jsonObject.getDouble(tagName);
    }
    private static int getInt(String tagName, JSONObject jsonObject) throws JSONException{
        return jsonObject.getInt(tagName);
    }
    private static boolean getBoolean(String tagName, JSONObject jsonObject) throws JSONException{
        return jsonObject.getBoolean(tagName);
    }
}
