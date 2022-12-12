package com.example.food_app2.ui.foodlists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.food_app2.R;
import com.example.food_app2.adapter.FoodListAdapter;
import com.example.food_app2.databinding.FragmentFoodlistBinding;
import com.example.food_app2.model.FoodModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FoodListFragment extends Fragment {


    private FragmentFoodlistBinding binding;
    Unbinder unbinder;
    @BindView(R.id.foodlist_recyclervw)
    RecyclerView recyclerView_foodlist;
    LayoutAnimationController layoutAnimationController;
    FoodListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FoodListViewModel foodListViewModel =
                new ViewModelProvider(this).get(FoodListViewModel.class);

        binding = FragmentFoodlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        unbinder= ButterKnife.bind(this,root);
        initViews();
//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        foodListViewModel.getMutableLiveDataFoodList().observe(getViewLifecycleOwner(), new Observer<List<FoodModel>>() {
            @Override
            public void onChanged(List<FoodModel> foodModels) {

                adapter=new FoodListAdapter(getContext(),foodModels);
                recyclerView_foodlist.setAdapter(adapter);
                recyclerView_foodlist.setLayoutAnimation(layoutAnimationController);

            }
        });
        return root;
    }

    private void initViews() {
        recyclerView_foodlist.setHasFixedSize(true);
        recyclerView_foodlist.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
