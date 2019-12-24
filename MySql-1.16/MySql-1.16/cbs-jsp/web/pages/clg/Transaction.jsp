<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>CtsTransaction Details</title>
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
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CtsTransaction.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Cheque Truncation Trasaction"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CtsTransaction.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col5,col8" columns="2" id="bodyPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="1" id="rightPanel" style="width:100%;height:265px;background-color:#e8eef7;">
                            <h:panelGrid columns="1" id="chqViewPanel" style="text-align:center;vertical-align:top;background-color:#e8eef7" columnClasses="col8">
                                <h:graphicImage id="chqImage" value="/cts-image/dynamic/?file=#{CtsTransaction.imageData}&orgn=#{CtsTransaction.folderName}" height="250" width="565" />
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="leftPanel" style="width:100%;vertical-align:top;">
                            <h:panelGroup layout="block" id="rightPanelGrid" style="width:100%;height:265px;vertical-align:top;background-color:#e8eef7; overflow:auto">
                                <a4j:region>
                                    <rich:dataTable  var="dataItem" value="#{CtsTransaction.txnDetailBeanList}"
                                                     rowClasses="gridrow1, gridrow2" id="taskList" rows="5" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column width="35"><h:outputText value="Action"/></rich:column>
                                                <rich:column width="35"><h:outputText value="Sr. No."/></rich:column>
                                                <rich:column width="55"><h:outputText value="Inst. Date"/></rich:column>
                                                <rich:column width="60"><h:outputText value="Amount">
                                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                                    </h:outputText>
                                                </rich:column>
                                                <rich:column width="50"><h:outputText value="Cheque No."/></rich:column>
                                                <rich:column width="55"><h:outputText value="Pr.Bank Code"/></rich:column>
                                                <rich:column><h:outputText value="Pr.Bank Name"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="selectLink" action="#{CtsTransaction.setRowValues}" reRender="calInstDate,txtInstNo,txtInstAmt,txtInFavOf,txtBankName,txtBankAddress,chqViewPanel,chqImage,signature,instHiddenAmt" focus="ddDestBranch" oncomplete="setMask();">
                                                <h:graphicImage value="/resources/images/select.gif" style="border:0" width="15" height="15"/>
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{CtsTransaction.txnDetailBean}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                        <rich:column><h:outputText value="#{dataItem.srNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.instDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.amount}"/></rich:column>
                                        <rich:column><h:outputText  value="#{dataItem.chqNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.prBankCode}"/></rich:column>
                                        <rich:column style="text-align:left;"><h:outputText  value="#{dataItem.prBankName}"/></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller id="detailScroller"align="left" for="taskList" maxPages="20" />
                                </a4j:region>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="panelTxn3" styleClass="row2"  style="width:100%;"columns="6"columnClasses="col18,col17,col18,col17,col17,col17" >
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch" style="align:left;"/>
                        <h:selectOneListbox id="ddBranch" size="1" styleClass="ddlist" value="#{CtsTransaction.branch}">
                            <f:selectItems value="#{CtsTransaction.branchList}"/>
                            <%--<a4j:support actionListener="#{CtsTransaction.branchLostFocus}" event="onblur" oncomplete="if(#{rich:element('ddBranch')}.value=='0'){#{rich:element('ddBranch')}.focus();} setMask();" reRender="errorMessage,taskList,btnSave,detailScroller"/>--%>
                            <a4j:support actionListener="#{CtsTransaction.retrieveScheduleNo}" event="onblur" oncomplete="setMask();" reRender="errorMessage,ddScheduleNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblScheduleNo" styleClass="label" value="Schedule No"/>
                        <h:selectOneListbox id="ddScheduleNo" size="1" styleClass="ddlist" value="#{CtsTransaction.scheduleNo}">
                            <f:selectItems value="#{CtsTransaction.scheduleNoList}"/>
                            <a4j:support actionListener="#{CtsTransaction.branchLostFocus}" event="onblur" oncomplete="setMask();" reRender="errorMessage,taskList,btnSave,detailScroller"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAccNo" styleClass="label" value="A/C No."/>
                        <h:inputText id="txtaccNo" styleClass="input" maxlength="#{CtsTransaction.acNoMaxLen}" value="#{CtsTransaction.acctCode}" onkeyup="this.value=this.value.toUpperCase();" style="width:100px;" size="15">
                            <a4j:support actionListener="#{CtsTransaction.onblurAccountCode}" event="onblur" reRender="alertId,errorMessage,stxtAccNo,stxtOpMode,stxtName,stxtJtName,stxtClrBal,stxtUnClrBal,lblSeqNo,stxtSeqNo" oncomplete="if(#{CtsTransaction.flag3==true}){#{rich:component('alertPanel')}.show();} setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panelTxn2" styleClass="row1"  style="width:100%;"columns="6"columnClasses="col18,col17,col18,col17,col17,col17" >   
                        <h:outputLabel id="lblOpMode" styleClass="label" value="Op. Mode"/>
                        <h:outputText id="stxtOpMode" styleClass="output" value="#{CtsTransaction.opMode}"/>
                        <h:outputLabel id="lblAccNo2" styleClass="label" value="Account No."/>
                        <h:outputText id="stxtAccNo" styleClass="output" value="#{CtsTransaction.accNo}"/>
                        <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                        <h:outputText id="stxtName" styleClass="output" value="#{CtsTransaction.name}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelTxn4" styleClass="row2"  style="width:100%;"columns="6"columnClasses="col18,col17,col18,col17,col17,col17" >
                        <h:outputLabel id="lblJtName" styleClass="label" value="Jt. Name"/>
                        <h:outputText id="stxtJtName" styleClass="output" value="#{CtsTransaction.jtName}"/>
                        <h:outputLabel id="lblClrBal" styleClass="label" value="Cl. Bal."/>
                        <h:outputText id="stxtClrBal" styleClass="output" value="#{CtsTransaction.clBal}">
                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                        </h:outputText>
                        <h:outputLabel id="lblUnClrBal" styleClass="label" value="UnCl. Bal."/>
                        <h:outputText id="stxtUnClrBal" styleClass="output" value="#{CtsTransaction.unclBal}">
                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                        </h:outputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="panelTxn5" styleClass="row1"  style="width:100%;"columns="6"columnClasses="col18,col18,col18,col18,col17,col17" >
                        <h:outputLabel id="lblInstDate" styleClass="label" value="Inst. Dt."/>
                        <h:inputText id="calInstDate" size="10" styleClass="input issueDt" value="#{CtsTransaction.instDate}">
                            <a4j:support event="onblur" focus="txtInstNo" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst. No."/>
                        <h:inputText id="txtInstNo" styleClass="input" style="width:80px;" value="#{CtsTransaction.instNo}" maxlength="6">
                            <a4j:support actionListener="#{CtsTransaction.checkInstNo}" event="onblur" oncomplete="if(#{CtsTransaction.flag5==true}){#{rich:component('alertPanelChq')}.show();} setMask();" reRender="alertChqId"/>
                        </h:inputText>
                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amount"/>
                        <h:inputText id="txtInstAmt" styleClass="input" style="width:80px;" value="#{CtsTransaction.instAmount}">
                            <a4j:support actionListener="#{CtsTransaction.getCtsSeqNo}" event="onblur" oncomplete="if(#{rich:element('txtInstAmt')}.value==''){#{rich:element('txtInstAmt')}.focus();} if(#{CtsTransaction.flag55=='true'}){#{rich:component('alertAmtwithSummary')}.show();setMask();}" reRender="stxtSeqNo,errorMessage,txtInFavOf,stxtName,alertAmtwithSummary"/>
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="panelTxn6" styleClass="row2"  style="width:100%;"columns="6"columnClasses="col18,col18,col18,col18,col17,col17" >
                        <h:outputLabel id="lblDetails" styleClass="label" value="Details"/>
                        <h:inputText id="txtDetails" styleClass="input"  value="#{CtsTransaction.dtls}" onkeypress="this.value=this.value.toUpperCase();" style="width:115px;" maxlength="80">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblInFavOf" styleClass="label" value="In Favour Of"/>
                        <h:inputText id="txtInFavOf" styleClass="input"  value="#{CtsTransaction.inFavourOf}" disabled="true" style="width:115px;"/>
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq. No." style="display:#{CtsTransaction.flag4};"/>
                        <h:outputText id="stxtSeqNo" styleClass="output" value="#{CtsTransaction.seqNo}" style="display:#{CtsTransaction.flag4};"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelTxn7" styleClass="row1"  style="width:100%;"columns="6"columnClasses="col18,col18,col18,col18,col17,col17">
                        <h:outputLabel id="lblBankName" styleClass="label" value="Bank Name"/>
                        <h:inputText id="txtBankName" styleClass="input" value="#{CtsTransaction.bankName}" disabled="true" style="width:115px;"/>
                        <h:outputLabel id="lblBankAddress" styleClass="label" value="Bank Address"/>
                        <h:inputText id="txtBankAddress" styleClass="input"  value="#{CtsTransaction.bankAddress}" disabled="true" style="width:115px;"/>
                        <h:inputHidden id="instHiddenAmt" value="#{CtsTransaction.instHiddenAmt}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" columns="2"columnClasses="col6,col13">
                        <h:panelGroup id="imageMsgPanel" style="text-align:left;" layout="block">
                            <h:outputText id="stxtInstruction" styleClass="output" value="F7-Sign. View,F8-White Image,F9-Back Image,F10-Gray Image,Ctrl+B-A/C Type Desc." style="color:blue;"/>
                            <a4j:region id="saveRegion">
                                <a4j:commandButton  id="btnSave" value="Save" action="#{CtsTransaction.saveInwardClgDetails}"disabled="#{CtsTransaction.flag2}" reRender="errorMessage,ddBranch,ddDestBranch,ddAccType,txtaccNo,txtaccNo2,stxtOpMode,stxtAccNo,stxtName,stxtJtName,stxtClrBal,stxtUnClrBal,calInstDate,txtInstNo,txtInstAmt,stxtSeqNo,txtDetails,txtInFavOf,txtBankName,txtBankAddress,taskList,chqImage,btnSave,stxtSeqNo" oncomplete="setMask();">
                                </a4j:commandButton>
                            </a4j:region>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CtsTransaction.cancelTxnProcess}" reRender="ddBranch,ddDestBranch,ddAccType,txtaccNo,txtaccNo2,stxtOpMode,stxtAccNo,stxtName,stxtJtName,stxtClrBal,stxtUnClrBal,calInstDate,txtInstNo,txtInstAmt,stxtSeqNo,txtDetails,txtInFavOf,txtBankName,txtBankAddress,taskList,chqImage,errorMessage,btnSave" oncomplete="#{rich:component('checkPanel')}.hide();#{rich:component('alertPanel')}.hide();#{rich:component('alertPanelChq')}.hide();#{rich:element('ddBranch')}.focus(); setMask();">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CtsTransaction.exitForm}" reRender="ddBranch,ddDestBranch,ddAccType,txtaccNo,txtaccNo2,stxtOpMode,stxtAccNo,stxtName,stxtJtName,stxtClrBal,stxtUnClrBal,calInstDate,txtInstNo,txtInstAmt,stxtSeqNo,txtDetails,txtInFavOf,txtBankName,txtBankAddress,taskList,chqImage,errorMessage,btnSave" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnChqData" value="Data Verification" action="#{CtsTransaction.checkData}" disabled="#{CtsTransaction.disableDataVerification}"reRender="chkId" oncomplete="#{rich:component('checkPanel')}.show(); setMask();"/>
                        </h:panelGroup>
                        <h:panelGroup id="msgPanel"  layout="block">
                            <h:outputText id="errorMessage" styleClass="error" value="#{CtsTransaction.errorMsg}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:jsFunction name="showSignDetail" oncomplete="if(#{rich:element('txtaccNo')}.value!='') #{rich:component('sigView')}.show(); setMask();" reRender="sigView"/>
                <a4j:jsFunction name="showWhilteImage" action="#{CtsTransaction.getWhiteImage}" reRender="chqImage,errorMessage" oncomplete="setMask();"/>
                <a4j:jsFunction name="showBackImage" action="#{CtsTransaction.getBackImage}" reRender="chqImage,errorMessage" oncomplete="setMask();"/>
                <a4j:jsFunction name="showGrayImage" action="#{CtsTransaction.getGrayImage}" reRender="chqImage,errorMessage" oncomplete="setMask();"/>
                <a4j:jsFunction name="showAccTypesDesc" action="#{CtsTransaction.accTypeNatureDesc()}" oncomplete="#{rich:component('accTypeDescView')}.show();" reRender="accTypeDesc,scrollAccTypeDesc"/>
            </a4j:form>
            <rich:modalPanel id="sigView" height="300" width="150" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Signature Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                        <rich:componentControl for="sigView" attachTo="closelink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="1" id="sigViewPanel" style="width:100%;height:355px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature"  style="width:500px;height:250px;"element="img" createContent="#{CtsTransaction.createSignature}" value="#{CtsTransaction.accNo}"/>
                        </h:panelGrid>
                        <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="sigViewBtnPanel">
                                <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;" oncomplete="setMask();">
                                    <rich:toolTip for="sigViewClose" value="Click To close Signature View"></rich:toolTip>
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            <rich:hotKey key="F7" handler="showSignDetail();"/>
            <rich:hotKey key="F8" handler="showWhilteImage();"/>
            <rich:hotKey key="F9" handler="showBackImage();"/>
            <rich:hotKey key="F10" handler="showGrayImage();"/>
            <rich:hotKey key="Ctrl+B" handler="showAccTypesDesc(); return false;"/>
            <rich:modalPanel id="checkPanel" label="Confirmation" autosized="true" width="300" onshow="#{rich:element('btnOK3')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="chkId" value="#{CtsTransaction.msgForDataVerification}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Ok" id="btnOK3" oncomplete="#{rich:component('checkPanel')}.hide(); setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="alertPanel" label="Confirmation" autosized="true" width="350" onshow="#{rich:element('btnCancel1')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="alertId" value="#{CtsTransaction.alertMsg}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" style="padding-left:110px;width:10px;">
                                    <a4j:commandButton value="OK" id="btnOK1" oncomplete="#{rich:component('alertPanel')}.hide(); setMask();" focus="#{rich:clientId('calInstDate')}InputDate"/>
                                </td>
                                <td align="center" style="padding-right:110px;width:10px;">
                                    <a4j:commandButton value="Cancel" id="btnCancel1" oncomplete="#{rich:component('alertPanel')}.hide();#{rich:element('ddAccType')}.focus();#{rich:element('txtaccNo')}.clear(); setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="alertPanelChq" label="Confirmation" autosized="true" width="400" onshow="#{rich:element('btnCancel2')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="alertChqId" value="#{CtsTransaction.alertMsgChq}"/>
                                </td>
                            </tr>
                            <tr>
                                <h:panelGroup layout="block">
                                    <td align="center" style="padding-left:110px;width:10px;">
                                        <a4j:commandButton value="Ok" id="btnOK2" oncomplete="#{rich:component('alertPanelChq')}.hide();#{rich:element('txtInstAmt')}.focus(); setMask();"/>
                                    </td>
                                    <td align="center" style="padding-right:110px;width:10px;">
                                        <a4j:commandButton value="Cancel" id="btnCancel2" oncomplete="#{rich:component('alertPanelChq')}.hide();#{rich:element('txtInstNo')}.focus(); setMask();"/>
                                    </td>
                                </h:panelGroup>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="alertAmtwithSummary" label="Confirmation" autosized="true" width="400">
                <f:facet name="header">
                    <h:outputText value="Confirmation"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="alertAmtSummary" value="#{CtsTransaction.alertMsgAmtSummary}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" style="padding-left:110px;width:10px;">
                                    <a4j:commandButton value="Ok" id="btnOK222" oncomplete="#{rich:component('alertAmtwithSummary')}.hide();#{rich:element('txtDetails')}.focus(); setMask();"/>
                                </td>
                                <td align="center" style="padding-right:110px;width:10px;">
                                    <a4j:commandButton value="Cancel" id="btnCancel222" oncomplete="#{rich:component('alertAmtwithSummary')}.hide();#{rich:element('txtInstAmt')}.focus(); setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="accTypeDescView" height="350" width="500" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Account Type Description" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="accTypeDescLink"/>
                        <rich:componentControl for="accTypeDescView" attachTo="accTypeDescLink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="accTypeDescPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                        <rich:dataTable  var="item" value="#{CtsTransaction.accTypeDescList}"
                                         rowClasses="gridrow1, gridrow2" id="accTypeDesc" rows="10"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="7"></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="A/C Code" /></rich:column>
                                    <rich:column><h:outputText value="A/C Type" /></rich:column>
                                    <rich:column><h:outputText value="A/C Nature" /></rich:column>
                                    <rich:column><h:outputText value="A/C Description" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.acctCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accType}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accNature}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accDesc}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="scrollAccTypeDesc"align="left" for="accTypeDesc" maxPages="10" />

                    </h:panelGrid>
                    <h:panelGrid id="accTypeDescFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="accTypeDescClose" value="Close" onclick="#{rich:component('accTypeDescView')}.hide(); return false;">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
