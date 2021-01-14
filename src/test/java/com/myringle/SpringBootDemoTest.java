package com.myringle;

import com.myringle.config.Config;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;

//@Log4j2
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringBootDemoTest {

   public static final org.slf4j.Logger  log = org.slf4j.LoggerFactory.getLogger(SpringBootTest.class);

    @Test
    public void  test1(){
         ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Config config = (Config)context.getBean("zookeeperConfig");
        System.out.println(config.getName() + "<>" + config.getSources());
    }

    @Test
    public  void  test2(){
        Connection   c  =  null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("[ Opened database successfully ]");
    }

}
