<%-- 
    Document   : BankLeadManagement
    Created on : 15 May, 2018, 5:12:20 PM
    Author     : Ng Sharma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title> Bank Lead Management </title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <%-- today --%>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BankLeadManagement.todayDate}"/>
                        </h:panelGroup>
                        <%--tittle And username--%>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Bank Lead Management"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BankLeadManagement.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%--message--%>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{BankLeadManagement.message}"/>
                    </h:panelGrid>
                    <%--function and customerID--%>
                    <h:panelGrid columns="1" id="gridPanel4" width="100%">                        
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanelFunc" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="funcLabel" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunc" styleClass="ddlist"  style="width:80px;" size="1" value="#{BankLeadManagement.function}">
                                    <f:selectItems value="#{BankLeadManagement.functionList}"/>
                                    <a4j:support action="#{BankLeadManagement.setFnOption}" event="onblur" reRender="txtBusinessGst,txtBusinessPan,stxtBankAccNo,txtCustId,stxtMsg,btnSave,txtComapnyName,txtAccountName,txtFirstName,txtLastName,txtEmail,txtmobile,stxtbankname,stxtBankIfsc,gridPanel103,taskList,ddBusinessCate,businessTypeList" focus="txtCustId"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="label1" styleClass="label" value="Customer ID"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCustId" styleClass="input" maxlength="10" value="#{BankLeadManagement.custId}" disabled="#{BankLeadManagement.isCustomerId}" style="width:80px;">
                                    <%--<rich:ajax listener="#{BankLeadManagement.bankBusinessDetails}" reRender="stxtbankname,stxtBankIfsc,stxtBankAccNo,txtBusinessPan,txtBusinessGst" /> 
                                    <a4j:log level="ALL" popup="false" width="400" height="200"/>    --%>
                                    <a4j:support action="#{BankLeadManagement.bankBusinessDetails}" event="onblur" reRender="stxtbankname,stxtBankIfsc,stxtBankAccNo,txtBusinessPan,txtBusinessGst,stxtMsg,txtComapnyName,txtAccountName" /> 
                                </h:inputText>
                            </h:panelGrid>
                            <%--bankname and ifsc--%>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="bankNameLabel" styleClass="label" value="Bank Name"></h:outputLabel>
                                <h:outputText id="stxtbankname" styleClass="output" value="#{BankLeadManagement.bankName}" style="width:80px;"/> <%--apply the reReader --%>
                                <h:outputLabel id="label11" styleClass="label" value="Bank IFSC" />
                                <h:inputText id="stxtBankIfsc" maxlength="11" styleClass="input" value="#{BankLeadManagement.bankIfsc}" disabled="#{BankLeadManagement.isIFSC}"> <%--apply the reReader --%>
                                    <a4j:support action="#{BankLeadManagement.validateBankIfsc}" event="onblur" reRender="stxtMsg"/>
                                </h:inputText>
                            </h:panelGrid>
                            <%-- Bank Accaount No. and PAN--%>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel14" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label9" styleClass="label" value="Bank Account No."/>
                                <h:inputText id="stxtBankAccNo" maxlength="12" styleClass="input" value="#{BankLeadManagement.bankAccoNo}" disabled="#{BankLeadManagement.isAccountNo}">
                                    <a4j:support action="#{BankLeadManagement.validateBankAccountNumber}" event="onblur" reRender="stxtMsg" />
                                </h:inputText>
                                <h:outputLabel id="label12" styleClass="label" value="Business PAN"/>
                                <h:inputText id="txtBusinessPan" maxlength="10" styleClass="input" value="#{BankLeadManagement.pan}" disabled="#{BankLeadManagement.isPan}">
                                    <a4j:support action="#{BankLeadManagement.validateBusinessPANNumber}" event="onblur" reRender="stxtMsg" />
                                </h:inputText>
                            </h:panelGrid>

                        <%-- Business GST--%>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label13" styleClass="label" value="Business GST"/>
                            <h:inputText id="txtBusinessGst" styleClass="input" maxlength="15" value="#{BankLeadManagement.businessGst}" disabled="#{BankLeadManagement.isGST}">
                                <a4j:support action="#{BankLeadManagement.validateBusinessGSTNumber}" event="onblur" reRender="stxtMsg" />
                            </h:inputText>
                            <h:outputLabel id="empty1" styleClass="label" value=" "/>
                            <h:outputLabel id="empty2" styleClass="label" value=" "/>
                        </h:panelGrid>
                        <%--company Name and Accout Name --%>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="6" id="gridPane120"  styleClass="row1">        
                            <h:outputLabel value="Company Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtComapnyName" styleClass="input" value="#{BankLeadManagement.companyName}" disabled="#{BankLeadManagement.isCompanyDisable}">
                                    <a4j:support action="#{BankLeadManagement.validateCompanyName}" event="onblur" reRender="stxtMsg" />
                                </h:inputText>
                                <h:outputLabel value="Account Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtAccountName" styleClass="input" value="#{BankLeadManagement.accountName}" disabled="#{BankLeadManagement.isAccountNameDisable}">
                                    <a4j:support action="#{BankLeadManagement.validateAccountName}" event="onblur" reRender="stxtMsg" />
                                </h:inputText>
                            </h:panelGrid>

                        <%--business catgory and Type --%>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5"  styleClass="row2">
                            <h:outputLabel  value="Business Category" styleClass="label"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                <%--apply reReader --%>
                                <h:selectOneListbox id="ddBusinessCate" styleClass="ddlist"  style="width:120px;" size="1" value="#{BankLeadManagement.businessCate}">
                                    <f:selectItems value="#{BankLeadManagement.businessCategoryList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="Businesstype"value="Business Type" styleClass="label"> <font class="required" style="color:red;">*</font></h:outputLabel> 
                                <%--apply reReader --%>
                                <h:selectOneListbox id="ddBusinessType" styleClass="ddlist"  style="width:120px;" size="1" value="#{BankLeadManagement.businessType}">
                                    <f:selectItems value="#{BankLeadManagement.businessTypeList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <%-- first and Last Name--%>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="6" id="gridPane121"  styleClass="row2">       
                                <h:outputLabel value="First Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                                <h:inputText id="txtFirstName" styleClass="input" value="#{BankLeadManagement.firstName}" disabled="#{BankLeadManagement.isFristNAmeDisable}" onkeyup="this.value=this.value.toUpperCase();">
                                    <a4j:support action="#{BankLeadManagement.validateFirstName}" event="onblur" reRender="stxtMsg"/>
                                </h:inputText>
                                <h:outputLabel value="Last Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                                <h:inputText id="txtLastName" styleClass="input" value="#{BankLeadManagement.lastName}" disabled="#{BankLeadManagement.isLastName}" onkeyup="this.value=this.value.toUpperCase();">
                                    <a4j:support action="#{BankLeadManagement.validateLastName}" event="onblur" reRender="stxtMsg" />
                                </h:inputText>
                            </h:panelGrid>
                            <%--Email and Mobile --%>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel19" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label15" styleClass="label" value="Email"><font class="required" style="color:red;">*</font></h:outputLabel> 
                                <h:inputText id="txtEmail" value="#{BankLeadManagement.email}"  disabled="#{BankLeadManagement.isEmailDisable}" styleClass="input" style="width:150px">
                                    <a4j:support action="#{BankLeadManagement.validateEmail}" event="onblur" reRender="stxtMsg" />
                                </h:inputText>
                                <h:outputLabel id="label16" styleClass="label" value="Mobile"><font class="required" style="color:red;">*</font></h:outputLabel> 
                                <%-- apply reReader--%>
                                <h:inputText id="txtmobile" maxlength="10" value="#{BankLeadManagement.mobile}" disabled="#{BankLeadManagement.isMobileDisable}" styleClass="input" style="width:100px">
                                    <a4j:support action="#{BankLeadManagement.validateMobilNumber}" event="onblur" reRender="stxtMsg" />
                                </h:inputText>
                            </h:panelGrid>                        
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnSave" value="#{BankLeadManagement.btnChangeName}" oncomplete="#{rich:component('processPanel')}.show();" disabled="#{BankLeadManagement.isbtnSave}" reRender="mainPanel"/>   
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BankLeadManagement.resetForm}" reRender="mainPanel" focus="ddFunc"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{BankLeadManagement.exitFrm}"/>
                            </h:panelGroup>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:195px;display:#{BankLeadManagement.othVisible};">
                        <rich:dataTable value="#{BankLeadManagement.custBusinessDataList}" var="dataItem" rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="15"><h:outputText value="Customer Bank Details"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Customer Id"/></rich:column>
                                    <rich:column><h:outputText value="Company Name"/></rich:column>
                                    <rich:column><h:outputText value="First Name"/></rich:column>
                                    <rich:column><h:outputText value="Last Name"/></rich:column>
                                    <rich:column><h:outputText value="Email"/></rich:column>
                                    <rich:column><h:outputText value="Mobile"/></rich:column>
                                    <rich:column><h:outputText value="Business Category ID" /></rich:column>
                                    <rich:column><h:outputText value="Business Type ID" /></rich:column>
                                    <rich:column><h:outputText value="Bank Name" /> </rich:column>
                                    <rich:column><h:outputText value="Bank IFSc" /> </rich:column>
                                    <rich:column><h:outputText value="Bank Account No." /></rich:column>
                                    <rich:column><h:outputText value="Bank PAN" /> </rich:column>
                                    <rich:column><h:outputText value="Business GST"/> </rich:column>
                                    <rich:column><h:outputText value="Enter By"/></rich:column>
                                    <%--<rich:column><h:outputText value="Last Update By"/></rich:column>
<rich:column><h:outputText value="Flag"/></rich:column>--%>
                                    <rich:column><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>

                            <rich:column><h:outputText value="#{dataItem.customerId}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.companyName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.firstName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.lastName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.email}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.mobile}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.businessCatDescription}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.businessTypeDescription}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.bankName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.bankIfsc}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.bankAccountNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.bankPanNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.businessGSt}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.username}"/></rich:column>
                            <%--<rich:column><h:outputText value="#{dataItem.dateUserFormat}"/></rich:column>
<rich:column><h:outputText value="#{dataItem.flag}"/></rich:column>--%>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{BankLeadManagement.setModifyData}" 
                                                 reRender="mainPanel">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{BankLeadManagement.currentAirPayRecord}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />                            
                    </h:panelGrid>
                </h:panelGrid>
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
                                            <h:outputText id="confirmid" value="Are you sure to process it ?"/> <%-- changing here--%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYes" action="#{BankLeadManagement.save}" onclick="#{rich:component('processPanel')}.hide();" 
                                                               reRender="mainPanel"/>
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
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>            
        </body>
    </html>
</f:view>
