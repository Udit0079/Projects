<%--
    Document   : InterestCalculation
    Created on : 03 Apr, 2012, 3:06:48 PM
    Author     : Dhirendra singh
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
            <title>Ho Interest Calculation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="SbIntCalAll">
                <h:panelGrid  columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{HoIntCal.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Ho Interest Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{HoIntCal.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{HoIntCal.message}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col1,col1,col1,col2,col1,col2" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblBranchWise" styleClass="label" value="Branch : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:70px;" value="#{HoIntCal.branch}">
                                <f:selectItems value="#{HoIntCal.branchOptionList}"/>
                                <a4j:support event="onblur" focus="ddQuarter"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblQuarter" styleClass="label" value = "Quarter"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddQuarter" styleClass="ddlist" size="1" style="width:100px;" value="#{HoIntCal.quarter}">
                                <f:selectItems value="#{HoIntCal.quarterEndList}"/>
                                <a4j:support event="onblur" focus="intRate"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblIntRate" styleClass="label" value="Rate of Interest :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="intRate" value="#{HoIntCal.intRate}" styleClass="input" style="width:60px">
                                <a4j:support event="onblur" focus="btnCalculate"/>
                            </h:inputText>
                        </h:panelGrid>
                        
                            <h:panelGrid columnClasses="col1,col1,col1,col2,col1,col2" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account :"/>
                                <h:outputText id="stxtDebitAmt" styleClass="output" value="#{HoIntCal.debitAcc}"/>
                                <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account :" />
                                <h:outputText id="stxtCreditAmt" styleClass="output" value="#{HoIntCal.creditAcc}"/>
                                <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Interest :" />
                                <h:outputText id="stxtTotalCredit" styleClass="output" value="#{HoIntCal.totalInt}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <rich:modalPanel id="calculatePanel" autosized="true" width="300" onshow="#{rich:element('btnCalcYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are you sure to calculate interest"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" id="btnCalcYes" action="#{HoIntCal.calculate}"
                                                               oncomplete="#{rich:component('calculatePanel')}.hide();if(#{HoIntCal.reportFlag})
                                                               {
                                                               #{rich:component('popUpRepPanel')}.show();
                                                               }
                                                               else if(#{!HoIntCal.reportFlag})
                                                               {
                                                               #{rich:component('popUpRepPanel')}.hide();
                                                               }"
                                                               reRender="lblMsg,leftPanel,btnPost,calFromDate,calToDate,popUpRepPanel,btnCalculate,intRate" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" onclick="#{rich:component('calculatePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('btnPostNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog"/>
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are You Sure To Post ?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{HoIntCal.Post}"
                                                               oncomplete="#{rich:component('postPanel')}.hide();" reRender="msgRow,Row2,Row4,Row5,btnPost,errorPanel9,popUpRepPanel,intRate" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnPostNo" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>

                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" disabled="#{HoIntCal.disableCal}" oncomplete="#{rich:component('calculatePanel')}.show()"
                                                   reRender="lblMsg,btnPost,calculatePanel"/>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{HoIntCal.disablePost}" oncomplete="#{rich:component('postPanel')}.show()"
                                                   reRender="msgRow,Row2,Row4,btnPost,postPanel,btnCalculate"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{HoIntCal.refresh}" reRender="lblMsg,leftPanel,calFromDate,calToDate,btnPost,btnCalculate,intRate" focus="ddBranch"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{HoIntCal.exitBtnAction}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="250" height="60" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                    <h:outputText value="It May Take Some Time. Please Wait..." />
                </rich:modalPanel>
            </a4j:form>

            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="SB Interest Calculation Report" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{HoIntCal.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>

        </body>
    </html>
</f:view>