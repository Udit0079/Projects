<%-- 
    Document   : TermDepositProvisionInterest
    Created on : Mar 25, 2017, 12:08:33 PM
    Author     : Admin
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
            <title>Term Deposit Provision Interest Calculation</title>
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
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{TermDepositeProvInterestCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Term Deposit Provision Interest Calculation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{TermDepositeProvInterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{TermDepositeProvInterestCalculation.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{TermDepositeProvInterestCalculation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" width="100%" columnClasses="col1,col1,col1,col2,col1,col2">
                        <h:outputLabel id="lblAcType" styleClass="label" value="Account Type :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAcType" tabindex="1" styleClass="ddlist" style="width: 70px" value="#{TermDepositeProvInterestCalculation.acType}" size="1" >
                                <f:selectItems value="#{TermDepositeProvInterestCalculation.acTypeList}" />
                                <a4j:support action="#{TermDepositeProvInterestCalculation.setdescription}" event="onchange"
                                             reRender="a3,a4,a5,a6,message,errorMessage,lpg,gpFooter,stxtAcToBeCr" limitToList="true" focus="ddIntOption" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblIntOption" styleClass="label" value="Interest Option :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddIntOption" tabindex="2" styleClass="ddlist" style="width: 100px" value="#{TermDepositeProvInterestCalculation.interestOption}" size="1" >
                                <f:selectItems value="#{TermDepositeProvInterestCalculation.interestOptionList}" />
                                <a4j:support action="#{TermDepositeProvInterestCalculation.setMonthEndQuarEnd}" event="onchange"
                                             reRender="a3,a4,a5,a6,message,errorMessage,lpg,gpFooter" limitToList="true" focus="ddMonthEnd" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblMonthEnd" styleClass="label" value="#{TermDepositeProvInterestCalculation.lblMonthEnd}"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup style="width:100%;text-align:center;">
                                <h:selectOneListbox id="ddMonthEnd" tabindex="3" styleClass="ddlist" style="width: 100px" value="#{TermDepositeProvInterestCalculation.monthEnd}" size="1" >
                                    <f:selectItems value="#{TermDepositeProvInterestCalculation.monthEndList}" />
                                    <a4j:support action="#{TermDepositeProvInterestCalculation.monthEndlostFocus}" event="onchange"
                                                 reRender="a3,a4,a5,a6,message,errorMessage,lpg,gpFooter" limitToList="true" focus="ddFinYear" />
                                </h:selectOneListbox>
                                <h:selectOneListbox id="ddFinYear" tabindex="4" styleClass="ddlist" style="width: 75px" value="#{TermDepositeProvInterestCalculation.finYear}" size="1" >
                                    <f:selectItems value="#{TermDepositeProvInterestCalculation.finYearList}" />
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblAcToBeDebt" styleClass="label" value="Account To Be Debited :" style="text-align:left;"/>
                            <h:outputText id="stxtAcToBeDebt" styleClass="output" value="#{TermDepositeProvInterestCalculation.glAcNo}" style="color:purple;text-align:left;"/>
                            <h:outputLabel id="lblDebtAmt" styleClass="label" value="Debit Amount  :" style="text-align:left;"/>
                            <h:outputText id="stxtDebtAmt" styleClass="output" value="#{TermDepositeProvInterestCalculation.debtAmt}" style="color:purple;text-align:left;"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a7" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblAcToBeCr" styleClass="label" value="Account To Be Credited :" style="text-align:left;"/>
                            <h:outputText id="stxtAcToBeCr" styleClass="output" value="#{TermDepositeProvInterestCalculation.acNoToBeCr}" style="color:purple;text-align:left;"/>
                            <h:outputLabel id="lblCrAmt" styleClass="label" value="Credit Amount   :" style="text-align:left;"/>
                            <h:outputText id="stxtCrAmt" styleClass="output" value="#{TermDepositeProvInterestCalculation.crAmt}" style="color:purple;text-align:left;"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnCalculate" tabindex="5"  value="Calculate" onclick="#{rich:component('calculatePanel')}.show()" rendered="#{TermDepositeProvInterestCalculation.calcFlag1==false}" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter" focus=""/>
                                <%--a4j:commandButton id="btnPost" tabindex="6" value="Post" onclick="#{rich:component('postPanel')}.show();#{rich:component('btnPost')}.disabled=true;" rendered="#{TermDepositeProvInterestCalculation.calcFlag1==true}" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter" /--%>
                                <a4j:commandButton id="btnRefresh" tabindex="7" value="Refresh" action="#{TermDepositeProvInterestCalculation.resetForm}" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter" />
                                <a4j:commandButton id="btnExit" tabindex="8" value="Exit" action="#{TermDepositeProvInterestCalculation.exitBtnAction}" reRender="message,errorMessage" />
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </a4j:form>
                <rich:modalPanel id="calculatePanel" autosized="true" width="250" onshow="#{rich:element('btnYesCalC')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Calculate Interest ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesCalC" ajaxSingle="true" action="#{TermDepositeProvInterestCalculation.callForProvCalculateAndPost}"
                                                       oncomplete="#{rich:component('calculatePanel')}.hide();
                                                       if(#{TermDepositeProvInterestCalculation.calcFlag1==true}){
                                                       #{rich:component('popUpRepPanel')}.show();
                                                       }
                                                       else if(#{TermDepositeProvInterestCalculation.calcFlag1==false})
                                                       {
                                                       #{rich:component('popUpRepPanel')}.hide();

                                                       }"
                                                       reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter,popUpRepPanel,stxtCrAmt" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoCalC" onclick="#{rich:component('calculatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <%--rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('btnYesPost')}.focus();">
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
                                    <a4j:commandButton value="Yes" id="btnYesPost" ajaxSingle="true" action="#{TermDepositeProvInterestCalculation.callProcedureForCalculateAndPost}"
                                                       oncomplete="#{rich:component('postPanel')}.hide();" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoPost" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel--%>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Term Deposite Interest Calculation" style="text-align:center;"/>
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
                                <a4j:include viewId="#{TermDepositeProvInterestCalculation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>
