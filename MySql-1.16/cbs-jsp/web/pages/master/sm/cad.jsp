<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="cad">
    <h:panelGrid id="schemePopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblChequeNotAllowed" styleClass="label" value="Cheque Not Allowed"/>
                <h:panelGroup id="BtnPanelChequeNotAllowed">
                    <h:inputText id="txtChequeNotAllowed" maxlength="3" style="width:120px"  value="#{cad.chequeNotAllowed}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeNotAllowedDisable}" styleClass="input">
                        <a4j:support action="#{cad.descChequeNotAllowed}" event="onblur" reRender="stxtChequeNotAllowed,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeNotAllowed" styleClass="label" value="#{cad.stChequeNotAllowed}" style="color:green;"/>
                <h:outputLabel id="lblChequeNotIssued" styleClass="label" value="Cheque Not Issued"/>
                <h:panelGroup id="BtnPanelChequeNotIssued">
                    <h:inputText id="txtChequeNotIssued" maxlength="3" style="width:120px" value="#{cad.chequeNotIssued}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeNotIssuedDisable}" styleClass="input">
                        <a4j:support action="#{cad.desChequeNotIssued}" event="onblur" reRender="stxtChequeNotIssued,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeNotIssued" styleClass="label" value="#{cad.stChequeNotIssued}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblChequeIssuedToMinor" styleClass="label" value="Cheque Issued To Minor"/>
                <h:panelGroup id="BtnPanelChequeIssuedToMinor">
                    <h:inputText id="txtChequeIssuedToMinor" maxlength="3" style="width:120px" value="#{cad.chequeIssuedToMinor}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeIssuedToMinorDisable}" styleClass="input">
                        <a4j:support action="#{cad.desChequeIssuedToMinor}" event="onblur" reRender="stxtChequeIssuedToMinor,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeIssuedToMinor" styleClass="label" value="#{cad.stChequeIssuedToMinormessage}" style="color:green;"/>
                <h:outputLabel id="lblChequeStopped" styleClass="label" value="Cheque Stopped"/>
                <h:panelGroup id="BtnPanelChequeStopped">
                    <h:inputText id="txtChequeStopped" maxlength="3" style="width:120px" value="#{cad.chequeStopped}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeStoppedDisable}" styleClass="input">
                        <a4j:support action="#{cad.descChequeStopped}" event="onblur" reRender="stxtChequeStopped,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeStopped" styleClass="label" value="#{cad.stChequeStopped}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblChequeCautioned" styleClass="label" value="Cheque Cautioned"/>
                <h:panelGroup id="BtnPanelChequeCautioned">
                    <h:inputText id="txtChequeCautioned" maxlength="3" style="width:120px" value="#{cad.chequeCautioned}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeCautionedDisable}" styleClass="input">
                        <a4j:support action="#{cad.descChequeCautioned}" event="onblur" reRender="stxtChequeCautioned,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeCautioned" styleClass="label" value="#{cad.stChequeCautioned}" style="color:green;"/>
                <h:outputLabel id="lblIntroducerNotCust" styleClass="label" value="Introducer Not Cust"/>
                <h:panelGroup id="BtnPanelIntroducerNotCust">
                    <h:inputText id="txtIntroducerNotCust" maxlength="3" style="width:120px" value="#{cad.introducerNotCust}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.introducerNotCustDisable}" styleClass="input">
                        <a4j:support action="#{cad.descIntroducerNotCust}" event="onblur" reRender="stxtIntroducerNotCust,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtIntroducerNotCust" styleClass="label" value="#{cad.stIntroducerNotCust}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblIntroducerNewCust" styleClass="label" value="Introducer New Cust"/>
                <h:panelGroup id="BtnPanelIntroducerNewCust">
                    <h:inputText id="txtIntroducerNewCust" maxlength="3" style="width:120px" value="#{cad.introducerNewCust}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.introducerNewCustDisable}" styleClass="input">
                        <a4j:support action="#{cad.descIntroducerNewCust}" event="onblur" reRender="stxtIntroducerNewCust,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtIntroducerNewCust" styleClass="label" value="#{cad.stIntroducerNewCust}" style="color:green;"/>
                <h:outputLabel id="lblEmployeesOwnAccount" styleClass="label" value="Employee's Own Account"/>
                <h:panelGroup id="BtnPanelEmployeesOwnAccount">
                    <h:inputText id="txtEmployeesOwnAccount" maxlength="3" style="width:120px" value="#{cad.employeesOwnAccount}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.employeesOwnAccountDisable}" styleClass="input">
                        <a4j:support action="#{cad.descEmployeesOwnAccount}" event="onblur" reRender="stxtEmployeesOwnAccount,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtEmployeesOwnAccount" styleClass="label" value="#{cad.stEmployeesOwnAccount}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblSIPerfixCharges" styleClass="label" value="SI Perfix Charges"/>
                <h:panelGroup id="BtnPanelSIPerfixCharges">
                    <h:inputText id="txtSIPerfixCharges" maxlength="3" style="width:120px" value="#{cad.sIPerfixCharges}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.sIPerfixChargesDisable}" styleClass="input">
                        <a4j:support action="#{cad.descSIPerfixCharges}" event="onblur" reRender="stxtSIPerfixCharges,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtSIPerfixCharges" styleClass="label" value="#{cad.stSIPerfixCharges}" style="color:green;"/>
                <h:outputLabel id="lblDrAgainstCC" styleClass="label" value="Dr Against CC"/>
                <h:panelGroup id="BtnPanelDrAgainstCC">
                    <h:inputText id="txtDrAgainstCC" maxlength="3" style="width:120px" value="#{cad.drAgainstCC}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.drAgainstCCDisable}" styleClass="input">
                        <a4j:support action="#{cad.descDrAgainstCC}" event="onblur" reRender="stxtDrAgainstCC,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtDrAgainstCC" styleClass="label" value="#{cad.stDrAgainstCC}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblClsPendingJobs" styleClass="label" value="Cls Pending Jobs"/>
                <h:panelGroup id="BtnPanelClsPendingJobs">
                    <h:inputText id="txtClsPendingJobs" maxlength="3" style="width:120px" value="#{cad.clsPendingJobs}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.clsPendingJobsDisable}" styleClass="input">
                        <a4j:support action="#{cad.descClsPendingJobs}" event="onblur" reRender="stxtClsPendingJobs,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtClsPendingJobs" styleClass="label" value="#{cad.stClsPendingJobs}" style="color:green;"/>
                <h:outputLabel id="lblClsPendingSI" styleClass="label" value="Cls Pending SI"/>
                <h:panelGroup id="BtnPanelClsPendingSI">
                    <h:inputText id="txtClsPendingSI" maxlength="3" style="width:120px" value="#{cad.clsPendingSI}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.clsPendingSIDisable}" styleClass="input">
                        <a4j:support action="#{cad.descClsPendingSI}" event="onblur" reRender="stxtClsPendingSI,lblMsg" />
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtClsPendingSI" styleClass="label" value="#{cad.stClsPendingSI}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblClsPendingCC" styleClass="label" value="Cls Pending CC"/>
                <h:panelGroup id="BtnPanelClsPendingCC">
                    <h:inputText id="txtClsPendingCC" maxlength="3" style="width:120px" value="#{cad.clsPendingCC}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.clsPendingCCDisable}" styleClass="input">
                        <a4j:support action="#{cad.descClsPendingCC}" event="onblur" reRender="stxtClsPendingCC,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtClsPendingCC" styleClass="label" value="#{cad.stClsPendingCC}" style="color:green;"/>
                <h:outputLabel id="lblClsPendingChq" styleClass="label" value="Cls Pending Chq"/>
                <h:panelGroup id="BtnPanelClsPendingChq">
                    <h:inputText id="txtClsPendingChq" maxlength="3" style="width:120px" value="#{cad.clsPendingChq}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.clsPendingChqDisable}" styleClass="input">
                        <a4j:support action="#{cad.descClsPendingChq}" event="onblur" reRender="stxtClsPendingChq,lblMsg" />
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtClsPendingChq" styleClass="label" value="#{cad.stClsPendingChq}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblClsPendingProxy" styleClass="label" value="Cls Pending Proxy"/>
                <h:panelGroup id="BtnPanelClsPendingProxy">
                    <h:inputText id="txtClsPendingProxy" maxlength="3" style="width:120px" value="#{cad.clsPendingProxy}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.clsPendingProxyDisable}" styleClass="input">
                        <a4j:support action="#{cad.descClsPendingProxy}" event="onblur" reRender="stxtClsPendingProxy,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtClsPendingProxy" styleClass="label" value="#{cad.stClsPendingProxy}" style="color:green;"/>
                <h:outputLabel id="lblChequeIssuedButNotAck" styleClass="label" value="Cheque Issued ButNot Ack"/>
                <h:panelGroup id="BtnPanelChequeIssuedButNotAck">
                    <h:inputText id="txtChequeIssuedButNotAck" maxlength="3" style="width:120px" value="#{cad.chequeIssuedButNotAck}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeIssuedButNotAckDisable}" styleClass="input">
                        <a4j:support action="#{cad.descChequeIssuedButNotAck}" event="onblur" reRender="stxtChequeIssuedButNotAck,lblMsg"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeIssuedButNotAck" styleClass="label" value="#{cad.stChequeIssuedButNotAck}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblChequeUnusable" styleClass="label" value="Cheque Unusable"/>
                <h:panelGroup id="BtnPanelChequeUnusable">
                    <h:inputText id="txtChequeUnusable" maxlength="3" style="width:120px" value="#{cad.chequeUnusable}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeUnusableDisable}" styleClass="input">
                        <a4j:support action="#{cad.descChequeUnusable}" event="onblur" reRender="stxtChequeUnusable,lblMsg" />
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeUnusable" styleClass="label" value="#{cad.stChequeUnusable}" style="color:green;"/>
                <h:outputLabel id="lblChequeStoppedButNotVerified" styleClass="label" value="Cheque Stopped But Not Verified"/>
                <h:panelGroup id="BtnPanelChequeStoppedButNotVerified">
                    <h:inputText id="txtChequeStoppedButNotVerified" maxlength="3" style="width:120px" value="#{cad.chequeStoppedButNotVerified}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cad.chequeStoppedButNotVerifiedDisable}" styleClass="input">
                        <a4j:support action="#{cad.descChequeStoppedButNotVerified}" event="onblur" reRender="stxtChequeStoppedButNotVerified,lblMsg" />
                    </h:inputText>
                </h:panelGroup>
                <h:outputText id="stxtChequeStoppedButNotVerified" styleClass="label" value="#{cad.stChequeStoppedButNotVerified}" style="color:green;"/>
                <rich:modalPanel top="false"autosized="true" width="500" id="showPanel">
                    <f:facet name="header">
                        <h:outputText style="padding-right:15px;"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink2" />
                            <rich:componentControl for="showPanel" attachTo="hidelink2" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:panelGrid id="mainPane2" bgcolor="#fff"  style="border:1px ridge #BED6F8" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{cad.tabSearch}" var="item" rowClasses="gridrow1, gridrow2" id="taskDetailList" rows="10" columnsWidth="100" rowKeyVar="row"
                                               onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                               onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Code Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Code"/></rich:column>
                                        <rich:column><h:outputText value="Description"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.code}"/></rich:column>
                                <rich:column><h:outputText value="#{item.desc}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scroller111" align="left" for="taskDetailList" maxPages="20" />
                        </a4j:region>
                        <h:panelGrid columns="1" id="backButton" styleClass="footer" style="height:30px;" width="100%">
                            <h:panelGroup id="backPanel" layout="block">
                                <a4j:commandButton style="align:center;" value="Exit" onclick="#{rich:component('showPanel')}.hide(); return false;" />
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </rich:modalPanel>
            </h:panelGrid>
            <h:panelGrid columnClasses="col1" columns="1" id="Row11" style="width:100%;text-align:left;" styleClass="row1">
                <h:panelGroup id="BtnHelp">
                    <h:outputLabel id="lblHelp" styleClass="label" value=" ->> For Seeing Code And Description Please Refer The Help Button" style="color:red;"/>
                    <a4j:commandButton id="btnHelp"  value="Help" oncomplete="#{rich:component('showPanel')}.show();" action="#{cad.getTableDetails}" reRender="Row10,taskDetailList,scroller111"/>
                </h:panelGroup>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
