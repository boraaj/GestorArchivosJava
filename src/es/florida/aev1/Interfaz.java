package es.florida.aev1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.BadLocationException;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Interfaz extends JFrame {

	private JPanel contentPane;
	private JTextField txtRuta;
	private JLabel lblInfoFile;
	private JLabel lblFile;
	private JButton btnArchivo;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JTextField txtBuscar;
	private JLabel lblBuscar;
	private JLabel lblReemplazar;
	private JTextField txtReemplazar;
	private JButton btnBuscar;
	private JButton btnReemplazar;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;
	private JButton btnGuardarCanvis;
	private JButton btnGuardarNom;
	JFrame mainFrame;
	DefaultListModel<String> listaDefault;
	private JTextArea txtInfoFile;
	private JTextArea txtContenidoFile;
	private JScrollPane spContenidoFile;
	private JScrollPane spInfoFile;
	private JLabel lblContador;
	
	
	/**
	 * Neteja els camps de text i deshabilita els botons de buscar i reemplaçar cada vegada que es vol obrir un fitxer. 
	 */
	public void ocultarBotones() {
		
		txtContenidoFile.setEditable(false);
		txtContenidoFile.setText("");
		txtReemplazar.setEnabled(false);
		btnReemplazar.setEnabled(false);
		btnBuscar.setEnabled(false);
		txtBuscar.setEnabled(false);
		btnGuardarCanvis.setEnabled(false);
		lblContador.setText("");
		btnEditar.setEnabled(true);
		txtInfoFile.setText("");
		
	}
	
	/**
	 * Programa principal. Llança la aplicación. 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null); //Centrar el Jframe
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz() {
		
		setResizable(false);
		setTitle("RoBoDoc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 890, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtRuta = new JTextField();
		txtRuta.setBounds(193, 28, 477, 35);
		txtRuta.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtRuta.setEnabled(false);
		contentPane.add(txtRuta);
		txtRuta.setColumns(10);
		
		
		/**
		 * Botón obrir carpeta. 
		 */
		JButton btnSeleccionar = new JButton("Obrir Carpeta");
		btnSeleccionar.setBounds(680, 28, 131, 35);
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ocultarBotones();
				//File Chooser que abre el menú contextual para abrir carpetas y ficheros. 
				JFileChooser seleccion = new JFileChooser();
				seleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = seleccion.showOpenDialog(seleccion);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//Cuando se selecciona un elemento y se pulsa en aceptar toma la ruta del archivo del cuadro de texto superior. 
					txtRuta.setText(seleccion.getSelectedFile().getAbsolutePath());
					//Crea un File del archivo con la ruta del elemento seleccionado en el JFIleChooser. 
					File elements = new File(seleccion.getSelectedFile().getAbsolutePath());
					String[] listaDirectorio = elements.list(); //LIsta de strings que tendrá los elementos de la carpeta. 
					String dirTxt = "";
					//Introducimos los nombres de los directorios en el array de Strings. 
					for (int i = 0; i < listaDirectorio.length; i++) {
						dirTxt += listaDirectorio[i] + "\n";
					}
					lblInfoFile.setText("Elements del directori");
					//Imprimimos el string con los elementos en la cadena de texto. 
					txtInfoFile.setText(dirTxt);

				}
			}
		});
		

		contentPane.add(btnSeleccionar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(52, 121, 236, 207);
		contentPane.add(scrollPane);

		spInfoFile = new JScrollPane();
		scrollPane.setRowHeaderView(spInfoFile);
		
				txtInfoFile = new JTextArea();
				scrollPane.setViewportView(txtInfoFile);
				txtInfoFile.setEditable(false);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(422, 121, 389, 207);
		contentPane.add(scrollPane_2);

		spContenidoFile = new JScrollPane();
		scrollPane_2.setRowHeaderView(spContenidoFile);
		
				txtContenidoFile = new JTextArea();
				scrollPane_2.setViewportView(txtContenidoFile);
				txtContenidoFile.setEditable(false);

		lblInfoFile = new JLabel("");
		lblInfoFile.setBounds(52, 88, 236, 22);
		contentPane.add(lblInfoFile);

		lblFile = new JLabel("Contingut del fitxer");
		lblFile.setBounds(423, 88, 123, 22);
		contentPane.add(lblFile);
		
		/**
		 * Crear nuevo documento. 
		 */

		JButton btnCrear = new JButton("Nou");
		btnCrear.setBounds(181, 393, 107, 33);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JFileChooser seleccion = new JFileChooser();
				seleccion.setDialogTitle("Crea un document nou");
				seleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = seleccion.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File archivo = new File(seleccion.getSelectedFile().getAbsolutePath());
					try {
						//Creamos el nuevo elemento "Clonando" el anterior. El nombre lo introducimos en el JFile Chooser
						archivo.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		contentPane.add(btnCrear);
		
		/**
		 * Obrir document. Rep el document amb la ruta del cuadre de text superior. Llig el contingut, el guarda en altre document per 
		 * a mostrar-ho i arreplega les propietats del document per a mostrar-ho també. 
		 * 
		 */

		JButton btnArchivo = new JButton("Obrir Document");
		btnArchivo.setBounds(52, 29, 131, 33);
		btnArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ocultarBotones(); //Funcione
				JFileChooser seleccion = new JFileChooser();
				txtContenidoFile.setText("");
				txtInfoFile.setText("");
				seleccion.setDialogTitle("Selecciona un document");
				seleccion.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = seleccion.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {

					txtRuta.setText(seleccion.getSelectedFile().getAbsolutePath());

					File arx = new File(seleccion.getSelectedFile().getAbsolutePath());
					FileReader fr;
					try {
						fr = new FileReader(arx);
						BufferedReader leerFile = new BufferedReader(fr);
						String line = leerFile.readLine();
						while (line != null) {
							txtContenidoFile.append(line + "\n");
							line = leerFile.readLine();
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String fileTxt = "Nom: " + arx.getName() + "\n" + "Ruta: " + arx.getPath() + "\n"
							+ "Ruta absoluta: " + arx.getAbsolutePath() + "\n" + "Escritura: " + arx.canWrite() + "\n"
							+ "Lectura: " + arx.canRead() + "\n" + "Tamany: " + arx.length() + " Bytes";
					
					lblInfoFile.setText("Informació del fitxer");
					txtInfoFile.setText(fileTxt);

					;
				}
			}
		});
		contentPane.add(btnArchivo);
		
		/**
		 * Copiar. Rep el document del cuadre de ruta superior,llig el contingut amb FR,BR,FW,BW y el volca en un nou document que s'anomena igual pero amb
		 * _copia.txt al final. 
		 */

		JButton btnCopiar = new JButton("Copiar");
		btnCopiar.setBounds(52, 393, 110, 33);
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser seleccion = new JFileChooser();
				seleccion.setDialogTitle("Copiar Document");
				seleccion.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = seleccion.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File archivo = new File(seleccion.getSelectedFile().getAbsolutePath());
					try {
						FileReader fr = new FileReader(archivo);
						FileWriter fw = new FileWriter(archivo.getAbsolutePath().split(".txt")[0] + "_copia.txt"); //Partix el nom del document en 2: la rutafins al .txt, el sustrau i afegeix _copia.txt
						BufferedReader br = new BufferedReader(fr);
						BufferedWriter bw = new BufferedWriter(fw);
						String linea = br.readLine();
						while (linea != null) {
							bw.write(linea);
							bw.newLine();
							linea = br.readLine();
						}
						//tancar les clases de lectura i escritura. 
						bw.close();
						br.close();
						br.close();
						fr.close();

					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}

			}
		});
		contentPane.add(btnCopiar);

		/**
		 * Eliminar. Amb un Jchooser que solament mostre arxius seleccionem arxiu i mostra un miossatge per confirmar. 
		 */
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(178, 349, 110, 33);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser seleccion = new JFileChooser();
				seleccion.setDialogTitle("Borrar Document");
				seleccion.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = seleccion.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					int confirmacion = JOptionPane.showConfirmDialog(null, "Segur que vols eliminar el fitxer?",
							"Confirmacio per a eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);

					if (confirmacion == 0) {
						File archivo = new File(seleccion.getSelectedFile().getAbsolutePath()); //creacio de un File per a arreplegar el File seleccionat en "seleccion".
						archivo.delete(); //borra el arxiu. 
					}

				}

			}
		});
		contentPane.add(btnEliminar);
		
		/**
		 * Boto que s'habilita solament cuan seleccionem un arxiu (txtContenidoFile is distint a vuit); Aques boto habilita buscar, editar, reemplaçar i 
		 * guarda canvis. 
		 */
		btnEditar = new JButton("Editar document");
		btnEditar.setEnabled(false);
		btnEditar.setBounds(422, 349, 130, 33);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBuscar.setEnabled(true);
				txtContenidoFile.setEditable(true);
				txtReemplazar.setEnabled(true);
				btnReemplazar.setEnabled(true);
				btnBuscar.setEnabled(true);
				btnGuardarCanvis.setEnabled(true);
			}
		});
		contentPane.add(btnEditar);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(422, 418, 179, 20);
		txtBuscar.setEnabled(false);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		lblBuscar = new JLabel("Buscar");
		lblBuscar.setBounds(494, 393, 41, 14);
		contentPane.add(lblBuscar);

		lblReemplazar = new JLabel("Reempla\u00E7ar");
		lblReemplazar.setBounds(484, 447, 79, 14);
		contentPane.add(lblReemplazar);

		txtReemplazar = new JTextField();
		txtReemplazar.setBounds(422, 472, 179, 20);
		txtReemplazar.setEnabled(false);
		txtReemplazar.setColumns(10);
		contentPane.add(txtReemplazar);
		
		
		/**
		 * Buscar coincidencies. 
		 * Carguem el contingut del text a buscar. "txtBuscar.getText()" a pattern, de la classe Pattern. Aquesta clase que registra patrons per 
		 * su posterior comparativa. comparem amb matcher el contingut del patró pattern amb el contingut del arxiu (txtCOntenidoFile.getText()) 
		 * si pattern i matcher coinxideixen subrayem cada coincidencia i sumem 1 al contador de coincidencies. 
		 */

		btnBuscar = new JButton("Buscar");
		btnBuscar.setEnabled(false);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Highlighter highlighter = txtContenidoFile.getHighlighter(); //Subrayador
				DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN); //Color de subrayador. 

				highlighter.removeAllHighlights(); //Borrem tots els subrayats per seguretat. 

				//l'Opcio solament s'activa si el contingut de txtBuscar no está viut. 
				if (txtBuscar.getText().isEmpty()) {
					JOptionPane.showConfirmDialog(null, "El cuadre de busqueda no pot estar vuit","Avis"
							,JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null);
				}else {
					Pattern pattern = Pattern.compile(Pattern.quote(txtBuscar.getText())); //patro de busqueda. 
					Matcher matcher = pattern.matcher(txtContenidoFile.getText()); // Contingut a comparar amb el patro. 
					int cont = 0; //contador de incidencies. 
					while (matcher.find()) { //si n'hi ha coincidencia. 
						try {
							highlighter.addHighlight(matcher.start(), matcher.end(), painter); //subrayem el match des de l'inici fins al final i afegim el color. 
							cont++; //sumem 1 al contador. 
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					lblContador.setText("Coincidencies trovades: "+cont);
					
				}
				
			}
		});
		btnBuscar.setBounds(629, 418, 131, 23);
		contentPane.add(btnBuscar);
		
		
		/**
		 * Opcion que reempaça el contingut del cuadre de buscar en el document per el de reemplaçar.  
		 */
		btnReemplazar = new JButton("Reempla\u00E7ar");
		btnReemplazar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtReemplazar.getText().isEmpty()) {
					JOptionPane.showConfirmDialog(null, "El cuadre de reemplaçar no pot estar vuit","Avis"
							,JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null);
				}else {
					String texto = txtContenidoFile.getText(); //capturem el contingut del fitxer. 
					String busqueda = txtBuscar.getText();//capturem la busqueda. 
					String cambia = txtReemplazar.getText();//capturem el text a reemplaçar  
					String nuevoText = texto.replace(busqueda, cambia); //Reemplaçem el text buscat amb el que volem reemplaçar. 
					txtContenidoFile.setText(nuevoText);
				}
			}
		});
		btnReemplazar.setBounds(629, 472, 131, 23);
		btnReemplazar.setEnabled(false);
		contentPane.add(btnReemplazar);
		
		
		/*
		 * Guardar cambis fets al document. PRegunta si vol reesriure o generar un nou document. 
		 */
		btnGuardarCanvis = new JButton("Guardar canvis");
		btnGuardarCanvis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] opciones = {"Sobreescriure","Nou"}; //array de objectes per poder personalitzer el missatge de avis. 
				Object defaultOpcion = opciones[0];
				
				int confirmacion = JOptionPane.showOptionDialog(null, "Vols sobreescriure el arxiu o crear uno nou?",
						"Confirmacio per a guardar canvis", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,opciones,defaultOpcion);

				if (confirmacion == 0) { //Si la opcio es la primera (Sobreescriure) Agafa la ruta, crea un File i li pasa el nou text al document que ja existeix. 
					String ruta = txtRuta.getText();
					File saveFile = new File(ruta);
					try {
						FileWriter fw = new FileWriter(saveFile);
						fw.write(txtContenidoFile.getText());
						fw.close();
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else { //si seleccionem l'altra opcion del menu missatge. 
					String mensajeJOption = JOptionPane.showInputDialog("Indica el nou nom del fitxer(sense extensio)");  

					File archivo = new File(txtRuta.getText());
					File nouName = new File(txtRuta.getText().split(archivo.getName())[0] + mensajeJOption + ".txt"); //Crea un nou arxiu en la mateixa ruta que el original amb el 
					try {																							//nou indicat  + .txt
						FileReader fr = new FileReader(archivo); //Llegim el document
						FileWriter fw = new FileWriter(nouName); //Carguem el text al nou document. 
						BufferedReader br = new BufferedReader(fr);
						BufferedWriter bw = new BufferedWriter(fw);
						String linea = br.readLine();
						while (linea != null) {
							bw.write(linea);
							bw.newLine();
							linea = br.readLine();
						}
						bw.close();
						br.close();
						br.close();
						fr.close();

					} catch (IOException e2) {
						e2.printStackTrace();
					}
					
				}
			}
		});
		btnGuardarCanvis.setBounds(637, 349, 123, 33);
		btnGuardarCanvis.setEnabled(false);
		contentPane.add(btnGuardarCanvis);
		
		/**
		 * Renombrar. Abans de terminar pregunta el nom del nou document. 
		 */
		JButton btnGuardarNom = new JButton("Renombrar");
		btnGuardarNom.setBounds(52, 349, 110, 33);
		btnGuardarNom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser seleccion = new JFileChooser();
				seleccion.setDialogTitle("Renombrar document");
				seleccion.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = seleccion.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String mensajeJOption = JOptionPane.showInputDialog("Indica el nou nom del fitxer");

					File archivo = new File(seleccion.getSelectedFile().getAbsolutePath());
					File nouName = new File(archivo.getAbsolutePath().split(archivo.getName())[0] + mensajeJOption + ".txt"); //El mateix que en guardar. 
					archivo.renameTo(nouName);
				}
			}
		});
		contentPane.add(btnGuardarNom);
		
		lblContador = new JLabel("");
		lblContador.setHorizontalAlignment(SwingConstants.LEFT);
		lblContador.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContador.setBounds(603, 88, 208, 22);
		contentPane.add(lblContador);
		
	}
}
