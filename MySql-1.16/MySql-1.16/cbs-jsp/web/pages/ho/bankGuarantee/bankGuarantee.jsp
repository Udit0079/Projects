<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Bank Guarantee</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />            
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="bankguarantee">
                <a4j:region>
                    <!-- Main Panel -->
                    <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel1" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{BankGuarantee.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Bank Guarantee"/>
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="otUsername" styleClass="output" value="#{BankGuarantee.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>

                        <!-- Message Panel -->
                        <h:panelGrid  id="errormsg" width="100%" style="width:100%;text-align:center" styleClass="row1">
                            <h:outputText id="errmsg" value="#{BankGuarantee.message}" styleClass="error"/>
                        </h:panelGrid>
                        <!-- Panel 1-->
                        <h:panelGrid id="PanelGrid1" styleClass="row2" bgcolor="#fff" columns="10" columnClasses="col1,col4,col1,col4,col1,col4,col1,col4,col1,col4"  style="border:1px ridge #BED6F8;"  width="100%">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{BankGuarantee.function}">
                                    <f:selectItems value="#{BankGuarantee.functionList}"/>
                                    <a4j:support action="#{BankGuarantee.blurAction}" event="onblur"reRender="ddAction,btnVerify,btnSave,txtguarno,txtacno,errormsg,table,tableList,txtname,txtodLimit,txtbalance,txtBenfiName,txtBenfiAddress,
                                                 lblguarNo,ddPrState,ddCity,txtPinCode,ddClasification,ddPurpose,ddvalidityIn,txtPeriod,txtExpiryDt,txtGuaranteeAmt,txtComissionAmt,txtCharges,vtop,ReportPanelGrid,fromDateLabel,fromDate,toDateLabel,toDate,lblType,txttype,printButton,DatePanelGrid,lblIssueType,txtissuetype,btnDelete"> </a4j:support>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAction" styleClass="label" value="Action"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAction" styleClass="ddlist" size="1" style="width:120px;" value="#{BankGuarantee.action}" >
                                    <f:selectItems value="#{BankGuarantee.actionList}"/>
                                    <a4j:support action="#{BankGuarantee.guaranteeBlur}" event="onblur"reRender="lblguarNo,txtguarno,ddFunction,lblIssueType,txtissuetype,fromDateLabel,fromDate,toDateLabel,toDate,tillDateLabel,tillDate,errmsg,txtacno,txtname,txtodLimit,txtbalance,txtBenfiName,txtBenfiAddress,ddPrState,ddCity,txtPinCode,ddClasification,ddPurpose,ddvalidityIn,txtPeriod,txtExpiryDt,txtGuaranteeAmt,txtComissionAmt,txtCharges"> </a4j:support>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblIssueType" styleClass="label" value="Issue Type"  style="width:85px;display:#{BankGuarantee.issueDisplay};"><font class="required" style="color:red;">*</font>
                                    <h:selectOneListbox value="#{BankGuarantee.issueType}" id="txtissuetype" style="width:85px;display:#{BankGuarantee.issueDisplay};" styleClass="ddlist"  size="1" >
                                        <f:selectItems value="#{BankGuarantee.issueTypeList}"/>
                                        <a4j:support action="#{BankGuarantee.dateblur()}" event="onblur"reRender="fromDateLabel,fromDate,toDateLabel,toDate,tillDateLabel,tillDate"> </a4j:support>
                                    </h:selectOneListbox>
                                </h:outputLabel>
                                <h:outputLabel id="lblguarNo" styleClass="label" value="Guarantee No"  style="display:#{BankGuarantee.firstDisplay};">   </h:outputLabel>
                                <h:selectOneListbox value="#{BankGuarantee.verGuarantee}" id="txtguarno" style="display:#{BankGuarantee.firstDisplay};" styleClass="ddlist"  size="1" >
                                    <f:selectItems value="#{BankGuarantee.actionGuaranteeList}"/>
                                    <a4j:support action="#{BankGuarantee.verifyBlur}" event="onblur" oncomplete="if(#{BankGuarantee.taxflag=='true'}){#{rich:element('txtComissionAmt')}.focus();}" reRender="errormsg,table,tableList,txtacno,txtname,txtodLimit,txtbalance,txtBenfiName,txtBenfiAddress,
                                                 ddPrState,ddCity,txtPinCode,ddClasification,ddPurpose,ddvalidityIn,txtPeriod,txtExpiryDt,txtGuaranteeAmt,txtComissionAmt,txtCharges,vtop,ddFunction,ddAction,btnVerify,ddIssued,txtodLimit,txtbalance,radioMarkSecurities,btnSave,btnDelete"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="DatePanelGrid" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8;display:#{BankGuarantee.displaypaneldate};" width="100%"> 
                                <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel90" style="height:30px;" styleClass="row1" width="100%">
                                    <h:outputLabel id="lblType" styleClass="label" value="Type"  style="width:85px;display:#{BankGuarantee.displaytype};"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                    <h:selectOneListbox value="#{BankGuarantee.reportType}" id="txttype" style="width:85px;display:#{BankGuarantee.displaytype};" styleClass="ddlist"  size="1" >
                                        <f:selectItems value="#{BankGuarantee.reportTypeList}"/>
                                        <a4j:support event="onblur" oncomplete="setMask()"/>
                                    </h:selectOneListbox> 
                                    <h:outputLabel id="fromDateLabel" styleClass="output" value="From Date" style="width:75px;display:#{BankGuarantee.displayFromDate};" ><font class="required" style="color:red;">*</font> </h:outputLabel>
                                    <h:inputText id="fromDate" value="#{BankGuarantee.fromDate}" style="width:75px;display:#{BankGuarantee.displayFromDate};" styleClass="input calInstDate">
                                        <a4j:support event="onblur" oncomplete="setMask()"/>
                                    </h:inputText>
                                    <h:outputLabel id="toDateLabel" styleClass="output" value="To Date"  style="width:75px;display:#{BankGuarantee.displayToDate};" ><font class="required" style="color:red;">*</font>
                                        <h:inputText id="toDate" value="#{BankGuarantee.toDate}" style="width:75px;display:#{BankGuarantee.displayToDate};" styleClass="input calInstDate">
                                            <a4j:support event="onblur" oncomplete="setMask()"/>
                                        </h:inputText>
                                    </h:outputLabel>
                                    <h:outputLabel id="tillDateLabel" styleClass="output" value="Till Date"  style="width:75px;display:#{BankGuarantee.displayTillDate};" ><font class="required" style="color:red;">*</font>
                                        <h:inputText id="tillDate" value="#{BankGuarantee.tillDate}" style="width:85px;display:#{BankGuarantee.displayTillDate};" styleClass="input calInstDate">
                                            <a4j:support event="onblur" oncomplete="setMask()"/>
                                        </h:inputText>
                                    </h:outputLabel> 

                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="ReportPanelGrid" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8;display:#{BankGuarantee.displaypanel};" width="100%"> 
                            <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel51" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblacno" styleClass="label"  value="Account No"  style="width:100%;text-align:left;"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtacno" value="#{BankGuarantee.acno}" styleClass="input" size="#{BankGuarantee.acNoMaxLen}" maxlength="#{BankGuarantee.acNoMaxLen}" onkeyup="this.value=this.value.toUpperCase();">
                                        <a4j:support actionListener="#{BankGuarantee.getNewAccNo}"
                                                     event="onblur" limitToList="true"
                                                     reRender="errormsg,txtname,txtodLimit,txtbalance,ddPrState,ddIssued"
                                                     focus=""/>
                                    </h:inputText>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                </h:panelGrid> 
                                <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel5" style="height:30px;" styleClass="row2" width="100%">
                                    <h:outputLabel id="lblName" styleClass="label"  value="Name"  style="width:100%;text-align:left;"/>
                                    <h:outputText  id="txtname" styleClass="output" style="color:black" value="#{BankGuarantee.name}"/>
                                    <h:outputLabel id="lblOdlimit" styleClass="label"  value="Odlimit"  style="width:100%;text-align:left;"/>
                                    <h:outputText  id="txtodLimit" styleClass="output" style="color:black" value="#{BankGuarantee.odLimit}"/>
                                    <h:outputLabel id="lblBalance" styleClass="label"  value="Balance"  style="width:100%;text-align:left;"/>
                                    <h:outputText  id="txtbalance" styleClass="output" style="color:black" value="#{BankGuarantee.balance}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanelh" style="height:30px;" styleClass="row1" width="100%">
                                    <h:outputLabel id="lblBenfiName" styleClass="label" value="Benficiary Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtBenfiName" maxlength="40"  styleClass="input" value="#{BankGuarantee.benfiName}" onkeydown="this.value=this.value.toUpperCase();"/>
                                    <h:outputLabel id="lblBenfiAddress" styleClass="label" value="Benficiary Address"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtBenfiAddress" maxlength="40" styleClass="input"  value="#{BankGuarantee.benfiAddress}" onkeydown="this.value=this.value.toUpperCase();"/>
                                    <h:outputLabel id="lblPrState" styleClass="label" value="State">
                                    <font class="required" style="color:red;">*</font>
                                    </h:outputLabel>
                                    <h:selectOneListbox value="#{BankGuarantee.state}" id="ddPrState"  styleClass="ddlist"  size="1">
                                        <f:selectItems value="#{BankGuarantee.stateList}"/>
                                        <a4j:support  action="#{BankGuarantee.getCitys}" event="onblur" reRender="gridPanel24,ddCity,errormsg" focus=""/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel24" style="height:30px;" styleClass="row2" width="100%">
                                    <h:outputLabel id="lblCity" styleClass="label" value="City">
                                    <font class="required" style="color:red;">*</font>
                                    </h:outputLabel>
                                    <h:selectOneListbox id="ddCity" styleClass="ddlist" size="1" style="width:100px;" value="#{BankGuarantee.city}">
                                        <f:selectItems value="#{BankGuarantee.cityList}"/>
                                        <a4j:support event="onblur" reRender="txtPinCode,errormsg" focus="" /> 
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblPinCode" styleClass="label" value="Postal Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtPinCode" maxlength="6" styleClass="input"  value="#{BankGuarantee.pinCode}" style="width:120px">
                                        <a4j:support action="#{BankGuarantee.pattern}" event="onblur"
                                                     reRender="ddIssued,errormsg"  focus="" />
                                    </h:inputText>  
                                    <h:outputLabel id="lblIssued" styleClass="label" value="Guarantee Issued By"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddIssued" styleClass="ddlist" size="1" style="width:150px;" value="#{BankGuarantee.guaranteeIssuedBy}" disabled="#{BankGuarantee.taxflag}">
                                        <f:selectItems value="#{BankGuarantee.guaranteeIssuedByList}"/>   
                                        <a4j:support action="#{BankGuarantee.classificationAction}" event="onblur" 
                                                     reRender="ddClasification,errormsg" focus="" />
                                    </h:selectOneListbox> 
                                </h:panelGrid>
                                <h:panelGrid id="PanelGrid2" styleClass="row1" columns="6" columnClasses="col1,col4,col1,col4,col1,col4" style="height:30px;" width="100%">
                                    <h:outputLabel id="lblClassification" styleClass="label" value="Classification" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddClasification" styleClass="ddlist" size="1" style="width:150px;" value="#{BankGuarantee.classification}">
                                        <f:selectItems value="#{BankGuarantee.classificationList}"/>
                                        <a4j:support action="#{BankGuarantee.purposeInAction}" event="onblur" 
                                                     reRender="ddPurpose,errormsg" focus="" />
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblPurpose" styleClass="label" value="Purpose" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddPurpose" styleClass="ddlist" size="1" style="width:100px;" value="#{BankGuarantee.purpose}">
                                        <f:selectItems value="#{BankGuarantee.purposeList}"/>
                                        <a4j:support action="#{BankGuarantee.validityInAction}" event="onblur" 
                                                     reRender="ddvalidityIn,errormsg" focus="" />
                                    </h:selectOneListbox>                            
                                    <h:outputLabel id="lblValidityIn" styleClass="label" value="Validity In/Peroid"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:outputLabel id="lblValidityIn1">
                                        <h:selectOneListbox id="ddvalidityIn" styleClass="ddlist" style="width:100px;" value="#{BankGuarantee.validityIn}" size="1" >
                                            <f:selectItems value="#{BankGuarantee.validityInList}" />
                                            <a4j:support  event="onblur"/>
                                        </h:selectOneListbox>
                                        <h:inputText id="txtPeriod" style="width:80px;" maxlength="10" styleClass="input"  value="#{BankGuarantee.period}">
                                            <a4j:support action="#{BankGuarantee.validityPeriodlostFocus}" event="onblur" 
                                                         reRender="txtExpiryDt,errormsg,btnSave" limitToList="true" focus="txtExpiryDt" />
                                        </h:inputText>
                                    </h:outputLabel>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel25" style="height:30px;" styleClass="row2" width="100%">
                                    <%--<h:outputLabel id="lblcommissionMode" styleClass="label" value="CommissionMode"><font class="required" color="red">*</font> </h:outputLabel>
                                    <h:selectOneListbox id="ddcomissionMode" styleClass="ddlist" style="margin-left:70px;width: 70px" value="#{BankGuarantee.mode}" size="1" >
                                        <f:selectItems value="#{BankGuarantee.modeList}" />
                                        <a4j:support  event="onblur"/>
                                    </h:selectOneListbox>--%>
                                    <h:outputLabel id="lblexpiryDate" styleClass="label" value="Guarantee Expiry Date"></h:outputLabel>

                                <h:inputText id="txtExpiryDt" styleClass="input" style="width:80px;setMask()" maxlength="10" value="#{BankGuarantee.guaranteeExpiryDate}">
                                    <f:convertDateTime pattern="dd/MM/yyyy"/>
                                    <a4j:support  event="onblur"/>
                                </h:inputText>
                                <h:outputLabel id="lblGuaranteeAmt" styleClass="label" value="Guarantee Amt"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtGuaranteeAmt" styleClass="input"  value="#{BankGuarantee.guaranteeAmt}" disabled="#{BankGuarantee.taxflag}"/>
                                    <h:outputLabel id="lblcomissionAmt" styleClass="label" value="ComissionAmt"/>
                                    <h:inputText id="txtComissionAmt" styleClass="input"  value="#{BankGuarantee.comissionAmt}" disabled="#{BankGuarantee.taxflag}">
                                        <a4j:support action="#{BankGuarantee.TotalcomAmt}" event="onblur" 
                                                     reRender="txtTotalComissionAmt,txtCharges,ddIssued,errormsg" limitToList="true" focus="" />
                                    </h:inputText>                                
                                    <%-- <h:outputLabel id="lbldateWise" styleClass="label" value="#{BankGuarantee.mode}" style="color:purple"/>--%>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanelTax" style="height:30px;" styleClass="row1" width="100%">
                                    <%--<h:outputLabel id="lblcommissionMode" styleClass="label" value="CommissionMode"><font class="required" color="red">*</font> </h:outputLabel>
                                    <h:selectOneListbox id="ddcomissionMode" styleClass="ddlist" style="margin-left:70px;width: 70px" value="#{BankGuarantee.mode}" size="1" >
                                        <f:selectItems value="#{BankGuarantee.modeList}" />
                                        <a4j:support  event="onblur"/>
                                    </h:selectOneListbox>--%>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel id="lblCharges" styleClass="label" value="Tax Amt" style="width:100%;text-align:left;"/>
                                    <h:inputText id="txtCharges"  styleClass="output" style="color:black"  value="#{BankGuarantee.taxCharges}" disabled="#{BankGuarantee.disableFlag}">
                                    </h:inputText>
                                    <%-- <h:outputLabel id="lbldateWise" styleClass="label" value="#{BankGuarantee.mode}" style="color:purple"/>--%>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="2" id="MarkSecuritiesRow" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblMarkSecurities" styleClass="label" value="Mark Securities : " style="color:purple;padding-left:200px;font-size:15px;"/>
                                    <h:selectOneRadio id="radioMarkSecurities" styleClass="label"  value="#{BankGuarantee.markSecurities}" style="width:80%;text-align:left;padding-right:200px;color:purple" >
                                        <f:selectItems value="#{BankGuarantee.marksSecuritiesList}"/>
                                        <a4j:support   ajaxSingle="true" focus="ddTypeOfSecurity" event="onclick" action="#{BankGuarantee.MarkSecurity}"reRender="lienMarkingPanel,SecurityPanel"
                                                       oncomplete="if(#{BankGuarantee.markSecurities == '2'})#{rich:component('SecurityPanel')}.show();
                                                       else if(#{BankGuarantee.markSecurities == '1'})#{rich:component('lienMarkingPanel')}.show();"/>
                                    </h:selectOneRadio>
                                </h:panelGrid>

                            <%--THIS IS SECURITY FORM JSP CODE--%>
                            <rich:modalPanel id="SecurityPanel" top="true" height="600" width="1000" onshow="#{rich:element('txtAcNo1')}.focus();">
                                <h:form>
                                    <a4j:form>
                                        <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                                            <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                                                <h:panelGroup id="a2" layout="block">
                                                    <h:outputLabel styleClass="headerLabel" value="Date: "/>
                                                    <h:outputText id="stxtDate" styleClass="headerLabel" value="#{BankGuarantee.todayDate}"/>
                                                </h:panelGroup>
                                                <h:outputLabel styleClass="headerLabel" value="Other"/>
                                                <h:panelGroup layout="block">
                                                    <h:outputLabel styleClass="headerLabel" value="User: "/>
                                                    <h:outputText id="stxtUser" styleClass="headerLabel" value="#{BankGuarantee.userName}"/>
                                                </h:panelGroup>
                                            </h:panelGrid>
                                            <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                                                <h:outputText id="messageLM" styleClass="msg" value="#{BankGuarantee.messageLM}"/>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow2" style="height:30px;" styleClass="row1" width="100%">
                                                <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Type Of Security "><font class="required" color="red">*</font></h:outputLabel>
                                                    <h:selectOneListbox id="ddTypeOfSecurity" styleClass="ddlist" style="width:134px"  size="1" value="#{BankGuarantee.securityType}">
                                                        <f:selectItems value="#{BankGuarantee.typeOfSecurityList}"/>
                                                        <a4j:support  ajaxSingle="true" action="#{BankGuarantee.changeLabel}" event="onblur" focus="#{rich:clientId('calEstimationDate')}InputDate"
                                                                      reRender="messageLM,lblEstimationDate,calEstimationDate,lblValuationAmt,secRow3,secRow4,ddSecurityDesc2,ddSecurityDesc3"/>
                                                    </h:selectOneListbox>
                                                    <h:outputLabel id="lblEstimationDate" styleClass="label" value="#{BankGuarantee.estimationDateLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <rich:calendar datePattern="dd/MM/yyyy" id="calEstimationDate" value="#{BankGuarantee.estimationDate}">
                                                        <a4j:support ajaxSingle="true" event="changed" focus="ddSecurityNature"/>
                                                    </rich:calendar>
                                                </h:panelGrid>
                                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="secRow3" style="height:30px;" width="100%" styleClass="row2">
                                                    <h:outputLabel id="lblSecurityNature" styleClass="label" value="Security Nature"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <h:selectOneListbox id="ddSecurityNature" styleClass="ddlist"  style="width:134px" size="1" value="#{BankGuarantee.securityNature}">
                                                        <f:selectItems value="#{BankGuarantee.securityNatureList}"/>
                                                        <a4j:support ajaxSingle="true" focus="#{rich:clientId('calRevalutionDate')}InputDate"/>
                                                    </h:selectOneListbox>
                                                    <h:outputLabel id="lblRevalutionDate" styleClass="label" value="#{BankGuarantee.revalutionDateLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <rich:calendar datePattern="dd/MM/yyyy" id="calRevalutionDate" value="#{BankGuarantee.revalutionDate}">
                                                        <a4j:support ajaxSingle="true" focus="ddSecurityDesc1"/>
                                                    </rich:calendar>
                                                </h:panelGrid>
                                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="secRow4" style="height:30px;" width="100%" styleClass="row1">
                                                    <h:outputLabel id="lblSecurityDesc1" styleClass="label" value="Security Desc (1)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <h:selectOneListbox id="ddSecurityDesc1" styleClass="ddlist"  size="1" style="width:134px" value="#{BankGuarantee.securityDesc1}">
                                                        <f:selectItems value="#{BankGuarantee.securityDesc1List}"/>
                                                        <a4j:support  ajaxSingle="true" action="#{BankGuarantee.onChangeOFSecurityDesc1}" event="onblur"
                                                                      reRender="messageLM,ddSecurityDesc2,ddSecurityDesc3" focus="txtParticular"/>
                                                    </h:selectOneListbox>
                                                    <h:outputLabel id="lblParticular" styleClass="label" value="Particular"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <h:inputText id="txtParticular" styleClass="input" maxlength = "255" onblur="this.value = this.value.toUpperCase();"
                                                                 value="#{BankGuarantee.particular}">
                                                        <a4j:support  ajaxSingle="true" action="#{BankGuarantee.setRemarksSec}" event="onchange"  reRender="messageLM,txtRemarksSec"/>
                                                    </h:inputText>
                                                </h:panelGrid>
                                                <h:panelGrid columnClasses="col9,col2,col7" columns="3" id="secRow5" style="height:30px;" width="100%" styleClass="row2">
                                                    <h:panelGrid columnClasses="col2,col7" columns="2" id="secRow115"style="height:30px;" width="100%" styleClass="row2">
                                                        <h:outputLabel id="lblSecurityDesc2" styleClass="label" value="Security Desc (2)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                        <h:panelGrid columnClasses="col5,col2,col1" columns="3" id="secRow116" style="width:100%;text-align:left;" styleClass="row2">
                                                            <h:selectOneListbox id="ddSecurityDesc2" styleClass="ddlist"  size="1" style="width:134px" value="#{BankGuarantee.securityDesc2}">
                                                                <f:selectItems value="#{BankGuarantee.securityDesc2List}"/>
                                                                <a4j:support  action="#{BankGuarantee.onChangeOFSecurityDesc2}" event="onblur" focus="txtValuationAmt"
                                                                              reRender="errmsgLM,ddSecurityDesc3,ddSecurityDesc2,stxtNameSecODScheme,stxtNameSecODRoi,txtMargin"/>
                                                            </h:selectOneListbox>
                                                        </h:panelGrid>
                                                    </h:panelGrid>
                                                    <h:outputLabel id="lblValuationAmt" styleClass="label" value="#{BankGuarantee.valuationAmtLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <h:inputText id="txtValuationAmt" styleClass="input" value="#{BankGuarantee.valuationAmt}">
                                                        <a4j:support ajaxSingle="true"  oncomplete="if(#{DlAccountOpeningRegister.securityflag=='true'}){#{rich:element('ddSecurityDesc3')}.focus();}else{#{rich:element('txtLienValue')}.focus();}"/>
                                                    </h:inputText>
                                                </h:panelGrid>
                                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow6" style="height:30px;" width="100%" styleClass="row1">
                                                    <h:outputLabel id="lblSecurityDesc3" styleClass="label"  value="Nature Of Charges"/>
                                                    <h:selectOneListbox id="ddSecurityDesc3" styleClass="ddlist"  disabled="#{BankGuarantee.disableSecDec3}"  size="1" style="width:134px" value="#{BankGuarantee.securityDesc3}">
                                                        <f:selectItems value="#{BankGuarantee.securityDesc3List}"/>
                                                        <a4j:support ajaxSingle="true" focus="txtLienValue"/>
                                                    </h:selectOneListbox>
                                                    <h:outputLabel id="lblLienValue" styleClass="label" value="Lien Value "><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <h:inputText id="txtLienValue" styleClass="input" value="#{BankGuarantee.lienValue}">
                                                        <a4j:support ajaxSingle="true" focus="ddStatus"/>
                                                    </h:inputText>
                                                </h:panelGrid>
                                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow7" style="height:30px;" width="100%" styleClass="row2">
                                                    <h:outputLabel id="lblStatus" styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <h:selectOneListbox id="ddStatus" styleClass="ddlist"  style="width:134px" size="1" value="#{BankGuarantee.status}" >
                                                        <f:selectItems value="#{BankGuarantee.statusList}"/>
                                                        <a4j:support ajaxSingle="true" focus="txtMargin"/>
                                                    </h:selectOneListbox>
                                                    <h:outputLabel id="lblMargin" styleClass="label" value="Margin(%)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                    <h:inputText id="txtMargin" styleClass="input" value="#{BankGuarantee.margin}"/>
                                                </h:panelGrid> 
                                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow8" style="height:30px;" width="100%" styleClass="row1">
                                                    <h:outputLabel id="lblOtherAC" styleClass="label" value="Other A/C "/>
                                                    <h:inputText id="txtOtherAC" styleClass="input" onblur="this.value = this.value.toUpperCase();" value="#{BankGuarantee.otherAc}"/>
                                                    <h:outputLabel id="lblRemarksSec" styleClass="label" value="Remarks "/>
                                                    <h:inputText id="txtRemarksSec" styleClass="input" maxlength = "255" onchange="this.value = this.value.toUpperCase();" value="#{BankGuarantee.remark}">
                                                        <a4j:support ajaxSingle="true" event="onblur" action="#{BankGuarantee.saveEnableSEC}" reRender="btnSaveSD,poFooterPanel"/>
                                                    </h:inputText>
                                                </h:panelGrid>
                                                <h:panelGrid columns="9" id="gpFooter1" style="width:100%;text-align:center;" styleClass="footer" >
                                                    <h:panelGroup layout="block" style="width:100%;text-align:center;" >
                                                        <a4j:commandButton id="btnSave2" tabindex="17" value="Save" disabled="#{BankGuarantee.fflag}" oncomplete="#{rich:component('savePanelSecuri')}.show()" reRender="savePanelSecuri,table,messageLM" focus="">
                                                        </a4j:commandButton>
                                                        <a4j:commandButton id="btnRefresh" tabindex="11" value="Refresh" ajaxSingle="true" action="#{BankGuarantee.resetValues}" reRender="messageLM,secRow2,secRow3,secRow4,secRow5,secRow6,secRow7,secRow8,SecurityDetailsTable" >
                                                        </a4j:commandButton>
                                                        <a4j:commandButton id="btnExit" tabindex="12" value="Exit"  ajaxSingle="true" onclick="#{rich:component('exitPanel')}.show()" reRender="messageLM,secRow2,secRow3,secRow4,secRow5,secRow6,secRow7,secRow8,SecurityDetailsTable">
                                                        </a4j:commandButton>
                                                    </h:panelGroup>
                                                </h:panelGrid>
                                            </h:panelGrid>
                                        </a4j:form>
                                    </h:form>
                                    <rich:modalPanel id="savePanelSecuri" autosized="true" width="200" onshow="#{rich:element('btnYesSavePan')}.focus();">
                                        <f:facet name="header">
                                            <h:outputText value="Do You Want To Save This Record" style="padding-right:15px;" />
                                        </f:facet>
                                        <h:form>
                                        <table width="100%">
                                            <tbody>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Yes" id="btnYesSavePan" ajaxSingle="true"  action="#{BankGuarantee.saveSecurityDetails}"
                                                                           oncomplete="#{rich:component('savePanelSecuri')}.hide();" reRender="messageLM,stxtmessage,SecurityPanel,table,taskList,vtop,btnRefresh,btnSave,errmsg"/>
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Cancel" id="btnNoSavePan" ajaxSingle="true" onclick="#{rich:component('savePanelSecuri')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </rich:modalPanel> 
                            <%--END OF SECURITY FORM JSP CODE--%>

                            <%--THIS IS TD LIEN MARKING FORM JSP CODE--%>
                            <rich:modalPanel id="lienMarkingPanel" top="true" height="600" width="1000" onshow="#{rich:element('txtAcNo1')}.focus();">
                                <h:form>
                                    <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                                        <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                                            <h:panelGroup id="a2" layout="block">
                                                <h:outputLabel styleClass="headerLabel" value="Date: "/>
                                                <h:outputText id="stxtDate" styleClass="headerLabel" value="#{BankGuarantee.todayDate}"/>
                                            </h:panelGroup>
                                            <h:outputLabel styleClass="headerLabel" value="Term Deposite Lien Marking"/>
                                            <h:panelGroup layout="block">
                                                <h:outputLabel styleClass="headerLabel" value="User: "/>
                                                <h:outputText id="stxtUser" styleClass="headerLabel" value="#{BankGuarantee.userName}"/>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                                            <h:outputText id="messageLM" styleClass="msg" value="#{BankGuarantee.messageLM}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" ><font class="required" color="red">*</font></h:outputLabel>
                                                <h:panelGroup layout="Acnoblock">
                                                    <h:inputText id="txtAcNo1" tabindex="1" size="20" maxlength="#{BankGuarantee.acNoMaxLen}" value="#{BankGuarantee.oldAcctNoLM}" styleClass="input">
                                                        <a4j:support action="#{BankGuarantee.customerDetail}" ajaxSingle="true" event="onblur" focus="ddTypeOfSecurity"
                                                                     reRender="messageLM,a3,a4,a5,a6,gpFooter,table,taskList,btnExit"/>
                                                    </h:inputText>
                                                    <h:outputLabel id="stxtlmAcNo" styleClass="label" value="#{BankGuarantee.acctNoLM}" ></h:outputLabel>
                                                </h:panelGroup>                                        
                                                <h:outputLabel id="lblHide3" value="" />
                                                <h:outputLabel id="lblHide4" value="" />
                                            </h:panelGrid>
                                            <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">

                                            <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" />
                                            <h:outputText id="stxtCustName" styleClass="output" value="#{BankGuarantee.custName}" style="color:purple;"/>
                                            <h:outputLabel id="lblJtName" styleClass="label" value="Joint Name :" />
                                            <h:outputText id="stxtJtName" styleClass="output" value="#{BankGuarantee.jtName}" style="color:purple;"/>
                                            <h:outputLabel id="lblOprMode" styleClass="label" value="Operation Mode :" />
                                            <h:outputText id="stxtOprMode" styleClass="output" value="#{BankGuarantee.oprMode}" style="color:purple;"/>

                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="securityType" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Type Of Security :" ><font class="required" color="red">*</font></h:outputLabel>
                                                <h:selectOneListbox id="ddTypeOfSecurity" styleClass="ddlist"  style="width:134px" size="1" value="#{BankGuarantee.typeOfSecurity}" >
                                                    <f:selectItems value="#{BankGuarantee.securityNatureList}"/>
                                                    <a4j:support ajaxSingle="true" event="onblur"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblTypeOfSecurity1" styleClass="label" />
                                                <h:outputLabel id="lblTypeOfSecurity2" styleClass="label" />
                                                <h:outputLabel id="lblTypeOfSecurity3" styleClass="label" />
                                                <h:outputLabel id="lblTypeOfSecurity4" styleClass="label" />

                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                            <a4j:region>
                                                <rich:dataTable value="#{BankGuarantee.gridDetailLM}" var="dataItem"
                                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                    <f:facet name="header">
                                                        <rich:columnGroup>
                                                            <rich:column colspan="16"><h:outputText value="Details" /></rich:column>
                                                            <rich:column breakBefore="true"><h:outputText value="A/C. No." /></rich:column>
                                                            <rich:column><h:outputText value="R.T. No." /></rich:column>
                                                            <rich:column><h:outputText value="Reciept No." /></rich:column>
                                                            <rich:column><h:outputText value="Control No." /></rich:column>
                                                            <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                                            <rich:column><h:outputText value="TD Made Date" /></rich:column>
                                                            <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                                            <rich:column><h:outputText value="Int Opt" /></rich:column>
                                                            <rich:column><h:outputText value="Int Table Code" /></rich:column>
                                                            <rich:column><h:outputText value="ROI" /></rich:column>
                                                            <rich:column visible="false"><h:outputText value="Status" /></rich:column>
                                                            <rich:column><h:outputText value="Lien Mark" /></rich:column>
                                                            <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                                        </rich:columnGroup>
                                                    </f:facet>
                                                    <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.reciept}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.printAmt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.fddt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.intTable}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                                    <rich:column visible="false"><h:outputText value="#{dataItem.status}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.lien}" /></rich:column>     
                                                    <rich:column style="text-align:center;width:40px">
                                                        <a4j:commandLink id="selectlink" ajaxSingle="true" action="#{BankGuarantee.fillValuesofGridInFieldsLM}"
                                                                         reRender="a5,a6,messageLM,gpFooter,table,taskList,btnSave,btnSave1" >
                                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                                            <f:setPropertyActionListener value="#{dataItem}" target="#{BankGuarantee.currentItemLM}"/>
                                                            <f:setPropertyActionListener value="#{row}" target="#{BankGuarantee.currentRowLM}"/>
                                                        </a4j:commandLink> 

                                                    </rich:column>

                                                </rich:dataTable>
                                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                                            </a4j:region>
                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblRecieptNo" styleClass="label" value="Reciept No. :" />
                                            <h:outputText id="stxtRecieptNo" styleClass="output" value="#{BankGuarantee.recieptNo}" style="color:purple;"/>
                                            <h:outputLabel id="lblControlNo" styleClass="label" value="Control No. :" />
                                            <h:outputText id="stxtControlNo" styleClass="output" value="#{BankGuarantee.controlNo}" style="color:purple;"/>
                                            <h:outputLabel id="lblPrinAmt" styleClass="label" value="Present Amount :" />
                                            <h:outputText id="stxtPrinAmt" styleClass="output" value="#{BankGuarantee.prinAmount}" style="color:purple;"/>
                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblLienStatus" styleClass="label" value="Lien Status :" />
                                            <h:outputText id="stxtLienStatus" styleClass="output" value="#{BankGuarantee.statusLien}" style="color:purple;"/>
                                            <h:outputLabel id="lblLienMarking" styleClass="label" value="Lien Marking :" ><font class="required" color="red">*</font></h:outputLabel>
                                                <h:selectOneListbox id="ddLienMarking" tabindex="3" styleClass="ddlist" value="#{BankGuarantee.lienMarkOption}" size="1" style="width: 120px">
                                                    <f:selectItems value="#{BankGuarantee.lienMarkOptionList}" />
                                                    <a4j:support  ajaxSingle="true" event="onblur" focus="txtDetails"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblDetails" styleClass="label" value="Details :" />
                                                <h:inputText id="txtDetails" tabindex="4" size="20" value="#{BankGuarantee.detail}" onblur="this.value = this.value.toUpperCase();" styleClass="input">
                                                    <a4j:support  ajaxSingle="true" event="onblur"/>
                                                </h:inputText>
                                            </h:panelGrid>
                                            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                                    <a4j:commandButton id="btnSave1" tabindex="17" value="Ok" disabled="#{BankGuarantee.flag1LM=='false'}" oncomplete="#{rich:component('savePanelInLien')}.show()" reRender="savePanelInLien,table,messageLM" focus="">
                                                    </a4j:commandButton>
                                                    <a4j:commandButton id="btnRefresh1" tabindex="19" value="Refresh" ajaxSingle="true" action="#{BankGuarantee.refreshOnInvalidAccNo}"  reRender="messageLM,txtAcNo1,stxtCustName,stxtJtName,stxtOprMode,ddTypeOfSecurity,table,stxtRecieptNo,stxtControlNo,stxtPrinAmt,stxtLienStatus,txtDetails,ddLienMarking" >
                                                    </a4j:commandButton>
                                                    <a4j:commandButton id="btnExit" tabindex="20" value="Exit" onclick="#{rich:component('exitPanel')}.show()" reRender="messageLM,savePanelInLien" >
                                                    </a4j:commandButton>

                                            </h:panelGroup>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:form>
                                <rich:modalPanel id="savePanelInLien" autosized="true" width="200" onshow="#{rich:element('btnYesSaveLien')}.focus();">
                                    <f:facet name="header">
                                        <h:outputText value="Do You Want To Save This Record ?" style="padding-right:15px;" />
                                    </f:facet>
                                    <h:form>
                                        <table width="100%">
                                            <tbody>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Yes" id="btnYesSaveLien" ajaxSingle="true" action="#{BankGuarantee.saveDetailLM}"
                                                                           oncomplete="#{rich:component('savePanelInLien')}.hide();" reRender="messageLM,lienMarkingPanel,table,taskList,btnRefresh,btnSave,errmsg" />
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Cancel" id="btnNoSaveLien" ajaxSingle="true" onclick="#{rich:component('savePanelInLien')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </rich:modalPanel>
                            <%--END OF TD LIEN MARKING FORM JSP CODE--%>
                            <rich:modalPanel id="exitPanel" autosized="true" width="200" onshow="#{rich:element('btnYesExit')}.focus();">
                                <f:facet name="header">
                                    <h:outputText value="Are You Sure To Exit?" style="padding-right:15px;" />
                                </f:facet>

                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" id="btnYesExit" ajaxSingle="true" action="#{BankGuarantee.resetPage()}" oncomplete="#{rich:component('lienMarkingPanel')}.hide(),#{rich:component('SecurityPanel')}.hide()"
                                                                       onclick="#{rich:component('exitPanel')}.hide();" reRender="lblMsg,SecurityPanel,lienMarkingPanel"/>
                                                </td>,
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" id="btnNoExit" ajaxSingle="true" onclick="#{rich:component('exitPanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel> 
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{BankGuarantee.currentItem}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="16"><h:outputText value="Details" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="A/c No." /></rich:column>
                                                <rich:column><h:outputText value="Lien A/c. No." /></rich:column>
                                                <rich:column><h:outputText value="Voucher No." /></rich:column>
                                                <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                                <rich:column><h:outputText value="Lien Amount" /></rich:column>
                                                <rich:column><h:outputText value="TD Made Date" /></rich:column>
                                                <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                                <rich:column><h:outputText value="Remark"/></rich:column>
                                                <rich:column><h:outputText value="New Status"/></rich:column>
                                                <rich:column><h:outputText value="Security Status"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.lienAcNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column> 
                                        <rich:column><h:outputText value="#{dataItem.printAmt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.lienValue}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.fddt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.details}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.guarSecStatus}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.lien}" />
                                            <a4j:support oncomplete="#{rich:component('selectPanel')}.show()" ajaxSingle="true" event="ondblclick">
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{BankGuarantee.currentItemSD}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{BankGuarantee.currentRowSD}" />
                                            </a4j:support>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="FooterPanel" bgcolor="#fff" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="btnPanel">
                                <h:commandButton id="printButton" tabindex="15" value="Print PDF" disabled="#{BankGuarantee.printDisable}" action="#{BankGuarantee.printPdfReport}"/>
                                <a4j:commandButton id="btnVerify" tabindex="16" value="Verify" disabled="#{BankGuarantee.flag1}" oncomplete="#{rich:component('verifyPanel')}.show()" reRender="verifyPanel,table,errmsg" focus="">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnSave" tabindex="17" value="Save" disabled="#{BankGuarantee.flag}" oncomplete="#{rich:component('savePanel')}.show()" reRender="savePanel,table,errmsg,btnRefresh" focus="">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnDelete" tabindex="18" value="Delete" disabled="#{BankGuarantee.deleteflag}" oncomplete="#{rich:component('deletePanel')}.show()" reRender="deletePanel,table,errmsg,btnRefresh" focus="">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnRefresh" value="Refresh"  disabled="#{BankGuarantee.flagRefresh}" action="#{BankGuarantee.reFresh}" reRender="PanelGridMain,lienMarkingPanel,SecurityPanel,btnSave"/>
                                <a4j:commandButton id="btnExit" value="Exit" disabled="#{BankGuarantee.flagExit}" action="#{BankGuarantee.exitBtnAction}" reRender="btnSave"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:region>
            </h:form>
            <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYesSave')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Save ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{BankGuarantee.saveDetailInfo}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="PanelGridMain,errmsg,btnRefresh,ddvalidityIn,txtPeriod" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoSave" ajaxSingle="true" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="verifyPanel" autosized="true" width="200" onshow="#{rich:element('btnYesVerify')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Verify?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesVerify" ajaxSingle="true" action="#{BankGuarantee.verifyLienDetailInfo}"
                                                       oncomplete="#{rich:component('verifyPanel')}.hide();" reRender="PanelGridMain,errmsg" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoVerify" ajaxSingle="true" onclick="#{rich:component('verifyPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="#{rich:element('btnYesDelete')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDelete" ajaxSingle="true" action="#{BankGuarantee.deleteDetailInfo}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="PanelGridMain,errmsg" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoDelete" ajaxSingle="true" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="selectPanel" autosized="true" width="200" onshow="#{rich:element('btnYesSelectSec')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Do You Want To Edit Status As EXPIRED ?" style="padding-right:15px;" />
                </f:facet>

                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSelectSec" ajaxSingle="true" action="#{BankGuarantee.select}"
                                                       onclick="#{rich:component('selectPanel')}.hide();" reRender="message,errmsg,taskList,btnRefresh,btnExit" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoSelectSec" onclick="#{rich:component('selectPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>