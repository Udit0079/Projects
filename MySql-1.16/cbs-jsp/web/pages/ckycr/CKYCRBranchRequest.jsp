<%-- 
    Document   : CKYCRBranchRequest
    Created on : 25 Jan, 2017, 1:28:13 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>CKYCR Request</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".dob").mask("99/99/9999");
                }
            </script>
    </head>
    <body>
        <h:form id="ckycrBranchRequest" enctype="multipart/form-data">
            <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                    <h:panelGroup id="groupPanel" layout="block">
                        <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                        <h:outputText id="stxtDate" styleClass="output" value="#{cKYCRRequestController.todayDate}"/>
                    </h:panelGroup>
                    <h:outputLabel id="label" styleClass="headerLabel" value="CKYCR Request"/>
                    <h:panelGroup id="groupPanel1" layout="block">
                        <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                        <h:outputText id="stxtUser" styleClass="output" value="#{cKYCRRequestController.userName}"/>
                    </h:panelGroup>
                </h:panelGrid>
                
                <h:panelGrid id="errormsg" style="height:25px;text-align:center" styleClass="#{cKYCRRequestController.msgStyle}" width="100%">
                    <h:outputText id="errmsg" value="#{cKYCRRequestController.errorMsg}"/>
                </h:panelGrid>
                <%--  <h:panelGrid id="successMsg" styleClass="msg" style="height:10px;text-align:center;" width="100%">
                    <h:outputText id="succMsg" value="#{cKYCRRequestController.successMsg}"/>
                    </h:panelGrid>--%>
                <h:panelGrid columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel1" width="100%" styleClass="row2">
                    <h:outputLabel value="Mode" id="modeLabel" styleClass="label"/>
                    <h:selectOneListbox id="modeId" value="#{cKYCRRequestController.mode}" disabled="#{cKYCRRequestController.modeDisable}" styleClass="ddlist" size="1"  style="width:100px" >
                        <f:selectItems value="#{cKYCRRequestController.modeList}" />
                         <a4j:support  action="#{cKYCRRequestController.setDisableTrue}" event="onchange" oncomplete="if(#{cKYCRRequestController.mode == 'DOWNLOAD'}){
                                               #{rich:element('custId')}.focus();setMask(); 
                                               }else{
                                               #{rich:element('typeId')}.disabled=false;
                                               #{rich:element('typeId')}.focus();
                                               }"    reRender="txnGrid,typeId,errmsg,successMsg,custId,dateOfBirth,custIdLabel,errormsg"/>
                    </h:selectOneListbox>

                    <h:outputLabel value="Type" id="typeLabel" styleClass="label"/>
                    <h:selectOneListbox id="typeId" value="#{cKYCRRequestController.type}" disabled="#{cKYCRRequestController.typeDisable}" styleClass="ddlist" size="1"  style="width:100px" >
                        <f:selectItems value="#{cKYCRRequestController.typeList}"/>
                        <a4j:support  action="#{cKYCRRequestController.getCustomerDetail}" event="onchange" oncomplete="if(#{cKYCRRequestController.type == 'INDIVIDUAL'}){
                                               #{rich:element('custId')}.focus();
                                               }else{
                                               #{rich:element('upload')}.focus();
                                               }" reRender="modeId,typeId,txnGrid,upload,type,errmsg,successMsg,custId,dateOfBirth,typeDisable,modeDisable,c1,checkAllId,errormsg"/>
                    </h:selectOneListbox> 
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel2" width="100%" styleClass="row1">
                        <h:outputLabel value="#{cKYCRRequestController.ckycrCustIdLebal}" id="custIdLabel" styleClass="label"/>
                        <h:inputText id="custId" styleClass="input" style="width:100px" disabled="#{cKYCRRequestController.customerIdDisable}" value="#{cKYCRRequestController.customerIdOrCKYCRNo}" maxlength="14" >
                            <a4j:support  action="#{cKYCRRequestController.getCustomerDetail}" event="onblur" oncomplete="if(#{cKYCRRequestController.mode == 'UPLOAD'} && #{cKYCRRequestController.type == 'INDIVIDUAL'}){
                                                #{rich:element('upload')}.focus();
                                               }
                                               if(#{cKYCRRequestController.mode == 'DOWNLOAD'} && #{cKYCRRequestController.type == 'INDIVIDUAL'}){
                                                #{rich:element('dateOfBirth')}.focus();setMask();
                                               }" reRender="save,upload,type,errmsg,successMsg,custId,dateOfBirth,txnGrid,typeId,modeId,errormsg"/> 
                        </h:inputText> 
                        <h:outputLabel value="Date of Birth" styleClass="label"/>
                        <h:inputText id="dateOfBirth" styleClass="input dob" maxlength="10"  disabled="#{cKYCRRequestController.dateOfBirthDisable}" value="#{cKYCRRequestController.dateOfBirth}" style="width:70px;setMask();" > <%-- styleClass="input dateOfBirth" maxlength="10" style="width:70px;setMask();" --%>
                            <a4j:support  action="#{cKYCRRequestController.setDisableTrue}" event="onblur" oncomplete="setMask();" reRender="upload,save,download,errmsg,successMsg,custId,modeId,dateOfBirth,txnGrid,errormsg" focus="download"/> 
                        </h:inputText>    
                        
                </h:panelGrid>
                <h:panelGrid id="checkBoxPenal" styleClass="row2" style="width:100%;text-align:left;">
                    <h:panelGroup id="checkBoxPenalGrp" layout="block">
                        <h:selectBooleanCheckbox id="checkAllId" value="#{cKYCRRequestController.checkBoxAll}" disabled="#{cKYCRRequestController.checkBoxAllDisable}">
                            <a4j:support actionListener="#{cKYCRRequestController.checkAll}" event="onclick" 
                                         reRender="txnGrid"/> 
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblCheckBox" styleClass="output" 
                                       value="All"></h:outputLabel>
                    </h:panelGroup>
                </h:panelGrid> 
                
                <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                            <rich:dataTable  value="#{cKYCRRequestController.customerDetailList}" var="item" 
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                     <rich:columnGroup>
                                        <rich:column colspan="7"><h:outputText value="Customer Deatil"/></rich:column>
                                        <rich:column breakBefore="true" width="60px"><h:outputText value="SNo."/></rich:column>
                                        <rich:column width="90"><h:outputText value="Customer Id" /></rich:column>
                                        <rich:column width="350"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column width="90"><h:outputText value="DOB" /></rich:column>
                                        <rich:column width="250"><h:outputText value="Father Name" /></rich:column>
                                        <rich:column width="110"><h:outputText value="Primary Branch" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.sno}"/></rich:column> <%--value="#{item.sno}" --%>
                                <rich:column><h:outputText value="#{item.customerIdOrCKYCRNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.dateOfBirth}"/></rich:column>
                                <rich:column><h:outputText value="#{item.fatherName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.primaryBrName}"/></rich:column>
                                <rich:column><h:selectBooleanCheckbox  id="c1"  value="#{item.checkBox}" disabled="#{cKYCRRequestController.gridCheckBoxDisable}" style="text-align: center;">
                                                <a4j:support actionListener="#{cKYCRRequestController.checkNotAll}" event="onclick" reRender="txnGrid"/> 
                                            </h:selectBooleanCheckbox>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="deafDataScroll"align="left" for="txnList" maxPages="20"/>
                </h:panelGrid>
                <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="upload" value="Upload" oncomplete="#{rich:component('processPanel')}.show()" disabled="#{cKYCRRequestController.uploadDisable}" reRender="modeId,upload,typeId,download,errmsg,successMsg,custId,dateOfBirth,txnGrid,processPanel,checkAllId,custIdLabel,errormsg"/>
                            <%--<a4j:commandButton id="save" value="Save"  disabled="#{cKYCRRequestController.saveDisable}" oncomplete="#{rich:component('processPanel')}.show()" reRender="modeId,upload,typeId,save,download,errmsg,successMsg,custId,dateOfBirth,txnGrid,processPanel,checkAllId"/>--%>
                            <a4j:commandButton id="download" value="Download" action="#{cKYCRRequestController.download}"  disabled="#{cKYCRRequestController.downloadDisable}" reRender="modeId,typeId,upload,download,errmsg,successMsg,custId,dateOfBirth,txnGrid,processPanel,checkAllId,custIdLabel,errormsg"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{cKYCRRequestController.refreshForm}" oncomplete="setMask();" reRender="modeId,upload,txnGrid,typeId,download,errmsg,successMsg,custId,dateOfBirth,txnGrid,processPanel,checkAllId,custIdLabel,errormsg"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{cKYCRRequestController.exitForm}"/>
                            
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
                                    <h:outputText value="Are you sure to Save ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{cKYCRRequestController.saveCKYCR}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('processPanel')}.focus();" 
                                                       reRender="modeId,upload,txnGrid,typeId,download,errmsg,successMsg,custId,dateOfBirth,processPanel,checkAllId,errormsg"/>
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
