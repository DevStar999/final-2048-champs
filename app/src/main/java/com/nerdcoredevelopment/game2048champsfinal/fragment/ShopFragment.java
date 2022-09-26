package com.nerdcoredevelopment.game2048champsfinal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.R;

import java.text.DecimalFormat;

public class ShopFragment extends Fragment {
    private Context context;
    private OnShopFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private AppCompatTextView currentCoinsTextView;
    private AppCompatImageView backButton;
    private AppCompatButton restorePurchase;

    /* Layouts of all purchase options */
    private ConstraintLayout shopCoinsLevel1ConstraintLayout;
    private ConstraintLayout shopCoinsLevel2ConstraintLayout;
    private ConstraintLayout shopCoinsLevel3ConstraintLayout;
    private ConstraintLayout shopCoinsLevel4ConstraintLayout;
    private ConstraintLayout shopCoinsLevel5ConstraintLayout;
    private ConstraintLayout shopCoinsLevel6ConstraintLayout;
    private ConstraintLayout shopCoinsLevel7ConstraintLayout;

    /* Purchase buttons of all purchase options */
    private AppCompatButton shopCoinsLevel1PurchaseButton;
    private AppCompatButton shopCoinsLevel2PurchaseButton;
    private AppCompatButton shopCoinsLevel3PurchaseButton;
    private AppCompatButton shopCoinsLevel4PurchaseButton;
    private AppCompatButton shopCoinsLevel5PurchaseButton;
    private AppCompatButton shopCoinsLevel6PurchaseButton;
    private AppCompatButton shopCoinsLevel7PurchaseButton;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void settingOnClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onShopFragmentInteractionBackClicked();
                }
            }
        });
        restorePurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onShopFragmentInteractionRestorePurchaseClicked();
                }
            }
        });

        shopCoinsLevel1ConstraintLayout.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel2ConstraintLayout.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel3ConstraintLayout.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel4ConstraintLayout.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel5ConstraintLayout.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel6ConstraintLayout.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel7ConstraintLayout.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });

        shopCoinsLevel1PurchaseButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel2PurchaseButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel3PurchaseButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel4PurchaseButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel5PurchaseButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel6PurchaseButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
        shopCoinsLevel7PurchaseButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onShopFragmentInteractionPurchaseOptionClicked(view.getId());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        sharedPreferences = context.getSharedPreferences("com.nerdcoredevelopment.game2048champsfinal", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        currentCoinsTextView = view.findViewById(R.id.current_coins_shop_fragment_text_view);
        DecimalFormat formatter = new DecimalFormat("#,###");
        currentCoinsTextView.setText(formatter.format(sharedPreferences.getInt("currentCoins", 2000)));
        backButton = view.findViewById(R.id.title_back_shop_fragment_button);
        restorePurchase = view.findViewById(R.id.restore_purchases_shop_fragment_button);

        shopCoinsLevel1ConstraintLayout = view.findViewById(R.id.shop_coins_level1_constraint_layout);
        shopCoinsLevel2ConstraintLayout = view.findViewById(R.id.shop_coins_level2_constraint_layout);
        shopCoinsLevel3ConstraintLayout = view.findViewById(R.id.shop_coins_level3_constraint_layout);
        shopCoinsLevel4ConstraintLayout = view.findViewById(R.id.shop_coins_level4_constraint_layout);
        shopCoinsLevel5ConstraintLayout = view.findViewById(R.id.shop_coins_level5_constraint_layout);
        shopCoinsLevel6ConstraintLayout = view.findViewById(R.id.shop_coins_level6_constraint_layout);
        shopCoinsLevel7ConstraintLayout = view.findViewById(R.id.shop_coins_level7_constraint_layout);

        shopCoinsLevel1PurchaseButton = view.findViewById(R.id.shop_coins_level1_purchase_button);
        shopCoinsLevel2PurchaseButton = view.findViewById(R.id.shop_coins_level2_purchase_button);
        shopCoinsLevel3PurchaseButton = view.findViewById(R.id.shop_coins_level3_purchase_button);
        shopCoinsLevel4PurchaseButton = view.findViewById(R.id.shop_coins_level4_purchase_button);
        shopCoinsLevel5PurchaseButton = view.findViewById(R.id.shop_coins_level5_purchase_button);
        shopCoinsLevel6PurchaseButton = view.findViewById(R.id.shop_coins_level6_purchase_button);
        shopCoinsLevel7PurchaseButton = view.findViewById(R.id.shop_coins_level7_purchase_button);

        settingOnClickListeners();

        return view;
    }

    public interface OnShopFragmentInteractionListener {
        void onShopFragmentInteractionBackClicked();
        void onShopFragmentInteractionRestorePurchaseClicked();
        void onShopFragmentInteractionPurchaseOptionClicked(int purchaseOptionViewId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnShopFragmentInteractionListener) {
            mListener = (OnShopFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnShopFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}