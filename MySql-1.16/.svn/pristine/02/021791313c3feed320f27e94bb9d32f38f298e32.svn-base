<%-- 
    Document   : sssAcDetails
    Created on : May 21, 2015, 1:04:20 PM
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
            <title>SSS Ac Details</title>
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
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{SSSAcDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="SSS Ac Details"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{SSSAcDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{SSSAcDetails.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="sectypegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblSecurityType" styleClass="label" value="Scheme Code"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSecurityType" style="width:160px" styleClass="ddlist"  value="#{SSSAcDetails.schemeCode}" size="1">
                            <f:selectItems value="#{SSSAcDetails.schemeCodeList}" />
                             <a4j:support oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Vendor Name"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlNo" style="width:150px" styleClass="ddlist" value="#{SSSAcDetails.vendorCode}" size="1">
                            <f:selectItems value="#{SSSAcDetails.vendorCodeList}" />
                            <a4j:support action="#{SSSAcDetails.onblurVendorCode}" event="onblur" reRender="maingrid" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAutoDebitFrq" styleClass="label" value="Auto Debit Freq"> </h:outputLabel>
                        <h:selectOneListbox id="stxtAutoDebitFrq" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcDetails.autoDebitFreq}" >
                            <f:selectItems value="#{SSSAcDetails.autoDebitFreqList}"/>
                             <a4j:support oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="sellinggrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblpolicy" styleClass="label" value="Policy No/Acno" > <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtpolicy" size="15" styleClass="input" value="#{SSSAcDetails.policyNo}" />
                        <h:outputLabel id="lblInsuredAamt" styleClass="label" value="Insured Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtinsuredAamt" size="10" styleClass="input" value="#{SSSAcDetails.insuredAamt}" />
                        <h:outputLabel id="lblglAmt" styleClass="label" value="GL Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtglAmt" size="10" styleClass="input" value="#{SSSAcDetails.glAmt}" />
                    </h:panelGrid>
                    <h:panelGrid id="schemegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblschemeGl" styleClass="label" value="Scheme GL Head"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="stxtschemeGl" size="10" styleClass="input" maxlength="12"value="#{SSSAcDetails.schemeGl}" />
                        <h:outputLabel id="lblpremiumAmt" styleClass="label" value="Premium Amount" > <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtpremiumAmt" size="10" styleClass="input" value="#{SSSAcDetails.premiumAmt}" />
                        <h:outputLabel id="lblplAmt" styleClass="label" value="PL Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtplAmt" size="10" styleClass="input" value="#{SSSAcDetails.plAmt}" />
                    </h:panelGrid>
                    <h:panelGrid id="glgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblschemePl" styleClass="label" value="Seheme  PL Head"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="stxtschemePl" size="10" styleClass="input" maxlength="6"value="#{SSSAcDetails.schemePl}" />
                        <h:outputLabel id="lblagentAmt" styleClass="label" value="Agent Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtagentAmt" size="10" styleClass="input" value="#{SSSAcDetails.agentAmt}" />
                        <h:outputLabel id="settleDtLbl" styleClass="label" value="Effect Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="settleDt" styleClass="input chkDt" value="#{SSSAcDetails.effectiveDate}" style="width:75px;" >
                            <%--f:convertDateTime pattern="dd/MM/yyyy" /--%>
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText>                  
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{SSSAcDetails.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Scheme Code" /></rich:column>
                                        <rich:column><h:outputText value="Vendor Code" /></rich:column>
                                        <rich:column><h:outputText value="Premium Amount" /></rich:column>
                                        <rich:column><h:outputText value="Insured Amount" /></rich:column>
                                        <rich:column><h:outputText value="Schme GL" /></rich:column>
                                        <rich:column><h:outputText value="GL Amount" /></rich:column>
                                        <rich:column><h:outputText value="Scheme PL" /></rich:column>
                                        <rich:column><h:outputText value="PL Amount" /></rich:column>
                                        <rich:column><h:outputText value="Agent Amount" /></rich:column>
                                        <rich:column><h:outputText value="Effect Date" /></rich:column>
                                        <%--rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Enter Date" /></rich:column--%>
                                        <rich:column><h:outputText value="Policy No/Acno" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.schemeCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.vendorCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.premiumAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.insuredAamt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.schemeGl}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.glAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.schemePl}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.plAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.agentAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.effectiveDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.policyNo}" /></rich:column>
                                <%--rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterDate}" /></rich:column>
                                <%--rich:column><h:outputText value="#{dataItem.txnId}" /></rich:column--%>

                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{SSSAcDetails.fillValuesofGridInFields}"  reRender="maingrid,btnAddNew,btnUpdate,taskList">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SSSAcDetails.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{SSSAcDetails.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAddNew" value="Save"  disabled="#{SSSAcDetails.addNewDisable}" action="#{SSSAcDetails.saveBtn}" oncomplete="setMask();" reRender="message,maingrid,Row4,btnUpdate"/>
                            <a4j:commandButton id="btnUpdate" value="Update" disabled="#{SSSAcDetails.updateDisable}" action="#{SSSAcDetails.updateBtn}" oncomplete="setMask();" reRender="message,btnUpdate,maingrid,btnAddNew"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{SSSAcDetails.resetForm}" oncomplete="setMask();"reRender="message,maingrid" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SSSAcDetails.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
