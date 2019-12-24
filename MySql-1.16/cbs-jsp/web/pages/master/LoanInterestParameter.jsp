<%-- 
    Document   : LoanInterestParameter
    Created on : 24 Mar, 2011, 11:46:48 AM
    Author     : Zeeshan Waris
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".issueDt").mask("99/99/9999");                    
                }
            </script>
            <title>Loan Interest Parameter</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanInterestParameter.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Loan Interest Parameter"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label14" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanInterestParameter.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" width="100%">
                        <h:panelGrid id="errorPanel" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                            <h:outputText id="stxtError" styleClass="error" value="#{LoanInterestParameter.message}"/>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblPurpose" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddPurpose" styleClass="ddlist" size="1" value="#{LoanInterestParameter.branch}"style="width:90px;">
                                    <f:selectItems value="#{LoanInterestParameter.branchList}"/>
                                </h:selectOneListbox>
                            <h:outputLabel id="lblParameter" styleClass="label" value="Parameter/Charge"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddParameter" styleClass="ddlist" size="1" value="#{LoanInterestParameter.parametreCharge}" style="width:90px;">
                                    <f:selectItems value="#{LoanInterestParameter.parametreChargeList}"/>
                                    <a4j:support action="#{LoanInterestParameter.allNatureList}" event="onblur" reRender="ddInterestType,ddAccountNature,stxtError"/>
                                </h:selectOneListbox>
                                <h:outputLabel/>
                             <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel21" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAccountNature" styleClass="label" value="Account Nature"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAccountNature" styleClass="ddlist" size="1" value="#{LoanInterestParameter.nature}" style="width:90px;">
                                    <f:selectItems value="#{LoanInterestParameter.natureList}"/>
                                    <a4j:support action="#{LoanInterestParameter.acctTypeList}" event="onblur" reRender="ddAccount"/>
                                </h:selectOneListbox>
                            <h:outputLabel id="lblAccount" styleClass="label" value="Account Type"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAccount" styleClass="ddlist" size="1" value="#{LoanInterestParameter.acctType}" style="width:90px;" >
                                    <f:selectItems value="#{LoanInterestParameter.intCodeList}"/>
                                    <a4j:support action="#{LoanInterestParameter.setIntOptions}" event="onblur" reRender="ddInterestType" focus="#{LoanInterestParameter.focusId}"/>
                                </h:selectOneListbox>
                            <h:outputLabel id="lblintType" styleClass="label" value="Interest Type" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddInterestType" styleClass="ddlist" size="1" value="#{LoanInterestParameter.interestType}" style="width:110px;" disabled="#{LoanInterestParameter.interestTypeDisabled}">
                                    <f:selectItems value="#{LoanInterestParameter.penalInterestOption}"/>
                                </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel19" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblintOption" styleClass="label" value="Interest Option" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddIntCode" styleClass="ddlist" size="1" value="#{LoanInterestParameter.intOption}" style="width:90px;">
                                    <f:selectItems value="#{LoanInterestParameter.interestTypeOption}"/>
                                 <%--a4j:support event="onblur" focus="ddFinancialYear" oncomplete="setMask();"/--%>
                                </h:selectOneListbox>
                            <h:outputLabel id="lblFinancialYear" styleClass="label" value="Financial Year" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFinancialYear" size="1" required="true" styleClass="ddlist"  value="#{LoanInterestParameter.financialYear}" style="width: 90px">
                                    <f:selectItems value="#{LoanInterestParameter.finYearOption}"/>
                                    <a4j:support action="#{LoanInterestParameter.setFinancialYearWiseDate}" event="onblur" oncomplete="setMask();" reRender="ddInterestType,txtFrmDate,txtToDate" focus="txtFrmDate"/>
                                </h:selectOneListbox>
                            <h:outputText value="Start Date: " styleClass="label"/>
                            <h:inputText id="txtFrmDate" size="10" styleClass="input issueDt" value="#{LoanInterestParameter.frDt}">
                                <%--a4j:support event="onblur" focus="btnSave" oncomplete="setMask();"/--%>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave"  value="Save" action="#{LoanInterestParameter.saveBtnAction}" reRender="mainPanel,stxtError"/>
                            <a4j:commandButton id="btnRefresh"  value="Refresh" action="#{LoanInterestParameter.refresh}"  reRender="mainPanel,stxtError" />
                            <a4j:commandButton id="btnExit"  value="Exit" action="#{LoanInterestParameter.exitForm()}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>