<%-- 
    Document   : NewEmployeeDetails
    Created on : Oct 25, 2013, 10:16:19 AM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Employee Details</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                function setMask() {
                    jQuery(".instDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headepanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{newEmployeeDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Employee Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{newEmployeeDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2" columns="2" id="errPanel" style="text-align:center;" width="100%" >
                        <h:outputText id="errMsgGeneralDetails" value="#{newEmployeeDetails.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row0" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel styleClass="label" value="Function "><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" value="#{newEmployeeDetails.function}" styleClass="ddlist" style="width:100px" size="1">
                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                            <f:selectItem itemValue="save" itemLabel="Add"/>
                            <f:selectItem itemValue="update" itemLabel="Edit"/>
                            <a4j:support event="onblur" action="#{newEmployeeDetails.onChangeFunction}"
                                         oncomplete="if(#{newEmployeeDetails.function=='update'})
                                         {#{rich:component('popUpGridPanel')}.show();}
                                         if(#{newEmployeeDetails.function=='save'})
                                         {#{rich:component('popUpGridPanel')}.hide();};setMask();"
                                         reRender="errMsgGeneralDetails,popUpGridPanel,saveGeneralDetails,
                                         txtEmpIdGeneralDetails,txtLastEmpIdGeneralDetails"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Emp Id"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtEmpIdGeneralDetails" value="#{newEmployeeDetails.empId}" maxlength="8" disabled="true" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                        <h:outputLabel styleClass="label" value="Last Emp Id"/>
                        <h:inputText id="txtLastEmpIdGeneralDetails" value="#{newEmployeeDetails.lastEmpId}" styleClass="input" disabled="true" style="width:100px;"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel styleClass="label" value="Customer Id"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtCustId" value="#{newEmployeeDetails.customerId}" maxlength="10" styleClass="input" style="width:90px;" onkeyup="this.value = this.value.toUpperCase();" disabled="#{newEmployeeDetails.custDisable}">
                            <a4j:support event="onblur" action="#{newEmployeeDetails.customerDetail}" reRender="errMsgGeneralDetails,txtEmployeeFName,txtEmployeeMName,
                                         txtEmployeeLName,calDateofbirth,ddSex,ddLoanAc,txtAccountNo,txtEmpCardNo,calJoiningDate,ddStatus,ddPaymentMode,txtConAddressLine,txtConCity,
                                         txtConState,txtConPin,txtConPhone,txtPerAddressLine,txtPerCity,txtPerState,txtPerPin,
                                         txtPerPhone" oncomplete="#{rich:element('txtAccountNo')}.focus();setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Employee Name"/>
                        <h:inputText id="txtEmployeeFName" value="#{newEmployeeDetails.empFName}" maxlength="70" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" disabled="#{newEmployeeDetails.fieldDisable}"/>
                        <h:inputText id="txtEmployeeMName" value="#{newEmployeeDetails.empMName}" maxlength="70" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" disabled="#{newEmployeeDetails.fieldDisable}"/>
                        <h:inputText id="txtEmployeeLName" value="#{newEmployeeDetails.empLName}" maxlength="70" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" disabled="#{newEmployeeDetails.fieldDisable}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel styleClass="label" value="Date of birth"/>
                        <h:inputText id="calDateofbirth" value="#{newEmployeeDetails.dateOfBirth}" styleClass="input instDt" size="10" maxlength="10" disabled="#{newEmployeeDetails.fieldDisable}" style="width:90px;"></h:inputText>
                        <h:outputLabel styleClass="label" value="Sex"/>
                        <h:selectOneListbox id="ddSex" value="#{newEmployeeDetails.sex}" styleClass="ddlist" size="1" style="width:100px;" disabled="#{newEmployeeDetails.fieldDisable}">
                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                            <f:selectItem itemValue="M" itemLabel="Male"/>
                            <f:selectItem itemValue="F" itemLabel="Female"/>
                            <f:selectItem itemValue="O" itemLabel="Other"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Loan A/c Numbers"/>
                        <h:selectOneListbox id="ddLoanAc" value="#{newEmployeeDetails.loanAccount}" styleClass="ddlist" size="1" style="width:100px;" >
                            <f:selectItems value="#{newEmployeeDetails.loanAccountList}"/>
                            <a4j:support event="onblur" action="#{newEmployeeDetails.onChangeLoanAc}"
                                         oncomplete="if(#{newEmployeeDetails.loanAccount!='0'})
                                         {#{rich:component('popUpLoanGridPanel')}.show();}
                                         else{#{rich:component('popUpLoanGridPanel')}.hide();};setMask();"
                                         reRender="errMsgGeneralDetails,popUpLoanGridPanel,
                                         stxtEmployeeId,stxtLoanAc,ddLoanStatus,loanEmi,taskList2,table2"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel styleClass="label" value="Salary Account"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtAccountNo" value="#{newEmployeeDetails.bankAccountNo}" styleClass="input" style="width:90px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="PPF A/c No."/>
                        <h:inputText id="txtPfAcno" value="#{newEmployeeDetails.pfAccount}" maxlength="35" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Employee Card No."/>
                        <h:inputText id="txtEmpCardNo" value="#{newEmployeeDetails.empCardNo}" maxlength="12" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel styleClass="label" value="Joining Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="calJoiningDate" value="#{newEmployeeDetails.joiningDate}" styleClass="input instDt" size="10" maxlength="10" style="width:90px;">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Employee Base Branch"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBaseBranch" value="#{newEmployeeDetails.baseBranch}" styleClass="ddlist" size="1" style="width:100px;" >
                            <f:selectItems value="#{newEmployeeDetails.baseBranchList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Employee Designation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddDesignation" value="#{newEmployeeDetails.designation}" styleClass="ddlist" size="1" style="width:100px;">
                            <f:selectItems value="#{newEmployeeDetails.designationList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                       
                        <h:selectOneListbox id="ddStatus" value="#{newEmployeeDetails.workStatusCode}" styleClass="ddlist" size="1" style="width:100px;">
                            <f:selectItems value="#{newEmployeeDetails.workStatusList}"/>
                             <a4j:support actionListener="#{newEmployeeDetails.retirementDetails()}" event="onblur" focus="btnSave" reRender="lblMsg,Row2,Row3,Row4,Row5,btnRegion"/>
                        </h:selectOneListbox>                    
                        <h:outputLabel styleClass="label" value="UAN No."/>
                        <h:inputText id="txtUANNo" value="#{newEmployeeDetails.uanNo}" maxlength="12" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur"/>
                        </h:inputText>              
                       <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanelGeneralDetails" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:region id="processActionRegion">
                            <h:panelGroup id="btnPanelGeneralDetails">
                                <a4j:commandButton id="saveGeneralDetails" value="Save" action="#{newEmployeeDetails.saveGeneralDetails}"
                                                   reRender="mainPanel,errMsgGeneralDetails"
                                                   oncomplete="setMask();" disabled="#{newEmployeeDetails.disableSaveButton}"/>

                                <a4j:commandButton id="cancelGeneralDetails" value="Cancel" 
                                                   action="#{newEmployeeDetails.cancelGeneralDetails}"
                                                   oncomplete="setMask();" reRender="mainPanel"/>
                                <a4j:commandButton id="exitGeneralDetails" value="Exit" 
                                                   action="#{newEmployeeDetails.exitGeneralDetails}"/>
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="300" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="mainPanelEmployeeSearch1"  columns="1" style="text-align:left;border:1px ridge #BED6F8;" width="100%">
                        <h:panelGrid id="PanelGridEmptable1"  styleClass="row2" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGroup layout="block">
                                <h:outputLabel value="Search Employee" styleClass="label"/>
                                &nbsp;
                                <h:inputText id="txtSearchValue1" value="#{newEmployeeDetails.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                &nbsp;
                                <h:outputLabel value="By" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox id="ddSearchCriteria1" value="#{newEmployeeDetails.empSearchCriteria}" size="1">
                                    <f:selectItem itemValue="Name"/>
                                    <f:selectItem itemValue="ID"/>
                                </h:selectOneListbox>
                                <a4j:commandButton value="Find" action="#{newEmployeeDetails.getEmployeeTableData}" reRender="table1,taskList1,errMsgGeneralDetails"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{newEmployeeDetails.empSearchTable}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="3" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Address" style="text-align:left"/></rich:column>
                                            <rich:column><h:outputText value="Phone Number" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empAddress}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empPhone}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" action="#{newEmployeeDetails.setEmpRowValues}"
                                                         oncomplete="#{rich:element('calDateofbirth')}.style=setMask();
                                                         #{rich:element('calJoiningDate')}.style=setMask();
                                                         #{rich:component('popUpGridPanel')}.hide()"
                                                         reRender="errMsgGeneralDetails,txtEmpIdGeneralDetails,
                                                         txtLastEmpIdGeneralDetails,popUpGridPanel,Row1,Row2,Row3,
                                                         Row4,Row5,Row6,ddLoanAc,txtPfAcno,ddBaseBranch,ddDesignation" focus="txtAccountNo">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{newEmployeeDetails.currentEmpItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close"  onclick="#{rich:component('popUpGridPanel')}.hide()" oncomplete="#{rich:element('calDateofbirth')}.style=setMask();"
                                           reRender="mainPanel,calDateofbirth,calJoiningDate,popUpGridPanel"/>
                    </h:panelGrid>
                </a4j:form>                            
            </rich:modalPanel>
            <rich:modalPanel id="popUpLoanGridPanel" label="Form" width="700" height="300" resizeable="true" 
                             moveable="false" onmaskdblclick="#{rich:component('popUpLoanGridPanel')}.hide();" 
                             onshow="#{rich:element('ddLoanStatus')}.focus();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Loan A/c Detail" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="loanDetailMainPanel"  columns="1" style="text-align:left;border:1px ridge #BED6F8;" width="100%">
                        <h:panelGrid id="loanStxtDetailGrid"  styleClass="row2" columnClasses="col13,col13,col13,col13" 
                                     columns="4" style="border:1px ridge #BED6F8" width="100%">
                            <h:outputLabel styleClass="label" value="Emp.Id"/>
                            <h:outputText id="stxtEmployeeId" value="#{newEmployeeDetails.mainEmpId}"/>
                            <h:outputLabel styleClass="label" value="Loan A/c"/>
                            <h:outputText id="stxtLoanAc" value="#{newEmployeeDetails.mainLoanAc}"/>
                        </h:panelGrid>
                        <h:panelGrid id="loanDetailFieldGrid"  styleClass="row1" columnClasses="col13,col13,col13,col13" 
                                     columns="4" style="border:1px ridge #BED6F8" width="100%">
                            <h:outputLabel styleClass="label" value="Status"/>
                            <h:selectOneListbox id="ddLoanStatus" value="#{newEmployeeDetails.loanAcStatus}" styleClass="ddlist" size="1" style="width:100px;" >
                                <f:selectItems value="#{newEmployeeDetails.loanAcStatusList}"/>
                                <%--a4j:support event="onblur" action="#{newEmployeeDetails.getDetailOnEmpIdAndLoanAc}" 
                                             reRender="loanEmi,errMsgGeneralDetails" oncomplete="#{rich:element('loanEmi')}.focus();setmask();"/--%>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <%--h:outputLabel styleClass="label" value="EMI"></h:outputLabel>
                            <h:inputText id="loanEmi" value="#{newEmployeeDetails.loanEmi}" styleClass="input" style="width:80px;"/--%>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table2" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{newEmployeeDetails.loanTable}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList2" rows="2" columnsWidth="100" 
                                                rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Loan A/c" style="text-align:left" /></rich:column>
                                            <%--rich:column><h:outputText value="Emi" style="text-align:left"/></rich:column--%>
                                            <rich:column><h:outputText value="Status" style="text-align:left" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.loanAc}" /></rich:column>
                                    <%--rich:column><h:outputText value="#{dataItem.emi}" /></rich:column--%>
                                    <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList2" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRichForLoan" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup>
                            <a4j:commandButton id="btnLoanSave" value="Save" action="#{newEmployeeDetails.saveInTable}" 
                                               oncomplete="setMask();" reRender="errMsgGeneralDetails,table2,taskList2"/>
                            <a4j:commandButton id="close" value="Close"  onclick="#{rich:component('popUpLoanGridPanel')}.hide()" 
                                               oncomplete="setMask();" reRender="mainPanel,popUpLoanGridPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>                            
            </rich:modalPanel>                    

            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>