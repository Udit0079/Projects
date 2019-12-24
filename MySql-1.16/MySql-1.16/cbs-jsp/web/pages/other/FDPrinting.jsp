<%-- 
    Document   : FDPrinting
    Created on : Sep 26, 2011, 5:27:28 PM
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
            <title>FD Printing</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="fdprinting">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{FDPrinting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="FD Printing"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FDPrinting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgGrid" columns="1" width="100%" styleClass="row2" style="width:100%;text-align:center;">
                        <h:outputText id="msg" styleClass="error" value="#{FDPrinting.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblAcno" styleClass="label" value="Account Number"/>
                        <h:inputText id="txtAcno" styleClass="input" value="#{FDPrinting.oldAcno}" maxlength="#{FDPrinting.acNoMaxLen}" style="width:100px">
                            <a4j:support action="#{FDPrinting.onLostFocus}" event="onblur" reRender="msg,newAcno" focus="txtRtNo"/>
                        </h:inputText>
                        <h:outputLabel id="newAcno" value="#{FDPrinting.newAcno}" styleClass="label"/>
                        <h:outputLabel id="lblRtNo" styleClass="label" value="Voucher No."/>
                        <h:inputText id="txtRtNo" styleClass="input" value="#{FDPrinting.rtNo}" maxlength="10" style="width:80px">
                            <a4j:support event="onblur" action="#{FDPrinting.getFDDetails}" focus="view" reRender="newAcno,viewPDF,view,msg,txtAcno,txtRtNo,stxtFaceValue,stxtCustName,stxtMatValue,stxtTdMadeDt,stxtWef,stxtMatDt,stxtPeriod,stxtIntRate,print,stxtJtName,stxtFdType,stxtAddr"/>
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <rich:panel id="fdDetails" header="FD Details">
                        <h:panelGrid id="fdGridoOne" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" styleClass="row2" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblFaceValue" styleClass="label" value="Face Value" style="font-weight:bold"/>
                            <h:outputText id="stxtFaceValue" styleClass="output" value="#{FDPrinting.faceValue}"/>
                            <h:outputLabel id="lblCustName" styleClass="label" value="Name" style="font-weight:bold"/>
                            <h:outputText id="stxtCustName" styleClass="output" value="#{FDPrinting.custName}"/>
                            <h:outputLabel id="lblMatValue" styleClass="label" value="Maturity Value" style="font-weight:bold"/>
                            <h:outputText id="stxtMatValue" styleClass="output" value="#{FDPrinting.matAmt}"/>
                        </h:panelGrid>
                        <h:panelGrid id="fdGridTwo" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblTdMadeDt" styleClass="label" value="Date" style="font-weight:bold"/>
                            <h:outputText id="stxtTdMadeDt" styleClass="output" value="#{FDPrinting.tdMadeDt}"/>
                            <h:outputLabel id="lblWef" styleClass="label" value="W.E.F." style="font-weight:bold"/>
                            <h:outputText id="stxtWef" styleClass="output" value="#{FDPrinting.fdDate}"/>
                            <h:outputLabel id="lblMatDt" styleClass="label" value="Due Date" style="font-weight:bold"/>
                            <h:outputText id="stxtMatDt" styleClass="output" value="#{FDPrinting.matDate}"/>
                        </h:panelGrid>
                        <h:panelGrid id="fdGridThree" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" styleClass="row2" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblPeriod" styleClass="label" value="Period" style="font-weight:bold"/>
                            <h:outputText id="stxtPeriod" styleClass="output" value="#{FDPrinting.period}"/>
                            <h:outputLabel id="lblIntRate" styleClass="label" value="Int. Rate" style="font-weight:bold"/>
                            <h:outputText id="stxtIntRate" styleClass="output" value="#{FDPrinting.roi}"/>
                            <h:outputLabel id="lblFdType" styleClass="label" value="Account Type" style="font-weight:bold"/>
                            <h:outputText id="stxtFdType" styleClass="output" value="#{FDPrinting.acType}"/>                            
                        </h:panelGrid>
                        <h:panelGrid id="fdGridFour" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblJtName" styleClass="label" value="Joint Holder" style="font-weight:bold"/>
                            <h:outputText id="stxtJtName" styleClass="output" value="#{FDPrinting.jtName}"/>
                            <h:outputLabel id="lblAddr" styleClass="label" value="Address" style="font-weight:bold"/>
                            <h:outputText id="stxtAddr" styleClass="output" value="#{FDPrinting.addr}"/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </rich:panel>
                    <h:panelGrid id="btnPanel" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnGroupPanel">
                            <a4j:commandButton id="print" value="Print" action="#{FDPrinting.fdToPrint}" reRender="mainPanel,msg" disabled="#{FDPrinting.printFlag}"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{FDPrinting.refreshForm}" oncomplete="setMask();" reRender="msg,txtAcno,txtRtNo,stxtFaceValue,stxtCustName,stxtMatValue,stxtTdMadeDt,stxtWef,stxtMatDt,stxtPeriod,stxtIntRate,print,newAcno,stxtJtName,stxtFdType"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{FDPrinting.exitForm}" reRender="msg,txtAcno,txtRtNo,stxtFaceValue,stxtCustName,stxtMatValue,stxtTdMadeDt,stxtWef,stxtMatDt,stxtPeriod,stxtIntRate,print,newAcno,stxtJtName,stxtFdType"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide();" />
            <rich:modalPanel id="wait" autosized="true" width="250" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tr style="height:40px">
                            <td align="center" width="50%" colspan="2">
                                <h:outputText value="Processing Wait Please..."/>
                            </td>
                        </tr>
                    </table>
                </h:form>

            </rich:modalPanel>
        </body>
    </html>
</f:view>
