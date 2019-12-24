<%--
    Document   : interestcalculation
    Created on : Aug 19, 2011, 2:27:49 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Interest Calculation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="InterestCalculation"/>
            <a4j:form id="interestcalculation">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="label" value="#{InterestCalculation.datetoday}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Interest Calculation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="label" value="#{InterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel0" styleClass="row1" style="text-align :center;">
                        <h:outputLabel id="errmsg" value="#{InterestCalculation.msg}"  styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid1" width="100%" columns="6" columnClasses="col3,col2,col3,col3,col3,col15"styleClass="row2">
                        <h:outputLabel value="Enter the Acount No." styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtacno" styleClass="input" value="#{InterestCalculation.oldAcno}" maxlength="#{InterestCalculation.acNoMaxLen}" size="#{InterestCalculation.acNoMaxLen}">
                                <a4j:support event="onblur" action="#{InterestCalculation.onblurAcno}" oncomplete="#{rich:element('txtTodate')}.style=setMask();" reRender="newAcno,outname,txtAccountopendt,outtextmatDt,txtFrdate,txtTodate,txtBalance,calculateBtn,postBtn,errmsg"/>
                            </h:inputText>
                            <h:outputLabel id="newAcno" value="#{InterestCalculation.newAcno}" styleClass="label" style="color:blue"/>
                        </h:panelGroup>
                        <h:outputLabel value="Account Holder Name" styleClass="label"/>
                        <h:outputText id="outname"value="#{InterestCalculation.name}" styleClass="msg"/>
                        <h:outputLabel value="Account open Date" styleClass="label"/>
                        <h:outputText id="txtAccountopendt" value="#{InterestCalculation.acopenDate}" styleClass="msg"><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel2" width="100%" columns="6" columnClasses="col3,col2,col3,col3,col3,col15"styleClass="row1">
                        <h:outputLabel value="Maturity Date" styleClass="label"/>
                        <h:outputText id="outtextmatDt" value="#{InterestCalculation.matDt}" styleClass="msg"><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText>
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtFrdate" styleClass="input calInstDate" style="width:70px;" maxlength="10" value="#{InterestCalculation.frDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputLabel styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtTodate" styleClass="input calInstDate"  style="width:70px;" maxlength="10" value="#{InterestCalculation.toDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputLabel styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>                    
                    <h:panelGrid id="BodyPanel3" width="100%" columns="6" columnClasses="col3,col2,col3,col3,col3,col15"styleClass="row2" >
                        <h:outputLabel value="Penalty ((If any) in %)" styleClass="label"/>
                        <h:inputText id="txtpenalty" styleClass="input" value="#{InterestCalculation.peanalty}" maxlength="6" size="8"/>
                        <h:outputLabel value="Balance" styleClass="label"/>
                        <h:outputText id="txtBalance" value="#{InterestCalculation.balance}" styleClass="msg"/>
                        <h:outputLabel value="Rate of Interest (in %)" styleClass="label"/>
                        <h:outputText id="outtextinterest" value="#{InterestCalculation.interest}" styleClass="msg"/>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel4" width="100%" columns="6" columnClasses="col3,col2,col3,col3,col3,col15" styleClass="row1">
                        <h:outputLabel value="Interest Paid" styleClass="label"/>
                        <h:outputText id="stxtTInt" value="#{InterestCalculation.totInt}" styleClass="msg"/>
                        <h:outputLabel value="Remaining Int." styleClass="label"/>
                        <h:outputText id="stxtBInt" value="#{InterestCalculation.interestamt}" styleClass="msg"/>
                        <h:outputLabel value="Actual Total Int." styleClass="label"/>
                        <h:outputText id="stxtActInt" value="#{InterestCalculation.actTotInt}" styleClass="msg"/>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel5" width="100%" columns="6" columnClasses="col3,col2,col3,col3,col3,col15" styleClass="row2">
                        <h:outputLabel value="Tds To Be Deducted" styleClass="label"/>
                        <h:outputText id="stxtBtds" value="#{InterestCalculation.balTds}" styleClass="msg"/>
                        <h:outputLabel value="Tds Deducted" styleClass="label"/>
                        <h:outputText id="stxtTtds" value="#{InterestCalculation.totTds}" styleClass="msg"/>
                        <h:outputLabel value="Tds Deducted Last Fin. Year" styleClass="label"/>
                        <h:outputText id="stxtFTds" value="#{InterestCalculation.finTds}" styleClass="msg"/>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel6" width="100%" columns="6" columnClasses="col3,col2,col3,col3,col3,col15" styleClass="row1">
                        <h:outputLabel value="TDS to be Deducted For Closed Accounts" styleClass="label"/>
                        <h:outputText id="stxtCTdsToDed" value="#{InterestCalculation.closeActTdsToBeDeducted}" styleClass="msg"/>
                        <h:outputLabel value="TDS Deducted For Closed Accounts" styleClass="label"/>
                        <h:outputText id="stxtCTds" value="#{InterestCalculation.closeActTdsDeducted}" styleClass="msg"/>
                        <h:outputLabel value="Interest For Closed Accounts" styleClass="label"/>
                        <h:outputText id="stxtCInt" value="#{InterestCalculation.closeActIntFinYear}" styleClass="msg"/>
                    </h:panelGrid> 
                    <h:panelGrid id="BodyPanel8" width="100%" columns="6" columnClasses="col3,col2,col3,col3,col3,col15" styleClass="row1">
                        <h:outputLabel value="Total Amt to be paid (Rs.)" styleClass="label"/>
                        <h:outputText id="outtexttotamt" value="#{InterestCalculation.totalamt}" styleClass="msg"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel9"  style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="group1" >
                            <a4j:commandButton id="calculateBtn" value="Calculate" disabled="#{InterestCalculation.calculateflag}"action="#{InterestCalculation.btnCalculate}" oncomplete="setMask();" reRender="errmsg,stxtCInt,stxtCTds,stxtCTdsToDed,stxtFTds,stxtTtds,stxtBtds,stxtBInt,stxtActInt,stxtTInt,outtextinterest,txtBalance,outtexttotamt,postBtn,newAcno"/>
                            <a4j:commandButton id="postBtn" value="Post"  disabled="#{InterestCalculation.postflag}" onclick="#{rich:component('modelpanel')}.show();" oncomplete=" setMask();" reRender="PanelGridMain"/>
                            <a4j:commandButton id="refreshBtn" value="Refresh" action="#{InterestCalculation.btnRefresh}" oncomplete="setMask();" reRender="PanelGridMain,newAcno"/>
                            <a4j:commandButton id="exitBtn" value="Exit" action="#{InterestCalculation.btnExit}" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                 <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:modalPanel id="modelpanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()" >
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
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
                                        <a4j:region id="yesBtn">
                                            <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{InterestCalculation.btnPost}"
                                                               onclick="#{rich:component('modelpanel')}.hide();" oncomplete="setMask();" reRender="PanelGridMain"/>
                                        </a4j:region>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="No" value="No" onclick="#{rich:component('modelpanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
