package com.embraiz.service.common;

import com.alibaba.fastjson.JSONObject;
import com.embraiz.entity.server.ConfConstraint;
import com.embraiz.entity.server.Obj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import com.google.common.base.CaseFormat;

@Repository
public class ObjectUtil{
    @PersistenceContext
    private EntityManager em;

    public List<Obj> selectChild(Integer objId){
        //get the child list from parent
        List<Obj> objList = new ArrayList<Obj>();
        return objList;
    }

    public String criteriaBuilder(String inputStr){
        // helper to build the where clause for entity
        String output="";
        return output;
    }

    public void updateKeyword(String tbName){
        // update all keyword for this table
    }
    public void updateKeyword(String tbName, int id){
        // populate keyword to a particular record
    }
    public List<Message> checkConstraint(String tbName, String insertString){

        Query query =  em.createNativeQuery("select * from conf_constraint where tb_name=?1", ConfConstraint.class);
        query.setParameter(1, tbName);
        List<ConfConstraint> confs =  query.getResultList();
        JSONObject jsonObject = JSONObject.parseObject(insertString);

        List<Message> messageList = new ArrayList<Message>();


        for (ConfConstraint conf :confs ){
           String fieldNameDB = conf.getFieldName();
           String fieldNameJson = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldNameDB);
           String fieldValue =  jsonObject.getString(fieldNameJson);
           String check_type = conf.getCheckType();
           Message message = new Message();
           message.setCode(conf.getErrorCode());
           message.setMesage(conf.getErrorMessage());
           message.setFieldNamee(fieldNameJson);

           if(check_type.equals("null")){
               if(StringUtils.isEmpty(fieldValue)){
                   messageList.add(message);
               }
           }else if(check_type.equals("email")){
               String regex = "^(.+)@(.+)$";
               Pattern pattern = Pattern.compile(regex);
               if(!StringUtils.isEmpty(fieldValue)) {
                   if (!pattern.matcher(fieldValue).matches()) {
                       messageList.add(message);
                   }
               }
           }else if(check_type.equals("unique")){
               query =  em.createNativeQuery("select count(*) from "+tbName+" where "+fieldNameDB+"=?1");
               query.setParameter(1,fieldValue );
               int count = ((Number) query.getSingleResult()).intValue();
               if(count>0){
                   messageList.add(message);
               }
           }

        }

        return messageList;
    }


}
