<%-- 
    Document   : H2hParameter
    Created on : 5 Jul, 2019, 2:24:05 PM
    Author     : root
--%>

<%-- 
    Document   : bankIinDetail
    Created on : 17 Jan, 2018, 3:53:42 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>H2h Parameter</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="Panel1" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{H2hParameterBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="H2h Parameter Detail"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{H2hParameterBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="messagePanel" columnClasses="col8,col8" columns="2"  style="width:100%;text-align:center;"styleClass="row1" width="100%"> 
                        <h:outputText id="stxtMessage" styleClass="error" value="#{H2hParameterBean.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel1" columnClasses="col3,col2,col3,col3,col3,col3" columns="4"  width="100%" styleClass="row2">                  
                        <h:outputLabel id="lblModule" styleClass="label" value="Module"></h:outputLabel>
                        <h:selectOneListbox id="ddmodule" style="width:200px" styleClass="ddlist"  value="#{H2hParameterBean.module}" size="1">
                            <f:selectItems value="#{H2hParameterBean.moduleList}"/>
                            <a4j:support action="#{H2hParameterBean.moduleOptionOnblur}" event="onblur" reRender="ddfield,ddneftBank,ddIsAuto,panel3" focus="ddfield"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblField" styleClass="label" value="Field"></h:outputLabel>
                        <h:selectOneListbox id="ddfield" style="width:200px" styleClass="ddlist"  value="#{H2hParameterBean.field}" size="1">
                            <f:selectItems value="#{H2hParameterBean.fieldList}"/>
                            <a4j:support action="#{H2hParameterBean.fieldOptionOnblur}" event="onblur" reRender="stxtMessage,panel2,panel3"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columnClasses="col3,col2,col3,col3,col3,col3" columns="4" style="display:#{H2hParameterBean.neftDis}"  width="100%" styleClass="row1">                  
                        <h:outputLabel id="lblneftBank" styleClass="label" value="Neft Bank Name"></h:outputLabel>
                        <h:selectOneListbox id="ddneftBank" style="width:200px" styleClass="ddlist"   value="#{H2hParameterBean.neftBankName}" size="1">
                            <f:selectItems value="#{H2hParameterBean.neftBankList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblIsauto" styleClass="label" value="Is Auto"></h:outputLabel>
                        <h:selectOneListbox id="ddIsAuto" style="width:200px" styleClass="ddlist"  value="#{H2hParameterBean.isAuto}" size="1">
                            <f:selectItems value="#{H2hParameterBean.isAutoList}"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columnClasses="col3,col2,col3,col3,col3,col3" columns="4" style="display:#{H2hParameterBean.valueDis}"  width="100%" styleClass="row2">
                        <h:outputLabel id="lblValue" styleClass="label" value="Value"></h:outputLabel>
                        <h:inputText id="valueInp" size="20" styleClass="input"  value="#{H2hParameterBean.value}" >
                        </h:inputText>
                        <h:outputLabel></h:outputLabel>
                        <h:outputText></h:outputText>
                    </h:panelGrid> 
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Update" onclick="#{rich:component('processPanel')}.show();" reRender="stxtMessage,processPanel" />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{H2hParameterBean.btnRefreshAction}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{H2hParameterBean.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="btnProcessRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure, You want to update the details?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{H2hParameterBean.updateBtnAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddTxnType')}.focus();" 
                                                           reRender="stxtMessage,ddmodule,ddfield,valueInp"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnProcessRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
