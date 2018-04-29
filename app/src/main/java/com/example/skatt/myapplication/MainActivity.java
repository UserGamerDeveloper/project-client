package com.example.skatt.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class MainActivity extends AppCompatActivity {

    int mGearScore;
    int mMoney;
    int mMoneyBank = 0;
    int mHpMaxDefault;
    int mHpMax;
    int mHp;
    CardTable mCardTableTarget;
    CardTable[] mCardsTable = new CardTable[8];
    final byte LOOT_AND_TRADE_MAX_COUNT = 3;
    int mLootCount;
    CardInventory[] mLoot = new CardInventory[LOOT_AND_TRADE_MAX_COUNT];
    byte mInventoryItemCount;
    final byte INVENTORY_MAX_COUNT = 4;
    CardInventory[] mInventory = new CardInventory[INVENTORY_MAX_COUNT];
    CardHand mHandOne;
    CardHand mHandTwo;
    ImageView mCardCenter;
    ConstraintLayout mButtonStart;
    ConstraintLayout mButtonContinue;
    ConstraintLayout mTradeZone;
    ImageView mShadow;
    ImageView hp_view;
    ImageView hp_bar;
    TextView mMoneyText;
    TextView mHpText;
    float money_text_size;
    final float money_text_size_constant = 0.0496f;
    float card_name_text_size;
    float card_hp_and_damage_text_size;
    final float card_name_text_size_constant = 0.060464f;
    final float card_hp_and_damage_text_size_constant = 0.102033f;
    float hp_text_size;
    final float hp_text_size_constant = 0.154202f;
    boolean is_first_click = true;
    boolean mIsAnimate;
    private final int mIdDrawableCardBack = R.drawable.card_back;
    private final int cardCenterBack = R.drawable.perekrestok;
    final Random random = new Random();
    DBOpenHelper mDBOpenHelper = new DBOpenHelper(MainActivity.this);
    Drawable mHpBarDrawable;
    ConstraintLayout mTable;
    private View collectionButton;
    final class SlotType {

        final static byte LOOT = 0;
        final static byte HAND = 1;
        final static byte INVENTORY = 2;

        private SlotType() {
        }
    }
    int inventory_animation_delta;
    int hand_animation_delta;
    float loot_animation_delta;
    AnimatorSet card_6_animation_click_vendor = new AnimatorSet();
    CardInventory mTargetSwap;
    CardInventoryTemp inventory_temp = new CardInventoryTemp();
    AnimatorListenerAdapter target_on_animation_end = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            deleteOnClickListenerBeforeAnomationTargetReset();
        }

        @Override
        public void onAnimationEnd(Animator animation) {

            for (byte i = 0; i < INVENTORY_MAX_COUNT; i++) {
                mInventory[i].setOnClickListener(mInventoryOnClickSwap);
            }
            for (int i = 0; i < LOOT_AND_TRADE_MAX_COUNT; i++) {
                mLoot[i].setOnClickListener(mInventoryOnClickSwap);
            }
            mHandOne.setOnClickListener(mInventoryOnClickSwap);
            mHandTwo.setOnClickListener(mInventoryOnClickSwap);

            mTargetSwap.setOnLongClickListener(mOnLongClickListener);
            mTargetSwap.setOnTouchListener(mCardMoveListener);
        }
    };
    AnimatorListenerAdapter on_target_off_animation = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            deleteOnClickListenerBeforeAnomationTargetReset();
            mTargetSwap.setOnLongClickListener(null);
            mTargetSwap.setOnTouchListener(null);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            for (byte i = 0; i < INVENTORY_MAX_COUNT; i++) {
                mInventory[i].setOnClickListener(mInventoryOnClickSwap);
            }
            mHandOne.setOnClickListener(mInventoryOnClickSwap);
            mHandTwo.setOnClickListener(mInventoryOnClickSwap);
        }
    };
    Animator target_on_animation;
    Animator target_off_animation;
    CardInventory trade_target;
    TextView[] mTradeCost = new TextView[3];
    ImageView[] mTradeCostImage = new ImageView[3];
    CardInventory[] mTradeItem = new CardInventory[3];
    ConstraintLayout mTradeSkill;
    ImageView mTradeSkillImage;
    DialogWindow mDialogWindow = new DialogWindow();
    TextView mGearScoreText;
    AnimatorSet card_reset_column_right = new AnimatorSet();
    AnimatorSet card_reset_row_top = new AnimatorSet();
    AnimatorSet card_reset_row_center = new AnimatorSet();
    AnimatorSet card_reset_row_bottom = new AnimatorSet();
    AnimatorSet card_animation_right = new AnimatorSet();
    AnimatorSet card_animation_get_left = new AnimatorSet();
    AnimatorSet card_animation_down = new AnimatorSet();
    AnimatorSet card_animation_get_top = new AnimatorSet();
    AnimatorSet card_animation_up = new AnimatorSet();
    AnimatorSet card_animation_get_bottom = new AnimatorSet();
    AnimatorSet openCardTable = new AnimatorSet();
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode("925238805882-72kg4srauv9f2ph2cf905r2cjhtbndbo.apps.googleusercontent.com")
            .requestEmail()
            .build();
    View mMenuButton;
    View mStatsButton;
    ObjectMapper mJackson = new ObjectMapper();
    byte mState;
    MyRequest request;
    OkHttpClient client;
    byte[] mNextCardTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region set certificate
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            InputStream certInputStream = getBaseContext().getResources().openRawResource(R.raw.cert);
            BufferedInputStream bis = new BufferedInputStream(certInputStream);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate cert = certificateFactory.generateCertificate(bis);
            keyStore.setCertificateEntry("cert", cert);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm()
            );
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustManagers, null);

            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0])
                    .hostnameVerifier((hostname, session) -> {
                        return true;
                    })
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //endregion

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.signInButton).setOnClickListener(onSign);

        mMenuButton = findViewById(R.id.menu);
        collectionButton = findViewById(R.id.collection);
        mStatsButton = findViewById(R.id.statsIcon);

        mStats = new Stats(mDBOpenHelper);
        mStats.setLayout(findViewById(R.id.stats));
        mStats.setDamageButtonMinus(findViewById(R.id.statsDamageButtonMinus));
        mStats.setDamageButtonPlus(findViewById(R.id.statsDamageButtonPlus));
        mStats.setDefenceButtonMinus(findViewById(R.id.statsDefenceButtonMinus));
        mStats.setDefenceButtonPlus(findViewById(R.id.statsDefenceButtonPlus));
        mStats.setHPButtonMinus(findViewById(R.id.statsHPButtonMinus));
        mStats.setHPButtonPlus(findViewById(R.id.statsHPButtonPlus));
        mStats.setLevelAndExperienceText(findViewById(R.id.statsExperience));
        mStats.setDamageBonusText(findViewById(R.id.statsDamageValueText));
        mStats.setDefenceBonusText(findViewById(R.id.statsDefenceValueText));
        mStats.setHPBonusText(findViewById(R.id.statsHPValueText));
        mStats.setPointsText(findViewById(R.id.statsPointsText));
        mStats.setResetButton(findViewById(R.id.statsResetButton));
        mStats.setConfirmButton(findViewById(R.id.statsConfirmButton));

        mGearScoreText = findViewById(R.id.gearScore);

        mTable = findViewById(R.id.table);
        mTable.setOnDragListener(mTableOnDropListener);

        mTradeSkill = findViewById(R.id.trade_skill);
        mTradeZone = findViewById(R.id.trade_zone);
        mTradeZone.setOnDragListener(mOnSell);

        mDialogWindow.setLayout(findViewById(R.id.trade_window));
        mDialogWindow.setText(findViewById(R.id.trade_window_text));
        mDialogWindow.setButtonOk(findViewById(R.id.trade_window_ok));
        mDialogWindow.setButtonYes(findViewById(R.id.trade_window_yes));
        mDialogWindow.setButtonNo(findViewById(R.id.trade_window_no));

        mTradeSkillImage = findViewById(R.id.trade_skill_image);
        mTradeSkillImage.setOnClickListener(mOnClickVendorSkill);

        mShadow = findViewById(R.id.shadow);

        mButtonStart = findViewById(R.id.button_start_layout);
        mButtonContinue = findViewById(R.id.button_continue_layout);
        mButtonContinue.setVisibility(View.GONE);

        mMoneyText = findViewById(R.id.money_text);
        mHpText = findViewById(R.id.hp_text);

        hp_view = findViewById(R.id.hp);

        mHpBarDrawable = ((ImageView)findViewById(R.id.hp_bar)).getDrawable();

        mCardCenter = findViewById(R.id.card_view_5);

        mCardsTable[0] = findViewById(R.id.card_table_1);
        mCardsTable[1] = findViewById(R.id.card_table_2);
        mCardsTable[2] = findViewById(R.id.card_table_3);
        mCardsTable[3] = findViewById(R.id.card_table_4);
        mCardsTable[4] = findViewById(R.id.card_table_6);
        mCardsTable[5] = findViewById(R.id.card_table_7);
        mCardsTable[6] = findViewById(R.id.card_table_8);
        mCardsTable[7] = findViewById(R.id.card_table_9);

        mCardsTable[0].mImageView = findViewById(R.id.card_view_1);
        mCardsTable[1].mImageView = findViewById(R.id.card_view_2);
        mCardsTable[2].mImageView = findViewById(R.id.card_view_3);
        mCardsTable[3].mImageView = findViewById(R.id.card_view_4);
        mCardsTable[4].mImageView = findViewById(R.id.card_view_6);
        mCardsTable[5].mImageView = findViewById(R.id.card_view_7);
        mCardsTable[6].mImageView = findViewById(R.id.card_view_8);
        mCardsTable[7].mImageView = findViewById(R.id.card_view_9);

        mCardsTable[0].mNameText = findViewById(R.id.card_name_1);
        mCardsTable[1].mNameText = findViewById(R.id.card_name_2);
        mCardsTable[2].mNameText = findViewById(R.id.card_name_3);
        mCardsTable[3].mNameText = findViewById(R.id.card_name_4);
        mCardsTable[4].mNameText = findViewById(R.id.card_name_6);
        mCardsTable[5].mNameText = findViewById(R.id.card_name_7);
        mCardsTable[6].mNameText = findViewById(R.id.card_name_8);
        mCardsTable[7].mNameText = findViewById(R.id.card_name_9);

        mCardsTable[0].mValueOneText = findViewById(R.id.card_hp_1);
        mCardsTable[1].mValueOneText = findViewById(R.id.card_hp_2);
        mCardsTable[2].mValueOneText = findViewById(R.id.card_hp_3);
        mCardsTable[3].mValueOneText = findViewById(R.id.card_hp_4);
        mCardsTable[4].mValueOneText = findViewById(R.id.card_hp_6);
        mCardsTable[5].mValueOneText = findViewById(R.id.card_hp_7);
        mCardsTable[6].mValueOneText = findViewById(R.id.card_hp_8);
        mCardsTable[7].mValueOneText = findViewById(R.id.card_hp_9);

        mCardsTable[0].mValueTwoText = findViewById(R.id.card_damage_1);
        mCardsTable[1].mValueTwoText = findViewById(R.id.card_damage_2);
        mCardsTable[2].mValueTwoText = findViewById(R.id.card_damage_3);
        mCardsTable[3].mValueTwoText = findViewById(R.id.card_damage_4);
        mCardsTable[4].mValueTwoText = findViewById(R.id.card_damage_6);
        mCardsTable[5].mValueTwoText = findViewById(R.id.card_damage_7);
        mCardsTable[6].mValueTwoText = findViewById(R.id.card_damage_8);
        mCardsTable[7].mValueTwoText = findViewById(R.id.card_damage_9);

        mLoot[0] = findViewById(R.id.card_loot_0);
        mLoot[1] = findViewById(R.id.card_loot_1);
        mLoot[2] = findViewById(R.id.card_loot_2);

        mLoot[0].setOnDragListener(mLootOnDropListener);
        mLoot[1].setOnDragListener(mLootOnDropListener);
        mLoot[2].setOnDragListener(mLootOnDropListener);

        mLoot[0].mSlotType = SlotType.LOOT;
        mLoot[1].mSlotType = SlotType.LOOT;
        mLoot[2].mSlotType = SlotType.LOOT;

        mLoot[0].mImageView = findViewById(R.id.card_loot_0_view);
        mLoot[1].mImageView = findViewById(R.id.card_loot_1_view);
        mLoot[2].mImageView = findViewById(R.id.card_loot_2_view);

        mLoot[0].mNameText = findViewById(R.id.card_loot_0_name);
        mLoot[1].mNameText = findViewById(R.id.card_loot_1_name);
        mLoot[2].mNameText = findViewById(R.id.card_loot_2_name);

        mLoot[0].mValueOneText = findViewById(R.id.card_loot_0_value);
        mLoot[1].mValueOneText = findViewById(R.id.card_loot_1_value);
        mLoot[2].mValueOneText = findViewById(R.id.card_loot_2_value);

        mTradeItem[0] = findViewById(R.id.card_trade_0);
        mTradeItem[1] = findViewById(R.id.card_trade_1);
        mTradeItem[2] = findViewById(R.id.card_trade_2);

        mTradeItem[0].mImageView = findViewById(R.id.card_trade_0_view);
        mTradeItem[1].mImageView = findViewById(R.id.card_trade_1_view);
        mTradeItem[2].mImageView = findViewById(R.id.card_trade_2_view);

        mTradeItem[0].mNameText = findViewById(R.id.card_trade_0_name);
        mTradeItem[1].mNameText = findViewById(R.id.card_trade_1_name);
        mTradeItem[2].mNameText = findViewById(R.id.card_trade_2_name);

        mTradeItem[0].mValueOneText = findViewById(R.id.card_trade_0_value);
        mTradeItem[1].mValueOneText = findViewById(R.id.card_trade_1_value);
        mTradeItem[2].mValueOneText = findViewById(R.id.card_trade_2_value);

        mTradeItem[0].setSlotId((byte)0);
        mTradeItem[1].setSlotId((byte)1);
        mTradeItem[2].setSlotId((byte)2);

        mTradeItem[0].setOnClickListener(mOnClickBuyCardListener);
        mTradeItem[1].setOnClickListener(mOnClickBuyCardListener);
        mTradeItem[2].setOnClickListener(mOnClickBuyCardListener);

        mTradeCost[0] = findViewById(R.id.cost_0_text);
        mTradeCost[1] = findViewById(R.id.cost_1_text);
        mTradeCost[2] = findViewById(R.id.cost_2_text);
        mTradeCostImage[0] = findViewById(R.id.cost_0_view);
        mTradeCostImage[1] = findViewById(R.id.cost_1_view);
        mTradeCostImage[2] = findViewById(R.id.cost_2_view);

        mHandOne = findViewById(R.id.card_hand_left);
        mHandTwo = findViewById(R.id.card_hand_right);
        mHandOne.setIDDrawableDefault(R.drawable.kulak_levo);
        mHandTwo.setIDDrawableDefault(R.drawable.kulak_pravo);
        mHandOne.mSlotType = SlotType.HAND;
        mHandTwo.mSlotType = SlotType.HAND;
        mHandOne.mImageView = findViewById(R.id.card_hand_leftview);
        mHandTwo.mImageView = findViewById(R.id.card_hand_right_view);
        mHandOne.mNameText = findViewById(R.id.card_hand_left_name);
        mHandTwo.mNameText = findViewById(R.id.card_hand_right_name);
        mHandOne.mValueOneText = findViewById(R.id.card_hand_left_value);
        mHandTwo.mValueOneText = findViewById(R.id.card_hand_right_value);
        mHandOne.setOnDragListener(mHandOnDropListener);
        mHandTwo.setOnDragListener(mHandOnDropListener);
        mHandOne.mDurabilityText = findViewById(R.id.hand_one_durability_text);
        mHandTwo.mDurabilityText = findViewById(R.id.hand_two_durability_text);
        mHandOne.mDurabilityImage = findViewById(R.id.hand_one_durability_image);
        mHandTwo.mDurabilityImage = findViewById(R.id.hand_two_durability_image);

        mInventory[0] = findViewById(R.id.card_inventory_0);
        mInventory[1] = findViewById(R.id.card_inventory_1);
        mInventory[2] = findViewById(R.id.card_inventory_2);
        mInventory[3] = findViewById(R.id.card_inventory_3);

        mInventory[0].mSlotType = SlotType.INVENTORY;
        mInventory[1].mSlotType = SlotType.INVENTORY;
        mInventory[2].mSlotType = SlotType.INVENTORY;
        mInventory[3].mSlotType = SlotType.INVENTORY;

        mInventory[0].mImageView = findViewById(R.id.card_inventory_0_view);
        mInventory[1].mImageView = findViewById(R.id.card_inventory_1_view);
        mInventory[2].mImageView = findViewById(R.id.card_inventory_2_view);
        mInventory[3].mImageView = findViewById(R.id.card_inventory_3_view);

        mInventory[0].mNameText = findViewById(R.id.card_inventory_0_name);
        mInventory[1].mNameText = findViewById(R.id.card_inventory_1_name);
        mInventory[2].mNameText = findViewById(R.id.card_inventory_2_name);
        mInventory[3].mNameText = findViewById(R.id.card_inventory_3_name);

        mInventory[0].mValueOneText = findViewById(R.id.card_inventory_0_value);
        mInventory[1].mValueOneText = findViewById(R.id.card_inventory_1_value);
        mInventory[2].mValueOneText = findViewById(R.id.card_inventory_2_value);
        mInventory[3].mValueOneText = findViewById(R.id.card_inventory_3_value);

        mCardsTable[0].TEST_GearScoreText = findViewById(R.id.card1GearScore);
        mCardsTable[1].TEST_GearScoreText = findViewById(R.id.card2GearScore);
        mCardsTable[2].TEST_GearScoreText = findViewById(R.id.card3GearScore);
        mCardsTable[3].TEST_GearScoreText = findViewById(R.id.card4GearScore);
        mCardsTable[4].TEST_GearScoreText = findViewById(R.id.card6GearScore);
        mCardsTable[5].TEST_GearScoreText = findViewById(R.id.card7GearScore);
        mCardsTable[6].TEST_GearScoreText = findViewById(R.id.card8GearScore);
        mCardsTable[7].TEST_GearScoreText = findViewById(R.id.card9GearScore);

        mLoot[0].TEST_GearScoreText = findViewById(R.id.loot0GearScore);
        mLoot[1].TEST_GearScoreText = findViewById(R.id.loot1GearScore);
        mLoot[2].TEST_GearScoreText = findViewById(R.id.loot2GearScore);

        mTradeItem[0].TEST_GearScoreText = findViewById(R.id.cardTrade0GearScore);
        mTradeItem[1].TEST_GearScoreText = findViewById(R.id.cardTrade1GearScore);
        mTradeItem[2].TEST_GearScoreText = findViewById(R.id.cardTrade2GearScore);

        mHandOne.TEST_GearScoreText = findViewById(R.id.cardHandLeftGearScore);
        mHandTwo.TEST_GearScoreText = findViewById(R.id.cardHandRightGearScore);

        mInventory[0].TEST_GearScoreText = findViewById(R.id.cardInventory0GearScore);
        mInventory[1].TEST_GearScoreText = findViewById(R.id.cardInventory1GearScore);
        mInventory[2].TEST_GearScoreText = findViewById(R.id.cardInventory2GearScore);
        mInventory[3].TEST_GearScoreText = findViewById(R.id.cardInventory3GearScore);

        mLoot[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot0GearScoreMob);
        mLoot[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot1GearScoreMob);
        mLoot[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.loot2GearScoreMob);

        mTradeItem[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade0GearScoreMob);
        mTradeItem[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade1GearScoreMob);
        mTradeItem[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardTrade2GearScoreMob);

        mHandOne.TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardHandLeftGearScoreMob);
        mHandTwo.TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardHandRightGearScoreMob);

        mInventory[0].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory0GearScoreMob);
        mInventory[1].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory1GearScoreMob);
        mInventory[2].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory2GearScoreMob);
        mInventory[3].TEST_MOB_GEARSCORE_TEXT = findViewById(R.id.cardInventory3GearScoreMob);
    }
    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            mGoogleSignInClient.silentSignIn().
                    addOnCompleteListener(this, this::login);
        }
        else {
            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
        }

        super.onStart();
    }

    GoogleSignInClient mGoogleSignInClient;
    View.OnClickListener onSign = onSign();
    View.OnClickListener onSign() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 0);
            }
        };
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            login(task);
        }
    }
    private void login(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            request = new MyRequest(account.getServerAuthCode());
            post("login", mJackson.writeValueAsString(request), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("onFailure", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseStr = response.body().string();
                    Log.d("login responseStr", responseStr);
                    MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                    if (!myResponse.isError()){
                        SQLiteDatabase data_base = mDBOpenHelper.getReadableDatabase();

                        String[] column_name = {DBOpenHelper.HP, DBOpenHelper.COSTRESET};
                        Cursor cursor = data_base.query(
                                DBOpenHelper.sTableTest,
                                column_name,
                                null,
                                null,
                                null,
                                null,
                                null
                        );
                        cursor.moveToFirst();

                        mHpMaxDefault = cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.HP));
                        mStatsResetCost = cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.COSTRESET));
                        cursor.close();

                        LoginResponce loginResponce = mJackson.readValue(
                                myResponse.getData(),
                                LoginResponce.class
                        );
                        mTable.post(() -> {
                            mMoneyBank = loginResponce.getMoneyBank();
                            mStats.login(loginResponce.getStats());
                            mHpMax = mHpMaxDefault + mStats.getHPBonus();
                            mHp = mHpMax;
                            mState = loginResponce.getState();
                            resetGame();
                            mInventory[0].setVisibility(View.VISIBLE);
                            mLoot[0].setVisibility(View.VISIBLE);
                            setTextSize();
                            setAnimators();
                            updateMoneyBankText();
                            mStats.updateLevelAndExperienceTextInThreadUI();
                            mIsAnimate = true;
                            if (mState!=State.NONE){
                                mHp = loginResponce.getHP();
                                mMoney = loginResponce.getMoney();
                                if (loginResponce.getCardTable0()!=null){
                                    mCardsTable[0].setIDMob(loginResponce.getCardTable0());
                                }
                                if (loginResponce.getCardTable1()!=null){
                                    mCardsTable[1].setIDMob(loginResponce.getCardTable1());
                                }
                                if (loginResponce.getCardTable2()!=null){
                                    mCardsTable[2].setIDMob(loginResponce.getCardTable2());
                                }
                                if (loginResponce.getCardTable3()!=null){
                                    mCardsTable[3].setIDMob(loginResponce.getCardTable3());
                                }
                                if (loginResponce.getCardTable4()!=null){
                                    mCardsTable[4].setIDMob(loginResponce.getCardTable4());
                                }
                                if (loginResponce.getCardTable5()!=null){
                                    mCardsTable[5].setIDMob(loginResponce.getCardTable5());
                                }
                                if (loginResponce.getCardTable6()!=null){
                                    mCardsTable[6].setIDMob(loginResponce.getCardTable6());
                                }
                                if (loginResponce.getCardTable7()!=null){
                                    mCardsTable[7].setIDMob(loginResponce.getCardTable7());
                                }
                                List<CardPlayerResponse> inventory = loginResponce.getInventory();
                                Iterator<CardPlayerResponse> iterator = inventory.iterator();
                                while (iterator.hasNext()) {
                                    CardPlayerResponse item = iterator.next();
                                    if (item.getSlotId()==4){
                                        mHandOne.setIDItem(item.getIdItem());
                                        mHandOne.setDurability(item.getDurability());
                                        iterator.remove();
                                        continue;
                                    }
                                    if (item.getSlotId()==5){
                                        mHandTwo.setIDItem(item.getIdItem());
                                        mHandTwo.setDurability(item.getDurability());
                                        iterator.remove();
                                    }
                                }
                                if (!inventory.isEmpty()){
                                    for (mInventoryItemCount = 0;
                                         mInventoryItemCount<inventory.size();
                                         mInventoryItemCount++)
                                    {
                                        CardPlayerResponse cardPlayerResponse = inventory.get(mInventoryItemCount);
                                        mInventory[mInventoryItemCount].setDurability(
                                                cardPlayerResponse.getDurability()
                                        );
                                        mInventory[mInventoryItemCount].setIDItem(cardPlayerResponse.getIdItem());
                                    }
                                }
                                mButtonStart.setVisibility(View.GONE);
                                collectionButton.setVisibility(View.GONE);
                                mStatsButton.setVisibility(View.GONE);
                                updateMoneyText();
                                mCardCenter.setVisibility(View.VISIBLE);
                                for (CardTable cardTable : mCardsTable) {
                                    cardTable.setVisibility(View.VISIBLE);
                                    if (!cardTable.isEmpty()){
                                        cardTable.load(mDBOpenHelper);
                                        cardTable.open();
                                    }
                                }

                                mHandOne.load(mStats,mDBOpenHelper);
                                mHandOne.updateDurabilityText();
                                mHandOne.open();
                                mHandOne.setOnClickListener(mInventoryOnClickSwap);
                                mHandTwo.load(mStats,mDBOpenHelper);
                                mHandTwo.updateDurabilityText();
                                mHandTwo.open();
                                mHandTwo.setOnClickListener(mInventoryOnClickSwap);
                                for (byte i = 0;i<INVENTORY_MAX_COUNT;i++){
                                    mInventory[i].setOnClickListener(mInventoryOnClickSwap);
                                    if (i<mInventoryItemCount){
                                        mInventory[i].load(mStats, mDBOpenHelper);
                                        mInventory[i].open();
                                        mInventory[i].setVisibility(View.VISIBLE);
                                    }
                                }
                                mIsAnimate = false;
                                if (mState!=State.SELECT_TARGET){
                                    mCardTableTarget = mCardsTable[loginResponce.getCardTableTargetIDInArray()];
                                    if (mState==State.COMBAT){
                                        mShadow.bringToFront();
                                        mCardTableTarget.getTargetAnimation().start();
                                        mCardTableTarget.getTargetAnimation().end();
                                        mCardTableTarget.bringToFront();
                                        mCardTableTarget.setValueTwoInUIThread(loginResponce.getCardTableTargetHP());
                                        mCardTableTarget.setOnClickListener(mDamageListener);
                                    }
                                    if (mState==State.SELECT_LOOT) {
                                        mCardTableTarget.bringToFront();
                                        mShadow.bringToFront();
                                        mCardTableTarget.getTargetAnimation().start();
                                        mCardTableTarget.getTargetAnimation().end();
                                        mIsAnimate = true;
                                        List<CardPlayerResponse> loot = loginResponce.getLoot();
                                        for (mLootCount = 0; mLootCount<loot.size();mLootCount++){
                                            CardPlayerResponse cardPlayerResponse = loot.get(mLootCount);
                                            mLoot[mLootCount].setIDItem(cardPlayerResponse.getIdItem());
                                            mLoot[mLootCount].setDurability(
                                                    cardPlayerResponse.getDurability()
                                            );
                                        }
                                        for (byte i = 0;i<mLootCount;i++){
                                            mLoot[i].load(mStats, mDBOpenHelper);
                                            mLoot[i].open();
                                            mLoot[i].setVisibility(View.VISIBLE);
                                            mLoot[i].bringToFront();
                                        }
                                        mCardTableTarget.getCloseAnimation().start();
                                        mCardTableTarget.getCloseAnimation().end();
                                        mButtonContinue.bringToFront();
                                        mButtonContinue.setVisibility(View.VISIBLE);
                                    }
                                    if (mState==State.TRADE){
                                        card_6_animation_click_vendor.start();
                                        card_6_animation_click_vendor.end();
                                        mShadow.bringToFront();
                                        mCardTableTarget.bringToFront();
                                        List<CardPlayerResponse> items = loginResponce.getTrade();
                                        for (byte i = 0; i< LOOT_AND_TRADE_MAX_COUNT; i++){
                                            CardPlayerResponse item = items.get(i);
                                            mTradeItem[i].setIDItem(item.getIdItem());
                                            mTradeItem[i].setDurability(item.getDurability());
                                            mTradeItem[i].load(mStats, mDBOpenHelper);
                                            mTradeItem[i].open();
                                            mTradeItem[i].setVisibility(View.VISIBLE);
                                            mTradeCost[i].setText(String.format("%d", mTradeItem[i].getCost()));
                                            mTradeCost[i].setVisibility(View.VISIBLE);
                                            mTradeCostImage[i].setVisibility(View.VISIBLE);
                                        }
                                        mTradeSkill.setVisibility(View.VISIBLE);
                                        mTradeZone.setVisibility(View.VISIBLE);
                                        mTable.setOnDragListener(null);
                                        if(mCardTableTarget.getSubType() == CardTableSubType.TRADER){
                                            Picasso.with(getBaseContext())
                                                    .load(R.drawable.navik_torgovca)
                                                    .into(mTradeSkillImage);
                                            mTradeSkillImage.setOnClickListener(mOnClickVendorSkill);
                                        }
                                        if (mCardTableTarget.getSubType() == CardTableSubType.BLACKSMITH){
                                            Picasso.with(getBaseContext())
                                                    .load(R.drawable.navik_kuznecaa)
                                                    .into(mTradeSkillImage);
                                            mTradeSkillImage.setOnClickListener(null);
                                        }
                                        if (mCardTableTarget.getSubType() == CardTableSubType.INNKEEPER){
                                            Picasso.with(getBaseContext())
                                                    .load(R.drawable.navik_traktirshika)
                                                    .into(mTradeSkillImage);
                                            mTradeSkillImage.setOnClickListener(mOnClickInnkeeperSkill);
                                        }
                                    }
                                }
                            }
                            Log.d("mIsAnimate", String.valueOf(mIsAnimate));
                            updateHPText();
                            findViewById(R.id.signLayout).setVisibility(View.GONE);
                        });
                    }
                }
            });
        }
        catch (ApiException e) {
            Log.w("login", "signInResult:failed code=" + e.getStatusCode());
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    private void setTextSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int card_height = mCardsTable[0].mImageView.getHeight();
        int card_width = mCardsTable[0].mImageView.getWidth();

        money_text_size = displayMetrics.heightPixels * money_text_size_constant;
        mMoneyText.setTextSize(COMPLEX_UNIT_PX, money_text_size);

        card_name_text_size = (float) (Math.sqrt(Math.pow(card_width, 2.0) +
                Math.pow(card_height, 2.0)) * card_name_text_size_constant);
        card_hp_and_damage_text_size = (float) (Math.sqrt(Math.pow(card_width, 2.0) +
                Math.pow(card_height, 2.0)) * card_hp_and_damage_text_size_constant);
        for (int i = 0; i < 8; i++) {
            mCardsTable[i].mNameText.setTextSize(COMPLEX_UNIT_PX, card_name_text_size);
            mCardsTable[i].mValueOneText.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
            mCardsTable[i].mValueTwoText.setTextSize(COMPLEX_UNIT_PX, card_hp_and_damage_text_size);
        }

        float card_inventory_value_one_text_size = (float) (Math.sqrt(Math.pow(mInventory[0].mImageView.getWidth(), 2.0) +
                Math.pow(mInventory[0].mImageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_inventory_name_text_size = (float) (Math.sqrt(Math.pow(mInventory[0].mImageView.getWidth(), 2.0) +
                Math.pow(mInventory[0].mImageView.getHeight(), 2.0)) * card_name_text_size_constant);
        for (int i = 0; i < INVENTORY_MAX_COUNT; i++) {
            mInventory[i].mNameText.setTextSize(COMPLEX_UNIT_PX, card_inventory_name_text_size);
            mInventory[i].mValueOneText.setTextSize(COMPLEX_UNIT_PX, card_inventory_value_one_text_size);
        }
        mInventory[0].setVisibility(View.GONE);

        float card_loot_value_one_text_size = (float) (Math.sqrt(Math.pow(mLoot[0].mImageView.getWidth(), 2.0) +
                Math.pow(mLoot[0].mImageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_loot_name_text_size = (float) (Math.sqrt(Math.pow(mLoot[0].mImageView.getWidth(), 2.0) +
                Math.pow(mLoot[0].mImageView.getHeight(), 2.0)) * card_name_text_size_constant);
        for (int i = 0; i < LOOT_AND_TRADE_MAX_COUNT; i++) {
            mLoot[i].mNameText.setTextSize(COMPLEX_UNIT_PX, card_loot_name_text_size);
            mLoot[i].mValueOneText.setTextSize(COMPLEX_UNIT_PX, card_loot_value_one_text_size);
        }
        Log.d(""+card_loot_value_one_text_size, ""+card_loot_name_text_size);
        mLoot[0].setVisibility(View.GONE);

        float card_hand_value_one_text_size = (float) (Math.sqrt(Math.pow(mHandOne.mImageView.getWidth(), 2.0) +
                Math.pow(mHandOne.mImageView.getHeight(), 2.0)) * card_hp_and_damage_text_size_constant);
        float card_hand_name_text_size = (float) (Math.sqrt(Math.pow(mHandOne.mImageView.getWidth(), 2.0) +
                Math.pow(mHandOne.mImageView.getHeight(), 2.0)) * card_name_text_size_constant);
        mHandOne.mNameText.setTextSize(COMPLEX_UNIT_PX, card_hand_name_text_size);
        mHandOne.mValueOneText.setTextSize(COMPLEX_UNIT_PX, card_hand_value_one_text_size);
        mHandTwo.mNameText.setTextSize(COMPLEX_UNIT_PX, card_hand_name_text_size);
        mHandTwo.mValueOneText.setTextSize(COMPLEX_UNIT_PX, card_hand_value_one_text_size);


        hp_text_size = (float) (Math.sqrt(Math.pow(hp_view.getWidth(), 2.0) +
                Math.pow(hp_view.getHeight(), 2.0)) * hp_text_size_constant);
        mHpText.setTextSize(COMPLEX_UNIT_PX, hp_text_size);
    }
    AnimatorSet columnCenterCardTableReset = new AnimatorSet();
    private void setAnimators() {
        float cardTableIncreaseAnimationValue = 3f;
        int duration = 500;
        int card_animation_duration = 500;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int card_height = mCardsTable[0].mImageView.getHeight();
        int card_width = mCardsTable[0].mImageView.getWidth();

        int[] card_5_coordinates = new int[2];
        int[] card_8_coordinates = new int[2];
        int[] card_4_coordinates = new int[2];
        int[] card_6_coordinates = new int[2];

        int[] card_inventory_coordinates = new int[2];
        int[] card_hand_coordinates = new int[2];
        int[] card_loot_coordinates = new int[2];
        int[] guideline_inventory_top_coordinates = new int[2];

        mInventory[0].mImageView.getLocationOnScreen(card_inventory_coordinates);
        mHandOne.mImageView.getLocationOnScreen(card_hand_coordinates);
        mLoot[0].mImageView.getLocationOnScreen(card_loot_coordinates);
        findViewById(R.id.guideline_inventory_top).getLocationOnScreen(guideline_inventory_top_coordinates);

        inventory_animation_delta = card_inventory_coordinates[1] + mInventory[0].mImageView.getHeight() -
                displayMetrics.heightPixels;
        hand_animation_delta = card_hand_coordinates[1] + mHandOne.mImageView.getHeight() -
                guideline_inventory_top_coordinates[1];
        loot_animation_delta = displayMetrics.heightPixels * 0.05f;

        mCardCenter.getLocationOnScreen(card_5_coordinates);
        mCardsTable[6].mImageView.getLocationOnScreen(card_8_coordinates);
        mCardsTable[3].mImageView.getLocationOnScreen(card_4_coordinates);
        mCardsTable[4].mImageView.getLocationOnScreen(card_6_coordinates);

        ConstraintLayout table_layout = findViewById(R.id.table);
        int card_distance_between_Y = card_5_coordinates[1] - card_8_coordinates[1];
        int card_distance_between_X = card_5_coordinates[0] - card_4_coordinates[0];
        int card_coordinates_animation_top_start = -card_8_coordinates[1] - card_height;
        int card_coordinates_animation_bottom_start = table_layout.getHeight() + card_height - card_8_coordinates[1];
        int card_coordinates_animation_right_start = table_layout.getWidth() + card_width - card_4_coordinates[0];
        int card_coordinates_animation_left_start = -card_4_coordinates[0] - card_width;

        //region General Animation
        AnimatorSet openCardTableRotateBack = new AnimatorSet();
        openCardTableRotateBack.setDuration(duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[4], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(mCardsTable[3], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(mCardsTable[6], View.ROTATION_Y, 0f, 90f),
                ObjectAnimator.ofFloat(mCardsTable[1], View.ROTATION_Y, 0f, 90f)
        );
        AnimatorListenerAdapter openCardTableRotateBackListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mCardsTable[1].isEmpty()){
                    mCardsTable[1].setIDMob(mNextCardTable[0]);
                    mCardsTable[1].load(mDBOpenHelper);
                    mCardsTable[1].open();
                }
                if (mCardsTable[3].isEmpty()){
                    mCardsTable[3].setIDMob(mNextCardTable[1]);
                    mCardsTable[3].load(mDBOpenHelper);
                    mCardsTable[3].open();
                }
                if (mCardsTable[4].isEmpty()){
                    mCardsTable[4].setIDMob(mNextCardTable[2]);
                    mCardsTable[4].load(mDBOpenHelper);
                    mCardsTable[4].open();
                }
                if (mCardsTable[6].isEmpty()){
                    mCardsTable[6].setIDMob(mNextCardTable[3]);
                    mCardsTable[6].load(mDBOpenHelper);
                    mCardsTable[6].open();
                }
            }
        };
        openCardTableRotateBack.addListener(openCardTableRotateBackListener);
        AnimatorSet openCardTableRotateFront = new AnimatorSet();
        openCardTableRotateFront.setDuration(duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.ROTATION_Y,
                        -90f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.ROTATION_Y,
                        -90f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.ROTATION_Y,
                        -90f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.ROTATION_Y,
                        -90f,
                        0f
                )
        );
        openCardTable.playSequentially(openCardTableRotateBack, openCardTableRotateFront);
        AnimatorListenerAdapter openCardTableListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimate = false;
                Log.d("mIsAnimate", String.valueOf(mIsAnimate));
            }
        };
        openCardTable.addListener(openCardTableListener);

        Animator shadowShow  = ObjectAnimator.ofFloat(
                mShadow,
                View.ALPHA,
                0f,
                .5f
        );

        Animator shadowHide = ObjectAnimator.ofFloat(
                mShadow,
                View.ALPHA,
                .5f,
                0f
        );

        columnCenterCardTableReset.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardCenter,
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );

        AnimatorSet rowCenterCardTableReset = new AnimatorSet();
        columnCenterCardTableReset.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_Y,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardCenter,
                        View.TRANSLATION_Y,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_Y,
                        0f,
                        0f
                )
        );

        AnimatorListenerAdapter cardTableCloseListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardTableTarget.close(mIdDrawableCardBack);
            }
        };
        AnimatorListenerAdapter cardTableCloseBackAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                loadLoot();
            }
        };
        //endregion
        //region a
        card_6_animation_click_vendor.setDuration(card_animation_duration).playTogether(
/*
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, card_4_coordinates[0]-card_6_coordinates[0]),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
*/
                shadowShow
        );

        card_animation_right.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_X, 0f, card_coordinates_animation_right_start),

                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_X, 0f, card_distance_between_X),
                ObjectAnimator.ofFloat(mCardCenter, View.TRANSLATION_X, 0f, card_distance_between_X)
        );

        card_animation_down.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, 0f, card_coordinates_animation_bottom_start),

                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, 0f, card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardCenter, View.TRANSLATION_Y, 0f, card_distance_between_Y)
        );

        card_animation_up.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, 0f, card_coordinates_animation_top_start),

                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, 0f, -card_distance_between_Y),
                ObjectAnimator.ofFloat(mCardCenter, View.TRANSLATION_Y, 0f, -card_distance_between_Y)
        );

        card_animation_get_top.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, card_coordinates_animation_top_start, 0f)
        );

        card_animation_get_bottom.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, card_coordinates_animation_bottom_start, 0f)
        );

        card_animation_get_left.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f),
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_X, card_coordinates_animation_left_start, 0f)
        );
        //endregion
        //region reset animation
        card_reset_row_top.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[5], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[6], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_center.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[3], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardCenter, View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_row_bottom.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[0], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[1], View.TRANSLATION_Y, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_Y, 0f, 0f)
        );

        card_reset_column_right.playTogether(
                ObjectAnimator.ofFloat(mCardsTable[2], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[4], View.TRANSLATION_X, 0f, 0f),
                ObjectAnimator.ofFloat(mCardsTable[7], View.TRANSLATION_X, 0f, 0f)
        );
        //endregion
        //region Animation Right Card Table
        AnimatorSet rightCardTableIncreaseAnimation = new AnimatorSet();
        rightCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        Animator rightCardTableMoveAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.TRANSLATION_X,
                0f,
                -card_distance_between_X
        );
        mCardsTable[4].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                rightCardTableIncreaseAnimation,
                rightCardTableMoveAnimation
        );
        Animator rightCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.ROTATION_Y,
                0f,
                90f
        );
        rightCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator rightCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.ROTATION_Y,
                -90f,
                0f
        );
        rightCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[4].getCloseAnimation().playSequentially(
                rightCardTableCloseFrontAnimation,
                rightCardTableCloseBackAnimation
        );
        AnimatorSet rightCardTableDicreaseAnimation = new AnimatorSet();
        rightCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator rightCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[4],
                View.TRANSLATION_X,
                -card_distance_between_X,
                0f
        );
        AnimatorSet rightCardTableTargetResetAnimation = new AnimatorSet();
        rightCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                rightCardTableDicreaseAnimation,
                rightCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToLeft = new AnimatorSet();
        cardTableMoveToLeft.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardCenter,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToLeftListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[0].copy(mCardsTable[1]);
                mCardsTable[3].close(cardCenterBack);
                mCardsTable[5].copy(mCardsTable[6]);
            }
        };
        cardTableMoveToLeft.addListener(cardTableMoveToLeftListener);

        AnimatorSet columnLeftCardTableResetAnimation = new AnimatorSet();
        columnLeftCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter columnLeftCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[1].copy(mCardsTable[2]);
                mCardsTable[6].copy(mCardsTable[7]);
            }
        };
        columnLeftCardTableResetAnimation.addListener(columnLeftCardTableResetAnimationListener);

        AnimatorSet getColumnRightAnimation = new AnimatorSet();
        getColumnRightAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getColumnRightAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardsTable[2].close(mIdDrawableCardBack);
                mCardsTable[4].close(mIdDrawableCardBack);
                mCardsTable[7].close(mIdDrawableCardBack);
            }
            @Override
            public void onAnimationEnd(Animator animation) {}
        };
        getColumnRightAnimation.addListener(getColumnRightAnimationListener);

        mCardsTable[4].getChangeAnimation().playSequentially(
                rightCardTableTargetResetAnimation,
                cardTableMoveToLeft,
                columnLeftCardTableResetAnimation,
                columnCenterCardTableReset,
                getColumnRightAnimation,
                openCardTable
        );
        //endregion
        //region Animation Bottom Card Table
        AnimatorSet mBottomCardTableIncreaseAnimation = new AnimatorSet();
        mBottomCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        AnimatorSet mBottomCardTableDicreaseAnimation = new AnimatorSet();
        mBottomCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator card_2_animation_movement = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.TRANSLATION_Y,
                0f,
                -card_distance_between_Y
        );
        mCardsTable[1].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                mBottomCardTableIncreaseAnimation,
                card_2_animation_movement
        );

        Animator bottomCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.ROTATION_Y,
                0f,
                90f
        );
        bottomCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator bottomCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.ROTATION_Y,
                -90f,
                0f
        );
        bottomCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[1].getCloseAnimation().playSequentially(
                bottomCardTableCloseFrontAnimation,
                bottomCardTableCloseBackAnimation
        );

        AnimatorSet bottomCardTableDicreaseAnimation = new AnimatorSet();
        bottomCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator bottomCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[1],
                View.TRANSLATION_Y,
                -card_distance_between_Y,
                0f
        );

        AnimatorSet bottomCardTableTargetResetAnimation = new AnimatorSet();
        bottomCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                bottomCardTableDicreaseAnimation,
                bottomCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToUp = new AnimatorSet();
        cardTableMoveToUp.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardCenter,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToUpListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[5].copy(mCardsTable[3]);
                mCardsTable[6].close(cardCenterBack);
                mCardsTable[7].copy(mCardsTable[4]);
            }
        };
        cardTableMoveToUp.addListener(cardTableMoveToUpListener);

        AnimatorSet rowTopCardTableResetAnimation = new AnimatorSet();
        rowTopCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter rowTopCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[3].copy(mCardsTable[0]);
                mCardsTable[4].copy(mCardsTable[2]);
            }
        };
        rowTopCardTableResetAnimation.addListener(rowTopCardTableResetAnimationListener);

        AnimatorSet getRowBottomAnimation = new AnimatorSet();
        getRowBottomAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getRowBottomAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardsTable[0].close(mIdDrawableCardBack);
                mCardsTable[1].close(mIdDrawableCardBack);
                mCardsTable[2].close(mIdDrawableCardBack);
            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        };
        getRowBottomAnimation.addListener(getRowBottomAnimationListener);

        mCardsTable[1].getChangeAnimation().playSequentially(
                bottomCardTableTargetResetAnimation,
                cardTableMoveToUp,
                rowTopCardTableResetAnimation,
                columnCenterCardTableReset,
                getRowBottomAnimation,
                openCardTable
        );
        //endregion
        //region Animation Left Card Table
        AnimatorSet mLeftCardTableIncreaseAnimation = new AnimatorSet();
        mLeftCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        AnimatorSet mLeftCardTableDicreaseAnimation = new AnimatorSet();
        mLeftCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator card_4_animation_movement = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.TRANSLATION_X,
                0f,
                card_distance_between_X
        );
        mCardsTable[3].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                mLeftCardTableIncreaseAnimation,
                card_4_animation_movement
        );

        Animator leftCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.ROTATION_Y,
                0f,
                90f
        );
        leftCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator leftCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.ROTATION_Y,
                -90f,
                0f
        );
        leftCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[3].getCloseAnimation().playSequentially(
                leftCardTableCloseFrontAnimation,
                leftCardTableCloseBackAnimation
        );

        AnimatorSet leftCardTableDicreaseAnimation = new AnimatorSet();
        leftCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator leftCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[3],
                View.TRANSLATION_X,
                card_distance_between_X,
                0f
        );

        AnimatorSet leftCardTableTargetResetAnimation = new AnimatorSet();
        leftCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                leftCardTableDicreaseAnimation,
                leftCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToRight = new AnimatorSet();
        cardTableMoveToRight.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardCenter,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToRightListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[2].copy(mCardsTable[1]);
                mCardsTable[4].close(cardCenterBack);
                mCardsTable[7].copy(mCardsTable[6]);
            }
        };
        cardTableMoveToRight.addListener(cardTableMoveToRightListener);

        AnimatorSet columnRingtCardTableResetAnimation = new AnimatorSet();
        columnRingtCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter columnRingtCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[1].copy(mCardsTable[0]);
                mCardsTable[6].copy(mCardsTable[5]);
            }
        };
        columnRingtCardTableResetAnimation.addListener(columnRingtCardTableResetAnimationListener);

        AnimatorSet getColumnLeftAnimation = new AnimatorSet();
        getColumnLeftAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getColumnLeftAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardsTable[0].close(mIdDrawableCardBack);
                mCardsTable[3].close(mIdDrawableCardBack);
                mCardsTable[5].close(mIdDrawableCardBack);
            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        };
        getColumnLeftAnimation.addListener(getColumnLeftAnimationListener);

        mCardsTable[3].getChangeAnimation().playSequentially(
                leftCardTableTargetResetAnimation,
                cardTableMoveToRight,
                columnRingtCardTableResetAnimation,
                columnCenterCardTableReset,
                getColumnLeftAnimation,
                openCardTable
        );
        //endregion
        //region Animation Top Card Table
        AnimatorSet mTopCardTableIncreaseAnimation = new AnimatorSet();
        mTopCardTableIncreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_X,
                        1f,
                        cardTableIncreaseAnimationValue
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_Y,
                        1f,
                        cardTableIncreaseAnimationValue
                )
        );
        AnimatorSet mTopCardTableDicreaseAnimation = new AnimatorSet();
        mTopCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator card_8_animation_movement = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.TRANSLATION_Y,
                0f,
                card_distance_between_Y
        );
        mCardsTable[6].getTargetAnimation().setDuration(duration).playTogether(
                shadowShow,
                mTopCardTableIncreaseAnimation,
                card_8_animation_movement
        );

        Animator topCardTableCloseFrontAnimation = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.ROTATION_Y,
                0f,
                90f
        );
        topCardTableCloseFrontAnimation.addListener(cardTableCloseListener);
        Animator topCardTableCloseBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.ROTATION_Y,
                -90f,
                0f
        );
        topCardTableCloseBackAnimation.addListener(cardTableCloseBackAnimationListener);
        mCardsTable[6].getCloseAnimation().playSequentially(
                topCardTableCloseFrontAnimation,
                topCardTableCloseBackAnimation
        );

        AnimatorSet topCardTableDicreaseAnimation = new AnimatorSet();
        topCardTableDicreaseAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_X,
                        cardTableIncreaseAnimationValue,
                        1f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.SCALE_Y,
                        cardTableIncreaseAnimationValue,
                        1f
                )
        );
        Animator topCardTableMoveBackAnimation = ObjectAnimator.ofFloat(
                mCardsTable[6],
                View.TRANSLATION_Y,
                card_distance_between_Y,
                0f
        );

        AnimatorSet topCardTableTargetResetAnimation = new AnimatorSet();
        topCardTableTargetResetAnimation.setDuration(duration).playTogether(
                shadowHide,
                topCardTableDicreaseAnimation,
                topCardTableMoveBackAnimation
        );

        AnimatorSet cardTableMoveToDown = new AnimatorSet();
        cardTableMoveToDown.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        card_coordinates_animation_left_start
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[1],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[6],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                ),
                ObjectAnimator.ofFloat(
                        mCardCenter,
                        View.TRANSLATION_X,
                        0f,
                        -card_distance_between_X
                )
        );
        AnimatorListenerAdapter cardTableMoveToDownListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[0].copy(mCardsTable[3]);
                mCardsTable[1].close(cardCenterBack);
                mCardsTable[2].copy(mCardsTable[4]);
            }
        };
        cardTableMoveToDown.addListener(cardTableMoveToDownListener);

        AnimatorSet rowBottomCardTableResetAnimation = new AnimatorSet();
        rowBottomCardTableResetAnimation.playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[0],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[3],
                        View.TRANSLATION_X,
                        0f,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[5],
                        View.TRANSLATION_X,
                        0f,
                        0f
                )
        );
        AnimatorListenerAdapter rowBottomCardTableResetAnimationListener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCardsTable[3].copy(mCardsTable[5]);
                mCardsTable[4].copy(mCardsTable[7]);
            }
        };
        rowBottomCardTableResetAnimation.addListener(rowBottomCardTableResetAnimationListener);

        AnimatorSet getRowTopAnimation = new AnimatorSet();
        getRowTopAnimation.setDuration(card_animation_duration).playTogether(
                ObjectAnimator.ofFloat(
                        mCardsTable[2],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[4],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                ),
                ObjectAnimator.ofFloat(
                        mCardsTable[7],
                        View.TRANSLATION_X,
                        card_coordinates_animation_right_start,
                        0f
                )
        );
        AnimatorListenerAdapter getRowTopAnimationListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mCardsTable[5].close(mIdDrawableCardBack);
                mCardsTable[6].close(mIdDrawableCardBack);
                mCardsTable[7].close(mIdDrawableCardBack);
            }
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        };
        getRowTopAnimation.addListener(getRowTopAnimationListener);

        mCardsTable[6].getChangeAnimation().playSequentially(
                topCardTableTargetResetAnimation,
                cardTableMoveToDown,
                rowBottomCardTableResetAnimation,
                columnCenterCardTableReset,
                getRowTopAnimation,
                openCardTable
        );
        //endregion
    }

    public void onClickButtonStart(View view) {
        mButtonStart.setVisibility(View.GONE);

        Byte[] startItemId = new Byte[6];
        for (byte i = 0;i<INVENTORY_MAX_COUNT;i++) {
            startItemId[i] = mInventory[i].getIDItem();
        }
        startItemId[4] = mHandOne.getIDItem();
        startItemId[5] = mHandTwo.getIDItem();
        String requestString = null;
        try {
            request.setData(mJackson.writeValueAsString(startItemId));
            requestString = mJackson.writeValueAsString(request);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        post("start", requestString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("start onFailure", e.toString());
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("start responseStr", responseStr);
                MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                if (!myResponse.isError()){
                    mNextCardTable = mJackson.readValue(myResponse.getData(), byte[].class);
                    mTable.post(() -> {
                        collectionButton.setVisibility(View.GONE);
                        mStatsButton.setVisibility(View.GONE);
                        setHPInUIThread(mHpMaxDefault+mStats.getHPBonus());
                        mHandOne.load(mStats, mDBOpenHelper);
                        mHandTwo.load(mStats, mDBOpenHelper);
                        for (int i = 0; i < 8; i++) {
                            mCardsTable[i].setVisibility(View.VISIBLE);
                        }
                        mCardCenter.setVisibility(View.VISIBLE);
                        for (byte i = 0; i < INVENTORY_MAX_COUNT; i++) {
                            mInventory[i].setOnClickListener(mInventoryOnClickSwap);
                        }
                        mHandOne.setOnClickListener(mInventoryOnClickSwap);
                        mHandTwo.setOnClickListener(mInventoryOnClickSwap);
                        setMoneyInUIThread(0);
                        openCardTable.start();
                    });
                }
            }
        });
    }

    public void onClickButtonContinue(View view) {
        String requestString = null;
        ArrayList<CardPlayerResponse> requestData = new ArrayList<>();
        for (byte i = 0; i < INVENTORY_MAX_COUNT;i++) {
            if (!mInventory[i].isEmpty()){
                requestData.add(new CardPlayerResponse(mInventory[i].getIDItem(), i, (byte) mInventory[i].getDurability()));
            }
            else{
                break;
            }
        }
        requestData.add(new CardPlayerResponse(mHandOne.getIDItem(), (byte) 4, (byte) mHandOne.getDurability()));
        requestData.add(new CardPlayerResponse(mHandTwo.getIDItem(), (byte) 5, (byte) mHandTwo.getDurability()));
        try {
            request.setData(mJackson.writeValueAsString(requestData));
            requestString = mJackson.writeValueAsString(request);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        post("continue", requestString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("continue onFailure", e.toString());
            }
            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("continue responseStr", responseStr);
                MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                if (!myResponse.isError()){
                    mNextCardTable = mJackson.readValue(myResponse.getData(), byte[].class);
                    mTable.post(() -> {
                        setSelectTarget();
                    });
                }
            }
        });
    }

    View.OnLongClickListener mOnLongClickListener = mOnLongClickListener();
    View.OnLongClickListener mOnLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mTargetSwap.getType() == InventoryType.FOOD) {
                    useFood();
                    return true;
                }
                if (mTargetSwap.getType() == InventoryType.SPELL) {
                    useSpell();
                    return true;
                }
                if(mTargetSwap.mSlotType == SlotType.HAND){
                    CardHand card_hand = (CardHand) mTargetSwap;
                    if (card_hand.getIDItem()!=card_hand.getIDDefault()){
                        if (mInventoryItemCount < INVENTORY_MAX_COUNT){
                            mInventory[mInventoryItemCount].copy(card_hand);
                            mInventory[mInventoryItemCount].setVisibility(View.VISIBLE);
                            mInventoryItemCount++;
                            card_hand.setIDItem(card_hand.getIDDefault());
                            card_hand.load(mStats, mDBOpenHelper);
                        }
                        else{
                            mDialogWindow.openInfo("Нет места в инвентаре.");
                        }
                        Log.d("resetTargetSwap", "hand take off");
                        resetTargetSwap();
                    }
                    return true;
                }
                return true;
            }
        };
    }

    View.OnTouchListener mCardMoveListener = mCardMoveListener();
    View.OnTouchListener mCardMoveListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_MOVE==event.getAction()){
                    if (event.getHistorySize()==1){
                        if (event.getY()<event.getHistoricalY(0)) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(mTargetSwap);
                            mTargetSwap.startDrag(data, shadowBuilder, mTargetSwap, 0);
                            mTargetSwap.setVisibility(View.INVISIBLE);
                            Log.d("INVISIBLE", "onTouch: ");
                        }
                    }
                }
                return false;
            }
        };
    }

    View.OnDragListener mHandOnDropListener = mHandOnDropListener();
    View.OnDragListener mHandOnDropListener() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    mTargetSwap.post(new Runnable() {
                        @Override
                        public void run() {
                            mTargetSwap.setVisibility(View.VISIBLE);
                        }
                    });
                    return false;
                }
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    CardHand target_swap_two = ((CardHand) v);
                    if (target_swap_two.mSlotType != mTargetSwap.mSlotType) {
                        if (mTargetSwap.getType() == InventoryType.SHIELD || mTargetSwap.getType() ==
                                InventoryType.WEAPON) {
                            if(target_swap_two.mDurabilityText.getVisibility()==View.VISIBLE){
                                inventory_temp.Copy(target_swap_two);
                                target_swap_two.copy(mTargetSwap);
                                mTargetSwap.copy(inventory_temp);
                                mTargetSwap.setVisibility(View.VISIBLE);
                            }
                            else{
                                target_swap_two.copy(mTargetSwap);
                                target_swap_two.mDurabilityText.setVisibility(View.VISIBLE);
                                target_swap_two.mDurabilityImage.setVisibility(View.VISIBLE);
                                mInventoryItemCount--;
                                inventorySort();
                                tryPickingLoot();
                            }
                            Log.d("resetTargetSwap", "hand swap");
                            resetTargetSwap();
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    View.OnDragListener mLootOnDropListener = mLootOnDropListener();
    View.OnDragListener mLootOnDropListener() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    mTargetSwap.post(new Runnable() {
                        @Override
                        public void run() {
                            mTargetSwap.setVisibility(View.VISIBLE);
                        }
                    });
                    return false;
                }
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    CardInventory target_swap_two = ((CardInventory) v);
                    if (mTargetSwap.mSlotType == SlotType.INVENTORY) {
                        inventory_temp.Copy(target_swap_two);
                        target_swap_two.copy(mTargetSwap);
                        mTargetSwap.copy(inventory_temp);
                        mTargetSwap.setVisibility(View.VISIBLE);
                        Log.d("resetTargetSwap", "loot swap");
                        resetTargetSwap();
                        return true;
                    }
                    else {
                        if (target_swap_two.getType() == InventoryType.WEAPON ||
                                        target_swap_two.getType() == InventoryType.SHIELD)
                        {
                            if(((CardHand) mTargetSwap).mDurabilityText.getVisibility()==View.VISIBLE){
                                        inventory_temp.Copy(target_swap_two);
                                        target_swap_two.copy(mTargetSwap);
                                        mTargetSwap.copy(inventory_temp);
                            }
                            else{
                                        mTargetSwap.copy(target_swap_two);
                                        ((CardHand) mTargetSwap).mDurabilityText.setVisibility(View.VISIBLE);
                                        ((CardHand) mTargetSwap).mDurabilityImage.setVisibility(View.VISIBLE);
                                        target_swap_two.setVisibility(View.GONE);
                                        mLootCount--;
                                        tryContinue();
                            }
                            mTargetSwap.setVisibility(View.VISIBLE);
                            Log.d("resetTargetSwap", "loot swap");
                            resetTargetSwap();
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    View.OnDragListener mTableOnDropListener = mTableOnDropListener();
    View.OnDragListener mTableOnDropListener() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    mTargetSwap.post(new Runnable() {
                        @Override
                        public void run() {
                            mTargetSwap.setVisibility(View.VISIBLE);
                        }
                    });
                    return false;
                }
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    if (mTargetSwap.getType() == InventoryType.FOOD) {
                        useFood();
                        return true;
                    }
                    if (mTargetSwap.getType() == InventoryType.SPELL) {
                        useSpell();
                        return true;
                    }
                }

                return false;
            }
        };
    }

    View.OnClickListener setTargetListener = setTargetListener();
    View.OnClickListener setTargetListener() {
        return v -> {
            if (!mIsAnimate) {
                mCardTableTarget = (CardTable) v;
                String requestString = null;
                try {
                    request.setData(mJackson.writeValueAsString(mCardTableTarget.getIdInArray()));
                    requestString = mJackson.writeValueAsString(request);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                post("target", requestString, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("target onFailure", e.toString());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseStr = response.body().string();
                        Log.d("target response ", responseStr);
                        MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                        if (!myResponse.isError()){
                            mTable.post(() -> {
                                mIsAnimate = true;
                                Log.d("mIsAnimate", String.valueOf(mIsAnimate));
                                if(mCardTableTarget.getType() == CardTableType.MOB){
                                    mCardTableTarget.bringToFront();
                                    mCardTableTarget.getTargetAnimation().start();
                                    mCardTableTarget.setOnClickListener(mDamageListener);
                                    return;
                                }
                                if (mCardTableTarget.getType() == CardTableType.VENDOR){
                                    card_6_animation_click_vendor.start();
                                    CardPlayerResponse[] cardTrade = null;
                                    try {
                                        cardTrade = mJackson.readValue(
                                                myResponse.getData(),
                                                CardPlayerResponse[].class
                                        );
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    mShadow.bringToFront();
                                    mCardTableTarget.bringToFront();
                                    for (byte i = 0; i< LOOT_AND_TRADE_MAX_COUNT; i++){
                                        mTradeItem[i].setIDItem(cardTrade[i].getIdItem());
                                        mTradeItem[i].load(mStats, mDBOpenHelper);
                                        mTradeItem[i].setDurability(cardTrade[i].getDurability());
                                        mTradeItem[i].open();
                                        mTradeItem[i].setVisibility(View.VISIBLE);
                                        mTradeCost[i].setText(String.format("%d", mTradeItem[i].getCost()));
                                        mTradeCost[i].setVisibility(View.VISIBLE);
                                        mTradeCostImage[i].setVisibility(View.VISIBLE);
                                    }
                                    mTradeSkill.setVisibility(View.VISIBLE);
                                    mTradeZone.setVisibility(View.VISIBLE);
                                    mTable.setOnDragListener(null);
                                    if(mCardTableTarget.getSubType() == CardTableSubType.TRADER){
                                        Picasso.with(getBaseContext())
                                                .load(R.drawable.navik_torgovca)
                                                .into(mTradeSkillImage);
                                        mTradeSkillImage.setOnClickListener(mOnClickVendorSkill);
                                        return;
                                    }
                                    if (mCardTableTarget.getSubType() == CardTableSubType.BLACKSMITH){
                                        Picasso.with(getBaseContext())
                                                .load(R.drawable.navik_kuznecaa)
                                                .into(mTradeSkillImage);
                                        mTradeSkillImage.setOnClickListener(null);
                                        return;
                                    }
                                    if (mCardTableTarget.getSubType() == CardTableSubType.INNKEEPER){
                                        Picasso.with(getBaseContext())
                                                .load(R.drawable.navik_traktirshika)
                                                .into(mTradeSkillImage);
                                        mTradeSkillImage.setOnClickListener(mOnClickInnkeeperSkill);
                                        return;
                                    }
                                }
                                if (mCardTableTarget.getType() == CardTableType.HALT){
                                    mShadow.bringToFront();
                                    mCardTableTarget.bringToFront();
                                    mCardTableTarget.getTargetAnimation().start();
                                    changeHPInUIThread(mHpMax);
                                    mCardTableTarget.getChangeAnimation().start();
                                    return;
                                }
                                if (mCardTableTarget.getType() == CardTableType.CHEST){
                                    mShadow.bringToFront();
                                    mCardTableTarget.bringToFront();
                                    mCardTableTarget.getTargetAnimation().start();
                                    mCardTableTarget.getCloseAnimation().start();
                                    //changeMoneyInUIThread(mCardTableTarget.getMoney());
                                }
                            });
                        }
                    }
                });
            }
        };
    }

    View.OnClickListener mInventoryOnClickSwap = mInventoryOnClickSwap();
    View.OnClickListener mInventoryOnClickSwap() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_first_click) {
                    mTargetSwap = (CardInventory) v;
                    if (mTargetSwap.mSlotType == SlotType.HAND) {
                        target_on_animation = ObjectAnimator.ofFloat(mTargetSwap, View.TRANSLATION_Y, 0f, -hand_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(mTargetSwap, View.TRANSLATION_Y, -hand_animation_delta, 0f);
                    }
                    if (mTargetSwap.mSlotType == SlotType.LOOT) {
                        target_on_animation = ObjectAnimator.ofFloat(mTargetSwap, View.TRANSLATION_Y, 0f, -loot_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(mTargetSwap, View.TRANSLATION_Y, -loot_animation_delta, 0f);

                    }
                    if (mTargetSwap.mSlotType == SlotType.INVENTORY) {
                        target_on_animation = ObjectAnimator.ofFloat(mTargetSwap, View.TRANSLATION_Y, 0f, -inventory_animation_delta);
                        target_off_animation = ObjectAnimator.ofFloat(mTargetSwap, View.TRANSLATION_Y, -inventory_animation_delta, 0f);
                    }

                    Log.d("target on", String.valueOf(mTargetSwap.mNameText.getText()));
                    is_first_click = false;

                    target_off_animation.addListener(on_target_off_animation);
                    target_on_animation.addListener(target_on_animation_end);
                    target_on_animation.start();

                    if (mTradeSkill.getVisibility()==View.VISIBLE && mCardTableTarget.getSubType()== CardTableSubType.BLACKSMITH&&
                            (mTargetSwap.getType()== InventoryType.SHIELD|| mTargetSwap.getType()== InventoryType.WEAPON)){
                        mTradeSkillImage.setOnClickListener(mOnClickBlacksmithSkill);
                    }
                }
                else {
                    CardInventory targetSwapTwo = (CardInventory) v;
                    if (!mTargetSwap.equals(targetSwapTwo) ) {
                        if (targetSwapTwo.mSlotType != mTargetSwap.mSlotType) {
                            if (targetSwapTwo.mSlotType == SlotType.HAND) {
                                CardHand targetSwapTwoHand = (CardHand) v;
                                if (mTargetSwap.getType() == InventoryType.SHIELD || mTargetSwap.getType() ==
                                        InventoryType.WEAPON) {
                                    if(targetSwapTwoHand.mDurabilityText.getVisibility()==View.VISIBLE){
                                        inventory_temp.Copy(targetSwapTwoHand);
                                        targetSwapTwoHand.copy(mTargetSwap);
                                        mTargetSwap.copy(inventory_temp);
                                    }
                                    else{
                                        targetSwapTwoHand.copy(mTargetSwap);
                                        targetSwapTwoHand.mDurabilityText.setVisibility(View.VISIBLE);
                                        targetSwapTwoHand.mDurabilityImage.setVisibility(View.VISIBLE);
                                        mInventoryItemCount--;
                                        inventorySort();
                                        tryPickingLoot();
                                    }
                                    Log.d("resetTargetSwap", "hand swap click");
                                    resetTargetSwap();
                                }
                            }
                            if (targetSwapTwo.mSlotType == SlotType.LOOT) {
                                if (mTargetSwap.mSlotType == SlotType.INVENTORY) {
                                    inventory_temp.Copy(targetSwapTwo);
                                    targetSwapTwo.copy(mTargetSwap);
                                    mTargetSwap.copy(inventory_temp);
                                    Log.d("resetTargetSwap", "loot swap click");
                                    resetTargetSwap();
                                }
                                else {
                                    if (targetSwapTwo.getType() == InventoryType.WEAPON ||
                                            targetSwapTwo.getType() == InventoryType.SHIELD)
                                    {
                                        if(((CardHand) mTargetSwap).mDurabilityText.getVisibility()==View.VISIBLE){
                                            inventory_temp.Copy(targetSwapTwo);
                                            targetSwapTwo.copy(mTargetSwap);
                                            mTargetSwap.copy(inventory_temp);
                                        }
                                        else{
                                            mTargetSwap.copy(targetSwapTwo);
                                            ((CardHand) mTargetSwap).mDurabilityText.setVisibility(View.VISIBLE);
                                            ((CardHand) mTargetSwap).mDurabilityImage.setVisibility(View.VISIBLE);
                                            targetSwapTwo.setVisibility(View.GONE);
                                            mLootCount--;
                                            tryContinue();
                                        }
                                        Log.d("resetTargetSwap", "loot swap click");
                                        resetTargetSwap();
                                    }
                                }
                            }
                        }
                    }
                    else {
                        Log.d("resetTargetSwap", "target off");
                        resetTargetSwap();
                    }
                }
            }
        };
    }

    View.OnClickListener mDamageListener = mDamageListener();
    View.OnClickListener mDamageListener() {
        return v -> {
            String requestString = null;
            try {
                CardPlayerResponse[] cardPlayer = new CardPlayerResponse[2];
                cardPlayer[0] = new CardPlayerResponse(mHandOne.getIDItem(),(byte)4, (byte)mHandOne.getDurability());
                cardPlayer[1] = new CardPlayerResponse(mHandTwo.getIDItem(),(byte)5, (byte)mHandTwo.getDurability());
                request.setData(mJackson.writeValueAsString(cardPlayer));
                requestString = mJackson.writeValueAsString(request);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            post("damage", requestString, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("damage onFailure", e.toString());
                }
                @Override
                public void onResponse(final Call call, Response response) throws IOException {
                    String responseStr = response.body().string();
                    Log.d("damage responseStr", responseStr);
                    MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                    if (!myResponse.isError()){
                        mTable.post(() -> {
                            int mobDamage = mCardTableTarget.getValueOne();
                            if(mobDamage>0&& mHandOne.getType() == InventoryType.SHIELD){
                                if (mHandOne.getValueOne() < mobDamage){
                                    mobDamage -= mHandOne.getValueOne();
                                }
                                else{
                                    mobDamage = 0;
                                }
                            }
                            if(mobDamage>0&& mHandTwo.getType() == InventoryType.SHIELD){
                                if (mHandTwo.getValueOne() < mobDamage){
                                    mobDamage -= mHandTwo.getValueOne();
                                }
                                else{
                                    mobDamage = 0;
                                }
                            }
                            if (mobDamage>0){
                                changeHP(-mobDamage);
                            }
                            if (mHp < 1) {
                                mMoneyBank += mMoney;
                                mMoney = 0;
                                resetGame();
                                mTable.post(() -> {
                                    changeHPInUIThread(mHpMax);
                                    mCardTableTarget.getChangeAnimation().start();
                                    mCardTableTarget.getChangeAnimation().end();
                                    collectionButton.setVisibility(View.VISIBLE);
                                    mStatsButton.setVisibility(View.VISIBLE);
                                    mButtonStart.setVisibility(View.VISIBLE);
                                });
                                return;
                            }
                            updateHPText();
                            if(mHandOne.mDurabilityText.getVisibility()==View.VISIBLE){
                                mHandOne.setDurabilityInUIThread(mHandOne.getDurability()-1);
                                if (mHandOne.getDurability()<1){
                                    mHandOne.setIDItem(mHandOne.getIDDefault());
                                    mHandOne.load(mStats, mDBOpenHelper);
                                }
                                else{
                                    mHandOne.updateDurabilityText();
                                }
                            }
                            if(mHandTwo.mDurabilityText.getVisibility()==View.VISIBLE){
                                mHandTwo.setDurabilityInUIThread(mHandTwo.getDurability()-1);
                                if (mHandTwo.getDurability()<1){
                                    mHandTwo.setIDItem(mHandTwo.getIDDefault());
                                    mHandTwo.load(mStats, mDBOpenHelper);
                                }
                                else{
                                    mHandTwo.updateDurabilityText();
                                }
                            }

                            int damage = ((mHandOne.getType() == InventoryType.WEAPON) ?
                                    mHandOne.getValueOne() : 0) + ((mHandTwo.getType() == InventoryType.WEAPON) ?
                                    mHandTwo.getValueOne() : 0);
                            mCardTableTarget.changeValueTwo(-damage);

                            if (mCardTableTarget.getValueTwo() < 1){
                                try {
                                    DamageResponse damageResponse = mJackson.readValue(
                                            myResponse.getData(),
                                            DamageResponse.class
                                    );
                                    mNextCardTable = damageResponse.getCardTableID();
                                    CardPlayerResponse[] loot = mJackson.readValue(
                                            damageResponse.getLoot(),
                                            CardPlayerResponse[].class
                                    );
                                    mLootCount = loot.length;
                                    for (int i = 0; i < mLootCount; i++) {
                                        mLoot[i].bringToFront();
                                        mLoot[i].setIDItem(loot[i].getIdItem());
                                        mLoot[i].setDurability(loot[i].getDurability());
                                    }
                                    mobDead();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                mCardTableTarget.updateValueTwoText();
                            }
                        });
                    }
                }
            });
        };
    }

    //region Stats
    Stats mStats;
    int mStatsResetCost;
    public void onClickIconStats(View view){
        mStats.setVisibility();
    }
    public void onClickDamageButtonMinus(View view){
        mStats.removeDamagePoint();
    }
    public void onClickDamageButtonPlus(View view){
        mStats.addDamagePoint();
    }
    public void onClickDefenceButtonMinus(View view){
        mStats.removeDefencePoint();
    }
    public void onClickDefenceButtonPlus(View view){
        mStats.addDefencePoint();
    }
    public void onClickHPButtonMinus(View view){
        mStats.removeHPPoint();
    }
    public void onClickHPButtonPlus(View view){
        mStats.addHPPoint();
    }
    View.OnClickListener onClickConfirmButtonListener = onClickConfirmButtonListener();
    View.OnClickListener onClickConfirmButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestString = null;
                try {
                    request.setData(mStats.confirm(mJackson));
                    requestString = mJackson.writeValueAsString(request);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                post("stats/confirm", requestString, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("stats/confirm onFailure", e.toString());
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseStr = response.body().string();
                                Log.d("stats/confirm response ", responseStr);
                                MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                                if (!myResponse.isError()){
                                    Log.d("stats confirm", "");
                                }
                            }
                });
                mDialogWindow.close();
            }
        };
    }
    public void onClickConfirmButton(View view){
        mDialogWindow.openDialog(
                "Подтвердить изменения?",
                onClickConfirmButtonListener
        );
    }
    View.OnClickListener onClickStatsResetListener = onClickStatsResetListener();
    View.OnClickListener onClickStatsResetListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestString = null;
                try {
                    requestString = mJackson.writeValueAsString(request);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                post("stats/reset", requestString, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("stats/reset onFailure", e.toString());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseStr = response.body().string();
                        Log.d("stats/reset response ", responseStr);
                        MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                        if (!myResponse.isError()){
                            mTable.post(new Runnable() {
                                @Override
                                public void run() {
                                    mStats.reset();
                                    changeMoneyBankInUIThread(-mStatsResetCost);
                                    mDialogWindow.close();
                                    Log.d("stats reset", " ok");
                                }
                            });
                        }
                    }
                });
            }
        };
    }
    public void onClickStatsReset(View view){
        if (mMoneyBank >= mStatsResetCost){
            mDialogWindow.openDialog(
                    String.format("Сбросить всё за %d золота.", mStatsResetCost),
                    onClickStatsResetListener
            );
        }
        else{
            mDialogWindow.openInfo(
                    String.format("Недостаточно %d золота.", mStatsResetCost - mMoney)
            );
        }
    }
    //endregion
    //region Trade
    View.OnClickListener mOnClickBuyCardListener = mOnClickBuyCardListener();
    View.OnClickListener mOnClickBuyCardListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoney >=((CardInventory)v).getCost()&& mInventoryItemCount < INVENTORY_MAX_COUNT){
                    trade_target = (CardInventory)v;
                    mDialogWindow.openDialog(
                            String.format("Купить карту за %d золотых?", trade_target.getCost()),
                            mCardTradeBuyClickYes
                    );
                }
                else{
                    if (!(mInventoryItemCount < INVENTORY_MAX_COUNT)){
                        mDialogWindow.openInfo("Нет места в инвентаре.");
                    }
                    else{
                        mDialogWindow.openInfo("Недостаточно золота.");
                    }
                }
            }
        };
    }

    View.OnClickListener mCardTradeBuyClickYes = mCardTradeBuyClickYes();
    View.OnClickListener mCardTradeBuyClickYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                trade_target.setVisibility(View.INVISIBLE);
                mTradeCost[trade_target.getSlotId()].setVisibility(View.INVISIBLE);
                mTradeCostImage[trade_target.getSlotId()].setVisibility(View.INVISIBLE);
                mInventory[mInventoryItemCount].copy(trade_target);
                mInventory[mInventoryItemCount].setVisibility(View.VISIBLE);
                mInventoryItemCount++;
                changeMoneyInUIThread(-trade_target.getCost());
            }
        };
    }

    View.OnClickListener mCardTradeSellClickYes = mCardTradeSellClickYes();
    View.OnClickListener mCardTradeSellClickYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                mTargetSwap.setVisibility(View.INVISIBLE);
                mInventoryItemCount--;
                inventorySort();
                changeMoneyInUIThread(mTargetSwap.getCost());
                resetTargetSwap();
            }
        };
    }

    View.OnDragListener mOnSell = mOnSell();
    View.OnDragListener mOnSell() {
        return new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

/*
                if (!event.getResult() && event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    return true;
                }
*/
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    mTargetSwap.setVisibility(View.VISIBLE);
                    if (mTargetSwap.getIdDrawable()!=R.drawable.kulak_levo||
                            mTargetSwap.getIdDrawable()!=R.drawable.kulak_pravo)
                    {
                        mDialogWindow.openDialog(
                                String.format("Продать карту за %d золотых?", mTargetSwap.getCost()),
                                mCardTradeSellClickYes
                        );
                    }
                }
                return true;
            }
        };
    }

    byte mCostVendorSkill = 1;
    View.OnClickListener mOnClickVendorSkill = mOnClickVendorSkill();
    View.OnClickListener mOnClickVendorSkill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoney >= mCostVendorSkill){
                    mDialogWindow.openDialog(
                            String.format("Обновить ассортимент торговца за %d золотых?", mCostVendorSkill),
                            mOnClickVendorSkillYes
                    );
                }
                else{
                    mDialogWindow.openInfo(
                            String.format("Недостаточно %d золота.", mCostVendorSkill - mMoney)
                    );
                }
            }
        };
    }
    View.OnClickListener mOnClickVendorSkillYes = mOnClickVendorSkillYes();
    View.OnClickListener mOnClickVendorSkillYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                changeMoneyInUIThread(-mCostVendorSkill);
                for (byte i = 0; i< LOOT_AND_TRADE_MAX_COUNT; i++){
                    mTradeItem[i].change(mStats, mDBOpenHelper,random, mGearScore);
                    mTradeItem[i].open();
                    mTradeItem[i].setVisibility(View.VISIBLE);
                    mTradeCost[i].setText(String.format("%d", mTradeItem[i].getCost()));
                    mTradeCost[i].setVisibility(View.VISIBLE);
                    mTradeCostImage[i].setVisibility(View.VISIBLE);
                }
            }
        };
    }

    View.OnClickListener mOnClickBlacksmithSkill = mOnClickBlacksmithSkill();
    View.OnClickListener mOnClickBlacksmithSkill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoney >= mCostVendorSkill){
                    mDialogWindow.openDialog(
                            String.format("Починитm предмет за %d золотых?", mCostVendorSkill),
                            mOnClickBlacksmithSkillYes
                    );
                }
                else{
                    mDialogWindow.openInfo(
                            String.format("Недостаточно %d золота.", mCostVendorSkill - mMoney)
                    );
                }
            }
        };
    }
    View.OnClickListener mOnClickBlacksmithSkillYes = mOnClickBlacksmithSkillYes();
    View.OnClickListener mOnClickBlacksmithSkillYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                changeMoneyInUIThread(-mCostVendorSkill);
                mTargetSwap.setDurability(10);
                if (mTargetSwap.mSlotType == SlotType.HAND){
                    ((CardHand) mTargetSwap).updateDurabilityText();
                }
                resetTargetSwap();
            }
        };
    }

    View.OnClickListener mOnClickInnkeeperSkill = mOnClickInnkeeperSkill();
    View.OnClickListener mOnClickInnkeeperSkill() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoney >= mCostVendorSkill){
                    mDialogWindow.openDialog(
                            String.format("Отдохнуть и восстановить здоровье за %d золотых?", mCostVendorSkill),
                            mOnClickInnkeeperSkillYes
                    );
                }
                else{
                    mDialogWindow.openInfo(
                            String.format("Недостаточно %d золота.", mCostVendorSkill - mMoney)
                    );
                }
            }
        };
    }
    View.OnClickListener mOnClickInnkeeperSkillYes = mOnClickInnkeeperSkillYes();
    View.OnClickListener mOnClickInnkeeperSkillYes() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWindow.close();
                changeMoneyInUIThread(-mCostVendorSkill);
                changeHPInUIThread(mHpMax);
                mTradeSkillImage.setOnClickListener(null);
            }
        };
    }

    public void onClickTradeExit(View view){
        mCardTableTarget.getChangeAnimation().start();
        mTradeSkill.setVisibility(View.GONE);
        mTradeZone.setVisibility(View.GONE);
        mTable.setOnDragListener(mTableOnDropListener);
    }
    //endregion

    public void onClickIconMenu(View view){
        if (findViewById(R.id.balanceMenu).getVisibility()==View.GONE){
            findViewById(R.id.balanceMenu).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.balanceMenu).setVisibility(View.GONE);
        }
    }
    public void onClickBalanceButton(View view){

        EditText baseText = findViewById(R.id.TEST1);
        EditText idText = findViewById(R.id.id);
        EditText paramText = findViewById(R.id.parmetr);
        EditText valueText = findViewById(R.id.value);

       try {
            String base = baseText.getText().toString();
            String id = idText.getText().toString();
            String param = paramText.getText().toString();
            String value = valueText.getText().toString();
            Byte.parseByte(value);
            final String a = "0";
            final String b = "1";
            final String c = "2";
            String where = null;
            String[] arg = null;
            ContentValues values = new ContentValues();

            switch (base){
                case a:{
                    base = DBOpenHelper.sTableTest;
                    break;
                }
                case b:{
                    Byte.parseByte(id);
                    base = DBOpenHelper.table_mobs;
                    where = DBOpenHelper.id +"=?";
                    arg = new String[]{id+""};
                    break;
                }
                case c:{
                    Byte.parseByte(id);
                    base = DBOpenHelper.table_inventory;
                    where = DBOpenHelper.id +"=?";
                    arg = new String[]{id+""};
                    break;
                }
                default:{
                    break;
                }
            }
            values.put(param, value);

            SQLiteDatabase data_base = mDBOpenHelper.getWritableDatabase();

            data_base.update(
                    base,
                    values,
                    where,
                    arg
            );
            data_base.close();
           baseText.setText("");
           idText.setText("");
           paramText.setText("");
           valueText.setText("");
       }
        catch (Exception e){

        }
    }

    public void onClickIconCollection(View view){
        if (findViewById(R.id.testMenu).getVisibility()==View.GONE){
            findViewById(R.id.testMenu).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.testMenu).setVisibility(View.GONE);
        }
    }
    public void onClickTestButton(View view){
        EditText idslotText = findViewById(R.id.idslot);
        EditText iditemText = findViewById(R.id.iditem);
        try {
            byte idslot = Byte.parseByte(idslotText.getText().toString());
            byte iditem = Byte.parseByte(iditemText.getText().toString());

            if (idslot<4){
                mInventory[idslot].load(mStats, mDBOpenHelper);
                mInventory[idslot].open();
                mInventory[idslot].setVisibility(View.VISIBLE);
                mInventory[idslot].setDurability(mInventory[idslot].getDurabilityMax());
            }
            else{
                if (idslot==4){
                    mHandOne.load(mStats, mDBOpenHelper);
                    mHandOne.open();
                    mHandOne.setVisibility(View.VISIBLE);
                    mHandOne.mDurabilityText.setVisibility(View.VISIBLE);
                    mHandOne.mDurabilityImage.setVisibility(View.VISIBLE);
                    mHandOne.setDurabilityInUIThread(mHandOne.getDurabilityMax());
                }
                else{
                    mHandTwo.load(mStats, mDBOpenHelper);
                    mHandTwo.open();
                    mHandTwo.setVisibility(View.VISIBLE);
                    mHandTwo.mDurabilityText.setVisibility(View.VISIBLE);
                    mHandTwo.mDurabilityImage.setVisibility(View.VISIBLE);
                    mHandTwo.setDurabilityInUIThread(mHandTwo.getDurabilityMax());
                }
            }
            idslotText.setText("");
            iditemText.setText("");
        }
        catch (Exception e){

        }
    }

    void post(String text, String data, Callback callback) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                data
        );
        Log.d("post "+text, data);
        Request request = new Request.Builder()
                .url("https://88.80.50.18:4430/"+text)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    private void resetGame() {
        for (byte i = 0; i < 8; i++) {
            mCardsTable[i].setIdInArray(i);
            mCardsTable[i].close(mIdDrawableCardBack);
            mCardsTable[i].setVisibility(View.INVISIBLE);
        }
        mCardCenter.setVisibility(View.INVISIBLE);

        mLoot[0].setVisibility(View.GONE);;
        mLoot[1].setVisibility(View.GONE);
        mLoot[2].setVisibility(View.GONE);

        mInventoryItemCount = 0;

        mHandOne.close(mIdDrawableCardBack);
        mHandTwo.close(mIdDrawableCardBack);
        mHandOne.setOnClickListener(null);
        mHandTwo.setOnClickListener(null);
        mHandOne.setIDItem(mHandOne.getIDDefault());
        mHandOne.load(mStats, mDBOpenHelper);
        mHandOne.open();
        mHandTwo.setIDItem(mHandTwo.getIDDefault());
        mHandTwo.load(mStats, mDBOpenHelper);
        mHandTwo.open();

        for (byte i = 0; i < INVENTORY_MAX_COUNT; i++) {
            mInventory[i].setVisibility(View.GONE);
            mInventory[i].setSlotId(i);
            mInventory[i].close(mIdDrawableCardBack);
        }

        mIsAnimate = true;
        Log.d("mIsAnimate", String.valueOf(mIsAnimate));

        mCardsTable[1].setOnClickListener(setTargetListener);
        mCardsTable[3].setOnClickListener(setTargetListener);
        mCardsTable[4].setOnClickListener(setTargetListener);
        mCardsTable[6].setOnClickListener(setTargetListener);

        mShadow.setVisibility(View.VISIBLE);
        mShadow.setAlpha(0f);
    }

    private void setSelectTarget() {
        mState = State.SELECT_TARGET;
        for (byte i = 0; i < LOOT_AND_TRADE_MAX_COUNT; i++) {
            mLoot[i].close(mIdDrawableCardBack);
            mLoot[i].setVisibility(View.GONE);
        }
        mLootCount = 0;
        if (mTargetSwap != null) {
            resetTargetSwap();
        }
        mCardTableTarget.getChangeAnimation().start();
        mCardTableTarget.setOnClickListener(setTargetListener);
        mButtonContinue.setVisibility(View.GONE);
    }

    private void deleteOnClickListenerBeforeAnomationTargetReset() {
        for (byte i = 0; i < INVENTORY_MAX_COUNT; i++) {
            mInventory[i].setOnClickListener(null);
        }
        for (int i = 0; i < LOOT_AND_TRADE_MAX_COUNT; i++) {
            mLoot[i].setOnClickListener(null);
        }
        mHandOne.setOnClickListener(null);
        mHandTwo.setOnClickListener(null);
    }

    private void resetTargetSwap() {
        if (mTargetSwap !=null){
            target_off_animation.start();
            Log.d("target off", (String) mTargetSwap.mNameText.getText());
        }
        if (mCardTableTarget.getSubType()==CardTableSubType.BLACKSMITH){
            mTradeSkillImage.setOnClickListener(null);
        }
        mTargetSwap = null;
        is_first_click = true;
    }

    private void changeGearScore(int delta) {
        mGearScore += delta;
        mGearScoreText.setText(String.format("%d", mGearScore));
    }

    private void useFood() {
        request.setData(mTargetSwap.getIDItem().toString());
        String requestString = null;
        try {
            requestString = mJackson.writeValueAsString(request);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        post("use/food", requestString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("use/food onFailure", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("use/food responseStr", responseStr);
                MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                if (!myResponse.isError()){
                    mTable.post(() -> {
                        changeHP(mTargetSwap.getValueOne());
                        mInventoryItemCount--;
                        inventorySort();
                        if (mState == State.SELECT_LOOT){
                            try {
                                if (myResponse.getData()!=null){
                                    mNextCardTable = mJackson.readValue(myResponse.getData(), byte[].class);
                                }
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }
                            tryPickingLoot();
                        }
                        resetTargetSwap();
                        Log.d("resetTargetSwap", "use food");
                        updateHPText();
                    });
                }
            }
        });
    }

    private void useSpell() {
        request.setData(mTargetSwap.getIDItem().toString());
        String requestString = null;
        try {
            requestString = mJackson.writeValueAsString(request);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        post("use/spell", requestString, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("use/spell onFailure", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("use/spell responseStr", responseStr);
                MyResponse myResponse = mJackson.readValue(responseStr, MyResponse.class);
                if (!myResponse.isError()){
                    mTable.post(() -> {
                        mCardTableTarget.changeValueTwo(-mTargetSwap.getValueOne());
                        mInventoryItemCount--;
                        inventorySort();
                        tryPickingLoot();
                        resetTargetSwap();
                        Log.d("resetTargetSwap", "use spell");
                        if (mCardTableTarget.getValueTwo() < 1) {
                            DamageResponse damageResponse = null;
                            try {
                                damageResponse = mJackson.readValue(
                                        myResponse.getData(),
                                        DamageResponse.class
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mNextCardTable = damageResponse.getCardTableID();
                            CardPlayerResponse[] loot = new CardPlayerResponse[0];
                            try {
                                loot = mJackson.readValue(
                                        damageResponse.getLoot(),
                                        CardPlayerResponse[].class
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mLootCount = loot.length;
                            for (int i = 0; i < mLootCount; i++) {
                                mLoot[i].bringToFront();
                                mLoot[i].setIDItem(loot[i].getIdItem());
                                mLoot[i].setDurability(loot[i].getDurability());
                            }
                        }
                        if (mCardTableTarget.getValueTwo() < 1) {
                            mobDead();
                        }
                        else{
                            mCardTableTarget.updateValueTwoText();
                        }
                    });
                }
            }
        });
    }

    private void mobDead() {
        mCardTableTarget.setValueTwoText(0);
        mCardTableTarget.setOnClickListener(null);
        changeMoneyInUIThread(mCardTableTarget.getMoney());
        mStats.addExperience(mCardTableTarget.getExperience());
        mCardTableTarget.getCloseAnimation().start();
    }

    private void loadLoot() {
        mState = State.SELECT_LOOT;
        mShadow.bringToFront();

        for (int i = 0; i < mLootCount; i++) {
            mLoot[i].load(mStats,mDBOpenHelper);
            mLoot[i].setVisibility(View.VISIBLE);
            mLoot[i].open();
            mLoot[i].bringToFront();
        }

        tryPickingLoot();

        if (mLootCount > 0) {
            mButtonContinue.bringToFront();
            mButtonContinue.setVisibility(View.VISIBLE);
        }
    }

    private void tryPickingLoot() {
        byte i = 0;
        while (((mLootCount > 0) && (mInventoryItemCount < INVENTORY_MAX_COUNT))) {
            if(mLoot[i].getVisibility()==View.VISIBLE) {
                mInventory[mInventoryItemCount].copy(mLoot[i]);
                mLoot[i].setVisibility(View.GONE);
                mInventory[mInventoryItemCount].setVisibility(View.VISIBLE);
                mInventoryItemCount++;
                mLootCount--;
            }
            i++;
        }
        tryContinue();
    }

    private void tryContinue() {
        if (mLootCount < 1 && mState == State.SELECT_LOOT) {
            if (mNextCardTable==null){
                onClickButtonContinue(null);
            }
            else{
                setSelectTarget();
            }
        }
    }

    void changeMoneyInUIThread(int delta){
        mMoney += delta;
        updateMoneyText();
    }
    void setMoneyInUIThread(int money){
        mMoney = money;
        updateMoneyText();
    }
    void updateMoneyText(){
        mMoneyText.setText(String.format("%d", mMoney));
    }
    void changeMoneyBankInUIThread(int delta){
        mMoneyBank += delta;
        updateMoneyText();
    }
    void setMoneyBankInUIThread(int money){
        mMoneyBank = money;
        updateMoneyText();
    }
    void updateMoneyBankText(){
        mMoneyText.setText(String.format("%d", mMoneyBank));
    }

    void changeHPInUIThread(int delta) {
        if (mHp + delta < mHpMax) {
            mHp = mHp + delta;
        }
        else {
            mHp = mHpMax;
        }
        updateHPText();
    }
    void changeHP(int delta) {
        if (mHp + delta < mHpMax) {
            mHp = mHp + delta;
        }
        else {
            mHp = mHpMax;
        }
    }
    void setHPInUIThread(int hp) {
        mHp = hp;
        updateHPText();
    }
    void updateHPText(){
        mHpText.setText(String.valueOf(mHp));
        mHpBarDrawable.setLevel(10000*mHp/mHpMax);
    }

    void inventorySort() {
        mTargetSwap.setVisibility(View.VISIBLE);
        byte id = mTargetSwap.getSlotId();
        for (; id < mInventoryItemCount; id++) {
            mInventory[id].copy(mInventory[id + 1]);
        }
        mInventory[id].setVisibility(View.GONE);
    }
}