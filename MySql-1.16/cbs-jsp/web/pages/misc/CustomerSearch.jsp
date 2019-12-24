<%--
    Document   : CustomerSearch
    Created on : Apr 30, 2010, 12:17:25 PM
    Author     : Administrator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Customer Search</title>
        </head>
        <body>
            <a4j:form id="CustomerSearch">

                <!-- Main Panel -->
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CustomerSearch.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Customer Search"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{CustomerSearch.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <!-- Message Panel -->
                    <h:panelGrid  id="errormsg" width="100%" style="width:100%;text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{CustomerSearch.msg}" styleClass="error"/>
                    </h:panelGrid>

                    <!-- Panel 1-->
                    <h:panelGrid id="PanelGrid1" styleClass="row2" bgcolor="#fff" columns="4" style="border:1px ridge #BED6F8;text-align:center"  width="100%">
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lblCriteria" styleClass="label" value="Search Criteria"/>&nbsp;
                            <h:selectOneListbox id="ddlCriteria" styleClass="ddlist" size="1" style="width: 115px" value="#{CustomerSearch.searchList}" tabindex="1">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="Account Number" />
                                <f:selectItem itemValue="Customer Name" />
                                <f:selectItem itemValue="Customer Name & Father Name" />
                                <f:selectItem itemValue="Customer ID" />
                                <f:selectItem itemValue="Joint Customer Name"/>
                                <f:selectItem itemValue="PAN Number"/>
                                <f:selectItem itemValue="AADHAAR (UIDAI)"/>
                                <f:selectItem itemValue="Mobile No."/>
                                <f:selectItem itemValue="P.Token No."/>
                                <a4j:support event="onblur" action="#{CustomerSearch.onblurSessionCriteria}"
                                             oncomplete="if(#{CustomerSearch.flag1=='true'} || #{CustomerSearch.flag2=='true'} || #{CustomerSearch.flag3=='true'}|| #{CustomerSearch.flag4=='true'})
                                             {
                                             #{rich:element('ddlCriteria')}.disabled='true';
                                             #{rich:element('PanelGrid2')}.style.display='';
                                             #{rich:element('PanelGridCustFath')}.style.display=none;
                                             }else if(#{CustomerSearch.custFatherflag=='true'})
                                             { 
                                             #{rich:element('PanelGridCustFath')}.style.display='';
                                             #{rich:element('PanelGrid2')}.style.display='none';
                                             }"
                                             reRender="lblCriteriaType,txtCriteriaType,PanelGrid2,PanelGridCustFath,errmsg">
                                </a4j:support>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"/>&nbsp;
                        <h:selectOneListbox id="ddlStatus" styleClass="ddlist" size="1" style="width: 115px" value="#{CustomerSearch.status}" tabindex="2">
                            <f:selectItem itemValue="--SELECT--"/>
                            <f:selectItem itemValue="All" />
                            <f:selectItem itemValue="Active" />
                            <f:selectItem itemValue="Close" />
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <!-- Panel 2-->
                    <h:panelGrid id="PanelGrid2" styleClass="row1" columns="4" bgcolor="#fff" style="border:1px ridge #BED6F8;display:none;text-align:center"  width="100%">
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lblCriteriaType" styleClass="label"
                                           value="#{CustomerSearch.searchList}" />&nbsp;
                            <h:inputText id="txtCriteriaType" styleClass="input" value="#{CustomerSearch.paramValue}" onkeyup="this.value = this.value.toUpperCase();" tabindex="3">
                                <a4j:support actionListener="#{CustomerSearch.getDetail}" event="onblur"
                                             oncomplete="if(#{CustomerSearch.searchList=='Account Number'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid3')}.style.display='';
                                             }
                                             else if(#{CustomerSearch.searchList=='Customer Name'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid4')}.style.display='';
                                             }
                                             else if(#{CustomerSearch.searchList=='Customer ID'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid4')}.style.display='';
                                             }
                                             else if(#{CustomerSearch.searchList=='Joint Customer Name'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid5')}.style.display='';
                                             }
                                             else if(#{CustomerSearch.searchList=='PAN Number'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid4')}.style.display='';
                                             }else if(#{CustomerSearch.searchList=='AADHAAR (UIDAI)'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid4')}.style.display='';
                                             }
                                             else if(#{CustomerSearch.searchList=='P.Token No.'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid4')}.style.display='';
                                             }
                                             else if(#{CustomerSearch.searchList=='Mobile No.'} && #{CustomerSearch.paramValue!=''})
                                             {
                                             #{rich:element('PanelGrid4')}.style.display='';
                                             }
                                             "
                                             reRender="txtCustID1,txtAccNo1,stxtAccNo1,txtCustName1,txtDOB1,txtFatherName1,txtMotherName1,
                                             txtMob1,txtPAN_GIR_No1,txtPermAddress1,txtMailAddress1,PanelGrid3,PanelGrid4,PanelGrid5,CustDetail,errmsg">
                                </a4j:support>
                            </h:inputText>
                            <h:outputLabel id="stxtAccNo1" value="#{CustomerSearch.newAcno}" styleClass="label"/>  
                        </h:panelGroup>

                    </h:panelGrid>

                    <!-- Panel Search based by Customer Name and Father Name -->                       
                    <h:panelGrid id="PanelGridCustFath"columnClasses ="col13,col13,col13,col13"styleClass="row1" columns="4" bgcolor="#fff" style="border:1px ridge #BED6F8;display:none;text-align:center"  width="100%">
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lblCriteriaCustName" styleClass="label"value="Customer Name" ><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtCriteriaCustName" styleClass="input" value="#{CustomerSearch.custName}" onkeyup="this.value = this.value.toUpperCase();" tabindex="3">
                        </h:inputText>
                            <h:outputLabel id="lblCriteriaFather" styleClass="label"value="Father Name" ><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtCriteriaFather" styleClass="input" value="#{CustomerSearch.fatherName}" onkeyup="this.value = this.value.toUpperCase();" tabindex="3">
                            <a4j:support actionListener="#{CustomerSearch.getDetail}" event="onblur"  
                                         oncomplete="if(#{CustomerSearch.searchList=='Customer Name & Father Name'} && #{CustomerSearch.custName!=''})
                                             {
                                             #{rich:element('PanelGrid4')}.style.display='';
                                             }"
                                             reRender ="PanelGrid4,CustDetail,PanelGridCustFath,errmsg"></a4j:support>
                        </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>                       


                    <!-- Panel 3-->
                    <h:panelGrid id="PanelGrid3" bgcolor="#fff" style="border:1px ridge #BED6F8;display:none"  width="100%">

                        <h:panelGrid columns="4" columnClasses="col1,col2,col1,col2" styleClass="row2" width="100%" style="border:1px ridge #BED6F8">
                            <h:outputLabel id="lblCustID1" value="Customer ID" styleClass="label"/>
                            <h:inputText id="txtCustID1" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.custID}" />
                            <h:outputLabel id="lblAccNo1" value="Account Number" styleClass="label"/>                          
                            <h:inputText id="txtAccNo1" styleClass="input"  readonly="true" value="#{CustomerSearch.currentDetail.accNo}"/>
                        </h:panelGrid>

                        <h:panelGrid columns="4" columnClasses="col1,col2,col1,col2" styleClass="row1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                            <h:outputLabel id="lblCustName1" value="Name" styleClass="label"/>
                            <h:inputText id="txtCustName1" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.custName}"/>
                            <h:outputLabel id="lblDOB1" value="Date Of Birth" styleClass="label"/>
                            <h:inputText id="txtDOB1" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.dob}"/>
                        </h:panelGrid>

                        <h:panelGrid columns="4" columnClasses="col1,col2,col1,col2" styleClass="row2" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                            <h:outputLabel id="lblFatherName1" value="Father Name" styleClass="label"/>
                            <h:inputText id="txtFatherName1" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.fName}"/>
                            <h:outputLabel id="lblMotherName1" value="Mother Name" styleClass="label"/>
                            <h:inputText id="txtMotherName1" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.mName}"/>
                        </h:panelGrid>

                        <h:panelGrid columns="4" columnClasses="col1,col2,col1,col2" styleClass="row1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                            <h:outputLabel id="lblMob1" value="Phone Number" styleClass="label"/>
                            <h:inputText id="txtMob1" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.phone}"/>
                            <h:outputLabel id="lblPAN_GIR_No1" value="PAN/GIR Number" styleClass="label"/>
                            <h:inputText id="txtPAN_GIR_No1" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.pan_gir}"/>
                        </h:panelGrid>

                        <h:panelGrid columns="2" columnClasses="col2,col14" styleClass="row2" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                            <h:outputLabel id="lblPermAddress1" value="Permanent Address" styleClass="label"/>
                            <h:inputText id="txtPermAddress1" size="120" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.permAdd}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col2,col14" styleClass="row1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                            <h:outputLabel id="lblMailAddress1" value="Mailing Address" styleClass="label"/>
                            <h:inputText id="txtMailAddress1" size="120" styleClass="input" readonly="true" value="#{CustomerSearch.currentDetail.mailAdd}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <!-- Panel 4-->
                    <h:panelGrid id="PanelGrid4" styleClass="row1" columns="1" style="height:30px;display:none"  width="100%">
                        <a4j:region>
                            <rich:dataTable id="CustDetail"
                                            value="#{CustomerSearch.customerDetailList}"
                                            rows="10"
                                            var="dataItem"
                                            rowClasses="gridrow1, gridrow2"
                                            columnsWidth="50"
                                            rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                            width="100%"
                                            >
                                <f:facet name="caption">
                                    <h:outputText value="Customer Details" />
                                </f:facet>
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Customer ID" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Account Number" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Name" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Date Of Birth" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Father Name"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Mother Name"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Phone Number" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="PAN/GIR Number"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Permanent Address"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Mailing Address"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Account Balance"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Account Status"  style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>

                                <rich:column><h:outputText value="#{dataItem.currentRow}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custID}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accNo}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dob}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fName}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mName}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.phone}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.pan_gir}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.permAdd}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mailAdd}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accBal}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accStatus}" style="text-align:center" /></rich:column>



                                <f:facet name="footer">
                                    <h:outputText value="#{CustomerSearch.currentRow} rows found" style="text-align:center" />
                                </f:facet>

                            </rich:dataTable>
                            <rich:datascroller id="scroller" align="left" for="CustDetail" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <!-- Panel 5-->
                    <h:panelGrid id="PanelGrid5" styleClass="row1" columns="1" style="height:30px;display:none"  width="100%">
                        <a4j:region>
                            <rich:dataTable id="JtCustDetail"
                                            value="#{CustomerSearch.customerDetailList}"
                                            rows="10"
                                            var="dataItem"
                                            rowClasses="gridrow1, gridrow2"
                                            columnsWidth="50"
                                            rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                            width="100%"
                                            >
                                <f:facet name="caption">
                                    <h:outputText value="Customer Details" />
                                </f:facet>
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Account Number" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Customer Name (Primary)" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Father's Name Of (Primary Customer)"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Date Of Birth (Primary)" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Mailing Address (Primary)"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Account Balance"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Account Status"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Joint Name1" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Cust Id1" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Joint Name2"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Cust Id2"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Joint Name3" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Cust Id3" style="text-align:center" /></rich:column>
                                        <rich:column><h:outputText value="Joint Name4"  style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Cust Id4"  style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.currentRow}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accNo}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fName}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dob}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mailAdd}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accBal}" style="text-align:left" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accStatus}" style="text-align:left" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName1}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtCustId1}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName2}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtCustId2}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName3}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtCustId3}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName4}" style="text-align:center" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtCustId4}" style="text-align:center" /></rich:column>
                                <f:facet name="footer">
                                    <h:outputText value="#{CustomerSearch.currentRow} rows found" style="text-align:center" />
                                </f:facet>
                            </rich:dataTable>
                            <rich:datascroller id="scrollerJt" align="left" for="JtCustDetail" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <!-- Footer Panel -->
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton type="reset" id="btnCancel" value="Refresh" action="#{CustomerSearch.reset}"
                                               oncomplete="#{rich:element('ddlCriteria')}.disabled=false;
                                               #{rich:element('PanelGrid1')}.style.display=none;
                                               #{rich:element('PanelGrid2')}.style.display=none;
                                               #{rich:element('PanelGrid3')}.style.display=none;
                                               #{rich:element('PanelGrid4')}.style.display=none;
                                               #{rich:element('PanelGrid5')}.style.display=none;
                                               #{rich:element('PanelGridCustFath')}.style.display=none;"
                                               reRender="PanelGrid1,PanelGrid2,PanelGrid3,PanelGrid4,PanelGrid5,PanelGridCustFath,errmsg"
                                               tabindex="3">
                            </a4j:commandButton>
                            <a4j:commandButton id="exit" action="#{CustomerSearch.exitAction}" value="Exit" reRender="errmsg">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>