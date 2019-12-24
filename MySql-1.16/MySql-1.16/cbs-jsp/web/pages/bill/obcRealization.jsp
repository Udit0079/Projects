<%-- 
    Document   : obcRealization
    Created on : Sep 30, 2010, 10:45:11 AM
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
            <title>OBC Realization</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{obcRealization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="OBC Realization"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{obcRealization.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="d22"  styleClass="row1" style="text-align:center;color:red" width="100%" >
                        <h:outputText id="stxtmessage" styleClass="output" value="#{obcRealization.message}"  />
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelBillType" style="height:30px" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type:"><font class="required">*</font></h:outputLabel>
                        <h:panelGroup layout="block"   >
                            <h:selectOneListbox id="ddBillType" styleClass="ddlist" size="1" style="width: 50px" value="#{obcRealization.billType}" >
                                <f:selectItem itemValue="BC"/>
                                <a4j:support event="onblur" focus="txtAccountToBeDebited" />
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1y1" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAccountToBeDebited" styleClass="label" value="Bill No." ><font class="required">*</font></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                            <h:inputText id="txtAccountToBeDebited" styleClass="input" style="width:100px" value="#{obcRealization.billNo}" maxlength="9">
                                <a4j:support event="onblur" focus="ddYear" />
                            </h:inputText>
                            <h:outputLabel id="lblyear" styleClass="label" value="Year" ><font class="required">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddYear" styleClass="ddlist" size="1" style="width:80px" value="#{obcRealization.year}" >
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{obcRealization.fYear}"/>
                                <a4j:support event="onblur" action="#{obcRealization.tabData}"  reRender="newpanel,txtAcNo,txtName,stxtmessage,txtinstAmt,txtinstNo,txtinstDate,txtcm,txtpAmt,txtcredit" oncomplete="if(#{obcRealization.flag1=='false'}){#{rich:element('txtAccountToBeDebited')}.focus();} else{#{rich:element('txtbnkCharges')}.focus();}"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>


                    <h:panelGrid columnClasses="col9" columns="2" id="a9" width="100%">
                        <h:panelGrid columns="2" columnClasses="col9" id="acNo" style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblAcNo" styleClass="headerLabel"  value="AccountNo."  style="width:100%;text-align:left;"/>
                            <h:outputText id="txtAcNo" styleClass="output" value="#{obcRealization.acNo}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col9" id="acName" style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblName" styleClass="headerLabel"  value="Name"  style="width:100%;text-align:left;"/>
                            <h:outputText id="txtName" styleClass="output" value="#{obcRealization.name}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col7,col7,col10" columns="3" id="inst" width="100%">
                        <h:panelGrid columns="2" columnClasses="col9" id="instAmt" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblinstAmt" styleClass="headerLabel"  value="Inst.Amount"  style="width:100%;text-align:left;"/>
                            <h:outputText id="txtinstAmt" styleClass="output" value="#{obcRealization.instAmt}"/>
                        </h:panelGrid>

                        <h:panelGrid columns="2" columnClasses="col9" id="instNo" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblinstNo" styleClass="headerLabel"  value="Inst.No."  style="width:100%;text-align:left;"/>
                            <h:outputText id="txtinstNo" styleClass="output" value="#{obcRealization.instNo}"/>
                        </h:panelGrid>

                        <h:panelGrid columns="2" columnClasses="col9" id="instDate" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblinstDate" styleClass="headerLabel"  value="Inst.Date"  style="width:100%;text-align:left;"/>
                            <h:outputText id="txtinstDate" styleClass="output" value="#{obcRealization.instDate}"/>
                        </h:panelGrid>
                    </h:panelGrid>


                    <h:panelGrid columnClasses="col7,col7,col10" columns="3" id="cm" width="100%">
                        <h:panelGrid columns="2" columnClasses="col9" id="commission" style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblcm" styleClass="headerLabel"  value="Commission"  style="width:100%;text-align:left;"/>
                            <h:inputText id="txtcm" style="width:100px;" styleClass="input" value="#{obcRealization.commision}" maxlength="10" >
                                <a4j:support event="onblur" focus="txtpAmt" />
                            </h:inputText>
                        </h:panelGrid>

                        <h:panelGrid columns="2" columnClasses="col9" id="pAmt" style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblpAmt" styleClass="headerLabel"  value="Postage Amount"  style="width:100%;text-align:left;"/>
                            <h:inputText id="txtpAmt" styleClass="input" value="#{obcRealization.postAmt}" maxlength="10" >
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col9,col9" id="gridPanel31" style="height:30px;" styleClass="row2"  width="100%">

                            <h:outputLabel id="lblfrom" styleClass="headerLabel"   value="Date Of Realisation" style="width:100%;text-align:left;"  />
                            <rich:calendar id="calfrom" datePattern="dd/MM/yyyy" value="#{obcRealization.realiseDate}" >
                                <a4j:support event="onchanged" focus="txtbnkCharges" />
                            </rich:calendar>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="newpanel"  width="100%"   >
                        <h:panelGrid columns="4" columnClasses="col9" id="bnkCharges"  style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblbnkCharges" styleClass="headerLabel" rendered="#{obcRealization.panelReason =='True'}"   value="Collecting Bank Charges"   style="width:100%;text-align:left;"/>
                            <h:inputText id="txtbnkCharges" styleClass="input" style="width:60px;" rendered="#{obcRealization.panelReason =='True'}"  value="#{obcRealization.bnkCharges}" >
                                <a4j:support event="onblur" reRender="txtcredit,newpanl,newpanel,stxtmessage,btnDishonored" action="#{obcRealization.tabOnCharge}" focus="txtcredit"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col9" id="credit"   style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblcredit"  styleClass="headerLabel" rendered="#{obcRealization.panelReason =='True'}"  value="Amt To Be Credited"   style="width:100%;text-align:left;"/>
                            <h:inputText id="txtcredit"  styleClass="input" style="width:150px;"  disabled="true" rendered="#{obcRealization.panelReason =='True'}"  value="#{obcRealization.credit}" />
                        </h:panelGrid>
                    </h:panelGrid>


                    <h:panelGrid columnClasses="col9" columns="2" id="newpanl"  width="100%" >
                        <h:panelGrid columns="2" columnClasses="col9" id="retunCharge"style="height:30px;"   styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblretunCharge" styleClass="headerLabel" rendered="#{obcRealization.panelReason =='False'}"   value="Retun Charges"  style="width:100%;text-align:left;"/>
                            <h:inputText id="txtretunCharge" styleClass="input" style="width:60px;" rendered="#{obcRealization.panelReason =='False'}"   value="#{obcRealization.retCharges}" />
                        </h:panelGrid>

                        <h:panelGrid columns="2" columnClasses="col9" id="reason" style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblreason" styleClass="label" rendered="#{obcRealization.panelReason =='False'}"  value="Reason For Cancel" ><font class="required">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddreason" styleClass="ddlist" rendered="#{obcRealization.panelReason =='False'}"  size="1" style="width:100px" value="#{obcRealization.reason1}" >
                                <f:selectItem itemValue="--SELECT--"/>  
                                <f:selectItems value="#{obcRealization.reason}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>


                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>

                            <rich:dataTable value="#{obcRealization.incis}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"  ><h:outputText  value="Bill No."  /></rich:column>
                                        <rich:column ><h:outputText value="  Account No."/></rich:column>
                                        <rich:column><h:outputText value=" Bill Type"/></rich:column>
                                        <rich:column><h:outputText value="FYear" /></rich:column>
                                        <rich:column><h:outputText value=" Inst. No." /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Bank Address" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column ><h:outputText value="#{dataItem.billNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.accountNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fYear}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankAddress}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>


                    <h:panelGrid columns="1" id="footer" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup layout="block">
                            <a4j:commandButton  id="btnRealised11" value="Realised" disabled="#{obcRealization.flagg}" reRender="taskList"  oncomplete="#{rich:component('savePanel')}.show()"  >
                            </a4j:commandButton>

                            <a4j:commandButton   id="btnDishonored" value="Dishonored" disabled="#{obcRealization.flag}" reRender="newpanel,newpanl,stxtmessage,taskList,btnRealised11" action="#{obcRealization.dishonorClickBtn}"  oncomplete="#{rich:component('dishonorPanel')}.show()" >
                            </a4j:commandButton>

                            <a4j:commandButton   id="btnRefresh" value="Refresh" action="#{obcRealization.clear}" reRender="newpanel,txtAcNo,txtName,stxtmessage,txtinstAmt,txtinstNo,txtinstDate,txtcm,txtpAmt,txtAccountToBeDebited,ddYear,txtbnkCharges,stxtmessage,ddreason,txtretunCharge,newpanl,btnRealised11,btnDishonored" >
                            </a4j:commandButton>

                            <a4j:commandButton id="btnClose" value="Close" action="#{obcRealization.exitForm}" reRender="newpanel,txtAcNo,txtName,stxtmessage,txtinstAmt,txtinstNo,txtinstDate,txtcm,txtpAmt,txtAccountToBeDebited,ddYear,txtbnkCharges,stxtmessage,ddreason,txtretunCharge,newpanl,btnRealised11,btnDishonored" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>                
            </a4j:form>


            <rich:modalPanel id="savePanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Do You Want To Pass This Record" style="padding-right:15px;" />
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
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes"  action="#{obcRealization.realisedClick}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="taskList,newpanel,txtAcNo,txtName,stxtmessage,txtinstAmt,txtinstNo,txtinstDate,txtcm,txtpAmt,txtAccountToBeDebited,ddYear,txtbnkCharges,stxtmessage,ddreason,txtretunCharge,newpanl" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

            <rich:modalPanel id="dishonorPanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Are You Sure, You Want to Dishonor this Record?" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="dishonorPanel" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes"  action="#{obcRealization.dishonorClick}"
                                                       oncomplete="#{rich:component('dishonorPanel')}.hide();" reRender="taskList,newpanel,txtAcNo,txtName,stxtmessage,txtinstAmt,txtinstNo,txtinstDate,txtcm,txtpAmt,txtAccountToBeDebited,ddYear,txtbnkCharges,stxtmessage,ddreason,txtretunCharge,newpanl" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('dishonorPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>


        </body>
    </html>
</f:view>
