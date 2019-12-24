<%--
    Document   : AccountMaintenanceRegister
    Created on : Dec 4, 2010, 12:41:53 PM
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
            <title>Account Maintenance Register</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript"></script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountMaintenanceRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Account Maintenance Register"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountMaintenanceRegister.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="d22" style="width:100%;text-align:center;" styleClass="row1"  >
                        <h:outputText id="stxtmessage" styleClass="output" value="#{AccountMaintenanceRegister.message}"  style="width:100%;color:red"/>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="acno" style="height:30px;"  styleClass="row2" width="100%"  >
                        <h:outputLabel id="lblacno" styleClass="label"  value="Account No"  style="width:100%;text-align:left;"></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtAccountNumber" styleClass="input" style="width:100px" value="#{AccountMaintenanceRegister.oldAcno}" maxlength="#{AccountMaintenanceRegister.acNoMaxLen}">
                                <a4j:support actionListener="#{AccountMaintenanceRegister.customerInfo}"
                                             oncomplete="if(#{AccountMaintenanceRegister.displayMaturityDetails==true}){#{rich:element('panelMaturityDetails')}.style.display='';}
                                             else if(#{AccountMaintenanceRegister.displayMaturityDetails==false}){#{rich:element('panelMaturityDetails')}.style.display=none;}"
                                             event="onblur" limitToList="true"
                                             reRender="demandTextId,panelMaturityDetails,stxtmessage,ddInterestOption,txtfhname,txtcn1,ddchkbkissue,txtMailingAddress,
                                             acno,txtphoneNo,txtNominee,txtRelationship,txtacInstruction,ddopendate1,ddmaturitydate,txtInterest1,ddapplyMinBalCharge,txtAmount1,txtguradianName,
                                             ddintroducerAcNo,txtintroducerAcNo,ddoperMode,ddorganizationType,txtan,rightPanel,dddocumentName,txtDocumentDetails,
                                             btnUpdate,txtjtName1,txtjtName2,txtjtName3,txtjtName4,dddobNominee,txtrelNominee,txtaddNominee,txtcustid1,txtcustid2,
                                             txtcustid3,txtcustid4,stxtAccNo,dmdPanel,ddActCategType,lblTDSApplicable,ddTDSApplicable,gridPanel1k"
                                             focus="ddchkbkissue"/>
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{AccountMaintenanceRegister.acno}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblcn1" styleClass="label"  value="Customer Name"  style="width:100%;text-align:left;"></h:outputLabel>
                        <h:outputText id="txtcn1" styleClass="output" value="#{AccountMaintenanceRegister.custname}" />
                        <h:outputLabel id="lblopendate1" styleClass="label"  value="A/c. Opening Date "  style="width:100%;text-align:left;"></h:outputLabel>
                        <rich:calendar id="ddopendate1" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" disabled="true" style="color:black" value="#{AccountMaintenanceRegister.acopendt}" inputSize="10"/>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="grid1" style="height:30px;"  styleClass="row1" width="100%"  >
                        <h:outputLabel id="lblfhname" styleClass="label"  value="Father/Husband Name"  style="width:100%;text-align:left;"/>
                        <h:outputText  id="txtfhname" styleClass="output" style="color:black" value="#{AccountMaintenanceRegister.fhname}"/>
                        <h:outputLabel id="lblphoneNo" styleClass="label"  value="Phone No "  style="width:100%;text-align:left;"/>
                        <h:outputText  id="txtphoneNo" styleClass="output" style="color:black" value="#{AccountMaintenanceRegister.phoneno}"/>
                        <h:outputLabel id="lblMailingAddress" styleClass="label"  value="Mailing Address"  style="width:100%;text-align:left;"> </h:outputLabel>
                        <h:outputText id="txtMailingAddress" styleClass="output" style="color:black;" value="#{AccountMaintenanceRegister.mailingadd}" />
                    </h:panelGrid>    

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="grid2" style="height:30px;"  styleClass="row2" width="100%"  >
                        <h:outputLabel id="lblchkbkissue" styleClass="label"  value="Check Book Issue "  style="width:100%;text-align:left;"></h:outputLabel>
                        <h:selectOneListbox id="ddchkbkissue" styleClass="ddlist" size="1" style="width: 80px" value="#{AccountMaintenanceRegister.chkbkissue}">
                            <f:selectItems value="#{AccountMaintenanceRegister.chkBookList}" />
                            <a4j:support event="onblur" action="#{AccountMaintenanceRegister.setchkBookIssue()}" focus="ddapplyMinBalCharge" reRender="stxtmessage,ddchkbkissue"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblapplyMinBalCharge" styleClass="label"  value="Apply Min Bal Charge"  style="width:100%;text-align:left;"/>
                        <h:selectOneListbox id="ddapplyMinBalCharge" styleClass="ddlist" size="1" style="width: 80px" value="#{AccountMaintenanceRegister.aplminblchg}">
                            <f:selectItems value="#{AccountMaintenanceRegister.aplMinBalList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lbloperMode" styleClass="label"  value="Operation Mode"  style="width:100%;text-align:left;"/>
                        <h:selectOneListbox id="ddoperMode" styleClass="ddlist" size="1" style="width: 130px" value="#{AccountMaintenanceRegister.opermode}">
                            <f:selectItems value="#{AccountMaintenanceRegister.opermodeoption}" />
                            <a4j:support actionListener="#{AccountMaintenanceRegister.onblurOperatingMode}" event="onchange" 
                                         reRender="stxtmessage,txtcustid1,txtcustid2,txtcustid3,txtcustid4,txtjtName1,txtjtName2,
                                         txtjtName3,txtjtName4,gridPanel1k" focus="txtNominee"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="grid3" style="height:30px;"  styleClass="row1" width="100%"  >
                        <h:outputLabel id="lblNominee" styleClass="label"  value="Nominee"  style="width:100%;text-align:left;"> </h:outputLabel>
                        <h:inputText id="txtNominee" styleClass="input" value="#{AccountMaintenanceRegister.nominee}"onkeydown="this.value=this.value.toUpperCase();" maxlength="150"/>
                        <h:outputLabel id="lblrelNominee" styleClass="label"  value="RelationShip With Nominee"  style="width:100%;text-align:left;"> </h:outputLabel>
                        <h:inputText id="txtrelNominee" styleClass="input" value="#{AccountMaintenanceRegister.relnominee}"onkeydown="this.value=this.value.toUpperCase();" maxlength="40"/>
                        <h:outputLabel id="lbladdNominee" styleClass="label"  value="Nominee Address"  style="width:100%;text-align:left;"> </h:outputLabel>
                        <h:inputText id="txtaddNominee" styleClass="input" value="#{AccountMaintenanceRegister.addnominee}"onkeydown="this.value=this.value.toUpperCase();" maxlength="150"/>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="grid4" style="height:30px;"  styleClass="row2" width="100%"  >
                        <h:outputLabel id="lbldobNominee" styleClass="label"  value="Nominee DOB"  style="width:100%;text-align:left;"> </h:outputLabel>
                        <rich:calendar id="dddobNominee" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{AccountMaintenanceRegister.dobnominee}" inputSize="10">
                            <a4j:support event="onselect" reRender="dddobNominee"/>
                        </rich:calendar>
                        <%--
                        <h:outputLabel id="lblorganizationType" styleClass="label"  value="Organization Type "  style="width:100%;text-align:left;"/>
                        <h:selectOneListbox id="ddorganizationType" styleClass="ddlist" size="1" style="width: 130px" value="#{AccountMaintenanceRegister.orgtype}">
                            <f:selectItems value="#{AccountMaintenanceRegister.orgtypeOption}" />
                            <a4j:support event="onblur" focus="ddActCategType"/>
                        </h:selectOneListbox>
                        --%>
                        <%-- Added by Manish Kumar --%>
                            <h:outputLabel id="lblorganizationType" styleClass="label" value="Organization Type "></h:outputLabel>
                            <h:outputLabel id="ddorganizationType" styleClass="label" value="#{AccountMaintenanceRegister.orgTypeDesc}"></h:outputLabel>
                        <%-- --%>
                        <h:outputLabel id="lblInterestOption" styleClass="label"  value="Interest Option "  style="width:100%;text-align:left;"/>
                        <h:selectOneListbox id="ddInterestOption" styleClass="ddlist" size="1" style="color:black;width: 130px" disabled="true" value="#{AccountMaintenanceRegister.intrstopt}">
                            <f:selectItems value="#{AccountMaintenanceRegister.intOptList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="grid5" style="height:30px;"  styleClass="row1" width="100%"  >
                        <h:outputLabel id="lblActCategType" styleClass="label"  value="Account Category"  style="width:100%;text-align:left;"/>
                        <h:selectOneListbox id="ddActCategType" styleClass="ddlist" size="1" style="width: 200px" value="#{AccountMaintenanceRegister.actCategory}">
                            <f:selectItems value="#{AccountMaintenanceRegister.actCategoryList}" />
                            <a4j:support event="onblur" 
                                         oncomplete="if(#{AccountMaintenanceRegister.hufFlag==false}){#{rich:element('txtHufFamily')}.focus();} 
                                         else if(#{AccountMaintenanceRegister.flag1==false}){#{rich:element('txtcustid1')}.focus();}
                                         else {#{rich:element('txtguradianName')}.focus();}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTDSApplicable" styleClass="label"value="TDS Applicable" style="display:#{AccountMaintenanceRegister.displayTds}"/>
                        <h:selectOneListbox id="ddTDSApplicable" styleClass="ddlist" size="1" style="width:78px;display:#{AccountMaintenanceRegister.displayTds}" value="#{AccountMaintenanceRegister.tdsApplicable}">
                            <f:selectItems value="#{AccountMaintenanceRegister.tdsApplicableList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col49" columns="4" id="gridPanel1k" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblHufFamily" styleClass="label" value="HUF Family Detail"/>
                        <h:inputText id="txtHufFamily" styleClass="input" style="width:590px" value="#{AccountMaintenanceRegister.hufFamily}" disabled="#{AccountMaintenanceRegister.hufFlag}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="ee" width="100%">
                        <h:panelGrid  columns="1" id="ee2" width="100%">
                            <h:panelGrid columns="3" columnClasses="col9" id="jtName1" style="height:30px;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lbljtName1" styleClass="label"  value="CustomerId1/JtName1"  style="width:100%;text-align:left;"/>
                                <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                    <h:inputText id="txtcustid1" styleClass="input" style="width:50px;" disabled="#{AccountMaintenanceRegister.flag1}"value="#{AccountMaintenanceRegister.custid1}">
                                        <a4j:support event="onblur" actionListener="#{AccountMaintenanceRegister.OnblurOfjname1}" reRender="txtjtName1,stxtmessage,txtcustid1" oncomplete="if(#{AccountMaintenanceRegister.flag2==false}){#{rich:element('txtcustid2')}.focus();} else {#{rich:element('txtguradianName')}.focus();}"/>
                                    </h:inputText>
                                    <h:outputText  id="txtjtName1" styleClass="output" value="#{AccountMaintenanceRegister.jtname1}"/>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columns="3" columnClasses="col9" id="jtName2" style="height:30px;"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="lbljtName2" styleClass="label"  value="Customer ID2/Jt Name2 "  style="width:100%;text-align:left;"/>
                                <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                    <h:inputText id="txtcustid2" styleClass="input" disabled="#{AccountMaintenanceRegister.flag2}"style="width:50px;" value="#{AccountMaintenanceRegister.custid2}">
                                        <a4j:support event="onblur" actionListener="#{AccountMaintenanceRegister.OnblurOfjname2}" reRender="txtjtName2,stxtmessage,txtcustid1,txtcustid2" oncomplete="if(#{AccountMaintenanceRegister.flag3==false}){#{rich:element('txtcustid3')}.focus();} else {#{rich:element('txtguradianName')}.focus();}"/>
                                    </h:inputText>
                                    <h:outputText  id="txtjtName2" styleClass="output" value="#{AccountMaintenanceRegister.jtname2}"/>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columns="2" columnClasses="col9" id="jtName3" style="height:30px;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lbljtName3" styleClass="label"  value="Customer ID3/Jt Name3 "  style="width:100%;text-align:left;"> </h:outputLabel>
                                <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                    <h:inputText id="txtcustid3" styleClass="input" disabled="#{AccountMaintenanceRegister.flag3}"style="width:50px;" value="#{AccountMaintenanceRegister.custid3}">
                                        <a4j:support event="onblur" actionListener="#{AccountMaintenanceRegister.OnblurOfjname3}" reRender="txtjtName3,stxtmessage,txtcustid1,txtcustid2,txtcustid3" oncomplete="if(#{AccountMaintenanceRegister.flag4==false}){#{rich:element('txtcustid4')}.focus();} else {#{rich:element('txtguradianName')}.focus();}"/>
                                    </h:inputText>
                                    <h:outputText id="txtjtName3" styleClass="output" value="#{AccountMaintenanceRegister.jtname3}"/>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columns="2" columnClasses="col9" id="jtName4" style="height:30px;"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="lbljtName4" styleClass="label"  value="Customer ID4/Jt Name4 "  style="width:100%;text-align:left;"> </h:outputLabel>
                                <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                    <h:inputText id="txtcustid4" styleClass="input" disabled="#{AccountMaintenanceRegister.flag4}"style="width:50px;" value="#{AccountMaintenanceRegister.custid4}">
                                        <a4j:support event="onblur" actionListener="#{AccountMaintenanceRegister.OnblurOfjname4}" reRender="txtjtName4,stxtmessage,txtcustid1,txtcustid2,txtcustid3,txtcustid4" focus="txtguradianName"/>
                                    </h:inputText>
                                    <h:outputText id="txtjtName4" styleClass="output" value="#{AccountMaintenanceRegister.jtname4}"/>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columns="2" columnClasses="col9" id="guradianName" style="height:30px;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblguradianName" styleClass="label"  value="Guradian Name "  style="width:100%;text-align:left;"> </h:outputLabel>
                                <h:inputText id="txtguradianName" styleClass="input" value="#{AccountMaintenanceRegister.guradianname}"onkeydown="this.value=this.value.toUpperCase();" maxlength="30">
                                    <a4j:support event="onblur" focus="txtacInstruction"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="acInstruction" style="height:30px;"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="lblacInstruction" styleClass="label"  value="A/C Instruction "  style="width:100%;text-align:left;" > </h:outputLabel>
                                <h:inputText id="txtacInstruction" styleClass="input" value="#{AccountMaintenanceRegister.acinstruction}"onkeydown="this.value=this.value.toUpperCase();" maxlength="100">
                                    <a4j:support event="onblur" focus="txtintroducerAcNo"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="introducerAcNo" style="height:30px;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblintroducerAcNo" styleClass="label"  value="Introducer A/C No"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                    <h:inputText id="txtintroducerAcNo" styleClass="input" style="width: 100px" maxlength="#{AccountMaintenanceRegister.acNoMaxLen}" value="#{AccountMaintenanceRegister.oldCodde}">
                                        <a4j:support actionListener="#{AccountMaintenanceRegister.getIntroducerAcctDetails}" event="onblur" 
                                                     oncomplete="if(#{AccountMaintenanceRegister.displayDmd==false}){#{rich:element('dddocumentName')}.focus();} 
                                                     else {#{rich:element('demandTextId')}.focus();}"
                                                     reRender="stxtmessage,lblintroducerA"/>
                                    </h:inputText>
                                    <h:outputLabel id="lblintroducerA" value="#{AccountMaintenanceRegister.introAccount}" styleClass="label" ></h:outputLabel>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="demandPanalId" style="height:30px;display:#{AccountMaintenanceRegister.displayDmd}"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="demandLabelId" styleClass="label"  value="Demand Amount "  style="width:100%;text-align:left;" > </h:outputLabel>
                                <h:inputText id="demandTextId" styleClass="input" value="#{AccountMaintenanceRegister.amount}" style="width: 70px" disabled="#{AccountMaintenanceRegister.disableAmt}">
                                    <a4j:support event="onblur" focus="dmdFlagOption"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="dmdPanel" style="height:30px;display:#{AccountMaintenanceRegister.displayDmd}"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="dmdFlagLbl" styleClass="label"  value="Demand Flag "  style="width:100%;text-align:left;" > </h:outputLabel>
                                <h:selectOneListbox id="dmdFlagOption" styleClass="ddlist" size="1" style="color:black;width:70px" value="#{AccountMaintenanceRegister.dmdFlag}" disabled="#{AccountMaintenanceRegister.disableAmt}">
                                    <f:selectItems value="#{AccountMaintenanceRegister.dmdList}" />
                                    <a4j:support event="onblur" focus="dddocumentName"/>
                                </h:selectOneListbox>
                            </h:panelGrid>

                        </h:panelGrid>
                        <h:panelGrid id="eee" width="100%">
                            <h:panelGrid  columns="1" id="ee1" width="100%">
                                <h:panelGrid columns="2" columnClasses="col9" id="documentName" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lbldocumentName" styleClass="label"  value="Document Name "  style="width:100%;text-align:left;"/>
                                    <h:selectOneListbox id="dddocumentName" styleClass="ddlist" size="1" style="width: 130px" value="#{AccountMaintenanceRegister.docsnm}">
                                        <f:selectItems value="#{AccountMaintenanceRegister.docnamOption}" />
                                        <a4j:support event="onblur" focus="txtDocumentDetails"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columns="2" columnClasses="col9" id="documentDetails" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblDocumentDetails" styleClass="label" value="Document Details "  style="width:100%;text-align:left;"></h:outputLabel>
                                    <h:inputText id="txtDocumentDetails" styleClass="input" value="#{AccountMaintenanceRegister.docdetails}" 
                                                 onkeydown="this.value=this.value.toUpperCase();" maxlength="25">
                                        <a4j:support event="onblur" oncomplete="if(#{AccountMaintenanceRegister.flag==false}){#{rich:element('btnUpdate')}.focus();} 
                                                     else {#{rich:element('btnRefresh')}.focus();}"
                                                     reRender="btnUpdate,btnRefresh"/>
                                    </h:inputText>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="vtop" columns="1" id="rightPanel" style="width:100%;height:195px;text-align:center;background-color:#e8eef7">
                                    <a4j:region>
                                        <rich:dataTable value="#{AccountMaintenanceRegister.incis}" var="dataItem"
                                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                            <f:facet name="header">
                                                <rich:columnGroup  >
                                                    <rich:column colspan="8"><h:outputText value=""  /></rich:column>
                                                    <rich:column breakBefore="true"  ><h:outputText  value="Document Name"  /></rich:column>
                                                    <rich:column><h:outputText value="Document Detail"/></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column ><h:outputText value="#{dataItem.description}" /></rich:column>
                                            <rich:column ><h:outputText value="#{dataItem.docudetails}" /></rich:column>
                                        </rich:dataTable>
                                        <rich:datascroller  align="left" for="taskList"  maxPages="20" />
                                    </a4j:region>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <rich:panel header="Maturity Details" id="panelMaturityDetails" style="display:none;">
                        <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="cc12" style="height:30px"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblMatDt" styleClass="label"  value="Maturity Date"  style="width:100%;text-align:left;"/> 
                            <rich:calendar id="ddmaturitydate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" disabled="true" value="#{AccountMaintenanceRegister.maturitydate}" inputSize="10"/>
                            <h:outputLabel id="lblInterest1" styleClass="label"  value="Interest"  style="width:100%;text-align:left;"/>
                            <h:outputText id="txtInterest1" styleClass="output" value="#{AccountMaintenanceRegister.interest}"/>
                            <h:outputLabel id="lblAmount1" styleClass="label"  value="Amount"  style="width:100%;text-align:left;"/> 
                            <h:outputText id="txtAmount1" styleClass="output" value="#{AccountMaintenanceRegister.amount}"/>
                        </h:panelGrid>
                    </rich:panel>
                    <h:panelGrid columns="3" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton id="btnUpdate" value="Update"  disabled="#{AccountMaintenanceRegister.flag=='true'}" oncomplete="#{rich:component('savePanel')}.show() " />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AccountMaintenanceRegister.reFresh}" reRender="demandTextId,txtAccountNumber,stxtmessage,ddInterestOption,txtfhname,txtcn1,ddchkbkissue,txtMailingAddress,txtPermaAddress,txtphoneNo,txtpanNo,txtNominee,txtRelationship,txtacInstruction,ddopendate1,ddmaturitydate,txtInterest1,ddapplyMinBalCharge,txtAmount1,txtguradianName,ddintroducerAcNo,txtintroducerAcNo,ddoperMode,ddorganizationType,txtan,rightPanel,btnUpdate,txtjtName1,txtjtName2,txtjtName3,txtjtName4,txtcustid1,txtcustid2,txtcustid3,txtcustid4,dddocumentName,txtDocumentDetails,dddobNominee,txtrelNominee,txtaddNominee,lblintroducerA,stxtmessage,stxtAccNo,ddActCategType,lblTDSApplicable,ddTDSApplicable,panelMaturityDetails"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountMaintenanceRegister.exitFrm}" reRender="dddobNominee,txtAccountNumber,stxtmessage,ddInterestOption,txtfhname,txtcn1,ddchkbkissue,txtMailingAddress,txtPermaAddress,txtphoneNo,txtpanNo,txtNominee,txtRelationship,txtacInstruction,ddopendate1,ddmaturitydate,txtInterest1,ddapplyMinBalCharge,txtAmount1,txtguradianName,ddintroducerAcNo,txtintroducerAcNo,ddoperMode,ddorganizationType,txtan,rightPanel,btnUpdate,txtjtName1,txtjtName2,txtjtName3,txtjtName4,txtcustid1,txtcustid2,txtcustid3,txtcustid4,dddocumentName,txtDocumentDetails,txtrelNominee,txtaddNominee,lblintroducerA,stxtmessage,stxtAccNo,ddActCategType"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="300" onshow="#{rich:element('yesBtn')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                        <rich:componentControl for="savePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td colspan="2">
                                    <h:outputText value="Do you want to update account details." style="font-weight:bold;font-size:13px"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <h:outputText/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="yesBtn" value="Yes" action="#{AccountMaintenanceRegister.updateData}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="demandTextId,dddobNominee,txtAccountNumber,stxtmessage,ddInterestOption,txtfhname,txtcn1,ddchkbkissue,txtMailingAddress,txtPermaAddress,txtphoneNo,txtpanNo,txtNominee,txtRelationship,txtacInstruction,ddopendate1,ddmaturitydate,txtInterest1,ddapplyMinBalCharge,txtAmount1,txtguradianName,ddintroducerAcNo,txtintroducerAcNo,ddoperMode,ddorganizationType,txtan,rightPanel,btnUpdate,txtjtName1,txtjtName2,txtjtName3,txtjtName4,txtcustid1,txtcustid2,txtcustid3,txtcustid4,dddocumentName,txtDocumentDetails,txtrelNominee,txtaddNominee,lblintroducerA,ddActCategType,gridPanel1k" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="NoBtn" value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>