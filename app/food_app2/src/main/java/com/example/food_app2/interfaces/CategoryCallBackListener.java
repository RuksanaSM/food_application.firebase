package com.example.food_app2.interfaces;

import com.example.food_app2.model.BestDeals_model;
import com.example.food_app2.model.CategoryModel;

import java.util.List;

public interface CategoryCallBackListener {

    void onCategoryLoadSuccess(List<CategoryModel> categoryModelList);
    void onCategoryLoadFailed(String message);
}
