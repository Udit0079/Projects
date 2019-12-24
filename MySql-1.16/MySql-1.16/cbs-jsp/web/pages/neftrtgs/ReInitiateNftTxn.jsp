<%-- 
    Document   : ReInitiateNftTxn
    Created on : 21 Jun, 2018, 1:19:35 PM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>ReInitiate NFT TXN</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>         
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="ReInitiateNFT">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ReInitiateNFT.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Renitiate Neft Txn Details"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ReInitiateNFT.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="3" columnClasses="col3,col3,col3" styleClass="row1" style="width:100%">
                        <h:panelGroup id="groupPanel11" layout="block">
                            <h:outputLabel id="label77" style="width:90px" styleClass="label" value="Transcation Date " ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="EnterDate" styleClass="input calInstDate" style="setMask();width:70px;" value="#{ReInitiateNFT.enterDate}">
                                    <a4j:support event="onblur" action="#{ReInitiateNFT.gridDetails}" reRender="gridPanelTable,txnList,errorMsg,gpFooter,btnRefresh,btnExit" oncomplete="setMask();"/>
                                </h:inputText>  
                            </h:panelGroup>
                            <h:outputText id="errorMsg" styleClass="error" value="#{ReInitiateNFT.errorMsg}" style="width:100%;text-align:center"/>
                        </h:panelGrid>

                    <h:panelGrid id="gridPanelTable" style="width:100%;height:0px" styleClass="row1"  columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{ReInitiateNFT.gridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="15" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="RefNo" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary Name" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary IFSC" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary A/cNo" /></rich:column>
                                        <rich:column><h:outputText value="Remitter A/cNo" /></rich:column>
                                        <rich:column><h:outputText value="Payment Date" /></rich:column>
                                        <rich:column><h:outputText value="Txn Date" /></rich:column>
                                        <rich:column><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center"><h:outputText value="#{tempItem.refno}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{tempItem.beneficiaryName}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{tempItem.beneficiaryIFSC}" /></rich:column>                               
                                <rich:column style="text-align:center">
                                    <h:outputText value="#{tempItem.amount}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{tempItem.creditAccountNo}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{tempItem.debitAccountNo}" /></rich:column>   
                                <rich:column style="text-align:center"><h:outputText value="#{tempItem.paymentDate}" /></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{tempItem.txnDate}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" oncomplete="#{rich:component('ConformationPanel')}.show()" reRender="ConformationPanel,errorPanel,errorMsg">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{tempItem}" target="#{ReInitiateNFT.tempCurrentItem}"/>  
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="unAuthTempDataScroll" align="center" for="txnList" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid>

                    <h:panelGrid id="gpFooter" columnClasses="col19,col20,col21" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ReInitiateNFT.refresh}" reRender="errorPanel,gridPanelTable,errorMsg,txnList,EnterDate,groupPanel11"></a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ReInitiateNFT.exit}"></a4j:commandButton>
                        </h:panelGroup>         
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
            <rich:modalPanel id="ConformationPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Conformation Dialog" style="padding-right:15px;text-align:center;font-weight:bold;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to reinitiate the transcation?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{ReInitiateNFT.update}" onclick="#{rich:component('ConformationPanel')}.hide();" 
                                                       reRender="errorPanel,gridPanelTable,errorMsg,txnList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('ConformationPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>  
    </html>
</f:view>
