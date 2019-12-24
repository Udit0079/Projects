<%--
    Document   : deadstockauthorization
    Created on : Oct 10, 2011, 6:55:17 PM
    Author     : Ankit Verma
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
            <a4j:keepAlive beanName="DeadstockAuthorization"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Deadstock Authorization</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                var row;
               
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DeadstockAuthorization.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel id="lblAuthMode" styleClass="headerLabel" value="#{DeadstockAuthorization.authMode}"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DeadstockAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{DeadstockAuthorization.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblAuthorizationMode" styleClass="headerLabel"  value="Authorization Mode"  />
                        <h:selectOneListbox id="mode"  styleClass="ddlist" size="1" style="width:120px" value="#{DeadstockAuthorization.authModeCombo}" >

                            <f:selectItems value="#{DeadstockAuthorization.authModeList}" />
                            <a4j:support event="onblur" oncomplete="if(#{DeadstockAuthorization.flag==1})
                                         {
                                         #{rich:element('gridPanelTable')}.style.display='none';
                                         #{rich:element('gridPanelTable1')}.style.display='';
                                         #{rich:element('gridpan')}.style.display='none';
                                         }
                                         else if(#{DeadstockAuthorization.flag==2})
                                         {
                                         #{rich:element('gridPanelTable')}.style.display='';
                                         #{rich:element('gridPanelTable1')}.style.display='none';
                                         #{rich:element('gridpan')}.style.display='';
                                         }
                                         else if(#{DeadstockAuthorization.flag==3})
                                         {
                                         #{rich:element('gridPanelTable')}.style.display='none';
                                         #{rich:element('gridPanelTable2')}.style.display='';
                                         #{rich:element('gridpan')}.style.display='none';
                                         }
                                         " focus="txtBatchNo" reRender="gridPanelTable,gridPanelTable1,gridpan,lblAuthMode,gridPanelTable2" action="#{DeadstockAuthorization.chooseTable}" />
                        </h:selectOneListbox>
                        <h:outputLabel />
                        <h:outputLabel />
                    </h:panelGrid>

                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px;" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable value="#{DeadstockAuthorization.datagrid1}" var="dataItem1"
                                            rowClasses="gridrow1, gridrow2" id="selectedItemList" rows="3" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                            onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;" >
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column ><h:outputText  value="Batch No." /></rich:column>
                                        <rich:column ><h:outputText  value="Account No." /></rich:column>
                                        <rich:column ><h:outputText  value="Customer Name" /></rich:column>
                                        <rich:column ><h:outputText  value="Credit" /></rich:column>
                                        <rich:column ><h:outputText  value="Debit" /></rich:column>
                                        <rich:column ><h:outputText  value="Entered By" /></rich:column>
                                        <rich:column ><h:outputText  value="Auth" /></rich:column>
                                        <rich:column ><h:outputText  value="Details" /></rich:column>
                                        <rich:column ><h:outputText  value="Rec No." /></rich:column>
                                        <rich:column ><h:outputText  value="Transaction Date" /></rich:column>
                                        <rich:column ><h:outputText  value="Respond Br." /></rich:column>
                                        <rich:column ><h:outputText value="Origin Br." /></rich:column>
                                        <rich:column ><h:outputText value="Instr No." /></rich:column>   
                                </rich:columnGroup>
                                </f:facet>
                                <rich:column ><h:outputText  value="#{dataItem1.trsno}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.acno}" /></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.custName}" /></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.crAmt}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.drAmt}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.enterBy}" /></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.auth}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.details}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.recNo}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.dt}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.destBrnId}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.orgBrnId}"/></rich:column>
                                <rich:column  ><h:outputText  value="#{dataItem1.instNo}"/></rich:column>
                            </rich:dataTable>
                           <rich:datascroller id="selectedListScroller" align="left" for="selectedItemList" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>



                    <h:panelGrid id="gridPanelTable1" style=" width:100%;height:0px;display:none;" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <%-- value="#{DeadstockAuthorization.datagrid}" --%>
                            <rich:dataTable  var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList2" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="S No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Total Issued" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Name & Desc." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="IssuedDt/SellingDt" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Branch Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Amt Per Unit" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Status" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Issue By/Sell By" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Auth." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Remarks" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>

                                <rich:column width="100px" ><h:outputText  style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList2" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable2" style=" width:100%;height:0px;display:none;" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <%-- value="#{DeadstockAuthorization.datagrid}" --%>
                            <rich:dataTable  var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList3" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="S No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Issued" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="IssuedDt/SellingDt" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Branch Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Amt Per Unit" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Status" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Issue By/Sell By" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Auth." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Remarks" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="100px" ><h:outputText  style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList3" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3" id="gridpan"   style="height:30px;" styleClass="row2" width="100%" >
                        <h:outputLabel id="lblBatchNoToAuth" styleClass="headerLabel"  value="Batch No. to Authorise"  />
                        <h:inputText id="txtBatchNo" styleClass="input" style="width:60px" value="#{DeadstockAuthorization.batchNo}">
                             <a4j:support action="#{DeadstockAuthorization.setSelectedBatch}" event ="onblur"
                                             reRender="selectedItemList,selectedListScroller,stxtError,otxtCrAmount,otxtDrAmount,btnAuthorise"/>
                        </h:inputText>
                        <h:panelGroup id="amountPanel">
                            <h:outputLabel id="lblCrAmount" styleClass="label" value="Cr. Amount :  " />
                            <h:outputText id="otxtCrAmount" styleClass="label" value="#{DeadstockAuthorization.crAmount}" style="color:blue;">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGroup>
                        <h:panelGroup id="amountPane2">
                            <h:outputLabel id="lblDrAmount" styleClass="label" value="Dr. Amount :  " />
                            <h:outputText id="otxtDrAmount" styleClass="label" value="#{DeadstockAuthorization.drAmount}" style="color:blue;">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGroup>
                        <h:outputText />
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnAuthorise" value="Authorize" action="#{DeadstockAuthorization.authorizeRecords}" disabled="#{DeadstockAuthorization.disableAuthorizeBtn}"  reRender="mainPanelGrid,stxtError,gridPanelTable,otxtCrAmount,otxtDrAmount" >
                            </a4j:commandButton>
                            <a4j:commandButton  id="btnRefresh" value="Refresh" action="#{DeadstockAuthorization.refreshPage}" reRender="mainPanelGrid,stxtError,gridPanelTable,otxtCrAmount,otxtDrAmount">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{DeadstockAuthorization.exitAction}" >
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
        </body>
    </html>
</f:view>
