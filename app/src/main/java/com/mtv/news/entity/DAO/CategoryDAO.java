package com.mtv.news.entity.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mtv.news.entity.Category;

import java.util.ArrayList;

public class CategoryDAO extends SQLiteOpenHelper {
    public  static  final String TableName = "Category2";
    public  static  final String categoryId = "category_id";
    public  static  final String title = "title";
    public  static  final String selected = "selected";
    public CategoryDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String sqlCreate = "Create table if not exists " + TableName + "("
                + categoryId + " Integer Primary Key, "
                + title + " Text, "
                + selected + " BOOLEAN "
                + ")";
        // chạy sql tạo bảng
        sqLiteDatabase.execSQL((sqlCreate));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // xóa bảng SinhVienTable đã có
        sqLiteDatabase.execSQL("Drop table if exists " + TableName);
        // tạo lại
        onCreate((sqLiteDatabase));
    }

    // lấy tất cả các dòng của TableContact trả về ArrayList
    public ArrayList<Category> getAllCategory(){
        ArrayList<Category> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Category category = new Category(cursor.getInt(0), cursor.getString(1),cursor.getInt(2)==1?true:false);
                list.add(category);
            }
        }
        return list;
    }
    public Category getCategoryById(int categoryId){
        SQLiteDatabase db = getWritableDatabase();
        Category category = null;
        String sql = "SELECT * FROM "+TableName +" WHERE category_id="+categoryId;
        Cursor cursor = db.rawQuery(sql, null);
        if( cursor != null && cursor.moveToFirst() ){
            category = new Category(cursor.getInt(0), cursor.getString(1),cursor.getInt(2)==1?true:false);
            db.close();
        }
        return category;
    }

    public void addCategory(Category category){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(categoryId,category.getCategoryId());
        value.put(title,category.getTitle());
        value.put(selected,category.isSelected());
        db.insert(TableName,null,value);
    }
    public void updateCategory(int Id,Category category){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(title,category.getTitle());
        value.put(selected,category.isSelected());
        db.update(TableName,value,categoryId+"=?",new String[]{String.valueOf(Id)});
        db.close();
    }

    public void deleteAuthor(int categoryId){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM "+TableName +" WHERE Sbd="+categoryId;
        db.execSQL(sql);
        db.close();
    }
}
