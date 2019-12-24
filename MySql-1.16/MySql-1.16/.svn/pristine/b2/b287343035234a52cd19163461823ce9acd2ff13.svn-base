<%-- 
    Document   : LockerEnquiry
    Created on : Sep 17, 2010, 4:51:22 PM
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
            <title>Locker Enquiry</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LockerEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Locker Enqiury"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LockerEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LockerEnquiry.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LockerEnquiry.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblSearch" styleClass="label" value="Search Wise :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSearch" tabindex="1" styleClass="ddlist" value="#{LockerEnquiry.searchWise}" size="1" style="width: 150px">
                            <f:selectItems value="#{LockerEnquiry.searchWiseList}"/>
                            <a4j:support ajaxSingle="true" event="onchange" oncomplete="if(#{rich:element('ddSearch')}.value == 'Lesse Name Wise'){#{rich:element('txtDebCustName')}.focus();}
                                         else if(#{rich:element('ddSearch')}.value == 'Locker No. Wise'){#{rich:element('txtLockerNo')}.focus();}
                                         else if(#{rich:element('ddSearch')}.value == 'Key No. Wise'){#{rich:element('txtKeyNo')}.focus();}
                                         else{#{rich:element('btnRefresh')}.focus();}"
                                         action="#{LockerEnquiry.searchWiseMethod}" reRender="a4,message,errorMessage" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%" style="height:30px;" styleClass="row2">
                        <h:outputLabel id="lblSearchChange" styleClass="label" value="#{LockerEnquiry.lableForChange}" style="padding-left:350px;"/>
                        <h:inputText id="txtDebCustName" maxlength="40" size="15" tabindex="2" value="#{LockerEnquiry.debtCustName}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerEnquiry.searchWise == 'Lesse Name Wise' ||LockerEnquiry.searchWise == 'Joint Holder Wise'}"/>
                        <h:inputText id="txtLockerNo" maxlength="6" size="15" tabindex="2" value="#{LockerEnquiry.debtCustName}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerEnquiry.searchWise == 'Locker No. Wise'}"/>
                        <h:inputText id="txtKeyNo" maxlength="6" size="15" tabindex="2" value="#{LockerEnquiry.debtCustName}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerEnquiry.searchWise == 'Key No. Wise'}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a5" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LockerEnquiry.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Cabinet No." /></rich:column>
                                        <rich:column><h:outputText value="Locker Type" /></rich:column>
                                        <rich:column><h:outputText value="Locker No." /></rich:column>
                                        <rich:column><h:outputText value="Debited Account No." /></rich:column>
                                        <rich:column><h:outputText value="Rent" /></rich:column>
                                        <rich:column><h:outputText value="Rent Due Dt." /></rich:column>
                                        <rich:column><h:outputText value="Debited Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Key No." /></rich:column>
                                        <rich:column><h:outputText value="Ad. Operator 1" /></rich:column>
                                        <rich:column><h:outputText value="Ad. Operator 2" /></rich:column>
                                        <rich:column><h:outputText value="Ad. Operator 3" /></rich:column>
                                        <rich:column><h:outputText value="Ad. Operator 4" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.cabiNateNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lockerType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lockerNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debitedAcNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rent}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rentDueDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debitedCustName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.keyNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.adOpr1}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.adOpr2}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.adOpr3}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.adOpr4}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSearch" tabindex="3" value="Search" action="#{LockerEnquiry.gridLoad}"  reRender="message,errorMessage,lpg,a5,taskList" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" tabindex="4" value="Refresh" action="#{LockerEnquiry.resetForm}"  reRender="message,errorMessage,lpg,a3,a4,a5,taskList" focus="ddSearch"/>
                            <a4j:commandButton id="btnExit" tabindex="5" value="Exit" action="#{LockerEnquiry.exitFrm}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
