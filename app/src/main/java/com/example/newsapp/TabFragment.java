package com.example.newsapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.newsapp.ui.home.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {

    int position;
    private TextView textView;
    private HomeViewModel homeViewModel;
    public String section;
    //public String URL_DATA = "http://nodejsapp-johnnyko.us-east-1.elasticbeanstalk.com/world";
    //public String URL_DATA = "http://nodejsapp-johnnyko.us-east-1.elasticbeanstalk.com/";
    public String URL_DATA = "http://content.guardianapis.com/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
       // public String URL_DATA = "http://nodejsapp-johnnyko.us-east-1.elasticbeanstalk.com/";
        switch (position) {
            case 0: section = "world";
                break;
            case 1: section = "business";
                break;
            case 2: section = "politics";
                break;
            case 3: section = "sport";
                break;
            case 4: section = "technology";
                break;
            case 5: section = "science";
                break;
        }
        URL_DATA = URL_DATA + section + "?api-key=ee45af7a-ea6c-49ca-8925-25269dab0e0d&show-blocks=all";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.headlines_news, container, false);
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

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        textView = (TextView) view.findViewById(R.id.textView_tab);
////
////        textView.setText("Tab " + (position + 1));
//                switch(position) {
//                    case 0: section = "world";
//                    break;
//                }
//                URL_DATA = "http://nodejsapp-johnnyko.us-east-1.elasticbeanstalk.com/" + section;
//    }

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
                                            o.getJSONObject("blocks").getJSONObject("main").getJSONArray("elements").getJSONObject(0).getJSONArray("assets").getJSONObject(0).getString("file"),
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