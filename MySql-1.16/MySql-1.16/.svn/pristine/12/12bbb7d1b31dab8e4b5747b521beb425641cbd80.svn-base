<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="relatedPersonInfo">
    <a4j:region>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row1" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelPersonType" styleClass="label" value="Related Person Type"/>
            <h:selectOneListbox id="ddRelPersonType" styleClass="ddlist" value="#{RelatedPersonInfo.relPersonType}" 
                                size="1" disabled="#{RelatedPersonInfo.otherFieldDisable}">
                <f:selectItems value="#{RelatedPersonInfo.relPersonTypeList}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblRelCustId" styleClass="label" value="Related Customer Id"/>
            <h:inputText id="txtRelCustId" styleClass="input" style="120px" maxlength="10" 
                         value="#{RelatedPersonInfo.relatedCustId}" disabled="#{RelatedPersonInfo.otherFieldDisable}">
                <a4j:support event="onblur" actionListener="#{RelatedPersonInfo.getCustDetail}" 
                             reRender="stxtRelCustName,ddRelPersonType,txtRelFirstName,txtRelMiddleName,txtRelLastName,
                             txtRelPan,txtRelUid,txtNregaJobCard,txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,
                             txtDin,ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,ddRelState,ddRelCountry,
                             txtJuriPostalCode,ddRelStatus" oncomplete="if(#{RelatedPersonInfo.custIdFocus == true})
                             {#{rich:element('ddRelStatus')}.focus();}"/>
            </h:inputText>
            <h:outputLabel id="lblRelCustName" styleClass="label" value="Related Customer Name"/>
            <h:outputText id="stxtRelCustName" styleClass="output" value="#{RelatedPersonInfo.relatedCustName}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row2" 
                     style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblRelFirstName" styleClass="label" value="First Name"/>
            <h:inputText id="txtRelFirstName" maxlength="70" styleClass="input" style="120px" 
                         value="#{RelatedPersonInfo.relatedFirstName}" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPersonInfo.fieldDisable}"/>
            <h:outputLabel id="lblRelMiddleName" styleClass="label" value="Middle Name"/>
            <h:inputText id="txtRelMiddleName" maxlength="70" styleClass="input" style="120px" 
                         value="#{RelatedPersonInfo.relatedMiddleName}" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPersonInfo.fieldDisable}"/>
            <h:outputLabel id="lblRelLastName" styleClass="label" value="Last Name"/>
            <h:inputText id="txtRelLastName" maxlength="70" styleClass="input" style="100px" 
                         value="#{RelatedPersonInfo.relatedLastName}" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPersonInfo.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row3" 
                     style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelPan" styleClass="label" value="Pan No"/>
            <h:inputText id="txtRelPan" styleClass="input" style="120px" 
                         value="#{RelatedPersonInfo.relPanNo}" disabled="#{RelatedPersonInfo.fieldDisable}" maxlength="10"
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblRelUid" styleClass="label" value="UID"/>
            <h:inputText id="txtRelUid" styleClass="input" style="120px" 
                         value="#{RelatedPersonInfo.relUid}" disabled="#{RelatedPersonInfo.fieldDisable}" maxlength="12"
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel/>
            <h:outputLabel/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row4" 
                     style="width:100%;text-align:left;display:#{RelatedPersonInfo.relIndView}" styleClass="row2">
            <h:outputLabel id="lblNregaJobCard" styleClass="label" value="NREGA Job Card"/>
            <h:inputText id="txtNregaJobCard" styleClass="input" style="120px" value="#{RelatedPersonInfo.relNregaJobCard}" 
                         disabled="#{RelatedPersonInfo.fieldDisable}" maxlength="25" 
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblPassNo" styleClass="label" value="Passport No"/>
            <h:inputText id="txtPassNo" styleClass="input" style="120px" value="#{RelatedPersonInfo.relPassportNo}" 
                         disabled="#{RelatedPersonInfo.fieldDisable}" maxlength="32" onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblPassExpDt" styleClass="label" value="Passport Expiry Date"/>
            <rich:calendar id="txtPassExpDt" value="#{RelatedPersonInfo.relPassportExpDt}" datePattern="dd/MM/yyyy" 
                           jointPoint="top-left" direction="top-right" inputSize="10" 
                           disabled="#{RelatedPersonInfo.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row5" 
                     style="width:100%;text-align:left;display:#{RelatedPersonInfo.relIndView}" styleClass="row1">
            <h:outputLabel id="lblVoterIdCard" styleClass="label" value="Voter ID Card"/>
            <h:inputText id="txtVoterIdCard" styleClass="input" style="120px;" 
                         value="#{RelatedPersonInfo.relVoterIdCard}" disabled="#{RelatedPersonInfo.fieldDisable}" 
                         maxlength="10" onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblDrLicNo" styleClass="label" value="Driving License No"/>
            <h:inputText id="txtDrLicNo" styleClass="input" style="120px" value="#{RelatedPersonInfo.relDrivingLiscNo}" 
                         maxlength="10" disabled="#{RelatedPersonInfo.fieldDisable}" 
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblDlExpiry" styleClass="label" value="Driving License Expiry Date"/>
            <rich:calendar id="DlExpiry" value="#{RelatedPersonInfo.dlExpiry}" datePattern="dd/MM/yyyy" 
                           jointPoint="top-left" direction="top-right" inputSize="10" 
                           disabled="#{RelatedPersonInfo.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row6" 
                     style="width:100%;text-align:left;display:#{RelatedPersonInfo.relComView}" styleClass="row2">
            <h:outputLabel id="lblDin" styleClass="label" value="DIN"/>
            <h:inputText id="txtDin" styleClass="input" style="120px;" value="#{RelatedPersonInfo.relDin}" 
                         disabled="#{RelatedPersonInfo.fieldDisable}" maxlength="8" 
                         onkeydown="this.value=this.value.toUpperCase();"/>
            <h:outputLabel id="lblExposed" styleClass="label" value="Politicaly Exposed"/>
            <h:selectOneListbox id="ddExposed" styleClass="ddlist" value="#{RelatedPersonInfo.relExposed}" 
                                size="1" disabled="#{RelatedPersonInfo.fieldDisable}">
                <f:selectItems value="#{RelatedPersonInfo.relExposedList}"/>
            </h:selectOneListbox>
            <h:outputLabel/>
            <h:outputLabel/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row7" 
                     style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelAddr1" styleClass="label" value="Address Line1"/>
            <h:inputText id="txtRelAddr1" styleClass="input" style="120px" value="#{RelatedPersonInfo.relAdd1}" 
                         maxlength="250" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPersonInfo.fieldDisable}"/>
            <h:outputLabel id="lblRelAddr2" styleClass="label" value="Address Line2"/>
            <h:inputText id="txtRelAddr2" styleClass="input" style="120px" value="#{RelatedPersonInfo.relAdd2}" 
                         maxlength="250" onkeydown="this.value=this.value.toUpperCase();" 
                         disabled="#{RelatedPersonInfo.fieldDisable}"/>
            <h:outputLabel id="lblRelCity" styleClass="label" value="City"/>
            <h:selectOneListbox id="ddRelCity" styleClass="ddlist" value="#{RelatedPersonInfo.relCity}" 
                                size="1" disabled="#{RelatedPersonInfo.fieldDisable}">
                <f:selectItems value="#{RelatedPersonInfo.relCityList}"/>
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row8" 
                     style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblRelState" styleClass="label" value="State"/>
            <h:selectOneListbox id="ddRelState" styleClass="ddlist" value="#{RelatedPersonInfo.relState}" 
                                size="1" disabled="#{RelatedPersonInfo.fieldDisable}">
                <f:selectItems value="#{RelatedPersonInfo.relStateList}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblRelCountry" styleClass="label" value="Country"/>
            <h:selectOneListbox id="ddRelCountry" styleClass="ddlist" value="#{RelatedPersonInfo.relCountry}" 
                                size="1" disabled="#{RelatedPersonInfo.fieldDisable}">
                <f:selectItems value="#{RelatedPersonInfo.relCountryList}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblRelPostalCode" styleClass="label" value="Postal Code"/>
            <h:inputText id="txtJuriPostalCode" styleClass="input" style="120px" value="#{RelatedPersonInfo.relPostalCode}" 
                         maxlength="6" disabled="#{RelatedPersonInfo.fieldDisable}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="row9" 
                     style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelStatus" styleClass="label" value="Status"/>
            <h:selectOneListbox id="ddRelStatus" styleClass="ddlist" value="#{RelatedPersonInfo.relStatus}" 
                                size="1">
                <f:selectItems value="#{RelatedPersonInfo.relStatusList}"/>
                <a4j:support event="onblur" actionListener="#{RelatedPersonInfo.onblurStatus}" reRender="realtedPersonsGrid,txnList,stxtError"/>
            </h:selectOneListbox>
            <h:outputLabel/>
            <h:outputLabel/>
            <h:outputLabel/>
            <h:outputLabel/>
        </h:panelGrid>
        <h:panelGrid id="realtedPersonsGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
            <rich:dataTable  value="#{RelatedPersonInfo.rpiTableList}" var="item"
                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                             width="100%">
                <f:facet name="header">
                    <rich:columnGroup>
                        <rich:column colspan="8"><h:outputText value="Related Person Detail"/></rich:column>
                        <rich:column breakBefore="true"><h:outputText value="Related Cust. ID"/></rich:column>
                        <rich:column><h:outputText value="Related Person Name"/></rich:column>
                        <rich:column><h:outputText value="Relationship Code" /></rich:column>
                        <rich:column><h:outputText value="Status" /></rich:column>
                        <rich:column visible="#{RelatedPersonInfo.selVisible}"><h:outputText value="Select"/></rich:column>
                        <rich:column visible="#{RelatedPersonInfo.delVisible}"><h:outputText value="Delete"/></rich:column>
                    </rich:columnGroup>
                </f:facet>
                <rich:column><h:outputText value="#{item.personCustomerid}"/></rich:column>
                <rich:column><h:outputText value="#{item.completeName}"/></rich:column>                            
                <rich:column><h:outputText value="#{item.relationshipCode}"/></rich:column>
                <rich:column><h:outputText value="#{item.delFlag}"/></rich:column>                            
                <rich:column style="text-align:center;width:40px">
                    <a4j:commandLink id="selectlink" action="#{RelatedPersonInfo.setTableToForm}" reRender="txtRelCustId,ddRelPersonType,txtRelFirstName,txtRelMiddleName,txtRelLastName,
                                     txtRelPan,txtRelUid,txtNregaJobCard,txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,
                                     txtDin,ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,ddRelState,ddRelCountry,
                                     txtJuriPostalCode,ddRelStatus,stxtRelCustName">
                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                        <f:setPropertyActionListener value="#{item}" target="#{RelatedPersonInfo.currentItem}"/>                                    
                    </a4j:commandLink>
                </rich:column>
                <rich:column style="text-align:center;width:40px">
                    <a4j:commandLink id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                        <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                        <f:setPropertyActionListener value="#{item}" target="#{RelatedPersonInfo.currentItem}"/>                                    
                    </a4j:commandLink>
                </rich:column>
            </rich:dataTable>
            <rich:datascroller id="unauthDataScroll" align="left" for="txnList" maxPages="20"/>
            <rich:modalPanel id="deletePanel" label="Confirmation Dialog" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this person ? "/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{RelatedPersonInfo.deleteForm}" reRender="realtedPersonsGrid,txnList,stxtError"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </h:panelGrid>







        <%--<h:panelGrid id="RelPerInfoPanel" columns="2" style="width:100%;text-align:center;">
    <h:panelGrid id="leftPanel9" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="relRow" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelCustId" styleClass="label" value="Customer ID"/>
            <h:inputText   disabled="#{RelatedPersonInfo.flag4}"value="#{RelatedPersonInfo.rpiCustId}"id="txtRelCustId" maxlength="10"styleClass="input" style="120px">
                <a4j:support actionListener="#{RelatedPersonInfo.onBlurRpiCustid}" event="onblur" reRender="txtRelName,stxtError"/>
            </h:inputText>
            <h:outputLabel id="lblRelName" styleClass="label" value="Name"/>
            <h:inputText  value="#{RelatedPersonInfo.rpiname}"id="txtRelName" maxlength="40"styleClass="input" style="120px" disabled="true">
                <a4j:support actionListener="#{RelatedPersonInfo.onblurRpiName}" event="onblur" reRender="stxtError"/>
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="relRow2" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblRelation" styleClass="label" value="Relation"/>
            <h:inputText  value="#{RelatedPersonInfo.rpiRelation}"id="txtRelation" maxlength="20"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();" >

                    </h:inputText>
                    <h:outputLabel id="lblDesc" styleClass="label" value="Description"/>
                    <h:inputText  value="#{RelatedPersonInfo.description}"id="txtDesc" maxlength="80"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support  event="onblur" reRender="taskList1,stxtError,txtRelCustId,txtRelName,txtRelation,txtDesc,rpiScroller" actionListener="#{RelatedPersonInfo.loadRpiGrid}"/>
                    </h:inputText>
                </h:panelGrid>
            </h:panelGrid>
        </h:panelGrid>
        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel104" width="100%" styleClass="row2" style="height:200px;">
            <rich:contextMenu attached="false" id="menu2" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{RelatedPersonInfo.fetchCurrentRow2}">
                    <a4j:actionparam name="custId" value="{custId}" />
                    <a4j:actionparam name="row" value="{currentRowTodRef}" />
                </rich:menuItem>
            </rich:contextMenu>
            <rich:dataTable value="#{RelatedPersonInfo.rpiTableList}"   var="dataItem1" rowClasses="gridrow1, gridrow2" id="taskList1" columnsWidth="100"
                            rowKeyVar="row" rows="5" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                <f:facet name="header">
                    <rich:columnGroup>
                        <rich:column colspan="6"><h:outputText value="Contact Person Detail" /></rich:column>
                        <rich:column breakBefore="true"><h:outputText value="Customer Id" /></rich:column>
                        <rich:column><h:outputText value="S.No."/></rich:column>
                        <rich:column><h:outputText value="Name"/></rich:column>
                        <rich:column><h:outputText value="Relation" /> </rich:column>
                        <rich:column><h:outputText value="Description" /></rich:column>
                        <rich:column><h:outputText value="Select Record" /></rich:column>
                    </rich:columnGroup>
                </f:facet>
                <rich:column><h:outputText value="#{dataItem1.custId}"/></rich:column>
                <rich:column><h:outputText value="#{dataItem1.srNo}" /></rich:column>
                <rich:column><h:outputText value="#{dataItem1.custName}" /></rich:column>
                <rich:column><h:outputText value="#{dataItem1.custRelation}" /></rich:column>
                <rich:column><h:outputText value="#{dataItem1.custDescription}" /></rich:column>
                <rich:column style="text-align:center;width:40px">
                    <a4j:commandLink  ajaxSingle="true" id="selectlink" oncomplete="#{rich:component('showPanel2')}.show();"
                                      >
                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                        <f:setPropertyActionListener value="#{dataItem1}" target="#{RelatedPersonInfo.currentItemRpi}"/>
                        <f:setPropertyActionListener value="#{row}" target="#{RelatedPersonInfo.currentRowTodRef}"/>
                    </a4j:commandLink>
        
        </rich:column>
        </rich:dataTable>
        <rich:datascroller id="rpiScroller"align="left" for="taskList1" maxPages="20" />
        <rich:modalPanel id="showPanel2" autosized="true" width="250" onshow="#{rich:element('btnSelectRpi')}.focus();">
            <f:facet name="header">
                <h:outputText value="Do you want to Select or Delete this Row." style="padding-right:15px;" />
            </f:facet>
            <f:facet name="controls">
                <h:panelGroup>
                    <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink2" />
                    <rich:componentControl for="showPanel2" attachTo="hidelink2" operation="hide" event="onclick" />
                </h:panelGroup>
            </f:facet>
            <h:form>
                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <tbody>
                        <tr style="height:40px">
                            <td align="center" width="50%" colspan="2">
                                <a4j:commandButton value="SELECT" id="btnSelectRpi" ajaxSingle="true"action="#{RelatedPersonInfo.selectRpi}"reRender="taskList1,txtRelCustId,txtRelName,txtRelation,txtDesc,stxtError"onclick="#{rich:component('showPanel2')}.hide();" oncomplete="#{rich:element('txtRelCustId')}.focus();"/>
                            </td>
                            <td align="center" width="50%">
                                <a4j:commandButton value="DELETE"  ajaxSingle="true"action="#{RelatedPersonInfo.deleteGridRpi}"reRender="taskList1"onclick="#{rich:component('showPanel2')}.hide();" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </h:form>
        </rich:modalPanel>
        </h:panelGrid>--%>
    </a4j:region>
</h:form>
