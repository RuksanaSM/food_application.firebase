package com.example.food_app2.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.food_app2.R;
import com.example.food_app2.adapter.CategoriesAdapter;
import com.example.food_app2.common.Commons;
import com.example.food_app2.common.SpacesItemDecoration;
import com.example.food_app2.databinding.ContentHomeBinding;
import com.example.food_app2.databinding.FragmentMenuBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MenuFragment extends Fragment {


    //    private FragmentMenuBinding binding;

    MenuViewModel menuViewModel;
    Unbinder unbinder;
    @BindView(R.id.recycler_menu)
    RecyclerView recyclerView_menu;
    AlertDialog alertDialog;
    CategoriesAdapter categoriesAdapter;
    LayoutAnimationController layoutAnimationController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                new ViewModelProvider(this).get(MenuViewModel.class);

//        binding = FragmentMenuBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        unbinder= ButterKnife.bind(this,root);
        initViews();
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        menuViewModel.getMsgError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), " "+s, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        menuViewModel.getCategorydata().observe(getViewLifecycleOwner(),categoryModels -> {
            alertDialog.dismiss();
            categoriesAdapter=new CategoriesAdapter(getContext(),categoryModels);
            recyclerView_menu.setAdapter(categoriesAdapter);
            recyclerView_menu.setLayoutAnimation(layoutAnimationController);
        });
        return root;
    }

    private void initViews() {
        alertDialog=new AlertDialog.Builder(getContext()).setCancelable(false).create();
        alertDialog.setMessage("Loading....");
        alertDialog.show();

        layoutAnimationController= AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (categoriesAdapter!=null)
                {
                    switch (categoriesAdapter.getItemViewType(position))
                    {
                        case Commons.DEFAULT_COLUMN_COUNT:
                            return 1;
                        case  Commons.FULL_WIDTH_COLUMN:
                            return 2;
                        default:return -1;
                    }
                }
                return -1;
            }
        });
        recyclerView_menu.setLayoutManager(gridLayoutManager);
        recyclerView_menu.addItemDecoration(new SpacesItemDecoration(8));

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
 //        binding = null;

    }
}