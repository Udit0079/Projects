<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form>
    <h:panelGrid id="jointDetailPopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="mainPanel" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
            <rich:panel header="Account Detail">
                <h:panelGrid columns="4" columnClasses="col1,col13,col1,col5" id="jtRow10" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblPrimaryAccount" styleClass="label"  value="Primary A/c:" />
                    <h:outputText id="stxtPrimaryAccount" styleClass="output" value="#{jointHolderBean.primaryAccount}"/>
                    <h:outputLabel id="lblPrimaryName" styleClass="label"  value="Primary A/c Name:" />
                    <h:outputText id="stxtPrimaryName" styleClass="output" value="#{jointHolderBean.primaryName}" />
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Ist Joint Holder Detail">
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow1" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblJtHolder1" styleClass="label"  value="Name: " />
                    <h:outputText id="stxtJtHolder1" styleClass="output" value="#{jointHolderBean.jtName1}"/>
                    <h:outputLabel id="lblDOB1" styleClass="label"  value="DOB:" />
                    <h:outputText id="stxtDOB1" styleClass="output" value="#{jointHolderBean.dob1}"/>
                    <h:outputLabel id="lblPANNo1" styleClass="label"  value="PAN No:" />
                    <h:outputText id="stxtPANNo1" styleClass="output" value="#{jointHolderBean.panNo1}"/>
                </h:panelGrid>
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow2" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblFatherName1" styleClass="label"  value="Father Name:" />
                    <h:outputText id="stxtFatherName1" styleClass="output" value="#{jointHolderBean.fatherName1}"/>
                    <h:outputLabel id="lblOccupation1" styleClass="label"  value="Occupation:" />
                    <h:outputText id="stxtOccupation1" styleClass="output" value="#{jointHolderBean.occupation1}"/>
                    <h:outputLabel id="lblAddress1" styleClass="label"  value="Address:" />
                    <h:outputText id="stxtAddress1" styleClass="output" value="#{jointHolderBean.address1}"/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="2nd Joint Holder Detail">
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow3" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblJtHolderName2" styleClass="label"  value="Name:" />
                    <h:outputText id="stxtJtHolderName2" styleClass="output" value="#{jointHolderBean.jtName2}"/>
                    <h:outputLabel id="lblDOB2" styleClass="label"  value="DOB:" />
                    <h:outputText id="stxtDOB2" styleClass="output" value="#{jointHolderBean.dob2}"/>
                    <h:outputLabel id="lblPANNo2" styleClass="label"  value="PAN No:" />
                    <h:outputText id="stxtPANNo2" styleClass="output" value="#{jointHolderBean.panNo2}"/>
                </h:panelGrid>
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow4" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblFatherName2" styleClass="label"  value="Father Name:" />
                    <h:outputText id="stxtFatherName2" styleClass="output" value="#{jointHolderBean.fatherName2}"/>
                    <h:outputLabel id="lblOccupation2" styleClass="label"  value="Occupation:" />
                    <h:outputText id="stxtOccupation2" styleClass="output" value="#{jointHolderBean.occupation2}"/>
                    <h:outputLabel id="lblAddress2" styleClass="label"  value="Address:" />
                    <h:outputText id="stxtAddress2" styleClass="output" value="#{jointHolderBean.address2}"/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="3rd Joint Holder Detail">
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow5" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblJtHolder3" styleClass="label"  value="Name:" />
                    <h:outputText id="stxtJtHolder3" styleClass="output" value="#{jointHolderBean.jtName3}"/>
                    <h:outputLabel id="lblDOB3" styleClass="label"  value="DOB:" />
                    <h:outputText id="stxtDOB3" styleClass="output" value="#{jointHolderBean.dob3}"/>
                    <h:outputLabel id="lblPANNo3" styleClass="label"  value="PAN No:" />
                    <h:outputText id="stxtPANNo3" styleClass="output" value="#{jointHolderBean.panNo3}"/>
                </h:panelGrid>
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow6" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblFatherName3" styleClass="label"  value="Father Name:" />
                    <h:outputText id="stxtFatherName3" styleClass="output" value="#{jointHolderBean.fatherName3}"/>
                    <h:outputLabel id="lblOccupation3" styleClass="label"  value="Occupation:" />
                    <h:outputText id="stxtOccupation3" styleClass="output" value="#{jointHolderBean.occupation3}"/>
                    <h:outputLabel id="lblAddress3" styleClass="label"  value="Address:" />
                    <h:outputText id="stxtAddress3" styleClass="output" value="#{jointHolderBean.address3}"/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="4th Joint Holder Detail">
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow7" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblJtHolderName4" styleClass="label"  value="Name:" />
                    <h:outputText id="stxtJtHolderName4" styleClass="output" value="#{jointHolderBean.jtName4}"/>
                    <h:outputLabel id="lblDOB4" styleClass="label"  value="DOB:" />
                    <h:outputText id="stxtDOB4" styleClass="output" value="#{jointHolderBean.dob4}"/>
                    <h:outputLabel id="lblPANNo4" styleClass="label"  value="PAN No:" />
                    <h:outputText id="stxtPANNo4" styleClass="output" value="#{jointHolderBean.panNo4}"/>
                </h:panelGrid>
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow8" style="height:30px;"  styleClass="row1" width="100%"  >
                    <h:outputLabel id="lblFatherName4" styleClass="label"  value="Father Name:" />
                    <h:outputText id="stxtFatherName4" styleClass="output" value="#{jointHolderBean.fatherName4}"/>
                    <h:outputLabel id="lblOccupation4" styleClass="label"  value="Occupation:" />
                    <h:outputText id="stxtOccupation4" styleClass="output" value="#{jointHolderBean.occupation4}"/>
                    <h:outputLabel id="lblAddress4" styleClass="label"  value="Address:" />
                    <h:outputText id="stxtAddress4" styleClass="output" value="#{jointHolderBean.address4}"/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Nominee Detail">
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow9" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblNomineeName" styleClass="label"  value="Nominee Name:" />
                    <h:outputText id="stxtNomineeName" styleClass="output" value="#{jointHolderBean.nomineeName}"/>
                    <h:outputLabel id="lblAddress" styleClass="label"  value="Nominee Address:" />
                    <h:outputText id="stxtAddress" styleClass="output" value="#{jointHolderBean.nomineeAddress}" />
                    <h:outputLabel id="lblRelation" styleClass="label"  value="Nominee Relation:" />
                    <h:outputText id="stxtRelation" styleClass="output" value="#{jointHolderBean.nomineeRelation}"/>
                </h:panelGrid>
            </rich:panel>
               <rich:panel header="Proprietor Detail">
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow11" style="height:30px;"  styleClass="row2" width="100%"  >
                    <h:outputLabel id="lblProprietorName1" styleClass="label"  value="Name:" />
                    <h:outputText id="ProprietorName1" styleClass="output" value="#{jointHolderBean.proprietorName1}"/>
                    <h:outputLabel id="lblProprietorName2" styleClass="label"  value="Name:" />
                    <h:outputText id="ProprietorName2" styleClass="output" value="#{jointHolderBean.proprietorName2}" />
                    <h:outputLabel id="lblProprietorName3" styleClass="label"  value="Name:" />
                    <h:outputText id="ProprietorName3" styleClass="output" value="#{jointHolderBean.proprietorName3}"/>
                </h:panelGrid>
                <h:panelGrid columns="6" columnClasses="col1,col2,col1,col1,col1,col2" id="jtRow12" style="height:30px;"  styleClass="row1" width="100%">
                    <h:outputLabel id="lblProprietorName4" styleClass="label"  value="Name:" />
                    <h:outputText id="ProprietorName4" styleClass="output" value="#{jointHolderBean.proprietorName4}"/>  
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>   
            </rich:panel>
            <h:panelGrid columns="4"  id="jtRow1Msg" style="width:100%;text-align:center;color:red"  styleClass="row2" width="100%"  >
                <h:outputLabel id="lblJtMsg" styleClass="label"  value="#{jointHolderBean.jtMsg}" />
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>

