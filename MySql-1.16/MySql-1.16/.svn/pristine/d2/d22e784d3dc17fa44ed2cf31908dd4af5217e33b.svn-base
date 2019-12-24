<%-- 
    Document   : SalaryStructure
    Created on : May 25, 2011, 3:43:17 PM
    Author     : Sudhir Kumar Bisht
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
            <title>Salary structure</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="salaryStructForm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{SalaryStructure.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Salary Structure"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SalaryStructure.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="gridPanel2"   width="100%" style="height:1px;text-align:center" styleClass="row2">
                        <h:outputText id="errMsg5" value="#{SalaryStructure.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="optionList"  styleClass="ddlist" value="#{SalaryStructure.function}" style="width:86px">
                            <f:selectItems value="#{SalaryStructure.functionList}"/>
                            <a4j:support event="onblur" action="#{SalaryStructure.onChangeFunction}" reRender="popUpGridPanel,salaryStructureGrid,salaryStructureList,errMsg5" 
                                         oncomplete="if(#{SalaryStructure.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); } 
                                         else {  #{rich:component('popUpGridPanel')}.hide(); }"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="purpose" styleClass="output" value="Purpose"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="purposelist" disabled="#{SalaryStructure.disablePurpose}" styleClass="ddlist" size="1" style="width:130px" value="#{SalaryStructure.purpose}">
                            <f:selectItems value="#{SalaryStructure.purposeListBox}"/>
                            <a4j:support event="onblur" action="#{SalaryStructure.onChangePurpose}" reRender="errMsg5,gridPanel14"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="applicableDate" styleClass="output" value="Applicable Date "><font class="required" style="color:red;">*</font></h:outputLabel>
                        <rich:calendar id="appCalendar" enableManualInput="true" disabled="#{SalaryStructure.disableAppliDate}" value="#{SalaryStructure.applicableDate}" datePattern="dd/MM/yyyy" inputSize="9"/>
                        <h:outputLabel id="nature" styleClass="output" value="Nature"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="natureList" disabled="#{SalaryStructure.disableNature}" styleClass="ddlist" size="1" style="width:130px" value="#{SalaryStructure.nature}">
                            <f:selectItems value="#{SalaryStructure.natureSelectList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel12" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="taxableLabel" styleClass="output" value="Taxable"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="taxableList" disabled="#{SalaryStructure.disabletaxable}" styleClass="output" size="1" style="width:86px" value="#{SalaryStructure.taxable}">
                            <f:selectItems value="#{SalaryStructure.taxableSelectList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="descLabel" styleClass="output" value="Description"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText maxlength="100" id="descLabelText" disabled="#{SalaryStructure.disableDesc}" styleClass="input" value="#{SalaryStructure.description}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel14" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="crGlLabel" styleClass="output" value="Credit GL Head"/>
                        <h:inputText maxlength="8" id="crGlText" disabled="#{SalaryStructure.disableCrGl}" styleClass="input" value="#{SalaryStructure.crGl}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                        
                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save" disabled="#{SalaryStructure.disableSave}" value="Save" action="#{SalaryStructure.saveSalaryStructure}" 
                                               reRender="save,delete,salaryStructureList,salaryStructureGrid,taxableList,natureList,descLabelText,appCalendar,errMsg5,purposelist,optionList,gridPanel14"/>
                            <a4j:commandButton id="delete" disabled="#{SalaryStructure.disableDelete}" action="#{SalaryStructure.deleteSalaryStructure}" value="Delete" 
                                               reRender="save,delete,salaryStructureList,salaryStructureGrid,taxableList,natureList,descLabelText,appCalendar,errMsg5,purposelist,optionList,gridPanel14"/>
                            <a4j:commandButton id="cancel" value="Refresh" action="#{SalaryStructure.refresh}" 
                                               reRender="save,delete,salaryStructureList,salaryStructureGrid,taxableList,natureList,descLabelText,appCalendar,errMsg5,purposelist,optionList,gridPanel14"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{SalaryStructure.btnExitAction}" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

            <rich:modalPanel id="popUpGridPanel" width="600" moveable="false" height="375" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Salary Structure Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <h:form>
                    <h:panelGrid id="salaryStructureGrid" style="width:100%;height:300px;" styleClass="row1" columnClasses="vtop" >
                        <rich:dataTable  value="#{SalaryStructure.salaryStructureArrayList}" var="dataItem"
                                         rowClasses="gridrow1, gridrow2" id="salaryStructureList" rows="8" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                         onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="120px"><h:outputText value="Purpose"/></rich:column>
                                    <rich:column width="100px"><h:outputText value="Description"/></rich:column>
                                    <rich:column width="100px"><h:outputText value="Nature"/></rich:column>
                                    <rich:column width="80px"><h:outputText value="Applicable Date"/></rich:column>
                                    <rich:column width="40px"><h:outputText value="Taxable"/></rich:column>
                                    <rich:column width="45px"><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.purpose}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.description}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.nature}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.applicableDate}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.taxable}"/></rich:column>
                            <rich:column style="text-align:center">
                                <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="delete,save,taxableList,natureList,descLabelText,appCalendar,errMsg5,purposelist,optionList,gridPanel14" 
                                                 action="#{SalaryStructure.setRowValues}" oncomplete="#{rich:component('popUpGridPanel')}.hide()">
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{SalaryStructure.currentSalStrucItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="salaryStructureList" maxPages="5"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel> 
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>