<%-- 
    Document   : TermDepositeLienMarking
    Created on : Dec 1, 2010, 11:39:47 AM
    Author     : ROHIT KRISHNA GUPTA
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Term Deposite Lien Marking</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{TermDepositeLienMarking.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Term Deposite Lien Marking"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{TermDepositeLienMarking.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{TermDepositeLienMarking.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{TermDepositeLienMarking.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                        <h:inputText id="txtAcNo" tabindex="1" maxlength="#{TermDepositeLienMarking.acNoMaxLen}" value="#{TermDepositeLienMarking.oldAcctNo}" styleClass="input" style="width:90px">
                            <a4j:support actionListener="#{TermDepositeLienMarking.customerDetail}" event="onblur" focus="ddType"
                                         reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList,stxtNewAcno" limitToList="true" />
                        </h:inputText>
                        <h:outputText id="stxtNewAcno" styleClass="output" value="#{TermDepositeLienMarking.acctNo}" style="color:green;"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" />
                        <h:outputText id="stxtCustName" styleClass="output" value="#{TermDepositeLienMarking.custName}" style="color:purple;"/>
                        <h:outputLabel id="lblJtName" styleClass="label" value="Joint Name :" />
                        <h:outputText id="stxtJtName" styleClass="output" value="#{TermDepositeLienMarking.jtName}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblOprMode" styleClass="label" value="Operation Mode :" />
                        <h:outputText id="stxtOprMode" styleClass="output" value="#{TermDepositeLienMarking.oprMode}" style="color:purple;"/>
                        <h:outputLabel id="lblType" styleClass="label" value="Type :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddType" tabindex="2" styleClass="ddlist" value="#{TermDepositeLienMarking.type}" size="1" style="width: 127px">
                            <f:selectItems value="#{TermDepositeLienMarking.typeList}" />
                           
                        </h:selectOneListbox>
                        <h:outputLabel id="lblHide1" value="" />
                        <h:outputLabel id="lblHide2" value="" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{TermDepositeLienMarking.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="A/C. No." /></rich:column>
                                        <rich:column><h:outputText value="R.T. No." /></rich:column>
                                        <rich:column><h:outputText value="Reciept No." /></rich:column>
                                        <rich:column><h:outputText value="Control No." /></rich:column>
                                        <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                        <rich:column><h:outputText value="TD Made Date" /></rich:column>
                                        <rich:column><h:outputText value="TD Date (wef)" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                        <rich:column><h:outputText value="Int Opt" /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column visible="false"><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Lien Mark" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.reciept}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.printAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fddt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tdmatDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column visible="false"><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lien}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{TermDepositeLienMarking.fillValuesofGridInFields}" 
                                                     reRender="a5,a6,message,errorMessage,gpFooter,table,taskList" >
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{TermDepositeLienMarking.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{TermDepositeLienMarking.currentRow}"/>
                                    </a4j:commandLink>
                                   
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblRecieptNo" styleClass="label" value="Reciept No. :" />
                        <h:outputText id="stxtRecieptNo" styleClass="output" value="#{TermDepositeLienMarking.recieptNo}" style="color:purple;"/>
                        <h:outputLabel id="lblControlNo" styleClass="label" value="Control No. :" />
                        <h:outputText id="stxtControlNo" styleClass="output" value="#{TermDepositeLienMarking.controlNo}" style="color:purple;"/>
                        <h:outputLabel id="lblPrinAmt" styleClass="label" value="Present Amount :" />
                        <h:outputText id="stxtPrinAmt" styleClass="output" value="#{TermDepositeLienMarking.prinAmount}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblLienStatus" styleClass="label" value="Lien Status :" />
                        <h:outputText id="stxtLienStatus" styleClass="output" value="#{TermDepositeLienMarking.status}" style="color:purple;"/>
                        <h:outputLabel id="lblLienMarking" styleClass="label" value="Lien Marking :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddLienMarking" tabindex="3" styleClass="ddlist" value="#{TermDepositeLienMarking.lienMarkOption}" size="1" style="width: 127px">
                            <f:selectItems value="#{TermDepositeLienMarking.lienMarkOptionList}" />
                           
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDetails" styleClass="label" value="Details :" />
                        <h:inputText id="txtDetails" tabindex="4" size="30" value="#{TermDepositeLienMarking.detail}" onblur="this.value = this.value.toUpperCase();" styleClass="input">
                          
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="5" value="Save" disabled="#{TermDepositeLienMarking.flag1}" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnRefresh" tabindex="6" value="Refresh" action="#{TermDepositeLienMarking.resetForm}" oncomplete="#{rich:element('btnSave')}.disabled = true;" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList" focus="txtAcNo"/>
                            <a4j:commandButton id="btnExit" tabindex="7" value="Exit" action="#{TermDepositeLienMarking.exitBtnAction}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are You sure to save ?"/>
                                    </td>
                                </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{TermDepositeLienMarking.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList" focus="txtAcNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
