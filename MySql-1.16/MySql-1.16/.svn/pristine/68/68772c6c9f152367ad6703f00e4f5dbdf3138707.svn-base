<%-- 
    Document   : trialBalanceMaster
    Created on : May 13, 2010, 12:33:54 PM
    Author     : jitendra Chaudhary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>trialBalanceMaster</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{trialBalanceMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Trial Balance Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{trialBalanceMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="height:30px;text-align:center; " styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{trialBalanceMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label1" styleClass="label" value="Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddType" styleClass="ddlist" size="1" style="width: 80px" value="#{trialBalanceMaster.type}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="LIABILITIES"/>
                                <f:selectItem itemValue="ASSESTS"/>
                                <a4j:support action="#{trialBalanceMaster.types}" event="onblur" reRender="gridPanel103,txtGroupCode,txtSubGroupCode,stxtMsg" limitToList="true" focus="txtGroupCode"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1y" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label2a" styleClass="label" value="Group Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtGroupCode" value="#{trialBalanceMaster.gCode}"  styleClass="input"style="width:70px" >
                                <a4j:support action="#{trialBalanceMaster.groupCodess}" event="onblur" reRender="gridPanel103,txtSubGroupCode,stxtMsg,txtSubGroupCode" limitToList="true" oncomplete="if(#{trialBalanceMaster.flag=='false'}){#{rich:element('txtGroupCode')}.focus();} else{#{rich:element('txtSubGroupCode')}.focus();} "  />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1y1" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label2ab" styleClass="label" value="Sub Group Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtSubGroupCode" styleClass="input"style="width:100px" value="#{trialBalanceMaster.subGroupCode}">
                                <a4j:support action="#{trialBalanceMaster.subGroupCodess}" event="onblur" reRender="txtCode2,txtGroupCode,stxtMsg" limitToList="true" focus="txtCode1"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanela" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label13" styleClass="label" value="Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddCode" styleClass="ddlist" size="1" style="width: 50px" value="#{trialBalanceMaster.acType}">
                                    <f:selectItems value="#{trialBalanceMaster.acTypeOption}" />
                                </h:selectOneListbox> 
                                <h:inputText id="txtCode1" styleClass="input"style="width:70px" onkeyup="this.value = this.value.toUpperCase();" value="#{trialBalanceMaster.accountNo}">
                                    <a4j:support action="#{trialBalanceMaster.accountDescription}" event="onblur" reRender="txtCode2,stxtCodeDescription,txtSubGroupCode,stxtMsg,stxtCode2" limitToList="true" focus="txtCode2"/>
                                </h:inputText>
                                <h:inputText id="txtCode2" disabled="#{trialBalanceMaster.flag1}" styleClass="input"style="width:130px" value="#{trialBalanceMaster.accountNo1}"/>
                                <%--<h:outputLabel id="stxtCode2" styleClass="label" value="#{trialBalanceMaster.accountNo}" ></h:outputLabel>--%>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label12" styleClass="label" value="Code Description"/>
                            <h:outputText id="stxtCodeDescription" styleClass="output" value="#{trialBalanceMaster.description}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1z" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 60px" value="#{trialBalanceMaster.accountType}">
                                <a4j:support action="#{trialBalanceMaster.codeDescriptionss}"  event="onblur" reRender="stxtAccountTypeDe" limitToList="true" />
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{trialBalanceMaster.acctTypeOption}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1x" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="labelp" styleClass="label" value="Account Status" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAccountStatus" styleClass="ddlist" size="1" style="width: 100px" value="#{trialBalanceMaster.accountStatus}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="OPERATIVE"/>
                                <f:selectItem itemValue="INOPERATIVE"/>
                                <f:selectItem itemValue="ALL"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel14" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label9" styleClass="label" value="Account Type De."/>
                            <h:outputText id="stxtAccountTypeDe" styleClass="output" value="#{trialBalanceMaster.codeDescri}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row1" style="height:235px;">
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                           actionListener="#{trialBalanceMaster.fetchCurrentRow}">
                                <a4j:actionparam name="acNo" value="{acNo}" />
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu>
                        <a4j:region>
                            <rich:dataTable value="#{trialBalanceMaster.trialBalanceMs}" var="item"  rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="Trial Balance Master" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Group Code" /></rich:column>
                                        <rich:column><h:outputText value="Sub Group Code" /></rich:column>
                                        <rich:column><h:outputText value="Code" /></rich:column>
                                        <rich:column><h:outputText value="Descriptions" /></rich:column>
                                        <rich:column><h:outputText value="A/c Type" /></rich:column>
                                        <rich:column><h:outputText value="Type" /></rich:column>
                                        <rich:column><h:outputText value="A/c Status" /></rich:column>
                                        <rich:column><h:outputText value="Last Updated By" /></rich:column>
                                        <rich:column><h:outputText value="Trantime" /></rich:column>
                                        <rich:column><h:outputText value="S.No" /></rich:column>
                                        <rich:column><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.groupCode}" /></rich:column>
                                <rich:column><h:outputText value="#{item.subGroupCode}" /></rich:column>
                                <rich:column><h:outputText value="#{item.code}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.description}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.accType}" /></rich:column>
                                <rich:column><h:outputText value="#{item.type}" /></rich:column>
                                <rich:column><h:outputText value="#{item.acctStatus}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.lastUpdatedBy}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.tranTime}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.seqNo}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{trialBalanceMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{trialBalanceMaster.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                        <rich:modalPanel id="deletePanel" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Are You Sure, You Want to Delete this Record?" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                    <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{trialBalanceMaster.delete}" oncomplete="#{rich:component('deletePanel')}.hide();" reRender="gridPanel103,stxtMsg,gridPanel1" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{trialBalanceMaster.save}" reRender="ddType,txtGroupCode,txtSubGroupCode,ddAccountStatus,txtCode1,ddAccountType,stxtAccountTypeDe,stxtUser,stxtMsg,gridPanel103,stxtCode2,txtCode2,stxtCodeDescription"/>
                            <a4j:commandButton id="btnReset" value="Refresh" action="#{trialBalanceMaster.clearText}" reRender="stxtAccountTypeDe,ddAccountStatus,ddAccountType,stxtCodeDescription,txtCode2,txtCode1,txtGroupCode,txtSubGroupCode,stxtMsg,gridPanel103,stxtCode2,ddType"/>
                            <a4j:commandButton id="btnExit" action="#{trialBalanceMaster.exitFrm}" value="Exit" reRender="stxtAccountTypeDe,ddAccountStatus,ddAccountType,stxtCodeDescription,txtCode2,txtCode1,txtGroupCode,txtSubGroupCode,stxtMsg,gridPanel103,stxtCode2,ddType"/>                        
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
