package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        JSONObject sandwichJSONObject;

        try {
            sandwichJSONObject = new JSONObject(json);
            JSONObject name = sandwichJSONObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsJSONArray = name.getJSONArray("alsoKnownAs");
            String placeOfOrigin = sandwichJSONObject.getString("placeOfOrigin");
            String description = sandwichJSONObject.getString("description");
            String image = sandwichJSONObject.getString("image");
            JSONArray ingredientsJSONArray = sandwichJSONObject.getJSONArray("ingredients");

            sandwich.setMainName(mainName);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setAlsoKnownAs(JsonUtils.getListFromJSONArray(alsoKnownAsJSONArray));
            sandwich.setIngredients(JsonUtils.getListFromJSONArray(ingredientsJSONArray));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return sandwich;
    }

    public static List<String> getListFromJSONArray(JSONArray jsonArray) {
        List<String> strings = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                strings.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }
}


