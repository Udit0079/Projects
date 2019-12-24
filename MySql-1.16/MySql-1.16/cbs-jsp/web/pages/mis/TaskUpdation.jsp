
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Task Updation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="form1">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TaskUpdation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Lead Updation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TaskUpdation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TaskUpdation.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="genpanel" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="action" value="Function" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="function" value="#{TaskUpdation.function}" styleClass="ddlist" style="width:130px" size="1"  >
                            <f:selectItems id="selectfunction" value="#{TaskUpdation.functionList}"/>
                            <a4j:support event="onblur" action="#{TaskUpdation.functionMode}" 
                                         reRender="errorMsg,gridPanelP2ATable,selectbranch,activityheader,panel2,branchId,userfilterId,targetTable,selectuserId,action1,purposegrid,enquirygrid,followdategrid,gridPanel7,gridPanelhistoryTable" 
                                         focus="branchId"/>
                        </h:selectOneListbox >
                        <h:outputLabel id="action1" value="Branch" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="branchId" value="#{TaskUpdation.branch}" styleClass="ddlist"   size="1"  style="width:130px;">
                            <f:selectItems id="selectbranch" value="#{TaskUpdation.branchList}"/>
                            <a4j:support event="onblur" action="#{TaskUpdation.onblurBranchMode}" 
                                         reRender="errorMsg,userfilterId"  focus="userfilterId"/>
                        </h:selectOneListbox >
                        <h:outputLabel id="userfilter" value="Assigned User" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="userfilterId" value="#{TaskUpdation.userAssignedFilter}" styleClass="ddlist"   size="1"  style="width:200px;">
                            <f:selectItems id="selectuserId" value="#{TaskUpdation.userAssginedFilter}"/>
                            <a4j:support event="onblur" action="#{TaskUpdation.getGridData}" 
                                         reRender="errorMsg,gridPanelP2ATable,branchId,action1,gridPanelhistoryTable,purposegrid,activityheader,panel2,enquirygrid,followdategrid,gridPanel7"
                                         oncomplete="if(#{rich:element('function')}.value=='M'){#{rich:element('gridPanelP2ATable')}.focus();}
                                         else if(#{rich:element('function')}.value=='V'){#{rich:element('txtFrmDate')}.focus();}" />
                        </h:selectOneListbox >
                    </h:panelGrid>  
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="display:#{TaskUpdation.panelViewDisplay}">                       
                        <h:outputLabel value="From Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskUpdation.frmDt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtToDate"/>
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtToDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskUpdation.toDt}">
                            <a4j:support event="onblur" oncomplete="setMask();" action="#{TaskUpdation.getGrid}" 
                                         reRender="errorMsg,targetTable" focus="printReport"/>  
                        </h:inputText>   
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelP2ATable" style="width:100%;height:0px;display:#{TaskUpdation.gridDisplay}" styleClass="row1"  columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskUpdation.gridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:20px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Customer Name"/></rich:column>
                                        <rich:column style="text-align:center;width:70px"><h:outputText value="Account No."/></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Father's Name"/></rich:column>
                                        <rich:column style="text-align:center;width:70px"><h:outputText value="ContactNo.[AlternateContactNo.]"/></rich:column>
                                        <rich:column style="text-align:center;width:70px"><h:outputText value="Email Id"/></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Address"/></rich:column>   
                                        <rich:column style="text-align:center;width:60px"><h:outputText value="Status"/></rich:column>   
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Follow Up Date Time"/></rich:column>
                                        <rich:column style="text-align:center;width:70px"><h:outputText value="Entry Date"/></rich:column>   
                                        <rich:column style="text-align:center;width:20px"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:20px"><h:outputText value="#{tempItem.srNo}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.customerName}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.accountNo}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.fatherName}" /></rich:column>                               
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.contactNo}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.emailId}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.address}" /></rich:column>
                                <rich:column style="text-align:center;width:60px"><h:outputText value="#{tempItem.status}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.followUpDatetime}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.followedDateTime}" /></rich:column>
                                <rich:column style="text-align:center;width:20px"> 
                                    <h:selectBooleanCheckbox  id ="checkbox11" value="#{tempItem.selected}">
                                        <a4j:support event="onclick" action="#{TaskUpdation.onClickcheckBox()}" reRender="errorMsg,txnList,personaldategrid,purposegrid,purposeid,
                                                     purpose,purposeList,purposeTypeData,purposeTypeDataList,statusid,status,refferedAccount,statusList,userassid,userAssigned,
                                                     userAssignedList,enquirygrid,historyheader,gridPanelhistoryTable,txnList1,refferedAccountNo,followdategrid,lockerCabinate,lockerType,lockerNo"  focus="enquiryType"/>
                                    </h:selectBooleanCheckbox>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempDataScroll" align="center" for="txnList" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid>       
                    <rich:panel id="activityheader" header="Activity Details" style="text-align:center;display:#{TaskUpdation.paneldisplay}">
                        <h:panelGrid id="purposegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="text-align:left;width:100%;height:0px;display:#{TaskUpdation.paneldisplay}"  styleClass="row2" width="100%" >
                            <h:outputLabel id="purposeid" value="Purpose" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="purpose" value="#{TaskUpdation.purpose}" styleClass="ddlist"  size="1" style="width:150px" title="Purpose (423)" disabled="#{TaskUpdation.disablePurpose}">
                                <f:selectItems id="purposeList" value="#{TaskUpdation.purposeList}"/>
                                <a4j:support event="onblur" action="#{TaskUpdation.getpurposeTyedata}" 
                                             reRender="errorMsg,purposeTypeData,purposeTypeDataList,statusid,status" focus="purposeTypeData"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="purposeTypeData" value="#{TaskUpdation.purposeTypeData}" styleClass="ddlist" size="1" style="width:150px"   title="Purpose Type Data (426)" disabled="#{TaskUpdation.disablePurpose}">
                                <f:selectItems id="purposeTypeDataList" value="#{TaskUpdation.purposeTypeDataList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel id="Enquiryid" value="Enquiry Type" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="enquiryType" value="#{TaskUpdation.enquiryType}" styleClass="ddlist" size="1" style="width:150px" title="Enquiry Type (425)"   disabled="#{TaskUpdation.disableforBrnManager}">
                                <f:selectItems id="enquiryTypeList" value="#{TaskUpdation.enquiryTypeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="enquirygrid" columns="8" columnClasses="col3,col3,col3,col3,col3,col3,col3,col3" style="text-align:left;width:100%;height:0px;display:#{TaskUpdation.paneldisplay}"  styleClass="row2" >   
                            <h:panelGroup>
                                <h:outputLabel id="statusid" value="Status" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                                    &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;  &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
                                <h:selectOneListbox id="status" value="#{TaskUpdation.status}" styleClass="ddlist"  size="1" style="width:150px" title="Status (424)" disabled="#{TaskUpdation.disableforBrnManager}">
                                    <f:selectItems id="statusList" value="#{TaskUpdation.statusList}"/>
                                    <a4j:support event="onblur" action="#{TaskUpdation.getUserAssignedEnabled}" 
                                                 reRender="errorMsg,userAssigned,userAssignedList,refferedAccountNo,personaldategrid,followupdatevalue,
                                                 selectHH1,selectMM1,selectSS1,lockerCabinate,lockerType,lockerNo"  
                                                 oncomplete="if((#{rich:element('purpose')}.value!='LO') && (#{rich:element('status')}.value=='B')){#{rich:element('refferedAccountNo')}.focus();}
                                                 else if((#{rich:element('status')}.value=='T')){#{rich:element('userAssigned')}.focus();}
                                                 else if((#{rich:element('purpose')}.value=='LO') && (#{rich:element('status')}.value=='B')){#{rich:element('lockerCabinate')}.focus();}
                                                 else if(#{rich:element('status')}.value=='F'){#{rich:element('checkbox')}.focus();}
                                                 else if(#{rich:element('status')}.value=='R'){#{rich:element('remarkvalue')}.focus();}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                            <h:panelGroup> 
                                <h:outputLabel id="userassid" value="User to be Assigned" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                                    &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;
                                <h:selectOneListbox id="userAssigned" value="#{TaskUpdation.userAssigned}" styleClass="ddlist" size="1" style="width:170px"  disabled="#{TaskUpdation.disableUsertoBeAssigned}" >
                                    <f:selectItems id="userAssignedList" value="#{TaskUpdation.userAssignedList}"/>
                                    <a4j:support event="onblur" action="#{TaskUpdation.checkUserAssgined}" 
                                                 reRender="errorMsg" />  
                                </h:selectOneListbox>
                            </h:panelGroup>
                            <h:panelGroup>
                                &nbsp;&nbsp;&nbsp;&nbsp; 
                                <h:outputLabel id="refferedAccount" value="#{TaskUpdation.referredAccountNoLabel}"  styleClass="label"  style="width:100px;display:#{TaskUpdation.refferedAcLabelDisplay}"><font class="required" color="red">*</font></h:outputLabel>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                                <h:inputText id="lockerCabinate"  value="#{TaskUpdation.lockerCabinate }"  style="width:60px;display:#{TaskUpdation.lockerDisplay}"    maxlength="6"  styleClass="input"  disabled="#{TaskUpdation.disableReferredAccountNo}"onkeyup="this.value = this.value.toUpperCase();">
                                </h:inputText>
                                &nbsp; 
                                <h:inputText id="lockerType"  value="#{TaskUpdation.lockerType }"  style="width:20px;display:#{TaskUpdation.lockerDisplay}"   maxlength="2"  styleClass="input " disabled="#{TaskUpdation.disableReferredAccountNo}" onkeyup="this.value = this.value.toUpperCase();">
                                </h:inputText>
                                &nbsp; 
                                <h:inputText id="lockerNo"  value="#{TaskUpdation.lockerNo }"  style="width:60px;display:#{TaskUpdation.lockerDisplay}"  maxlength="6"  styleClass="input" disabled="#{TaskUpdation.disableReferredAccountNo}"   onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support event="onblur" action="#{TaskUpdation.validateReferredAccountNo}" 
                                                 reRender="errorMsg" focus='remarkvalue' />
                                </h:inputText> 
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                <h:inputText id="refferedAccountNo"  value="#{TaskUpdation.referredAccountNo}"  style="width:100px;display:#{TaskUpdation.refferedAcDisplay}"  disabled="#{TaskUpdation.disableReferredAccountNo}"  styleClass="input">
                                    <a4j:support event="onblur" action="#{TaskUpdation.validateReferredAccountNo}" 
                                                 reRender="errorMsg" focus='remarkvalue'/>
                                </h:inputText>    
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="personaldategrid" columns="9" columnClasses="col3,col3,col3,col3,col3,col3,col3,col3,col3" style="text-align:left;width:100%;height:0px;display:#{TaskUpdation.personalVisitDisplay}"  styleClass="row1" width="100%" >
                            <h:panelGroup>
                                <h:outputLabel id="personlVisit" value="Personal Visit" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                                    &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; 
                                <h:selectBooleanCheckbox id ="checkbox" value="#{TaskUpdation.checkBox}" disabled="#{TaskUpdation.disableforBrnManager}">
                                    <a4j:support event="onclick" action="#{TaskUpdation.onClickedcheckBox()}" reRender="errorMsg,personaldatevalue"  focus="personaldatevalue"/>
                                </h:selectBooleanCheckbox>
                                &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;       
                                <h:outputLabel id="personaldate" value="Pesonal Visit Date"  styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                                    &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; 
                                <h:inputText id="personaldatevalue" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskUpdation.personalVisitDate}"  disabled="#{TaskUpdation.disableforBrnManager}">
                                    <a4j:support event="onblur" oncomplete="setMask();" focus="selectHH11"/>
                                </h:inputText>
                                &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;  
                                <h:panelGroup id="pgTime111">
                                    <h:outputLabel id="lblHH11" styleClass="headerLabel"  value="HH"  />
                                    &nbsp;
                                    <h:selectOneListbox id="selectHH11" value="#{TaskUpdation.selectHH11}" styleClass="ddlist" size="1" disabled="#{TaskUpdation.disableforBrnManager}" >
                                        <f:selectItems id="selectHH1List1" value="#{TaskUpdation.selectHH1List1}" />
                                    </h:selectOneListbox>  
                                    &nbsp;
                                    <h:outputLabel id="lblMM11" styleClass="headerLabel"  value="MM"  />
                                    &nbsp;
                                    <h:selectOneListbox id="selectMM11" value="#{TaskUpdation.selectMM11}" styleClass="ddlist" size="1" disabled="#{TaskUpdation.disableforBrnManager}">
                                        <f:selectItems id="selectMM1List1" value="#{TaskUpdation.selectMM1List1}" />
                                    </h:selectOneListbox>
                                    &nbsp;
                                    <h:selectOneListbox id="selectSS11" value="#{TaskUpdation.selectSS11}" styleClass="ddlist" size="1" disabled="#{TaskUpdation.disableforBrnManager}">
                                        <f:selectItems id="selectSS1List1" value="#{TaskUpdation.selectSS1List1}" />

                                    </h:selectOneListbox>  
                                </h:panelGroup>
                            </h:panelGroup>
                        </h:panelGrid> 
                        <h:panelGrid id="followdategrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="text-align:left;width:100%;height:0px;display:#{TaskUpdation.paneldisplay}"  styleClass="row1" width="100%" >   
                            <h:outputLabel id="followupdate" value="Follow Up Date"  styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="followupdatevalue" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskUpdation.followDate}" disabled="#{TaskUpdation.disablefollowUpData}">
                                <a4j:support event="onblur" oncomplete="setMask();"focus="selectHH1"/>
                            </h:inputText>
                            <h:outputLabel id="labelInTime" styleClass="label" value="Follow Up Time"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup id="pgTime1">
                                <h:outputLabel id="lblHH1" styleClass="headerLabel"  value="HH"  />
                                &nbsp;
                                <h:selectOneListbox id="selectHH1" value="#{TaskUpdation.selectHH1}" styleClass="ddlist" size="1" disabled="#{TaskUpdation.disablefollowUpData}">
                                    <f:selectItems id="selectHH1List" value="#{TaskUpdation.selectHH1List}" />
                                </h:selectOneListbox>  
                                &nbsp;
                                <h:outputLabel id="lblMM1" styleClass="headerLabel"  value="MM"  />
                                &nbsp;
                                <h:selectOneListbox id="selectMM1" value="#{TaskUpdation.selectMM1}" styleClass="ddlist" size="1" disabled="#{TaskUpdation.disablefollowUpData}">
                                    <f:selectItems id="selectMM1List" value="#{TaskUpdation.selectMM1List}" />
                                </h:selectOneListbox>
                                &nbsp;
                                <h:selectOneListbox id="selectSS1" value="#{TaskUpdation.selectSS1}" styleClass="ddlist" size="1" disabled="#{TaskUpdation.disablefollowUpData}">
                                    <f:selectItems id="selectSS1List" value="#{TaskUpdation.selectSS1List}" />
                                </h:selectOneListbox>  
                            </h:panelGroup>
                            <h:outputLabel id="custmoerRemarkid" value="Remark of Customer" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="remarkvalue"  value="#{TaskUpdation.customerRemark}"  style="width:180px" styleClass="row1"/>
                        </h:panelGrid> 
                    </rich:panel>        
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer" >
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">                           
                            <a4j:commandButton id="save" value="Modify" action="#{TaskUpdation.onButtonClick}" oncomplete="#{rich:component('buttonConfirmationPanel')}.show()"   style="display:#{TaskUpdation.paneldisplay}"  reRender="buttonConfirmationPanel,errorMsg"/>
                            <a4j:commandButton id="printReport" value="PrintReport" action="#{TaskUpdation.printReport}" style="display:#{TaskUpdation.panelViewDisplay}"    reRender="errorMsg"/>
                            <a4j:commandButton id="pdf" value="PDF Download" action="#{TaskUpdation.btnPdfAction}"  style="display:#{TaskUpdation.panelViewDisplay}"  reRender=" errorMsg"/>
                            <h:commandButton id="excel" value="Download Excel" actionListener="#{TaskUpdation.downloadExcel}"   style="display:#{TaskUpdation.panelViewDisplay}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TaskUpdation.refresh}" reRender="a1,errorMsg,ddbranch,Remarkss,enquirygrid,purposegrid,
                                               gridPanelP2ATable,panel2,branch1,historyheader,activityheader,newuser1,newuser,genpanel,genpanel1,targetTable,
                                               gridPanelhistoryTable,lockerCabinate,lockerType,lockerNo" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{TaskUpdation.exit}" reRender="errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelhistoryTable" style="width:100%;height:0px;display:#{TaskUpdation.historyGridDisplay}" styleClass="row1"  columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskUpdation.historyGridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList1" rows="5" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="History Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:20px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:20px"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Account No." /></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Follow Up Date Time" /></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Status" /></rich:column>
                                        <rich:column style="text-align:center;width:130px"><h:outputText value="Follow Up Remarks" /></rich:column>
                                        <rich:column style="text-align:center;width:70px"><h:outputText value="Enquiry Type" /></rich:column>                         
                                        <rich:column style="text-align:center;width:70px"><h:outputText value="Entry Date Time" /></rich:column>                         
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:20px"><h:outputText value="#{tempItem.srNo}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.customerName}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.accountNo}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.followUpDatetime}" /></rich:column>                               
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.status}"/></rich:column>
                                <rich:column style="text-align:center;width:130px"><h:outputText value="#{tempItem.followUpRemarks}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.enquiryType}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.followedDateTime}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempDataScrollId" align="center" for="txnList1" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid> 
                    <h:panelGrid id="targetTable" style="width:100%;height:0px;display:#{TaskUpdation.panelViewDisplay}" styleClass="row1"  columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskUpdation.totalTargetGridDetail}" var="temp"
                                             rowClasses="gridrow1, gridrow2" id="List1" rows="5" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Target Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:20px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Assigned User" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="New" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Follow Up" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Booked/Successful" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Transfer" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Reject/Dump" /></rich:column>                         
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Pending(New+FollowUp)"/></rich:column>                         
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:20px"><h:outputText value="#{temp.srno}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{temp.assignedUser}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{temp.newS}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{temp.followup}" /></rich:column>                               
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{temp.transfer}"/></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{temp.booked}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{temp.reject}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{temp.pending}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempData" align="center" for="List1" maxPages="10" />
                        </a4j:region> 
                    </h:panelGrid>          

                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>                 
                </h:panelGrid>
            </h:form>
            <a4j:region id="idBtnRegion">
                <rich:modalPanel id="buttonConfirmationPanel" autosized="true" width="250" onshow="#{rich:element('btnAlertYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Conformation Dialog" style="padding-right:15px;text-align:center;font-weight:bold;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="btnConfirmid" value="#{TaskUpdation.msgvalue}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnAlertYes" action="#{TaskUpdation.buttonClick}" onclick="#{rich:component('buttonConfirmationPanel')}.hide();" 
                                                           reRender="mainPanel,gridPanelP2ATable,gridPanelhistoryTable,personaldategrid"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnAlertNo" onclick="#{rich:component('buttonConfirmationPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>