<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="nreInfo">
    <a4j:region>
        <h:panelGrid id="NreInfoPanel" columns="2" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel7" style="width:100%;text-align:center;">
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="nreRow1" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblNonResDt" styleClass="label"  value="Date Of Becoming NRE"/>
                    <rich:calendar value="#{NreInfo.dateOfBeingNre}" datePattern="dd/MM/yyyy" id="NonResDt" 
                                   jointPoint="top-left" direction="right" inputSize="10" inputStyle="#{NreInfo.dateOfBeingNreChgFlag}">
                        <a4j:support event="onchanged"/>
                    </rich:calendar>
                    <h:outputLabel id="lblCountry" styleClass="label" value="Country"/>
                    <h:selectOneListbox value="#{NreInfo.countryForNre}" id="ddCountry" style="width:72px;#{NreInfo.countryForNreChgFlag}" styleClass="ddlist" size="1">
                        <f:selectItems value="#{NreInfo.countryForNreOption}"/>
                        <a4j:support event="onblur"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="nreRow2" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblLocContPCd" styleClass="label" value="NRE Relative Customer Id"/>
                    <h:inputText  id="txtLocContPCd" value="#{NreInfo.nreRelativeCode}" maxlength="10" styleClass="input" 
                                  style="120px;#{NreInfo.nreRelativeCodeChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur" actionListener="#{NreInfo.onblurGuardianCustId}" reRender="stxtRelativeCustId"/>
                    </h:inputText>
                    <h:outputLabel id="lblRelativeCustId" styleClass="label" value="NRE Relative Customer Name"/>
                    <h:outputText id="stxtRelativeCustId" styleClass="output" value="#{NreInfo.relativeCustName}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="nreRow3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblLocAddr1" styleClass="label" value="Address Line1"/>
                    <h:inputText value="#{NreInfo.nreAdd1}" id="txtLocAddr1" maxlength="50" styleClass="input" 
                                  style="120px;#{NreInfo.nreAdd1ChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblLocAddr2" styleClass="label" value="Address Line2"/>
                    <h:inputText value="#{NreInfo.nreAdd2}" id="txtLocAddr2" maxlength="50" styleClass="input" 
                                 style="120px;#{NreInfo.nreAdd2ChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="nreRow5" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblNreVill" styleClass="label" value="Village / Street"/>
                    <h:inputText  value="#{NreInfo.nreVillCode}" id="txtNreVill" maxlength="45" styleClass="input" 
                                  style="120px;#{NreInfo.nreVillCodeChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblNreCity" styleClass="label" value="City"/>
                    <h:inputText id="txtNreCity" styleClass="input" value="#{NreInfo.cityForNre}" maxlength="50" 
                                 style="120px;#{NreInfo.cityForNreChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="nreRow6" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblNreState" styleClass="label" value="State"/>
                    <h:inputText id="txtNreState" value="#{NreInfo.stateForNre}" maxlength="50" 
                                 styleClass="input" style="120px;#{NreInfo.stateForNreChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblNrePostal" styleClass="label" value="Postal Code"/>
                    <h:inputText  value="#{NreInfo.nrePostal}"id="txtNrePostal" maxlength="6" styleClass="input" 
                                  style="120px;#{NreInfo.nrePostalChgFlag}">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="nreRow9" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblLocPhoneNo" styleClass="label" value="Phone No"/>
                    <h:inputText value="#{NreInfo.nrePhoneNo}" id="txtLocPhoneNo" maxlength="15" 
                                 styleClass="input" style="120px;#{NreInfo.nrePhoneNoChgFlag}">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblLocTelex" styleClass="label" value="Mobile No"/>
                    <h:inputText  value="#{NreInfo.nreMob}"id="txtLocTelex" maxlength="15" styleClass="input" 
                                  style="120px;#{NreInfo.nreMobChgFlag}">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="nreRow10" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblNreRelEmail" styleClass="label" value="E-Mail ID"/>
                    <h:inputText  value="#{NreInfo.nreEmail}"id="txtNreRelEmail" maxlength="40" styleClass="input" 
                                  style="120px;#{NreInfo.nreEmailChgFlag}">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblNonResEndDt" styleClass="label" value="NRE Becoming Resident"/>
                    <rich:calendar  value="#{NreInfo.nreBecomResident}" datePattern="dd/MM/yyyy" id="NonResEndDt"  
                                    jointPoint="top-left" direction="top-right" inputSize="10" inputStyle="#{NreInfo.nreBecomResidentChgFlag}">
                        <a4j:support actionListener="#{NreInfo.onblurNreBecomeResident}" event="onchanged" reRender="stxtError"/>
                    </rich:calendar>
                </h:panelGrid>
            </h:panelGrid>
        </h:panelGrid>
    </a4j:region>
</h:form>
