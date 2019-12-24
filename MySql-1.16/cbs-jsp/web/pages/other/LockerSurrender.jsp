<%-- 
    Document   : LockerSurrender
    Created on : Sep 18, 2010, 1:05:26 PM
    Author     : ROHIT KRISHNA GUPTA
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
            <title>Locker Surrender</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="LockerSurrender"/>
            <a4j:form id="LockerSurrender">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{LockerSurrender.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Locker Surrender"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{LockerSurrender.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                            <h:outputText id="errorMessage" styleClass="error" value="#{LockerSurrender.errorMessage}"/>
                            <h:outputText id="message" styleClass="msg" value="#{LockerSurrender.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblOpt" styleClass="label" value="Option :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddOpt" styleClass="ddlist" value="#{LockerSurrender.opt}" size="1" style="width: 102px">
                                    <f:selectItems value="#{LockerSurrender.optList}"/>
                                    <a4j:support ajaxSingle="true" event="onblur" action="#{LockerSurrender.optAction}" focus="#{LockerSurrender.focusId}" 
                                                 reRender="a19,authGrid,ddCabNo,ddLockerType,txtLockerNo,stxtLesseName,stxtLesseAcNo,stxtRentDueDt,message,errorMessage"/>
                                </h:selectOneListbox>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>                        
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblCabNo" styleClass="label" value="Cabinate No. :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddCabNo" styleClass="ddlist" value="#{LockerSurrender.cabNo}" size="1" style="width: 102px" disabled="#{LockerSurrender.disCab}">
                                    <f:selectItems value="#{LockerSurrender.cabNoList}"/>
                                    <a4j:support ajaxSingle="true" event="onblur" action="#{LockerSurrender.lockerTypeCombo}" reRender="ddLockerType,message,errorMessage" focus="ddLockerType"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblLockerType" styleClass="label" value="Locker Type :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddLockerType" styleClass="ddlist" value="#{LockerSurrender.lockerType}" size="1" style="width: 102px" disabled="#{LockerSurrender.disLocker}">
                                    <f:selectItems value="#{LockerSurrender.lockerTypeList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblLockerNo" styleClass="label" value="Locker No. :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtLockerNo" maxlength="6" size="16" value="#{LockerSurrender.lockerNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" disabled="#{LockerSurrender.disLocNo}">
                                    <a4j:support event="onblur"  action="#{LockerSurrender.lockerNoLostFocus}" reRender="stxtLesseName,stxtLesseAcNo,stxtRentDueDt,a19,taskList,message,errorMessage" focus="btnSurrender"/>
                                </h:inputText>
                            </h:panelGrid>    
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblLesseName" styleClass="label" value="Lesse's Name :" style="padding-left:70px;"/>
                                <h:outputText id="stxtLesseName" styleClass="output" value="#{LockerSurrender.lesseName}" style="color:purple;"/>
                                <h:outputLabel id="lblLesseAcNo" styleClass="label" value="Lesse's A/c. No. :" style="padding-left:70px;"/>
                                <h:outputText id="stxtLesseAcNo" styleClass="output" value="#{LockerSurrender.lesseAcNo}" style="color:purple;"/>
                                <h:outputLabel id="lblRentDueDt" styleClass="label" value="Rent Due Date :" style="padding-left:70px;"/>
                                <h:outputText id="stxtRentDueDt" styleClass="output" value="#{LockerSurrender.rentDueDt}" style="color:purple;"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="a19" width="100%" style="display:#{LockerSurrender.disView};height:auto;" styleClass="row2">
                            <a4j:region>
                                <rich:dataTable value="#{LockerSurrender.gridDetail}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="10" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Cabinate No." /></rich:column>
                                            <rich:column><h:outputText value="Locker Type" /></rich:column>
                                            <rich:column><h:outputText value="Locker No." /></rich:column>
                                            <rich:column><h:outputText value="Account No." /></rich:column>
                                            <rich:column><h:outputText value="Customer Name" /></rich:column>
                                            <rich:column><h:outputText value="Rent" /></rich:column>
                                            <rich:column><h:outputText value="Rent Due Date" /></rich:column>
                                            <rich:column><h:outputText value="Mode" /></rich:column>
                                            <rich:column><h:outputText value="Ad Operator1" /></rich:column>
                                            <rich:column><h:outputText value="Ad Operator2" /></rich:column>
                                            <rich:column><h:outputText value="Ad Operator3" /></rich:column>
                                            <rich:column><h:outputText value="Ad Operator4" /></rich:column>
                                            <rich:column><h:outputText value="Security" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.cabNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.lockerType}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.lockerNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.custname}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.rent}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{dataItem.rentDueDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.mode}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.adOpr1}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.adOpr2}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.adOpr3}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.adOpr4}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.security}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="authGrid" style="height:auto;display:#{LockerSurrender.disView1};" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{LockerSurrender.authGridDetail}" var="authDataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="10"  rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="6"><h:outputText value="UnAuthorized Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Cabinate No." /></rich:column>
                                            <rich:column><h:outputText value="Locker Type" /></rich:column>
                                            <rich:column><h:outputText value="Locker No." /></rich:column>
                                            <rich:column><h:outputText value="Account No." /></rich:column>
                                            <rich:column><h:outputText value="Enter By"/></rich:column>
                                            <rich:column><h:outputText value="Select"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{authDataItem.cabiNateNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{authDataItem.lockerType}" /></rich:column>
                                    <rich:column><h:outputText value="#{authDataItem.lockerNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{authDataItem.debitedAcNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{authDataItem.debitedCustName}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{LockerSurrender.setTableDataToForm}" 
                                                         reRender="authGrid,ddCabNo,ddLockerType,txtLockerNo,stxtLesseName,stxtLesseAcNo,
                                                         stxtRentDueDt,message,errorMessage,btnDelete">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{authDataItem}" target="#{LockerSurrender.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSurrender" value="Surrender" oncomplete="#{rich:component('surrenderPanel')}.show()"  reRender="message,errorMessage,lpg,a19,taskList"/>
                                    <a4j:commandButton id="btnDelete" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()" disabled="#{LockerSurrender.disDelete}" reRender="message,errorMessage,lpg,a19,taskList"/>
                                    <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LockerSurrender.resetForm}"  reRender="ddOpt,message,errorMessage,lpg,ddCabNo,ddLockerType,txtLockerNo,stxtLesseName,stxtLesseAcNo,stxtRentDueDt,taskList,taskList1" focus="ddOpt"/>
                                    <a4j:commandButton id="btnExit" value="Exit" action="#{LockerSurrender.exitFrm}" reRender="message,errorMessage" />
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                        
                    </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="surrenderPanel" autosized="true" width="250" onshow="#{rich:element('btnYesSurr')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure For Surrender ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSurr" ajaxSingle="true" action="#{LockerSurrender.surrenderBtn}"
                                                       onclick="#{rich:component('surrenderPanel')}.hide();" reRender="message,errorMessage,
                                                       lpg,taskList,a19,ddCabNo,ddLockerType,txtLockerNo,stxtLesseName,stxtLesseAcNo,
                                                         stxtRentDueDt,authGrid,btnDelete"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSurr" onclick="#{rich:component('surrenderPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true" action="#{LockerSurrender.deleteBtn}"
                                                       onclick="#{rich:component('deletePanel')}.hide();" reRender="authGrid,ddCabNo,ddLockerType,txtLockerNo,stxtLesseName,stxtLesseAcNo,
                                                         stxtRentDueDt,message,errorMessage,btnDelete"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>                
        </body>
    </html>
</f:view>
