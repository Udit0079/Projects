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
                            <h:outputText id="stxtDate" styleClass="output" value="#{MandateReverification.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Mandate ReVerification"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MandateReverification.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblMmsMode" styleClass="label" value="Mandate Mode"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMmsMode" styleClass="ddlist" size="1" style="width:80px" value="#{MandateReverification.mmsMode}">
                            <f:selectItems value="#{MandateReverification.mmsModeList}"/>
                            <a4j:support event="onblur" action="#{MandateReverification.modeTypeOption}" reRender="ddMmsType" oncomplete="setMask();" focus="ddMmsType"/>
                        </h:selectOneListbox>

                        <h:outputLabel id="lblMmsType" styleClass="label" value="Mandate Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMmsType" styleClass="ddlist" size="1" style="width:80px" value="#{MandateReverification.mmsType}">
                            <f:selectItems value="#{MandateReverification.mmsTypeList}"/>
                        </h:selectOneListbox>
                     </h:panelGrid>
                        <h:panelGrid id="gridPanelumrn" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                          <h:outputLabel id="lblchiUmrn" styleClass="label" value="Chi Umrn"></h:outputLabel>
                           <h:inputText id="txtchiUmrn" styleClass="input" value="#{MandateReverification.chiUmrn}" maxlength="20" size="24">
                                <a4j:support event="onblur" action="#{MandateReverification.getgridDetails}" reRender="stxtMsg,ddReason,tablePanel,taskList" oncomplete="setMask();"/>
                            </h:inputText>
                          <h:outputLabel></h:outputLabel>
                          <h:outputText></h:outputText>
                        </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblUmrn" styleClass="label" value="UMRN"></h:outputLabel>
                        <h:outputText id="stxtUmrn" styleClass="output" value="#{MandateReverification.umrn}"/>
                        <h:outputLabel id="lblAcno" styleClass="label" value="A/c No"></h:outputLabel>
                        <h:panelGroup id="pId">
                            <h:inputText id="txtAcno" styleClass="input" value="#{MandateReverification.acno}" maxlength="#{MandateReverification.acNoMaxLen}" size="15">
                                <a4j:support event="onblur" action="#{MandateReverification.retrieveAcnoDetail}" reRender="stxtMsg,stxtName,lblNewAcno,signature,stxtOperMode" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel id="lblNewAcno" styleClass="label" value="#{MandateReverification.newAcno}" style="color:green"></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblName" styleClass="label" value="Name"></h:outputLabel>
                        <h:outputText id="stxtName" styleClass="output" value="#{MandateReverification.name}"/>
                        <h:outputLabel id="label8" styleClass="label" value="Operation Mode"/>
                        <h:outputText id="stxtOperMode" styleClass="output" value="#{MandateReverification.opMode}"/>
                        <h:outputLabel id="lblCategory" styleClass="label" value="Mandate Category"></h:outputLabel>
                        <h:outputText id="stxtCategory" styleClass="output" value="#{MandateReverification.category}"/>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" style="width:80px" value="#{MandateReverification.status}">
                            <f:selectItems value="#{MandateReverification.statusList}"/>
                            <a4j:support event="onblur" action="#{MandateReverification.makeReasonEnable}" reRender="stxtMsg,ddReason"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblReason" styleClass="label" value="Reason"/>
                        <h:selectOneListbox id="ddReason" styleClass="ddlist" size="1" style="width:150px" 
                                            value="#{MandateReverification.reason}"  disabled="#{MandateReverification.reasonDisable}">
                            <f:selectItems value="#{MandateReverification.reasonList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>    
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{MandateReverification.gridDetail}" var="dataItem" 
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
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{MandateReverification.setTableDataToForm}" reRender="stxtMsg,stxtUmrn,txtAcno,lblNewAcno,stxtName,stxtCategory,stxtOperMode" focus="txtAcno">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{MandateReverification.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" columnClasses="col19,col20,col21" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel1" styleClass="vtop">
                         </h:panelGroup>
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="Verify" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel,stxtOperMode"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{MandateReverification.refreshForm}" reRender="mainPanel,jtHolder" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{MandateReverification.exitForm}" reRender="mainPanel,jtHolder"/>
                        </h:panelGroup>
                        <h:panelGroup id="btnPanel2" styleClass="vtop">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{MandateReverification.errMessage}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
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
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{MandateReverification.processAction}" 
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
