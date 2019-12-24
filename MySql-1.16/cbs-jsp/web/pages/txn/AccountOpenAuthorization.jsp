<%-- 
    Document   : AccountOpenAuthorization
    Created on : Oct 27, 2010, 11:36:10 AM
    Author     : ROHIT KRISHNA GUPTA
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Account Open Authorization</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountOpenAuthorization.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Account Open Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountOpenAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{AccountOpenAuthorization.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{AccountOpenAuthorization.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountOpenAuthorization.acDetailList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="Account Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="A/C. No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Father/Husband Name" /></rich:column>
                                        <rich:column><h:outputText value="Permanent Address" /></rich:column>
                                        <rich:column><h:outputText value="Correspondence Address" /></rich:column>
                                        <rich:column><h:outputText value="Phone No." /></rich:column>
                                        <rich:column><h:outputText value="DOB" /></rich:column>
                                        <rich:column><h:outputText value="Occupation" /></rich:column>
                                        <rich:column><h:outputText value="Operation Mode" /></rich:column>
                                        <rich:column><h:outputText value="Nominee" /></rich:column>
                                        <rich:column><h:outputText value="Guardian Name" /></rich:column>
                                        <rich:column><h:outputText value="Relationship" /></rich:column>
                                        <rich:column><h:outputText value="Pan No." /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Introducer A/C. No." /></rich:column>
                                        <rich:column><h:outputText value="Introducer Name" /></rich:column>
                                        <rich:column><h:outputText value="Limit/Sanctioned Amt" /></rich:column>
                                        <rich:column><h:outputText value="Joint Name 1" /></rich:column>
                                        <rich:column><h:outputText value="Joint Name 2" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Cust Type" /></rich:column>
                                        <rich:column><h:outputText value="Install Amt" /></rich:column>
                                        <rich:column><h:outputText value="Period" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Amt" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fatherName}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.prAddress}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.crAddress}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.phoneNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dob}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.occupation}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.operMode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.nominee}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.grName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.relationship}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.panNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.introAcctNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.introName}" /></rich:column>
                                <rich:column >
                                    <h:outputText value="#{dataItem.limit}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column ><h:outputText value="#{dataItem.jtName1}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.jtName2}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.custTp}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.instAmt}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.prd}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.matAmt}" /></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountOpenAuthorization.pendingAcctList}" var="pendingLt" align="center"
                                            rowClasses="gridrow1,gridrow2" id="taskList2" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="40%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12">
                                            <h:outputText value="Pending List To Be Authorized" />
                                        </rich:column>
                                        <rich:column breakBefore="true" width="50"> <h:outputText value="Account No."/></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{pendingLt.acno}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink  id="selectlink" action="#{AccountOpenAuthorization.loadAcDetailGrid}"
                                                      reRender="a6,a7,message,errorMessage,lpg,taskList,taskList2,gpFooter" focus="btnAuthorize">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{pendingLt}" target="#{AccountOpenAuthorization.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{AccountOpenAuthorization.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="center" for="taskList2" maxPages="10" />
                        </a4j:region>
                        <rich:modalPanel id="authorizePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Authorize?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{AccountOpenAuthorization.accountAuthorization}"
                                                                   oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="a6,a7,lpg,taskList,taskList2,message,errorMessage,gpFooter" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" disabled="#{AccountOpenAuthorization.btnFlag}" oncomplete="#{rich:component('authorizePanel')}.show()" reRender="a6,a7,lpg,message,errorMessage"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountOpenAuthorization.btnExitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
