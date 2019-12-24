<%-- 
    Document   : glheadSearch
    Created on : Feb 28, 2014, 11:19:41 AM
    Author     : Athar Reza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Gl Head Search</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="GlheadSearch">  
                
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{GlHeadSearch.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Gl Head Search"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{GlHeadSearch.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{GlHeadSearch.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col1"  width="100%" styleClass="row1">
                        <h:outputLabel id="status" styleClass="label" value="Search Option"/>
                        <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{GlHeadSearch.searchOption}" style="width:120">
                            <f:selectItems value="#{GlHeadSearch.searchOptionList}"/>
                            <%--a4j:support event="onblur"  action="#{GlHeadSearch.searchAction}" reRender="seqNo,lblMsg" oncomplete="setMask()"/--%>
                        </h:selectOneListbox>                    
                        <h:outputLabel id="seq" value="GlCode/Name" styleClass="label"/>
                        <h:inputText id="seqNo" value="#{GlHeadSearch.glCodeName}" styleClass="input" style="width:80px">
                            <a4j:support event="onblur" oncomplete="setMask()"/>
                        </h:inputText>
                             
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{GlHeadSearch.gridDetail}" var="dataItem"
                                            rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="PO DETAILS" /></rich:column> 
                                        <rich:column breakBefore="true"><h:outputText value="S.No." /></rich:column>
                                        <rich:column><h:outputText value="Gl Account No" /></rich:column>  
                                        <rich:column><h:outputText value="Gl Name" /></rich:column>                                                                            
                                        <rich:column><h:outputText value="Post Flag" /></rich:column>
                                        <rich:column><h:outputText value="Msg Flag" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet> 
                                <rich:column><h:outputText value="#{dataItem.sqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.glAcNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.glName}" /></rich:column>              
                                <rich:column><h:outputText value="#{dataItem.glPostfg}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.glMesgfg}" /></rich:column>               
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:region id="saveRegion">
                                <a4j:commandButton value="Show Details" id="btnSave" action="#{GlHeadSearch.getGlDetails}" reRender="mainPanel,taskList,lblMsg" oncomplete="setMask()"/>
                            </a4j:region>
                            <a4j:commandButton value="Refresh" id="refresh" action="#{GlHeadSearch.refreshForm}" reRender="mainPanel,taskList,lblMsg" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{GlHeadSearch.exitBtnAction}" reRender="lblMsg,taskList"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="saveRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>            
        </body>
    </html>
</f:view>