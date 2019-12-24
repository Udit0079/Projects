<%-- 
    Document   : noticeToBorrower
    Created on : Sep 18, 2014, 3:51:00 PM
    Author     : Athar Raza
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
            <title>Notice To Borrower</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NoticeToBorrower.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Notice To Borrower"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NoticeToBorrower.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row1" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{NoticeToBorrower.msg}"/>
                    </h:panelGrid>  
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputLabel value="Branch Wise" styleClass="label" style="display:"/>
                        <h:selectOneListbox id="branch" size="1" value="#{NoticeToBorrower.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{NoticeToBorrower.branchCodeList}" /> 
                        </h:selectOneListbox>         
                        <h:outputText value="Report Type"styleClass="label" />
                        <h:selectOneListbox id="ddacType" size="1" value="#{NoticeToBorrower.repType}" styleClass="ddlist" style="width: 100px" >
                            <f:selectItems value="#{NoticeToBorrower.repTypeList}"/>
                            <a4j:support  action="#{NoticeToBorrower.changeFunction}" event="onblur"
                                          reRender="lblAcType,txtAccno,selectListBox,selectAcTypeList" oncomplete="setMask();"/>
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblAcType" styleClass="headerLabel"  value="#{NoticeToBorrower.output}"  />
                        <h:panelGroup layout="block">
                            <h:inputText id="txtAccno" value="#{NoticeToBorrower.acNo}" size="10" style="display:#{NoticeToBorrower.displayAcNo}">
                                <a4j:support  action="#{NoticeToBorrower.acnoBlur}" event="onblur"
                                              reRender="newAcNo,stxtError" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="newAcNo" value="#{NoticeToBorrower.newAcNo}" styleClass="output" style="color:green"/>
                            <h:selectOneListbox id="selectListBox" value="#{NoticeToBorrower.acType}" styleClass="ddlist"  style="display:#{NoticeToBorrower.displayAcType};width:70px;" size="1" >
                                <f:selectItems id="selectAcTypeList" value="#{NoticeToBorrower.acTypeList}" />
                            </h:selectOneListbox>
                        </h:panelGroup>   
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%">
                        <h:outputLabel value="No of Days " styleClass="label"/>
                        <h:inputText id="txtFrom" value="#{NoticeToBorrower.day}" size="8" />
                        <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="As On Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{NoticeToBorrower.date}"/>
                            <%--font color="purple">DD/MM/YYYY</font--%>
                        </h:panelGroup> 
                        <h:outputLabel value="Report Base" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{NoticeToBorrower.reportType}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{NoticeToBorrower.reportTypeList}"/> 
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col10,col11" style="text-align:left;width:100%" width="100%" styleClass="footer" >
                        <%--h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" --%> 
                        <h:outputText id="codeId" value="#{NoticeToBorrower.slabDay}" styleClass="output" style="color:red;align:left" /> 
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnView" value="Print Report"   action="#{NoticeToBorrower.viewReport}" reRender="stxtError,mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{NoticeToBorrower.refreshForm}"  reRender="mainPanelGrid,txtDate" oncomplete="setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{NoticeToBorrower.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
