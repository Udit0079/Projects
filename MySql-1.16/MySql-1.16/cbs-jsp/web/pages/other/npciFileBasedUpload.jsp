<%-- 
    Document   : npciFileBasedUpload
    Created on : 27 Jan, 2018, 12:59:05 PM
    Author     : root
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>NPCI File Type Upload</title>
        </head>
        <body>
            <h:form id="uploadForm" enctype="multipart/form-data" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NpciFileUploadBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="FileUpload"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NpciFileUploadBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{NpciFileUploadBean.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columnClasses="col15,col15,col15,col15" columns="4" width="100%" styleClass="row2">
                        <h:outputLabel id="lblFuntion" styleClass="label" value="File Type" ><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{NpciFileUploadBean.fileType}">
                            <f:selectItems value="#{NpciFileUploadBean.fileTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="fileuploadlbl" value="Upload File Path" styleClass="label"/>
                        <t:inputFileUpload id="file" value="#{NpciFileUploadBean.uploadedFile}" required="false"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Upload" action="#{NpciFileUploadBean.uploadProcess}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{NpciFileUploadBean.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{NpciFileUploadBean.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>  
            </h:form>
        </body>
    </html>
</f:view>