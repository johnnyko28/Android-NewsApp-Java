package com.example.newsapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.ui.home.HomeFragment;
import com.example.newsapp.ui.home.HomeViewModel;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    ArrayList<ListItem> bookmarkListItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

//        ImageView bookmark_cardView = (ImageView) cardView.findViewById(R.id.bookmark_cardView);
//        //ImageButton bookmark_cardView_button = (ImageButton) bookmark_cardView.findViewById(R.id.bookmark_cardView);
//        //bookmark_cardView_button.setOnClickListener(new View.OnClickListener(){
//        bookmark_cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveData();
//            }
//        });
        //loadData();

        //listItems = new ArrayList<>();
        holder.bookmark_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You saved " +listItem.getHead(), Toast.LENGTH_LONG).show();
                ImageView bookmark_cardView = (ImageView) v.findViewById(R.id.bookmark_cardView);
                bookmark_cardView.setImageResource(R.drawable.bookmark_filled);
                bookmark_cardView.setColorFilter(Color.RED);
                //saveData(listItem);
            }
        });


        holder.textViewHead.setText(listItem.getHead());
        holder.section.setText(listItem.getSection());
        holder.time.setText(listItem.getTime());
        //holder.articleID.setText(listItem.getArticleID());
        Picasso.get()
                .load(listItem.getImageUrl())
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "You clicked " +listItem.getHead(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, detailed.class);
                intent.putExtra("ArticleID", listItem.getArticleID());
                intent.putExtra("WebTitle", listItem.getHead());
                intent.putExtra("SectionName", listItem.getSection());
                intent.putExtra("ImageUrl", listItem.getImageUrl());
                intent.putExtra("Date", listItem.getTime());
                intent.putExtra("WebUrl", listItem.getWebUrl());
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               // Toast.makeText(context, "You long clicked " +listItem.getHead(), Toast.LENGTH_LONG).show();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.show_dialog);
                dialog.setTitle(listItem.getHead());
                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText(listItem.getHead());

                ImageView titleImage = (ImageView) dialog.findViewById(R.id.image);
                Picasso.get()
                        .load(listItem.getImageUrl())
                        .into(titleImage);

                ImageView twitterImage = (ImageView) dialog.findViewById(R.id.dialog_twitter);
                Picasso.get()
                        .load(R.drawable.bluetwitter)
                        .into(twitterImage);
                twitterImage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        Uri uri = Uri.parse("https://twitter.com/intent/tweet?text=Check+out+this+link:%0a" + listItem.getWebUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                });
                final ImageView bookmarkImage = (ImageView) dialog.findViewById(R.id.dialog_bookmark);
                bookmarkImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "You saved " +listItem.getHead(), Toast.LENGTH_LONG).show();
                        bookmarkImage.setImageResource(R.drawable.bookmark_filled);
                        bookmarkImage.setColorFilter(Color.RED);
                        //saveData(listItem);
                    }
                });

//                ImageView bookmarkImage = (ImageView) dialog.findViewById(R.id.dialog_bookmark);
//                Picasso.get()
//                        .load(R.drawable.bookmark)
//                        .into(bookmarkImage);

                dialog.show();
                return false;
            }
        });

    }
//    private void saveData(ListItem listItem){
//        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(listItem);
//        editor.putString("bookmarked_articles", json);
//        editor.apply();
//        Log.d("save msg", json);
////        Log.d("save msg", listItem.getArticleID());
//    }
//    private void loadData(){
//        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("bookmarked_articles", null);
//        Type type = new TypeToken<ArrayList<ListItem>>() {}.getType();
//        bookmarkListItems = gson.fromJson(json, type);
//
//        if(bookmarkListItems == null) {
//            bookmarkListItems = new ArrayList<>();
//        }
//        Log.d("load msg", String.valueOf(bookmarkListItems));
//    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView section;
        public TextView time;
        public ImageView imageView;
        public LinearLayout cardView;
        public TextView articleID;
        public ImageView bookmark_cardView;

        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            section = (TextView) itemView.findViewById(R.id.section);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            time = (TextView) itemView.findViewById(R.id.time);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardView);
            context =itemView.getContext();
            bookmark_cardView = (ImageView) itemView.findViewById(R.id.bookmark_cardView);
            //articleID = (TextView) itemView.findViewById(R.id.section);
        }
    }
}
