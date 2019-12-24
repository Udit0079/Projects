<%-- 
    Document   : bankprofile
    Created on : 22 Jul, 2014, 4:24:20 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Bank Profile</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{bankProfile.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Bank Profile"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{bankProfile.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{bankProfile.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="functionPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" value="#{bankProfile.function}">
                            <f:selectItems value="#{bankProfile.functionList}"/>
                            <a4j:support event="onblur" action="#{bankProfile.functionProcess}" reRender="mainPanel,stxtMessage,btnHtml" oncomplete="setMask();" focus="txtBankName"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Bank Name"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBankName" styleClass="input" style="width:100px" maxlength="100" onkeyup="this.value=this.value.toUpperCase();" value="#{bankProfile.bankName}"/>
                        <h:outputLabel styleClass="label" value="Registered Office Address"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRegOfficeAdd" styleClass="input" style="width:100px" maxlength="200" onkeyup="this.value=this.value.toUpperCase();" value="#{bankProfile.regOfficeAddress}"/>
                    </h:panelGrid>
                    <h:panelGrid id="regOfficePanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel styleClass="label" value="Registered Office Pin"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRegOfficePin" styleClass="input" style="width:100px" maxlength="10" value="#{bankProfile.regOfficePin}"/>
                        <h:outputLabel styleClass="label" value="HO Office Address"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtHoOfficeAdd" styleClass="input" style="width:100px" maxlength="200" onkeyup="this.value=this.value.toUpperCase();" value="#{bankProfile.hoOfficeAddress}"/>
                        <h:outputLabel styleClass="label" value="HO Office Pin"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtHoOfficePin" styleClass="input" style="width:100px" maxlength="10" value="#{bankProfile.hoOfficePin}"/>
                    </h:panelGrid>
                    <h:panelGrid id="statusPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel styleClass="label" value="Status"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" value="#{bankProfile.status}">
                            <f:selectItems value="#{bankProfile.statusList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Bank Category"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCategory" styleClass="ddlist" size="1" value="#{bankProfile.category}">
                            <f:selectItems value="#{bankProfile.categoryList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="RBI Rating"/>
                        <h:selectOneListbox id="ddRating" styleClass="ddlist" size="1" value="#{bankProfile.rbiRating}">
                            <f:selectItems value="#{bankProfile.rbiRatingList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="subCategoryPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel styleClass="label" value="Sub-Category"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSubCategory" styleClass="ddlist" size="1" value="#{bankProfile.subCategory}">
                            <f:selectItems value="#{bankProfile.subCategoryList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Licence Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtLicenseDate" styleClass="input issueDt" style="width:70px" value="#{bankProfile.licenceDate}">
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Licence No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtLicenceNo" styleClass="input" style="width:100px" maxlength="50" onkeyup="this.value=this.value.toUpperCase();" value="#{bankProfile.licenceNo}"/>
                    </h:panelGrid>
                    <h:panelGrid id="insepectionPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel styleClass="label" value="Last Inspection Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInspectionDate" styleClass="input issueDt" style="width:70px" value="#{bankProfile.lastInspectionDate}">
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Last AGM Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtAgmDate" styleClass="input issueDt" style="width:70px" value="#{bankProfile.agmDate}">
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Total Regular Members"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtTotRegMem" styleClass="input" style="width:100px" value="#{bankProfile.totRegularMembers}"/>
                    </h:panelGrid>
                    <h:panelGrid id="memberPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel styleClass="label" value="Total Nominal Members"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtTotNomMem" styleClass="input" style="width:100px" value="#{bankProfile.totNominalMembers}"/>
                        <h:outputLabel styleClass="label" value="Last Int.Audit Date"/>
                        <h:inputText id="txtIntAuditDate" styleClass="input issueDt" style="width:70px" value="#{bankProfile.internalAuditDate}">
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Major Irregular No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtMajorIrRegNo" styleClass="input" style="width:100px" value="#{bankProfile.majorIrRegNo}"/>
                    </h:panelGrid>
                    <h:panelGrid id="irRegularPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel styleClass="label" value="Minor Irregular No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtMinorIrRegNo" styleClass="input" style="width:100px" value="#{bankProfile.minorIrRegNo}"/>
                        <h:outputLabel styleClass="label" value="Paras Fully No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtParasFullyNo" styleClass="input" style="width:100px" value="#{bankProfile.parasFullyNo}"/>
                        <h:outputLabel styleClass="label" value="Paras Outstanding No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtParasOutNo" styleClass="input" style="width:100px" value="#{bankProfile.parasOutstandNo}"/>
                    </h:panelGrid>
                    <h:panelGrid id="bankLocationPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel styleClass="label" value="Bank Region"/>
                        <h:selectOneListbox id="ddBankRegion" styleClass="ddlist" size="1" value="#{bankProfile.bankRegion}">
                            <f:selectItems value="#{bankProfile.bankRegionList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="#{bankProfile.btnValue}" action="#{bankProfile.alertMsgAction}" oncomplete="#{rich:component('savePanel')}.show();" reRender="stxtMessage,savePanel,cMsg"/>
                            <a4j:commandButton action="#{bankProfile.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="stxtMessage,mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{bankProfile.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="btnActionGrid">
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="cMsg" value="#{bankProfile.confirmationMessage}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnSaveYes" action="#{bankProfile.btnSaveAction}" oncomplete="#{rich:component('savePanel')}.hide();" reRender="stxtMessage,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>
