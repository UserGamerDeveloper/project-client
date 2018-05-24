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
    static final String name = "N";
    static final String VALUETWO = "VT";
    static final String VALUEONE = "VO";
    static final String type = "T";
    static final String money = "M";
    static final String COST = "C";
    static final String GEARSCORE = "G";
    static final String MOB_GEARSCORE = "MG";
    static final String SUBTYPE = "S";
    static final String EXPERIENCE = "E";
    static final String DURABILITY_MAX = "D";

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

        values.put(DBOpenHelper.id, 69);
        values.put(DBOpenHelper.name, "Спелл 69");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 70);
        values.put(DBOpenHelper.name, "Спелл 70");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 71);
        values.put(DBOpenHelper.name, "Спелл 71");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 72);
        values.put(DBOpenHelper.name, "Спелл 72");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 73);
        values.put(DBOpenHelper.name, "Спелл 73");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 74);
        values.put(DBOpenHelper.name, "Спелл 74");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 75);
        values.put(DBOpenHelper.name, "Спелл 75");
        values.put(DBOpenHelper.VALUEONE, 7);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 76);
        values.put(DBOpenHelper.name, "Спелл 76");
        values.put(DBOpenHelper.VALUEONE, 8);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 77);
        values.put(DBOpenHelper.name, "Спелл 77");
        values.put(DBOpenHelper.VALUEONE, 9);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 78);
        values.put(DBOpenHelper.name, "Спелл 78");
        values.put(DBOpenHelper.VALUEONE, 10);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, InventoryType.SPELL);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }

    private void Set_Food(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 59);
        values.put(DBOpenHelper.name, "Еда 59");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 60);
        values.put(DBOpenHelper.name, "Еда 60");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 61);
        values.put(DBOpenHelper.name, "Еда 61");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 62);
        values.put(DBOpenHelper.name, "Еда 62");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 63);
        values.put(DBOpenHelper.name, "Еда 63");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 64);
        values.put(DBOpenHelper.name, "Еда 64");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 65);
        values.put(DBOpenHelper.name, "Еда 65");
        values.put(DBOpenHelper.VALUEONE, 7);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 66);
        values.put(DBOpenHelper.name, "Еда 66");
        values.put(DBOpenHelper.VALUEONE, 8);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 67);
        values.put(DBOpenHelper.name, "Еда 67");
        values.put(DBOpenHelper.VALUEONE, 9);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 68);
        values.put(DBOpenHelper.name, "Еда 68");
        values.put(DBOpenHelper.VALUEONE, 10);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        values.put(DBOpenHelper.type, InventoryType.FOOD);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }

    private void Set_Shield(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 25);
        values.put(DBOpenHelper.name, "Щит 25");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 9);
        values.put(DBOpenHelper.GEARSCORE, 9);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 26);
        values.put(DBOpenHelper.name, "Щит 26");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 11);
        values.put(DBOpenHelper.GEARSCORE, 11);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 27);
        values.put(DBOpenHelper.name, "Щит 27");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 15);
        values.put(DBOpenHelper.GEARSCORE, 15);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 28);
        values.put(DBOpenHelper.name, "Щит 28");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 17);
        values.put(DBOpenHelper.GEARSCORE, 17);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 8);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 29);
        values.put(DBOpenHelper.name, "Щит 29");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 21);
        values.put(DBOpenHelper.GEARSCORE, 21);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 9);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 30);
        values.put(DBOpenHelper.name, "Щит 30");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 24);
        values.put(DBOpenHelper.GEARSCORE, 24);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 36);
        values.put(DBOpenHelper.name, "Щит 36");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 25);
        values.put(DBOpenHelper.GEARSCORE, 25);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 31);
        values.put(DBOpenHelper.name, "Щит 31");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 28);
        values.put(DBOpenHelper.GEARSCORE, 28);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 11);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 32);
        values.put(DBOpenHelper.name, "Щит 32");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 30);
        values.put(DBOpenHelper.GEARSCORE, 30);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 12);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 37);
        values.put(DBOpenHelper.name, "Щит 37");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 31);
        values.put(DBOpenHelper.GEARSCORE, 31);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 33);
        values.put(DBOpenHelper.name, "Щит 33");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 36);
        values.put(DBOpenHelper.GEARSCORE, 36);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 13);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 34);
        values.put(DBOpenHelper.name, "Щит 34");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 38);
        values.put(DBOpenHelper.GEARSCORE, 38);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 14);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 38);
        values.put(DBOpenHelper.name, "Щит 38");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 39);
        values.put(DBOpenHelper.GEARSCORE, 39);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 35);
        values.put(DBOpenHelper.name, "Щит 35");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 42);
        values.put(DBOpenHelper.GEARSCORE, 42);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 15);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 39);
        values.put(DBOpenHelper.name, "Щит 39");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 43);
        values.put(DBOpenHelper.GEARSCORE, 43);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 40);
        values.put(DBOpenHelper.name, "Щит 40");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 47);
        values.put(DBOpenHelper.GEARSCORE, 47);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 8);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 41);
        values.put(DBOpenHelper.name, "Щит 41");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 54);
        values.put(DBOpenHelper.GEARSCORE, 54);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 9);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 42);
        values.put(DBOpenHelper.name, "Щит 42");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 55);
        values.put(DBOpenHelper.GEARSCORE, 55);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 43);
        values.put(DBOpenHelper.name, "Щит 43");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 62);
        values.put(DBOpenHelper.GEARSCORE, 62);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 44);
        values.put(DBOpenHelper.name, "Щит 44");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 63);
        values.put(DBOpenHelper.GEARSCORE, 63);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 45);
        values.put(DBOpenHelper.name, "Щит 45");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 68);
        values.put(DBOpenHelper.GEARSCORE, 68);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 46);
        values.put(DBOpenHelper.name, "Щит 46");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 72);
        values.put(DBOpenHelper.GEARSCORE, 72);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 11);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 47);
        values.put(DBOpenHelper.name, "Щит 47");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 78);
        values.put(DBOpenHelper.GEARSCORE, 78);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 12);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 48);
        values.put(DBOpenHelper.name, "Щит 48");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 79);
        values.put(DBOpenHelper.GEARSCORE, 79);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 8);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 49);
        values.put(DBOpenHelper.name, "Щит 49");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 80);
        values.put(DBOpenHelper.GEARSCORE, 80);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 50);
        values.put(DBOpenHelper.name, "Щит 50");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 86);
        values.put(DBOpenHelper.GEARSCORE, 86);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 51);
        values.put(DBOpenHelper.name, "Щит 51");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 89);
        values.put(DBOpenHelper.GEARSCORE, 89);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 13);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 52);
        values.put(DBOpenHelper.name, "Щит 52");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 93);
        values.put(DBOpenHelper.GEARSCORE, 93);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 9);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 53);
        values.put(DBOpenHelper.name, "Щит 53");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 97);
        values.put(DBOpenHelper.GEARSCORE, 97);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 14);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 54);
        values.put(DBOpenHelper.name, "Щит 54");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 98);
        values.put(DBOpenHelper.GEARSCORE, 98);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 55);
        values.put(DBOpenHelper.name, "Щит 55");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 105);
        values.put(DBOpenHelper.GEARSCORE, 105);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 15);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 56);
        values.put(DBOpenHelper.name, "Щит 56");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 106);
        values.put(DBOpenHelper.GEARSCORE, 106);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 57);
        values.put(DBOpenHelper.name, "Щит 57");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 107);
        values.put(DBOpenHelper.GEARSCORE, 107);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 58);
        values.put(DBOpenHelper.name, "Щит 58");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        values.put(DBOpenHelper.type, InventoryType.SHIELD);
        values.put(DBOpenHelper.COST, 108);
        values.put(DBOpenHelper.GEARSCORE, 108);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }

    private void Set_Weapons(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 1);
        values.put(DBOpenHelper.name, "Кулак");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kulak_levo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 0);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.MOB_GEARSCORE, 99999);
        values.put(DBOpenHelper.DURABILITY_MAX, 0);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 2);
        values.put(DBOpenHelper.name, "Оружие 2");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 25);
        values.put(DBOpenHelper.GEARSCORE, 25);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 3);
        values.put(DBOpenHelper.name, "Оружие 3");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 31);
        values.put(DBOpenHelper.GEARSCORE, 31);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 4);
        values.put(DBOpenHelper.name, "Оружие 4");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 39);
        values.put(DBOpenHelper.GEARSCORE, 39);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 5);
        values.put(DBOpenHelper.name, "Оружие 5");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 43);
        values.put(DBOpenHelper.GEARSCORE, 43);
        values.put(DBOpenHelper.MOB_GEARSCORE, 0);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 6);
        values.put(DBOpenHelper.name, "Оружие 6");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 47);
        values.put(DBOpenHelper.GEARSCORE, 47);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 8);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 7);
        values.put(DBOpenHelper.name, "Оружие 7");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 54);
        values.put(DBOpenHelper.GEARSCORE, 54);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 9);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 8);
        values.put(DBOpenHelper.name, "Оружие 8");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 55);
        values.put(DBOpenHelper.GEARSCORE, 55);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 9);
        values.put(DBOpenHelper.name, "Оружие 9");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 62);
        values.put(DBOpenHelper.GEARSCORE, 62);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 10);
        values.put(DBOpenHelper.name, "Оружие 10");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 63);
        values.put(DBOpenHelper.GEARSCORE, 63);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 11);
        values.put(DBOpenHelper.name, "Оружие 11");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 68);
        values.put(DBOpenHelper.GEARSCORE, 68);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 12);
        values.put(DBOpenHelper.name, "Оружие 12");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 72);
        values.put(DBOpenHelper.GEARSCORE, 72);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 11);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 13);
        values.put(DBOpenHelper.name, "Оружие 13");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 78);
        values.put(DBOpenHelper.GEARSCORE, 78);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 12);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 14);
        values.put(DBOpenHelper.name, "Оружие 14");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 79);
        values.put(DBOpenHelper.GEARSCORE, 79);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 8);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 15);
        values.put(DBOpenHelper.name, "Оружие 15");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 80);
        values.put(DBOpenHelper.GEARSCORE, 80);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 16);
        values.put(DBOpenHelper.name, "Оружие 16");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 86);
        values.put(DBOpenHelper.GEARSCORE, 86);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 17);
        values.put(DBOpenHelper.name, "Оружие 17");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 89);
        values.put(DBOpenHelper.GEARSCORE, 89);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 13);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 18);
        values.put(DBOpenHelper.name, "Оружие 18");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 93);
        values.put(DBOpenHelper.GEARSCORE, 93);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 9);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 19);
        values.put(DBOpenHelper.name, "Оружие 19");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 97);
        values.put(DBOpenHelper.GEARSCORE, 97);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 14);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 20);
        values.put(DBOpenHelper.name, "Оружие 20");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 98);
        values.put(DBOpenHelper.GEARSCORE, 98);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 21);
        values.put(DBOpenHelper.name, "Оружие 21");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 105);
        values.put(DBOpenHelper.GEARSCORE, 105);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 15);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 22);
        values.put(DBOpenHelper.name, "Оружие 22");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 106);
        values.put(DBOpenHelper.GEARSCORE, 106);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 10);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 23);
        values.put(DBOpenHelper.name, "Оружие 23");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 107);
        values.put(DBOpenHelper.GEARSCORE, 107);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 24);
        values.put(DBOpenHelper.name, "Оружие 24");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        values.put(DBOpenHelper.type, InventoryType.WEAPON);
        values.put(DBOpenHelper.COST, 108);
        values.put(DBOpenHelper.GEARSCORE, 108);
        values.put(DBOpenHelper.MOB_GEARSCORE, 999);
        values.put(DBOpenHelper.DURABILITY_MAX, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

    }

    private void Set_Mobs(SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 4);
        values.put(DBOpenHelper.name, "Торговец");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.torgovec);
        values.put(DBOpenHelper.type, CardTableType.VENDOR);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.TRADER);
        values.put(DBOpenHelper.GEARSCORE, 9999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 1);
        values.put(DBOpenHelper.name, "Кузнец");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kuznec);
        values.put(DBOpenHelper.type, CardTableType.VENDOR);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.BLACKSMITH);
        values.put(DBOpenHelper.GEARSCORE, 9999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 2);
        values.put(DBOpenHelper.name, "Трактирщик");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.traktirshik);
        values.put(DBOpenHelper.type, CardTableType.VENDOR);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.INNKEEPER);
        values.put(DBOpenHelper.GEARSCORE, 9999);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 3);
        values.put(DBOpenHelper.name, "Сундук");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.type, CardTableType.CHEST);
        values.put(DBOpenHelper.GEARSCORE, 9999);
        values.put(DBOpenHelper.money, 1);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        //region Mobs
        values.put(DBOpenHelper.id, 5);
        values.put(DBOpenHelper.name, "Моб 5");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.VALUETWO, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 2);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 2);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 6);
        values.put(DBOpenHelper.name, "Моб 6");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.VALUETWO, 1);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 3);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 3);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 7);
        values.put(DBOpenHelper.name, "Моб 7");
        values.put(DBOpenHelper.VALUEONE, 1);
        values.put(DBOpenHelper.VALUETWO, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 3);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 3);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 8);
        values.put(DBOpenHelper.name, "Моб 8");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.VALUETWO, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 4);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 4);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 9);
        values.put(DBOpenHelper.name, "Моб 9");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.VALUETWO, 2);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 5);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 10);
        values.put(DBOpenHelper.name, "Моб 10");
        values.put(DBOpenHelper.VALUEONE, 2);
        values.put(DBOpenHelper.VALUETWO, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 5);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 5);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 11);
        values.put(DBOpenHelper.name, "Моб 11");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.VALUETWO, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 6);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 0);
        values.put(DBOpenHelper.EXPERIENCE, 6);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 12);
        values.put(DBOpenHelper.name, "Моб 12");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.VALUETWO, 3);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 7);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 13);
        values.put(DBOpenHelper.name, "Моб 13");
        values.put(DBOpenHelper.VALUEONE, 3);
        values.put(DBOpenHelper.VALUETWO, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 7);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 7);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 14);
        values.put(DBOpenHelper.name, "Моб 14");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.VALUETWO, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 8);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 8);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 15);
        values.put(DBOpenHelper.name, "Моб 15");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.VALUETWO, 4);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 9);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 9);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 16);
        values.put(DBOpenHelper.name, "Моб 16");
        values.put(DBOpenHelper.VALUEONE, 4);
        values.put(DBOpenHelper.VALUETWO, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 9);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 9);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 17);
        values.put(DBOpenHelper.name, "Моб 17");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.VALUETWO, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 10);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 10);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 18);
        values.put(DBOpenHelper.name, "Моб 18");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.VALUETWO, 5);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 11);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 11);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 19);
        values.put(DBOpenHelper.name, "Моб 19");
        values.put(DBOpenHelper.VALUEONE, 5);
        values.put(DBOpenHelper.VALUETWO, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 11);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 11);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 20);
        values.put(DBOpenHelper.name, "Моб 20");
        values.put(DBOpenHelper.VALUEONE, 6);
        values.put(DBOpenHelper.VALUETWO, 6);
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        values.put(DBOpenHelper.money, 12);
        values.put(DBOpenHelper.type, CardTableType.MOB);
        values.put(DBOpenHelper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DBOpenHelper.GEARSCORE, 999);
        values.put(DBOpenHelper.EXPERIENCE, 12);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        //endregion
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}