<%-- 
    Document   : alterIndicatorInfoCustIdWise
    Created on : Mar 7, 2017, 4:25:33 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Str Alert master</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="formAtm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AlertIndicatorInfoCustIdWise.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Str Alert Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AlertIndicatorInfoCustIdWise.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="message" styleClass="msg" value="#{AlertIndicatorInfoCustIdWise.message}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblcustId" styleClass="label" value="Customer Id" />  
                            <h:inputText id="stxtcustId" styleClass="input" style="width:80px;" value="#{AlertIndicatorInfoCustIdWise.custid}" maxlength="12">
                                <a4j:support action="#{AlertIndicatorInfoCustIdWise.onCustIdAction}" event="onblur" oncomplete="setMask();" reRender="message,stxtcustName,txtDob,stxtfatherName,table,btnSave"/>   
                            </h:inputText>
                            <h:outputLabel id="lblcustName" styleClass="label" value="Customer Name" />
                            <h:outputText id="stxtcustName" styleClass="output" value="#{AlertIndicatorInfoCustIdWise.custName}"/>
                            <h:outputLabel id="lblDob" styleClass="label" value="Dob" />  
                            <h:outputText id="txtDob" styleClass="output" value="#{AlertIndicatorInfoCustIdWise.dob}" >
                                <a4j:support oncomplete="setMask();"/>
                            </h:outputText>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblfatherName" styleClass="label" value="Father Name"  />
                            <h:outputText id="stxtfatherName" styleClass="output" value="#{AlertIndicatorInfoCustIdWise.fatherName}"/>
                            <h:outputLabel id="lblAlertType" styleClass="label" value="Alert Type" /> 
                            <h:selectOneListbox id="ddAlertType" styleClass="ddlist" size="1" style="width:80px;" value="#{AlertIndicatorInfoCustIdWise.alertType}">
                                <f:selectItems value="#{AlertIndicatorInfoCustIdWise.alertTypeList}"/>
                                <a4j:support action="#{AlertIndicatorInfoCustIdWise.onAlertCode}" event="onchange" oncomplete="setMask();" reRender="message,ddAlertCode,ddAlertSubCode,txnGrid,table,btnSave"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAlertCode" styleClass="label" value="Alert Code" /> 
                            <h:selectOneListbox id="ddAlertCode" styleClass="ddlist" size="1" style="width:150px;" value="#{AlertIndicatorInfoCustIdWise.alertCode}" disabled="#{AlertIndicatorInfoCustIdWise.disableAlert}">
                                <f:selectItems value="#{AlertIndicatorInfoCustIdWise.alertCodeList}"/>
                                <a4j:support action="#{AlertIndicatorInfoCustIdWise.onAlertCodeSub}" event="onchange" oncomplete="setMask();" reRender="message,ddAlertSubCode,stxtSubCode,Row2,table,btnSave"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAlertSubCode" styleClass="label" value="Alert Sub Code" /> 
                            <h:panelGroup>
                                <h:selectOneListbox id="ddAlertSubCode" styleClass="ddlist" size="1" style="width:150px;" value="#{AlertIndicatorInfoCustIdWise.alertSubCode}"disabled="#{AlertIndicatorInfoCustIdWise.disableAlert}">
                                    <f:selectItems value="#{AlertIndicatorInfoCustIdWise.alertSubCodeList}"/>
                                    <a4j:support action="#{AlertIndicatorInfoCustIdWise.onAlertCodeSubDesc}" event="onchange" oncomplete="setMask();" reRender="message,stxtSubCode,Row2,table,btnSave"/>
                                </h:selectOneListbox>
                                <h:outputText id="stxtSubCode" styleClass="output" value="#{AlertIndicatorInfoCustIdWise.subCodeDesc}" style="color:green"/>
                            </h:panelGroup>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>

                        <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;display:#{AlertIndicatorInfoCustIdWise.gridDisplay}" columnClasses="vtop">
                            <rich:dataTable  value="#{AlertIndicatorInfoCustIdWise.strAlertList}" var="item" 
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="STR Alert"/></rich:column>
                                        <rich:column breakBefore="true" width="5"><h:outputText value="SNo."/></rich:column>
                                        <rich:column width="300"><h:outputText value="Description"/></rich:column>
                                        <rich:column width="50"><h:outputText value="From [X1]"/></rich:column>
                                        <rich:column width="50"><h:outputText value="To [X1]" /></rich:column>
                                        <rich:column width="50"><h:outputText value="No. of Txn [N]" /></rich:column>
                                        <rich:column width="50"><h:outputText value="Day Or Month [day/Month]" /></rich:column>
                                        <%--rich:column width="50"><h:outputText value="#" /></rich:column--%>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                                <rich:column style="text-align:left;"><h:outputText value="#{item.description}"/></rich:column>
                                <rich:column><h:inputText value="#{item.fromAmt}"/></rich:column>
                                <rich:column><h:inputText value="#{item.toAmt}"/></rich:column>
                                <rich:column><h:inputText value="#{item.noOfTxn}"/></rich:column>
                                <rich:column><h:inputText value="#{item.dayMonth}"/></rich:column>
                                <%--rich:column><h:selectBooleanCheckbox  id="c1"  value="#{item.checkBox}">
                                        <a4j:support actionListener="#{AlertIndicatorInfoCustIdWise.checkUnCheck}" event="onclick" reRender="txnGrid"/> 
                                    </h:selectBooleanCheckbox>
                                </rich:column--%> 
                            </rich:dataTable>
                            <rich:datascroller id="deafDataScroll"align="left" for="txnList" maxPages="20"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" value="#{AlertIndicatorInfoCustIdWise.btnValue}" action="#{AlertIndicatorInfoCustIdWise.populateMessage}" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="leftPanel,mainPanel,processPanel,confirmid"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AlertIndicatorInfoCustIdWise.refreshForm}" oncomplete="setMask();" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{AlertIndicatorInfoCustIdWise.exitBtnAction}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{AlertIndicatorInfoCustIdWise.confirmationMsg}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{AlertIndicatorInfoCustIdWise.saveMasterDetail}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="message,mainPanel"/>
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
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>