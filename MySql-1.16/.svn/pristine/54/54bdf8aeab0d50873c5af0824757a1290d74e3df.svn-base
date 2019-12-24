<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
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
            <title>FileUpload Social Security Schemes</title>
        </head>
        <body>
            <h:form id="uploadForm" enctype="multipart/form-data" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{sssFileUpload.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="FileUpload"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{sssFileUpload.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{sssFileUpload.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel99" width="100%" styleClass="row2">
                        <h:outputLabel id="lblScheme" styleClass="label" value="Social Security Scheme"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddScheme" style="width: 180px" styleClass="ddlist" value="#{sssFileUpload.schemeCode}" size="1">
                            <f:selectItems value="#{sssFileUpload.schemeList}"/>
                            <a4j:support event="onblur" action="#{sssFileUpload.onChangeScheme}" reRender="ddSp"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSP" styleClass="label" value="Scheme Provider"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSp" style="width: 150px" styleClass="ddlist"  value="#{sssFileUpload.vendor}" size="1">
                            <f:selectItems value="#{sssFileUpload.vendorList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel17" width="100%" styleClass="row1">
                        <h:outputLabel id="lblControlFileId"  styleClass="label" value="Control File Id"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtControlFileId" onkeyup="this.value = this.value.toUpperCase();" size="25" 
                                     styleClass="input" value="#{sssFileUpload.fileId}" maxlength="21"/>
                        <h:outputLabel id="fileuploadlbl" value="Upload File Path" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <t:inputFileUpload id="file" value="#{sssFileUpload.uploadedFile}" required="false"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Upload" action="#{sssFileUpload.uploadProcess}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{sssFileUpload.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{sssFileUpload.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
