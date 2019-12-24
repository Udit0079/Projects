<%-- 
    Document   : prizmcardissue
    Created on : 21 Aug, 2015, 4:20:30 PM
    Author     : root
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
            <title>ATM Account Mapping</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{prizmCardIssue.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM Account Mapping"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{prizmCardIssue.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{prizmCardIssue.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="functiongrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" style="width: 120px" styleClass="ddlist"  value="#{prizmCardIssue.function}" size="1">
                            <f:selectItems value="#{prizmCardIssue.functionList}" />
                            <a4j:support action="#{prizmCardIssue.onChangeFunction}" event="onblur" oncomplete="setMask();" focus="txtAcno"
                                         reRender="message,txtAcno,txtCardNo,txtCardExpiry,txtMinLimit,txnGrid,txnList,btnProcess,stxtName"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcno" styleClass="label" value="A/c No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtAcno" styleClass="input" maxlength="12" size="14" value="#{prizmCardIssue.acno}" disabled="#{prizmCardIssue.disableAcno}">
                                <a4j:support action="#{prizmCardIssue.onBlurAccount}" event="onblur" ajaxSingle="true"
                                             reRender="message,txtCardNo,txtCardExpiry,txtMinLimit,btnProcess,stxtName" 
                                             oncomplete="setMask();if(#{prizmCardIssue.function == 'A'}){#{rich:element('txtMinLimit')}.focus();}else{#{rich:element('txtCardNo')}.focus();}"/>
                            </h:inputText>
                            <h:outputText id="stxtName" styleClass="label" value="#{prizmCardIssue.acnoName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCardNo" styleClass="label" value="Card No. :"/>
                        <h:inputText id="txtCardNo" styleClass="input" maxlength="16" size="18" value="#{prizmCardIssue.cardNo}" disabled="#{prizmCardIssue.disableCardNo}"/>
                        <h:outputLabel id="lblCardExpiry" styleClass="label" value="Card Expiry Date"/>
                        <h:inputText id="txtCardExpiry" styleClass="input issueDt" style="width:70px" value="#{prizmCardIssue.cardExpiry}" disabled="#{prizmCardIssue.disableCardExpiry}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <%--<h:outputLabel id="lblPin" styleClass="label" value="Pin Required:"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPin" style="width:120px" styleClass="ddlist" value="#{prizmCardIssue.pin}" size="1" disabled="#{prizmCardIssue.disablePin}">
                            <f:selectItems value="#{prizmCardIssue.pinList}" />
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>--%>
                    </h:panelGrid>
                    <h:panelGrid id="limitGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblMinLimit" styleClass="label" value="Min Txn Limit :"/>
                        <h:inputText id="txtMinLimit" styleClass="input" maxlength="12" size="14" value="#{prizmCardIssue.minLimit}" disabled="#{prizmCardIssue.disableLimit}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="#{prizmCardIssue.processButtonValue}" 
                                               action="#{prizmCardIssue.populateMessage}" 
                                               oncomplete="#{rich:component('processPanel')}.show()" 
                                               disabled="#{prizmCardIssue.processVisible}" reRender="message,processPanel,confirmid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{prizmCardIssue.resetForm}" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{prizmCardIssue.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;display:#{prizmCardIssue.enableAuthGrid}" columnClasses="vtop">
                        <rich:dataTable  value="#{prizmCardIssue.unAuthList}" var="item"
                                         rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="9"><h:outputText value="ATM Registration"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="A/c Status" /></rich:column>
                                    <rich:column><h:outputText value="A/c No." /></rich:column>
                                    <rich:column><h:outputText value="Card No." /></rich:column>
                                    <rich:column><h:outputText value="Card Expiry Date" /></rich:column>
                                    <rich:column><h:outputText value="Pin Status" /></rich:column>
                                    <rich:column><h:outputText value="Reg.Date" /></rich:column>
                                    <rich:column><h:outputText value="Min.Limit" /></rich:column>
                                    <rich:column><h:outputText value="Enter By" /></rich:column>
                                    <rich:column><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.acStatus}"/></rich:column>
                            <rich:column><h:outputText value="#{item.acno}"/></rich:column>
                            <rich:column><h:outputText value="#{item.cardNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.cardExpiryDate}"/></rich:column>
                            <rich:column><h:outputText value="#{item.pinStatus}"/></rich:column>                            
                            <rich:column><h:outputText value="#{item.regDate}"/></rich:column>
                            <rich:column><h:outputText value="#{item.minLimit}"/></rich:column>                            
                            <rich:column><h:outputText value="#{item.enterBy}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink id="selectlink" oncomplete="#{rich:component('authPanel')}.show()" reRender="message,authPanel" ajaxSingle="true">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{item}" target="#{prizmCardIssue.currentItem}"/>                                    
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="unauthDataScroll"align="left" for="txnList" maxPages="10"/>
                    </h:panelGrid>
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
                                    <h:outputText id="confirmid" value="#{prizmCardIssue.confirmationMsg}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{prizmCardIssue.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddFunction')}.focus();" 
                                                       reRender="message,maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="authPanel" autosized="true" width="250" onshow="#{rich:element('btnAuthNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="authConfirmId" value="Are you sure to authorize ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnAuthYes" action="#{prizmCardIssue.txnVerification}" 
                                                       oncomplete="#{rich:component('authPanel')}.hide(); #{rich:element('ddFunction')}.focus();" 
                                                       reRender="message,maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnAuthNo" onclick="#{rich:component('authPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
