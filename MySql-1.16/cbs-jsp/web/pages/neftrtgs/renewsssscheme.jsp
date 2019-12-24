<%-- 
    Document   : sssregistrationdetails
    Created on : Jun 4, 2015, 2:19:32 PM
    Author     : Admin
--%>
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>SSS Renew Process</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{renewSssSchemeBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="SSS Renew Process"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{renewSssSchemeBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{renewSssSchemeBean.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columnClasses="col15,col15,col15,col15" columns="4" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" value="Scheme Name"/>
                        <h:selectOneListbox id="ddSecurityType" styleClass="ddlist" value="#{renewSssSchemeBean.schemeCode}" size="1" style="width:160px;">
                            <f:selectItems value="#{renewSssSchemeBean.schemeCodeList}" />
                            <a4j:support event="onblur" action="#{renewSssSchemeBean.getVendor}" reRender="ddCtrlNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Vendor Name"/> 
                        <h:selectOneListbox id="ddCtrlNo" styleClass="ddlist" value="#{renewSssSchemeBean.vendorCode}" size="1" style="width:150px;">
                            <f:selectItems value="#{renewSssSchemeBean.vendorCodeList}" />
                            <%--<a4j:support event="onblur" action="#{renewSssSchemeBean.getRenewData}" reRender="errmsg,tablePanel,taskList"/>--%>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{renewSssSchemeBean.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Account Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="A/c No" /></rich:column>
                                        <rich:column><h:outputText value="Name" /></rich:column>
                                        <rich:column><h:outputText value="Enrollment Date" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enrollDate}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <rich:modalPanel id="calculatePanel" autosized="true" width="300" onshow="#{rich:element('btnCalcYes')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are you sure to show all the a/c going to be renewed"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" id="btnCalcYes" action="#{renewSssSchemeBean.calculate}"
                                                               oncomplete="#{rich:component('calculatePanel')}.hide();if(#{renewSssSchemeBean.reportFlag})
                                                               {
                                                               #{rich:component('popUpRepPanel')}.show();
                                                               }
                                                               else if(#{!renewSssSchemeBean.reportFlag})
                                                               {
                                                               #{rich:component('popUpRepPanel')}.hide();
                                                               }"
                                                               reRender="errmsg,popUpRepPanel,tablePanel,taskList,btnProcess,btnCalculate"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" onclick="#{rich:component('calculatePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnCalculate" value="Calculate" oncomplete="#{rich:component('calculatePanel')}.show()"
                                               reRender="calculatePanel,errmsg" disabled="#{renewSssSchemeBean.calcBtnDisable}"/>
                            <a4j:commandButton id="btnProcess" value="Post" oncomplete="#{rich:component('processPanel')}.show()" 
                                               reRender="errmsg,processPanel" disabled="#{renewSssSchemeBean.postBtnDisable}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{renewSssSchemeBean.resetForm}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{renewSssSchemeBean.exitBtnAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
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
                                    <h:outputText id="confirmid" value="Are you sure to renew all these a/c"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{renewSssSchemeBean.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" reRender="errmsg,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Pradhanmantri Schemes" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{renewSssSchemeBean.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>