<%-- 
    Document   : CECS Credit Inward
    Created on : 23 JAN, 2015, 10:00:00 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>ECS Inward Processing</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issuedt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ecsInwController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="ECS Credit Verification"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ecsInwController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{ecsInwController.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col52" 
                                 style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:80px" value="#{ecsInwController.branch}">
                            <f:selectItems value="#{ecsInwController.branchList}"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:outputLabel style="display:#{ecsInwController.outputVisible};"/>
                            <h:outputLabel id="lblType" styleClass="label" style="display:#{ecsInwController.typeVisible};" value="Type"><font class="required" color="red">*</font></h:outputLabel>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel style="display:#{ecsInwController.outputVisible};"/>
                            <h:selectOneListbox id="ddType" styleClass="ddlist" size="1" style="width:80px;display:#{ecsInwController.typeVisible};" value="#{ecsInwController.type}">
                                <f:selectItems value="#{ecsInwController.typeList}"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblFileUpDt" styleClass="label" value="File Settlement Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFileUpDt" styleClass="input issuedt" style="width:70px" value="#{ecsInwController.fileUpDt}">
                            <a4j:support event="onblur" action="#{ecsInwController.fileUpDtProcess}" reRender="stxtMsg,ddSeqNo" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSeqNo" styleClass="ddlist" size="1" style="width:80px" value="#{ecsInwController.seqNo}">
                            <f:selectItems value="#{ecsInwController.seqNoList}"/>
                            <a4j:support event="onblur" action="#{ecsInwController.retrieveProcess}" 
                                         reRender="stxtMsg,stxtDestAcno,stxtAcType,stxtBenName,stxtAmount,
                                         stxtMicr,stxtUserNarration,stxtUniqueRefNo,stxtStatus,stxtReason,
                                         tablePanel,taskList" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblDestAcno" styleClass="label" value="Destination A/c No."></h:outputLabel>
                        <h:outputText id="stxtDestAcno" styleClass="output" value="#{ecsInwController.stxtDestAcno}"/>
                        <h:outputLabel id="lblAcType" styleClass="label" value="A/c Type"></h:outputLabel>
                        <h:outputText id="stxtAcType" styleClass="output" value="#{ecsInwController.stxtAcType}"/>
                        <h:outputLabel id="lblBenName" styleClass="label" value="Beneficiary Name"></h:outputLabel>
                        <h:outputText id="stxtBenName" styleClass="output" value="#{ecsInwController.stxtBenName}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAmount" styleClass="label" value="Amount"></h:outputLabel>
                        <h:outputText id="stxtAmount" styleClass="output" value="#{ecsInwController.stxtAmount}"/>
                        <h:outputLabel id="lblMicr" styleClass="label" value="IFSC/MICR/IIN"></h:outputLabel>
                        <h:outputText id="stxtMicr" styleClass="output" value="#{ecsInwController.stxtMicr}"/>
                        <h:outputLabel id="lblUserNarration" styleClass="label" value="User Narration"></h:outputLabel>
                        <h:outputText id="stxtUserNarration" styleClass="output" value="#{ecsInwController.stxtUserNarration}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblUniqueRefNo" styleClass="label" value="Unique Ref.No"></h:outputLabel>
                        <h:outputText id="stxtUniqueRefNo" styleClass="output" value="#{ecsInwController.stxtUniqueRefNo}"/>
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"></h:outputLabel>
                        <h:outputText id="stxtStatus" styleClass="output" value="#{ecsInwController.stxtStatus}"/>
                        <h:outputLabel id="lblReason" styleClass="label" value="Reason"></h:outputLabel>
                        <h:outputText id="stxtReason" styleClass="output" value="#{ecsInwController.stxtReason}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel7" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAcno" styleClass="label" value="A/c No"></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                            <h:inputText id="txtAcno" styleClass="input" value="#{ecsInwController.accountNo}" maxlength="#{ecsInwController.acNoMaxLen}" size="#{ecsInwController.acNoMaxLen}">
                                <a4j:support event="onblur" action="#{ecsInwController.retrieveValidAcno}" 
                                             reRender="stxtAccNo,stxtMsg,stxtName,stxtBalance" oncomplete="setMask();" focus="btnSave"/>
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{ecsInwController.acno}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblBalance" styleClass="label" value="A/c Balance"></h:outputLabel>
                        <h:outputText id="stxtBalance" styleClass="output" value="#{ecsInwController.balance}"/>
                        <h:outputLabel id="lblName" styleClass="label" value="Name"></h:outputLabel>
                        <h:outputText id="stxtName" styleClass="output" value="#{ecsInwController.name}"/>
                    </h:panelGrid>


                    <h:panelGrid id="gridPanel8" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;display:#{ecsInwController.verificationstatusdisplay}" styleClass="row1" width="100%">
                        <h:outputLabel id="lblVerfStatus" styleClass="label" value="Status"></h:outputLabel>
                        <h:selectOneListbox id="ddVerfStatus" styleClass="ddlist" size="1" style="width:80px" value="#{ecsInwController.verificationstatus}"
                                            disabled="#{ecsInwController.verificationstatusdisable}">
                            <f:selectItems value="#{ecsInwController.verificationstatusList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{ecsInwController.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="14"><h:outputText value="ECS Credit Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                        <rich:column><h:outputText value="Credit Ref.No" /></rich:column>
                                        <rich:column><h:outputText value="Dest.A/c No" /></rich:column>
                                        <rich:column><h:outputText value="Dest.A/c Type" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary Name"/></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Dest.IIN" /></rich:column>
                                        <rich:column><h:outputText value="User Name Narration" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Reason" /></rich:column>
                                        <rich:column><h:outputText value="CBS A/c No" /></rich:column>
                                        <rich:column><h:outputText value="CBS A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="Verify By" /></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tranRef}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.oldAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.oldAcName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.micr}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.userName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.reason}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cbsAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cbsName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.authBy}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ecsInwController.setTableDataToForm}" 
                                                     reRender="stxtMsg,stxtDestAcno,stxtAcType,stxtBenName,stxtAmount,
                                                     stxtMicr,stxtUserNarration,stxtUniqueRefNo,stxtStatus,stxtReason,
                                                     txtAcno,stxtBalance,stxtName,gridPanel8,gridPanel7" focus="txtAcno">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ecsInwController.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" columnClasses="col19,col20,col21" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="footerPanel1" layout="block" style="width:100%;text-align:center;">
                            <h:outputText id="footerText" styleClass="output" value="Ctrl+J - Joint Detail" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="Verify" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ecsInwController.refreshForm}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ecsInwController.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                        <h:panelGroup/>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:jsFunction name="jtDetails" action="#{ecsInwController.validateJointAccount}" 
                                oncomplete="if(#{ecsInwController.jtDetailPopUp==true})
                                {#{rich:component('jtHolder')}.show();}else{#{rich:component('jtHolder')}.hide();}" 
                                reRender="jtHolder,stxtMsg" />
                <a4j:jsFunction name="populateData" action="#{ecsInwController.jtDetails}" reRender="jointDetailPopUpForm"/>
                <rich:hotKey key="Ctrl+J" handler="jtDetails();"/>
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
                                        <h:outputText id="confirmid" value="Are you sure to verify it ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{ecsInwController.processAction}" 
                                                           onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="mainPanel" oncomplete="setMask();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="jtHolder" label="Form" width="900" height="400" style="overflow:auto;" resizeable="false" onbeforeshow="populateData();">
                    <f:facet name="header">
                        <h:outputText value="Joint Detail" style="text-align:center;"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:outputLink value="#" onclick="#{rich:component('jtHolder')}.hide(); return false;">X</h:outputLink>
                    </f:facet>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr>
                                <td>
                                    <a4j:include viewId="#{ecsInwController.viewID}"></a4j:include>
                                    </td>

                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                    <a4j:commandButton value="Close" id="btnClose" onclick="#{rich:component('jtHolder')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
