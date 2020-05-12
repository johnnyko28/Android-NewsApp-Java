package com.example.newsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.components.IMarker;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;

public class detailed extends AppCompatActivity {


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);



        //get the current intent
        Intent intent = getIntent();
//get the attached extras from the intent
//we should use the same key as we used to attach the data.
        String articleID = intent.getStringExtra("ArticleID");
        final String head = intent.getStringExtra("WebTitle");
        String imageUrl = intent.getStringExtra("ImageUrl");
        String sectionName = intent.getStringExtra("SectionName");
        String date = intent.getStringExtra("Date");

        String URL_DATA = "https://content.guardianapis.com/" + articleID +
                "?api-key=ee45af7a-ea6c-49ca-8925-25269dab0e0d&show-blocks=all";

        String webUrl = intent.getStringExtra("WebUrl");

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //white
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FFFFFF"));
        //black
        ColorDrawable colorDrawable1
                = new ColorDrawable(Color.parseColor("#000000"));
        actionBar.setBackgroundDrawable(colorDrawable);

        Spannable text = new SpannableString(head);
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);

//        actionBar.setTitle(Html.fromHtml("<font color='#000000'></font>"));
//        getSupportActionBar().setTitle(head);

        Drawable backArrow = getResources().getDrawable(R.drawable.icons8_left_24);
        backArrow.setColorFilter(getResources().getColor(R.color.colorSection), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        //actionBar.hide();







        TextView textView = (TextView) findViewById(R.id.titleDetailed);
        textView.setText(head);

        TextView sectionView = (TextView) findViewById(R.id.sectionDetailed);
        sectionView.setText(sectionName);

        TextView dateView = (TextView) findViewById(R.id.dateDetailed);
        //String FormatedDate = DateFormat.getDateInstance().format(date);
        dateView.setText(date);

        ImageView imageView = (ImageView) findViewById(R.id.imageViewDetailed);
        Picasso.get()
                .load(imageUrl)
                .into(imageView);

        TextView fullArticleView = (TextView) findViewById(R.id.viewFullArticle1);
        fullArticleView.setText(webUrl);
        //fullArticleView.setLinksClickable(true);
        //fullArticleView.setAutoLinkMask(Linkify.ALL);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching News");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject response = jsonObject.getJSONObject("response");
                            JSONObject array = response.getJSONObject("content");
                            JSONObject array1 = array.getJSONObject("blocks");
                            JSONArray array2 = array1.getJSONArray("body");

                            JSONObject o = array2.getJSONObject(0);

                            TextView descriptionView = (TextView) findViewById(R.id.description);
                           descriptionView.setText(Html.fromHtml((o.getString("bodyHtml")), Html.FROM_HTML_MODE_LEGACY));
                            //descriptionView.setText(o.getString("bodyHtml"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//
//        TextView descriptionView = (TextView) findViewById(R.id.description);
//        descriptionView.setText(head);
    }

//    private void loadViewData() {
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Fetching News");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                URL_DATA,
//
//
//                JSONObject jsonObject = new JSONObject(s);
//        JSONObject response = jsonObject.getJSONObject("response");
//        JSONArray array = response.getJSONArray("results");
//                );
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
