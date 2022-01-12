package com.magicalcoder.youyaboot.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.magicalcoder.youyaboot.core",
        "com.magicalcoder.youyaboot.service",
        "com.magicalcoder.youyaboot.admin"
        })
/*让过滤器WebFilter生效*/
@ServletComponentScan(basePackages = {
    "com.magicalcoder.youyaboot.admin.common.filter",
    "com.magicalcoder.youyaboot.admin.rmp.common.filter"
})
public class YouyaBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouyaBootAdminApplication.class, args);
    }

}
