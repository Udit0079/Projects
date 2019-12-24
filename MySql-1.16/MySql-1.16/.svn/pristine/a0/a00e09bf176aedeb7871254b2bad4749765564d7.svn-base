<%-- 
    Document   : contractordetails
    Created on : 25 july 2011, 4:38:57 PM
    Author     : Zeeshan Waris
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Contractor Details</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="ContractorDetails">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ContractorDetails.userName}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Contractor Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ContractorDetails.todayDate}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row2">
                            <h:outputText id="lblMsg" styleClass="error" value="#{ContractorDetails.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblContractorFirstName" styleClass="label" value="Contractor First Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtContractorFirstName" styleClass="input"  value="#{ContractorDetails.firstName}"  maxlength="15" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblContractorMidName" styleClass="label" value="Contractor Mid Name"/>
                            <h:inputText id="txtContractorMidName" styleClass="input"  value="#{ContractorDetails.midName}"  maxlength="15" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblContractorLastName" styleClass="label" value="Contractor Last Name"/>
                            <h:inputText id="txtContractorLastName" styleClass="input"  value="#{ContractorDetails.lastName}"  maxlength="15" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblContractorAddress" styleClass="label" value="Contractor Address"/>
                            <h:inputText id="txtContractorAddress" styleClass="input"  value="#{ContractorDetails.address}"  maxlength="100" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblCity" styleClass="label" value="City/Village"/>
                            <h:inputText id="txtCity" styleClass="input"  value="#{ContractorDetails.city}"  maxlength="15" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblPin" styleClass="label" value="Pin"/>
                            <h:inputText id="txtPin" styleClass="input"  value="#{ContractorDetails.pin}"  maxlength="8" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblTelephone" styleClass="label" value="Telephone"/>
                            <h:inputText id="txtTelephone" styleClass="input"  value="#{ContractorDetails.telephone}"  maxlength="20" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblResiTelNo" styleClass="label" value="Resi. Tel. No."/>
                            <h:inputText id="txtResTelNo" styleClass="input"  value="#{ContractorDetails.residenaceTelephone}"  maxlength="20" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblMobile" styleClass="label" value="Mobile"/>
                            <h:inputText id="txtMobile" styleClass="input"  value="#{ContractorDetails.mobile}"  maxlength="20" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFax" styleClass="label" value="Fax"/>
                            <h:inputText id="txtFax" styleClass="input"  value="#{ContractorDetails.fax}"  maxlength="20" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblState" styleClass="label" value="State"/>
                            <h:inputText id="txtState" styleClass="input"  value="#{ContractorDetails.state}"  maxlength="15" style="width:140px;" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblEmailId" styleClass="label" value="E-mail Id"/>
                            <h:inputText id="txtEmailId" styleClass="input"  value="#{ContractorDetails.emailid}"  maxlength="25" style="width:140px;"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                        <rich:contextMenu attached="false" id="menuCustomerDetails" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                           actionListener="#{ContractorDetails.fetchCurrentRow}">
                                <a4j:actionparam name="Description" value="{Description}"/>
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu>
                        <a4j:region>
                            <rich:dataTable value="#{ContractorDetails.contSearch}" var="item"
                                            rowClasses="row1, row2" id="ListCustomerDetails" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="6">
                                            <h:outputText value="Description Table"/>
                                        </rich:column>
                                        <rich:column breakBefore="true" width="30%"><h:outputText value="Contractor Name"/></rich:column>
                                        <rich:column width="20%"><h:outputText value="City"/></rich:column>
                                        <rich:column width="15%"><h:outputText value="Mobile No"/></rich:column>
                                        <rich:column width="15%"><h:outputText value="Telephone No"/></rich:column>
                                        <rich:column width="10%"><h:outputText value="Delete"/></rich:column>
                                        <rich:column width="10%"><h:outputText value="Update"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.contFirstName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.city}"/></rich:column>
                                <rich:column><h:outputText value="#{item.mobileNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.tel}"/></rich:column>

                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" action="#{ContractorDetails.SelectCurrentroewforEdit}" reRender="leftPanel,subbodyPanel" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{row}" target="#{ContractorDetails.currentRow}" />
                                        <f:setPropertyActionListener value="#{item}" target="#{ContractorDetails.currentItem}"/>
                                    </a4j:commandLink>
                                    <rich:toolTip for="deletelink" value="Delete" />
                                </rich:column>

                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ContractorDetails.SelectCurrentroewforEdit}" reRender="ddbiltype,leftPanel,btnSave,btnupdate,txtCode,txtContractorCode,btnContractorCode" focus="btnSave">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{ContractorDetails.currentItem}"/>
                                    </a4j:commandLink>
                                    <rich:toolTip for="selectlink" value="Update" />
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scroller20" align="left" for="ListCustomerDetails" maxPages="5" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{ContractorDetails.saveContractorDetailAction}" disabled="#{ContractorDetails.disableSave}" reRender="lblMsg,mainPanel,gridPanel103"  focus="btnExit"/>
                            <a4j:commandButton id="btnupdate" value="Update" action="#{ContractorDetails.updateTemporaryStaffAction}" disabled="#{ContractorDetails.disableUpdate}" reRender="mainPanel,acView,stxtMsg"/>
                            <a4j:commandButton  id="btnrefresh" type="reset" value="Refresh" action="#{ContractorDetails.refreshButtonAction}"  reRender="mainPanel,btnupdate,btnSave,lblMsg"/>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{ContractorDetails.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>


                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
                    </f:facet>

                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To Delete ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:region id="yesBtn">
                                            <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{ContractorDetails.deleteContractorDetailsAction}"
                                                               onclick="#{rich:component('deletePanel')}.hide();" reRender="lblMsg,leftPanel,gridPanel103" />
                                        </a4j:region>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="No" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>


