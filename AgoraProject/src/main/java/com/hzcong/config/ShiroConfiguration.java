package com.hzcong.config;
import com.hzcong.springboot.filter.*;
import com.hzcong.data.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class  ShiroConfiguration {

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }


    //将自己的验证方式加入容器
    @Bean
    public UserRealm myShiroRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }


    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }


    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置过滤器，默认的过滤器有authc、anon等
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("loginFilter", new LoginFilter());   //加入自定义过滤器
        filtersMap.put("adminFilter", new AdminFilter());
        filtersMap.put("teacherFilter", new TeacherFilter());
        filtersMap.put("roomFilter", new RoomFilter());
        filtersMap.put("sectionFilter", new SectionFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        //配置过滤规则: url:filter1[,[filter2[,……]]]。即一个url对应一条过滤链
        //所有url的过滤规则只以第一次设置的为准，后面的无效
        Map<String,String> map = new LinkedHashMap<String, String>();
        //以下几个页面可以以游客身份访问

//        /**
//         * 静态资源 anon
//         */
//        map.put("/css/**","anon");
//        map.put("/img/**","anon");
//        map.put("/js/**","anon");
//        map.put("/layui/**","anon");
//
//        /**
//         * 主页、登录、注册 anon
//         */
//        map.put("/index","anon");
//        map.put("/login","anon");
//        map.put("/register","anon");
//
//
//        /**
//         * 修改密码 anon
//         */
//        map.put("/setNewPassword","anon");
//        map.put("/resetPassword","anon");
//        map.put("/generateForgetMail","anon");
//        map.put("/changePassword","anon");
//        map.put("/executeChangePassword","anon");



        /**
         * 登录过滤器
         */
        map.put("/userHome","loginFilter");
        map.put("/history","loginFilter");
        map.put("/myCourse","loginFilter");
        map.put("/teacherAuthentication","loginFilter");



        /**
         * 开课  loginFilter teacherFilter
         */
        map.put("/giveCourse","loginFilter,teacherFilter");
        map.put("/addSection","loginFilter,teacherFilter");


        /**
         * section详细页
         */
        map.put("/getSection","sectionFilter");


        /**
         *  房间里面的内容 loginFilter roomFilter
         */
        map.put("/teacherRoom","loginFilter,roomFilter");
        map.put("/studentRoom","loginFilter,roomFilter");

        /**
         * 管理者 loginFilter adminFilter
         */
        map.put("/admin/enterAdmin","anon");
        map.put("/admin/adminLogin","anon");
        map.put("/admin/**","adminFilter");

        /**
         * 默认 loginFilter
         */





        //没有配置过滤规则的url，都默认经过loginFilter，需要把它放到最后，即过滤连的最后一个。
        map.put("/**","anon");
//        map.put("/**","myAuthenticationFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }


    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
