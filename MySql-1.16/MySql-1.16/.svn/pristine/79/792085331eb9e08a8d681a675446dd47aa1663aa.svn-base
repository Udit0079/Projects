package com.cbs.web.security;

import com.cbs.dto.UserDTO;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Dhirendra Singh
 */
public class UserAuthenticationService implements UserDetailsService {

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        try {
            List<UserDTO> users = getUserByUserName(userName);
            if (users.isEmpty()) {
                throw new UsernameNotFoundException("Username " + userName + " not found");
            }
            UserDTO userDTO = users.get(0);
            UserDetails user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.isEnabled(), userDTO.isAccountNonExpired(),
                    userDTO.isCredentialsNonExpired(), userDTO.isAccountNonLocked(), getUserRoles(userDTO.getRole()));
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    /**
     *
     * @param roleName
     * @return
     */
    public List<GrantedAuthority> getUserRoles(String roleName) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        GrantedAuthorityImpl authority = new GrantedAuthorityImpl(roleName);

        authorities.add(authority);

        return authorities;
    }

    /**
     *
     * @param userName
     * @return
     * @throws ServiceLocatorException
     * @throws ApplicationException
     * @throws UnknownHostException
     */
    public List<UserDTO> getUserByUserName(String userName) throws ServiceLocatorException, ApplicationException, UnknownHostException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        AdminManagementFacadeRemote beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
        return beanRemote.getUserByUsername(userName, Init.IP2BR.getBranch(InetAddress.getByName(WebUtil.getClientIP(req))));
    }
}
