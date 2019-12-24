<%-- 
    Document   : cbsbillprintmanager
    Created on : Sep 26, 2011, 1:07:27 PM
    Author     : Sudhir Kr Bisht
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>PO/DD/FD Parameters</title>
        </head>
        <body>
            <a4j:form id="billPrintManager">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BillPrintManager.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="PO/DD/FD Parameters"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BillPrintManager.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorMsgReporting"   width="100%"   style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id="errorGrid" value="#{BillPrintManager.message}" style="color:red" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="billTypeGrid" columns="6" columnClasses="col1,col2,col1,col2,col1,col1" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputLabel id="billTypeLabel" styleClass="label" value="Bill Type">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="billTypeText" disabled="#{BillPrintManager.disablebillType}" styleClass="ddlist"  size="1" style="width:80px" value="#{BillPrintManager.billType}">
                            <f:selectItems value="#{BillPrintManager.billTypeList}"/>
                        </h:selectOneListbox>    
                        <h:outputLabel id="fieldOrderLabel" styleClass="label" value=" Field Type">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                         <h:selectOneListbox id="fieldOrderText" styleClass="ddlist" size="1" disabled="#{BillPrintManager.disablebillType}" value="#{BillPrintManager.labelValue}" style="width:80px">
                            <f:selectItems value="#{BillPrintManager.labelValueList}"/>
                            <a4j:support event="onblur" action="#{BillPrintManager.getFieldList}" reRender="fieldNameText,errorGrid" focus="fieldNameText"/>
                        </h:selectOneListbox>   
                        <h:outputLabel id="fieldLabel" styleClass="label" value="Field name">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="fieldNameText" styleClass="ddlist"  size="1" value="#{BillPrintManager.fieldName}" style="width:120px">
                            <f:selectItems value="#{BillPrintManager.fieldNameList}"/>
                            <a4j:support event="onblur" action="#{BillPrintManager.onBlurBillTypeEvent}"
                                         oncomplete="if(#{BillPrintManager.flag=='false'}) {#{rich:element('parameterGrid')}.style.display=none;}else{#{rich:element('parameterGrid')}.style.display='';}"
                                         reRender="topSpaceGrid,parameterList,errorGrid" focus="xText"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                            
                    <h:panelGrid id="topSpaceGrid" columns="6" columnClasses="col1,col2,col1,col2,col1,col1" style="height:30px;width:100%"  styleClass="row1" width="100%">
                        <h:outputLabel id="xLabel" styleClass="label" value="X Co-ordinate:">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText id="xText" styleClass="input" value="#{BillPrintManager.x}" maxlength="2" size="10"/>
                        <h:outputLabel id="yLabel" styleClass="label" value="Y Co-ordinate:">
                        <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText id="yText" styleClass="input" value="#{BillPrintManager.y}" maxlength="2" size="10"/>
                        <h:outputLabel id="colWidth" styleClass="label" value="Column Width:">
                        <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText id="colWidthText" styleClass="input" value="#{BillPrintManager.width}" maxlength="2" size="10"/>
                    </h:panelGrid>
                    <h:panelGrid id="parameterGrid" width="100%" style="width:100%;height:0px;display:none;" columnClasses="vtop">
                        <rich:dataTable id="parameterList" value="#{BillPrintManager.printGridList}" var="dataItem" rowClasses="gridrow1, gridrow2" rows="10"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                        width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="10"><h:outputText value=""/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Bill Type" /></rich:column>
                                    <rich:column ><h:outputText value="Field Type" /></rich:column>
                                    <rich:column ><h:outputText value="Field Name"/></rich:column>
                                    <rich:column width="180px" ><h:outputText value="Field Order"/></rich:column>
                                    <rich:column width="180px"><h:outputText value="X Co-Ordinate"/></rich:column>
                                    <rich:column width="140px"><h:outputText value="Y Co-Ordinate"/></rich:column>
                                    <rich:column width="140px"><h:outputText value="Column Width"/></rich:column>
                                    <rich:column width="140px"><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.billType}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.fieldType}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.fieldName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.fieldOrder}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.x}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.y}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.width}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  ajaxSingle="true" id="selectlink" action="#{BillPrintManager.setGridData}"
                                                  reRender="update,delete,save,parameterGrid,parameterList,errorGrid,billTypeText,fieldNameText,fieldOrderText,xText,yText,colWidthText">
                                    <h:graphicImage id="imagerender" value="/resources/images/select.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{BillPrintManager.currentprintGrid}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="stopDataScroll" align="left" for="parameterList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton id="update" disabled="#{BillPrintManager.disableUpdate}" value="Update" action="#{BillPrintManager.updateButton}"
                                               reRender="update,delete,save,parameterGrid,parameterList,errorGrid,billTypeText,fieldNameText,fieldOrderText,xText,yText,colWidthText"/>
                            <a4j:commandButton id="btnRefresh"  value="Refresh" action="#{BillPrintManager.refresh}"
                                               reRender="update,delete,save,parameterGrid,parameterList,errorGrid,billTypeText,fieldNameText,fieldOrderText,xText,yText,colWidthText"
                                               oncomplete="#{rich:element('parameterGrid')}.style.display=none;"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{BillPrintManager.exit}" 
                                               reRender="update,delete,save,parameterGrid,parameterList,errorGrid,billTypeText,fieldNameText,fieldOrderText,xText,yText,colWidthText"
                                               oncomplete="#{rich:element('parameterGrid')}.style.display=none;"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
