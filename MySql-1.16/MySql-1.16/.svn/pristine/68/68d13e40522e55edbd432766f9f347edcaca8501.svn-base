<%-- 
    Document   : AcctEnquery
    Created on : 1 Nov, 2010, 2:58:09 PM
    Author     : Zeeshan Waris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>

        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Customer Enquiry</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>

        <body>
            <a4j:form id="Form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="header" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AcctEnquery.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Customer Enquiry"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AcctEnquery.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>


                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel01" style="width:100%;text-align:center;" styleClass="row2">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{AcctEnquery.message}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1" columns="3" id="Panel0" style="width:100%;" styleClass="row1">

                            <h:outputLabel id="lblModeOfInquery" styleClass="label"  value="Mode Of Enquiry"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddGotTo" value="#{AcctEnquery.modeOfInquery}" size="1" style="width:160px" styleClass="ddlist">
                                <f:selectItems value="#{AcctEnquery.ddModeOfInq}"/>
                                <a4j:support ajaxSingle="true" action="#{AcctEnquery.mode}" event="onchange" reRender="stxtMsg,subbodyPanelmain,stxtEnqueryMode" />
                            </h:selectOneListbox>
                            <h:outputText id="stxtEnqueryMode" styleClass="output" value="#{AcctEnquery.showMode}" style="color:green;"/>

                        </h:panelGrid>
                        <h:panelGrid columns="1" id="subbodyPanelmain" style="width:100%;">

                            <h:panelGrid columns="1" id="subbodyPanel1" style="width:100%;" rendered="#{AcctEnquery.modeOfInquery == '1'}">
                                <h:panelGrid columnClasses="col1,col1" columns="3" id="PanelStartPage" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblaccountDetails" styleClass="label"  value="Account Number"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtaccountDetails" style="width: 150px"  value="#{AcctEnquery.oldAcctNo}" maxlength="#{AcctEnquery.acNoMaxLen}" styleClass="input">
                                        <a4j:support action="#{AcctEnquery.detailAcctWise}"  event="onblur" reRender="tableAccountWise,stxtStAccountNo,subbodyPanel1,stxtEnqueryMode,stxtMsg,txtBalance"/>
                                    </h:inputText>
                                    <h:outputText id="stxtStAccountNo" styleClass="output" value="#{AcctEnquery.acctNo}" />
                                 <%--   <h:outputText id="stxtStAccountNo" styleClass="output" value="#{AcctEnquery.stAcctNo}" /> --%>
                                </h:panelGrid>

                                <rich:panel header="Customer Infromation" style="text-align:left;" >
                                    <h:panelGrid columns="1" id="panelcustomerInfromation" style="width:100%;">
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel1" style="width:100%;" styleClass="row1">
                                            <h:outputLabel id="lblAccountNo" styleClass="label"  value="Account No"/>
                                            <h:inputText id="txtAccountNo" disabled="true"style="width: 134px" value="#{AcctEnquery.accountNo}" styleClass="input" />
                                            <h:outputLabel id="lblCustomerName" styleClass="label"  value="CustomerName"/>
                                            <h:inputText id="txtCustomerName" disabled="true"style="width: 134px" value="#{AcctEnquery.customerName}" styleClass="input" />
                                            <h:outputLabel id="lblsonOf" value="S/O|D/O|W/O" styleClass="label"/>
                                            <h:inputText id="txtsonOf" disabled="true"style="width: 134px" value="#{AcctEnquery.daughterOF}" styleClass="input" />

                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel2" style="width:100%;" styleClass="row2">
                                            <h:outputLabel id="lblContactNo" styleClass="label"  value="Contact No."/>
                                            <h:inputText id="txtContactNo" disabled="true"style="width: 134px" value="#{AcctEnquery.contactNo}" styleClass="input" />
                                            <h:outputLabel id="lblBalance" styleClass="label"  value="Balance"/>
                                            <h:inputText id="txtBalance" disabled="true"style="width: 134px" value="#{AcctEnquery.balance}" styleClass="input">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                            </h:inputText>
                                            <h:outputLabel id="lblJointName1" styleClass="label"  value="Joint Name1"/>
                                            <h:inputText id="txtJointName1" disabled="true"style="width: 134px" value="#{AcctEnquery.jointName1}" styleClass="input"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel3" style="width:100%;" styleClass="row1">

                                            <h:outputLabel id="lblJointName2" value="Joint Name2" styleClass="label" />
                                            <h:inputText id="txtJointName2" disabled="true"style="width: 134px" value="#{AcctEnquery.jointName2}"styleClass="input"/>
                                            <h:outputLabel id="lblJointName3" value="Joint Name3" styleClass="label"/>
                                            <h:inputText id="txtJointName3" disabled="true"style="width: 134px" value="#{AcctEnquery.jointName3}" styleClass="input" />
                                            <h:outputLabel id="lblJointName4" value="JointName4" styleClass="label" />
                                            <h:inputText id="txtJointName4" disabled="true"style="width: 134px" value="#{AcctEnquery.jointName4}" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col3" columns="6" id="Panel4" style="width:100%;" styleClass="row2">

                                            <h:outputLabel id="lblAddress" styleClass="label"  value="Address"/>
                                            <h:inputText id="txtAddress" disabled="true"style="width: 570px" value="#{AcctEnquery.address}" styleClass="input" />
                                            <h:outputLabel id="lbl1" styleClass="label" />
                                            <h:outputLabel id="lbl2" styleClass="label" />
                                            <h:outputLabel id="lbl3" styleClass="label" />
                                            <h:outputLabel id="lbl4" styleClass="label" />
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </rich:panel>

                                <rich:panel header="Outword Clearing Pending Transactions" style="text-align:left;">

                                    <h:panelGrid columnClasses="vtop" columns="1" id="tableAccountWise" width="100%" styleClass="row2" style="height:168px;">

                                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                            <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                           actionListener="#{AcctEnquery.fetchCurrentRow}">
                                                <a4j:actionparam name="Scheme" value="{Scheme}"/>
                                                <a4j:actionparam name="row" value="{currentRow}" />
                                            </rich:menuItem>
                                        </rich:contextMenu>

                                        <a4j:region>
                                            <rich:dataTable value="#{AcctEnquery.accountWiseTable}" var="item"
                                                            rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>

                                                        <rich:column colspan="3">

                                                        </rich:column>

                                                        <rich:column breakBefore="true" >
                                                            <h:outputText value="Instrument #"   />
                                                        </rich:column>
                                                        <rich:column >
                                                            <h:outputText value="Amount" />
                                                        </rich:column>

                                                        <rich:column >
                                                            <h:outputText value="Entry Date" />
                                                        </rich:column>


                                                    </rich:columnGroup>
                                                </f:facet>

                                                <rich:column>
                                                    <h:outputText value="#{item.txninstNo}" />
                                                </rich:column>

                                                <rich:column>
                                                    <h:outputText value="#{item.txninStmt}" />
                                                </rich:column>
                                                <rich:column>
                                                    <h:outputText value="#{item.date}" />
                                                </rich:column>



                                            </rich:dataTable>
                                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                                        </a4j:region>
                                    </h:panelGrid>
                                </rich:panel>

                            </h:panelGrid>

                            <h:panelGrid columns="1" id="subbodyPanel2" style="width:100%;" rendered="#{AcctEnquery.modeOfInquery == '2'}">
                                <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" columns="8" id="Panel10" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblBrCode" styleClass="label"  value="Branch"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width: 70px" value="#{AcctEnquery.branch}" >
                                        <f:selectItems value="#{AcctEnquery.branchList}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblStatus" styleClass="label"  value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>

                                    <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" style="width: 70px" value="#{AcctEnquery.statusVariable}" >
                                        <f:selectItems value="#{AcctEnquery.statusDd}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblAccountType" styleClass="label"  value="Account Type"><font class="required" style="color:red;">*</font></h:outputLabel>

                                    <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 70px" value="#{AcctEnquery.acctTypeNamewise}" >
                                        <f:selectItem itemValue="All"/>
                                        <f:selectItems value="#{AcctEnquery.ddAccountType}"/>
                                    </h:selectOneListbox>

                                    <h:outputLabel id="lblByFirstName" styleClass="label"  value="Find By First Name/Last Name"><font class="required" style="color:red;">*</font></h:outputLabel>

                                    <h:selectOneListbox id="ddByFirstName" styleClass="ddlist" size="1" style="width: 70px" value="#{AcctEnquery.nameMode}" >

                                        <f:selectItems value="#{AcctEnquery.nameWiseDd}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel11" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblCustName" styleClass="label"  value="Customer Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtCustName" style="width: 134px" value="#{AcctEnquery.name}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" >
                                        <a4j:support action="#{AcctEnquery.getTableNameWiseCustomerDetails}"  event="onblur" reRender="nameWisePanel,tableCustomerDetails,ddBranch,ddStatus,ddAccountType,ddByFirstName,stxtMsg"/>
                                    </h:inputText>
                                    <h:outputLabel id="lblDefault1" styleClass="label"/>
                                    <h:outputLabel id="lblDefault2" styleClass="label"/>
                                    <h:outputLabel id="lblDefault3" styleClass="label"/>
                                    <h:outputLabel id="lblDefault4" styleClass="label"/>
                                </h:panelGrid>

                                <h:panelGrid columns="2" id="nameWisePanel" style="width:100%;">
                                    <h:panelGrid columns="1" id="nameWisePanelLeft" style="width:100%;">
                                        <rich:panel header="CustomerDetails" style="text-align:left;">

                                            <h:panelGrid columnClasses="vtop" columns="1" id="tableCustomerDetails" width="100%" styleClass="row2" style="height:168px;">

                                                <rich:contextMenu attached="false" id="menuCustomerDetails" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                                   actionListener="#{AcctEnquery.fetchCurrentRow}">
                                                        <a4j:actionparam name="Cust Name" value="{Cust Name}"/>
                                                        <a4j:actionparam name="row" value="{currentRow5}" />
                                                    </rich:menuItem>
                                                </rich:contextMenu>

                                                <a4j:region>
                                                    <rich:dataTable value="#{AcctEnquery.custDetailtable}" var="item5" 
                                                                    rowClasses="row1, row2" id="ListCustomerDetails" rows="3" columnsWidth="100" rowKeyVar="row"
                                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                        <f:facet name="header">
                                                            <rich:columnGroup>

                                                                <rich:column colspan="6">

                                                                </rich:column>

                                                                <rich:column breakBefore="true" >
                                                                    <h:outputText value="Account No."   />
                                                                </rich:column>
                                                                <rich:column >
                                                                    <h:outputText value="Cust Name" />
                                                                </rich:column>
                                                                <rich:column >
                                                                    <h:outputText value="S/O|W/O/D/O" />
                                                                </rich:column>

                                                                <rich:column >
                                                                    <h:outputText value="Cust.Address" />
                                                                </rich:column>
                                                                <rich:column >
                                                                    <h:outputText value="Phone No." />
                                                                </rich:column>
                                                                <rich:column >
                                                                    <h:outputText value="Select" />
                                                                </rich:column>


                                                            </rich:columnGroup>
                                                        </f:facet>

                                                        <rich:column>
                                                            <h:outputText value="#{item5.acno}" />
                                                        </rich:column>

                                                        <rich:column>
                                                            <h:outputText value="#{item5.custname}" />
                                                        </rich:column>
                                                        <rich:column>
                                                            <h:outputText value="#{item5.fname}" />
                                                        </rich:column>
                                                        <rich:column>
                                                            <h:outputText value="#{item5.craddress}" />
                                                        </rich:column>
                                                        <rich:column>
                                                            <h:outputText value="#{item5.phoneno}" />
                                                        </rich:column>

                                                        <rich:column>
                                                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{AcctEnquery.select}" reRender="tableJointName,Panel12,Panel13,Panel14,tableClearingPendingTransactions,stxtMsg">
                                                                <h:graphicImage value="/resources/images/passed.gif" style="border:0" />

                                                                <f:setPropertyActionListener value="#{item5}" target="#{AcctEnquery.currentItem5}"/>
                                                                <f:setPropertyActionListener value="#{row}" target="#{AcctEnquery.currentRow5}" />
                                                            </a4j:commandLink>
                                                            <rich:toolTip for="selectlink" value="Select" />
                                                        </rich:column>




                                                    </rich:dataTable>
                                                    <rich:datascroller id="scroller20" align="left" for="ListCustomerDetails" maxPages="5" />
                                                </a4j:region>
                                            </h:panelGrid>
                                        </rich:panel>

                                        <rich:panel header="JointName" style="text-align:left;">

                                            <h:panelGrid columnClasses="vtop" columns="1" id="tableJointName" width="100%" styleClass="row2" style="height:180px;">

                                                <rich:contextMenu attached="false" id="menuJointName" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                                   actionListener="#{AcctEnquery.fetchCurrentRow}">
                                                        <a4j:actionparam name="Scheme" value="{Scheme}"/>
                                                        <a4j:actionparam name="row" value="{currentRow}" />
                                                    </rich:menuItem>
                                                </rich:contextMenu>

                                                <a4j:region>
                                                    <rich:dataTable value="#{AcctEnquery.jointNametable}" var="itemJt"
                                                                    rowClasses="row1, row2" id="ListJointName" rows="3" columnsWidth="100" rowKeyVar="row"
                                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                        <f:facet name="header">
                                                            <rich:columnGroup>

                                                                <rich:column colspan="4">

                                                                </rich:column>

                                                                <rich:column breakBefore="true" >
                                                                    <h:outputText value="Jt. Name1"   />
                                                                </rich:column>
                                                                <rich:column >
                                                                    <h:outputText value="Jt. Name2" />
                                                                </rich:column>

                                                                <rich:column >
                                                                    <h:outputText value="Jt. Name3" />
                                                                </rich:column>
                                                                <rich:column >
                                                                    <h:outputText value="Jt. Name4" />
                                                                </rich:column>


                                                            </rich:columnGroup>
                                                        </f:facet>

                                                        <rich:column>
                                                            <h:outputText value="#{itemJt.jtname1}" />
                                                        </rich:column>

                                                        <rich:column>
                                                            <h:outputText value="#{itemJt.jtname2}" />
                                                        </rich:column>
                                                        <rich:column>
                                                            <h:outputText value="#{itemJt.jtname3}" />
                                                        </rich:column>
                                                        <rich:column>
                                                            <h:outputText value="#{itemJt.jtname4}" />
                                                        </rich:column>



                                                    </rich:dataTable>
                                                    <rich:datascroller id="scrollerJointName" align="left" for="ListJointName" maxPages="5" />
                                                </a4j:region>
                                            </h:panelGrid>
                                        </rich:panel>
                                    </h:panelGrid>



                                    <h:panelGrid columns="1" id="nameWisePanelRight" style="width:100%;">
                                        <rich:panel header="Customer Balancing Information" style="text-align:left;">
                                            <h:panelGrid columnClasses="col1,col2,col1,col2" columns="2" id="Panel12" style="width:100%;" styleClass="row2">
                                                <h:outputLabel id="lblAccountNumb" styleClass="label"  value="Account Number"/>
                                                <h:inputText id="txtAccountNumb" style="width: 134px" value="#{AcctEnquery.acctNoBalancing}" disabled="#{AcctEnquery.disableValue}" styleClass="input"/>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col1,col2,col1,col2" columns="2" id="Panel13" style="width:100%;" styleClass="row1">
                                                <h:outputLabel id="lblCustomerName1" styleClass="label"  value="Customer Name"/>
                                                <h:inputText id="txtCustomerName1" style="width: 134px" value="#{AcctEnquery.custNameBalancing}" disabled="#{AcctEnquery.disableValue}" styleClass="input"/>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col1,col2,col1,col2" columns="2" id="Panel14" style="width:100%;" styleClass="row2">
                                                <h:outputLabel id="lblBalance1" styleClass="label"  value="Balance"/>
                                                <h:inputText id="txtBalance1" style="width: 134px" value="#{AcctEnquery.balanceBalancing}" disabled="#{AcctEnquery.disableValue}" styleClass="input">
                                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                                </h:inputText>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col1,col2,col1,col2" columns="1" id="Panel15" style="width:100%;" styleClass="row1">
                                                <h:outputLabel id="lblBalance11" styleClass="label"  value=""/>

                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col1,col2,col1,col2" columns="1" id="Panel16" style="width:100%;" styleClass="row2">
                                                <h:outputLabel id="lblBalance12" styleClass="label"  value=""/>

                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col1,col2,col1,col2" columns="1" id="Panel17" style="width:100%;" styleClass="row1">
                                                <h:outputLabel id="lblBalance13" styleClass="label"  value=""/>

                                            </h:panelGrid>
                                        </rich:panel>
                                        <rich:panel header="O/W Clearing Pending Transactions" style="text-align:left;">
                                            <h:panelGrid columnClasses="vtop" columns="1" id="tableClearingPendingTransactions" width="100%" styleClass="row2" style="height:168px;">

                                                <rich:contextMenu attached="false" id="menuClearingPendingTransactions" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                                   actionListener="#{AcctEnquery.fetchCurrentRow}">
                                                        <a4j:actionparam name="Scheme" value="{Scheme}"/>
                                                        <a4j:actionparam name="row" value="{currentRow}" />
                                                    </rich:menuItem>
                                                </rich:contextMenu>

                                                <a4j:region>
                                                    <rich:dataTable value="#{AcctEnquery.clearingTable}" var="itemCl"
                                                                    rowClasses="row1, row2" id="ListClearingPendingTransactions" rows="3" columnsWidth="100" rowKeyVar="row"
                                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                        <f:facet name="header">
                                                            <rich:columnGroup>

                                                                <rich:column colspan="3">

                                                                </rich:column>

                                                                <rich:column breakBefore="true" >
                                                                    <h:outputText value="Instrument #" />
                                                                </rich:column>
                                                                <rich:column >
                                                                    <h:outputText value="Amount" />
                                                                </rich:column>

                                                                <rich:column >
                                                                    <h:outputText value="Entry Date" />
                                                                </rich:column>


                                                            </rich:columnGroup>
                                                        </f:facet>

                                                        <rich:column>
                                                            <h:outputText value="#{itemCl.txnNum}" />
                                                        </rich:column>

                                                        <rich:column>
                                                            <h:outputText value="#{itemCl.txnAmt}" />
                                                        </rich:column>
                                                        <rich:column>
                                                            <h:outputText value="#{itemCl.txnDt}" />
                                                        </rich:column>
                                                    </rich:dataTable>
                                                    <rich:datascroller id="scrollerClearingPendingTransactions" align="left" for="ListClearingPendingTransactions" maxPages="5" />
                                                </a4j:region>
                                            </h:panelGrid>
                                        </rich:panel>
                                    </h:panelGrid>
                                </h:panelGrid>

                            </h:panelGrid>

                        </h:panelGrid>

                    </h:panelGrid>




                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <%--<a4j:commandButton id="btnRemmitEnquiry" value="Remmit Enquiry" disabled="#{AcctEnquery.flag}"></a4j:commandButton>--%>
                            <a4j:commandButton id="btnAcView" value="A/c's View" oncomplete="if(#{AcctEnquery.accountNo==null}){#{rich:component('acView')}.hide();} else if(#{AcctEnquery.accountNo==''}){#{rich:component('acView')}.hide();} else{#{rich:component('acView')}.show();}" reRender="acView,stxtMsg" action="#{AcctEnquery.acctViewAuthUnAuth}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AcctEnquery.btnRefAct}" reRender="stxtMsg,ddGotTo,stxtEnqueryMode,txtaccountDetails,stxtStAccountNo,txtAccountNo,txtJointName1,txtCustomerName,txtJointName2,txtAddress,txtJointName3,txtContactNo,txtJointName4,txtBalance,txtsonOf,taskList,ddBranch,ddStatus,ddAccountType,txtCustName,ddByFirstName,ListJointName,
                                               nameWisePanelRight,ListCustomerDetails"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AcctEnquery.btnExit}" reRender="stxtMsg,ddGotTo,stxtEnqueryMode,txtaccountDetails,stxtStAccountNo,txtAccountNo,txtJointName1,txtCustomerName,txtJointName2,txtAddress,txtJointName3,txtContactNo,txtJointName4,txtBalance,txtsonOf,taskList,ddBranch,ddStatus,ddAccountType,txtCustName,ddByFirstName,ListJointName,
                                               nameWisePanelRight,ListCustomerDetails">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>

                <rich:modalPanel id="acView" height="560" width="900" left="true">
                    <f:facet name="header">
                        <h:outputText value="Account Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="acView" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="acViewPanel" width="100%">
                            <h:panelGrid id="acViewRow1" columns="6" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row1" style="text-align:left;">
                                <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                                <h:outputText id="txtAccNo" style="output" value="#{AcctEnquery.accountNo}"/>
                                <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance:"/>
                                <h:outputText id="txtOpeningBal" style="output" value="#{AcctEnquery.openBalance}">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                                <h:outputLabel id="lblPresentBal" styleClass="label" value="Present Balance:"/>
                                <h:outputText id="txtPresentBal" style="output" value="#{AcctEnquery.presentBalance}">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>

                            <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                                <rich:dataTable  var="txnItem" value="#{AcctEnquery.txnDetailList}"
                                                 rowClasses="gridrow1, gridrow2" id="authTxnList" rows="8"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="8"><h:outputText value="Account Details" /></rich:column>
                                            <rich:column breakBefore="true" style="width:"><h:outputText value="Date" /></rich:column>
                                            <rich:column><h:outputText value="Particulars" /></rich:column>
                                            <rich:column><h:outputText value="Cheque No." /></rich:column>
                                            <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                            <rich:column><h:outputText value="Deposite" /></rich:column>
                                            <rich:column><h:outputText value="Balance" /></rich:column>
                                            <rich:column><h:outputText value="Entered By" /></rich:column>
                                            <rich:column><h:outputText value="Authorized" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{txnItem.dt}"/></rich:column>
                                    <rich:column><h:outputText value="#{txnItem.details}"/></rich:column>
                                    <rich:column><h:outputText value="#{txnItem.instNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{txnItem.drAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{txnItem.crAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{txnItem.amount}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{txnItem.enterBy}"/></rich:column>
                                    <rich:column><h:outputText  value="#{txnItem.authBy}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="authTxnList" maxPages="20" />
                            </h:panelGrid>


                            <h:panelGrid id="pendingTxnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                                <rich:dataTable  var="unAuthItem" value="#{AcctEnquery.txnDetailUnAuthList}"
                                                 rowClasses="gridrow1, gridrow2" id="pendingTxnList" rows="5"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="8"><h:outputText value="Today's Pending Authorization" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Date" /></rich:column>
                                            <rich:column><h:outputText value="Particulars" /></rich:column>
                                            <rich:column><h:outputText value="Cheque No." /></rich:column>
                                            <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                            <rich:column><h:outputText value="Deposite" /></rich:column>
                                            <rich:column><h:outputText value="Balance" /></rich:column>
                                            <rich:column><h:outputText value="Entered By" /></rich:column>
                                            <rich:column><h:outputText value="Authorized" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{unAuthItem.dt}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.details}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.instNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.drAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.crAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.amount}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.enterBy}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.authBy}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="unauthDataScroll"align="left" for="pendingTxnList" maxPages="20" />
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose" value="Close" onclick="#{rich:component('acView')}.hide(); return false;">

                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                 <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>

            </a4j:form>
        </body>
    </html>
</f:view>
