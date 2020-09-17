package com.embraiz.server;

import com.embraiz.entity.server.ConfConstraint;
import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import com.embraiz.service.common.ObjectUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServerApplicationTests {
    @PersistenceContext
    private EntityManager em;

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

        Query query = em.createNativeQuery("SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'dodo50_erp' AND TABLE_NAME = 'obj'");
        List list = query.getResultList();
        Query type = em.createNativeQuery("SELECT DATA_TYPE FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'dodo50_erp' AND TABLE_NAME = 'obj'");
        List typeList = ((Query) type).getResultList();
        Query id = em.createNativeQuery("SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'dodo50_erp' AND TABLE_NAME= 'obj' AND COLUMN_KEY = 'PRI'");
        List idList = ((Query) id).getResultList();
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(idList.get(0))) {
                output.append("@Id\n");
                output.append("@Column(name=" + list.get(i) + ")\n");
                output.append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                output.append("private " + typeList.get(i) + " " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,list.get(i).toString()) + ";\n\n");
            } else {
                output.append("@Column(name=" + list.get(i) + ")\n");
                output.append("private " + typeList.get(i) + " " + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,list.get(i).toString()) + ";\n\n");
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
}
