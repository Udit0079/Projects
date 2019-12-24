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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Bulk Deaf Marking</title>
        </head>
        <body>
            <a4j:form id="form">
                <h:panelGrid id="mainPanel" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="gridPanel" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{bulkDeafMarking.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Bulk Deaf Marking"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{bulkDeafMarking.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{bulkDeafMarking.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblNature" styleClass="label"  value="A/c Nature"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddNature" size="1" value="#{bulkDeafMarking.acNature}" styleClass="ddlist" style="width: 70px" disabled="#{bulkDeafMarking.fieldDisable}">
                                <f:selectItems value="#{bulkDeafMarking.acNatureList}"/>
                                <a4j:support  action="#{bulkDeafMarking.getAcTypeByAcNature}" event="onchange" reRender="ddAcType,panel2"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAcType" styleClass="label"  value="A/c Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAcType" styleClass="ddlist" size="1" style="width: 70px" value="#{bulkDeafMarking.acType}" disabled="#{bulkDeafMarking.fieldDisable}">
                                <f:selectItems value="#{bulkDeafMarking.acTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblMonth" styleClass="label"  value="Month"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{bulkDeafMarking.month}" disabled="#{bulkDeafMarking.fieldDisable}">
                                    <f:selectItems value="#{bulkDeafMarking.monthList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtYear" styleClass="input" value="#{bulkDeafMarking.year}" maxlength="4" size="5" disabled="#{bulkDeafMarking.fieldDisable}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%" style="display:#{bulkDeafMarking.roiVisible}">
                            <h:outputLabel id="roiLabel" value="Saving Roi" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="roiTxt" styleClass="input" value="#{bulkDeafMarking.savingRoi}" maxlength="5" size="5"/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                                <a4j:commandButton id="btnReport" action="#{bulkDeafMarking.retrieveDeafData}" value="Show" oncomplete="if(#{bulkDeafMarking.disableBtn==false}){
                                                   #{rich:component('popUpRepPanel')}.show();
                                                   }
                                                   else if(#{bulkDeafMarking.disableBtn==true})
                                                   {
                                                   #{rich:component('popUpRepPanel')}.hide();
                                                   }" reRender="errorMsg,ddNature,ddAcType,ddMonth,txtYear,btnSave,txnGrid,txnList,panel2,roiLabel,roiTxt,popUpRepPanel"/>
                                <a4j:commandButton id="btnSave" disabled="#{bulkDeafMarking.disableBtn}" value="Mark DEAF" oncomplete="#{rich:component('processPanel')}.show()" reRender="errorMsg,processPanel,confirmid"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{bulkDeafMarking.refreshForm}" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{bulkDeafMarking.exitForm}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                            <rich:dataTable  value="#{bulkDeafMarking.deafDataList}" var="item"
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="Deaf A/c Deatil"/></rich:column>
                                        <rich:column breakBefore="true" width="5"><h:outputText value="SNo."/></rich:column>
                                        <rich:column width="150"><h:outputText value="A/c No." /></rich:column>
                                        <rich:column width="400"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column width="100"><h:outputText value="Amount" /></rich:column>
                                        <rich:column width="100"><h:outputText value="Interest" /></rich:column>
                                        <rich:column width="100"><h:outputText value="Last Txn. Date" /></rich:column>
                                        <rich:column width="100"><h:outputText value="Voucher No" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                                <rich:column><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column>
                                    <h:outputText value="#{item.amount}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{item.interest}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{item.lastTrnDt}"/></rich:column>
                                <rich:column><h:outputText value="#{item.receiptNo}"/></rich:column>                            
                            </rich:dataTable>
                            <rich:datascroller id="deafDataScroll"align="left" for="txnList" maxPages="20"/>
                        </h:panelGrid>
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                        <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" 
                                         style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <f:facet name="header">
                                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                            </f:facet>
                        </rich:modalPanel>
                    </h:panelGrid>
                </a4j:form>
                <a4j:region id="processActionRegion">
                    <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to mark the deaf ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{bulkDeafMarking.processAction}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="errorMsg,mainPanel,txnGrid,txnList"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>

            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" 
                             moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Deaf A/c Marking" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{bulkDeafMarking.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
