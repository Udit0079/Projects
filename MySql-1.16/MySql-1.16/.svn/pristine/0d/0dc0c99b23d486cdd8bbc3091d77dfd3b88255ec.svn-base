<%-- 
    Document   : securitymaster
    Created on : May 29, 2012, 10:56:13 AM
    Author     : root
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
            <title>Investment Security Master</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".chkDt").mask("99/99");
                    jQuery(".matDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{goiMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="GOI Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{goiMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>


                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{goiMaster.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="functiongrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" style="width: 120px" styleClass="ddlist"  value="#{goiMaster.function}" size="1">
                            <f:selectItems value="#{goiMaster.functionList}" />
                            <a4j:support action="#{goiMaster.onChangeFunction}" event="onchange" oncomplete="setMask()" reRender="maingrid"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSecurityName" styleClass="label" value="Security Name"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtSecurityName" value="#{goiMaster.securityName}" maxlength="200" onkeyup="this.value = this.value.toUpperCase();" size="30" styleClass="input">
                            <a4j:support action="#{goiMaster.onBlurSecurityName}" event="onblur" oncomplete="setMask()" reRender="message" limitToList="true"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="maturitygrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblMaturityYear" styleClass="label" value="Maturity Year"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtMaturityYear" value="#{goiMaster.maturityYear}" maxlength="10" size="10" styleClass="input matDt">
                                <a4j:support action="#{goiMaster.onBlurMaturityYear}" event="onblur" reRender="message" limitToList="true"/>
                            </h:inputText>
                            <h:outputLabel styleClass="label"><font class="required" color="red">dd/mm/yyyy</font></h:outputLabel>
                        </h:panelGroup>
                        <h:outputLabel id="lblRoi" styleClass="label" value="Rate of Interest(%)"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRoi" value="#{goiMaster.pageRoi}" size="10" styleClass="input">
                            <a4j:support action="#{goiMaster.onBlurPageRoi}" event="onblur" reRender="message" limitToList="true"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="dategrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFirstIntDate" styleClass="label" value="First Int.Pay.Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="chkDt" value="#{goiMaster.firstIntDate}" size="10" styleClass="input chkDt">
                                <a4j:support action="#{goiMaster.onBlurFirstIntDate}" event="onblur" reRender="message,txtSecondIntDate,btnProcess,tablePanel,taskList" focus="btnProcess" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel styleClass="label"><font class="required" color="red">dd/mm</font></h:outputLabel>
                        </h:panelGroup>
                        <h:outputLabel id="lblSecondIntDate" styleClass="label" value="Second Int.Pay.Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtSecondIntDate" value="#{goiMaster.secondIntDate}" size="10" styleClass="input" disabled="#{goiMaster.secondDtVisible}"/>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{goiMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="GOI Master Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No" rendered="#{goiMaster.snoFlag}"/></rich:column>
                                        <rich:column><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Security Name" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Year" /></rich:column>
                                        <rich:column><h:outputText value="First Date" /></rich:column>
                                        <rich:column><h:outputText value="Second Date" /></rich:column>
                                        <rich:column><h:outputText value="Roi" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="#{goiMaster.gridOperation}"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.date}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.securityName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.maturityYear}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.firstDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secondDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterby}" /></rich:column>
                                <rich:column style="text-align:center;width:40px" rendered="#{goiMaster.addFlag}">
                                    <a4j:commandLink id="deletelink" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show();">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{goiMaster.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column style="text-align:center;width:40px" rendered="#{goiMaster.selectFlag}">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{goiMaster.setTableDataToForm}" reRender="maingrid">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{goiMaster.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="#{goiMaster.processButtonValue}" 
                                               action="#{goiMaster.populateMessage}" 
                                               oncomplete="#{rich:component('processPanel')}.show();setMask()" 
                                               disabled="#{goiMaster.processVisible}" reRender="message,processPanel,confirmid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{goiMaster.resetForm}" oncomplete="setMask()" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{goiMaster.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
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
                                    <h:outputText id="confirmid" value="#{goiMaster.confirmationMsg}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{goiMaster.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddFunction')}.focus();" 
                                                       reRender="message,maingrid,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{goiMaster.delete}" oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,maingrid,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
