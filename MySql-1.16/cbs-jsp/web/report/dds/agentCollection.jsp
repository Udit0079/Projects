<%-- 
    Document   : agentCollection
    Created on : Jun 14, 2016, 11:27:11 AM
    Author     : saurabhsipl
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
            <title>Agent Wise Collection Sheet</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".fromDate").mask("99/99/9999");                  
                    jQuery(".toDate").mask("99/99/9999"); 
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel for="date"  value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{AgentCollection.date}" id="date" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Agent Wise Collection Sheet" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel for="user"  value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{AgentCollection.user}" id="user" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a14" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{AgentCollection.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" width="100%" columnClasses="col8">                       
                         <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a3" styleClass="row2" style="height:30px;" width="100%">
                            <h:outputText value="Branch Wise" styleClass="label"/>
                            <h:selectOneListbox id="branch" size="1" value="#{AgentCollection.branchCode}" styleClass="ddlist">
                                <f:selectItems value="#{AgentCollection.branchCodeList}" />
                                 <%--a4j:support action="#{FinalBalanceReport.ddStatusProcessValueChange}" event="onblur" reRender="ddAcType,lblAcType" focus="ddAcType" oncomplete="setMask();"/--%>
                            </h:selectOneListbox> 
                            <h:outputText/>
                         </h:panelGrid>
                         <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a4" styleClass="row2" style="height:30px;" width="100%">
                                <h:outputLabel id="label11" value="Status : " styleClass="label"/>
                                <h:selectOneListbox id="ddStatus" styleClass="ddlist" value="#{AgentCollection.ddStatus}" size="1" style="width:102px">
                                    <f:selectItems value="#{AgentCollection.ddStatusList}"/>
                                    <a4j:support action="#{AgentCollection.ddStatusProcessValueChange}" event="onblur" reRender="ddAcType,lblAcType" focus="ddAcType" oncomplete="setMask();"/>
                                </h:selectOneListbox> 
                                <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a5" styleClass="row1" style="height:30px;" width="100%">
                            <h:outputLabel id="label5" value="A/c. Type : " styleClass="label"/>
                            <h:selectOneListbox id="ddAcType" styleClass="ddlist" value="#{AgentCollection.ddAcType}" size="1" style="width:140px" disabled="#{AgentCollection.ddAcTypeDisableFlag}">
                                <f:selectItems value="#{AgentCollection.ddAcTypeList}"/>
                                <a4j:support action="#{AgentCollection.ddAcTypeProcessValueChange}" event="onblur" reRender="lblAcType,agentLbl,ddAgent" focus="ddAgent" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="#{AgentCollection.lblAcType}" id="lblAcType" style="font-size: 12px;color:purple;"/>
                        </h:panelGrid>
                         <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a7" styleClass="row2" style="height:30px;" width="100%">
                            <h:outputLabel id="agentLbl" value="Agent Code : " styleClass="label" style="display:#{AgentCollection.displayAgCode}"/>
                            <h:selectOneListbox id="ddAgent" styleClass="ddlist" value="#{AgentCollection.agentCode}" size="1" style="width:150px;display:#{AgentCollection.displayAgCode}" >
                                <f:selectItems value="#{AgentCollection.agentCodeList}"/>
                            </h:selectOneListbox>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a8" styleClass="row2" style="height:30px;" width="100%">
                            <h:outputLabel id="label9" value="From Date : " styleClass="label"/>
                            <h:inputText id="fromDate" styleClass="input fromDate" value="#{AgentCollection.fromDate}" size="8">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputText/>                           
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a9" styleClass="row2" style="height:30px;" width="100%">
                            <h:outputLabel id="label10" value="To Date : " styleClass="label"/>
                            <h:inputText id="toDate" styleClass="input toDate" value="#{AgentCollection.toDate}" size="8">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputText/> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a10" styleClass="row1" style="height:30px;" width="100%">
                            <h:outputLabel id="label6" styleClass="label" value="From A/c. No. : "/>
                            <h:inputText value="#{AgentCollection.txtFromAcno}" id="txtFromAcno" styleClass="input" onkeyup="this.value = this.value.toUpperCase()" style="width:80px"/>
                            <h:outputLabel value="#{AgentCollection.lblFromAc}" id="lblFromAc" style="font-size: 12px;color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a11" styleClass="row1" style="height:30px;" width="100%">
                            <h:outputLabel id="label7" styleClass="label" value="To A/c. No. : "/>
                            <h:inputText value="#{AgentCollection.txtToAcno}" id="txtToAcno" onkeyup="this.value = this.value.toUpperCase()"
                                         styleClass="input"  style="width:80px">
                                <a4j:support action="#{AgentCollection.txtToAcnoProcessValueChange}" event="onblur" reRender="lblFromAc,lblToAc,lblMsg" oncomplete="setMask();" focus="btnHtml"/>
                            </h:inputText>
                            <h:outputLabel value="#{AgentCollection.lblToAc}" id="lblToAc" style="font-size: 12px;color:purple;"/>
                        </h:panelGrid>                                                        
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <%--<a4j:commandButton action="#{AgentCollection.btnHtmlAction}" id="btnHtml" value="Print Report"/> --%>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{AgentCollection.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AgentCollection.refreshForm}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton action="#{AgentCollection.btnExitAction}" id="btnExit" value="Exit" reRender="lblMsg,ddStatus,ddAcType,txtFromAcno,txtToAcno,lblFromAc,lblToAc,lblAcType,calDate"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>