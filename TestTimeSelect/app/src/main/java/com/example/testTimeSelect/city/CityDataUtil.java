package com.example.testTimeSelect.city;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.testTimeSelect.bean.AreaBean;
import com.example.testTimeSelect.bean.CityBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lipiao
 * 省
 * 城市数据初始化工具类
 */
public class CityDataUtil {
    private Activity activity;

    private List<CityBean> provinceList;

    public CityDataUtil(Activity activity) {
        this.activity = activity;
        provinceList = JSON.parseArray(readTestJson(activity, "area.json"), CityBean.class);
    }

    /**
     * 所有省
     */
    public String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    public Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * 当前省的名称
     */
    public String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    public String mCurrentCityName;

    public String PCName = "";

    public String LOCode = "";

    public List<CityBean> getProvinceList() {
        return provinceList;
    }

    /**
     * 根据code获取城市名
     *
     * @param pid
     * @param cid
     * @return
     */
    public String codeGetString(String pid, String cid) {
        if (provinceList.size() != 0) {
            for (CityBean bean : provinceList) {
                if (bean.getAreaCode().equals(pid)) {
                    if (bean.getZones().size() == 0) {
                        PCName = bean.getAreaName() + " ";
                    } else {
                        for (AreaBean areaBean : bean.getZones()) {
                            if (areaBean.getAreaCode().equals(cid)) {
                                PCName = bean.getAreaName() + " " + areaBean.getAreaName();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return PCName;
    }

    /**
     * 根据城市名获取code值
     *
     * @param pidName
     * @param cidName
     * @return
     */
    public String stringGetCode(String pidName, String cidName) {
        if (provinceList != null && !provinceList.isEmpty()) {
            for (CityBean bean : provinceList) {
                if (bean.getAreaName().equals(pidName)) {
                    if (bean.getZones().size() == 0) {
                        LOCode = bean.getAreaCode() + " ";
                    } else {
                        for (AreaBean areaBean : bean.getZones()) {
                            if (areaBean.getAreaName().equals(cidName)) {
                                LOCode = bean.getAreaCode() + " " + areaBean.getAreaCode();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return LOCode;
    }

    /**
     * 解析省市区的XML数据
     */
    public void initProvinceDatas() {
        //*/ 初始化默认选中的省、市、区
        if (provinceList != null && !provinceList.isEmpty()) {
            mCurrentProviceName = provinceList.get(0).getAreaName();
            List<AreaBean> cityList = provinceList.get(0).getZones();
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getAreaName();
            }
        }
        mProvinceDatas = new String[provinceList.size()];
        for (int i = 0; i < provinceList.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provinceList.get(i).getAreaName();
            List<AreaBean> cityList = provinceList.get(i).getZones();
            String[] cityNames = new String[cityList.size()];
            for (int j = 0; j < cityList.size(); j++) {
                // 遍历省下面的所有市的数据
                cityNames[j] = cityList.get(j).getAreaName();
            }
            // 省-市的数据，保存到mCitisDatasMap
            mCitisDatasMap.put(provinceList.get(i).getAreaName(), cityNames);
        }
    }


    /**
     * 读取本地json文件
     *
     * @param ctx
     * @param file
     * @return
     */
    public static String readTestJson(Context ctx, String file) {
        StringBuilder buf = new StringBuilder();
        InputStream json = null;
        BufferedReader in = null;
        try {
            json = ctx.getAssets().open(file);
            in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str = null;

            while ((str = in.readLine()) != null) {
                buf.append(str);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buf.toString();
    }

}
