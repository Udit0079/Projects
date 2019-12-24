<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="custAddressInfo">
    <a4j:region>
        <h:panelGrid id="addrInfoPanel" columns="3" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel3" style="width:100%;text-align:center;">
                <rich:panel header=" Permanent / Current / Overseas Address" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow0" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblPerAddrType" styleClass="label" value="Permanent Address Type">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{CustAddressInfo.perAddType}" id="ddPerAddrType" 
                                            styleClass="ddlist" size="1" style="width:140px;#{CustAddressInfo.perAddTypeChgFlag}">
                            <f:selectItems value="#{CustAddressInfo.perAddTypeList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPerPoaType" styleClass="label" value="POA">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddPerAddProof" styleClass="ddlist" value="#{CustAddressInfo.prefPoaAdd}" size="1"  
                                            style="width:120px;#{CustAddressInfo.prefPoaAddChgFlag}">
                            <f:selectItems value="#{CustAddressInfo.prefPoaAddList}"/>
                            <a4j:support event="onblur"  actionListener="#{CustAddressInfo.onChangePerPOA()}" reRender="lblPerOthrPOA,txtPerOthrPOA"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPerOthrPOA" styleClass="label" value="Other POA" style="display:#{CustAddressInfo.perOtherPOADisplay};">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.perOtherPOA}" id="txtPerOthrPOA" maxlength="50" styleClass="input" 
                                      style="display:#{CustAddressInfo.perOtherPOADisplay};120px;#{CustAddressInfo.perOtherPOAChgFlag}" 
                                      onkeydown="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow1" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblPrAddr1" styleClass="label" value="Address Line1">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.perAdd1}" id="txtPrAddr1" maxlength="50" styleClass="input" 
                                      style="120px;#{CustAddressInfo.perAdd1ChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblPrAddr2" styleClass="label" value="Address Line2"/>
                        <h:inputText  value="#{CustAddressInfo.perAdd2}" id="txtPrAddr3" maxlength="50" styleClass="input" 
                                      style="120px;#{CustAddressInfo.perAdd2ChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblPrVillage" styleClass="label" value="City / Town / Village">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.perVillage}" id="txtPrVillage" maxlength="45" styleClass="input" 
                                      style="100px;#{CustAddressInfo.perVillageChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow2" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblPrBlock" styleClass="label" value="Block"></h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.perBlock}" id="txtPrBlock" maxlength="45"styleClass="input" 
                                      style="100px;#{CustAddressInfo.perBlockChgFlag}"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblPrCountry" styleClass="label" value="Country">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{CustAddressInfo.countryForPermanentAddress}" id="ddPrCountry" 
                                            styleClass="ddlist" size="1" style="width:120px;#{CustAddressInfo.perCountryChgFlag}">
                            <f:selectItems value="#{CustAddressInfo.countryForPermanentAddressOption}"/>
                            <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangePerCountry()}" reRender="ddPrPostal,
                                         txtPrPostal,ddPrState,addrRow3,ddPrPostal,txtPrState,txtPrCity" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPrState" styleClass="label" value="State">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.perStateNotIN}"id="txtPrState" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.perStateChgFlag};display:#{CustAddressInfo.displayOnPerCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:selectOneListbox value="#{CustAddressInfo.stateForPermanentAddress}" id="ddPrState" 
                                                style="width:120px;#{CustAddressInfo.perStateChgFlag};display:#{CustAddressInfo.displayOnPerCntryIN};" styleClass="ddlist"  size="1">
                                <f:selectItems value="#{CustAddressInfo.stateForPermanentAddressOption}"/>
                                <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangePerState()}" reRender="addrRow3"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow3" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblPrCity" styleClass="label" value="District">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.perDistrictNotIN}"id="txtPrCity" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.perDistrictChgFlag};display:#{CustAddressInfo.displayOnPerCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText value="#{CustAddressInfo.perDistrict}" id="ddPrCity" styleClass="input" 
                                         style="#{CustAddressInfo.perDistrictChgFlag};display:#{CustAddressInfo.displayOnPerCntryIN};">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <rich:suggestionbox height="100" width="150" usingSuggestObjects="true"
                                                onobjectchange="printObjectsSelected(#{rich:component('suggestion')});"
                                                suggestionAction="#{CustAddressInfo.getDistrictList}" var="dist"
                                                for="ddPrCity" fetchValue="#{dist}" id="suggestion" >
                                <h:column>
                                    <h:outputText value="#{dist}" />
                                </h:column>
                            </rich:suggestionbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblPrPostal" styleClass="label" value="Postal Code">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.perpostalCode}"id="txtPrPostal" maxlength="6" styleClass="input" 
                                      style="120px;#{CustAddressInfo.perPostalChgFlag};">
                            <a4j:support event="onblur"/>
                        </h:inputText>


                        <h:outputLabel id="lblPrPhone" styleClass="label" value="Phone No"/>
                        <h:inputText  value="#{CustAddressInfo.perPhoneNo}"id="txtPrPhone"maxlength="15" 
                                      styleClass="input" style="100px;#{CustAddressInfo.perPhoneChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow4" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblPrFax" styleClass="label" value="Fax No"/>
                        <h:inputText  value="#{CustAddressInfo.perFaxNo}"id="txtPrFax" maxlength="15" styleClass="input" 
                                      style="120px;#{CustAddressInfo.perFaxChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblPerEmail" styleClass="label" value="Email Id"/>
                        <h:inputText value="#{CustAddressInfo.perEmail}"id="txtPerEmail" maxlength="40" styleClass="input" 
                                     style="120px;#{CustAddressInfo.perMailChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                </rich:panel>
                <h:panelGrid id="checkBoxPenal" styleClass="row1" style="width:100%;text-align:left;">
                    <h:panelGroup id="checkBoxPenalGrp" layout="block">
                        <h:selectBooleanCheckbox id="checkPerMAilAddress" value="#{CustAddressInfo.mailAddSameAsPerAdd}">
                            <a4j:support actionListener="#{CustAddressInfo.onclickCheckBox()}" event="onclick" 
                                         reRender="FuncPanel,MailRow0,MailRow1,MailRow2,MailRow3,MailRow4"/>
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblCheckBox" styleClass="output" 
                                       value="Same Permanent and Mailing Address?"></h:outputLabel>
                    </h:panelGroup>
                </h:panelGrid>
                <rich:panel header="Mailing / Correspondence / Local Address" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="MailRow0" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblmailAddrType" styleClass="label" value="Mailing Address Type">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{CustAddressInfo.mailAddType}" id="ddmailAddrType" 
                                            styleClass="ddlist" size="1" style="#{CustAddressInfo.mailAddTypeChgFlag}">
                            <f:selectItems value="#{CustAddressInfo.mailAddTypeList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMailPoaType" styleClass="label" value="POA">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{CustAddressInfo.mailPoa}" id="ddMailPoaType" 
                                            styleClass="ddlist" size="1" style="width:140px;#{CustAddressInfo.mailPoaChgFlag}" >
                            <f:selectItems value="#{CustAddressInfo.mailPoaList}"/>
                            <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangeMailPOA()}" reRender="lblMailOthrPOA,txtMailOthrPOA"/>
                        </h:selectOneListbox>


                        <h:outputLabel id="lblMailOthrPOA" styleClass="label" value="Other POA" style="display:#{CustAddressInfo.mailOtherPOADisplay};" >
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.mailOtherPOA}" id="txtMailOthrPOA" maxlength="50" styleClass="input" 
                                      style="display:#{CustAddressInfo.mailOtherPOADisplay};120px;#{CustAddressInfo.mailOtherPOAChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="MailRow1" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblMailAddr1" styleClass="label" value="Address Line1">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.mailAdd1}" id="txtMailAddr1" maxlength="50" styleClass="input" 
                                      style="120px;#{CustAddressInfo.mailAdd1ChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblMailAddr2" styleClass="label" value="Address Line2"/>
                        <h:inputText  value="#{CustAddressInfo.mailAdd2}" id="txtMailAddr2" maxlength="50" styleClass="input" 
                                      style="120px;#{CustAddressInfo.mailAdd2ChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblMailVillage" styleClass="label" value="City / Town / Village">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.mailVillage}" id="txtMailVillage" maxlength="45" styleClass="input" 
                                      style="100px;#{CustAddressInfo.mailVillageChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="MailRow2" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblMailBlock" styleClass="label" value="Block"/>
                        <h:inputText  value="#{CustAddressInfo.mailBlock}" id="txtMailBlock" maxlength="45" styleClass="input" 
                                      style="100px;#{CustAddressInfo.mailBlockChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblMailCountryCode" styleClass="label" value="Country">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{CustAddressInfo.countryForMailAddress}" id="ddMailCountryCode" 
                                            styleClass="ddlist" size="1" style="width:120px;#{CustAddressInfo.mailCountryChgFlag}">
                            <f:selectItems value="#{CustAddressInfo.countryForMailAddressOption}"/>
                            <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangeMailCountry()}" reRender="ddMailState,txtMailState,MailRow3"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMailState" styleClass="label" value="State">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.mailStateNotIN}"id="txtMailState" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.mailStateChgFlag};display:#{CustAddressInfo.displayOnMailCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:selectOneListbox value="#{CustAddressInfo.stateForMailAddress}" id="ddMailState" 
                                                style="width:120px;#{CustAddressInfo.mailStateChgFlag};display:#{CustAddressInfo.displayOnMailCntryIN};" styleClass="ddlist" size="1">
                                <f:selectItems value="#{CustAddressInfo.stateForMailAddressOption}"/>
                                <a4j:support event="onblur"  actionListener="#{CustAddressInfo.onChangeMailState()}" reRender="MailRow3"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="MailRow3" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblMailCity" styleClass="label" value="District">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.mailDistrictNotIN}"id="txtMailCity" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.mailDistrictChgFlag};display:#{CustAddressInfo.displayOnMailCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>

                            <h:inputText value="#{CustAddressInfo.mailDistrict}" id="ddMailCity" styleClass="input" 
                                         style="#{CustAddressInfo.mailDistrictChgFlag};display:#{CustAddressInfo.displayOnMailCntryIN};">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <rich:suggestionbox height="100" width="150" usingSuggestObjects="true"
                                                onobjectchange="printObjectsSelected(#{rich:component('mailSuggestion')});"
                                                suggestionAction="#{CustAddressInfo.getMailDistrictList}" var="mailDist"
                                                for="ddMailCity" fetchValue="#{mailDist}" id="mailSuggestion" >
                                <h:column>
                                    <h:outputText value="#{mailDist}" />
                                </h:column>
                            </rich:suggestionbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblMailPostalCode" styleClass="label" value="Postal Code">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.mailPostalCode}"id="txtMailPostalCode" maxlength="6" styleClass="input" 
                                      style="120px;#{CustAddressInfo.mailPostalChgFlag};">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblMailPhone" styleClass="label" value="Phone No"/>
                        <h:inputText  value="#{CustAddressInfo.mailPhoneNo}"id="txtMailPhone"maxlength="15" styleClass="input" 
                                      style="100px;#{CustAddressInfo.mailPhoneChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="MailRow4" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblMailFax" styleClass="label" value="Fax No"/>
                        <h:inputText  value="#{CustAddressInfo.mailFax}" id="txtMailFax" maxlength="15" styleClass="input" 
                                      style="120px;#{CustAddressInfo.mailFaxChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblCorEmail" styleClass="label" value="Email Id"/>
                        <h:inputText value="#{CustAddressInfo.mailEmail}" id="txtMailEmail" maxlength="40" styleClass="input" 
                                     style="120px;#{CustAddressInfo.mailMailChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                </rich:panel>
                <rich:panel header="Employer Address" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblEmplCode" styleClass="label" value="Employer Code"/>
                        <h:inputText  value="#{CustAddressInfo.empCode}" id="txtEmplCode" maxlength="10" styleClass="input" 
                                      style="120px;#{CustAddressInfo.empCodeChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblEmplNo" styleClass="label" value="Employee No"/>
                        <h:inputText  value="#{CustAddressInfo.empNo}" id="txtEmplNo" maxlength="10" styleClass="input" 
                                      style="120px;#{CustAddressInfo.empNoChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow1" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblEmplAddr1" styleClass="label" value="Address Line 1"/>
                        <h:inputText  value="#{CustAddressInfo.empAdd1}" id="txtEmplAddr1" maxlength="50" styleClass="input" 
                                      style="120px;#{CustAddressInfo.empAdd1ChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblEmplAddr2" styleClass="label" value="Address Line 2"/>
                        <h:inputText  value="#{CustAddressInfo.empAdd2}" id="txtEmplAddr2" maxlength="50" styleClass="input" 
                                      style="120px;#{CustAddressInfo.empAdd2ChgFlag}" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblEmplVillage" styleClass="label" value="City / Town / Village"/>
                        <h:inputText value="#{CustAddressInfo.empVillage}" id="txtEmplVillage" maxlength="45"
                                     styleClass="input" style="120px;#{CustAddressInfo.empVillageChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow2" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblEmplTehsil" styleClass="label" value="Tehsil"/>
                        <h:inputText value="#{CustAddressInfo.empTehsil}" id="txtEmplTehsil" maxlength="45"
                                     styleClass="input" style="100px;#{CustAddressInfo.empTehsilChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>

                        <h:outputLabel id="lblEmplCountry" styleClass="label" value="Country"/>

                        <h:selectOneListbox value="#{CustAddressInfo.countryForEmpAddress}" id="ddEmplCountry" 
                                            styleClass="ddlist" size="1" style="width:120px;#{CustAddressInfo.empCountryChgFlag}">
                            <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangeEmpCountry()}" reRender="ddEmplState,txtEmplState,ovrRow3"/>
                            <f:selectItems value="#{CustAddressInfo.countryForEmpAddressOption}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblEmplState" styleClass="label" value="State"/>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.empStateNotIN}"id="txtEmplState" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.empStateChgFlag};display:#{CustAddressInfo.displayOnEmpCntryNotIN};">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <h:selectOneListbox  value="#{CustAddressInfo.stateForEmpAddress}" id="ddEmplState" 
                                                 style="width:72px;#{CustAddressInfo.empStateChgFlag};display:#{CustAddressInfo.displayOnEmpCntryIN};" styleClass="ddlist" size="1">
                                <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangeEmpState}" reRender="ovrRow3"/>
                                <f:selectItems value="#{CustAddressInfo.stateForEmpAddressOption}"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow3" 
                                 style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblEmplCity" styleClass="label" value="District"/>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.empDistrictNotIN}"id="txtEmplCity" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.empDistrictChgFlag};display:#{CustAddressInfo.displayOnEmpCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText value="#{CustAddressInfo.empDistrict}" id="ddEmplCity" styleClass="input" 
                                         style="#{CustAddressInfo.empDistrictChgFlag};display:#{CustAddressInfo.displayOnEmpCntryIN};">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <rich:suggestionbox height="100" width="150" usingSuggestObjects="true"
                                                onobjectchange="printObjectsSelected(#{rich:component('empSuggestion')});"
                                                suggestionAction="#{CustAddressInfo.getEmpDistrictList}" var="empDist"
                                                for="ddEmplCity" fetchValue="#{empDist}" id="empSuggestion">
                                <h:column>
                                    <h:outputText value="#{empDist}" />
                                </h:column>
                            </rich:suggestionbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblEmplPostal" styleClass="label" value="Postal Code">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText value="#{CustAddressInfo.empPostalCode}" id="txtEmplPostal" maxlength="6"
                                     styleClass="input" style="120px;#{CustAddressInfo.empPostalChgFlag};">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblEmplPhone" styleClass="label" value="Phone No"/>
                        <h:inputText  value="#{CustAddressInfo.empPhoneNo}" id="txtEmplPhone" maxlength="15" 
                                      styleClass="input" style="100px;#{CustAddressInfo.empPhoneChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow4" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblEmplTelexNo" styleClass="label" value="Telex No"/>
                        <h:inputText  value="#{CustAddressInfo.empTelexNo}"id="txtEmplTelexNo" maxlength="15"styleClass="input" 
                                      style="120px;#{CustAddressInfo.empTelexChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblEmplFax" styleClass="label" value="Fax No"/>
                        <h:inputText  value="#{CustAddressInfo.empFax}" id="txtEmplFax" maxlength="15"styleClass="input" 
                                      style="120px;#{CustAddressInfo.empFaxChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblEmplEmail" styleClass="label" value="Email Id"/>
                        <h:inputText  value="#{CustAddressInfo.empEmail}"id="txtEmplEmail"maxlength="40" 
                                      styleClass="input" style="120px;#{CustAddressInfo.empEmailChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                </rich:panel>
                <h:panelGrid id="juriAddAsPenal" styleClass="row1" style="width:100%;text-align:left;">
                    <h:panelGroup id="juriAddAsPenalGrp" layout="block">
                        <h:outputLabel id="lblJuriAddAs" styleClass="output" 
                                       value="Jurisdiction Address As ?"></h:outputLabel>
                        <h:selectOneListbox  value="#{CustAddressInfo.juriAddBasedOnFlag}" id="ddJuriAddAs" 
                                             style="width:120px;#{CustAddressInfo.juriAddBasedOnFlagChgFlag}" styleClass="ddlist" size="1">
                            <a4j:support event="onblur"  actionListener="#{CustAddressInfo.onchangeJuriAddBasedFlag}"
                                         reRender="FuncPanel,juriRow1,juriRow2,juriRow3,ddJuriAddrType,ddJuriPoaType,txtJuriState,ddjuriState,txtJuriPostalCode,lblJuriOthrPOA,txtJuriOthrPOA"/>
                            <f:selectItems value="#{CustAddressInfo.juriAddBasedList}"/>
                        </h:selectOneListbox>
                    </h:panelGroup>
                </h:panelGrid>
                <rich:panel header="Jurisdiction Address" style="text-align:left;">
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="juriRow1" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblJuriAddrType" styleClass="label" value="Jurisdiction Address Type">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{CustAddressInfo.juriAddType}" id="ddJuriAddrType" 
                                            styleClass="ddlist" size="1" style="width:140px;#{CustAddressInfo.juriAddTypeChgFlag}">
                            <f:selectItems value="#{CustAddressInfo.juriAddTypeList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblJuriPoaType" styleClass="label" value="POA">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{CustAddressInfo.juriPoa}" id="ddJuriPoaType" 
                                            styleClass="ddlist" size="1" style="width:140px;#{CustAddressInfo.juriPoaChgFlag}" >
                            <f:selectItems value="#{CustAddressInfo.juriPoaList}"/>
                            <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangeJuriPOA()}" reRender="lblJuriOthrPOA,txtJuriOthrPOA"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblJuriOthrPOA" styleClass="label" value="Other POA" style="display:#{CustAddressInfo.juriOtherPOADisplay};">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.juriOtherPOA}" id="txtJuriOthrPOA" maxlength="50" styleClass="input" 
                                      style="display:#{CustAddressInfo.juriOtherPOADisplay};120px;#{CustAddressInfo.juriOtherPOAChgFlag}" 
                                      onkeydown="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="juriRow2" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblJuriAddr1" styleClass="label" value="Address"/>
                        <h:inputText  value="#{CustAddressInfo.juriAdd1}" id="txtJuriAddr1" maxlength="50" styleClass="input" 
                                      style="120px;#{CustAddressInfo.juriAdd1ChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblJuriCity" styleClass="label" value="City / Town / Village">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.juriCity}" id="txtJuriCity" maxlength="45" styleClass="input" 
                                      style="100px;#{CustAddressInfo.juriCityChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>

                        <h:outputLabel id="lblJuriCountryCode" styleClass="label" value="ISO -3166 Country Code"/>
                        <h:selectOneListbox  value="#{CustAddressInfo.juriCountryCode}" id="ddJuriCountryCode" 
                                             style="width:120px;#{CustAddressInfo.juriCountryChgFlag}" styleClass="ddlist" size="1">
                            <f:selectItems value="#{CustAddressInfo.juriCountryCodeList}"/>
                            <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangeJuriCountry()}" reRender="juriRow3"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="juriRow3" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblJuriState" styleClass="label" value="State"/>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.juriStateNotIN}"id="txtjuriState" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.juriStateChgFlag};display:#{CustAddressInfo.displayOnJuriCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:selectOneListbox value="#{CustAddressInfo.juriState}" id="ddjuriState" 
                                                style="width:72px;#{CustAddressInfo.juriStateChgFlag};display:#{CustAddressInfo.displayOnJuriCntryIN};" styleClass="ddlist" size="1">
                                <f:selectItems value="#{CustAddressInfo.stateForMailAddressOption}"/>
                                <a4j:support event="onblur" actionListener="#{CustAddressInfo.onChangeJuriState()}" reRender="juriRow3"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblJuriDistrict" styleClass="label" value="District"/>
                        <h:panelGroup>
                            <h:inputText  value="#{CustAddressInfo.juriDistrictNotIN}"id="txtJuriDistrict" maxlength="50" styleClass="input" 
                                          style="120px;#{CustAddressInfo.juriPostalChgFlag};display:#{CustAddressInfo.displayOnJuriCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText value="#{CustAddressInfo.juriDistrict}" id="ddJuriDistrict" styleClass="input" 
                                         style="#{CustAddressInfo.juriPostalChgFlag};display:#{CustAddressInfo.displayOnJuriCntryIN};">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <rich:suggestionbox  usingSuggestObjects="true"
                                                 onobjectchange="printObjectsSelected(#{rich:component('juriSuggestion')});"
                                                 suggestionAction="#{CustAddressInfo.getJuriDistrictList}" var="juriDist"
                                                 for="ddJuriDistrict" fetchValue="#{juriDist}" id="juriSuggestion">
                                <h:column>
                                    <h:outputText value="#{juriDist}" />
                                </h:column>
                            </rich:suggestionbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblJuriPostalCode" styleClass="label" value="Postal Code">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  value="#{CustAddressInfo.juriPostalCode}"id="txtJuriPostalCode" maxlength="6" styleClass="input" 
                                      style="120px;#{CustAddressInfo.juriPostalChgFlag};">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                </rich:panel>
            </h:panelGrid>
        </h:panelGrid>
    </a4j:region>
</h:form>