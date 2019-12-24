<%-- 
    Document   : npaAccountDetail
    Created on : Mar 7, 2014, 6:56:21 PM
    Author     : Athar Reza
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
            <title>Npa Account Detail Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="npaReport">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NpaAccountDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Npa Account Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NpaAccountDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{NpaAccountDetail.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{NpaAccountDetail.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{NpaAccountDetail.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputText value="Type of NPA" styleClass="label"/>
                        <h:selectOneListbox id="ddTypeOfNpa" size="1" value="#{NpaAccountDetail.typeOfNpa}" styleClass="ddlist">
                            <f:selectItems value="#{NpaAccountDetail.typeOfNpaList}" />
                            <a4j:support event="onblur" action="#{NpaAccountDetail.blurTypeOfNpaList}"  reRender="ddlAcNatureHeader,ddlAccountTypeTypeddl,errorMsg" oncomplete="setMask();" />
                        </h:selectOneListbox> 
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="8" columnClasses="col3,col3,col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="A/c Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddlAcNatureHeader" value="#{NpaAccountDetail.acNature}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{NpaAccountDetail.acNatureList}"/>
                            <a4j:support event="onblur" action="#{NpaAccountDetail.blurAcctNature}"  reRender="ddlAccountTypeTypeddl,errorMsg"  oncomplete="setMask();"/>
                        </h:selectOneListbox>  
                        <h:outputText value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccountTypeTypeddl" size="1" value="#{NpaAccountDetail.acctType}" styleClass="ddlist">
                            <f:selectItems value="#{NpaAccountDetail.acctTypeList}" />
                        </h:selectOneListbox>                       
                        <h:outputText value="Till Date" styleClass="label"/>
                        <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="width:70px;" value="#{NpaAccountDetail.tillDate}"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{NpaAccountDetail.viewReport}" disabled="#{NpaAccountDetail.disableBntPrint}" oncomplete="if(#{NpaAccountDetail.reportFlag}){#{rich:component('popUpRepPanel')}.show();}else{#{rich:component('popUpRepPanel')}.hide();};setMask();" reRender="a1,errorMsg,popUpRepPanel,btnPost"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{NpaAccountDetail.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <h:commandButton id="btnExcelDownload"  value="Excel Download" action="#{NpaAccountDetail.excelDownload}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <%--<a4j:commandButton id="btnPost" value="Npa Mark" action="#{NpaAccountDetail.npaMarkPost}" disabled="#{NpaAccountDetail.disableBntPost}" reRender="a1,errorMsg"/>--%>
                            <a4j:commandButton id="btnPost" value="Npa Mark" oncomplete="#{rich:component('savePanel')}.show();setMask();" disabled="#{NpaAccountDetail.disableBntPost}"  reRender="a1,errorMsg,savePanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{NpaAccountDetail.refresh}" reRender="a1,errorMsg,txtFrmDate" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{NpaAccountDetail.exitAction}" reRender="errorMsg" oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>                    
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>             
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>  
                <a4j:region id="btnActionGrid">                    
                    <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                        <f:facet name="header">
                            <h:panelGrid style="width:100%;text-align:center;">
                                <h:outputText value="Npa Account Status" style="text-align:center;"/>
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
                                    <a4j:include viewId="#{NpaAccountDetail.viewID}"/>
                                </td>

                            </tr>
                        </tbody>
                    </table>
                </rich:modalPanel>  
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to mark npa?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnSaveYes" action="#{NpaAccountDetail.npaMarkPost}" onclick="#{rich:component('savePanel')}.hide();setMask();" reRender="errorMsg,btnPost"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"  oncomplete="setMask();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>             
                    </rich:modalPanel>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>
