<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="custGeneralInformation">
    <a4j:region>
        <h:panelGrid id="genInfoPanel1" columns="2" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel2" style="width:100%;text-align:center;">
                <rich:panel header="Customer Introducer Details" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow5" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblIntCustId" styleClass="label" value="Introducer Customer Id"/>
                        <h:inputText  value="#{CustGeneralInformation.introCusId}"id="txtIntCustId"maxlength="10" styleClass="input" style="120px">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurcustIntroId}" event="onblur" reRender="ddTitleIntro,txtName,genRow6,genRow7,genRow8,genRow99,stxtError"/>
                        </h:inputText>
                        <h:outputLabel id="lbltitleIntro" styleClass="label" value="Title"/>
                        <h:selectOneListbox id="ddTitleIntro" styleClass="ddlist" disabled="true"value="#{CustGeneralInformation.titleType2}" size="1">
                            <f:selectItems value="#{CustGeneralInformation.titleType2Option}"/>
                            <%--<rich:toolTip for="ddTitleIntro" value="Please Select Title"></rich:toolTip>--%>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblName" styleClass="label" value="Introducer Name"/>
                        <h:inputText  value="#{CustGeneralInformation.introName}"id="txtName" disabled="true" maxlength="40"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurIntroName}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblIntAddr1" styleClass="label" value="Address Line1"/>
                        <h:inputText value="#{CustGeneralInformation.introAdd1}" id="txtIntAddr1" disabled="true"maxlength="50"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblIntAddr2" styleClass="label" value="Address Line2"/>
                        <h:inputText  value="#{CustGeneralInformation.introAdd2}"id="txtIntAddr2" disabled="true"maxlength="50"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblIntVillage" styleClass="label" value="Introducer Village"/>
                        <h:inputText  value="#{CustGeneralInformation.introVill}"id="txtIntVillage"disabled="true"maxlength="45" styleClass="input" style="100px"onkeydown="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow7" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblIntBlock" styleClass="label" value="Introducer Block"/>
                        <h:inputText  value="#{CustGeneralInformation.introBlock}"id="txtIntBlock" disabled="true"maxlength="45"styleClass="input" style="100px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblIntCity" styleClass="label" value="Introducer City"/>
                        <h:selectOneListbox value="#{CustGeneralInformation.introCity}" id="ddIntCity" disabled="true"styleClass="ddlist" size="1">
                            <f:selectItems value="#{CustGeneralInformation.introCityOption}"/>

                        </h:selectOneListbox>
                        <h:outputLabel id="lblIntState" styleClass="label" value="Introducer State"/>
                        <h:selectOneListbox value="#{CustGeneralInformation.introState}" id="ddIntState"disabled="true" style="width:72px;"styleClass="ddlist" size="1">
                            <f:selectItems value="#{CustGeneralInformation.introStateOption}"/>

                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow8" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblIntPostal" styleClass="label" value="Introducer Postal"/>
                        <h:inputText  value="#{CustGeneralInformation.introPostal}"id="txtIntPostal"disabled="true"maxlength="11" styleClass="input" style="100px">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurIntroPostalCode}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                        <h:outputLabel id="lblIntCountry" styleClass="label" value="Introducer Country"/>
                        <h:selectOneListbox value="#{CustGeneralInformation.introCountry}" id="ddIntCountry"disabled="true" styleClass="ddlist" size="1">
                            <f:selectItems value="#{CustGeneralInformation.introCountryOption}"/>

                        </h:selectOneListbox>
                        <h:outputLabel id="lblIntPh" styleClass="label" value="Introducer Phone"/>
                        <h:inputText  value="#{CustGeneralInformation.introPhone}"id="txtIntPh" disabled="true"maxlength="15"styleClass="input" style="100px">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurIntroPhone}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow99" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblIntTlx" styleClass="label" value="Introducer Telex"/>
                        <h:inputText  value="#{CustGeneralInformation.introTelex}"id="txtIntTlx" disabled="true"maxlength="15"styleClass="input" style="100px">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurIntroTelex}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                        <h:outputLabel id="lblIntFax" styleClass="label" value="Introducer Fax"/>
                        <h:inputText  value="#{CustGeneralInformation.introFax}"id="txtIntFax"disabled="true" maxlength="15"styleClass="input" style="120px">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurIntroFax}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                </rich:panel>
                <rich:panel header="Customer Financial Details" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col16,col15,col3,col3,col3,col1" columns="7" id="genRow17" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblFinYrMn" styleClass="label" value="Financial Year & Month"/>
                        <h:inputText  value="#{CustGeneralInformation.custFinanYear}"id="txtFinYr" maxlength="4"styleClass="input" style="width:55px;">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurFinYear}" event="onblur" reRender="stxtError"/>

                        </h:inputText>
                        <h:inputText  value="#{CustGeneralInformation.custFinanMon}"id="txtFinMn" maxlength="2"styleClass="input" style="width:55px;">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurFinMonth}" event="onblur" reRender="stxtError"/>

                        </h:inputText>
                        <h:outputLabel id="lblCurCd" styleClass="label" value="Currency Code"/>
                        <h:selectOneListbox value="#{CustGeneralInformation.currencyCode}" id="ddCurCd" style="width:72px;"styleClass="ddlist" size="1">
                            <f:selectItems value="#{CustGeneralInformation.currencyCodeOption}"/>

                        </h:selectOneListbox>
                        <h:outputLabel id="lblBAssets" styleClass="label" value="Bussiness Assets"/>
                        <h:inputText  value="#{CustGeneralInformation.businessAssests}"id="txtBAssets" styleClass="input"style="100px">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow18" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblPAssets" styleClass="label" value="Property Assets"/>
                        <h:inputText  value="#{CustGeneralInformation.propAssets}"id="txtPAssets" styleClass="input" style="100px">
                        </h:inputText>
                        <h:outputLabel id="lblInvst" styleClass="label" value="Investments"/>
                        <h:inputText  value="#{CustGeneralInformation.investment}"id="txtInvst" styleClass="input" style="120px"/>
                        <%--<h:outputLabel id="lblNetworth" styleClass="label" value="Annual Income / Turnover"/>
                        <h:inputText  value="#{CustGeneralInformation.netWorth }"id="txtNetworth" styleClass="input" style="120px">
                            <a4j:support event="onblur"/>
                        </h:inputText>--%>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow19" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblDeposits" styleClass="label" value="Deposits"/>
                        <h:inputText  value="#{CustGeneralInformation.deposits}"id="txtDeposits" styleClass="input" style="100px"/>
                        <h:outputLabel id="lblOthBnkCd" styleClass="label" value="Other Bank Code"/>
                        <h:inputText  value="#{CustGeneralInformation.otherBnkCode}"id="txtOthBnkCd" maxlength="6"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblLmtWthOthBnk" styleClass="label" value="Limit With Other Bank"/>
                        <h:inputText   value="#{CustGeneralInformation.limWithOtherBnk}"id="txtLmtWthOthBnk" styleClass="input" style="120px"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow20" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblFnBLt" styleClass="label" value="Fund Based Limit"/>
                        <h:inputText  value="#{CustGeneralInformation.funfBasedLim}"id="txtFnBLt" styleClass="input" style="100px"/>
                        <h:outputLabel id="lblNFnBLt" styleClass="label" value="Non Fund Based Limit"/>
                        <h:inputText  value="#{CustGeneralInformation.nonFundBasedLim}"id="txtNFnBLt" styleClass="input" style="100px"/>
                        <h:outputLabel id="lblOffLnDbLt" styleClass="label" value="Off Line Customer Debit Limit"/>
                        <h:inputText  value="#{CustGeneralInformation.offLinecustDebitLim}"id="txtOffLnDbLt" styleClass="input" style="120px"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow21" style="width:100%;text-align:left;" styleClass="row1">
                        <%--<h:outputLabel id="lblSalary" styleClass="label" value="Salary"/>
                        <h:inputText  value="#{CustGeneralInformation.salary2}"id="txtSalary" styleClass="input" style="120px"/>--%>
                        <h:outputLabel id="lblCustFinDt" styleClass="label" value="Customer Financial Date"/>
                        <rich:calendar  value="#{CustGeneralInformation.custFinandate}"datePattern="dd/MM/yyyy" id="CustFinDt" jointPoint="top-left" direction="top-right" inputSize="10">
                            <a4j:support event="onchanged" focus="ddCrCard"/>
                        </rich:calendar>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                </rich:panel>
                <rich:panel header="Customer Card/Bank Details" style="text-align:left;">
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="genRow22" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblCrCard" styleClass="label" value="Credit Card"/>
                        <h:selectOneListbox value="#{CustGeneralInformation.creditCard}" id="ddCrCard" styleClass="ddlist" size="1">
                            <f:selectItems value="#{CustGeneralInformation.creditCardOption}"/>
                            <a4j:support actionListener="#{CustGeneralInformation.onblurCreditCard}" event="onblur" reRender="txtCrCardNo,txtCardIssuer,txtBnkName,txtOAcNo,txtBrName"/>
                            <%--<rich:toolTip for="ddCrCard" value="Please Select Credit Card"></rich:toolTip>--%>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCrCardNo" styleClass="label" value="Card No"/>
                        <h:inputText  value="#{CustGeneralInformation.cardNo}"id="txtCrCardNo" disabled="#{CustGeneralInformation.flagForCreditCard}"maxlength="20" styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="genRow23" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblCardIssuer" styleClass="label" value="Card Issuer"/>
                        <h:inputText  value="#{CustGeneralInformation.cardIssuer}"id="txtCardIssuer" disabled="#{CustGeneralInformation.flagForCreditCard}"maxlength="32"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblBnkName" styleClass="label" value="Bank Name"/>
                        <h:inputText  value="#{CustGeneralInformation.bnkName}"id="txtBnkName" disabled="#{CustGeneralInformation.flagForCreditCard}"maxlength="40"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurBankName}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="genRow24" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblOAcNo" styleClass="label" value="Account No"/>
                        <h:inputText  value="#{CustGeneralInformation.accNo}"id="txtOAcNo" disabled="#{CustGeneralInformation.flagForCreditCard}"maxlength="11"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblBrName" styleClass="label" value="Branch Name"/>
                        <h:inputText  value="#{CustGeneralInformation.branchname}"id="txtBrName"disabled="#{CustGeneralInformation.flagForCreditCard}"maxlength="40" styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support actionListener="#{CustGeneralInformation.onblurBranchName}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                    </h:panelGrid>
                </rich:panel>
            </h:panelGrid>
        </h:panelGrid>
    </a4j:region>
</h:form>