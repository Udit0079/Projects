<%-- 
    Document   : shareHolder
    Created on : May 23, 2013, 3:23:33 PM
    Author     : Athar Reza
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
            <title>Share Holders Report</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("39/19/9999");
                    jQuery(".toDt").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="shareHolders">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareHolder.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Share Holders Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareHolder.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{ShareHolder.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2">
                        <h:outputText id="lblacType" value="Branch Name:" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{ShareHolder.alphacode}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{ShareHolder.alphacodeList}" />
                        </h:selectOneListbox>                         
                        <h:outputLabel id="lblrepType" styleClass="label" value="Report Option"/>
                        <h:selectOneListbox id="ddrepType" size="1" styleClass="ddlist" value="#{ShareHolder.repType}" style="width:100px;">
                            <f:selectItems value="#{ShareHolder.repTypeList}"/>
                             <a4j:support action="#{ShareHolder.setFocus}" event="onblur" focus="#{ShareHolder.focusId}" reRender="a1,ddmember,txtfrmacno,txtfrmacno12,errorMsg" oncomplete="setMask()"/>    
                        </h:selectOneListbox>
                        <h:outputLabel id="lblmember1" styleClass="label" value="Status"/>
                        <h:selectOneListbox id="ddstatus" size="1" styleClass="ddlist" value="#{ShareHolder.status}" style="width:100px;" disabled = "#{ShareHolder.disableStatus}">
                            <f:selectItems value="#{ShareHolder.statusList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpane333"   style="height:30px;" styleClass="row1" width="100%" > 
                        <h:outputText value="From Account No" styleClass="label"/>
                        <h:panelGroup layout="block">
                         <h:inputText id="txtfrmacno" value="#{ShareHolder.frFolionoShow}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();" disabled = "#{ShareHolder.disableFolio}">
                            <a4j:support action="#{ShareHolder.folioFromMethod}" event="onblur" focus="txtfrmacno12" reRender="stxtFolioFromShow,errorMsg"/>                     
                        </h:inputText>
                        <h:outputText id="stxtFolioFromShow" styleClass="output" value="#{ShareHolder.frAcNo}" />
                        </h:panelGroup>
                        <h:outputText value="To Account No" styleClass="label"/>
                        <h:panelGroup layout="block">
                        <h:inputText id="txtfrmacno12" value="#{ShareHolder.toFolionoShow}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();" disabled = "#{ShareHolder.disableFolio}">
                            <a4j:support action="#{ShareHolder.folioToMethod}" event="onblur" focus="frDt" reRender="stxtFolioFromShow12,errorMsg"/>                     
                        </h:inputText>
                        <h:outputText id="stxtFolioFromShow12" styleClass="output" value="#{ShareHolder.toAcNo}" />
                        </h:panelGroup>
                        <h:outputLabel id="lblmember" styleClass="label" value="Account Catageory"/>
                        <h:selectOneListbox id="ddmember" size="1" styleClass="ddlist" value="#{ShareHolder.accountCat}" style="width:100px;">
                            <f:selectItems value="#{ShareHolder.accountCatList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{ShareHolder.frDt}" />
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input frDt" size="12" value="#{ShareHolder.tillDt}" />
                        <h:outputLabel value="Order By" styleClass="label"/>
                        <h:selectOneListbox id="orderbylist" size="1" value="#{ShareHolder.orderBy}" styleClass="ddlist" style="width:100px;">
                            <f:selectItems value="#{ShareHolder.orderByList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{ShareHolder.viewReport}" reRender="errorMsg" disabled = "#{ShareHolder.disableBtn}"/>
                            <h:commandButton id="btnPrint" value="Excel Download" actionListener="#{ShareHolder.excelPrint}" disabled = "#{ShareHolder.disableBtnExcel}"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ShareHolder.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"disabled = "#{ShareHolder.disableBtn}"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{ShareHolder.refreshPage}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton  id="exit" value="Exit" action="case1"/>
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
