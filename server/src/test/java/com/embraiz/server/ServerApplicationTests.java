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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServerApplicationTests {
    @PersistenceContext
    private EntityManager em;

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
}
