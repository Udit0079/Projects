<%-- 
    Document   : sssAcnoRegistration
    Created on : May 26, 2015, 5:44:31 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>SSS Acno Registration</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="formAtm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SSSAcnoRegistration.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="SSS Acno Registration"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SSSAcnoRegistration.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="message" styleClass="msg" value="#{SSSAcnoRegistration.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcnoRegistration.function}">
                                    <f:selectItems value="#{SSSAcnoRegistration.functionList}"/>
                                    <a4j:support action="#{SSSAcnoRegistration.chgFunction}" event="onblur" oncomplete="setMask();" reRender="message,ddschemeCode,ddvendorName,txtAcNo,stxtcustName,stxtcustAadhar,stxtcustAdd,stxtcustDob,stxtcustEmailId,stxtcustMobile,stxtNomMarried,ddStFlg,table,btnSave"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblschemeCode" styleClass="label" value="Scheme Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddschemeCode" styleClass="ddlist" size="1" style="width:150px;" value="#{SSSAcnoRegistration.schemeCode}" >
                                    <f:selectItems value="#{SSSAcnoRegistration.schemeCodeList}"/>
                                    <a4j:support action="#{SSSAcnoRegistration.getVendorc}" event="onblur"reRender="ddvendorName"> </a4j:support>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblvendorName" styleClass="label" value="Vendor Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddvendorName" styleClass="ddlist" size="1" style="width:150px;" value="#{SSSAcnoRegistration.vendorCode}" >
                                    <f:selectItems value="#{SSSAcnoRegistration.vendorCodeList}"/>
                                    <a4j:support action="#{SSSAcnoRegistration.nomRelation}" event="onchange" reRender="ddnomRelationship"> </a4j:support>
                                </h:selectOneListbox>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAcNo" styleClass="label" value="Account No"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup layout="block" style="width:100%;text-align:left;">
                                <h:inputText id="txtAcNo" styleClass="input" maxlength="#{SSSAcnoRegistration.acNoMaxLen}" style="width:80px;" value="#{SSSAcnoRegistration.accountNo}">
                                    <a4j:support action="#{SSSAcnoRegistration.chenageOperation}" event="onblur" oncomplete="if(#{SSSAcnoRegistration.popupCustFlag=='true'}) #{rich:component('custView')}.show()"  reRender="stxtAccNo,custView,message,txtAcNo,stxtcustName,stxtcustAadhar,stxtcustAdd,stxtcustDob,stxtcustEmailId,stxtcustMobile,stxtNomMarried,table,btnSave"/>
                                </h:inputText>
                                <h:outputText id="stxtAccNo" styleClass="output" value="#{SSSAcnoRegistration.acno}"/>
                            </h:panelGroup>                                
                            <h:outputLabel id="lblcustName" styleClass="label" value="Customer Name"  />
                            <h:outputText id="stxtcustName" styleClass="output" value="#{SSSAcnoRegistration.custName}"/>
                            <h:outputLabel id="lblcustAadhar" styleClass="label" value="AAdhar No."  />
                            <h:outputText id="stxtcustAadhar" styleClass="output" value="#{SSSAcnoRegistration.custAadhar}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblcustAdd" styleClass="label" value="Address"  />
                            <h:outputText id="stxtcustAdd" styleClass="output" value="#{SSSAcnoRegistration.custAdd}"/>
                            <h:outputLabel id="lblcustDob" styleClass="label" value="DOB"  />
                            <h:outputText id="stxtcustDob" styleClass="output" value="#{SSSAcnoRegistration.custDob}"/>
                            <h:outputLabel id="lblcustEmailId" styleClass="label" value="Email Id"  />
                            <h:outputText id="stxtcustEmailId" styleClass="output" value="#{SSSAcnoRegistration.custEmailId}"/>

                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblcustMobile" styleClass="label" value="Mobile No."  />
                            <h:outputText id="stxtcustMobile" styleClass="output" value="#{SSSAcnoRegistration.custMobile}"/>
                            <h:outputLabel id="lblNomMarried" styleClass="label" value="Married"  />
                            <h:outputText id="stxtNomMarried" styleClass="output" value="#{SSSAcnoRegistration.married}"/>
                            <h:outputLabel id="lblSpouseName" styleClass="label" value="Spouse Name" > </h:outputLabel>
                            <h:inputText id="stxtSpouseName" styleClass="input" style="width:150px;" value="#{SSSAcnoRegistration.spouseName}" onkeydown="this.value=this.value.toUpperCase();" /> 
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblspouseAadhar" styleClass="label" value="Spouse AAdhar" > </h:outputLabel>
                            <h:inputText id="stxtspouseAadhar" styleClass="input" style="width:80px;" value="#{SSSAcnoRegistration.spouseAadhar}" maxlength="12" onkeydown="this.value=this.value.toUpperCase();"/> 
                            <h:outputLabel id="lblName" styleClass="label" value="Nomine Name" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtName" styleClass="input" style="width:150px;" value="#{SSSAcnoRegistration.nomName}" onkeydown="this.value=this.value.toUpperCase();"/> 
                                <h:outputLabel id="lblDob" styleClass="label" value="Nomine Dob" ><font class="required" style="color:red;">*</font> </h:outputLabel> 
                                <h:inputText id="txtDob" styleClass="input chkDt" value="#{SSSAcnoRegistration.nomDob}" style="width:75px;" >
                                    <a4j:support oncomplete="setMask();"/>
                                </h:inputText>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAddress" styleClass="label" value="Nomine Address" ><font class="required" style="color:red;">*</font> </h:outputLabel>
                                <h:inputText id="txtAddress" styleClass="input" style="width:150px;" value="#{SSSAcnoRegistration.nomAdd}" onkeydown="this.value=this.value.toUpperCase();"/> 
                                <h:outputLabel id="lblNomineAadhar" styleClass="label" value="Nomine AAdhar" > </h:outputLabel>
                                <h:inputText id="stxtNomineAadhar" styleClass="input" style="width:80px;" value="#{SSSAcnoRegistration.nomAadhar}" maxlength="12" /> 
                                <h:outputLabel id="lblnomRelationship" styleClass="label" value="Nominee Relationship"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddnomRelationship" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcnoRegistration.nomRelationship}" >
                                    <f:selectItems value="#{SSSAcnoRegistration.nomRelationshipList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblminorFlag" styleClass="label" value="Minor Flag"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                <h:selectOneListbox id="ddminorFlag" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcnoRegistration.minorFlag}" >
                                    <f:selectItems value="#{SSSAcnoRegistration.minorFlagList}"/>
                                    <a4j:support action="#{SSSAcnoRegistration.minorDisb}" event="onblur"reRender="txtguardianName,txtguardianAdd"/>                                            
                                </h:selectOneListbox>
                                <h:outputLabel id="lblguardianName" styleClass="label" value="Guardain Name" > </h:outputLabel>
                                <h:inputText id="txtguardianName" styleClass="input" style="width:150px;" value="#{SSSAcnoRegistration.guardianName}" disabled="#{SSSAcnoRegistration.minorDiable}"onkeydown="this.value=this.value.toUpperCase();"/>
                                <h:outputLabel id="lblguardianAdd" styleClass="label" value="Guardain Address" > </h:outputLabel>
                                <h:inputText id="txtguardianAdd" styleClass="input" style="width:150px;" value="#{SSSAcnoRegistration.guardianAdd}" disabled="#{SSSAcnoRegistration.minorDiable}"onkeydown="this.value=this.value.toUpperCase();"/> 
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblStFlg" styleClass="label" value="Enrollment Type"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                <h:selectOneListbox id="ddStFlg" styleClass="ddlist" size="1" style="width:80px;" disabled="#{SSSAcnoRegistration.enrollDiable}"value="#{SSSAcnoRegistration.enrollCancelation}" >
                                    <f:selectItems value="#{SSSAcnoRegistration.enrollCancelationList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblDisab" styleClass="label" value="Disability"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddDisab" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcnoRegistration.disability}" >
                                    <f:selectItems value="#{SSSAcnoRegistration.disabilityList}"/>
                                    <a4j:support action="#{SSSAcnoRegistration.disabilityFucn}" event="onblur" reRender="txtAreaDisabDesc"> </a4j:support>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblDisabDesc" styleClass="label" value="Disability Details"> </h:outputLabel>
                                <h:inputTextarea rows="3" cols="80" id="txtAreaDisabDesc"  value="#{SSSAcnoRegistration.disabilityDetails}"  disabled="#{SSSAcnoRegistration.disableDisab}"onkeypress="this.value=this.value.toUpperCase();" styleClass="input" style="height:30px;width:180px">
                                </h:inputTextarea>
                            </h:panelGrid>

                        <rich:panel header="Atal Pension yojna Details" style="text-align:left;">

                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblswalban" styleClass="label" value="Swalamban Sub"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddswalban" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcnoRegistration.swalban}" >
                                        <f:selectItems value="#{SSSAcnoRegistration.swalbanList}"/>                                    
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblpRan" styleClass="label" value="Pran" ><font class="required" style="color:red;">*</font> </h:outputLabel>
                                    <h:inputText id="stxtpRan" styleClass="input" style="width:80px;" value="#{SSSAcnoRegistration.pran}" maxlength="12" /> 

                                <h:outputLabel id="lblpension" styleClass="label" value="Pension Amount" ><font class="required" style="color:red;">*</font> </h:outputLabel>
                                    <h:inputText id="stxtpension" styleClass="input" style="width:80px;" value="#{SSSAcnoRegistration.pensionAmt}" maxlength="12">
                                        <a4j:support action="#{SSSAcnoRegistration.retrieveContributationAmount}" event="onblur" reRender="stxtcontributionAmt,message,mainPanel"/>
                                    </h:inputText> 

                            </h:panelGrid> 

                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row11" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblcontributionAmt" styleClass="label" value="Contribution Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:outputText id="stxtcontributionAmt" styleClass="output" value="#{SSSAcnoRegistration.contributionAmt}"/>
                                    <h:outputLabel id="lblincomeTax" styleClass="label" value="Income Tax Period"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                    <h:selectOneListbox id="ddincomeTax" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcnoRegistration.incomeTax}" >
                                        <f:selectItems value="#{SSSAcnoRegistration.incomeTaxList}"/>
                                        <%--a4j:support action="#{SSSAcnoRegistration.minorDisb}" event="onblur"reRender="txtguardianName,txtguardianAdd"/--%>                                            
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblapyStateCode" styleClass="label" value="State Code"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                    <h:selectOneListbox id="ddapyStateCode" styleClass="ddlist" size="1" style="width:80px;" value="#{SSSAcnoRegistration.apyStateCode}" >
                                        <f:selectItems value="#{SSSAcnoRegistration.apyStateCodeList}"/>
                                        <%--a4j:support action="#{SSSAcnoRegistration.minorDisb}" event="onblur"reRender="txtguardianName,txtguardianAdd"/--%>                                            
                                    </h:selectOneListbox>
                                </h:panelGrid>

                        </rich:panel>

                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{SSSAcnoRegistration.gridDetail}" var="dataItem"
                                                rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="Registration Details"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No."/></rich:column>
                                            <rich:column><h:outputText value="Customer Name"/></rich:column>
                                            <rich:column><h:outputText value="Scheme Name"/></rich:column>
                                            <rich:column><h:outputText value="Vendor Name"/></rich:column>
                                            <rich:column><h:outputText value="Nominee Name"/></rich:column>
                                            <rich:column><h:outputText value="Guardian Name"/></rich:column>
                                            <rich:column><h:outputText value="Spouse Name"/></rich:column>
                                            <rich:column><h:outputText value="Enroll By"/></rich:column>
                                            <rich:column><h:outputText value="Enroll Date"/></rich:column>
                                            <rich:column><h:outputText value="Auth"/></rich:column>
                                            <rich:column width="20"><h:outputText value="SELECT"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.acNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.custName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.schemeCode}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.vendorCode}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.nomName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.guardianName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.spouseName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enterBy}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enrolDate}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.auth}"/></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{SSSAcnoRegistration.select}" oncomplete="setMask();" reRender="mainPanel">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{SSSAcnoRegistration.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" value="#{SSSAcnoRegistration.btnValue}" action="#{SSSAcnoRegistration.populateMessage}" disabled="#{SSSAcnoRegistration.btnFlag}" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="mainPanel,processPanel,confirmid"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SSSAcnoRegistration.refreshForm}" oncomplete="setMask();" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{SSSAcnoRegistration.exitBtnAction}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="signatureGridPanel" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputText id="stxtSignature" styleClass="output" value="F7-Signature View" style="color:blue;"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:jsFunction name="showSignDetail" oncomplete="if(#{rich:element('txtAcNo')}.value!='') #{rich:component('sigView')}.show();" action="#{SSSAcnoRegistration.getSignatureDetail}" reRender="sigView"/>
            </a4j:form>
            <rich:hotKey key="F7" handler="showSignDetail();"/>
            <rich:modalPanel id="sigView" height="530" width="700" style="align:right">
                <f:facet name="header">
                    <h:outputText value="Signature Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                        <rich:componentControl for="sigView" attachTo="closelink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>

                    <h:panelGrid id="sigViewRow1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                        <h:outputText id="txtAcNo" style="output" value="#{SSSAcnoRegistration.accNo}"/>
                        <h:outputLabel id="lblAcName" styleClass="label" value="Name"/>
                        <h:outputText id="txtAcName" style="output" value="#{SSSAcnoRegistration.accountName}"/>
                        <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                        <h:outputText id="txtCustomerId" style="output" value="#{SSSAcnoRegistration.custId}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                        <h:outputLabel id="lblOpMode" styleClass="label" value="Operation Mode"/>
                        <h:outputText id="txtOpMode" style="output" value="#{SSSAcnoRegistration.opMode}"/>
                        <h:outputLabel id="lblOpDt" styleClass="label" value="Opening Date"/>
                        <h:outputText id="txtOpDt" style="output" value="#{SSSAcnoRegistration.openDate}"/>
                        <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No"/>
                        <h:outputText id="txtPanNo" style="output" value="#{SSSAcnoRegistration.custPanNo}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow3" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblJtName" styleClass="label" value="Jt.Name"/>
                        <h:outputText id="txtJtName" style="output" value="#{SSSAcnoRegistration.jtName}"/>
                        <h:outputLabel id="lblSigAnnualTrunOver" styleClass="label" value="Annual Turnover"/>
                        <h:outputText id="txtSigAnnualTrunOver" style="output" value="#{SSSAcnoRegistration.annualTurnover}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow4" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                        <h:outputLabel id="lblAcInst" styleClass="label" value="A/c Instruction"/>
                        <h:outputText id="txtAcIns" style="output" value="#{SSSAcnoRegistration.accInstruction}"/>
                        <h:outputLabel id="lblProfession" styleClass="label" value="Profession"/>
                        <h:outputText id="txtProfession" style="output" value="#{SSSAcnoRegistration.profession}"/>
                    </h:panelGrid>

                    <h:panelGrid id="custIdPanelGrid" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblSigRiskCat" styleClass="label" value="Risk Category"/>
                        <h:outputText id="txtSigRiskCat" style="output" value="#{SSSAcnoRegistration.riskCategorization}"/>
                        <h:outputLabel id="lblSigDpLimit" styleClass="label" value="DP Limit"/>
                        <h:outputText id="txtSigDpLimit" style="output" value="#{SSSAcnoRegistration.dpLimit}"/>
                    </h:panelGrid>

                    <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{SSSAcnoRegistration.createSignature}" value="#{SSSAcnoRegistration.accNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="sigViewBtnPanel">
                            <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="custView" height="350" width="500" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Customer Detail Alert !" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="glheadlink1"/>
                        <rich:componentControl for="custView" attachTo="glheadlink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="mainPane2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                        <h:panelGrid id="custViewRow14" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                            <h:outputLabel value="Mandatory fields for schemes Registration.So please check / verify all fields from Customer Detail Form,then Registration !" styleClass="output" style="color:red;"/>
                        </h:panelGrid>

                        <h:panelGrid id="custViewRow1" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                            <h:outputLabel value="TITLE should not be blank." styleClass="output" style="color:blue;"/>
                            <h:outputLabel value="GENDER should not be blank." styleClass="output" style="color:blue;"/>
                        </h:panelGrid>
                        <h:panelGrid id="custViewRow3" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1">
                            <h:outputLabel value="MARITAL STATUS should not be blank." styleClass="output" style="color:blue;"/>
                            <h:outputLabel value="DATE OF BIRTH should not be blank." styleClass="output" style="color:blue;"/>
                        </h:panelGrid>
                        <h:panelGrid id="custViewRow5" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                            <h:outputLabel value="MINOR FLAG should not be blank." styleClass="output" style="color:blue;"/>
                            <h:outputLabel value="MOBILE NUMBER should be 10 digits." styleClass="output" style="color:blue;"/>
                        </h:panelGrid>
                        <h:panelGrid id="custViewRow7" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                            <h:outputLabel value="PERMANET PIN should be 6 digits." styleClass="output" style="color:blue;"/>
                            <h:outputLabel value="PERMANET DISTRICT should not be blank." styleClass="output" style="color:blue;"/>
                        </h:panelGrid>
                        <h:panelGrid id="custViewRow9" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                            <h:outputLabel value="PERMANET STATE should not be blank." styleClass="output" style="color:blue;"/>
                            <h:outputLabel value="MALING PIN should be 6 digits" styleClass="output" style="color:blue;"/>
                        </h:panelGrid>
                        <h:panelGrid id="custViewRow11" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1">
                            <h:outputLabel value="MALING DISTRICT should not be blank." styleClass="output" style="color:blue;"/>
                            <h:outputLabel value="PERMANENT ADDRESS LINE1 should not be blank." styleClass="output" style="color:blue;"/>
                        </h:panelGrid>
                        <h:panelGrid id="custViewRow13" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                            <h:outputLabel value="PERMANENT ADDRESS LINE2 should not be blank." styleClass="output" style="color:blue;"/>
                            <h:outputLabel value="MAIL ADDRESS LINE1 should not be blank." styleClass="output" style="color:blue;"/>
                        </h:panelGrid>
                        <%--h:panelGrid id="custViewRow14" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                            <h:outputLabel value="*Above all field are mandatory,First Please check all field from Customer Detail Form !" styleClass="output" style="color:red;"/>
                        </h:panelGrid--%>
                        <h:panelGrid id="custViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="custViewBtnPanel">
                                <a4j:commandButton id="custViewClose" value="OK" action="#{SSSAcnoRegistration.refreshForm}" oncomplete="#{rich:component('custView')}.hide(); return false;" reRender="mainPanel">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>


            <%--New Addition--%>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{SSSAcnoRegistration.confirmationMsg}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{SSSAcnoRegistration.saveMasterDetail}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="message,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>