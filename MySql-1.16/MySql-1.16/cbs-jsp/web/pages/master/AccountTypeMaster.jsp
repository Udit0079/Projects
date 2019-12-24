<%-- 
    Document   : AccountTypeMaster
    Created on : 3 Dec, 2010, 5:35:49 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Account Type Master</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript"></script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="label" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountTypeMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="label" value=" Account Type Master"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="label" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountTypeMaster.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="d22" style="width:100%;text-align:center;" styleClass="row1"  >
                        <h:outputText id="stxtmessage" styleClass="output" value="#{AccountTypeMaster.message}"  style="width:100%;color:red"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  style="width:100%;text-align:left;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcNature" styleClass="label"  value="Account Nature" style="width:100%;text-align:left;" />
                        <h:selectOneListbox id="ddAcNature" styleClass="ddlist" size="1" style="width: 100px" value="#{AccountTypeMaster.nature}">
                            <f:selectItem itemValue="--Select--"/>
                            <f:selectItems value="#{AccountTypeMaster.acctNatureOption}"/>
                        </h:selectOneListbox>
                        <h:outputText id="panel1"/>
                        <h:outputText id="panel2"/>
                        <h:outputText id="panel3"/>
                        <h:outputText id="panel4"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="accountType" style="height:30px;"  styleClass="row1" width="100%"  >
                        <h:outputLabel id="lblAccountType" styleClass="label"  value="Account Code / Account Type"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup  layout="block" >
                            <h:inputText id="txtAccountType" styleClass="input" value="#{AccountTypeMaster.accType}"   style="width: 45px" onkeyup="this.value = this.value.toUpperCase();" maxlength="2">
                                <a4j:support event="onblur"  action="#{AccountTypeMaster.onBlurAcctCode}" reRender="stxtmessage,ddAcNature,txtAccountType,txtDescription,ddGlhead,txtGlhead,update,ddIntHead,txtIntHead,ddGlHeadProvision,txtGlHeadProvision,btnSave,txtMinimumBalance,txtMinBalance,txtChequeBook,txtRateInterest,txtstaffROI,txtGlHeadINCURI,txtPenalty,txtMinBalCharge,txtPeProductCode,ddOFNatureOf,textacType" focus="textacType"/>
                            </h:inputText>
                            <h:inputText id="textacType" styleClass="input" value="#{AccountTypeMaster.accountType}" style="width: 50px" onkeyup="this.value = this.value.toUpperCase();" maxlength="2"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblDescription" styleClass="label"  value="Description"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtDescription" styleClass="input" value="#{AccountTypeMaster.description}" onkeyup="this.value = this.value.toUpperCase();" maxlength="40"/>
                        <h:outputLabel id="lblGlhead" styleClass="label"  value="G.L. Head For A/c Type"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                            <h:inputText id="txtGlhead" styleClass="input" value="#{AccountTypeMaster.oldAcctGl1}" style="width:95px" onkeyup="this.value = this.value.toUpperCase();" maxlength="8">
                                <a4j:support action="#{AccountTypeMaster.getAccountDetailGLHeadAcType}" event="onblur"
                                             reRender="stxtGlhead,stxtmessage" limitToList="true" focus="txtIntHead"/> 
                            </h:inputText>  
                            <h:outputLabel id="stxtGlhead" styleClass="label"  value="#{AccountTypeMaster.acctGl1}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="glhead" style="height:30px;"  styleClass="row2" width="100%"  >
                        <h:outputLabel id="lblIntHead" styleClass="label"  value="Int. Head (G.L)"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                            <h:inputText id="txtIntHead" styleClass="input" value="#{AccountTypeMaster.oldIntHead1}" onkeyup="this.value = this.value.toUpperCase();" style="width:95px" maxlength="8">
                                <a4j:support action="#{AccountTypeMaster.getAccountDetailIntGLHead}" event="onblur"
                                             reRender="stxtIntHead,stxtmessage" limitToList="true" focus="txtGlHeadProvision"/> 
                            </h:inputText>
                            <h:outputLabel id="stxtIntHead" styleClass="label"  value="#{AccountTypeMaster.intHead1}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblGlHeadProvision" styleClass="label"  value="G.L. Head Provision For Interest"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                            <h:inputText id="txtGlHeadProvision" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{AccountTypeMaster.oldGlHeadProvision1}" maxlength="8" style="width:95px">
                                <a4j:support action="#{AccountTypeMaster.getAccountDetailGlHeadProvision}" event="onblur"
                                             reRender="stxtGlHeadProvision,stxtmessage" limitToList="true" focus="txtMinimumBalance"/> 
                            </h:inputText>
                            <h:outputLabel id="stxtGlHeadProvision" styleClass="label"  value="#{AccountTypeMaster.glHeadProvision1}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblMinimumBalance" styleClass="label"  value="Minimum Balance"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtMinimumBalance" styleClass="input" value="#{AccountTypeMaster.minBalance}" style="width:95px" maxlength="5"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="minBalance" style="height:30px;"  styleClass="row2" width="100%"  >
                        <h:outputLabel id="lblMinBalance" styleClass="label"  value="Min Balance (Cheque)"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtMinBalance" styleClass="input" value="#{AccountTypeMaster.minbalChq}" maxlength="10" style="width:95px"/>
                        <h:outputLabel id="lblChequeBook" styleClass="label"  value="Cheque Book Series"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtChequeBook" styleClass="input" value="#{AccountTypeMaster.chqBookSeries}" maxlength="8" style="width:95px"/>
                        <h:outputLabel id="lblRateInterest" styleClass="label"  value="Rate Of Interest"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRateInterest" styleClass="input" value="#{AccountTypeMaster.roi}" maxlength="8" style="width:95px"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="rateInterest" style="height:30px;"  styleClass="row1" width="100%"  >
                        <h:outputLabel id="lblstaffROI" styleClass="label"  value="Staff (ROI)"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtstaffROI" styleClass="input" value="#{AccountTypeMaster.roiStaff}" maxlength="8" style="width:95px"/>
                        <h:outputLabel id="lblGlHeadINCURI" styleClass="label"  value="G.L Head INCURI"  style="width:100%;text-align:left;"> </h:outputLabel>
                        <h:inputText id="txtGlHeadINCURI" styleClass="input" value="#{AccountTypeMaster.glHeadIncuri}" maxlength="8" style="width:95px"/>
                        <h:outputLabel id="lblPenalty" styleClass="label"  value="Penalty"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtPenalty" styleClass="input" value="#{AccountTypeMaster.penalty}" maxlength="8" style="width:95px"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="glHeadINCURI" style="height:30px;"  styleClass="row2" width="100%"  >
                        <h:outputLabel id="lblMinBalCharge" styleClass="label"  value="Min. Bal. Charge"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtMinBalCharge" styleClass="input" value="#{AccountTypeMaster.minBalCharges}" maxlength="10" style="width:95px"/>
                        <h:outputLabel id="lblProductCode" styleClass="label"  value="Product Code"  style="width:100%;text-align:left;"> <font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtPeProductCode" styleClass="input" value="#{AccountTypeMaster.productCode}" maxlength="4" style="width:95px"/>
                        <h:outputLabel id="lblOFNatureOf" styleClass="label"  value="OF Nature Of T/D's" style="width:100%;text-align:left;" />
                        <h:selectOneListbox id="ddOFNatureOf" styleClass="ddlist" size="1" style="width: 100px" value="#{AccountTypeMaster.tdsNature}">
                            <f:selectItem itemValue="--Select--"/>
                            <f:selectItem itemValue="Yes"/>
                            <f:selectItem itemValue="No"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="HoextractPanel41" columns="1" columnClasses="vtop" styleClass="row2" style="height:200px;" width="100%">
                        <rich:dataTable  value="#{AccountTypeMaster.gridData}" var="dataitem" rowClasses="gridrow1, gridrow2" rows="10" id="taskList1" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Acctcode"/></rich:column>
                                    <rich:column><h:outputText value="AcctDesc"/></rich:column>
                                    <rich:column><h:outputText value="Acctnature"/></rich:column>
                                    <rich:column><h:outputText value="GlHead"/></rich:column>
                                    <rich:column><h:outputText value="GlHeadint"/></rich:column>
                                    <rich:column><h:outputText value="MinBal"/></rich:column>
                                    <rich:column><h:outputText value="MinInt"/></rich:column>
                                    <rich:column><h:outputText value="MinBalCharge"/></rich:column>
                                    <rich:column><h:outputText value="ChqSrNo"/></rich:column>
                                    <rich:column><h:outputText value="GlHeadProv"/></rich:column>
                                    <rich:column><h:outputText value="ProductCode"/></rich:column>
                                    <rich:column><h:outputText value="OfAcctNature"/></rich:column>
                                    <rich:column><h:outputText value="StaffInt"/></rich:column>
                                    <rich:column><h:outputText value="MinBalChq"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column>
                                <h:outputText value="#{dataitem.acctcode}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.acctDesc}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.acctnature}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.glHead}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=" #{dataitem.glheadint}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.minbal}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.minInt}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.minbalcharge }"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=" #{dataitem.chqsrno}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.GLheadProv}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.productcode}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.ofAcctnature}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.staffInt}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.minbal_chq}"/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList1" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid columns="3" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton  id="btnSave" disabled="#{AccountTypeMaster.save}" value="Save" oncomplete="#{rich:component('savePanel')}.show() "/>
                            <a4j:commandButton   id="update" disabled="#{AccountTypeMaster.update1}"  value="Update" oncomplete="#{rich:component('updatePanel')}.show() "/>
                            <a4j:commandButton  id="btnReport" value="Report" action="#{AccountTypeMaster.Report}" reRender="stxtmessage,HoextractPanel41"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" reRender="stxtmessage,btnSave,update,ddAcNature,txtAccountType,txtDescription,txtGlhead,txtIntHead,txtGlHeadProvision,txtMinimumBalance,txtMinBalance,txtChequeBook,txtRateInterest,txtstaffROI,txtGlHeadINCURI,txtPenalty,txtMinBalCharge,txtPeProductCode,ddOFNatureOf,HoextractPanel41,stxtGlhead,stxtIntHead,stxtGlHeadProvision,textacType" action="#{AccountTypeMaster.clear}"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountTypeMaster.exitBtnAction}" reRender="stxtmessage,btnSave,update,ddAcNature,txtAccountType,txtDescription,ddGlhead,txtGlhead,ddIntHead,txtIntHead,ddGlHeadProvision,txtGlHeadProvision,txtMinimumBalance,txtMinBalance,txtChequeBook,txtRateInterest,txtstaffROI,txtGlHeadINCURI,txtPenalty,txtMinBalCharge,txtPeProductCode,ddOFNatureOf,HoextractPanel41,stxtGlhead,stxtIntHead,stxtGlHeadProvision">
                            </a4j:commandButton>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus()">
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
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%" colspan="2">
                                    <h:outputText value="Do you want to save this record ?" style="padding-right:15px;" />
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" action="#{AccountTypeMaster.save}" id="btnYes"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="stxtmessage,btnSave,update,ddAcNature,txtAccountType,txtDescription,ddGlhead,txtGlhead,ddIntHead,txtIntHead,ddGlHeadProvision,txtGlHeadProvision,txtMinimumBalance,txtMinBalance,txtChequeBook,txtRateInterest,txtstaffROI,txtGlHeadINCURI,txtPenalty,txtMinBalCharge,txtPeProductCode,ddOFNatureOf,HoextractPanel41,stxtGlhead,stxtIntHead,stxtGlHeadProvision" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="updatePanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Do You Want To Update This Record" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink21" />
                        <rich:componentControl for="updatePanel" attachTo="hidelink21" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" action="#{AccountTypeMaster.updateData}"
                                                       oncomplete="#{rich:component('updatePanel')}.hide();" reRender="stxtmessage,btnSave,update,ddAcNature,txtAccountType,txtDescription,ddGlhead,txtGlhead,ddIntHead,txtIntHead,ddGlHeadProvision,txtGlHeadProvision,txtMinimumBalance,txtMinBalance,txtChequeBook,txtRateInterest,txtstaffROI,txtGlHeadINCURI,txtPenalty,txtMinBalCharge,txtPeProductCode,ddOFNatureOf,HoextractPanel41,stxtGlhead,stxtIntHead,stxtGlHeadProvision,txtGlhead" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
