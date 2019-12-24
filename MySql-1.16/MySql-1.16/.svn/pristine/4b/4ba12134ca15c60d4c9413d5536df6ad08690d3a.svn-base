<%-- 
    Document   : ctsDetail
    Created on : 18 Dec, 2017, 11:10:18 AM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>CTS Archiving Detail</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{CtsArchivingDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="CTS Archiving Details"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CtsArchivingDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{CtsArchivingDetail.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid" columnClasses="col17,col1,col17,col1,col17,col1,col17,col1" columns="8" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFileType" value="Type" styleClass="label"/>
                        <h:selectOneListbox id="ddFileType" value="#{CtsArchivingDetail.fileType}" styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems value="#{CtsArchivingDetail.fileTypeList}" />
                            <a4j:support event="onblur" actionListener="#{CtsArchivingDetail.onblurFileType()}" 
                                         oncomplete="setMask();" reRender="mainPanel,tablePanel,imgPanel,chqViewPanel,chqImage,frDate,txtChequeNo" focus="#{CtsArchivingDetail.focusField}"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:outputLabel id="lblFrDt" styleClass="label" value="From Date" style="display:#{CtsArchivingDetail.firstdate}"/>
                            <h:outputLabel id="labelCheque" styleClass="label" value="Cheque No." style="display:#{CtsArchivingDetail.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:inputText id="frDate" size="10" styleClass="input issueDt"  value="#{CtsArchivingDetail.frdt}" style="display:#{CtsArchivingDetail.firstdate}" >
                                <a4j:support event="onblur" focus="toDate" oncomplete="setMask();"/>
                            </h:inputText>
                            <a4j:region id="dataChqRegion">
                                <h:inputText id="txtChequeNo" styleClass="input" value="#{CtsArchivingDetail.chequeNo}" maxlength="8"  size="15"style="display:#{CtsArchivingDetail.displayoncheque}" >
                                    <a4j:support event="onblur" action="#{CtsArchivingDetail.gridload}" reRender="stxtMessage,tablePanel,taskList"/>
                                </h:inputText>
                            </a4j:region>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel id="lblToDt" styleClass="label" value="To Date" style="display:#{CtsArchivingDetail.secondDate}"/>
                            <h:outputLabel  style="display:#{CtsArchivingDetail.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <a4j:region id="dataRegion">
                                <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{CtsArchivingDetail.todt}"style="display:#{CtsArchivingDetail.secondDate}" >
                                    <a4j:support event="onblur" reRender="stxtMessage,tablePanel,taskList" action="#{CtsArchivingDetail.gridload}"   oncomplete="setMask();"/>
                                </h:inputText>
                            </a4j:region>
                            <h:outputLabel  style="display:#{CtsArchivingDetail.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel  style="display:#{CtsArchivingDetail.secondDate}"/>
                            <h:outputLabel  style="display:#{CtsArchivingDetail.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel  style="display:#{CtsArchivingDetail.secondDate}"/>
                            <h:outputLabel  style="display:#{CtsArchivingDetail.displayoncheque}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <h:panelGrid id="footerPanel"  columnClasses="col19,col10,col19" columns="3" style="width:100%;text-align:center;" styleClass="footer" >
                    <h:panelGroup id="btnPanel1" styleClass="vtop">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="F8-White Image,F9-Back Image, F10-Gray Image" style="color:blue;text-align:left;"/>
                    </h:panelGroup>
                    <h:panelGroup id="btnPanel" style="width:100%;text-align:center;" styleClass="vtop">
                        <a4j:commandButton id="btnHtml" value="Generate Image" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="stxtMessage,processPanel"/>
                        <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CtsArchivingDetail.btnRefreshAction}" reRender="stxtMessage,mainPanel,tablePanel,taskList,imgPanel" oncomplete="setMask();"/>
                        <a4j:commandButton id="btnExit" value="Exit" action="#{CtsArchivingDetail.btnExitAction}" reRender="stxtMessage,mainPanel,tablePanel,taskList"/>
                    </h:panelGroup>
                    <h:panelGroup/>
                </h:panelGrid>
                <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                    <a4j:region id="tblRegion">
                        <rich:dataTable value="#{CtsArchivingDetail.gridDetail}" var="dataItem"
                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onclick="this.style.backgroundColor='#428bca'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                        style="display:#{CtsArchivingDetail.displayOnGenerate}"  >

                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="9"><h:outputText value="Cts Archiving Detail" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Sr No." /></rich:column>
                                    <rich:column><h:outputText value="Item Seq No" /></rich:column>
                                    <rich:column><h:outputText value="AC No." /></rich:column>
                                    <rich:column><h:outputText value="Cheque No" /></rich:column>
                                    <rich:column><h:outputText value="Cheque Date" /></rich:column>
                                    <rich:column><h:outputText value="Amount" /></rich:column>
                                    <rich:column><h:outputText value="In Favour Of" /></rich:column>
                                    <rich:column><h:outputText value="PrBank Code" /></rich:column>
                                    <rich:column><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.srNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.itemSeqNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.chqNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.instDt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.instAmt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.inFavourOf}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.prBankCode}" /></rich:column>
                            <rich:column>
                                <a4j:commandLink ajaxSingle="true" id="selectLink" action="#{CtsArchivingDetail.setRowValues}" reRender="stxtMessage,chqImage" oncomplete="setMask();">
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" width="15" height="15"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{CtsArchivingDetail.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                    </a4j:region>
                </h:panelGrid>
                <a4j:jsFunction name="showWhiteImage" action="#{CtsArchivingDetail.getWhiteImage}" reRender="imgPanel,chqViewPanel,chqImage,stxtMessage" oncomplete="setMask();"  />
                <a4j:jsFunction name="showGrayImage" action="#{CtsArchivingDetail.getGrayImage}" reRender="imgPanel,chqViewPanel,chqImage,stxtMessage" oncomplete="setMask();" />
                <a4j:jsFunction name="showBackImage" action="#{CtsArchivingDetail.getBackImage}" reRender="imgPanel,chqViewPanel,chqImage,stxtMessage" oncomplete="setMask();"/>
                <rich:hotKey key="F8" handler="showWhiteImage();"/>
                <rich:hotKey key="F9" handler="showBackImage();"/>
                <rich:hotKey key="F10" handler="showGrayImage();"/>
                <h:panelGrid columns="2" id="imgPanel" style="width:100%;height:265px;display:#{CtsArchivingDetail.displayimg};background-color:#e8eef7;">
                    <h:panelGrid columns="1" id="chqViewPanel" style="text-align:left;background-color:#e8eef7;dispaly:#{CtsArchivingDetail.displayimg} " columnClasses="col8">
                        <h:graphicImage id="chqImage" value="/cts-image/dynamic/?file=#{CtsArchivingDetail.imageData}&orgn=#{CtsArchivingDetail.folderName}" height="300" width="650"  />
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('docTypeAlertWait')}.show()" onstop="#{rich:component('docTypeAlertWait')}.hide()" for="dataRegion"/>
                <rich:modalPanel id="docTypeAlertWait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <a4j:status onstart="#{rich:component('chqAlertWait')}.show()" onstop="#{rich:component('chqAlertWait')}.hide()" for="dataChqRegion"/>
                <rich:modalPanel id="chqAlertWait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>

                <a4j:region id="btnActionGrid">
                    <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText id="confirmid" value="Are you sure to generate the image ?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYes" action="#{CtsArchivingDetail.processAction}" 
                                                               oncomplete="#{rich:component('processPanel')}.hide();setMask();" 
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
                </a4j:region>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnActionGrid"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
