<%-- 
    Document   : mmsverification
    Created on : 29 Feb, 2016, 4:15:24 PM
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
            <title>Mandate Verification</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{mmsVerifierBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Mandate Verification"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{mmsVerifierBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="image" columns="2" columnClasses="col11,col10" width="100%" style="height:270px;vertical-align:top;" styleClass="row1">
                        <h:panelGrid id="imageGrid" columns="1" style="width:100%;text-align:left;" columnClasses="vtop">
                            <h:graphicImage id="chqImage" value="/mms/dynamic/?file=#{mmsVerifierBean.imageName}&fileNo=#{mmsVerifierBean.seqNo}&mandate=#{mmsVerifierBean.mmsType}&fileUpDt=#{mmsVerifierBean.fileUpDt}&mmsMode=#{mmsVerifierBean.mmsMode}" height="270" width="572"/>
                        </h:panelGrid>
                        <h:panelGrid id="sigGrid" columns="1" style="width:100%;text-align:right;" columnClasses="vtop">
                            <a4j:mediaOutput id="signature" style="width:400px;height:270px;"element="img" createContent="#{mmsVerifierBean.createSignature}" value="#{mmsVerifierBean.acno}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblMmsMode" styleClass="label" value="Mandate Mode"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMmsMode" styleClass="ddlist" size="1" style="width:80px" value="#{mmsVerifierBean.mmsMode}">
                            <f:selectItems value="#{mmsVerifierBean.mmsModeList}"/>
                            <a4j:support event="onblur" action="#{mmsVerifierBean.modeTypeOption}" reRender="ddMmsType" oncomplete="setMask();" focus="ddMmsType"/>
                        </h:selectOneListbox>

                        <h:outputLabel id="lblMmsType" styleClass="label" value="Mandate Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMmsType" styleClass="ddlist" size="1" style="width:80px" value="#{mmsVerifierBean.mmsType}">
                            <f:selectItems value="#{mmsVerifierBean.mmsTypeList}"/>
                            <a4j:support event="onblur" action="#{mmsVerifierBean.getAllReason}" reRender="stxtMsg,ddReason" oncomplete="setMask();" focus="txtFileUpDt"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFileUpDt" styleClass="label" value="File Upload Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFileUpDt" styleClass="input issuedt" style="width:70px" value="#{mmsVerifierBean.fileUpDt}">
                            <a4j:support event="onblur" action="#{mmsVerifierBean.fileUpDtProcess}" reRender="stxtMsg,ddSeqNo" oncomplete="setMask();" focus="ddSeqNo"/>
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="File No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSeqNo" styleClass="ddlist" size="1" style="width:80px" value="#{mmsVerifierBean.seqNo}">
                            <f:selectItems value="#{mmsVerifierBean.seqNoList}"/>
                            <a4j:support event="onblur" action="#{mmsVerifierBean.retrieveAllMandate}" reRender="stxtMsg,tablePanel,taskList" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblUmrn" styleClass="label" value="UMRN"></h:outputLabel>
                        <h:outputText id="stxtUmrn" styleClass="output" value="#{mmsVerifierBean.umrn}"/>
                        <h:outputLabel id="lblAcno" styleClass="label" value="A/c No"></h:outputLabel>
                        <h:panelGroup id="pId">
                            <h:inputText id="txtAcno" styleClass="input" value="#{mmsVerifierBean.acno}" maxlength="#{mmsVerifierBean.acNoMaxLen}" size="15">
                                <a4j:support event="onblur" action="#{mmsVerifierBean.retrieveAcnoDetail}" reRender="stxtMsg,stxtName,lblNewAcno,signature,stxtOperMode" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel id="lblNewAcno" styleClass="label" value="#{mmsVerifierBean.newAcno}" style="color:green"></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblName" styleClass="label" value="Name"></h:outputLabel>
                        <h:outputText id="stxtName" styleClass="output" value="#{mmsVerifierBean.name}"/>
                        <h:outputLabel id="label8" styleClass="label" value="Operation Mode"/>
                        <h:outputText id="stxtOperMode" styleClass="output" value="#{mmsVerifierBean.opMode}"/>
                        <h:outputLabel id="lblCategory" styleClass="label" value="Mandate Category"></h:outputLabel>
                        <h:outputText id="stxtCategory" styleClass="output" value="#{mmsVerifierBean.category}"/>
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" style="width:80px" value="#{mmsVerifierBean.status}">
                            <f:selectItems value="#{mmsVerifierBean.statusList}"/>
                            <a4j:support event="onblur" action="#{mmsVerifierBean.makeReasonEnable}" reRender="stxtMsg,ddReason"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblReason" styleClass="label" value="Reason"/>
                        <h:selectOneListbox id="ddReason" styleClass="ddlist" size="1" style="width:150px" 
                                            value="#{mmsVerifierBean.reason}" disabled="#{mmsVerifierBean.reasonDisable}">
                            <f:selectItems value="#{mmsVerifierBean.reasonList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>    
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{mmsVerifierBean.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="2" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="16"><h:outputText value="Mandate Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="UMRN" /></rich:column>
                                        <rich:column><h:outputText value="A/c Type" /></rich:column>
                                        <rich:column><h:outputText value="A/c No" /></rich:column>
                                        <rich:column><h:outputText value="A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="Frequency" /></rich:column>
                                        <rich:column><h:outputText value="First Collection Date" /></rich:column>
                                        <rich:column><h:outputText value="Final Collection Date" /></rich:column>
                                        <rich:column><h:outputText value="Collection Amount" /></rich:column>
                                        <rich:column><h:outputText value="Maximum Amount" /></rich:column>
                                        <rich:column><h:outputText value="Debtor IFSC/MICR/IIN" /></rich:column>
                                        <rich:column><h:outputText value="Debtor Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Creditor Name" /></rich:column>
                                        <rich:column><h:outputText value="Mandate Status" /></rich:column>
                                        <rich:column><h:outputText value="Accept Status" /></rich:column>
                                        <rich:column><h:outputText value="Reject Reason" /></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.mndtId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAcctTpPrtry}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAcctIdOthrId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFrqcy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFrstColltnDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFnlColltnDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.colltnAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.maxAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAgtFinInstnIdClrSysMmbIdMmbId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAgtFinInstnIdNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cdtrNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mandateStatus}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accept}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rejectCode}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{mmsVerifierBean.setTableDataToForm}" reRender="stxtMsg,stxtUmrn,txtAcno,lblNewAcno,stxtName,image,imageGrid,chqImage,sigGrid,signature,stxtCategory,stxtOperMode" focus="txtAcno">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{mmsVerifierBean.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" columnClasses="col19,col20,col21" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel1" styleClass="vtop">
                            <h:outputText id="stxtInstruction1" styleClass="output" value="F8-Gray Image, F9-White Image, F4-Prev. Mandate Detail, Ctrl+J- Joint Detail" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="Verify" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel,stxtOperMode"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{mmsVerifierBean.refreshForm}" reRender="mainPanel,jtHolder" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{mmsVerifierBean.exitForm}" reRender="mainPanel,jtHolder"/>
                        </h:panelGroup>
                        <h:panelGroup id="btnPanel2" styleClass="vtop">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{mmsVerifierBean.errMessage}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

                <a4j:jsFunction name="showWhilteImage" action="#{mmsVerifierBean.getWhiteImage}" reRender="chqImage" />
                <a4j:jsFunction name="showGrayImage" action="#{mmsVerifierBean.getGrayImage}" reRender="chqImage" />
                <a4j:jsFunction name="showPrevMandateDetail" oncomplete="if(#{mmsVerifierBean.mandateViewFlag == true})
                                {#{rich:component('mandateView')}.show();}else{#{rich:component('mandateView')}.hide();} " 
                                reRender="mandateView" action="#{mmsVerifierBean.getPrevMandateDetail}"/>
                <a4j:jsFunction name="jtDetails"  action="#{mmsVerifierBean.jtDetails}"
                                oncomplete="if(#{mmsVerifierBean.jtDetailPopUp==true}){#{rich:component('jtHolder')}.show();}else{#{rich:component('jtHolder')}.hide();}" reRender="jtHolder,stxtMsg" />

                <rich:hotKey key="F4" handler="showPrevMandateDetail();"/>
                <rich:hotKey key="F8" handler="showGrayImage();"/>
                <rich:hotKey key="F9" handler="showWhilteImage();"/>
                <rich:hotKey key="Ctrl+J" handler="jtDetails();"/>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
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
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{mmsVerifierBean.processAction}" 
                                                           onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMsg,tablePanel,taskList,stxtUmrn,txtAcno,lblNewAcno,stxtName,
                                                           image,imageGrid,chqImage,sigGrid,signature,stxtCategory,stxtOperMode" 
                                                           oncomplete="setMask()"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>

                <rich:modalPanel id="mandateView" height="560" width="900" left="true">
                    <f:facet name="header">
                        <h:outputText value="Previous Mandate Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="mandateView" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="mandateViewPanel" width="100%">
                            <h:panelGrid id="mandateTableGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                                <rich:dataTable  var="unAuthItem" value="#{mmsVerifierBean.preMandateList}"
                                                 rowClasses="gridrow1, gridrow2" id="mandateTable" rows="5"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="34"><h:outputText value="Previous Mandate Detail" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Msg Id" /></rich:column>
                                            <rich:column><h:outputText value="Creation Time" /></rich:column>
                                            <rich:column><h:outputText value="Initiating Id" /></rich:column>
                                            <rich:column><h:outputText value="Instructing Member Id" /></rich:column>
                                            <rich:column><h:outputText value="Instructing Member Bank Name" /></rich:column>
                                            <rich:column><h:outputText value="Instructed Member Id" /></rich:column>
                                            <rich:column><h:outputText value="Instructed Member Bank Name" /></rich:column>
                                            <rich:column><h:outputText value="Authorized" /></rich:column>
                                            <rich:column><h:outputText value="UMRN" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Manadte Req. Id" /></rich:column>
                                            <rich:column><h:outputText value="Category" /></rich:column>
                                            <rich:column><h:outputText value="Proprietary" /></rich:column>
                                            <rich:column><h:outputText value="SeqTp" /></rich:column>
                                            <rich:column><h:outputText value="Frqcy" /></rich:column>
                                            <rich:column><h:outputText value="FrstColltnDt" /></rich:column>
                                            <rich:column><h:outputText value="FnlColltnDt" /></rich:column>
                                            <rich:column><h:outputText value="Collection Amount" /></rich:column>
                                            <rich:column><h:outputText value="Maximum Amount" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Creditor Name" /></rich:column>
                                            <rich:column><h:outputText value="Creditor A/c Id" /></rich:column>
                                            <rich:column><h:outputText value="Creditor Agent Member Id" /></rich:column>
                                            <rich:column><h:outputText value="Creditor Agent Member Bank" /></rich:column>
                                            <rich:column><h:outputText value="Debtor Name" /></rich:column>
                                            <rich:column><h:outputText value="Consumer Ref No" /></rich:column>
                                            <rich:column><h:outputText value="Scheme Ref No" /></rich:column>
                                            <rich:column><h:outputText value="Phone" /></rich:column>
                                            <rich:column><h:outputText value="Mobile" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Email" /></rich:column>
                                            <rich:column><h:outputText value="Debtor Account Number" /></rich:column>
                                            <rich:column><h:outputText value="Debtor Account Type" /></rich:column>
                                            <rich:column><h:outputText value="Debtor IFSC/MICR/IIN" /></rich:column>
                                            <rich:column><h:outputText value="Debtor Bank Name" /></rich:column>
                                            <rich:column><h:outputText value="Mandate Type" /></rich:column>
                                            <rich:column><h:outputText value="Update By" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{unAuthItem.msgId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.creDtTm}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.initgPtyIdOrgIdOthrId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.instgAgtFinInstnIdClrSysMmbIdMmbId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.instgAgtFinInstnIdNm}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.instdAgtFinInstnIdClrSysMmbIdMmbId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.instdAgtFinInstnIdNm}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.mndtId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.mndtReqId}"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="#{unAuthItem.tpSvcLvlPrtry}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.tpLclInstrmPrtry}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.ocrncsSeqTp}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.ocrncsFrqcy}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.ocrncsFrstColltnDt}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.ocrncsFnlColltnDt}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.colltnAmt}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.maxAmt}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.cdtrNm}"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="#{unAuthItem.cdtrAcctIdOthrId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.cdtrAgtFinInstnIdClrSysMmbIdMmbId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.cdtrAgtFinInstnIdNm}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrNm}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrIdPrvtIdOthrId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrIdPrvtIdOthrSchmeNmPrtry}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrCtctDtlsPhneNb}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrCtctDtlsMobNb}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrCtctDtlsEmailAdr}"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="#{unAuthItem.dbtrCtctDtlsOthr}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrAcctIdOthrId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrAcctTpPrtry}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrAgtFinInstnIdClrSysMmbIdMmbId}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.dbtrAgtFinInstnIdNm}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.mandateType}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.updateBy}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="mandateTable" maxPages="20" />
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="mandateViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="mandateViewBtnPanel">
                                <a4j:commandButton id="mandateViewClose" value="Close" onclick="#{rich:component('mandateView')}.hide(); return false;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>

                <rich:modalPanel id="jtHolder" top="true" height="600" width="1000">
                    <h:form>
                        <h:panelGrid bgcolor="#fff" columns="1" id="outerPanelOtherAc" style="border:1px ridge #BED6F8" width="100%" >
                            <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                                <h:panelGroup id="groupPanel2" layout="block">
                                    <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                    <h:outputText id="stxtDate" styleClass="output" value="#{mmsVerifierBean.todayDate}"/>
                                </h:panelGroup>
                                <h:outputLabel id="label2" styleClass="headerLabel" value="Joint Holder Details"/>
                                <h:panelGroup id="groupPanel3" layout="block">
                                    <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                    <h:outputText id="stxtUser" styleClass="output" value="#{mmsVerifierBean.userName}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columns="4"  id="jtRow1Msg" style="width:100%;text-align:center;color:red"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblJtMsg" styleClass="label"  value="#{mmsVerifierBean.jtMsg}" />
                            </h:panelGrid>
                            <rich:panel header="Ist Joint Holder Detail">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow1" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolder1" styleClass="label"  value="Name: " />
                                    <h:outputText id="stxtJtHolder1" styleClass="output" value="#{mmsVerifierBean.jtName1}"/>
                                    <h:outputLabel id="lblDOB1" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB1" styleClass="output" value="#{mmsVerifierBean.dob1}"/>
                                    <h:outputLabel id="lblPANNo1" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo1" styleClass="output" value="#{mmsVerifierBean.panNo1}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow2" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName1" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName1" styleClass="output" value="#{mmsVerifierBean.fatherName1}"/>
                                    <h:outputLabel id="lblOccupation1" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation1" styleClass="output" value="#{mmsVerifierBean.occupation1}"/>
                                    <h:outputLabel id="lblAddress1" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress1" styleClass="output" value="#{mmsVerifierBean.address1}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Joint Holder Two Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow3" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolderName2" styleClass="label"  value="Name:" />
                                    <h:outputText id="stxtJtHolderName2" styleClass="output" value="#{mmsVerifierBean.jtName2}"/>
                                    <h:outputLabel id="lblDOB2" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB2" styleClass="output" value="#{mmsVerifierBean.dob2}"/>
                                    <h:outputLabel id="lblPANNo2" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo2" styleClass="output" value="#{mmsVerifierBean.panNo2}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow4" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName2" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName2" styleClass="output" value="#{mmsVerifierBean.fatherName2}"/>
                                    <h:outputLabel id="lblOccupation2" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation2" styleClass="output" value="#{mmsVerifierBean.occupation2}"/>
                                    <h:outputLabel id="lblAddress2" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress2" styleClass="output" value="#{mmsVerifierBean.address2}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Joint Holder Three Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow5" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolder3" styleClass="label"  value="Name:" />
                                    <h:outputText id="stxtJtHolder3" styleClass="output" value="#{mmsVerifierBean.jtName3}"/>
                                    <h:outputLabel id="lblDOB3" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB3" styleClass="output" value="#{mmsVerifierBean.dob3}"/>
                                    <h:outputLabel id="lblPANNo3" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo3" styleClass="output" value="#{mmsVerifierBean.panNo3}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow6" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName3" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName3" styleClass="output" value="#{mmsVerifierBean.fatherName3}"/>
                                    <h:outputLabel id="lblOccupation3" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation3" styleClass="output" value="#{mmsVerifierBean.occupation3}"/>
                                    <h:outputLabel id="lblAddress3" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress3" styleClass="output" value="#{mmsVerifierBean.address3}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Joint Holder Four Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow7" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolderName4" styleClass="label"  value="Name:" />
                                    <h:outputText id="stxtJtHolderName4" styleClass="output" value="#{mmsVerifierBean.jtName4}"/>
                                    <h:outputLabel id="lblDOB4" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB4" styleClass="output" value="#{mmsVerifierBean.dob4}"/>
                                    <h:outputLabel id="lblPANNo4" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo4" styleClass="output" value="#{mmsVerifierBean.panNo4}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow8" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName4" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName4" styleClass="output" value="#{mmsVerifierBean.fatherName4}"/>
                                    <h:outputLabel id="lblOccupation4" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation4" styleClass="output" value="#{mmsVerifierBean.occupation4}"/>
                                    <h:outputLabel id="lblAddress4" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress4" styleClass="output" value="#{mmsVerifierBean.address4}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Nominee Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow9" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblNomineeName" styleClass="label"  value="Nominee Name:" />
                                    <h:outputText id="stxtNomineeName" styleClass="output" value="#{mmsVerifierBean.nomineeName}"/>
                                    <h:outputLabel id="lblAddress" styleClass="label"  value="Nominee Address:" />
                                    <h:outputText id="stxtAddress" styleClass="output" value="#{mmsVerifierBean.nomineeAddress}" />
                                    <h:outputLabel id="lblRelation" styleClass="label"  value="Nominee Relation:" />
                                    <h:outputText id="stxtRelation" styleClass="output" value="#{mmsVerifierBean.nomineeRelation}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <a4j:commandButton id="btnExit" value="Exit" oncomplete="#{rich:component('jtHolder')}.hide();"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:form>
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
