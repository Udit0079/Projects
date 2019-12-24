<%-- 
    Document   : SecurityDetails
    Created on : 7 Aug, 2010, 12:06:28 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Security Details</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="SecurityDetails"/>
            <a4j:form id="SecurityDetails">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SecurityDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Security Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SecurityDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{SecurityDetails.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                            <h:outputText id="stxtName" styleClass="output" value="#{SecurityDetails.name}"/>
                            <h:outputLabel id="lblAccount" styleClass="label" value="Account"/>
                            <h:outputText id="stxtAccount" styleClass="output" value="#{SecurityDetails.acno}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="2" id="MarkSecuritiesRow" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblMarkSecurities" styleClass="label" value="Mark Securities : " style="color:purple;padding-left:200px;font-size:15px;"/>
                            <h:selectOneRadio id="MarkSecurities" styleClass="label"  value="#{SecurityDetails.markSecurities}" style="width:80%;text-align:left;padding-right:200px;color:purple" >
                                <f:selectItems value="#{SecurityDetails.marksSecuritiesList}"/>
                                <a4j:support   ajaxSingle="true" focus="ddTypeOfSecurity" event="onclick" actionListener="#{SecurityDetails.MarkSecurity}"reRender="Row2,Row3,lienMarkingPanel,accountStatus,btnSaveSD,outerPanel"
                                               oncomplete="if(#{SecurityDetails.markSecurities == '3'})#{rich:component('lienMarkingPanel')}.hide();
                                               else if(#{SecurityDetails.markSecurities == '2'})#{rich:component('lienMarkingPanel')}.show();
                                               else if(#{SecurityDetails.markSecurities == '1'})#{rich:component('accountStatus')}.show();"/>
                            </h:selectOneRadio>
                        </h:panelGrid >
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2" rendered="#{SecurityDetails.markSecurities == '3'}">
                            <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Type Of Security "/>
                            <h:selectOneListbox id="ddTypeOfSecurity" tabindex="1" styleClass="ddlist" style="width:134px"  size="1" value="#{SecurityDetails.securityType}">
                                <f:selectItems value="#{SecurityDetails.typeOfSecurityList}"/>
                                <a4j:support  ajaxSingle="true" actionListener="#{SecurityDetails.changeLabel}" event="onblur" focus="ddSecurityNature"
                                              reRender="lblMsg,lblEstimationDate,calEstimationDate,lblRevalutionDate,lblValuationAmt,ddSecurityDesc1,ddSecurityDesc2,ddSecurityDesc3"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblEstimationDate" styleClass="label" value="#{SecurityDetails.estimationDateLbl}"/>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="8" id="calEstimationDate" value="#{SecurityDetails.estimationDate}">
                                <a4j:support ajaxSingle="true" event="changed" focus="ddSecurityNature"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row1" rendered="#{SecurityDetails.markSecurities == '3'}">
                            <h:outputLabel id="lblSecurityNature" styleClass="label" value="Security Nature"/>
                            <h:selectOneListbox id="ddSecurityNature" tabindex="2" styleClass="ddlist"  style="width:134px" size="1" value="#{SecurityDetails.securityNature}">
                                <f:selectItems value="#{SecurityDetails.securityNatureList}"/>
                                <a4j:support ajaxSingle="true" focus="ddSecurityDesc1"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblRevalutionDate" styleClass="label" value="#{SecurityDetails.revalutionDateLbl}"/>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="9" id="calRevalutionDate" value="#{SecurityDetails.revalutionDate}">
                                <a4j:support ajaxSingle="true" focus="ddSecurityDesc1"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row2" rendered="#{SecurityDetails.markSecurities == '3'}">
                            <h:outputLabel id="lblSecurityDesc1" styleClass="label" value="Security Desc (1)"/>
                            <h:selectOneListbox id="ddSecurityDesc1" tabindex="3" styleClass="ddlist"  size="1" style="width:134px" value="#{SecurityDetails.securityDesc1}">
                                <f:selectItems value="#{SecurityDetails.securityDesc1List}"/>
                                <a4j:support  ajaxSingle="true" actionListener="#{SecurityDetails.onChangeOFSecurityDesc1}" event="onblur"
                                              reRender="lblMsg,ddSecurityDesc2,ddSecurityDesc3" focus="ddSecurityDesc2"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblParticular" styleClass="label" value="Particular"/>
                            <h:inputText id="txtParticular" tabindex="10" styleClass="label" maxlength = "255" onblur="this.value = this.value.toUpperCase();"
                                         value="#{SecurityDetails.particular}">
                                <a4j:support  ajaxSingle="true" actionListener="#{SecurityDetails.setRemarks}" event="onchange"  reRender="lblMsg,txtRemarks"/>
                            </h:inputText>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row1" rendered="#{SecurityDetails.markSecurities == '3'}">
                            <h:outputLabel id="lblSecurityDesc2" styleClass="label" value="Security Desc (2)"/>
                            <h:selectOneListbox id="ddSecurityDesc2" tabindex="4" styleClass="ddlist"  size="1" style="width:134px" value="#{SecurityDetails.securityDesc2}">
                                <f:selectItems value="#{SecurityDetails.securityDesc2List}"/>
                                <a4j:support  actionListener="#{SecurityDetails.onChangeOFSecurityDesc2}" event="onblur" focus="ddSecurityDesc3"
                                              reRender="lblMsg,ddSecurityDesc3,ddSecurityDesc2"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblValuationAmt" styleClass="label" value="#{SecurityDetails.valuationAmtLbl}"/>
                            <h:inputText id="txtValuationAmt" tabindex="11" styleClass="label" value="#{SecurityDetails.valuationAmt}">
                                <a4j:support ajaxSingle="true" focus="ddSecurityDesc1"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row6" style="width:100%;text-align:left;" styleClass="row2" rendered="#{SecurityDetails.markSecurities == '3'}">
                            <h:outputLabel id="lblSecurityDesc3" styleClass="label"  value="Nature Of Charges"/>
                            <h:selectOneListbox id="ddSecurityDesc3" tabindex="5" styleClass="ddlist"  disabled="#{SecurityDetails.disableSecDec3}"  size="1" style="width:134px" value="#{SecurityDetails.securityDesc3}">
                                <f:selectItems value="#{SecurityDetails.securityDesc3List}"/>
                                <a4j:support ajaxSingle="true" focus="ddStatus"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblLienValue" styleClass="label" value="Lien Value "/>
                            <h:inputText id="txtLienValue" tabindex="12" styleClass="label" value="#{SecurityDetails.lienValue}">
                                <a4j:support ajaxSingle="true" focus="ddStatus"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row7" style="width:100%;text-align:left;" styleClass="row1" rendered="#{SecurityDetails.markSecurities == '3'}">
                            <h:outputLabel id="lblStatus" styleClass="label" value="Status"/>
                            <h:selectOneListbox id="ddStatus" tabindex="6" styleClass="ddlist"  style="width:134px" size="1" value="#{SecurityDetails.status}" >
                                <f:selectItems value="#{SecurityDetails.statusList}"/>
                                <a4j:support ajaxSingle="true" focus="txtOtherAC"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblMargin" styleClass="label" value="Margin(%)"/>
                            <h:inputText id="txtMargin" tabindex="13" styleClass="label" value="#{SecurityDetails.margin}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row8" style="width:100%;text-align:left;" styleClass="row2" rendered="#{SecurityDetails.markSecurities == '3'}">
                            <h:outputLabel id="lblOtherAC" styleClass="label" value="Other A/C "/>
                            <h:inputText id="txtOtherAC" tabindex="7" styleClass="label" onblur="this.value = this.value.toUpperCase();" value="#{SecurityDetails.otherAc}"/>
                            <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks "/>
                            <h:inputText id="txtRemarks" tabindex="14" styleClass="label" maxlength = "255" onblur="this.value = this.value.toUpperCase();" value="#{SecurityDetails.remark}">
                                <a4j:support ajaxSingle="true" focus="txtMargin" actionListener="#{SecurityDetails.saveEnable}" reRender="btnSaveSD"/>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="SecurityDetails"  width="100%" styleClass="row2">
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete=" #{rich:component('deletePanel')}.show()"

                                           actionListener="#{SecurityDetails.fetchCurrentRow}">
                                <a4j:actionparam name="Maturity Value" value="{Maturity Value}" />
                                <a4j:actionparam name="row" value="{currentRowSD}" />
                            </rich:menuItem>
                        </rich:contextMenu>
                        <a4j:region>
                            <rich:dataTable value ="#{SecurityDetails.securityDetail}" 
                                            rowClasses="row1, row2" id = "SecurityDetailsTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="17"></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                        <rich:column><h:outputText value="Maturity Value" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                        <rich:column><h:outputText value="Issue Date" /></rich:column>
                                        <rich:column><h:outputText value="Value at Lien Time" /></rich:column>
                                        <rich:column><h:outputText value="Type" /></rich:column>
                                        <rich:column><h:outputText value="LastSTM Dt" /></rich:column>
                                        <rich:column><h:outputText value="DP Limit" /></rich:column>
                                        <rich:column><h:outputText value="STM Date" /></rich:column>
                                        <rich:column><h:outputText value="NextSTM Dt" /></rich:column>
                                        <rich:column><h:outputText value="STM Frequency" /></rich:column>
                                        <rich:column><h:outputText value="Remark" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Entered By" /></rich:column>
                                        <rich:column><h:outputText value="Entered Date" /></rich:column>
                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                        <rich:column><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                                <rich:column><h:outputText value="#{item.matValue}"/></rich:column>
                                <rich:column><h:outputText value="#{item.matDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.issueDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.lienValue}"/></rich:column>
                                <rich:column><h:outputText value="#{item.type}"/></rich:column>
                                <rich:column><h:outputText value="#{item.ltSTMDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.DPLimit}"/></rich:column>
                                <rich:column><h:outputText value="#{item.STMDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.nxSTMDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.STMFrequency}"/></rich:column>
                                <rich:column><h:outputText value="#{item.remark}"/></rich:column>
                                <rich:column><h:outputText value="#{item.status}"/>
                                    <a4j:support oncomplete="#{rich:component('selectPanel')}.show()" ajaxSingle="true" event="ondblclick">
                                        <f:setPropertyActionListener value="#{item}" target="#{SecurityDetails.currentItemSD}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{SecurityDetails.currentRowSD}" />
                                    </a4j:support>
                                </rich:column>
                                <rich:column><h:outputText value="#{item.enteredBy}"/></rich:column>
                                <rich:column><h:outputText value="#{item.enteredDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.particular}"/></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="Updatelink" oncomplete="#{rich:component('UpdatePanel')}.show()">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{SecurityDetails.currentItemSD}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{SecurityDetails.currentRowSD}" />
                                    </a4j:commandLink>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{item}" target="#{SecurityDetails.currentItemSD}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{SecurityDetails.currentRowSD}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="SecurityDetailsTable" maxPages="40" />
                    </h:panelGrid>
                    <rich:modalPanel id="UpdatePanel" autosized="true" width="200" onshow="#{rich:element('btnYesUpdateSec')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Are You Sure To Edit The Security Details Of SNo #{SecurityDetails.currentItemSD.sno} ?" style="padding-right:15px;" />
                        </f:facet>

                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesUpdateSec" ajaxSingle="true" action="#{SecurityDetails.Updateselect}"
                                                               onclick="#{rich:component('UpdatePanel')}.hide();" reRender="leftPanel,ddTypeOfSecurity,ddStatus,btnUpdateSD" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" id="btnNoUpdateSec" onclick="#{rich:component('UpdatePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="selectPanel" autosized="true" width="200" onshow="#{rich:element('btnYesSelect')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Do You Want To Edit Status As EXPIRED ?" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesSelect" ajaxSingle="true" action="#{SecurityDetails.select}"
                                                               onclick="#{rich:component('selectPanel')}.hide();" reRender="SecurityDetailsTable,lblMsg" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" id="btnNoSelect" onclick="#{rich:component('selectPanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel> 
                    <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="#{rich:element('btnYesDelSec')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Are You Sure To Delete The Security Details ?" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesDelSec" ajaxSingle="true" action="#{SecurityDetails.delete}"
                                                               onclick="#{rich:component('deletePanel')}.hide();" reRender="SecurityDetailsTable,lblMsg" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" id="btnNodelSec" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <%--THIS IS TD LIEN MARKING FORM JSP CODE--%>
                    <rich:modalPanel id="lienMarkingPanel" top="true" onshow="#{rich:element('txtAcNo')}.focus();" height="600" width="1000">
                        <h:form>
                            <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                                <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                                    <h:panelGroup id="a2" layout="block">
                                        <h:outputLabel styleClass="headerLabel" value="Date: "/>
                                        <h:outputText id="stxtDate" styleClass="headerLabel" value="#{SecurityDetails.todayDate}"/>
                                    </h:panelGroup>
                                    <h:outputLabel styleClass="headerLabel" value="Term Deposite Lien Marking"/>
                                    <h:panelGroup layout="block">
                                        <h:outputLabel styleClass="headerLabel" value="User: "/>
                                        <h:outputText id="stxtUser" styleClass="headerLabel" value="#{SecurityDetails.userName}"/>
                                    </h:panelGroup>
                                </h:panelGrid>
                                <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                                    <h:outputText id="errorMessage" styleClass="error" value="#{SecurityDetails.errorMessage}"/>
                                    <h:outputText id="message" styleClass="msg" value="#{SecurityDetails.messageLM}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                    <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" ><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtAcNo" tabindex="1" size="20" maxlength="12" value="#{SecurityDetails.acctNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                        <a4j:support actionListener="#{SecurityDetails.customerDetail}" ajaxSingle="true" event="onblur" focus="ddTypeOfSecurity"
                                                     reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList" limitToList="true" />
                                    </h:inputText>
                                    <h:outputLabel id="lblHide1" value="" />
                                    <h:outputLabel id="lblHide2" value="" />
                                    <h:outputLabel id="lblHide3" value="" />
                                    <h:outputLabel id="lblHide4" value="" />
                                </h:panelGrid>
                                <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                    <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" />
                                    <h:outputText id="stxtCustName" styleClass="output" value="#{SecurityDetails.custName}" style="color:purple;"/>
                                    <h:outputLabel id="lblJtName" styleClass="label" value="Joint Name :" />
                                    <h:outputText id="stxtJtName" styleClass="output" value="#{SecurityDetails.jtName}" style="color:purple;"/>
                                    <h:outputLabel id="lblOprMode" styleClass="label" value="Operation Mode :" />
                                    <h:outputText id="stxtOprMode" styleClass="output" value="#{SecurityDetails.oprMode}" style="color:purple;"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" id="securityType" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                    <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Type Of Security :" />
                                    <h:selectOneListbox id="ddTypeOfSecurity" styleClass="ddlist"  style="width:134px" size="1" value="#{SecurityDetails.typeOfSecurity}" >
                                        <f:selectItems value="#{SecurityDetails.securityNatureList}"/>
                                        <a4j:support ajaxSingle="true" event="onblur"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblTypeOfSecurity1" styleClass="label" />
                                    <h:outputLabel id="lblTypeOfSecurity2" styleClass="label" />
                                    <h:outputLabel id="lblTypeOfSecurity3" styleClass="label" />
                                    <h:outputLabel id="lblTypeOfSecurity4" styleClass="label" />

                                </h:panelGrid>
                                <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                    <a4j:region>
                                        <rich:dataTable value="#{SecurityDetails.gridDetail}" var="dataItem"
                                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                                    <rich:column breakBefore="true"><h:outputText value="A/C. No." /></rich:column>
                                                    <rich:column><h:outputText value="R.T. No." /></rich:column>
                                                    <rich:column><h:outputText value="Reciept No." /></rich:column>
                                                    <rich:column><h:outputText value="Control No." /></rich:column>
                                                    <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                                    <rich:column><h:outputText value="TD Made Date" /></rich:column>
                                                    <rich:column><h:outputText value="TD Date (wef)" /></rich:column>
                                                    <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                                    <rich:column><h:outputText value="Int Opt" /></rich:column>
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
                                            <rich:column><h:outputText value="#{dataItem.tdmatDt}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                            <rich:column visible="false"><h:outputText value="#{dataItem.status}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.lien}" /></rich:column>
                                            <rich:column style="text-align:center;width:40px">
                                                <a4j:commandLink id="selectlink" actionListener="#{SecurityDetails.fillValuesofGridInFields}"
                                                                 reRender="a5,a6,message,errorMessage,gpFooter,table,taskList" focus="btnSave">
                                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                                    <f:setPropertyActionListener value="#{dataItem}" target="#{SecurityDetails.currentItem}"/>
                                                    <f:setPropertyActionListener value="#{row}" target="#{SecurityDetails.currentRow}"/>
                                                </a4j:commandLink>
                                            </rich:column>
                                        </rich:dataTable>
                                        <rich:datascroller align="left" for="taskList" maxPages="20" />
                                    </a4j:region>
                                </h:panelGrid>
                                <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                    <h:outputLabel id="lblRecieptNo" styleClass="label" value="Reciept No. :" />
                                    <h:outputText id="stxtRecieptNo" styleClass="output" value="#{SecurityDetails.recieptNo}" style="color:purple;"/>
                                    <h:outputLabel id="lblControlNo" styleClass="label" value="Control No. :" />
                                    <h:outputText id="stxtControlNo" styleClass="output" value="#{SecurityDetails.controlNo}" style="color:purple;"/>
                                    <h:outputLabel id="lblPrinAmt" styleClass="label" value="Present Amount :" />
                                    <h:outputText id="stxtPrinAmt" styleClass="output" value="#{SecurityDetails.prinAmount}" style="color:purple;"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                    <h:outputLabel id="lblLienStatus" styleClass="label" value="Lien Status :" />
                                    <h:outputText id="stxtLienStatus" styleClass="output" value="#{SecurityDetails.statusLien}" style="color:purple;"/>
                                    <h:outputLabel id="lblLienMarking" styleClass="label" value="Lien Marking :" />
                                    <h:selectOneListbox id="ddLienMarking" tabindex="3" styleClass="ddlist" value="#{SecurityDetails.lienMarkOption}" size="1" style="width: 127px">
                                        <f:selectItems value="#{SecurityDetails.lienMarkOptionList}" />
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="txtDetails"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblDetails" styleClass="label" value="Details :" />
                                    <h:inputText id="txtDetails" tabindex="4" size="20" value="#{SecurityDetails.detail}" onblur="this.value = this.value.toUpperCase();" styleClass="input">
                                        <a4j:support  ajaxSingle="true" event="onblur"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                    <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                        <a4j:commandButton id="btnSave" tabindex="17" value="Save" disabled="#{SecurityDetails.flag1}" oncomplete="#{rich:component('savePanelInLien')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList" focus=""/>
                                        <a4j:commandButton id="btnRefresh" tabindex="19" value="Refresh" ajaxSingle="true" action="#{SecurityDetails.resetForm}" oncomplete="#{rich:element('btnSave')}.disabled = true;" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList" />
                                        <a4j:commandButton id="btnExit" tabindex="20" value="Exit" onclick="#{rich:component('exitPanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList,savePanelInLien" />
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:form>
                        <rich:modalPanel id="savePanelInLien" autosized="true" width="200" onshow="#{rich:element('btnYesLienSave')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Are You Sure To Select This Voucher No. ?" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYesLienSave" ajaxSingle="true" action="#{SecurityDetails.saveDetail}"
                                                                   oncomplete="#{rich:component('savePanelInLien')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList,SecurityDetailsTable" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" id="btnNoLienSave" ajaxSingle="true" onclick="#{rich:component('savePanelInLien')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </rich:modalPanel>
                    <%--END OF TD LIEN MARKING FORM JSP CODE--%>
                    <%--START OF ACCOUNT STATUS FORM JSP CODE--%>
                    <rich:modalPanel id="accountStatus" top="true" onshow="#{rich:element('txtAccountNumber')}.focus();" height="600" width="1000">
                        <h:form>
                            <a4j:form>
                                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                                        <h:panelGroup id="groupPanel" layout="block" >
                                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                                            <h:outputText id="stxtDate" styleClass="output" value="#{SecurityDetails.todayDate}"/>
                                        </h:panelGroup>
                                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Account Status"/>
                                        <h:panelGroup id="groupPanel2" layout="block">
                                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                                            <h:outputText id="stxtUser" styleClass="output" value="#{SecurityDetails.userName}" />
                                        </h:panelGroup>
                                    </h:panelGrid>
                                    <h:panelGrid columns="1" id="d22" style="width:100%;text-align:center;" styleClass="row1"  >
                                        <h:outputText id="stxtmessage" styleClass="output" value="#{SecurityDetails.messageAcct}"  style="width:100%;color:red"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col7" columns="2" id="a9" width="100%">
                                        <h:panelGrid columns="1" id="accountType" style="width:100%;text-align:center;" styleClass="row2" width="100%"  >
                                            <h:outputLabel id="lblAccountNumber" styleClass="headerLabel"  value="Account Number"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                        </h:panelGrid>
                                        <h:panelGrid columns="3" columnClasses="col7,col7" id="AccountNumber" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                <h:inputText id="txtAccountNumber" styleClass="input" maxlength="12"style="width: 80px" value="#{SecurityDetails.oldcode}" onkeyup="this.value = this.value.toUpperCase();">
                                                    <a4j:support id="a4j2" ajaxSingle="true" event="onblur" limitToList="true" focus="ddnStatus"reRender="stxtmessage,txtAccountNumber,txtName,txtpStatus,a19,taskList,btnSave,a91,d91,txtRemarks,ddnStatus,stxtNewStatusAcno" actionListener="#{SecurityDetails.custName}"/>
                                                </h:inputText>
                                                <h:outputText id="stxtNewStatusAcno" value="#{SecurityDetails.acnoAcct}" styleClass="output" style="color:green" />
                                            </h:panelGroup>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col7" columns="2" id="c9" width="100%">
                                        <h:panelGrid columns="1"  id="Name" style="width:100%;text-align:center;"  styleClass="row1" width="100%"  >
                                            <h:outputLabel id="lblName" styleClass="headerLabel"  value="Name"  style="width:100%;text-align:left;"></h:outputLabel>
                                        </h:panelGrid>
                                        <h:panelGrid columns="1"  id="Name1" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                            <h:outputText id="txtName" styleClass="output" value="#{SecurityDetails.nameAcct}" />
                                        </h:panelGrid>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col7" columns="2" id="a91" width="100%">
                                        <h:panelGrid columns="2" columnClasses="col7,col7" id="pStatus" style="height:30px;"  styleClass="row2" width="100%"  >
                                            <h:outputLabel id="lblpStatus" styleClass="headerLabel"  value="Present Status"  style="width:100%;text-align:left;"> </h:outputLabel>
                                            <h:outputText id="txtpStatus" styleClass="output" value="#{SecurityDetails.pStatus}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="nStatus"  style="height:30px;"  styleClass="row2" width="100%"  >
                                            <h:outputLabel id="lblnStatus" styleClass="headerLabel"  value="New Status"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                <h:selectOneListbox id="ddnStatus" styleClass="ddlist" size="1" style="width: 100px"   value="#{SecurityDetails.nStatus}">
                                                    <f:selectItem itemValue="--Select--"/>
                                                    <f:selectItem itemValue="Lien Marked"/>
                                                    <a4j:support ajaxSingle="true" id="soppt" event="onchange" actionListener="#{SecurityDetails.save1}"  reRender="d91,stxtmessage" />
                                                </h:selectOneListbox>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col7" columns="2" id="d91" width="100%">
                                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="lien" style="height:30px;"  styleClass="row2" rendered="#{SecurityDetails.flagAcct=='true'}" width="100%"  >
                                            <h:outputLabel id="lbllien" styleClass="headerLabel"  value="Lien Account No."  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                <h:selectOneListbox id="ddlien" styleClass="ddlist" size="1" style="width: 100px"  disabled="#{SecurityDetails.flagLienMark}" value="#{SecurityDetails.lienacctype}">
                                                    <f:selectItem itemValue="--Select--"/>
                                                    <f:selectItems value="#{SecurityDetails.lienacctOption}"/>
                                                    <a4j:support ajaxSingle="true"  event="onchange"/>
                                                </h:selectOneListbox>
                                                <h:inputText id="txtlienAccountNumber" styleClass="input" disabled="#{SecurityDetails.flagLienMark}" style="width: 130px" value="#{SecurityDetails.liencode}">
                                                    <a4j:support ajaxSingle="true"  event="onchange"/>
                                                </h:inputText>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                        <h:panelGrid columns="2" columnClasses="col7,col7" id="ll" style="height:30px;"  rendered="#{SecurityDetails.flagAcct=='true'}" styleClass="row2" width="100%"  >
                                            <h:outputLabel id="lbllamt" styleClass="headerLabel"  value="Lien Amount"  style="width:150%;text-align:left;"> </h:outputLabel>
                                            <h:inputText id="txtlamt" styleClass="input" value="#{SecurityDetails.lienAmt}">
                                                <a4j:support ajaxSingle="true"  event="onchange"/>
                                            </h:inputText>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col7" columns="2" id="a92" width="100%">
                                        <h:panelGrid columns="3" columnClasses="col7,col7,col7" id="wefDate" style="height:30px;"  styleClass="row1" width="100%"  >
                                            <h:outputLabel id="lblwefDate" styleClass="headerLabel"  value="W.E.F.Date"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                <rich:calendar id="ddwefDate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{SecurityDetails.wefDate}">
                                                    <a4j:support ajaxSingle="true" actionListener="#{SecurityDetails.onblurWefDate}" event="onchanged" reRender="stxtmessage" focus="txtRemarks"/>
                                                </rich:calendar>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="remarks" style="height:30px;"   styleClass="row1" width="100%"  >
                                            <h:outputLabel id="lblRemarks" styleClass="headerLabel"  value="Remarks"  style="width:100%;text-align:left;"></h:outputLabel>
                                            <h:inputText id="txtRemarks" styleClass="input" style="width: 200px" disabled="#{SecurityDetails.fflag=='false'}" value="#{SecurityDetails.remarks}">
                                                <a4j:support ajaxSingle="true"  event="onchange"/>
                                            </h:inputText>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col7" columns="2" id="a93" width="100%">
                                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate" style="height:30px;"  styleClass="row2" width="100%"  >
                                            <h:outputLabel id="lblReportDate" styleClass="headerLabel"  value="Report Date"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                            <rich:calendar id="ddReportDate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{SecurityDetails.reportDate}">
                                                <a4j:support ajaxSingle="true" actionListener="#{SecurityDetails.onblurReportDate}" event="onchanged" reRender="stxtmessage" focus="includeFl"/>
                                            </rich:calendar>
                                        </h:panelGrid>
                                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate1" style="height:30px;"  styleClass="row2" width="100%"  >
                                            <h:selectBooleanCheckbox id="includeFl" >
                                                <a4j:support ajaxSingle="true" event="onclick">
                                                    <a4j:actionparam name="rowId" value="#{result.rowId}" />
                                                </a4j:support>
                                                <h:outputLabel id="lblnForAllStatusReport" styleClass="headerLabel"  value="For All Status Report"  style="width:100%;text-align:left;"></h:outputLabel>
                                            </h:selectBooleanCheckbox>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                                        <a4j:region>
                                            <rich:dataTable value="#{SecurityDetails.incis}" var="dataItem"
                                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="8"><h:outputText value="Details" /></rich:column>
                                                        <rich:column breakBefore="true"  ><h:outputText  value="AccountNo"  /></rich:column>
                                                        <rich:column><h:outputText value="Remark"/></rich:column>
                                                        <rich:column><h:outputText value="Status" /></rich:column>
                                                        <rich:column><h:outputText value="Date" /></rich:column>
                                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                                        <rich:column><h:outputText value="Auth" /></rich:column>
                                                        <rich:column><h:outputText value="EnterBy" /></rich:column>
                                                        <rich:column ><h:outputText value="EfftDate"/></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column ><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                                <rich:column ><h:outputText value="#{dataItem.remark}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.spFlag}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.date}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.auth}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.efftDate}" /></rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller  align="left" for="taskList"  maxPages="20" />
                                        </a4j:region>
                                    </h:panelGrid>
                                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                                        <h:panelGroup id="a1" layout="block" >
                                            <a4j:commandButton id="btnSave" value="Save" disabled="#{SecurityDetails.fflag=='false'}" oncomplete="#{rich:component('savePanel')}.show()" reRender="stxtmessage,txtAccountNumber,txtName,ddlien,txtlienAccountNumber,txtlamt,nStatus,txtpStatus,txtRemarks,taskList,btnSave,a19,ddwefDate,ddReportDate,acnoAcct"/>
                                            <a4j:commandButton id="btnRefresh" value="Refresh" ajaxSingle="true" reRender="stxtmessage,txtAccountNumber,txtName,ddlien,txtlienAccountNumber,txtlamt,nStatus,txtpStatus,txtRemarks,taskList,btnSave,a19,ddwefDate,ddReportDate,acnoAcct" action="#{SecurityDetails.reFresh}"/>
                                            <a4j:commandButton id="btnExit" value="Exit" ajaxSingle="true" onclick="#{rich:component('exitPanel')}.show()" reRender="stxtmessage,a9,c9,d91,a91,a92,a93,a19"/>
                                        </h:panelGroup>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </a4j:form>
                            <rich:modalPanel id="savePanel" autosized="true" width="200">
                                <f:facet name="header">
                                    <h:outputText value="Do You Want To Save This Record" style="padding-right:15px;" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                        <rich:componentControl for="savePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{SecurityDetails.save}"
                                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="stxtmessage,a9,c9,d91,a91,a92,a93,a19,SecurityDetailsTable"/>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" ajaxSingle="true" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>

                        </h:form>
                    </rich:modalPanel>
                    <%--END OF ACCOUNT STATUS FORM JSP CODE--%>
                    <rich:modalPanel id="exitPanel" autosized="true" width="200" onshow="#{rich:element('btnYesExitSec')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Are You Sure To Exit?" style="padding-right:15px;" />
                        </f:facet>

                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesExitSec" ajaxSingle="true" action="#{SecurityDetails.clearModelPanel}" oncomplete="#{rich:component('lienMarkingPanel')}.hide(),#{rich:component('accountStatus')}.hide()"
                                                               onclick="#{rich:component('exitPanel')}.hide();" reRender="lblMsg,btnSave,a1,leftPanel,mainPanel,SecurityDetailsTable"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" id="btnNoExitSec" ajaxSingle="true" onclick="#{rich:component('exitPanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>

                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnUpdateSD" value="Update" tabindex="15" disabled="#{SecurityDetails.updateDisable}" action="#{SecurityDetails.UpdateTable}" reRender="lblMsg,leftPanel,SecurityDetails,leftPanel"/>
                            <a4j:commandButton id="btnSaveSD" value="Save" tabindex="16" disabled="#{SecurityDetails.saveDisable}" action="#{SecurityDetails.saveSecurityDetails}" reRender="lblMsg,leftPanel,SecurityDetails"/>
                            <a4j:commandButton id="btnRefreshSD" value="Refresh" tabindex="17" action="#{SecurityDetails.resetPage}" reRender="lblMsg,leftPanel,SecurityDetails"/>
                            <a4j:commandButton id="btnExitSD" value="Exit" action="#{SecurityDetails.exitToAcCloseForm}" tabindex="18"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
