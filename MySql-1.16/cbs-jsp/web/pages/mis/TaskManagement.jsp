
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Lead Assignment</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{TaskManagement.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Lead Assignment"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TaskManagement.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TaskManagement.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="genpanel" columns="8" columnClasses="col3,col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="action" value="Function" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="function" value="#{TaskManagement.function}" styleClass="ddlist" style="width:130px" size="1">
                            <f:selectItems id="selectfunction" value="#{TaskManagement.functionList}"/>
                            <a4j:support event="onblur" action="#{TaskManagement.functionMode}" 
                                         reRender="errorMsg,originlist,selectorigin,accountcheckValue,activityheader,
                                         selectaccountcheckbylist,datasourcepanel,newuser,newuser1,newuser2,branch1,filter,
                                         panel2,gridPanelP2ATable,purposegrid,enquirygrid,gridPanel7,panel2,followdateygrid,
                                         buttonConfirmationPanel,branchho,branchvalue,branchho,selectbranchho"  focus="originlist"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="originby" value="Origin By" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="originlist" value="#{TaskManagement.originBy}" styleClass="ddlist" title="origin By (428)"  style="width:130px" size="1"  >
                            <f:selectItems id="selectorigin" value="#{TaskManagement.originByList}"/>
                            <a4j:support event="onblur" action="#{TaskManagement.originByMode}" 
                                         oncomplete="if((#{rich:element('function')}.value=='A') && (#{rich:element('originlist')}.value=='N')){#{rich:element('accountcheckValue')}.focus();}
                                         else  if((#{rich:element('function')}.value=='A') && (#{rich:element('originlist')}.value=='U')){#{rich:element('sourceofdata')}.focus();}
                                         else  if((#{rich:element('function')}.value=='V')){#{rich:element('branchho')}.focus();}"
                                         reRender="errorMsg,branch1,accountcheckValue,datasourcepanel,selectaccountcheckbylist,datasourcepanel,selectSourceofdata,
                                         sourceofmarketingvalue,selectSourceofmarketing,userAssigned,userAssignedList,newuser,newuser1,
                                         newuser2,branch1,panel2,filter"/>
                        </h:selectOneListbox> 
                        <h:selectOneListbox id="accountcheckValue" value="#{TaskManagement.originbyFilter}" styleClass="ddlist"  style="width:170px;display:#{TaskManagement.panelByOrigin}" size="1"  >
                            <f:selectItems id="selectaccountcheckbylist" value="#{TaskManagement.originByFilterList}"/>
                            <a4j:support event="onblur" action="#{TaskManagement.accountCheckMode}" reRender="errorMsg ,newuser,newuser1,newuser2,newUserAccountNo,"
                                         focus="sourceofdata"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="branchvalue" value="Branch" styleClass="label" style="display:#{TaskManagement.viewBranch}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="branchho" value="#{TaskManagement.branchHo}" styleClass="ddlist"  style="width:170px;display:#{TaskManagement.viewBranch}" size="1"  >
                            <f:selectItems id="selectbranchho" value="#{TaskManagement.branchHoList}"/>
                            <a4j:support event="onblur"  reRender="errorMsg "
                                         focus="sourceofdata"/>
                        </h:selectOneListbox>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid id="datasourcepanel" columns="9" columnClasses="col3,col3,col3,col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="Func" value="Source of Data" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="sourceofdata" value="#{TaskManagement.dataSource}" styleClass="ddlist" title="Source of Data (421)" style="width:130px"  size="1"   >
                            <f:selectItems id="selectSourceofdata" value="#{TaskManagement.dataSourceList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="sourceofmarketing" value="Source of Marketing" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="sourceofmarketingvalue" value="#{TaskManagement.marketingSource}" styleClass="ddlist"  title="Source of Marketing (422)" style="width:130px" size="1"  >
                            <f:selectItems id="selectSourceofmarketing" value="#{TaskManagement.marketingSourceList}"/>
                            <a4j:support   oncomplete="if((#{rich:element('function')}.value=='A') && (#{rich:element('originlist')}.value=='U')){#{rich:element('ddbranch')}.focus();}
                                           else  if((#{rich:element('function')}.value=='A') && (#{rich:element('originlist')}.value=='N')&&(#{rich:element('accountcheckValue')}.value=='N')){#{rich:element('usernamevalue')}.focus();}
                                           else  if((#{rich:element('function')}.value=='A') && (#{rich:element('originlist')}.value=='N')&&(#{rich:element('accountcheckValue')}.value!='N')){#{rich:element('newUserAccountNo')}.focus();}
                                           else  if((#{rich:element('function')}.value=='V') && (#{rich:element('originlist')}.value=='A')){#{rich:element('txtFrmDate')}.focus();}"/>
                        </h:selectOneListbox>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid  id="newuser" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%"  style="display:#{TaskManagement.panelDisplay1}" >
                        <h:outputLabel id="useraccountNo" value="Account No." styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:inputText id="newUserAccountNo"  value="#{TaskManagement.newUserAccountNo}" styleClass="input" maxlength="12"  size="15" disabled="#{TaskManagement.disableNewUserAcNo}">
                            <a4j:support event="onblur" action="#{TaskManagement.accountMode}"   reRender="errorMsg,newuser1,usernamevalue,fathernamevalue,newuser2" focus="usernamevalue"/> 
                        </h:inputText>
                        <h:outputLabel id="username" value="Customer Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:inputText id="usernamevalue"  value="#{TaskManagement.customerName}" styleClass="input" maxlength="100"  size="25" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur" action="#{TaskManagement.validateCustomername}" reRender="errorMsg" focus="fathernamevalue"/>
                        </h:inputText>
                        <h:outputLabel id="fathername" value="Father's Name" styleClass="label" /> 
                        <h:inputText id="fathernamevalue"  value="#{TaskManagement.fatherName}" styleClass="input" maxlength="100"  size="25" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur"  focus="custmobilevlue" />
                        </h:inputText>
                    </h:panelGrid>  
                    <h:panelGrid id="newuser1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%"  style="display:#{TaskManagement.panelDisplay1}" > 
                        <h:outputLabel id="ContactNo" value="Contact No." styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:inputText  id="custmobilevlue" value="#{TaskManagement.contactNo}" styleClass="input" maxlength="10" size="12"> 
                            <a4j:support event="onblur" action="#{TaskManagement.validateContactNo}" reRender="errorMsg" focus="altermobilevlue"/>
                        </h:inputText>
                        <h:outputLabel id="alternateContactNo" value="Alternate Contact No." styleClass="label"/> 
                        <h:inputText  id="altermobilevlue" value="#{TaskManagement.alternateContactNo}" styleClass="input" maxlength="10" size="12">
                            <a4j:support event="onblur"   focus="emailvalue"/>
                        </h:inputText>
                        <h:outputLabel id="Emailid" value="Email Id" styleClass="label"/> 
                        <h:inputText id="emailvalue"  value="#{TaskManagement.emailId}" styleClass="input" maxlength="100"  size="20" onkeyup="this.value = this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="newuser2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%"  style="display:#{TaskManagement.panelDisplay1}" >
                        <h:outputLabel id="address" value="Address" styleClass="label"/> 
                        <h:inputText id="Adressvalue"  value="#{TaskManagement.address}" styleClass="input" maxlength="100"  size="30" onkeyup="this.value = this.value.toUpperCase();"/>
                        <h:outputLabel id="city" value="City" styleClass="label"/>
                        <h:inputText id="cityvalue"  value="#{TaskManagement.city}" styleClass="input" maxlength="100"  size="18" onkeyup="this.value = this.value.toUpperCase();"/>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid id="branch1" columns="4" columnClasses="col3,col3,col3,col3" styleClass="row1" width="100%"  style="display:#{TaskManagement.panelDisplay2}">
                        <h:outputLabel id="lblbranch" value="Branch" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddbranch" value="#{TaskManagement.branch}" styleClass="ddlist" style="width:130px;" size="1">
                            <f:selectItems id="selectbranch" value="#{TaskManagement.branchList}"/>
                            <a4j:support event="onblur" action="#{TaskManagement.onBlurBranch}"  focus="Remarkss" reRender="errorMsg,userAssigned,gridPanelP2ATable,userAssignedList" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRemarks" value="Remarks" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="Remarkss"  value="#{TaskManagement.remarks}" style="width:200px" styleClass="row1"/>
                    </h:panelGrid>   
                    <h:panelGrid id="filter" columns="4" columnClasses="col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{TaskManagement.panelDisplay2}">
                        <h:outputLabel id="lblsearchBy" value="Search By" styleClass="label"/>  
                        <h:selectOneListbox id="ddsearchBy" value="#{TaskManagement.searchBy}"  styleClass="ddlist" style="width:100px" size="1" title="code no(429)">
                            <f:selectItems id="selectsearchBy" value="#{TaskManagement.searchByList}"/>
                            <a4j:support event="onblur" action="#{TaskManagement.searchByAction}"  reRender="a1,ddsearchByOption,errorMsg," focus="ddsearchByOption" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblsearchByOption" value="Search Option" styleClass="label"/>
                        <h:selectOneListbox id="ddsearchByOption" value="#{TaskManagement.searchByOption}"  styleClass="ddlist" style="width:130px" size="1" title="code no(430)">
                            <f:selectItems id="selectsearchByOption" value="#{TaskManagement.searchByOptionList}" />
                            <a4j:support event="onblur"   focus="txtFrmDate" />
                        </h:selectOneListbox>
                    </h:panelGrid> 
                    <h:panelGrid id="panel2" columns="4" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{TaskManagement.commonDisplay}">                       
                        <h:outputLabel value="From Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskManagement.frmDt}">
                            <a4j:support event="onblur" oncomplete="setMask(); " focus="txtToDate" />
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtToDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskManagement.toDt}">
                            <a4j:support event="onblur" action="#{TaskManagement.onBlurGetGridtabel}" oncomplete="if((#{rich:element('function')}.value=='A')){#{rich:element('allchechbox')}.focus();}
                                         else  if((#{rich:element('function')}.value=='V')){#{rich:element('save1')}.focus();};setMask();"
                                         reRender="stxtNewAccount,panelDisplay2,errorMsg,p2AGridDetail,gridPanelP2ATable" />
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelP2ATable" style="width:100%;height:0px;display:#{TaskManagement.panelDisplay2}" styleClass="row1"  columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskManagement.gridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:40px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Account No." /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Father's Name" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Contact No." /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Email Id" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Address" /></rich:column>                      
                                        <rich:column style="text-align:center;width:40px">
                                            <h:selectBooleanCheckbox   id ="allchechbox" value="#{TaskManagement.allSelected}">
                                                <a4j:support event="onchange" action="#{TaskManagement.setAllBoxSelected}" reRender="txnList"/>
                                            </h:selectBooleanCheckbox>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:40px"><h:outputText value="#{tempItem.srNo}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.customerName}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.accountNo}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.fatherName}" /></rich:column>                               
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.contactNo}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.emailId}" /></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.address}" /></rich:column>
                                <rich:column style="text-align:center;width:40px"> 
                                    <h:selectBooleanCheckbox id="checkBox" value="#{tempItem.selected}">
                                        <a4j:support event="onclick" action="#{TaskManagement.onClickcheckBox()}" focus='purpose'  reRender="errorMsgReporting,errorGrid,fromText,toText"/>
                                    </h:selectBooleanCheckbox>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempDataScroll" align="center" for="txnList" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid>   
                    <rich:panel id="activityheader" header="Activity Details" style="text-align:center;display:#{TaskManagement.mainPanelForAdd}">
                        <h:panelGrid id="purposegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="text-align:left;width:100%;height:0px;display:#{TaskManagement.mainPanelForAdd}">
                            <h:outputLabel id="purposeid" value="Purpose" styleClass="label" ><font class="required" color="red" >*</font></h:outputLabel>
                            <h:selectOneListbox id="purpose" value="#{TaskManagement.purpose}" styleClass="ddlist"  size="1"  title="Purpose (423)" style="width:150px">
                                <f:selectItems id="purposeList" value="#{TaskManagement.purposeList}"/>
                                <a4j:support event="onblur" action="#{TaskManagement.getpurposeTyedata}" 
                                             reRender="errorMsg ,purposeTypeData,purposeTypeDataList" focus='purposeTypeData'/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="purposeTypeData" value="#{TaskManagement.purposeTypeData}" styleClass="ddlist" size="1" title="Purpose Type Data (426)" style="width:150px">
                                <f:selectItems id="purposeTypeDataList" value="#{TaskManagement.purposeTypeDataList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel id="Enquiryid" value="Enquiry Type" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="enquiryType" value="#{TaskManagement.enquiryType}" styleClass="ddlist" size="1"  title="Enquiry Type (425)" style="width:150px">
                                <f:selectItems id="enquiryTypeList" value="#{TaskManagement.enquiryTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel></h:outputLabel>
                        </h:panelGrid>         
                        <h:panelGrid id="enquirygrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="text-align:left;width:100%;height:0px;display:#{TaskManagement.mainPanelForAdd}">   
                            <h:outputLabel id="statusid" value="Status" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="status" value="#{TaskManagement.status}" styleClass="ddlist"  size="1" title="Status (424)"  style="width:150px">
                                <f:selectItems id="statusList" value="#{TaskManagement.statusList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="userassid" value="User to be Assigned" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="userAssigned" value="#{TaskManagement.userAssigned}" styleClass="ddlist" size="1" title="User List  #{TaskManagement.userName}"  style="width:220px">
                                <f:selectItems id="userAssignedList" value="#{TaskManagement.userAssignedList}"/>
                                <a4j:support event="onblur"  
                                             reRender="errorMsg" focus='followupdatevalue'/>
                            </h:selectOneListbox>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid id="followdateygrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%"  style="text-align:left;width:100%;height:0px;display:#{TaskManagement.mainPanelForAdd}">  
                            <h:outputLabel id="followupdate" value="Follow Up date" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="followupdatevalue" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskManagement.followDate}">
                                <a4j:support event="onblur" oncomplete="setMask();" focus='remarkvalue' />
                            </h:inputText>
                            <h:outputLabel id="custmoerRemarkid" value="Remark of Customer" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="remarkvalue"  value="#{TaskManagement.customerRemark}"  style="width:200px" styleClass="row1">
                                <a4j:support event="onblur"   focus='save' />
                            </h:inputText>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel></h:outputLabel>
                        </h:panelGrid> 
                    </rich:panel>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="#{TaskManagement.buttonValue}" style="width:100%;text-align:center;">                           
                            <a4j:commandButton id="save" value="Save" action="#{TaskManagement.onbuttonClicked}"   style="display:#{TaskManagement.mainPanelForAdd}"  oncomplete="#{rich:component('buttonConfirmationPanel')}.show()"  reRender="buttonConfirmationPanel,errorMsg"/>
                            <a4j:commandButton id="save1" value="Print Report" action="#{TaskManagement.printReport}"  style="display:#{TaskManagement.viewpdfbutton}"   reRender="errorMsg"/>
                            <a4j:commandButton id="pdf" value="PDF Download" action="#{TaskManagement.pdfDownload}"   style="display:#{TaskManagement.viewpdfbutton}" reRender="errorMsg"/>
                            <h:commandButton id="excel" value="Download Excel" actionListener="#{TaskManagement.downloadExcel}"   style="display:#{TaskManagement.viewpdfbutton}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TaskManagement.refresh}" reRender="mainPanel,errorMsg,ddbranch,Remarkss,enquirygrid,purposegrid,gridPanelP2ATable,panel2,branch1,filter,newuser1,newuser,newuser2,genpanel,genpanel1,mainPanel" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{TaskManagement.exit}" reRender="errorMsg"/>
                        </h:panelGroup>
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
                                        <h:outputText id="btnConfirmid" value="#{TaskManagement.msgvalue}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnAlertYes" action="#{TaskManagement.buttonClick}" onclick="#{rich:component('buttonConfirmationPanel')}.hide();" 
                                                           reRender="mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnAlertNo" onclick="#{rich:component('buttonConfirmationPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <a4j:status onstart="#{rich:component('waitid')}.show()" onstop="#{rich:component('waitid')}.hide()" />
                        <rich:modalPanel id="waitid" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <f:facet name="header">
                                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                            </f:facet>
                        </rich:modalPanel>  
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>
