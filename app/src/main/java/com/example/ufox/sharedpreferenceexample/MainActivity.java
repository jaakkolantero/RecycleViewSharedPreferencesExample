package com.example.ufox.sharedpreferenceexample;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //TODO 3. Variables used with shared preferences
    private Gson gson;
    private SharedPreferences mSharedPreferences;
    private ArrayList<MyItem> mItemArrayList;

    //TODO 4. Variables used with recyclerview
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO 1. Add Gson depedency to build.gradle (Module: app) and sync
        //TODO 2. Add Recyclerview depedency to build.gradle (Module: app) and sync
        //TODO 6. Create data item layout my_item.xml
        //TODO 9. Add RecycleView to activity_main.xml layout

        //TODO 10. Setup everything and populate array with dummy data
        //Init and populate ArrayList
        mItemArrayList = new ArrayList<>();
        mItemArrayList.add(new MyItem("John"));
        mItemArrayList.add(new MyItem("Paul"));
        mItemArrayList.add(new MyItem("Johnny"));
        mItemArrayList.add(new MyItem("Paulina"));

        //init Gson
        gson = new Gson();
        //Use default Shared preferences (com.example.ufox.sharedpreferenceexample_preferences)
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        // specify an adapter
        mRecyclerViewAdapter = new MyAdapter(mItemArrayList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }

    //TODO 11. Get data from shared preferences
    @Override
    protected void onResume() {
        super.onResume();



        String jsonArray = mSharedPreferences.getString("myItems","");
        if(!jsonArray.equalsIgnoreCase(""))
        {
            if (!mItemArrayList.isEmpty()) {
                mItemArrayList.clear();
            }

            mItemArrayList.addAll( (ArrayList<MyItem>) gson.fromJson(jsonArray,new TypeToken<ArrayList<MyItem>>(){}.getType()) );
            Log.i("MainActivity", "onResume: " + jsonArray);
            Log.i("MainActivity", "onResume: " + mItemArrayList.get(0));
            mRecyclerViewAdapter.notifyItemRangeChanged(0,mItemArrayList.size()-1);
        }

    }

    //TODO 12. Save data to shared preferences
    @Override
    protected void onPause() {
        super.onPause();

        Log.i("MainActivity", "onPause: ");

        //Edit shared preferences
        SharedPreferences.Editor myEditor = mSharedPreferences.edit();
        //Convert ArrayList to Json array
        String toJson = gson.toJson(mItemArrayList);
        //myEditor.clear()
        //Put Json array to shared preferences
        myEditor.putString("myItems",toJson);
        //Apply changes to shared preferencess
        myEditor.apply();
    }
}
