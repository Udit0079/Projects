<%-- 
    Document   : aadharMovedOutUpdate
    Created on : Apr 6, 2015, 10:56:09 AM
    Author     : Admin
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
            <title>AAdhar No. Moved Out</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AadharMovedOutUpdation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="AAdhar No. Moved Out"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AadharMovedOutUpdation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="msg" value="#{AadharMovedOutUpdation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col8,col8" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel3" style="height:30px;" styleClass="row1" width="100%"> 
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:165px;" value="#{AadharMovedOutUpdation.function}">
                                    <f:selectItems value="#{AadharMovedOutUpdation.functionList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel4" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblAAdharNo" styleClass="label" value="AAdhar No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtAAdharNo" styleClass="input" value="#{AadharMovedOutUpdation.aadharNo}" maxlength="12" style="width:80px;"> 
                                    <a4j:support action="#{AadharMovedOutUpdation.gridLoad}" event="onblur" oncomplete="setMask();"
                                                 reRender="a19,taskList,message,btnSave"/>
                                </h:inputText>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{AadharMovedOutUpdation.gridDetail}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Customer Id" /></rich:column>
                                            <rich:column><h:outputText value="AAdhar No." /></rich:column>
                                            <rich:column><h:outputText value="Account No." /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <%--rich:column><h:outputText value="Mapped Status" /></rich:column--%>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.custId}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.aadharNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                    <%--rich:column><h:outputText value="#{dataItem.mappedStatus}" /></rich:column--%>
                                    <%--rich:column style="text-align:center;width:40px">     
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" oncomplete="#{rich:component('savePanel')}.show()" reRender=",message,lpg,frDt,toDt">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{AadharMovedOutUpdation.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{AadharMovedOutUpdation.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column--%>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="12" value="Update" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,a19,taskList"/>
                                <a4j:commandButton id="btnRefresh" tabindex="13" ajaxSingle="true" value="Refresh" action="#{AadharMovedOutUpdation.resetForm}"
                                                   reRender="taskList,message,lpg,a19,ddFunction,txtAAdharNo,btnSave" oncomplete="setMask();">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" tabindex="14" ajaxSingle="true" value="Exit" action="#{AadharMovedOutUpdation.exitForm}" reRender="taskList,message,lpg,a19"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('save')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Process It ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="save" ajaxSingle="true" action="#{AadharMovedOutUpdation.postDetail}" oncomplete="#{rich:component('savePanel')}.hide();return false;"
                                                       reRender="message,lpg,taskList" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('waitPost')}.show()" onstop="#{rich:component('waitPost')}.hide()"/>
            <rich:modalPanel id="waitPost" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>                
        </body>
    </html>
</f:view>

