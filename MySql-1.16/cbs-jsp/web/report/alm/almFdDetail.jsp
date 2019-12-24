<%-- 
    Document   : almFdDetail
    Created on : May 28, 2014, 6:03:57 PM
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
            <title>Bucket Wise Detail Report</title>
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
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AlmFdDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Bucket Wise Detail Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AlmFdDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{AlmFdDetail.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{AlmFdDetail.branchCode}" styleClass="ddlist"style="width: 110px">
                            <f:selectItems value="#{AlmFdDetail.branchCodeList}" />
                        </h:selectOneListbox>   
                        <h:outputText value="A/c Nature"styleClass="label" />
                        <h:selectOneListbox id="ddacNature" size="1" value="#{AlmFdDetail.acnoNature}" styleClass="ddlist" style="width: 70px" >
                            <f:selectItems value="#{AlmFdDetail.acnoNatureList}"/>
                            <a4j:support id="idnature" event="onblur" action="#{AlmFdDetail.initData}" reRender="ddacType,ddDlCase"/>
                        </h:selectOneListbox>   
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="7" columnClasses="col3,col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%">
                        <h:outputText value="Account Type"styleClass="label" />
                        <h:selectOneListbox id="ddacType" size="1" value="#{AlmFdDetail.acctType}" styleClass="ddlist" style="width: 70px" >
                            <f:selectItems value="#{AlmFdDetail.acctTypeList}"/>
                        </h:selectOneListbox> 
                        
                        <h:outputLabel id="lblBucketBase" styleClass="label"  value="Alm Base"/>
                            <h:panelGroup>
                            <h:selectOneListbox id="ddaBucketBase" styleClass="ddlist" size="1" style="width: 110px" value="#{AlmFdDetail.bucketBase}">
                                <f:selectItems value="#{AlmFdDetail.bucketBaseList}"/>
                                <a4j:support id="idBucket" event="onblur" action="#{AlmFdDetail.getAllBucketNo}" reRender="ddacBkt,ddFrom,ddTo,txtFrom,txtTo"/>
                            </h:selectOneListbox> 
                            <h:selectOneListbox id="ddDlCase" styleClass="ddlist" size="1" style="width: 110px" value="#{AlmFdDetail.dlCase}" disabled="#{AlmFdDetail.dlCaseBool}">
                                <f:selectItems value="#{AlmFdDetail.dlCaseList}"/>                            
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">

                        <h:outputLabel id="lblacbkt" styleClass="label"  value="Bucket No"/>
                        <h:selectOneListbox id="ddacBkt" styleClass="ddlist" size="1" style="width: 110px" value="#{AlmFdDetail.bucketNo}" disabled="#{AlmFdDetail.bucketBool}">
                            <f:selectItems value="#{AlmFdDetail.bucketNoList}"/>
                        </h:selectOneListbox> 

                        <h:outputText value="Till Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{AlmFdDetail.asOnDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>

                    </h:panelGrid>
                            
                    <h:panelGrid id="panel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblFrom" styleClass="label"  value="From"></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddFrom" styleClass="ddlist" size="1" style="width: 70px" value="#{AlmFdDetail.fromType}" disabled="#{AlmFdDetail.fromToBool}">
                                    <f:selectItems value="#{AlmFdDetail.fromTypeList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtFrom" styleClass="input" value="#{AlmFdDetail.fromIo}" maxlength="4"size="5" disabled="#{AlmFdDetail.fromToBool}"/>
                            </h:panelGroup>

                        <h:outputLabel id="lblTo" styleClass="label"  value="To"></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddTo" styleClass="ddlist" size="1" style="width: 70px" value="#{AlmFdDetail.toType}" disabled="#{AlmFdDetail.fromToBool}">
                                    <f:selectItems value="#{AlmFdDetail.toTypeList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtTo" styleClass="input" value="#{AlmFdDetail.toIo}" maxlength="4" size="5" disabled="#{AlmFdDetail.fromToBool}"/>
                            </h:panelGroup>
                    </h:panelGrid> 

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{AlmFdDetail.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{AlmFdDetail.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AlmFdDetail.refresh}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{AlmFdDetail.exitAction}" reRender="errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
