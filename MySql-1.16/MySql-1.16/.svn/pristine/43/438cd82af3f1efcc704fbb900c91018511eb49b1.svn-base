<%-- 
    Document   : SchemeMaster
    Created on : 01 Aug, 2011, 05:01:27 PM
    Author     : Dinesh Pratap Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Scheme Master</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="schemeMasterForm">
                <a4j:region id="a4jRegion">
                    <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{schememaster.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblInterestMaster" styleClass="headerLabel" value="Scheme Master"/>
                            <h:panelGroup id="groupPanel3" layout="block">
                                <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtDate" styleClass="output"  value="#{schememaster.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="FramPanelMsg" style="text-align:center;height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{schememaster.message}" />
                        </h:panelGrid>
                        <h:panelGrid id="functionPanelGrid" columnClasses="col9,col9" columns="2" width="100%">
                            <h:panelGrid columnClasses="col9" columns="3" id="panelFunction" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="txtFunction" styleClass="ddlist" required="true" style="width:130px" value="#{schememaster.functionFlag}"  size="1">
                                    <f:selectItems value="#{schememaster.functionOption}"/>
                                    <a4j:support  event="onblur" action="#{schememaster.funtionAction}" focus="ddCurrencyType" reRender="lblMsg,stxtFunction" ajaxSingle="true"/>
                                </h:selectOneListbox>
                                <h:outputText id="stxtFunction" styleClass="label"  value="#{schememaster.functionValue}" style="color:green;"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="panelCurrency" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblCurrencyType" styleClass="label"  value="Currency Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddCurrencyType" styleClass="ddlist" required="true" style="width:130px" value="#{schememaster.currencyType}" size="1" disabled="#{schememaster.currencyCodeFlag }">
                                    <f:selectItems value="#{schememaster.ddCurrencyCode}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="FramPanelMain" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblSchemeCode" styleClass="label" value="Scheme Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtSchemeCode" styleClass="input" style="width:128px" value="#{schememaster.schemeCode}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                                    <a4j:support action="#{schememaster.schemeCorrespondData}" event="onblur" oncomplete="if(#{rich:element('txtFunction')}.value=='I'){#{rich:element('ddPageType')}.focus();} else{#{rich:element('ddSchemeType')}.focus();}" reRender="lblMsg,ddCurrencyType,ddSchemeType,ddPageType"/>
                                </h:inputText>
                            </h:panelGrid>                            
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="FramPanelMain1" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblSchemeType" styleClass="label" value="Scheme Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddSchemeType" styleClass="ddlist" required="true" style="width:130px" value="#{schememaster.schemeType}" size="1" disabled="#{schememaster.schemeTypeflag }">
                                    <f:selectItems value="#{schememaster.ddSchemeTypeCode}"/>
                                    <a4j:support  event="onblur" action="#{schememaster.getForms}" focus="ddPageType" reRender="lblMsg,ddPageType" ajaxSingle="true"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="2" id="pagePanelMain" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblPageType" styleClass="label"  value="Choose Form"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddPageType" styleClass="ddlist" required="true" style="width:130px" value="#{schememaster.pageType}" size="1">
                                    <f:selectItems value="#{schememaster.ddPageOption}"/>
                                    <a4j:support  event="onchange" action="#{schememaster.getPopUpForms}"
                                                  oncomplete="
                                                  if(#{schememaster.popUpFlag==true})
                                                  {
                                                  #{rich:component('popUpPanel')}.show();
                                                  }
                                                  else if(#{schememaster.popUpFlag==false})
                                                  {
                                                  #{rich:component('popUpPanel')}.hide();
                                                  }" reRender="lblMsg,popUpPanel"/>
                                </h:selectOneListbox>
                            </h:panelGrid>

                            <h:panelGrid id="extraPagePanelGrid" columnClasses="col9" style="height:30px;border:1px ridge #BED6F8;" columns="2" width="100%" styleClass="row2"/>
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="BtnPanel">
                                <a4j:commandButton id="btnSave"  value="Save"  disabled="#{schememaster.flag }" onclick="saveUpdateSchemeMaster();"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{schememaster.refresh}" reRender="mainPanel" ajaxSingle="true"/>
                                <%-- <a4j:support action="#{schememaster.refresh}" event="onclick" reRender="mainPanel" ajaxSingle="true"/>
</a4j:commandButton>--%>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{schememaster.exitForm}" reRender="mainPanel" ajaxSingle="true"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:jsFunction name="populateData" action="#{opschememaster.formData}" reRender="schemePopUpForm"/>
                    <a4j:jsFunction name="saveData" action="#{opschememaster.addModifyData}" reRender="mainPanel"/>
                    <a4j:jsFunction name="saveUpdateSchemeMaster" action="#{opschememaster.saveUpdateSchemeMaster}" reRender="mainPanel"/>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="a4jRegion"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </a4j:region>
            </a4j:form>
            <rich:modalPanel id="popUpPanel" label="Form" width="900" height="400" style="overflow:auto;" resizeable="false" onbeforeshow="populateData();">
                <f:facet name="header">
                    <h:outputText value="#{schememaster.formName}" style="text-align:center;"/>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink value="#" onclick="#{rich:component('popUpPanel')}.hide(); return false;">X</h:outputLink>
                </f:facet>
                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{schememaster.viewID}"></a4j:include>
                            </td>

                        </tr>
                        <tr>
                            <td align="center" width="50%">
                                <a4j:commandButton value="Close" id="btnClose" onclick="saveData();#{rich:component('popUpPanel')}.hide();"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>


