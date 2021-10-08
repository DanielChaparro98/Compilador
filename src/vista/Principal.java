package vista;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class Principal extends JPanel {

	/**
	 * Create the panel.
	 */
	public Principal() {
		setLayout(null);
		
		JButton btnAnalisisLexico = new JButton("Analisis Lexico");
		btnAnalisisLexico.setBounds(27, 32, 141, 23);
		add(btnAnalisisLexico);
		
		JButton btnAnalisisSintactico = new JButton("Analisis Sintactico");
		btnAnalisisSintactico.setBounds(198, 32, 141, 23);
		add(btnAnalisisSintactico);
		
		JButton btnAnalisisSemantico = new JButton("Analisis Semantico");
		btnAnalisisSemantico.setBounds(382, 32, 155, 23);
		add(btnAnalisisSemantico);
		
		JLabel lblIngreseUnTextocodigo = new JLabel("Ingrese un texto(Codigo)");
		lblIngreseUnTextocodigo.setBounds(27, 88, 130, 14);
		add(lblIngreseUnTextocodigo);
		
		JTextPane textPaneInput = new JTextPane();
		textPaneInput.setBounds(54, 113, 471, 124);
		add(textPaneInput);
		
		JLabel lblSalida = new JLabel("Salida");
		lblSalida.setBounds(27, 248, 46, 14);
		add(lblSalida);
		
		JTextPane textPaneOutput = new JTextPane();
		textPaneOutput.setBounds(54, 273, 471, 141);
		add(textPaneOutput);

	}
}
