package com.embraiz.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class ServerApplicationTests {
    @persistenContext
    private EntityManager em;

    @Test
    void AutoCreate() {
        Query query = em.createNativeQuery("select xxxxx");
        List<HashMap> map  = ((Query) query).getResultList();
        StringBuffer output = new StringBuffer();
        for(xxxx){
            if(map[0]=="varchar"){
                output.append("@Column(name....,.");
            }
        }
        System.out.println(output);
    }

}
