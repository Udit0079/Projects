<%--
    Document   : ShiftMaster
    Created on : Jul 20, 2011, 2:52:43 PM
    Author     : Administrator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Shift Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                    setTimeMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
                function setTimeMask(){
                    jQuery(".calInstTime").mask("99:99");
                }
            </script>
        </head>
        <body>
            <a4j:form id="ShiftMaster">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="headerpanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{ShiftMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Shift Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShiftMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel"   width="100%" style="height:1px;text-align:center;" styleClass="row2">
                        <h:outputText id="errMsg" value="#{ShiftMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                     
                     <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                      <h:outputLabel styleClass="label" style="padding-left:80px" value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>  
                      <h:selectOneListbox size="1" id="operationList"  styleClass="ddlist" value="#{ShiftMaster.operation}">
                          <f:selectItems value="#{ShiftMaster.operationList}"/>
                          <a4j:support event="onblur" action="#{ShiftMaster.setOperation}" reRender="popUpGridPanel,mainPanel,save,delete" oncomplete="if(#{ShiftMaster.operation=='2'}){
                                                                                                                                                                #{rich:component('popUpGridPanel')}.show();}
                                                                                                                                                                else 
                                                                                                                                                                {#{rich:component('popUpGridPanel')}.hide();};
                                                                                                                                                                 #{rich:element('txtShiftCode')}.style=setTimeMask();
                                                                                                                                                                 #{rich:element('txtDescription')}.style=setTimeMask();
                                                                                                                                                                #{rich:element('txtTimeIn')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtTimeOut')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtBreakFrom')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtBreakTo')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtGraceTimeIn')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtGraceTimeOut')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtGraceBreakTime')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtODTimeFirstHalf')}.style=setTimeMask();
                                                                                                                                                                  #{rich:element('txtODTimeSecondHalf')}.style=setTimeMask();"/>
                        </h:selectOneListbox>
                      <h:outputText style="padding-left:80px"/>
                      <h:outputText style="padding-left:80px"/>
                      </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel1" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Shift Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtShiftCode" maxlength="6" disabled="#{ShiftMaster.disableShiftCode}" onkeyup="this.value = this.value.toUpperCase();" style="width:35px;text-align:center" styleClass="input" value="#{ShiftMaster.shiftCode}"/>
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Description"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtDescription" maxlength="100" disabled="#{ShiftMaster.disableDescription}" onkeyup="this.value = this.value.toUpperCase();" style="width:100px;" styleClass="input"  value="#{ShiftMaster.description}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel2" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Time In"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtTimeIn" disabled="#{ShiftMaster.disableTimeIn}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.timeIn}"/>
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Time Out"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtTimeOut" disabled="#{ShiftMaster.disableTimeOut}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.timeOut}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel3" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Break From"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtBreakFrom" disabled="#{ShiftMaster.disableBreakFrom}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.breakFrom}"/>
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Break To"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtBreakTo" disabled="#{ShiftMaster.disableBreakTo}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.breakTo}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel4" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Grace Time In"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtGraceTimeIn" disabled="#{ShiftMaster.disableGraceTimeIn}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.graceTimeIn}"/>
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Grace Time Out"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtGraceTimeOut" disabled="#{ShiftMaster.disableGraceTimeOut}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.graceTimeOut}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel5" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Grace Break Time"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtGraceBreakTime" disabled="#{ShiftMaster.disableGraceBreakTime}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.graceBreakTime}"/>
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="OD Time First Half"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtODTimeFirstHalf" disabled="#{ShiftMaster.disableOdisableimeFirstHalf}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.odTimeFirstHalf}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="OD Time Second Half"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtODTimeSecondHalf" disabled="#{ShiftMaster.disableOdisableimeSecondHalf}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{ShiftMaster.odTimeSecondHalf}"/>
                        <h:outputLabel/><h:outputLabel/>
                    </h:panelGrid>
                </h:panelGrid>
              
                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save" value="Save" action="#{ShiftMaster.saveAction}" disabled="#{ShiftMaster.disableSaveButton}"
                                               reRender="mainPanel"/>
                            
                           <a4j:commandButton id="delete" value="Delete" action="#{ShiftMaster.deleteAction}" disabled="#{ShiftMaster.disableDeleteButton}"
                                               oncomplete="#{rich:element('tablePanel1')}.style.display='';"
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{ShiftMaster.cancelAction}" type="reset"
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{ShiftMaster.exitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="200" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                   <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Shift Master Details" style="text-align:center;"/>
                    </h:panelGrid>
                    </f:facet>  
                  <h:panelGrid columnClasses="vtop" columns="1" id="tablePanel1"  styleClass="row2" width="100%">
                     <a4j:region>
                         <rich:dataTable value="#{ShiftMaster.shiftMasterTable}"
                                            var="dataItem"
                                            rowClasses="gridrow1,gridrow2"
                                            id="taskList1"
                                            rows="3"
                                            columnsWidth="100"
                                            rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                            width="100%">
                           <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Shift Code" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Description" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Time In"  style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Time Out" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Break From"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Break To"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Grace Time In"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Grace Time Out"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Grace Break Time"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="OD Time First Half"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="OD Time Second Half"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                               <rich:column><h:outputText value="#{dataItem.sno}" style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.shiftCode}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.description}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.timeIn}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.timeOut}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.breakFrom}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.breakTo}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.graceTimeIn}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.graceTimeOut}"  style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.graceBreakTime}"  style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.odTimeFirstHalf}"  style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.odTimeSecondHalf}" style="text-align:center"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                 <a4j:commandLink id="selectlink" action="#{ShiftMaster.setShiftRowValues}"
                                                     oncomplete="#{rich:element('txtTimeIn')}.style=setTimeMask();
                                                     #{rich:element('txtTimeOut')}.style=setTimeMask();
                                                     #{rich:element('txtBreakFrom')}.style=setTimeMask();
                                                     #{rich:element('txtBreakTo')}.style=setTimeMask();
                                                     #{rich:element('txtGraceTimeIn')}.style=setTimeMask();
                                                     #{rich:element('txtGraceTimeOut')}.style=setTimeMask();
                                                     #{rich:element('txtGraceBreakTime')}.style=setTimeMask();
                                                     #{rich:element('txtODTimeFirstHalf')}.style=setTimeMask();
                                                     #{rich:element('txtODTimeSecondHalf')}.style=setTimeMask();"
                                reRender="mainPanel,txtTimeIn,txtTimeOut,txtBreakFrom,txtBreakTo,txtGraceTimeIn,txtGraceTimeOut,txtGraceBreakTime,txtODTimeFirstHalf,txtODTimeSecondHalf,popUpGridPanel" focus="selectlink">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" ondblclick=" #{rich:component('popUpGridPanel')}.hide();"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ShiftMaster.currentShiftItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ShiftMaster.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                   <f:facet name="footer">
                                       <h:outputText value="#{ShiftMaster.totalShiftRecords} rows found" style="text-align:center" />
                                </f:facet>
                         </rich:dataTable>
                          <rich:datascroller align="left" for="taskList1" maxPages="10" />
                     </a4j:region>
                 </h:panelGrid>
              </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>

