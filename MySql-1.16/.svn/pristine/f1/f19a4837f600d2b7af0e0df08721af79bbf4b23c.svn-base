<%-- 
    Document   : ChargeMaster
    Created on : Aug 29, 2013, 10:25:46 AM
    Author     : Athar Reza
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
            <title>Charge Master</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ChargeMasters.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblInterestMaster" styleClass="headerLabel" value="Charge Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ChargeMasters.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1" columns="1" id="bodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel51" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ChargeMasters.message}"/>
                            <h:outputText id="errorMessage" styleClass="msg" value="#{ChargeMasters.errorMsg}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel141" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblRecordStatus" styleClass="label" value="Charge Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRecordStatus" styleClass="ddlist" size="1" required="true" style="width: 110px" value="#{ChargeMasters.chargeType}" disabled="#{ChargeMasters.chargeTypeDisable}">
                                <f:selectItems value="#{ChargeMasters.chargeTypeList}"/>
                                <a4j:support action="#{ChargeMasters.resetButton}" event="onblur"
                                             reRender="BtnPanel,stxtMsg,errorMessage,ddRecordStatus1,taskList,gridPanel103,scroller"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblRecordStatus1" styleClass="label" value="Charge Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRecordStatus1" styleClass="ddlist" size="1" required="true" style="width: 180px" value="#{ChargeMasters.chargeName}">
                                <f:selectItems value="#{ChargeMasters.chargeNmeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel142" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblRecordStatus2" styleClass="label" value="A/c Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRecordStatus2" styleClass="ddlist" size="1" required="true" style="width: 110px" value="#{ChargeMasters.acctType}">
                                <f:selectItems value="#{ChargeMasters.accTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblRecordStatus3" styleClass="label" value="Fix Flag"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRecordStatus3" styleClass="ddlist" size="1" required="true" style="width: 110px" value="#{ChargeMasters.fixFlag}">
                                <f:selectItems value="#{ChargeMasters.fixFlagList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="gridPanel6"  styleClass="row2">
                            <h:outputLabel id="lblDisFrAmount" styleClass="label"  value="From Range"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtdisAmt"  style="width:110px" value="#{ChargeMasters.fromRange}" styleClass="input" maxlength="12" >                       
                            </h:inputText> 
                            <h:outputLabel id="lblDisToAmount" styleClass="label"  value="To Range"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtdistoAmt"  style="width:110px" value="#{ChargeMasters.toRange}" styleClass="input" maxlength ="12" >
                            </h:inputText> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel13" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblDisFrAmount12" styleClass="label"  value="Cr GlHead"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtdisAmt12"  style="width:110px" value="#{ChargeMasters.crglhead}" styleClass="input" maxlength="6">                       
                            </h:inputText> 
                            <h:outputLabel id="lblDisFrAmount13" styleClass="label"  value=" Amount:"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtdisAmt14"  style="width:110px" value="#{ChargeMasters.amount}" styleClass="input" maxlength="12" disabled="#{ChargeMasters.amountDisable}">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="gridPanel5"  styleClass="row2">
                            <h:outputLabel id="lblStartDate" styleClass="label" value="Effective Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calStartDate" value="#{ChargeMasters.effDt}" inputSize="10"/>
                            <h:outputLabel id="lblLastEffDt" styleClass="label" value="Last Effective Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:outputText id="lastEffDate" styleClass="output" value="#{ChargeMasters.lastEffDate}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel15" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblCreatedByUserId" styleClass="label" value="Enter By "/>
                            <h:outputText id="stxtCreatedByUserId" styleClass="output" value="#{ChargeMasters.enterBy}"/>
                            <h:outputLabel id="lblCreationDate" styleClass="label" value="Creation Date"/>
                            <h:outputText id="stxtCreationDate" styleClass="output" value="#{ChargeMasters.creationDt}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel16" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblLastChangedUserId" styleClass="label" value="Updated By "/>
                            <h:outputText id="stxtLastChangedUserId" styleClass="output" value="#{ChargeMasters.updateBy}"/>
                            <h:outputLabel id="lblLastChangedDate" styleClass="label" value="Updated Date"/>
                            <h:outputText id="stxtLastChangedDate" styleClass="output" value="#{ChargeMasters.changeDate}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{ChargeMasters.chargeMasterData}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Charge Type" /> </rich:column>
                                            <rich:column><h:outputText value="Charge Name" /></rich:column>
                                            <rich:column><h:outputText value="A/c Type" /></rich:column>
                                            <rich:column><h:outputText value="From Range" /></rich:column>
                                            <rich:column><h:outputText value="To Range" /></rich:column>
                                            <rich:column><h:outputText value="Fix Flag" /></rich:column>
                                            <rich:column><h:outputText value="Amount" /></rich:column>
                                            <rich:column><h:outputText value="Effective Date" /></rich:column>
                                            <rich:column><h:outputText value="Cr GlHead" /></rich:column>
                                            <rich:column><h:outputText value="Update" /></rich:column>
                                            <%--rich:column><h:outputText value="Update" /></rich:column--%>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.chargesType}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.chargesName}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.acType}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.frRange}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.toeRange}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.fixprFlag}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.amt}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.effDate}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.crglHead}"/></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ChargeMasters.select}" reRender="BtnPanel,ddRecordStatus,ddRecordStatus1,ddRecordStatus2,ddRecordStatus3,txtdisAmt,txtdistoAmt,txtdisAmt12,txtdisAmt14,frDt,stxtCreatedByUserId,stxtCreationDate,stxtLastChangedUserId,stxtLastChangedDate,footerPanel,ddRecordStatus,stxtMsg,errorMessage,lastEffDate" focus="btnUpdate" >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{item}" target="#{ChargeMasters.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ChargeMasters.currentRow}" />
                                        </a4j:commandLink>
                                        <rich:toolTip for="selectlink" value="Edit" />
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>                        
                    </h:panelGrid> 

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave"  value="Save"  focus="btnUpdate" oncomplete="#{rich:component('savePanel')}.show()"
                                               reRender="bodyPanel,taskList,stxtMsg,errorMessage" rendered="#{ChargeMasters.flag == 'true'}" />
                            <a4j:commandButton id="btnUpdate"  value="Update" oncomplete="#{rich:component('updatePanel')}.show()"
                                               reRender="bodyPanel,taskList,stxtMsg,errorMessage"rendered="#{ChargeMasters.flag == 'false'}" />
                            <a4j:commandButton id = "btnRefresh" value="Refresh" action="#{ChargeMasters.clear1}" reRender="mainPanel,btnSave,errorMessage" focus="txtCode"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ChargeMasters.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>            
            </a4j:form>  

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
                                        <a4j:commandButton  id="YesBtn" value="Yes" ajaxSingle="true" action="#{ChargeMasters.saveBtnAction}"
                                                            oncomplete="#{rich:component('savePanel')}.hide();" reRender="bodyPanel,taskList,stxtMsg,errorMessage" />
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
                                        <a4j:commandButton id="upYes" value="Yes" ajaxSingle="true" action="#{ChargeMasters.updateBtnAction}"
                                                           oncomplete="#{rich:component('updatePanel')}.hide();" reRender="bodyPanel,taskList,stxtMsg,errorMessage"/>
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

        </body>
    </html>
</f:view>