package com.hzcong.springboot.filter;

import com.hzcong.config.SystemConstants;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TeacherFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        Subject subject = getSubject(servletRequest, servletResponse);
        if(!subject.hasRole(SystemConstants.TEACHER_SIGN)){
            httpServletResponse.sendRedirect("/errorPage/TeacherFilterRefused");
            return false;
        }
        return true;
    }
}
