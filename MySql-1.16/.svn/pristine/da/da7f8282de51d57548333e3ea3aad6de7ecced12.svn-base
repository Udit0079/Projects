<%-- 
    Document   : manualinward
    Created on : Jul 19, 2013, 12:10:03 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Manual Inward</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".issueDt").mask("99/99/9999");
                }
                function validate(e) {e = e || window.event; var bad = /[^\sa-z\d]/i, key = String.fromCharCode( e.keyCode || e.which ); if ( e.which !== 0 && e.charCode !== 0 && bad.test(key) ){e.returnValue = false;if ( e.preventDefault ) {e.preventDefault();}} } 
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{manualInward.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Manual Inward Processing"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{manualInward.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{manualInward.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="acnoPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAccNo" styleClass="label" value="A/C No."><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtaccNo" styleClass="input" maxlength="#{manualInward.acNoMaxLen}" value="#{manualInward.acctCode}" style="width:100px;" onkeypress="validate(event)">
                                <a4j:support event="onblur" action="#{manualInward.validateAccount}" reRender="stxtMessage,stxtAccNo,stxtName,stxtOpMode,stxtJtName,stxtClrBal,stxtUnClrBal,lblSeqNo,stxtSeqNo,txtInFavOf" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{manualInward.accNo}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                        <h:outputText id="stxtName" styleClass="output" value="#{manualInward.name}"/>
                        <h:outputLabel id="lblOpMode" styleClass="label" value="Op. Mode"/>
                        <h:outputText id="stxtOpMode" styleClass="output" value="#{manualInward.opMode}"/>
                    </h:panelGrid>
                    <h:panelGrid id="jtNamePanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblJtName" styleClass="label" value="Jt. Name"/>
                        <h:outputText id="stxtJtName" styleClass="output" value="#{manualInward.jtName}"/>
                        <h:outputLabel id="lblClrBal" styleClass="label" value="Cl. Bal."/>
                        <h:outputText id="stxtClrBal" styleClass="output" value="#{manualInward.clBal}"/>
                        <h:outputLabel id="lblUnClrBal" styleClass="label" value="UnCl. Bal."/>
                        <h:outputText id="stxtUnClrBal" styleClass="output" value="#{manualInward.unclBal}"/>
                    </h:panelGrid>
                    <h:panelGrid id="instNoPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst. No."><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtInstNo" styleClass="input" style="width:80px;" value="#{manualInward.instNo}" maxlength="10" onkeypress="validate(event)">
                            <a4j:support event="onblur" action="#{manualInward.validateInstNo}" reRender="stxtMessage" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblInstDate" styleClass="label" value="Inst. Dt."><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtInstDate" size="10" styleClass="input issueDt" value="#{manualInward.instDate}">
                            <a4j:support event="onblur" action="#{manualInward.validateInstDt}" reRender="stxtMessage" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtInstAmt" styleClass="input" style="width:80px;" value="#{manualInward.instAmount}">
                            <a4j:support event="onblur" action="#{manualInward.getCtsSeqNo}" reRender="stxtMessage,txtInFavOf,lblSeqNo,stxtSeqNo,stxtName,txtPrbankCode" oncomplete="if(#{manualInward.acctNature == 'PO'}){#{rich:element('txtPrbankCode')}.focus();}else{#{rich:element('txtInFavOf')}.focus();};setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="favourofPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblInFavOf" styleClass="label" value="In Favour Of"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtInFavOf" styleClass="input"  value="#{manualInward.inFavourOf}" disabled="#{manualInward.disableFavourof}" style="width:170px;" onkeyup="this.value=this.value.toUpperCase();" maxlength="200"/>
                        <h:outputLabel id="lblPrbankCode" styleClass="label" value="Pr. Bank Code"/>
                        <h:inputText id="txtPrbankCode" styleClass="input" style="width:70px;" value="#{manualInward.prBankCode}" onkeypress="validate(event)" maxlength="9">
                            <a4j:support event="onblur" action="#{manualInward.validatePrBankCode}" reRender="stxtMessage,stxtBankName,stxtBranchName" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblBankName" styleClass="label" value="Bank Name"/>
                        <h:outputText id="stxtBankName" styleClass="output" value="#{manualInward.bankName}"/>
                    </h:panelGrid>
                    <h:panelGrid id="branchPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBranchName" styleClass="label" value="Branch Name"/>
                        <h:outputText id="stxtBranchName" styleClass="output" value="#{manualInward.branchName}"/>
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq. No." style="display:#{manualInward.displaySeqNo};"/>
                        <h:outputText id="stxtSeqNo" styleClass="output" value="#{manualInward.seqNo}" style="display:#{manualInward.displaySeqNo};"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Save" oncomplete="#{rich:component('savePanel')}.show();" reRender="stxtMessage,savePanel"/>
                            <a4j:commandButton id="btnRefresh" action="#{manualInward.btnRefreshAction}" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" action="#{manualInward.btnExitAction}" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable id="taskList" value="#{manualInward.tableDataList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Unauthorized Instrument Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Inst. No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. Date." /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="In Favour Of" /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Branch Name" /></rich:column>
                                        <rich:column><h:outputText value="Pr. Bank Code" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accountNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.inFavourof}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.branchName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.prBankCode}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to save this transaction ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnSaveYes" action="#{manualInward.saveTransaction}" oncomplete="#{rich:component('savePanel')}.hide();" reRender="mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
