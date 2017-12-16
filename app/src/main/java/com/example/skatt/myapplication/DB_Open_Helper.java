package com.example.skatt.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DB_Open_Helper extends SQLiteOpenHelper {

    private static final int DB_version = 1;
    private static final String DB_name = "data_base";

    public static final String table_name = "mobs";
    public static final String id = "id";
    public static final String name = "name";
    public static final String damage = "damage";
    public static final String hp = "hp";
    public static final String id_image = "id_image";
    public static final String query_create = "create table " + table_name + " ("+ id +" INTEGER, "
            + name + " TEXT, " + hp + " INTEGER, " + damage + " INTEGER, " + id_image + " INTEGER)";

    public DB_Open_Helper(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(query_create);

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 0);
        values.put(DB_Open_Helper.name, "Алхимик");
        values.put(DB_Open_Helper.hp, 1);
        values.put(DB_Open_Helper.damage, 1);
        values.put(DB_Open_Helper.id_image, R.drawable.alhimik);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_name,
                null,
                values);

        values.put(DB_Open_Helper.id, 1);
        values.put(DB_Open_Helper.name, "Лазутчик");
        values.put(DB_Open_Helper.hp, 2);
        values.put(DB_Open_Helper.damage, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.lazutchik);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_name,
                null,
                values);

        values.put(DB_Open_Helper.id, 2);
        values.put(DB_Open_Helper.name, "Троль");
        values.put(DB_Open_Helper.hp, 3);
        values.put(DB_Open_Helper.damage, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.troll);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_name,
                null,
                values);

        values.put(DB_Open_Helper.id, 3);
        values.put(DB_Open_Helper.name, "Головорез");
        values.put(DB_Open_Helper.hp, 4);
        values.put(DB_Open_Helper.damage, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.golovorez);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_name,
                null,
                values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
