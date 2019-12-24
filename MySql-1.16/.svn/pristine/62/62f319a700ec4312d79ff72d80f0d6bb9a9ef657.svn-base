<%-- 
    Document   : BankDirectory
    Created on : 29 Oct, 2010, 4:31:27 PM
    Author     : root
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Bank Directory</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BankDirectory.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Bank Directory"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BankDirectory.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{BankDirectory.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblMicr" styleClass="label" value="Micr"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtMicr" styleClass="input" maxlength="5" onblur="this.value = this.value.toUpperCase();" value="#{BankDirectory.micr}"/>
                            <h:outputLabel id="lblBankCode" styleClass="label" value="Bank Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtBankCode" maxlength="5" styleClass="input" value="#{BankDirectory.bankCode}">
                                <a4j:support  action="#{BankDirectory.securityDetailsTableValues}"  event="onblur"
                                              reRender="leftPanel,lblMsg,leftPanel,BankDirectory,btnSave,txtBankCode,txtBranchNo,txtBankCode" focus="txtBranchNo"/>
                            </h:inputText>
                            <h:outputLabel id="lblBranchNo" styleClass="label" value="Branch No."><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtBranchNo" maxlength="5" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BankDirectory.branchNo}">
                             </h:inputText>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblBranchName" styleClass="label" value="Bank Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtBranchName" maxlength="100" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BankDirectory.branchName}"/>
                            <h:outputText id="lbl1" styleClass="label" />
                            <h:outputText id="lbl2" styleClass="label" />
                            <h:outputText id="lbl3" styleClass="label"/>
                            <h:outputText id="lbl4" styleClass="label"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtBranch" maxlength="150" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BankDirectory.branch}"/>
                            <h:outputLabel id="lbl5" styleClass="label" />
                            <h:outputLabel id="lbl6" styleClass="label" />
                            <h:outputLabel id="lbl7" styleClass="label"/>
                            <h:outputLabel id="lbl8" styleClass="label"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblPhone" styleClass="label" value="Phone No"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtPhone"  maxlength="10" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BankDirectory.phoneNo}"/>
                            <h:outputLabel id="lblFax" styleClass="label" value="Fax"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtFax" maxlength="10" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BankDirectory.faxNo}"/>
                            <h:outputLabel id="lblWeekly" styleClass="label" value="Weekly"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddWeekly" styleClass="ddlist" value="#{BankDirectory.weekly}" size="1">
                                <f:selectItems value="#{BankDirectory.weeklyList}"/>
                                <a4j:support  action="#{BankDirectory.weekly}" event="onblur"
                                              reRender="btnSave"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1,col2" columns="1" id="BankDirectory"  width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{BankDirectory.bankDir}"
                                            rowClasses="row1, row2" id = "BankDirectoryTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="16"></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Branch Code"  /> </rich:column>
                                        <rich:column><h:outputText value="Branch No" /></rich:column>
                                        <rich:column><h:outputText value="Branch" /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Phone No" /></rich:column>
                                        <rich:column><h:outputText value="Fax " /></rich:column>
                                        <rich:column><h:outputText value="Weekly" /></rich:column>
                                        <rich:column><h:outputText value="Update" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.bankCode}"/></rich:column>
                                <rich:column><h:outputText value="#{item.branchNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.branch}"/></rich:column>
                                <rich:column><h:outputText value="#{item.bankName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.phone}"/></rich:column>
                                <rich:column><h:outputText value="#{item.fax}"/></rich:column>
                                <rich:column><h:outputText value="#{item.weekly}"/></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{BankDirectory.select}" focus="txtBranchNo" reRender="Row1,Row2,Row3,Row4,btnSave,btnUpdate">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{BankDirectory.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{BankDirectory.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="BankDirectoryTable" maxPages="40" />
                    </h:panelGrid>

                    <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Are you sure to save data?" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id = "btnYes" ajaxSingle="true" action="#{BankDirectory.save}"
                                                               onclick="#{rich:component('savePanel')}.hide();" reRender="lblMsg,BankDirectory,btnSave,leftPanel " focus="btnRefresh"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel"  id = "btnNo" ajaxSingle="true" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="updatePanel" autosized="true" width="200" onshow="#{rich:element('btnYesU')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Are you sure, You want to update this record?" style="padding-right:15px;" />
                        </f:facet>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink21" />
                                <rich:componentControl for="updatePanel" attachTo="hidelink21" operation="hide" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id = "btnYesU" ajaxSingle="true" action="#{BankDirectory.update}"
                                                               oncomplete="#{rich:component('updatePanel')}.hide();" reRender="lblMsg,btnUpdate,BankDirectory,leftPanel" focus="btnRefresh"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" id = "btnNoU" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Save" onclick="#{rich:component('savePanel')}.show()" disabled="#{BankDirectory.saveDisable}"/>
                            <a4j:commandButton id="btnUpdate" value="Update" onclick="#{rich:component('updatePanel')}.show()"
                                               disabled="#{BankDirectory.updateDisable}" reRender="btnUpdate"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BankDirectory.refresh}" reRender="lblMsg,leftPanel,BankDirectory"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{BankDirectory.exitBtnAction}" focus="txtBankCode"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
