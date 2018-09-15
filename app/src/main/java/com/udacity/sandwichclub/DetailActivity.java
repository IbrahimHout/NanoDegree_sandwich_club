package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;

    TextView alsoKnownTv;
    TextView descriptionTV;
    TextView ingredientsTv;
    TextView originTv;

    LinearLayout alsoKwonAsLinearLayout;
    LinearLayout descriptionLayout;
    LinearLayout ingredientsLayout;
    LinearLayout placeOfOriginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.error)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void initViews() {
        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTV = findViewById(R.id.description_tv);

        alsoKwonAsLinearLayout = findViewById(R.id.alsoKnownAsLinearLayout);
        descriptionLayout = findViewById(R.id.descriptionLinearLayout);
        ingredientsLayout = findViewById(R.id.ingredientsLinearLayout);
        placeOfOriginLayout = findViewById(R.id.placeOfOriginLinearLayout);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs()!=null && sandwich.getAlsoKnownAs().size()>0){
            alsoKnownTv.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownTv.append(" ,"+sandwich.getAlsoKnownAs().get(i));
            }
        }else {
            alsoKwonAsLinearLayout.setVisibility(View.GONE);
        }


        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            ingredientsTv.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                ingredientsTv.append(" ," + sandwich.getIngredients().get(i));
            }
        } else {
            ingredientsLayout.setVisibility(View.GONE);
        }

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            descriptionTV.setText(sandwich.getDescription());

        } else {
            descriptionLayout.setVisibility(View.GONE);
        }
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(sandwich.getPlaceOfOrigin());

        } else {
            placeOfOriginLayout.setVisibility(View.GONE);
        }



    }
}
