package com.example.skatt.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DB_version = 1;
    private static final String DB_name = "data_base";

    static final String TABLE_MOBS = "mobs";
    static final String TABLE_INVENTORY = "inventory";
    static final String TABLE_TEST = "test";

    static final String id = "ID";
    static final String ID_IMAGE = "ID_IMAGE";
    static final String name = "NM";
    static final String VALUETWO = "VT";
    static final String VALUEONE = "VO";
    static final String type = "T";
    static final String money = "MN";
    static final String COST = "COST";
    static final String GEARSCORE = "GS";
    static final String MOB_GEARSCORE = "MGS";
    static final String SUBTYPE = "ST";
    static final String EXPERIENCE = "EXP";
    static final String DURABILITY_MAX = "DM";

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
    static final String HP_BONUS_PER_STAT = "HPB";
    static final String HP_DEFAULT = "HPD";
    static final String COST_RESET_STATS = "CR";

    DBOpenHelper(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query_table_mobs_create = "create table " + TABLE_MOBS + " ("+ id +" INTEGER, "
                + name + " TEXT, " + VALUEONE + " INTEGER, " + VALUETWO + " INTEGER, "+ money +
                " INTEGER, " + type + " INTEGER, " + GEARSCORE + " INTEGER, " + SUBTYPE +
                " INTEGER, " + EXPERIENCE + " INTEGER, " + ID_IMAGE + " INTEGER)";
        String query_table_inventory_create = "create table " + TABLE_INVENTORY + " ("+ id +" INTEGER, "
                + name + " TEXT, " + VALUEONE + " INTEGER, " + ID_IMAGE + " INTEGER, " + COST +
                " INTEGER, "+ GEARSCORE + " INTEGER, " + DURABILITY_MAX + " INTEGER, " + MOB_GEARSCORE +
                " INTEGER, " + type + " INTEGER)";
        String tableTestCreateQuery = "create table " + TABLE_TEST + " ("+ LVL1 + " INTEGER, " +
                LVL2 + " INTEGER, " + LVL3 + " INTEGER, "
                + LVL4 + " INTEGER, " + LVL5 + " INTEGER, " + LVL6 + " INTEGER, "
                + LVL7 + " INTEGER, " + LVL8 + " INTEGER, " + LVL9 + " INTEGER, "
                + LVL10 + " INTEGER, " + LVL11 + " INTEGER, " + LVL12 + " INTEGER, "
                + LVL13+ " INTEGER, " + LVL14 + " INTEGER, " + LVL15+ " INTEGER, "
                + LVL16+ " INTEGER, " + LVL17 + " INTEGER, " + LVL18+ " INTEGER, "
                + LVL19+ " INTEGER, " + LVL20 + " INTEGER, " +
                HP_BONUS_PER_STAT + " INTEGER, " + HP_DEFAULT + " INTEGER, " + COST_RESET_STATS + " INTEGER)";

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

        values.put(DBOpenHelper.HP_BONUS_PER_STAT, 1);
        values.put(DBOpenHelper.LVL1, 10);
        values.put(DBOpenHelper.LVL2, 20);
        values.put(DBOpenHelper.LVL3, 300);
        values.put(DBOpenHelper.LVL4, 1000);
        values.put(DBOpenHelper.LVL5, 1000);
        values.put(DBOpenHelper.LVL6, 1000);
        values.put(DBOpenHelper.LVL7, 1000);
        values.put(DBOpenHelper.LVL8, 1000);
        values.put(DBOpenHelper.LVL9, 1000);
        values.put(DBOpenHelper.LVL10, 1000);
        values.put(DBOpenHelper.LVL11, 1000);
        values.put(DBOpenHelper.LVL12, 1000);
        values.put(DBOpenHelper.LVL13, 1000);
        values.put(DBOpenHelper.LVL14, 1000);
        values.put(DBOpenHelper.LVL15, 1000);
        values.put(DBOpenHelper.LVL16, 1000);
        values.put(DBOpenHelper.LVL17, 1000);
        values.put(DBOpenHelper.LVL18, 1000);
        values.put(DBOpenHelper.LVL19, 1000);
        values.put(DBOpenHelper.LVL20, 1000);
        values.put(DBOpenHelper.HP_DEFAULT, 30);
        values.put(DBOpenHelper.COST_RESET_STATS, 1);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_TEST,
                null,
                values);
    }

    private void setSpells(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 16);
        values.put(DBOpenHelper.name, "Спелл 1");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 1);
        values.put(DBOpenHelper.GEARSCORE, 1);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);
    }

    private void Set_Food(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 1);
        values.put(DBOpenHelper.name, "Яблоко");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 1);
        values.put(DBOpenHelper.GEARSCORE, 1);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 2);
        values.put(DBOpenHelper.name, "Сыр");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.sir);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 2);
        values.put(DBOpenHelper.GEARSCORE, 2);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 3);
        values.put(DBOpenHelper.name, "Похлёбка");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.pohlebka);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 3);
        values.put(DBOpenHelper.GEARSCORE, 3);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 4);
        values.put(DBOpenHelper.name, "Мясо");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.myaso);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 4);
        values.put(DBOpenHelper.GEARSCORE, 4);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 5);
        values.put(DBOpenHelper.name, "Эль");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.el);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 5);
        values.put(DBOpenHelper.GEARSCORE, 5);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);
    }

    private void Set_Shield(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 6);
        values.put(DBOpenHelper.name, "Амулет");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.amulet);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 6);
        values.put(DBOpenHelper.GEARSCORE, 6);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 7);
        values.put(DBOpenHelper.name, "Кольцо пик");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kolco_pik);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 2);
        values.put(DBOpenHelper.GEARSCORE, 2);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 8);
        values.put(DBOpenHelper.name, "Старый щит");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.stariy_shit);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 3);
        values.put(DBOpenHelper.GEARSCORE, 3);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 9);
        values.put(DBOpenHelper.name, "Крепкий щит");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.krepkiy_shit);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 4);
        values.put(DBOpenHelper.GEARSCORE, 4);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);

        values.put(DBOpenHelper.id, 10);
        values.put(DBOpenHelper.name, "Рубинор");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 5);
        values.put(DBOpenHelper.GEARSCORE, 5);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_INVENTORY,
                null,
                values);
    }

    private void Set_Weapons(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 0);
        values.put(DBOpenHelper.name, "Кулак");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kulak_levo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 99999);
        values.put(DBOpenHelper.DURABILITY_MAX, 0);

        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 11);
        values.put(DBOpenHelper.name, "Вилы");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.vili);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 6);
        values.put(DBOpenHelper.GEARSCORE, 6);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 12);
        values.put(DBOpenHelper.name, "Охотник");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.ohotnik);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 2);
        values.put(DBOpenHelper.GEARSCORE, 2);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 13);
        values.put(DBOpenHelper.name, "Колун");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kolun);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 3);
        values.put(DBOpenHelper.GEARSCORE, 3);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 14);
        values.put(DBOpenHelper.name, "Клык");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.klik);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 4);
        values.put(DBOpenHelper.GEARSCORE, 4);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 15);
        values.put(DBOpenHelper.name, "Жало");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 5);
        values.put(DBOpenHelper.GEARSCORE, 5);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);

        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }
    //16
    private void Set_Mobs(SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();
        //region Mobs
        values.put(DBOpenHelper.id, 4);
        values.put(DBOpenHelper.name, "Алхимик");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.VALUETWO, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.alhimik);
        values.put(DBOpenHelper.money, 1);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 1);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);

        values.put(DBOpenHelper.id, 5);
        values.put(DBOpenHelper.name, "Лазутчик");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.VALUETWO, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.lazutchik);
        values.put(DBOpenHelper.money, 2);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 2);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);

        values.put(DBOpenHelper.id, 6);
        values.put(DBOpenHelper.name, "Горный тролль");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.VALUETWO, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.troll);
        values.put(DBOpenHelper.money, 3);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.TROLL);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 3);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);

        values.put(DBOpenHelper.id, 3);
        values.put(DBOpenHelper.name, "Головорез");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.VALUETWO, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.golovorez);
        values.put(DBOpenHelper.money, 4);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.BANDIT);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 4);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);
        //endregion
        //region Vendors
        values.put(DBOpenHelper.id, 0);
        values.put(DBOpenHelper.name, "Торговец");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.torgovec);
        values.put(DBOpenHelper.type, CardTableType.VENDOR);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.TRADER);
        values.put(DBOpenHelper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);

        values.put(DBOpenHelper.id, 1);
        values.put(DBOpenHelper.name, "Кузнец");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kuznec);
        values.put(DBOpenHelper.type, CardTableType.VENDOR);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.BLACKSMITH);
        values.put(DBOpenHelper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);

        values.put(DBOpenHelper.id, 2);
        values.put(DBOpenHelper.name, "Трактирщик");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.traktirshik);
        values.put(DBOpenHelper.type, CardTableType.VENDOR);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.INNKEEPER);
        values.put(DBOpenHelper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);
        //endregion
        values.put(DBOpenHelper.id, 7);
        values.put(DBOpenHelper.name, "Привал");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.prival);
        values.put(DBOpenHelper.type, CardTableType.HALT);
        values.put(DBOpenHelper.GEARSCORE, 9999);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);

        values.put(DBOpenHelper.id, 8);
        values.put(DBOpenHelper.name, "Сундук");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, CardTableType.CHEST);
        values.put(DBOpenHelper.GEARSCORE, 9999);
        values.put(DBOpenHelper.money, 1);

        sqLiteDatabase.insert(
                DBOpenHelper.TABLE_MOBS,
                null,
                values);
    }
    //8
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}