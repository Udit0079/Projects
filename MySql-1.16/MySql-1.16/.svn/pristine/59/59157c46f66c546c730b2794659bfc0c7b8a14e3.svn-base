<%--
    Document   : LoanSecurityAuth
    Created on : Jan 29, 2011, 10:58:08 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Loan Security Auth</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanSecurityAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Authorization"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanSecurityAuth.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="lpg" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="message" styleClass="msg" value="#{LoanSecurityAuth.message}"/>
                    </h:panelGrid>
                    <%--h:panelGrid columnClasses="col8,co8" columns="2" id="gridPanel14" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputLabel id="label9" styleClass="label" style="padding-left:350px" value="Acno"/>
                        <h:panelGroup >
                            <h:outputText id="stxtAcno" styleClass="output" value="#{LoanSecurityAuth.acNumbers}" />
                            <h:outputLabel id="lblspace" styleClass="label" value=" "/>
                            <h:outputLabel id="lblspace1" styleClass="label" value=""/>
                            <h:outputLabel id="lblspace2" styleClass="label" value=" "/>
                            <h:outputText id="stxtName" styleClass="output" value="#{LoanSecurityAuth.customerName}" />
                        </h:panelGroup>
                    </h:panelGrid--%>
                    <h:panelGrid columnClasses="col2,col2,col7,col7" columns="4" id="gridPanel14" styleClass="row2" width="100%">
                        <h:outputLabel id="label9" styleClass="label" value="Acno"/>
                        <h:outputText id="stxtAcno" styleClass="output" value="#{LoanSecurityAuth.acNumbers}" />
                        <h:panelGroup >
                            <h:outputLabel id="lblTotLien" styleClass="label" value="Total Lien -->"/>
                            <h:outputLabel id="lblspace2" styleClass="label" value="#{LoanSecurityAuth.totLien}"/>
                        </h:panelGroup>
                        <h:outputText id="stxtName" styleClass="output" value="#{LoanSecurityAuth.customerName}" />                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LoanSecurityAuth.loanSeqAuth}" var="dataItem1"  rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="14"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                         <rich:column><h:outputText value="NAME" /></rich:column>
                                        <rich:column><h:outputText value="DETAILS" /></rich:column>
                                        <rich:column><h:outputText value="SECURITY TYPE" /></rich:column>
                                        <rich:column><h:outputText value="SECURITY OPTION" /></rich:column>
                                        <rich:column><h:outputText value="SECURITY CHARGE" /></rich:column>
                                        <rich:column><h:outputText value="MATURITY/ HYPOTHICATED VALUE" /></rich:column>
                                        <rich:column><h:outputText value="LIEN VALUE" /></rich:column>
                                        <rich:column><h:outputText value="PRIMARY/ COLLATERAL" /></rich:column>
                                        <rich:column><h:outputText value="MARGIN" /></rich:column>
                                        <rich:column><h:outputText value="ISSUE DATE/ DOC RECEIVED DATE" /></rich:column>
                                        <rich:column><h:outputText value="MATURITY DATE/ NEXT DUE DATE" /></rich:column>
                                        <rich:column><h:outputText value="ENTER BY" /></rich:column>
                                        <rich:column><h:outputText value="AUTH" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem1.seqNumber}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.name}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.details}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.securityType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.securityOption}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.securityCharge}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem1.maturityValue}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem1.lienValue}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem1.primaryColl}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem1.margin}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem1.issueDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.maturityDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.auth}" />
                                        <a4j:support action="#{LoanSecurityAuth.changeValue}" ajaxSingle="true" event="ondblclick" reRender="a6,btnAuthorize" >
                                            <f:setPropertyActionListener value="#{row}" target="#{LoanSecurityAuth.currentRow1}"/>
                                        </a4j:support>
                                </rich:column>

                           </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LoanSecurityAuth.loanSeq}" var="dataItem" align="center" rowClasses="gridrow1,gridrow2" id="taskList2" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="40%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Pending List To Be Authorized" /></rich:column>
                                        <rich:column breakBefore="true" width="50"> <h:outputText value="Account No."/></rich:column>
                                        <rich:column><h:outputText value="S. No." /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.seqNumber}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{LoanSecurityAuth.getAuthorizeValue}"
                                                         reRender="a6,stxtAcno,stxtName,lblspace2" >
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{LoanSecurityAuth.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{LoanSecurityAuth.currentRow}"/>
                                        </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="center" for="taskList2" maxPages="10" />
                        </a4j:region>
                        <rich:modalPanel id="authorizePanel" autosized="true" width="200" onshow="btnYes">
                            <f:facet name="header">
                                <h:outputText value="Are You Sure To Authorize?" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{LoanSecurityAuth.accountAuthorization}"
                                                                   oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="a6,a7,stxtAcno,stxtName,message,gpFooter" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnList" value="List" action="#{LoanSecurityAuth.getList}" reRender="a7,message" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnAuthorize" value="Authorize" disabled="#{LoanSecurityAuth.flag}" oncomplete="#{rich:component('authorizePanel')}.show()" reRender="a6,a7,lpg,message">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanSecurityAuth.exitFrm}" reRender="a6,a7,stxtAcno,stxtName,message" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:outputText value="Processing" />
                    </f:facet>
                    <h:outputText value="Wait Please..." />
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
