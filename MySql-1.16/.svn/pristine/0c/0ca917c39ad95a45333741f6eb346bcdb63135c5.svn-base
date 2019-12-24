<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="kycDetails">
    <a4j:region>
    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15" columns="7" id="kycGrid1" style="width:100%;text-align:left;" styleClass="row1">
        <h:outputLabel id="lblEducationDetails" styleClass="label" value="Education Details"/>
        <h:selectOneListbox id="ddEducationDetails" styleClass="ddlist" size="1" value="#{KycDetails.educationDetails}">
            <f:selectItems value="#{KycDetails.educationDetailsList}"/>
        </h:selectOneListbox>
        <h:outputLabel id="lblDependents" styleClass="label" value="Dependents"/>
        <h:panelGroup id="spouseGrp" layout="block">
            <h:selectBooleanCheckbox id="checkSpouse" value="#{KycDetails.spouse}">
            </h:selectBooleanCheckbox>
            <h:outputLabel id="lblSpouse" styleClass="label" value="Spouse"/>
        </h:panelGroup>
        <h:panelGroup id="parentsGrp" layout="block">
            <h:selectBooleanCheckbox id="checkParents" value="#{KycDetails.parents}">
            </h:selectBooleanCheckbox>
            <h:outputLabel id="lblParents" styleClass="label" value="Parents"/>
        </h:panelGroup>
        <h:panelGroup id="childrenGrp" layout="block">
            <h:selectBooleanCheckbox id="checkChildren" value="#{KycDetails.children}">
                <a4j:support event="onclick" actionListener="#{KycDetails.onclickChildren}" reRender="lblChild,txtChildren"/>
            </h:selectBooleanCheckbox>
            <h:outputLabel id="lblChildren" styleClass="label" value="Children"/>
        </h:panelGroup>
        <h:panelGroup id="childGrp" layout="block">
            <h:outputLabel id="lblChild" styleClass="label" value="No. Of Children" style="display:#{KycDetails.childrenFlag}"/>
            <h:inputText id="txtChildren" styleClass="input" style="width:20px;display:#{KycDetails.childrenFlag};" value="#{KycDetails.noOfChildren}">
            </h:inputText>
        </h:panelGroup>


    </h:panelGrid>
    <rich:panel header="Family Members" style="text-align:left;">
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1,col1" columns="7" id="kycGrid2" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblAgeGroup1" styleClass="label" value="Age Group"/>
            <h:outputLabel id="lblAgeGroup2" styleClass="label" value="UP To 10 Yrs."/>
            <h:outputLabel id="lblAgeGroup3" styleClass="label" value="11 To 20 Yrs."/>
            <h:outputLabel id="lblAgeGroup4" styleClass="label" value="21 To 45 Yrs."/>
            <h:outputLabel id="lblAgeGroup5" styleClass="label" value="46 To 60 Yrs."/>
            <h:outputLabel id="lblAgeGroup6" styleClass="label" value="Above 61 Yrs."/>
            <h:outputLabel id="lblAgeGroup7" styleClass="label" value="Total"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1,col1" columns="7" id="kycGrid3" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblNoMales" styleClass="label" value="No. Of Males"/>
            <h:inputText id="txtNoMales1" styleClass="input" style="width:20px;" value="#{KycDetails.male1}">
                <a4j:support actionListener="#{KycDetails.onblurMale}" event="onblur" reRender="txtNoMales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoMales2" styleClass="input" style="width:20px;" value="#{KycDetails.male2}">
                <a4j:support actionListener="#{KycDetails.onblurMale}" event="onblur" reRender="txtNoMales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoMales3" styleClass="input" style="width:20px;" value="#{KycDetails.male3}">
                <a4j:support actionListener="#{KycDetails.onblurMale}" event="onblur" reRender="txtNoMales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoMales4" styleClass="input" style="width:20px;" value="#{KycDetails.male4}">
                <a4j:support actionListener="#{KycDetails.onblurMale}" event="onblur" reRender="txtNoMales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoMales5" styleClass="input" style="width:20px;" value="#{KycDetails.male5}">
                <a4j:support actionListener="#{KycDetails.onblurMale}" event="onblur" reRender="txtNoMales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoMales6" styleClass="input" style="width:20px;" value="#{KycDetails.totalMale}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1,col1" columns="7" id="kycGrid4" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblNoFemales" styleClass="label" value="No. Of Females"/>
            <h:inputText id="txtNoFemales1" styleClass="input" style="width:20px;" value="#{KycDetails.female1}">
                <a4j:support actionListener="#{KycDetails.onblurFemale}" event="onblur" reRender="txtNoFemales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoFemales2" styleClass="input" style="width:20px;" value="#{KycDetails.female2}">
                <a4j:support actionListener="#{KycDetails.onblurFemale}" event="onblur" reRender="txtNoFemales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoFemales3" styleClass="input" style="width:20px;" value="#{KycDetails.female3}">
                <a4j:support actionListener="#{KycDetails.onblurFemale}" event="onblur" reRender="txtNoFemales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoFemales4" styleClass="input" style="width:20px;" value="#{KycDetails.female4}">
                <a4j:support actionListener="#{KycDetails.onblurFemale}" event="onblur" reRender="txtNoFemales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoFemales5" styleClass="input" style="width:20px;" value="#{KycDetails.female5}">
                <a4j:support actionListener="#{KycDetails.onblurFemale}" event="onblur" reRender="txtNoFemales6,stxtError"/>
            </h:inputText>
            <h:inputText id="txtNoFemales6" styleClass="input" style="width:20px;" value="#{KycDetails.totalFemale}"/>
        </h:panelGrid>
    </rich:panel>
    <rich:panel header="Any Relative Setteled In Abroad If Yes Then:" style="text-align:left;">
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycGrid5" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelativeName1" styleClass="label" value="1. Name"/>
            <h:inputText id="txtRelativeName1" styleClass="input" />
            <h:outputLabel id="lblRelativeAddress1" styleClass="label" value="Address"/>
            <h:inputText id="txtRelativeAddress1" styleClass="input" />
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycGrid6" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblRelativeName2" styleClass="label" value="2. Name"/>
            <h:inputText id="txtRelativeName2" styleClass="input" />
            <h:outputLabel id="lblRelativeAddress2" styleClass="label" value="Address"/>
            <h:inputText id="txtRelativeAddress2" styleClass="input" />
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycGrid7" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRelativeName3" styleClass="label" value="3. Name"/>
            <h:inputText id="txtRelativeName3" styleClass="input" />
            <h:outputLabel id="lblRelativeAddress3" styleClass="label" value="Address"/>
            <h:inputText id="txtRelativeAddress3" styleClass="input" />
        </h:panelGrid>
    </rich:panel>
    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycGrid8" style="width:100%;text-align:left;" styleClass="row2">
        <h:outputLabel id="lblAbroad" styleClass="label" value="How many times you have been Abroad in last three years"/>
        <h:inputText id="txtAbroad" styleClass="input" value="#{KycDetails.abroadTime}"/>
        <h:outputLabel id="lblRelativeInBank" styleClass="label" value="Do you have any relatives in Bank?"/>
        <h:selectOneListbox id="ddRelativeInBank" styleClass="ddlist" size="1" value="#{KycDetails.relativeInBank}">
            <f:selectItems value="#{KycDetails.relativeInBankList}"/>
        </h:selectOneListbox>
    </h:panelGrid>
    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycGrid9" style="width:100%;text-align:left;" styleClass="row1">
        <h:outputLabel id="lblCreditCardInfo" styleClass="label" value="Do you have any Credit Card?"/>
        <h:selectOneListbox id="ddCreditCardInfo" styleClass="ddlist" size="1" value="#{KycDetails.creditCardDetails}">
            <f:selectItems value="#{KycDetails.creditCardDetailsList}"/>
        </h:selectOneListbox>
        <h:outputLabel id="lblDirectorRelation" styleClass="label" value="Do you have any relation with the Director of the Respective Bank?"/>
        <h:selectOneListbox id="ddDirectorRelation" styleClass="ddlist" size="1" value="#{KycDetails.relationWithDirector}">
            <f:selectItems value="#{KycDetails.relationWithDirectorList}"/>
        </h:selectOneListbox>
    </h:panelGrid>

    <rich:panel header="Assets" style="text-align:left;">
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1,col1" columns="7" id="kycGrid10" style="width:100%;text-align:left;" styleClass="row2">
           
            <h:outputLabel id="lblAssetsType" styleClass="label" value="Assets Type"/>
            <h:selectOneListbox id="ddAssetsType" styleClass="ddlist" size="1" value="#{KycDetails.assetsType}">
                <f:selectItems value="#{KycDetails.assetsTypeList}"/>
                <a4j:support actionListener="#{KycDetails.onblurAssetsType}" event="onblur" reRender="ddAssetOwnership,ddAssetValue,stxtError,btnAdd1" focus="ddAssetOwnership" oncomplete="#{rich:component('btnAdd1')}.disabled='false';" />
            </h:selectOneListbox>
            <h:outputLabel id="lblAssetOwnership" styleClass="label" value="Asset Ownership" style="display:#{KycDetails.assetFlag}"/>
            <h:selectOneListbox id="ddAssetOwnership" styleClass="ddlist" size="1" value="#{KycDetails.assetsOwnership}" style="display:#{KycDetails.assetFlag}">
                <f:selectItems value="#{KycDetails.assetsOwnershipList}"/>
               
            </h:selectOneListbox>
            <h:outputLabel id="lblAssetValue" styleClass="label" value="Asset Value" style="display:#{KycDetails.assetFlag}"/>
            <h:selectOneListbox id="ddAssetValue" styleClass="ddlist" size="1" value="#{KycDetails.assetValue}" style="display:#{KycDetails.assetFlag}">
                <f:selectItems value="#{KycDetails.assetValueList}"/>
               
            </h:selectOneListbox>
            <a4j:commandButton id="btnAdd1" value="Add" action="#{KycDetails.clickOnAdd1}" reRender="taskList3,ddAssetsType,ddAssetOwnership,ddAssetValue,btnAdd1,stxtError,assetsScroller" oncomplete="#{rich:element('btnAdd1')}.disabled='true';" focus="ddAssetsType" />
           
        </h:panelGrid>
        <h:panelGrid columnClasses="vtop" columns="1" id="kycGrid11" width="100%" styleClass="row1" style="height:100px;">
            <rich:contextMenu attached="false" id="menu3" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{KycDetails.fetchCurrentRow3}">
                    <a4j:actionparam name="custAssets" value="{assets}" />
                    <a4j:actionparam name="row" value="{currentRowAssetTable}" />
                </rich:menuItem>
            </rich:contextMenu>
            <rich:dataTable var="dataItem3" value="#{KycDetails.listAssetsTable}" rowClasses="gridrow1, gridrow2" id="taskList3" columnsWidth="100"
                            rowKeyVar="row" rows="5" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                <f:facet name="header">
                    <rich:columnGroup>
                        <rich:column colspan="5"></rich:column>
                        <rich:column breakBefore="true"><h:outputText value="S.No." /></rich:column>
                        <rich:column><h:outputText value="Assets Type" /></rich:column>
                        <rich:column><h:outputText value="Assets"/></rich:column>
                        <rich:column><h:outputText value="Assets Value" /> </rich:column>
                        <rich:column><h:outputText value="Select Record" /> </rich:column>
                    </rich:columnGroup>
                </f:facet>
                <rich:column><h:outputText value="#{dataItem3.sNo1}" /></rich:column>
                <rich:column><h:outputText value="#{dataItem3.astType}" /></rich:column>
                <rich:column><h:outputText value="#{dataItem3.assets}" /></rich:column>
                <rich:column><h:outputText value="#{dataItem3.astValue}" /></rich:column>
                <rich:column style="text-align:center;width:40px">
                    <a4j:commandLink  ajaxSingle="true" id="selectlink3" oncomplete="#{rich:component('showPanel3')}.show();"
                                      >
                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                        <f:setPropertyActionListener value="#{dataItem3}" target="#{KycDetails.currentItemAssetTavle}"/>
                        <f:setPropertyActionListener value="#{row}" target="#{KycDetails.currentRowAssetTable}"/>
                    </a4j:commandLink>
                    <%--<rich:toolTip for="selectlink3" value="Select This Row"/>--%>
                </rich:column>
            </rich:dataTable>
            <rich:datascroller id="assetsScroller"align="left" for="taskList3" maxPages="20" />
          
            <rich:modalPanel id="showPanel3" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Do you want to Select or Delete this Row." style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="showPanel3" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="SELECT"  ajaxSingle="true"action="#{KycDetails.selectAssets}" reRender="taskList3,ddAssetsType,ddAssetOwnership,ddAssetValue,stxtError" onclick="#{rich:component('showPanel3')}.hide();" focus="ddAssetsType" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="DELETE"  ajaxSingle="true"action="#{KycDetails.deleteGridAssets}" reRender="taskList3"onclick="#{rich:component('showPanel3')}.hide();#{rich:element('ddAssetsType')}.focus();" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
             
        </h:panelGrid>
    </rich:panel>
   
    <rich:panel header="Existing Credit Facility/Loans" style="text-align:left;">
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1,col1" columns="7" id="kycGrid12" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblExistingLoans" styleClass="label" value="Existing Loans"/>
            <h:selectOneListbox id="ddExistingLoans" styleClass="ddlist" size="1" value="#{KycDetails.existLoans}">
                <f:selectItems value="#{KycDetails.existLoansList}"/>
                <a4j:support actionListener="#{KycDetails.onblurExistingLoans}" event="onblur" reRender="txtLoanAmount,btnAdd2" focus="txtLoanAmount"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblLoanAmount" styleClass="label" value="Loan Amount"/>
            <h:inputText id="txtLoanAmount" styleClass="input" value="#{KycDetails.loanAmount}" style="width:70px;" disabled="#{KycDetails.existLoanAmtFlag}"/>
            <a4j:commandButton id="btnAdd2" action="#{KycDetails.clickOnAdd2}" value="Add" disabled="#{KycDetails.btnAdd2Flag}" oncomplete="#{rich:element('btnAdd2')}.disabled='true';#{rich:element('ddExistingLoans')}.focus();" reRender="taskList4,ddExistingLoans,txtLoanAmount,stxtError,loanScroller"/>
            <h:outputText/>
            <h:outputText/>
        </h:panelGrid>
        <h:panelGrid columnClasses="vtop" columns="1" id="kycGrid13" width="100%" styleClass="row1" style="height:100px;">
            <rich:contextMenu attached="false" id="menu4" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{KycDetails.fetchCurrentRow4}">
                    <a4j:actionparam name="custLoans" value="{loanType}" />
                    <a4j:actionparam name="row" value="{currentRowLoanTable}" />
                </rich:menuItem>
            </rich:contextMenu>
            <rich:dataTable var="dataItem4" value="#{KycDetails.listLoanTable}" rowClasses="gridrow1, gridrow2" id="taskList4" columnsWidth="100"
                            rowKeyVar="row" rows="5" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                <f:facet name="header">
                    <rich:columnGroup>
                        <rich:column colspan="4"></rich:column>
                        <rich:column breakBefore="true"><h:outputText value="S.No." /></rich:column>
                        <rich:column><h:outputText value="Loan Type" /></rich:column>
                        <rich:column><h:outputText value="Value"/></rich:column>
                        <rich:column><h:outputText value="Select Record" /> </rich:column>
                    </rich:columnGroup>
                </f:facet>
                <rich:column style="width:100px;"><h:outputText value="#{dataItem4.sNo2}" /></rich:column>
                <rich:column style="width:100px;"><h:outputText value="#{dataItem4.loanType}" /></rich:column>
                <rich:column style="text-align:center;"><h:outputText value="#{dataItem4.loanValue}" /></rich:column>
                <rich:column style="text-align:center;width:40px">
                    <a4j:commandLink  ajaxSingle="true" id="selectlink4" oncomplete="#{rich:component('showPanel4')}.show();"
                                      >
                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                        <f:setPropertyActionListener value="#{dataItem4}" target="#{KycDetails.currentItemLoanTable}"/>
                        <f:setPropertyActionListener value="#{row}" target="#{KycDetails.currentRowLoanTable}"/>
                    </a4j:commandLink>
                    <%--<rich:toolTip for="selectlink4" value="Select This Row"/>--%>
                </rich:column>
            </rich:dataTable>
            <rich:datascroller id="loanScroller"align="left" for="taskList4" maxPages="20" />
            <rich:modalPanel id="showPanel4" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Do you want to Select or Delete this Row." style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink4" />
                        <rich:componentControl for="showPanel4" attachTo="hidelink4" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="SELECT"  ajaxSingle="true"action="#{KycDetails.selectLoans}" reRender="taskList4,ddExistingLoans,txtLoanAmount,btnAdd2"onclick="#{rich:component('showPanel4')}.hide();" focus="ddExistingLoans"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="DELETE"  ajaxSingle="true"action="#{KycDetails.deleteGridLoans}" reRender="taskList4"onclick="#{rich:component('showPanel4')}.hide();#{rich:element('ddExistingLoans')}.focus();" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </h:panelGrid>
    </rich:panel>
    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1,col1" columns="7" id="kycGrid14" style="width:100%;text-align:left;" styleClass="row2">
        <h:outputLabel id="lblMedicalInsurance" styleClass="label" value="Do you have any Medical Insurance?"/>
        <h:selectOneListbox id="ddMedicalInsurance" styleClass="ddlist" size="1" value="#{KycDetails.medicalInsurance}">
            <f:selectItems value="#{KycDetails.medicalInsuranceList}"/>
        </h:selectOneListbox>
        <h:outputText/>
        <h:outputText/>
        <h:outputText/>
        <h:outputText/>
        <h:outputText/>
    </h:panelGrid>
           </a4j:region>
</h:form>
