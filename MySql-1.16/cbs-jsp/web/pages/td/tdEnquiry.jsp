<%-- 
    Document   : TdEnquiry
    Created on : Nov 9, 2010, 2:08:04 PM
    Author     : root
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Td Enquiry</title>
        </head>
        <body>
            <a4j:form id="tdEnquiry">
                <h:panelGrid id="tdEnquiry" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Term Deposit Enquiry"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col7,col2,col7,col2" columns="4" id="tdenquirypanelgridselect" width="100%" styleClass="row1">
                        <h:outputLabel id="tdenquirylabel" styleClass="label" value="Select Reveiw By Amount And Interest"/>
                        <h:selectOneListbox  id="tdEnquirygridlistboxold" styleClass="ddlist" size="1" style="width:120px" value="#{TdEnquiry.item1}">
                            <f:selectItems value="#{TdEnquiry.list1}"/>
                            <a4j:support event="onchange"  actionListener="#{TdEnquiry.onChange}" reRender="out1,out2,msgouttext,inText1,tdEnquirygridlistbox,tdEnquiryIUnput1,tdEnquirygridlistbox3,tdenquiryinput4,investrsout"/>
                        </h:selectOneListbox>
                        <h:outputText id="out1" styleClass="output" value="#{TdEnquiry.varVisisble}"/>
                        <h:inputText  id="inText1" styleClass="input" style="width:90px" value="#{TdEnquiry.amount}" maxlength="10">
                            <a4j:support actionListener="#{TdEnquiry.onblurAmount}" event="onblur" reRender="msgouttext"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col7,col2,col7,col2" columns="4" id="tdMQPayment" width="100%" styleClass="row2">
                        <h:outputLabel id="pTInq" value="Type Of Inquiry" styleClass="output"/>
                        <h:selectOneListbox id="tdInqListbox" styleClass="ddlist" size="1" value="#{TdEnquiry.mqPayment}">
                            <f:selectItems value="#{TdEnquiry.mqPaymentList}"/>
                            <a4j:support event="onblur" actionListener="#{TdEnquiry.onBlurMQpayment}" reRender="msgouttext,aa,mqMonthTxt,out2,investrsout" focus="#{TdEnquiry.focusVar}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="mqMonth" value="Months" styleClass="output"/>
                        <h:inputText id="mqMonthTxt" styleClass="input" style="width:90px" value="#{TdEnquiry.mqMonth}" disabled="#{TdEnquiry.disMqMonth}"/>
                    </h:panelGrid>
                    <h:panelGrid  columnClasses="col9,col9" id="aa" columns="2" width="100%">
                        <rich:panel header="Duration In" style="text-align:left;">
                            <h:panelGrid  columnClasses="col9,col9" id="gridPanelnew1" columns="2"  width="100%" styleClass="row1">                       
                                <h:outputLabel id="tdEnquiryOutput" value="Select Duration" styleClass="output"/>
                                <h:panelGroup>
                                    <h:selectOneListbox id="tdEnquirygridlistbox" styleClass="ddlist" size="1" style="width:100px" value="#{TdEnquiry.item2}" disabled="#{TdEnquiry.disDuration}">
                                        <f:selectItems  value="#{TdEnquiry.list2}"/>
                                        <a4j:support event="onchange"/>
                                    </h:selectOneListbox>
                                    <h:inputText id="tdEnquiryIUnput1" styleClass="input" style="width:80px;text-align:left;" value="#{TdEnquiry.txtduration}" maxlength="4" disabled="#{TdEnquiry.disDurationTxt}">
                                        <a4j:support actionListener="#{TdEnquiry.onblurDuration}" event="onblur" reRender="msgouttext"/>
                                    </h:inputText>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:panel>
                        <rich:panel header="Interest Options" style="text-align:left;">
                            <h:panelGrid  columnClasses="col9,col9" id="tdEnquirygrid" columns="2"  width="100%"  styleClass="row1">                        
                                <h:outputLabel id="out4" value="Select Interest Option" styleClass="output"/>
                                <h:panelGroup>
                                    <h:selectOneListbox id="tdEnquirygridlistbox3" styleClass="ddlist" size="1" style="width:100px" value="#{TdEnquiry.item3}" disabled="#{TdEnquiry.disIntOpt}">
                                        <f:selectItems  value="#{TdEnquiry.list3}"/>
                                        <a4j:support event="onchange"/>
                                    </h:selectOneListbox>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:panel>
                    </h:panelGrid>
                    <h:panelGrid  columnClasses="col7,col2,col7,col2" columns="4" id="tdEnquirypanel8" width="100%"  styleClass="row2">
                        <h:outputLabel id="out5" value="Enter the rate of interest" styleClass="output"/>
                        <h:inputText disabled="" id="tdenquiryinput4" styleClass="input" style="width:80px" value="#{TdEnquiry.rateInterest}" maxlength="5">
                            <a4j:support actionListener="#{TdEnquiry.onblurRoi}" event="onblur" reRender="msgouttext"/>
                        </h:inputText>
                        <h:outputText id="out2" styleClass="output" value="#{TdEnquiry.varVisisble1}"/>
                        <h:outputLabel id="investrsout" value="#{TdEnquiry.investRs}" styleClass="output"/>
                    </h:panelGrid>
                   
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="msggridpanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="msgouttext" styleClass="error" value="#{TdEnquiry.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  reRender="msgouttext,investrsout,tdenquiryinput4,inText1,tdEnquiryIUnput1" id="btnIssue" value="Calculate" action="#{TdEnquiry.btnCalculateAction}"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{TdEnquiry.resetForm}" reRender="msgouttext,investrsout,tdenquiryinput4,inText1,tdEnquiryIUnput1"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TdEnquiry.exitBtnAction}" reRender="msgouttext,investrsout,tdenquiryinput4,inText1,tdEnquiryIUnput1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

