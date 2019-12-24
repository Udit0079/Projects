<%-- 
    Document   : acmandate
    Created on : 16 Jan, 2016, 11:10:55 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>MANDATE / ECS FEEDING</title>
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
            <h:form id="form1" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{accountMandate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="MANDATE / ECS FEEDING"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{accountMandate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{accountMandate.errMessage}"/>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel3A" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTxnFileType" styleClass="label" value="TXN File Type" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddTxnFileType" styleClass="ddlist" size="1"  value="#{accountMandate.txnFileType}" style="width:100px;">
                            <f:selectItems value="#{accountMandate.txnFileTypeList}"/>
                            <a4j:support event="onblur" action="#{accountMandate.onblurTxnFileType()}" reRender="gridPanel3,tablePanel,footerPanel,gridPanel62"
                                         oncomplete="setMask();#{rich:element('ddFunction')}.focus();"/>
                        </h:selectOneListbox>
                        <h:outputLabel />
                        <h:outputLabel />
                        <h:outputLabel />
                        <h:outputLabel />
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="TXN Type" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1"  value="#{accountMandate.function}" style="width:100px;">
                            <f:selectItems value="#{accountMandate.functionList}"/>
                            <a4j:support event="onblur" action="#{accountMandate.onblurTxnType()}" reRender="gridPanel3,tablePanel,footerPanel"
                                         oncomplete="setMask();if(#{accountMandate.function=='EDIT'}){#{rich:element('ddmodfyCriteria')}.focus();}else{
                                         #{rich:element('ddProprietary')}.focus();}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblmodfyCriteria" styleClass="label" value="Modify Criteria" style="display:#{accountMandate.displayOnCreate};"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddmodfyCriteria" styleClass="ddlist" size="1" value="#{accountMandate.modifyCrit}" style="width:140px;display:#{accountMandate.displayOnCreate};">
                            <f:selectItems value="#{accountMandate.modifyCritList}"/>
                            <a4j:support event="onblur" focus="txtmdfyAcno" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblModfyAcno" styleClass="label" value="A/C Number" style="display:#{accountMandate.displayOnCreate};"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtmdfyAcno" styleClass="input" value="#{accountMandate.modifyAcno}" maxlength="35"  style="display:#{accountMandate.displayOnCreate};">
                            <a4j:support event="onblur" action="#{accountMandate.getDataToModify}" reRender="stxtMsg,tablePanel,taskList" />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel33" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblProprietary" styleClass="label" value="Proprietary"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddProprietary" styleClass="ddlist" size="1" style="width:80px" value="#{accountMandate.proprietary}">
                            <f:selectItems value="#{accountMandate.proprietaryList}"/>
                            <a4j:support event="onblur" action="#{accountMandate.onblurProprietary()}" reRender="craditorDetail,debitorDetail"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCategory" styleClass="label" value="Category"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCategory" styleClass="ddlist" size="1" style="width:180px" value="#{accountMandate.category}">
                            <f:selectItems value="#{accountMandate.categoryList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAmtType" styleClass="label" value="Amount Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAmtType" styleClass="ddlist" size="1" style="width:140px" value="#{accountMandate.amtType}">
                            <f:selectItems value="#{accountMandate.amtTypeList}"/>
                            <a4j:support event="onblur" />
                        </h:selectOneListbox>
                    </h:panelGrid>    
                    <h:panelGrid id="gridPanel44" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAmt" styleClass="label" value="Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtAmt" styleClass="input" style="width:100px" value="#{accountMandate.amt}" 
                                     onkeyup="this.value=this.value.toUpperCase();" maxlength="27">
                            <a4j:support event="onblur" />
                        </h:inputText>
                        <h:outputLabel id="lblFreq" styleClass="label" value="Frequency"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFreq" styleClass="ddlist" size="1" style="width:180px" value="#{accountMandate.frequency}">
                            <f:selectItems value="#{accountMandate.frequencyList}"/>
                            <a4j:support event="onblur" />
                        </h:selectOneListbox>                        
                        <h:outputLabel id="lblSequenceType" styleClass="label" value="Sequence Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSequenceType" styleClass="ddlist" size="1" style="width:140px" value="#{accountMandate.sequenceType}">
                            <f:selectItems value="#{accountMandate.sequenceTypeList}"/>
                            <a4j:support event="onblur" />
                        </h:selectOneListbox>    
                    </h:panelGrid>  
                    <h:panelGrid id="gridPanel56" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblPeriodType" styleClass="label" value="Period Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPeriodType" styleClass="ddlist" size="1" style="width:150px" value="#{accountMandate.period}">
                            <f:selectItems value="#{accountMandate.periodList}"/>
                            <a4j:support event="onblur" action="#{accountMandate.onblurPeriodType()}" reRender="txtromDt,txtTodt" oncomplete="setMask();" focus="txtfromDt"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFromDt" styleClass="label" value="From Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtfromDt" styleClass="input issuedt" style="width:100px" value="#{accountMandate.frDt}" maxlength="10">
                            <a4j:support event="onblur" />
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="txtTodt" styleClass="input issuedt" style="width:100px" value="#{accountMandate.toDt}"
                                     disabled="#{accountMandate.disableToDate}">
                            <a4j:support event="onblur" />
                        </h:inputText>                    
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel57" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddStatusType" styleClass="ddlist" size="1" style="width:80px" value="#{accountMandate.statusType}">
                            <f:selectItems value="#{accountMandate.statusTypeList}"/>
                            <a4j:support event="onblur" />
                        </h:selectOneListbox>

                        <h:outputLabel id="lblRef1" styleClass="label" value="REFERENCE 1"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRef1" styleClass="input" style="width:180px" value="#{accountMandate.ref1}" maxlength="100" 
                                     onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur" />
                        </h:inputText>
                        <h:outputLabel id="lblRef2" styleClass="label" value="REFERENCE 2"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRef2" styleClass="input" style="width:180px" value="#{accountMandate.ref2}" 
                                     onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur" />
                        </h:inputText>                                            

                    </h:panelGrid>
                    <rich:panel header="#{accountMandate.crHeader}" id="craditorDetail" style="text-align:left;">
                        <h:panelGrid id="gridPanel58" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblCrAcType" styleClass="label" value="A/C Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCrAcType" styleClass="ddlist" size="1" style="width:80px" value="#{accountMandate.crAcType}">
                                <f:selectItems value="#{accountMandate.crAcTypeList}"/>
                                <a4j:support event="onblur" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCrAcNo" styleClass="label" value="A/C No."><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCrAcNo" styleClass="input" style="width:200px" value="#{accountMandate.crAcNo}" maxlength="35">
                                <a4j:support event="onblur" action="#{accountMandate.onblurAcNo()}" reRender="stxtMsg,craditorDetail" focus="txtCrPartyName"/>
                            </h:inputText>
                            <h:outputLabel id="lblCrPartyName" styleClass="label" value="Party Name"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCrPartyName" styleClass="input" style="width:170px" value="#{accountMandate.crAcHolderName}"
                                         maxlength="40" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" />
                            </h:inputText>                                           
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel59" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblCrBankName" styleClass="label" value="Bank Name"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCrBankName" styleClass="input" style="width:200px" value="#{accountMandate.crBankName}"
                                         disabled="#{accountMandate.disableOnCradit}" maxlength="40" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <h:outputLabel id="lblCrIfscCode" styleClass="label" value="IFSC/MICR"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddFCodeType1" styleClass="ddlist" size="1" style="width:80px" value="#{accountMandate.crFinTranCodeType}"
                                                    disabled="#{accountMandate.disableOnCradit}" >
                                    <f:selectItems value="#{accountMandate.finTranCodeTypeList}"/>
                                </h:selectOneListbox>

                                <h:inputText id="txtCrIfscCode" styleClass="input" style="width:100px" value="#{accountMandate.crFinTranCode}"
                                             disabled="#{accountMandate.disableOnCradit}" maxlength="11" onkeyup="this.value=this.value.toUpperCase();">
                                    <a4j:support event="onblur" />
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblCrUtilcode" styleClass="label" value="Utility Code"/>
                            <h:inputText id="txtCrUtilcode" styleClass="input utilitycode" style="width:100px" value="#{accountMandate.crUtilityCode}"
                                         maxlength="18">
                                <a4j:support event="onblur" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel59A" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblCrMob" styleClass="label" value="Mobile"/>
                            <h:inputText id="txtCrMob" styleClass="input" style="width:100px" value="#{accountMandate.crMob}" maxlength="10">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <h:outputLabel id="lblCrEmail" styleClass="label" value="Email"/>
                            <h:inputText id="txtCrEmail" styleClass="input" style="width:200px" value="#{accountMandate.crEmail}">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </rich:panel>

                    <rich:panel header="#{accountMandate.drHeader}" id="debitorDetail" style="text-align:left;">
                        <h:panelGrid id="gridPanel60" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblDrAcType" styleClass="label" value="A/C Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDrAcType" styleClass="ddlist" size="1" style="width:80px" value="#{accountMandate.drAcType}">
                                <f:selectItems value="#{accountMandate.drAcTypeList}"/>
                                <a4j:support event="onblur" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblDrAcNo" styleClass="label" value="A/C No."><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtDrAcNo" styleClass="input" style="width:200px" value="#{accountMandate.drAcNo}" maxlength="35">
                                <a4j:support event="onblur" action="#{accountMandate.onblurAcNo()}" reRender="stxtMsg,debitorDetail" focus="txtDrPartyName"/>
                            </h:inputText>
                            <h:outputLabel id="lblDrPartyName" styleClass="label" value="Party Name"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtDrPartyName" styleClass="input" style="width:170px" value="#{accountMandate.drAcHolderName}"
                                         maxlength="40" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel61" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblDrBankName" styleClass="label" value="Bank Name"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtDrBankName" styleClass="input" style="width:200px" value="#{accountMandate.drBankName}"
                                         disabled="#{accountMandate.disableOnDebit}" maxlength="40" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <h:outputLabel id="lblDrIfsc" styleClass="label" value="IFSC/MICR"><font class="required" color="red">*</font></h:outputLabel>

                            <h:panelGroup>
                                <h:selectOneListbox id="ddFCodeType2" styleClass="ddlist" size="1" style="width:80px" value="#{accountMandate.drFinTranCodeType}"
                                                    disabled="#{accountMandate.disableOnDebit}" >
                                    <f:selectItems value="#{accountMandate.finTranCodeTypeList}"/>
                                </h:selectOneListbox>

                                <h:inputText id="txtDrIfsc" styleClass="input" style="width:100px" value="#{accountMandate.drFinTranCode}" 
                                             maxlength="11" disabled="#{accountMandate.disableOnDebit}" onkeyup="this.value=this.value.toUpperCase();">
                                    <a4j:support event="onblur" />
                                </h:inputText>
                            </h:panelGroup>



                            <h:outputLabel id="lblDrUtilcode" styleClass="label" value="Utility Code"/>
                            <h:inputText id="txtDrUtilcode" styleClass="input utilitycode" style="width:100px" 
                                         value="#{accountMandate.drUtilityCode}" maxlength="18">
                                <a4j:support event="onblur" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel61A" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblDrMob" styleClass="label" value="Mobile"/>
                            <h:inputText id="txtDrMob" styleClass="input" style="width:100px" value="#{accountMandate.drMob}" maxlength="10">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <h:outputLabel id="lblDrEmail" styleClass="label" value="Email"/>
                            <h:inputText id="txtDrEmail" styleClass="input utilitycode" style="width:200px" value="#{accountMandate.drEmail}">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </rich:panel>

                    <h:panelGrid id="gridPanel62" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;display:#{accountMandate.displayImageUploadField};" styleClass="row2" width="100%">
                        <h:outputLabel id="fileuploadlbl1" value="Upload JPEG FIle" styleClass="label"/>
                        <t:inputFileUpload id="file1" value="#{accountMandate.uploadedFile}" required="false"/>                        
                        <h:outputLabel id="fileuploadlbl2" value="Upload TIFF File" styleClass="label"/>
                        <t:inputFileUpload id="file2" value="#{accountMandate.uploadedFile1}" required="false"/>                        
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{accountMandate.displayOnCreate};" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{accountMandate.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="18"><h:outputText value="Input Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="UMRN" /></rich:column>
                                        <rich:column ><h:outputText value="Proprietary" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Frequency" /></rich:column>
                                        <rich:column><h:outputText value="From Date" /></rich:column>
                                        <rich:column><h:outputText value="To Date" /></rich:column>
                                        <rich:column><h:outputText value="Creditor A/C No." /></rich:column>
                                        <rich:column><h:outputText value="Creditor IFSC/MICR/IIN" /></rich:column>
                                        <rich:column><h:outputText value="Debitor A/C No." /></rich:column>
                                        <rich:column><h:outputText value="Debitor IFSC/MICR/IIN" /></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.cBSUmrn}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.proprietary}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.frequency}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fromDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.toDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.creditorAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.creditorIFSC}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debtorAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debtorIFSC}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" 
                                                     action="#{accountMandate.setTableDataToForm}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{accountMandate.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="30"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="#{accountMandate.btnValue}" 
                                               action="#{accountMandate.populateAlertMessage}" 
                                               oncomplete="#{rich:component('panel')}.show()" 
                                               reRender="stxtMsg,panel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{accountMandate.refreshForm}" 
                                               reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{accountMandate.exitForm}" 
                                               reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="panel" width="350" height="100">
                    <f:facet name="header">
                        <h:panelGroup>
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink"/>
                            <rich:componentControl for="panel" attachTo="hidelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="#{accountMandate.confirmAlert}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <h:commandButton value="Yes" id="btnYes" action="#{accountMandate.processAction}" 
                                                     onclick="#{rich:component('panel')}.hide();"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('panel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </rich:modalPanel>
            </h:form>
            <%--<a4j:region id="processActionRegion">
        <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
            <f:facet name="header">
                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
            </f:facet>
            <h:form>
                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <tbody>
                        <tr style="height:40px">
                            <td colspan="2">
                                <h:outputText id="confirmid" value="#{accountMandate.confirmAlert}"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" width="50%">
                                <h:commandButton value="Yes" id="btnYes" action="#{accountMandate.processAction}" 
                                                 onclick="#{rich:component('processPanel')}.hide();"/>
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
    </rich:modalPanel>--%>
        </body>
    </html>
</f:view>
