<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Social Security Scheme File Generation</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{SssFileGenerator.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Social Security Scheme File Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SssFileGenerator.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{SssFileGenerator.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" style="width: 150px" styleClass="ddlist"  value="#{SssFileGenerator.function}" size="1">
                            <f:selectItems value="#{SssFileGenerator.functionList}"/>
                            <a4j:support action="#{SssFileGenerator.onChangeFunction}" event="onblur" reRender="stxtMessage,btnHtml" oncomplete="setMask();" focus="ddScheme"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblScheme" styleClass="label" value="Social Security Scheme :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddScheme" style="width: 180px" styleClass="ddlist"  value="#{SssFileGenerator.schemeCode}" size="1">
                            <f:selectItems value="#{SssFileGenerator.schemeList}"/>
                            <a4j:support action="#{SssFileGenerator.onChangeScheme}" event="onblur" reRender="ddSp" oncomplete="setMask();" focus="ddSp"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSP" styleClass="label" value="Scheme Provider :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSp" style="width: 150px" styleClass="ddlist"  value="#{SssFileGenerator.vendor}" size="1">
                            <f:selectItems value="#{SssFileGenerator.vendorList}"/>
                            <a4j:support action="#{SssFileGenerator.onChangeVendor}" event="onblur" reRender="dateGrid1,dateGrid2,controlfileGrid" oncomplete="setMask();" focus="txtGenDate"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid1" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblGenDate" styleClass="label" value="File Generation Date : "><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtGenDate" styleClass="input issueDt" value="#{SssFileGenerator.fileGenerationDt}" size="10">
                            <a4j:support event="onblur" action="#{SssFileGenerator.onChangeFileGenDt}" reRender="dateGrid1,dateGrid2,controlfileGrid,stxtMessage" oncomplete="if(#{SssFileGenerator.function == 'CTP' || SssFileGenerator.function == 'SSF' || SssFileGenerator.function == 'SRF' || (SssFileGenerator.function == 'GSF' && SssFileGenerator.schemeCode == 'APY')}){#{rich:element('btnHtml')}.focus();}else if(#{SssFileGenerator.function =='GSF' &&(SssFileGenerator.schemeCode == 'PMJJBY' || SssFileGenerator.schemeCode == 'PMSBY')}){#{rich:element('txtTotPremium')}.focus();}else if(#{SssFileGenerator.function =='RSF'}){#{rich:element('ddctrlFile')}.focus();};setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblTotPremium" styleClass="label" value="Total Premium :"/>
                        <h:inputText id="txtTotPremium" styleClass="input" value="#{SssFileGenerator.premiumAmt}" size="15" disabled="#{SssFileGenerator.disabled}"/>
                        <h:outputLabel id="lblTotRecord" styleClass="label" value="Total Record :"/>
                        <h:outputText id="txtTotRecord" styleClass="output" value="#{SssFileGenerator.totalRecord}"/>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid2" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;display:#{SssFileGenerator.display}" styleClass="row2" width="100%">
                        <h:outputLabel id="lblUtrNo" styleClass="label" value="UTR / Ref No. : "/>
                        <h:inputText id="txtUtrNo" styleClass="input" value="#{SssFileGenerator.utrNo}"/>
                        <h:outputLabel id="lblutrDate" styleClass="label" value="UTR Date : "/>
                        <h:inputText id="txtutrDate" styleClass="input issueDt" value="#{SssFileGenerator.utrDate}" size="10"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>

                    <h:panelGrid id="controlfileGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;display:#{SssFileGenerator.controlFileDisplay}" styleClass="row2" width="100%">
                        <h:outputLabel id="lblctrlFile" styleClass="label" value="Control File Name :"/>
                        <h:selectOneListbox id="ddctrlFile" style="width: 160px" styleClass="ddlist"  value="#{SssFileGenerator.ctrlFile}" size="1">
                            <f:selectItems value="#{SssFileGenerator.ctrlFileList}"/>
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="#{SssFileGenerator.btnValue}" action="#{SssFileGenerator.createConfirmTxt}" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="stxtMessage,processPanel,confirmid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SssFileGenerator.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SssFileGenerator.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{SssFileGenerator.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"><h:outputText value="Generated File Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="File No" /></rich:column>
                                        <rich:column><h:outputText value="File Gen. Date" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="File Gen. By" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fileNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenBy}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{SssFileGenerator.downloadFile}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SssFileGenerator.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="#{SssFileGenerator.confirmText}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{SssFileGenerator.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddFunction')}.focus();setMask();" 
                                                       reRender="stxtMessage,mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();" oncomplete="setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
