<%--
    Document   : loanadvanceauthorization
    Created on : Sep 7, 2011, 2:38:04 PM
    Author     : Administrator
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Loan / Advance Authorization</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanAdvanceAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Loan / Advance Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="Username: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanAdvanceAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="errPanel" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText value="#{LoanAdvanceAuthorization.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel1" style="width:100%;text-align:center;" styleClass="row2">
                        <h:panelGroup layout="block">
                            <h:outputLabel value="Select Authorization type" styleClass="label"/>&nbsp;
                            <h:selectOneListbox id="ddSelectAuthType" size="1" styleClass="ddlist" style="width:130px" value="#{LoanAdvanceAuthorization.authType}">
                                <f:selectItems value="#{LoanAdvanceAuthorization.typeList}"/>
                                <a4j:support event="onblur" action="#{LoanAdvanceAuthorization.onBlurAuthType}" reRender="mainPanel,panel3"
                                             oncomplete="
                                             if(#{LoanAdvanceAuthorization.authType=='Advance'})
                                             {
                                             #{rich:element('panel3')}.style.display='';
                                             #{rich:element('panel2')}.style.display=none;
                                             }
                                             if(#{LoanAdvanceAuthorization.authType=='Loan'})
                                             {
                                             #{rich:element('panel2')}.style.display='';
                                             #{rich:element('panel3')}.style.display=none;
                                             }
                                             "/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="panel2" style="display:none;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LoanAdvanceAuthorization.loanTable}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList1" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Loan No." style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Employee Name" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Loan Type" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Sanction Date" style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Sanction Amount" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.empLoanNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.loanTypeDescription}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sanctionDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sanctionAmount}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink1"
                                                     oncomplete="#{rich:element('panel2')}.style.display='';
                                                     #{rich:component('confirmationPanel')}.show();"
                                                     reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{LoanAdvanceAuthorization.currentLoanItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="panel3" style="display:none;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LoanAdvanceAuthorization.advanceTable}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList2" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Advance No." style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Employee Name" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Advance Type" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Sanction Date" style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Sanction Amount" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.empAdvNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.description}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sanctionDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sanctionAmount}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink2"
                                                     oncomplete="#{rich:element('panel3')}.style.display='';
                                                     #{rich:component('confirmationPanel')}.show();"
                                                     reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{LoanAdvanceAuthorization.currentAdvanceItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList2" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCalcel" type="reset" value="Cancel" action="#{LoanAdvanceAuthorization.cancelAction}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanAdvanceAuthorization.exitBtnAction}"/>
                        </h:panelGroup>

                        <rich:modalPanel id="confirmationPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Confirmation DialogBox" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Authorize The Data ?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:region id="yesBtn">
                                                    <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{LoanAdvanceAuthorization.authorizeBtnAction}"
                                                                       onclick="#{rich:component('confirmationPanel')}.hide();" reRender="mainPanel" />
                                                </a4j:region>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="No" value="No" onclick="#{rich:component('confirmationPanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>


