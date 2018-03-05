package com.example.skatt.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Open_Helper extends SQLiteOpenHelper {

    private static final int DB_version = 1;
    private static final String DB_name = "data_base";

    static final String table_mobs = "mobs";
    static final String table_inventory = "inventory";
    static final String sTableTest = "test";
    static final String id = "ID";
    static final String name = "NAME";
    static final String VALUETWO = "VALUETWO";
    static final String VALUEONE = "VALUEONE";
    static final String id_image = "IDIMAGE";
    static final String type = "TYPE";
    static final String money = "MONEY";
    static final String cost = "COST";
    static final String GEARSCORE = "GS";
    static final String MOBGEARSCORE = "MOBGS";
    static final String SUBTYPE = "SUBTYPE";
    static final String EXPERIENCE = "EXP";
    static final String CVENDOR = "CVENDOR";
    static final String CHALT = "CHALT";
    static final String CWORS = "CWORS";
    static final String CFood = "CFOOD";
    static final String CSpell = "CSPELL";
    static final String CChest = "CCHEST";
    static final String GSRANGERATE = "GSRANGERATE";
    static final String sHaltHealth = "HaltHealth";
    static final String LVL1 = "LVL1";
    static final String LVL2 = "LVL2";
    static final String LVL3 = "LVL3";
    static final String LVL4 = "LVL4";
    static final String LVL5 = "LVL5";
    static final String LVL6 = "LVL6";
    static final String LVL7 = "LVL7";
    static final String LVL8 = "LVL8";
    static final String LVL9 = "LVL9";
    static final String LVL10 = "LVL10";
    static final String LVL11 = "LVL11";
    static final String LVL12 = "LVL12";
    static final String LVL13 = "LVL13";
    static final String LVL14 = "LVL14";
    static final String LVL15 = "LVL15";
    static final String LVL16 = "LVL16";
    static final String LVL17 = "LVL17";
    static final String LVL18 = "LVL18";
    static final String LVL19 = "LVL19";
    static final String LVL20 = "LVL20";
    static final String LVL = "LVL";
    static final String GSPERSTAT = "GSPERSTAT";
    static final String HPBONUS = "HPBONUS";
    static final String DURABILITY = "DURABILITY";
    static final String HP = "HP";

    DB_Open_Helper(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query_table_mobs_create = "create table " + table_mobs + " ("+ id +" INTEGER, "
                + name + " TEXT, " + VALUEONE + " INTEGER, " + VALUETWO + " INTEGER, "+ money +
                " INTEGER, " + type + " INTEGER, " + GEARSCORE + " INTEGER, " + SUBTYPE +
                " INTEGER, " + EXPERIENCE + " INTEGER, " + id_image + " INTEGER)";
        String query_table_inventory_create = "create table " + table_inventory + " ("+ id +" INTEGER, "
                + name + " TEXT, " + VALUEONE + " INTEGER, " + id_image + " INTEGER, " + cost +
                " INTEGER, "+ GEARSCORE + " INTEGER, " + DURABILITY + " INTEGER, " + MOBGEARSCORE +
                " INTEGER, " + type + " INTEGER)";
        String tableTestCreateQuery = "create table " + sTableTest + " ("+ money +" INTEGER, "
                + CHALT + " INTEGER, " + CWORS + " INTEGER, " + CFood +
                " INTEGER, " + CSpell + " INTEGER, "+ CVENDOR + " INTEGER, " +
                CChest + " INTEGER, " + sHaltHealth + " INTEGER, " + LVL + " INTEGER, "
                + LVL1 + " INTEGER, " + LVL2 + " INTEGER, " + LVL3 + " INTEGER, "
                + LVL4 + " INTEGER, " + LVL5 + " INTEGER, " + LVL6 + " INTEGER, "
                + LVL7 + " INTEGER, " + LVL8 + " INTEGER, " + LVL9 + " INTEGER, "
                + LVL10 + " INTEGER, " + LVL11 + " INTEGER, " + LVL12 + " INTEGER, "
                + LVL13+ " INTEGER, " + LVL14 + " INTEGER, " + LVL15+ " INTEGER, "
                + LVL16+ " INTEGER, " + LVL17 + " INTEGER, " + LVL18+ " INTEGER, "
                + LVL19+ " INTEGER, " + LVL20 + " INTEGER, " + GSPERSTAT + " INTEGER, " +
                HPBONUS + " INTEGER, " + HP + " INTEGER, " + GSRANGERATE + " INTEGER)";

        sqLiteDatabase.execSQL(query_table_mobs_create);
        sqLiteDatabase.execSQL(query_table_inventory_create);
        sqLiteDatabase.execSQL(tableTestCreateQuery);

        setTestData(sqLiteDatabase);
        Set_Mobs(sqLiteDatabase);
        Set_Weapons(sqLiteDatabase);
        Set_Shield(sqLiteDatabase);
        Set_Food(sqLiteDatabase);
        setSpells(sqLiteDatabase);
    }

    private void setTestData(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.CVENDOR, 20);
        values.put(DB_Open_Helper.CHALT, 6);
        values.put(DB_Open_Helper.CWORS, 5);
        values.put(DB_Open_Helper.CFood, 3);
        values.put(DB_Open_Helper.CSpell, 10);
        values.put(DB_Open_Helper.CChest, 40);
        values.put(DB_Open_Helper.GSRANGERATE, 50);
        values.put(DB_Open_Helper.GSPERSTAT, 1);
        values.put(DB_Open_Helper.HPBONUS, 1);
        values.put(DB_Open_Helper.LVL, 0);
        values.put(DB_Open_Helper.LVL1, 10);
        values.put(DB_Open_Helper.LVL2, 20);
        values.put(DB_Open_Helper.LVL3, 300);
        values.put(DB_Open_Helper.LVL4, 1000);
        values.put(DB_Open_Helper.LVL5, 1000);
        values.put(DB_Open_Helper.LVL6, 1000);
        values.put(DB_Open_Helper.LVL7, 1000);
        values.put(DB_Open_Helper.LVL8, 1000);
        values.put(DB_Open_Helper.LVL9, 1000);
        values.put(DB_Open_Helper.LVL10, 1000);
        values.put(DB_Open_Helper.LVL11, 1000);
        values.put(DB_Open_Helper.LVL12, 1000);
        values.put(DB_Open_Helper.LVL13, 1000);
        values.put(DB_Open_Helper.LVL14, 1000);
        values.put(DB_Open_Helper.LVL15, 1000);
        values.put(DB_Open_Helper.LVL16, 1000);
        values.put(DB_Open_Helper.LVL17, 1000);
        values.put(DB_Open_Helper.LVL18, 1000);
        values.put(DB_Open_Helper.LVL19, 1000);
        values.put(DB_Open_Helper.LVL20, 1000);
        values.put(DB_Open_Helper.HP, 30);

        sqLiteDatabase.insert(
                DB_Open_Helper.sTableTest,
                null,
                values);
    }

    private void setSpells(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 0);
        values.put(DB_Open_Helper.name, "Спелл 1");
        values.put(DB_Open_Helper.VALUEONE, 1);
        values.put(DB_Open_Helper.id_image, R.drawable.testblack);
        values.put(DB_Open_Helper.type, Inventory_Type.SPELL);
        values.put(DB_Open_Helper.cost, 1);
        values.put(DB_Open_Helper.GEARSCORE, 1);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Food(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 1);
        values.put(DB_Open_Helper.name, "Яблоко");
        values.put(DB_Open_Helper.VALUEONE, 1);
        values.put(DB_Open_Helper.id_image, R.drawable.yablachko);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);
        values.put(DB_Open_Helper.cost, 1);
        values.put(DB_Open_Helper.GEARSCORE, 1);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 2);
        values.put(DB_Open_Helper.name, "Сыр");
        values.put(DB_Open_Helper.VALUEONE, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.sir);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);
        values.put(DB_Open_Helper.cost, 2);
        values.put(DB_Open_Helper.GEARSCORE, 2);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 3);
        values.put(DB_Open_Helper.name, "Похлёбка");
        values.put(DB_Open_Helper.VALUEONE, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.pohlebka);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);
        values.put(DB_Open_Helper.cost, 3);
        values.put(DB_Open_Helper.GEARSCORE, 3);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 4);
        values.put(DB_Open_Helper.name, "Мясо");
        values.put(DB_Open_Helper.VALUEONE, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.myaso);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);
        values.put(DB_Open_Helper.cost, 4);
        values.put(DB_Open_Helper.GEARSCORE, 4);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 5);
        values.put(DB_Open_Helper.name, "Эль");
        values.put(DB_Open_Helper.VALUEONE, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.el);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);
        values.put(DB_Open_Helper.cost, 5);
        values.put(DB_Open_Helper.GEARSCORE, 5);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Shield(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 6);
        values.put(DB_Open_Helper.name, "Амулет");
        values.put(DB_Open_Helper.VALUEONE, 6);
        values.put(DB_Open_Helper.id_image, R.drawable.amulet);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 6);
        values.put(DB_Open_Helper.GEARSCORE, 6);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 7);
        values.put(DB_Open_Helper.name, "Кольцо пик");
        values.put(DB_Open_Helper.VALUEONE, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.kolco_pik);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 2);
        values.put(DB_Open_Helper.GEARSCORE, 2);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 8);
        values.put(DB_Open_Helper.name, "Старый щит");
        values.put(DB_Open_Helper.VALUEONE, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.stariy_shit);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 3);
        values.put(DB_Open_Helper.GEARSCORE, 3);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 9);
        values.put(DB_Open_Helper.name, "Крепкий щит");
        values.put(DB_Open_Helper.VALUEONE, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.krepkiy_shit);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 4);
        values.put(DB_Open_Helper.GEARSCORE, 4);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 10);
        values.put(DB_Open_Helper.name, "Рубинор");
        values.put(DB_Open_Helper.VALUEONE, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.rubinor);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 5);
        values.put(DB_Open_Helper.GEARSCORE, 5);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Weapons(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 11);
        values.put(DB_Open_Helper.name, "Вилы");
        values.put(DB_Open_Helper.VALUEONE, 6);
        values.put(DB_Open_Helper.id_image, R.drawable.vili);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 6);
        values.put(DB_Open_Helper.GEARSCORE, 6);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 12);
        values.put(DB_Open_Helper.name, "Охотник");
        values.put(DB_Open_Helper.VALUEONE, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.ohotnik);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 2);
        values.put(DB_Open_Helper.GEARSCORE, 2);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 13);
        values.put(DB_Open_Helper.name, "Колун");
        values.put(DB_Open_Helper.VALUEONE, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.kolun);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 3);
        values.put(DB_Open_Helper.GEARSCORE, 3);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 14);
        values.put(DB_Open_Helper.name, "Клык");
        values.put(DB_Open_Helper.VALUEONE, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.klik);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 4);
        values.put(DB_Open_Helper.GEARSCORE, 4);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 15);
        values.put(DB_Open_Helper.name, "Жало");
        values.put(DB_Open_Helper.VALUEONE, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.jalo);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 5);
        values.put(DB_Open_Helper.GEARSCORE, 5);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);
        values.put(DB_Open_Helper.DURABILITY, 10);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Mobs(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();
        //region Mobs
        values.put(DB_Open_Helper.id, 4);
        values.put(DB_Open_Helper.name, "Алхимик");
        values.put(DB_Open_Helper.VALUEONE, 1);
        values.put(DB_Open_Helper.VALUETWO, 1);
        values.put(DB_Open_Helper.id_image, R.drawable.alhimik);
        values.put(DB_Open_Helper.money, 1);
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 1);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 5);
        values.put(DB_Open_Helper.name, "Лазутчик");
        values.put(DB_Open_Helper.VALUEONE, 2);
        values.put(DB_Open_Helper.VALUETWO, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.lazutchik);
        values.put(DB_Open_Helper.money, 2);
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 2);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 6);
        values.put(DB_Open_Helper.name, "Горный тролль");
        values.put(DB_Open_Helper.VALUEONE, 3);
        values.put(DB_Open_Helper.VALUETWO, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.troll);
        values.put(DB_Open_Helper.money, 3);
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.TROLL);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 3);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 3);
        values.put(DB_Open_Helper.name, "Головорез");
        values.put(DB_Open_Helper.VALUEONE, 4);
        values.put(DB_Open_Helper.VALUETWO, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.golovorez);
        values.put(DB_Open_Helper.money, 4);
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.BANDIT);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 4);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);
        //endregion
        //region Vendors
        values.put(DB_Open_Helper.id, 0);
        values.put(DB_Open_Helper.name, "Торговец");
        values.put(DB_Open_Helper.id_image, R.drawable.torgovec);
        values.put(DB_Open_Helper.type, CardTableType.VENDOR);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.TRADER);
        values.put(DB_Open_Helper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 1);
        values.put(DB_Open_Helper.name, "Кузнец");
        values.put(DB_Open_Helper.id_image, R.drawable.kuznec);
        values.put(DB_Open_Helper.type, CardTableType.VENDOR);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.BLACKSMITH);
        values.put(DB_Open_Helper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 2);
        values.put(DB_Open_Helper.name, "Трактирщик");
        values.put(DB_Open_Helper.id_image, R.drawable.traktirshik);
        values.put(DB_Open_Helper.type, CardTableType.VENDOR);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.INNKEEPER);
        values.put(DB_Open_Helper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);
        //endregion
        values.put(DB_Open_Helper.id, 7);
        values.put(DB_Open_Helper.name, "Привал");
        values.put(DB_Open_Helper.id_image, R.drawable.prival);
        values.put(DB_Open_Helper.type, CardTableType.HALT);
        values.put(DB_Open_Helper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 8);
        values.put(DB_Open_Helper.name, "Сундук");
        values.put(DB_Open_Helper.id_image, R.drawable.testblack);
        values.put(DB_Open_Helper.type, CardTableType.CHEST);
        values.put(DB_Open_Helper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);
    }
    //8
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}