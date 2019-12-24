<%-- 
    Document   : custAdditionalAddresDetails
    Created on : 3 Apr, 2017, 3:08:52 PM
    Author     : root
--%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="custAdditionalAddresDetails">
    <a4j:region>
        <h:panelGrid id="addAddressInfoPanel" columns="2" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">

                <rich:panel header="Additional Address Details (Optional)" id="addDetailsTab" style="text-align:left;">
                    <h:panelGrid id="addressDetails1" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:left;" styleClass="row1">

                        <h:outputLabel id="lblAddType" styleClass="label" value="Address Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAddType" styleClass="ddlist" style="width:120px;" value="#{custAdditionalAddress.addressType}" size="1" >
                            <f:selectItems value="#{custAdditionalAddress.addressTypeList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>

                        <h:outputLabel id="lblAddline1" styleClass="label" value="Address Line 1"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtAddline1" styleClass="input" value="#{custAdditionalAddress.localAddressLine1}"  maxlength="50" >
                            <a4j:support event="onblur"/>
                        </h:inputText>

                        <h:outputLabel id="lblAddline2" styleClass="label" value="Address Line 2"/>
                        <h:inputText id="txtAddline2" styleClass="input" value="#{custAdditionalAddress.localAddressLine2}"  maxlength="50" >
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="addressDetails2" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblAddline3" styleClass="label" value="Address Line 3"/>
                        <h:inputText id="txtAddline3" styleClass="input" value="#{custAdditionalAddress.localAddressLine3}"  maxlength="50" >
                            <a4j:support event="onblur"/>
                        </h:inputText>

                        <h:outputLabel id="lblCity" styleClass="label" value="City/Town/Village"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtCity" styleClass="input" value="#{custAdditionalAddress.localAddressCityVillage}"  maxlength="50" >
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblCountry" styleClass="label" value="Country"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCountry" styleClass="ddlist" style="width:120px;" value="#{custAdditionalAddress.localAddressCountry}" size="1" >
                            <f:selectItems value="#{custAdditionalAddress.localAddressCountryList}"/>
                            <a4j:support event="onblur" actionListener="#{custAdditionalAddress.onChangeCountry()}" reRender="addressDetails3"/>
                        </h:selectOneListbox>
                    </h:panelGrid> 
                    <h:panelGrid id="addressDetails3" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:left;" styleClass="row1">

                        <h:outputLabel id="lblState" styleClass="label" value="State"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText  value="#{custAdditionalAddress.stateNotIN}"id="txtPrState" maxlength="50" styleClass="input" 
                                          style="120px;display:#{custAdditionalAddress.displayOnCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:selectOneListbox value="#{custAdditionalAddress.localAddressState}" id="ddPrState" 
                                                style="width:120px;display:#{custAdditionalAddress.displayOnCntryIN};" styleClass="ddlist"  size="1">
                                <f:selectItems value="#{CustAddressInfo.stateForPermanentAddressOption}"/>
                                <a4j:support event="onblur" />
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblDistrict" styleClass="label" value="District"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText  value="#{custAdditionalAddress.districtNotIN}"id="txtPrCity" maxlength="50" styleClass="input" 
                                          style="120px;display:#{custAdditionalAddress.displayOnCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText value="#{custAdditionalAddress.localAddressDistrict}" id="ddPrCity" styleClass="input" 
                                         style="display:#{custAdditionalAddress.displayOnCntryIN};">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <rich:suggestionbox height="100" width="150" usingSuggestObjects="true"
                                                onobjectchange="printObjectsSelected(#{rich:component('suggestion')});"
                                                suggestionAction="#{custAdditionalAddress.getDistrictList}" var="dist"
                                                for="ddPrCity" fetchValue="#{dist}" id="suggestion" >
                                <h:column>
                                    <h:outputText value="#{dist}" />
                                </h:column>
                            </rich:suggestionbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblPincode" styleClass="label" value="Pin Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtPincode" styleClass="input" value="#{custAdditionalAddress.localAddressPINCode}"  maxlength="6" >
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid> 
                    <h:panelGrid id="addressDetails4" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblTypeOfDoc" styleClass="label" value="Address Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddTypeOfDoc" styleClass="ddlist" style="width:120px;" value="#{custAdditionalAddress.proofofAdd}" size="1" >
                            <f:selectItems value="#{CustAddressInfo.prefPoaAddList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblResiNo" styleClass="label" value="Residence Telephone No."></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtResiIsdNo" maxlength="4" styleClass="input" style="width:40px;" value="#{custAdditionalAddress.resiTelSTDCode}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText id="txtResiNo" maxlength="10" styleClass="input" style="width:80px;" value="#{custAdditionalAddress.resiTelNo}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblOfcNo" styleClass="label" value="Office Telephone No."></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtOfcIsd" maxlength="4" styleClass="input" style="width:40px;" value="#{custAdditionalAddress.officeTelSTDCode}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText id="txtOfcNo" maxlength="10" styleClass="input" style="width:80px;" value="#{custAdditionalAddress.officeTelNo}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="addressDetails5" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblMobNo" styleClass="label" value="Mobile No"></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtIsdCode" maxlength="3" styleClass="input" style="width:20px;" value="#{custAdditionalAddress.mobISDCode}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText id="txtMobNo" maxlength="10" styleClass="input" style="width:100px;" value="#{custAdditionalAddress.mobileNo}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblFaxNo" styleClass="label" value="Fax No."></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtFaxSTD" maxlength="4" styleClass="input" style="width:40px;" value="#{custAdditionalAddress.faxSTDCode}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText id="txtFaxNo" maxlength="10" styleClass="input" style="width:80px;" value="#{custAdditionalAddress.faxNo}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblEmail" styleClass="label" value="Email Id"></h:outputLabel>
                        <h:inputText id="txtEmail"  styleClass="input" style="width:120px;" value="#{custAdditionalAddress.emailID}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnRegion">
                                <a4j:commandButton disabled="#{CustomerDetail.flagForSelect}" reRender="txnGrid,stxtError,addDetailsTab"  
                                                   id="btnAdd" value="ADD" 
                                                   action="#{custAdditionalAddress.onAddAdditionalAddress}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:220px;" columnClasses="vtop">
                        <rich:dataTable  value="#{custAdditionalAddress.addressDetailsList}" var="item" 
                                         rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="14"><h:outputText value="Additional Address Details"/></rich:column>
                                    <rich:column breakBefore="true" width="2"><h:outputText value="SNo."/></rich:column>
                                    <rich:column width="50"><h:outputText value="Action" /></rich:column>
                                    <rich:column width="250"><h:outputText value="Address Type" /></rich:column>
                                    <rich:column width="220"><h:outputText value="Address Line 1" /></rich:column>
                                    <rich:column width="250"><h:outputText value="City/Town/Village" /></rich:column>
                                    <rich:column width="250"><h:outputText value="District" /></rich:column>
                                    <rich:column width="250"><h:outputText value="State" /></rich:column>
                                    <rich:column width="250"><h:outputText value="Country" /></rich:column>
                                    <rich:column width="250"><h:outputText value="PIN Code" /></rich:column>
                                    <rich:column width="250"><h:outputText value="Mobile No." /></rich:column>
                                    <rich:column width="250"><h:outputText value="Residencial Tel. No." /></rich:column>
                                    <rich:column width="250"><h:outputText value="Office Tel. No." /></rich:column>
                                    <rich:column width="250"><h:outputText value="Fax No." /></rich:column>
                                    <rich:column width="250"><h:outputText value="Email Id" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{custAdditionalAddress.addressDetailsList.indexOf(item)+1}"/></rich:column>
                            <rich:column>
                                <a4j:commandLink  id="deliteLink" action="#{custAdditionalAddress.onDeleteAddAddress(item)}" reRender="txnGrid" focus="btnAuthorize">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:1" />
                                </a4j:commandLink>
                            </rich:column>
                            <rich:column><h:outputText value="#{item.addressTypeDesc}"/></rich:column>
                            <rich:column><h:outputText value="#{item.localAddressLine1}"/></rich:column>
                            <rich:column><h:outputText value="#{item.localAddressCityVillage}"/></rich:column>
                            <rich:column><h:outputText value="#{item.localAddressDistrict}"/></rich:column>



                            <rich:column><h:outputText value="#{item.localAddressStateDesc}"/></rich:column>
                            <rich:column><h:outputText value="#{item.localAddressCountryDesc}"/></rich:column>
                            <rich:column><h:outputText value="#{item.localAddressPINCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.mobISDCode} "/> <h:outputText value="#{item.mobileNo}"/></rich:column>




                            <rich:column><h:outputText value="#{item.resiTelSTDCode} "/> <h:outputText value="#{item.resiTelNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.officeTelSTDCode} "/> <h:outputText value="#{item.officeTelNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.faxSTDCode} "/> <h:outputText value="#{item.faxNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.emailID}"/></rich:column>


                        </rich:dataTable>
                        <rich:datascroller id="idDataScroll"align="left" for="txnList" maxPages="10"/>
                    </h:panelGrid>
                </rich:panel>
            </h:panelGrid>
        </h:panelGrid>
    </a4j:region>
</h:form>