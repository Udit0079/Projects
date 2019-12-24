<%-- 
    Document   : backDateEntry
    Created on : 21 May, 2018, 2:56:00 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Back Date Entry</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".valueDt").mask("99/99/9999");
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BackDateEntry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Back Date Entry"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BackDateEntry.userName}"/>
                        </h:panelGroup>                         
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                        <h:outputText id="message" styleClass="msg" value="#{BackDateEntry.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel0"   style="height:30px;txt-align:leftext-align:left" styleClass="row2" width="100%" >
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" style="width: 120px" styleClass="ddlist" value="#{BackDateEntry.function}" size="1">
                                <f:selectItems value="#{BackDateEntry.funcList}" />
                                <a4j:support event="onblur" action="#{BackDateEntry.funcTypeAction}" oncomplete="setMask();" reRender="lblbatchNo,txtbatchNo,btnLabel,gridpanel6,
                                             gridpanel4,tblRegion,tblverifyRegion,TaskList,message,gridpanel1,gridpanel2,gridpanel3,gridpanel5,lblmode,ddMode,gridpanel8" focus="#{BackDateEntry.focusId}"/>
                            </h:selectOneListbox> 
                            <h:panelGroup>
                                <h:outputLabel id="lblmode" styleClass="label" value="Mode" style = "display:#{BackDateEntry.modedisplay}" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:outputLabel id="lblbatchNo" styleClass="label" value="Batch No." style = "display:#{BackDateEntry.batchdisplay}" ><font class="required" color="red">*</font></h:outputLabel>
                            </h:panelGroup>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddMode" style="width: 120px;display:#{BackDateEntry.modedisplay}" styleClass="ddlist" value="#{BackDateEntry.mode}" size="1">
                                    <f:selectItems value="#{BackDateEntry.modeList}" />
                                    <a4j:support event="onblur" action="#{BackDateEntry.modeAction}" reRender="message,ddFunction,txtbatchNo,txtAcno,
                                                 stxtNewAccount,stxtGlName,ddtxnType,txtAmount,valueDt,stxtTotalcr,stxtTotaldr,verTaskList,
                                                 gridpanel4,tblRegion,gridpanel6,tblverifyRegion,TaskList,stxtBalance,txtRemarks" 
                                                 focus="#{BackDateEntry.focusId}" oncomplete="setMask();"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtbatchNo" styleClass="input" value="#{BackDateEntry.batch}" size="15" style = "display:#{BackDateEntry.batchdisplay}">
                                    <a4j:support event="onblur" action="#{BackDateEntry.batchAction}" reRender="message,gridpanel6,tblverifyRegion,TaskList" oncomplete="setMask();"/>
                                </h:inputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel1"   style="height:30px; display:#{BackDateEntry.panel1display}; txt-align:leftext-align:left" styleClass="row1" width="100%" >
                            <h:outputLabel id="valueDtLbl" styleClass="label" value="Transaction Date" />
                            <h:inputText id="valueDt" styleClass="input valueDt" value="#{BackDateEntry.valueDate}" disabled="#{BackDateEntry.disabledate}"  size="10"/>
                            <h:outputLabel id="lblacno" styleClass="label" value="Account No." ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:inputText id="txtAcno" styleClass="input" value="#{BackDateEntry.acno}" maxlength="#{BackDateEntry.acNoMaxLen}" size="15">
                                    <a4j:support event="onblur" action="#{BackDateEntry.accountAction}" reRender="stxtNewAccount,message,stxtBalance,stxtGlName,valueDt" 
                                                 oncomplete="setMask();" focus="#{BackDateEntry.focusId}"/>
                                </h:inputText>
                                <h:outputText id="stxtNewAccount" styleClass="output" value="#{BackDateEntry.newAccountNo}"/>
                            </h:panelGroup>
                         </h:panelGrid>
                        <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel2"   style="height:30px;display:#{BackDateEntry.panel2display}; txt-align:leftext-align:left" styleClass="row2" width="100%" >
                            <h:outputLabel id="lblglName" styleClass="label" value="GL Head Name.:" ></h:outputLabel>
                            <h:outputText id="stxtGlName" styleClass="output" value="#{BackDateEntry.glHeadname}"/>
                            <h:outputLabel id="lblBalance" styleClass="label" value="GL Head Balance:" ></h:outputLabel>
                            <h:outputText id="stxtBalance" styleClass="output" value="#{BackDateEntry.balance}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel3"   style="height:30px;display:#{BackDateEntry.panel3display}; txt-align:leftext-align:left" styleClass="row1" width="100%" >
                             <h:outputLabel id="lbltxn" styleClass="label" value="Transaction type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddtxnType" style="width: 120px" styleClass="ddlist" value="#{BackDateEntry.txnType}" size="1">
                                <f:selectItems value="#{BackDateEntry.txnTypeList}" />
                                <a4j:support event="onblur" action="#{BackDateEntry.txnTypeAction}" reRender="message,valueDt" oncomplete="setMask();" focus="#{BackDateEntry.focusId}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAmount" styleClass="label" value="Amount"/>
                            <h:inputText id="txtAmount" styleClass="input" style="width:110px" value="#{BackDateEntry.amount}" maxlength ="14">
                                <f:convertNumber type="currency" pattern="#.00" maxFractionDigits="2"/>
                            </h:inputText> 
                           <h:outputText>
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel8"   style="height:30px;display:#{BackDateEntry.panel8display}; txt-align:leftext-align:left" styleClass="row2" width="100%" >
                            <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks">
                            <font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:inputText id="txtRemarks" styleClass="input" style="width:250px;" value="#{BackDateEntry.remarks}" 
                                         maxlength="200" onkeydown="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" action ="#{BackDateEntry.gridAction}" reRender = "message,gridpanel4,tblRegion,verTaskList,selectlink,
                                             ddFunction,txtbatchNo,txtAcno,stxtNewAccount,stxtGlName,ddtxnType,txtAmount,valueDt,stxtTotalcr,stxtTotaldr,txtRemarks,stxtBalance" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText>
                            </h:outputText>
                            <h:outputText>
                            </h:outputText>
                        </h:panelGrid>     
                        <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel5"   style="height:30px;display:#{BackDateEntry.panel4display} ; txt-align:leftext-align:left" styleClass="row1" width="100%" >
                            <h:outputLabel id="lbltlcr" styleClass="label" value="Total Credit:" ></h:outputLabel>
                            <h:outputText id="stxtTotalcr" styleClass="output" value="#{BackDateEntry.totalcredit}"/>
                            <h:outputLabel id="lbltldr" styleClass="label" value="Total debit:" ></h:outputLabel>
                            <h:outputText id="stxtTotaldr" styleClass="output" value="#{BackDateEntry.totaldebit}"/>
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnLabel" value="#{BackDateEntry.btnLbl}" onclick="#{rich:component('processPanel')}.show()" oncomplete="setMask();" disabled="#{BackDateEntry.disableBtn}"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BackDateEntry.refresh}" reRender="mainPanel" oncomplete="setMask();"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{BackDateEntry.exitForm}" reRender="mainPanel"/>                            
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="gridpanel4" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8; display:#{BackDateEntry.gridTableDisplay}" styleClass="row1" width="100%">
                            <a4j:region id="tblRegion">
                                <rich:dataTable value="#{BackDateEntry.gridTable}" var="item"
                                                rowClasses="gridrow1,gridrow2" id="verTaskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">  
                                        <rich:columnGroup>
                                            <rich:column colspan="6"><h:outputText value="Back Date Entry Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                            <rich:column><h:outputText value="Gl Name" /></rich:column>
                                            <rich:column><h:outputText value="Txn Type" /></rich:column>
                                            <rich:column><h:outputText value="Amount" /></rich:column>
                                            <rich:column><h:outputText value="Date" /></rich:column>
                                            <rich:column><h:outputText value="Delete" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.glname}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.txnType}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.amount}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.date}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{BackDateEntry.deleteDetailsAction}"
                                                         reRender="message,stxtTotalcr,stxtTotaldr,verTaskList">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{item}" target="#{BackDateEntry.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="verTaskList" maxPages="20"/>
                            </a4j:region>       
                        </h:panelGrid>
                        <h:panelGrid id="gridpanel6" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8; display:#{BackDateEntry.verifyTableDisplay}" styleClass="row1" width="100%">
                            <a4j:region id="tblverifyRegion">
                                <rich:dataTable value="#{BackDateEntry.verifyTable}" var="item"
                                                rowClasses="gridrow1,gridrow2" id="TaskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="8"><h:outputText value="Back Date Entry Authorization Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                            <rich:column><h:outputText value="Gl Name" /></rich:column>
                                            <rich:column><h:outputText value="Debit amount" /></rich:column>
                                            <rich:column><h:outputText value="Credit amount" /></rich:column>
                                            <rich:column><h:outputText value="Date" /></rich:column>
                                            <rich:column><h:outputText value="Batch no." /></rich:column>
                                            <rich:column><h:outputText value="Rec no." /></rich:column>
                                            <rich:column><h:outputText value="Detail" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.glname}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.dramount}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.cramount}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.date}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.batchno}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.recno}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.detail}" /></rich:column>
                                </rich:dataTable> 
                                <rich:datascroller align="left" for="TaskList" maxPages="20"/>
                            </a4j:region>       
                        </h:panelGrid>
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                        <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
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
                                    <h:outputText id="confirmid" value="Are you sure to save it ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{BackDateEntry.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="message,gridpanel4,verTaskList,ddFunction,txtbatchNo,txtAcno,stxtNewAccount,stxtGlName,ddtxnType,txtAmount,valueDt,stxtTotalcr,stxtTotaldr,TaskList,stxtBalance"/>
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

