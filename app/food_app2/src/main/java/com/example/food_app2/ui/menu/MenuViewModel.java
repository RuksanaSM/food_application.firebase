package com.example.food_app2.ui.menu;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food_app2.common.Commons;
import com.example.food_app2.interfaces.CategoryCallBackListener;
import com.example.food_app2.model.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends ViewModel implements CategoryCallBackListener {

//    private final MutableLiveData<String> mText;
    private MutableLiveData<List<CategoryModel>> categorydata;
    private MutableLiveData<String> msgError=new MutableLiveData<>();
    private CategoryCallBackListener categoryCallBackListeber;

    public MenuViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is Menu fragment");

        categoryCallBackListeber=this;
    }


    public MutableLiveData<List<CategoryModel>> getCategorydata() {

        if (categorydata==null)
        {
            categorydata=new MutableLiveData<>();
            msgError=new MutableLiveData<>();
            loadCategories();
        }
        return categorydata;
    }

    private void loadCategories() {

        List<CategoryModel> temList=new ArrayList<>();
        DatabaseReference catRef= FirebaseDatabase.getInstance().getReference(Commons.GET_REF);
        catRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data:snapshot.getChildren())
                {
                    CategoryModel model=data.getValue(CategoryModel.class);
                    model.setMenu_id(data.getKey());
                    temList.add(model);
                }
                categoryCallBackListeber.onCategoryLoadSuccess(temList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                categoryCallBackListeber.onCategoryLoadFailed(error.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMsgError() {
        return msgError;
    }

    @Override
    public void onCategoryLoadSuccess(List<CategoryModel> categoryModelList) {
        categorydata.setValue(categoryModelList);
    }

    @Override
    public void onCategoryLoadFailed(String message) {

        msgError.setValue(message);
    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
}