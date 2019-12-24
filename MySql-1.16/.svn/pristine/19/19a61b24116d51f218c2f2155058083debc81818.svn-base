<%-- 
    Document   : TxnCustomerDetails
    Created on : 13 Apr, 2011, 6:56:09 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">  
<f:view>
<html>
    <head>
        <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
        <title> Customer Details Report</title>
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Cache-Control" content="no-cache"/>
        <script type="text/javascript"></script>
    </head>
    <body>
        <a4j:form id="custDetailsForm">
            <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                    <h:panelGroup id="groupPanel" layout="block" >
                        <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                        <h:outputText id="stxtDate" styleClass="output" value="#{CustomerDetailsReport.todayDate}"/>
                    </h:panelGroup>
                    <h:outputLabel id="lblincident" styleClass="headerLabel" value="Customer Details Report"/>
                    <h:panelGroup id="groupPanel2" layout="block">
                        <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                        <h:outputText id="stxtUser" styleClass="output" value="#{CustomerDetailsReport.userName}" /> 
                    </h:panelGroup>
                </h:panelGrid>

                <h:panelGrid columns="1" id="d22"  styleClass="row1" style="width:100%;text-align:center;color:red" >
                    <h:outputText id="stxtmessage" styleClass="output" value="#{CustomerDetailsReport.messages}"  />
                </h:panelGrid>   

               <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="grdPanel2" style="height:30px;" styleClass="row2" width="100%">
                    <h:outputLabel id="lblactype" styleClass="label" value="Account No.:" style="width:100%;text-align:left;"/>
                    <h:panelGroup layout="block" style="width:100%;text-align:left;">
                        <h:inputText id="txtacno" styleClass="input" maxlength="#{CustomerDetailsReport.acNoMaxLen}" value="#{CustomerDetailsReport.oldAccNo}">
                            <a4j:support id="a4j" event="onblur" action="#{CustomerDetailsReport.tabData}" 
                                         reRender="stxtmessageMis,lblJtMsg,mainPanel,stxtmessage,txtacopen,txtomode,txtotype,stxtcn,
                                         txtarelname,txtmailadd,txtacinstn,txtphno,txtpanno,txtdetail,txtdname,txtioption,txtblnce,
                                         txtcbook,txtroivalue,txtrdMateval,txtrdinstallval,txtrdMAtval,txtfdvalue,a19,Account"
                                         oncomplete="if(#{CustomerDetailsReport.flag == 'true'})#{rich:component('Account')}.show();"/>
                        </h:inputText>
                        <h:outputText id="stxtAccNo" styleClass="output" value="#{CustomerDetailsReport.accno}"/>
                    </h:panelGroup>
                    <h:outputLabel id="lblcn" styleClass="label" value="Customer Name :" style="width:100%;text-align:center;"/>
                    <h:outputText id="stxtcn" styleClass="output" value="#{CustomerDetailsReport.cname}"/>
                </h:panelGrid> 


                <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="acopen" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblacopen" styleClass="label"  value="A/c Opening Date:"  style="width:100%;text-align:left;"/>
                    <h:outputText id="txtacopen" styleClass="output" value="#{CustomerDetailsReport.opendate}"/>
                    <h:outputText id="txtacopen1" styleClass="output" />
                    <h:outputText id="txtacopen2" styleClass="output" />
                </h:panelGrid>

                <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="relnam" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblrelname" styleClass="label"  value="Father/Husband/Guardian Name:"  style="width:100%;text-align:left;"/>
                    <h:outputText id="txtarelname" styleClass="output" value="#{CustomerDetailsReport.relname}"/>
                    <h:outputText id="txtarelname1" styleClass="output"/>
                    <h:outputText id="txtarelname2" styleClass="output" />
                </h:panelGrid>

                <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="mailadd" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblmailadd" styleClass="label"  value="Mailing Address:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtmailadd" styleClass="output" value="#{CustomerDetailsReport.mailadd}"/>
                    <h:outputText id="txtmailadd1" styleClass="output" />
                    <h:outputText id="txtmailadd2" styleClass="output" />
                </h:panelGrid>

                <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" id="phno" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblphno" styleClass="label"  value="Phone No:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtphno" styleClass="output" value="#{CustomerDetailsReport.phno}"/>
                    <h:outputText id="txtphno1" styleClass="output" />
                    <h:outputText id="txtphno2" styleClass="output" />
                </h:panelGrid>


                <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="panno" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblpanno" styleClass="label"  value="PAN No:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtpanno" styleClass="output" value="#{CustomerDetailsReport.panno}"/>
                    <h:outputLabel id="lblacinstn" styleClass="label"  value="A/c Instruction:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtacinstn" styleClass="output" value="#{CustomerDetailsReport.acinstn}"/>
                    <h:outputText id="txtfd" styleClass="output" value="#{CustomerDetailsReport.fd}"/>
                    <h:outputText id="txtfdvalue" styleClass="output" value="#{CustomerDetailsReport.fdvalue}"/>
                </h:panelGrid>

                <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="dname" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lbldname" styleClass="label"  value="Document Name:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtdname" styleClass="output" value="#{CustomerDetailsReport.dname}"/>
                    <h:outputLabel id="lbldetail" styleClass="label"  value="Document Details:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtdetail" styleClass="output" value="#{CustomerDetailsReport.detail}"/>
                    <h:outputText id="txtroi" styleClass="output" value="#{CustomerDetailsReport.roi}"/>
                    <h:outputText id="txtroivalue" styleClass="output" value="#{CustomerDetailsReport.roivalue}"/>
                </h:panelGrid>



                <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="omode" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblomode" styleClass="label"  value="Operation Mode:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtomode" styleClass="output" value="#{CustomerDetailsReport.omode}"/>
                    <h:outputLabel id="lblotype" styleClass="label"  value="Organisation Type:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtotype" styleClass="output" value="#{CustomerDetailsReport.otype}"/>
                    <h:outputText id="txtrdinstall" styleClass="output" value="#{CustomerDetailsReport.rdinstall}"/>
                    <h:outputText id="txtrdinstallval" styleClass="output" value="#{CustomerDetailsReport.rdinstallval}"/>
                </h:panelGrid>

                <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="dob" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lbldob" styleClass="label"  value="DOB" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtdob" styleClass="output" value="#{CustomerDetailsReport.dob}"/>
                    <h:outputLabel id="lblcbook" styleClass="label"  value="Chq Book Facility:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtcbook" styleClass="output" value="#{CustomerDetailsReport.cbook}"/>
                    <h:outputText id="txtrdMat" styleClass="output" value="#{CustomerDetailsReport.rdMat}"/>
                    <h:outputText id="txtrdMateval" styleClass="output" value="#{CustomerDetailsReport.rdMateval}"/>
                </h:panelGrid>

                <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="ioption" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblioption" styleClass="label"  value="Interest Option:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtioption" styleClass="output" value="#{CustomerDetailsReport.ioption}"/>
                    <h:outputLabel id="lblblnce" styleClass="label"  value="Balance:" style="width:100%;text-align:left;"/>
                    <h:outputText id="txtblnce" styleClass="output" value="#{CustomerDetailsReport.blnce}">
                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                    </h:outputText>
                    <h:outputText id="txtrdMAt" styleClass="output" value="#{CustomerDetailsReport.rdMAt}"/>
                    <h:outputText id="txtrdMAtval" styleClass="output" value="#{CustomerDetailsReport.rdMAtval}">
                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                    </h:outputText>
                </h:panelGrid>
                <h:panelGrid columns="3" columnClasses="col7,col7,col2,col2" id="panelMsg" style="height:30px;color:green"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="Message1"   value="Press Ctrl+D To View Security Detail" styleClass="label"/>
                    <h:outputLabel id="Message3"   value="Press Ctrl+J To View Joint Holder Details and Click Exit To Hide" styleClass="label"/>
                    <h:outputLabel id="Message2"   value="Press Ctrl+O To View Other A/c And" styleClass="label"/>
                </h:panelGrid>
                <h:panelGrid columns="1" id="footer" style="width:100%;text-align:center;" styleClass="footer" >
                    <h:panelGroup>
                        <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CustomerDetailsReport.reSet}" reRender="stxtmessage,txtacopen,txtomode,txtotype,stxtcn,txtarelname,txtmailadd,txtacinstn,txtphno,txtpanno,txtdetail,txtdname,txtioption,txtblnce,txtcbook,txtroivalue,txtrdMateval,txtrdinstallval,txtrdMAtval,txtfdvalue,a19,txtacno,stxtAccNo">
                            </a4j:commandButton>
                        <a4j:commandButton id="btnClose" value="Close" action="#{CustomerDetailsReport.clearForm}" reRender="stxtmessage,txtacopen,txtomode,txtotype,stxtcn,txtarelname,txtmailadd,txtacinstn,txtphno,txtpanno,txtdetail,txtdname,txtioption,txtblnce,txtcbook,txtroivalue,txtrdMateval,txtrdinstallval,txtrdMAtval,txtfdvalue,a19,txtacno,stxtAccNo">
                            </a4j:commandButton>
                    </h:panelGroup>
                </h:panelGrid>
            </h:panelGrid>

            <%--    Start Of Security Detail     --%>

            <rich:modalPanel id="SecurityPanel" top="true" height="150" width="1000">
                <h:form>
                    <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%" >
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{CustomerDetailsReport.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="label2" styleClass="headerLabel" value="Security Details"/>
                            <h:panelGroup id="groupPanel3" layout="block">
                                <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{CustomerDetailsReport.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{CustomerDetailsReport.security}" var="Item"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="9" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="33"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                            <rich:column> <h:outputText value="Security Type" /></rich:column>
                                            <rich:column><h:outputText value="Mat. Value" /></rich:column>
                                            <rich:column><h:outputText value="Mat. Date" /></rich:column>
                                            <rich:column><h:outputText value="Lien Value" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <rich:column><h:outputText value="Issue Date" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>

                                    <rich:column><h:outputText value="#{Item.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.securityType}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.matValue}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.matDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.lienValue}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.status}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.issueDate}" /></rich:column>


                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:commandButton id="btnExit1" value="Exit" oncomplete="#{rich:component('SecurityPanel')}.hide();">
                                
                            </a4j:commandButton>
                        </h:panelGrid>
                    </h:panelGrid>

                </h:form>

            </rich:modalPanel>
            <%--    End Of Security Detail     --%>

            <%--    Start Of Other A/c     --%>

            <rich:modalPanel id="OtherAc" top="true" width="1000">
                <h:form>
                    <h:panelGrid bgcolor="#fff" columns="1" id="outerPanelOtherAc" style="border:1px ridge #BED6F8" width="100%" >
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{CustomerDetailsReport.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="label2" styleClass="headerLabel" value="Other A/c"/>
                            <h:panelGroup id="groupPanel3" layout="block">
                                <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{CustomerDetailsReport.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{CustomerDetailsReport.otherAc}" var="Item"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="9" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="33"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                            <rich:column> <h:outputText value="Acno" /></rich:column>
                                            <rich:column><h:outputText value="Name" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>

                                    <rich:column><h:outputText value="#{Item.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.name}" /></rich:column>

                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="btnExit2" value="Exit" oncomplete="#{rich:component('OtherAc')}.hide();">
                            
                        </a4j:commandButton>
                    </h:panelGrid>
                </h:form>

            </rich:modalPanel>
            <%--    End Of Other A/c     --%>

            <%--    Start Of Joint Holder Details     --%>

           <rich:modalPanel id="jtHolder" top="true" height="600" width="1000">
                    <h:form>
                        <h:panelGrid bgcolor="#fff" columns="1" id="outerPanelOtherAc" style="border:1px ridge #BED6F8" width="100%" >
                            <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                                <h:panelGroup id="groupPanel2" layout="block">
                                    <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                    <h:outputText id="stxtDate" styleClass="output" value="#{CustomerDetailsReport.todayDate}"/>
                                </h:panelGroup>
                                <h:outputLabel id="label2" styleClass="headerLabel" value="Joint Holder Details"/>
                                <h:panelGroup id="groupPanel3" layout="block">
                                    <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                    <h:outputText id="stxtUser" styleClass="output" value="#{CustomerDetailsReport.userName}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columns="4"  id="jtRow1Msg" style="width:100%;text-align:center;color:red"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblJtMsg" styleClass="label"  value="#{CustomerDetailsReport.jtMsg}" />
                            </h:panelGrid>
                            <rich:panel header="Joint Holder One Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow1" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolder1" styleClass="label"  value="Name: " />
                                    <h:outputText id="stxtJtHolder1" styleClass="output" value="#{CustomerDetailsReport.jtName1}"/>
                                    <h:outputLabel id="lblDOB1" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB1" styleClass="output" value="#{CustomerDetailsReport.dob1}"/>
                                    <h:outputLabel id="lblPANNo1" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo1" styleClass="output" value="#{CustomerDetailsReport.panNo1}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow2" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName1" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName1" styleClass="output" value="#{CustomerDetailsReport.fatherName1}"/>
                                    <h:outputLabel id="lblOccupation1" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation1" styleClass="output" value="#{CustomerDetailsReport.occupation1}"/>
                                    <h:outputLabel id="lblAddress1" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress1" styleClass="output" value="#{CustomerDetailsReport.address1}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Joint Holder Two Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow3" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolderName2" styleClass="label"  value="Name:" />
                                    <h:outputText id="stxtJtHolderName2" styleClass="output" value="#{CustomerDetailsReport.jtName2}"/>
                                    <h:outputLabel id="lblDOB2" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB2" styleClass="output" value="#{CustomerDetailsReport.dob2}"/>
                                    <h:outputLabel id="lblPANNo2" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo2" styleClass="output" value="#{CustomerDetailsReport.panNo2}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow4" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName2" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName2" styleClass="output" value="#{CustomerDetailsReport.fatherName2}"/>
                                    <h:outputLabel id="lblOccupation2" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation2" styleClass="output" value="#{CustomerDetailsReport.occupation2}"/>
                                    <h:outputLabel id="lblAddress2" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress2" styleClass="output" value="#{CustomerDetailsReport.address2}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Joint Holder Three Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow5" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolder3" styleClass="label"  value="Name:" />
                                    <h:outputText id="stxtJtHolder3" styleClass="output" value="#{CustomerDetailsReport.jtName3}"/>
                                    <h:outputLabel id="lblDOB3" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB3" styleClass="output" value="#{CustomerDetailsReport.dob3}"/>
                                    <h:outputLabel id="lblPANNo3" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo3" styleClass="output" value="#{CustomerDetailsReport.panNo3}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow6" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName3" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName3" styleClass="output" value="#{CustomerDetailsReport.fatherName3}"/>
                                    <h:outputLabel id="lblOccupation3" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation3" styleClass="output" value="#{CustomerDetailsReport.occupation3}"/>
                                    <h:outputLabel id="lblAddress3" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress3" styleClass="output" value="#{CustomerDetailsReport.address3}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Joint Holder Four Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow7" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblJtHolderName4" styleClass="label"  value="Name:" />
                                    <h:outputText id="stxtJtHolderName4" styleClass="output" value="#{CustomerDetailsReport.jtName4}"/>
                                    <h:outputLabel id="lblDOB4" styleClass="label"  value="DOB:" />
                                    <h:outputText id="stxtDOB4" styleClass="output" value="#{CustomerDetailsReport.dob4}"/>
                                    <h:outputLabel id="lblPANNo4" styleClass="label"  value="PAN No:" />
                                    <h:outputText id="stxtPANNo4" styleClass="output" value="#{CustomerDetailsReport.panNo4}"/>
                                </h:panelGrid>
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow8" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblFatherName4" styleClass="label"  value="Father Name:" />
                                    <h:outputText id="stxtFatherName4" styleClass="output" value="#{CustomerDetailsReport.fatherName4}"/>
                                    <h:outputLabel id="lblOccupation4" styleClass="label"  value="Occupation:" />
                                    <h:outputText id="stxtOccupation4" styleClass="output" value="#{CustomerDetailsReport.occupation4}"/>
                                    <h:outputLabel id="lblAddress4" styleClass="label"  value="Address:" />
                                    <h:outputText id="stxtAddress4" styleClass="output" value="#{CustomerDetailsReport.address4}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Nominee Details">
                                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow9" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblNomineeName" styleClass="label"  value="Nominee Name:" />
                                    <h:outputText id="stxtNomineeName" styleClass="output" value="#{CustomerDetailsReport.nomineeName}"/>
                                    <h:outputLabel id="lblAddress" styleClass="label"  value="Nominee Address:" />
                                    <h:outputText id="stxtAddress" styleClass="output" value="#{CustomerDetailsReport.nomineeAddress}" />
                                    <h:outputLabel id="lblRelation" styleClass="label"  value="Nominee Relation:" />
                                    <h:outputText id="stxtRelation" styleClass="output" value="#{CustomerDetailsReport.nomineeRelation}"/>
                                </h:panelGrid>
                            </rich:panel>
                            <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <a4j:commandButton id="btnExit" value="Exit" oncomplete="#{rich:component('jtHolder')}.hide();">
                                </a4j:commandButton>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:form>
                </rich:modalPanel>
            <%--    End Of Joint Holder Details     --%>


            <%--    Start Of Tl and Dl Account Details     --%>


            <rich:modalPanel id="Account" top="true" height="500" width="1000">
                <h:form>
                    <h:panelGrid bgcolor="#fff" columns="1" id="outerPanelOtherAc" style="border:1px ridge #BED6F8" width="100%" >
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{CustomerDetailsReport.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="label2" styleClass="headerLabel" value ="Loan / MIS /Customer Details"/>
                            <h:panelGroup id="groupPanel3" layout="block">
                                <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{CustomerDetailsReport.userName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <rich:tabPanel>
                            <rich:tab label="Loan Details" switchType="client" title="Loan Details">
                                <h:panelGrid columns="4"  id="acctMsg" style="width:100%;text-align:center;color:red"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblacctMsg" styleClass="label"  value="#{CustomerDetailsReport.loanMsg}" />
                                </h:panelGrid>
                                <h:panelGrid id="LoanDetailsPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow1" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblSanctionLt" styleClass="label"  value="Sanction Limit" />
                                        <h:outputText id="stxtSanctionLt" styleClass="output" value="#{CustomerDetailsReport.sanctionLimit}"/>
                                        <h:outputLabel id="lblOverDueEmi" styleClass="label"  value="Over Due EMI" />
                                        <h:outputText id="stxtOverDueEmi" styleClass="output" value="#{CustomerDetailsReport.overdueEMI}"/>
                                        <h:outputLabel id="lblInterestOption" styleClass="label"  value="Interest Option" />
                                        <h:outputText id="stxtInterestOption" styleClass="output" value="#{CustomerDetailsReport.intOption}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow2" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblSanctionDate" styleClass="label"  value="Sanction Date" />
                                        <h:outputText id="stxtSanctionDate" styleClass="output" value="#{CustomerDetailsReport.sanctionDate}"/>
                                        <h:outputLabel id="lblLoanPeriod" styleClass="label"  value="Loan Period" />
                                        <h:outputText id="stxtLoanPeriod" styleClass="output" value="#{CustomerDetailsReport.loanPeriod}"/>
                                        <h:outputLabel id="lblSecurityType" styleClass="label"  value="Security Type" />
                                        <h:outputText id="stxtSecurityType" styleClass="output" value="#{CustomerDetailsReport.securityType}"/>
                                    </h:panelGrid>

                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow3" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblROI" styleClass="label"  value="ROI" />
                                        <h:outputText id="stxtROI" styleClass="output" value="#{CustomerDetailsReport.loanAcctRoi}"/>
                                        <h:outputLabel id="lblStatus" styleClass="label"  value="Status" />
                                        <h:outputText id="stxtStatus" styleClass="output" value="#{CustomerDetailsReport.status}"/>
                                        <h:outputLabel id="lblDistictCode1" styleClass="label"  value="Security Value" />
                                        <h:outputText id="stxtDistictCode1" styleClass="output" value="#{CustomerDetailsReport.securityValue}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow4" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblAmountDisbursed" styleClass="label"  value="Amount Disbursed" />
                                        <h:outputText id="stxtAmountDisbursed" styleClass="output" value="#{CustomerDetailsReport.amountDisbursed}"/>
                                        <h:outputLabel id="lblNPADate" styleClass="label"  value="NPADate" />
                                        <h:outputText id="stxtNPADate" styleClass="output" value="#{CustomerDetailsReport.nPADate}"/>
                                        <h:outputLabel id="lblDPandODLimit" styleClass="label"  value="DP and OD Limit" />
                                        <h:outputText id="stxtDPandODLimit" styleClass="output" />
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow5" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblOverDue" styleClass="label"  value="Over Due" />
                                        <h:outputText id="stxtOverDue" styleClass="output" value="#{CustomerDetailsReport.overDue}"/>
                                        <h:outputLabel id="lblNPAIntrest" styleClass="label"  value="NPA Intrest" />
                                        <h:outputText id="stxtNPAIntrest" styleClass="output" value="#{CustomerDetailsReport.nPAIntrest}"/>
                                        <h:outputLabel id="lblPenalROI" styleClass="label"  value="Penal ROI" />
                                        <h:outputText id="stxtPenalROI" styleClass="output" value="#{CustomerDetailsReport.penalROI}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow6" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblRecovery" styleClass="label"  value="Recovery" />
                                        <h:outputText id="stxtRecovery" styleClass="output" value="#{CustomerDetailsReport.recover}"/>
                                        <h:outputLabel id="lblCategory" styleClass="label"  value="Category" />
                                        <h:outputText id="stxtCategory" styleClass="output" value="#{CustomerDetailsReport.category}"/>
                                        <h:outputLabel id="lblADHOCLimit" styleClass="label"  value="ADHOC Limit" />
                                        <h:outputText id="stxtADHOCLimit" styleClass="output" value="#{CustomerDetailsReport.aDHOCLimit}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow7" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblInterestDue" styleClass="label"  value="Interest Due" />
                                        <h:outputText id="stxtInterestDue" styleClass="output" value="#{CustomerDetailsReport.interestDue}"/>
                                        <h:outputLabel id="lblClassification" styleClass="label"  value="Classification" />
                                        <h:outputText id="stxtClassification" styleClass="output" value="#{CustomerDetailsReport.classification}"/>
                                        <h:outputLabel id="lblADHOCROI" styleClass="label"  value="ADHOC ROI" />
                                        <h:outputText id="stxtADHOCROI" styleClass="output" value="#{CustomerDetailsReport.aDHOCROI}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow8" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblPrincipalAmt" styleClass="label"  value="Principal Amt." />
                                        <h:outputText id="stxtPrincipalAmt" styleClass="output" value="#{CustomerDetailsReport.principalAmt}"/>
                                        <h:outputLabel id="lblSector" styleClass="label"  value="Sector" />
                                        <h:outputText id="stxtSector" styleClass="output" value="#{CustomerDetailsReport.sector}"/>
                                        <h:outputLabel id="lblADHOCAppDate" styleClass="label"  value="ADHOC App.Date" />
                                        <h:outputText id="stxtADHOCAppDate" styleClass="output" value="#{CustomerDetailsReport.aDHOCAppDate}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow9" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblPeriod" styleClass="label"  value="Period" />
                                        <h:outputText id="stxtPeriod" styleClass="output" value="#{CustomerDetailsReport.period}"/>
                                        <h:outputLabel id="lblSchemes" styleClass="label"  value="Schemes" />
                                        <h:outputText id="stxtSchemes" styleClass="output" value="#{CustomerDetailsReport.schemes}"/>
                                        <h:outputLabel id="lblSubsidy" styleClass="label"  value="Subsidy" />
                                        <h:outputText id="stxtSubsidy" styleClass="output" value="#{CustomerDetailsReport.subsidy}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="acctRow10" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblInstallAmount" styleClass="label"  value="Install.Amount" />
                                        <h:outputText id="stxtInstallAmount" styleClass="output" value="#{CustomerDetailsReport.installAmount}"/>
                                        <h:outputLabel id="lblPaidEMI" styleClass="label"  value="Paid EMI" />
                                        <h:outputText id="stxtPaidEMI" styleClass="output" value="#{CustomerDetailsReport.paidEMI}"/>
                                        <h:outputLabel id="lblSubsidyExpDate" styleClass="label"  value="Subsidy Exp.Date" />
                                        <h:outputText id="stxtSubsidyExpDate" styleClass="output" value="#{CustomerDetailsReport.subsidyExpDate}"/>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </rich:tab>
                            <rich:tab label="MIS Details" switchType="client" title="MIS Details">
                                <h:panelGrid id="MISDetailsPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columns="1" id="d22"  styleClass="row2" style="width:100%;text-align:center;color:red" >
                                        <h:outputText id="stxtmessageMis" styleClass="output" value="#{CustomerDetailsReport.misMsg}"  />
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow1" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblSector1" styleClass="label"  value="Sector" />
                                        <h:outputText id="stxtSector1" styleClass="output" value="#{CustomerDetailsReport.sectorMis}"/>
                                        <h:outputLabel id="lblSubSector" styleClass="label"  value="Sub Sector" />
                                        <h:outputText id="stxtSubSector" styleClass="output" value="#{CustomerDetailsReport.subSector}"/>
                                        <h:outputLabel id="lblPurposeofAdvance" styleClass="label"  value="Purpose of Advance" />
                                        <h:outputText id="stxtPurposeofAdvance" styleClass="output" value="#{CustomerDetailsReport.purposeAdvance}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow2" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblModeofAdvance" styleClass="label"  value="Mode of Advance" />
                                        <h:outputText id="stxtModeofAdvance" styleClass="output" value="#{CustomerDetailsReport.modeAdvance}"/>
                                        <h:outputLabel id="lblTypeofAdvance" styleClass="label"  value="Type of Advance" />
                                        <h:outputText id="stxtTypeofAdvance" styleClass="output" value="#{CustomerDetailsReport.typeAdvance}"/>
                                        <h:outputLabel id="lblSecured" styleClass="label"  value="Secured" />
                                        <h:outputText id="stxtSecured" styleClass="output" value="#{CustomerDetailsReport.secured}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow3" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblGuaranteeCover" styleClass="label"  value="Guarantee Cover" />
                                        <h:outputText id="stxtGuaranteeCover" styleClass="output" value="#{CustomerDetailsReport.guaranteeCover}"/>
                                        <h:outputLabel id="lblIndustryType" styleClass="label"  value="Industry Type" />
                                        <h:outputText id="stxtIndustryType" styleClass="output" value="#{CustomerDetailsReport.industryType}"/>
                                        <h:outputLabel id="lblSickness" styleClass="label"  value="Sickness" />
                                        <h:outputText id="stxtSickness" styleClass="output" value="#{CustomerDetailsReport.sickness}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow4" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblExposure" styleClass="label"  value="Exposure" />
                                        <h:outputText id="stxtExposure" styleClass="output" value="#{CustomerDetailsReport.exposure}"/>
                                        <h:outputLabel id="lblRestructuring" styleClass="label"  value="Restructuring" />
                                        <h:outputText id="stxtRestructuring" styleClass="output" value="#{CustomerDetailsReport.restructuring}"/>
                                        <h:outputLabel id="lblSanctionLevel" styleClass="label"  value="Sanction Level" />
                                        <h:outputText id="stxtSanctionLevel" styleClass="output" value="#{CustomerDetailsReport.sanctionLevel}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow5" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblSanctioningAuthority" styleClass="label"  value="Sanctioning Authority" />
                                        <h:outputText id="stxtSanctioningAuthority" styleClass="output" value="#{CustomerDetailsReport.sanAuth}"/>
                                        <h:outputLabel id="lblInterestTable" styleClass="label"  value="Interest Table" />
                                        <h:outputText id="stxtInterestTable" styleClass="output" value="#{CustomerDetailsReport.intTable}"/>
                                        <h:outputLabel id="lblInterestType" styleClass="label"  value="Interest Type" />
                                        <h:outputText id="stxtInterestType" styleClass="output" value="#{CustomerDetailsReport.interestType}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow6" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblSchemeCode" styleClass="label"  value="Scheme Code" />
                                        <h:outputText id="stxtSchemeCode" styleClass="output" value="#{CustomerDetailsReport.schemeCode}"/>
                                        <h:outputLabel id="lblNPAClassification" styleClass="label"  value="NPA Classification" />
                                        <h:outputText id="stxtNPAClassification" styleClass="output" value="#{CustomerDetailsReport.npaCal}"/>
                                        <h:outputLabel id="lblCourts" styleClass="label"  value="Courts" />
                                        <h:outputText id="stxtCourts" styleClass="output" value="#{CustomerDetailsReport.court}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow7" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblModeofSettlement" styleClass="label"  value="Mode of Settlement" />
                                        <h:outputText id="stxtModeofSettlement" styleClass="output" value="#{CustomerDetailsReport.modeOfSet}"/>
                                        <h:outputLabel id="lblDebtWaiverReason" styleClass="label"  value="Debt Waiver Reason" />
                                        <h:outputText id="stxtDebtWaiverReason" styleClass="output" value="#{CustomerDetailsReport.debtWaiverReason}"/>
                                        <h:outputLabel id="lblAssetClassReason" styleClass="label"  value="Asset Class Reason" />
                                        <h:outputText id="stxtAssetClassReason" styleClass="output" value="#{CustomerDetailsReport.assetClassReason}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="misRow8" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblPopulationGroup" styleClass="label"  value="Population Group" />
                                        <h:outputText id="stxtPopulationGroup" styleClass="output" value="#{CustomerDetailsReport.populationGroup}"/>
                                        <h:outputLabel id="lblNPAClassification1" styleClass="label"  />
                                        <h:outputText id="stxtNPAClassification1" styleClass="output"/>
                                        <h:outputLabel id="lblCourts1" styleClass="label"  />
                                        <h:outputText id="stxtCourts1" styleClass="output" />
                                    </h:panelGrid>

                                </h:panelGrid>
                            </rich:tab>
                            <rich:tab label="Customer Details" switchType="client" title="Customer Details">
                                <h:panelGrid columns="4"  id="custMsg" style="width:100%;text-align:center;color:red"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblcustMsg" styleClass="label"  value="#{CustomerDetailsReport.custMsg}" />
                                </h:panelGrid>
                                <h:panelGrid id="CustomerDetailsPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="custRow1" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblCustomerStatus" styleClass="label"  value="Customer Status" />
                                        <h:outputText id="stxtCustomerStatus" styleClass="output" value="#{CustomerDetailsReport.custStatus}"/>
                                        <h:outputLabel id="lblOccupation" styleClass="label"  value="Occupation" />
                                        <h:outputText id="stxtOccupation" styleClass="output" value="#{CustomerDetailsReport.occup}"/>
                                        <h:outputLabel id="lblConstitution" styleClass="label"  value="Constitution" />
                                        <h:outputText id="stxtConstitution" styleClass="output" value="#{CustomerDetailsReport.constitution}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="custRow2" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblCaste" styleClass="label"  value="Caste" />
                                        <h:outputText id="stxtCaste" styleClass="output" value="#{CustomerDetailsReport.casteCode}"/>
                                        <h:outputLabel id="lblCommunity" styleClass="label"  value="Community" />
                                        <h:outputText id="stxtCommunity" styleClass="output" value="#{CustomerDetailsReport.communityCode}"/>
                                        <h:outputLabel id="lblState" styleClass="label"  value="State" />
                                        <h:outputText id="stxtState" styleClass="output" value="#{CustomerDetailsReport.stateCode}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="custRow3" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblTehsil" styleClass="label"  value="Tehsil" />
                                        <h:outputText id="stxtTehsil" styleClass="output" value="#{CustomerDetailsReport.tehsil}"/>
                                        <h:outputLabel id="lblBlock" styleClass="label"  value="Block" />
                                        <h:outputText id="stxtBlock" styleClass="output" value="#{CustomerDetailsReport.block}"/>
                                        <h:outputLabel id="lblVillage" styleClass="label"  value="Village" />
                                        <h:outputText id="stxtVillage" styleClass="output" value="#{CustomerDetailsReport.village}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="custRow4" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblRiskRatingInt" styleClass="label"  value="Risk Rating Intenal" />
                                        <h:outputText id="stxtRiskRatingInt" styleClass="output" value="#{CustomerDetailsReport.creditRiskRatingInternal}"/>
                                        <h:outputLabel id="lblRiskRatingOper" styleClass="label"  value="Risk Rating Operational" />
                                        <h:outputText id="stxtRiskRatingOper" styleClass="output" value="#{CustomerDetailsReport.operRiskRating}"/>
                                        <h:outputLabel id="lblBussiness" styleClass="label"  value="Bussiness Segment" />
                                        <h:outputText id="stxtBussiness" styleClass="output" value="#{CustomerDetailsReport.businessSeg}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="custRow5" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                        <h:outputLabel id="lblSalesTurnOver" styleClass="label"  value="Sales Turn Over" />
                                        <h:outputText id="stxtSalesTurnOver" styleClass="output" value="#{CustomerDetailsReport.salesTurnover}"/>
                                        <h:outputLabel id="lblStateMinority" styleClass="label"  value="State Minority" />
                                        <h:outputText id="stxtStateMinority" styleClass="output" value="#{CustomerDetailsReport.stateCodeMinor}"/>
                                        <h:outputLabel id="lblRatingLong" styleClass="label"  value="Rating External Long Term" />
                                        <h:outputText id="stxtRatingLong" styleClass="output" value="#{CustomerDetailsReport.externalRatingLongTerm}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="custRow6" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                        <h:outputLabel id="lblRatingShort" styleClass="label"  value="Rating External Short Term" />
                                        <h:outputText id="stxtRatingShort" styleClass="output" value="#{CustomerDetailsReport.externalRatingShortTerm}"/>
                                        <h:outputLabel id="lblStateMinority1" styleClass="label"/>
                                        <h:outputText id="stxtStateMinority1" styleClass="output"/>
                                        <h:outputLabel id="lblStateMinority2" styleClass="label"/>
                                        <h:outputText id="stxtStateMinority2" styleClass="output"/>
                                    </h:panelGrid>
                                </h:panelGrid>

                            </rich:tab>
                        </rich:tabPanel>
                        <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:commandButton id="btnExit4" value="Exit" oncomplete="#{rich:component('Account')}.hide();">
                                
                            </a4j:commandButton>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:form>

            </rich:modalPanel>
            <%--    End Of Tl and Dl Account Details     --%>
            <a4j:region id="ctrlKeyRegion">
                <a4j:jsFunction name="securityDetail"  actionListener="#{CustomerDetailsReport.secDetail}"
                                oncomplete="#{rich:component('SecurityPanel')}.show();" reRender="SecurityPanel" />
                <a4j:jsFunction name="otherAcno"  actionListener="#{CustomerDetailsReport.otherAccDetail}"
                                oncomplete="#{rich:component('OtherAc')}.show();" reRender="OtherAc" />
                <a4j:jsFunction name="jtDetails"  actionListener="#{CustomerDetailsReport.jtDetails}"
                                oncomplete="#{rich:component('jtHolder')}.show();" reRender="jtHolder" />
            </a4j:region>
        </a4j:form>
        <a4j:status onstart="#{rich:component('wait4')}.show()" onstop="#{rich:component('wait4')}.hide()" for="ctrlKeyRegion"/>
        <rich:modalPanel id="wait4" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
            <f:facet name="header">
                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
            </f:facet>
        </rich:modalPanel>  
        <rich:hotKey key="Ctrl+D" handler="securityDetail(); return false;"/>
        <rich:hotKey key="Ctrl+O" handler="otherAcno(); return false;"/>
        <rich:hotKey key="Ctrl+J" handler="jtDetails(); return false;"/>
    </body>
</html>
</f:view>