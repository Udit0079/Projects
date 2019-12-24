<%-- 
    Document   : singleAcnoDeafMark
    Created on : Oct 21, 2015, 2:03:13 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Single Account Deaf Mark</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{SingleAcnoDeafMark.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Single Account Deaf Mark"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{SingleAcnoDeafMark.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{SingleAcnoDeafMark.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="functionId" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCtrlId" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlId" style="width:80px" styleClass="ddlist" value="#{SingleAcnoDeafMark.function}" size="1">
                            <f:selectItems value="#{SingleAcnoDeafMark.functionList}" />
                            <a4j:support action="#{SingleAcnoDeafMark.onblurFunction}" event="onblur" reRender="settlefrDt,settlefrDtLbl,settletoDtLbl,settletoDt,btnAddNew,ddCtrlNo,stxtVoucher,roiTxt,txtYear,ddMonth,txtDeafAmt,settleDtLbl,settleDt,table,taskList,message" oncomplete="setMask();"/>
                        </h:selectOneListbox> 
                        <h:outputLabel id="settlefrDtLbl" styleClass="label" value="From Date" style="display:#{SingleAcnoDeafMark.frToDtstyle}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="settlefrDt" styleClass="input chkDt" value="#{SingleAcnoDeafMark.frDt}" style="width:75px;display:#{SingleAcnoDeafMark.frToDtstyle}">
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="settletoDtLbl" styleClass="label" value="To Date" style="display:#{SingleAcnoDeafMark.frToDtstyle}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="settletoDt" styleClass="input chkDt" value="#{SingleAcnoDeafMark.toDt}" style="width:75px;display:#{SingleAcnoDeafMark.frToDtstyle}">
                            <a4j:support action="#{SingleAcnoDeafMark.onLoadGrid}"event="onblur" reRender="table,taskList,message" oncomplete="setMask();"/>
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid id="sectypegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblschemeGl" styleClass="label" value="Account No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup id="p1">
                            <h:inputText id="stxtschemeGl" size="10" styleClass="input" maxlength="12"value="#{SingleAcnoDeafMark.acNo}" disabled="#{SingleAcnoDeafMark.acnoDisable}">
                                <a4j:support action="#{SingleAcnoDeafMark.onblurAcnoOption}" event="onblur" reRender="newAcno,txtDeafAmt,lblVoucher,settleDt,stxtVoucher,roiTxt,message" oncomplete="setMask();"/> 
                            </h:inputText>
                            <h:outputText id="newAcno" value="#{SingleAcnoDeafMark.newAcno}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Interest Option"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlNo" style="width:120px" styleClass="ddlist" value="#{SingleAcnoDeafMark.intOption}" size="1" disabled="#{SingleAcnoDeafMark.intOptdis}">
                            <f:selectItems value="#{SingleAcnoDeafMark.intOptionList}" />
                            <a4j:support action="#{SingleAcnoDeafMark.onblurIntOption}" event="onblur" reRender="stxtVoucher,roiTxt,txtYear,ddMonth,txtDeafAmt,settleDtLbl,settleDt" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDeafAmt" styleClass="label" value="Deaf Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtDeafAmt" size="10" styleClass="input" value="#{SingleAcnoDeafMark.deafAmt}" disabled="#{SingleAcnoDeafMark.deafdis}"/>
                    </h:panelGrid>
                    <h:panelGrid id="glgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblVoucher" styleClass="label" value="Voucher No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="stxtVoucher" size="10" styleClass="input" maxlength="6"value="#{SingleAcnoDeafMark.voucherNo}"disabled="#{SingleAcnoDeafMark.vouchDis}" />
                        <h:outputLabel id="settleDtLbl" styleClass="label" value="#{SingleAcnoDeafMark.lableButton}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="settleDt" styleClass="input chkDt" value="#{SingleAcnoDeafMark.effectDt}" style="width:75px;display:#{SingleAcnoDeafMark.effectStyle}" >
                                <a4j:support oncomplete="setMask();"/>
                            </h:inputText>
                            <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px;display:#{SingleAcnoDeafMark.monthStyle}" value="#{SingleAcnoDeafMark.month}" >
                                <f:selectItems value="#{SingleAcnoDeafMark.monthList}"/>
                            </h:selectOneListbox>
                            <h:inputText id="txtYear" styleClass="input" value="#{SingleAcnoDeafMark.year}" maxlength="4" size="5"style="display:#{SingleAcnoDeafMark.monthStyle}"/>
                        </h:panelGroup>
                        <h:outputLabel id="roiLabel" value="Saving Roi" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="roiTxt" styleClass="input" value="#{SingleAcnoDeafMark.savingRoi}" maxlength="2" size="10" disabled="#{SingleAcnoDeafMark.savingDis}" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{SingleAcnoDeafMark.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Txn Id" /></rich:column>
                                        <rich:column><h:outputText value="Accont No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Int.Till Date" /></rich:column>
                                        <rich:column><h:outputText value="Deaf Amount" /></rich:column>
                                        <rich:column><h:outputText value="Deaf Date" /></rich:column>
                                        <rich:column><h:outputText value="Reciept No." /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.txnId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.effectDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.deafAmt}" style="text-align:right;"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.deafDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.receiptNo}" style="text-align:right;" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{SingleAcnoDeafMark.onLoadField}"  reRender="maingrid,btnAddNew,taskList,stxtschemeGl">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SingleAcnoDeafMark.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{SingleAcnoDeafMark.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid> 
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAddNew" value="#{SingleAcnoDeafMark.funButton}" oncomplete="#{rich:component('processPanel')}.show()" reRender="message,processPanel"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{SingleAcnoDeafMark.resetForm}" oncomplete="setMask();"reRender="message,maingrid" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SingleAcnoDeafMark.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{SingleAcnoDeafMark.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>    
        </body>
    </html>
</f:view>
