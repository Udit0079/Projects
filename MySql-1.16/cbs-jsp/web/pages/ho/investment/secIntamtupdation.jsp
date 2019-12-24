<%-- 
    Document   : secIntamtupdation
    Created on : Aug 27, 2013, 10:37:27 AM
    Author     : sipl
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Interest Received Amount Updation</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{InterestSecAmtUpdation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Interest Received Amount Updation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{InterestSecAmtUpdation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{InterestSecAmtUpdation.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="ctrlNoGrid" columns="4" columnClasses="col1,col2,col1,col2" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No"/>
                        <h:outputText id="stxtCtrlNo" styleClass="output" value="#{InterestSecAmtUpdation.controlNo}"/>
                        <h:outputLabel id="lblTotReceAmt" styleClass="label" value="Total Rec. Amount"/>
                        <h:inputText id="txtTotReceAmt" styleClass="input" value="#{InterestSecAmtUpdation.txtReceivedAmount}" disabled="#{InterestSecAmtUpdation.txtVisible}">
                            <a4j:support event="onblur" action="#{InterestSecAmtUpdation.amountProcess}" reRender="message,debitAcnoGrid,creditAcnoGrid" focus="btnProcess"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="debitAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblDebitAcno" styleClass="label" value="Debit A/c No."></h:outputLabel>
                        <h:outputText id="stxtDebitAcno" styleClass="output" value="#{InterestSecAmtUpdation.drAcno}"/>
                        <h:outputText id="stxtDrHeadName" value="#{InterestSecAmtUpdation.drHeadName}" styleClass="output"/>
                        <h:outputLabel id="lblDebitAmt" styleClass="label" value="Total Debit Amount"></h:outputLabel>
                        <h:outputText id="stxtDebitAmt" styleClass="output" value="#{InterestSecAmtUpdation.drAmt}"/>
                        <h:outputLabel/>                        
                    </h:panelGrid>
                    <h:panelGrid id="creditAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCreditAcno" styleClass="label" value="Credit A/c No."></h:outputLabel>    
                        <h:outputText id="stxtCreditAcno" styleClass="output" value="#{InterestSecAmtUpdation.crAcno}"/>
                        <h:outputText id="stxtCrHeadName" value="#{InterestSecAmtUpdation.crHeadName}" styleClass="output"/>
                        <h:outputLabel id="lblCreditAmt" styleClass="label" value="Total Credit Amount"></h:outputLabel>
                        <h:outputText id="stxtCreditAmt" styleClass="output" value="#{InterestSecAmtUpdation.crAmt}"/>
                        <h:outputLabel/>                        
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{InterestSecAmtUpdation.tableList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="13"><h:outputText value="Control Number Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Control No."/></rich:column>
                                        <rich:column><h:outputText value="Last Int. Paid Date"/></rich:column>
                                        <rich:column><h:outputText value="Total Int. Amount Rec."/></rich:column>
                                        <rich:column><h:outputText value="Purchase Date"/></rich:column>
                                        <rich:column><h:outputText value="Maturity Date"/></rich:column>
                                        <rich:column><h:outputText value="Face Value"/></rich:column>
                                        <rich:column><h:outputText value="Roi"/></rich:column>
                                        <rich:column><h:outputText value="Seller Name"/></rich:column>
                                        <rich:column><h:outputText value="Security Desc."/></rich:column>
                                        <rich:column><h:outputText value="Maturity Value"/></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lastIntPaidDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totAmtIntRec}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.purchaseDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.faceValue}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sellerName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bookValue}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{InterestSecAmtUpdation.setTableDataToForm}" reRender="message,stxtCtrlNo,txtTotReceAmt,btnProcess" focus="txtTotReceAmt">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InterestSecAmtUpdation.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Update" oncomplete="#{rich:component('processPanel')}.show()" 
                                               disabled="#{InterestSecAmtUpdation.processVisible}" reRender="message,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InterestSecAmtUpdation.resetForm}" reRender="message,stxtCtrlNo,txtTotReceAmt,btnProcess,debitAcnoGrid,creditAcnoGrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InterestSecAmtUpdation.exitBtnAction}" reRender="message,stxtCtrlNo,txtTotReceAmt,btnProcess,debitAcnoGrid,creditAcnoGrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to update ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{InterestSecAmtUpdation.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="message,stxtCtrlNo,txtTotReceAmt,tablePanel,taskList,btnProcess"/>
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