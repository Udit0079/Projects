<%-- 
    Document   : shareenquiry
    Created on : 22 Mar, 2012, 12:56:10 PM
    Author     : zeeshan waris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Share Enquiry</title>
        </head>
        <body>
            <a4j:form id="ShareEnquiry">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Share Enquiry"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{ShareEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{ShareEnquiry.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="pane1"  styleClass="row2"> 
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxfolio" value="#{ShareEnquiry.folioNoAs}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.folioNoValueChange}" 
                                             oncomplete="if(#{ShareEnquiry.folioNoAs=='true'}){
                                             #{rich:element('ddFolioNoAs')}.disabled=false;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true;
                                             #{rich:element('ddfathername')}.disabled=true; 
                                             #{rich:element('ddFolioNoAs')}.focus();}"
                                             reRender="PanelGridMain" />
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblfolionoas" styleClass="label"  value="Folio No As"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddFolioNoAs" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.ddFolio}" >
                            <f:selectItems value="#{ShareEnquiry.typeList}"/>
                            <a4j:support event="onblur" actionListener="#{ShareEnquiry.ddFolioNoValueChange}"
                                         oncomplete="if(#{ShareEnquiry.ddFolio=='0'}){
                                         #{rich:element('checkboxfolio')}.disabled=false;
                                         #{rich:element('ddFolioNoAs')}.disabled=false;
                                         #{rich:element('txtfrom')}.disabled=false;
                                         #{rich:element('txtto')}.disabled=true;
                                         #{rich:element('checkboxShareHolderName')}.disabled=false;
                                         #{rich:element('checkboxfName')}.disabled=true;
                                         #{rich:element('ddfirstname')}.disabled=true;
                                         #{rich:element('txtfirstname')}.disabled=true;                                         
                                         #{rich:element('checkboxaddwise')}.disabled=false;
                                         #{rich:element('checkboxhNo')}.disabled=true;
                                         #{rich:element('ddhouse')}.disabled=true;
                                         #{rich:element('txthouse')}.disabled=true;
                                         #{rich:element('checkboxSector')}.disabled=true;
                                         #{rich:element('ddSector')}.disabled=true;
                                         #{rich:element('txtSector')}.disabled=true; 
                                         #{rich:element('checkboxcity')}.disabled=true;
                                         #{rich:element('ddcity')}.disabled=true;
                                         #{rich:element('txtcity')}.disabled=true;
                                         #{rich:element('checkboxenterby')}.disabled=false;
                                         #{rich:element('ddenterby')}.disabled=true;
                                         #{rich:element('checkboxfathername')}.disabled=false;
                                         #{rich:element('ddfathername')}.disabled=true;
                                         #{rich:element('txtfrom')}.focus();}
                                         else if(#{ShareEnquiry.ddFolio=='1'}){
                                         #{rich:element('checkboxfolio')}.disabled=false;
                                         #{rich:element('ddFolioNoAs')}.disabled=false;
                                         #{rich:element('txtfrom')}.disabled=false;
                                         #{rich:element('txtto')}.disabled=false;
                                         #{rich:element('checkboxShareHolderName')}.disabled=false;
                                         #{rich:element('checkboxfName')}.disabled=true;
                                         #{rich:element('ddfirstname')}.disabled=true;
                                         #{rich:element('txtfirstname')}.disabled=true;
                                         #{rich:element('checkboxaddwise')}.disabled=false;
                                         #{rich:element('checkboxhNo')}.disabled=true;
                                         #{rich:element('ddhouse')}.disabled=true;
                                         #{rich:element('txthouse')}.disabled=true;
                                         #{rich:element('checkboxSector')}.disabled=true;
                                         #{rich:element('ddSector')}.disabled=true;
                                         #{rich:element('txtSector')}.disabled=true; 
                                         #{rich:element('checkboxcity')}.disabled=true;
                                         #{rich:element('ddcity')}.disabled=true;
                                         #{rich:element('txtcity')}.disabled=true;
                                         #{rich:element('checkboxenterby')}.disabled=false;
                                         #{rich:element('ddenterby')}.disabled=true;
                                         #{rich:element('checkboxfathername')}.disabled=false;
                                         #{rich:element('ddfathername')}.disabled=true;
                                         #{rich:element('txtfrom')}.focus();}"
                                         reRender="PanelGridMain,txtto"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblfrom" value="From" styleClass="label"/>
                        <h:panelGroup  layout="block">
                            <h:inputText id="txtfrom"  style="width: 90px" value="#{ShareEnquiry.folioFromShow}" styleClass="input" disabled="#{ShareEnquiry.ddFolioDisable}" maxlength="#{ShareEnquiry.acNoMaxLen}">                      
                                <a4j:support action="#{ShareEnquiry.folioFromMethod}" event="onblur" 
                                             oncomplete="if(#{ShareEnquiry.ddFolio=='0'}){
                                             #{rich:element('btnPrint')}.focus();}
                                             else if(#{ShareEnquiry.ddFolio=='1'}){
                                             #{rich:element('txtto')}.focus();}"
                                             reRender="stxtFolioFromShow,txtto,btnPrint,errmsg"/>                     
                            </h:inputText>
                            <h:outputText id="stxtFolioFromShow" styleClass="output" value="#{ShareEnquiry.folioFrom}" />
                        </h:panelGroup>

                        <h:outputLabel id="lblto" value="To" styleClass="label"/>
                        <h:panelGroup  layout="block">
                            <h:inputText id="txtto"  style="width: 90px" value="#{ShareEnquiry.folioToShow}" styleClass="input" disabled="#{ShareEnquiry.folioToDisable}" maxlength="#{ShareEnquiry.acNoMaxLen}">
                                <a4j:support action="#{ShareEnquiry.folioToMethod}" event="onblur" reRender="stxtFolioToShow,btnPrint,errmsg" focus="btnPrint"/>
                            </h:inputText>
                            <h:outputText id="stxtFolioToShow" styleClass="output" value="#{ShareEnquiry.folioTo}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="panel2"  styleClass="row1"> 
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxShareHolderName" value="#{ShareEnquiry.shareHolderName}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.shareHolderNameValueChange}"  
                                             oncomplete="if(#{ShareEnquiry.shareHolderName=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=false;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true; }"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblShareHolderName" styleClass="label"  value="Share Holder Name"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="panel3"  styleClass="row2">  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxfName" value="#{ShareEnquiry.firstName}" disabled="#{ShareEnquiry.firstNameDisable}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.firstNameValueChange}"  
                                             oncomplete="if(#{ShareEnquiry.firstName=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=false;
                                             #{rich:element('ddfirstname')}.disabled=false;
                                             #{rich:element('txtfirstname')}.disabled=false;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true;
                                             #{rich:element('ddfirstname')}.focus();}"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblFirstName" styleClass="label"  value="First Name"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddfirstname" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.ddFirstName}" disabled="#{ShareEnquiry.ddFirstNameDisable}">
                            <f:selectItems value="#{ShareEnquiry.modeList}"/>
                        </h:selectOneListbox>
                        <h:inputText id="txtfirstname"  style="width: 134px" value="#{ShareEnquiry.txtFirstname}" styleClass="input" maxlength="90"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{ShareEnquiry.ddFirstNameDisable}"/>
                        <h:panelGroup>
                            <%--h:selectBooleanCheckbox id="checkboxlName" value="#{ShareEnquiry.lastName}" disabled="#{ShareEnquiry.lastNameDisable}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.lastNameValueChange}" 
                                             oncomplete="if(#{ShareEnquiry.lastName=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxlName')}.disabled=false;
                                             #{rich:element('ddlastname')}.disabled=false;
                                             #{rich:element('txtlastname')}.disabled=false;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true;
                                             #{rich:element('ddlastname')}.focus();}"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblLastName" styleClass="label"  value="Last Name"/--%>
                            <h:outputLabel/>
                        </h:panelGroup>
                        <%--h:selectOneListbox id="ddlastname" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.ddLastName}" disabled="#{ShareEnquiry.ddLastNameDisable}">
                            <f:selectItems value="#{ShareEnquiry.modeList}"/>
                        </h:selectOneListbox>
                        <h:inputText id="txtlastname"  style="width: 134px" value="#{ShareEnquiry.txtLastName}" styleClass="input" maxlength="20"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{ShareEnquiry.ddLastNameDisable}"/--%>
                         <h:outputLabel/>
                         <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="panel4"  styleClass="row1">  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxaddwise" value="#{ShareEnquiry.addressWise}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.addressWiseValueChange}"  
                                             oncomplete="if(#{ShareEnquiry.addressWise=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=false;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=false;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=false;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true; }"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblAddressWise" styleClass="label"  value="Address Wise"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="panel5"  styleClass="row2">  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxhNo" value="#{ShareEnquiry.houseNo}" disabled="#{ShareEnquiry.houseNoDisable}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.houseNoValueChange}"  
                                             oncomplete="if(#{ShareEnquiry.houseNo=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=false;
                                             #{rich:element('ddhouse')}.disabled=false;
                                             #{rich:element('txthouse')}.disabled=false;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true; 
                                             #{rich:element('ddhouse')}.focus();}"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblHouseNo" styleClass="label"  value="House No."/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddhouse" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.ddHouseNo}" disabled="#{ShareEnquiry.txtHouseNoDisable}">
                            <f:selectItems value="#{ShareEnquiry.modeList}"/>
                        </h:selectOneListbox>
                        <h:inputText id="txthouse"  style="width: 134px" value="#{ShareEnquiry.txtHouseNo}" styleClass="input" maxlength="90"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{ShareEnquiry.txtHouseNoDisable}"/>
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxSector" value="#{ShareEnquiry.sector}" disabled="#{ShareEnquiry.sectorDisable}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.sectorValueChange}"  
                                             oncomplete="if(#{ShareEnquiry.sector=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=false;
                                             #{rich:element('ddSector')}.disabled=false;
                                             #{rich:element('txtSector')}.disabled=false; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true; 
                                             #{rich:element('ddSector')}.focus();}"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblSector" styleClass="label"  value="Sector"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddSector" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.ddSector}" disabled="#{ShareEnquiry.txtSectorDisable}">
                            <f:selectItems value="#{ShareEnquiry.modeList}"/>
                        </h:selectOneListbox>
                        <h:inputText id="txtSector"  style="width: 134px" value="#{ShareEnquiry.txtSector}" styleClass="input" maxlength="60"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{ShareEnquiry.txtSectorDisable}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="panel6"  styleClass="row1">  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxcity" value="#{ShareEnquiry.city}" disabled="#{ShareEnquiry.cityDisable}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.cityValueChange}"  
                                             oncomplete="if(#{ShareEnquiry.city=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=false;
                                             #{rich:element('ddcity')}.disabled=false;
                                             #{rich:element('txtcity')}.disabled=false;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true; 
                                             #{rich:element('ddcity')}.focus();}"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblcity" styleClass="label"  value="City"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddcity" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.ddCity}" disabled="#{ShareEnquiry.txtCityDisable}">
                            <f:selectItems value="#{ShareEnquiry.modeList}"/>
                        </h:selectOneListbox>
                        <h:inputText id="txtcity"  style="width: 134px" value="#{ShareEnquiry.txtCity}" styleClass="input" maxlength="20"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{ShareEnquiry.txtCityDisable}"/>
                        <h:outputLabel />
                        <h:outputLabel/>
                        <h:outputLabel />
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="panel7"  styleClass="row2">  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxenterby" value="#{ShareEnquiry.enterBy}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.enterByValueChange}" 
                                             oncomplete="if(#{ShareEnquiry.enterBy=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=false;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=true; 
                                             #{rich:element('ddenterby')}.focus();}"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblenterby" styleClass="label"  value="Enter By"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddenterby" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.ddEnterBy}" disabled="#{ShareEnquiry.txtEnterByDisable}" >
                            <f:selectItems value="#{ShareEnquiry.modeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel />
                        <h:outputLabel />
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="panel8"  styleClass="row1">  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxfathername" value="#{ShareEnquiry.fatherName}">
                                <a4j:support event="onchange" actionListener="#{ShareEnquiry.fatherNameValueChange}"  
                                             oncomplete="if(#{ShareEnquiry.fatherName=='true'}){
                                             #{rich:element('checkboxfolio')}.disabled=false;
                                             #{rich:element('ddFolioNoAs')}.disabled=true;
                                             #{rich:element('txtfrom')}.disabled=true;
                                             #{rich:element('txtto')}.disabled=true;
                                             #{rich:element('checkboxShareHolderName')}.disabled=false;
                                             #{rich:element('checkboxfName')}.disabled=true;
                                             #{rich:element('ddfirstname')}.disabled=true;
                                             #{rich:element('txtfirstname')}.disabled=true;
                                             #{rich:element('checkboxaddwise')}.disabled=false;
                                             #{rich:element('checkboxhNo')}.disabled=true;
                                             #{rich:element('ddhouse')}.disabled=true;
                                             #{rich:element('txthouse')}.disabled=true;
                                             #{rich:element('checkboxSector')}.disabled=true;
                                             #{rich:element('ddSector')}.disabled=true;
                                             #{rich:element('txtSector')}.disabled=true; 
                                             #{rich:element('checkboxcity')}.disabled=true;
                                             #{rich:element('ddcity')}.disabled=true;
                                             #{rich:element('txtcity')}.disabled=true;
                                             #{rich:element('checkboxenterby')}.disabled=false;
                                             #{rich:element('ddenterby')}.disabled=true;
                                             #{rich:element('checkboxfathername')}.disabled=false;
                                             #{rich:element('ddfathername')}.disabled=false; 
                                             #{rich:element('ddfathername')}.focus();}"
                                             reRender="PanelGridMain"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblfathername" styleClass="label"  value="Father Name"/>
                        </h:panelGroup>
                        <h:inputText id="ddfathername" styleClass="ddlist" size="1" style="width: 134px" value="#{ShareEnquiry.txtFatherName}" disabled="#{ShareEnquiry.txtFatherNameDisable}" maxlength="50"/>

                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{ShareEnquiry.printAction}"  reRender="errmsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ShareEnquiry.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ShareEnquiry.refreshAction}"   reRender="PanelGridMain"/>
                            <a4j:commandButton action="#{ShareEnquiry.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>


