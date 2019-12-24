<%-- 
    Document   : targentMaster
    Created on : 23 Apr, 2013, 12:00:18 PM
    Author     : Nishant Srivastava
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title> Target Master</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="TargetMaster"/>
            <a4j:form id="TargetMaster">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TargetMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Target Master Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TargetMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{TargetMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{TargetMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="height:30px;" styleClass="row1"/>
                        <h:panelGrid columnClasses="col9" columns="8" id="a6" width="100%" style="height:30px;" styleClass="row1"/>                    
                        <h:panelGrid columnClasses="col9" columns="6" id="a7" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="accountTy" styleClass="label" value="Account Type:" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="accountTy1" tabindex="3" styleClass="ddlist" value="#{TargetMaster.accountType}" size="1"  style="width:120px">
                                <f:selectItems value="#{TargetMaster.accountTypeList}"/>
                                <a4j:support event="onblur"  action="#{TargetMaster.getacctNature}"
                                             reRender="taskList,message,errorMessage" /> 
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="actype" styleClass="label" value="A/C's:" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="actype1" tabindex="4" styleClass="ddlist" value="#{TargetMaster.actype}" size="1" style="width:120px">
                                <f:selectItems value="#{TargetMaster.acTypeList}"/>               
                            </h:selectOneListbox>                       
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="targetAc" styleClass="label" value="Target A/C's:"  style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="targetAc1" style="width:120px" tabindex="5" value="#{TargetMaster.targetAc}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="tragetAmount" styleClass="label" value="Target Amount:" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="tragetAmount1" style="width:120px" tabindex="6" value="#{TargetMaster.tragetAmount}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a11" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="monthlyYear" styleClass="label" value="Mounthly/Yearly:" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="monthlyYear1" tabindex="7" styleClass="ddlist" value="#{TargetMaster.monthlyYear}" size="1" style="width:120px">
                                <f:selectItems value="#{TargetMaster.monthlyYearList}"/> 
                                <a4j:support event="onblur"  action="#{TargetMaster.getMonthYEAR}" 
                                             oncomplete="if(#{TargetMaster.flag1=='true'} || #{TargetMaster.flag2=='true'})
                                             {
                                             #{rich:element('month')}.focus()='true';  
                                             }"reRender="Year1,month,a14,errmsg">
                                </a4j:support>
                            </h:selectOneListbox> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a16" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="month1" styleClass="label" style="padding-left:70px;"></h:outputLabel>
                            <h:selectOneListbox id="month"  styleClass="ddlist" value="#{TargetMaster.month}" size="1" style="width:120px">
                                <f:selectItems value="#{TargetMaster.monthList}"/> 
                                <a4j:support event="onblur"  action="#{TargetMaster.getMonthly}"
                                             reRender="Year1,table,taskList,message,errorMessage,gpFooter" />                                 
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a12" width="100%" style="height:30px;" styleClass="row1">

                            <h:outputLabel id="Year" styleClass="label" value="For Mounthly/Yearly: " style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="Year1" styleClass="ddlist" value="#{TargetMaster.year}" size="1" style="width:120px">
                                <f:selectItems value="#{TargetMaster.yearList}"/> 
                                <a4j:support event="onblur"  action="#{TargetMaster.getYearly}"
                                             reRender="table,taskList,message,errorMessage,gpFooter,btnSave" />                                
                            </h:selectOneListbox>                       
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a14" width="100%" style="height:30px;" styleClass="row1">
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{TargetMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Target Master" /></rich:column>                                        
                                        <rich:column breakBefore="true"><h:outputText value="Acctdesc :" /></rich:column>                                                                            
                                        <rich:column><h:outputText value="Acct Nature :" /></rich:column>
                                        <rich:column><h:outputText value="Orgn Type :" /></rich:column>
                                        <rich:column><h:outputText value="Orgn Code :" /></rich:column>
                                        <rich:column><h:outputText value="Target AcNo : " /></rich:column>
                                        <rich:column><h:outputText value="Target Amount :" /></rich:column>
                                        <rich:column><h:outputText value="From Date : " /></rich:column>
                                        <rich:column><h:outputText value="To Date : " /></rich:column>
                                        <rich:column><h:outputText value="Type : " /></rich:column>
                                        <rich:column><h:outputText value="Period : " /></rich:column>
                                        <rich:column><h:outputText value="Eneter By : " /></rich:column>
                                        <rich:column><h:outputText value="Auth By : " /></rich:column>
                                        <rich:column><h:outputText value="Tran Time : " /></rich:column>
                                        <rich:column width="20"><h:outputText value="SELECT" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>                              
                                <rich:column><h:outputText value="#{dataItem.accountType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acctNature}" /></rich:column>              
                                <rich:column><h:outputText value="#{dataItem.orgntype}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.orgnCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.targetAc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tragetAmount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.month}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.year}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.actype}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.monthlyYear}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterDate}" /></rich:column>

                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{TargetMaster.fillValuesofGridInFields}" reRender="taskList" focus="ddStatus">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{TargetMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{TargetMaster.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="10" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter" focus=""/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TargetMaster.exitBtnAction}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
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
                                    <a4j:commandButton value="Yes" id="btnYes1" action="#{TargetMaster.saveMasterDetail}"  oncomplete="#{rich:component('savePanel')}.hide();return false;" reRender="taskList"/>

                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo1" onclick="#{rich:component('savePanel')}.hide();return false;"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
