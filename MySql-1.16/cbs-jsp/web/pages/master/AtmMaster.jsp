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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>ATM Master</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ATMMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM Master Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ATMMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="msg" value="#{ATMMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="identifierGrid" columns="4" columnClasses="col13,col13,col13,col13" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel styleClass="label" style="padding-left:70px;" value="Identifier:"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtIdentifier" styleClass="input" style="width:130px" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" value="#{ATMMaster.atmIdentifier}" disabled="#{ATMMaster.disableId}"/>
                            <h:outputLabel styleClass="label"  style="padding-left:70px;" value="ATM Branch"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAtmBranch" styleClass="ddlist"  size="1" style="width:120px" value="#{ATMMaster.atmBranch}">
                                <f:selectItems value="#{ATMMaster.atmBranchList}"/>                                      
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="nameGrid" columns="4" columnClasses="col13,col13,col13,col13" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel styleClass="label" style="padding-left:70px;" value="ATM Name:"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtAtmName" styleClass="input" style="width:130px" maxlength="50" onkeyup="this.value = this.value.toUpperCase();" value="#{ATMMaster.atmName}"/>
                            <h:outputLabel styleClass="label" style="padding-left:70px;" value="Status :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" required="true" style="width:120px" value="#{ATMMaster.atmStatus}">
                                <f:selectItems value="#{ATMMaster.atmStatusList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="headGrid" columns="4" columnClasses="col13,col13,col13,col13" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel styleClass="label" style="padding-left:70px;" value="ATM Cash Head :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtHead" styleClass="input" style="width:120px" onkeyup="this.value = this.value.toUpperCase();" maxlength="12" value="#{ATMMaster.atmCashHead}"/>
                            <h:outputLabel styleClass="label" style="padding-left:70px;"value="ATM Address :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtAtmAddress" styleClass="input" style="width:120px" onkeyup="this.value = this.value.toUpperCase();" maxlength="100" value="#{ATMMaster.atmAddress}"/>
                        </h:panelGrid>
                        <h:panelGrid id="cityGrid" columns="4" columnClasses="col13,col13,col13,col13" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel styleClass="label" style="padding-left:70px;" value="City/District :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCity" styleClass="input" style="width:120px" onkeyup="this.value = this.value.toUpperCase();" maxlength="50"value="#{ATMMaster.city}"/>
                            <h:outputLabel styleClass="label" style="padding-left:70px;" value="State :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddState" styleClass="ddlist" size="1" required="true" style="width:120px" value="#{ATMMaster.state}">
                                <f:selectItems value="#{ATMMaster.stateList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="offSiteGrid" columns="4" columnClasses="col13,col13,col13,col13" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel styleClass="label" style="padding-left:70px;" value="Location Type :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddLocationType" styleClass="ddlist" size="1" required="true" style="width:120px" value="#{ATMMaster.locationType}">
                                <f:selectItems value="#{ATMMaster.locationTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel styleClass="label" style="padding-left:70px;" value="Site :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddSite" styleClass="ddlist" size="1" required="true" style="width:120px" value="#{ATMMaster.site}">
                                <f:selectItems value="#{ATMMaster.siteList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{ATMMaster.gridDetail}" var="dataItem"
                                                rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="15"><h:outputText value="ATM DETAILS" /></rich:column> 
                                            <rich:column breakBefore="true"><h:outputText value="IDENTIFIER" /></rich:column>
                                            <rich:column><h:outputText value="NAME"/></rich:column>
                                            <rich:column><h:outputText value="BRANCH" /></rich:column>                                                                            
                                            <rich:column><h:outputText value="CASH HEAD" /></rich:column>
                                            <rich:column><h:outputText value="STATUS"/></rich:column>
                                            <rich:column><h:outputText value="ADDRESS" /></rich:column>
                                            <rich:column><h:outputText value="CITY" /></rich:column>
                                            <rich:column><h:outputText value="STATE" /></rich:column>
                                            <rich:column><h:outputText value="ENTER BY" /></rich:column>
                                            <rich:column width="20"><h:outputText value="Update" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>                              
                                    <rich:column><h:outputText value="#{dataItem.atmIdentifier}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.atmName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.atmBranch}" /></rich:column>              
                                    <rich:column><h:outputText value="#{dataItem.atmCashHead}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.atmStatus}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.atmAddress}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.city}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.state}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ATMMaster.select}" reRender="mainPanel" focus="Update1">    
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{ATMMaster.currentItem}"/>
                                        </a4j:commandLink>
                                        <rich:toolTip for="selectlink" value="Edit"/>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="9" value="Save" focus="Update1" oncomplete="#{rich:component('savePanel')}.show()" 
                                                   reRender="mainPanel" rendered="#{ATMMaster.flag == 'true'}"/>
                                <a4j:commandButton id="Update1" tabindex="10" value="Update" 
                                                   oncomplete="#{rich:component('updatePanel')}.show()" reRender="message" rendered="#{ATMMaster.flag == 'false'}"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ATMMaster.refreshForm}" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{ATMMaster.exitBtnAction}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="updatePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" />
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Update ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{ATMMaster.updateATMTypeDetail}" oncomplete="#{rich:component('updatePanel')}.hide();" reRender="mainPanel"/>
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYes1')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Save ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes1" action="#{ATMMaster.saveMasterDetail}"  oncomplete="#{rich:component('savePanel')}.hide();" reRender="mainPanel"/>

                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo1"onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>