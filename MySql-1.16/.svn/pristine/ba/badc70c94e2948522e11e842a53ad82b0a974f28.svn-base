<%-- 
    Document   : biometricReg
    Created on : 14 Dec, 2016, 5:35:48 PM
    Author     : Dhirendra Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Biometric Registration</title>
        </head>
        <body>
            <a4j:form id="form1" prependId="false">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BioMetricReg.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Biometric Registration"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BioMetricReg.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsgReporting"   width="100%"   style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id="errorGrid" value="#{BioMetricReg.message}" style="color:red" styleClass="output"/>
                    </h:panelGrid>
                    <h:panelGrid id="dataPanel" columnClasses="col10,col11" columns="2" width="100%">
                        <h:panelGrid id="leftPanel" columns="1" width="100%">
                            <h:panelGrid id="instruction" columns="1" style="height:30px;width:100%"  styleClass="row2" width="100%">
                                <h:outputLabel id="lblIst" styleClass="msg" value="User can register minimum 1 and maximum 4 Fingers."/>
                            </h:panelGrid>
                            <h:panelGrid id="userData" columnClasses="col7,col12" columns="2" style="height:30px;width:100%"  styleClass="row2" width="100%">
                                <h:outputLabel id="lblUsrId" styleClass="label" value="UserId :">
                                    <font class="required" style="color:red;">*</font>
                                </h:outputLabel>
                                <h:inputText  id="input1" styleClass="input" value="#{BioMetricReg.userId}"  maxlength="30" style="width:120px" disabled="#{BioMetricReg.txtDisable}">
                                    <a4j:support event="onblur" actionListener="#{BioMetricReg.getUserRegistration}" reRender="errorGrid,dataPanel,regConfirmAlert,saveUpdate" 
                                                 oncomplete="if(#{BioMetricReg.isRegistered}) #{rich:component('regConfirmAlert')}.show();"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="userData1" columnClasses="col7,col12" columns="2" style="height:30px;width:100%" styleClass="row1" width="100%">       
                                <h:outputLabel id="lblName" styleClass="label" value="User Name :"/>
                                <h:outputText id="usrName" styleClass="output" value="#{BioMetricReg.name}" style="width:120px"/>
                            </h:panelGrid>
                            <h:panelGrid id="userData2" columnClasses="col7,col12" columns="2" style="height:30px;width:100%" styleClass="row2" width="100%">
                                <h:outputLabel id="lblRole" styleClass="label" value="User Role :"/>
                                <h:outputText id="usrRole" styleClass="output" value="#{BioMetricReg.role}" style="width:120px"/>
                            </h:panelGrid>
                            <h:panelGrid id="userData3" columnClasses="col7,col12" columns="2" style="height:30px;width:100%" styleClass="row1" width="100%">
                                <h:outputLabel id="lblAdd" styleClass="label" value="Address :"/>
                                <h:outputText id="usrAdd" styleClass="output" value="#{BioMetricReg.address}" style="width:120px"/>
                            </h:panelGrid>
                            <h:panelGrid id="userData4" columnClasses="col7,col12" columns="2" style="height:30px;width:100%" styleClass="row2" width="100%">
                                <h:outputLabel id="lblBrn" styleClass="label" value="Operational Branch :"/>
                                <h:outputText id="usrBranch" styleClass="output" value="#{BioMetricReg.brName}" style="width:120px"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="rightPanel" columns="1" width="100%">
                            <h:panelGrid id="gpFingerImage" columns="8" columnClasses="col33,col0,col33,col0,col33,col0,col33,col0" styleClass="row2" width="100%" style="height:200px">
                                <h:outputLabel id="lblfgr1" styleClass="msg" value="Finger # 1"/>
                                <h:outputLabel />
                                <h:outputLabel id="lblfgr2" styleClass="msg" value="Finger # 2"/>
                                <h:outputLabel />
                                <h:outputLabel id="lblfgr3" styleClass="msg" value="Finger # 3"/>
                                <h:outputLabel />
                                <h:outputLabel id="lblfgr4" styleClass="msg" value="Finger # 4"/>
                                <h:outputLabel />
                                <h:panelGroup layout="block" id="imgPanel1" styleClass="img" >
                                    <h:graphicImage id="fingerImg1" alt="Finger Image One" value="#{BioMetricReg.imageDataOne}" width="120px" height="150px"/>
                                </h:panelGroup>
                                <h:outputLabel />
                                <h:panelGroup layout="block" id="imgPanel2" styleClass="img" >
                                    <h:graphicImage id="fingerImg2" alt="Finger Image Two" value="#{BioMetricReg.imageDataTwo}" width="120px" height="150px"/>
                                </h:panelGroup>
                                <h:outputLabel />
                                <h:panelGroup layout="block" id="imgPanel3" styleClass="img" >
                                    <h:graphicImage id="fingerImg3" alt="Finger Image Three" value="#{BioMetricReg.imageDataThree}" width="120px" height="150px"/>
                                </h:panelGroup>
                                <h:outputLabel />
                                <h:panelGroup layout="block" id="imgPanel4" styleClass="img" >
                                    <h:graphicImage id="fingerImg4" alt="Finger Image Four" value="#{BioMetricReg.imageDataFour}" width="120px" height="150px"/>
                                </h:panelGroup>
                                <h:outputLabel />
                                <a4j:commandButton id="userReg1" value="Capture" action="#{BioMetricReg.captureFingerOne}" reRender="gpFingerImage,fingerImg,errorGrid"/>
                                <h:outputLabel />
                                <a4j:commandButton id="userReg2" value="Capture" action="#{BioMetricReg.captureFingerTwo}" reRender="gpFingerImage,fingerImg,errorGrid"/>
                                <h:outputLabel />
                                <a4j:commandButton id="userReg3" value="Capture" action="#{BioMetricReg.captureFingerThree}" reRender="gpFingerImage,fingerImg,errorGrid"/>
                                <h:outputLabel />
                                <a4j:commandButton id="userReg4" value="Capture" action="#{BioMetricReg.captureFingerFour}" reRender="gpFingerImage,fingerImg,errorGrid"/>
                                <h:outputLabel />
                            </h:panelGrid>      
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer"> 
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton id="saveUpdate" value="#{BioMetricReg.btnLabel}" action="#{BioMetricReg.saveUpdateBiometricDetails}" reRender="gpFingerImage,fingerImg,errorGrid,leftPanel"/>
                            <a4j:commandButton id="btnRefresh"  value="Refresh" action="#{BioMetricReg.refreshPage}" reRender="mainPanel" disabled="#{BioMetricReg.txtDisable}"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{BioMetricReg.exit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>                        
            <a4j:region>
                <rich:modalPanel id="regConfirmAlert" autosized="true" width="310" onshow="">
                    <f:facet name="header">
                        <h:outputText value="Biometric Registration Confirmation" style="padding-right:15px;font-size:15px;font-weight:bold;font-size:15px" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:justify;font-weight:bold;">
                            <tbody>
                                <tr style="height:60px">
                                    <td>
                                        <h:outputText value="#{BioMetricReg.confirmationMsg}" style="color:blue;font-weight:bold;font-size:13px"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{BioMetricReg.getUserDetails}" reRender="errorGrid,dataPanel,regConfirmAlert" onclick="#{rich:component('regConfirmAlert')}.hide()"/>
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('regConfirmAlert')}.hide()" reRender="regConfirmAlert"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>                        
        </body>
    </html>
</f:view>