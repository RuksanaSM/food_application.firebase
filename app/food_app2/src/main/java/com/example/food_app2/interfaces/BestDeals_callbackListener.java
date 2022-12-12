package com.example.food_app2.interfaces;

import com.example.food_app2.model.BestDeals_model;

import java.util.List;

public interface BestDeals_callbackListener {
    void onBestDealsSuccess(List<BestDeals_model> bestDealsModelList);
    void onBestDealsLoadFailed(String message);
}
