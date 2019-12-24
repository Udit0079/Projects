

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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>TD Listing</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdManaged.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="TD Listing"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdManaged.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel2" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label1" styleClass="label" value="Account No.">
                                <font class="required" style="color:red;">*</font>
                                </h:outputLabel>
                                <h:panelGroup id="panelGroup1" layout="block">
                                    <h:inputText id="txtAccNo" styleClass="input" value="#{TdManaged.accNo}" maxlength="#{TdManaged.acNoMaxLen}" style="width:90px">
                                        <a4j:support actionListener="#{TdManaged.getAccDetail}" event="onblur" oncomplete="if(#{TdManaged.acNoFlag=='true'}){#{rich:element('txtAccNo')}.focus();} else{#{rich:element('btnDetail')}.focus();#{rich:element('gridPanel103')}.style.display='';#{rich:element('gridrtNo')}.style.display='none';
                                                     #{rich:element('gridroi')}.style.display='none';#{rich:element('gridIssueDt')}.style.display='none';#{rich:element('gridMatDte')}.style.display='none';
                                                     #{rich:element('btnBack')}.style.display='none';#{rich:element('btnPrint')}.style.display='none';#{rich:element('btnModify')}.style.display='none';#{rich:element('btnDetail')}.style.display='';#{rich:element('gridMatDte2')}.style.display='none';}"
                                                     reRender="stxtjtName,stxtOperMode,output1,stxtName,stxtTotAmt,stxtBalTd,stxtTdsDeduc,stxtTotIntTrans,taskList,errMsg,btnDetail,dataScroll1" limitToList="true" />

                                </h:inputText>
                                <h:outputText id="output1" styleClass="output" value="#{TdManaged.acNoLabel}" />
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel3" style="height:30px;" styleClass="row2" width="100%">  
                            <h:outputLabel id="label2" styleClass="label" value="Name"/>
                            <h:panelGroup id="panelGroup2" layout="block">
                                <h:outputText id="stxtName" styleClass="output" value="#{TdManaged.name}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel14" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel15" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="jtLbl" styleClass="label" value="Joint Name"/>
                            <h:outputText id="stxtjtName" styleClass="output" value="#{TdManaged.jtName}"/>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel16" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="operMdLbl" styleClass="label" value="Mode of Operation"/>
                            <h:outputText id="stxtOperMode" styleClass="output" value="#{TdManaged.operMode}"/>
                        </h:panelGrid>
                    </h:panelGrid>           
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel4" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel5" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label3" styleClass="label" value="Total Prin. Amt."/>
                            <h:panelGroup id="panelGroup3" layout="block">
                                <h:outputText id="stxtTotAmt" styleClass="output" value="#{TdManaged.totPrntAmt}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel6" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label5" styleClass="label" value="Balance in TD A/C"/>
                            <h:panelGroup id="panelGroup4" layout="block">
                                <h:outputText id="stxtBalTd" styleClass="output" value="#{TdManaged.balTd}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel9" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel8" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel  id="label7" styleClass="label" value="TDS Deducted"/>
                            <h:panelGroup id="panelGroup6" layout="block">
                                <h:outputText id="stxtTdsDeduc" styleClass="output" value="#{TdManaged.tdsDeduct}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel9" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label8" styleClass="label" value="Total Interest Transferred"/>
                            <h:panelGroup id="panelGroup7" layout="block">
                                <h:outputText id="stxtTotIntTrans" styleClass="output" value="#{TdManaged.totIntTransfr}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridrtNo" width="100%" style="display:none;">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridRtNo" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel  id="lblRtNo" styleClass="label" value="R.T.No."/>
                            <h:panelGroup id="panelGrpRtNo" layout="block">
                                <h:outputText id="stxtRtNo" styleClass="output" value="#{TdManaged.rtNo}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridPrinAmt" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblPrinAmt" styleClass="label" value="Prin. Amt."/>
                            <h:panelGroup id="panelGrpPrinAmt" layout="block">
                                <h:outputText id="stxtPrinAmt" styleClass="output" value="#{TdManaged.prinAmt}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid style="display:none;" columnClasses="col8,col8" columns="2" id="gridroi" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridRoi" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblRoi" styleClass="label" value="ROI"/>
                            <h:panelGroup id="panelGrpRoi" layout="block">
                                <h:inputText id="stxtRoi" styleClass="input" disabled="#{TdManaged.flagForRoi}"style="width:40px;"value="#{TdManaged.roi}" maxlength="5">
                                </h:inputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridMatAmt" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblMatAmt" styleClass="label" value="Mat. Amt."/>
                            <h:panelGroup id="panelGrpMatAmt" layout="block">
                                <h:outputText id="stxtMatAmt" styleClass="output" value="#{TdManaged.matAmt}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid style="display:none;" columnClasses="col8,col8" columns="2" id="gridIssueDt" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridIssueDate" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblIssueDate" styleClass="label" value="Issue Date"/>
                            <h:panelGroup id="panelGrpIssueDt" layout="block">
                                <h:outputText id="stxtIssueDt" styleClass="output" value="#{TdManaged.issueDate}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridTdWef" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblTdWef" styleClass="label" value="TD wef"/>
                            <h:panelGroup id="panelGrpTdWef" layout="block">
                                <h:outputText id="stxtTdWef" styleClass="output" value="#{TdManaged.tdDateWef}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid style="display:none;" columnClasses="col8,col8" columns="2" id="gridMatDte" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridMtDte" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblMtDate" styleClass="label" value="Mat. Date"/>
                            <h:panelGroup id="panelGrpMtDte" layout="block">
                                <h:outputText id="stxtMatDte" styleClass="output" value="#{TdManaged.matdate}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridRcptNo" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel  id="lblRcptNo" styleClass="label" value="Receipt No."/>
                            <h:panelGroup id="panelGrpRcptNo" layout="block">
                                <h:outputText id="stxtRcptNo" styleClass="output" value="#{TdManaged.rcptNo}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid  style="display:none;"columnClasses="col8,col8" columns="2" id="gridMatDte2" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridMtDte2" styleClass="row1" width="100%">
                            <h:outputLabel id="lblMtDate2" styleClass="label" value="Mat. Date"/>
                            <h:panelGrid columnClasses="col1,col1,col2,col1,col2,col7" columns="6" id="Row11" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblYear" styleClass="label" value="Years"/>
                                <h:inputText id="txtYear" styleClass="input" style="width:30px;" value="#{TdManaged.years}" maxlength="4">
                                    <a4j:support actionListener="#{TdManaged.onblurMatDate}" event="onblur" reRender="errMsg,stxtMatDte" focus="txtMonths"/>
                                </h:inputText>
                                <h:outputLabel id="lblMonths" styleClass="label" value="Months"/>
                                <h:inputText id="txtMonths" styleClass="input"style="width:30px;"value="#{TdManaged.months}" maxlength="4">
                                    <a4j:support actionListener="#{TdManaged.onblurMatDate}" event="onblur" reRender="errMsg,stxtMatDte" focus="txtDays"/>
                                </h:inputText>
                                <h:outputLabel id="lblDays" styleClass="label" value="Days"/>
                                <h:inputText id="txtDays" styleClass="input"style="width:30px;"value="#{TdManaged.days}" maxlength="4">
                                    <a4j:support actionListener="#{TdManaged.onblurMatDate}" event="onblur" reRender="errMsg,stxtMatDte" focus="btnModify"/>
                                </h:inputText>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridRcptNo2" styleClass="row1" width="100%">
                            <h:outputLabel  id="lblRcptNo2" styleClass="label" value="Receipt No."/>
                            <h:panelGroup id="panelGrpRcptNo2" layout="block">
                                <h:outputText id="stxtRcptNo2" styleClass="output" value="#{TdManaged.rcptNo}">
                                </h:outputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:200px;">
                        <rich:dataTable  rows="10"value="#{TdManaged.acd}" var="dataItem" rowClasses="gridrow1, gridrow2" id="taskList"  columnsWidth="100"
                                         rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="25"><h:outputText value="TD Listing" /></rich:column>
                                    <rich:column breakBefore="true"> <h:outputText value="Control No." /> </rich:column> <rich:column><h:outputText value="R.T. No." /></rich:column>
                                    <rich:column><h:outputText value="Receipt No." /> </rich:column>
                                    <rich:column><h:outputText value="ROI" /></rich:column>
                                    <rich:column><h:outputText value="TD Date" /></rich:column>
                                    <rich:column><h:outputText value="TD Date w.e.f." /></rich:column>
                                    <rich:column><h:outputText value="Mat. Date" /></rich:column>
                                    <rich:column><h:outputText value="Prin. Amt.(Rs.)" /></rich:column>
                                    <rich:column><h:outputText value="Int Opt" /></rich:column>
                                    <rich:column><h:outputText value="Int. To Acno." /></rich:column>
                                    <rich:column><h:outputText value="Period" /></rich:column>
                                    <rich:column><h:outputText value="Int. at Mat.(Rs.)" /></rich:column>
                                    <rich:column><h:outputText value="Tot. TD Amt.(Rs.)" /></rich:column>
                                    <rich:column><h:outputText value="Status" /></rich:column>
                                    <rich:column><h:outputText value="Net Roi" /></rich:column>
                                    <rich:column><h:outputText value="Final Amount" /></rich:column>
                                    <rich:column><h:outputText value="ODFDR Acno." /></rich:column>                                    
                                    <rich:column><h:outputText value="Select Record" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.ctrlNo}"/></rich:column>
                            <rich:column><h:outputText  value="#{dataItem.rtNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.rcptNo}" /></rich:column>
                            <rich:column><h:outputText  value="#{dataItem.roi}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.tdDate}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.tdDateWef}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.matDate}" /></rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.prinAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.intOpt}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.intToAcno}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.period}" /></rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.intAtMat}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.totalTdAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.status}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.netRoi}" /></rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.finalAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.odfdrAccNo}" /></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink oncomplete="#{rich:element('gridPanel103')}.style.display='none';#{rich:element('gridrtNo')}.style.display='';
                                                 #{rich:element('gridroi')}.style.display='';#{rich:element('gridIssueDt')}.style.display='';#{rich:element('gridMatDte')}.style.display='';
                                                 #{rich:element('btnBack')}.style.display='';#{rich:element('btnPrint')}.style.display='';#{rich:element('btnModify')}.style.display='';#{rich:element('btnDetail')}.style.display='none';#{rich:element('gridMatDte2')}.style.display='none';"
                                                 ajaxSingle="true" id="selectlink" action="#{TdManaged.codeForModifyButton}"
                                                 reRender="txtYear,txtMonths,txtDays,stxtRtNo,stxtPrinAmt,stxtRoi,stxtMatAmt,stxtIssueDt,stxtTdWef,stxtMatDte,stxtRcptNo,errMsg,btnModify,stxtRcptNo2">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{TdManaged.accDtl}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{TdManaged.currentRow}"/>
                                </a4j:commandLink>

                            </rich:column>                          
                        </rich:dataTable>
                        <rich:datascroller id="dataScroll1"align="left" for="taskList" maxPages="6" />

                    </h:panelGrid>
                    <h:panelGrid id="errorMsg" columns="1" style="width:100%;text-align:center;" columnClasses="col8" width="100%" styleClass="row2">
                        <h:outputText  style="align:center;" id="errMsg" styleClass="error" value="#{TdManaged.msg}" />
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton disabled="true" id="btnCalculator" value="Calculator"/>
                            <a4j:commandButton id="btnDetail" value="Detail" disabled="#{TdManaged.flag}"  reRender="taskDetailList,scrollDetails" 
                                               onclick="#{rich:component('showPanel')}.show();" action="#{TdManaged.getDetlOnClick}"/>
                            <a4j:commandButton action="#{TdManaged.loadGridonBack}" reRender="taskList" ajaxSingle="true" onclick="#{rich:element('btnBack')}.style.display='none';#{rich:element('errMsg')}.style.display='none';#{rich:element('gridPanel103')}.style.display='';#{rich:element('gridrtNo')}.style.display='none';
                                               #{rich:element('gridroi')}.style.display='none';#{rich:element('gridIssueDt')}.style.display='none';#{rich:element('gridMatDte')}.style.display='none';
                                               #{rich:element('btnPrint')}.style.display='none';#{rich:element('btnModify')}.style.display='none';#{rich:element('btnDetail')}.style.display='';#{rich:element('gridMatDte2')}.style.display='none';" style="display:none;"  id="btnBack" value="Back"/>
                            <a4j:commandButton id="btnModify" style="display:none" value="#{TdManaged.captionModify}" disabled="#{TdManaged.flagForModify}" action="#{TdManaged.clickOnModifyButton}" 
                                               oncomplete="#{rich:element('btnModify')}.value='#{TdManaged.captionModify}';" reRender="stxtRoi,errMsg" onclick="#{rich:element('gridMatDte2')}.style.display='';#{rich:element('gridMatDte')}.style.display='none';" focus="stxtRoi"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{TdManaged.refreshForm()}" 
                                               reRender="mainPanel,txtAccNo,output1,stxtName,stxtTotAmt,stxtBalTd,stxtTdsDeduc,stxtTotIntTrans,taskList"/>
                            <a4j:commandButton style="display:none" id="btnPrint" value="Print" disabled="true"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TdManaged.exitFrm}" 
                                               reRender="txtAccNo,output1,stxtName,stxtTotAmt,stxtBalTd,stxtTdsDeduc,stxtTotIntTrans,taskList"/>
                        </h:panelGroup>
                        <rich:modalPanel top="false"autosized="true" width="1000"  id="showPanel">
                            <f:facet name="header">
                                <h:outputText style="padding-right:15px;"/>
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink2" />
                                    <rich:componentControl for="showPanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:panelGrid id="mainPane2" bgcolor="#fff"  style="border:1px ridge #BED6F8" width="100%">
                                <rich:dataTable    value="#{TdManaged.accdtl}"var="item"
                                                   rowClasses="gridrow1, gridrow2" id="taskDetailList"  columnsWidth="100" rowKeyVar="row"
                                                   onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                   onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                   rows="10"onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                                   ">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="7"><h:outputText value="TD Receipt Listing Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                            <rich:column><h:outputText value="Voucher No." /></rich:column>
                                            <rich:column><h:outputText value="Dr. Amount" /></rich:column>
                                            <rich:column><h:outputText value="Cr. Amount" /></rich:column>
                                            <rich:column><h:outputText value="Issue Date" /></rich:column>
                                            <rich:column><h:outputText value="TD Date w.e.f." /></rich:column>
                                            <rich:column><h:outputText value="Details" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.accountNo}"/></rich:column>
                                    <rich:column><h:outputText  value="#{item.voucherNo}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.drAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.crAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column ><h:outputText  value="#{item.isuDate}" /></rich:column>
                                    <rich:column ><h:outputText  value="#{item.fdDate}" /></rich:column>
                                    <rich:column ><h:outputText  value="#{item.details}" /></rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scrollDetails" align="left" for="taskDetailList" maxPages="5"/>
                                <h:panelGrid columns="1" id="backButton" styleClass="footer" style="height:30px;" width="100%">
                                    <h:panelGroup id="backPanel" layout="block">
                                        <a4j:commandButton style="align:center;" value="Back"onclick="#{rich:component('showPanel')}.hide();" />
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:modalPanel>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
