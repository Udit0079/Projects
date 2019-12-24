<%-- 
    Document   : accountSmsDetail
    Created on : 12 Feb, 2018, 11:30:42 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Account Sms Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="acwisesmsdetails">
                <h:panelGrid columns="1" id="mainPanel" width="100%" style="ridge #BED6F8">
                    <h:panelGrid id="headerPanel" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountSmsDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Account Sms Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountSmsDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{AccountSmsDetail.errMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row1">
                          <h:outputLabel id="acId" value="A/c Number " styleClass="label"/>
                        <h:inputText id="txtAcno" value="#{AccountSmsDetail.acno}" size="#{AccountSmsDetail.acNoMaxLen}" styleClass="input" maxlength="#{AccountSmsDetail.acNoMaxLen}">
                            <a4j:support event="onblur" action="#{AccountSmsDetail.accountAction}" reRender="errorMsg" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="Detail Type" styleClass="label"/>
                        <h:selectOneListbox id="ddDetailType" value="#{AccountSmsDetail.detailType}" styleClass="ddlist" size="1" style="width:140px">
                            <f:selectItems value="#{AccountSmsDetail.detailTypeList}"/>
                            <a4j:support event="onchange" action="#{AccountSmsDetail.deatilTypeAction}" reRender="errorMsg,panel2,acId,txtAcno" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                      
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row2" >
                        <h:outputLabel styleClass="label" value="From Date"/>
                        <h:inputText id="txtFrDt" styleClass="input issueDt" style="width:70px" value="#{AccountSmsDetail.frDt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="To Date"/>
                        <h:inputText id="txtToDt" styleClass="input issueDt" style="width:70px" value="#{AccountSmsDetail.toDt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  id="buttonPanel" columns="1" style="text-align: center; width: 100%" styleClass="footer">
                        <a4j:region id="buttonRegion">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnPrint" value="Get Detail" action="#{AccountSmsDetail.btnHtmlAction}" reRender="errorMsg,tablePanel,taskList"/>
                                <a4j:commandButton value="Refresh" action="#{AccountSmsDetail.refresh}" reRender="mainPanel" oncomplete="setMask();"/>
                                <a4j:commandButton value="Exit" action="#{AccountSmsDetail.exit}" reRender="mainPanel" oncomplete="setMask();"/>
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                    <a4j:region id="tblRegion">
                        <rich:dataTable value="#{AccountSmsDetail.gridDetail}" var="dataItem"
                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onclick="this.style.backgroundColor='#428bca'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                          

                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="5"><h:outputText value="Account Sms Detail" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Sr No." /></rich:column>
                                    <rich:column><h:outputText value="Date" /></rich:column>
                                    <rich:column><h:outputText value="AC No." /></rich:column>
                                    <rich:column><h:outputText value="Mobile No." /></rich:column>
                                    <rich:column><h:outputText value="Message" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.srNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.mobno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.msg}" /></rich:column>
                          </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                    </a4j:region>
                </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="buttonRegion"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" 
                                     style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>

