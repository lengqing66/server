package com.embraiz.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
//        String result = "{'userRoleId':1,'userId':1,'roleId':1,'roleName':'superAdmin','userName':'admin','nickName':'Administrator','email':'adminembraiz.com','mobilePhone':null,'directPhone':null,'address1':null,'address2':null,'title':null,'firstName':null,'lastName':null,'position':null,'isFirstLogin':0,'status':1,'isRemove':0,'activeDate':null}";
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        System.out.println(jsonObject.getString("roleName"));
//
//        //ObjectUtil objUtil = new ObjectUtil();
//        System.out.println(jsonObject.toJSONString(objUtil.checkConstraint("obj_user", result), true));

        Query query = em.createNativeQuery("SELECT COLUMN_NAME,DATA_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'dodo50_erp' AND TABLE_NAME = 'obj' order by column_key DESC");
        List list = query.getResultList();
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            if (i == 0) {
                output.append("@Id\n");
                output.append("@Column(name=" + object[0] + ")\n");
                output.append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                output.append("private " + object[1] + " " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
            } else {
                output.append("@Column(name=" + object[0] + ")\n");
                output.append("private " + object[1] + " " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, object[0].toString()) + ";\n\n");
            }
        }
        System.out.println(output);
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
    void SelectBySearchTest() {
        String str = "[{'name':'objId','value':'1','type':'number'},{'name':'objTitle','value':'Admin','type':'text'},{'name':'createDate1','value':'2016-03-27','type':'date'},{'name':'createData2','value':'2016-03-29','type':'date'}]";
//        String str = "[{'name':'I0','value':'2','type':'number'},{'name':'I1','value':'3','type':'number'}]";
        JSONArray jsonArray =JSONArray.parseArray(str);
//        for(int i=0;i<jsonArray.size();i++){
//            JSONObject obj=(JSONObject)jsonArray.get(i);
//            System.out.println(obj.getString("columnId"));
//        }
        sqlutil.SelectBySearch(jsonArray);
    }
}
