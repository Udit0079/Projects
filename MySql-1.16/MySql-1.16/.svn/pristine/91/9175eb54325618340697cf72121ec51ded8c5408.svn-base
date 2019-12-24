<%-- 
    Document   : cashierScroll
    Created on : 15 Dec, 2011, 5:50:19 PM
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
            <title>Cashier Scroll</title>
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
            <a4j:form id="form1">
                <a4j:keepAlive beanName="CashierScroll"/>
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{CashierScroll.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Cashier Scroll" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{CashierScroll.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{CashierScroll.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="gridPanel31" width="100%;">
                        <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6"  style="height:30px;text-align:left;" styleClass="row2" width="100%">
                            <h:outputLabel id="label4" value="Branch :" styleClass="output">
                                <font class="required" style="color:red">*</font>
                            </h:outputLabel>
                            <h:selectOneListbox id="ddRep" styleClass="ddlist" value="#{CashierScroll.dropDown1}" size="1">
                                <f:selectItems value="#{CashierScroll.dropDown1List}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="calDatelbl" value="Date :" styleClass="output">
                                <font class="required" style="color:red">*</font>
                            </h:outputLabel>
                            <h:panelGroup styleClass="label">
                                <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{CashierScroll.calDate}">
                                    <a4j:support action="#{CashierScroll.dateValidate}" event="onblur" reRender="ddOrderBy" focus="#{CashierScroll.focusId}"/>
                                </h:inputText>
                                <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel id="orderLbl" value="Order By :" styleClass="output">
                                <font class="required" style="color:red">*</font>
                            </h:outputLabel>
                            <h:selectOneListbox id="ddOrderBy" styleClass="ddlist" value="#{CashierScroll.orderBy}" size="1" style="width:120px" disabled="#{CashierScroll.disabled}">
                                <f:selectItems value="#{CashierScroll.orderByList}"/>
                            </h:selectOneListbox>    
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="af11" columnClasses="col23,col2,col23,col2,col23,col2" styleClass="row1" width="100%">
                            <h:outputLabel id="chkBoxLabel" value="Time Based Report" styleClass="output"/>
                            <h:selectBooleanCheckbox value="#{CashierScroll.checkbox1}" id="checkbox1">
                                <a4j:support action="#{CashierScroll.checkbox1ProcessValueChange}" event="onclick" 
                                             reRender="label4ss,labelHH,dropDown2,label4MM,dropDown3,dropDown4,label45,
                                             label4hh1,dropDown5,dropDown6,label4mm1,dropDown7"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="label4ss" style="display:#{CashierScroll.timeFlag};" value="From Time:" styleClass="output"/>
                            <h:panelGroup layout="block">
                                <h:outputLabel id="labelHH" style="z-index: 508;display:#{CashierScroll.timeFlag};" value="HH" styleClass="output"/>
                                <h:selectOneListbox id="dropDown2" styleClass="ddlist" value="#{CashierScroll.dropDown2}" size="1" style="display:#{CashierScroll.timeFlag}">
                                    <f:selectItems value="#{CashierScroll.dropDown2List}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="label4MM" style="z-index: 508;display:#{CashierScroll.timeFlag}" value="MM" styleClass="output"/>
                                <h:selectOneListbox id="dropDown3" styleClass="ddlist" value="#{CashierScroll.dropDown3}" size="1" style="display:#{CashierScroll.timeFlag}">
                                    <f:selectItems value="#{CashierScroll.dropDown3List}"/>
                                </h:selectOneListbox>
                                <h:selectOneListbox id="dropDown4" styleClass="ddlist" value="#{CashierScroll.dropDown4}" size="1" style="display:#{CashierScroll.timeFlag}">
                                    <f:selectItems value="#{CashierScroll.dropDown4List}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>    
                            <h:outputLabel id="label45" style="z-index: 508;display:#{CashierScroll.timeFlag}" value="To Time:" styleClass="output"/>
                            <h:panelGroup layout="block">        
                                <h:outputLabel id="label4hh1" style="z-index: 508;display:#{CashierScroll.timeFlag}" value="HH" styleClass="output"/>
                                <h:selectOneListbox id="dropDown5" styleClass="ddlist" value="#{CashierScroll.dropDown5}" size="1" style="display:#{CashierScroll.timeFlag}">
                                    <f:selectItems value="#{CashierScroll.dropDown5List}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="label4mm1" style="z-index:508;display:#{CashierScroll.timeFlag}" value="MM" styleClass="output"/>
                                <h:selectOneListbox id="dropDown6" styleClass="ddlist" value="#{CashierScroll.dropDown6}" size="1" style="display:#{CashierScroll.timeFlag}">
                                    <f:selectItems value="#{CashierScroll.dropDown6List}"/>
                                </h:selectOneListbox>
                                <h:selectOneListbox id="dropDown7" styleClass="ddlist" value="#{CashierScroll.dropDown7}" size="1" style="display:#{CashierScroll.timeFlag}">
                                    <f:selectItems value="#{CashierScroll.dropDown7List}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>    
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel9" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="a100" layout="block">
                            <h:commandButton id="btnExcelDownload"  value="Excel Download" action="#{CashierScroll.excelDownload}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{CashierScroll.btnHtmlAction}"
                                               id="btnHtml" value="Print Report" reRender="lblMsg,txtDate" oncomplete="setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{CashierScroll.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{CashierScroll.refreshForm}"
                                               id="btnRefresh" value="Refresh" reRender="lblMsg,txtDate,a1" oncomplete="setMask()"/>
                            <a4j:commandButton action="#{CashierScroll.btnExitAction}" 
                                               id="btnExit" value="Exit" reRender="label4ss,labelHH,dropDown2,label4MM,dropDown3,dropDown4,label45,
                                               label4hh1,dropDown5,dropDown6,label4mm1,dropDown7,ddRep,calDate,checkbox1,lblMsg,txtDate" oncomplete="setMask()"/>
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
