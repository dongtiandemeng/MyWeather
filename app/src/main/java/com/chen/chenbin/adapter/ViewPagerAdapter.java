package com.chen.chenbin.adapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.chen.chenbin.data.MyApplication;
import com.chen.chenbin.data.MyDatabaseSqlite;
import com.chen.chenbin.fragment.MyFragment;
import com.chen.chenbin.myweather.MainActivity;

/**
 * Created by chenbin on 2016/6/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static String DATABASE_NAME="Weather.db";
//    public static int NUM_ITEMS = 0;
    public  int NUM_ITEMS = 0;//不能每new一个，就使用静态变量啊，这样很多数目不断增加的。这个不是共享变量，每一次new。就重新检索
    MyDatabaseSqlite myDatabaseSqlite;
    SQLiteDatabase dbr;
    String[] columns = {"city", "weather"};
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
//        Log.i("ViewPagerAdapter", "ViewPagerAdapter " + MainActivity.cityLinkedList);
        notifyDataSetChanged();
        Log.i("sequence", "ViewPagerAdapter NUM_ITEMS =");
        Log.i("sequence",String.valueOf(NUM_ITEMS));
         myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(), DATABASE_NAME, null, 1);
         dbr = myDatabaseSqlite.getReadableDatabase();



        Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);
        while (cursor.moveToNext()){
            NUM_ITEMS++;
            if (cursor.isLast())
                break;
        }
        Log.i("sequence","ViewPagerAdapter NUM_ITEMS=");
        Log.i("sequence", String.valueOf(NUM_ITEMS));

    }


    @Override
    public Fragment getItem(int position) {


//        for (int i=0;i<NUM_ITEMS;i++){
//            if (position)
//        }

//        String whichCity = MainActivity.cityLinkedList.get(position);
//        Fragment fragment = new MyFragment();
//        Bundle args = new Bundle();
//        args.putString("whichCity",whichCity);
//        fragment.setArguments(args);
        Log.i("sequence","ViewPagerAdapter position =");
        Log.i("sequence",String.valueOf(position));
//        MyFragment.WHICH_CITY = MainActivity.cityLinkedList.get(position);
        Fragment fragment = MyFragment.newInstance(MyFragment.WHICH_CITY,position);



//        Log.i("whichCity","ViewPagerAdapter whichCity"+whichCity);
        return fragment;
    }

    @Override
    public int getCount() {

        return NUM_ITEMS;
    }
}
