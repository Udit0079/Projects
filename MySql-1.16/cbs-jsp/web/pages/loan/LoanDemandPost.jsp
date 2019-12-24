<%--
    Document   : LoanDemandPost
    Created on : 11 Dec, 2010, 1:06:33 PM
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
            <title>Loan Demand Posting</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanDemandPost.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Loan Demand Posting"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanDemandPost.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{LoanDemandPost.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblSingleAcct" styleClass="label" />
                            <h:selectOneRadio id="ddSingleAcct" style="width:140px;" styleClass="label" value="#{LoanDemandPost.singleAcct}">
                                <f:selectItems value="#{LoanDemandPost.singleAcctList}"/>
                                <a4j:support  action="#{LoanDemandPost.disableSingleAcct}" event="onchange"
                                              reRender="txtAccountNo,ddAllAccount,lblMsg,txtYear,ddMonthYear"/>
                            </h:selectOneRadio>
                            <h:outputLabel id="lblSingleAcct1" styleClass="label" />
                            <h:outputLabel id="lblSingleAcct2" styleClass="label" />

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAllAccount" styleClass="label" />
                            <h:selectOneRadio id="ddAllAccount" style="width:140px;" styleClass="label" value="#{LoanDemandPost.allAcct}">
                                <f:selectItems value="#{LoanDemandPost.allAcctList}"/>
                                <a4j:support  action="#{LoanDemandPost.disableAllAcct}" event="onchange"
                                              reRender="ddSingleAcct,txtAccountNo,lblMsg,txtYear,ddMonthYear"/>
                            </h:selectOneRadio>
                            <h:inputText id="txtAccountNo" styleClass="input" style="width: 134px" maxlength="#{LoanDemandPost.acNoMaxLen}" value="#{LoanDemandPost.accountNo}" disabled="#{LoanDemandPost.disableAcno}">
                                <a4j:support  ajaxSingle="true" action="#{LoanDemandPost.checkAccountNo}" event="onblur" reRender="lblMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblSingleAcct3" styleClass="label" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblMonth1" styleClass="label" />
                            <h:outputLabel id="lblMonth" styleClass="label" value = "Month / Year"/>
                            <h:panelGroup id = "bind">
                                <h:selectOneListbox id="ddMonthYear" styleClass="ddlist" size="1" style="width: 100px"  value="#{LoanDemandPost.month}">
                                    <f:selectItems value="#{LoanDemandPost.monthYearList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblseperatar" styleClass="label" value = " / "/>
                                <h:inputText id="txtYear" styleClass="input" style="width: 134px" value="#{LoanDemandPost.year}">
                                    <a4j:support  action="#{LoanDemandPost.changeOnYear}" event="onblur" focus="btnPost"
                                                  reRender="btnPost"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblMonth3" styleClass="label" />
                        </h:panelGrid>

                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="loanTable"  width="100%" styleClass="row2">

                        <a4j:region>
                            <rich:dataTable value ="#{LoanDemandPost.dmdtbl}"
                                            rowClasses="row1, row2" id = "Table" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Acno"  /> </rich:column>
                                        <rich:column><h:outputText value="Schedule No." /></rich:column>
                                        <rich:column><h:outputText value="Flow Id" /></rich:column>
                                        <rich:column><h:outputText value="Demand Date" /></rich:column>
                                        <rich:column><h:outputText value="Frequency Type" /></rich:column>
                                        <rich:column><h:outputText value="Demand Effective Date" /></rich:column>
                                        <rich:column><h:outputText value="Over Due Date" /></rich:column>
                                        <rich:column><h:outputText value="Demand Amount" /></rich:column>
                                        <rich:column><h:outputText value="Equated Amount" /></rich:column>
                                        <rich:column><h:outputText value="Late Payment Fee" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{item.scheduleNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.flowId}"/></rich:column>
                                <rich:column><h:outputText value="#{item.demandDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.freqtype}"/></rich:column>
                                <rich:column><h:outputText value="#{item.dmdEffDt}"/></rich:column>
                                <rich:column><h:outputText value="#{item.overDueDt}"/></rich:column>
                                <rich:column><h:outputText value="#{item.dmdAmt}"><f:convertNumber   pattern="####.00"  /></h:outputText></rich:column>
                                <rich:column><h:outputText value="#{item.equatedAmt}"><f:convertNumber   pattern="####.00"  /></h:outputText></rich:column>
                                <rich:column><h:outputText value="#{item.latePayFee}"><f:convertNumber   pattern="####.00"  /></h:outputText></rich:column>


                            </rich:dataTable>

                        </a4j:region>
                        <rich:datascroller align="left" for="Table" maxPages="20" />
                    </h:panelGrid>

                    <rich:modalPanel id="postPanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirm Dialog ?" style="padding-right:15px;" />
                        </f:facet>
                        <a4j:region id="postRegion">
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px;">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Post Data?" styleClass="output"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{LoanDemandPost.Post}"
                                                                   onclick="#{rich:component('postPanel')}.hide();" reRender="lblMsg,loanTable,btnPost"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel"  ajaxSingle="true" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </a4j:region>
                    </rich:modalPanel>

                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">

                            <a4j:commandButton id="btnPost" value="Post" disabled="#{LoanDemandPost.disablePost}" onclick="#{rich:component('postPanel')}.show()">
                            </a4j:commandButton>


                            <a4j:commandButton id="btnRefresh" value="Refresh">
                                <a4j:support  action="#{LoanDemandPost.refresh}" event="onclick"
                                              reRender="lblMsg,leftPanel,loanTable,btnPost,Row3"/>
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanDemandPost.exitForm}">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="postRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>