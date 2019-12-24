<%-- 
    Document   : intreceamtupdation
    Created on : Jul 2, 2013, 6:46:27 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Update Interest Posted</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{interestReceAmtUpdation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Update Interest Posted"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{interestReceAmtUpdation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{interestReceAmtUpdation.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="ctrlNoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="intOptLbl" styleClass="label" value="Interest Option"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddIntOption" styleClass="ddlist" style="width: 100px" value="#{interestReceAmtUpdation.interestOption}" size="1" disabled="#{interestReceAmtUpdation.disIntOpt}">
                            <f:selectItems value="#{interestReceAmtUpdation.interestOptionList}" />
                            <a4j:support event="onblur" action="#{interestReceAmtUpdation.getDataToUpdate}" reRender="message,tableList,tablePanel,ddIntOption"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No"/>
                        <h:outputText id="stxtCtrlNo" styleClass="output" value="#{interestReceAmtUpdation.controlNo}"/>
                        <h:outputLabel id="lblTotReceAmt" styleClass="label" value="Total Rec. Amount"/>
                        <h:inputText id="txtTotReceAmt" styleClass="input" value="#{interestReceAmtUpdation.txtReceivedAmount}">
                            <a4j:support event="onblur" action="#{interestReceAmtUpdation.amountProcess}" reRender="message,debitAcnoGrid,creditAcnoGrid,stxtDrHeadName,stxtCrHeadName,btnProcess" focus="btnProcess"/>
                        </h:inputText>                        
                    </h:panelGrid>
                    <h:panelGrid id="debitAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblDebitAcno" styleClass="label" value="Debit A/c No."></h:outputLabel>
                        <h:outputText id="stxtDebitAcno" styleClass="output" value="#{interestReceAmtUpdation.drAcno}"/>
                        <h:outputText id="stxtDrHeadName" value="#{interestReceAmtUpdation.drHeadName}" styleClass="output"/>
                        <h:outputLabel id="lblDebitAmt" styleClass="label" value="Total Debit Amount"></h:outputLabel>
                        <h:outputText id="stxtDebitAmt" styleClass="output" value="#{interestReceAmtUpdation.drAmt}"/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="creditAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCreditAcno" styleClass="label" value="Credit A/c No."></h:outputLabel>    
                        <h:outputText id="stxtCreditAcno" styleClass="output" value="#{interestReceAmtUpdation.crAcno}"/>
                        <h:outputText id="stxtCrHeadName" value="#{interestReceAmtUpdation.crHeadName}" styleClass="output"/>
                        <h:outputLabel id="lblCreditAmt" styleClass="label" value="Total Credit Amount"></h:outputLabel>
                        <h:outputText id="stxtCreditAmt" styleClass="output" value="#{interestReceAmtUpdation.crAmt}"/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{interestReceAmtUpdation.tableList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="13"><h:outputText value="Control Number Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Control No."/></rich:column>
                                        <rich:column><h:outputText value="Last Int. Paid Date"/></rich:column>
                                        <rich:column><h:outputText value="Int. Option"/></rich:column>
                                        <rich:column><h:outputText value="Total Int. Amount Rec."/></rich:column>
                                        <rich:column><h:outputText value="Purchase Date"/></rich:column>
                                        <rich:column><h:outputText value="Maturity Date"/></rich:column>
                                        <rich:column><h:outputText value="Face Value"/></rich:column>
                                        <rich:column><h:outputText value="Roi"/></rich:column>
                                        <rich:column><h:outputText value="Seller Name"/></rich:column>
                                        <rich:column><h:outputText value="Security Desc."/></rich:column>
                                        <rich:column><h:outputText value="Maturity Value"/></rich:column>
                                        <rich:column><h:outputText value="FDR No."/></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lastIntPaidDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totAmtIntRec}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.purchaseDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.faceValue}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sellerName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bookValue}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fdrNo}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{interestReceAmtUpdation.setTableDataToForm}" reRender="message,stxtCtrlNo,txtTotReceAmt,btnProcess" focus="txtTotReceAmt">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{interestReceAmtUpdation.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Update" oncomplete="#{rich:component('processPanel')}.show()" 
                                               disabled="#{interestReceAmtUpdation.processVisible}" reRender="message,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{interestReceAmtUpdation.resetForm}" reRender="message,stxtCtrlNo,txtTotReceAmt,btnProcess,debitAcnoGrid,creditAcnoGrid,stxtDrHeadName,stxtCrHeadName,ddIntOption,tableList,tablePanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{interestReceAmtUpdation.exitBtnAction}" reRender="message,stxtCtrlNo,txtTotReceAmt,btnProcess,debitAcnoGrid,creditAcnoGrid"/>
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
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{interestReceAmtUpdation.processAction}" 
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
