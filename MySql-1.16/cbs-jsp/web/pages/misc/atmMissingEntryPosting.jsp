<%-- 
    Document   : atmMissingEntryPosting
    Created on : Mar 20, 2015, 12:50:49 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>ATM Missing Entry</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calIssDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="formAtm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AtmMissingEntryPosting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM Acquirer Missing Txn Posting"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AtmMissingEntryPosting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="message" styleClass="msg" value="#{AtmMissingEntryPosting.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAtmBranch" styleClass="label" value="Atm Branch" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAtmBranch" styleClass="ddlist" size="1" style="width:90px;" value="#{AtmMissingEntryPosting.atmBranch}">
                                <f:selectItems value="#{AtmMissingEntryPosting.atmBranchList}"/>
                                <a4j:support action="#{AtmMissingEntryPosting.showAtmId}" event="onblur" oncomplete="setMask();" 
                                             reRender="message,calIssDate1,table,btnSave,ddAtmId" focus="ddAtmId"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAtmId" styleClass="label" value="Atm Id" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAtmId" styleClass="ddlist" size="1" style="width:90px;" value="#{AtmMissingEntryPosting.atmId}" >
                                <f:selectItems value="#{AtmMissingEntryPosting.atmIdList}"/>
                                <a4j:support action="#{AtmMissingEntryPosting.showGlhead}" event="onblur" oncomplete="setMask();" 
                                             reRender="message,calIssDate1,table,btnSave,txtGlhead,stxtCrHead" focus="txtAmt"/>
                            </h:selectOneListbox>
              `          </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblGlhead" styleClass="label" value="Atm GlHead" style="padding-left:70px;"><%--<font class="required" style="color:red;">*</font>--%></h:outputLabel>
                            <h:inputText id="txtGlhead" styleClass="input" value="#{AtmMissingEntryPosting.glHead}" maxlength="17" style="width:90px;" disabled="true"/>
                            <h:outputLabel id="lblAmount" styleClass="label"  value="Amount:"style="padding-left:70px;"/>
                            <h:inputText id="txtAmt"  style="width:90px" value="#{AtmMissingEntryPosting.amount}" styleClass="input" maxlength="12">
                                <%--f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/--%>
                                  <%--a4j:support action="#{AtmMissingEntryPosting.amountProcess}" event="onblur" reRender="message" oncomplete="setMask();"/--%>
                            </h:inputText> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblIssueDt" styleClass="label" value="Value. Date" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="calIssDate1" styleClass="input calIssDate" maxlength="10" style="width:90px;"  value="#{AtmMissingEntryPosting.valueDt}" >
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                                 <a4j:support action="#{AtmMissingEntryPosting.gridLoad}" event="onblur" oncomplete="setMask();" 
                                             reRender="message,calIssDate1,table,taskList,btnSave,txtGlhead,stxtCrHead"/>
                            </h:inputText>
                            <h:outputLabel id="lblCrHead" styleClass="label" value="Debit Head" style="padding-left:70px;"/>
                            <h:outputText id="stxtCrHead" styleClass="output" value="#{AtmMissingEntryPosting.debitHead}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{AtmMissingEntryPosting.gridDetail}" var="dataItem"
                                                rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Atm Missing Entry Detail"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Atm Branch"/></rich:column>
                                            <rich:column><h:outputText value="Atm Id"/></rich:column>
                                            <rich:column><h:outputText value="GlHead"/></rich:column>
                                            <rich:column><h:outputText value="Amount"/></rich:column>
                                            <rich:column><h:outputText value="Value Date"/></rich:column>
                                            <rich:column><h:outputText value="Enter By"/></rich:column>
                                            <%--rich:column width="20"><h:outputText value="SELECT"/></rich:column--%>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.atmBranchTab}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.atmIdTab}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.glHeadTab}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.amountTab}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.valueDtTab}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enterByTab}"/></rich:column>
                                    
                                    <%--rich:column style="text-align:center;width:40px">     
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" oncomplete="#{rich:component('savePanel')}.show()" reRender=",message,lpg,frDt,toDt">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{AtmMissingEntryPosting.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{AtmMissingEntryPosting.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column--%>
                                    
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="12" value="Post" oncomplete="#{rich:component('savePanel')}.show()" disabled="#{AtmMissingEntryPosting.postButtonDisable}" 
                                                   reRender="message,taskList,ddAtmId,txtGlhead,txtAmt,stxtCrHead,calIssDate1"/>
                                <a4j:commandButton id="btnRefresh" tabindex="13" ajaxSingle="true" value="Refresh" action="#{AtmMissingEntryPosting.resetForm}"
                                                   reRender="taskList,message,btnSave,ddAtmId,txtGlhead,txtAmt,stxtCrHead,calIssDate1" oncomplete="setMask();">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" tabindex="14" ajaxSingle="true" value="Exit" action="#{AtmMissingEntryPosting.exitForm}" reRender="taskList,message,lpg"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>   
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('save')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Atm Missing Entry Post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="save" ajaxSingle="true" action="#{AtmMissingEntryPosting.postDetail}" oncomplete="#{rich:component('savePanel')}.hide();return false;"
                                                       reRender="mainPanel,message,lpg,taskList,btnRefresh" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>