package com.embraiz.service.common;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Service
public class SQLUtil {
    @PersistenceContext
    private EntityManager em;

    public void SelectBySearch(JSONArray jsonArray, String tableName) {
//        boolean isNumber = false;//是否包括number，sql语句中用=
//        boolean isText = false;//是否包含text，sql语句中用like %...%
//        boolean isDate = false;//是否包含date，sql语句中用between...and...
//        boolean isFirstNum = true;//是否为第一个number，直接判断
//        boolean isFirstText = true;//是否为第一个text，在不存在number时进行判断
//        boolean isFirstDate = true;//是否为第一个date，在不存在number和text时进行判断
//        for (int i = 0; i < jsonArray.size(); i++) {//判断传入json数据包含哪些类型
//            JSONObject obj = (JSONObject) jsonArray.get(i);
//            if (obj.getString("type").equals("number")) {
//                isNumber = true;
//            } else if (obj.getString("type").equals("text")) {
//                isText = true;
//            } else if (obj.getString("type").equals("date")) {
//                isDate = true;
//            }
//        }
//        String sql = "";//sql语句的判断条件
//        if (isNumber == true) {
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject obj = (JSONObject) jsonArray.get(i);
//                if (obj.getString("type").equals("number")) {
//                    if (isFirstNum == true) {
//                        sql += " where " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + "=" + obj.getString("value");
//                        isFirstNum = false;
//                    } else {
//                        sql += " and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + "=" + obj.getString("value");
//                    }
//                }
//            }
//        }
//        if (isText == true) {
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject obj = (JSONObject) jsonArray.get(i);
//                if (obj.getString("type").equals("text")) {
//                    if (isNumber == false) {
//                        if (isFirstText == true) {
//                            sql += " where " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " like '%" + obj.getString("value") + "%'";
//                            isFirstText = false;
//                        } else {
//                            sql += " and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " like '%" + obj.getString("value") + "%'";
//                        }
//                    } else {
//                        sql += " and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " like '%" + obj.getString("value") + "%'";
//                    }
//                }
//            }
//        }
//        if (isDate == true) {
//            String Date = null;
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject obj = (JSONObject) jsonArray.get(i);
//                if (obj.getString("type").equals("date")) {
//                    if (isNumber == false && isText == false) {
//                        if (isFirstDate == true) {
//                            if (Date == null) {
//                                sql += " where " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " between '" + obj.getString("value") + "'";
//                                Date = obj.getString("name");
//                            } else if (Date.equals(obj.getString("name"))) {
//                                sql += " and '" + obj.getString("value") + "'";
//                                Date = null;
//                                isFirstDate = false;
//                            }
//                        } else {
//                            if (Date == null) {
//                                sql += " and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " between '" + obj.getString("value") + "'";
//                                Date = obj.getString("name");
//                            } else if (Date.equals(obj.getString("name"))) {
//                                sql += " and '" + obj.getString("value") + "'";
//                                Date = null;
//                            }
//                        }
//                    } else {
//                        if (Date == null) {
//                            sql += " and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " between '" + obj.getString("value") + "'";
//                            Date = obj.getString("name");
//                        } else if (Date.equals(obj.getString("name"))) {
//                            sql += " and '" + obj.getString("value") + "'";
//                            Date = null;
//                        }
//                    }
//                }
//            }
//        }
//        Query query = em.createNativeQuery("select * from obj" + sql);
//        List list = query.getResultList();
//        for (int i = 0; i < list.size(); i++) {
//            Object[] object = (Object[]) list.get(i);
//            System.out.println(i + " : " + list.get(i));
//            for (int j = 0; j < object.length; j++) {
//                System.out.println(j + " : " + object[j]);
//            }
//        }
        StringBuffer sql = new StringBuffer("select * from " + tableName + " where 1=1");
        List<Object> param = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {//判断传入json数据包含哪些类型
            JSONObject obj = (JSONObject) jsonArray.get(i);
            if (obj.getString("type").equals("number")) {
                sql.append(" and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + "=?");
                param.add(obj.getString("value"));
            }
            else if (obj.getString("type").equals("text")) {
                sql.append(" and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " like '%?%'");
                param.add(obj.getString("value"));
            }
//            else if (obj.getString("type").equals("date")) {
//                sql.append(" and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + "between ? and ?");
//                param.add(obj.getString("value"));
//            }
        }
        System.out.println(sql);
        Query query = em.createNativeQuery(sql.toString());
        System.out.println(param.get(0));
        System.out.println(param.get(1));
        System.out.println(param.get(2));
        System.out.println(param.get(3));
        query.setParameter(1, param.get(0));
        System.out.println("1y");
        query.setParameter(2, param.get(1));
        System.out.println("2y");
        query.setParameter(3, param.get(2));
        System.out.println("3y");
        query.setParameter(4, param.get(3));
        System.out.println("4y");
//        for (int i = 0; i < param.size(); i++) {
//            System.out.println(param);
//            System.out.println(param.get(i));
//            query.setParameter(i + 1, param.get(i));
//        }
        List list = query.getResultList();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            System.out.println(i + " : " + list.get(i));
            for (int j = 0; j < object.length; j++) {
                System.out.println(j + " : " + object[j]);
            }
        }
    }

}
