<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="relatedperson">
    <a4j:region>
        <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="row1" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelPersonType" styleClass="label" value="Type of RelationShip"/>
            <h:selectOneListbox id="ddRelPersonType1" styleClass="ddlist" value="#{RelatedPerson.relPersonType}" 
                                size="1" disabled="#{RelatedPerson.otherFieldDisable}">
                <f:selectItems value="#{RelatedPerson.relPersonTypeList}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblRelCustId1" styleClass="label" value="Related Customer Id"/>
            <h:panelGroup id = "rid">
            <h:inputText id="txtRelCustId1" styleClass="input" style="100px" maxlength="10" 
                         value="#{RelatedPerson.relatedCustId}" disabled="#{RelatedPerson.otherFieldDisable}">
                <a4j:support event="onblur" actionListener="#{RelatedPerson.getCustDetail}" 
                             reRender="stxtRelCustName1,ddRelPersonType1" />
            </h:inputText>
             <h:outputText id="stxtRelCustName1" styleClass="output" value="#{RelatedPerson.relatedCustName}"/>
            </h:panelGroup>
             
          
         </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row2" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblFunctionType" styleClass="label" value="Function Flag"/>
            <h:selectOneListbox id="ddFunction1" styleClass="ddlist" value="#{RelatedPerson.flagType}" 
                                size="1">
                <f:selectItems value="#{RelatedPerson.flagTypeList}"/>
                  <a4j:support event="onblur" actionListener="#{RelatedPerson.onblurflagType}" reRender="realtedPersonsGrid,txnList1,stxtError"/>
            </h:selectOneListbox>
            
            <h:outputLabel/>
            <h:outputLabel/>
          
            
            
              </h:panelGrid>
    <%--    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row2" 
                     style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblRelFirstName" styleClass="label" value="First Name"/>
            <h:inputText id="txtRelFirstName" maxlength="70" styleClass="input" style="120px" 
                         value="#{RelatedPerson.relatedFirstName}" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPerson.fieldDisable}"/>
            <h:outputLabel id="lblRelMiddleName" styleClass="label" value="Middle Name"/>
            <h:inputText id="txtRelMiddleName" maxlength="70" styleClass="input" style="120px" 
                         value="#{RelatedPerson.relatedMiddleName}" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPerson.fieldDisable}"/>
            <h:outputLabel id="lblRelLastName" styleClass="label" value="Last Name"/>
            <h:inputText id="txtRelLastName" maxlength="70" styleClass="input" style="100px" 
                         value="#{RelatedPerson.relatedLastName}" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPerson.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row3" 
                     style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelPan" styleClass="label" value="Pan No"/>
            <h:inputText id="txtRelPan" styleClass="input" style="120px" 
                         value="#{RelatedPerson.relPanNo}" disabled="#{RelatedPerson.fieldDisable}" maxlength="10"
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblRelUid" styleClass="label" value="UID"/>
            <h:inputText id="txtRelUid" styleClass="input" style="120px" 
                         value="#{RelatedPerson.relUid}" disabled="#{RelatedPerson.fieldDisable}" maxlength="12"
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel/>
            <h:outputLabel/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row4" 
                     style="width:100%;text-align:left;display:#{RelatedPerson.relIndView}" styleClass="row2">
            <h:outputLabel id="lblNregaJobCard" styleClass="label" value="NREGA Job Card"/>
            <h:inputText id="txtNregaJobCard" styleClass="input" style="120px" value="#{RelatedPerson.relNregaJobCard}" 
                         disabled="#{RelatedPerson.fieldDisable}" maxlength="25" 
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblPassNo" styleClass="label" value="Passport No"/>
            <h:inputText id="txtPassNo" styleClass="input" style="120px" value="#{RelatedPerson.relPassportNo}" 
                         disabled="#{RelatedPerson.fieldDisable}" maxlength="32" onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblPassExpDt" styleClass="label" value="Passport Expiry Date"/>
            <rich:calendar id="txtPassExpDt" value="#{RelatedPerson.relPassportExpDt}" datePattern="dd/MM/yyyy" 
                           jointPoint="top-left" direction="top-right" inputSize="10" 
                           disabled="#{RelatedPerson.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row5" 
                     style="width:100%;text-align:left;display:#{RelatedPerson.relIndView}" styleClass="row1">
            <h:outputLabel id="lblVoterIdCard" styleClass="label" value="Voter ID Card"/>
            <h:inputText id="txtVoterIdCard" styleClass="input" style="120px;" 
                         value="#{RelatedPerson.relVoterIdCard}" disabled="#{RelatedPerson.fieldDisable}" 
                         maxlength="10" onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblDrLicNo" styleClass="label" value="Driving License No"/>
            <h:inputText id="txtDrLicNo" styleClass="input" style="120px" value="#{RelatedPerson.relDrivingLiscNo}" 
                         maxlength="10" disabled="#{RelatedPerson.fieldDisable}" 
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblDlExpiry" styleClass="label" value="Driving License Expiry Date"/>
            <rich:calendar id="DlExpiry" value="#{RelatedPerson.dlExpiry}" datePattern="dd/MM/yyyy" 
                           jointPoint="top-left" direction="top-right" inputSize="10" 
                           disabled="#{RelatedPerson.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row6" 
                     style="width:100%;text-align:left;display:#{RelatedPerson.relComView}" styleClass="row2">
            <h:outputLabel id="lblDin" styleClass="label" value="DIN"/>
            <h:inputText id="txtDin" styleClass="input" style="120px;" value="#{RelatedPerson.relDin}" 
                         disabled="#{RelatedPerson.fieldDisable}" maxlength="8" 
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblExposed" styleClass="label" value="Politicaly Exposed"/>
            <h:selectOneListbox id="ddExposed" styleClass="ddlist" value="#{RelatedPerson.relExposed}" 
                                size="1" disabled="#{RelatedPerson.fieldDisable}">
                <f:selectItems value="#{RelatedPerson.relExposedList}"/>
            </h:selectOneListbox>
            <h:outputLabel/>
            <h:outputLabel/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row7" 
                     style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelAddr1" styleClass="label" value="Address Line1"/>
            <h:inputText id="txtRelAddr1" styleClass="input" style="120px" value="#{RelatedPerson.relAdd1}" 
                         maxlength="250" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPerson.fieldDisable}"/>
            <h:outputLabel id="lblRelAddr2" styleClass="label" value="Address Line2"/>
            <h:inputText id="txtRelAddr2" styleClass="input" style="120px" value="#{RelatedPerson.relAdd2}" 
                         maxlength="250" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPerson.fieldDisable}"/>
            <h:outputLabel id="lblRelCity" styleClass="label" value="City"/>
            <h:selectOneListbox id="ddRelCity" styleClass="ddlist" value="#{RelatedPerson.relCity}" 
                                size="1" disabled="#{RelatedPerson.fieldDisable}">
                <f:selectItems value="#{RelatedPerson.relCityList}"/>
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row8" 
                     style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblRelState" styleClass="label" value="State"/>
            <h:selectOneListbox id="ddRelState" styleClass="ddlist" value="#{RelatedPerson.relState}" 
                                size="1" disabled="#{RelatedPerson.fieldDisable}">
                <f:selectItems value="#{RelatedPerson.relStateList}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblRelCountry" styleClass="label" value="Country"/>
            <h:selectOneListbox id="ddRelCountry" styleClass="ddlist" value="#{RelatedPersonInfo.relCountry}" 
                                size="1" disabled="#{RelatedPersonInfo.fieldDisable}">
                <f:selectItems value="#{RelatedPersonInfo.relCountryList}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblRelPostalCode" styleClass="label" value="Postal Code"/>
            <h:inputText id="txtJuriPostalCode" styleClass="input" style="120px" value="#{RelatedPerson.relPostalCode}" 
                         maxlength="6" disabled="#{RelatedPerson.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row9" 
                     style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelStatus" styleClass="label" value="Status"/>
            <h:selectOneListbox id="ddRelStatus" styleClass="ddlist" value="#{RelatedPerson.relStatus}" 
                                size="1">
                <f:selectItems value="#{RelatedPerson.relStatusList}"/>
                <a4j:support event="onblur" actionListener="#{RelatedPerson.onblurStatus}" reRender="realtedPersonsGrid,txnList,stxtError"/>
            </h:selectOneListbox>
            <h:outputLabel/>
            <h:outputLabel/>
            <h:outputLabel/>
            <h:outputLabel/>
        </h:panelGrid>   --%>
        <h:panelGrid id="realtedPersonsGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
            <rich:dataTable  value="#{RelatedPerson.rpiTableList}" var="item"
                             rowClasses="gridrow1, gridrow2" id="txnList1" rows="5" columnsWidth="100"
                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                             width="100%">
                <f:facet name="header">
                    <rich:columnGroup>
                        <rich:column colspan="8"><h:outputText value="Related Person Detail"/></rich:column>
                        <rich:column breakBefore="true"><h:outputText value="Related Cust. ID"/></rich:column>
                        <rich:column><h:outputText value="Related Person Name"/></rich:column>
                        <rich:column><h:outputText value="Relationship Code" /></rich:column>
                         <rich:column><h:outputText value="Flag" /></rich:column>
                        <rich:column visible="#{RelatedPerson.selVisible}"><h:outputText value="Select"/></rich:column>
                        <rich:column visible="#{RelatedPerson.delVisible}"><h:outputText value="Delete"/></rich:column>
                    </rich:columnGroup>
                </f:facet>
                <rich:column><h:outputText value="#{item.personCustomerid}"/></rich:column>
                <rich:column><h:outputText value="#{item.completeName}"/></rich:column>   
                <rich:column><h:outputText value="#{item.relationshipCode}"/></rich:column>
                <rich:column><h:outputText value="#{item.delFlag}"/></rich:column>                            
                <rich:column style="text-align:center;width:40px">
                    <a4j:commandLink id="selectlink" action="#{RelatedPerson.setTableToForm}" reRender="txtRelCustId1,ddRelPersonType1,ddFunction1">
                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                        <f:setPropertyActionListener value="#{item}" target="#{RelatedPerson.currentItem}"/>                                    
                    </a4j:commandLink>
                </rich:column>
               <rich:column style="text-align:center;width:40px">
                    <a4j:commandLink id="deletelink" action="#{RelatedPerson.deleteForm}" reRender="txtRelCustId1,ddRelPersonType1,ddFunction1,txnList1">
                        <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                        <f:setPropertyActionListener value="#{item}" target="#{RelatedPerson.currentItem}"/>                                    
                    </a4j:commandLink>
                </rich:column>
            </rich:dataTable>
            <rich:datascroller id="unauthDataScroll" align="left" for="txnList1" maxPages="20"/>
  </h:panelGrid>
</a4j:region>
</h:form> 

