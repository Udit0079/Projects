<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="custGeneralInfo">
    <a4j:region>
        <h:panelGrid id="genInfoPanel" columns="3" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel1" style="width:100%;text-align:center;">
                <%--<h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblAcctMgr" styleClass="label" value="Account Manager"/>
                    <h:inputText  value="#{CustGeneralInfo.accountManager}"maxlength="10"id="txtAcctMgr" styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support actionListener="#{CustGeneralInfo.onblurAcctManager}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                    <h:outputLabel id="lblAllowS" styleClass="label" value="Allow Sweeps"/>
                    <h:selectOneListbox  value="#{CustGeneralInfo.allowSweeps}"id="ddAllowS" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.allowSweepsOption}"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblTFinCust" styleClass="label" value="Trade Finance Customer"/>
                    <h:selectOneListbox  value="#{CustGeneralInfo.tradeFinanceCustomer}"id="ddTFinCust" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.tradeFinanceCustomerOption}"/>
                        <a4j:support actionListener="#{CustGeneralInfo.tfiVisibility}"event="onchange" reRender="lblTfiNameStar,lblTfiAdd1Star,stxtError" />

                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblSwiftSt" styleClass="label" value="Swift Code Status"/>
                    <h:selectOneListbox value="#{CustGeneralInfo.swiftCodeStatus}" id="ddSwiftSt" styleClass="ddlist"size="1">
                        <f:selectItems value="#{CustGeneralInfo.swiftCodeStatusOption}"/>
                        <a4j:support actionListener="#{CustGeneralInfo.onblurSwiftCodeStatus}" event="onblur" oncomplete="if(#{CustGeneralInfo.swiftFocus=='true'}){#{rich:element('txtSwiftCd')}.focus();}else if(#{CustGeneralInfo.swiftFocus=='false'}){#{rich:element('txtBcbfId')}.focus();}" reRender="txtSwiftCd"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblSwiftCd" styleClass="label" value="Swift Code"/>
                    <h:inputText  value="#{CustGeneralInfo.swiftCode}"disabled="#{CustGeneralInfo.swiftFlag}"maxlength="12"id="txtSwiftCd" styleClass="input" style="120px"/>
                    <h:outputLabel id="lblBcbfId" styleClass="label" value="BCBF ID"/>
                    <h:inputText  value="#{CustGeneralInfo.bcbfId}"id="txtBcbfId" maxlength="12"styleClass="input" style="120px"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCombStmt" styleClass="label" value="Combined Statement"/>
                    <h:selectOneListbox value="#{CustGeneralInfo.combinedStatement}" id="ddCombStmt" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.combinedStatementOption}"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblStmtFreq" styleClass="label" value="Statement Frequency"/>
                    <h:selectOneListbox  value="#{CustGeneralInfo.statementFrequency}"id="ddStmtFreq" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.statementFrequencyOption}"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblStmtFreqWNo" styleClass="label" value="Statement Frequency Week No"/>
                    <h:selectOneListbox value="#{CustGeneralInfo.statementFreqWeekNo}" id="ddStmtFreqWNo" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.statementFreqWeekNoOption}"/>

                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow4" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblStmtFreqWD" styleClass="label" value="Statement Frequency Week Day"/>
                    <h:selectOneListbox  value="#{CustGeneralInfo.statementFreWeekDay}" id="ddStmtFreqWD" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.statementFreWeekDayOption}"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblStmtFreqDt" styleClass="label" value="Statement Frequency Date"/>
                    <rich:calendar  value="#{CustGeneralInfo.statementFrequencyDate}"datePattern="dd/MM/yyyy" id="StatementFreqDt" jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{CustGeneralInfo.onblurStatementFreqDate}"focus="ddStmtFreqNp" reRender="stxtError"/>
                    </rich:calendar>

                    <h:outputLabel id="lblStmtFreqNp" styleClass="label" value="Statement Frequency NP"/>
                    <h:selectOneListbox  value="#{CustGeneralInfo.statementFreNp}"id="ddStmtFreqNp" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.statementFreNpOption}"/>

                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow9" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblSal" styleClass="label" value="Salary"/>
                    <h:inputText  value="#{CustGeneralInfo.salary}"id="txtSal" maxlength="15"styleClass="input">
                        <a4j:support event="onblur" reRender="stxtError" actionListener="#{CustGeneralInfo.onblurSalary}"/>
                    </h:inputText>
                    <h:outputLabel id="lblChgSt" styleClass="label" value="Charge Status"/>
                    <h:selectOneListbox value="#{CustGeneralInfo.chargeStatus}" id="ddChgSt" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.chargeStatusOption}"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblChgLvlCd" styleClass="label" value="Charge Level Code"/>
                    <h:selectOneListbox value="#{CustGeneralInfo.chargeLevelCode}" id="ddChgLvlCd" styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.chargeLevelCodeOption}"/>

                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow10" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblAbbChCd" styleClass="label" value="ABB Charge Code"/>
                    <h:selectOneListbox value="#{CustGeneralInfo.abbChargeCode}" id="ddAbbChCd" style="width:72px;"styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.abbChargeCodeOption}"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblEpsChCd" styleClass="label" value="EPS Charge Code"/>
                    <h:selectOneListbox  value="#{CustGeneralInfo.epsChargeCode}"id="ddEpsChCd" style="width:72px;"styleClass="ddlist" size="1">
                        <f:selectItems value="#{CustGeneralInfo.epsChargeCodeOption}"/>

                    </h:selectOneListbox>
                    <h:outputLabel id="lblPRemitt" styleClass="label" value="Paper Remittance"/>
                    <h:inputText  value="#{CustGeneralInfo.paperRemitence}"maxlength="10"id="txtPRemitt" styleClass="input" style="120px"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow11" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDelChCd" styleClass="label" value="Delivery Channel Charge Code"/>
                    <h:inputText  value="#{CustGeneralInfo.deliveryChannelChargeCode}"maxlength="10"id="txtDelChCd" styleClass="input" style="120px"/>
                    <h:outputLabel id="lblAcctLChg" styleClass="label" value="Account Level Charges"/>
                    <h:inputText  value="#{CustGeneralInfo.acctLevelChanges}"maxlength="5"id="txtAcctChg" styleClass="input">
                    </h:inputText>
                    <h:outputLabel id="lblCustLChg" styleClass="label" value="Customer Level Charges"/>
                    <h:inputText  value="#{CustGeneralInfo.custLevelChanges}"maxlength="5"id="txtCustLChg" styleClass="input">
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow12" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblTaxSlab" styleClass="label" value="TAX Slab"/>
                    <h:inputText  value="#{CustGeneralInfo.taxSlab}"maxlength="5"id="txtTaxSlab" styleClass="input" style="120px"/>
                    <h:outputLabel id="lblITFNo" styleClass="label" value="IT File No"/>
                    <h:inputText  value="#{CustGeneralInfo.itFileNo}"maxlength="20"id="txtITFNo" styleClass="input" style="120px"/>
                    <h:outputLabel id="lblTDSCode" styleClass="label" value="TDS Code"/>
                    <h:inputText  value="#{CustGeneralInfo.tdsCode}"maxlength="12"id="txtTDSCode" styleClass="input" style="120px"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow13" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblTDSCustId" styleClass="label" value="TDS Cust Id"/>
                    <h:inputText  value="#{CustGeneralInfo.tdsCustId}"maxlength="10"id="txtTDSCustId" styleClass="input" style="120px"/>
                    <h:outputLabel id="lblTDSFRecDt" styleClass="label" value="TDS Form Receive Date"/>
                    <rich:calendar  value="#{CustGeneralInfo.tdsFormRcvdate}"datePattern="dd/MM/yyyy" id="TDSFRecDt" jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" focus="txtTDSExRefNo"/>
                    </rich:calendar>
                    <h:outputLabel id="lblTDSExRefNo" styleClass="label" value="TDS Exemption Ref No"/>
                    <h:inputText  value="#{CustGeneralInfo.tdsExempRefNo}"maxlength="30"id="txtTDSExRefNo" styleClass="input" style="120px"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow14" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblTDSExEndDt" styleClass="label" value="TDS Exemption End Date"/>
                    <rich:calendar  value="#{CustGeneralInfo.tdsExemEndDate}"datePattern="dd/MM/yyyy" id="TDSExEndDt" jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" focus="txtTdsLmt"/>
                    </rich:calendar>
                    <h:outputLabel id="lblTdsLmt" styleClass="label" value="Customer Floor Limit For TDS"/>
                    <h:inputText  value="#{CustGeneralInfo.custFloorLim}"id="txtTdsLmt" styleClass="input" style="120px"/>
                    <h:outputLabel id="lblRemark" styleClass="label" value="Remarks"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:inputText  value="#{CustGeneralInfo.remarks}"id="txtRemark"maxlength="80" styleClass="input" style="120px"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow15" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblPanNo" styleClass="label" value="PAN/GIR No">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:panelGroup id="panGrp" layout="block">
                        <h:selectOneListbox id="ddPanNoOption" styleClass="ddlist" value="#{CustGeneralInfo.panNoOption}" size="1">
                            <f:selectItems value="#{CustGeneralInfo.panNoOptionList}"/>
                            <a4j:support actionListener="#{CustGeneralInfo.onblurPanNo}" event="onblur" reRender="txtPanNo,stxtError" focus="txtPanNo"/>
                        </h:selectOneListbox>
                        <h:inputText  value="#{CustGeneralInfo.panGirNo}"maxlength="10" id="txtPanNo" styleClass="input" style="width:80px;" disabled="#{CustGeneralInfo.panNoVisibility}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGroup>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>--%>
                <rich:panel header="Proof of Identity (PoI)" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow0" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblLegalPoiDoc" styleClass="label" value="POI(Id Proof) Doc">
                            <font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddLegalPoiDoc" styleClass="ddlist" value="#{CustGeneralInfo.legelPoiDoc}" size="1"  
                                            style="width:140px;#{CustGeneralInfo.legalPoiDocChgFlag}">
                            <f:selectItems value="#{CustGeneralInfo.legelPoiDocList}"/>
                            <a4j:support event="onblur" actionListener="#{CustGeneralInfo.onChangePOI}" reRender="genRow1"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblIdentityNo" styleClass="label" value="Identity No.">
                            <font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText  id="txtIdentityNo" value="#{CustGeneralInfo.identityNo}" styleClass="input" maxlength="21" size="15"  
                                      onkeydown="this.value=this.value.toUpperCase();" style="#{CustGeneralInfo.identityNoChgFlag}">>
                            <a4j:support event="onblur" action= "#{CustGeneralInfo.checkmappedidentityNo}" reRender="stxtError,persInfoPanel,funcRow1,funcRow2,genInfoPanel"/>
                        </h:inputText>
                        <h:outputLabel id="lblIdExpiryDate" styleClass="label" value="Id Expiry Date"/>
                        <rich:calendar value="#{CustGeneralInfo.idExpiryDate}"datePattern="dd/MM/yyyy" id="txtIdExpiryDate" 
                                       inputSize="10" inputStyle="#{CustGeneralInfo.idExpiryDateChgFlag}">
                            <a4j:support event="onchanged"/>
                        </rich:calendar>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:panelGroup>
                            <h:outputLabel id="lbltinIssuingCountry" styleClass="label" value="TIN Issuing Country" style="display:#{CustGeneralInfo.tinIssuingCountryDisplay};">
                                <font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:outputLabel id="lblothrPOI" styleClass="label" value="Other POI" style="display:#{CustGeneralInfo.othrPoiDisplay};">
                                <font class="required" style="color:red;">*</font></h:outputLabel>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddtinIssuingCountry" styleClass="ddlist" value="#{CustGeneralInfo.tinIssuingCountry}" size="1"  
                                                style="display:#{CustGeneralInfo.tinIssuingCountryDisplay};#{CustGeneralInfo.tinIssuingCountryChgFlag}" >
                                <f:selectItems value="#{CustGeneralInfo.tinIssuingCountryList}"/>
                                <a4j:support event="onblur" />
                            </h:selectOneListbox>
                            <h:inputText value="#{CustGeneralInfo.otherIdentity}" id="txtothrPOI" styleClass="input" style="width:120px;display:#{CustGeneralInfo.othrPoiDisplay};
                                         #{CustGeneralInfo.otherIdentityChgFlag}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <rich:panel header="Other Identity Details(Optional)" style="text-align:left;">
                        <h:panelGrid columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" id="genRowId1" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblIdType1" styleClass="label" value="POI(Id Proof) Doc">
                                <font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddIdType1" styleClass="ddlist" value="#{CustGeneralInfo.optLegalPoiDoc}" size="1"  
                                                style="width:140px;">
                                <f:selectItems value="#{CustGeneralInfo.legelPoiDocList}"/>
                                <a4j:support event="onblur" actionListener="#{CustGeneralInfo.onChangeOptPOI}" reRender="genRow1,genRowId2,txnGrid"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblIdentityNo1" styleClass="label" value="Identity No.">
                                <font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText  id="txtIdentityNo1" value="#{CustGeneralInfo.optIdentityNo}" styleClass="input" maxlength="21" size="15"  
                                          onkeydown="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" action="#{CustGeneralInfo.checkDuplicateidentity}" reRender="stxtError,persInfoPanel,funcRow1,funcRow2,genInfoPanel" />
                            </h:inputText>
                            <h:outputLabel id="lblIdExpiryDate1" styleClass="label" value="Id Expiry Date"/>
                            <rich:calendar value="#{CustGeneralInfo.optIdExpiryDate}"datePattern="dd/MM/yyyy" id="txtIdExpiryDate1" 
                                           inputSize="10">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                            <a4j:commandButton value="ADD" id="btnYes" action="#{CustGeneralInfo.onAddPOI}" 
                                               onclick="#{rich:component('processPanel')}.hide();" 
                                               reRender="stxtError,funcRow1,funcRow2,persInfoPanel,genInfoPanel" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRowId2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:panelGroup>
                                <h:outputLabel id="lbltinIssuingCountry1" styleClass="label" value="TIN Issuing Country" style="display:#{CustGeneralInfo.optTinIssuingCountryDisplay};">
                                    <font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:outputLabel id="lblothrPOI1" styleClass="label" value="Other POI" style="display:#{CustGeneralInfo.optOthrPoiDisplay};">
                                    <font class="required" style="color:red;">*</font></h:outputLabel>
                            </h:panelGroup>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddtinIssuingCountry1" styleClass="ddlist" value="#{CustGeneralInfo.optTinIssuingCountry}" size="1"  
                                                    style="display:#{CustGeneralInfo.optTinIssuingCountryDisplay};#{CustGeneralInfo.tinIssuingCountryChgFlag}" >
                                    <f:selectItems value="#{CustGeneralInfo.tinIssuingCountryList}"/>
                                    <a4j:support event="onblur" />
                                </h:selectOneListbox>
                                <h:inputText value="#{CustGeneralInfo.optOtherIdentity}" id="txtothrPOI1" styleClass="input" style="width:120px;display:#{CustGeneralInfo.optOthrPoiDisplay};
                                             #{CustGeneralInfo.otherIdentityChgFlag}">
                                    <a4j:support event="onblur"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                            <rich:dataTable  value="#{CustGeneralInfo.custIdList}" var="item" 
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="Other Identity Details"/></rich:column>
                                        <rich:column breakBefore="true" width="5"><h:outputText value="SNo."/></rich:column>
                                        <rich:column width="350"><h:outputText value="Idetitification Type" /></rich:column>
                                        <rich:column width="220"><h:outputText value="Identity No." /></rich:column>
                                        <rich:column width="250"><h:outputText value="Id Expiry Date" /></rich:column>
                                        <rich:column width="250"><h:outputText value="Tin Issuing Country" /></rich:column>
                                        <rich:column width="100"><h:outputText value="Action" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{CustGeneralInfo.custIdList.indexOf(item)+1}"/></rich:column>
                                <rich:column><h:outputText value="#{item.idTypeDesc}"/></rich:column>
                                <rich:column><h:outputText value="#{item.identityNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.identityExpiryDate}"/></rich:column>
                                <rich:column><h:outputText value="#{item.tinIssuingCountryDesc}"/></rich:column>
                                <rich:column>
                                    <a4j:commandLink  id="deliteLink" action="#{CustGeneralInfo.onDeletePOI(item)}"
                                                      reRender="txnGrid" focus="btnAuthorize">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:1" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="idDataScroll"align="left" for="txnList" maxPages="10"/>
                        </h:panelGrid>
                    </rich:panel>
                </rich:panel>
                <rich:panel header="Other Details" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow5" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblIncomeRange" styleClass="label" value="Income Range"/>
                        <h:selectOneListbox id="ddIncomeRange" styleClass="ddlist" value="#{CustGeneralInfo.incomeRange}" size="1" style="#{CustGeneralInfo.incomeRangeChgFlag}">
                            <f:selectItems value="#{CustGeneralInfo.incomeRangeList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblNetworth" styleClass="label" value="Netwoth"/>
                        <h:inputText value="#{CustGeneralInfo.networth}" id="txtNetworth" styleClass="input" style="width:80px;#{CustGeneralInfo.networthChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblNetworthAsOn" styleClass="label" value="Networth As On"/>
                        <rich:calendar value="#{CustGeneralInfo.networthAsOn}"datePattern="dd/MM/yyyy" id="txtNetworthAsOn" 
                                       jointPoint="top-left" direction="top-right" inputSize="10" inputStyle="#{CustGeneralInfo.networthAsOnChgFlag}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblEduQualification" styleClass="label" value="Education Qualification"/>
                        <h:selectOneListbox id="ddEduQualification" styleClass="ddlist" value="#{CustGeneralInfo.qualification}" size="1" 
                                            disabled="#{CustGeneralInfo.custGeneralInfoIndDisable}" style="#{CustGeneralInfo.eduQualificationChgFlag}">
                            <f:selectItems value="#{CustGeneralInfo.qualificationList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblExposed" styleClass="label" value="Politicaly Exposed"/>
                        <h:selectOneListbox id="ddExposed" styleClass="ddlist" value="#{CustGeneralInfo.exposed}" size="1" 
                                            disabled="#{CustGeneralInfo.custGeneralInfoIndDisable}" style="#{CustGeneralInfo.exposedChgFlag}">
                            <f:selectItems value="#{CustGeneralInfo.exposedList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSalary" styleClass="label" value="Salary"/>
                        <h:inputText id="txtSalary" styleClass="input" style="120px;#{CustGeneralInfo.salaryChgFlag}" value="#{CustGeneralInfo.salary}" disabled="#{CustGeneralInfo.custGeneralInfoIndDisable}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                </rich:panel>
            </h:panelGrid>
        </h:panelGrid>
    </a4j:region>
</h:form>