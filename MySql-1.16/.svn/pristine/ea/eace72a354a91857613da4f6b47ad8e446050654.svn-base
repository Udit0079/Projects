<%-- 
    Document   : otherUnclaimedDetail
    Created on : Jan 7, 2015, 5:47:17 PM
    Author     : Athar Raza
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
            <title>Other Unclaimed Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{OtherUnclaimedDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Other Unclaimed Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{OtherUnclaimedDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="msg" styleClass="error" value="#{OtherUnclaimedDetail.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblNature" styleClass="label"  value="A/c Nature"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddNature" size="1" value="#{OtherUnclaimedDetail.acNature}" styleClass="ddlist" style="width:70px" disabled="#{bulkDeafMarking.fieldDisable}">
                            <f:selectItems value="#{OtherUnclaimedDetail.acNatureList}"/>
                            <a4j:support  action="#{OtherUnclaimedDetail.getGlCode}" event="onchange" reRender="ddAcType"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="label"  value="A/c Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddAcType" styleClass="ddlist" size="1" style="width:120px" value="#{OtherUnclaimedDetail.acType}" disabled="#{bulkDeafMarking.fieldDisable}">
                                <f:selectItems value="#{OtherUnclaimedDetail.acTypeList}"/>
                                <a4j:support  action="#{OtherUnclaimedDetail.getGlAccount}" event="onchange" reRender="stxtGlacnoAcNo"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="stxtGlacnoAcNo" styleClass="label" value="#{OtherUnclaimedDetail.glAcno}"style="color:green"></h:outputLabel>
                        </h:panelGroup>
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{OtherUnclaimedDetail.month}" disabled="#{bulkDeafMarking.fieldDisable}">
                                <f:selectItems value="#{OtherUnclaimedDetail.monthList}"/>
                            </h:selectOneListbox>
                            <h:inputText id="txtYear" styleClass="input" value="#{OtherUnclaimedDetail.year}" maxlength="4" size="5" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{OtherUnclaimedDetail.tableDataList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="7"><h:outputText value="Other UnClaimed Detail"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="F Year"/></rich:column>
                                        <rich:column><h:outputText value="GL Account No."/></rich:column>
                                        <rich:column><h:outputText value="Sequence No"/></rich:column>
                                        <rich:column><h:outputText value="Inst No"/></rich:column>
                                        <rich:column><h:outputText value="Amount"/></rich:column>
                                        <rich:column><h:outputText value="Status"/></rich:column>
                                        <rich:column><h:outputText value="Origin Date"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fYear}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.glAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.originDt}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnShow" value="Show" action="#{OtherUnclaimedDetail.gridLoad}" reRender="msg,maingrid,tablePanel,taskList"/>
                            <a4j:commandButton id="btnProcess" value="Mark DEAF" oncomplete="#{rich:component('processPanel')}.show()" 
                                               reRender="msg,processPanel,tablePanel,taskList" disabled="#{OtherUnclaimedDetail.disablePost}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{OtherUnclaimedDetail.resetForm}" reRender="msg,maingrid,tablePanel,taskList"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{OtherUnclaimedDetail.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
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
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{OtherUnclaimedDetail.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
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
