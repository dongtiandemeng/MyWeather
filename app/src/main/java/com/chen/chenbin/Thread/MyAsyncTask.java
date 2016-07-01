package com.chen.chenbin.Thread;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.chen.chenbin.data.GetWeather;
import com.chen.chenbin.data.SaveGetWeatherInstance;
import com.chen.chenbin.myweather.MainActivity;
import com.chen.chenbin.myweather.SearchActivity;

/**
 * Created by chenbin on 2016/6/17.
 */
public class MyAsyncTask extends AsyncTask {
    private String query;
    private GetWeather getWeather;
    private SearchView searchView;
    private SearchActivity searchActivity;

    public MyAsyncTask(SearchActivity searchActivity,SearchView searchView,String query){
        this.query = query;
        this.searchView = searchView;
        this.searchActivity = searchActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Object[] params) {
        getWeather = new GetWeather();
        Log.i("mytime", "MyAsyncTask.class getWeather.apiTest(query)1");
        getWeather.apiTest(query);
        SaveGetWeatherInstance saveGetWeatherInstance = new SaveGetWeatherInstance();


        //已经返回数据才进行下一步操作
        if (saveGetWeatherInstance.getGetWeatherJsonString()!=null){
            if (getWeather.getErrorMessage()!=null){
                if ("unknown city".equals(getWeather.getErrorMessage())){
                    Toast.makeText(searchActivity, "你输入的城市名称错误，请重新输入", Toast.LENGTH_LONG).show();
                }else {
//                   SaveGetWeatherInstance saveGetWeatherInstance = new SaveGetWeatherInstance();
                    saveGetWeatherInstance.setGetWeather(getWeather);
                }
            }
//            MainActivity.cityLinkedList.add(query);
            Intent intent = new Intent();
            intent.putExtra("getData", true);
            searchActivity.setResult(searchActivity.RESULT_OK, intent);
            searchActivity.finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
//        if (!(boolean)o){
//            return;
//        }

//        if (getWeather.getErrorMessage()!=null){
//            if ("unknown city".equals(getWeather.getErrorMessage())){
//                Toast.makeText(searchActivity, "你输入的城市名称错误，请重新输入", Toast.LENGTH_LONG).show();
//            }else {
//                SaveGetWeatherInstance saveGetWeatherInstance = new SaveGetWeatherInstance();
//                saveGetWeatherInstance.setGetWeather(getWeather);
//            }
//        }
//        MainActivity.cityLinkedList.add(query);
//        Intent intent = new Intent();
//        intent.putExtra("getData", true);
//        searchActivity.setResult(searchActivity.RESULT_OK, intent);
//        searchActivity.finish();

    }
}
