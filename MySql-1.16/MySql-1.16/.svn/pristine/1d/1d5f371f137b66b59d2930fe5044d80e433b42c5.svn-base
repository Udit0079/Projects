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
            <title>ACH/ECS Input Generation</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{npciInputController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="ACH/ECS Input Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{npciInputController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{npciInputController.errorMessage}"/>
                    </h:panelGrid>
                    <rich:panel id="panal1">
                        <h:panelGrid id="functionGrid1" columnClasses="col15,col15,col15,col15,col15,col15,col15,col52" columns="8" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" style="width: 140px" styleClass="ddlist"  value="#{npciInputController.function}" 
                                                size="1">
                                <f:selectItems value="#{npciInputController.functionList}"/>
                                <a4j:support event="onblur" action="#{npciInputController.functionAction}" 
                                             reRender="stxtMessage,btnHtml,confirmid,lblGenDate,txtGenDate,tablePanel,taskList,functionGrid,tablePanelDownload,mainPanel"
                                             oncomplete="setMask();" focus="ddType" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblType" styleClass="label" value="File Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddType" styleClass="ddlist"  value="#{npciInputController.fileType}" 
                                                size="1" style="width: 140px;">
                                <f:selectItems value="#{npciInputController.fileTypeList}"/>
                                <a4j:support event="onblur" actionListener="#{npciInputController.onblurFileType()}" 
                                             oncomplete="setMask();" focus="ddTranType" reRender="mainPanel,tablePanel,tablePanelDownload,tablePanelECS"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblTranType" styleClass="label" value="Transaction Type" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddTranType" styleClass="ddlist"  value="#{npciInputController.tranType}" 
                                                style="width: 140px;" size="1">
                                <f:selectItems value="#{npciInputController.tranTypeList}"/>
                                <a4j:support event="onblur" oncomplete="setMask();if(#{npciInputController.function=='S'}){#{rich:element('txtFromDate')}.focus();}else{
                                             if(#{npciInputController.fileType=='ECS'}){#{rich:element('ddSeqType')}.focus();}else{#{rich:element('ddTxnType')}.focus();}}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblTxnType" styleClass="label" value="Txn Type" style="display:#{npciInputController.txnTypeFlag}"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddTxnType" styleClass="ddlist"  value="#{npciInputController.txnType}" 
                                                style="width:140px;display:#{npciInputController.txnTypeFlag}" size="1">
                                <f:selectItems value="#{npciInputController.txnTypeList}"/>
                                <a4j:support event="onblur" focus="ddSeqType"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="functionGrid2" columnClasses="col15,col15,col15,col15,col15,col15,col15,col52" columns="8" style="height:30px;display:#{npciInputController.displayOngenerateACH}" styleClass="row1" width="100%">
                            <h:outputLabel id="lblSeqType" styleClass="label" value="Sequence Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddSeqType" style="width: 140px" styleClass="ddlist"  value="#{npciInputController.seqType}" size="1">
                                <f:selectItems value="#{npciInputController.seqTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblSettlementDt" styleClass="label" value="Settlement Date"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtSettlementDt" styleClass="input issueDt" value="#{npciInputController.settlementDt}" size="10"/>
                            <h:outputLabel id="lblFreqType" styleClass="label" value="Frequency Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFreqType" style="width: 140px" styleClass="ddlist"  value="#{npciInputController.freqnType}" size="1">
                                <f:selectItems value="#{npciInputController.freqnTypeList}"/>
                                <a4j:support event="onblur" oncomplete="setMask();" focus="txtGenDate"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid id="functionGrid3" columnClasses="col15,col15,col15,col15,col15,col15,col15,col52" columns="8" style="height:30px;display:#{npciInputController.displayOngenerate}" styleClass="row2" width="100%">
                            <h:outputLabel id="lblForgetFlag" styleClass="label" value="Forget Flag"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddForgetFlag" style="width: 140px" styleClass="ddlist"  value="#{npciInputController.forgateFlag}" size="1">
                                <f:selectItems value="#{npciInputController.forgetFlagList}"/>
                                <a4j:support actionListener="#{npciInputController.onBlurForgrtFlag()}" event="onblur"  oncomplete="setMask();" reRender="mainPanel,tablePanel" focus="txtForgetDay"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblForgetDay" styleClass="label" value="No of Days" style="display:#{npciInputController.displayOngenerateACH}"/>
                            <h:inputText id="txtForgetDay" styleClass="input" value="#{npciInputController.forgetDay}" size="2" 
                                         style="display:#{npciInputController.displayOngenerateACH}" disabled="#{npciInputController.disableForgrtDay}">
                                <a4j:support actionListener="#{npciInputController.getUmrnDataForGrid}" event="onblur" reRender="mainPanel,tablePanel" oncomplete="setMask();"/> 
                            </h:inputText>
                            <h:outputLabel id="lblGenDate" styleClass="label" value="Date" style="display:#{npciInputController.dateFlag};"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtGenDate" styleClass="input issueDt" value="#{npciInputController.dt}" size="10" style="display:#{npciInputController.dateFlag};">
                                <a4j:support actionListener="#{npciInputController.getUmrnDataForGrid}" event="onblur" reRender="mainPanel,tablePanel" oncomplete="setMask();"/> 
                            </h:inputText>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid id="functionGrid4" columnClasses="col15,col15,col15,col15,col15,col15,col15,col52" columns="8" style="height:30px;display:#{npciInputController.displayOnShow}" styleClass="row1" width="100%">
                            <h:outputLabel id="lblFromDate" styleClass="label" value="From Date" style="display:#{npciInputController.displayOnShow}"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtFromDate" styleClass="input issueDt" value="#{npciInputController.fromDate}" size="10" 
                                         style="display:#{npciInputController.displayOnShow}">
                            </h:inputText>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date" style="display:#{npciInputController.displayOnShow}"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtToDate" styleClass="input issueDt" value="#{npciInputController.toDate}" size="10" 
                                         style="display:#{npciInputController.displayOnShow}" >
                                <a4j:support actionListener="#{npciInputController.processAction()}" event="onblur" reRender="mainPanel,tablePanelDownload,tablePanelECS"
                                             oncomplete="setMask();"/> 
                            </h:inputText>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </rich:panel>
                </h:panelGrid>

                <h:panelGroup >
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{npciInputController.displayOngenerateACH}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{npciInputController.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" 
                                            style="display:#{npciInputController.displayOngenerate}">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="UMRN Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="UMR No." /></rich:column>
                                        <rich:column><h:outputText value="Frequency" /></rich:column>
                                        <rich:column><h:outputText value="Craditor A/C No." /></rich:column>
                                        <rich:column><h:outputText value="Craditor Name" /></rich:column>
                                        <rich:column><h:outputText value="Craditor IFSC" /></rich:column>
                                        <rich:column><h:outputText value="Debtor A/C No." /></rich:column>
                                        <rich:column><h:outputText value="Debtor Name" /></rich:column>
                                        <rich:column><h:outputText value="Debtor IFSC" /></rich:column>

                                        <rich:column>  
                                            <h:selectBooleanCheckbox  id="c1"  value="#{npciInputController.checkAllBox}">
                                                <a4j:support actionListener="#{npciInputController.checkAll}" event="onclick" reRender="tablePanel"/> 
                                            </h:selectBooleanCheckbox>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.cBSUmrn}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.frequency}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.creditorAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.creditorName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.creditorIFSC}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debtorAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debtorName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debtorIFSC}" /></rich:column>

                                <rich:column style="text-align:center;width:40px">
                                    <h:selectBooleanCheckbox  id="c2"  value="#{dataItem.checkBox}">
                                        <a4j:support actionListener="#{npciInputController.checkUnCheck}" event="onclick" reRender="tablePanel"/> 
                                    </h:selectBooleanCheckbox>
                                </rich:column> 
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanelDownload" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{npciInputController.displayOnShowACH}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion1">
                            <rich:dataTable value="#{npciInputController.gridDetailToShowData}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList1" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" 
                                            style="display:#{npciInputController.displayOnShowACH}">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="ACH DR INPUT File" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="File Settlement Date" /></rich:column>
                                        <rich:column ><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="File Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem[1]}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem[0]}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <%--  <a4j:commandLink id="saveLink" value="#{dataItem[0]}"  style="color:blue;" styleClass="headerLabel"
                                                      action="#{npciInputController.downloadFile(dataItem[0])}"  reRender="stxtMessage,tablePanelDownload">
                                          <f:setPropertyActionListener target="#{ClearingTxtFileBr.fileName}" value="#{fileName}"/>
                                     </a4j:commandLink>
                                    --%>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{npciInputController.downloadFile(dataItem[0])}" 
                                                     reRender="mainPanel,stxtMessage,tablePanelDownload">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <%--  <f:setPropertyActionListener value="#{dataItem}" target="#{npciInputController.currentItemECS}"/>--%>
                                    </a4j:commandLink>
                                </rich:column> 
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanelECS" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{npciInputController.displayOnShowECS}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegionECS">
                            <rich:dataTable value="#{npciInputController.gridDetailECS}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskListECS" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" 
                                            style="display:#{npciInputController.displayOnShowECS}">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"><h:outputText value="Generated ECS File Details" /></rich:column>
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
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{npciInputController.downloadFile(dataItem.fileName)}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <%--  <f:setPropertyActionListener value="#{dataItem}" target="#{npciInputController.currentItemECS}"/>--%>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskListECS" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGroup>


                <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                    <h:panelGroup id="btnPanel">
                        <a4j:commandButton id="btnHtml" value="#{npciInputController.btnValue}" style="display:#{npciInputController.displayOngenerate}"
                                           action="#{npciInputController.createConfirmTxt}" 
                                           oncomplete="#{rich:component('processPanel')}.show();setMask();" 
                                           reRender="mainPanel,tablePanel,tablePanelDownload,processPanel"/>
                        <a4j:commandButton id="btnRefresh" value="Refresh" action="#{npciInputController.btnRefreshAction}" 
                                           reRender="mainPanel,tablePanel,tablePanelDownload" oncomplete="setMask();"/>
                        <a4j:commandButton id="btnExit" value="Exit" action="#{npciInputController.btnExitAction}" reRender="mainPanel,tablePanel" oncomplete="setMask();"/>
                    </h:panelGroup>
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
                                    <h:outputText id="confirmid" value="#{npciInputController.confirmText}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{npciInputController.processAction()}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddFunction')}.focus();setMask();" 
                                                       reRender="mainPanel,tablePanel,tablePanelDownload"/>
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
