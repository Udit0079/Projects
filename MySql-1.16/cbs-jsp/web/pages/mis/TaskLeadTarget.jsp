
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
            <title>Lead Target  </title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{TaskLeadTarget.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Lead Target"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TaskLeadTarget.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TaskLeadTarget.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="genpanel" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="action" value="Function" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="function" value="#{TaskLeadTarget.function}" styleClass="ddlist" style="width:110px" size="1"  >
                            <f:selectItems id="selectfunction" value="#{TaskLeadTarget.functionList}"/>
                            <a4j:support event="onblur" action="#{TaskLeadTarget.functionMode}" 
                                         reRender="errorMsg,gridPanelP2ATable,gridPanelhistory,branchvalue,entryType,gridPanelTable,userassid,userAssigned,purposegrid,
                                         entryvalue,timelimitGrid,purposegrid,gridPanel7,genpanel1"  focus="branchvalue"/>
                        </h:selectOneListbox >
                        <h:outputLabel id="branch" value="Branch" styleClass="label" style="width:100%;height:0px;"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="branchvalue" value="#{TaskLeadTarget.branch}" styleClass="ddlist" style="width:110px" size="1" disabled="#{TaskLeadTarget.disable}">
                            <f:selectItems id="selectbranch" value="#{TaskLeadTarget.branchList}"/>
                        </h:selectOneListbox >
                        <h:outputLabel id="entryType" value="Entry Type" styleClass="label" style="width:100%;height:0px;display:#{TaskLeadTarget.entryTypeDisplay}"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="entryvalue" value="#{TaskLeadTarget.entryType}" styleClass="ddlist" style="width:110px;display:#{TaskLeadTarget.entryTypeDisplay}" size="1" disabled="#{TaskLeadTarget.disable}" >
                            <f:selectItems id="selectentryType" value="#{TaskLeadTarget.entryTypeList}"/>
                            <a4j:support event="onblur" action="#{TaskLeadTarget.entryTypeMode}" 
                                         reRender="errorMsg,txtFrmDate,txtToDate,purposeid,purpose,gridPanelP2ATable,gridPanelTable,gridPanelTable2,
                                         userassid,userAssigned,purposeList,marketingList,smkt,purpodisabledse" 
                                         focus="fincialyearvalue"/>
                        </h:selectOneListbox >
                    </h:panelGrid>
                    <h:panelGrid id="genpanel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="fiancialyear" value="Finacial Year" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="fincialyearvalue" value="#{TaskLeadTarget.finacialYear}" styleClass="ddlist" style="width:80px" size="1"  disabled="#{TaskLeadTarget.disable}">
                            <f:selectItems id="selectyear" value="#{TaskLeadTarget.finacialYearList}"/>
                        </h:selectOneListbox >
                        <h:outputLabel value="From Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskLeadTarget.frmDt}" disabled="#{TaskLeadTarget.disable}">
                            <a4j:support event="onblur" oncomplete="setMask(); " focus="txtToDate"/>
                        </h:inputText> 
                        <h:outputLabel value="To Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtToDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{TaskLeadTarget.toDt}" disabled="#{TaskLeadTarget.disable}">
                            <a4j:support event="onblur"  oncomplete="if((#{rich:element('function')}.value=='A') && (#{rich:element('entryvalue')}.value=='M')){#{rich:element('purpose')}.focus();}
                                         else  if((#{rich:element('function')}.value=='A') && (#{rich:element('entryvalue')}.value=='D')){#{rich:element('smkt')}.focus();}
                                         else  if((#{rich:element('function')}.value=='A') && (#{rich:element('entryvalue')}.value=='Y')){#{rich:element('purpose')}.focus();}" />
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid id="purposegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="display:#{TaskLeadTarget.panelAddDisplay}"  styleClass="row2" width="100%" >
                        <h:outputLabel id="purposeid" value="#{TaskLeadTarget.labelValue}" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="purpodisabledse" value="#{TaskLeadTarget.purpose}" styleClass="ddlist"  size="1" disabled="#{TaskLeadTarget.disable}" style="width:140px;display:#{TaskLeadTarget.purposedisplay}" title="Purpose (423)" >
                                <f:selectItems id="purposeList" value="#{TaskLeadTarget.purposeList}"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="smkt" value="#{TaskLeadTarget.sourceofMarketing}" styleClass="ddlist" disabled="#{TaskLeadTarget.disable}" size="1" style="width:150px;display:#{TaskLeadTarget.dailyDisplay}" title="Source of marketing (422)" >
                                <f:selectItems id="marketingList" value="#{TaskLeadTarget.marketingSourceList}"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="assignedtarget" value="Assigned Target" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="targetvalue"  value="#{TaskLeadTarget.assignedTarget}"  style="width:70px">
                            <a4j:support event="onblur"  oncomplete="if((#{rich:element('function')}.value=='A') && (#{rich:element('entryvalue')}.value=='D')){#{rich:element('userAssigned')}.focus();}
                                         else  if((#{rich:element('function')}.value=='A') && (#{rich:element('entryvalue')}.value=='M')){#{rich:element('userAssigned')}.focus();}
                                         else  if((#{rich:element('function')}.value=='A') && (#{rich:element('entryvalue')}.value=='Y')){#{rich:element('save')}.focus();}" /> 
                        </h:inputText>
                        <h:outputLabel id="userassid" value="User to be Assigned" styleClass="label" style="width:100%;height:0px;display:#{TaskLeadTarget.userassignedDisplay}" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="userAssigned" value="#{TaskLeadTarget.userAssigned}" styleClass="ddlist" size="1" title="User List  #{TaskLeadTarget.userName}" disabled="#{TaskLeadTarget.disable}"  style="width:220px;display:#{TaskLeadTarget.userassignedDisplay}">
                            <f:selectItems id="userAssignedList" value="#{TaskLeadTarget.userAssignedList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelP2ATable" style="width:100%;height:0px;display:#{TaskLeadTarget.hogridDisplay}" styleClass="row2" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskLeadTarget.hoGridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="8" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:30px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Branch"/></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Purpose"/></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Assigned Target"/></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Achieved Target"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:40px"><h:outputText value="#{tempItem.srNo}"/></rich:column>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.branch}"/></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.purpose}"/></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.assignedTarget}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.achievedTarget}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempDataScroll" align="center" for="txnList" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style="width:100%;height:0px;display:#{TaskLeadTarget.dailyDisplay}" styleClass="row2" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskLeadTarget.gridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList3" rows="8" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Daily Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:30px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Source of Marketing"/></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Assigned User"/></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Assigned Target"/></rich:column>
                                        <rich:column style="text-align:center;width:110px"><h:outputText value="Enter By" /></rich:column> 
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Entry Date" /></rich:column> 
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:40px"><h:outputText value="#{tempItem.srNo}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.sourceOfMarketing}"/></rich:column>
                                <rich:column style="text-align:center;width:110px"><h:outputText value="#{tempItem.assignedUser}"/></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.assignedTarget}"/></rich:column>
                                <rich:column style="text-align:center;width:110px"><h:outputText value="#{tempItem.enterBy}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.entryDate}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" ajaxSingle="tajaxSinglerue" action="#{TaskLeadTarget.targetUpdate}" reRender="purposegrid,save,genpanel,genpanel1,branch, errorPanel,errorMsg">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{tempItem}" target="#{TaskLeadTarget.tempCurrentItem}"/>  
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempDataScroll1" align="center" for="txnList3" maxPages="18"/>
                        </a4j:region> 
                    </h:panelGrid> 

                    <h:panelGrid id="gridPanelTable2" style="width:100%;height:0px;display:#{TaskLeadTarget.monthlygridDisplay}" styleClass="row2" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskLeadTarget.monthlyGridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList1" rows="8" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Monthly Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:30px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Purpose"/></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Assigned User"/></rich:column>
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Assigned Target"/></rich:column>
                                        <rich:column style="text-align:center;width:110px"><h:outputText value="Enter By" /></rich:column> 
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Entry Date" /></rich:column> 
                                        <rich:column style="text-align:center;width:80px"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:40px"><h:outputText value="#{tempItem.srNo}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.purpose}"/></rich:column>
                                <rich:column style="text-align:center;width:110px"><h:outputText value="#{tempItem.assignedUser}"/></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.assignedTarget}"/></rich:column>
                                <rich:column style="text-align:center;width:110px"><h:outputText value="#{tempItem.enterBy}" /></rich:column>
                                <rich:column style="text-align:center;width:80px"><h:outputText value="#{tempItem.entryDate}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" ajaxSingle="tajaxSinglerue" action="#{TaskLeadTarget.targetUpdate}" reRender="purposegrid,save,genpanel,genpanel1,branch, errorPanel,errorMsg">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{tempItem}" target="#{TaskLeadTarget.tempCurrentItem}"/>  
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempDataScroll3" align="center" for="txnList1" maxPages="18"/>
                        </a4j:region> 
                    </h:panelGrid> 

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">                           
                            <a4j:commandButton id="save" value="#{TaskLeadTarget.buttonValue}"   action="#{TaskLeadTarget.onButtonClicked}" oncomplete="#{rich:component('buttonConfirmationPanel')}.show()"  style="display:#{TaskLeadTarget.panelAddDisplay}"  reRender="buttonConfirmationPanel,errorMsg"/>
                            <a4j:commandButton id="printReport" value="Print Report" action="#{TaskLeadTarget.printReport}"  style="display:#{TaskLeadTarget.panelViewDisplay}"     reRender="errorMsg"/>
                            <a4j:commandButton id="pdf" value="PDF Download" action="#{TaskLeadTarget.pdfDownload}"  style="display:#{TaskLeadTarget.panelViewDisplay}"    reRender="errorMsg"/>
                            <h:commandButton id="excel" value="Download Excel" actionListener="#{TaskLeadTarget.downloadExcel}"   style="display:#{TaskLeadTarget.panelViewDisplay}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TaskLeadTarget.refresh}" reRender="errorMsg, gridPanelP2ATable,purposegrid,genpanel1,genpanel,mainPanel"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{TaskLeadTarget.exit}" reRender="errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid id="gridPanelhistory" style="width:100%;height:0px;text-align:center;display:#{TaskLeadTarget.hoHistoryGridDisplay}" styleClass="row2" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{TaskLeadTarget.hoHistoryGridDetail}" var="tempItem" align="center"
                                             rowClasses="gridrow1, gridrow2" id="txnList2" rows="5" columnsWidth="50" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="70%" style="width:70%;height:0px;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="4"><h:outputText value="Yearly History Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:10px"><h:outputText value="Sr No." /></rich:column>
                                        <rich:column style="text-align:center;width:20px"><h:outputText value="Purpose"/></rich:column>
                                        <rich:column style="text-align:center;width:20px"><h:outputText value="Assigned Target"/></rich:column>
                                        <rich:column style="text-align:center;width:20px"><h:outputText value="Achieved Target"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:10px"><h:outputText value="#{tempItem.srNo}"/></rich:column>
                                <rich:column style="text-align:center;width:20px"><h:outputText value="#{tempItem.purpose}"/></rich:column>
                                <rich:column style="text-align:center;width:20px"><h:outputText value="#{tempItem.assignedTarget}" /></rich:column>
                                <rich:column style="text-align:center;width:20px"><h:outputText value="#{tempItem.achievedTarget}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="UnAuthTempDataScroll2" align="center" for="txnList2" maxPages="18" />
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
                                        <h:outputText id="btnConfirmid" value="#{TaskLeadTarget.msgvalue}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnAlertYes" action="#{TaskLeadTarget.buttonClick}" onclick="#{rich:component('buttonConfirmationPanel')}.hide();" 
                                                           reRender="mainPanel,save"/>
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
