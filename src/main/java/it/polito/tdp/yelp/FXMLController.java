/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.User;
import it.polito.tdp.yelp.model.UtenteSim;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	int n = 0;
    	
    	this.txtResult.clear();

    	if(this.cmbAnno.getValue() == null) {
    		this.txtResult.setText("Seleziona un valore dalla tendina.");
    	}

    	if(this.txtN.getText().equals("")) {
    		this.txtResult.setText("Inserire un valore per n.");
    		return;
    	}

    	try {
    		n = Integer.parseInt(this.txtN.getText());
    		this.model.creaGrafo(n, this.cmbAnno.getValue());

    		this.txtResult.setText("Grafo creato.\n");
    		this.txtResult.appendText("# vertici: " + this.model.getNumVertici() + "\n");
    		this.txtResult.appendText("# archi:   " + this.model.getNumArchi() + "\n");

    		this.cmbUtente.getItems().addAll(this.model.getVertici(n));



    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un valore numerico per n.");
    		return;
    	}
    	
    	

    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	
    	this.txtResult.clear();

    	if(this.cmbUtente.getValue() == null) {
    		this.txtResult.setText("Seleziona un valore dalla tendina.");
    	} else {
    		List<UtenteSim> simili =this.model.utentiSim(this.cmbUtente.getValue());
    		
    		this.txtResult.setText("Utenti simili a " + this.cmbUtente.getValue().toString() + ":\n");
    		
    		for(UtenteSim u: simili) {
    			this.txtResult.appendText(u.toString() + "\n");
    		}
    	}

    }
    
    @FXML
    void doSimula(ActionEvent event) {

    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	for(int i=2005; i<2014; i++) {
    		this.cmbAnno.getItems().add(i);
    	}
    	
    }
}
