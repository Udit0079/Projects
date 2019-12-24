<%--
 Document : ddsagentmaster
 
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>DDS Agent Master</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DDSAgentMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="DDS Agent Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DDSAgentMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="grdpane6" style="height:30px;text-align:center" styleClass="row1" width="100%">
                        <h:outputText id="stxtError" styleClass="error" value="#{DDSAgentMaster.msg}"/>
                    </h:panelGrid>

                    <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="gridpanel1" styleClass="row2">
                        <h:outputLabel id="lblName" styleClass="label" value="Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtName" styleClass="input" maxlength="40" value="#{DDSAgentMaster.name}" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblCode" styleClass="label" value="Agent Code"/>
                            <h:outputLabel id="lblAgentCode" styleClass="label" value="#{DDSAgentMaster.agentCode}"/>
                        </h:panelGrid>

                    <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="gridpanel2" styleClass="row1">
                        <h:outputLabel id="lblAddress" styleClass="label" value="Address" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtAddress" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" maxlength="120" value="#{DDSAgentMaster.address}" style="width:250px"/>
                            <h:outputLabel id="lblPhone" styleClass="label" value="Phone"/>
                            <h:inputText id="txtPhone" styleClass="input" maxlength="12" value="#{DDSAgentMaster.phone}" style="width:90px"/>
                        </h:panelGrid>

                    <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="gridpane" styleClass="row2">
                        <h:outputLabel styleClass="label" value="Status"></h:outputLabel>
                        <h:selectOneListbox id="ddStatus" size="1" value="#{DDSAgentMaster.status}" styleClass="ddlist" disabled="#{DDSAgentMaster.update}">
                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                            <f:selectItem itemValue="A" itemLabel="Active"/>
                            <f:selectItem itemValue="I" itemLabel="Inactive"/>
                            <f:selectItem itemValue="D" itemLabel="Deleted"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks"></h:outputLabel>
                        <h:inputText id="txtRemarks" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" maxlength="100" value="#{DDSAgentMaster.remarks}" style="width:200px"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="passwordPanel" styleClass="row1">
                        <h:outputLabel id="lblPassword" styleClass="label" value="DDS Machine Password" />
                        <h:inputText id="txtPassword" styleClass="input" maxlength="8" value="#{DDSAgentMaster.password}" style="width:80px"/>
                        <h:outputLabel id="lblAcno" styleClass="label" value="Agent Account No" style="display:#{DDSAgentMaster.displayAcct}"/>
                        <h:panelGroup style="display:#{DDSAgentMaster.displayAcct}">
                            <h:inputText id="txtAcNo" styleClass="input" maxlength="12" value="#{DDSAgentMaster.acno}" style="width:100px">
                                <a4j:support event="onblur" action="#{DDSAgentMaster.accountDetails}" reRender="a1"/>
                            </h:inputText>
                            <h:outputText id="txtNewAcNo" styleClass="output" value="#{DDSAgentMaster.newAcNo}"/>
                        </h:panelGroup>
                        <h:outputLabel style="display:#{DDSAgentMaster.displayBlank}"/>
                        <h:outputLabel style="display:#{DDSAgentMaster.displayBlank}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="custdtlGrid" styleClass="row2" style="display:#{DDSAgentMaster.displayAcct}">
                        <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name" />
                        <h:outputText id="txtCustName" styleClass="output" value="#{DDSAgentMaster.custname}"/>
                        <h:outputLabel id="lblOpenDt" styleClass="label" value="Account Opening Date" />
                        <h:outputText id="txtOpenDt" styleClass="output" value="#{DDSAgentMaster.openDate}"/>
                    </h:panelGrid>   
                    <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="jointdtlGrid" styleClass="row1" style="display:#{DDSAgentMaster.displayAcct}">
                        <h:outputLabel id="lblJtName" styleClass="label" value="Joint Names" />
                        <h:outputText id="txtJtName" styleClass="output" value="#{DDSAgentMaster.jointName}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>       
                    <h:panelGrid id="gridPanelTable" styleClass="row2" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable value="#{DDSAgentMaster.datagrid}" var="dataItem1"
                                            rowClasses="gridrow1, gridrow2" id="taskList1" rows="3" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                            onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="80px"><h:outputText style="text-align:center" value="Agent Code"/></rich:column>
                                        <rich:column width="200px"><h:outputText style="text-align:center" value="Agent Name"/></rich:column>
                                        <rich:column><h:outputText style="text-align:center" value="Address"/></rich:column>
                                        <rich:column width="100px"><h:outputText style="text-align:center" value="Phone No."/></rich:column>
                                        <rich:column width="100px"><h:outputText style="text-align:center" value="Status"/></rich:column>
                                        <rich:column><h:outputText style="text-align:center" value="Remarks"/></rich:column>
                                        <rich:column width="150px"><h:outputText style="text-align:center" value="DDS Machine Password"/></rich:column>
                                        <rich:column width="100px"><h:outputText style="text-align:center" value="Agent Account No"/></rich:column>
                                        <rich:column width="50px"><h:outputText style="text-align:center" value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="80px" style="text-align: center;"><h:outputText value="#{dataItem1.agentCode}" style="text-align:center"/></rich:column>
                                <rich:column width="200px"><h:outputText value="#{dataItem1.name}"style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.address}"style="text-align:center"/></rich:column>
                                <rich:column width="100px"><h:outputText value="#{dataItem1.phone}"style="text-align:center"/></rich:column>
                                <rich:column width="100px"><h:outputText value="#{dataItem1.status}"style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.remarks}"style="text-align:center"/></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem1.password}"style="text-align:center"/></rich:column>
                                <rich:column width="100px"><h:outputText value="#{dataItem1.agAcno}"style="text-align:center"/></rich:column>
                                <rich:column width="50px" style="text-align: center;">
                                    <a4j:commandLink id="selectlink" action="#{DDSAgentMaster.selectData}" reRender="a1" focus="selectlink">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem1}" target="#{DDSAgentMaster.currentData}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnsave" value="Save" disabled="#{DDSAgentMaster.disableSave}" action="#{DDSAgentMaster.saveData}" oncomplete="if(#{DDSAgentMaster.flag==false}){#{rich:component('acView1')}.hide();} else{#{rich:component('acView1')}.show();}" reRender="a1,acView1,lblAgentCode" focus="btnOk"/>
                            <a4j:commandButton id="update" value="Update" disabled="#{DDSAgentMaster.update}" action="#{DDSAgentMaster.updateData}" reRender="a1"/>
                            <a4j:commandButton id="btnrefresh" value="Refresh" action="#{DDSAgentMaster.btnRefresh}" reRender="a1"/>
                            <a4j:commandButton id="btnclose" value="Exit" action="#{DDSAgentMaster.exitAction}" reRender="a1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="acView1" height="80" width="400">
                    <f:facet name="header">
                        <h:outputText value="Information"/>
                    </f:facet>
                    <h:panelGrid id="grdp" style="height:30px;text-align:center" styleClass="row1" width="100%">
                        <h:outputText value="#{DDSAgentMaster.msgBox}" style="font-weight:bold;"/>
                    </h:panelGrid>
                    <h:panelGrid id="grdp1" style="height:30px;text-align:center" styleClass="row2" width="100%">
                        <a4j:commandButton id="btnOk" value="OK" onclick="#{rich:component('acView1')}.hide()" reRender="lblAgentCode"/>
                    </h:panelGrid>
                </rich:modalPanel>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait5')}.show()" onstop="#{rich:component('wait5')}.hide()"/>
            <rich:modalPanel id="wait5" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>