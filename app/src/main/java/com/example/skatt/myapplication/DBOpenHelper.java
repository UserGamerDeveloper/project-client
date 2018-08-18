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

    static final String IP1 = "IP1";
    static final String IP2 = "IP2";
    static final String IP3 = "IP3";
    static final String IP4 = "IP4";

    DBOpenHelper(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

/*
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
                HP_BONUS_PER_STAT + " INTEGER, " + HP_DEFAULT + " INTEGER, " + IP1 + " INTEGER, " +
                IP2 + " INTEGER, " + IP3 + " INTEGER, " + IP4 + " INTEGER, " + COST_RESET_STATS + " INTEGER)";
*/
        String query_table_mobs_create = "create table " + TABLE_MOBS + " ("+ id +" INTEGER, "
                + name + " TEXT, " + ID_IMAGE + " INTEGER)";
        String query_table_inventory_create = "create table " + TABLE_INVENTORY + " ("+ id +" INTEGER, "
                + name + " TEXT, " + ID_IMAGE + " INTEGER)";
        String tableTestCreateQuery = "create table " + TABLE_TEST + " ("+ IP1 + " INTEGER, " +
                IP2 + " INTEGER, " + IP3 + " INTEGER, " + IP4 + " INTEGER)";

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
        values.put(DBOpenHelper.IP1, 91);
        values.put(DBOpenHelper.IP2, 185);
        values.put(DBOpenHelper.IP3, 66);
        values.put(DBOpenHelper.IP4, 79);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_TEST, null, values);
    }

    private void setSpells(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 29);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 30);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 31);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 32);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 33);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 34);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 35);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 36);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 37);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 38);
        values.put(DBOpenHelper.name, "Спелл");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }

    private void Set_Food(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 19);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 20);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 21);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 22);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 23);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 24);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 25);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 26);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 27);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 28);
        values.put(DBOpenHelper.name, "Еда");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.yablachko);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }

    private void Set_Shield(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 10);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 11);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 12);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 13);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 14);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 15);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 16);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 17);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 18);
        values.put(DBOpenHelper.name, "Щит");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.rubinor);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }

    private void Set_Weapons(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 1);
        values.put(DBOpenHelper.name, "Кулак");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kulak_levo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 2);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 3);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 4);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 5);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 6);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 7);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 8);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);

        values.put(DBOpenHelper.id, 9);
        values.put(DBOpenHelper.name, "Оружие");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.jalo);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_INVENTORY, null, values);
    }

    private void Set_Mobs(SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.id, 4);
        values.put(DBOpenHelper.name, "Торговец");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.torgovec);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 1);
        values.put(DBOpenHelper.name, "Кузнец");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.kuznec);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 2);
        values.put(DBOpenHelper.name, "Трактирщик");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.traktirshik);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 3);
        values.put(DBOpenHelper.name, "Сундук");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

/*
        values.put(DBOpenHelper.id, 39);
        values.put(DBOpenHelper.name, "Портал");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.prival);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);
*/

        //region Mobs
        values.put(DBOpenHelper.id, 5);
        values.put(DBOpenHelper.name, "Моб 5");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 6);
        values.put(DBOpenHelper.name, "Моб 6");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 7);
        values.put(DBOpenHelper.name, "Моб 7");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 8);
        values.put(DBOpenHelper.name, "Моб 8");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 9);
        values.put(DBOpenHelper.name, "Моб 9");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 10);
        values.put(DBOpenHelper.name, "Моб 10");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 11);
        values.put(DBOpenHelper.name, "Моб 11");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 12);
        values.put(DBOpenHelper.name, "Моб 12");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 13);
        values.put(DBOpenHelper.name, "Моб 13");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 14);
        values.put(DBOpenHelper.name, "Моб 14");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 15);
        values.put(DBOpenHelper.name, "Моб 15");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 16);
        values.put(DBOpenHelper.name, "Моб 16");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 17);
        values.put(DBOpenHelper.name, "Моб 17");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 18);
        values.put(DBOpenHelper.name, "Моб 18");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 19);
        values.put(DBOpenHelper.name, "Моб 19");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 20);
        values.put(DBOpenHelper.name, "Моб 20");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 21);
        values.put(DBOpenHelper.name, "Моб 21");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 22);
        values.put(DBOpenHelper.name, "Моб 22");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 23);
        values.put(DBOpenHelper.name, "Моб 23");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 24);
        values.put(DBOpenHelper.name, "Моб 24");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 25);
        values.put(DBOpenHelper.name, "Моб 25");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 26);
        values.put(DBOpenHelper.name, "Моб 26");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 27);
        values.put(DBOpenHelper.name, "Моб 27");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 28);
        values.put(DBOpenHelper.name, "Моб 28");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 29);
        values.put(DBOpenHelper.name, "Моб 29");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 30);
        values.put(DBOpenHelper.name, "Моб 30");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 31);
        values.put(DBOpenHelper.name, "Моб 31");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 32);
        values.put(DBOpenHelper.name, "Моб 32");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 33);
        values.put(DBOpenHelper.name, "Моб 33");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 34);
        values.put(DBOpenHelper.name, "Моб 34");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 35);
        values.put(DBOpenHelper.name, "Моб 35");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 36);
        values.put(DBOpenHelper.name, "Моб 36");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 37);
        values.put(DBOpenHelper.name, "Моб 37");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 38);
        values.put(DBOpenHelper.name, "Моб 38");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 39);
        values.put(DBOpenHelper.name, "Моб 39");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 40);
        values.put(DBOpenHelper.name, "Моб 40");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 41);
        values.put(DBOpenHelper.name, "Моб 41");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 42);
        values.put(DBOpenHelper.name, "Моб 42");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 43);
        values.put(DBOpenHelper.name, "Моб 43");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 44);
        values.put(DBOpenHelper.name, "Моб 44");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        values.put(DBOpenHelper.id, 45);
        values.put(DBOpenHelper.name, "Моб 45");
        values.put(DBOpenHelper.ID_IMAGE, R.drawable.testblack);
        sqLiteDatabase.insert(DBOpenHelper.TABLE_MOBS, null, values);

        //endregion
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}