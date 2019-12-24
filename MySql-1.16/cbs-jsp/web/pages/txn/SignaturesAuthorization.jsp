<%-- 
    Document   : SignaturesAuthorization
    Created on : 1 Nov, 2010, 5:42:30 PM
    Author     : Nishant Kansal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>CKYC Image / Signatures Authorization</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SignaturesAuthorizationManaged.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Signatures Authorization"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SignaturesAuthorizationManaged.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelTable" width="100%" styleClass="row1">
                            <h:panelGrid id="functList"  columns="4" columnClasses="col2,col7,col2,col7"style="width:100%;" styleClass="row2" >
                                <h:outputLabel id="lblFunct" styleClass="label" value="Function"/>
                                <h:selectOneListbox id="funlist" styleClass="ddlist"  size="1" value="#{SignaturesAuthorizationManaged.function}">
                                    <f:selectItems value="#{SignaturesAuthorizationManaged.functionList}"/>
                                    <a4j:support actionListener="#{SignaturesAuthorizationManaged.displayFields}" event="onblur" focus="ddImgType"
                                                 reRender="lblImgType,ddImgType,lblcustId,txtCustId"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblImgType" styleClass="label" value="Classification"/>
                                <h:selectOneListbox id="ddImgType" styleClass="ddlist"  size="1" value="#{SignaturesAuthorizationManaged.imgType}">
                                    <f:selectItems value="#{SignaturesAuthorizationManaged.imgTypeList}"/>
                                    <a4j:support actionListener="#{SignaturesAuthorizationManaged.getUnauthorizedAcctNoLoadGrid}" event="onblur" focus="txtCustId"
                                                 reRender="dataList,taskList"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="custGrid"  columns="4" columnClasses="col2,col7,col2,col7"style="width:100%;" styleClass="row1" >
                                <h:outputLabel id="lblcustId" styleClass="label" value="Customer Id / AcNo" style="display:#{SignaturesAuthorizationManaged.displayCustId}"/>
                                <h:inputText id="txtCustId" styleClass="input" value="#{SignaturesAuthorizationManaged.custId}" style="width:90px;display:#{SignaturesAuthorizationManaged.displayCustId}">
                                    <a4j:support actionListener="#{SignaturesAuthorizationManaged.getCustomerImgDetails}" event="onblur" 
                                                 reRender="dataList,taskList,stxtError"/>
                                </h:inputText>
                                <h:outputText/>
                                <h:outputText/>
                            </h:panelGrid>
                            <h:panelGrid id="dataList" style="width:100%;height:200px" >
                                <rich:dataTable var="dataItem" value="#{SignaturesAuthorizationManaged.signAuthGridList}" 
                                                rowClasses="gridrow1, gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="5"><h:outputText value="List of Pending Signatures/ CKYC Images" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                            <rich:column><h:outputText value="AcNo / CustId" /></rich:column>
                                            <rich:column><h:outputText value="Img Name" /></rich:column>
                                            <rich:column width="70px"><h:outputText value="Select Record" /></rich:column>
                                            <rich:column width="70px"><h:outputText value="Delete Record" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.serialNo}"/></rich:column>
                                    <rich:column><h:outputText id="stxtAccNo"value="#{dataItem.accountNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.imgName}"/></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" ajaxSingle="true"action="#{SignaturesAuthorizationManaged.setRowValues}" 
                                                         reRender="signature,sigViewPanel,stxtAccInst,stxtCustName,stxtAccOpenDate,stxtIntroAccNo,stxtSignScanBy,stxtError">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{SignaturesAuthorizationManaged.signAuthGrid}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{SignaturesAuthorizationManaged.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="deleteLink" ajaxSingle="true" onclick="#{rich:component('deletePanel')}.show();">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{SignaturesAuthorizationManaged.signAuthGrid}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{SignaturesAuthorizationManaged.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="dataScroll"align="left" for="taskList" maxPages="3" />
                            </h:panelGrid>
                            <h:panelGrid id="panelCustName"  columns="2" columnClasses="col7,col12"style="width:100%;" styleClass="row2" >
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name"/>
                                <h:outputText id="stxtCustName" styleClass="output" value="#{SignaturesAuthorizationManaged.custName}" style="color:green;"/>
                            </h:panelGrid>
                            <h:panelGrid id="panelAccInst"  columns="2" columnClasses="col7,col12"style="width:100%;t" styleClass="row1" >
                                <h:outputLabel id="lblAccInst" styleClass="label" value="Account Instruction"/>
                                <h:outputText id="stxtAccInst" styleClass="output" value="#{SignaturesAuthorizationManaged.accInstr}" style="color:green;"/>
                            </h:panelGrid>
                            <h:panelGrid id="panelAccOpenDate"  columns="2" columnClasses="col7,col12"style="width:100%" styleClass="row2" >
                                <h:outputLabel id="lblAccOpenDate" styleClass="label" value="A/C Opening Date"/>
                                <h:outputText id="stxtAccOpenDate" styleClass="output" value="#{SignaturesAuthorizationManaged.accOpenDate}" style="color:green;"/>
                            </h:panelGrid>
                            <h:panelGrid id="panelIntroAccNo"  columns="2" columnClasses="col7,col12"style="width:100%" styleClass="row1" >
                                <h:outputLabel id="lblIntroAccNo" styleClass="label" value="Introducer's A/C No."/>
                                <h:outputText id="stxtIntroAccNo" styleClass="output" value="#{SignaturesAuthorizationManaged.introAccNo}" style="color:green;"/>
                            </h:panelGrid>
                            <h:panelGrid id="panelSignScanBy"  columns="2" columnClasses="col7,col12"style="width:100%;" styleClass="row2" >
                                <h:outputLabel id="lblSignScanBy" styleClass="label" value="Signatures Scanned By"/>
                                <h:outputText id="stxtSignScanBy" styleClass="output" value="#{SignaturesAuthorizationManaged.signScannedBy}" style="color:green;"/>
                            </h:panelGrid>
                            <h:panelGrid id="errorPanel" style="width:100%;text-align:center;" styleClass="row1">
                                <h:outputText id="stxtError" styleClass="error" value="#{SignaturesAuthorizationManaged.errorMsg}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:600px;height:440px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{SignaturesAuthorizationManaged.createSignature}" value="#{SignaturesAuthorizationManaged.acNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" action="#{SignaturesAuthorizationManaged.clickOnAuthorize}" 
                                               reRender="functList,stxtAccInst,stxtCustName,stxtAccOpenDate,stxtIntroAccNo,stxtSignScanBy,stxtError,signature,taskList,dataScroll,txtCustId,lblcustId">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnClose" value="Refresh" action="#{SignaturesAuthorizationManaged.refreshForm}" 
                                               reRender="functList,stxtAccInst,stxtCustName,stxtAccOpenDate,stxtIntroAccNo,stxtSignScanBy,stxtError,signature,taskList,txtCustId,lblcustId">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Exit" action="#{SignaturesAuthorizationManaged.exitForm}" 
                                               reRender="functList,stxtAccInst,stxtCustName,stxtAccOpenDate,stxtIntroAccNo,stxtSignScanBy,stxtError,signature,taskList,txtCustId,lblcustId">
                            </a4j:commandButton>
                        </h:panelGroup>
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                        <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <f:facet name="header">
                                <h:outputText value="Processing" />
                            </f:facet>
                            <h:outputText value="Wait Please..." />
                        </rich:modalPanel>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                        <rich:componentControl for="deletePanel" attachTo="hidelink1" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%" colspan="2">
                                    <h:outputText  id="stxtMesg" styleClass="output" value="Are you sure to delete this record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="btnYes" value="Yes" ajaxSingle="true" action="#{SignaturesAuthorizationManaged.deleteUnauthorizedSignature}" 
                                                       onclick="#{rich:component('deletePanel')}.hide();" 
                                                       reRender="functList,stxtAccInst,stxtCustName,stxtAccOpenDate,stxtIntroAccNo,stxtSignScanBy,stxtError,signature,taskList,dataScroll"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton  id="btnNo" value="No" onclick="#{rich:component('deletePanel')}.hide();" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
