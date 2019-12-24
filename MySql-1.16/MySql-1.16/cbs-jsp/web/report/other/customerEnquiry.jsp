<%-- 
    Document   : customerEnquiry
    Created on : Feb 13, 2012, 12:32:10 PM
    Author     : admin
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
            <title>Customer Enquiry </title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="customerenquiry">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{customerEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Customer Enquiry Report "/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{customerEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{customerEnquiry.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="7" id="brnPanel" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left;width:100%" >
                        <h:outputText />
                        <h:outputText value="Branch Code" styleClass="label"/>
                        <h:selectOneListbox id="Branch" value="#{customerEnquiry.branch}"  styleClass="ddlist" size="1">
                            <f:selectItems value="#{customerEnquiry.branchList}" /> 
                        </h:selectOneListbox> 
                        <h:outputText value="Customer Id wise" styleClass="label"/>
                        <h:inputText id="bytxtcustomerid" value="#{customerEnquiry.customerId}" size="10">
                            <a4j:support action="#{customerEnquiry.disabledfields}" event="onblur" reRender="mainPanel"/>
                        </h:inputText>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columns="7" id="firstPanel" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left;width:100%" >
                        <h:outputText />
                        <h:outputText value="Gender wise" styleClass="label"/>
                        <h:selectOneListbox id="gender" value="#{customerEnquiry.gender}"  styleClass="ddlist" size="1" disabled="#{customerEnquiry.fielddisable}">
                            <f:selectItems value="#{customerEnquiry.genderlist}" /> 
                        </h:selectOneListbox>
                        <h:outputText value="Date of birth wise" styleClass="label"  />
                        <h:panelGroup id="p">
                            <h:selectOneListbox value="#{customerEnquiry.dd}"   styleClass="ddlist" size="1" disabled="#{customerEnquiry.fielddisable}">
                                <f:selectItems value="#{customerEnquiry.ddList}" />
                            </h:selectOneListbox>
                            <h:selectOneListbox value="#{customerEnquiry.mm}"   styleClass="ddlist" size="1" disabled="#{customerEnquiry.fielddisable}">
                                <f:selectItems value="#{customerEnquiry.mmList}" />
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputText/>
                    </h:panelGrid>
                    
                    <h:panelGrid id="thirdPanel" columns="7" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left;width:100%" >
                        <h:outputText/>
                        <h:outputText value="Area wise" styleClass="label" />
                        <h:inputText value="#{customerEnquiry.area}" size="10" disabled="#{customerEnquiry.fielddisable}"></h:inputText>

                        <h:outputText value="Income wise" styleClass="label"  />
                        <h:inputText id="income"  value="#{customerEnquiry.income}" size="10" disabled="#{customerEnquiry.fielddisable}"/>
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="fivePanel" columns="7" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left;width:100%">
                        <h:outputText />
                        <h:outputText  id="age" value="Age wise  :    From Age" styleClass="label"/>
                        <h:inputText id="fromAge" value="#{customerEnquiry.fromAge}" size="10" disabled="#{customerEnquiry.fielddisable}"/>
                        <h:outputLabel value="To Age" styleClass="label"/>
                        <h:inputText id="toage"  value="#{customerEnquiry.toAge}" size="10" disabled="#{customerEnquiry.fielddisable}"/>
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="fourPanel" columns="7" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left;width:100%" >
                        <h:outputText />
                        <h:outputText value="Account Type" styleClass="label" />
                        <h:selectOneListbox id="acType" value="#{customerEnquiry.acType}"  styleClass="ddlist" size="1" disabled="#{customerEnquiry.fielddisable}">
                            <f:selectItems value="#{customerEnquiry.acTypeList}" /> 
                        </h:selectOneListbox>
                        <h:outputText value="Occupation wise" styleClass="label" />
                        <h:selectOneListbox id="occplist" value="#{customerEnquiry.ocupation}"   styleClass="ddlist" size="1" disabled="#{customerEnquiry.fielddisable}">
                            <f:selectItems value="#{customerEnquiry.ocupationList}" />
                        </h:selectOneListbox>
                        <h:outputText />
                    </h:panelGrid>                    
                    <h:panelGrid id="secondPanel" columns="7" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left;width:100%" >
                        <h:outputText />                        
                        <h:outputText  id="Balancefrom" value="Balance : From " styleClass="label"/>
                        <h:inputText id="balFrom" value="#{customerEnquiry.balFrom}" size="10" disabled="#{customerEnquiry.fielddisable}"/>
                        <h:outputLabel id="BalanceTo" value="Balance : To" styleClass="label"/>
                        <h:inputText id="balTo"  value="#{customerEnquiry.balTo}" size="10" disabled="#{customerEnquiry.fielddisable}"/>
                        <h:outputText/>
                    </h:panelGrid>
                   
                    <h:panelGrid id="eightPanel" columns="7" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left;width:100%">
                        <h:outputText />
                        <h:outputText value="Average Balance wise" styleClass="label" />
                        <h:inputText id="AvgBalance" value="#{customerEnquiry.avgBalance}" size="10" disabled="#{customerEnquiry.fielddisable}"><f:convertNumber pattern="####.##" /></h:inputText>
                        <h:outputText value="Customer Status" styleClass="label" />
                        <h:selectOneListbox id="custStatusList" value="#{customerEnquiry.custStatus}"   styleClass="ddlist" size="1" disabled="#{customerEnquiry.fielddisable}">
                            <f:selectItems value="#{customerEnquiry.custStatusList}" />
                        </h:selectOneListbox>
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid  columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  value="Print Report" type="submit"action="#{customerEnquiry.viewReport}" reRender="errorPanel"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{customerEnquiry.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton value="Refresh" action="#{customerEnquiry.refresh}" reRender="errorPanel,brnPanel,firstPanel,thirdPanel,fivePanel,fourPanel,secondPanel" />
                            <a4j:commandButton   value="Exit" action="case1" reRender="a1"/>
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
