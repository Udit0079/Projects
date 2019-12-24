<%-- 
    Document   : shareCertificate
    Created on : Jun 11, 2015, 2:51:29 PM
    Author     : Admin
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
            <title>Share Certificate</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="folioStatement">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareCertificate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Share Certificate"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareCertificate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{ShareCertificate.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="reportID" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{ShareCertificate.reportType}" styleClass="ddlist" size="1" style="width:90px">
                            <f:selectItems value="#{ShareCertificate.reportTypeList}"/> 
                            <a4j:support action="#{ShareCertificate.reprtOption}" event="onchange" reRender="a1,txtacno,txtcertificateNo,dateId,folioPanalId,frId,toId,frDt,txtfrmacno,stxtFolioFromShow,toDt,txttoacno,stxtFolioToShow,errorMsg"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="frId"value="#{ShareCertificate.lableButton1}" styleClass="label"/>
                        <h:panelGroup id="dateId">
                            <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{ShareCertificate.frmDt}" style="display:#{ShareCertificate.styleDate}" >
                                <a4j:support event="onchange" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:inputText id="txtfrmacno" value="#{ShareCertificate.frmFolioNoShow}" styleClass="input" maxlength="#{ShareCertificate.acNoMaxLen}" size="12" onkeyup="this.value=this.value.toUpperCase();" style="display:#{ShareCertificate.styleFolio}">
                                <a4j:support action="#{ShareCertificate.folioFromMethod}" event="onblur" focus="txttoacno"  reRender="stxtFolioFromShow,txttoacno,errorMsg" oncomplete="setMask();"/>                     
                            </h:inputText>
                            <h:outputText id="stxtFolioFromShow" styleClass="output" value="#{ShareCertificate.frmFolioNo}" style="color:green;display:#{ShareCertificate.styleFolio}"/>
                            <h:inputText id="txtfrmCertNo" value="#{ShareCertificate.frmCertNoShow}" styleClass="input" maxlength="#{ShareCertificate.acNoMaxLen}" size="12" onkeyup="this.value=this.value.toUpperCase();" style="display:#{ShareCertificate.styleCertNo}">
                                <a4j:support action="#{ShareCertificate.setOnfromCertNo}" event="onblur" focus="txttoCertNo"  reRender="stxtfrmCertNoShow,errorMsg" oncomplete="setMask();"/>                     
                            </h:inputText>
                            <h:outputText id="stxtfrmCertNoShow" styleClass="output" value="#{ShareCertificate.frmCertNo}" style="color:green;display:#{ShareCertificate.styleCertNo}"/>
                        </h:panelGroup>
                        <h:outputText id="toId" value="#{ShareCertificate.lableButton2}" styleClass="label"/> 
                        <h:panelGroup id="folioPanalId">
                            <h:inputText id="toDt" styleClass="input toDt" size="12" value="#{ShareCertificate.toDt}" style="display:#{ShareCertificate.styleDate}">
                                <a4j:support event="onchange" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:inputText id="txttoacno" value="#{ShareCertificate.toFolioNoShow}" styleClass="input" size="12" maxlength="#{ShareCertificate.acNoMaxLen}" onkeyup="this.value=this.value.toUpperCase();" style="display:#{ShareCertificate.styleFolio}">
                                <a4j:support action="#{ShareCertificate.folioTo1Method}" event="onblur" reRender="stxtFolioToShow,errorMsg" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="stxtFolioToShow" styleClass="output" value="#{ShareCertificate.toFolioNo}" style="color:green;display:#{ShareCertificate.styleFolio}"/>
                            <h:inputText id="txttoCertNo" value="#{ShareCertificate.toCertNoShow}" styleClass="input" size="12" maxlength="#{ShareCertificate.acNoMaxLen}" onkeyup="this.value=this.value.toUpperCase();" style="display:#{ShareCertificate.styleCertNo}">
                                <a4j:support action="#{ShareCertificate.setOntoCertNo}" event="onblur" reRender="stxtoCertNoShow,errorMsg" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="stxtoCertNoShow" styleClass="output" value="#{ShareCertificate.toCertNo}" style="color:green;display:#{ShareCertificate.styleCertNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText value="Folio Number :" styleClass="label"/>
                        <h:panelGroup id="folioId">
                            <h:inputText id="txtacno" value="#{ShareCertificate.folioNo}" styleClass="input" size="12" maxlength="#{ShareCertificate.acNoMaxLen}" onkeyup="this.value=this.value.toUpperCase();" disabled="#{ShareCertificate.disableSingleAcno}">
                                <a4j:support action="#{ShareCertificate.folioToMethod}" event="onblur" reRender="stxtFolioShow,ddCertificateNo,errorMsg" focus="txtcertificateNo"/>
                            </h:inputText>
                            <h:outputText id="stxtFolioShow" styleClass="output" value="#{ShareCertificate.folioNoShow}" style="color:green" />
                        </h:panelGroup>
                        <h:outputText value="Certificate Number :" styleClass="label"/>
                        <h:panelGroup id="certificateId">
                            <%--h:inputText id="txtcertificateNo" value="#{ShareCertificate.certificateNo}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();" disabled="#{ShareCertificate.disableSingleAcno}">
                                <a4j:support action="#{ShareCertificate.retriveCustData}" event="onblur" reRender="stxtcertificateNoShow,stxtcustName,stxtfatherName,stxtcustCity,stxtcustAdd,stxtcustState,stxtformShareNo,stxttoShareNo,stxtnoOfshare,stxtshareAmt,stxtissueDt,errorMsg"/>
                            </h:inputText--%>
                            <h:selectOneListbox id="ddCertificateNo" value="#{ShareCertificate.certificateNo}" styleClass="ddlist" size="1" style="width:90px" disabled="#{ShareCertificate.disableSingleAcno}">
                                <f:selectItems value="#{ShareCertificate.certificateNoList}"/> 
                                <a4j:support action="#{ShareCertificate.retriveCustData}" event="onblur" reRender="stxtcertificateNoShow,stxtcustName,stxtfatherName,stxtcustCity,stxtcustAdd,stxtcustState,stxtformShareNo,stxttoShareNo,stxtnoOfshare,stxtshareAmt,stxtissueDt,errorMsg"/>
                            </h:selectOneListbox>
                            <h:outputText id="stxtcertificateNoShow" styleClass="output" value="#{ShareCertificate.certificateNoShow}" style="color:green"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblcustName" styleClass="label" value="Customer Name :"  />
                        <h:outputText id="stxtcustName" styleClass="output" value="#{ShareCertificate.custName}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblfatherName" styleClass="label" value="Father Name :"  />
                        <h:outputText id="stxtfatherName" styleClass="output" value="#{ShareCertificate.fatherName}"/>
                        <h:outputLabel id="lblcustAdd" styleClass="label" value="Address :"  />
                        <h:outputText id="stxtcustAdd" styleClass="output" value="#{ShareCertificate.custAdd}"/>
                        <h:outputLabel id="lblcustCity" styleClass="label" value="City :"  />
                        <h:outputText id="stxtcustCity" styleClass="output" value="#{ShareCertificate.custCity}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblcustState" styleClass="label" value="State :"/>
                        <h:outputText id="stxtcustState" styleClass="output" value="#{ShareCertificate.custState}"/>
                        <h:outputLabel id="lblformShareNo" styleClass="label" value="From Share :"  />
                        <h:outputText id="stxtformShareNo" styleClass="output" value="#{ShareCertificate.formShareNo}"/>
                        <h:outputLabel id="lbltoShareNo" styleClass="label" value="To Share :"  />
                        <h:outputText id="stxttoShareNo" styleClass="output" value="#{ShareCertificate.toShareNo}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblnoOfshare" styleClass="label" value="No of Share :"  />
                        <h:outputText id="stxtnoOfshare" styleClass="output" value="#{ShareCertificate.noOfshare}"/>
                        <h:outputLabel id="lblshareAmt" styleClass="label" value="Distinctive No. :"  />
                        <h:outputText id="stxtshareAmt" styleClass="output" value="#{ShareCertificate.shareAmt}"/>
                        <h:outputLabel id="lblissueDt" styleClass="label" value="Issue Date :"  />
                        <h:outputText id="stxtissueDt" styleClass="output" value="#{ShareCertificate.issueDt}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col10,col11" id="gridPanel7" style="text-align:left;width:100%" width="100%" styleClass="footer">
                        <h:outputText id="alertId" value="#You can print maximum 25 page at  a time,Please check then print " styleClass="output" style="color:red;align:left" /> 
                        <h:panelGroup id="gridPanel" layout="block">
                            <a4j:commandButton  id="print" value="Print" action="#{ShareCertificate.printButton}" reRender="errorMsg"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{ShareCertificate.refreshPage}" reRender="errorMsg,a1" oncomplete="setMask();"/>
                            <a4j:commandButton  id="exit" value="Exit" action="case1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
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
