<%-- 
    Document   : AccountEditAuthorization
    Created on : Oct 29, 2010, 10:39:19 AM
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
            <title>Account Edit Authorization</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountEditAuthorization.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Account Edit Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountEditAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{AccountEditAuthorization.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{AccountEditAuthorization.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountEditAuthorization.otherAcNatListOld}" var="dataItem" rendered="#{AccountEditAuthorization.gridFlag==true}"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="Old Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Operation Mode" /></rich:column>
                                        <%--rich:column><h:outputText value="Organization" /></rich:column--%>
                                        <rich:column><h:outputText value="Introducer A/c. No." /></rich:column>
                                        <%--<rich:column><h:outputText value="Father Name" /></rich:column>
                                        <rich:column><h:outputText value="Corr. Add" /></rich:column>
                                        <rich:column><h:outputText value="Per. Add" /></rich:column>
                                        <rich:column><h:outputText value="Ph. No." /></rich:column>
                                        <rich:column><h:outputText value="Pan No." /></rich:column>--%>
                                        <rich:column><h:outputText value="Nominee" /></rich:column>
                                        <rich:column><h:outputText value="Relationship" /></rich:column>
                                        <rich:column><h:outputText value="JtName 1" /></rich:column>
                                        <rich:column><h:outputText value="JtName 2" /></rich:column>
                                        <rich:column><h:outputText value="JtName 3" /></rich:column>
                                        <rich:column><h:outputText value="Min Bal Charges" /></rich:column>
                                        <rich:column><h:outputText value="Guardian Name" /></rich:column>
                                        <rich:column><h:outputText value="Instructions" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.custName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.operMode}"/></rich:column>
                                <%--rich:column><h:outputText value="#{dataItem.occupation}"/></rich:column--%>
                                <rich:column><h:outputText value="#{dataItem.introAcctNo}"/></rich:column>
                                <%--<rich:column><h:outputText value="#{dataItem.fatherName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.crAddress}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.prAddress}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.phoneNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.panNo}"/></rich:column>--%>
                                <rich:column><h:outputText value="#{dataItem.nominee}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.relationship}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName1}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName2}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName3}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.minBalCharges}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.grName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instructions}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}"/></rich:column>                                
                            </rich:dataTable>

                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountEditAuthorization.otherAcNatListNew}" var="dataItem1" rendered="#{AccountEditAuthorization.gridFlag==true}"
                                            rowClasses="gridrow1,gridrow2" id="taskList1" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="New Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Operation Mode" /></rich:column>
                                        <%--rich:column><h:outputText value="Organization" /></rich:column--%>
                                        <rich:column><h:outputText value="Introducer A/c. No." /></rich:column>
                                        <%--<rich:column><h:outputText value="Father Name" /></rich:column>
                                        <rich:column><h:outputText value="Corr. Add" /></rich:column>
                                        <rich:column><h:outputText value="Per. Add" /></rich:column>
                                        <rich:column><h:outputText value="Ph. No." /></rich:column>
                                        <rich:column><h:outputText value="Pan No." /></rich:column>--%>
                                        <rich:column><h:outputText value="Nominee" /></rich:column>
                                        <rich:column><h:outputText value="Relationship" /></rich:column>
                                        <rich:column><h:outputText value="JtName 1" /></rich:column>
                                        <rich:column><h:outputText value="JtName 2" /></rich:column>
                                        <rich:column><h:outputText value="JtName 3" /></rich:column>
                                        <rich:column><h:outputText value="Min Bal Charges" /></rich:column>
                                        <rich:column><h:outputText value="Guardian Name" /></rich:column>
                                        <rich:column><h:outputText value="Instructions" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem1.custNameNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.operModeNew}"/></rich:column>
                                <%--rich:column><h:outputText value="#{dataItem1.occupationNew}"/></rich:column--%>
                                <rich:column><h:outputText value="#{dataItem1.introAcctNoNew}"/></rich:column>
                                <%--<rich:column><h:outputText value="#{dataItem1.fatherNameNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.crAddressNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.prAddressNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.phoneNoNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.panNoNew}"/></rich:column>--%>
                                <rich:column><h:outputText value="#{dataItem1.nomineeNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.relationshipNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.jtName1New}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.jtName2New}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.jtName3New}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.minBalChargesNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.grNameNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.instructionsNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.enterByNew}"/></rich:column>                                
                            </rich:dataTable>

                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a8" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountEditAuthorization.FDAcNatListOld}" var="dataItem2" rendered="#{AccountEditAuthorization.gridFlag==false}"
                                            rowClasses="gridrow1,gridrow2" id="taskList2" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="Old Detail Of Account" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Operation Mode" /></rich:column>
                                        <%--rich:column><h:outputText value="Organization" /></rich:column--%>
                                        <rich:column><h:outputText value="Introducer A/c. No." /></rich:column>
                                        <%--<rich:column><h:outputText value="Father Name" /></rich:column>
                                        <rich:column><h:outputText value="Corr. Add" /></rich:column>
                                        <rich:column><h:outputText value="Per. Add" /></rich:column>
                                        <rich:column><h:outputText value="Ph. No." /></rich:column>
                                        <rich:column><h:outputText value="Pan No." /></rich:column>--%>
                                        <rich:column><h:outputText value="Apply TDS" /></rich:column>
                                        <rich:column><h:outputText value="TDS Documents" /></rich:column>
                                        <rich:column><h:outputText value="Nominee" /></rich:column>
                                        <rich:column><h:outputText value="Relationship" /></rich:column>
                                        <rich:column><h:outputText value="JtName 1" /></rich:column>
                                        <rich:column><h:outputText value="JtName 2" /></rich:column>
                                        <rich:column><h:outputText value="JtName 3" /></rich:column>
                                        <rich:column><h:outputText value="Guardian Name" /></rich:column>
                                        <rich:column><h:outputText value="Instructions" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Cust Type" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem2.custName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.operMode}"/></rich:column>
                                <%--rich:column><h:outputText value="#{dataItem2.occupation}"/></rich:column--%>
                                <rich:column><h:outputText value="#{dataItem2.introAcctNo}"/></rich:column>
                                <%--<rich:column><h:outputText value="#{dataItem2.fatherName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.crAddress}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.prAddress}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.phoneNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.panNo}"/></rich:column>--%>
                                <rich:column><h:outputText value="#{dataItem2.applyTds}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.tdsDocuments}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.nominee}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.relationship}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.jtName1}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.jtName2}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.jtName3}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.grName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.instructions}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.enterBy}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.cust_type}"/></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a9" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountEditAuthorization.FDAcNatListNew}" var="dataItem3" rendered="#{AccountEditAuthorization.gridFlag==false}"
                                            rowClasses="gridrow1,gridrow2" id="taskList3" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="New Detail Of Account" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Operation Mode" /></rich:column>
                                        <%--rich:column><h:outputText value="Organization" /></rich:column--%>
                                        <rich:column><h:outputText value="Introducer A/c. No." /></rich:column>
                                        <%--<rich:column><h:outputText value="Father Name" /></rich:column>
                                        <rich:column><h:outputText value="Corr. Add" /></rich:column>
                                        <rich:column><h:outputText value="Per. Add" /></rich:column>
                                        <rich:column><h:outputText value="Ph. No." /></rich:column>
                                        <rich:column><h:outputText value="Pan No." /></rich:column>--%>
                                        <rich:column><h:outputText value="Apply TDS" /></rich:column>
                                        <rich:column><h:outputText value="TDS Documents" /></rich:column>
                                        <rich:column><h:outputText value="Nominee" /></rich:column>
                                        <rich:column><h:outputText value="Relationship" /></rich:column>
                                        <rich:column><h:outputText value="JtName 1" /></rich:column>
                                        <rich:column><h:outputText value="JtName 2" /></rich:column>
                                        <rich:column><h:outputText value="JtName 3" /></rich:column>
                                        <rich:column><h:outputText value="Guardian Name" /></rich:column>
                                        <rich:column><h:outputText value="Instructions" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Cust Type" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem3.custNameNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.operModeNew}"/></rich:column>
                                <%--rich:column><h:outputText value="#{dataItem3.occupationNew}"/></rich:column--%>
                                <rich:column><h:outputText value="#{dataItem3.introAcctNoNew}"/></rich:column>
                                <%--<rich:column><h:outputText value="#{dataItem3.fatherNameNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.crAddressNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.prAddressNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.phoneNoNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.panNoNew}"/></rich:column>--%>
                                <rich:column><h:outputText value="#{dataItem3.applyTdsNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.tdsDocumentsNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.nomineeNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.relationshipNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.jtName1New}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.jtName2New}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.jtName3New}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.grNameNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.instructionsNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.enterByNew}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem3.cust_typeNew}"/></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a10" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountEditAuthorization.pendingAcctList}" var="pendingLt" align="center"
                                            rowClasses="gridrow1,gridrow2" id="taskList4" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="40%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12">
                                            <h:outputText value="Pending List To Be Authorized" />
                                        </rich:column>
                                        <rich:column breakBefore="true" width="50"> <h:outputText value="Account No."/></rich:column>
                                        <rich:column width="20"><h:outputText value="S.No." /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>

                                <rich:column><h:outputText value="#{pendingLt.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{pendingLt.txnId}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink  id="selectlink" action="#{AccountEditAuthorization.loadAcDetailGrid}"
                                                      reRender="a6,a7,a8,a9,a10,message,errorMessage,lpg,taskList,taskList1,taskList2,taskList3,taskList4,gpFooter" focus="btnAuthorize">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{pendingLt}" target="#{AccountEditAuthorization.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{AccountEditAuthorization.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="center" for="taskList4" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" oncomplete="#{rich:component('authorizePanel')}.show()" disabled="#{AccountEditAuthorization.btnFlag}" reRender="a6,a7,a8,a9,a10,message,errorMessage,lpg,taskList,taskList1,taskList2,taskList3,taskList4,gpFooter"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountEditAuthorization.exitBtnAction}"/>
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
            <rich:modalPanel id="authorizePanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Authorize?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{AccountEditAuthorization.accountAuthorization}"
                                                       oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="a6,a7,a8,a9,a10,message,errorMessage,lpg,taskList,taskList1,taskList2,taskList3,taskList4,gpFooter"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNo" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
