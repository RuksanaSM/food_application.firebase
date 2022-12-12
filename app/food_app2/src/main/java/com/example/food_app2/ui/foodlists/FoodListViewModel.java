package com.example.food_app2.ui.foodlists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food_app2.common.Commons;
import com.example.food_app2.model.FoodModel;

import java.util.List;

public class FoodListViewModel extends ViewModel {

    private  MutableLiveData<List<FoodModel>> mutableLiveDataFoodList;

    public FoodListViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is slideshow fragment");

    }

//    public LiveData<String> getText() {
//        return mText;
//    }


    public MutableLiveData<List<FoodModel>> getMutableLiveDataFoodList() {
        if (mutableLiveDataFoodList==null)
            mutableLiveDataFoodList=new MutableLiveData<>();
        mutableLiveDataFoodList.setValue(Commons.categorySelected.getFood());
        return mutableLiveDataFoodList;
    }
}