<%-- 
    Document   : InterestMaster
    Created on : 4 Sep, 2010, 10:13:02 AM
    Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>InterestMaster</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InterestMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblInterestMaster" styleClass="headerLabel" value="Interest Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InterestMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1" columns="1" id="bodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel51" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{InterestMaster.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel0" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblCode" styleClass="label" value="Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtCode" maxlength="12" style="width:110px" value="#{InterestMaster.code}" disabled="#{InterestMaster.codeDisable}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                <a4j:support action="#{InterestMaster.resetButton}" event="onblur"
                                             reRender="BtnPanel,stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblCodeDescription" styleClass="label" value="Code Description"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtCodeDescription" style="width:110px" value="#{InterestMaster.codeDescription}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel11" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblInterestPercentageDebit" styleClass="label" value="Interest Percentage Debit (%)"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtInterestPercentage" maxlength="15" style="width:110px" value="#{InterestMaster.interestPercentageDebit}" disabled="#{InterestMaster.debitDisable}" styleClass="input">
                                <a4j:support action="#{InterestMaster.setCreditDisable}" event="onblur"
                                             reRender="txtInterestPercentage,txtInterestPercentageCredit,stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblInterestPercentagecredit" styleClass="label" value="Interest Percentage Credit (%)"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtInterestPercentageCredit" maxlength="15" style="width:110px" value="#{InterestMaster.interestPercentageCredit}" disabled="#{InterestMaster.creditDisable}" styleClass="input">
                                <a4j:support action="#{InterestMaster.setDebitDisable}" event="onblur"
                                             reRender="txtInterestPercentage,txtInterestPercentageCredit,stxtMsg" focus="txtVersionNoDescription" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel12" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblStartDate" styleClass="label" value="Start Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calStartDate" value="#{InterestMaster.fromDate}" inputSize="10"/>
                            <h:outputLabel id="lblEndDate" styleClass="label" value="End Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calEndDate" value="#{InterestMaster.toDate}" inputSize="10"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel13" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblVersionNo" styleClass="label" value="Version No."/>
                            <h:outputText id="txtVersionNo" styleClass="output" value="#{InterestMaster.versionNo}"/>
                            <h:outputLabel id="lblVersionNoDescription" styleClass="label" value="Version No. Description"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtVersionNoDescription" maxlength="60" style="width:110px" value="#{InterestMaster.versionNoDesc}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel14" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblRecordStatus" styleClass="label" value="Record Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRecordStatus" styleClass="ddlist" size="1" required="true" style="width: 110px" value="#{InterestMaster.status}">
                                <f:selectItems value="#{InterestMaster.recrdStatus}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblRecordModificationCount" styleClass="label" value="Record Modification Count"/>
                            <h:outputText id="txtRecordModificationCount" styleClass="output" value="#{InterestMaster.recordModificationCount}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel15" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblCreatedByUserId" styleClass="label" value="Created By "/>
                            <h:outputText id="stxtCreatedByUserId" styleClass="output" value="#{InterestMaster.createdBy}"/>
                            <h:outputLabel id="lblCreationDate" styleClass="label" value="Creation Date"/>
                            <h:outputText id="stxtCreationDate" styleClass="output" value="#{InterestMaster.creationDate}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel16" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblLastChangedUserId" styleClass="label" value="Last Updated By "/>
                            <h:outputText id="stxtLastChangedUserId" styleClass="output" value="#{InterestMaster.changedBy}"/>
                            <h:outputLabel id="lblLastChangedDate" styleClass="label" value="Last Updated Date"/>
                            <h:outputText id="stxtLastChangedDate" styleClass="output" value="#{InterestMaster.changedDate}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel30" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblIndicatorFlag" styleClass="label" value="Indicator Flag"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtIndicatorFlag" style="width:110px" value="#{InterestMaster.indicatorFlag}" maxlength="2" disabled="#{InterestMaster.indFlagDisable}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputText id="stxtFree1" styleClass="output" value=""/>
                            <h:outputText id="stxtFree2" styleClass="output" value=""/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{InterestMaster.intMasterData}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Code" /> </rich:column>
                                            <rich:column><h:outputText value="Code Description" /></rich:column>
                                            <rich:column><h:outputText value="Indicator Flag" /></rich:column>
                                            <rich:column><h:outputText value="Record Status" /></rich:column>
                                            <rich:column><h:outputText value="Start Date " /></rich:column>
                                            <rich:column><h:outputText value="End Date" /></rich:column>
                                            <rich:column><h:outputText value="Version No Description" /></rich:column>
                                            <rich:column><h:outputText value="Interest Percentage Debit" /></rich:column>
                                            <rich:column><h:outputText value="Interest Percentage Credit" /></rich:column>
                                            <rich:column><h:outputText value="Update" /></rich:column>
                                            <rich:column><h:outputText value="Delete" /> </rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.codeValue}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.codeDes}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.indFlag}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.rdStatus}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.strtDate}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.endDate}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.versionNumDescription}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.intPercent}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.intPercentCredit}"/></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{InterestMaster.select}" reRender="BtnPanel,txtCode,txtCodeDescription,txtInterestPercentage,txtInterestPercentageCredit,txtIndicatorFlag,calStartDate,calEndDate,txtVersionNo,txtVersionNoDescription,txtRecordModificationCount,stxtCreatedByUserId,stxtCreationDate,stxtLastChangedUserId,stxtLastChangedDate,footerPanel,txtIndicatorFlag,txtCode,ddRecordStatus,stxtMsg" focus="btnUpdate" >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{item}" target="#{InterestMaster.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{InterestMaster.currentRow}" />
                                        </a4j:commandLink>
                                        <rich:toolTip for="selectlink" value="Edit" />
                                    </rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="stxtMsg" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{row}" target="#{InterestMaster.currentRow}" />
                                            <f:setPropertyActionListener value="#{item}" target="#{InterestMaster.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="20" />
                            </a4j:region>

                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave"  value="Save"  focus="btnUpdate" oncomplete="#{rich:component('savePanel')}.show()"
                                               reRender="bodyPanel,taskList,stxtMsg" rendered="#{InterestMaster.flag == 'true'}" />
                            <a4j:commandButton id = "btnRefresh" value="Refresh" action="#{InterestMaster.clear}" reRender="mainPanel,btnSave" focus="txtCode"/>
                            <a4j:commandButton id="btnUpdate"  value="Update" oncomplete="#{rich:component('updatePanel')}.show()"
                                               reRender="bodyPanel,taskList,stxtMsg"rendered="#{InterestMaster.flag == 'false'}" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InterestMaster.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('delYes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete this Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="delYes" value="Yes" ajaxSingle="true" action="#{InterestMaster.delete}"
                                                           onclick="#{rich:component('deletePanel')}.hide();" reRender="taskList,stxtMsg" />
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton  id="delNo" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="updatePanel" autosized="true" width="250" onshow="#{rich:element('upYes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>

                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To update?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="upYes" value="Yes" ajaxSingle="true" action="#{InterestMaster.updateBtnAction}"
                                                           oncomplete="#{rich:component('updatePanel')}.hide();" reRender="bodyPanel,taskList,stxtMsg"/>
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton  id="upNo" value="No" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>


            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('YesBtn')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>

                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Save?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton  id="YesBtn" value="Yes" ajaxSingle="true" action="#{InterestMaster.saveBtnAction}"
                                                            oncomplete="#{rich:component('savePanel')}.hide();" reRender="bodyPanel,taskList,stxtMsg" />
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton  id="cancelBtn" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>