<%-- 
    Document   : AtmCardMaster
    Created on : Sep 1, 2014, 6:53:48 PM
    Author     : sipl
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>ATM Card Issue</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calIssDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="formAtm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ATMCardMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM Card Issue"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ATMCardMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                        <h:outputText id="message" styleClass="msg" value="#{ATMCardMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="normalPanel" style="width:100%;text-align:center;display:#{ATMCardMaster.normalDisplay}">
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{ATMCardMaster.function}">
                                    <f:selectItems value="#{ATMCardMaster.functionList}"/>
                                    <a4j:support action="#{ATMCardMaster.chgFunction}" event="onblur" oncomplete="setMask();" focus="txtAcNo"
                                                 reRender="message,txtAcNo,txtCardNo,calIssDate1,txtMinLmt,ddStFlg,table,btnSave"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAcNo" styleClass="label" value="Account No"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtAcNo" styleClass="input" maxlength="12" style="width:80px;" value="#{ATMCardMaster.acNo}" disabled="#{ATMCardMaster.acFlag}">
                                    <a4j:support action="#{ATMCardMaster.chenageOperation}" event="onblur" oncomplete="setMask();"
                                                 reRender="message,txtAcNo,txtCardNo,calIssDate1,txtMinLmt,ddStFlg,table,btnSave"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblCardNo" styleClass="label" value="Card No" style="padding-left:70px;"/>
                                <h:inputText id="txtCardNo" styleClass="input" value="#{ATMCardMaster.cardNo}" maxlength="20" disabled="#{ATMCardMaster.cardFlag}"/>
                                <h:outputLabel id="lblIssueDt" styleClass="label" value="Issue. Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="calIssDate1" styleClass="input calIssDate" maxlength="10" style="width:80px;"  value="#{ATMCardMaster.issueDt}" disabled="#{ATMCardMaster.dtFlag}">
                                    <f:convertDateTime pattern="dd/MM/yyyy"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblMinLmt" styleClass="label" value="Min Limiit" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtMinLmt" styleClass="input" style="width:80px;" value="#{ATMCardMaster.minLmt}" disabled="#{ATMCardMaster.lmtFlag}"/>
                                <h:outputLabel id="lblStFlg" styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddStFlg" styleClass="ddlist" size="1" style="width:80px;" value="#{ATMCardMaster.stFlg}" disabled="#{ATMCardMaster.statusFlag}">
                                    <f:selectItems value="#{ATMCardMaster.statusList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{ATMCardMaster.gridDetail}" var="dataItem"
                                                    rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" 
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="10"><h:outputText value="CARD ISSUE DETAILS"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="ACCOUNT NO"/></rich:column>
                                                <rich:column><h:outputText value="CARD NO"/></rich:column>
                                                <rich:column><h:outputText value="ISSUE DT"/></rich:column>
                                                <rich:column><h:outputText value="MIN LIMIT"/></rich:column>
                                                <rich:column><h:outputText value="STATUS"/></rich:column>
                                                <rich:column width="20"><h:outputText value="SELECT"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.acNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.cardNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.issDt}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.minLmt}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.status}"/></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ATMCardMaster.select}" oncomplete="setMask();" reRender="normalPanel,message">
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{ATMCardMaster.currentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave" value="#{ATMCardMaster.btnValue}" action="#{ATMCardMaster.saveMasterDetail}" disabled="#{ATMCardMaster.btnFlag}" oncomplete="setMask();" reRender="normalPanel,message"/>
                                    <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ATMCardMaster.refreshForm}" oncomplete="setMask();" reRender="normalPanel,message"/>
                                    <a4j:commandButton id="btnExit" value="Exit" action="#{ATMCardMaster.exitBtnAction}" reRender="normalPanel,message"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="seqPanel" style="width:100%;display:#{ATMCardMaster.seqFileDisplay}">
                            <h:panelGrid id="seqRow1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                                <h:outputLabel id="lblFileMode" styleClass="label" value="File Mode" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFileMode" styleClass="ddlist" size="1" style="width:150px;" value="#{ATMCardMaster.fileMode}">
                                    <f:selectItems value="#{ATMCardMaster.fileModeList}"/>
                                    <a4j:support event="onblur" action="#{ATMCardMaster.fileModeProcess}" reRender="message,ddOperation" focus="ddOperation"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblOperation" styleClass="label" value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddOperation" styleClass="ddlist" size="1" style="width:120px;" value="#{ATMCardMaster.operation}">
                                    <f:selectItems value="#{ATMCardMaster.operationList}"/>
                                    <a4j:support event="onblur" action="#{ATMCardMaster.operationProcess}" 
                                                 reRender="message,txtAccountNo,stxtNewAccountNo,seqRow2,txtChn,seqRow3,stxtCustomerId,
                                                 stxtGender,stxtName,seqRow4,stxtDob,stxtMail,seqRow5,txtEncodingName,txtEmbossingName,
                                                 ddCardType,seqRow6,ddTxnLimitCardType,ddCardRelationShip,ddServiceCode,seqRow7,
                                                 ddTxnLimitTypetxtLimitAmount,txtLimitCount,seqRow8,ddKccEmvType,booleanCheckBox,
                                                 seqRow9,txtSecondaryAccountNo,stxtNewSan,ddSecondaryTxnLimitType,txtSecondaryLimitAmount,
                                                 seqRow10,txtSecondaryLimitCount,seqRow11,seqTaskList,seqRow12,stxtPrCardStatus,
                                                 ddChgCardStatus,seqRow13,stxtPrEncodingName,stxtPrEmbossingName,seqRow14,
                                                 txtChgEncodingName,txtChgEmbossingName,seqRow15,lblPurLimitAmount,txtPurLimitAmount,
                                                 seqRow15,lblPurLimitAmount,txtPurLimitAmount,seqRow16,tblRegionverify,verTaskList,lblAccountNo,
                                                 seqRow17,tblRegiondeactive" focus="txtAccountNo"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAccountNo" styleClass="label" style="display:#{ATMCardMaster.acdisplay}"  value="Account No"><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup>
                                    <h:inputText id="txtAccountNo" styleClass="input" style="display:#{ATMCardMaster.acdisplay}" value="#{ATMCardMaster.accountNo}" maxlength="#{ATMCardMaster.acNoMaxLen}" size="15">
                                        <a4j:support event="onblur" action="#{ATMCardMaster.accountProcess}" reRender="message,
                                                     stxtNewAccountNo,seqRow2,txtChn,seqRow3,stxtCustomerId,stxtGender,stxtName,
                                                     seqRow4,stxtDob,stxtMail,seqRow5,txtEncodingName,txtEmbossingName,ddCardType,
                                                     seqRow6,ddTxnLimitCardType,ddCardRelationShip,ddServiceCode,seqRow7,
                                                     ddTxnLimitTypetxtLimitAmount,txtLimitCount,seqRow8,ddKccEmvType,booleanCheckBox,
                                                     seqRow9,txtSecondaryAccountNo,stxtNewSan,ddSecondaryTxnLimitType,txtSecondaryLimitAmount,
                                                     seqRow10,txtSecondaryLimitCount,seqRow11,seqTaskList,seqRow15,lblPurLimitAmount,txtPurLimitAmount,
                                                     seqRow15,lblPurLimitAmount,txtPurLimitAmount,txtSecondaryWdrlLimitCount,txtSecondaryPurLimitAmount
                                                     txtSecondaryWdrlLimitAmount,txtSecondaryPurLimitCount,seqRow16,tblRegionverify,verTaskList,seqRow17,
                                                     tblRegiondeactive,deTaskList" 
                                                     oncomplete="if(#{ATMCardMaster.fileMode=='N'}){#{rich:element('txtEncodingName')}.focus()}"/>
                                    </h:inputText>
                                    <h:outputText id="stxtNewAccountNo" styleClass="output" value="#{ATMCardMaster.newAccountNumber}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="display:#{ATMCardMaster.chnPanelDisplay}">
                                <h:outputLabel id="lblChn" styleClass="label" value="CHN(Card No)"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtChn" styleClass="input" value="#{ATMCardMaster.chnNo}" maxlength="19" size="20">
                                    <a4j:support event="onblur" action="#{ATMCardMaster.chnProcess}" reRender="message,seqRow12,stxtPrCardStatus,
                                                 ddChgCardStatus,seqRow13,stxtPrEncodingName,stxtPrEmbossingName,seqRow14,txtChgEncodingName,txtChgEmbossingName"/>
                                </h:inputText>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow3" columns="6" columnClasses="col3,col3,col3,col3,col0,col13" styleClass="row1" width="100%" style="display:#{ATMCardMaster.firstDisplay}">
                                <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                                <h:outputText id="stxtCustomerId" styleClass="output" value="#{ATMCardMaster.customerId}"/>
                                <h:outputLabel id="lblGender" styleClass="label" value="Gender"/>
                                <h:outputText id="stxtGender" styleClass="output" value="#{ATMCardMaster.gender}"/>
                                <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                                <h:outputText id="stxtName" styleClass="output" value="#{ATMCardMaster.name}"/>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow4" columns="4" columnClasses="col3,col3,col3,col49" styleClass="row2" width="100%" style="display:#{ATMCardMaster.firstDisplay}">
                                <h:outputLabel id="lblDob" styleClass="label" value="Date of Birth"/>
                                <h:outputText id="stxtDob" styleClass="output" value="#{ATMCardMaster.dob}"/>
                                <h:outputLabel id="lblMail" styleClass="label" value="Mail Address"/>
                                <h:outputText id="stxtMail" styleClass="output" value="#{ATMCardMaster.mail}"/>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow5" columns="4" columnClasses="col3,col3,col3,col49" styleClass="row1" width="100%" style="display:#{ATMCardMaster.firstDisplay}">
                                <h:outputLabel id="lblEncodingName" styleClass="label" value="Encoding Name"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtEncodingName" styleClass="input" value="#{ATMCardMaster.encodingName}" maxlength="25" size="20" onkeyup="this.value=this.value.toUpperCase();"/>
                                <h:outputLabel id="lblEmbossingName" styleClass="label" value="Embossing Name"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtEmbossingName" styleClass="input" value="#{ATMCardMaster.embossingName}" maxlength="25" size="20" onkeyup="this.value=this.value.toUpperCase();"/>
                           <%--      <h:outputLabel id="lblCardType" styleClass="label" value="Card Type" ><font class="required" style="color:red;">*</font></h:outputLabel>
                               <h:selectOneListbox id="ddCardType" styleClass="ddlist" size="1" style="width:140px;" value="#{ATMCardMaster.cardType}">
                                    <f:selectItems value="#{ATMCardMaster.cardTypeList}"/>
                                </h:selectOneListbox>  --%>
                            </h:panelGrid>
                    <%--        <h:panelGrid id="seqRow6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{ATMCardMaster.firstDisplay}">
                                <h:outputLabel id="lblTxnLimitCardType" styleClass="label" value="Txn Limit Card Type" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddTxnLimitCardType" styleClass="ddlist" size="1" style="width:140px;" value="#{ATMCardMaster.txnLimitCardType}">
                                    <f:selectItems value="#{ATMCardMaster.txnLimitCardTypeList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblCardRelationShip" styleClass="label" value="Card Relationship" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddCardRelationShip" styleClass="ddlist" size="1" style="width:140px;" value="#{ATMCardMaster.cardRelationship}">
                                    <f:selectItems value="#{ATMCardMaster.cardRelationshipList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblServiceCode" styleClass="label" value="Service Code" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddServiceCode" styleClass="ddlist" size="1" style="width:140px;" value="#{ATMCardMaster.serviceCode}">
                                    <f:selectItems value="#{ATMCardMaster.serviceCodeList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>  --%>
                            <h:panelGrid id="seqRow7" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="display:#{ATMCardMaster.firstDisplay}">
                                <h:outputLabel id="lblTxnLimitType" styleClass="label" value="Txn Limit Type" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddTxnLimitType" styleClass="ddlist" size="1" style="width:140px;" value="#{ATMCardMaster.txnLimitType}">
                                    <f:selectItems value="#{ATMCardMaster.txnLimitTypeList}"/>
                                </h:selectOneListbox>
                             <%--   <h:outputLabel id="lblLimitAmount" styleClass="label" value="Limit Amount"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtLimitAmount" styleClass="input" value="#{ATMCardMaster.limitAmount}" size="20"/>
                                <h:outputLabel id="lblLimitCount" styleClass="label" value="Limit Count"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtLimitCount" styleClass="input" value="#{ATMCardMaster.limitCount}" size="20"/>   --%>
                             <h:outputLabel id="lblWdrlLimitAmount" styleClass="label" value="Withdrawal Limit Amount"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtWdrlLimitAmount" styleClass="input" value="#{ATMCardMaster.wdrlLimitAmount}" size="20" maxlength="5" />
                                <h:outputLabel id="lblWdrlLimitCount" styleClass="label" value="Withdrawal Limit Count"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtWdrlLimitCount" styleClass="input" value="#{ATMCardMaster.wdrlLimitCount}" size="20"/>
                            </h:panelGrid>
                         <h:panelGrid id="seqRow15" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="display:#{ATMCardMaster.firstDisplay}">
                                <h:outputLabel id="lblPurLimitAmount" styleClass="label" value="Purchase Limit Amount"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtPurLimitAmount" styleClass="input" value="#{ATMCardMaster.purLimitAmount}" size="20" maxlength="5" />
                                <h:outputLabel id="lblPurLimitCount" styleClass="label" value="Purchase Limit Count"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtPurLimitCount" styleClass="input" value="#{ATMCardMaster.purLimitCount}" size="20"/>
                                <h:outputLabel id="lblMinLimit" styleClass="label" value="Min Limit"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtMinLimit" styleClass="input" value="#{ATMCardMaster.minLimit}" size="20" maxlength="5" />
                               </h:panelGrid>
                            <h:panelGrid id="seqRow8" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{ATMCardMaster.firstDisplay}">
                              <%--  <h:outputLabel id="lblKccEmvType" styleClass="label" value="KCC/EMV Type" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddKccEmvType" styleClass="ddlist" size="1" style="width:140px;" value="#{ATMCardMaster.kccEmvType}">
                                    <f:selectItems value="#{ATMCardMaster.kccEmvTypeList}"/>
                                </h:selectOneListbox>  --%>
                                <h:outputLabel id="lblSecondaryCheckBox" styleClass="label" value="Add Secondary Account"/>
                                <h:selectBooleanCheckbox id="booleanCheckBox" value="#{ATMCardMaster.secondaryChkBox}" styleClass="input">
                                    <a4j:support event="onchange" action="#{ATMCardMaster.checkUncheckProcess}" reRender="message,seqRow9,seqRow10,seqRow11,srqRow16" focus="txtSecondaryAccountNo"/>
                                </h:selectBooleanCheckbox>                                                                                
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow9" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="display:#{ATMCardMaster.secondaryPanelDisplay}">
                                <h:outputLabel id="lblSecondaryAccountNo" styleClass="label" value="SAN"><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup>
                                    <h:inputText id="txtSecondaryAccountNo" styleClass="input" value="#{ATMCardMaster.san}" maxlength="#{ATMCardMaster.acNoMaxLen}"  size="15">
                                        <a4j:support event="onblur" action="#{ATMCardMaster.sanProcess}" reRender="message,stxtNewSan" focus="ddSecondaryTxnLimitType"/>
                                    </h:inputText>
                                    <h:outputText id="stxtNewSan" styleClass="output" value="#{ATMCardMaster.newSan}"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblSecondaryTxnLimitType" styleClass="label" value="Txn Limit Type" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddSecondaryTxnLimitType" styleClass="ddlist" size="1" style="width:120px;" value="#{ATMCardMaster.secondaryTxnLimitType}">
                                    <f:selectItems value="#{ATMCardMaster.secondaryTxnLimitTypeList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblSecondaryWdrlLimitAmount" styleClass="label" value="Widrawal Limit Amount"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSecondaryWdrlLimitAmount" styleClass="input" value="#{ATMCardMaster.secondaryWdrlLimitAmount}" size="20" maxlength="5" />
                            </h:panelGrid>
                            <h:panelGrid id="seqRow10" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{ATMCardMaster.secondaryPanelDisplay}">
                                <h:outputLabel id="lblSecondaryWdrlLimitCount" styleClass="label" value="Widrawal Limit Count"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSecondaryWdrlLimitCount" styleClass="input" value="#{ATMCardMaster.secondaryWdrlLimitCount}" size="20" >
                                </h:inputText>
                                <h:outputLabel id="lblSecondaryPurLimitAmount" styleClass="label" value="Purchase Limit Amount"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSecondaryPurLimitAmount" styleClass="input" value="#{ATMCardMaster.secondaryPurLimitAmount}" size="20" maxlength="5" />
                             <h:outputLabel id="lblSecondaryPurLimitCount" styleClass="label" value="Purchase Limit count"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSecondaryPurLimitCount" styleClass="input" value="#{ATMCardMaster.secondaryPurLimitCount}" size="20">
                                    <a4j:support event="onblur" action="#{ATMCardMaster.addSecondaryAccountDetail}" reRender="message,seqRow11,seqTaskList"/>
                                </h:inputText>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow11" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{ATMCardMaster.secondaryPanelDisplay}" styleClass="row1" width="100%">
                                <a4j:region id="tblRegion">
                                    <rich:dataTable value="#{ATMCardMaster.secondaryTable}" var="item"
                                                    rowClasses="gridrow1,gridrow2" id="seqTaskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="8"><h:outputText value="Secondary Account Detail" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                                <rich:column><h:outputText value="Card No" /></rich:column>
                                                <rich:column><h:outputText value="Limit Type" /></rich:column>
                                                <rich:column><h:outputText value="Widrawal Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="widrawal Limit Count" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Count" /></rich:column>
                                                <rich:column rendered="#{ATMCardMaster.selectRender}"><h:outputText value="Delete" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.secondaryAccount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.cardNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.txnLimitType}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitCount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitCount}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px" rendered="#{ATMCardMaster.selectRender}">
                                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ATMCardMaster.setTableDataToForm}" 
                                                             reRender="message,seqRow9,txtSecondaryAccountNo,ddSecondaryTxnLimitType,
                                                             txtSecondaryLimitAmount,txtSecondaryLimitCount,seqRow11,seqTaskList,txtSecondaryWdrlLimitCount,
                                                             txtSecondaryPurLimitAmount,txtSecondaryPurLimitCount,txtSecondaryWdrlLimitAmount">
                                                <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{item}" target="#{ATMCardMaster.secondaryCurrentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="seqTaskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow12" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{ATMCardMaster.secondFileDataDisplay}">
                                <h:outputLabel id="lblPrCardStatus" styleClass="label" value="Present Card Status"/>
                                <h:outputText id="stxtPrCardStatus" styleClass="output" value="#{ATMCardMaster.presentCardStatus}"/>
                                <h:outputLabel id="lblChgCardStatus" styleClass="label" value="Status To Be Change" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddChgCardStatus" styleClass="ddlist" size="1" style="width:120px;" value="#{ATMCardMaster.cardStatus}">
                                    <f:selectItems value="#{ATMCardMaster.cardStatusList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow13" columns="4" columnClasses="col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{ATMCardMaster.fourthFileDataDisplay}">
                                <h:outputLabel id="lblPrEncodingName" styleClass="label" value="Present Encoding Name"/>
                                <h:outputText id="stxtPrEncodingName" styleClass="output" value="#{ATMCardMaster.prEncodingName}"/>
                                <h:outputLabel id="lblPrEmbossingName" styleClass="label" value="Present Embossing Name"/>
                                <h:outputText id="stxtPrEmbossingName" styleClass="output" value="#{ATMCardMaster.prEmbossingName}"/>
                            </h:panelGrid>
                            <h:panelGrid id="seqRow14" columns="4" columnClasses="col3,col3,col3,col3" styleClass="row1" width="100%" style="display:#{ATMCardMaster.fourthFileDataDisplay}">
                                <h:outputLabel id="lblChgEncodingName" styleClass="label" value="Encoding Name"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtChgEncodingName" styleClass="input" value="#{ATMCardMaster.chgEncodingName}" maxlength="25" size="20" onkeyup="this.value=this.value.toUpperCase();"/>
                                <h:outputLabel id="lblChgEmbossingName" styleClass="label" value="Embossing Name"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtChgEmbossingName" styleClass="input" value="#{ATMCardMaster.chgEmbossingName}" maxlength="25" size="20" onkeyup="this.value=this.value.toUpperCase();"/>
                            </h:panelGrid>
                         <h:panelGrid id="seqRow16" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{ATMCardMaster.fifthPanelDisplay}" styleClass="row1" width="100%">
                                <a4j:region id="tblRegionverify">
                                    <rich:dataTable value="#{ATMCardMaster.verifyTable}" var="item"
                                                    rowClasses="gridrow1,gridrow2" id="verTaskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="10"><h:outputText value="Verification Account Detail" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                                <rich:column><h:outputText value="Card No" /></rich:column>
                                                <rich:column><h:outputText value="Issue Date" /></rich:column>
                                                <rich:column><h:outputText value="Limit Type" /></rich:column>
                                                <rich:column><h:outputText value="Widrawal Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="widrawal Limit Count" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Count" /></rich:column>
                                                <rich:column><h:outputText value="minimum Limit" /></rich:column>
                                                <rich:column rendered="#{ATMCardMaster.selectRender}"><h:outputText value="Verify" /></rich:column>
                                              </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.acNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.cardNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.registrationDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.txnLimitType}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitCount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitCount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.minLmt}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px" rendered="#{ATMCardMaster.selectRender}">
                                            <a4j:commandLink ajaxSingle="true" id="selectlink"  oncomplete="if(#{ATMCardMaster.verifyPanelViewFlag == true})
                                                             {#{rich:component('verifyViewPanel')}.show();}else{#{rich:component('verifyViewPanel')}.hide();} " action="#{ATMCardMaster.getviewDetails}"
                                                             reRender="message,seqRow9,txtSecondaryAccountNo,ddSecondaryTxnLimitType,txtSecondaryWdrlLimitCount,seqRow16,
                                                             txtSecondaryPurLimitAmount,txtSecondaryPurLimitCount,txtSecondaryWdrlLimitAmount,tblRegionverify,verTaskList,verifyPanel,verifyViewPanel,
                                                             stxtAc1,stxtName1,stxtcard1,stxtissueDt1,stxtencoding1,stxtminlmt1,stxtwidlmtAmt1,stxtwidlmtcount1,stxtpurlmtAmt1,stxttxnlimitType1
                                                             stxtcardStatus1,stxtpurlmtcount1,tblview,viewTaskList,verifyViewPanel2,stxtcustid1,stxtgender1,stxtembossing1">
                                                <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{item}" target="#{ATMCardMaster.verifyCurrentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="verTaskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                        <h:panelGrid id="seqRow17" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{ATMCardMaster.sixthPanelDisplay}" styleClass="row1" width="100%">
                                <a4j:region id="tblRegiondeactive">
                                    <rich:dataTable value="#{ATMCardMaster.deactivateTable}" var="item"
                                                    rowClasses="gridrow1,gridrow2" id="deTaskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="11"><h:outputText value="Deactivation or Update Account Detail" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                                <rich:column><h:outputText value="Card No" /></rich:column>
                                                <rich:column><h:outputText value="Issue Date" /></rich:column>
                                                <rich:column><h:outputText value="Limit Type" /></rich:column>
                                                <rich:column><h:outputText value="Widrawal Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="widrawal Limit Count" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Count" /></rich:column>
                                                <rich:column><h:outputText value="minimum Limit" /></rich:column>
                                                <rich:column rendered="#{ATMCardMaster.deactiveRender}"><h:outputText value="Deactivate" /></rich:column>
                                                <rich:column rendered="#{ATMCardMaster.selectUpdateRender}"><h:outputText value="Select" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.acNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.cardNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.registrationDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.txnLimitType}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitCount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitCount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.minLmt}" /></rich:column>
                                   <rich:column style="text-align:center;width:40px" rendered="#{ATMCardMaster.deactiveRender}">
                                            <a4j:commandLink ajaxSingle="true" id="deactivatelink" oncomplete="#{rich:component('deactivatePanel')}.show();"
                                                             reRender="message,seqRow9,txtSecondaryAccountNo,ddSecondaryTxnLimitType,txtSecondaryWdrlLimitCount,seqRow16,
                                                             txtSecondaryPurLimitAmount,txtSecondaryPurLimitCount,txtSecondaryWdrlLimitAmount,tblRegionverify,verTaskList,deactivatePanel">
                                                <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{item}" target="#{ATMCardMaster.deactiveCurrentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    <rich:column style="text-align:center;width:40px" rendered="#{ATMCardMaster.selectUpdateRender}">
                                        <a4j:commandLink ajaxSingle="true" id="selectUpdatelink" action = "#{ATMCardMaster.selectDetailUpdateOpration}"
                                                             reRender="message,deactivatePanel,txtChn">
                                                <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{item}" target="#{ATMCardMaster.deactiveCurrentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="verTaskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="seqGpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="buttonProcess" value="Process" oncomplete="#{rich:component('processPanel')}.show();" reRender="seqPanel,message,processPanel"/>
                                    <a4j:commandButton id="buttonRefresh" value="Refresh" action="#{ATMCardMaster.refreshSeqForm}" reRender="seqPanel,message,tblRegionverify,seqRow16,tblRegiondeactive,seqRow17"/>
                                    <a4j:commandButton id="buttonExit" value="Exit" action="#{ATMCardMaster.exitSeqBtnAction}" reRender="seqPanel,message"/>
                                </h:panelGroup>
                            </h:panelGrid>
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
                                    <h:outputText id="confirmid" value="Are you sure to process it ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{ATMCardMaster.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="message,seqPanel,seqRow11,seqTaskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
             <rich:modalPanel id="verifyPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to verify this entry ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{ATMCardMaster.accountVerification}" 
                                                       oncomplete="#{rich:component('verifyPanel')}.hide();#{rich:component('verifyViewPanel')}.hide(); return false;" 
                                                       reRender="message,seqPanel,seqRow16,verTaskList,tblRegionverify"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('verifyPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
      <rich:modalPanel id="deactivatePanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to deactivate this entry ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{ATMCardMaster.cardDeactivation}" 
                                                       oncomplete="#{rich:component('deactivatePanel')}.hide();" 
                                                       reRender="message,seqPanel,deTaskList,seqRow17,tblRegiondeactive"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deactivatePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
                            <rich:modalPanel id="verifyViewPanel" height="350" width="900">
                    <f:facet name="header">
                        <h:outputText value="Verification detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="verifyViewPanel" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="verifyViewPanel1" width="100%">
                        <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow1" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblAc1" styleClass="label"  value="Ac No: " />
                                    <h:outputText id="stxtAc1" styleClass="output" value="#{ATMCardMaster.acno1}"/>
                                    <h:outputLabel id="lblName1" styleClass="label"  value="Customer Name:" />
                                    <h:outputText id="stxtName1" styleClass="output" value="#{ATMCardMaster.name1}"/>
                                    <h:outputLabel id="lblcard" styleClass="label"  value="Card No:" />
                                    <h:outputText id="stxtcard1" styleClass="output" value="#{ATMCardMaster.card1}"/>
                                </h:panelGrid>
                         <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow2" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblcustid1" styleClass="label"  value="Customer Id: " />
                                    <h:outputText id="stxtcustid1" styleClass="output" value="#{ATMCardMaster.custid1}"/>
                                    <h:outputLabel id="lblgender1" styleClass="label"  value="Gender:" />
                                    <h:outputText id="stxtgender1" styleClass="output" value="#{ATMCardMaster.gender1}"/>
                                    <h:outputLabel id="lblembossing1" styleClass="label"  value="Embossing Name:" />
                                    <h:outputText id="stxtembossing1" styleClass="output" value="#{ATMCardMaster.embossing1}"/>
                                </h:panelGrid>
                        <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow3" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblissueDt1" styleClass="label"  value="Issue Date: " />
                                    <h:outputText id="stxtissueDt1" styleClass="output" value="#{ATMCardMaster.date1}"/>
                                    <h:outputLabel id="lblencoding1" styleClass="label"  value="Encoding Name:" />
                                    <h:outputText id="stxtencoding1" styleClass="output" value="#{ATMCardMaster.encoding1}"/>
                                    <h:outputLabel id="lblminlmt1" styleClass="label"  value="Min Limit:" />
                                    <h:outputText id="stxtminlmt1" styleClass="output" value="#{ATMCardMaster.minlmt1}"/>
                                </h:panelGrid>
                        <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow4" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblwidlmtAmt1" styleClass="label"  value="Widrawal Limit Amount: " />
                                    <h:outputText id="stxtwidlmtAmt1" styleClass="output" value="#{ATMCardMaster.widlmtamt1}"/>
                                    <h:outputLabel id="lblwidlmtcount1" styleClass="label"  value="Widrawal Count Limit:" />
                                    <h:outputText id="stxtwidlmtcount1" styleClass="output" value="#{ATMCardMaster.widlmtcount1}"/>
                                    <h:outputLabel id="lblpurlmtcount1" styleClass="label"  value="purchase Limit Count:" />
                                    <h:outputText id="stxtpurlmtcount1" styleClass="output" value="#{ATMCardMaster.purlmtcount1}"/>
                                </h:panelGrid>
                        <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow5" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblpurlmtAmt1" styleClass="label"  value="Purchase Limit Amount: " />
                                    <h:outputText id="stxtpurlmtAmt1" styleClass="output" value="#{ATMCardMaster.purlmtamt1}"/>
                                    <h:outputLabel id="lblcardStatus1" styleClass="label"  value="Card Status:" />
                                    <h:outputText id="stxtcardStatus1" styleClass="output" value="#{ATMCardMaster.cardStatus1}"/>
                                    <h:outputLabel id="lbltxnlimitType1" styleClass="label"  value="Transaction Limit Type:" />
                                    <h:outputText id="stxttxnlimitType1" styleClass="output" value="#{ATMCardMaster.txnLimitType1}"/>
                                </h:panelGrid>
                        </h:panelGrid>
                    <h:panelGrid id="verifyViewPanel2" width="100%">
                    <f:facet name="header">
                        <h:outputText value="Secondry Account Detail "/>
                    </f:facet>
                    <a4j:region id="tblview">
                                    <rich:dataTable value="#{ATMCardMaster.secondaryViewTable}" var="item"
                                                    rowClasses="gridrow1,gridrow2" id="viewTaskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="7"><h:outputText value="Secondary Account Detail" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                                <rich:column><h:outputText value="Card No" /></rich:column>
                                                <rich:column><h:outputText value="Limit Type" /></rich:column>
                                                <rich:column><h:outputText value="Widrawal Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="widrawal Limit Count" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Amount" /></rich:column>
                                                <rich:column><h:outputText value="purchase Limit Count" /></rich:column>
                                             </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.secondaryAccount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.cardNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.txnLimitType}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.withdrawalLimitCount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitAmount}" /></rich:column>
                                        <rich:column><h:outputText value="#{item.purchaseLimitCount}" /></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="viewTaskList" maxPages="20"/>
                                </a4j:region>
                     </h:panelGrid>
                        <h:panelGrid id="verifyViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="vrifyViewBtnPanel">
                                <a4j:commandButton id="vrifyViewbtn" value="Verify" onclick="#{rich:component('verifyPanel')}.show();"/>
                                <a4j:commandButton id="vrifyViewClose" value="Close" onclick="#{rich:component('verifyViewPanel')}.hide(); return false;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
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