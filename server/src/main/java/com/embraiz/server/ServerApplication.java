package com.embraiz.server;

import com.embraiz.server.config.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = "com.embraiz")
@EntityScan("com.embraiz.entity")
@EnableJpaRepositories(basePackages = "com.embraiz.repository", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableTransactionManagement
@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
