package vista;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Principal extends JPanel {

	/**
	 * Create the panel.
	 */
	
	 int estado = 0;
	    // Posición actual con respecto al editor.
	     int posicion = 0;
	    // Representa el texto fuente del análisis.
	     String fuente = "";
	    // Se analizará carácter por carácter el String fuente. 'caracter'
	    // representará el simbolo.
	    char caracter;
	    // Es el carácter o conjunto de caracteres que representan una 
	    // acción, atributo, etc. en el lenguaje.
	     String lexema = "";
	    // Lista completa de lexemas encontradas en el String fuente.
	     ArrayList<String> listaLexema = new ArrayList();
	    // Indica que tipo de lexema se encuentra en la lista lexema.
	    // los atributos estan ordenados: 
	    // listaLexema(i) es de tipo listaToken(i)
	     ArrayList<String> listaToken = new ArrayList();
	     
	     JTextPane textPaneOutput=null;
	public Principal() {
		
	   
	
	     
	    
		setLayout(null);
		
		JTextPane textPaneInput = new JTextPane();
		textPaneInput.setBounds(54, 113, 471, 124);
		add(textPaneInput);
		
		
		textPaneOutput = new JTextPane();
		textPaneOutput.setBounds(54, 273, 471, 141);
		add(textPaneOutput);
		
		
		JButton btnAnalisisLexico = new JButton("Analisis Lexico");
		btnAnalisisLexico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 estado = 0;
			        posicion = 0;
			        lexema = "";
			        listaLexema.clear();
			        listaToken.clear();
			        fuente = textPaneInput.getText();
			        fuente = fuente.trim();
			        if(fuente.length() == 0){
			        	textPaneOutput.setText("El cuadro de entrada no contiene\ncaracteres a"
			                + " evaluar. ");
			    	}
			        else{
			            iniciarProceso();
			            imprimirLista();
			        }
			}
		});
		btnAnalisisLexico.setBounds(27, 32, 141, 23);
		add(btnAnalisisLexico);
		
		JButton btnAnalisisSintactico = new JButton("Analisis Sintactico");
		btnAnalisisSintactico.setBounds(198, 32, 141, 23);
		
		add(btnAnalisisSintactico);
		
		JButton btnAnalisisSemantico = new JButton("Analisis Semantico");
		btnAnalisisSemantico.setBounds(382, 32, 155, 23);
		add(btnAnalisisSemantico);
		
		JLabel lblIngreseUnTextocodigo = new JLabel("Ingrese un texto(Codigo)");
		lblIngreseUnTextocodigo.setBounds(27, 88, 230, 14);
		add(lblIngreseUnTextocodigo);
		
		
		
		JLabel lblSalida = new JLabel("Salida");
		lblSalida.setBounds(27, 248, 46, 14);
		add(lblSalida);
		
		
		
		JButton btnAbrirArchivo = new JButton("Abrir Archivo");
		btnAbrirArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cadena ="";
				String text="";
				JFileChooser chooser = new JFileChooser();
//			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
//			        "JPG & GIF Images", "jpg", "gif","docs");
//			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(textPaneInput);
			    try {
					FileReader file= new FileReader(chooser.getSelectedFile());
					BufferedReader buffered= new BufferedReader(file);
					 if(returnVal == JFileChooser.APPROVE_OPTION) {
					    	try {
								while( (cadena=buffered.readLine())!=null) {
									text+=cadena;
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					    		textPaneInput.setText(text);
					           
					    }
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
			}
		});
		btnAbrirArchivo.setBounds(382, 66, 155, 23);
		add(btnAbrirArchivo);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnSalir.setBounds(475, 439, 89, 23);
		add(btnSalir);
		
		//if(textPaneInput.getText().equals(" "))
		//btnAnalisisLexico.setEnabled(false);

		
		
	}
	
	private void iniciarProceso(){
        caracter = fuente.charAt(posicion);
        switch(estado){
            case 0:{
            	/**
            	* En este estado el lexema actual esta vacío. Si se obtiene 
            	* como carácter actual ';', '+' o '=' no cambia de estado debido
            	* a que estos simbolos son de aceptación, entonces, se agregan a la 
            	* lista de lexemas, y se vuelve a iniciar el proceso. Si obtiene
            	* un caracter de tipo vacío no realiza ningún cambio de estados,
            	* solo reinicia el proceso. para los dígitos o letras.
            	* Se realiza el cambio de estado que corresponde con el automata.
            	*/
                if(caracter ==';'){
                    lexema += Character.toString(caracter);
                    addList(lexema,"punto y coma");
                    lexema = "";
                }
                else if(caracter == '+'){
                    lexema += Character.toString(caracter);
                    addList(lexema,"mas");
                    lexema = "";
                }
                else if(caracter == '='){
                    lexema += Character.toString(caracter);
                    addList(lexema,"igual");
                    lexema = "";
                }
                else if(Character.isDigit(caracter)){
                    estado = 5;
                    lexema += Character.toString(caracter);
                }
                else if(Character.isLetter(caracter)){
                    estado = 1;
                    lexema += Character.toString(caracter);
                }
                else if(esEspacio(caracter)){}
                else{error();}
                break;
            }
            case 1:{
            	/**
		* Estado 1, inicia cuando se encuentra una letra.
            	*/
            	String variable="''";
                if(caracter == ';'){
                    addList(lexema,"identificador");
                    addList(";","punto y coma");
                    estado = 0;
                    lexema = "";
                }
                else if(caracter == '='){
                    addList(lexema,"identificador");
                    addList("=","igual");
                    estado = 0;
                    lexema = "";
                }
                else if(caracter == '+'){
                    addList(lexema,"identificador");
                    addList("+","mas");
                    estado = 0;
                    lexema = "";
                }
                else if(caracter == '"'){
                    addList(lexema,"identificador");
                    addList("","String");
                    estado = 0;
                    lexema = "";
                }
                else if(esEspacio(caracter)){
                    addList(lexema,"identificador");
                    estado = 0;
                    lexema = "";
                }
                else if(Character.isDigit(caracter)||Character.isLetter(caracter))
                {
                    lexema += Character.toString(caracter);
                }
                else{error();}
//                JOptionPane.showConfirmDialog(null, "estado:" + estado + " caracter:" + caracter + " lexema:" 
//                    	+ lexema + " posicion:" + posicion);
                break;
            }
            case 5:{
            	/**
            	* Estado 5, inicia cuando se encuentra un digito.
            	*/
                if(caracter == ';'){
                    addList(lexema,"numero");
                    addList(";","punto y coma");
                    lexema = "";
                    estado = 0;
                }
                else if(caracter == '='){
                    addList(lexema,"numero");
                    addList("=","igual");
                    lexema = "";
                    estado = 0;
                }
                else if(caracter == '+'){
                    addList(lexema,"numero");
                    addList("+","mas");
                    lexema = "";
                    estado = 0;
                }
                else if(esEspacio(caracter)){
                    addList(lexema,"numero");
                    lexema = "";
                    estado = 0;
                }
                else if(Character.isDigit(caracter))
                { 
                    lexema += Character.toString(caracter);
                }
                else {
                    error();
                }
                break;
            }
            default:
                break;
        }
        /**
	* Al finalizar el análisis con el carácter actual se toma la
	* posición siguiente y se repite el análisis hasta llegar al punto final 
	* del String fuente.
        */
        posicion++;
        //JOptionPane.showConfirmDialog(null, "");
        if (posicion >= fuente.length()){
            if(estado == 1){
                addList(lexema,"identificador");
            }
            else if(estado == 5){
                addList(lexema,"numero");
            }
        }
        else{
            iniciarProceso();
        }
    }
    /**
     * Llamado cuando se encuentra un error en la entrada. Se llama el mismo
     * hasta que encuentra un caracter limitador.
     * Los caracteres limitadores son: + = ; o espacio.
     */
    private void error(){
        lexema += Character.toString(caracter);
        posicion++;
        if(posicion >= fuente.length()){
            estado = 0;
            addList(lexema,"error");
        }else{
            caracter = fuente.charAt(posicion);
            if(caracter == '='){
                addList(lexema,"error");
                addList("=","igual");
                estado = 0;
                lexema = "";
            }
            else if(caracter == '+'){
                addList(lexema,"error");
                addList("+","suma");
                estado = 0;
                lexema = "";
            }
            else if(caracter == ';'){
                addList(lexema,"error");
                addList(";","punto y coma");
                estado = 0;
                lexema = "";
            }
            else if(esEspacio(caracter)){
                addList(lexema,"error");
                estado = 0;
                lexema = "";
            }
            else{error();}
        }
    }
    /**
     * 
     * @param c Agregarle char c es una total tontería porque siempre se analizará 
     * caracter( pero bueno =D).
     * @return true si el caracter actual es espacio, tabulador o cambio de linea.
     * También se pueden utilizar métodos de la clase Character para verificar si el 
     * carácter actual pertecence a los tipos buscados( tabulación, espacio o salto
     * de línea.)
     */
    private boolean esEspacio(char c){
        return c == '\n' || c== '\t' || c == ' ';
    }
    /**
    * destino: representa el textArea en el que se mostrarán los resultados obtenidos.
    * Imprime en el textArea 'destino' la lista de tokens y sus lexemas.
    */
    private void imprimirLista(){
        String auxiliar = "Token    -------   Lexema\n";
        for(int i = 0; i < listaLexema.size(); i++){
            auxiliar += listaToken.get(i) + "  -------  " + listaLexema.get(i) + "\n";
        }
        textPaneOutput.setText(auxiliar);
    }
    
    private void addList(String lex, String token){
        listaLexema.add(lex);
        listaToken.add(token);
    }
}
