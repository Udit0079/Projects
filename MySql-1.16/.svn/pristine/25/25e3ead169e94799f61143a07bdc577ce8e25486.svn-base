<%-- 
    Document   : tdsoncashwithdrawal
    Created on : 12 Sep, 2019, 3:28:54 PM
    Author     : root
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
            <title>TDS On Cash Withdrawal Report</title>
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
            <h:form id="tdsoncashwithdrawal">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdsOnCashWithdrawal.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="TDS On Cash Withdrawal Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdsOnCashWithdrawal.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TdsOnCashWithdrawal.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="8" columnClasses="col3,col3,col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{TdsOnCashWithdrawal.branchcode}" styleClass="ddlist">
                            <f:selectItems value="#{TdsOnCashWithdrawal.branchcodeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">  
                            <h:inputText id="fromDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TdsOnCashWithdrawal.fromDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel value="To Date" styleClass="label"/>
                            <h:panelGroup styleClass="label">
                                <h:inputText id="toDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TdsOnCashWithdrawal.toDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                        </h:panelGrid>
                       <h:panelGrid id="panel2" columns="8" columnClasses="col3,col3,col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                            <h:outputText id="reptypetxt" value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="rType" size="1" value="#{TdsOnCashWithdrawal.reptype}" styleClass="ddlist">
                            <f:selectItems value="#{TdsOnCashWithdrawal.reporttype}" />
                            <a4j:support event="onblur" action="#{TdsOnCashWithdrawal.onblurreptype}" oncomplete="setMask();" reRender = "repopttxt,rOpt,btnExcel"/>
                        </h:selectOneListbox>  
                            <h:outputText id="repopttxt" value="Report Option" styleClass="label" style="display:#{TdsOnCashWithdrawal.reportOptdisplay}"/>
                        <h:selectOneListbox id="rOpt" size="1" value="#{TdsOnCashWithdrawal.repopt}" styleClass="ddlist" style="display:#{TdsOnCashWithdrawal.reportOptdisplay}">
                            <f:selectItems value="#{TdsOnCashWithdrawal.reportoption}" />
                        </h:selectOneListbox> 
                         <h:outputLabel id="blank1" />
                         <h:outputLabel id="blank2" />
                        </h:panelGrid>                               
                        <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">                                                       
                                <h:commandButton id="btnExcel"  value="Excel Download" actionListener="#{TdsOnCashWithdrawal.excelDownload}"  styleClass="color: #044F93;text-decoration: none;" style="display:#{TdsOnCashWithdrawal.displayDownloadBtn}" ></h:commandButton>     
                                <a4j:commandButton action="#{TdsOnCashWithdrawal.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="errorMsg,a1"/>   
                                <h:commandButton id="btnPDF"  value="PDF Download" action="#{TdsOnCashWithdrawal.viewPdfReport}"   styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                                <a4j:commandButton value="Refresh" action="#{TdsOnCashWithdrawal.refresh}" oncomplete="setMask()" reRender="txtToDate,a1,rType,rOpt"/>
                                <a4j:commandButton   value="Exit" action="#{TdsOnCashWithdrawal.exitAction}" oncomplete="setMask()" reRender="txtToDate,a1"/>
                            </h:panelGroup>
                        </h:panelGrid>                 
                    </h:panelGrid>
                </h:form>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                   <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                   </f:facet>
                </rich:modalPanel>               
        </body>
    </html>
</f:view>