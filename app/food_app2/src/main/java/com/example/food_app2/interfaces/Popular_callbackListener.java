package com.example.food_app2.interfaces;

import com.example.food_app2.model.Popular_Category_model;

import java.util.List;

public interface Popular_callbackListener {

    void onPopularLoadSuccess(List<Popular_Category_model> popularCategoryModels);
    void onPopularLoadFailed(String message);



}
