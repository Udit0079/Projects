<%-- 
    Document   : LockerOperation
    Created on : Dec 14, 2015, 4:38:48 PM
    Author     : Administrator
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Locker Operation</title>
        </head>
        <body>
            <a4j:form id="lockerOperation">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3" style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LockerOperation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Locker Operation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{LockerOperation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col5,col8" columns="3" id="bodyPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                            <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                                <h:outputText id="errmsg" value="#{LockerOperation.message}" styleClass="error"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel44" style="text-align:left;" styleClass="row2">
                            <h:outputLabel id="funcLabel" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunc" styleClass="ddlist" size="1" value="#{LockerOperation.fnDetails}">
                                    <f:selectItems value="#{LockerOperation.fnDetailsList}"/>
                                    <a4j:support action="#{LockerOperation.setFnOption}" event="onblur" reRender="btnSave,txtLockerNo,ddlockerNolist,errmsg,leftPanel,rightPanel,tablePanel"/>
                                </h:selectOneListbox>
                            <h:outputLabel/>
                             <h:outputLabel/>
                            </h:panelGrid>   
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel4" style="text-align:left;" styleClass="row2">
                                <h:outputLabel value="LockerType :" styleClass="label"/>
                                <h:selectOneListbox id="ddLockerType" styleClass="ddlist" value="#{LockerOperation.lockerType}" size="1">
                                    <f:selectItems value="#{LockerOperation.lockerTypeList}"/>
                                    <a4j:support event="onblur" actionListener="#{LockerOperation.onChangeLockerType}" reRender="ddCabNo" focus="ddCabNo"/>
                                </h:selectOneListbox>
                                <h:outputLabel value="Cabinet Number :" styleClass="label"/>
                                <h:selectOneListbox id="ddCabNo" styleClass="ddlist" value="#{LockerOperation.cabNo}" size="1">
                                    <f:selectItems value="#{LockerOperation.cabList}"/>
                                    <a4j:support event="onblur" actionListener="#{LockerOperation.getChangeLockerNo}" reRender="ddlockerNolist,txtLockerNo" focus = "#{LockerOperation.focusId}"/>
                                </h:selectOneListbox>                                                        
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel5" style="text-align:left;" styleClass="row1">
                                <h:outputLabel value="Locker Number :" styleClass="label"/>
                                <h:panelGroup layout="block" id="gPanaelId"> 
                                <h:inputText id="txtLockerNo" styleClass="input" style="width:80px;display:#{LockerOperation.lockerNoStyle}" value="#{LockerOperation.lockerNo}">
                                    <a4j:support event="onblur" actionListener="#{LockerOperation.onChangeLockerNo}" reRender="stxtAccName,stxtOpDate,stxtKeyNo,stxtOperMode,stxtAttachAc,stxtLastLedDt,
                                                 stxtBal,stxtLastOperOn,stxtRentPaidUpto,stxtCurrRent,stxtDueLockerRent,panelImage22,tablePanel,stxtOperName,stxtRentDueDt" focus="selectHH1"/>
                                </h:inputText>
                                 <h:selectOneListbox id="ddlockerNolist" styleClass="ddlist"  size="1" style="display:#{LockerOperation.lockerNoStyle1};width:80px;" value="#{LockerOperation.lockerNoDropDown}" >
                                    <f:selectItems value="#{LockerOperation.lockerNoDropDownList}"/>
                                        <a4j:support action="#{LockerOperation.onChangeLockerNo}" event="onblur" reRender="stxtAccName,stxtOpDate,stxtKeyNo,stxtOperMode,stxtAttachAc,stxtLastLedDt,
                                                 stxtBal,stxtLastOperOn,stxtRentPaidUpto,stxtCurrRent,stxtDueLockerRent,panelImage22,tablePanel,stxtOperName,stxtRentDueDt,selectHH1,selectMM1,selectHH2,selectMM2,selectSS1,selectSS2"/>
                                </h:selectOneListbox>
                                </h:panelGroup>
                                <h:outputLabel id="labelAcName" styleClass="label" value="Account Name"/>
                                <h:outputText id="stxtAccName" styleClass="output" value="#{LockerOperation.accountName}"/>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" styleClass="row2">
                                <h:outputLabel id="labelOpDate" styleClass="label" value="Opening Date"/>
                                <h:outputText id="stxtOpDate" styleClass="output" value="#{LockerOperation.openDate}"/>
                                <h:outputLabel id="labelKeyNo" styleClass="label" value="Key No."/>
                                <h:outputText id="stxtKeyNo" styleClass="output" value="#{LockerOperation.keyNo}"/>                                
                            </h:panelGrid> 
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel7" style="text-align:left;" styleClass="row1">
                                <h:outputLabel id="labelOperMode" styleClass="label" value="Operation Mode"/>
                                <h:outputText id="stxtOperMode" styleClass="output" value="#{LockerOperation.opMode}"/>
                                <h:outputLabel id="labelAttachAc" styleClass="label" value="Attached A/c"/>
                                <h:outputText id="stxtAttachAc" styleClass="output" value="#{LockerOperation.attachedAcNo}"/>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col6" columns="2" id="gridPanel12" style="text-align:left;" styleClass="row2">
                                <h:outputLabel id="labelOperName" styleClass="label" value="Operator Name"/>
                                <h:outputText id="stxtOperName" styleClass="output" value="#{LockerOperation.opOperName}"/>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel8" style="text-align:left;" styleClass="row1">
                                <h:outputLabel id="labelLastLedDt" styleClass="label" value="Last Ledger Date"/>
                                <h:outputText id="stxtLastLedDt" styleClass="output" value="#{LockerOperation.lTrnDate}"/>
                                <h:outputLabel id="labelBal" styleClass="label" value="Balance"/>
                                <h:outputText id="stxtBal" styleClass="output" value="#{LockerOperation.balance}"/>                               
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel9" style="text-align:left;" styleClass="row2">
                                <h:outputLabel id="labelLastOperOn" styleClass="label" value="Last Operated On"/>
                                <h:outputText id="stxtLastOperOn" styleClass="output" value="#{LockerOperation.lOpDate}"/>
                                <h:outputLabel id="labelRentPaidUpto" styleClass="label" value="Rent Paid Upto"/>
                                <h:outputText id="stxtRentPaidUpto" styleClass="output" value="#{LockerOperation.rPaidDt}"/>                            
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel19" style="text-align:left;" styleClass="row1">
                                <h:outputLabel id="labelRentDueDt" styleClass="label" value="Rent Due Date"/>
                                <h:outputText id="stxtRentDueDt" styleClass="output" value="#{LockerOperation.rDueDt}"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel10" style="text-align:left;" styleClass="row2">
                                <h:outputLabel id="labelCurrRent" styleClass="label" value="Current Rent"/>
                                <h:outputText id="stxtCurrRent" styleClass="output" value="#{LockerOperation.curRent}"/>
                                <h:outputLabel id="labelDueLockerRent" styleClass="label" value="Due Locker Rent"/>
                                <h:outputText id="stxtDueLockerRent" styleClass="output" value="#{LockerOperation.dueRent}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel11" style="text-align:left;" styleClass="row1">
                                <h:outputLabel id="labelInTime" styleClass="label" value="In Time"/>
                                <h:panelGroup id="pgTime1" >
                                    <h:outputLabel id="lblHH1" styleClass="headerLabel"  value="HH"  />
                                    <h:selectOneListbox id="selectHH1" value="#{LockerOperation.selectHH1}" styleClass="ddlist" size="1"  disabled="#{LockerOperation.disableInTime}" >
                                        <f:selectItems id="selectHH1List" value="#{LockerOperation.selectHH1List}" />
                                    </h:selectOneListbox>  
                                    <h:outputLabel id="lblMM1" styleClass="headerLabel"  value="MM"  />
                                    <h:selectOneListbox id="selectMM1" value="#{LockerOperation.selectMM1}" styleClass="ddlist" size="1" disabled="#{LockerOperation.disableInTime}" >
                                        <f:selectItems id="selectMM1List" value="#{LockerOperation.selectMM1List}" />
                                    </h:selectOneListbox>  
                                    <h:selectOneListbox id="selectSS1" value="#{LockerOperation.selectSS1}" styleClass="ddlist" size="1" disabled="#{LockerOperation.disableInTime}" >
                                        <f:selectItems id="selectSS1List" value="#{LockerOperation.selectSS1List}" />
                                    </h:selectOneListbox>  
                                </h:panelGroup>
                                <h:outputLabel id="labelOutTime" styleClass="label" value="Out Time"/>
                                <h:panelGroup id="pgTime2" >
                                    <h:outputLabel id="lblHH2" styleClass="headerLabel"  value="HH"  />
                                    <h:selectOneListbox id="selectHH2" value="#{LockerOperation.selectHH2}" styleClass="ddlist" size="1">
                                        <f:selectItems id="selectHH2List" value="#{LockerOperation.selectHH2List}" />
                                    </h:selectOneListbox>  
                                    <h:outputLabel id="lblMM2" styleClass="headerLabel"  value="MM"  />
                                    <h:selectOneListbox id="selectMM2" value="#{LockerOperation.selectMM2}" styleClass="ddlist" size="1">
                                        <f:selectItems id="selectMM2List" value="#{LockerOperation.selectMM2List}" />
                                    </h:selectOneListbox>  
                                    <h:selectOneListbox id="selectSS2" value="#{LockerOperation.selectSS2}" styleClass="ddlist" size="1">
                                        <f:selectItems id="selectSS2List" value="#{LockerOperation.selectSS2List}" />
                                    </h:selectOneListbox>  
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="rightPanel" style="width:100%;height:350px;text-align:center;background-color:#e8eef7;">
                            <h:panelGrid id="panelImage22" columns="1" columnClasses="col8"style="width:100%;text-align:center;" styleClass="row1" >
                                <a4j:mediaOutput id="signature"  style="width:550px;height:280px;"element="img" createContent="#{LockerOperation.createSignature}" value="#{LockerOperation.attachedAcNo}"/>
                            </h:panelGrid>
                        </h:panelGrid>                        
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columnClasses="col1,col1,col1,col1" columns="4" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LockerOperation.tableList}" var="dataItem"
                                            rowClasses="row1, row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"></rich:column>
                                        <rich:column breakBefore="true" style="width:10%;"><h:outputText value="Customer ID."/></rich:column>
                                        <rich:column><h:outputText value="Customer Name"/></rich:column>
                                        <rich:column><h:outputText value="Father Name"/></rich:column>
                                        <rich:column><h:outputText value="Gender"/></rich:column>
                                        <rich:column><h:outputText value="Pan No"/></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.folioNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fatherName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.crPbNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.pNo}" /></rich:column>                                
                            </rich:dataTable>
                            <rich:dataTable value="#{LockerOperation.tableList1}" var="dataItem1"
                                            rowClasses="row1, row2" id="taskList1" rows="3" columnsWidth="50" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="50%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"></rich:column>
                                        <rich:column breakBefore="true" style="width:20%;"><h:outputText value="Date Of Operation"/></rich:column>
                                        <rich:column><h:outputText style="width:10%;" value="In Time"/></rich:column>
                                        <rich:column><h:outputText style="width:10%;" value="Out Time"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem1.dateOfOperation}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.inTime}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.outTime}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" action="#{LockerOperation.saveAction}" type="submit" value="#{LockerOperation.bntButton}" reRender="errmsg,gridPanel44,leftPanel,rightPanel,tablePanel"/>
                            <a4j:commandButton id="btnReset" actionListener="#{LockerOperation.resetAction}" type="reset" value="Reset" reRender="ddFunc,errmsg,leftPanel,rightPanel,tablePanel"/>
                            <a4j:commandButton id="btnExit" action="#{LockerOperation.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
