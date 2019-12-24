<%-- 
    Document   : mismatchneftrtgs
    Created on : May 18, 2013, 12:11:33 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Mismatch NEFT-RTGS Processing</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{mismatchNeftController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Mismatch Processing"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{mismatchNeftController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{mismatchNeftController.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="processTypeComponentPanel" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblProcessType" styleClass="label" value="Process Type"></h:outputLabel>
                        <h:selectOneListbox id="ddProcessType" styleClass="ddlist" required="true" style="width:80px" value="#{mismatchNeftController.processType}" size="1">
                            <f:selectItems value="#{mismatchNeftController.processTypeList}"/>
                            <a4j:support event="onblur" action="#{mismatchNeftController.processTypeAction}" reRender="lblMsg,ddNeftBank"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblActionType" styleClass="label" value="Mode"></h:outputLabel>
                        <h:selectOneListbox id="ddActionType" styleClass="ddlist" required="true" style="width:120px" value="#{mismatchNeftController.actionType}" size="1">
                            <f:selectItems value="#{mismatchNeftController.actionTypeList}"/>
                            <a4j:support event="onblur" action="#{mismatchNeftController.actionTypeAction}" reRender="lblMsg,ddStatus"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblNeftBank" styleClass="label" value="Neft Bank"></h:outputLabel>
                        <h:selectOneListbox id="ddNeftBank" styleClass="ddlist" required="true" style="width:80px" value="#{mismatchNeftController.neftBank}" size="1">
                            <f:selectItems value="#{mismatchNeftController.neftBankList}"/>
                            <a4j:support event="onblur" action="#{mismatchNeftController.neftBankAction}" reRender="lblMsg,tablePanel,taskList"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="utrComponentPanel" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblUtr" styleClass="label" value="UTR"></h:outputLabel>
                        <h:outputText id="stxtUtr" styleClass="output" value="#{mismatchNeftController.pUtr}"/>
                        <h:outputLabel id="lblBeneAccount" styleClass="label" value="Beneficiary A/c"></h:outputLabel>
                        <h:outputText id="stxtBeneAccount" styleClass="output" value="#{mismatchNeftController.pBeneAccount}"/>
                    </h:panelGrid>
                    <h:panelGrid id="statusComponentPanel" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAmount" styleClass="label" value="Amount"></h:outputLabel>
                        <h:outputText id="stxtAmount" styleClass="output" value="#{mismatchNeftController.pAmount}"/>
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"></h:outputLabel>
                        <h:selectOneListbox id="ddStatus" styleClass="ddlist" required="true" style="width:80px" value="#{mismatchNeftController.pStatus}" size="1">
                            <f:selectItems value="#{mismatchNeftController.pStatusList}"/>
                            <a4j:support event="onblur" action="#{mismatchNeftController.validateStatus}" reRender="lblMsg,btnProcess"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{mismatchNeftController.tableDataList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Mismatch Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="UTR"/></rich:column>
                                        <rich:column><h:outputText value="Beneficiary A/c"/></rich:column>
                                        <rich:column><h:outputText value="Beneficiary Name"/></rich:column>
                                        <rich:column><h:outputText value="Receiver IFSC"/></rich:column>
                                        <rich:column><h:outputText value="Amount"/></rich:column>
                                        <rich:column><h:outputText value="Reason"/></rich:column>
                                        <rich:column><h:outputText value="Actual Value" /></rich:column>
                                        <rich:column><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.utr}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.beneAccount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.beneName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.receiverIfsc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.reason}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.actualValue}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{mismatchNeftController.createProcessData}" reRender="lblMsg,stxtUtr,stxtBeneAccount,stxtAmount" focus="ddStatus">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{mismatchNeftController.currentItem}"/>                                    
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="gpFooter" columnClasses="col19,col20,col21" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="footerPanel1" layout="block" style="width:100%;text-align:center;">
                            <h:outputText id="footerText" styleClass="output" value="Ctrl+J - Joint Detail" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup id="footerPanel2" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Process" oncomplete="#{rich:component('processPanel')}.show()" reRender="lblMsg,processPanel,tablePanel,taskList" 
                                               disabled="#{mismatchNeftController.disProcessButton}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{mismatchNeftController.resetForm}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{mismatchNeftController.exitBtnAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                        <h:panelGroup/>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:jsFunction name="jtDetails" action="#{mismatchNeftController.validateJointAccount}" 
                                oncomplete="if(#{mismatchNeftController.jtDetailPopUp==true})
                                {#{rich:component('jtHolder')}.show();}else{#{rich:component('jtHolder')}.hide();}" 
                                reRender="jtHolder,lblMsg" />
                <a4j:jsFunction name="populateData" action="#{mismatchNeftController.jtDetails}" reRender="jointDetailPopUpForm"/>
                <rich:hotKey key="Ctrl+J" handler="jtDetails();"/>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to process ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{mismatchNeftController.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide();" 
                                                           reRender="lblMsg,tablePanel,taskList,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="jtHolder" label="Form" width="900" height="400" style="overflow:auto;" resizeable="false" onbeforeshow="populateData();">
                    <f:facet name="header">
                        <h:outputText value="Joint Detail" style="text-align:center;"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:outputLink value="#" onclick="#{rich:component('jtHolder')}.hide(); return false;">X</h:outputLink>
                    </f:facet>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr>
                                <td>
                                    <a4j:include viewId="#{mismatchNeftController.viewID}"></a4j:include>
                                    </td>

                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                    <a4j:commandButton value="Close" id="btnClose" onclick="#{rich:component('jtHolder')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
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
