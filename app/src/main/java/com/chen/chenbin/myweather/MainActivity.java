package com.chen.chenbin.myweather;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.chenbin.adapter.ViewPagerAdapter;
import com.chen.chenbin.data.GetWeather;
import com.chen.chenbin.data.MyApplication;
import com.chen.chenbin.data.MyDatabaseSqlite;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 每次添加和删除都要重新加载viewpager。要用到initPagerView method
 *
 * 还要加载天气图片，遇到城市输入错误怎么做，存到手机数据库
 *
 * 查询的数据必须独立出来，不能写在MyFragment ，因为查询的数据影响到2部分的组件，一个是MyFragment，一个是SearchActivity
 * 不独立出来需要写2部分的重复代码
 *
 * 不行的，保存的数据一定要存在数据库，否则网络延迟，viewPager在滑动的时候就崩溃了
 * 建立sqlite3，在GetWeather就resonseString存到sqlite，在MyFragment中从sqlite中读取数据
 * sqlite2 的表的设计，一个城市对应一个string。要插入，查询，删除操作
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    LinearLayout layout;
    // String[] cityArrays = {"观洲" ,"佛山","上海","汕头"};//实际的情况是这个数组是用户添加的，必须是动态的arraylist，但是不能重复，而且删除必须是可以直接删除，
    //不用遍历，需要查数据吗？不需要。点击添加按钮，必须跳转到搜索页面，搜索页面返回结果，才添加这个城市的集合。
    //删除按钮，必须删除组件，而且删除集合中的城市
    //集合的功能必须能遍历，能通过名字直接删除，不用遍历，不能重复
//    public  static  LinkedList<String> cityLinkedList = new LinkedList<String>();
    public static  LinkedList<String> cityJsonStringLinkedList = new LinkedList<String>();

    int count = 0;

    private ViewPager mViewPager;

    private ViewPagerAdapter viewPagerAdapter;

    private static int REQUEST_CODE =100;

    private static boolean successReceiveData = false;

    private View viewTemp;

    private int count_initPageView = 0;//非常怪异，居然是2到1次，增加之后删除

    private static String DATABASE_NAME="Weather.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.i("myToolbar", myToolbar + "<");

        setSupportActionBar(myToolbar);



            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);//toolbar菜单按钮出现
            getSupportActionBar().setIcon(android.R.color.transparent);
            getSupportActionBar().setTitle("");
            Log.i("version ","small Build.VERSION.SDK_INT="+Build.VERSION.SDK_INT  +"Build.VERSION_CODES.JELLY_BEAN_MR1 ="+Build.VERSION_CODES.JELLY_BEAN_MR1);


        //让按钮产生交互行为
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,myToolbar,R.string.drawer_open,R.string.drawer_close);//用v7就有list下标那种酷炫的效果
        //Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        managerCity();

        initPageView();//肯定出错，因为第一次没有点击搜索，SaveGetWeatherInstance中的GetWeather为空，MyFragment中的GetWeather就报空指针错误
        Log.i("count_initPageView", "count_initPageView onCreate=" + count_initPageView);

    }

    private void initPageView() {
        count_initPageView++;
        Log.i("count_initPageView","count_initPageView initPageView="+count_initPageView);
        mViewPager = (ViewPager) findViewById(R.id.pager);

//        ViewPagerAdapter.NUM_ITEMS = GetWeather.COUNT;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        Log.i("MyFragment", "MainActivity viewPagerAdapterArgs = " + ViewPagerAdapter.NUM_ITEMS);
        mViewPager.setAdapter(viewPagerAdapter);

    }

    private void managerCity() {
        //左边导航栏
//        cityLinkedList.add("广州");
//        cityLinkedList.add("佛山");
//        cityLinkedList.add("上海");
//        cityLinkedList.add("汕头");
        RelativeLayout r1= new RelativeLayout(this);
        RelativeLayout.LayoutParams relLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //布局文件不动态构建，从xml文件中获取
//        layout=new LinearLayout(this);
        layout = (LinearLayout) findViewById(R.id.left_linearLayout);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        ViewGroup.LayoutParams paramsLinearLayout = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);



//        LinearLayout hori_LinearLayout = new LinearLayout(this);
//        LinearLayout.LayoutParams hori_LinearLayout_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        hori_LinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//        TextView city_textView =new TextView(this);
//        city_textView.setText("城市");
//        city_textView.setTextSize(28);
//        city_textView.setBackgroundColor(Color.BLUE);
//        LinearLayout.LayoutParams city_textView_params = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1);
//
//
//        hori_LinearLayout.addView(city_textView, city_textView_params);
//
//        Button btn_del = new Button(this);
//        btn_del.setText("删除");
//        btn_del.setTextSize(28);
//
//        LinearLayout.LayoutParams  btn_del_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //添加权重的
//
//
//        hori_LinearLayout.addView(btn_del, btn_del_param);
//        layout.addView(hori_LinearLayout, hori_LinearLayout_param);

        MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(), DATABASE_NAME, null, 1);
        SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();

        String[] columns = {"city", "weather"};
        Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String city = cursor.getString(cursor.getColumnIndex("city"));
                if (city!=null){
                    LinearLayout hori_LinearLayout = new LinearLayout(MainActivity.this);
                    LinearLayout.LayoutParams hori_LinearLayout_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    hori_LinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    final TextView city_textView =new TextView(MainActivity.this);
                    city_textView.setText(city);
                    city_textView.setTextSize(28);
                    city_textView.setBackgroundColor(Color.BLUE);
                    LinearLayout.LayoutParams city_textView_params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1);
                    hori_LinearLayout.addView(city_textView, city_textView_params);

                    Button btn_del = new Button(MainActivity.this);
                    btn_del.setText("删除");
                    btn_del.setTextSize(28);

                    LinearLayout.LayoutParams  btn_del_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //添加权重的

                    hori_LinearLayout.addView(btn_del, btn_del_param);
                    layout.addView(hori_LinearLayout, hori_LinearLayout_param);

                    //删除的按钮的监听
                    btn_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View viewParent =(View)v.getParent();
                            ViewGroup viewGroup = (ViewGroup)viewParent.getParent();
                            viewGroup.removeView(viewParent);

                            //我必须知道城市的下标或者名字才能删除这个城市啊。
                            //找到city组件，找到text
                            ViewGroup linearLayout = (ViewGroup) v.getParent();
                            TextView textView = (TextView) linearLayout.getChildAt(0);

//                            cityLinkedList.remove(textView.getText().toString());
                            String city_view = textView.getText().toString();
                            MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(), DATABASE_NAME, null, 1);
                            SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();

                            String[] columns = {"city", "weather"};
                            Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);
                            while (cursor.moveToNext()){
                                String city = cursor.getString(cursor.getColumnIndex("city"));
                                Log.i("sequence","你要删除的数据库的城市名称=");
                                Log.i("sequence",city);

                                Log.i("sequence","你要删除的组件的城市名称=");
                                Log.i("sequence",city_view);

                                if (city_view.equals(city)){
//                        Log.i("sequence","column");
//                        Log.i("sequence", String.valueOf(cursor.getColumnIndex("_id")));
//
//                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                                    SQLiteDatabase dbw = myDatabaseSqlite.getWritableDatabase();
                                    dbw.delete("city_weather","city = '"+city_view+"'",null);//原来没有单引号，无法区分列还是字符串。还有cd databases,然后才sqlite3 数据库名字
                                    Log.i("sequence", "已经执行删除的sql语句");
                                }
                                if (cursor.isLast())
                                    break;
                            }
                            MainActivity.this.initPageView();
                        }
                    });

                }
                if (cursor.isLast())
                    break;
            }
        }
        Button btn_add = new Button(this);
        btn_add.setText("添加");
        LinearLayout.LayoutParams btn_add_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        layout.addView(btn_add, btn_add_param);


        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击添加按钮，把添加按钮删除

                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                Bundle bundle = new Bundle();
//                startActivity(intent);

                startActivityForResult(intent, REQUEST_CODE);
                viewTemp=v;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            Log.i("addLeft","requestCode");
            if (resultCode == RESULT_OK){
//                data.getBundleExtra();
                Log.i("addLeft","resultCode");
                Log.i("sequence", "MainActivity addLeft getData"+String.valueOf(data.getBooleanExtra("getData", false)));
                Log.i("sequence", "MainActivity addLeft successReceiveData"+successReceiveData);
                successReceiveData = data.getBooleanExtra("getData",false);

                Log.i("addLeft","successReceiveData "+successReceiveData );
                if (successReceiveData){
                    addLeftCityItemView(viewTemp);
                    Log.i("addLeft", "addLeft");

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void addLeftCityItemView(View v){
        initPageView();
        Log.i("sequence", " MainActivity addLeftCityItemView"+"3");
        //判断是否已经添加了这个城市
//                if (cityLinkedList.contains("user_input_city")){
//                Toast.makeText(MainActivity.this,"该城市你已经添加过了",Toast.LENGTH_LONG).show();
//                  return;
//               }else {
//                    cityLinkedList.add("user_input_city");
//                }
        //真正写的时候这个要删除
//                if (count<cityLinkedList.size()-1) {
//                    count = count + 1;
//
//                }else {
//                    return;
//                }
        //需要返回查询的城市已经成功添加城市列表的标记，才可以进行下面添加城市的操作

        ViewGroup viewGroup = (ViewGroup)v.getParent();
        viewGroup.removeView(v);
        //添加按钮的父组件是线性布局，得到线性布局，删除添加按钮就行了。



        LinearLayout hori_LinearLayout = new LinearLayout(MainActivity.this);
        LinearLayout.LayoutParams hori_LinearLayout_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hori_LinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        final TextView city_textView =new TextView(MainActivity.this);
        //这一行错误，必须知道cityLinkedList的最后一个
//        city_textView.setText(cityLinkedList.get(count));
        MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(), DATABASE_NAME, null, 1);
        SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();

        String[] columns = {"city", "weather"};
        Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);
        String add_city="";
        if (cursor!=null){
            while (cursor.moveToNext()){
                add_city = cursor.getString(cursor.getColumnIndex("city"));
                if (cursor.isLast())
                    break;
            }
        }

//        Log.i("sequence", "MainActivity cityLinkedList.getLast()" + cityLinkedList.getLast());
//        city_textView.setText(cityLinkedList.getLast());
        city_textView.setText(add_city);
        city_textView.setTextSize(28);
        city_textView.setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams city_textView_params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1);


        hori_LinearLayout.addView(city_textView, city_textView_params);

        Button btn_del = new Button(MainActivity.this);
        btn_del.setText("删除");
        btn_del.setTextSize(28);

        LinearLayout.LayoutParams  btn_del_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //添加权重的


        hori_LinearLayout.addView(btn_del, btn_del_param);
        layout.addView(hori_LinearLayout, hori_LinearLayout_param);

        //删除的按钮的监听
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewParent =(View)v.getParent();
                ViewGroup viewGroup = (ViewGroup)viewParent.getParent();
                viewGroup.removeView(viewParent);

                //我必须知道城市的下标或者名字才能删除这个城市啊。
                //找到city组件，找到text
                ViewGroup linearLayout = (ViewGroup) v.getParent();
                TextView textView = (TextView) linearLayout.getChildAt(0);

//                cityLinkedList.remove(textView.getText().toString());
                String city_view = textView.getText().toString();
                MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(), DATABASE_NAME, null, 1);
                SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();

                String[] columns = {"city", "weather"};
                Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);
                while (cursor.moveToNext()){
                    String city = cursor.getString(cursor.getColumnIndex("city"));
                    Log.i("sequence","你要删除的数据库的城市名称=");
                    Log.i("sequence",city);

                    Log.i("sequence","你要删除的组件的城市名称=");
                    Log.i("sequence",city_view);

                    if (city_view.equals(city)){
//                        Log.i("sequence","column");
//                        Log.i("sequence", String.valueOf(cursor.getColumnIndex("_id")));
//
//                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        SQLiteDatabase dbw = myDatabaseSqlite.getWritableDatabase();
                        dbw.delete("city_weather","city = '"+city_view+"'",null);//原来没有单引号，无法区分列还是字符串。还有cd databases,然后才sqlite3 数据库名字
                        Log.i("sequence", "已经执行删除的sql语句");
                    }
                    if (cursor.isLast())
                        break;
                }
                MainActivity.this.initPageView();
            }
        });

        //add一个添加按钮
        //点击添加按钮，把添加按钮删除

        viewGroup.addView(v);
    }
    //让箭头按钮变成菜单图标
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    //这个方法好像没啥用
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * An {@code int} value that may be updated atomically.
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    /**
     * 动态生成View ID
     * API LEVEL 17 以上View.generateViewId()生成
     * API LEVEL 17 以下需要手动生成
     */
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

}
