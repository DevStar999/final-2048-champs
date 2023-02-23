package com.nerdcoredevelopment.game2048champsfinal.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.nerdcoredevelopment.game2048champsfinal.NumericValueDisplay;
import com.nerdcoredevelopment.game2048champsfinal.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionOfferingsCallback;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QPermission;
import com.qonversion.android.sdk.dto.offerings.QOffering;
import com.qonversion.android.sdk.dto.offerings.QOfferings;
import com.qonversion.android.sdk.dto.products.QProduct;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* TODO -> Whenever the ShopFragment is opened give something like a toast message or a dialog that says 'Network connection
           failed' etc. when internet is not connected (Or else the user will keep pressing the button but nothing will
           happen)
*/
public class ShopFragment extends Fragment {
    private Context context;
    private OnShopFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;

    /* Variables related to this fragment */
    private int currentCoins;
    private Map<String, Integer> coinsReward;

    /* Views related to this fragment */
    private AppCompatTextView currentCoinsTextView;
    private AppCompatImageView backButton;
    private LinearLayout scrollViewContainerLinearLayout;
    private List<ConstraintLayout> shopCoinsConstraintLayouts;
    private List<AppCompatButton> shopCoinsPurchaseButtons;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void loadShopItemsInLayout() {
        for (int level = 1; level <= 7; level++) {
            ConstraintLayout shopItemRootLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.layout_shop_item,
                    scrollViewContainerLinearLayout, false);
            int idForItemRootLayoutResId = context.getResources().getIdentifier("shop_coins_level" + level
                    + "_shop_fragment_constraint_layout", "id", context.getPackageName());
            shopItemRootLayout.setId(idForItemRootLayoutResId);
            LinearLayout.LayoutParams layoutParamsShopItemRootLayout =
                    (LinearLayout.LayoutParams) shopItemRootLayout.getLayoutParams();
            layoutParamsShopItemRootLayout.setMargins(dpToPx(12, context), dpToPx(8, context),
                    dpToPx(12, context), dpToPx(8, context));
            shopItemRootLayout.setLayoutParams(layoutParamsShopItemRootLayout);

            FrameLayout shopCoinsOfferFrameLayout = shopItemRootLayout.findViewById(R.id.shop_coins_offer_frame_layout);
            if (level == 3 || level == 5 || level == 6) {
                shopCoinsOfferFrameLayout.setVisibility(View.INVISIBLE);
            }

            ShapeableImageView shopCoinsRibbonLabelImageView = shopItemRootLayout.findViewById(R.id
                    .shop_coins_ribbon_label_image_view);
            if (level == 7) {
                shopCoinsRibbonLabelImageView.setImageDrawable(context.getDrawable(R.drawable.tilted_ribbon_purple));
            }

            AppCompatTextView shopCoinsRibbonLabelTextView = shopItemRootLayout.findViewById(R.id
                    .shop_coins_ribbon_label_text_view);
            if (level == 1 || level == 2) { shopCoinsRibbonLabelTextView.setText("STEAL!"); }
            else if (level == 4) { shopCoinsRibbonLabelTextView.setText("HOT!"); }
            else if (level == 7) { shopCoinsRibbonLabelTextView.setText("VIP"); }

            AppCompatImageView shopCoinsIconImageView = shopItemRootLayout.findViewById(R.id.shop_coins_icon_image_view);
            int iconResId = context.getResources().getIdentifier("shop_coins_level" + level + "_icon",
                    "drawable", context.getPackageName());
            shopCoinsIconImageView.setImageResource(iconResId);

            AppCompatTextView shopCoinsCountTextView = shopItemRootLayout.findViewById(R.id.shop_coins_count_text_view);
            int coinsCount = coinsReward.get("coins_level" + level);
            shopCoinsCountTextView.setText(NumericValueDisplay.getGeneralValueDisplay(coinsCount));

            AppCompatButton shopCoinsPurchaseButton = shopItemRootLayout.findViewById(R.id.shop_coins_purchase_button);
            int idForPurchaseButtonResId = context.getResources().getIdentifier("shop_coins_level" + level +
                    "_shop_fragment_purchase_button", "id", context.getPackageName());
            shopCoinsPurchaseButton.setId(idForPurchaseButtonResId);

            LinearLayout shopCoinsRewardLinearLayout = shopItemRootLayout.findViewById(R.id.shop_coins_reward_linear_layout);
            ConstraintLayout.LayoutParams layoutParamsShopCoinsRewardLinearLayout =
                    (ConstraintLayout.LayoutParams) shopCoinsRewardLinearLayout.getLayoutParams();
            layoutParamsShopCoinsRewardLinearLayout.endToStart = context.getResources().getIdentifier("shop_coins_level"
                    + level + "_shop_fragment_purchase_button", "id", context.getPackageName());
            shopCoinsRewardLinearLayout.setLayoutParams(layoutParamsShopCoinsRewardLinearLayout);

            scrollViewContainerLinearLayout.addView(shopItemRootLayout);
        }
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
    }

    private void handlePurchaseOfCoins(QProduct qProduct, String productIdPrefix) {
        Qonversion.purchase((Activity) context, qProduct, new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NotNull Map<String, QPermission> permissions) {
                int rewardAmount = coinsReward.get(productIdPrefix);
                currentCoins += rewardAmount;
                Toast.makeText(context, "Purchase Successful \uD83E\uDD17 Rewarded +" + rewardAmount + " Coins",
                        Toast.LENGTH_LONG).show();
                if (mListener != null) {
                    mListener.onShopFragmentInteractionUpdateCoins(currentCoins);
                }
            }
            @Override
            public void onError(@NotNull QonversionError error) {
                // TODO -> Create a purchase failed dialog
            }
        });
    }

    private void loadItemPrices(int retryAttemptCount) {
        if (retryAttemptCount >= 10) {
            Toast.makeText(context, "Network connection failed. Please check Internet connectivity",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Qonversion.offerings(new QonversionOfferingsCallback() {
            @Override
            public void onSuccess(@NotNull QOfferings offerings) {
                if (!offerings.getAvailableOfferings().isEmpty()) {
                    for (QOffering currentOffering: offerings.getAvailableOfferings()) {
                        QProduct qProduct = currentOffering.getProducts().get(0);
                        String storeId = qProduct.getStoreID();
                        String prefix = "coins_level";
                        if (storeId != null && storeId.startsWith(prefix)) {
                            int level = Integer.parseInt(String.valueOf(storeId.charAt(prefix.length())));
                            level--;
                            if (qProduct.getSkuDetail() != null && !qProduct.getSkuDetail().getPrice().isEmpty()) {
                                shopCoinsPurchaseButtons.get(level).setText(qProduct.getSkuDetail().getPrice());
                            }
                            String finalPrefix = prefix + (level+1);
                            shopCoinsConstraintLayouts.get(level)
                                    .setOnClickListener(view -> handlePurchaseOfCoins(qProduct, finalPrefix));
                            shopCoinsPurchaseButtons.get(level)
                                    .setOnClickListener(view -> handlePurchaseOfCoins(qProduct, finalPrefix));
                        }
                    }
                }
            }
            @Override
            public void onError(@NotNull QonversionError error) {
                loadItemPrices(retryAttemptCount + 1);
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

        currentCoins = sharedPreferences.getInt("currentCoins", 3000);
        coinsReward = new HashMap<>() {{
            put("coins_level1", 1000); put("coins_level2", 3000); put("coins_level3", 5000);
            put("coins_level4", 10000); put("coins_level5", 25000);
            put("coins_level6", 50000); put("coins_level7", 100000);
        }};

        currentCoinsTextView = view.findViewById(R.id.current_coins_shop_fragment_text_view);
        currentCoinsTextView.setText(NumericValueDisplay.getGeneralValueDisplay(currentCoins));
        backButton = view.findViewById(R.id.title_back_shop_fragment_button);
        scrollViewContainerLinearLayout = view.findViewById(R.id.scroll_view_container_shop_fragment_linear_layout);

        loadShopItemsInLayout();

        shopCoinsConstraintLayouts = new ArrayList<>();
        for (int level = 1; level <= 7; level++) {
            int layoutResId = context.getResources().getIdentifier("shop_coins_level" + level +
                    "_shop_fragment_constraint_layout", "id", context.getPackageName());
            shopCoinsConstraintLayouts.add(view.findViewById(layoutResId));
        }

        shopCoinsPurchaseButtons = new ArrayList<>();
        for (int level = 1; level <= 7; level++) {
            int layoutResId = context.getResources().getIdentifier("shop_coins_level" + level +
                    "_shop_fragment_purchase_button", "id", context.getPackageName());
            shopCoinsPurchaseButtons.add(view.findViewById(layoutResId));
        }

        settingOnClickListeners();

        loadItemPrices(0);

        return view;
    }

    public void updateCoinsShopFragment(int currentCoins) {
        if (mListener != null) {
            this.currentCoins = currentCoins;
            currentCoinsTextView.setText(NumericValueDisplay.getGeneralValueDisplay(currentCoins));
        }
    }

    public interface OnShopFragmentInteractionListener {
        void onShopFragmentInteractionBackClicked();
        void onShopFragmentInteractionUpdateCoins(int currentCoins);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnShopFragmentInteractionListener) {
            mListener = (OnShopFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnShopFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}