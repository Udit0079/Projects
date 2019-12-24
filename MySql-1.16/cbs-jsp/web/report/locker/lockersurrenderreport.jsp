<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Locker Surrender Report</title>
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
            <a4j:form id="lockersurrenderreport">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{lockerSurrenderReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Locker Surrender Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{lockerSurrenderReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{lockerSurrenderReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel4" style="text-align:center;" styleClass="row2">
                        <h:outputLabel value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{lockerSurrenderReport.branchCode}" styleClass="ddlist" style="width:70px">
                            <f:selectItems value="#{lockerSurrenderReport.branchCodeList}" /> 
                            <a4j:support event="onblur" actionListener="#{lockerSurrenderReport.onChangeOption}" reRender="ddCabNo"/>
                        </h:selectOneListbox>
                        <%--h:selectOneRadio id="rdType" value="#{lockerSurrenderReport.option}" styleClass="output">
                            <f:selectItem itemValue="Cabinet Wise" itemLabel="Cabinet Wise" />
                            <f:selectItem itemValue="All" itemLabel="All"/>
                            <a4j:support event="onchange" actionListener="#{lockerSurrenderReport.onChangeOption}" reRender="ddCabNo"/>
                        </h:selectOneRadio--%>
                        <h:outputLabel value="Cabinet Number :" styleClass="label"/>
                        <h:selectOneListbox id="ddCabNo" styleClass="ddlist" value="#{lockerSurrenderReport.cabNo}" size="1" style="width:70px" >
                            <f:selectItems value="#{lockerSurrenderReport.cabList}"/>
                        </h:selectOneListbox>

                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{lockerSurrenderReport.frmDt}" style="width:70px">
                            <a4j:support event="onblur" focus="toDt" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" size="12" value="#{lockerSurrenderReport.toDt}" style="width:70px">
                            <a4j:support event="onblur" focus="print" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{lockerSurrenderReport.printAction}" type="submit" value="Print Report" reRender="errmsg"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{lockerSurrenderReport.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <a4j:commandButton id="btnReset" actionListener="#{lockerSurrenderReport.resetAction}" type="reset" value="Reset" reRender="PanelGridMain"/>
                            <a4j:commandButton id="btnExit" action="#{lockerSurrenderReport.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>