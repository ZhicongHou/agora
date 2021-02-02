package com.hzcong.springboot.filter;

import com.hzcong.data.entities.SectionEntity;
import com.hzcong.springboot.service.Impl.SectionServiceImpl;
import com.hzcong.springboot.service.SectionService;
import com.hzcong.util.SpringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SectionFilter extends AccessControlFilter {

    @Autowired
    private SectionService sectionService;


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        String secId = httpServletRequest.getParameter("secId");

        if (sectionService == null) {
            sectionService = (SectionServiceImpl) SpringUtils.getBean("sectionServiceImpl");
        }

        SectionEntity section = sectionService.getSectionById(secId);
        if(section==null){   //不存在该课程
            httpServletResponse.sendRedirect("/errorPage/NoSection");
            return false;
        }
        return true;
    }
}
