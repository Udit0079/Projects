<%-- 
    Document   : bulkUploadneftrtgs
    Created on : 3 Jan, 2019, 2:37:26 PM
    Author     : rahuljain
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
            <title>FileUpload NEFT-RTGS</title>
        </head>
        <body>
            <h:form id="uploadForm" enctype="multipart/form-data" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BulkUploadNeftRtgs.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="FileUpload"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BulkUploadNeftRtgs.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="downloadLink" columns="3" columnClasses="col9,col7,col2" style="height:30px;align:right;" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:commandButton id="btnDownload" value="Download Sample File" action="#{BulkUploadNeftRtgs.downloadSampleFile}"/>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{BulkUploadNeftRtgs.message}"/>
                    </h:panelGrid>
                    <%--<h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" width="100%">--%>
                    <h:panelGrid columnClasses="col1,col20,col1,col9" columns="4" id="gridPanel17" width="100%">
                        <h:outputLabel></h:outputLabel>
                        <h:outputText></h:outputText>
                        <h:outputLabel id="fileuploadlbl" value="Upload File Path" styleClass="label"/>
                        <t:inputFileUpload id="file" value="#{BulkUploadNeftRtgs.uploadedFile}" required="false"/>
                    </h:panelGrid>
                    <%--</h:panelGrid>--%>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Upload" action="#{BulkUploadNeftRtgs.uploadProcess}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{BulkUploadNeftRtgs.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{BulkUploadNeftRtgs.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>

