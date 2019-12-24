<%-- 
    Document   : crrslrPercent
    Created on : Oct 24, 2015, 5:27:25 PM
    Author     : saurabhsipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>CRR SLR Percentage</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{CrrSlrPerc.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="CRR SLR ENTRY FORM"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{CrrSlrPerc.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{CrrSlrPerc.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="glgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="settleDtLbl" styleClass="label" value="WEF DATE:"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="settleDt" styleClass="input chkDt" value="#{CrrSlrPerc.wefdate}" style="width:75px;" >
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText> 
                        <h:outputLabel id="lblagentAmt" styleClass="label" value="CRR PERCENTAGE:"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtagentAmt" size="10" styleClass="input" value="#{CrrSlrPerc.crrPercentage}" />
                        <h:outputLabel id="lblschemePl" styleClass="label" value="SLR PERCENTAGE:"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="stxtschemePl" size="10" styleClass="input" maxlength="6"value="#{CrrSlrPerc.slrPercentage}" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:195px;">
                        <rich:dataTable value="#{CrrSlrPerc.crrslrData}" var="dataItem" rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column breakBefore="true"><h:outputText value="S No"/></rich:column>
                                    <rich:column><h:outputText value="Wef Date"/></rich:column>
                                    <rich:column><h:outputText value="Crr Percentage"/></rich:column>
                                    <rich:column><h:outputText value="Slr Percentage"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:center;"><h:outputText value="#{dataItem.srlNo}"/></rich:column>
                            <rich:column style="text-align:center;"><h:outputText value="#{dataItem.wefDate}"/></rich:column>
                            <rich:column style="text-align:center;"><h:outputText value="#{dataItem.crrPercent}"/></rich:column>
                            <rich:column style="text-align:center;"><h:outputText value="#{dataItem.slrPercent}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />                            
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAddNew" value="Save" action="#{CrrSlrPerc.processSave}" oncomplete="setMask();" reRender="message,maingrid,Row4,btnUpdate"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{CrrSlrPerc.refreshForm}" oncomplete="setMask();"reRender="message,maingrid" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CrrSlrPerc.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
