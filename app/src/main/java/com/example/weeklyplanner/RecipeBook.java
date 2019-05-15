
        package com.example.weeklyplanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

        public class RecipeBook extends AppCompatActivity {
            ArrayList<String> allRecipiesString;
            ArrayList<String> newNames;
            ArrayList<String> existingNames;
            ArrayList<String> allRecipiesNewString;
            File savedRecipies;
            ListView recipe_list;
            Button back;
            ArrayList<String> toRemove;
            ArrayList<String> namesToRemove;
            File newlyReceivedRecipies;

            @SuppressLint("LongLogTag")
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_recipe_book);
                recipe_list= findViewById(R.id.recipeBookListView);
                namesToRemove = new ArrayList<>();
                allRecipiesNewString = getIntent().getStringArrayListExtra("allRecipiesForBook");
                savedRecipies = new File(getApplicationContext().getFilesDir(),"savedBook.txt");
                toRemove = getIntent().getStringArrayListExtra("toRemove");
                toRemove = MainScreen.initialise(toRemove);
                allRecipiesString = ShoppingList.loadArray(savedRecipies);
                Log.i(String.valueOf(allRecipiesString),"allRecipiesString");
                newNames = MainScreen.initialise(newNames);
                existingNames = MainScreen.initialise(existingNames);
                for(int i = 0; i<allRecipiesNewString.size();i++){
                    String[] splitting = allRecipiesNewString.get(i).split(";");
                    newNames.add(splitting[0]);
                }
                for(int i = 0; i<allRecipiesString.size();i++){
                    String[] splitting = allRecipiesString.get(i).split(";");
                    existingNames.add(splitting[0]);
                }
                ArrayList<String> copy = newNames;
                for(int i = 0; i<newNames.size();i++){

                    for(int j=i+1; j<copy.size();j++){
                        if(newNames.get(i).equals(copy.get(j))){
                            newNames.remove(i);
                            allRecipiesNewString.remove(i);
                        }
                    }
                }
                copy = newNames;
                for(int i = 0; i<newNames.size();i++){

                    for(int j=i+1; j<copy.size();j++){
                        if(newNames.get(i).equals(copy.get(j))){
                            newNames.remove(i);
                            allRecipiesNewString.remove(i);
                        }
                    }
                }
                copy = newNames;
                for(int i = 0; i<newNames.size();i++){

                    for(int j=i+1; j<copy.size();j++){
                        if(newNames.get(i).equals(copy.get(j))){
                            newNames.remove(i);
                            allRecipiesNewString.remove(i);
                        }
                    }
                }
                Log.i(String.valueOf(allRecipiesNewString),"allRecipiesNewString before adding it");
                Log.i(String.valueOf(allRecipiesString),"before adding the new stuff");
                allRecipiesString.addAll(allRecipiesNewString);
                Log.i(String.valueOf(allRecipiesString),"allRecipiesString after adding new stuff");
                for(int i=0;i<toRemove.size();i++){
                    String[] temp = toRemove.get(i).split(";");
                    if(temp!=null) {
                        namesToRemove.add(temp[0]);
                    }
                }

                for(int i=0; i<newNames.size();i++){
                    for(int j=0; j<existingNames.size();j++) {

                        if (newNames.get(i).equals(existingNames.get(j))) {
                            allRecipiesString.remove(j);
                        }
                    }
                }
                existingNames=new ArrayList<>();
                for(int i = 0; i<allRecipiesString.size();i++){
                    String[] splitting = allRecipiesString.get(i).split(";");
                    existingNames.add(splitting[0]);
                }
                Log.i(String.valueOf(allRecipiesString),"after updating");
                Log.i(String.valueOf(existingNames),"existing names before checkignToRemove");
                Log.i(String.valueOf(namesToRemove),"name that should be removed");

                for(int i=0;i<existingNames.size();i++){
                    for(int j=0;j<namesToRemove.size();j++) {
                        if (existingNames.get(i).equals(namesToRemove.get(j))) {
                            existingNames.remove(i);
                            allRecipiesString.remove(i);
                        }
                    }
                }

                Log.i(String.valueOf(allRecipiesString),"allRecipiesString 3");
                recipe_list.setOnItemClickListener(new RecipeBookAdapter.OnItemAutoCompleteAdder());
                ShoppingList.saveArray(allRecipiesString,savedRecipies);
                recipe_list.setAdapter(new RecipeBookAdapter(this, allRecipiesString, savedRecipies));
                back=findViewById(R.id.backToMealAdder);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i1=new Intent(com.example.weeklyplanner.RecipeBook.this,MealAdder.class);
                        ShoppingList.saveArray(allRecipiesString,savedRecipies);
                        startActivity(i1);
                    }
                });
            }
        }