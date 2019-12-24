<%-- 
    Document   : SecurityAuth
    Created on : Jul 29, 2015, 6:35:37 PM
    Author     : Administrator
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Authorize Security Detail</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{SecurityAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Authorization Of Security Details"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SecurityAuth.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="lpg" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="message" styleClass="msg" value="#{SecurityAuth.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="msgStatus" style="width:100%;text-align:center;display:#{SecurityAuth.flag3}" styleClass="row2">
                        <h:outputText id="stxtMsgStatus" styleClass="blink_text" style="color:red;"value="#{SecurityAuth.alertMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,co8" columns="2" id="gridPanel14" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputLabel id="label9" styleClass="label" style="width:100%;text-align:center;" value="Details Of Following Control Need To Be Authorized"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,co8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="labelAcno" styleClass="label" style="padding-left:350px" value="Control No"/>
                        <h:selectOneListbox id="ddCtrlNo" styleClass="ddlist" size="1" style="width:100px" value="#{SecurityAuth.ctrlNo}" tabindex="1">
                            <f:selectItem itemValue="--SELECT--"/>
                            <f:selectItems value="#{SecurityAuth.pendingCtrlNoList}" />
                            <a4j:support actionListener="#{SecurityAuth.getList}" event="onblur" reRender="taskList,taskList1,message,msgStatus" limitToList="true"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{SecurityAuth.secFirst}" var="dataItem" rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="13"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Control No"/></rich:column>
                                        <rich:column><h:outputText value="Security Type" /></rich:column>
                                        <rich:column><h:outputText value="Security Detail" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                        <rich:column><h:outputText value="Seller Name" /></rich:column>
                                        <rich:column><h:outputText value="Code/Cert. No." /></rich:column>
                                        <rich:column><h:outputText value="Issue Date" /></rich:column>
                                        <rich:column><h:outputText value="Purchase Date" /></rich:column>
                                        <rich:column><h:outputText value="Transaction Date" /></rich:column>
                                        <rich:column><h:outputText value="Settlement Date" /></rich:column>
                                        <rich:column><h:outputText value="1st Intt. Payable Date" /></rich:column>
                                        <rich:column><h:outputText value="2nd Intt. Payable Date" /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secTp}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secDetail}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sellerName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.codeNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.issDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.purDT}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.trnDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.settleDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fIntPayDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sIntPayDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{SecurityAuth.secFirst}" var="dataItem" align="center" rowClasses="gridrow1,gridrow2" id="taskList1" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="13"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"> <h:outputText value="No Of Share"/></rich:column>
                                        <rich:column><h:outputText value="Constituent A/C No." /></rich:column>
                                        <rich:column><h:outputText value="Issued Unit" /></rich:column>
                                        <rich:column><h:outputText value="Issuing Price" /></rich:column>
                                        <rich:column><h:outputText value="Face Value" /></rich:column>
                                        <rich:column><h:outputText value="Book Value" /></rich:column>
                                        <rich:column><h:outputText value="YTM" /></rich:column>
                                        <rich:column><h:outputText value="Marking" /></rich:column>
                                        <rich:column><h:outputText value="Accrued Interest" /></rich:column>
                                        <rich:column><h:outputText value="Constituent SGL A/C" /></rich:column>
                                        <rich:column><h:outputText value="Broker A/C No." /></rich:column>
                                        <rich:column><h:outputText value="Brokerage Amount" /></rich:column>
                                        <rich:column><h:outputText value="Credit GL Head" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.noOfShare}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.conAcNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.issueUnit}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.issuePrice}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.faceValue}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bookValue}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ytm}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.marking}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accrInt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.conSglAc}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.brokerAc}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.brokerageAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.crGlHead}"/></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>          
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" oncomplete="#{rich:component('authorizePanel')}.show()"/>
                            <a4j:commandButton id="btnDelete" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SecurityAuth.clearText}" reRender="taskList,taskList1,ddCtrlNo,lpg,message"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SecurityAuth.exitFrm}" reRender="taskList,taskList1,ddCtrlNo,message"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:outputText value="Processing" />
                    </f:facet>
                    <h:outputText value="Wait Please..." />
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
            <rich:modalPanel id="authorizePanel" autosized="true" width="200" onshow="btnYes">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Authorize?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{SecurityAuth.ctrlAuth}"
                                                       oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="taskList,taskList1,ddCtrlNo,message,stxtMsgStatus,msgStatus" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="btnYes">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete?" style="padding-right:15px;"/>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnDelYes" ajaxSingle="true" action="#{SecurityAuth.ctrlDelete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,taskList1,ddCtrlNo,message,stxtMsgStatus,msgStatus" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>