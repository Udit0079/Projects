<%-- 
    Document   : depreciationapply
    Created on : Nov 25, 2011, 12:34:00 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Apply Depreciation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>

        </head>
        <body>
            <a4j:form id="depreciationapply">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DepreciationApply.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Apply Depreciation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DepreciationApply.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgGrid" columns="1" width="100%" styleClass="row2" style="width:100%;text-align:center;">
                        <h:outputText id="msg" styleClass="error" value="#{DepreciationApply.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblItemGroup" styleClass="label" value="Item Group"/>
                        <h:selectOneListbox id="ddItemGroup" styleClass="ddlist"  size="1" style="width:120px" value="#{DepreciationApply.itemGroup}">
                            <f:selectItems value="#{DepreciationApply.itemGroupList}"/>
                            <a4j:support event="onblur" action="#{DepreciationApply.setFromToDate}" reRender="inputPanel2,msg"  />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"/>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist"  size="1" style="width:120px" value="#{DepreciationApply.branch}">
                            <f:selectItems value="#{DepreciationApply.branchList}"/>
                            <a4j:support event="onblur" action="#{DepreciationApply.dataofItem}" reRender="dditemList,msg"  />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblitemOption" styleClass="label" value="Item Option" />
                        <h:selectOneListbox id="dditemListOption" size="1" styleClass="ddlist" value="#{DepreciationApply.itemOption}" >
                            <f:selectItems value="#{DepreciationApply.itemOptionList}" />
                            <a4j:support event="onblur" action="#{DepreciationApply.itemOptionAction}" reRender="dditemList,ddItemdistinctNo,msg"  />
                        </h:selectOneListbox>     
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblitemcode" styleClass="label" value=" Select Item" />
                        <h:selectOneListbox id="dditemList" size="1" styleClass="ddlist" style = "width:120px" value="#{DepreciationApply.itemCodeString}" disabled= "#{DepreciationApply.disableItemDisNo}">
                            <f:selectItems value="#{DepreciationApply.itemList}" />
                            <a4j:support event="onblur" action="#{DepreciationApply.distinctNo}" reRender="ddItemdistinctNo,frDt" oncomplete="setMask();" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblItemdistinctNo" styleClass="label" value="Item Distinct No."/>
                        <h:selectOneListbox id="ddItemdistinctNo" styleClass="ddlist"  size="1" style="width:120px" value="#{DepreciationApply.itemDistinctNo}" disabled= "#{DepreciationApply.disableItemDisNo}">
                            <f:selectItems value="#{DepreciationApply.itemDistinctNoList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{DepreciationApply.frmDt}" style="width:70px;">
                             <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{DepreciationApply.toDt}" style="width:70px;">
                             <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{DepreciationApply.dataGrid}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList1" rows="5" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Distinct No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Orignal Cost" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Book Value" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Depreciation Percent" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Depreciation Amount" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.itemCode}"style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.itemName}"style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.itemdstncno}"style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.orignalCost}"style="text-align:center" ><f:convertNumber maxFractionDigits="2" minFractionDigits="2" /></h:outputText></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.bookValue}"style="text-align:center" ><f:convertNumber maxFractionDigits="2" minFractionDigits="2" /></h:outputText></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.depPre}"style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.depAmt}"style="text-align:center"  /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="btnPanel" width="100%" columns="1" style="text-align:center" styleClass="footer" >
                        <h:panelGroup >
                            <a4j:commandButton id="btnCalculate" type="submit" value="Calculate" action="#{DepreciationApply.calculateDepreciation}" oncomplete="if(#{DepreciationApply.disableBtn==true}){
                                               #{rich:component('popUpRepPanel')}.show(); } else if(#{DepreciationApply.disableBtn==false}){
                                               #{rich:component('popUpRepPanel')}.hide();}" reRender="gridPanelTable,msg,popUpRepPanel,btnCalculate" />
                            <a4j:commandButton id="btnPost" type="submit" value="Post" action="#{DepreciationApply.postData}" reRender="msg"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{DepreciationApply.refreshData}"  reRender="mainPanel,msg" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{DepreciationApply.exitAction}" reRender="mainPanel,msg" >
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
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" 
                             moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Depreciation Calculation Report" style="text-align:center;"/>
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
                                <a4j:include viewId="#{DepreciationApply.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
