package com.example.food_app2.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food_app2.common.Commons;
import com.example.food_app2.interfaces.Popular_callbackListener;
import com.example.food_app2.model.Popular_Category_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements Popular_callbackListener {

//    private final MutableLiveData<String> mText;
    private MutableLiveData<List<Popular_Category_model>> popularList;
    private MutableLiveData<String> MsgError;
    private Popular_callbackListener popularCallbackListener;

    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
        popularCallbackListener=this;
    }


    public MutableLiveData<List<Popular_Category_model>> getPopularList() {
        if (popularList==null)
        {
            popularList=new MutableLiveData<>();
            MsgError=new MutableLiveData<>();
            LoadPopularList();
        }
        return popularList;
    }

    private void LoadPopularList() {
        List<Popular_Category_model> tempList=new ArrayList<>();
        DatabaseReference popularRef=FirebaseDatabase.getInstance().getReference(Commons.POPULAR_CATEGORY_REF);

        popularRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot itemSnapshot:snapshot.getChildren())
                {
                    Popular_Category_model model=itemSnapshot.getValue(Popular_Category_model.class);
                    tempList.add(model);
                }
                popularCallbackListener.onPopularLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                popularCallbackListener.onPopularLoadFailed(error.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMsgError() {
        return MsgError;
    }

    @Override
    public void onPopularLoadSuccess(List<Popular_Category_model> popularCategoryModels) {
        popularList.setValue(popularCategoryModels);
    }

    @Override
    public void onPopularLoadFailed(String message) {
         MsgError.setValue(message);
    }

//    public LiveData<String> getText() {
//        return mText;
//    }
}