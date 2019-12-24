<%-- 
    Document   : TdReceiptSearch
    Created on : May 16, 2010, 1:24:58 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Td Receipt Search </title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdReceiptSearch.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="TD Receipt Search"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdReceiptSearch.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label9" styleClass="label" value="TD A/C Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddTdAccountType" styleClass="ddlist" size="1" style="width: 80px" value="#{TdReceiptSearch.acType}" >
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{TdReceiptSearch.acctTypeOption}" />
                            </h:selectOneListbox>
                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label10" styleClass="label" value="Receipt Number"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtReceiptNumber" value="#{TdReceiptSearch.receiptNo}" styleClass="input" style="width:100px"  >
                                <a4j:support actionListener="#{TdReceiptSearch.getTableDetails}" event="onblur" 
                                              reRender="gridPanel103,stxtMsg" limitToList="true"  />
                                
                            </h:inputText>

                        </h:panelGrid>

                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:200px;" >
                        <rich:dataTable value="#{TdReceiptSearch.receiptDetail}" var="item"
                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="11"><h:outputText value="TD Receipt Search" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Control No." /></rich:column>
                                    <rich:column><h:outputText value="RT No." /></rich:column>
                                    <rich:column><h:outputText value="A/C No" /></rich:column>
                                    <rich:column><h:outputText value="Principal Amt(rs.)" /></rich:column>
                                    <rich:column><h:outputText value="ROI" /></rich:column>
                                    <rich:column><h:outputText value="TD Date" /></rich:column>
                                    <rich:column><h:outputText value="TD Date(w.e.f)" /></rich:column>
                                    <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                    <rich:column><h:outputText value="Int Opt." /></rich:column>
                                    <rich:column><h:outputText value="IntToAcno" /></rich:column>
                                    <rich:column><h:outputText value="Period" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>

                            <rich:column><h:outputText value="#{item.seqNo}" /></rich:column>
                            <rich:column><h:outputText value="#{item.voucherNo}" /></rich:column>
                            <rich:column><h:outputText value="#{item.acno}" /></rich:column>
                            <rich:column>
                                        <h:outputText value="#{item.prinAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column><h:outputText value="#{item.roi}" /></rich:column>
                            <rich:column><h:outputText value="#{item.tdMadeDt}" /></rich:column>
                            <rich:column><h:outputText value="#{item.fdDt}" /></rich:column>
                            <rich:column><h:outputText value="#{item.matDt}" /></rich:column>
                            <rich:column><h:outputText value="#{item.intOpt}" /></rich:column>
                            <rich:column><h:outputText value="#{item.intToAcno}" /></rich:column>    
                            <rich:column><h:outputText value="#{item.period}" /></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />

                        <h:outputText id="stxtMsg" styleClass="error" value="#{TdReceiptSearch.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TdReceiptSearch.resetAllValue}" reRender="gridPanel103,txtReceiptNumber,ddTdAccountType,stxtMsg" focus="ddTdAccountType">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TdReceiptSearch.exitFrm}" reRender="gridPanel103,txtReceiptNumber,ddTdAccountType,stxtMsg">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
