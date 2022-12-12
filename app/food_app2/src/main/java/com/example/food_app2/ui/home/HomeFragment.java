package com.example.food_app2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.food_app2.R;
import com.example.food_app2.adapter.PopularCategoriesAdapter;
import com.example.food_app2.databinding.FragmentHomeBinding;
import com.example.food_app2.model.BestDeals_model;
import com.example.food_app2.model.Popular_Category_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    Unbinder unbinder;

    @BindView(R.id.recycler_popularid)
    RecyclerView recyclerView;
@BindView(R.id.image_slider)
ImageSlider imageSlider;
List<SlideModel> dealsModels=new ArrayList<>();

LayoutAnimationController layoutAnimationController;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        unbinder= ButterKnife.bind(this,root);

        init();
        homeViewModel.getPopularList().observe(getViewLifecycleOwner(),popularCategoryModels -> {

            //create Adapter
            PopularCategoriesAdapter popularCategoriesAdapter=new PopularCategoriesAdapter(getContext(),popularCategoryModels);
            recyclerView.setAdapter(popularCategoriesAdapter);


        });
        FirebaseDatabase.getInstance().getReference().child("MostPopular").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data:snapshot.getChildren())
                        {
//                                BestDeals_model model=data.getValue(BestDeals_model.class);
//                                dealsModels.add(model);
//
//                            dealsModels.add(new SlideModel(data.child("food_id").getValue().toString(),data.child("image").getValue().toString(),data.child("menu_id").getValue().toString(),data.child("name").getValue().toString(),ScaleTypes.FIT));
//                            imageSlider.setImageList(dealsModels);

                            dealsModels.add(new SlideModel("https://media-cdn.tripadvisor.com/media/photo-s/1a/52/54/68/mmmmm.jpg", "Pizza",ScaleTypes.FIT));
                            dealsModels.add(new SlideModel("https://i.pinimg.com/originals/e3/94/49/e394499e82fb72b0d1dacc49ab3b4ce0.jpg", "Pasta.",ScaleTypes.FIT));

                            dealsModels.add(new SlideModel("https://patch.com/img/cdn20/users/22871539/20190830/112111/styles/patch_image/public/standarddd___30111828259.jpg", "Burger",ScaleTypes.FIT));
                            dealsModels.add(new SlideModel("https://i.pinimg.com/originals/6f/9b/74/6f9b7447b4baf5f0c21b96d6f148d8bb.jpg", "Cake",ScaleTypes.FIT));
                            dealsModels.add(new SlideModel("https://i.pinimg.com/originals/3a/d8/f9/3ad8f9f95e8ffa073fdeb16622b93f4e.jpg", "Faluda",ScaleTypes.FIT));
                            dealsModels.add(new SlideModel("https://sinofoodsupply.com/wp-content/uploads/2017/08/Jelly-carrageenan-powder.jpg", "Deserts",ScaleTypes.CENTER_CROP));



                            imageSlider.setImageList(dealsModels);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        return root;
    }

    private void init() {

        layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setLayoutAnimation(layoutAnimationController);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}