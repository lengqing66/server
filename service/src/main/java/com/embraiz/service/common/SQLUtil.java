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
        StringBuffer sql = new StringBuffer("select * from " + tableName + " where 1=1");
        List<Object> param = new ArrayList<>();
        boolean isStartDate = true;
        for (int i = 0; i < jsonArray.size(); i++) {//判断传入json数据包含哪些类型
            JSONObject obj = (JSONObject) jsonArray.get(i);
            if (obj.getString("type").equals("number")) {
                sql.append(" and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + "=?");
                param.add(obj.getString("value"));
            } else if (obj.getString("type").equals("text")) {
                sql.append(" and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " like ?");
                param.add(obj.getString("value"));
            } else if (obj.getString("type").equals("date")) {
                if (isStartDate == true) {
                    sql.append(" and " + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, obj.getString("name")) + " between ?");
                    param.add(obj.getString("value"));
                    isStartDate = false;
                } else {
                    sql.append(" and ?");
                    param.add(obj.getString("value"));
                    isStartDate = true;
                }
            }
        }
        Query query = em.createNativeQuery(sql.toString());
        for (int i = 0; i < param.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            if (obj.getString("type").equals("text")) {
                query.setParameter(i + 1, "%" + param.get(i) + "%");
            } else {
                query.setParameter(i + 1, param.get(i));
            }
        }
        List list = query.getResultList();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            for (int j = 0; j < object.length; j++) {
                System.out.println(i + "." + j + " : " + object[j]);
            }
        }
    }
}
