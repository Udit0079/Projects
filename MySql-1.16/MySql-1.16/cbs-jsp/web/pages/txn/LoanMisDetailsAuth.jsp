<%--
    Document   : LoanMisDetailsAuth
    Created on : Feb 1, 2011, 11:15:35 AM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Loan MIS Details Auth</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanMisDetailsAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Authorization Of The Loan MIS Details"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanMisDetailsAuth.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="lpg" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="message" styleClass="msg" value="#{LoanMisDetailsAuth.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,co8" columns="2" id="gridPanel14" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputLabel id="label9" styleClass="label" style="width:100%;text-align:center;" value="Details Of Following Accounts Need To Be Authorized"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,co8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="labelAcno" styleClass="label" style="padding-left:350px" value="Acno"/>
                        <h:selectOneListbox id="ddAcno" styleClass="ddlist" size="1" style="width:100px" value="#{LoanMisDetailsAuth.acno}" tabindex="1">
                            <f:selectItem itemValue="--SELECT--"/>
                            <f:selectItems value="#{LoanMisDetailsAuth.accountTypeOption}" />
                            <a4j:support actionListener="#{LoanMisDetailsAuth.getList}"  event="onblur"  reRender="taskList,taskList1,taskList2,taskList3,message" limitToList="true"  />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LoanMisDetailsAuth.loamMisFirst}" var="dataItem"  rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="13"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="CUST ID" /></rich:column>
                                        <rich:column><h:outputText value="NAME" /></rich:column>
                                        <rich:column><h:outputText value="SECTOR" /></rich:column>
                                        <rich:column><h:outputText value="SUB SECTOR" /></rich:column>
                                        <rich:column><h:outputText value="MODE OF ADVANCE" /></rich:column>
                                        <rich:column><h:outputText value="TYPE OF ADVANCE" /></rich:column>
                                        <rich:column><h:outputText value="TERM OF ADVANCE" /></rich:column>
                                        <rich:column><h:outputText value="SUB SECTOR's CATEGORY (for Quarterly)" /></rich:column>
                                        <rich:column><h:outputText value="SUB SECTOR's CATEGORY (for Yearly)" /></rich:column>
                                        <rich:column><h:outputText value="PURPOSE OF ADVANCE (OSS7)" /></rich:column>
                                        <rich:column><h:outputText value="EXPOSURE" /></rich:column>
                                        <rich:column><h:outputText value="EXPOSURE CATEGORY" /></rich:column>
                                        <rich:column><h:outputText value="PURPOSE OF EXPOSURE CATEGORY" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.custId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sectorDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.subSectorDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.modeOfAdvanceDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.securedDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.typeOfAdvanceDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.purposeOfAdvanceDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.guarnteeCoverDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.purOfAdvDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.exposureDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.exposureCategoryDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.exposureCategoryPurposeDesc}" /></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LoanMisDetailsAuth.loamMisSecond}" var="dataItem1" align="center" rowClasses="gridrow1,gridrow2" id="taskList1" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="SCHEME CODE" /></rich:column>
                                        <rich:column><h:outputText value="INTEREST TABLE" /></rich:column>
                                        <rich:column><h:outputText value="INTEREST TYPE" /></rich:column>
                                        <rich:column><h:outputText value="APPLICANT CATEGORY" /></rich:column>
                                        <rich:column><h:outputText value="CATEGORY OPT" /></rich:column>
                                        <rich:column><h:outputText value="MINOR COMMUNITY" /></rich:column>
                                        <rich:column><h:outputText value="Director/ Staff" /></rich:column>
                                        <rich:column><h:outputText value="Relation" /></rich:column>
                                        <rich:column><h:outputText value="Drawing Power Indicator(DP)" /></rich:column>
                                        <rich:column><h:outputText value="Population Group" /></rich:column>
                                        <rich:column><h:outputText value="Sanction Level" /></rich:column>
                                        <rich:column><h:outputText value="Sanctioning Authority" /></rich:column>
                                        
                                        <%--
                                        <rich:column><h:outputText value="RESTRUCTURING"/></rich:column>
                                        <rich:column><h:outputText value="SANCTION LEVEL" /></rich:column>
                                        <rich:column><h:outputText value="SANCTIONING AUTHORITY" /></rich:column>
                                        
                                        
                                        
                                        <rich:column><h:outputText value="NPA CLASSIFICATION" /></rich:column>
                                        <rich:column><h:outputText value="COURTS" /></rich:column>
                                        <rich:column><h:outputText value="MODE OF SETTLEMENT" /></rich:column>
                                        <rich:column><h:outputText value="DEBT WAIVER REASON" /></rich:column>
                                        <rich:column><h:outputText value="ASSET CLASS REASON" /></rich:column>--%>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem1.schemeCodeDesc}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.intTableDesc}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.intTypeDesc}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.applicantCategoryDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.categoryOptDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.minorCommunityDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.relationDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.relOwnerDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.drawingPwrIndDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.popuGroupDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.sanctionLevelDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.sanctionAuthDesc}" /></rich:column>
                                
                                
                                <%--<rich:column><h:outputText value="#{dataItem1.restructing}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.senctionLevel}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.senctionAuthority}"/></rich:column>                                
                                <rich:column><h:outputText value="#{dataItem1.npaClassification}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.court}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.modeOfSet}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.debtWaiverRs}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.assetClassRe}"/></rich:column>--%>

                            </rich:dataTable>
                        </a4j:region>

                    </h:panelGrid>
          <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:235px;">
                        <h:panelGroup id="p4">

                            <a4j:region>
                                <rich:dataTable value="#{LoanMisDetailsAuth.loamMisThird}" var="dataItem2"  rowClasses="gridrow1,gridrow2" id="taskList2" rows="4" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="CREATED BY USER ID" /></rich:column>
                                            <rich:column><h:outputText value="CREATION DATE" /></rich:column>
                                            <rich:column><h:outputText value="LAST UPDATED BY USER ID" /></rich:column>
                                            <rich:column><h:outputText value="LAST UPDATED DATE" /></rich:column>
                                            <rich:column><h:outputText value="TOTAL MODIFICATIONS" /></rich:column>
                                            <rich:column><h:outputText value="NET WORTH" /></rich:column>
                                            <rich:column><h:outputText value="MARGIN MONEY" /></rich:column>
                                            <rich:column><h:outputText value="DOCUMENT DATE" /></rich:column>
                                            <rich:column><h:outputText value="DOCUMENT EXP DATE" /></rich:column>
                                            <rich:column><h:outputText value="RENEWAL DATE" /></rich:column>
                                            <rich:column><h:outputText value="MONTHLY INCOME" /></rich:column>                                            
                                            <rich:column><h:outputText value="REMARKS" /></rich:column>
                                            <rich:column><h:outputText value="AUTH" /></rich:column>

                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText  value="#{dataItem2.createdUsrId}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.creationDt}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.lastUpdtUsrId}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.lastUpdtDt}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.totalModification}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.netWorth}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.marginMoney}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.documentDt}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.documentExpDt}" /></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.renewalDt}"/></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.monthlyIncome}" /></rich:column>                                    
                                    <rich:column><h:outputText  value="#{dataItem2.remarks}" /></rich:column>
                                    <rich:column><h:outputText  value="#{dataItem2.auth}" /></rich:column>
                                </rich:dataTable>
                            </a4j:region>
                            <a4j:region>
                                <rich:dataTable value="#{LoanMisDetailsAuth.loamMisFourth}" var="dataItem3" rowClasses="gridrow1,gridrow2" id="taskList3" rows="4" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="NPA Classification" /></rich:column>
                                            <rich:column><h:outputText value="Asset Class Reason" /></rich:column>
                                            <rich:column><h:outputText value="Courts" /></rich:column>
                                            <rich:column><h:outputText value="Mode of Settlement" /></rich:column>
                                            <rich:column><h:outputText value="Debt Waiver Reason" /></rich:column>                                            
                                            <rich:column><h:outputText value="Restructuring" /></rich:column>
                                            <rich:column><h:outputText value="Sanction Amount" /></rich:column>

                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem3.npaClassDesc}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem3.assetClassReasonDesc}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem3.courtsDesc}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem3.modeOfSettleDesc}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem3.debtWaiverReasonDesc}" /></rich:column>
                                    
                                    <rich:column><h:outputText value="#{dataItem3.restructuringDesc}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem3.sanctionAmt}" /></rich:column>
                                </rich:dataTable>
                             </a4j:region>
                        </h:panelGroup>
                        <h:panelGrid columnClasses="col8,col8" columns="1" id="lpgdbl" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                            <h:outputLabel id="lbldbl" styleClass="label" value="Click To Authorize This Entry" />
                        </h:panelGrid>

                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                             <a4j:commandButton id="btnAuthorize" value="Authorize" oncomplete="#{rich:component('authorizePanel')}.show()" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LoanMisDetailsAuth.clearText}" reRender="taskList,taskList1,taskList2,taskList3,ddAcno,lpg,message" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanMisDetailsAuth.exitFrm}" reRender="taskList,taskList1,taskList2,taskList3,ddAcno,message" >
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
            <rich:modalPanel id="authorizePanel" autosized="true" width="200" onshow="btnYes">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Authorize?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{LoanMisDetailsAuth.accountAuthorization}"
                                                       oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="taskList,taskList1,taskList2,taskList3,ddAcno,message" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>