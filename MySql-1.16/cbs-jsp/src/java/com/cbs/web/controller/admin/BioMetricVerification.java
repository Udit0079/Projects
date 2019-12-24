/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.entity.CbsUserBiometricTemplate;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.VerifyReqData;
import com.cbs.web.pojo.VerifyResData;
import com.cbs.web.utils.Util;
import com.google.gson.Gson;
import com.hrms.web.utils.WebUtil;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 *
 * @author root
 */
public class BioMetricVerification extends BaseBean {

    public BioMetricVerification() {
        try {
            beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            setTextMsg("Please put your finger on Biometric Device and wait for some time. Please do not Refresh the page.......");
            if (getUserName().equals("")) {
                throw new ApplicationException("userId does not exsit.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object thumbVerification() {
        try {
            if (gSon == null) {
                gSon = new Gson();
            }

            List<CbsUserBiometricTemplate> dataList = beanRemote.getBiometricDetails(getUserName());
            //byte[] isoT = readFile("/root/Desktop/MyWork/FingerPrint/MFS100_Linux_SDK_9.0.2.2/Sample/MANTRA.MFS100.Test/FingerData/ISOTemplate.iso");
            // byte[] isoT = readFile("/backup/FingerData/ISOTemplate.iso");

            List<byte[]> tmptList = new ArrayList<byte[]>();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Biometric does not registered for this user");
            }

            for (CbsUserBiometricTemplate obj : dataList) {
                tmptList.add(obj.getIsotemplate());
            }
            VerifyReqData data = new VerifyReqData();
            data.setQuality(60);

            data.setTimeOut(10000);
            data.setIsoTemplates(tmptList);

            String result = Util.postMFS100Client(WebUtil.getClientIP(this.getHttpRequest()), "match", gSon.toJson(data));

            VerifyResData dataObj = gSon.fromJson(result, VerifyResData.class);
            if (dataObj.getErrorCode() != 0) {
                throw new ApplicationException(dataObj.getErrorDescription());
            }
            if (!dataObj.isStatus()) {
                beanRemote.updateFailedLoginStatus(getUserName());
                throw new ApplicationException("Biometric authentication failed");
            }
            beanRemote.updateLoginTime(getUserName());
            getHttpResponse().sendRedirect("/cbs-jsp/faces/pages/admin/Welcome.jsp");
        } catch (Exception e) {
            try {
                getHttpSession().removeAttribute("userid");
                getHttpSession().removeAttribute("usrbr");
                
                SecurityContext context = (SecurityContext) getHttpSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
                if (context != null) {
                    Authentication authentication = context.getAuthentication();
                    if(authentication.isAuthenticated())authentication.setAuthenticated(false);
                }
                
                getHttpSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, e.getMessage());
                getHttpResponse().sendRedirect("/cbs-jsp/login.jsp?error=3");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static byte[] readFile(String filePath) {
        FileInputStream fileInputStream = null;

        File file = new File(filePath);

        byte[] bFile = new byte[(int) file.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

        } catch (Exception ex) {
            System.out.println("-------> " + ex.getMessage());
        }
        return bFile;
    }

    private String textMsg;

    Gson gSon;

    private final String jndiHomeName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanRemote = null;

    public String getTextMsg() {
        return textMsg;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }
}
