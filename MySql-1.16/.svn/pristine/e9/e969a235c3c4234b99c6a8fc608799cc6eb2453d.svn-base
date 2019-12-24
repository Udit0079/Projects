<%-- 
    Document   : LockerRentCollection
    Created on : Sep 22, 2010, 4:09:05 PM
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
            <title>Locker Rent Collection</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{LockerRentCollection.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Locker Rent Collection"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{LockerRentCollection.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LockerRentCollection.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LockerRentCollection.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblPostOption" styleClass="label" value="Post Option :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPostOption" tabindex="1" styleClass="ddlist" value="#{LockerRentCollection.postOption}" size="1" style="width: 150px">
                            <f:selectItems value="#{LockerRentCollection.postOptionList}"/>
                            <a4j:support event="onblur"  action="#{LockerRentCollection.setGlHead}" oncomplete="if(#{rich:element('ddPostOption')}.value == 'SINGLE LOCKER'){#{rich:element('txtLockerNo')}.focus();}else{#{rich:element('btnPost')}.focus();}"
                                         reRender="a3,a5,taskList,table,stxtCrAccount,message,errorMessage,gpFooter"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%" style="height:30px;" styleClass="row2">
                        <h:outputLabel id="lblCrAccount" styleClass="label" value="Credit Account :" style="padding-left:350px;"/>
                        <h:outputText id="stxtCrAccount" styleClass="output" value="#{LockerRentCollection.crAcct}"/>

                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a5" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblLockerNo" styleClass="label" value="#{LockerRentCollection.lblLockerNo}" style="padding-left:350px;"/>
                        <h:inputText id="txtLockerNo" maxlength="6" size="15" tabindex="2" value="#{LockerRentCollection.lockerNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerRentCollection.lblLockerNo == 'Enter Locker No. :'}">
                            <a4j:support event="onblur"  action="#{LockerRentCollection.lockerNoLostFocus}"  reRender="a5,a3,table,taskList,message,errorMessage,gpFooter" focus="btnPost"/>
                        </h:inputText>

                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LockerRentCollection.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Cabinate No." /></rich:column>
                                        <rich:column><h:outputText value="Locker Type" /></rich:column>
                                        <rich:column><h:outputText value="Locker No." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Rent" /></rich:column>
                                        <rich:column><h:outputText value="Rent Due Date" /></rich:column>
                                        <rich:column><h:outputText value="Adv Payment Year" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column rendered="#{LockerRentCollection.postOption == 'SINGLE LOCKER'}" style="text-align:center;cursor:pointer;">
                                            <h:outputText value="Apply" />
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.cabNo}"><f:convertNumber type="currency" pattern="#" minFractionDigits="0" integerOnly="true"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.lockerTy}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.lockerNo}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.actNo}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column style="text-align:center">
                                    <h:outputText value="#{dataItem.rent}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.rentDueDt}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.advPayYr}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column style="text-align:center" rendered="#{LockerRentCollection.postOption == 'SINGLE LOCKER'}">
                                    <h:outputText value="#{dataItem.apply}" />
                                    <a4j:support action="#{LockerRentCollection.changeApply}" ajaxSingle="true" event="ondblclick" reRender="taskList,table,gpFooter,errorMessage">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{LockerRentCollection.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{LockerRentCollection.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="1" id="a7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIndication" styleClass="label" value="Double Click On Apply Column To Change The Apply."/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPost" tabindex="3" value="Post" disabled="#{LockerRentCollection.flag}" oncomplete="#{rich:component('postPanel')}.show();#{rich:element('btnPost')}.disabled = true" reRender="a3,a4,a5,message,errorMessage,lpg,taskList,table,gpFooter"/>
                            <a4j:commandButton id="btnRefresh" tabindex="4" value="Refresh" oncomplete="#{rich:element('btnPost')}.disabled = true" action="#{LockerRentCollection.resetForm}"  reRender="a3,a4,a5,message,errorMessage,lpg,taskList,table,gpFooter" focus="ddPostOption"/>
                            <a4j:commandButton id="btnExit" tabindex="5" value="Exit" reRender="message,errorMessage" action="#{LockerRentCollection.exitForm}"/>
                            <a4j:commandButton id="btnReport" tabindex="6" action="#{LockerRentCollection.showReport}"   oncomplete="#{rich:component('postPanel')}.hide();
                                                                                                                             if(#{LockerRentCollection.reportFlag}){
                                                                                                                                     #{rich:component('popUpRepPanel')}.show();
                                                                                                                               }
                                                                                                                               else if(#{LockerRentCollection.reportFlag})
                                                                                                                               {
                                                                                                                                    #{rich:component('popUpRepPanel')}.hide(); 
                                                                                                                               }"
                                               value="Report" rendered="#{LockerRentCollection.flag==false}" reRender="message,errorMessage,popUpRepPanel" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('btnYesPost')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesPost" ajaxSingle="true" action="#{LockerRentCollection.postRent}"
                                                     
                                          onclick="#{rich:component('postPanel')}.hide();"   reRender="a3,a4,a5,message,errorMessage,lpg,taskList,table,gpFooter,popUpRepPanel" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoPost" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Locker Rent Due Report" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{LockerRentCollection.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
