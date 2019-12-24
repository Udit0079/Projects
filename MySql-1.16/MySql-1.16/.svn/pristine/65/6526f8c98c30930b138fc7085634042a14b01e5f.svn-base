<%-- 
    Document   : transactioncount
    Created on : Jul 15, 2015, 11:14:35 AM
    Author     : Admin
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
            <title>Transaction Summary</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{TransactionCount.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Transaction Summary" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{TransactionCount.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{TransactionCount.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8,col8" columns="1" id="gridPanel31" width="100%;">
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6"  style="height:30px;text-align:left;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblBranchWise" styleClass="label" value="Branch : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:70px;" value="#{TransactionCount.branchOption}">
                                <f:selectItems value="#{TransactionCount.branchOptionList}"/>
                            </h:selectOneListbox>

                            <h:outputLabel id="frDatelbl" value="From Date :" styleClass="output"><font class="required" style="color:red">*</font></h:outputLabel>
                            <h:panelGroup styleClass="label">
                                <h:inputText id="frtxtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{TransactionCount.frDate}"/>
                                <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>

                            <h:outputLabel id="toDatelbl" value="To Date :" styleClass="output"><font class="required" style="color:red">*</font></h:outputLabel>
                            <h:panelGroup styleClass="label">
                                <h:inputText id="totxtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{TransactionCount.toDate}"/>
                                <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>

                        </h:panelGrid>

                        <h:panelGrid columns="5" id="af11" style="height:30px;width:100%;" styleClass="row2" width="100%">
                            <h:panelGroup layout="block" style="padding-left:445px;">
                                <h:selectBooleanCheckbox value="#{TransactionCount.checkbox1}" id="checkbox1">
                                    <a4j:support action="#{TransactionCount.checkbox1ProcessValueChange}" event="onclick" 
                                                 reRender="label4ss,labelHH,dropDown2,label4MM,dropDown3,dropDown4,label45,
                                                 label4hh1,dropDown5,dropDown6,label4mm1,dropDown7"/>
                                </h:selectBooleanCheckbox>
                                <h:outputLabel id="chkBoxLabel" value="Time Based Report" styleClass="output"/>
                            </h:panelGroup>
                            <h:panelGroup id="a14" style="padding-right:160px;">
                                <h:outputLabel id="label4ss" style="display:#{TransactionCount.timeFlag};" value="From :" styleClass="output"/>
                                <h:outputLabel id="labelHH" style="z-index: 508;display:#{TransactionCount.timeFlag};" value="HH" styleClass="output"/>
                                <h:selectOneListbox id="dropDown2" styleClass="ddlist" value="#{TransactionCount.dropDown2}" size="1" style="display:#{TransactionCount.timeFlag}">
                                    <f:selectItems value="#{TransactionCount.dropDown2List}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="label4MM" style="z-index: 508;display:#{TransactionCount.timeFlag}" value="MM" styleClass="output"/>
                                <h:selectOneListbox id="dropDown3" styleClass="ddlist" value="#{TransactionCount.dropDown3}" size="1" style="display:#{TransactionCount.timeFlag}">
                                    <f:selectItems value="#{TransactionCount.dropDown3List}"/>
                                </h:selectOneListbox>
                                <h:selectOneListbox id="dropDown4" styleClass="ddlist" value="#{TransactionCount.dropDown4}" size="1" style="display:#{TransactionCount.timeFlag}">
                                    <f:selectItems value="#{TransactionCount.dropDown4List}"/>
                                </h:selectOneListbox>

                                <h:outputLabel id="label45" style="z-index: 508;display:#{TransactionCount.timeFlag}" value="To :" styleClass="output"/>
                                <h:outputLabel id="label4hh1" style="z-index: 508;display:#{TransactionCount.timeFlag}" value="HH" styleClass="output"/>
                                <h:selectOneListbox id="dropDown5" styleClass="ddlist" value="#{TransactionCount.dropDown5}" size="1" style="display:#{TransactionCount.timeFlag}">
                                    <f:selectItems value="#{TransactionCount.dropDown5List}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="label4mm1" style="z-index:508;display:#{TransactionCount.timeFlag}" value="MM" styleClass="output"/>
                                <h:selectOneListbox id="dropDown6" styleClass="ddlist" value="#{TransactionCount.dropDown6}" size="1" style="display:#{TransactionCount.timeFlag}">
                                    <f:selectItems value="#{TransactionCount.dropDown6List}"/>
                                </h:selectOneListbox>
                                <h:selectOneListbox id="dropDown7" styleClass="ddlist" value="#{TransactionCount.dropDown7}" size="1" style="display:#{TransactionCount.timeFlag}">
                                    <f:selectItems value="#{TransactionCount.dropDown7List}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>


                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel9" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="a100" layout="block">
                            <a4j:commandButton action="#{TransactionCount.btnHtmlAction}"
                                               id="btnHtml" value="Print Report" reRender="lblMsg,frtxtDate,totxtDate"  oncomplete="setMask()"/>
                             <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{TransactionCount.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;" /> 
                            <a4j:commandButton action="#{TransactionCount.refreshForm}" 
                                               id="btnRefresh" value="Refresh" reRender="label4ss,labelHH,dropDown2,label4MM,dropDown3,dropDown4,label45,
                                               label4hh1,dropDown5,dropDown6,label4mm1,dropDown7,ddRep,calDate,checkbox1,lblMsg,frtxtDate,totxtDate"  oncomplete="setMask()"/>
                            <a4j:commandButton action="#{TransactionCount.btnExitAction}" 
                                               id="btnExit" value="Exit" reRender="label4ss,labelHH,dropDown2,label4MM,dropDown3,dropDown4,label45,
                                               label4hh1,dropDown5,dropDown6,label4mm1,dropDown7,ddRep,calDate,checkbox1,lblMsg,frtxtDate,totxtDate"  oncomplete="setMask()"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
