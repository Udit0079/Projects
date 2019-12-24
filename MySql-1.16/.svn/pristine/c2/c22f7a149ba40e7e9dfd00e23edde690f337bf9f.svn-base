/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.security;

import com.cbs.constant.RoleTypes;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Dhirendra Singh
 */
public class UserDetailsValidator implements UserDetailsChecker {

    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dd = new SimpleDateFormat("dd/MM/yyyy");
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    AdminManagementFacadeRemote beanRemote;

    public void check(UserDetails user) {
        try {
            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            req.getSession().setAttribute("userid", user.getUsername());
            beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
            
            if (CbsUtil.dayDiff(ymd.parse(beanRemote.getDBCurDate()), ymd.parse(ymd.format(new Date()))) != 0) {
                throw new ApplicationException("Date of Application Server and Database server is different. Contact to your Administrator");
            }
            String workingDt = beanRemote.getBankWorkingDate();
            if (!getUserRole(user).equals(RoleTypes.ROLE_DBA.getValue()) && !getUserRole(user).equals(RoleTypes.ROLE_SUPER_USER.getValue())) {
                
                if (CbsUtil.dayDiff(ymd.parse(workingDt), ymd.parse(ymd.format(new Date()))) != 0) {
                    throw new ApplicationException("Date denied. Current bank working day is " + dd.format(ymd.parse(workingDt)));
                }
                
                if (getUserRole(user).equals(RoleTypes.ROLE_SYS_ADMIN.getValue()) || getUserRole(user).equals(RoleTypes.ROLE_OFFICER.getValue())
                || getUserRole(user).equals(RoleTypes.ROLE_CLERK.getValue())) {
            
                    workingDt = beanRemote.getBranchWorkingDate(Init.IP2BR.getBranch(InetAddress.getByName(WebUtil.getClientIP(req))));
                    if (CbsUtil.dayDiff(ymd.parse(workingDt), ymd.parse(ymd.format(new Date()))) != 0) {
                        throw new DateValidationException("Date denied. Current branch working day is " + dd.format(ymd.parse(workingDt)), user);
                    }
                }
            }else{
                if (getUserRole(user).equals(RoleTypes.ROLE_SUPER_USER.getValue()) && (CbsUtil.dayDiff(ymd.parse(workingDt), ymd.parse(ymd.format(new Date()))) > 0)) {
                    throw new ApplicationException("Date denied. Current bank working day is " + dd.format(ymd.parse(workingDt)));
                }
            }
        } catch (Exception ex) {
            throw new DateValidationException(ex.getMessage(), user);
        }
                
        if (!user.isEnabled()) {
            throw new DisabledException("User id is inactive", user);
        }

        if (!user.isAccountNonLocked()) {
            throw new LockedException("Please verify login status", user);
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("User id is deleted or canceled", user);
        }
    }

    private String getUserRole(UserDetails user) {
        Iterator itr = user.getAuthorities().iterator();
        String role = "";
        while (itr.hasNext()) {
            GrantedAuthority ga = (GrantedAuthority) itr.next();
            role = ga.getAuthority();
            break;
        }
        return role;
    }
}
