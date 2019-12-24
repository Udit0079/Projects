<%-- 
    Document   : issueCheckBookWithSlab
    Created on : 16 Feb, 2018, 6:42:03 PM
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
            <title>Issue ChequeBook Register</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="Form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IssueChBookWithSlab.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Issue ChequeBook With Slab"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IssueChBookWithSlab.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{IssueChBookWithSlab.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2"  columnClasses="col5,col8" id="subbodyPanel1" style="width:100%;">
                            <h:panelGrid id="leftPanel" columns="1" style="width:100%;" >
                                <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="Panel12" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblInvtClass" styleClass="label" value="Inventory Class :" ><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddInvtClass" tabindex="4" styleClass="ddlist" value="#{IssueChBookWithSlab.invtClass}" size="1" style="width:100px">
                                            <f:selectItems value="#{IssueChBookWithSlab.invtClassList}" />
                                            <a4j:support action="#{IssueChBookWithSlab.setInventoryType}" event="onblur" reRender="mainPanel,Panel790,stxtMsg,Panel1,Panel2" focus="ddInvtType"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblInvtType" styleClass="label" value="Inventory Type :" ><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddInvtType" tabindex="5" styleClass="ddlist" value="#{IssueChBookWithSlab.invtType}" size="1" style="width:100px">
                                            <f:selectItems value="#{IssueChBookWithSlab.invtTypeList}" />
                                            <a4j:support action="#{IssueChBookWithSlab.setChequeSeriesList}" event="onblur" reRender="mainPanel,Panel3,stxtMsg" focus="txtchequeSeries"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                <h:panelGrid columnClasses="col1,col22" columns="3" id="Panel3" style="width:100%;text-align:left;" styleClass="row2" >
                                    <h:outputLabel id="lblchequeSeries" styleClass="label"  value="Cheque Series :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="txtchequeSeries" tabindex="4" size="1" styleClass="ddlist" style="width:100px " value="#{IssueChBookWithSlab.cheqSeries}" disabled="#{IssueChBookWithSlab.seriesDisable}">
                                            <f:selectItems value="#{IssueChBookWithSlab.chequeSeriesList}" />
                                            <a4j:support action="#{IssueChBookWithSlab.getTableStock}"  event="onblur" 
                                                         reRender="mainPanel,stxtMsg,Panel2,Panel3,Panel4,Stocktable" focus="txtaccountDetails"/>
                                        </h:selectOneListbox>
                                    <h:outputLabel>
                                </h:outputLabel>
                                    </h:panelGrid>
                                    
                                <%--h:panelGrid columnClasses="col8,col8" columns="2" id="Panel11" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblIAccountNum" styleClass="label"  value="Account No. :"/>
                                    <h:outputText id="stxtAccountNum" styleClass="output" value="#{IssuecheckBookRegister.stAccountNo}" style="color:purple;"/>
                                </h:panelGrid--%>
                                

                                <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="Panel6" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblChqNoFrom" styleClass="label"  value="Chq. No. From :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtChqNoFrom" style="width:100px"  value="#{IssueChBookWithSlab.chqFrom}" styleClass="input" maxlength="8" disabled="#{IssueChBookWithSlab.flagDisable}"/>
                                        <h:outputLabel id="lblChqNoTo" styleClass="label"  value="Chq.No. To :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtChqNoTo" style="width:100px"  value="#{IssueChBookWithSlab.chqTo}" styleClass="input" maxlength="8" disabled="#{IssueChBookWithSlab.flagDisable}">
                                            <a4j:support action="#{IssueChBookWithSlab.setLeaves}"  event="onblur" reRender="mainPanel,txtNoOfLeaves,txtChqNoTo,txtChqNoFrom,stxtMsg" focus="txtNoOfLeaves"/>
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="PanelEnd1" style="width:100%;" styleClass="row1">
                                        <h:outputLabel id="lblRemarks" styleClass="label"  value="Remarks :"></h:outputLabel>
                                        <h:inputText id="txtRemarks" style="width:150px" value="#{IssueChBookWithSlab.remarks}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                        <h:outputLabel id="lblNoOfLeaves" styleClass="label"  value="No. Of Leaves :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtNoOfLeaves" style="width:100px"  value="#{IssueChBookWithSlab.noOfLeaves}" styleClass="input" maxlength="8" disabled="#{IssueChBookWithSlab.flagDisable}"/>
                                     <h:outputLabel id="lblPrintLot" styleClass="label"  value="Print Lot" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                      <h:inputText id="txtNoprintlot" style="width:100px"  value="#{IssueChBookWithSlab.printlot}" styleClass="input" maxlength="8" disabled="#{IssueChBookWithSlab.flagDisable}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel1" style="width:100%;" styleClass="row2">

                                    <h:outputLabel id="lblInstType" styleClass="label" for="ddInstType" value="Inst Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddInstType" value="#{IssueChBookWithSlab.cmbInstType}" size="1" style="width:70px" styleClass="ddlist" >
                                        <f:selectItems value="#{IssueChBookWithSlab.comboinstType}"/>
                                        <a4j:support event="onchange" action="#{IssueChBookWithSlab.getCode}" focus="ddslb"
                                                     reRender="ddslb,stxtMssg"/>
                                    </h:selectOneListbox>

                                    <h:outputLabel id="lblSlabCode" styleClass="label" for="ddslb" value="Slab Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddslb" style="width:70px" size="1" value="#{IssueChBookWithSlab.combCode}" styleClass="ddlist" disabled="#{IssueChBookWithSlab.disableSlabCode}">
                                        <f:selectItems value="#{IssueChBookWithSlab.comboCode}"/>
                                        <a4j:support  event="onblur" action="#{IssueChBookWithSlab.checkSlabAmtCode}"
                                                      reRender="ddAmountRange,ddAmtTo,stxtMssg"/>
                                    </h:selectOneListbox>
                               </h:panelGrid>
                                     <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel23" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblAmountRange" styleClass="label"  value="Amount Range"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddAmountRange" size="1" style="width:70px" styleClass="ddlist" value="#{IssueChBookWithSlab.amtRangeFrom}" >
                                        <f:selectItems value="#{IssueChBookWithSlab.comboAmtFrm}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lbl" styleClass="label" />
                                    <h:selectOneListbox id="ddAmtTo" style="width:70px" size="1" styleClass="ddlist" value="#{IssueChBookWithSlab.amtRangeTo}" >
                                        <f:selectItems value="#{IssueChBookWithSlab.comboAmtTo}"/>
                                        <a4j:support  event="onblur" action="#{IssueChBookWithSlab.checkGoto}"
                                                     reRender="Panel3,Panel4,ddNumberFrom,ddNoTo,ddNumberOfLeaves,stxtMssg,ddslb,gridPanelIssue,IssueList"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>

                                </h:panelGrid>
                                <h:panelGrid id="middlePanel" columns="1" style="width:100%;">
                                    <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:230px;">
                                        <a4j:region>
                                            <rich:dataTable value="#{IssueChBookWithSlab.stocktable}" var="item" 
                                                            rowClasses="row1, row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="5"><h:outputText value="Detail of inventory" /></rich:column>
                                                        <rich:column breakBefore="true" width="10%"><h:outputText value="S.No."/></rich:column>
                                                        <rich:column width="28%"><h:outputText value="Chq. No. From"/></rich:column>
                                                        <rich:column width="28%"><h:outputText value="Chq. No. To" /></rich:column>
                                                        <rich:column width="28%" style="text-align:center;"><h:outputText value="Stock Date" /></rich:column>
                                                        <rich:column width="6%"><h:outputText value="select" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column width="10%"><h:outputText value="#{item.sno}" /></rich:column>
                                                <rich:column width="28%"><h:outputText value="#{item.chNofrom}" /></rich:column>
                                                <rich:column width="28%"><h:outputText value="#{item.chNoTo}" /></rich:column>
                                                <rich:column width="28%" style="text-align:center;"><h:outputText value="#{item.stockDt}" /></rich:column>
                                                <rich:column width="6%">
                                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{IssueChBookWithSlab.select}" reRender="mainPanel,Panel3,Panel4,Panel5,txtRemarks" focus="ddChqBookcharges">
                                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{item}" target="#{IssueChBookWithSlab.currentItem}"/>
                                                        <f:setPropertyActionListener value="#{row}" target="#{IssueChBookWithSlab.currentRow}" />
                                                    </a4j:commandLink>
                                                    <rich:toolTip for="selectlink" value="Edit" />
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                                        </a4j:region>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                                <%--               <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('saveYes')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Confirmation DialogBox" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Save?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:region id="yesBtn">
                                                    <a4j:commandButton id="saveYes" value="Yes" ajaxSingle="true" action="#{IssueChBookWithSlab.saveBtnAction}"
                                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="mainPanel,stxtMsg,subbodyPanel" />
                                                </a4j:region>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="saveNo" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>   --%>
                     <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{IssueChBookWithSlab.saveBtnAction()}"  reRender="stxtMsg,subbodyPanel,Panel1,Panel23,ddslb,ddAmountRange,ddAmtTo,mainPanel" />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{IssueChBookWithSlab.btnRefresh}" reRender="mainPanel,Stocktable,Panel1,Panel23,ddslb,ddAmountRange,ddAmtTo" focus="ddInvtClass"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{IssueChBookWithSlab.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                  
               </h:panelGrid>
            </a4j:form>
         </body>
    </html>
</f:view>
