<%-- 
    Document   : centralizedIncidentialCharges
    Created on : 26 Dec, 2018, 12:55:18 PM
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
            <title>Centralized Incidental Charges</title>
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
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{CentralizedIncidentailCharges.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Centralized Incidental Charges"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{CentralizedIncidentailCharges.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="d22" styleClass="row1" width="100%" style="text-align:center;">
                        <h:outputText id="stxtmessage" styleClass="error" value="#{CentralizedIncidentailCharges.message}"  />
                    </h:panelGrid>

                    <h:panelGrid columns="2" columnClasses="col9,col9" id="grdPanel22" style="height:30px;text-align:center;" styleClass="row2" width="100%">
                        <h:panelGroup styleClass="label">
                            <h:outputLabel id="lblbranch" styleClass="headerLabel" value="Branch :"/>
                            <h:selectOneListbox id="ddbranch" styleClass="ddlist" size="1" style="width: 100px" value="#{CentralizedIncidentailCharges.branch}" >
                                <f:selectItems value="#{CentralizedIncidentailCharges.branchList}"/>
                            </h:selectOneListbox>
                          </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col9,col9" columns="2" id="a9" width="100%">
                        <h:panelGrid id="gridPanel1" columns="2" columnClasses="col9,col9" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblstatus" styleClass="headerLabel"  value="Status :" style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddstatus" styleClass="ddlist" size="1" style="width: 100px" value="#{CentralizedIncidentailCharges.status}" disabled="#{CentralizedIncidentailCharges.flag}" >
                                    <f:selectItems value="#{CentralizedIncidentailCharges.statusType}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9,col9" id="grdPanel2" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblactype" styleClass="headerLabel" value="Account Type :" style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddactype" styleClass="ddlist" size="1" style="width: 100px" value="#{CentralizedIncidentailCharges.acctype}" disabled="#{CentralizedIncidentailCharges.flag1}" >
                                    <f:selectItems value="#{CentralizedIncidentailCharges.acctTypeOption}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="gridPanel31" style="height:30px;" styleClass="row1"  width="100%">
                                <h:outputLabel id="lblfrom" styleClass="headerLabel"   value="From :" style="width:100%;text-align:left;" ><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar id="calfrom" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{CentralizedIncidentailCharges.fromDate}" disabled="#{CentralizedIncidentailCharges.flag2}" inputSize="10"></rich:calendar>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="gridPanel32" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblto" styleClass="headerLabel" value="To :" style="width:100%;text-align:left;" ><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar id="calto" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{CentralizedIncidentailCharges.toDate}" disabled="#{CentralizedIncidentailCharges.flag3}" inputSize="10"></rich:calendar>
                            </h:panelGrid>
                            <h:panelGrid columns="2"  columnClasses="col9" id="c" style="height:30px;" styleClass="row2" width="100%" >
                                <h:outputLabel id="lbl44" styleClass="headerLabel"   value="Ac To Be Debited :" style="width:100%;text-align:left;"  />
                                <h:outputText id="stxtdebactype" styleClass="output" value="As Per List In The Report  "/>
                            </h:panelGrid>
                            <h:panelGrid columns="2"  columnClasses="col9" id="d3" style="height:30px;" styleClass="row2" width="100%" >
                                <h:outputLabel id="lbldr" styleClass="headerLabel"   value="Dr Amt :" style="width:100%;text-align:left;"  />
                                <h:outputText id="stxtDebit1" styleClass="output" value="#{CentralizedIncidentailCharges.debAcctype}"/>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="b10" style="height:30px;" styleClass="row1" width="100%" >
                                <h:outputLabel id="lblcr" styleClass="headerLabel"   value="Ac To Be Credited :" style="width:100%;text-align:left;"  />
                                <h:outputText id="stxtcrdaccount" styleClass="output" value ="#{CentralizedIncidentailCharges.crdaccount}"/>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9" id="b9" style="height:30px;" styleClass="row1" width="100%" >
                                <h:outputLabel id="lblcr1" styleClass="headerLabel"   value="Cr Amt :" style="width:100%;text-align:left;"  />
                                <h:outputText id="stxtCredit" styleClass="output" value="#{CentralizedIncidentailCharges.crdAcctype}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col9" id="grdpanel4" style="height:30px;" styleClass="row2" width="100%" >
                            <h:outputLabel id="lblcharge" styleClass="headerLabel"  value="Minimum Charge Per Page @ :" style="width:100%;padding-left:250px;"/>
                            <h:inputText id="txtcharge" styleClass="input"  size="15"  value="#{CentralizedIncidentailCharges.charge}" disabled="#{CentralizedIncidentailCharges.flag4}" maxlength="8"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{CentralizedIncidentailCharges.incis}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="7"><h:outputText value="Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="AcNumber" /></rich:column>
                                            <rich:column><h:outputText value="CustName" /></rich:column>
                                            <rich:column><h:outputText value="Penality" /></rich:column>
                                            <rich:column><h:outputText value="Limit" /></rich:column>
                                            <rich:column><h:outputText value="OptStatus" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <rich:column><h:outputText value="Trans" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.accno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.penlty}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.lim}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{dataItem.optst}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.status1}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.tran}" /></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="5" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid  id="dww" style="height:30px;" styleClass="row2" width="100%" >
                            <h:outputLabel id="rrdww" styleClass="headerLabel" value="#These entries treated as one ledger page." style="width:100%;text-align:left;color:purple;"  />
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton value="Calculate" id="btn31" disabled="#{CentralizedIncidentailCharges.flag5}"  oncomplete="#{rich:component('savePanel')}.show()"/>
                                <a4j:commandButton value="Post" id="btn2" disabled="#{CentralizedIncidentailCharges.flag6}" oncomplete="#{rich:component('dishonorPanel')}.show()"/>
                                <a4j:commandButton id="btn3"  value="Refresh" action="#{CentralizedIncidentailCharges.reSet}" reRender="stxtDebit1,stxtCredit,a19,stxtcrdaccount,stxtmessage,ddstatus,ddactype,calfrom,calto,txtcharge,btn31,btn2" focus="ddstatus"/>
                                <a4j:commandButton id="btn4"  value="Exit" action="#{CentralizedIncidentailCharges.exitBtnAction}" reRender="stxtDebit1,stxtCredit,a19,stxtcrdaccount,stxtmessage,ddstatus,ddactype,calfrom,calto,txtcharge,btn31,btn2" />
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnCalcYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to calculate ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnCalcYes" action="#{CentralizedIncidentailCharges.calculationData}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();
                                                       if(#{CentralizedIncidentailCharges.flag6==false})
                                                       {
                                                       #{rich:component('popUpRepPanel')}.show();
                                                       }
                                                       else if(#{CentralizedIncidentailCharges.flag6==true})
                                                       {
                                                       #{rich:component('popUpRepPanel')}.hide();
                                                       }"
                                                       reRender="popUpRepPanel,stxtDebit1,stxtCredit,a19,stxtcrdaccount,stxtmessage,ddstatus,ddactype,calfrom,calto,txtcharge,btn31,btn2" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnCalcNo" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="dishonorPanel" autosized="true" width="200" onshow="#{rich:element('btnPostYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnPostYes" action="#{CentralizedIncidentailCharges.postData}" 
                                                       reRender="stxtDebit1,stxtCredit,a19,stxtcrdaccount,stxtmessage,ddstatus,ddactype,calfrom,calto,txtcharge,btn2,popUpRepPanel"
                                                       oncomplete="#{rich:component('dishonorPanel')}.hide();
                                                       if(#{CentralizedIncidentailCharges.flag6==true})
                                                       {
                                                       #{rich:component('popUpRepPanel')}.show();
                                                       }
                                                       else if(#{CentralizedIncidentailCharges.flag6==false})
                                                       {
                                                       #{rich:component('popUpRepPanel')}.hide();
                                                       }"/>

                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnPostNo" onclick="#{rich:component('dishonorPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Incidental Charges" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{CentralizedIncidentailCharges.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
