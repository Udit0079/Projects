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
            <title>Report Writer</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
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
            <h:form id="reportWriter">
                <h:panelGrid columns="1" id="a1" width="100%" style="ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{reportWriter.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Report Writer"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{reportWriter.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{reportWriter.errMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1">
                        <h:outputText value="Report Name" styleClass="label"/>
                        <h:inputText value="#{reportWriter.reportName}" styleClass="input" onkeyup="this.value = this.value.toUpperCase();"/>
                        
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="listbranch" value="#{reportWriter.branch}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{reportWriter.branchList}" />
                        </h:selectOneListbox> 
                        
                         <h:outputText value="Report Option" styleClass="label"/>
                        <h:selectOneListbox id="listReportOption" value="#{reportWriter.reportOption}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{reportWriter.reportOptionList}" />
                        </h:selectOneListbox> 
                        
                    </h:panelGrid>
                    <rich:panel header="Column Options">
                        <h:panelGrid id="panel2" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row2" style="margin:1px">
                            <h:outputText value="Customer id" styleClass="label" />
                            <h:selectBooleanCheckbox id="ddCustId" value="#{reportWriter.customerIdBoolean}"/>
                            <h:outputText  value="Name" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.nameBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Correspondence Address" styleClass="label"/>
                            <h:selectBooleanCheckbox value="#{reportWriter.custAddBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Permanent Address" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.perAddBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                        </h:panelGrid>
                        <h:panelGrid id="panel3" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row1" style="margin:1px">
                            <h:outputText  value="Date of Birth" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.dateofbrthBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText  value="Phone Number" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.phoneNoBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Gender " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.genderBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Father's Name " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.fatherNameBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                        </h:panelGrid>
                        <h:panelGrid id="panel4" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row2" style="margin:1px">
                            <h:outputText value="Occupation" styleClass="label"/>
                            <h:selectBooleanCheckbox value="#{reportWriter.occupationBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUnCheckOccupation}" event="onchange" reRender="ddCustId,ddOccupationWise"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Annual Income " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.annualIncomeBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUnCheckAnnualIncome}" event="onchange" reRender="ddCustId,txtAnnualIncomeFrom,txtAnnualIncomeTo"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Area/Sector " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.areaSectorBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUnCheckArea}" event="onchange" reRender="ddCustId,txtAreaSector"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Risk Categorization " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.riskCategorizationBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUnCheckRiskCat}" event="onchange" reRender="ddCustId,ddRiskCategorization"/>
                            </h:selectBooleanCheckbox>
                        </h:panelGrid>
                        <h:panelGrid id="panel5" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row1" style="margin:1px">
                            <h:outputText value="Age Wise " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.ageWiseBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUnCheckAge}" event="onchange" reRender="ddCustId,txtFromAge,txtToAge"/>
                            </h:selectBooleanCheckbox>
                             <h:outputText value="Pan No. " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.panBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Aadhar Card" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.aadharCardBoolean}">
                               <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid id="panel6" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row2" style="margin:1px">
                            <h:outputText value="Account No" styleClass="label"/> 
                            <h:selectBooleanCheckbox id="acnoCheckBox" value="#{reportWriter.acNoBoolean}">
                                <a4j:support actionListener="#{reportWriter.acnoUserCheckUncheckOperation}" event="onchange" reRender="basicChBok,daChBok,acnoCheckBox,listOrderby,selectListBox,listStatus"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Account Opening Date" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.accOpenDtBoolean}" >
                                <a4j:support actionListener="#{reportWriter.checkUncheckAcnoOption}" event="onchange" reRender="acnoCheckBox,listOrderby,selectListBox,listStatus"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Operating Mode" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.operatingBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckAcnoOption}" event="onchange" reRender="acnoCheckBox,listOrderby,selectListBox,listStatus"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Joint Holder Name" styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.jtNameBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckAcnoOption}" event="onchange" reRender="acnoCheckBox,listOrderby,selectListBox,listStatus"/>
                            </h:selectBooleanCheckbox>
                        </h:panelGrid>
                        <h:panelGrid id="panel7" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row1" style="margin:1px">
                            <h:outputText value="Nomine's Name " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.nomineNameBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckAcnoOption}" event="onchange" reRender="acnoCheckBox,listOrderby,selectListBox,listStatus"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Balance " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.balanceBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckAcnoOption}" event="onchange" reRender="acnoCheckBox,listOrderby,selectListBox,listStatus"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Average Balance " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.averageBalanceBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckAcnoOption}" event="onchange" reRender="acnoCheckBox"/>
                            </h:selectBooleanCheckbox>
                            <h:outputText value="Account Category " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.acctCategoryOption}">
                                <a4j:support actionListener="#{reportWriter.checkUncheckAcnoOption}" event="onchange" reRender="acnoCheckBox"/>
                            </h:selectBooleanCheckbox>
                        </h:panelGrid>
                        <h:panelGrid id="panel13" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row2" style="margin:1px">
                            
                            <%--h:outputText value="Kyc Details " styleClass="label" />
                            <h:selectBooleanCheckbox value="#{reportWriter.kycBoolean}">
                                <a4j:support actionListener="#{reportWriter.checkUnCheckKyc}" event="onchange" reRender="panel2,panel3,panel4,panel5,panel6,panel7,panel8,panel9,panel10,panel11"/>
                            </h:selectBooleanCheckbox--%>
                            
                            <h:outputText value="Basic" styleClass="label" />
                            <h:selectBooleanCheckbox id="basicChBok" value="#{reportWriter.basicBoolean}" disabled="#{reportWriter.acNoBoolean}"> 
                                <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId" />
                            </h:selectBooleanCheckbox>
                            <h:outputText value="DA" styleClass="label" />
                            <h:selectBooleanCheckbox id="daChBok" value="#{reportWriter.daBoolean}" disabled="#{reportWriter.acNoBoolean}">
                                 <a4j:support actionListener="#{reportWriter.checkUncheckCustDetailOption}" event="onchange" reRender="ddCustId"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/> 
                        </h:panelGrid>
                    </rich:panel>
                    <rich:panel header="Report Filter Options">
                        <h:panelGrid id="panel8" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="margin:1px">
                            <h:outputText value="Account Type" styleClass="label" />
                            <h:selectOneListbox id="selectListBox" value="#{reportWriter.selectAcType}" styleClass="ddlist" size="1"  style="width:50px" 
                                                disabled="#{reportWriter.disabledAcType}">
                                <f:selectItems id="selectAcTypeList" value="#{reportWriter.selectAcTypeList}" />
                                <a4j:support event="onblur" action="#{reportWriter.acTypeAction}" reRender="txtFromAcno,txtToAcno"/>
                            </h:selectOneListbox> 
                            <h:outputText value="Account Status" styleClass="label"/>
                            <h:selectOneListbox id="listStatus" value="#{reportWriter.status}" styleClass="ddlist" size="1"  style="width:160px"   disabled="#{reportWriter.disabledAcType}">
                                <f:selectItems value="#{reportWriter.statusList}" />
                            </h:selectOneListbox> 
                            <h:outputLabel id="lblOccupationWise" value="Occupation " styleClass="label"/>
                            <h:selectOneListbox id="ddOccupationWise" value="#{reportWriter.occupation}" styleClass="ddlist" size="1"  style="width:120px" disabled="#{reportWriter.disableOccupation}">
                                <f:selectItems value="#{reportWriter.occupationList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="panel9" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="margin:1px">
                            <h:outputLabel id="lblAnnualIncomeFrom" value="Annual Income From " styleClass="label"/>
                            <h:inputText id="txtAnnualIncomeFrom" value="#{reportWriter.annualIncomeFrom}" size="10" disabled="#{reportWriter.disableAnnualIncome}"/>
                            <h:outputLabel id="lblAnnualIncomeTo" value="Annual Income To " styleClass="label"/>
                            <h:inputText id="txtAnnualIncomeTo" value="#{reportWriter.annualIncomeTo}" size="10" disabled="#{reportWriter.disableAnnualIncome}"/>
                            <h:outputLabel id="lblArea" value="Area/Sector " styleClass="label"/>
                            <h:inputText id="txtAreaSector" value="#{reportWriter.areaSector}" size="10" disabled="#{reportWriter.disableArea}" onkeyup="this.value=this.value.toUpperCase();"/>
                        </h:panelGrid>
                        <h:panelGrid id="panel10" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="margin:1px">
                            <%--<h:outputLabel id="lblAverageBalance" value="Average Balance " styleClass="label"/>
                            <h:inputText id="txtAverageBalance" value="#{reportWriter.averageBalance}" size="10" disabled="#{reportWriter.disableAverageBal}"/>--%>
                            <h:outputLabel id="lblRiskCategorization" value="Risk Categorization " styleClass="label"/>
                            <h:selectOneListbox id="ddRiskCategorization" value="#{reportWriter.riskCategorization}" styleClass="ddlist" size="1" style="width:120px" disabled="#{reportWriter.disableRisk}">
                                <f:selectItems value="#{reportWriter.riskCategorizationList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblFromAge" value="From Age " styleClass="label"/>
                            <h:inputText id="txtFromAge" value="#{reportWriter.fromAge}" size="10" disabled="#{reportWriter.disableAge}"/>
                            <h:outputLabel id="lblToAge" value="To Age " styleClass="label"/>
                            <h:inputText id="txtToAge" value="#{reportWriter.toAge}" size="10" disabled="#{reportWriter.disableAge}"/>
                        </h:panelGrid>
                        <h:panelGrid id="panel11" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="margin:1px">
                            <h:outputLabel id="lblFromDate" styleClass="headerLabel"  value="From Date"  />
                            <h:panelGroup styleClass="label">
                                <h:inputText id="txtFromDate"  styleClass="input calInstDate" style="width:70px"  maxlength="40" value="#{reportWriter.frmDate}"> 
                                    <f:convertDateTime pattern="dd/MM/yyyy" /> </h:inputText>
                                    <font color="purple" >DD/MM/YYYY</font>
                                </h:panelGroup>
                                <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="To Date"  />
                                <h:panelGroup styleClass="label">
                                    <h:inputText id="txtToDate"   styleClass="input calInstDate" style="width:70px"  value="#{reportWriter.toDate}" >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                <font color="purple">DD/MM/YYYY</font>
                                </h:panelGroup>
                                <h:outputText value="Order By" styleClass="label"/>
                                <h:selectOneListbox id="listOrderby" value="#{reportWriter.orderBy}" styleClass="ddlist" size="1"  style="width:120px" >
                                    <f:selectItems value="#{reportWriter.orderByList}" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="panel12" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="margin:1px">
                                <h:outputText value="Customer Status" styleClass="label" />
                                <h:selectOneListbox id="selectCustStatusListBox" value="#{reportWriter.custStatus}" styleClass="ddlist" size="1"  style="width:50px" >
                                    <f:selectItems id="selectCustStatusList" value="#{reportWriter.custStatusList}" />
                                </h:selectOneListbox>
                                <h:outputLabel styleClass="label" value="From A/c. No"/>
                                <h:inputText id="txtFromAcno" styleClass="input" onkeyup="this.value = this.value.toUpperCase()" 
                                             style="width:80px" maxlength="6" value="#{reportWriter.txtFromAcno}" disabled="#{reportWriter.disableAcnoRange}"/>
                                <h:outputLabel styleClass="label" value="To A/c. No"/>
                                <h:inputText id="txtToAcno" styleClass="input" onkeyup="this.value = this.value.toUpperCase()" 
                                             style="width:80px" maxlength="6" value="#{reportWriter.txtToAcno}" disabled="#{reportWriter.disableAcnoRange}"/>
                            </h:panelGrid>
                        </rich:panel>
                        <h:panelGrid  columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                            <a4j:region id="btnRgn">
                                <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                    <h:commandButton  value="Download Excel"  actionListener="#{reportWriter.viewReport}"/>
                                    <h:commandButton  value="Download Pdf"  actionListener="#{reportWriter.pdfReport}" />
                                    <h:commandButton  value="View Report"  actionListener="#{reportWriter.htmlReport}" disabled ="true"/>
                                    <a4j:commandButton value="Refresh" action="#{reportWriter.refresh}" reRender="a1" oncomplete="setMask()"/>
                                    <a4j:commandButton   value="Exit" action="case1" reRender="a1"/>
                                </h:panelGroup>
                            </a4j:region>
                        </h:panelGrid>
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnRgn"/>
                        <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                            <f:facet name="header">
                                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                            </f:facet>
                        </rich:modalPanel>
                    </h:panelGrid>
                </h:form>
        </body>
    </html>
</f:view>
