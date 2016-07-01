package com.chen.chenbin.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.chen.chenbin.myweather.MainActivity;
import com.chen.chenbin.myweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenbin on 2016/6/17.
 * 这个类不需要数据，可以直接设置成为静态方法，直接调用就行了
 */
public class GetWeather {
    public  String jonString="{\"HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"39\",\"co\":\"0\",\"no2\":\"23\",\"o3\":\"112\",\"pm10\":\"39\",\"pm25\":\"23\",\"qlty\":\"良\",\"so2\":\"2\"}},\"basic\":{\"city\":\"北京\",\"cnty\":\"中国\",\"id\":\"CN101010100\",\"lat\":\"39.904000\",\"lon\":\"116.391000\",\"update\":{\"loc\":\"2016-06-11 20:52\",\"utc\":\"2016-06-11 12:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:42\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"101\",\"txt_d\":\"晴\",\"txt_n\":\"多云\"},\"date\":\"2016-06-11\",\"hum\":\"20\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1006\",\"tmp\":{\"max\":\"30\",\"min\":\"18\"},\"vis\":\"10\",\"wind\":{\"deg\":\"348\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:43\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"104\",\"txt_d\":\"晴\",\"txt_n\":\"阴\"},\"date\":\"2016-06-12\",\"hum\":\"14\",\"pcpn\":\"0.0\",\"pop\":\"1\",\"pres\":\"1005\",\"tmp\":{\"max\":\"32\",\"min\":\"20\"},\"vis\":\"10\",\"wind\":{\"deg\":\"211\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:43\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"302\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"雷阵雨\"},\"date\":\"2016-06-13\",\"hum\":\"38\",\"pcpn\":\"12.8\",\"pop\":\"81\",\"pres\":\"998\",\"tmp\":{\"max\":\"27\",\"min\":\"18\"},\"vis\":\"9\",\"wind\":{\"deg\":\"35\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"1\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:44\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"300\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-06-14\",\"hum\":\"85\",\"pcpn\":\"15.6\",\"pop\":\"75\",\"pres\":\"996\",\"tmp\":{\"max\":\"25\",\"min\":\"18\"},\"vis\":\"7\",\"wind\":{\"deg\":\"308\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"10\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:44\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"100\",\"txt_d\":\"多云\",\"txt_n\":\"晴\"},\"date\":\"2016-06-15\",\"hum\":\"17\",\"pcpn\":\"0.0\",\"pop\":\"34\",\"pres\":\"997\",\"tmp\":{\"max\":\"29\",\"min\":\"19\"},\"vis\":\"10\",\"wind\":{\"deg\":\"354\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:45\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-06-16\",\"hum\":\"18\",\"pcpn\":\"0.0\",\"pop\":\"14\",\"pres\":\"999\",\"tmp\":{\"max\":\"32\",\"min\":\"21\"},\"vis\":\"10\",\"wind\":{\"deg\":\"333\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"10\"}},{\"astro\":{\"sr\":\"04:45\",\"ss\":\"19:45\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"104\",\"txt_d\":\"多云\",\"txt_n\":\"阴\"},\"date\":\"2016-06-17\",\"hum\":\"12\",\"pcpn\":\"0.0\",\"pop\":\"36\",\"pres\":\"1001\",\"tmp\":{\"max\":\"30\",\"min\":\"21\"},\"vis\":\"10\",\"wind\":{\"deg\":\"210\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"1\"}}],\"hourly_forecast\":[{\"date\":\"2016-06-11 22:00\",\"hum\":\"40\",\"pop\":\"0\",\"pres\":\"1009\",\"tmp\":\"28\",\"wind\":{\"deg\":\"251\",\"dir\":\"西南风\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"now\":{\"cond\":{\"code\":\"101\",\"txt\":\"多云\"},\"fl\":\"27\",\"hum\":\"37\",\"pcpn\":\"0\",\"pres\":\"1007\",\"tmp\":\"27\",\"vis\":\"10\",\"wind\":{\"deg\":\"200\",\"dir\":\"西南风\",\"sc\":\"3-4\",\"spd\":\"11\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"炎热\",\"txt\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，户外运动请注意防晒，推荐您在室内进行低强度运动。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，但稍感觉有些热，不过还是个好天气哦。适宜旅游，可不要错过机会呦！\"},\"uv\":{\"brf\":\"强\",\"txt\":\"紫外线辐射强，建议涂擦SPF20左右、PA++的防晒护肤品。避免在10点至14点暴露于日光下。\"}}}]}";
    public static int COUNT=0;
    public  String getJonString() {
        return jonString;
    }

    public void setJonString(String jonString) {
        this.jonString = jonString;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String errorMessage) {
        GetWeather.errorMessage = errorMessage;
    }

    public static String  errorMessage = "";

    public static ArrayList<HashMap<String,Object>> dealIndexOfLiving(String jonString){
        ArrayList<HashMap<String,Object>> arrayList =new ArrayList<HashMap<String, Object>>();
        try {
            JSONArray jsonArray = new JSONArray(new JSONObject(jonString).getString("HeWeather data service 3.0"));
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            JSONObject suggestion = jsonObject.getJSONObject("suggestion");

            JSONObject suggestion_comf = suggestion.getJSONObject("comf");
            String suggestion_comf_brf = suggestion_comf.getString("brf");
            Log.i("HeWeather", "suggestion_comf_brf =" + suggestion_comf_brf + "\n");//舒适度指数（一个笑脸），，干脆弄个紫外线一把伞代替x

            JSONObject suggestion_cw = suggestion.getJSONObject("cw");
            String suggestion_cw_brf = suggestion_cw.getString("brf");
            Log.i("HeWeather","suggestion_cw_brf ="+suggestion_cw_brf+"\n"); //洗车指数car,pngx

            JSONObject suggestion_drsg = suggestion.getJSONObject("drsg");
            String suggestion_drsg_brf = suggestion_drsg.getString("brf");
            Log.i("HeWeather","suggestion_drsg_brf ="+suggestion_drsg_brf+"\n");//穿衣指数，衣服x

            JSONObject suggestion_flu = suggestion.getJSONObject("flu");
            String suggestion_flu_brf = suggestion_flu.getString("brf");
            Log.i("HeWeather","suggestion_flu_brf ="+suggestion_flu_brf+"\n");//感冒指数?药丸x

            JSONObject suggestion_sport = suggestion.getJSONObject("sport");
            String suggestion_sport_brf = suggestion_sport.getString("brf");
            Log.i("HeWeather","suggestion_sport_brf ="+suggestion_sport_brf+"\n");//运动指数running.png

            JSONObject suggestion_trav = suggestion.getJSONObject("trav");

            String suggestion_trav_txt = suggestion_sport.getString("txt");
            String suggestion_trav_brf = suggestion_sport.getString("brf");//?旅游 ，飞机

            Log.i("HeWeather","suggestion_trav_brf ="+suggestion_trav_brf+"\n");//旅游指数
            Log.i("HeWeather","suggestion_trav_txt ="+suggestion_trav_txt+"\n");//旅游指数

//            for(int i=0;i<6;i++){
//                HashMap<String,String> map = new HashMap<String,String>();
//                map.put("comf",suggestion_comf_brf);
//                map.put("cw",suggestion_cw_brf);
//                map.put("drsg",suggestion_drsg_brf);
//                map.put("flu",suggestion_flu_brf);
//                map.put("sport",suggestion_sport_brf);
//                map.put("trav",suggestion_trav_brf);
//                arrayList.add(map);
//            }
            HashMap<String,Object> map = null;

            map= new HashMap<String,Object>();
            map.put("brif", "舒适度"+suggestion_comf_brf);
            //还要在这里添加图片，hashmap的类型要设置为object
            map.put("lifeImage", R.drawable.smile );//smile
            arrayList.add(map);

            map= new HashMap<String,Object>();
            map.put("brif","洗车"+suggestion_cw_brf);
            map.put("lifeImage",R.drawable.car);//car
            arrayList.add(map);

            map= new HashMap<String,Object>();
            map.put("brif","穿衣"+suggestion_drsg_brf);
            map.put("lifeImage",R.drawable.clothing);//clothing
            arrayList.add(map);

            map= new HashMap<String,Object>();
            map.put("brif","流感"+suggestion_flu_brf);
            map.put("lifeImage",R.drawable.pill);//pill
            arrayList.add(map);

            map= new HashMap<String,Object>();
            map.put("brif","运动"+suggestion_sport_brf);
            map.put("lifeImage",R.drawable.ic_directions_run_white_48dp);//run_white_48dp
            arrayList.add(map);

            map= new HashMap<String,Object>();
            map.put("brif","旅游"+suggestion_trav_brf);
            map.put("lifeImage",R.drawable.ic_flight_white_48dp);//flight_white_48dp
            arrayList.add(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<HashMap<String,String>> dealWeekWeather(String jonString){
        ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String,String>>();
        try {
            JSONArray jsonArray = new JSONArray(new JSONObject(jonString).getString("HeWeather data service 3.0"));
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JSONArray daily_forecast = jsonObject.getJSONArray("daily_forecast");
            for (int i=0;i<8;i++){
                JSONObject oneday = daily_forecast.getJSONObject(i);

                String oneday_date = oneday.getString("date");
                JSONObject oneday_cond = oneday.getJSONObject("cond");
                String oneday_cond_txt_d = oneday_cond.getString("txt_d");
                JSONObject oneday_tmp = oneday.getJSONObject("tmp");
                String oneday_tmp_max = oneday_tmp.getString("max");
                String oneday_tmp_min = oneday_tmp.getString("min");
                JSONObject oneday_wind = oneday.getJSONObject("wind");
//            String oneday_wind_dir = oneday_wind.getString("dir");
//            Log.i("HeWeather","oneday_wind_dir ="+oneday_wind_dir+"\n");
                String oneday_wind_sc = oneday_wind.getString("sc");

                HashMap<String,String> map = new HashMap<String,String>();
                map.put("date",oneday_date);
                map.put("weather",oneday_cond_txt_d);
                map.put("maxTmp",oneday_tmp_max);
                map.put("minTmp",oneday_tmp_min);
                map.put("wind",oneday_wind_sc);

                arrayList.add(map);

                Log.i("HeWeather","oneday_dat第"+i+"天日期 ="+oneday_date+"\n");//第一天的日期11
                Log.i("HeWeather","oneday_cond_txt_d 第"+i+"天天气="+oneday_cond_txt_d+"\n");//第一天天气的12
                Log.i("HeWeather","oneday_tmp_max第"+i+"天的最高温度 ="+oneday_tmp_max+"\n");//第一天的最高温度13
                Log.i("HeWeather","oneday_tmp_min 第"+i+"天的最低温度="+oneday_tmp_min+"\n");//第一天的最低温度14
                Log.i("HeWeather","oneday_wind_sc第"+i+"天的风力 ="+oneday_wind_sc+"\n");//第一天的风力15
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void apiTest(final String city) {

        Parameters para = new Parameters();
        Log.i("mytime","GetWeather.class apiTest 2");
        Log.i("apiTest", "apiTest \n");
//        para.put("city", "beijing");
        para.put("city", city);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack() {

                    @Override
                    public void onSuccess(int status, String responseString) {
//                        Log.i("sdkdemo", "onSuccess");
//                        mTextView.setText(responseString);
//                        Log.i("string", responseString);
                        String statusWeather = null;
                        try {
                            JSONArray jsonArray = new JSONArray(new JSONObject(responseString).getString("HeWeather data service 3.0"));
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            statusWeather = jsonObject.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("sdkdemo", "statusWeather =" + statusWeather + "\n");
                        if (statusWeather != null) {
                            if ("unknown city".equals(statusWeather)) {
                                //在android要使用Toast提示错误
                                GetWeather.setErrorMessage(statusWeather);
                                Log.i("sdkdemo", "输入的城市错误");
//                                Toast.makeText(getActivity(), "你输入的城市名称错误，请重新输入", Toast.LENGTH_LONG).show();
                                //这里发生城市错误，管理城市组件就不能添加了
//                                GetWeather.this.setErrorMessage(statusWeather);
                                Log.i("MyFragment", "return");
                                return;
                            }
                        }
//                        Log.i("mytime", "GetWeather.class apiTest  onSuccess 3");
//                        Log.i("MyFragment", "trrrrrrrrrrrrrrrrrrrrrrrrr");

//                        SaveGetWeatherInstance saveGetWeatherInstance = new SaveGetWeatherInstance();
////                        saveGetWeatherInstance.setGetWeatherJsonString(responseString);
//                        GetWeather getWeather = new GetWeather();
//                        Log.i("MyFragment", "GetWeather class responseString=" + responseString);
//                        Log.i("MyFragment","MyFragment saveGetWeatherInstance setGetWeatherJsonString="+saveGetWeatherInstance.getGetWeatherJsonString());
//                        jonString = responseString;
                        //这里修改jsonString没有用，因为apiTest 中的一个内部类new ApiCallBack()，会屏蔽点变量的改变，所以jsonString = responseString这句话
                        //没有起到作用，没有修改jsonString的值。
                        //这样很难修改jsonString，无法得到responseString,因为这个参数是一个内部类的参数
//                        getWeather.setJonString(responseString);
//                        Log.i("string", "new ApiCallBack() jonString******");
//                        Log.i("string", getWeather.getJonString());
//                        saveGetWeatherInstance.setGetWeather(getWeather);
//                        Log.i("string", "saveGetWeatherInstance.getGetWeather()" + saveGetWeatherInstance.getGetWeather());
//                        MainActivity.cityJsonStringLinkedList.add(responseString);

                        String DATABASE_NAME="Weather.db";
                        MyDatabaseSqlite myDatabaseSqlite = new MyDatabaseSqlite(MyApplication.getContext(),DATABASE_NAME,null,1);
                        Log.i("myDatabaseSqlite", "myDatabaseSqlite=" + myDatabaseSqlite);
                        SQLiteDatabase dbw  = myDatabaseSqlite.getWritableDatabase();
                        Log.i("myDatabaseSqlite", "dbw=" + dbw);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("city",city);

                        contentValues.put("weather", responseString);

                        dbw.insert("city_weather", null, contentValues);
//                        MainActivity.cityLinkedList.add(city);

//                        Log.i("sequence","GetWeather cityLinkedList"+MainActivity.cityLinkedList);

                        dbw.close();
//                        GetWeather.COUNT++;

                        Log.i("sequence", " GetWeather.apiTest 2");
                        Log.i("sequence", " city 2.1="+city);
//                        MyDatabaseSqlite myDatabaseSqlite1 = new MyDatabaseSqlite(MyApplication.getContext(),DATABASE_NAME,null,1);
//                        SQLiteDatabase dbr = myDatabaseSqlite.getReadableDatabase();
//
//                        String[] columns={"city","weather"};
//                        Cursor cursor = dbr.query("city_weather", columns, null, null, null, null, null);
//                        cursor.move(GetWeather.COUNT);
//                        String weather = cursor.getString(cursor.getColumnIndex("weather"));
//                        Log.i("myDatabaseSqlite","weather="+weather);


                    }

                    @Override
                    public void onComplete() {
                        Log.i("sdkdemo", "onComplete");
                    }

                    @Override
                    public void onError(int status, String responseString, Exception e) {
//                        Log.i("sdkdemo", "onError, status: " + status);
//                        Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
//                        mTextView.setText(getStackTrace(e));
                    }

                });

//        Log.i("string", "apiTest jonString******");
//        Log.i("string", jonString);

    }

    public  static HashMap<String,String> dealNowWeather(String jonString){
        HashMap<String,String> map=null;
        try {
            JSONArray jsonArray = new JSONArray(new JSONObject(jonString).getString("HeWeather data service 3.0"));
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            JSONObject now = jsonObject.getJSONObject("now");
            JSONObject now_cond = now.getJSONObject("cond");
            String now_cond_txt= now_cond.getString("txt");
            Log.i("HeWeather","now_cond_txt ="+now_cond_txt+"\n");//当天的天气2

            String now_tmp = now.getString("tmp");
            Log.i("HeWeather","now_tmp 当天的温度="+now_tmp+"\n");//当天的温度1


            JSONObject aqi = jsonObject.getJSONObject("aqi");
            JSONObject aqi_city = aqi.getJSONObject("city");
            String aqi_city_qlty = null;
            try {
                aqi_city_qlty = aqi_city.getString("qlty");
            } catch (JSONException e) {
                if (aqi_city_qlty==null||aqi_city_qlty=="")//原来这里没有数据，就立刻发生异常，下面的代码没有执行，try，catch让下面的代码能够执行
                    aqi_city_qlty = "无";
                e.printStackTrace();
            }
            Log.i("HeWeather","aqi_city_qlty 当天的空气质量="+aqi_city_qlty);//当天的空气质量3

            //城市名字
            JSONObject basic = jsonObject.getJSONObject("basic");
            String basic_city = basic.getString("city");

            map= new HashMap<String,String>();
            map.put("now_tmp",now_tmp);
            map.put("now_cond_txt",now_cond_txt);
            map.put("aqi_city_qlty",aqi_city_qlty);
            map.put("city",basic_city);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
    String getStackTrace(Throwable e) {
        if (e == null) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        str.append(e.getMessage()).append("\n");
        for (int i = 0; i < e.getStackTrace().length; i++) {
            str.append(e.getStackTrace()[i]).append("\n");
        }
        return str.toString();
    }
}
