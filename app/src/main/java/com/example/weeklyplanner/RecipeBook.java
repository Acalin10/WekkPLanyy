
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
            ArrayList<Recipe> allStringsRec;
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
                Log.i(String.valueOf(allRecipiesNewString),"allRecipiesNewString at arrvial");
                toRemove = getIntent().getStringArrayListExtra("toRemove");
                toRemove = MainScreen.initialise(toRemove);
                allStringsRec=MainScreen.initialise(allStringsRec);
                allRecipiesString = ShoppingList.loadArray(savedRecipies);
                Log.i(String.valueOf(allRecipiesString),"loaded recipies");
                newNames = MainScreen.initialise(newNames);
                existingNames = MainScreen.initialise(existingNames);
                for(int i = 0; i<allRecipiesNewString.size();i++){
                    String[] splitting = allRecipiesNewString.get(i).split(";");
                    newNames.add(splitting[0]);
                }
                Log.i(String.valueOf(newNames),"newNames at arrival");
                for(int i = 0; i<allRecipiesString.size();i++){
                    String[] splitting = allRecipiesString.get(i).split(";");
                    existingNames.add(splitting[0]);
                }
                Log.i(String.valueOf(existingNames),"existingNames at loaded");
                ArrayList<String> copy = newNames;
                for(int i = 0; i<newNames.size();i++){

                    for(int j=i+1; j<copy.size();j++){
                        if(newNames.get(i).equals(copy.get(j))){
                            newNames.remove(i);
                            allRecipiesNewString.remove(i);
                        }
                    }
                }
                Log.i(newNames+" || "+allRecipiesNewString,"new names plus Recipies after first check");
                copy = newNames;
                for(int i = 0; i<newNames.size();i++){

                    for(int j=i+1; j<copy.size();j++){
                        if(newNames.get(i).equals(copy.get(j))){
                            newNames.remove(i);
                            allRecipiesNewString.remove(i);
                        }
                    }
                }
                Log.i(newNames+" || "+allRecipiesNewString,"new names plus Recipies after second check");
                copy = newNames;
                for(int i = 0; i<newNames.size();i++){

                    for(int j=i+1; j<copy.size();j++){
                        if(newNames.get(i).equals(copy.get(j))){
                            newNames.remove(i);
                            allRecipiesNewString.remove(i);
                        }
                    }
                }
                Log.i(newNames+" || "+allRecipiesNewString,"new names plus Recipies after third check");
                allRecipiesString.addAll(allRecipiesNewString);
                Log.i(String.valueOf(allRecipiesString),"allRecipiesString after adding new recipies");
                for(int i=0;i<toRemove.size();i++){
                    String[] temp = toRemove.get(i).split(";");
                    if(temp!=null) {
                        namesToRemove.add(temp[0]);
                    }
                }
                Log.i(String.valueOf(namesToRemove),"names that should be removed");
                existingNames=new ArrayList<>();
                for(int i = 0; i<allRecipiesString.size();i++){
                    String[] splitting = allRecipiesString.get(i).split(";");
                    existingNames.add(splitting[0]);
                }
                ArrayList<String> copy3 =existingNames;
                ArrayList<String> duplicates=new ArrayList<>();
                for(int i=0;i<existingNames.size();i++){
                    for(int j=i+1;j<copy3.size();j++){
                        if(existingNames.get(i).equals(copy3.get(j))){
                            duplicates.add(allRecipiesString.get(i));
                        }
                    }
                }
                Log.i(String.valueOf(duplicates),"duplicates");
                Log.i(String.valueOf(allRecipiesString),"before removing duplicates");
                    allRecipiesString.removeAll(duplicates);
                allRecipiesString.addAll(duplicates);
                for(int i=0;i<allRecipiesString.size();i++){
                    allStringsRec.add(Recipe.toRecipe(allRecipiesString.get(i)));
                }
                ArrayList<Recipe> copyy = allStringsRec;
                for(int i=0;i<allStringsRec.size();i++){
                    for(int j=i+1;j<copyy.size();j++){
                        if(allStringsRec.get(i).getName().equals(copyy.get(j).getName())){
                            allStringsRec.remove(i);
                        }
                    }
                }
                allRecipiesString=new ArrayList<>();
                for(int i=0;i<allStringsRec.size();i++){
                    allRecipiesString.add(allStringsRec.get(i).toString());
                }
                Log.i(String.valueOf(allRecipiesString),"allRecipiesString after removal of duplicates");
                existingNames=new ArrayList<>();
                for(int i = 0; i<allRecipiesString.size();i++){
                    String[] splitting = allRecipiesString.get(i).split(";");
                    existingNames.add(splitting[0]);
                }
                Log.i(String.valueOf(existingNames),"existingNames after splitting");
                for(int i=0;i<existingNames.size();i++){
                    for(int j=0;j<namesToRemove.size();j++) {
                        if (existingNames.get(i).equals(namesToRemove.get(j))) {
                            existingNames.remove(i);
                            allRecipiesString.remove(i);
                        }
                    }
                }
                Log.i(existingNames+" || "+allRecipiesString,"existingNames and allRecipiesString before adapter");
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