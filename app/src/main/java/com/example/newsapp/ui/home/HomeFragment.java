package com.example.newsapp.ui.home;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.ListItem;
import com.example.newsapp.MyAdapter;
import com.example.newsapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String URL_DATA = "https://content.guardianapis.com/search?order-by=newest&show-fields=starRating,headline,thumbnail,short-url&api-key=ee45af7a-ea6c-49ca-8925-25269dab0e0d";
    //private static final String URL_DATA = "http://nodejsapp-johnnyko.us-east-1.elasticbeanstalk.com/latest";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    private HomeViewModel homeViewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home_news, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems = new ArrayList<>();
        //loadData();
        mSwipeRefreshLayout = root.findViewById(R.id.swiperefresh_items);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecylerViewData();
                mSwipeRefreshLayout.setRefreshing(false);
                // Your code to make your refresh action
                // CallYourRefreshingMethod();

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(loadRecylerViewData().isRefreshing()) {
//                            mySwipeRefreshLayout.setRefreshing(false);
//                        }
//                    }
//                }, 1000);
            }
        });


        loadRecylerViewData();
//        for(int i = 0; i <= 10; i++) {
//            ListItem listItem = new ListItem(
//                    "heading" + i+1,
//                    "loresffsdf",
//                    "test"
//
//            );
//            listItems.add(listItem);
//        }
//        adapter = new MyAdapter(listItems, this);
//        recyclerView.setAdapter(adapter);
        //mSwipeRefreshLayout = root.findViewById(R.id.recyclerView);

        return root;
    }



    private void loadRecylerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching News");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject response = jsonObject.getJSONObject("response");

                            JSONArray array = response.getJSONArray("results");

                            //JSONArray  array = new JSONArray(s);


                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);


                                try {
                                    ListItem item = new ListItem(
                                            o.getString("webTitle"),
                                            o.getString("sectionName"),
                                            o.getString("webPublicationDate"),
                                            o.getJSONObject("fields").getString("thumbnail"),
                                            o.getString("id"),
                                            o.getString("webUrl")
                                    );
                                    listItems.add(item);
                                } catch(Exception e) {
                                    ListItem item = new ListItem(
                                            o.getString("webTitle"),
                                            o.getString("sectionName"),
                                            o.getString("webPublicationDate"),
                                            "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png",
                                            o.getString("id"),
                                            o.getString("webUrl")
                                    );
                                    listItems.add(item);
                                }


                            }



                            adapter = new MyAdapter(listItems, getActivity().getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }



}