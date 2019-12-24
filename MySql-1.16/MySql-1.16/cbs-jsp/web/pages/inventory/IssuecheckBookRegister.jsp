<%-- 
    Document   : IssuecheckBookRegister
    Created on : 27 Oct, 2010, 1:10:42 PM
    Author     : Zeeshan Waris
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{IssuecheckBookRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Issue ChequeBook Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IssuecheckBookRegister.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{IssuecheckBookRegister.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2"  columnClasses="col5,col8" id="subbodyPanel1" style="width:100%;">
                            <h:panelGrid id="leftPanel" columns="1" style="width:100%;" >
                                <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="Panel1" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblInvtClass" styleClass="label" value="Inventory Class :" ><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddInvtClass" tabindex="4" styleClass="ddlist" value="#{IssuecheckBookRegister.invtClass}" size="1" style="width:100px">
                                            <f:selectItems value="#{IssuecheckBookRegister.invtClassList}" />
                                            <a4j:support action="#{IssuecheckBookRegister.setInventoryType}" event="onblur" reRender="mainPanel,Panel790,stxtMsg,Panel1,Panel2" focus="ddInvtType"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblInvtType" styleClass="label" value="Inventory Type :" ><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddInvtType" tabindex="5" styleClass="ddlist" value="#{IssuecheckBookRegister.invtType}" size="1" style="width:100px">
                                            <f:selectItems value="#{IssuecheckBookRegister.invtTypeList}" />
                                            <a4j:support action="#{IssuecheckBookRegister.setChequeSeriesList}" event="onblur" reRender="mainPanel,Panel3,stxtMsg" focus="txtchequeSeries"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="Panel3" style="width:100%;text-align:left;" styleClass="row2" >
                                    <h:outputLabel id="lblchequeSeries" styleClass="label"  value="Cheque Series :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="txtchequeSeries" tabindex="4" size="1" styleClass="ddlist" style="width:100px " value="#{IssuecheckBookRegister.cheqSeries}" disabled="#{IssuecheckBookRegister.seriesDisable}">
                                            <f:selectItems value="#{IssuecheckBookRegister.chequeSeriesList}" />
                                            <a4j:support action="#{IssuecheckBookRegister.getTableStock}"  event="onblur" 
                                                         reRender="mainPanel,stxtMsg,Panel2,Panel3,Panel4,Stocktable" focus="txtaccountDetails"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblaccountDetails" styleClass="label"  value="Account No :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:panelGroup id="groupPanelNo" layout="block">
                                            <h:inputText id="txtaccountDetails" style="width:100px"  value="#{IssuecheckBookRegister.oldAcctNo}" onkeyup="this.value = this.value.toUpperCase();" maxlength="#{IssuecheckBookRegister.acNoMaxLen}" styleClass="input">
                                                <a4j:support action="#{IssuecheckBookRegister.customerDetails}"  event="onblur" reRender="mainPanel,Panel11,Panel12,Panel13,Panel14,gridPanelIssue,Panel6,stxtMsg,Panel3,Panel4,Panel5,Panel6,PanelEnd" focus="ddChqBookcharges"/>
                                            </h:inputText>
                                            <h:outputLabel id="stxtaccountDetails" styleClass="label"  value="#{IssuecheckBookRegister.acctNo}"></h:outputLabel>
                                        </h:panelGroup>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="Panel4" style="width:100%;" styleClass="row1">
                                        <h:outputLabel id="lblPartyName" styleClass="label"  value="Party Name :"/>
                                        <h:outputText id="stxtPartyName" styleClass="output" value="#{IssuecheckBookRegister.stPartyName}" style="color:purple;"/>    
                                        <h:outputLabel id="lblOperationMode" styleClass="label"  value="Operation Mode :"/>
                                        <h:outputText id="stxtOperationMode" styleClass="output" value="#{IssuecheckBookRegister.stOperationMode}" style="color:purple;"/>

                                </h:panelGrid>
                                <%--h:panelGrid columnClasses="col8,col8" columns="2" id="Panel11" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblIAccountNum" styleClass="label"  value="Account No. :"/>
                                    <h:outputText id="stxtAccountNum" styleClass="output" value="#{IssuecheckBookRegister.stAccountNo}" style="color:purple;"/>
                                </h:panelGrid--%>
                                <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="Panel13" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblJointName" styleClass="label"  value="Joint Name :"/>
                                    <h:outputText id="stxtJointName" styleClass="output" value="#{IssuecheckBookRegister.stJointName}" style="color:purple;"/>
                                    <h:outputLabel id="lblChqBookcharges" styleClass="label"  value="Charges :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddChqBookcharges" styleClass="ddlist"  size="1" style="width:100px" value="#{IssuecheckBookRegister.chqbookcharges}" >
                                            <f:selectItems value="#{IssuecheckBookRegister.ddchqbookCharges}"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="Panel6" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblChqNoFrom" styleClass="label"  value="Chq. No. From :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtChqNoFrom" style="width:100px"  value="#{IssuecheckBookRegister.chqFrom}" styleClass="input" maxlength="8" disabled="#{IssuecheckBookRegister.flagDisable}"/>
                                        <h:outputLabel id="lblChqNoTo" styleClass="label"  value="Chq.No. To :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtChqNoTo" style="width:100px"  value="#{IssuecheckBookRegister.chqTo}" styleClass="input" maxlength="8" disabled="#{IssuecheckBookRegister.flagDisable}">
                                            <a4j:support action="#{IssuecheckBookRegister.setLeaves}"  event="onblur" reRender="mainPanel,txtNoOfLeaves,txtChqNoTo,txtChqNoFrom,stxtMsg" focus="txtNoOfLeaves"/>
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="PanelEnd1" style="width:100%;" styleClass="row1">
                                        <h:outputLabel id="lblRemarks" styleClass="label"  value="Remarks :"></h:outputLabel>
                                        <h:inputText id="txtRemarks" style="width:150px" value="#{IssuecheckBookRegister.remarks}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                        <h:outputLabel id="lblNoOfLeaves" styleClass="label"  value="No. Of Leaves :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtNoOfLeaves" style="width:100px"  value="#{IssuecheckBookRegister.noOfLeaves}" styleClass="input" maxlength="8" disabled="#{IssuecheckBookRegister.flagDisable}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="PanelEnd2" style="width:100%;" styleClass="row2">
                                        <h:outputLabel id="lblAtPar" styleClass="label"  value="At Par:"></h:outputLabel>
                                        <h:selectOneListbox id="ddAtPar" styleClass="ddlist"  size="1" style="width:100px" value="#{IssuecheckBookRegister.atPar}" >
                                            <f:selectItems value="#{IssuecheckBookRegister.atParList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblbookType" styleClass="label"  value="Chq Book Type:"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddbookType" styleClass="ddlist"  size="1" style="width:100px" value="#{IssuecheckBookRegister.chBookType}" >
                                            <f:selectItems value="#{IssuecheckBookRegister.chBookTypeList}"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid id="middlePanel" columns="1" style="width:100%;">
                                    <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:230px;">
                                        <a4j:region>
                                            <rich:dataTable value="#{IssuecheckBookRegister.stocktable}" var="item" 
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
                                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{IssuecheckBookRegister.select}" reRender="mainPanel,Panel3,Panel4,Panel5,txtRemarks" focus="ddChqBookcharges">
                                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{item}" target="#{IssuecheckBookRegister.currentItem}"/>
                                                        <f:setPropertyActionListener value="#{row}" target="#{IssuecheckBookRegister.currentRow}" />
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
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelIssue" width="100%" styleClass="row2" style="height:300px;">
                            <a4j:region>
                                <rich:dataTable value="#{IssuecheckBookRegister.issueTable}" var="item1"
                                                rowClasses="row1, row2" id="IssueList" rows="8" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11"><h:outputText value="Detail of Issued Inventory" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                            <rich:column><h:outputText value="Issue Date" /></rich:column>
                                            <rich:column><h:outputText value="Chq.No.From" /></rich:column>
                                            <rich:column><h:outputText value="Chq.No.To" /></rich:column>
                                            <rich:column><h:outputText value="Issued By" /></rich:column>
                                            <rich:column><h:outputText value="Remarks" /></rich:column>
                                            <rich:column><h:outputText value="Leaves" /></rich:column>
                                            <rich:column><h:outputText value="At Par" /></rich:column>
                                            <rich:column><h:outputText value="Cheque Book Type" /></rich:column>
                                            <rich:column><h:outputText value="Authorized" /></rich:column>
                                            <rich:column><h:outputText value="Delete" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item1.accountNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{item1.issueDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{item1.chequeNoFrom}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item1.cheqNoTo}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item1.issuedBy}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item1.remarks}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item1.leaves}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item1.atPar}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item1.chBookType}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item1.authorized}" /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink"  reRender="mainPanel,PanelEnd1,PanelEnd2,IssueList" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{row}" target="#{IssuecheckBookRegister.currentRow1}" />
                                            <f:setPropertyActionListener value="#{item1}" target="#{IssuecheckBookRegister.currentItem1}"/>
                                        </a4j:commandLink>
                                        <rich:toolTip for="deletelink" value="Delete" />
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scrollerIssue" align="left" for="IssueList" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="BtnPanel">
                                <a4j:commandButton id="btnSave" value="Save" onclick="#{rich:component('savePanel')}.show()"  reRender="stxtMsg,subbodyPanel,IssueList,taskList" />
                                <a4j:commandButton  id="btnUpdate" disabled="true" value="Updation" onclick="#{rich:component('updatePanel')}.show()" reRender="stxtMsg,subbodyPanel,IssueList,taskList" />
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{IssuecheckBookRegister.btnRefresh}" reRender="mainPanel,Stocktable" focus="ddInvtClass"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{IssuecheckBookRegister.btnExit}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputText id="stxtInstruction1" styleClass="output" value="F7-Sign View!" style="color:blue;"/>
                        </h:panelGrid>                    
                    </h:panelGrid>
                    <a4j:region id="keyRegion">
                        <a4j:jsFunction name="showSignDetail" oncomplete="if(#{rich:element('txtaccountDetails')}.value!='') #{rich:component('sigView')}.show();" reRender="sigView"/>
                    </a4j:region>
                </a4j:form>
                <rich:modalPanel id="sigView" height="530" width="700" style="align:right">
                    <f:facet name="header">
                        <h:outputText value="Signature Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                            <rich:componentControl for="sigView" attachTo="closelink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                            <h:panelGrid id="sigViewRow1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                                <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                                <h:outputText id="txtAccNo" style="output" value="#{IssuecheckBookRegister.accNo}"/>
                                <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                                <h:outputText id="txtCustomerId" style="output" value="#{IssuecheckBookRegister.custId}"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="sigViewRow2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                                <h:outputLabel id="lblOpMode" styleClass="label" value="Operation Mode"/>
                                <h:outputText id="txtOpMode" style="output" value="#{IssuecheckBookRegister.opMode}"/>
                                <h:outputLabel id="lblOpDt" styleClass="label" value="Opening Date"/>
                                <h:outputText id="txtOpDt" style="output" value="#{IssuecheckBookRegister.openDate}"/>
                                <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No"/>
                                <h:outputText id="txtPanNo" style="output" value="#{IssuecheckBookRegister.custPanNo}"/>
                            </h:panelGrid>
                            <h:panelGrid id="sigViewRow3" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                                <h:outputLabel id="lblJtName" styleClass="label" value="Jt.Name"/>
                                <h:outputText id="txtJtName" style="output" value="#{IssuecheckBookRegister.jtName}"/>
                                <h:outputLabel id="lblSigAnnualTrunOver" styleClass="label" value="Annual Turnover"/>
                                <h:outputText id="txtSigAnnualTrunOver" style="output" value="#{IssuecheckBookRegister.annualTurnover}"/>
                            </h:panelGrid>
                            <h:panelGrid id="sigViewRow4" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                                <h:outputLabel id="lblAcInst" styleClass="label" value="A/c Instruction"/>
                                <h:outputText id="txtAcIns" style="output" value="#{IssuecheckBookRegister.accInstruction}"/>
                                <h:outputLabel id="lblProfession" styleClass="label" value="Profession"/>
                                <h:outputText id="txtProfession" style="output" value="#{IssuecheckBookRegister.profession}"/>
                            </h:panelGrid>
                            <h:panelGrid id="custIdPanelGrid" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                                <h:outputLabel id="lblSigRiskCat" styleClass="label" value="Risk Category"/>
                                <h:outputText id="txtSigRiskCat" style="output" value="#{IssuecheckBookRegister.riskCategorization}"/>
                                <h:outputLabel id="lblSigDpLimit" styleClass="label" value="DP Limit"/>
                                <h:outputText id="txtSigDpLimit" style="output" value="#{IssuecheckBookRegister.dpLimit}"/>
                            </h:panelGrid>
                            <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                                <a4j:mediaOutput id="signature" element="img" createContent="#{IssuecheckBookRegister.createSignature}" value="#{IssuecheckBookRegister.accNo}"/>
                            </h:panelGroup>
                            <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="sigViewBtnPanel">
                                    <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;">

                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('delYes')}.focus()">
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
                                        <a4j:commandButton id="delYes" value="Yes" ajaxSingle="true" action="#{IssuecheckBookRegister.deleteIssue}"
                                                           onclick="#{rich:component('deletePanel')}.hide();" reRender="mainPanel,IssueList,stxtMsg" />
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="delNo" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="updatePanel" autosized="true" width="250" onshow="#{rich:element('upYes')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To update?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="upYes" value="Yes" ajaxSingle="true" action="#{IssuecheckBookRegister.updateBtnAction}"
                                                           onclick="#{rich:component('updatePanel')}.hide();" reRender="mainPanel,stxtMsg,subbodyPanel,IssueList,taskList" />
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="upNo" value="No" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('saveYes')}.focus()">
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

                                        <a4j:commandButton id="saveYes" value="Yes" ajaxSingle="true" action="#{IssuecheckBookRegister.saveBtnAction}"
                                                           onclick="#{rich:component('savePanel')}.hide();" reRender="mainPanel,stxtMsg,subbodyPanel,IssueList,taskList" />

                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="saveNo" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <rich:hotKey key="F7" handler="showSignDetail();"/>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('waitdel')}.show()" onstop="#{rich:component('waitdel')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="waitdel" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
