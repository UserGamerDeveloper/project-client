package com.example.skatt.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Open_Helper extends SQLiteOpenHelper {

    private static final int DB_version = 1;
    private static final String DB_name = "data_base";

    public static final String table_mobs = "mobs";
    public static final String table_inventory = "inventory";
    public static final String sTableTest = "test";
    public static final String id = "id";
    public static final String name = "name";
    public static final String value_two = "value_two";
    public static final String value_one = "value_one";
    public static final String id_image = "id_image";
    public static final String type = "type";
    public static final String money = "money";
    public static final String cost = "cost";
    public static final String GEARSCORE = "gearScore";
    public static final String MOBGEARSCORE = "mobGearScore";
    public static final String SUBTYPE = "SUBTYPE";
    public static final String EXPERIENCE = "experience";
    public static final String sChanceVendor = "ChanceVendor";
    public static final String sChanceHalt = "ChanceHalt";
    public static final String sChanceWeaponOrShield = "ChanceWeaponOrShield";
    public static final String sChanceFood = "ChanceFood";
    public static final String sChanceSpell = "ChanceSpell";
    public static final String sChanceChest = "ChanceChest";

    DB_Open_Helper(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query_table_mobs_create = "create table " + table_mobs + " ("+ id +" INTEGER, "
                + name + " TEXT, " + value_one + " INTEGER, " + value_two + " INTEGER, "+ money +
                " INTEGER, " + type + " INTEGER, " + GEARSCORE + " INTEGER, " + SUBTYPE +
                " INTEGER, " + EXPERIENCE + " INTEGER, " + id_image + " INTEGER)";
        String query_table_inventory_create = "create table " + table_inventory + " ("+ id +" INTEGER, "
                + name + " TEXT, " + value_one + " INTEGER, " + id_image + " INTEGER, " + cost +
                " INTEGER, "+ GEARSCORE + " INTEGER, " + MOBGEARSCORE + " INTEGER, " + type + " INTEGER)";
        String tableTestCreateQuery = "create table " + sTableTest + " ("+ money +" INTEGER, "
                + sChanceHalt + " INTEGER, " + sChanceWeaponOrShield + " INTEGER, " + sChanceFood +
                " INTEGER, " + sChanceSpell + " INTEGER, "+ sChanceVendor + " INTEGER, " +
                sChanceChest + " INTEGER, " + type + " INTEGER)";

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

        values.put(DB_Open_Helper.sChanceVendor, 20);
        values.put(DB_Open_Helper.sChanceHalt, 6);
        values.put(DB_Open_Helper.sChanceWeaponOrShield, 5);
        values.put(DB_Open_Helper.sChanceFood, 3);
        values.put(DB_Open_Helper.sChanceSpell, 10);
        values.put(DB_Open_Helper.sChanceChest, 40);

        sqLiteDatabase.insert(
                DB_Open_Helper.sTableTest,
                null,
                values);
    }

    private void setSpells(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 0);
        values.put(DB_Open_Helper.name, "Спелл 1");
        values.put(DB_Open_Helper.value_one, 1);
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
        values.put(DB_Open_Helper.value_one, 1);
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
        values.put(DB_Open_Helper.value_one, 2);
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
        values.put(DB_Open_Helper.value_one, 3);
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
        values.put(DB_Open_Helper.value_one, 4);
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
        values.put(DB_Open_Helper.value_one, 5);
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
        values.put(DB_Open_Helper.value_one, 6);
        values.put(DB_Open_Helper.id_image, R.drawable.amulet);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 6);
        values.put(DB_Open_Helper.GEARSCORE, 6);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 7);
        values.put(DB_Open_Helper.name, "Кольцо пик");
        values.put(DB_Open_Helper.value_one, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.kolco_pik);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 2);
        values.put(DB_Open_Helper.GEARSCORE, 2);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 8);
        values.put(DB_Open_Helper.name, "Старый щит");
        values.put(DB_Open_Helper.value_one, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.stariy_shit);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 3);
        values.put(DB_Open_Helper.GEARSCORE, 3);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 9);
        values.put(DB_Open_Helper.name, "Крепкий щит");
        values.put(DB_Open_Helper.value_one, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.krepkiy_shit);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 4);
        values.put(DB_Open_Helper.GEARSCORE, 4);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 10);
        values.put(DB_Open_Helper.name, "Рубинор");
        values.put(DB_Open_Helper.value_one, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.rubinor);
        values.put(DB_Open_Helper.type, Inventory_Type.SHIELD);
        values.put(DB_Open_Helper.cost, 5);
        values.put(DB_Open_Helper.GEARSCORE, 5);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);
    }

    private void Set_Weapons(SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();

        values.put(DB_Open_Helper.id, 11);
        values.put(DB_Open_Helper.name, "Вилы");
        values.put(DB_Open_Helper.value_one, 6);
        values.put(DB_Open_Helper.id_image, R.drawable.vili);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 6);
        values.put(DB_Open_Helper.GEARSCORE, 6);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 12);
        values.put(DB_Open_Helper.name, "Охотник");
        values.put(DB_Open_Helper.value_one, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.ohotnik);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 2);
        values.put(DB_Open_Helper.GEARSCORE, 2);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 13);
        values.put(DB_Open_Helper.name, "Колун");
        values.put(DB_Open_Helper.value_one, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.kolun);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 3);
        values.put(DB_Open_Helper.GEARSCORE, 3);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 14);
        values.put(DB_Open_Helper.name, "Клык");
        values.put(DB_Open_Helper.value_one, 4);
        values.put(DB_Open_Helper.id_image, R.drawable.klik);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 4);
        values.put(DB_Open_Helper.GEARSCORE, 4);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_inventory,
                null,
                values);

        values.put(DB_Open_Helper.id, 15);
        values.put(DB_Open_Helper.name, "Жало");
        values.put(DB_Open_Helper.value_one, 5);
        values.put(DB_Open_Helper.id_image, R.drawable.jalo);
        values.put(DB_Open_Helper.type, Inventory_Type.WEAPON);
        values.put(DB_Open_Helper.cost, 5);
        values.put(DB_Open_Helper.GEARSCORE, 5);
        values.put(DB_Open_Helper.MOBGEARSCORE, 0);

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
        values.put(DB_Open_Helper.value_one, 1);
        values.put(DB_Open_Helper.value_two, 1);
        values.put(DB_Open_Helper.id_image, R.drawable.alhimik);
        values.put(DB_Open_Helper.money, 1);
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 5);
        values.put(DB_Open_Helper.name, "Лазутчик");
        values.put(DB_Open_Helper.value_one, 2);
        values.put(DB_Open_Helper.value_two, 2);
        values.put(DB_Open_Helper.id_image, R.drawable.lazutchik);
        values.put(DB_Open_Helper.money, 2);
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.GOBLIN);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);

        values.put(DB_Open_Helper.id, 6);
        values.put(DB_Open_Helper.name, "Горный тролль");
        values.put(DB_Open_Helper.value_one, 3);
        values.put(DB_Open_Helper.value_two, 3);
        values.put(DB_Open_Helper.id_image, R.drawable.troll);
        values.put(DB_Open_Helper.money, 3);
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.TROLL);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 0);

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
        values.put(DB_Open_Helper.type, CardTableType.MOB);
        values.put(DB_Open_Helper.SUBTYPE, CardTableSubType.BANDIT);
        values.put(DB_Open_Helper.GEARSCORE, 0);
        values.put(DB_Open_Helper.EXPERIENCE, 0);

        sqLiteDatabase.insert(
                DB_Open_Helper.table_mobs,
                null,
                values);
        //endregion
        //region Vendors
        values.put(DB_Open_Helper.id, 0);
        values.put(DB_Open_Helper.name, "Горговец");
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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}