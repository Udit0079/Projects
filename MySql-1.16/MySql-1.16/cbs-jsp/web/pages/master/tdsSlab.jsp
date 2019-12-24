<%-- 
    Document   : tdsSlab
    Created on : May 13, 2010, 11:00:16 AM
    Author     : Jitendra Kumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>TDS Slab </title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{tdsSlab.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="TDS Slab"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{tdsSlab.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{tdsSlab.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="label1" styleClass="label" for="ddCustType" value="Customer Type"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddCustType" styleClass="ddlist" size="1" style="width:100px" value="#{tdsSlab.custType}" tabindex="1">
                                        <f:selectItem itemValue="--SELECT--"/>
                                        <f:selectItem itemValue="INDIVIDUAL"/>
                                        <f:selectItem itemValue="COMPANY"/>
                                        <f:selectItem itemValue="OTHERS"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                                    <h:outputLabel id="label12" styleClass="label" value="TDS Rate(%)" ><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtTdsRatePer" value="#{tdsSlab.tdsRate}" styleClass="input" style="width:100px" tabindex="3" maxlength="11" >
                                    </h:inputText>
                                </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel17" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label13" styleClass="label" value="Tds Rate Without Pan" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtTdsRatePan" value="#{tdsSlab.tdsRatePan}" styleClass="input" style="width:100px" tabindex="3" maxlength="11" >
                                    </h:inputText>
                                </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanela" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="labela" styleClass="label" value="TDS-GL Head" ><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtTdsGlHead2" value="#{tdsSlab.glHead2}" styleClass="input" style="width:100px" tabindex="5" maxlength="6" onkeyup="this.value=this.value.toUpperCase();">
                                        <a4j:support action="#{tdsSlab.checkGlHead}"  event="onblur" reRender="stxtMsg" limitToList="true" focus="#{rich:clientId('calApplicableDate')}InputDate"/>
                                    </h:inputText>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid  columns="1" id="gridPanel103" width="100%" >
                                <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel14" style="height:30px;" styleClass="row2" width="100%">
                                    <h:outputLabel id="label9" styleClass="label" value="TDS Interest Amount" ><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtTdsAmount" value="#{tdsSlab.tdsAmount}" styleClass="input" style="width:100px" tabindex="2" maxlength="11">
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel18" style="height:30px;" styleClass="row1" width="100%">
                                    <h:outputLabel id="label14" styleClass="label" value="Surcharge" ><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtSurcharge" value="#{tdsSlab.surcharge}" styleClass="input" style="width:100px" tabindex="4"  maxlength="11">
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel19" style="height:30px;" styleClass="row1" width="100%">
                                    <h:outputLabel id="label144" styleClass="label" value="Srctzn Tds Amount" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSrctznTdsAmt" value="#{tdsSlab.srctznTdsAmt}" styleClass="input" style="width:100px" tabindex="4"  maxlength="11">
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelb" style="height:30px;" styleClass="row2" width="100%">
                                    <h:outputLabel id="labelb" styleClass="label" value="Applicable Date" ><font class="required" color="red">*</font></h:outputLabel>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calApplicableDate" value="#{tdsSlab.dates}" tabindex="6"  inputSize="10">
                                        <a4j:support actionListener="#{tdsSlab.onblurApplicableDate}"  event="onchanged" focus="btnList"
                                                     reRender="stxtMsg"  />
                                    </rich:calendar>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="2" id="gridPanele" width="100%" styleClass="row2" style="height:250px;">
                            <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{tdsSlab.fetchCurrentRow}">
                                    <a4j:actionparam name="acNo" value="{acNo}" />
                                    <a4j:actionparam name="row" value="{currentRow}" />
                                </rich:menuItem>
                            </rich:contextMenu>
                            <a4j:region>
                                <rich:dataTable value="#{tdsSlab.slabes}" var="dataItem" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row" rowClasses="gridrow1,gridrow2"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11"><h:outputText value="TDS Slab" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Type" /></rich:column>
                                            <rich:column><h:outputText value="Applicable Date" /></rich:column>
                                            <rich:column><h:outputText value="TDS Amount" /></rich:column>
                                            <rich:column><h:outputText value="TDS Rate(%)" /></rich:column>
                                            <rich:column><h:outputText value="TDS Surcharge" /></rich:column>
                                            <rich:column><h:outputText value="GL Head" /></rich:column>
                                            <rich:column><h:outputText value="EnterBy" /></rich:column>
                                            <rich:column><h:outputText value="Last Update" /></rich:column>
                                        
                                          <rich:column><h:outputText value="Tds Rate Without Pan" /></rich:column>
                                          <rich:column><h:outputText value="Srctzn Tds Amount" /></rich:column>
                                            <rich:column visible="false"><h:outputText value="S.No" /></rich:column>
                                            <rich:column><h:outputText value="Action" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.type}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.applicableDate}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.tdsAmount}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.tdsRate}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column >
                                        <h:outputText value="#{dataItem.tdsSurcharge}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.tdsGlHead}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.lastUpDateDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.tdsRatePan}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.srctznTdsAmt}" /></rich:column>
                                    <rich:column visible="false"><h:outputText value="#{dataItem.sNumber}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{tdsSlab.setRowValues}"
                                                         reRender="txtSurcharge,btnSave,btnUpdate,btnDelete,txtTdsRatePer,txtTdsAmount,calApplicableDate,ddCustType,txtTdsGlHead2,txtTdsRatePan,txtSrctznTdsAmt" >
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{tdsSlab.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{tdsSlab.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnList" value="List" style="width:60px" action="#{tdsSlab.getList}"  reRender="gridPanele,stxtMsg" tabindex="7"/>
                                <a4j:commandButton id="btnSave" value="Save"style="width:60px" action="#{tdsSlab.save}" reRender="ddCustType,btnUpdate,btnSave,btnDelete,calApplicableDate,txtTdsAmount,txtTdsRatePer,txtSurcharge,stxtUser,gridPanele,txtTdsGlHead2,txtTdsRatePan,txtSrctznTdsAmt,stxtMsg" tabindex="8" disabled="#{tdsSlab.flag}"/>
                                <a4j:commandButton id="btnUpdate" value="Update" style="width:60px" action="#{tdsSlab.upDate}" reRender="ddCustType,btnUpdate,btnSave,btnDelete,calApplicableDate,txtTdsAmount,txtTdsRatePer,txtSurcharge,gridPanele,txtTdsGlHead2,txtTdsRatePan,txtSrctznTdsAmt,stxtMsg"  disabled="#{tdsSlab.flag1}"/>
                                <a4j:commandButton id="btnDelete" value="Delete" style="width:60px" action="#{tdsSlab.delete}" reRender="ddCustType,btnUpdate,btnSave,btnDelete,calApplicableDate,txtTdsAmount,txtTdsRatePer,txtSurcharge,gridPanele,txtTdsGlHead2,txtTdsRatePan,txtSrctznTdsAmt,stxtMsg" disabled="#{tdsSlab.flag2}"/>
                                <a4j:commandButton id="btnReset" value="Refresh" style="width:60px" action="#{tdsSlab.clearText}" reRender="ddCustType,btnUpdate,btnSave,btnDelete,txtTdsGlHead1,txtTdsGlHead2,txtTdsAmount,txtSurcharge,txtTdsRatePer,calApplicableDate,gridPanele,txtTdsRatePan,txtSrctznTdsAmt,stxtMsg"   tabindex="10"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{tdsSlab.exitFrm}" style="width:60px" tabindex="9"/>                           
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:outputText value="Processing" />
                        </f:facet>
                        <h:outputText value="Wait Please..." />
                    </rich:modalPanel>
                    <rich:messages></rich:messages>
                </a4j:form>
        </body>
    </html>
</f:view>
