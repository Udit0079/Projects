<%-- 
    Document   : taxslabmaster
    Created on : Jun 15, 2012, 3:34:31 PM
    Author     : Ankit Verma
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
            <title>Slab Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="slabMasterForm">
                <h:panelGrid id="slabMasterPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{TaxSlabMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Slab Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TaxSlabMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="gridPanel"   width="100%" style="height:1px;text-align:center" styleClass="row1">
                        <h:outputText id="errMsg51" value="#{TaxSlabMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                                         
                   <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:80px" styleClass="ddlist" value="#{TaxSlabMaster.operation}">
                                <f:selectItems value="#{TaxSlabMaster.operationList}"/>
                                <a4j:support event="onblur" action="#{TaxSlabMaster.setOperationOnBlur}"  reRender="popUpGridPanel,mainPanel,errMsg51,slabMasterGrid" oncomplete="if(#{TaxSlabMaster.operation=='2'}){ #{rich:component('popUpGridPanel')}.show(); } else {  #{rich:component('popUpGridPanel')}.hide(); }" />
                        </h:selectOneListbox>

                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                      
                      <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="slabGridPane4" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="fromAmount" styleClass="output" value="From Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText maxlength="10" id="fromAmountText"  styleClass="input" value="#{TaxSlabMaster.slabFromAmount}" style="width:80px"/>
                        <h:outputLabel id="toAmount" styleClass="output" value="To Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText maxlength="10" id="toAmountText"  styleClass="input" value="#{TaxSlabMaster.slabToAmount}" style="width:80px"/>
                    </h:panelGrid>
                        
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="slabGridpanelSalry" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="applicableTaxLbl" styleClass="output" value="Applicable Tax(%)"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText maxlength="10" id="applicableTax" styleClass="input" value="#{TaxSlabMaster.aplicableTax}" style="width:50px"/>
                        <h:outputLabel id="taxFor" styleClass="output" value="Tax For"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="dexcriptionList"  styleClass="ddlist" size="1" style="width:80px" value="#{TaxSlabMaster.taxFor}" disabled="#{TaxSlabMaster.disableTaxFor}">
                            <f:selectItems value="#{TaxSlabMaster.taxForList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    
                    
                    <h:panelGrid  id="stopPaymentFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="stopPaymentBtnPanel">
                            <a4j:commandButton id="save" value="Save" action="#{TaxSlabMaster.saveSlabMaster}" 
                                               reRender="slabMasterPanel,errMsg51,slabMasterGrid,delete"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{TaxSlabMaster.deleteSlabMaster}" disabled="#{TaxSlabMaster.disableDelete}" 
                                               reRender="slabMasterPanel,errMsg51,slabMasterGrid"/>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{TaxSlabMaster.refreshSlabMasterForm}" 
                                               reRender="slabMasterPanel,errMsg51,slabMasterGrid"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{SalaryStructure.btnExitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="popUpGridPanel" width="750" moveable="false" height="375" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Tax Slab Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <h:form>
                    <h:panelGrid id="slabMasterGrid" width="100%" style="width:100%;height:300px;" columnClasses="vtop">
                        <rich:dataTable  id="slabList" var="dataItem" value="#{TaxSlabMaster.slabMasterGrid}"
                                         rowClasses="gridrow1, gridrow2" rows="8"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="80px"><h:outputText value="Start Range"/></rich:column>
                                    <rich:column width="80px"><h:outputText value="End Range"/></rich:column>
                                    <rich:column width="80px"><h:outputText value="Applicable Tax(%)"/></rich:column>
                                    <rich:column width="80px"><h:outputText value="Tax For"/></rich:column>
                                    <rich:column width="55px"><h:outputText value="Enter by"/></rich:column>
                                    <rich:column width="35px"><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.rangeFrom}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.rangeTo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.applicableTax}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.taxFor}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.enterBy}"/></rich:column>
                            <rich:column style="text-align:center">
                                <a4j:commandLink ajaxSingle="true" id="selectlink" limitToList="true" action="#{TaxSlabMaster.setRowValuesSlabMasterGrid}" oncomplete="#{rich:component('popUpGridPanel')}.hide()"
                                                 reRender="slabMasterPanel,delete" >
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{TaxSlabMaster.currentItem}"/>
                                </a4j:commandLink>
                                <rich:toolTip for="selectlink" value="Select this row."/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="stopDataScroll" align="left" for="slabList" maxPages="5"/>
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