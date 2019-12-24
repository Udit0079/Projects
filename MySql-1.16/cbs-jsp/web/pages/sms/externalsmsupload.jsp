<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Bulk SMS Upload</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <h:form id="uploadForm" enctype="multipart/form-data" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{externalSmsUpload.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Bulk SMS Upload"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{externalSmsUpload.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="downloadLink" columns="3" columnClasses="col9,col7,col2" style="height:30px;align:right;" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:commandButton id="btnDownload" value="Download Sample File" action="#{externalSmsUpload.downloadSampleFile}"/>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{externalSmsUpload.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columnClasses="col2,col7,col7,col2" columns="4" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="fileuploadlbl" value="Upload File Path" styleClass="label"/>
                        <t:inputFileUpload id="file" value="#{externalSmsUpload.uploadedFile}" required="false"/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" columnClasses="col3,col3,col3,col3" id="gridpanelInfo"   style="height:30px;" styleClass="row2" width="100%" >
                            <h:outputLabel  value="Instant Message Active, Message should not exceed 160 characters" styleClass="error" style="color:blue"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" columnClasses="col3,col3,col3,col3" id="gridpanelMessage"   style="height:30px;" styleClass="row1" width="100%" >
                            <h:inputTextarea rows="3" cols="80" id="txtAreaMessage"  value="#{externalSmsUpload.textMessage}"  
                                             styleClass="input" 
                                             style="height:60px;width:450px">
                                <a4j:support event="onblur"  action="#{externalSmsUpload.countingAction}" reRender="count"  />
                            </h:inputTextarea>
                            <h:outputText id="count" value="Total Characters : #{externalSmsUpload.count}" styleClass="output"/>
                        </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Upload" action="#{externalSmsUpload.uploadProcess}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{externalSmsUpload.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{externalSmsUpload.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
