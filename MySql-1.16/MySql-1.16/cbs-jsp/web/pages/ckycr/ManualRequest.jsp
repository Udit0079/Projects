<%-- 
    Document   : ManualRequest
    Created on : 21 Mar, 2017, 11:55:52 AM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Manual Request</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="manualRequest" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{manualRequestController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Manual Request"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{manualRequestController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanalId" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="msgId" value="#{manualRequestController.msg}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="4" columnClasses="col13,col13,col13,col13" style="width:100%" styleClass="row1">
                        <h:outputLabel id="lblMode" styleClass="label" value="Mode"/>
                        <h:selectOneListbox id="ddMode" value="#{manualRequestController.mode}" styleClass="ddlist" size="1" style="width:200px">
                            <f:selectItems value="#{manualRequestController.modeList}" />
                            <a4j:support event="onblur" action="#{manualRequestController.modeFieldAction}" reRender="mainPanel" 
                                         oncomplete="if(#{manualRequestController.mode=='UPLOAD'}){#{rich:element('ddType')}.focus();}
                                         else if(#{manualRequestController.mode=='DOWNLOAD'}){#{rich:element('txtCustId')}.focus();}
                                         else{#{rich:element('txtFrDt')}.focus();};setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblType" styleClass="label" value="Type"/>
                        <h:selectOneListbox id="ddType" styleClass="ddlist" size="1" value="#{manualRequestController.type}" 
                                            style="width:100px" disabled="#{manualRequestController.typeDisable}">
                            <f:selectItems value="#{manualRequestController.typeList}"/>
                            <a4j:support event="onblur" action="#{manualRequestController.typeFieldAction}" 
                                         reRender="msgId,txtCustId,txtDob,gridPanel2,txnGrid,txnList,checkBoxPenal,checkBoxPenalGrp,checkAllId,uploadBtn"
                                         oncomplete="if(#{manualRequestController.type=='INDIVIDUAL'}){#{rich:element('txtCustId')}.focus();}
                                         else{#{rich:element('uploadBtn')}.focus();};setMask();"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" 
                                 style="width:100%;display:#{manualRequestController.custIdRowVisible}">
                        <h:outputLabel id="lblCustId" styleClass="label" value="#{manualRequestController.ckycrCustIdLebal}"/>
                        <h:inputText id="txtCustId" styleClass="input" style="width:100px" value="#{manualRequestController.customerIdOrCKYCRNo}" maxlength="14">
                            <a4j:support event="onblur" action="#{manualRequestController.customerCkycNoAction}" 
                                         reRender="msgId,txtDob,txnGrid,txnList,checkBoxPenal,checkBoxPenalGrp,checkAllId,
                                         uploadBtn" oncomplete="if(#{manualRequestController.mode=='UPLOAD'}){#{rich:element('uploadBtn')}.focus();}
                                         else{#{rich:element('txtDob')}.focus();};setMask();"/>
                        </h:inputText> 
                        <h:outputLabel id="lblDob" styleClass="label" value="Date of Birth" style="display:#{manualRequestController.dobVisible}"/>
                        <h:inputText id="txtDob" styleClass="input issueDt" maxlength="10" style="width:96px;display:#{manualRequestController.dobVisible}" value="#{manualRequestController.dateOfBirth}">
                            <a4j:support event="onblur" action="#{manualRequestController.dobFieldAction}" reRender="msgId" oncomplete="setMask();"/> 
                        </h:inputText>    
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" style="width:100%;display:#{manualRequestController.visibleDtField}">
                        <h:outputLabel styleClass="label" value="From Date" />
                        <h:inputText id="txtFrDt" styleClass="input issueDt" maxlength="10" value="#{manualRequestController.fromDate}" style="width:98px"/>
                        <h:outputLabel styleClass="label" value="To Date"/>
                        <h:inputText id="txtToDt" styleClass="input issueDt" maxlength="10" value="#{manualRequestController.toDate}" style="width:96px">
                            <a4j:support event="onblur" action="#{manualRequestController.toDateFieldAction}" 
                                         reRender="msgId,genUploadGrid,gentxnList,genDonwloadGrid,gentDownxnList,
                                         tablePanel,taskList,uploadBtn" oncomplete="setMask();"/> 
                        </h:inputText>    
                    </h:panelGrid>    
                    <h:panelGrid id="checkBoxPenal" styleClass="row1" style="width:100%;text-align:left;display:#{manualRequestController.visibleLeftAllCheckBox}">
                        <h:panelGroup id="checkBoxPenalGrp" layout="block">
                            <h:selectBooleanCheckbox id="checkAllId" value="#{manualRequestController.checkBoxAll}" disabled="#{manualRequestController.checkBoxAllDisable}">
                                <a4j:support actionListener="#{manualRequestController.checkAll}" event="onclick" reRender="msgId,txnGrid,genUploadGrid,genDonwloadGrid"/> 
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBox" styleClass="output" value="All"></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;display:#{manualRequestController.awaitingGridDisplay}" columnClasses="vtop">
                        <rich:dataTable  value="#{manualRequestController.customerDetailList}" var="item" 
                                         rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="6"><h:outputText value="Customer Deatil"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Customer Id"/></rich:column>
                                    <rich:column width="350"><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column width="90"><h:outputText value="Date of Birth"/></rich:column>
                                    <rich:column width="250"><h:outputText value="Father Name" /></rich:column>
                                    <rich:column width="110"><h:outputText value="Primary Branch Name" /></rich:column>
                                    <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.customerIdOrCKYCRNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.custName}"/></rich:column>
                            <rich:column><h:outputText value="#{item.dateOfBirth}"/></rich:column>
                            <rich:column><h:outputText value="#{item.fatherName}"/></rich:column>
                            <rich:column><h:outputText value="#{item.primaryBrName}"/></rich:column>
                            <rich:column>
                                <h:selectBooleanCheckbox id="c1" value="#{item.checkBox}" disabled="#{manualRequestController.gridCheckBoxDisable}" style="text-align: center;"/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="txnGridScroll"align="left" for="txnList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="genUploadGrid" width="100%" style="background-color:#edebeb;height:200px;display:#{manualRequestController.genUploadGridDisplay}" columnClasses="vtop">
                        <rich:dataTable  value="#{manualRequestController.genUploadDataList}" var="item" 
                                         rowClasses="gridrow1, gridrow2" id="gentxnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="5"><h:outputText value="Customer Deatil"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Customer Id"/></rich:column>
                                    <rich:column width="110"><h:outputText value="Branch Code" /></rich:column>
                                    <rich:column width="110"><h:outputText value="Request By" /></rich:column>
                                    <rich:column width="110"><h:outputText value="Request Date" /></rich:column>
                                    <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.customerId}"/></rich:column>
                            <rich:column><h:outputText value="#{item.primaryBrName}"/></rich:column>
                            <rich:column><h:outputText value="#{item.requestBy}"/></rich:column>
                            <rich:column><h:outputText value="#{item.requestDate}"/></rich:column>
                            <rich:column width="4%" style="text-align:center"> 
                                <h:selectBooleanCheckbox value="#{item.selected}"/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="genDataScroll"align="left" for="gentxnList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="genDonwloadGrid" width="100%" style="background-color:#edebeb;height:200px;display:#{manualRequestController.genDownloadGridDisplay}" columnClasses="vtop">
                        <rich:dataTable  value="#{manualRequestController.genDownloadDataList}" var="item" 
                                         rowClasses="gridrow1, gridrow2" id="gentDownxnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="4"><h:outputText value="Customer Deatil"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Sno."/></rich:column>
                                    <rich:column width="110"><h:outputText value="Customer Id/CKYC No" /></rich:column>
                                    <rich:column width="110"><h:outputText value="Date Of Birth" /></rich:column>
                                    <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                            <rich:column><h:outputText value="#{item.customerIdOrCKYCRNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.dateOfBirth}"/></rich:column>
                            <rich:column width="4%" style="text-align:center"> 
                                <h:selectBooleanCheckbox value="#{item.selected}"/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="genDownDataScroll"align="left" for="gentDownxnList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{manualRequestController.allGridDisplay}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{manualRequestController.downloadDetailList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="4"><h:outputText value="Generated File Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="SNo." /></rich:column>
                                        <rich:column><h:outputText value="Uploaded Date" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.uploadedGenDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.uploadedFileName}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{manualRequestController.downloadFile}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{manualRequestController.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="btnPanelGrid" columns="2" columnClasses="col7,col7,col7" styleClass="footer" style="width:100%;height:30px;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="uploadBtn" value="#{manualRequestController.buttonLebal}" 
                                               oncomplete="#{rich:component('processPanel')}.show()" 
                                               reRender="msgId,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{manualRequestController.refreshForm}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{manualRequestController.exitForm}" reRender="mainPanel" oncomplete="setMask();"/>
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
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to #{manualRequestController.buttonLebal} ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{manualRequestController.processBtnAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('processPanel')}.focus();setMask();" 
                                                       reRender="mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>       
            </rich:modalPanel>
        </body>
    </html>
</f:view>
