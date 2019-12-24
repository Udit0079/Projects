<%-- 
    Document   : onus
    Created on : Dec 12, 2011, 6:18:22 PM
    Author     : Sudhir
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
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>OnUs report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{OnUs.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAccountMaintenanceRegister" styleClass="headerLabel" value="On-Us Report"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{OnUs.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorMsgReporting" style="display:none;text-align:center" styleClass="row1" width="100%">
                        <h:outputText id="errorGrid" styleClass="error" value="#{OnUs.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6" id="mainPanel1" width="100%" styleClass="row2" style="text-align:center">
                        <h:outputLabel id="reportType" styleClass="label" value="Transaction Type"/>
                        <h:selectOneListbox  required="true" id="reportTypeList"  style="width:100px;" styleClass="ddlist" size="1" value="#{OnUs.transactionType}"> 
                            <f:selectItems value="#{OnUs.transactionTypeList}"/>
                            <a4j:support ajaxSingle="true" event="onblur" focus="calInstDate"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="date" styleClass="label" value="Date"/>
                        <h:inputText id="calInstDate" styleClass="input calInstDate" style="width:70px;" value="#{OnUs.trandate}">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                            <a4j:support ajaxSingle="true" event="onblur" focus="checkBox"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6" id="bodyPanel" style="text-align:center" width="100%" styleClass="row1">
                        <h:outputLabel id="timebased" styleClass="label" value="Time based Report"/>
                        <h:selectBooleanCheckbox id="checkBox" value="#{OnUs.checkBox}">
                            <a4j:support ajaxSingle="true" event="onclick" action="#{OnUs.onClickCheckBox()}" focus="btnReport" reRender="bodyPanel,errorMsgReporting" 
                                         oncomplete="if(#{OnUs.checkBox==true}) {#{rich:element('from')}.style.display='';#{rich:element('frmPanel')}.style.display='';#{rich:element('to')}.style.display='';#{rich:element('toPanel')}.style.display='';}"/>
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="from" value="From" styleClass="label" style="display:none;"/>
                        <h:panelGroup id="frmPanel" style="display:none;">
                            <h:outputLabel id="hhlabel" value="HH" styleClass="label"/>
                            <h:selectOneListbox id="hhlist"  style="width:40px;" styleClass="ddlist" size="1" value="#{OnUs.fromHour}"> 
                                <f:selectItems value="#{OnUs.fromHourList}"/>
                                <a4j:support ajaxSingle="true" event="onblur"/> 
                            </h:selectOneListbox>
                            <h:outputLabel id="mmlabel" value="MM" styleClass="label"/>
                            <h:selectOneListbox id="mmlist"  style="width:40px;" styleClass="ddlist" size="1" value="#{OnUs.fromMinutes}"> 
                                <f:selectItems value="#{OnUs.fromMinuteList}"/> 
                                <a4j:support ajaxSingle="true" event="onblur"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="timelist"  style="width:40px;" styleClass="ddlist" size="1" value="#{OnUs.fromTime}"> 
                                <f:selectItems value="#{OnUs.fromTimeList}"/> 
                                <a4j:support ajaxSingle="true" event="onblur"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="to" value="To" styleClass="label" style="display:none;"/>
                        <h:panelGroup id="toPanel" style="display:none;">
                            <h:outputLabel id="thhlabel" value="HH" styleClass="label"/>
                            <h:selectOneListbox id="thhlist"  style="width:40px;" styleClass="ddlist" size="1" value="#{OnUs.toHour}"> 
                                <f:selectItems value="#{OnUs.toHourList}"/>
                                <a4j:support ajaxSingle="true" event="onblur"/> 
                            </h:selectOneListbox>
                            <h:outputLabel id="tmmlabel" value="MM" styleClass="label"/>
                            <h:selectOneListbox id="tmmlist"  style="width:40px;" styleClass="ddlist" size="1" value="#{OnUs.toMinutes}"> 
                                <f:selectItems value="#{OnUs.toMinuteList}"/>
                                <a4j:support ajaxSingle="true" event="onblur"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="ttimelist"  style="width:40px;" styleClass="ddlist" size="1" value="#{OnUs.toTime}"> 
                                <f:selectItems value="#{OnUs.toTimeList}"/>
                                <a4j:support ajaxSingle="true" event="onblur"/> 
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block">
                            <a4j:commandButton ajaxSingle="true" id="btnReport" value="Print Report" action="#{OnUs.printReport()}" 
                                               oncomplete="if(#{OnUs.flag=='false'}) {#{rich:element('errorMsgReporting')}.style.display='';}" reRender="errorMsgReporting"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{OnUs.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{OnUs.exitFrm()}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status  onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>

        </body>
    </html>
</f:view>