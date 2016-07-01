package com.chen.chenbin.myweather;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.SearchView;
import android.widget.Toast;

import com.chen.chenbin.Thread.MyAsyncTask;
import com.chen.chenbin.data.GetWeather;
import com.chen.chenbin.data.MyApplication;
import com.chen.chenbin.data.MyDatabaseSqlite;
import com.chen.chenbin.data.SaveGetWeatherInstance;



/**
 * 这个类必须返回的数据是保存getWeather吗？不是已经改了，saveGetWeatherInstance.getGetWeatherJsonString()！=null，说明数据已经返回了
 * 1.MainActivity.cityLinkedList.add(query);
 * 2.关闭这个Activity
 * 2.
 *                                 Intent intent = new Intent();
 intent.putExtra("getData", true);

 setResult(RESULT_OK, intent);
 //难道是因为没有finish导致没有返回结果
 finish();
 */
public class SearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Handler handler;
    private String queryString;
    private static String DATABASE_NAME="Weather.db";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.search_toolbar);



        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SearchView searchView = (SearchView) findViewById(R.id.id_searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.onActionViewExpanded();

        handler = new Handler();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

//
                //连接网络，进行天气的城市查询，返回数据成功的时候，，添加所在的城市到链表中。
                //当然这个城市是必须存在的，不能随便乱输入。客户端必须对城市进行查询验证，
                // 1那么数据就必须保存在手机的数据库
                //2 要么使用接口。最好使用接口。如果用天气的接口不知道可以查出城市错误吗？

                /*
                查询城市的代码......
                 */

                queryString =query;
                searchView.clearFocus();
//                GetWeather getWeather = new GetWeather();
//                Log.i("sequence"," SearchActivity cityLinkedList"+MainActivity.cityLinkedList);
//                if (MainActivity.cityLinkedList.contains(query)){
//                    Toast.makeText(SearchActivity.this,query+"已经添加过了",Toast.LENGTH_LONG).show();
//
//                    return true;
//                }

                Log.i("sequence"," SearchActivity GetWeather.apiTest 1");

                {
                    MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(), DATABASE_NAME, null, 1);
                    SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();

                    String[] columns = {"city", "weather"};
                    Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);
                    while(cursor.moveToNext()) {
                        String city = cursor.getString(cursor.getColumnIndex("city"));
                        Log.i("sequence", "city=" + city);
                        if (queryString.equals(city)) {
                            Toast.makeText(SearchActivity.this,query+"已经添加过了",Toast.LENGTH_LONG).show();
                            return true;
                        }
                        if (cursor.isLast())
                            break;
                    }
                }
                GetWeather.apiTest(query);//这里出错，因为这里改变了城市，但是再次实例化的时候传入的城市没有储存，这次只是临时查询
                //这里查询天气的数据后，这个实例要用，不能在MyFragment中再实例化一个GetWeather。用一个类去保存GetWeather的实例，那么就可以在MyFragment中去使用这个实例
                //第2中处理方式是传递参数到MyFragment.那么就需要传递getWeather到MainActivity。然后传递到ViewPagerAdapter,然后传递到MyFragment,太麻烦了

                //错误，不是按照时间的顺序，执行getWeather.apiTest(query);这个方法，之后，不是执行完毕再执行下面的代码，而是同时执行，所以导致了错误。使用AsyTask
//                SaveGetWeatherInstance saveGetWeatherInstance = new SaveGetWeatherInstance();
                //线程没有启动导致错误
//                Intent intent = new Intent();
//                intent.putExtra("getData", false);//重置上面的值，否则MainActivity得到的intent是原来的值

                new Thread(new Runnable() {
//                    @Override
                    public void run() {
                        boolean flag = true;
                        Log.i("sequence","SearchActivity Thread ");

                        while (flag) {
                            if ("unknown city".equals(GetWeather.getErrorMessage())){

                                GetWeather.setErrorMessage("");
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SearchActivity.this,queryString+"你输入的城市不存在",Toast.LENGTH_LONG).show();
                                    }
                                });
                                break;
                            }
                            MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(), DATABASE_NAME, null, 1);
                            SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();

                            String[] columns = {"city", "weather"};
                            Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);

//                            if(cursor.move(GetWeather.COUNT)){
                            while(cursor.moveToNext()){
//                                Log.i("sequence","SearchActivity cursor.move GetWeather COUNT="+GetWeather.COUNT);
//                                String weather = cursor.getString(cursor.getColumnIndex("weather"));
//                                Log.i("sequence","weather="+weather);
                                String city = cursor.getString(cursor.getColumnIndex("city"));
                                Log.i("sequence","city="+city);
//                                SaveGetWeatherInstance saveGetWeatherInstance = new SaveGetWeatherInstance();
//                                GetWeather getWeather = saveGetWeatherInstance.getGetWeather();

//                            if (getWeather!=null){
//                                if (weather !=null){
                                if (queryString.equals(city)){
                                    flag = false;

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
//                                    MainActivity.cityLinkedList.add(queryString);

                                            Intent intent = new Intent();
                                            intent.putExtra("getData", true);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    });
                                        dbr.close();
                                        myDatabaseSqlite.close();
                                    break;
//                                    try {
//                                        this.wait();
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
                                }
                                if (cursor.isLast()){
                                    break;
                                }
                            }
//                            dbr.close();
//                            myDatabaseSqlite.close();



                        }


                    }
                }).start();


//                if (getWeather.getErrorMessage()!=null){
//                    if ("unknown city".equals(getWeather.getErrorMessage())){
//                        Toast.makeText(SearchActivity.this, "你输入的城市名称错误，请重新输入", Toast.LENGTH_LONG).show();
//                        return false;
//                    }else {
////                        SaveGetWeatherInstance saveGetWeatherInstance = new SaveGetWeatherInstance();
//                        saveGetWeatherInstance.setGetWeather(getWeather);
//                    }
//                }
//
//                //原来是if和else方法有问题
//                //好奇怪，居然不执行unknown city之后就跳出if和else循环
////                这个方法被执行2次，按钮按下和弹起，解决办法就是清空第二次的查询字符串
//                    searchView.clearFocus();//
//
//                    MainActivity.cityLinkedList.add(query);
//
//                    //这里每次一查询完毕，就结束这个activity，其实是有问题的
//                    Intent intent = new Intent();
//                    intent.putExtra("getData", true);
//
//                    setResult(RESULT_OK, intent);
//                    //难道是因为没有finish导致没有返回结果
//                    finish();
//                if (query!=null){
//                    MyAsyncTask myAsyncTask = new MyAsyncTask(SearchActivity.this,searchView,query);
//                    myAsyncTask.execute();
//                    searchView.clearFocus();
//                    return true;
//                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        /**
         * 添加一个返回键和一个监听事件
         */
        //要在搜索按钮添加返回键，可以返回啊。返回的话，直接finish这个activity就好了

        /**
         * 设置结果码，返回数据给activity
         *
         */

//        不是这样返回的,不能一初始化完毕，又关掉啊。应该要设置监听才行。因为那个点击返回的按钮的事件是android自动做的，
// 我没有处理，导致错误。没有返回数据

//        Intent intent = new Intent();
//        intent.putExtra("getData",true);
//
//        setResult(RESULT_OK,intent);
//        //难道是因为没有finish导致没有返回结果
//        finish();
    }
}
