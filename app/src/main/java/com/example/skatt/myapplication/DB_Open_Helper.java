package com.example.skatt.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseIntArray;

public class DB_Open_Helper extends SQLiteOpenHelper {

    private static final int DB_version = 1;
    private static final String DB_name = "data_base";

    public static final SparseIntArray count_item_in_type = new SparseIntArray();
    public static final int count_id_in_type = 1000;
    public static final String table_mobs = "mobs";
    public static final String table_inventory = "inventory";
    public static final String id = "id";
    public static final String name = "name";
    public static final String value_two = "value_two";
    public static final String value_one = "value_one";
    public static final String id_image = "id_image";
    public static final String type = "type";
    public static final String money = "money";

    DB_Open_Helper(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query_table_mobs_create = "create table " + table_mobs + " ("+ id +" INTEGER, "
                + name + " TEXT, " + value_one + " INTEGER, " + value_two + " INTEGER, "+ money + " INTEGER, " + id_image + " INTEGER)";
        String query_table_inventory_create = "create table " + table_inventory + " ("+ id +" INTEGER, "
                + name + " TEXT, " + value_one + " INTEGER, " + id_image + " INTEGER, "+ type + " INTEGER)";

        sqLiteDatabase.execSQL(query_table_mobs_create);
        sqLiteDatabase.execSQL(query_table_inventory_create);

        Set_Mobs(sqLiteDatabase);

        Set_Weapons(sqLiteDatabase);

        Set_Shield(sqLiteDatabase);

        Set_Food(sqLiteDatabase);
    }

    private void Set_Food(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.FOOD);
        values.put(DB_Open_Helper.name, "Яблоко");
        values.put(DB_Open_Helper.value_one, 1);
        values.put(DB_Open_Helper.id_image, R.drawable.yablachko);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.FOOD+1);
        values.put(DB_Open_Helper.name, "Сыр");
        values.put(DB_Open_Helper.value_one, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.sir);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.FOOD+2);
        values.put(DB_Open_Helper.name, "Похлёбка");
        values.put(DB_Open_Helper.value_one, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.pohlebka);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.FOOD+3);
        values.put(DB_Open_Helper.name, "Мясо");
        values.put(DB_Open_Helper.value_one, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.myaso);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.FOOD+4);
        values.put(DB_Open_Helper.name, "Эль");
        values.put(DB_Open_Helper.value_one, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.el);
        values.put(DB_Open_Helper.type, Inventory_Type.FOOD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Shield(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.name, "Амулет");
        values.put(DB_Open_Helper.value_one, 6);
        values.put(DB_Open_Helper.id_image, R.drawable.amulet);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.SHIELD+1);
        values.put(DB_Open_Helper.name, "Кольцо пик");
        values.put(DB_Open_Helper.value_one, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.kolco_pik);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.SHIELD+2);
        values.put(DB_Open_Helper.name, "Старый щит");
        values.put(DB_Open_Helper.value_one, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.stariy_shit);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.SHIELD+3);
        values.put(DB_Open_Helper.name, "Крепкий щит");
        values.put(DB_Open_Helper.value_one, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.krepkiy_shit);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.SHIELD+4);
        values.put(DB_Open_Helper.name, "Рубинор");
        values.put(DB_Open_Helper.value_one, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.rubinor);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Weapons(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.name, "Вилы");
        values.put(DB_Open_Helper.value_one, 6);
        values.put(DB_Open_Helper.id_image, R.drawable.vili);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.WEAPON+1);
        values.put(DB_Open_Helper.name, "Охотник");
        values.put(DB_Open_Helper.value_one, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.ohotnik);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.WEAPON+2);
        values.put(DB_Open_Helper.name, "Колун");
        values.put(DB_Open_Helper.value_one, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.kolun);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.WEAPON+3);
        values.put(DB_Open_Helper.name, "Клык");
        values.put(DB_Open_Helper.value_one, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.klik);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, count_id_in_type *Inventory_Type.WEAPON+4);
        values.put(DB_Open_Helper.name, "Жало");
        values.put(DB_Open_Helper.value_one, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.jalo);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Mobs(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 0);
        values.put(DB_Open_Helper.name, "Алхимик");
        values.put(DB_Open_Helper.value_one, 1);
        values.put(DB_Open_Helper.value_two, 1);
        values.put(DB_Open_Helper.id_image, R.drawable.alhimik);
        values.put(DB_Open_Helper.money, 1);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 1);
        values.put(DB_Open_Helper.name, "Лазутчик");
        values.put(DB_Open_Helper.value_one, 2);
        values.put(DB_Open_Helper.value_two, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.lazutchik);
        values.put(DB_Open_Helper.money, 2);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 2);
        values.put(DB_Open_Helper.name, "Троль");
        values.put(DB_Open_Helper.value_one, 3);
        values.put(DB_Open_Helper.value_two, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.troll);
        values.put(DB_Open_Helper.money, 3);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 3);
        values.put(DB_Open_Helper.name, "Головорез");
        values.put(DB_Open_Helper.value_one, 4);
        values.put(DB_Open_Helper.value_two, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.golovorez);
        values.put(DB_Open_Helper.money, 4);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}