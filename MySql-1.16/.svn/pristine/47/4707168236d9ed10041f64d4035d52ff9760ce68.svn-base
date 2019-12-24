<%-- 
    Document   : finalBalRep
    Created on : 13 Dec, 2011, 4:51:32 PM
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
            <title>Final Balance Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calDate").mask("99/99/9999");                  
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel for="stxtDate"  value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{FinalBalanceReport.stxtDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Final Balance Report" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel for="stxtUser"  value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{FinalBalanceReport.stxtUser}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a14" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{FinalBalanceReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" width="100%" columnClasses="col9">                       
                         <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a88" styleClass="row2" style="height:30px;" width="100%">
                            <h:outputText value="Branch Wise" styleClass="label"/>
                            <h:selectOneListbox id="branch" size="1" value="#{FinalBalanceReport.branchCode}" styleClass="ddlist">
                                <f:selectItems value="#{FinalBalanceReport.branchCodeList}" />
                                <a4j:support action="#{FinalBalanceReport.checkExtCounter}" event="onblur" reRender="exLabel,exddl,lblMsg,excunter" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                       <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a888" styleClass="row2" style="height:30px;" width="100%">                          
                            <h:outputText value="Type" styleClass="label"/>
                            <h:selectOneListbox id="type" size="1" value="#{FinalBalanceReport.type}" styleClass="ddlist">
                                <f:selectItems value="#{FinalBalanceReport.typeList}" />
                                 <%--a4j:support action="#{FinalBalanceReport.ddStatusProcessValueChange}" event="onblur" reRender="ddAcType,lblAcType" focus="ddAcType" oncomplete="setMask();"/--%>
                            </h:selectOneListbox> 
                            <h:outputText/>
                        </h:panelGrid>                       
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a5" styleClass="row1" style="height:30px;" width="100%">
                            <h:outputLabel id="label4" value="Status : " styleClass="label"/>
                            <h:selectOneListbox id="ddStatus" styleClass="ddlist" value="#{FinalBalanceReport.ddStatus}" size="1" style="width:102px">
                                <f:selectItems value="#{FinalBalanceReport.ddStatusList}"/>
                                <a4j:support action="#{FinalBalanceReport.ddStatusProcessValueChange}" event="onblur" reRender="ddAcType,lblAcType" focus="ddAcType" oncomplete="setMask();"/>
                            </h:selectOneListbox> 
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a10" styleClass="row1" style="height:30px;" width="100%">
                            <h:outputLabel id="label5" value="A/c. Type : " styleClass="label"/>
                            <h:selectOneListbox id="ddAcType" styleClass="ddlist" value="#{FinalBalanceReport.ddAcType}" size="1" style="width: 70px" disabled="#{FinalBalanceReport.ddAcTypeDisableFlag}">
                                <f:selectItems value="#{FinalBalanceReport.ddAcTypeList}"/>
                                <a4j:support action="#{FinalBalanceReport.ddAcTypeProcessValueChange}" event="onblur" reRender="lblAcType,agentLbl,ddAgent" focus="calDate" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="#{FinalBalanceReport.lblAcType}" id="lblAcType" style="font-size: 12px;color:purple;"/>
                        </h:panelGrid>            
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a8" styleClass="row2" style="height:30px;" width="100%">
                            <h:outputLabel id="label9" value="As On : " styleClass="label"/>
                            <h:inputText id="calDate" styleClass="input calDate" value="#{FinalBalanceReport.calDate}" size="8">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a12" styleClass="row2" style="height:30px;" width="100%">
                            <h:outputLabel id="agentLbl" value="Agent Code : " styleClass="label" style="display:#{FinalBalanceReport.displayAgCode}"/>
                            <h:selectOneListbox id="ddAgent" styleClass="ddlist" value="#{FinalBalanceReport.agentCode}" size="1" style="width:70px;display:#{FinalBalanceReport.displayAgCode}" >
                                <f:selectItems value="#{FinalBalanceReport.agentCodeList}"/>
                            </h:selectOneListbox>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a7" styleClass="row1" style="height:30px;" width="100%">
                            <h:outputLabel id="label6" styleClass="label" value="From A/c. No. : "/>
                            <h:inputText value="#{FinalBalanceReport.txtFromAcno}" id="txtFromAcno" styleClass="input" onkeyup="this.value = this.value.toUpperCase()" style="width:80px"/>
                            <h:outputLabel value="#{FinalBalanceReport.lblFromAc}" id="lblFromAc" style="font-size: 12px;color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="a11" styleClass="row1" style="height:30px;" width="100%">
                            <h:outputLabel id="label7" styleClass="label" value="To A/c. No. : "/>
                            <h:inputText value="#{FinalBalanceReport.txtToAcno}" id="txtToAcno" onkeyup="this.value = this.value.toUpperCase()"
                                         styleClass="input"  style="width:80px">
                                <a4j:support action="#{FinalBalanceReport.txtToAcnoProcessValueChange}" event="onblur" reRender="lblFromAc,lblToAc,lblMsg" oncomplete="setMask();" focus="btnHtml"/>
                            </h:inputText>
                            <h:outputLabel value="#{FinalBalanceReport.lblToAc}" id="lblToAc" style="font-size: 12px;color:purple;"/>
                        </h:panelGrid>
                         <h:panelGrid columnClasses="col2,col2,col11" columns="3" id="excunter" styleClass="row2" style="height:30px;display:#{FinalBalanceReport.displayExCtr}" width="100%">
                           <h:outputLabel id="exLabel" value="Ext Counter Enclude" styleClass="label" style="display:#{FinalBalanceReport.displayExCtr}"/>
                        <h:selectOneListbox id="exddl" size="1" value="#{FinalBalanceReport.exCounter}" styleClass="ddlist" style="display:#{FinalBalanceReport.displayExCtr}">
                            <f:selectItems value="#{FinalBalanceReport.exCounterList}" />
                        </h:selectOneListbox> 
                           <h:outputText/>
                       </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{FinalBalanceReport.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/> 
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{FinalBalanceReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{FinalBalanceReport.refreshForm}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton action="#{FinalBalanceReport.btnExitAction}" id="btnExit" value="Exit" reRender="lblMsg,ddStatus,ddAcType,txtFromAcno,txtToAcno,lblFromAc,lblToAc,lblAcType,calDate"/>
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

