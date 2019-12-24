<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="ad">
    <h:panelGrid id="schemePopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="ShemeAssetDetailsleftPanel10" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="buySelRow" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblDPDCounter" styleClass="label" value="DPD Counter"/>
                <h:inputText id="txtDPDCounter" styleClass="input" style="width:120px" value="#{ad.dPDCounter}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ad.disablePDP }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblMainClass" styleClass="label" value="Main Class"/>
                <h:inputText id="txtMainClass" styleClass="input" style="width:120px" value="#{ad.mainClass}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ad.adFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblSubClass" styleClass="label" value="Sub Class"/>
                <h:inputText id="txtSubClass" styleClass="input"style="width:120px" value="#{ad.subClass}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ad.adFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="buySelRow1" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblIntAccre" styleClass="label" value="Int Accre"/>
                <h:selectOneListbox id="ddIntAccre" styleClass="ddlist" required="true" style="width:120px" value="#{ad.intAccre}" size="1" disabled="#{ad.adFlag }">
                    <f:selectItems value="#{ad.ddAdTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblIntFlagBk" styleClass="label" value="Int Flag Bk"/>
                <h:selectOneListbox id="ddIntFlagBk" styleClass="ddlist" required="true" style="width:120px" value="#{ad.intFlagBk}" size="1" disabled="#{ad.adFlag }">
                    <f:selectItems value="#{ad.ddAdTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblIntFlagColl" styleClass="label" value="Int Flag Coll"/>
                <h:selectOneListbox id="ddIntFlagColl" styleClass="ddlist" required="true" style="width:120px" value="#{ad.intFlagColl}" size="1" disabled="#{ad.adFlag }">
                    <f:selectItems value="#{ad.ddAdTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="buySelRow2" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblPDFlag" styleClass="label" value="PD Flag"/>
                <h:selectOneListbox id="ddPDFlag" styleClass="ddlist" required="true" style="width:120px" value="#{ad.pDFlag}" size="1" disabled="#{ad.adFlag }">
                    <f:selectItems value="#{ad.ddAdTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblIntSuspPlaceHolder" styleClass="label" value="Int Susp PlaceHolder"/>
                <h:inputText id="txtIntSuspPlaceHolder" styleClass="input" style="width:120px" value="#{ad.intSuspPlaceHolder}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ad.adFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblProvisionDR" styleClass="label" value="Provision DR"/>
                <h:inputText id="txtProvisionDR" styleClass="input" style="width:120px" value="#{ad.provisionDR}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ad.adFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="buySelRow3" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblPlaceHoldersCr" styleClass="label" value="PlaceHolders Cr"/>
                <h:inputText id="txtPlaceHoldersCr" styleClass="input" style="width:120px" value="#{ad.placeHoldersCr}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ad.adFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lbldelFlg" styleClass="label" value="Del Flag"/>
                <h:selectOneListbox id="dddelFlg" styleClass="ddlist" required="true" style="width:120px" value="#{ad.delFlag}" size="1" disabled="#{ad.adFlag }">
                    <f:selectItems value="#{ad.ddAdTrnRefNo}"/>
                    <a4j:support action="#{ad.setTable}" event="onblur" reRender="GLridPanel200,buySelRow,buySelRow1,buySelRow2,buySelRow3,lblMsg"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblExpLmt" styleClass="label" />
                <h:outputLabel id="lblExpLmt1" styleClass="label"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="vtop" columns="1" id="GLridPanel200" width="100%" styleClass="row2" style="height:168px;">
                <a4j:region>
                    <rich:dataTable value="#{ad.asset}" var="itemAsset" rowClasses="row1, row2" id="AssetDetailsTable" rows="3" columnsWidth="100" rowKeyVar="row"
                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column colspan="12"></rich:column>
                                <rich:column breakBefore="true"><h:outputText value="DPD Counter" /> </rich:column>
                                <rich:column><h:outputText value="Main Class" /></rich:column>
                                <rich:column><h:outputText value="Sub Class" /></rich:column>
                                <rich:column><h:outputText value="Int Accre" /></rich:column>
                                <rich:column><h:outputText value="Int Flag Bk" /></rich:column>
                                <rich:column><h:outputText value="Int Flag Coll" /></rich:column>
                                <rich:column><h:outputText value="PD Flag" /></rich:column>
                                <rich:column><h:outputText value="Int Susp PlaceHolder" /></rich:column>
                                <rich:column><h:outputText value="Provision DR" /></rich:column>
                                <rich:column><h:outputText value="PlaceHolders Cr" /></rich:column>
                                <rich:column><h:outputText value="Del Flag" /></rich:column>
                                <rich:column>
                                    <h:outputText value="Edit" />
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column><h:outputText value="#{itemAsset.tbdPDCounter}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbMainClass}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbSubClass}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbIntAccre}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbIntFlagBk}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbIntFlagColl}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbPDFlag}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbIntSuspPlaceHolder}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbprovisionDR}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbPlaceHoldersCr}"/></rich:column>
                        <rich:column><h:outputText value="#{itemAsset.tbdelFlag}"/></rich:column>
                        <rich:column>
                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ad.selectAsset}" reRender="ShemeAssetDetailsleftPanel10,lblMsg,AssetDetailsTable">
                                <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                <f:setPropertyActionListener value="#{itemAsset}" target="#{ad.currentItem1}"/>
                                <f:setPropertyActionListener value="#{row}" target="#{ad.currentRow1}" />
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>
                    <rich:datascroller id="scrollerAssetDetailsTable" align="left" for="AssetDetailsTable" maxPages="20" />
                </a4j:region>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
