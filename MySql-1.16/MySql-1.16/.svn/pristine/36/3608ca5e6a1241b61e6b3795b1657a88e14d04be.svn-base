<%-- 
    Document   : FidelityPremiumSlab
    Created on : Apr 25, 2014, 3:22:15 PM
    Author     : sipl
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
            <title>Fidility Premium Slab</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calEffDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="fiForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{FidelityPremiumSlab.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Fidility Premium Slab"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FidelityPremiumSlab.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{FidelityPremiumSlab.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblDesgCode" styleClass="label" value="Designation. :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDesgCode" styleClass="ddlist" value="#{FidelityPremiumSlab.desgCd}" size="1" style="width:120px" disabled="#{FidelityPremiumSlab.dsgFlg}">
                                <f:selectItems value="#{FidelityPremiumSlab.desgCdList}"/>
                                <a4j:support action="#{FidelityPremiumSlab.getData}" event="onblur" reRender="FidilitySlab"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblBondAmt" styleClass="label" value="Bond Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtBondAmt" styleClass="input" value="#{FidelityPremiumSlab.bondAmt}"/>
                            <h:outputLabel id="lblPrAmt" styleClass="label" value="Premium Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtPrAmt" styleClass="input" value="#{FidelityPremiumSlab.prAmt}"/>                           
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblPrd" styleClass="label" value="Period"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtPrd" styleClass="input" value="#{FidelityPremiumSlab.prd}"/>
                            <h:outputLabel id="lblCrHead" styleClass="label" value="Credit GL Head"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtCrHead" styleClass="input" value="#{FidelityPremiumSlab.crHead}"/>
                            <h:outputLabel id="lblDrHead" styleClass="label" value="Debit GL Head"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtDrHead" styleClass="input" value="#{FidelityPremiumSlab.drHead}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputText value="Effective Date" styleClass="label"/>
                            <h:inputText id="txtEffDate" styleClass="input calEffDate" style="width:70px;" value="#{FidelityPremiumSlab.effDt}" disabled="#{FidelityPremiumSlab.effFlg}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                <a4j:support ajaxSingle="true" event="onblur"/>
                            </h:inputText>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="FidilitySlab" width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{FidelityPremiumSlab.fiDir}"
                                            rowClasses="row1, row2" id = "FidilityTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Designation Code"/></rich:column>
                                        <rich:column><h:outputText value="Designation"/></rich:column>
                                        <rich:column><h:outputText value="Bond Amount"/></rich:column>
                                        <rich:column><h:outputText value="Premium Amount"/></rich:column>
                                        <rich:column><h:outputText value="Period"/></rich:column>
                                        <rich:column><h:outputText value="Credit GL"/></rich:column>
                                        <rich:column><h:outputText value="Debit GL"/></rich:column>
                                        <rich:column><h:outputText value="Effective Date"/></rich:column>
                                        <rich:column><h:outputText value="Update" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.dsgCode}"/></rich:column>
                                <rich:column><h:outputText value="#{item.dsgDesc}"/></rich:column>
                                <rich:column><h:outputText value="#{item.bondAmount}"/></rich:column>
                                <rich:column><h:outputText value="#{item.prAmount}"/></rich:column>
                                <rich:column><h:outputText value="#{item.period}"/></rich:column>
                                <rich:column><h:outputText value="#{item.crGl}"/></rich:column>
                                <rich:column><h:outputText value="#{item.drGl}"/></rich:column>
                                <rich:column><h:outputText value="#{item.effDt}"/></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{FidelityPremiumSlab.select}" focus="txtBondAmt" reRender="Row1,Row2,Row3,btnSave,btnUpdate">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{FidelityPremiumSlab.currentItem}"/>                                        
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="FidilityTable" maxPages="40"/>
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
                                            <a4j:commandButton value="Yes" id = "btnYes" ajaxSingle="true" action="#{FidelityPremiumSlab.save}"
                                                               onclick="#{rich:component('savePanel')}.hide();" reRender="lblMsg,BankDirectory,btnSave,leftPanel,FidilitySlab" focus="btnRefresh"/>
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
                                            <a4j:commandButton value="Yes" id = "btnYesU" ajaxSingle="true" action="#{FidelityPremiumSlab.update}"
                                                               oncomplete="#{rich:component('updatePanel')}.hide();" reRender="lblMsg,btnUpdate,leftPanel,FidilitySlab" focus="btnRefresh"/>
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
                            <a4j:commandButton id="btnSave" value="Save" onclick="#{rich:component('savePanel')}.show()" disabled="#{FidelityPremiumSlab.saveDisable}" reRender="lblMsg,FidilitySlab,btnSave,btnUpdate"/>
                            <a4j:commandButton id="btnUpdate" value="Update" onclick="#{rich:component('updatePanel')}.show()"
                                               disabled="#{FidelityPremiumSlab.updateDisable}" reRender="lblMsg,FidilitySlab,btnSave,btnUpdate"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{FidelityPremiumSlab.refresh}" reRender="lblMsg,leftPanel,FidilitySlab,btnSave,btnUpdate"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{FidelityPremiumSlab.exitBtnAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>