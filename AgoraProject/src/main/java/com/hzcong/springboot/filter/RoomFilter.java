package com.hzcong.springboot.filter;

import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.SectionEntity;
import com.hzcong.springboot.service.Impl.SectionServiceImpl;
import com.hzcong.springboot.service.SectionService;
import com.hzcong.springboot.service.TaskService;
import com.hzcong.util.SpringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RoomFilter extends AccessControlFilter {


    @Autowired
    private SectionService sectionService;

    @Autowired
    private TaskService taskService;


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        Subject subject = getSubject(servletRequest, servletResponse);
        String userName = (String)subject.getPrincipal();
        String secId = httpServletRequest.getParameter("secId");

        if(userName==null || secId==null){
            httpServletResponse.sendRedirect("/errorPage/RoomFilterRefused");
            return false;
        }

        System.out.println("secId="+secId+" userName="+userName);

       if (sectionService == null) {
            sectionService = (SectionServiceImpl) SpringUtils.getBean("sectionServiceImpl");
        }
        if (taskService == null) {
            taskService = (TaskService) SpringUtils.getBean("taskServiceImpl");
        }


        SectionEntity section = sectionService.getSectionById(secId);
        if(section==null){   //不存在该课程
            httpServletResponse.sendRedirect("/errorPage/NoSection");
            return false;
        }else if(section.isProhibited()){  // 该课程被禁播
            httpServletResponse.sendRedirect("/errorPage/RoomProhibited");
            return false;
        }


        boolean isTeacherOfTheRoom = sectionService.existsSectionBySecIdAndTeaName(secId,userName);
        boolean isStudentOfTheRoom = taskService.existsTaskBySecIdAndStuName(secId,userName);
        boolean isAdmin = subject.hasRole(SystemConstants.ADMIN_SIGN);

        if(!isTeacherOfTheRoom && !isStudentOfTheRoom && !isAdmin){
            httpServletResponse.sendRedirect("/errorPage/RoomFilterRefused");
            return false;
        }
        return true;
    }
}
