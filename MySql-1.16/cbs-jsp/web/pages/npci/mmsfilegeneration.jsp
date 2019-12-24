<%-- 
    Document   : mmsfilegeneration
    Created on : 4 Mar, 2016, 1:13:36 PM
    Author     : root
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Mandate Return Generation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issuedt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{mmsFileGenerationBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Mandate Return Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{mmsFileGenerationBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="stxtMsg" value="#{mmsFileGenerationBean.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblOperation" styleClass="label" value="Operation"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddOperation" styleClass="ddlist" size="1" style="width:80px" value="#{mmsFileGenerationBean.operation}">
                            <f:selectItems value="#{mmsFileGenerationBean.operationList}"/>
                            <a4j:support event="onblur" action="#{mmsFileGenerationBean.btnChangeProcess}" reRender="stxtMsg,btnSave"/>
                        </h:selectOneListbox>
                        <h:outputLabel></h:outputLabel>
                        <h:outputText></h:outputText>
                         <h:outputLabel></h:outputLabel>
                        <h:outputText></h:outputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblOption" styleClass="label" value="Option"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddOption" styleClass="ddlist" size="1" style="width:80px" value="#{mmsFileGenerationBean.option}">
                            <f:selectItems value="#{mmsFileGenerationBean.optionList}"/>
                           <a4j:support event="onblur" action="#{mmsFileGenerationBean.optionOpration}" reRender="stxtMsg,ddFileType"/> 
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMmsType" styleClass="label" value="Mandate Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMmsType" styleClass="ddlist" size="1" style="width:80px" value="#{mmsFileGenerationBean.mmsType}">
                            <f:selectItems value="#{mmsFileGenerationBean.mmsTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFileType" styleClass="label" value="File Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFileType" styleClass="ddlist" size="1" style="width:80px" value="#{mmsFileGenerationBean.fileType}">
                            <f:selectItems value="#{mmsFileGenerationBean.fileTypeList}"/>
                            <a4j:support event="onblur" action="#{mmsFileGenerationBean.btnFileTypeProcess}" 
                                         reRender="stxtMsg,gridPanel3,gridPanel4" oncomplete="if(#{mmsFileGenerationBean.fileType=='RETURN'})
                                         {#{rich:element('txtFileUpDt')}.focus();}else{#{rich:element('txtDt')}.focus();};setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;display:#{mmsFileGenerationBean.returnFlag}" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFileUpDt" styleClass="label" value="File Upload Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFileUpDt" styleClass="input issuedt" style="width:70px" value="#{mmsFileGenerationBean.fileUpDt}">
                            <a4j:support event="onblur" action="#{mmsFileGenerationBean.fileUpDtProcess}" reRender="stxtMsg,ddSeqNo" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="File No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSeqNo" styleClass="ddlist" size="1" style="width:80px" value="#{mmsFileGenerationBean.seqNo}">
                            <f:selectItems value="#{mmsFileGenerationBean.seqNoList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;display:#{mmsFileGenerationBean.initiationFlag}" styleClass="row2" width="100%">
                        <h:outputLabel id="lblDt" styleClass="label" value="Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtDt" styleClass="input issuedt" style="width:70px" value="#{mmsFileGenerationBean.tillDt}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="footerPanel" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="#{mmsFileGenerationBean.btnValue}" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{mmsFileGenerationBean.refreshForm}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{mmsFileGenerationBean.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{mmsFileGenerationBean.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"><h:outputText value="Generated File Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="File No" /></rich:column>
                                        <rich:column><h:outputText value="File Gen. Date" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="File Gen. By" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fileNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenBy}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{mmsFileGenerationBean.downloadFile}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{mmsFileGenerationBean.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to process it ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{mmsFileGenerationBean.processAction}" 
                                                           onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMsg,tablePanel,taskList" oncomplete="setMask()"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
