<%-- 
    Document   : voucherPrinting
    Created on : Apr 12, 2017, 12:12:07 PM
    Author     : Admin
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
            <title>Voucher Printing</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="loanAcStateme">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{VoucherPrinting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Voucher Printing"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{VoucherPrinting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{VoucherPrinting.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel4"  styleClass="row2"> 
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{VoucherPrinting.branch}" styleClass="ddlist">
                            <f:selectItems value="#{VoucherPrinting.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Txn Mode" styleClass="label"/>
                        <h:selectOneListbox id="txnId" size="1" value="#{VoucherPrinting.txnMode}" styleClass="ddlist">
                            <f:selectItems value="#{VoucherPrinting.txnModeList}" />
                            <a4j:support event="onchange" action="#{VoucherPrinting.onReportAction}" reRender="lId,vId"/>
                        </h:selectOneListbox>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5"  styleClass="row1">
                        <%--h:outputText value="Print Type" styleClass="label"/>
                        <h:selectOneListbox id="repId" size="1" value="#{VoucherPrinting.prinType}" styleClass="ddlist">
                            <f:selectItems value="#{VoucherPrinting.prinTypList}" />
                            <a4j:support event="onblur" action="#{VoucherPrinting.onReportAction}" focus="view" reRender="newAcno"/>
                        </h:selectOneListbox--%>
                        <h:outputText id="lId"value="#{VoucherPrinting.lableButtom}" styleClass="label"/>
                        <h:selectOneListbox id="vId" size="1" value="#{VoucherPrinting.lable}" styleClass="ddlist">
                            <f:selectItems value="#{VoucherPrinting.lableList}" />
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <%--h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel6"  styleClass="row2">
                        <h:outputText value="Date" styleClass="label"/>
                        <h:inputText id="txtFrmDate"   styleClass="input frDt" style="width:70px;"  value="#{VoucherPrinting.date}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid--%>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnPrint" value="Printing" actionListener="#{VoucherPrinting.printReport}"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{VoucherPrinting.refreshPage}" oncomplete="setMask()" reRender="a1,errorMsg"/>
                            <a4j:commandButton  id="exit" value="Exit" action="#{VoucherPrinting.exitPage}"reRender="a1,errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
