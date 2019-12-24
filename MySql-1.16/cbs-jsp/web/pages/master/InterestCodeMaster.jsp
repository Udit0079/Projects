<%--
    Document   : InterestCodeVersion
Created on : 3 Sep, 2010, 5:46:03 PM
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
            <title>Interest Code Master</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">            
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InterestCodeMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Interest Code Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InterestCodeMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>


                    <h:panelGrid id="richPanel" width="100%">
                        <h:panelGrid columnClasses="col1,col2,col1,col2" columns="3" id="FramPanelMsg" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{InterestCodeMaster.msg}" />
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col1,col3,col3,col3,col3" columns="6" id="FramPanel1" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblInterestTableCode" styleClass="label"  value="Interest Table Code"/>
                            <h:panelGroup id ="HelpButtom">
                                <h:inputText id="txtInterestTableCode" style="width:70px"  styleClass="input" value="#{InterestCodeMaster.intTableCode}" disabled="#{InterestCodeMaster.intTableCodeDisable}" onkeyup="this.value = this.value.toUpperCase();" >
                                    <a4j:support action="#{InterestCodeMaster.setDataInField}" event="onblur"
                                                 reRender="txtInterestRateDescription,FramPanel1,FramPanel2,FramPanel3,FramPanel6,FramPanel4,FramPanel5,FramPanel9,FramPanel7,FramPanel8,slabTable,lblMsg,btnSave,btnAddNewSlab,gridPanel103,gridPanel22" focus="txtInterestRateDescription"/>
                                </h:inputText>
                                <a4j:commandButton id="btnHelp"  onclick="#{rich:component('showPanel')}.show();" value="..." style="width:30px;height:20px"
                                                   action="#{InterestCodeMaster.getTableDetails}"  reRender="btnHelp,mainPane2" >
                                    <rich:toolTip for="btnHelp" value="For Code and Description please click this button."></rich:toolTip>
                                </a4j:commandButton>
                            </h:panelGroup>
                            <h:outputLabel id="lblInterestRateDescription" styleClass="label"  value="Interest Rate Description"/>
                            <h:inputText id="txtInterestRateDescription" style="width:160px" styleClass="input" value="#{InterestCodeMaster.intRateDes}" onkeypress="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblCurrencyCode" styleClass="label"  value="Currency Code"/>
                            <h:selectOneListbox id="ddCurrencyCode" styleClass="ddlist" style="width:100px" size ="1" disabled="#{InterestCodeMaster.currencyCodeDisable}" value="#{InterestCodeMaster.currencyCode}">
                                <f:selectItems value="#{InterestCodeMaster.currencyCodeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col1,col3,col3,col3,col3" columns="6" id="FramPanel2" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblRecordStatus" styleClass="label"  value="Record Status"/>
                            <h:selectOneListbox id="ddRecordStatus" styleClass="ddlist" style="width:100px" size ="1" value="#{InterestCodeMaster.recordStatusMs}">
                                <f:selectItems value = "#{InterestCodeMaster.recordStatusList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblStartDt" styleClass="label"  value="Start Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calStartDt" value = "#{InterestCodeMaster.startDate}" inputSize="10"/>
                            <h:outputLabel id="lblEndDt" styleClass="label"  value="End Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calEndDt" value = "#{InterestCodeMaster.endDate}" inputSize="10"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col1,col3,col3,col3,col3" columns="6" id="FramPanel3" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblBaseIntTable" styleClass="label"  value="Base Interest Table Code"/>
                            <h:selectOneListbox id="ddBaseIntTable" styleClass="ddlist" style="width:100px" size ="1" value="#{InterestCodeMaster.baseIntTableCode}">
                                <a4j:support action="#{InterestCodeMaster.setBaseIndFlag}" event="onblur"
                                             reRender="txtBaseIndFlag,txtPerCebit,txtPerDebit" focus="txtBaseIndFlag"/>
                                <f:selectItems value="#{InterestCodeMaster.baseIntTableCodeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblBaseIndFlag" styleClass="label"  value="Base Indicator Flag"/>
                            <h:inputText id="txtBaseIndFlag" style="width:100px" value="#{InterestCodeMaster.baseindFlag}"
                                         onkeyup="this.value = this.value.toUpperCase();" disabled="#{InterestCodeMaster.baseIndFlabDisable}"
                                         styleClass="input"/>
                            <h:outputLabel id="lblPerCebit" styleClass="label"  value="Base Percentage Cebit"/>
                            <h:inputText id="txtPerCebit" style="width:100px" value="#{InterestCodeMaster.basePerCredit}" styleClass="input" disabled="#{InterestCodeMaster.creditDisable}" >
                                <a4j:support action="#{InterestCodeMaster.setDebitDisable}" event="onblur"
                                             reRender="txtPerDebit,txtPerCebit,lblMsg" focus="txtPerDebit" />
                            </h:inputText>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col1,col3,col3,col3,col3" columns="6" id="FramPanel6" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblPerDebit" styleClass="label"  value="Base Percentage Debit"/>
                            <h:inputText id="txtPerDebit" style="width:100px" value="#{InterestCodeMaster.basePerDebit}" styleClass="input" disabled="#{InterestCodeMaster.debitDisable}">
                                <a4j:support action="#{InterestCodeMaster.setCreditDisable}" event="onblur"
                                             reRender="txtPerDebit,txtPerCebit,lblMsg" focus="txtIntTblVerDesc" />
                            </h:inputText>
                            <h:outputLabel id="lblIntVerNo" styleClass="label"  value="Interest Version No"/>
                            <h:outputText id="txtIntVerNo" style="width:100px" styleClass="input" value="#{InterestCodeMaster.intVerNo}"/>
                            <h:outputLabel id="lblIntTblVerDesc" styleClass="label"  value="Interest Table Version Description"/>
                            <h:inputText id="txtIntTblVerDesc" style="width:100px" styleClass="input" value="#{InterestCodeMaster.intTableDesc}"
                                         onkeyup="this.value = this.value.toUpperCase();"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col1,col3,col3,col3,col3" columns="6" id="FramPanel4" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblInterestVersionRemark" styleClass="label"  value="Interest Version Remark"/>
                            <h:inputText id="txtInterestVersionRemark" style="width:100px" styleClass="input" value="#{InterestCodeMaster.intVerRemark}"  onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblModtficationCounter" styleClass="label"  value="Record Modification Counter"/>
                            <h:outputText id="stxtModtficationCounter" styleClass="output" value="#{InterestCodeMaster.modificationMasterCounter}"/>
                            <h:outputLabel id="lblCreatedBy" styleClass="label"  value="Created By"/>
                            <h:outputText id="stxtCretedBy" styleClass="output" value="#{InterestCodeMaster.createdBy}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col1,col3,col3,col3,col3" columns="6" id="FramPanel5" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblCreatedDate" styleClass="label"  value="Created Date"/>
                            <h:outputText id="stxtCreatedDate" styleClass="output" value="#{InterestCodeMaster.createdDate}"/>
                            <h:outputLabel id="lblUpdatedBy" styleClass="label"  value="Last Updated By" />
                            <h:outputText id="stxtUpdatedBy" style="width:100px" styleClass="output" value="#{InterestCodeMaster.lastUpdatedBy}"/>
                            <h:outputLabel id="lblUpdatedDate" styleClass="label"  value="Last Updated Date"/>
                            <h:outputText id="stxtUpdatedDate" styleClass="output" value="#{InterestCodeMaster.lastUpdatedDate}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <rich:panel  id="gridPanel22" style="width:100%;text-align:left;" header="Slab">
                        <h:panelGrid  columns="14" id="subPanel2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:panelGroup id="subFromPanel112" >
                                <h:outputLabel id="lblLoanPeriodDays" styleClass="label"  value="Loan Period Months/Days" />
                                <h:inputText id="txtLoanPeriodMonths" style="width:80px" styleClass="input" value="#{InterestCodeMaster.loanPeriodMonths}" disabled="#{InterestCodeMaster.loanMonthDisable}">
                                    <a4j:support  action="#{InterestCodeMaster.validation1}" event="onblur" reRender="txtLoanPeriodDays,lblMsg,txtBeginSlabAmount"/> 
                                </h:inputText>

                                <h:outputLabel id="lblSeperator" styleClass="label" value="/"/>
                                <h:inputText id="txtLoanPeriodDays" style="width:80px" styleClass="input" value="#{InterestCodeMaster.loanPeriodDays}"disabled="#{InterestCodeMaster.loanDaysDisable}">
                                    <a4j:support  action="#{InterestCodeMaster.validation2}" event="onblur" reRender="txtLoanPeriodMonths,lblMsg,txtBeginSlabAmount"/>
                                </h:inputText>
                            </h:panelGroup>

                            <h:panelGroup id="subPanel118" >
                                <h:outputLabel id="lblBeginSlabAmount" style="margin-left:20px" styleClass="label"  value="Begin Slab Amount       "/>
                                <h:inputText id="txtBeginSlabAmount" style="width:80px" styleClass="input" value="#{InterestCodeMaster.bgSlabAmt}" disabled="#{InterestCodeMaster.bgSlabAmtDisable}"/>
                            </h:panelGroup>

                            <h:panelGroup id="subPanel19" >
                                <h:outputLabel id="lblEndSlabAmount" style="margin-left:50px" styleClass="label"  value="End Slab Amount    "/>
                                <h:inputText id="txtEndSlabAmount" style="width:80px" styleClass="input" value="#{InterestCodeMaster.endSlabAmt}" disabled="#{InterestCodeMaster.endSlabAmtDisable}">
                                    <a4j:support actionListener="#{InterestCodeMaster.checkSlab}" event="onblur"
                                                 reRender="lblMsg" focus="ddNormalInt" />
                                </h:inputText>
                            </h:panelGroup>

                            <h:panelGroup id="subFromPanel13" >
                                <h:outputLabel id="lblNormalInt" style="margin-left:40px" styleClass="label"  value="Normal Int Indicator    "/>
                                <h:selectOneListbox id="ddNormalInt" styleClass="ddlist" style="width:80px" size ="1" value="#{InterestCodeMaster.normalIntInd}" disabled="#{InterestCodeMaster.normalIntIndicatorDisable}">
                                    <f:selectItems value="#{InterestCodeMaster.normalIntIndList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblNormalIntPer" style="margin-left:20px" styleClass="label"  value="Percentage(%)"/>
                                <h:inputText id="txtNormalIntPer" style="width:80px"  styleClass="input" value="#{InterestCodeMaster.normalIntPer}" disabled="#{InterestCodeMaster.normalIntIndicatorDisable}"/>
                            </h:panelGroup>

                            

                        </h:panelGrid>

                        <h:panelGrid  columns="18" id="subPanel1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:panelGroup id="subFromPanel14" >
                                <h:outputLabel id="lblpenalInt" styleClass="label"  value="Peenal Int Indicator    "/>
                                <h:selectOneListbox id="ddpenalInt" styleClass="ddlist" style="width:80px" size ="1" value="#{InterestCodeMaster.peenalIntInd}" disabled="#{InterestCodeMaster.peenalIntIndicatorDisable}">
                                    <f:selectItems value="#{InterestCodeMaster.peenalIntIndList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblpenalIntPer" styleClass="label"  value="Percentage(%)  " />
                                <h:inputText id="txtpenalIntPer" style="width:40px" styleClass="input" value="#{InterestCodeMaster.peenalIntPer}" disabled="#{InterestCodeMaster.peenalIntIndicatorDisable}"/>
                            </h:panelGroup>
                            <%-- comment by manish kumar 18-05-s17
                            <h:panelGroup id="subFromPanel15" >
                                <h:outputLabel id="lblCleanInt" styleClass="label"  value="Clean Portion Indicator    "/>
                                <h:selectOneListbox id="ddCleanInt" styleClass="ddlist" style="width:45px" size ="1" value="#{InterestCodeMaster.cleanPortionInd}" disabled="#{InterestCodeMaster.cleanPortionIndicatorDisable}">
                                    <f:selectItems value="#{InterestCodeMaster.cleanPortionIndList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblCleanIntPer" styleClass="label"  value="Percentage(%)   "/>
                                <h:inputText id="txtCleanIntPer" style="width:40px" styleClass="input" value="#{InterestCodeMaster.cleanIntPer}" disabled="#{InterestCodeMaster.cleanPortionIndicatorDisable}"/>
                            </h:panelGroup>

                            <h:panelGroup id="subFromPanel16" >
                                <h:outputLabel id="lblQISInt" styleClass="label"  value="QIS Portion Indicator    "/>
                                <h:selectOneListbox id="ddQISInt" styleClass="ddlist" style="width:45px" size ="1" value="#{InterestCodeMaster.qisPortionInd}" disabled="#{InterestCodeMaster.qisPortionIndicatorDisable}">
                                    <f:selectItems value="#{InterestCodeMaster.qisPortionList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblQISIntPer" styleClass="label"  value="Percentage(%) "/>
                                <h:inputText id="txtlblQISIntPer" style="width:40px" styleClass="input" value="#{InterestCodeMaster.qisIntPer}" disabled="#{InterestCodeMaster.qisPortionIndicatorDisable}"/>
                            </h:panelGroup>
                            --%>
                            <h:panelGroup id="subPanel17" >
                                <h:outputLabel id="lblAddIntInd" styleClass="label"  value="Additional Interest Indicator "/>
                                <h:selectOneListbox id="ddAddIntInd" styleClass="ddlist" style="width:45px" size ="1" value="#{InterestCodeMaster.additionalInd}" disabled="#{InterestCodeMaster.additionalInterestIndicatorDisable}">
                                    <f:selectItems value="#{InterestCodeMaster.addPortionList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAddIntPer" styleClass="label"  value="Percentage(%) "/>
                                <h:inputText id="txtAddIntPer" style="width:40px" styleClass="input" value="#{InterestCodeMaster.additionalInt}"disabled="#{InterestCodeMaster.additionalInterestIndicatorDisable}" >
                                </h:inputText>
                            </h:panelGroup >
                            
                            <h:panelGroup id="subFromPanel11" >
                                <h:outputLabel id="lblIntSlabDC" style="margin-left:15px" styleClass="label"  value="Int Slab  D/C  Flag "/>
                                <h:selectOneListbox id="ddIntSlabDC" styleClass="ddlist" style="width:80px" size ="1" value="#{InterestCodeMaster.intSlabDcFlag}" disabled="#{InterestCodeMaster.intSlabDcFlagDisable}">
                                    <f:selectItems value="#{InterestCodeMaster.intFlagCrDeFlgList}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                            <h:panelGroup id="subFromPanel40" >
                                <h:outputLabel id="lblIntSlabVerRemark" styleClass="label"  value="Interest Slab Version Remarks"/>
                                <h:inputText id="txtIntSlabVerRemark" style="width:60px" styleClass="input" value="#{InterestCodeMaster.intSlabVerRemark}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{InterestCodeMaster.interestSlabVesionRemarkDisable}"/>
                            </h:panelGroup>
                            <h:panelGroup id="subFromPanel21" >
                                <h:outputLabel id="lblIntSlabDes" styleClass="label"  value="Interest Slab Description" />
                                <h:inputText id="txtIntSlabDes" style="width:80px" styleClass="input" value="#{InterestCodeMaster.intSlabDes}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{InterestCodeMaster.interestSlabDescDisable}"/>
                            </h:panelGroup>
                            <h:panelGroup id="subPanel18" >
                                <h:outputLabel id="lblRecord" styleClass="label"  value="Record Status "/>
                                <h:selectOneListbox id="ddRecord" styleClass="ddlist" style="width:80px" size ="1" value="#{InterestCodeMaster.recordStatus}" disabled="#{InterestCodeMaster.recordStatusDisable}">
                                    <a4j:support action="#{InterestCodeMaster.setTable}" event="onblur"
                                                 reRender="slabTable,btnSave,lblMsg,gridPanel22"/>
                                    <f:selectItems value="#{InterestCodeMaster.recordStatusList}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                            
                        </h:panelGrid>
                        <h:panelGrid  columns="14" id="subPanel3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:panelGroup id="subFromPanel34" >
                                <h:outputLabel id="lblIntSlabVerNo" styleClass="label"  value="Interest Slab Version No."/>
                                <h:outputText id="txtIntSlabVerNo" style="width:40px" styleClass="input" value="#{InterestCodeMaster.intVerNoSlab}" />
                            </h:panelGroup>
                            
                            <h:panelGroup id="subPanel35" >
                                <h:outputLabel id="lblCreateBy" styleClass="label" value="Created By "/>
                                <h:outputText id="txtCreateBy" style="width:40px"  value="#{InterestCodeMaster.createdBySlab}"/>
                            </h:panelGroup>
                            <h:panelGroup id="subPanel36" >
                                <h:outputLabel id="lblCreatedDt" styleClass="label"  value="Created Date "/>
                                <h:outputText id="txtCreatedDt" style="width:40px"  value="#{InterestCodeMaster.createdDateSlab}"/>
                            </h:panelGroup>
                            <h:panelGroup id="subFromPanel37" >
                                <h:outputLabel id="lblUpDateBy" styleClass="label"  value="Last UpDated By "/>
                                <h:outputText id="txtUpDateBy" style="width:40px" value="#{InterestCodeMaster.lastUpdatedBySlab}"/>
                            </h:panelGroup>
                            <h:panelGroup id="subFromPanel38" >
                                <h:outputLabel id="lblUpDateDt" styleClass="label"  value="Last Updated By "/>
                                <h:outputText id="txtUpDateDt" style="width:40px" value="#{InterestCodeMaster.lastUpdatedDateSlab}"/>
                            </h:panelGroup>
                            <h:panelGroup id="subFromPanel20" >
                                <h:outputLabel id="lblCounter" styleClass="label"  value="Record Modification Counter "/>
                                <h:outputText id="stxtCounter" styleClass="output" value="#{InterestCodeMaster.modificationCounter}"/>
                            </h:panelGroup>
                            
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col1,col2" columns="1" id="gridPanel103" width="100%" styleClass="row2">
                            <a4j:region>
                                <rich:dataTable value ="#{InterestCodeMaster.intCodeMaster}"
                                                rowClasses="row1, row2" id = "slabTable" rows="3" columnsWidth="100" rowKeyVar="row" var ="item"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="Interest Table Code" /> </rich:column>
                                            <rich:column><h:outputText value="Loan Period Months" /></rich:column>
                                            <rich:column><h:outputText value="Loan Period Days" /></rich:column>
                                            <rich:column><h:outputText value="Begin  Slab  Amount" /></rich:column>
                                            <rich:column><h:outputText value="End Slab Amount" /></rich:column>
                                            <rich:column><h:outputText value="Normal Interest Indicator" /></rich:column>
                                            <rich:column><h:outputText value="Normal Interest Percentage" /></rich:column>
                                            <rich:column><h:outputText value="Peenal Interest Indicator" /></rich:column>
                                            <rich:column><h:outputText value="Peenal Interest Percentage" /></rich:column>
                                            <rich:column><h:outputText value="Clean Portion Indicator" /></rich:column>
                                            <rich:column><h:outputText value="Clean Interest Percentage" /></rich:column>
                                            <rich:column><h:outputText value="QIS Portion Indicator" /></rich:column>
                                            <rich:column><h:outputText value="QIS Interest Percentage" /></rich:column>
                                            <rich:column><h:outputText value="Additional Portion Indicator" /></rich:column>
                                            <rich:column><h:outputText value="Additional Interest Percentages" /></rich:column>
                                            <rich:column><h:outputText value="Interest Slab Cr/Dr Flag" /></rich:column>
                                            <rich:column><h:outputText value="Record Status" /></rich:column>
                                            <rich:column><h:outputText value="Record Modification Counter" /></rich:column>
                                            <rich:column><h:outputText value="Select/Delete" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>


                                    <rich:column><h:outputText value="#{item.intTableCode}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.loanPeriodMonths}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.loanPeriodDays}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.bgSlabAmt}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item.endSlabAmt}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.normalIntInd}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.normalIntPer}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.peenalIntInd}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item.peenalIntPer}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.cleanPortionInd}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.cleanIntPer}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.qisPortionInd}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.qisIntPer}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item.additionalInd}"/></rich:column>
                                    <rich:column ><h:outputText value="#{item.additionalInt}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.intSlabDcFlag}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.recordStatus}" /></rich:column>
                                    <rich:column ><h:outputText value="#{item.modificationCount}"/></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{InterestCodeMaster.select}"
                                                         reRender="gridPanel22,btnUpdate,richPanel,ddBaseIntTable,slabTable,btnAddNewSlab,ddCurrencyCode" >
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />

                                            <f:setPropertyActionListener value="#{item}" target="#{InterestCodeMaster.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{InterestCodeMaster.currentRow}" />
                                        </a4j:commandLink>

                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" disabled="#{InterestCodeMaster.deleteDisable}">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{item}" target="#{InterestCodeMaster.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{InterestCodeMaster.currentRow}" />
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="slabTable" maxPages="40" />
                            </a4j:region>
                            <rich:modalPanel id="selectPanel" autosized="true" width="200"  rendered="#{InterestCodeMaster.intTableCode != ''}">
                                <f:facet name="header">
                                    <h:outputText value="Are you want to discard the changes?" style="padding-right:15px;" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink4" />
                                        <rich:componentControl for="selectPanel" attachTo="hidelink2" operation="hide" event="onclick"/>
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{InterestCodeMaster.select}"
                                                                       onclick="#{rich:component('selectPanel')}.hide();" reRender="gridPanel22,btnUpdate,richPanel,slabTable" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('selectPanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <rich:modalPanel id="deletePanel" autosized="true" width="200">
                                <f:facet name="header">
                                    <h:outputText value="Delete this Entry?" style="padding-right:15px;" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                        <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick"/>
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{InterestCodeMaster.delete}"
                                                                       onclick="#{rich:component('deletePanel')}.hide();" reRender="slabTable,txtBeginSlabAmount,txtLoanPeriodDays,txtLoanPeriodMonths" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>


                        </h:panelGrid>
                    </rich:panel>
                    <rich:modalPanel id="savePanel" autosized="true" width="200" >
                        <f:facet name="header">
                            <h:outputText value="Are you Want to Save ?" style="padding-right:15px;" />
                        </f:facet>

                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{InterestCodeMaster.saveAction}"
                                                               onclick="#{rich:component('savePanel')}.hide();" reRender="gridPanel22,lblMsg,btnSave,richPanel,gridPanel103" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="updatePanel" autosized="true" width="200" >
                        <f:facet name="header">
                            <h:outputText value="Are you Want to Update The Record ?" style="padding-right:15px;" />
                        </f:facet>

                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{InterestCodeMaster.updateAction}"
                                                               onclick="#{rich:component('updatePanel')}.hide();" reRender="gridPanel22,lblMsg,btnSave,richPanel,gridPanel103,btnAddNewSlab,subPanel1,subPanel2,subPanel3" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel top="false"autosized="true" width="500"  id="showPanel">
                        <f:facet name="header">
                            <h:outputText style="padding-right:15px;"/>
                        </f:facet>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink3" />
                                <rich:componentControl for="showPanel" attachTo="hidelink3" operation="hide" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                        <a4j:form id="detailForm">
                            <h:panelGrid id="mainPane2" bgcolor="#fff"  style="border:1px ridge #BED6F8" width="100%">
                                <a4j:region>
                                    <rich:dataTable    value="#{InterestCodeMaster.tabSearch}" var="item"
                                                       rowClasses="gridrow1, gridrow2" id="taskDetailList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                       onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                       onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                       onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">


                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="3"><h:outputText value="Interest Table Code Details"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Interest Table Code"/></rich:column>
                                                <rich:column><h:outputText value="Interest Table Code Description"/></rich:column>

                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.code}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.desc}"/></rich:column>

                                    </rich:dataTable>
                                    <rich:datascroller id="scroller" align="left" for="taskDetailList" maxPages="20" />
                                </a4j:region>
                                <h:panelGrid columns="1" id="backButton" styleClass="footer" style="height:30px;" width="100%">
                                    <h:panelGroup id="backPanel" layout="block">
                                        <a4j:commandButton style="align:center;" value="Exit"onclick="#{rich:component('showPanel')}.hide();" />
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                        </a4j:form>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <rich:componentControl for="showPanel" attachTo="btnDetail" operation="show" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                    </rich:modalPanel>

                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnAddNewSlab" value="Add New Slab " disabled="#{InterestCodeMaster.newSlabDisable}"
                                               action="#{InterestCodeMaster.addNewEntry}" reRender="gridPanel22,slabTable,btnSave,btnAddNewSlab,btnUpdate"/>
                            <a4j:commandButton id="btnSave" value="Save" oncomplete="#{rich:component('savePanel')}.show()" disabled="#{InterestCodeMaster.saveDisable}"/>
                            <a4j:commandButton id="btnUpdate" value="Update" disabled="#{InterestCodeMaster.updateDisable}" oncomplete="#{rich:component('updatePanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InterestCodeMaster.refreshForm}" 
                                               reRender="outerPanel,gridPanel22,slabTable,btnSave,btnAddNewSlab,btnUpdate,lblMsg,subPanel1,subPanel2,subPanel3"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InterestCodeMaster.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>


        </body>
    </html>
</f:view>
