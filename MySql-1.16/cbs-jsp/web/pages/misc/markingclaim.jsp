<%-- 
    Document   : markingclaim
    Created on : 6 Nov, 2014, 3:35:00 PM
    Author     : dinesh Pratap Singh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Marking Claim</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{makingClaim.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Marking Claim"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{makingClaim.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="msg" styleClass="error" value="#{makingClaim.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="fieldgrid" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAcno" styleClass="label" value="A/c Number"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtAcno" value="#{makingClaim.acno}" maxlength="12" size="14" styleClass="input">
                            <a4j:support action="#{makingClaim.processAcNo}" event="onblur" reRender="lblMonth,txtYear,lblseqNo,txtseqNo,msg,tablePanel,taskList,roiGrid,roiLabel,roiTxt"/>
                        </h:inputText>
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Year" style="display:#{makingClaim.hide}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtYear" styleClass="input" value="#{makingClaim.year}" maxlength="4" size="5" style="display:#{makingClaim.hide}"/>
                        <h:outputLabel id="lblseqNo" styleClass="label"  value="Sequence No" style="display:#{makingClaim.hide}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtseqNo" styleClass="input" value="#{makingClaim.seqNo}" maxlength="6" size="5" style="display:#{makingClaim.hide}">
                            <a4j:support action="#{makingClaim.processAcNo}" event="onblur" reRender="msg,tablePanel,taskList,roiGrid,roiLabel,roiTxt"/> 
                        </h:inputText>
                        <h:outputLabel id="lblStatus" styleClass="label" value="Customer Name"/>
                        <h:outputText id="stxtStatus" styleClass="output" value="#{makingClaim.custname}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid id="roiGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%" style="display:#{makingClaim.roiVisible}">
                        <h:outputLabel id="roiLabel" value="Saving Roi" styleClass="label" style="display:#{makingClaim.roiVisible}"/>
                        <h:inputText id="roiTxt" styleClass="input" value="#{makingClaim.savingRoi}" 
                                     style="display:#{makingClaim.roiVisible}" maxlength="5" size="5"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{makingClaim.tableDataList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="UnClaimed Detail"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="A/c Number"/></rich:column>
                                        <rich:column><h:outputText value="Customer Name"/></rich:column>
                                        <rich:column><h:outputText value="Amount"/></rich:column>
                                        <rich:column><h:outputText value="Deaf Date"/></rich:column>
                                        <rich:column><h:outputText value="Voucher No"/></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custname}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.deafDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{makingClaim.selectData}" reRender="msg,stxtStatus,btnProcess">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{makingClaim.currentItem}"/>                                    
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Post" oncomplete="#{rich:component('processPanel')}.show()" 
                                               reRender="msg,processPanel" disabled="#{makingClaim.disablePost}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{makingClaim.resetForm}" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{makingClaim.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
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
                                    <h:outputText id="confirmid" value="Are you sure to post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{makingClaim.processAction}" 
                                                       onclick="#{rich:component('processPanel')}.hide();" 
                                                       reRender="maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
