<%-- 
    Document   : fdrupdation
    Created on : Jul 12, 2012, 6:06:10 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>FDR Updation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{fdrUpdation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="FDR Updation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{fdrUpdation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{fdrUpdation.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="secTypeGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtCtrlNo" value="#{fdrUpdation.ctrlNo}" size="10" styleClass="input">
                            <a4j:support action="#{fdrUpdation.onBlurCtrlNo}" event="onblur" reRender="message,ddSecType,txtTdDetails,txtFdrNo,ddBank,ddBranch,chkDt,txtdays,txtMonth,txtYears,txtMatDt,txtRoi,ddIntOpt,txtFaceVal,stxtInterest,stxtBookValue,btnProcess"/>
                        </h:inputText>
                        <h:outputLabel id="lblSecType" styleClass="label" value="Security Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSecType" style="width: 120px" styleClass="ddlist"  value="#{fdrUpdation.secType}" size="1">
                            <f:selectItems value="#{fdrUpdation.secTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTdDetails" styleClass="label" value="TD Details"></h:outputLabel>
                        <h:inputText id="txtTdDetails" value="#{fdrUpdation.tdDetails}" size="10" styleClass="input"/>
                    </h:panelGrid>

                    <h:panelGrid id="bankGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFdrNo" styleClass="label" value="FDR No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFdrNo" value="#{fdrUpdation.fdrNo}" size="10" styleClass="input">
                            <a4j:support action="#{fdrUpdation.onBlurFdrNo}" event="onblur" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblBank" styleClass="label" value="Bank"></h:outputLabel>
                        <h:selectOneListbox id="ddBank" style="width: 120px" styleClass="ddlist"  value="#{fdrUpdation.bank}" size="1" disabled="true">
                            <f:selectItems value="#{fdrUpdation.bankList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" style="width: 120px" styleClass="ddlist"  value="#{fdrUpdation.branch}" size="1">
                            <f:selectItems value="#{fdrUpdation.branchList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid id="periodGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblPurDt" styleClass="label" value="Purchase Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="chkDt" value="#{fdrUpdation.purDate}" size="10" styleClass="input chkDt">
                            <a4j:support action="#{fdrUpdation.onBlurPurDate}" event="onblur" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblPeriod" styleClass="label" value="Period"></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lbldays" styleClass="label" value="Days"></h:outputLabel>
                            <h:inputText id="txtdays" value="#{fdrUpdation.days}" size="3" styleClass="input"/>
                            <h:outputLabel id="lblMonth" styleClass="label" value="Months"></h:outputLabel>
                            <h:inputText id="txtMonth" value="#{fdrUpdation.months}" size="3" styleClass="input"/>
                            <h:outputLabel id="lblYears" styleClass="label" value="Years"></h:outputLabel>
                            <h:inputText id="txtYears" value="#{fdrUpdation.years}" size="5" styleClass="input">
                                <a4j:support event="onblur" action="#{fdrUpdation.onBlurYears}" reRender="message,txtMatDt"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblMaturityDt" styleClass="label" value="Maturity Date"></h:outputLabel>
                        <h:inputText id="txtMatDt" value="#{fdrUpdation.matDate}" size="10" styleClass="input" disabled="#{fdrUpdation.visibleMatDt}"/>
                    </h:panelGrid>

                    <h:panelGrid id="intOptGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblRoi" styleClass="label" value="Roi"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRoi" value="#{fdrUpdation.roi}" size="10" styleClass="input">
                            <a4j:support action="#{fdrUpdation.onBlurRoi}" event="onblur" reRender="message"/>
                        </h:inputText>
                        <h:outputLabel id="lblIntOpt" styleClass="label" value="Interest Option"></h:outputLabel>
                        <h:selectOneListbox id="ddIntOpt" style="width: 120px" styleClass="ddlist"  value="#{fdrUpdation.intOpt}" size="1">
                            <f:selectItems value="#{fdrUpdation.intOptList}" />
                            <a4j:support action="#{fdrUpdation.onBlurIntOpt}" event="onblur" reRender="message,stxtInterest,stxtBookValue,btnProcess"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFaceVal" styleClass="label" value="Face Value"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFaceVal" value="#{fdrUpdation.faceValue}" size="10" styleClass="input" disabled="true"/>
                    </h:panelGrid>

                    <h:panelGrid id="bookValueGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblInterest" styleClass="label" value="Interest"></h:outputLabel>
                        <h:inputText id="stxtInterest" value="#{fdrUpdation.interest}" styleClass="input">
                            <a4j:support action="#{fdrUpdation.onBlurInterest}" event="onblur" reRender="message,stxtBookValue" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblBookValue" styleClass="label" value="Maturity Value"></h:outputLabel>
                        <h:outputText id="stxtBookValue" value="#{fdrUpdation.bookvalue}" styleClass="output"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Update" oncomplete="#{rich:component('processPanel')}.show();" disabled="#{fdrUpdation.processBtnVisible}" reRender="maingrid,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{fdrUpdation.resetForm}" reRender="maingrid,processPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{fdrUpdation.exitBtnAction}" reRender="maingrid,processPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to update this entry ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{fdrUpdation.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="message,maingrid"/>
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
