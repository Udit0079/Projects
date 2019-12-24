<%-- 
    Document   : signatureupload
    Created on : Dec 13, 2011, 4:31:43 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Signature Upload Processing</title>
        </head>
        <body>
            <h:form id="uploadSignature" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{signatureupload.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="FileUpload"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{signatureupload.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{signatureupload.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="successMsg" style="height:1px;text-align:center" styleClass="msg" width="100%">
                        <h:outputText id="succMsg" value="#{signatureupload.successMsg}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel1" width="100%" styleClass="row2">
                        <h:outputLabel value="Classification" id="classificationLabel" styleClass="label"/>
                        <h:selectOneListbox id="classification" value="#{signatureupload.classification}" styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems value="#{signatureupload.classificationList}" />
                            <a4j:support  action="#{signatureupload.setDisableTrue}" event="onchange"  reRender="type,errmsg,succMsg,custId"/>                     
                        </h:selectOneListbox>

                        <h:outputLabel value="Customer Id" id="custIdLabel" styleClass="label"/>
                        <h:inputText id="custId" styleClass="input" style="width:100px" value="#{signatureupload.custId}" disabled="#{signatureupload.disableFlag}"  maxlength="12" >
                            <a4j:support  action="#{signatureupload.getTypeListValue}" event="onblur" focus="type" reRender="type,succMsg,errmsg"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel2" width="100%" styleClass="row1">
                        <h:outputLabel value="Type" id="typeLabel" styleClass="label"/>
                        <h:selectOneListbox id="type" value="#{signatureupload.aadType}" styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems value="#{signatureupload.addTypeList}"/>
                            <a4j:support  action="#{signatureupload.resetMsg}" event="onchange"  reRender="errmsg,succMsg"/> 
                        </h:selectOneListbox> 

                        <h:outputLabel id="fileuploadlbl1" value="Upload File Path" styleClass="label"/>
                        <t:inputFileUpload id="file1" value="#{signatureupload.uploadedFile}" required="false">
                        </t:inputFileUpload>   
                    </h:panelGrid>

                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Upload" action="#{signatureupload.uploadProcess}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{signatureupload.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{signatureupload.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
