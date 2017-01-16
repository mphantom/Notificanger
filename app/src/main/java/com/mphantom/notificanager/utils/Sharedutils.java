//package com.mphantom.notificanager.utils;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Build;
//
//import com.google.gson.Gson;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * Created by mphantom on 16/11/16.
// */
//
//public class Sharedutils {
//    public static final String SHARED_NAME = "default_date";
//    private static Sharedutils instance;
//    private SharedPreferences settings;
//    public static final int SHARE_MODEL;
//
//    private Sharedutils(Context context) {
//        this.settings = context.getApplicationContext()
//                .getSharedPreferences(SHARED_NAME, SHARE_MODEL);
//    }
//
//    public static Sharedutils getInstance(Context context) {
//        if (instance == null) {
//            instance = new Sharedutils(context);
//        }
//
//        return instance;
//    }
//
//    public void saveLong(String key, long value) {
//        this.apply(this.settings.edit().putLong(key, value));
//    }
//
//    public void saveFloat(String key, float value) {
//        this.apply(this.settings.edit().putFloat(key, value));
//    }
//
//    public float getFloat(String key) {
//        return this.settings.getFloat(key, -1.0F);
//    }
//
//
//    public long getLong(String key) {
//        return this.settings.getLong(key, -1L);
//    }
//
//    public void saveString(String key, String value) {
//        this.apply(this.settings.edit().putString(key, value));
//    }
//
//    public String getString(String key) {
//        return this.settings.getString(key, "");
//    }
//
//    public void saveInt(String key, int value) {
//        this.apply(this.settings.edit().putInt(key, value));
//    }
//
//    public int getInt(String key, int defaultValue) {
//        return this.settings.getInt(key, defaultValue);
//    }
//
//    public Double getOptDouble(String key) {
//        String retStr = this.settings.getString(key, (String) null);
//        Double ret = null;
//
//        try {
//            ret = Double.valueOf(Double.parseDouble(retStr));
//        } catch (Exception var5) {
//            ;
//        }
//
//        return ret;
//    }
//
//    public Boolean getOptBoolean(String key) {
//        String retStr = this.settings.getString(key, (String) null);
//        Boolean ret = null;
//
//        try {
//            ret = Boolean.valueOf(Boolean.parseBoolean(retStr));
//        } catch (Exception var5) {
//            ;
//        }
//
//        return ret;
//    }
//
//    public Double getDouble(String key) {
//        String retStr = this.settings.getString(key, (String) null);
//        Double ret = null;
//
//        try {
//            if (retStr != null) {
//                ret = Double.valueOf(Double.parseDouble(retStr));
//                return ret;
//            } else {
//                return null;
//            }
//        } catch (Exception var5) {
//            return null;
//        }
//    }
//
//    public void saveHashMap(String key, Map<String, String> map) {
//        JSONObject ret = new JSONObject(map);
//        this.apply(this.settings.edit().putString(key, ret.toString()));
//    }
//
//    public HashMap<String, String> getHashMapByKey(String key) {
//        HashMap ret = new HashMap();
//        String mapStr = this.settings.getString(key, "{}");
//        JSONObject mapJson = null;
//
//        try {
//            mapJson = new JSONObject(mapStr);
//        } catch (Exception var8) {
//            return ret;
//        }
//
//        if (mapJson != null) {
//            Iterator it = mapJson.keys();
//
//            while (it.hasNext()) {
//                String theKey = (String) it.next();
//                String theValue = mapJson.optString(theKey);
//                ret.put(theKey, theValue);
//            }
//        }
//
//        return ret;
//    }
//
//    public void saveBoolean(String key, boolean bool) {
//        this.apply(this.settings.edit().putBoolean(key, bool));
//    }
//
//    public boolean getBoolean(String key) {
//        return this.settings.getBoolean(key, false);
//    }
//
//    public boolean getBoolean(String key, boolean is) {
//        return this.settings.getBoolean(key, is);
//    }
//
//    public void saveArrayList(String key, ArrayList<String> list) {
//        this.apply(this.settings.edit().putString(key, (new Gson()).toJson(list)));
//    }
//
//    public ArrayList<String> getArrayList(String key) {
//        ArrayList ret = new ArrayList();
//        String listStr = this.settings.getString(key, "{}");
//        JSONArray listJson = null;
//
//        try {
//            listJson = new JSONArray(listStr);
//        } catch (Exception var7) {
//            return ret;
//        }
//
//        if (listJson != null) {
//            for (int i = 0; i < listJson.length(); ++i) {
//                String temp = listJson.optString(i);
//                ret.add(temp);
//            }
//        }
//
//        return ret;
//    }
//
//    public void removeByKey(String key) {
//        this.apply(this.settings.edit().remove(key));
//    }
//
//    public boolean contains(String alarmHour) {
//        return this.settings.contains(alarmHour);
//    }
//
//    static {
//        SHARE_MODEL = Build.VERSION.SDK_INT >= 24 ? 4 : 7;
//    }
//
//    @SuppressLint({"NewApi"})
//    public void apply(SharedPreferences.Editor editor) {
//        if (Build.VERSION.SDK_INT >= 9) {
//            editor.apply();
//        } else {
//            editor.commit();
//        }
//
//    }
//}
