<%--
    Document   : td15hEntry
    Created on : May 16, 2010, 12:09:28 PM
    Author     : jitendra kumar Chaudhary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>TDS Documents Entry </title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{td15hEntry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="TDS Documents Entry"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{td15hEntry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{td15hEntry.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel4" width="100%">                        
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanelFunc" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="funcLabel" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunc" styleClass="ddlist"  style="width:80px;" size="1" value="#{td15hEntry.fnDetails}">
                                    <f:selectItems value="#{td15hEntry.fnDetailsList}"/>
                                    <a4j:support action="#{td15hEntry.setFnOption}" event="onblur" reRender="ddDocument,stxtJtUgName,stxtDob,stxtFatherName,stxtCustName,txtCustId,stxtMsg,btnSave,gridPanel103,gridPanel104,gridPanel15,gridPaneMsg,incomeId,txtAssessment" focus="#{td15hEntry.focusId}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblDocument" styleClass="label" value="Document"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddDocument" styleClass="ddlist"  style="width:125px;" size="1" value="#{td15hEntry.tdsDetails}" disabled="#{td15hEntry.docDisable}" >
                                    <f:selectItems value="#{td15hEntry.tdsDetailsList}"/>
                                    <a4j:support action="#{td15hEntry.setGridDocDetails}" event="onblur" reRender="stxtMsg,gridPanel5,gridPane120,gridPane121,ddTax"/>
                                </h:selectOneListbox>
                            </h:panelGrid>                            
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="taxLabel" styleClass="label" value="Tax Option"></h:outputLabel>
                            <h:selectOneListbox id="ddTax" styleClass="ddlist"  style="width:100px;" size="1" value="#{td15hEntry.taxOption}" disabled="#{td15hEntry.taxDisable}">
                                 <f:selectItems value="#{td15hEntry.taxOptionList}"/>
                                    <%--a4j:support action="#{td15hEntry.setFnOption}" event="onblur" reRender="" focus="#{td15hEntry.focusId}"/--%>
                              </h:selectOneListbox>
                            <h:outputLabel id="label1" styleClass="label" value="Customer ID"><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup layout="block">
                                    <h:inputText id="txtCustId" styleClass="input" maxlength="10" value="#{td15hEntry.custId}" style="display:#{td15hEntry.custIdStyle};width:80px;">
                                        <a4j:support action="#{td15hEntry.getAccountDetails}" event="onblur" oncomplete="if(#{td15hEntry.popupCustFlag=='true'}) #{rich:component('custView')}.show()"reRender="custView,stxtMsg,txtCustId,gridPanel103,gridPanel14,gridPanel16,gridPanel17,txtDetails,btnSave,gridPanel104,ddDocument,TotalNoId,AggregateAmountId,IncomeId,DeductAmtId,txtAssessment" />
                                    </h:inputText>
                                    <h:selectOneListbox id="ddCustID" styleClass="ddlist"  size="1" style="display:#{td15hEntry.verifyStyle};width:100px;" value="#{td15hEntry.custNumber}" >
                                        <f:selectItems value="#{td15hEntry.custList}"/>
                                        <a4j:support action="#{td15hEntry.getAccountDetails}" event="onblur" reRender="stxtMsg,txtCustId,gridPanel103,gridPanel14,gridPanel16,gridPanel17,txtDetails,btnSave,gridPanel104,ddDocument,TotalNoId,AggregateAmountId,IncomeId,DeductAmtId,txtAssessment"/>
                                    </h:selectOneListbox>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel14" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label9" styleClass="label" value="Customer Name"/>
                                <h:outputText id="stxtCustName"  styleClass="output" value="#{td15hEntry.custName}" />
                                <h:outputLabel id="label12" styleClass="label" value="Father Name"/>
                                <h:outputText id="stxtFatherName" styleClass="output" value="#{td15hEntry.fatherName}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="labelDob" styleClass="label" value="Date Of Birth"/>
                                <h:outputText id="stxtDob" styleClass="output" value="#{td15hEntry.dob}"/>
                                <h:outputLabel id="label13" styleClass="label" value="Pan No"/>
                                <h:outputText id="stxtJtUgName" styleClass="output" value="#{td15hEntry.panNo}" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5"  styleClass="row2">
                                <h:outputText  value="Whether assessed to tax under the Income-tax Act, 1961" styleClass="label" />
                                <h:selectBooleanCheckbox id="incomeId"value="#{td15hEntry.incomeTaxChecking}" disabled="#{td15hEntry.disableForExampt}">
                                    <a4j:support event="onchange" actionListener="#{td15hEntry.assessmentControl}" reRender="newreportId,txtAssessment,errorMsg" oncomplete="setMask();"/>  
                                </h:selectBooleanCheckbox>
                                <h:outputLabel id="assessmentoId"value="Latest assessment year" styleClass="label"/>
                                <h:inputText id="txtAssessment" value="#{td15hEntry.assYear}" styleClass="input" size="8" maxlength="4" onkeyup="this.value=this.value.toUpperCase();" disabled="#{td15hEntry.disableAssYear}">
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="6" id="gridPane120"  styleClass="row1">        
                                <h:outputLabel value="Total No. of Form 15G filed" styleClass="label"/>
                                <h:inputText id="TotalNoId" styleClass="input" value="#{td15hEntry.totalNoForm}" size="8" maxlength="100" disabled="#{td15hEntry.disableForExampt}"/>  
                                <h:outputLabel value="Aggregate Amount of income for which Form No. 15G/15H filed" styleClass="label"/>
                                <h:inputText id="AggregateAmountId" styleClass="input" value="#{td15hEntry.aggregateAmount}" size="8" maxlength="100" disabled="#{td15hEntry.disableForExampt}"/>  
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="6" id="gridPane121"  styleClass="row2">       
                                <h:outputLabel value="Income from other resources" styleClass="label"/>
                                <h:inputText id="IncomeId" styleClass="input" value="#{td15hEntry.otherIncome}" size="8" maxlength="100" disabled="#{td15hEntry.disableForExampt}"/>  
                                <h:outputLabel value="Deductions Amount" styleClass="label"/>
                                <h:inputText id="DeductAmtId" styleClass="input" value="#{td15hEntry.deductionAmt}" size="8" maxlength="100" disabled="#{td15hEntry.disableForExampt}"/>  
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel19" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label15" styleClass="label" value="Date Of Submission"/>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calDtOfSubmmition" value="#{td15hEntry.dates}" disabled="true" inputSize="8">                                
                                </rich:calendar>
                                <h:outputLabel id="label16" styleClass="label" value="FYear"/>
                                <h:inputText id="txtFyear" value="#{td15hEntry.fYear}" disabled="true" styleClass="input" style="width:60px" maxlength="4"/>
                            </h:panelGrid>                        
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:195px;display:#{td15hEntry.addVisible};">
                            <rich:dataTable value="#{td15hEntry.tdEntries}" var="dataItem" rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="6"><h:outputText value="Account Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Account No"/></rich:column>
                                        <rich:column><h:outputText value="Voucher No"/></rich:column>
                                        <rich:column><h:outputText value="Date Of Creation"/></rich:column>
                                        <rich:column><h:outputText value="Date Of Maturity"/></rich:column>
                                        <rich:column><h:outputText value="Status"/></rich:column>
                                        <rich:column><h:outputText value="Projected Interest"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.accountNumber}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.vchNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dateOfSub}"/></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.dateOfmat}"/></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.rFlag}"/></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.projectedInt}"/></rich:column>

                            <f:facet name="footer">
                                <rich:columnGroup style="background-color: #b0c4de;">
                                    <rich:column>Total Interest</rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column style="text-align:left;">
                                        <h:outputText value="#{td15hEntry.totalProjectedInt}"><f:convertNumber   pattern="####.00"  />
                                        </h:outputText>
                                    </rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <%--rich:column >
                                <h:outputText value="#{dataItem.rFlag}" style="cursor:pointer;" />
                                <a4j:support action="#{td15hEntry.changeStatus}" ajaxSingle="true" event="ondblclick" reRender="taskList,stxtMsg">
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{td15hEntry.currentItem}"/>
                                </a4j:support>
                            </rich:column--%>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />                            
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel104" width="100%" styleClass="row2" style="height:195px;display:#{td15hEntry.othVisible};">
                        <rich:dataTable value="#{td15hEntry.tdEntries}" var="dataItem" rowClasses="row1,row2" id="taskList1" rows="5" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="8"><h:outputText value="TDS Documents Details"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Seq No"/></rich:column>
                                    <rich:column><h:outputText value="Account No"/></rich:column>
                                    <rich:column><h:outputText value="Date Of Submission"/></rich:column>
                                    <rich:column><h:outputText value="FYear"/></rich:column>
                                    <rich:column><h:outputText value="TDS Document"/></rich:column>
                                    <rich:column><h:outputText value="Unique Identification No."/></rich:column>
                                    <rich:column><h:outputText value="Status"/></rich:column>
                                    <rich:column><h:outputText value="Auth"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.seqNumber}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.accountNumber}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dateOfSub}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.fYear}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.tdDtl}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.uni}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.rFlag}"/></rich:column>
                            <%--rich:column >
                                <h:outputText value="#{dataItem.rFlag}" style="cursor:pointer;" />
                                <a4j:support action="#{td15hEntry.changeStatus}" ajaxSingle="true" event="ondblclick" reRender="taskList1,stxtMsg">
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{td15hEntry.currentItem}"/>
                                </a4j:support>
                            </rich:column--%>
                            <rich:column><h:outputText value="#{dataItem.authStatus}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList1" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPaneMsg" style="width:100%;text-align:left;" styleClass="row2" width="100%">
                        <h:outputText id="stxtDispMsg" styleClass="msg" value="#{td15hEntry.disMsg}"/>
                    </h:panelGrid>        
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="#{td15hEntry.btnValue}" action="#{td15hEntry.populateMessage}" oncomplete="#{rich:component('processPanel')}.show();setMask();"  reRender="processPanel,confirmid,stxtFatherName,calDtOfSubmmition,txtDetails,stxtUser,stxtMsg,txtCustId,stxtDob,stxtCustName,stxtJtUgName,gridPanel103,txtFyear,taskList,taskList1" disabled="#{td15hEntry.saveDisable}"/>                                                           
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{td15hEntry.resetAllValue}" reRender="mainPanel,stxtFatherName,stxtMsg,txtCustId,stxtCustName,stxtJtUgName,txtDetails,gridPanel103,txtFyear,calDtOfSubmmition,gridPanel16,btnSave,ddFunc,gridPanel104,gridPanel15,msg" focus="ddFunc"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{td15hEntry.exitFrm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

                <a4j:region id="processActionRegion">
                    <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText id="confirmid" value="#{td15hEntry.confirmationMsg}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYes" action="#{td15hEntry.save}" onclick="#{rich:component('processPanel')}.hide();" 
                                                               reRender="message,mainPanel"/>
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

                <rich:modalPanel id="custView" height="350" width="500" style="align:right" autosized="true">
                    <f:facet name="header">
                        <h:outputText value="Customer Detail Alert !" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="glheadlink1"/>
                            <rich:componentControl for="custView" attachTo="glheadlink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="mainPane2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGrid id="custViewRow14" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                                <h:outputLabel value="Mandatory fields for 15G / 15H fill up.So please check / verify all fields from Customer Detail Form,then fill up !" styleClass="output" style="color:red;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewRow1" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                                <h:outputLabel value="Customer Name should not be blank." styleClass="output" style="color:blue;"/>
                                <h:outputLabel value="Pan No should not be blank." styleClass="output" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewRow3" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1">
                                <h:outputLabel value="Date of birth should not be blank." styleClass="output" style="color:blue;"/>
                                <h:outputLabel value="Flat/Door/Block No. should not be blank." styleClass="output" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewRow5" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                                <h:outputLabel value="Village / Sector should not be blank." styleClass="output" style="color:blue;"/>
                                <h:outputLabel value="City/District should not be blank." styleClass="output" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewRow7" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                                <h:outputLabel value="State should not be blank." styleClass="output" style="color:blue;"/>
                                <h:outputLabel value="Country should not be blank." styleClass="output" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewRow9" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                                <h:outputLabel value="Mobile No should be 10 digits." styleClass="output" style="color:blue;"/>
                                <h:outputLabel value="" styleClass="output" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewRow11" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1">
                                <h:outputLabel value="Postal Code should be 6 digits" styleClass="output" style="color:blue;"/>
                                <h:outputLabel value="Email should not be blank." styleClass="output" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewRow13" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2" >
                                <h:outputLabel value="Residential Status should not be blank." styleClass="output" style="color:blue;"/>
                                <h:outputLabel value="Permanent address line1 should not be blank." styleClass="output" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid id="custViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="custViewBtnPanel">
                                    <a4j:commandButton id="custViewClose" value="OK" action="#{td15hEntry.resetAllValue}" oncomplete="#{rich:component('custView')}.hide(); return false;" reRender="mainPanel">
                                    </a4j:commandButton>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel> 

                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>            
        </body>
    </html>
</f:view>
