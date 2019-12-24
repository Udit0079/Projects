<%-- 
    Document   : npciecsmandate
    Created on : 30 Dec, 2015, 2:15:50 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>ECS Mandate</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issuedt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{npciEcsMandate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="ECS Debit Verification"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{npciEcsMandate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{npciEcsMandate.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTxnType" styleClass="label" value="Txn. Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddTxnType" styleClass="ddlist" size="1" style="width:80px" value="#{npciEcsMandate.txnType}">
                            <f:selectItems value="#{npciEcsMandate.txnTypeList}"/>
                            <a4j:support event="onblur" action="#{npciEcsMandate.getAllRejectReason}" reRender="stxtMsg,ddReason" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:80px" value="#{npciEcsMandate.branch}">
                            <f:selectItems value="#{npciEcsMandate.branchList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFileUpDt" styleClass="label" value="File Settlement Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFileUpDt" styleClass="input issuedt" style="width:70px" value="#{npciEcsMandate.fileUpDt}">
                            <a4j:support event="onblur" action="#{npciEcsMandate.fileUpDtProcess}" reRender="stxtMsg,ddSeqNo" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSeqNo" styleClass="ddlist" size="1" style="width:80px" value="#{npciEcsMandate.seqNo}">
                            <f:selectItems value="#{npciEcsMandate.seqNoList}"/>
                            <a4j:support event="onblur" action="#{npciEcsMandate.retrieveProcess}" reRender="stxtMsg,mainPanel,ddReason" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblOldAcno" styleClass="label" value="Old A/c No."></h:outputLabel>
                        <h:outputText id="stxtOldAcno" styleClass="output" value="#{npciEcsMandate.oldAcno}"/>
                        <h:outputLabel id="lblOldAcName" styleClass="label" value="Old A/c Name"></h:outputLabel>
                        <h:outputText id="stxtOldAcName" styleClass="output" value="#{npciEcsMandate.oldAcName}"/>
                        <h:outputLabel id="lblMicr" styleClass="label" value="Micr Code"></h:outputLabel>
                        <h:outputText id="stxtMicr" styleClass="output" value="#{npciEcsMandate.micr}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAcType" styleClass="label" value="A/c Type"></h:outputLabel>
                        <h:outputText id="stxtAcType" styleClass="output" value="#{npciEcsMandate.acType}"/>
                        <h:outputLabel id="lblRrn" styleClass="label" value="Transaction Ref. No"></h:outputLabel>
                        <h:outputText id="stxtRrn" styleClass="output" value="#{npciEcsMandate.stRrn}"/>
                        <h:outputLabel id="lblAcValidFlag" styleClass="label" value="Status"></h:outputLabel>
                        <h:outputText id="stxtAcValidFlag" styleClass="output" value="#{npciEcsMandate.statusFlag}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblEcsAmount" styleClass="label" value="ECS Amount"></h:outputLabel>
                        <h:outputText id="stxtEcsAmount" styleClass="output" value="#{npciEcsMandate.ecsAmount}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel7" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAcno" styleClass="label" value="A/c No"></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                            <h:inputText id="txtAcno" styleClass="input" value="#{npciEcsMandate.accountNo}" maxlength="#{npciEcsMandate.acNoMaxLen}" size="#{npciEcsMandate.acNoMaxLen}">
                                <a4j:support event="onblur" action="#{npciEcsMandate.retrieveValidAcno}" reRender="stxtAccNo,stxtMsg,stxtName,stxtBalance,txtMandateAccNo,txtMandateName" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{npciEcsMandate.acno}"/>
                        </h:panelGroup>                        
                        <h:outputLabel id="lblBalance" styleClass="label" value="A/c Balance"></h:outputLabel>
                        <h:outputText id="stxtBalance" styleClass="output" value="#{npciEcsMandate.balance}"/>
                        <h:outputLabel id="lblName" styleClass="label" value="Name"></h:outputLabel>
                        <h:outputText id="stxtName" styleClass="output" value="#{npciEcsMandate.name}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel8" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcValid" styleClass="label" value="Status"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAcValid" styleClass="ddlist" size="1" style="width:80px" value="#{npciEcsMandate.status}">
                            <f:selectItems value="#{npciEcsMandate.statusList}"/>
                            <a4j:support event="onblur" action="#{npciEcsMandate.statusSelectProcess}" reRender="stxtMsg,ddReason" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblReason" styleClass="label" value="Reason"/>
                        <h:selectOneListbox id="ddReason" styleClass="ddlist" size="1" style="width:80px" 
                                            value="#{npciEcsMandate.reason}" disabled="#{npciEcsMandate.reasonDisable}">
                            <f:selectItems value="#{npciEcsMandate.reasonList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{npciEcsMandate.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="ECS Debit Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Transaction Ref.No" /></rich:column>
                                        <rich:column><h:outputText value="Old A/c No" /></rich:column>
                                        <rich:column><h:outputText value="Old A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="ECS Amount" /></rich:column>
                                         <rich:column><h:outputText value="User Name" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Return Reason" /></rich:column>
                                        <rich:column><h:outputText value="New A/c No" /></rich:column>
                                        <rich:column><h:outputText value="New A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.tranRef}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.oldAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.oldAcName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.userName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acValFlag}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.returnCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cbsAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cbsName}" /></rich:column>
                               <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{npciEcsMandate.setTableDataToForm}" reRender="mainPanel" focus="txtAcno">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{npciEcsMandate.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" columnClasses="col19,col20,col21" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <%--<h:panelGroup id="btnPanel1" styleClass="vtop">
                            <h:outputText id="stxtInstruction1" styleClass="output" value="F10-Account Mandate View" style="color:blue;"/>
                        </h:panelGroup>--%>
                        <h:panelGroup id="footerPanel1" layout="block" style="width:100%;text-align:center;">
                            <h:outputText id="footerText" styleClass="output" value="Ctrl+J - Joint Detail" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="Verify" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{npciEcsMandate.refreshForm}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{npciEcsMandate.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                        <h:panelGroup/>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:jsFunction name="showMandateDeatil" oncomplete="if(#{npciEcsMandate.mandateViewFlag=='true'}){#{rich:component('mandateViewPanel')}.show();}" reRender="mandateViewPanel,stxtMsg,txtMandateAccNo,txtMandateName" action="#{npciEcsMandate.mandateViewAuthProcess}"/>
                <rich:hotKey id="F4Key" key="F10" handler="showMandateDeatil();"/>

                <a4j:jsFunction name="jtDetails" action="#{npciEcsMandate.validateJointAccount}" 
                                oncomplete="if(#{npciEcsMandate.jtDetailPopUp==true})
                                {#{rich:component('jtHolder')}.show();}else{#{rich:component('jtHolder')}.hide();}" 
                                reRender="jtHolder,stxtMsg" />
                <a4j:jsFunction name="populateData" action="#{npciEcsMandate.jtDetails}" reRender="jointDetailPopUpForm"/>
                <rich:hotKey key="Ctrl+J" handler="jtDetails();"/>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to verify it ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{npciEcsMandate.processAction('')}" 
                                                           onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel,tablePanel,taskList,obBalPanel,confirmid" 
                                                           oncomplete="if(#{npciEcsMandate.odBalFlag==true}){#{rich:component('obBalPanel')}.show()};setMask()"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="obBalPanel" autosized="true" width="250" onshow="#{rich:element('btnOdNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{npciEcsMandate.confirmationMsg}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnOdYes" action="#{npciEcsMandate.odBalExceedProcess}" 
                                                           onclick="#{rich:component('obBalPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel,tablePanel,taskList"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnOdNo" onclick="#{rich:component('obBalPanel')}.hide();"
                                                           reRender="stxtMsg"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="mandateViewPanel" height="300" width="1100" left="true">
                    <f:facet name="header">
                        <h:outputText value="Account Mandate Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="mandateViewPanel" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="mandatePanelGrid1" width="100%">
                            <h:panelGrid id="mandateViewRow1" columns="4" columnClasses="col1,col2,col1,col8" width="100%" styleClass="row1" style="text-align:left;">                    
                                <h:outputLabel id="lblMandateAccNo" styleClass="label" value="Account No."/>
                                <h:outputText id="txtMandateAccNo" style="output" value="#{npciEcsMandate.mandatePanelAc}"/>
                                <h:outputLabel id="lblMandateName" styleClass="label" value="Name"/>
                                <h:outputText id="txtMandateName" style="output" value="#{npciEcsMandate.mandatePanelName}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                            <rich:dataTable value="#{npciEcsMandate.mandateGridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="3" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="18"><h:outputText value="Mandate Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="UMRN" /></rich:column>
                                        <rich:column><h:outputText value="A/c Number" /></rich:column>
                                        <rich:column><h:outputText value="Product" /></rich:column>
                                        <rich:column><h:outputText value="Instruction" /></rich:column>
                                        <rich:column><h:outputText value="Effective Date" /></rich:column>
                                        <rich:column><h:outputText value="Periodicity" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Mandate Validity" /></rich:column>
                                        <rich:column><h:outputText value="No of Installment" /></rich:column>
                                        <rich:column><h:outputText value="From Date" /></rich:column>
                                        <rich:column><h:outputText value="To Date" /></rich:column>
                                        <rich:column><h:outputText value="Received IFSC" /></rich:column>
                                        <rich:column><h:outputText value="Received MICR" /></rich:column>
                                        <rich:column><h:outputText value="User No" /></rich:column>
                                        <rich:column><h:outputText value="User Name" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.umrn}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debitAccount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.schemeName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instruction}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dateOfEffect}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.periodicity}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amtFlag}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mandateAmtPeriodicityFlag}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.installmentNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fromDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.toDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mandateReceivedIfsc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mandateReceivedMicr}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mandateUserNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mandateUserName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="30"/>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose" value="Close" onclick="#{rich:component('mandateViewPanel')}.hide(); return false;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                <rich:modalPanel id="jtHolder" label="Form" width="900" height="400" style="overflow:auto;" resizeable="false" onbeforeshow="populateData();">
                    <f:facet name="header">
                        <h:outputText value="Joint Detail" style="text-align:center;"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:outputLink value="#" onclick="#{rich:component('jtHolder')}.hide(); return false;">X</h:outputLink>
                    </f:facet>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr>
                                <td>
                                    <a4j:include viewId="#{npciEcsMandate.viewID}"></a4j:include>
                                    </td>

                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                    <a4j:commandButton value="Close" id="btnClose" onclick="#{rich:component('jtHolder')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
