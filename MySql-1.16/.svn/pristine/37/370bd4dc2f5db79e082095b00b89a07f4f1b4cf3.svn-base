<%--
    Document   : StockBookIssueRegister
    Created on : 13 Jan, 2011, 11:09:55 AM
    Author     : root
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
            <title>Stock/Book Issue Register</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="StockBookIssueRegister">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="header" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{StockBookIssueRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Stock Book Issue Register"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{StockBookIssueRegister.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>


                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel01" style="width:100%;text-align:center;" styleClass="row2">
                            <h:outputText id="stxtMssg" styleClass="error" value="#{StockBookIssueRegister.msg}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3" columns="2" id="Panel0" style="width:100%;" styleClass="row1">

                            <h:outputLabel id="lblGoTo" styleClass="label" for="ddGotTo" value="GoTo:"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddGotTo" value="#{StockBookIssueRegister.cmbGoto}" size="1" style="width:110px" styleClass="ddlist">
                                <f:selectItems value="#{StockBookIssueRegister.comboGoto}"/>
                                <a4j:support event="onchange" action="#{StockBookIssueRegister.tableChange}" reRender="mainPanel,stxtMssg,RightPanel,p3,p4,p5,p6" focus="ddInstType"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columns="3" id="subbodyPanel1" style="width:100%;">
                            <h:panelGrid id="leftPanel" columns="1" width="100%" style="height:30px" >

                                <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel1" style="width:100%;" styleClass="row2">

                                    <h:outputLabel id="lblInstType" styleClass="label" for="ddInstType" value="Inst Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddInstType" value="#{StockBookIssueRegister.cmbInstType}" size="1" style="width:70px" styleClass="ddlist" >
                                        <f:selectItems value="#{StockBookIssueRegister.comboinstType}"/>
                                        <a4j:support event="onchange" action="#{StockBookIssueRegister.getCode}" focus="ddslb"
                                                     reRender="ddslb,stxtMssg"/>
                                    </h:selectOneListbox>

                                    <h:outputLabel id="lblSlabCode" styleClass="label" for="ddslb" value="Slab Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddslb" style="width:70px" size="1" value="#{StockBookIssueRegister.combCode}" styleClass="ddlist" disabled="#{StockBookIssueRegister.disableSlabCode}">
                                        <f:selectItems value="#{StockBookIssueRegister.comboCode}"/>
                                        <a4j:support  event="onblur" action="#{StockBookIssueRegister.checkSlabAmtCode}"
                                                      oncomplete="if(#{rich:element('ddAmountRange')}.value == ''){#{rich:element('ddslb')}.focus();}
                                                      else if(#{rich:element('ddAmountRange')}.value == null){#{rich:element('ddslb')}.focus();}
                                                      if(#{rich:element('ddAmtTo')}.value == ''){#{rich:element('ddslb')}.focus();}
                                                      else if(#{rich:element('ddAmtTo')}.value == null){#{rich:element('ddslb')}.focus();}
                                                      else{#{rich:element('ddAmtTo')}.focus();}"

                                                      reRender="ddAmountRange,ddAmtTo,stxtMssg"/>
                                    </h:selectOneListbox>


                                </h:panelGrid>


                                <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel2" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblAmountRange" styleClass="label"  value="Amount Range"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddAmountRange" size="1" style="width:70px" styleClass="ddlist" value="#{StockBookIssueRegister.amtRangeFrom}" >
                                        <f:selectItems value="#{StockBookIssueRegister.comboAmtFrm}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lbl" styleClass="label" />
                                    <h:selectOneListbox id="ddAmtTo" style="width:70px" size="1" styleClass="ddlist" value="#{StockBookIssueRegister.amtRangeTo}" >
                                        <f:selectItems value="#{StockBookIssueRegister.comboAmtTo}"/>
                                        <a4j:support  event="onblur" action="#{StockBookIssueRegister.checkGoto}"
                                                      oncomplete="if(#{rich:element('ddAmtTo')}.value == ''){#{rich:element('ddInstType')}.focus();}
                                                      else if(#{rich:element('ddAmtTo')}.value == null){#{rich:element('ddInstType')}.focus();}
                                                      else if
                                                      ((#{StockBookIssueRegister.cmbGoto == '1'}))
                                                      {#{rich:element('txtTo666')}.focus();}
                                                      else if
                                                      ((#{StockBookIssueRegister.cmbGoto == '2'}))
                                                      {#{rich:element('ddNumberFrom')}.focus();}"
                                                      reRender="Panel3,Panel4,ddNumberFrom,ddNoTo,ddPrintLot,ddNumberOfLeaves,stxtMssg,ddslb,ddGotTo"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel3" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblNumberFrom"  styleClass="label"  value="No. From"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:panelGroup id="p3">

                                        <h:selectOneListbox  id="ddNumberFrom" size="1" style="width:70px" styleClass="ddlist" rendered="#{StockBookIssueRegister.cmbGoto == '2'}" disabled="#{StockBookIssueRegister.noFromDisable}" value ="#{StockBookIssueRegister.noFromDataIssue}">
                                            <f:selectItems value="#{StockBookIssueRegister.combAmountFrom}"/>
                                            <a4j:support  event="onblur" action="#{StockBookIssueRegister.setTableIssue}" focus="btnAdd"
                                                          reRender="issueregister,btnSave,stxtMssg,ddNoTo,ddNumberOfLeaves,ddPrintLot,ShowTable  ,Panel3,Panel4,ddNumberFrom,ddNoTo,ddPrintLot,ddNumberOfLeaves,stxtMssg,ddslb,ddGotTo,btnAdd"/>
                                        </h:selectOneListbox>
                                        <h:inputText id="txtTo666" style="width:68px" value="#{StockBookIssueRegister.numFrom}"  rendered="#{StockBookIssueRegister.cmbGoto == '1'}" styleClass="input" maxlength="8">


                                        </h:inputText>
                                    </h:panelGroup>
                                    <h:outputLabel id="lblNoTo" styleClass="label"  value="No. To"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:panelGroup id="p4">

                                        <h:selectOneListbox id="ddNoTo" size="1" style="width:70px" styleClass="ddlist" rendered="#{StockBookIssueRegister.cmbGoto == '2'}" value ="#{StockBookIssueRegister.noToDataIssue}" disabled="#{StockBookIssueRegister.noToDisable}" >
                                            <f:selectItems value="#{StockBookIssueRegister.combAmountTo}"/>
                                        </h:selectOneListbox>&nbsp
                                        <h:inputText  id="txtTo667" style="width:69px" value="#{StockBookIssueRegister.numTo}"  rendered="#{StockBookIssueRegister.cmbGoto == '1'}" styleClass="input" maxlength="8" >
                                            <a4j:support action="#{StockBookIssueRegister.setLeaves}" event="onblur" reRender="txtTo669,stxtMssg,txtTo668" focus="txtTo668" />

                                        </h:inputText>
                                    </h:panelGroup>

                                </h:panelGrid>


                                <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel4" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblNumberOfLeaves" styleClass="label"  value="No. Of Leaves"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:panelGroup id="p6">

                                        <h:selectOneListbox id="ddNumberOfLeaves" size="1" style="width:70px" styleClass="ddlist" rendered="#{StockBookIssueRegister.cmbGoto == '2'}" value ="#{StockBookIssueRegister.leavesDataIssue}" disabled="#{StockBookIssueRegister.noOfLeavwsDisable}" >
                                            <f:selectItems value="#{StockBookIssueRegister.combNoLeaves}"/>
                                        </h:selectOneListbox>
                                        <h:inputText id="txtTo669" style="width:69px" value="#{StockBookIssueRegister.txtLeaves}"  rendered="#{StockBookIssueRegister.cmbGoto == '1'}" styleClass="input" maxlength="8" disabled="true"/>

                                    </h:panelGroup>
                                    <h:outputLabel id="lblPrintLot" styleClass="label"  value="Print Lot" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:panelGroup id="p5">

                                        <h:selectOneListbox id="ddPrintLot" size="1" style="width:70px" styleClass="ddlist" rendered="#{StockBookIssueRegister.cmbGoto == '2'}" value="#{StockBookIssueRegister.lotDataIssue}" disabled="#{StockBookIssueRegister.printLOtDisable}" >
                                            <f:selectItems value="#{StockBookIssueRegister.combprintlot}"/>
                                        </h:selectOneListbox>&nbsp;
                                        <h:inputText  id="txtTo668" style="width:69px" value="#{StockBookIssueRegister.lot}"  rendered="#{StockBookIssueRegister.cmbGoto == '1'}" styleClass="input" maxlength="10" onkeyup="this.value = this.value.toUpperCase();">
                                            <a4j:support action="#{StockBookIssueRegister.setTable}"  event="onblur" focus="btnSave" reRender="taskList,btnSave,stxtMssg"/>
                                        </h:inputText>
                                    </h:panelGroup>

                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel4gh" style="width:100%;text-align:center;" styleClass="row2">
                                    <a4j:commandButton id="btnAdd" value="Add" disabled="#{StockBookIssueRegister.addButton}" >
                                        <a4j:support action="#{StockBookIssueRegister.addButton}"  event="onblur" focus="btnSave" reRender="issueregister,btnSave,stxtMssg,ddNoTo,ddNumberOfLeaves,ddPrintLot,ShowTable  ,Panel3,Panel4,ddNumberFrom,ddNoTo,ddPrintLot,ddNumberOfLeaves,stxtMssg,ddslb,ddGotTo,btnAdd"/>

                                    </a4j:commandButton>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col7" columns="1" id="RightPanel" width="100%" styleClass="row2" style="height:170px;">
                                <a4j:region>
                                    <rich:dataTable  value="#{StockBookIssueRegister.issueList}" var="Item" rendered="#{StockBookIssueRegister.flag == 'true'}"
                                                     rowClasses="row1, row2" id="issueregister" rows="6" columnsWidth="20" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>

                                                <rich:column colspan="11">

                                                </rich:column>

                                                <rich:column breakBefore="true">
                                                    <h:outputText value="Inst Type" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="Code" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="No. From" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="No. To" />
                                                </rich:column>



                                                <rich:column>
                                                    <h:outputText value="Amt From" />
                                                </rich:column>
                                                <rich:column>
                                                    <h:outputText value="Amt To" />
                                                </rich:column>
                                                <rich:column>
                                                    <h:outputText value="Leaves" />
                                                </rich:column>
                                                <rich:column>
                                                    <h:outputText value="Print Lot" />
                                                </rich:column>
                                                <rich:column>
                                                    <h:outputText value="Issued By" />
                                                </rich:column>
                                                <rich:column><h:outputText value="Action" /></rich:column>

                                            </rich:columnGroup>
                                        </f:facet>


                                        <rich:column>
                                            <h:outputText value="#{Item.insttype}"  />
                                        </rich:column>

                                        <rich:column>
                                            <h:outputText value="#{Item.code}"  />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="#{Item.numFrom}" />
                                        </rich:column>

                                        <rich:column >
                                            <h:outputText value="#{Item.numTo}" />
                                        </rich:column>
                                        <rich:column >
                                            <h:outputText value="#{Item.amtFrom}">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column >
                                            <h:outputText value="#{Item.amtTo}">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column >
                                            <h:outputText value="#{Item.leaves}" />
                                        </rich:column>
                                        <rich:column >
                                            <h:outputText value="#{Item.printlot}" />
                                        </rich:column>
                                        <rich:column >
                                            <h:outputText value="#{Item.issuedBy}" />
                                        </rich:column>
                                        <rich:column>
                                            <a4j:commandLink id="deletelink1" reRender="subbodyPanel" oncomplete="#{rich:component('deletePanel1')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{row}" target="#{StockBookIssueRegister.currentRow1}" />
                                                <f:setPropertyActionListener value="#{item}" target="#{StockBookIssueRegister.currentItem1}"/>
                                            </a4j:commandLink>
                                            <rich:toolTip for="deletelink" value="Delete" />
                                        </rich:column>
                                    </rich:dataTable>
                                </a4j:region>
                                <a4j:region>
                                    <rich:dataTable  value ="#{StockBookIssueRegister.allreciept}" var="Item"  rendered="#{StockBookIssueRegister.flag == 'false'}"
                                                     rowClasses="row1, row2" id="ShowTable" rows="2" columnsWidth="20" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>

                                                <rich:column colspan="7">

                                                </rich:column>

                                                <rich:column breakBefore="true">
                                                    <h:outputText value="Inst Type" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="Inst Num" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="Series" />
                                                </rich:column>
                                                <rich:column>
                                                    <h:outputText value="Status" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="Amount From" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="Amount To" />
                                                </rich:column>
                                                <rich:column><h:outputText value="Action" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column>
                                            <h:outputText value="#{Item.insType}"  />
                                        </rich:column>

                                        <rich:column>
                                            <h:outputText value="#{Item.ddnum}"  />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="#{Item.series}" />
                                        </rich:column>

                                        <rich:column >
                                            <h:outputText value="#{Item.stats}" />
                                        </rich:column>
                                        <rich:column >
                                            <h:outputText value="#{Item.amtFrm}">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column >
                                            <h:outputText value="#{Item.amtto}">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column>
                                            <a4j:commandLink id="selectlink" oncomplete="#{rich:component('SelectPanel1')}.show()" reRender="ShowTable,stxtMssg"  >
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{Item}" target="#{StockBookIssueRegister.currentItem2}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{StockBookIssueRegister.currentRow2}" />
                                            </a4j:commandLink>
                                            <rich:toolTip for="selectlink" value="Select" />
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="ShowTable" maxPages="10" />
                                </a4j:region>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                        <a4j:region>
                            <rich:dataTable value="#{StockBookIssueRegister.show}" var="dataItem"
                                            rowClasses="row1, row2" id="taskList" rows="4" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12">
                                        </rich:column>
                                        <rich:column breakBefore="true">
                                            <h:outputText value="SNo" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Inst Code" />
                                        </rich:column>

                                        <rich:column>
                                            <h:outputText value="Code" />
                                        </rich:column>

                                        <rich:column>
                                            <h:outputText value="No. From" />
                                        </rich:column>

                                        <rich:column>
                                            <h:outputText value="No. To" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Amt From" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Amt To" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Leaves" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Print Lot" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Status" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Entry By" />
                                        </rich:column>
                                        <rich:column><h:outputText value="Action" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>

                                <rich:column>
                                    <h:outputText value="#{dataItem.sNo}"  />
                                </rich:column>

                                <rich:column>
                                    <h:outputText value="#{dataItem.instCode}"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText  value="#{dataItem.code}"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.numFrom}"  />
                                </rich:column>

                                <rich:column >
                                    <h:outputText value="#{dataItem.numTo}"  />
                                </rich:column>
                                <rich:column >
                                    <h:outputText value="#{dataItem.amtFrom}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column >
                                    <h:outputText value="#{dataItem.amtto}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column >
                                    <h:outputText value="#{dataItem.leaves}" />
                                </rich:column>
                                <rich:column >
                                    <h:outputText value="#{dataItem.printlot}" />
                                </rich:column>
                                <rich:column >
                                    <h:outputText value="#{dataItem.status}" />
                                </rich:column>

                                <rich:column >
                                    <h:outputText value="#{dataItem.enterBy}" />
                                </rich:column>
                                <rich:column>
                                    <a4j:commandLink id="deletelink" reRender="subbodyPanel" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{row}" target="#{StockBookIssueRegister.currentRow}" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{StockBookIssueRegister.currentItem}"/>
                                    </a4j:commandLink>

                                    <rich:toolTip for="deletelink" value="Delete" />
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">

                            <a4j:commandButton id="btnSave" value="Save" disabled="#{StockBookIssueRegister.disable}"  >
                                <a4j:support event="onclick" action="#{StockBookIssueRegister.checkSaveButton}"  reRender="mainPanel,btnSave,stxtMssg,taskList,issueregister,ShowTable"/>
                            </a4j:commandButton>

                            <a4j:commandButton id="btnAllReceipts" value="All Receipts">
                                <a4j:support action="#{StockBookIssueRegister.showAllDataStoc}" event="oncomplete" reRender="stxtMssg,taskList,ShowTable,RightPanel,issueregister"/>
                            </a4j:commandButton>

                            <a4j:commandButton id="btnDeleteSeries" value="Delete Series">
                                <a4j:support event="onclick" oncomplete="#{rich:component('deleteSeriesPanel')}.show()"  reRender="stxtMssg"/>
                            </a4j:commandButton>

                            <a4j:commandButton id="btnRefresh" value="Refresh"   >
                                <a4j:support action="#{StockBookIssueRegister.refreshButton}" event="oncomplete"  reRender="mainPanel,message,txtFromDDNo,txtToDDNo,ddInstrumentType,txtSlabCode,txtDelSeries" focus="ddGotTo"/>
                            </a4j:commandButton>

                            <a4j:commandButton id="btnExit" value="Exit" action="#{StockBookIssueRegister.exitBtnAction}" reRender="mainPanel">
                            </a4j:commandButton>


                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>

                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>

            </a4j:form>

            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('deleteYes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete this Row?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="deleteYes" value="Yes" action="#{StockBookIssueRegister.delete}"
                                                           onclick="#{rich:component('deletePanel')}.hide();" reRender="taskList" />
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="deleteNo" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />

                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>



            <rich:modalPanel id="deletePanel1" autosized="true" width="250" onshow="#{rich:element('delYes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Delete this transaction?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="delYes" value="Yes" action="#{StockBookIssueRegister.deleteIssue}"
                                                           onclick="#{rich:component('deletePanel1')}.hide();" reRender="issueregister" />
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="delNo" value="No" onclick="#{rich:component('deletePanel1')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>



            <rich:modalPanel id="SelectPanel" autosized="true" width="250" onshow="#{rich:element('selectYes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do you want to mark the leaf as damaged!!!"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="selectYes" value="Yes"  action="#{StockBookIssueRegister.bookIssueGridDoubleClick}"
                                                           oncomplete="#{rich:component('SelectPanel')}.hide();" reRender="issueregister,stxtMssg" />
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton  id="selectNo" value="No" onclick="#{rich:component('SelectPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="SelectPanel1" autosized="true" width="250" onshow="#{rich:element('damageYes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do you want to mark the leaf as damaged !!!"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="damageYes" value="Yes"  action="#{StockBookIssueRegister.bookIssueGridDblClick}"
                                                           oncomplete="#{rich:component('SelectPanel1')}.hide();" reRender="ShowTable,stxtMssg" />
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="damageNo"  value="No" onclick="#{rich:component('SelectPanel1')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deleteSeriesPanel" width="450">
                <f:facet name="header">
                    <h:outputText value="Series Deletion Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <h:panelGrid bgcolor="#fff" columns="1" id="mainDelPanel" style="border:1px ridge #BED6F8" width="100%">
                                <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                                    <h:outputText id="message" styleClass="error" value="#{StockBookIssueRegister.delMessage}"/>
                                </h:panelGrid>
                                <h:panelGrid id="fromDDNo" columns="4" columnClasses="col2,col7" styleClass="row1" width="100%">
                                    <h:outputLabel id="lblFromDDNo" styleClass="label" value="From DDNo"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtFromDDNo" value="#{StockBookIssueRegister.fromDDNo}" maxlength="10" size="10" styleClass="input"/>
                                <h:outputLabel id="lblToDDNo" styleClass="label" value="To DDNo"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtToDDNo" value="#{StockBookIssueRegister.toDDNo}" maxlength="10" size="10" styleClass="input"/>    
                            </h:panelGrid>
                            <h:panelGrid id="InstrumentGrid" columns="4" columnClasses="col2,col7" styleClass="row2" width="100%">
                                <h:outputLabel id="lblInstrumentType" styleClass="label" value="Instrument Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddInstrumentType" value="#{StockBookIssueRegister.instrumentType}" size="1" style="width:70px" styleClass="ddlist" >
                                    <f:selectItems value="#{StockBookIssueRegister.comboinstType}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblSlabCode" styleClass="label" value="Slab Code"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSlabCode" value="#{StockBookIssueRegister.slabCode}" size="10" styleClass="input" maxlength="5"/>
                            </h:panelGrid>
                            <h:panelGrid id="delSeries" columns="4" columnClasses="col2,col7" styleClass="row1" width="100%">
                                <h:outputLabel id="lblDelSeries" styleClass="label" value="Series"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtDelSeries" value="#{StockBookIssueRegister.delSeries}" maxlength="2" size="10" styleClass="input" onkeyup="this.value=this.value.toUpperCase();"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="BtnPanel">
                                    <a4j:commandButton id="btnDelete" value="Delete">
                                        <a4j:support event="onclick" action="#{StockBookIssueRegister.deleteSeries}"  reRender="mainDelPanel"/>
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnReset" value="Refresh"   >
                                        <a4j:support event="onclick" action="#{StockBookIssueRegister.refreshDelForm}" reRender="message,txtFromDDNo,txtToDDNo,ddInstrumentType,txtSlabCode,txtDelSeries" focus="txtFromDDNo"/>
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnClose" value="Close" onclick="#{rich:component('deleteSeriesPanel')}.hide();return false;" >
                                    </a4j:commandButton>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </tbody>
                </table>
            </h:form>
        </rich:modalPanel>
    </body>
</html>
</f:view>