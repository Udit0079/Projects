<%-- 
    Document   : agentCommisonPosting
    Created on : 21 Dec, 2018, 11:38:44 AM
    Author     : Rahul jain
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Agent Commission Posting</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
             <a4j:form id="AgentCommission">
            <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AgentCommisonPosting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Agent Commission Posting"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AgentCommisonPosting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{AgentCommisonPosting.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{AgentCommisonPosting.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{AgentCommisonPosting.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputText/>
                        <h:outputText/>        
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{AgentCommisonPosting.frmDt}"/>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtToDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{AgentCommisonPosting.toDt}">
                             <a4j:support action="#{AgentCommisonPosting.onblurOption}" event="onblur"
                                                   reRender="errorMsg,agentPanelGrid,taskList,popUpRepPanel" oncomplete="if(#{AgentCommisonPosting.reportFlag==true}){
                                               #{rich:component('popUpRepPanel')}.show();
                                               }
                                               else if(#{AgentCommisonPosting.reportFlag==false})
                                               {
                                               #{rich:component('popUpRepPanel')}.hide();
                                               }"/>
                            </h:inputText>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                    </h:panelGrid>
              <h:panelGrid columnClasses="col1,col2" columns="1" id="agentPanelGrid" width="100%" styleClass="row1" style="height:50%">
                        <a4j:region>
                            <rich:dataTable value ="#{AgentCommisonPosting.tableList}"
                                            rowClasses="row1, row2" id = "taskList" rows="6" columnsWidth="100" rowKeyVar="row" var ="dataItem"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Agent Commison Detail"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Branch"/> </rich:column>
                                        <rich:column><h:outputText value="Agent Code" /></rich:column>
                                        <rich:column><h:outputText value="A/c No" /></rich:column>
                                        <rich:column><h:outputText value="AGENT NAME" /></rich:column>
                                        <rich:column><h:outputText value="CASH DEPOSIT" /></rich:column>
                                        <rich:column><h:outputText value="COMMISSION" /></rich:column>
                                        <rich:column><h:outputText value="SECURITY" /></rich:column>
                                        <rich:column><h:outputText value="I-TAX" /></rich:column>
                                        <rich:column><h:outputText value="SURCHARGE" /></rich:column>
                                        <rich:column><h:outputText value="NET TAX" /></rich:column>
                                        <rich:column><h:outputText value="NET PAYBLE AMOUNT" /></rich:column>
                                     </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.branchName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.agCode}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.agName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.agBal}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.comAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.iTax}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.surCharge}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.taxAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.netAmt}"/></rich:column>
                            <f:facet name="footer">
                                    <rich:columnGroup style="background-color: #b0c4de;">

                                        <rich:column>Totals</rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{AgentCommisonPosting.totalAgBal}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{AgentCommisonPosting.totalCommision}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{AgentCommisonPosting.totalTaxAmt}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{AgentCommisonPosting.totalNetAmt}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                           </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="taskList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPost" value="Post" action="#{AgentCommisonPosting.postBtnAction}"  reRender="errorMsg"/>
                           
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AgentCommisonPosting.refresh}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{AgentCommisonPosting.exitAction}" reRender="errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                     <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('No')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Post Data?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="yes" ajaxSingle="true" action="#{AgentCommisonPosting.postBtnAction}"  
                                                                   oncomplete="#{rich:component('postPanel')}.hide();" reRender="stxtMsg,btnPost"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="No" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                 </rich:modalPanel>
                </h:panelGrid>
             </a4j:form>
   <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Agent commision Calculation" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{AgentCommisonPosting.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>