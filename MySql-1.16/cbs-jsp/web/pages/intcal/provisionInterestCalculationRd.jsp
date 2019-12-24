<%-- 
    Document   : ProvisionInterestCalculationRd
    Created on : May 15, 2010, 10:45:54 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Rd Interest Provisioning</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ProvisionInterestCalculationRd.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="RD Interest Provisioning"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ProvisionInterestCalculationRd.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{ProvisionInterestCalculationRd.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label1" styleClass="label" value="A/C Type"><font class="error">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddaccountType" styleClass="ddlist" size="1" style="width: 50px" tabindex="1" value="#{ProvisionInterestCalculationRd.acctCode}">
                                <f:selectItems value="#{ProvisionInterestCalculationRd.acctTypeOption}"/>
                                <a4j:support action="#{ProvisionInterestCalculationRd.getInterestGLHeads}"  event="onblur" focus="ddIntOption"
                                             reRender="stxtAccToBeCredited,stxtAccToBeDebited" limitToList="true" />
                            </h:selectOneListbox>
                            <h:outputLabel id="intOptLabel" styleClass="label" value="Interest Option"><font class="error">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddIntOption" styleClass="ddlist" size="1" style="width: 75px" tabindex="1" value="#{ProvisionInterestCalculationRd.interestOpt}">
                                <f:selectItems value="#{ProvisionInterestCalculationRd.intOptionList}"/>
                                <a4j:support action="#{ProvisionInterestCalculationRd.setInterestOption}"  event="onblur" focus="ddQuarterEnding1"
                                             reRender="ddQuarterEnding1,stxtAccToBeDebited,label13" limitToList="true" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                            
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanela" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label13" styleClass="label" value="#{ProvisionInterestCalculationRd.labelForMonth}" ><font class="error">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddQuarterEnding1" styleClass="ddlist" size="1" style="width: 90px" value="#{ProvisionInterestCalculationRd.forTheMonth}" tabindex="2">
                                <f:selectItems value="#{ProvisionInterestCalculationRd.forTheMonthList}"/>
                            </h:selectOneListbox>
                            
                            <h:outputLabel id="yearLabel" styleClass="label" value="Year" ><font class="error">*</font></h:outputLabel>
                            <h:selectOneListbox id="finYearList" styleClass="ddlist" size="1" style="width: 60px" value="#{ProvisionInterestCalculationRd.year}" tabindex="3">
                                <f:selectItems value="#{ProvisionInterestCalculationRd.quarterEndOption}"/>
                            </h:selectOneListbox>

                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label12" styleClass="label" value="A/C To Be Credited"/>
                            <h:outputText id="stxtAccToBeCredited" styleClass="output" value="#{ProvisionInterestCalculationRd.acToBeCredited}"/>
                            <h:outputLabel id="label9" styleClass="label" value="Cr Amt."/>
                            <h:outputText id="stxtCrAmt" styleClass="output" value="#{ProvisionInterestCalculationRd.crAmount}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel17" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label15" styleClass="label" value="A/C To Be Debited"/>
                            <h:outputText id="stxtAccToBeDebited" styleClass="output" value="#{ProvisionInterestCalculationRd.acToBeDebited}" />
                            <h:outputLabel id="labelp" styleClass="label" value="Dr Amt."/>
                            <h:outputText id="stxtDrAmt" styleClass="output" value="#{ProvisionInterestCalculationRd.crAmount}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCalculate" value="Calculate" action="#{ProvisionInterestCalculationRd.calculate}" reRender="ddForTheMonthOf,ddQuarterEnding1,stxtDrAmt,stxtMsg,stxtCrAmt,gridPanele,popUpRepPanel" tabindex="5" 
                                               oncomplete="if(#{ProvisionInterestCalculationRd.reportFlag}){#{rich:component('popUpRepPanel')}.show();}else{#{rich:component('popUpRepPanel')}.hide();}"/>

                            <a4j:commandButton id="btnPost" value="Post" action="#{ProvisionInterestCalculationRd.calculationPost}" reRender="ddForTheMonthOf,ddQuarterEnding1,stxtDrAmt,stxtMsg,stxtCrAmt,gridPanele" tabindex="6"/>
                             <a4j:commandButton id="btnReset" value="Refresh" action="#{ProvisionInterestCalculationRd.clearText}" reRender="stxtAccToBeCredited,stxtAccToBeDebited,ddForTheMonthOf,ddQuarterEnding1,stxtDrAmt,stxtMsg,stxtCrAmt,gridPanele" tabindex="8" focus="ddaccountType"/>   
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ProvisionInterestCalculationRd.exitFrm}" tabindex="7" reRender="ddForTheMonthOf,ddQuarterEnding1,stxtDrAmt,stxtMsg,stxtCrAmt" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Rd Provision Interest" style="text-align:center;"/>
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
                                <a4j:include viewId="#{ProvisionInterestCalculationRd.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
