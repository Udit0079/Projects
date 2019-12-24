<%-- 
    Document   : amortcalculation
    Created on : Jun 13, 2012, 11:13:49 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Amortization Schedule</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{amortCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Amortization Schedule"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{amortCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{amortCalculation.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="amortPanel" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                        <h:outputLabel id="secType" styleClass="label" value="Security Type"/>
                        <h:selectOneListbox id="secTypeDD" styleClass="ddlist" size="1" style="width: 200px" value="#{amortCalculation.secTpDd}">
                            <f:selectItems value="#{amortCalculation.secTypeList}"/>
                                <a4j:support action="#{amortCalculation.secTypeLostFocus}" event="onblur" reRender="message,ddCtrlNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlNo" style="width:120px" styleClass="ddlist"  value="#{amortCalculation.ctrl}" size="1">
                            <f:selectItems value="#{amortCalculation.ctrlList}" />
                            <a4j:support action="#{amortCalculation.onBlurCtrlNo}" event="onblur" reRender="message"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid id="combogrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblOption" styleClass="label" value="Option"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddOption" style="width:120px" styleClass="ddlist" value="#{amortCalculation.option}" size="1">
                            <f:selectItems value="#{amortCalculation.optionList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAmortization" styleClass="label" value="Amortization"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAmortization" style="width:120px" styleClass="ddlist" value="#{amortCalculation.amortization}" size="1">
                            <f:selectItems value="#{amortCalculation.amortizationList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnCalc" value="Calculation" action="#{amortCalculation.calculation}" oncomplete="if(#{amortCalculation.calcFlag==true}){
                                                #{rich:component('popUpRepPanel')}.show();
                                                }
                                                else if(#{amortCalculation.calcFlag==false})
                                                {
                                                #{rich:component('popUpRepPanel')}.hide();
                                                }" reRender="maingrid,popUpRepPanel"/>
                            <a4j:commandButton id="btnPost" disabled="#{amortCalculation.visiblePost}" value="Post" oncomplete="#{rich:component('processPanel')}.show();" reRender="maingrid,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{amortCalculation.resetForm}" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{amortCalculation.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Amortization Calculation" style="text-align:center;"/>
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
                                <a4j:include viewId="#{amortCalculation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>            
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to post this record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{amortCalculation.post}" oncomplete="#{rich:component('processPanel')}.hide();" reRender="message,maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
