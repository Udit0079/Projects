<%-- 
    Document   : loanIntCert
    Created on : Dec 14, 2011, 7:21:30 PM
    Author     : admin
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
            <title>Loan Interest Certificate </title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="laonRepayment">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanIntCert.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Loan Interest Certificate"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanIntCert.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{LoanIntCert.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id ="panl1" columns="3" columnClasses="col3,col3,col3" styleClass="row11" width="100%">
                        <h:panelGroup id ="typePanel">
                            <h:outputText value="Type " styleClass="label"/>
                            <h:selectOneListbox id="ddType" size="1" value="#{LoanIntCert.type}" styleClass="ddlist">
                                <f:selectItems value="#{LoanIntCert.typeList}"/>
                                <a4j:support action="#{LoanIntCert.onBlurType}" event="onblur" reRender="ddAcType,ddschemeType,txtacno"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup id ="accountPanel"  style="display:#{LoanIntCert.flag1}">
                            <h:outputText value="Account Type" styleClass="label"/>
                            <h:selectOneListbox id="ddAcType" size="1" value="#{LoanIntCert.acType}" styleClass="ddlist"  >
                                <f:selectItems value="#{LoanIntCert.acTypeList}"/>
                                <a4j:support action="#{LoanIntCert.blurAcType()}" event="onblur"  focus="ddschemeType"  reRender="ddschemeType"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup id ="schemePane2"  style="display:#{LoanIntCert.flag1}">
                            <h:outputText value="Scheme Type" styleClass="label" />
                            <h:selectOneListbox id="ddschemeType" size="1" value="#{LoanIntCert.schemeType}" styleClass="ddlist">
                                <f:selectItems value="#{LoanIntCert.schemeTypeList}" />                                
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                        <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{LoanIntCert.flag2}">                        
                        <h:outputText value="Account Number" styleClass="label"  style="disabeld#{LoanIntCert.flag2}" />
                        <h:inputText id="txtacno" value="#{LoanIntCert.oldacNo}" styleClass="input" style="width:100px" maxlength="12"> 
                            <a4j:support event="onblur" action="#{LoanIntCert.getNewAcno}" reRender="stxtNewAcno,errorMsg" focus="txtFrmDate"/>
                        </h:inputText>
                        <h:outputText id="stxtNewAcno" value="#{LoanIntCert.acNo}" styleClass="output" style="color:green" />
                        <h:outputText />
                        <h:outputText />
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{LoanIntCert.frmDt}"/>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtToDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{LoanIntCert.toDt}"/>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  value="Print Report" action="#{LoanIntCert.viewReport}" reRender="errorMsg,txtFrmDate,txtToDate" oncomplete="#{rich:element('txtFrmDate')}.style=setMask();#{rich:element('txtToDate')}.style=setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{LoanIntCert.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton value="Refresh" action="#{LoanIntCert.refresh}" oncomplete="#{rich:element('txtFrmDate')}.style=setMask();#{rich:element('txtToDate')}.style=setMask()" reRender="txtFrmDate,txtToDate,a1"/>
                            <a4j:commandButton   value="Exit" action="#{LoanIntCert.exitAction}" oncomplete="#{rich:element('txtFrmDate')}.style=setMask();#{rich:element('txtToDate')}.style=setMask()" reRender="txtFrmDate,txtToDate,a1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
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
