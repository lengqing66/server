package com.embraiz.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.embraiz.entity.base.SearchMap;
import com.embraiz.service.common.SQLUtil;
import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.embraiz.service.common.ObjectUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServerApplicationTests {
    @PersistenceContext
    private EntityManager em;

    @Resource
    private SQLUtil sqlutil;

    @Autowired
    private ObjectUtil objUtil;

    @Test
    void contextLoads() {
        String result = "{'userRoleId':1,'userId':1,'roleId':1,'roleName':'superAdmin','userName':'admin','nickName':'Administrator','email':'adminembraiz.com','mobilePhone':null,'directPhone':null,'address1':null,'address2':null,'title':null,'firstName':null,'lastName':null,'position':null,'isFirstLogin':0,'status':1,'isRemove':0,'activeDate':null}";
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(jsonObject.getString("roleName"));

        //ObjectUtil objUtil = new ObjectUtil();
        System.out.println(jsonObject.toJSONString(objUtil.checkConstraint("obj_user", result), true));
    }

    /*
    @Test
    void generateEntity(){
        em.createQuery("xxxxxxx");
        List<String> columnString =  query.getResultList();
        for (String conf :confs ){
            System.out.prinln("  @Column(name = \"obj_parent_id\")\n" +
                    "    private Integer objParentId;");
        }

    }
    */

    @Test
    void autoCreate() {
//        String tableName="obj";
        String tableName="vUserRole";
        Query query = em.createNativeQuery("SELECT COLUMN_NAME,DATA_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'dodo50_erp' AND TABLE_NAME = '"+CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tableName)+"' order by column_key DESC");
        List list = query.getResultList();
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            if (i == 0) {
                output.append("@Id\n");
                output.append("@Column(name=\"" + object[0] + "\")\n");
                output.append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                if(object[1].equals("int")||object[1].equals("tinyint")||object[1].equals("smallint")) {
                    output.append("private Integer " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
                }else if(object[1].equals("varchar")){
                    output.append("private String " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
                }else if(object[1].equals("timestamp")||object[1].equals("datetime")){
                    output.append("private Date " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
                }
            } else {
                output.append("@Column(name=\"" + object[0] + "\")\n");
                if(object[1].equals("int")||object[1].equals("tinyint")||object[1].equals("smallint")) {
                    output.append("private Integer " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
                }else if(object[1].equals("varchar")){
                    output.append("private String " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
                }else if(object[1].equals("timestamp")||object[1].equals("datetime")){
                    output.append("private Date " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
                }
            }
        }
        System.out.println(output);
    }

    @Test
    void SelectBySearchTest() {
//        String str = "[{'name':'objId','value':'1','type':'number'},{'name':'objTitle','value':'Admin','type':'text'},{'name':'createDate','value':'2016-03-27','type':'date'},{'name':'createDate','value':'2016-03-29','type':'date'}]";
        String str = "[{'name':'l0','value':'2','type':'number'},{'name':'objTitle','value':'Admin','type':'text'},{'name':'createDate','value':'2016-03-27','type':'date'},{'name':'createDate','value':'2016-03-30','type':'date'},{'name':'l1','value':'3','type':'number'},{'name':'status','value':'1','type':'text'},{'name':'l2','value':'3','type':'date'},{'name':'l2','value':'6','type':'date'}]";
//        String str = "[{'name':'createDate','value':'2016-03-27','type':'date'},{'name':'createDate','value':'2016-03-29','type':'date'},{'name':'l2','value':'3','type':'date'},{'name':'l2','value':'6','type':'date'}]";
        JSONArray jsonArray =JSONArray.parseArray(str);
        sqlutil.SelectBySearch(jsonArray,"obj");
    }
}
