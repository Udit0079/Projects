<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="minorInfo">
    <a4j:region>
        <h:panelGrid id="MinInfoPanel" columns="2" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel6" style="width:100%;text-align:center;">
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow1" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMinDob" styleClass="label" value="Date Of Birth"/>
                    <rich:calendar  value="#{MinorInfo.minorDob}"datePattern="dd/MM/yyyy" id="MinDob" jointPoint="bottom-left" direction="bottom-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{MinorInfo.onchangeMinorDateofBirth}" reRender="stxtError" oncomplete="#{rich:element('txtGurdCode')}.focus();"/>
                    </rich:calendar>
                    <h:outputLabel id="lblMajDt" styleClass="label" value="Majority Date"/>
                    <rich:calendar  value="#{MinorInfo.majoritydate}"datePattern="dd/MM/yyyy" id="MajDt"  jointPoint="bottom-left" direction="bottom-right" focus="txtGurdCode" inputSize="10">
                        <a4j:support event="onchanged" focus="txtGurdCode" actionListener="#{MinorInfo.onchangeMajorityDate}" reRender="stxtError"/>
                    </rich:calendar>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow2" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblGurdCode" styleClass="label" value="Guardian Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                    <h:inputText  value="#{MinorInfo.guardianCode}"id="txtGurdCode" maxlength="5"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMinTitle" styleClass="label" value="Title"/>
                    <h:selectOneListbox id="ddMinTitle" styleClass="ddlist" value="#{MinorInfo.titleTypeGuar}" size="1">
                        <f:selectItems value="#{MinorInfo.titleTypeGuarOption}"/>
                        <%--<rich:toolTip for="ddMinTitle" value="Please Select Title"></rich:toolTip>--%>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblGurdName" styleClass="label" value="Guardian Name"/>
                    <h:inputText  value="#{MinorInfo.guagdianName}"id="txtGurdName" maxlength="90"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support actionListener="#{MinorInfo.guardianName}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow4" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMinAddr1" styleClass="label" value="Address Line1"/>
                    <h:inputText  value="#{MinorInfo.guarAdd1}"id="txtMinAddr1" maxlength="50"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:outputLabel id="lblMinAddr2" styleClass="label" value="Address Line2"/>
                    <h:inputText  value="#{MinorInfo.guarAdd2}"id="txtMinAddr2" maxlength="50"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow5" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMinVill" styleClass="label" value="Village"><font class="required" style="color:red;">*</font></h:outputLabel>
                    <h:inputText  value="#{MinorInfo.guarVillage}"id="txtMinVill" maxlength="45"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:outputLabel id="lblMinTehsil" styleClass="label" value="Tehsil Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                    <h:inputText  value="#{MinorInfo.guarTehsilCode}"id="txtMinTehsil" maxlength="45"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support actionListener="#{MinorInfo.onblurGuarTehsilCode}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow6" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMinCity" styleClass="label" value="City"/>
                    <h:selectOneListbox  value="#{MinorInfo.cityForMinorAddress}"id="ddMinCity" styleClass="ddlist"  size="1">
                        <f:selectItems value="#{MinorInfo.cityForMinorAddressOption}"/>
                        <%--<rich:toolTip for="ddMinCity" value="Please Select City"></rich:toolTip>--%>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblMinState" styleClass="label" value="State"/>
                    <h:selectOneListbox value="#{MinorInfo.stateForMinorAddress}" id="ddMinState" style="width:72px;"styleClass="ddlist"  size="1">
                        <f:selectItems value="#{MinorInfo.stateForMinorAddressOption}"/>
                        <%--<rich:toolTip for="ddMinState" value="Please Select State"></rich:toolTip>--%>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow7" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMinPostal" styleClass="label" value="Postal Code"/>
                    <h:inputText  value="#{MinorInfo.guarpostal}"id="txtMinPostal" maxlength="6"styleClass="input" style="120px">
                        <a4j:support actionListener="#{MinorInfo.onblurMinorPostalCode}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                    <h:outputLabel id="lblMinCountry" styleClass="label" value="Country"/>
                    <h:selectOneListbox value="#{MinorInfo.countryForMinorAddress}" id="ddMinCountry" styleClass="ddlist"  size="1">
                        <f:selectItems value="#{MinorInfo.countryForMinorAddressOption}"/>
                        <%--<rich:toolTip for="ddMinCountry" value="Please Select Country"></rich:toolTip>--%>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow8" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMinPhoneNo" styleClass="label" value="Phone No"/>
                    <h:inputText  value="#{MinorInfo.guarPhone}"id="txtMinPhoneNo" maxlength="15"styleClass="input" style="120px">
                        <a4j:support actionListener="#{MinorInfo.onblurMinorPhone}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                    <h:outputLabel id="lblMinMob" styleClass="label" value="Mobile No"/>
                    <h:inputText   value="#{MinorInfo.guarMob}"id="txtMinMob" maxlength="15"styleClass="input" style="120px">
                        <a4j:support actionListener="#{MinorInfo.onblurMinorMobile}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="minRow9" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMinEmail" styleClass="label" value="E-Mail ID"/>
                    <h:inputText  value="#{MinorInfo.guarEmail}"id="txtMinEmail" maxlength="40"styleClass="input" style="120px">
                        <a4j:support actionListener="#{MinorInfo.onblurMinorEmail}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>
            </h:panelGrid>
        </h:panelGrid>
    </a4j:region>
</h:form>
