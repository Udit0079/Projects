<%-- 
    Document   : bulkrecovery
    Created on : 26 Apr, 2014, 3:18:25 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>NEFT DATA RECONSILATION</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="uploadForm" enctype="multipart/form-data" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NeftDataReconsilation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Neft Data Reconsilation"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NeftDataReconsilation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelMsg" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="errorMsg"   styleClass="error" value="#{NeftDataReconsilation.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel15" width="100%" styleClass="row1">
                        <h:outputLabel id="Func" value="Function" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="ddfunction" value="#{NeftDataReconsilation.function}" styleClass="ddlist" style="width:130px" size="1"  >
                            <f:selectItems id="selectfuction" value="#{NeftDataReconsilation.functionList}"/>
                            <a4j:support event="onblur" action="#{NeftDataReconsilation.optionsMode}" reRender="errorMsg,fileuploadlbl,file,lblFromDate,txtFrmDate,a6" oncomplete="setMask();" focus="#{NeftDataReconsilation.focusId}"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:outputLabel id="fileuploadlbl" value="Bulk File Path" styleClass="label" style="display:#{NeftDataReconsilation.enableUpload}"/>
                            <h:outputLabel id="lblFromDate" styleClass="label" value="Reconsilation Date" style="display:#{NeftDataReconsilation.enablepdf}"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        </h:panelGroup>
                        <h:panelGroup>    
                            <t:inputFileUpload id="file" value="#{NeftDataReconsilation.uploadedFile}" required="false" style="display:#{NeftDataReconsilation.enableUpload}"/>
                            <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="width:70px;display:#{NeftDataReconsilation.enablepdf}"  value="#{NeftDataReconsilation.reconDate}">
                                <a4j:support event="onblur" action="#{NeftDataReconsilation.validateDt}" reRender="errorMsg" oncomplete="setMask();" focus="btnUpload"/>
                            </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Upload Process" action="#{NeftDataReconsilation.uploadProcess}"  style="display:#{NeftDataReconsilation.enableUpload}"/>
                            <a4j:commandButton id="btnprint" value="PrintReport" action="#{NeftDataReconsilation.uploadProcess}"  style="display:#{NeftDataReconsilation.enablepdf}"  reRender="errorMsg"/>
                            <a4j:commandButton id="btnDownload"  value="PDF Download" action="#{NeftDataReconsilation.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;" style="display:#{NeftDataReconsilation.enablepdf}"  />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{NeftDataReconsilation.refreshForm}" oncomplete="setMask();" 
                                               reRender="errorMsg,txtFrmDate,file,ddfunction,lblFromDate,fileuploadlbl,btnUpload,btnprint,btnDownload"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{NeftDataReconsilation.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
