package com.example.food_application.firebase.databases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.Nullable;

import com.example.food_application.firebase.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

//public class Database extends SQLiteOpenHelper {
//    private static final String DB_Name="EatItDB.db";
//        private static final int DB_Ver=1;
//    public Database(@Nullable Context context) {
//        super(context, DB_Name, null,DB_Ver);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//
//        String qry="create table OrderDetail( ID integer not null primary key autoincrement unique,ProductId text,ProductName text,Quantity text,Price text,Discount text)";
//        sqLiteDatabase.execSQL(qry);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        String qry="DROP TABLE IF EXISTS OrderDetail";
//        sqLiteDatabase.execSQL(qry);
//    }
//
//    @SuppressLint("Range")
//    public List<Order> getCarts()
//    {
//        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
//        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
//        String[] sqlSelect={"ProductId","ProductName","Quantity","Price","Discount"};
//        String sqlTable="OrderDetail";
//        queryBuilder.setTables(sqlTable);
//        Cursor cursor=queryBuilder.query(sqLiteDatabase,sqlSelect,null,null,null,null,null);
//        final List<Order> result=new ArrayList<>();
//        if (cursor.moveToFirst())
//        {
//            do {
//                result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductId")),
//                        cursor.getString(cursor.getColumnIndex("ProductName")),
//                        cursor.getString(cursor.getColumnIndex("Quantity")),
//                        cursor.getString(cursor.getColumnIndex("Price")),
//                        cursor.getString(cursor.getColumnIndex("Discount"))
//                        ));
//
//            } while (cursor.moveToNext());
//
//        }
//return  result;
//    }
//
//    public void addToCart(Order order)
//    {
//        SQLiteDatabase database=getReadableDatabase();
//
//
//        String Querys=String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
//                order.getProductId(),
//                order.getProductName(),
//                order.getQuantity(),
//                order.getPrice(),
//                order.getDiscount());
//
//        database.execSQL(Querys);
//    }
//}
public class Database extends SQLiteAssetHelper {

    private static final String DB_Name="EatItDB.db";
    private static final int DB_Ver=1;

    public Database(Context context) {
        super(context, DB_Name, null,DB_Ver);

    }
    @SuppressLint("Range")
    public List<Order> getCarts()
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductName","ProductId","Quantity","Price","Discount"};
        String sqlTable="OrderDetail";
        queryBuilder.setTables(sqlTable);

        Cursor cursor=queryBuilder.query(sqLiteDatabase,sqlSelect,null,null,null,null,null);
        final List<Order> result=new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))
                        ));

            } while (cursor.moveToNext());

        }
return  result;
    }


    public void addToCart(Order order)
    {
        SQLiteDatabase database=getReadableDatabase();
        String Querys=String.format("INSERT INTO OrderDetail(ProductName,ProductId,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        database.execSQL(Querys);
    }

    public void cleanCart()
    {
        SQLiteDatabase database=getReadableDatabase();
        String Querys=String.format("DELETE FROM OrderDetail");

        database.execSQL(Querys);
    }
}
