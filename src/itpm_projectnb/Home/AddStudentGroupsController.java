/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itpm_projectnb.Home;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adeesha
 */
public class AddStudentGroupsController implements Initializable {

 
    @FXML
    private ComboBox AcYrComb;    
    ObservableList<String> list1 = FXCollections.observableArrayList("Y1.S1", "Y1.S2", "Y2.S1","Y2.S2","Y3.S1","Y3.S2","Y4.S1","Y4.S2");
   
    @FXML
    private ComboBox programComb;
    ObservableList<String> list2 = FXCollections.observableArrayList("IT", "SE","CS","DS","ISE","IM","CSNE");
    
    @FXML
    private ComboBox groupNoComb;
    ObservableList<String> list3 = FXCollections.observableArrayList("1", "2","3","4","5","6","7","8", "9", "10");
    
    @FXML
    private ComboBox subgroupNoComb;
    ObservableList<String> list4 = FXCollections.observableArrayList("1", "2","3","4","5","6","7","8", "9", "10");

    @FXML
    private TextField grpID;
    
    
    @FXML
    private TextField subgrpID;

    @FXML
    private Button generateIDBtn;
    
    @FXML
    private Button saveBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private FontAwesomeIconView switchIcon;
    @FXML
    private FontAwesomeIconView exitIcon;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            AcYrComb.setItems(list1);
            programComb.setItems(list2);
            groupNoComb.setItems(list3);
            subgroupNoComb.setItems(list4);     
    }  
    
                   AlertBox ab = new AlertBox();   
        Connection con = DbConnect.connectDB();
        PreparedStatement preState = null ;
        ResultSet rs = null;
               
                   
    @FXML
    public void generateID(javafx.event.ActionEvent actionEvent) throws IOException {
     if (actionEvent.getSource() == generateIDBtn)
       {
         
         if ( AcYrComb.getSelectionModel().isEmpty() ||  programComb.getSelectionModel().isEmpty() || groupNoComb.getSelectionModel().isEmpty() || subgroupNoComb.getSelectionModel().isEmpty() )
           {
                   ab.displayError("Error!", "Please fill all the relevant fields to generate IDs");
           }
             else
                            generateIDs();

       }
    }
     
    @FXML
    public void saveGroup(javafx.event.ActionEvent actionEvent) throws IOException {
        
           if (actionEvent.getSource() == saveBtn)
            {
                  insertRecord();                                   
            }
       }

     String YearAndSem;
     String program;
     String groupNo;
     String subgroupNo;
     String groupID;
     String subgroupID;
      
   
            
    public void generateIDs()
       {
            YearAndSem = AcYrComb.getValue().toString();
             program = programComb.getValue().toString();
             groupNo = groupNoComb.getValue().toString();
             subgroupNo = subgroupNoComb.getValue().toString();
             groupID = YearAndSem+".0"+groupNo;
             subgroupID = groupID+"."+subgroupNo;
             
            grpID.setText(groupID);
            subgrpID.setText(subgroupID);        
       }   
    
      public boolean isEmpty()
     {              

         if ( AcYrComb.getSelectionModel().isEmpty() ||  programComb.getSelectionModel().isEmpty() || groupNoComb.getSelectionModel().isEmpty() || subgroupNoComb.getSelectionModel().isEmpty() ||  grpID.getText().isEmpty() || subgrpID.getText().isEmpty())
         { 
                              return true;
         }
         else
        return false;
     }
      
        public boolean isSubGroupAlreadyAdded()
    { 
          String subGroupID = subgrpID.getText();
      
         try {
           String query = "SELECT COUNT(*) AS recordCount FROM studentgroups WHERE SubGroupID = '"+ subGroupID +"' ";

            preState= con.prepareStatement(query);
            rs = preState.executeQuery();            
            
                if(rs.next())
                {
                    if ( rs.getInt(1)==0)                
                        return false;
                } 
         }
         catch (SQLException ex) {
            Logger.getLogger(AddStudentGroupsController.class.getName()).log(Level.SEVERE, null, ex);
            }
                 return true;     
    }
/*
 if ( programComb.getSelectionModel().isEmpty())
         {
             if ( groupNoComb.getSelectionModel().isEmpty())
         {
             if ( subgroupNoComb.getSelectionModel().isEmpty())
         {
             if ( grpID.getText().isEmpty())
         {
             if (  subgrpID.getText().isEmpty())
             {
        */
     
    
    public void insertRecord()
    {  
               if (!isEmpty())
               {
               if ( !isSubGroupAlreadyAdded())
               {
        try {
            String query = "INSERT INTO studentgroups (YearAndSem, Programme, GroupNo, SubGroupNo, GroupID, SubGroupID) values ('"+ YearAndSem+"' , '"+ program+"' , '"+ groupNo+"' , '"+ subgroupNo+"', '"+ groupID+"', '"+ subgroupID+"' )";
            preState= con.prepareStatement(query);
            preState.execute();
            
               ab.displayInfo("Success", "A new group has been added.");
            
        } catch (SQLException ex) {
            Logger.getLogger(AddStudentGroupsController.class.getName()).log(Level.SEVERE, null, ex);
        }     
               }
                    else               
                        ab.displayError("Error!", "Sub Group is already added!");
               }
               else               
                    ab.displayError("Error!", "Not any value can be empty. Please fill");
    }

    
    @FXML
    private void switchScene(MouseEvent event) throws IOException {
       Parent pane = FXMLLoader.load(getClass().getResource("manageStudentGroups.fxml"));
       Scene scene = new Scene(pane);
         //     rootPane.getChildren().setAll(pane);

       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(scene);
       window.centerOnScreen();
      // rootPane.getChildren().setAll(pane);
     // AlertBox av = new AlertBox();
     // av.loadStage("manageStudentGroups.fxml");
    }

    @FXML
    private void exitScene(MouseEvent event) throws IOException {
       Parent pane = FXMLLoader.load(getClass().getResource("home.fxml"));
   

        Scene scene = new Scene(pane);
         //     rootPane.getChildren().setAll(pane);

       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(scene);
       window.centerOnScreen();
}
       
      // rootPane.setVisible(false);
                  //   pane.setVisible(true);

     //rootPane.getChildren().setAll(pane);
       //bp.setCenter(pane);
    @FXML
        public void setToolTip()
        {
            Tooltip.install(switchIcon, new Tooltip("Switch to Manage Groups"));
        }

    @FXML
    private void clearData(ActionEvent event) {
        if (event.getSource() == clearBtn)
            {
                  AcYrComb.setValue(null);
                  programComb.setValue(null);
                  groupNoComb.setValue(null);
                  subgroupNoComb.setValue(null);
                  grpID.setText(null);
                  subgrpID.setText(null);
            }
    }
            
}
