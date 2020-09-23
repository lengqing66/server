package com.embraiz.service.common;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class SQLUtil {
    @PersistenceContext
    private EntityManager em;

    public void SelectBySearch(JSONArray jsonArray) {
        boolean isNumber = false;
        boolean isText = false;
        boolean isDate = false;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            if (obj.getString("type").equals("number")) {
                isNumber = true;
            } else if (obj.getString("type").equals("text")) {
                isText = true;
            } else if (obj.getString("type").equals("date")) {
                isDate = true;
            }
        }
        String sql = "";
        if (isNumber == true) {
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject obj = (JSONObject) jsonArray.get(i);
//                if (obj.getString("type").equals("number")) {
//                    System.out.println(obj.getString("type"));
//                }
//            }
            sql += " where l2 = 0";
            //select obj_id from obj where obj_parent_id=6;
        }
        if (isText == true) {
            if(isNumber == false) {
                sql += " where obj_desc like '%Admin%'";
            }else {
                sql += " and obj_desc like '%Admin%'";
            }
            //select obj_id from obj where obj_title like '%user%';
        }
        if (isDate == true) {
            if(isNumber == false&&isText==false) {
                sql += " where create_date between '2016-03-28 16:13:09' and '2016-03-28 16:13:11'";
            }else {
                sql += " and create_date between '2016-03-28 16:13:09' and '2016-03-28 16:13:11'";
            }
            //select obj_id from obj where create_date between '2016-03-29' and '2016-03-30';
        }
        System.out.println(sql);
//        Query query = em.createNativeQuery("select * from "+a+" where "+b+"="+c);
        Query query = em.createNativeQuery("select * from obj"+sql);
        List list = query.getResultList();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            System.out.println(i+" : "+list.get(i));
            for(int j=0;j<object.length;j++){
                System.out.println(j+" : "+object[j]);
            }
        }

    }

}
