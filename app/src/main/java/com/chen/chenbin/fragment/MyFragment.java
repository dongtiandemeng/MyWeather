package com.chen.chenbin.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chen.chenbin.data.GetWeather;
import com.chen.chenbin.data.MyDatabaseSqlite;
import com.chen.chenbin.data.SaveGetWeatherInstance;
import com.chen.chenbin.myweather.MainActivity;
import com.chen.chenbin.myweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenbin on 2016/6/16.
 */
public class MyFragment extends Fragment {
    public static String WHICH_CITY="city";
//    public static String ARG_PLANET_NUMBER = "";
//    private String[] mPlanetTitles = {"one", "two", "three"};
//    private  TextView mTextView;
//    String jonString="{\"HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"39\",\"co\":\"0\",\"no2\":\"23\",\"o3\":\"112\",\"pm10\":\"39\",\"pm25\":\"23\",\"qlty\":\"良\",\"so2\":\"2\"}},\"basic\":{\"city\":\"北京\",\"cnty\":\"中国\",\"id\":\"CN101010100\",\"lat\":\"39.904000\",\"lon\":\"116.391000\",\"update\":{\"loc\":\"2016-06-11 20:52\",\"utc\":\"2016-06-11 12:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:42\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"101\",\"txt_d\":\"晴\",\"txt_n\":\"多云\"},\"date\":\"2016-06-11\",\"hum\":\"20\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1006\",\"tmp\":{\"max\":\"30\",\"min\":\"18\"},\"vis\":\"10\",\"wind\":{\"deg\":\"348\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:43\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"104\",\"txt_d\":\"晴\",\"txt_n\":\"阴\"},\"date\":\"2016-06-12\",\"hum\":\"14\",\"pcpn\":\"0.0\",\"pop\":\"1\",\"pres\":\"1005\",\"tmp\":{\"max\":\"32\",\"min\":\"20\"},\"vis\":\"10\",\"wind\":{\"deg\":\"211\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:43\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"302\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"雷阵雨\"},\"date\":\"2016-06-13\",\"hum\":\"38\",\"pcpn\":\"12.8\",\"pop\":\"81\",\"pres\":\"998\",\"tmp\":{\"max\":\"27\",\"min\":\"18\"},\"vis\":\"9\",\"wind\":{\"deg\":\"35\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"1\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:44\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"300\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-06-14\",\"hum\":\"85\",\"pcpn\":\"15.6\",\"pop\":\"75\",\"pres\":\"996\",\"tmp\":{\"max\":\"25\",\"min\":\"18\"},\"vis\":\"7\",\"wind\":{\"deg\":\"308\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"10\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:44\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"100\",\"txt_d\":\"多云\",\"txt_n\":\"晴\"},\"date\":\"2016-06-15\",\"hum\":\"17\",\"pcpn\":\"0.0\",\"pop\":\"34\",\"pres\":\"997\",\"tmp\":{\"max\":\"29\",\"min\":\"19\"},\"vis\":\"10\",\"wind\":{\"deg\":\"354\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:45\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-06-16\",\"hum\":\"18\",\"pcpn\":\"0.0\",\"pop\":\"14\",\"pres\":\"999\",\"tmp\":{\"max\":\"32\",\"min\":\"21\"},\"vis\":\"10\",\"wind\":{\"deg\":\"333\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"10\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:45\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"104\",\"txt_d\":\"多云\",\"txt_n\":\"阴\"},\"date\":\"2016-06-17\",\"hum\":\"12\",\"pcpn\":\"0.0\",\"pop\":\"36\",\"pres\":\"1001\",\"tmp\":{\"max\":\"30\",\"min\":\"21\"},\"vis\":\"10\",\"wind\":{\"deg\":\"210\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"1\"}}],\"hourly_forecast\":[{\"date\":\"2016-06-11 22:00\",\"hum\":\"40\",\"pop\":\"0\",\"pres\":\"1009\",\"tmp\":\"28\",\"wind\":{\"deg\":\"251\",\"dir\":\"西南风\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"now\":{\"cond\":{\"code\":\"101\",\"txt\":\"多云\"},\"fl\":\"27\",\"hum\":\"37\",\"pcpn\":\"0\",\"pres\":\"1007\",\"tmp\":\"27\",\"vis\":\"10\",\"wind\":{\"deg\":\"200\",\"dir\":\"西南风\",\"sc\":\"3-4\",\"spd\":\"11\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"炎热\",\"txt\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，户外运动请注意防晒，推荐您在室内进行低强度运动。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，但稍感觉有些热，不过还是个好天气哦。适宜旅游，可不要错过机会呦！\"},\"uv\":{\"brf\":\"强\",\"txt\":\"紫外线辐射强，建议涂擦SPF20左右、PA++的防晒护肤品。避免在10点至14点暴露于日光下。\"}}}]}";

    private  ListView listView;
    private GridView gridView;

    GetWeather getWeather ;
    SaveGetWeatherInstance saveGetWeatherInstance;
    private static String DATABASE_NAME="Weather.db";

    public static Fragment newInstance(String whichCity,int whatPage){
        Fragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(whichCity, whatPage);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_fragment, container, false);
//        TextView tv = (TextView) v.findViewById(R.id.id_fragment_item_tv);
        int args = getArguments().getInt(WHICH_CITY);
//        int args = getArguments().getInt(ARG_PLANET_NUMBER);
//        Log.i("MyFragment","MyFragment ARG_PLANET_NUMBER = "+ARG_PLANET_NUMBER);
//        Log.i("MyFragment", "MyFragment args = " + args);
//        tv.setText(mPlanetTitles[args]);
//        tv.setText(args);



        //加载新闻,还是错误了，不应该在这里，应该在搜索那里去做查询，失败了，就不要添加这个fragment，而是在viewpager那里判断
//        getWeather = new GetWeather();
//        getWeather.apiTest(args);
//        if (getWeather.getErrorMessage()!=null){
//            if ("unknown city".equals(getWeather.getErrorMessage())){
//                Toast.makeText(getActivity(), "你输入的城市名称错误，请重新输入", Toast.LENGTH_LONG).show();
////                return;
//            }
//        }
        //得到已经在SearchMainActivity中获取天气的GetWeather实例，这样就保证只查询一次，而不是实例化2次GetWeather

        saveGetWeatherInstance = new SaveGetWeatherInstance();
        getWeather =saveGetWeatherInstance.getGetWeather();
        //不同实例的静态变量的set和get方法调用的变量为空


//        if(getWeather!=null){
//            initUI(v);
//        }
//        if(getWeather.getJonString()!=null){
//            initUI(v,MainActivity.cityJsonStringLinkedList.get(0));

            MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(getActivity(),DATABASE_NAME,null,1);
            SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();

            String[] columns={"city","weather"};
            Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);

        if (cursor.move(args+1)!=false){
//            cursor.move(args+1);
            String weather = cursor.getString(cursor.getColumnIndex("weather"));
            Log.i("weather","MyFragment");
            Log.i("weather",weather);
            Log.i("sequence", " MyFragment"+"4");
            initUI(v, weather);
        }

        return v;
    }

    private void initUI(View view,String jsonString) {

        TextView textView_now_city= (TextView) view.findViewById(R.id.id_now_city_name);
        TextView textView_now_tmp = (TextView) view.findViewById(R.id.id_textView_now_tmp);
        TextView textView_now_weather = (TextView) view.findViewById(R.id.id_textView_now_weather);
        TextView textView_now_aqi = (TextView) view.findViewById(R.id.id_textView_now_aqi);


        HashMap map = GetWeather.dealNowWeather(jsonString);
//                HashMap map = getWeather.dealNowWeather(saveGetWeatherInstance.getGetWeatherJsonString());
        Log.i("excepton","exception = "+(String) map.get("city"));

        textView_now_city.setText((String) map.get("city"));
        textView_now_tmp.setText((String)map.get("now_tmp"));
        textView_now_weather.setText((String)map.get("now_cond_txt"));
        textView_now_aqi.setText((String)map.get("aqi_city_qlty"));
//        mTextView = (TextView) view.findViewById(R.id.id_mTextView);

//        apiTest();
//        mTextView.setText("");
        listView = (ListView) view.findViewById(R.id.id_week_weather_list);
        gridView = (GridView) view.findViewById(R.id.id_gridview_life);


        ArrayList<HashMap<String,String>> arrayListWeekWeather;
//        arrayListWeekWeather =getWeather.dealWeekWeather(saveGetWeatherInstance.getGetWeatherJsonString());
        arrayListWeekWeather =GetWeather.dealWeekWeather(jsonString);
//        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
//
//        for(int i = 1;i < 6;i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("ItemImage", R.drawable.icon);
//            map.put("ItemText", "NO."+i);
//            meumList.add(map);
//        }
//
//        SimpleAdapter saMenuItem = new SimpleAdapter(this,
//                meumList, //数据源
//                R.layout.menuitem, //xml实现
//                new String[]{"ItemImage","ItemText"}, //对应map的Key
//                new int[]{R.id.ItemImage,R.id.ItemText});  //对应R的Id



        SimpleAdapter simpleAdapterWeekWeather = new SimpleAdapter(getActivity(),
                arrayListWeekWeather,
                R.layout.week_weather_item,
                new String[]{"date","weather","maxTmp","minTmp","wind"},
                new int[]{R.id.id_date,R.id.id_weather,R.id.id_tmp_max,R.id.id_tmp_min,R.id.id_wind});

        listView.setAdapter(simpleAdapterWeekWeather);

        /*-----------------gridview-----------------------------------*/
//        map.put("comf",suggestion_comf_brf);
//        map.put("cw",suggestion_cw_brf);
//        map.put("drsg",suggestion_drsg_brf);
//        map.put("flu",suggestion_flu_brf);
//        map.put("sport",suggestion_sport_brf);
//        map.put("trav", suggestion_trav_brf);

        //        map.put("brif",suggestion_cw_brf);
//        map.put("lifeImage",R.drawable.car);

        ArrayList<HashMap<String,Object>> arrayListIndexOfLiving;
        arrayListIndexOfLiving = GetWeather.dealIndexOfLiving(jsonString);
//        arrayListIndexOfLiving = getWeather.dealIndexOfLiving(saveGetWeatherInstance.getGetWeatherJsonString());

        SimpleAdapter simpleAdapterIndexOfLiving = new SimpleAdapter(getActivity(),
                arrayListIndexOfLiving,
                R.layout.life_gridview_item,
                new String[]{"brif","lifeImage"},
                new int[]{R.id.id_life_desc,R.id.id_life_image});//这个文字填充是没有问题的，但是图片就有问题了。所以在map中要添加图片
        gridView.setAdapter(simpleAdapterIndexOfLiving);

    }


}
