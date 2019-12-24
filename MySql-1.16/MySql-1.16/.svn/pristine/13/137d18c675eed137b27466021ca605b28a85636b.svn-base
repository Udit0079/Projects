<%-- 
    Document   : AtmChargePosting
    Created on : Sep 3, 2014, 3:52:44 PM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>ATM Charge Posting</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <a4j:region id="btnAjaxGrid">
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{AtmChargePosting.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="label2" styleClass="headerLabel" value="ATM Charge Posting"/>
                            <h:panelGroup id="groupPanel3" layout="block">
                                <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{AtmChargePosting.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="inputPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblBranchWise" styleClass="label" value="Branch : "><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:70px;" value="#{AtmChargePosting.branchOpt}">
                                    <f:selectItems value="#{AtmChargePosting.branchOptionList}"/>
                                    <a4j:support  action="#{AtmChargePosting.setBranchOptions}" event="onblur" focus="cal"
                                                  reRender="msg,txtFrDt,txtToDt,stxtTotCharge,stxtCrHead,grid,cal,save"/>
                                </h:selectOneListbox>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="inputDatePanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" style="width:100%;text-align:left;">
                                <h:outputLabel id="lblFromDate" styleClass="label" value="From Date"/>
                                <h:inputText id="txtFrDt" styleClass="input issueDt" value="#{AtmChargePosting.frDt}" size="10" disabled="#{AtmChargePosting.dtEnable}"/>
                                <h:outputLabel id="lblToDate" styleClass="label" value="To Date"/>
                                <h:inputText id="txtToDt" styleClass="input issueDt" value="#{AtmChargePosting.toDt}" size="10" disabled="#{AtmChargePosting.dtEnable}"/>
                            </h:panelGrid>
                            <h:panelGrid id="inputLabelPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                                <h:outputLabel id="lblTotCharge" styleClass="label" value="Total Charge"/>
                                <h:outputText id="stxtTotCharge" styleClass="output" value="#{AtmChargePosting.totalChg}"/>
                                <h:outputLabel id="lblCrHead" styleClass="label" value="Credit Head"/>
                                <h:outputText id="stxtCrHead" styleClass="output" value="#{AtmChargePosting.crHead}"/>
                            </h:panelGrid>
                            <h:panelGrid id="datatablepanelGrid" style="width:100%"> 
                                <rich:dataTable id="grid" value="#{AtmChargePosting.chargePostingList}" var="dataItem" rows="10" width="100%" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" rowClasses="gridrow1,gridrow2">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width=""><h:outputText value="S.No." /></rich:column>
                                            <rich:column><h:outputText value="Account no." /></rich:column>
                                            <rich:column><h:outputText value="No of Cards"/></rich:column>
                                            <rich:column><h:outputText value="Charges"/></rich:column>                                        
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.noOfMsg}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.charges}"/></rich:column>                                
                                </rich:dataTable>
                                <rich:datascroller for="grid" maxPages="50" style="text-align:left"/>
                            </h:panelGrid> 
                            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation Dialog"/>
                                </f:facet>
                                <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are you sure to post charges ?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnSaveYes" ajaxSingle="true" action="#{AtmChargePosting.postAction}" onclick="#{rich:component('savePanel')}.hide();
                                                                   if(#{AtmChargePosting.calcFlag1==true}){#{rich:component('popUpRepPanel')}.show();}
                                                                   else if(#{AtmChargePosting.calcFlag1==false}){#{rich:component('popUpRepPanel')}.hide();}" 
                                                                   reRender="msg,popUpRepPanel,save,datatablepanelGrid,inputDatePanel,inputLabelPanel"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <h:panelGrid columns="2" columnClasses="col10,col11" style="text-align:left;width:100%" width="100%" styleClass="footer">
                            <h:outputText id="msg" styleClass="error" value="#{AtmChargePosting.msg}"/>
                            <h:panelGroup id="btnGroupPanel" >
                                <a4j:commandButton id="cal" value="Calculate" actionListener="#{AtmChargePosting.calculateAction}" reRender="datatablepanelGrid,grid,stxtCrHead,stxtTotCharge,cal,save" disabled="#{AtmChargePosting.btnCalFlag}"/>
                                <a4j:commandButton id="save" value="Post" oncomplete="#{rich:component('savePanel')}.show();" reRender="savePanel" disabled="#{AtmChargePosting.btnPostFlag}"/>
                                <a4j:commandButton id="refresh" value="Refresh" action="#{AtmChargePosting.refreshForm}"  reRender="mainPanel" focus="rDDType" oncomplete="setMask();"/>
                                <a4j:commandButton id="poBtnCancel" value="Exit"  action="#{AtmChargePosting.exitForm}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:region>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnAjaxGrid"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="ATM CHARGE REPORT" style="text-align:center;"/>
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
                                <a4j:include viewId="#{AtmChargePosting.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>                
        </body>
    </html>
</f:view>