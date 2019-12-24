

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Branch Master</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BranchMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Branch Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BranchMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{BranchMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{BranchMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid columnClasses="col9" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblBranchCode" styleClass="label" value="Branch Code :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtBranchCode" maxlength="3" tabindex="1" value="#{BranchMaster.brCode}" onkeyup="this.value = this.value.toUpperCase();"  size="20" styleClass="input">
                                    <a4j:support action="#{BranchMaster.checkBranchCode}" event="onblur"
                                                 reRender="a8,a11,a10,a12,a14,a17,a16,a18,lpg,message,errorMessage" limitToList="true" focus="txtAlphaCode"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblAlphaCode" styleClass="label" value="Alpha Code :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtAlphaCode" maxlength="6" tabindex="2" size="20" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.alphaCode}" />
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="a11" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblBranchName" styleClass="label" value="Branch Name :" style="padding-left:75px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBranchName" maxlength="50"  tabindex="3" size="60" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.brName}" />
                            <h:outputLabel id="lblBranchMobNO" styleClass="label" value="Branch Mobile No :" style="padding-left:75px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBranchMobNO" maxlength="50"  tabindex="3" size="10" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.mobileNo}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13" columns="2" id="a12" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblBrnAddress" styleClass="label" value="Branch Address :" style="padding-left:75px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBrnAddress" maxlength="150"  tabindex="4" size="60" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.brAddress}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a13" width="100%">
                            <h:panelGrid columnClasses="col9" columns="2" id="a14" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblCity" styleClass="label" value="City :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCity"  maxlength="50" disabled="false" size="20" tabindex="5" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.city}" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a16" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblState" styleClass="label" value="State :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtState" maxlength="50" tabindex="6" disabled="false" size="20" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.state}" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a17" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblPinCode" styleClass="label" value="Pin Code :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtPinCode" maxlength="6" tabindex="7" value="#{BranchMaster.pinCode}" onkeyup="this.value = this.value.toUpperCase();"  size="20" styleClass="input">
                                    <a4j:support action="#{BranchMaster.validatePinCode}" event="onblur"
                                                 reRender="a17,lpg,message,errorMessage" limitToList="true" focus="txtRegOffice"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a18" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblRegOffice" styleClass="label" value="Regional Office :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtRegOffice" maxlength="50" tabindex="8" disabled="false" size="20" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.regOffice}" />
                            </h:panelGrid>
                            <h:panelGrid id="ifscGrid" columnClasses="col9" columns="2" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblIfsc" styleClass="label" value="IFSC Code" style="padding-left:70px;"/>
                                <h:inputText id="txtIfsc"  tabindex="9"  maxlength="11" disabled="false" size="20" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{BranchMaster.ifscCode}" />
                            </h:panelGrid>
                            <h:panelGrid id="blankGrid" columnClasses="col9" columns="2" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel styleClass="label" style="padding-left:70px;" value="Location Type"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddLocation" tabindex="10" styleClass="ddlist" size="1" value="#{BranchMaster.locationType}">
                                    <f:selectItems value="#{BranchMaster.locationTypeList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="compPanel" width="100%">
                            <h:panelGrid columnClasses="col9" columns="2" id="a88" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel styleClass="label" style="padding-left:70px;" value="Computerized Status"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddCompStatus" styleClass="ddlist" tabindex="11" size="1" value="#{BranchMaster.computerStatus}">
                                    <f:selectItems value="#{BranchMaster.computerStatusList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a89" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{BranchMaster.gridDetail}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Branch Code" /></rich:column>
                                            <rich:column><h:outputText value="Branch Name" /></rich:column>
                                            <rich:column><h:outputText value="Alpha Code" /></rich:column>
                                            <rich:column><h:outputText value="Branch Address" /></rich:column>
                                            <rich:column><h:outputText value="City" /></rich:column>
                                            <rich:column><h:outputText value="State" /></rich:column>
                                            <rich:column><h:outputText value="Pin Code" /></rich:column>
                                            <rich:column><h:outputText value="Regional Office" /></rich:column>
                                          <rich:column><h:outputText value="Mobile No" /></rich:column>
                                            <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                            <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.brnCode}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.brnName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.alphaCode}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.brnAddress}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.city}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.state}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.pinCode}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.regOffice}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.mobileNo}" /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="a8,a10,message,errorMessage,lpg,a16,a18,a14,a17,a11,a12">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{BranchMaster.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{BranchMaster.currentRow}" />
                                        </a4j:commandLink>
                                        <rich:toolTip for="deletelink" value="Delete" />
                                    </rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" action="#{BranchMaster.fillValuesofGridInFields}" oncomplete="#{rich:element('txtBranchCode')}.disabled = true;" focus="btnModify"
                                                         reRender="a8,a10,message,errorMessage,lpg,a16,a18,a14,a17,a11,a12,gpFooter,txtIfsc,ddCompStatus,ddLocation" >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{BranchMaster.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{BranchMaster.currentRow}"/>
                                        </a4j:commandLink>
                                        <rich:toolTip for="selectlink" value="Click To Select This Row."/>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="12" value="Save" oncomplete="#{rich:component('savePanel')}.show()" 
                                                   reRender="message,errorMessage" rendered="#{BranchMaster.flag == 'true'}"/>

                            <a4j:commandButton id="btnModify" tabindex="13" value="Modify" oncomplete="#{rich:component('modifyPanel')}.show()" 
                                               reRender="message,errorMessage"  rendered="#{BranchMaster.flag == 'false'}"/>

                            <a4j:commandButton id="btnRefresh" tabindex="14" value="Refresh" oncomplete="#{rich:element('txtBranchCode')}.disabled = false;"
                                               action="#{BranchMaster.refreshForm}" reRender="taskList,a8,a11,a10,a12,a14,a17,a16,a18,lpg,message,errorMessage,gpFooter,txtIfsc,ddCompStatus,ddLocation"
                                               focus="txtBranchCode"/>

                            <a4j:commandButton id="btnExit" tabindex="15" value="Exit" action="#{BranchMaster.exitForm}" 
                                               reRender="taskList,a8,a11,a10,a12,a14,a17,a16,a18,lpg,message,errorMessage,gpFooter,txtIfsc,ddCompStatus,ddLocation"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnDelyes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnDelyes" ajaxSingle="true"  action="#{BranchMaster.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,a8,a11,a10,a12,a14,a17,a16,a18,lpg,message,errorMessage,gpFooter,txtIfsc,ddCompStatus,ddLocation" focus="txtBranchCode"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnDelNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Save ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnSaveYes" ajaxSingle="true" action="#{BranchMaster.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="taskList,a8,a11,a10,a12,a14,a17,a16,a18,lpg,message,errorMessage,gpFooter,txtIfsc,ddCompStatus,ddLocation" focus="txtBranchCode"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="modifyPanel" autosized="true" width="250" onshow="#{rich:element('btnModifyYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Modify?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnModifyYes" ajaxSingle="true" action="#{BranchMaster.modifyDetail}"
                                                       oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="taskList,a8,a11,a10,a12,a14,a17,a16,a18,lpg,message,errorMessage,gpFooter,txtIfsc,ddCompStatus,ddLocation" focus="txtBranchCode"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnModifyNo" onclick="#{rich:component('modifyPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
