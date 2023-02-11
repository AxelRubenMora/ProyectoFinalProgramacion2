package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import doll.Contact;


public class AgendaContactos extends JFrame {

    /**
	 *
	 */
	public DefaultTableModel modeloTabla;
    private JTable tablaA;
    private JFileChooser ExploradorDeArchivos;
    ArrayList <Contact> contactos;
	
    public AgendaContactos() {
    	
    	this.contactos = new ArrayList<Contact>();
        setTitle("Agenda de contactos");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icono= new ImageIcon("src//GurrenIcono.png");// Guardamos la imagen como un icono
		//ponemos el iconoc en el this con este metodo
		setIconImage(icono.getImage());
        // Create table model
        String[] NombreDeColumnas = {"Nombre", "Telefono", "Correo"};
        modeloTabla = new DefaultTableModel(NombreDeColumnas, 0);

        // Create table
        tablaA = new JTable(modeloTabla);
        tablaA.setFillsViewportHeight(true);
        // Agregamos la tabla al scrollPane
        JScrollPane scrollPane = new JScrollPane(tablaA);

        // Creamos botones
        JButton agregarBtn = new JButton("add");
        JButton editarBtn = new JButton("Editar");
        JButton borrarBtn = new JButton("Borrar");
        JButton guardarBtn = new JButton("Guardar");
        JButton cargarBtn = new JButton("Cargar");
        JButton enviarBtn = new JButton("enviar");

        // Add buttons to button panel
        JPanel panelDeBotones = new JPanel();
        panelDeBotones.add(agregarBtn);
        panelDeBotones.add(editarBtn);
        panelDeBotones.add(borrarBtn);
        panelDeBotones.add(guardarBtn);
        panelDeBotones.add(cargarBtn);
        panelDeBotones.add(enviarBtn);
        // agregamos el scroll pane y el panel de botones al frame
        add(scrollPane, BorderLayout.CENTER);
        add(panelDeBotones, BorderLayout.SOUTH);

        // Creamos un explorador de archivos
        ExploradorDeArchivos = new JFileChooser();

        // Creamos las acciones de lo botones
        agregarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField escribriNombre = new JTextField();
                JTextField escribirTelefono = new JTextField();
                JTextField escribirCorreo = new JTextField();
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
                inputPanel.add(new JLabel("Nombre:"));
                inputPanel.add(escribriNombre);
                inputPanel.add(new JLabel("telefono:"));
                inputPanel.add(escribirTelefono);
                inputPanel.add(new JLabel("CorreoElectronico:"));
                inputPanel.add(escribirCorreo);
                int resultado = JOptionPane.showOptionDialog(null, inputPanel, "Agregar Contacto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (resultado == JOptionPane.OK_OPTION) {
                    String varNombre = escribriNombre.getText();
                    String varTelefono = escribirTelefono.getText();
                    String varCorreo = escribirCorreo.getText();
                    if (varNombre.trim().isEmpty() || varTelefono.trim().isEmpty() || varCorreo.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nombre, Telefono y Correo no pueden estar vacíos.");
                        return;
                    }
                    String posibleCorreo = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                    Pattern correoPatron = Pattern.compile(posibleCorreo);
                    Matcher coincide = correoPatron.matcher(escribirCorreo.getText());
                    if(!coincide.matches()){
                        JOptionPane.showMessageDialog(null, "Porfavor Digite un correo valido.");
                        return;
                    }
                    String posibleTelefono = "^[0-9]{8}$";
                    Pattern patronTelefono = Pattern.compile(posibleTelefono);
                    coincide = patronTelefono.matcher(escribirTelefono.getText());
                    if(!coincide.matches()){
                        JOptionPane.showMessageDialog(null, "Porfavor Digite un numero de telefono valido.");
                        return;
                    }
                    modeloTabla.addRow(new Object[] {varNombre, varTelefono, varCorreo});
                }
            }
        });

        
        editarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSelecionada = tablaA.getSelectedRow();
                if (filaSelecionada != -1) {
                    JTextField escribriNombre = new JTextField((String) modeloTabla.getValueAt(filaSelecionada, 0));
                    JTextField escribirTelefono = new JTextField((String) modeloTabla.getValueAt(filaSelecionada, 1));
                    JTextField escribirCorreo = new JTextField((String) modeloTabla.getValueAt(filaSelecionada, 2));
                    JPanel inputPanel = new JPanel();
                    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
                    inputPanel.add(new JLabel("Name:"));
                    inputPanel.add(escribriNombre);
                    inputPanel.add(new JLabel("Phone:"));
                    inputPanel.add(escribirTelefono);
                    inputPanel.add(new JLabel("Email:"));
                    inputPanel.add(escribirCorreo);
                    int resultado = JOptionPane.showOptionDialog(null, inputPanel, "Editar contacto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (resultado == JOptionPane.OK_OPTION) {
                    String nombre = escribriNombre.getText();
                    String telefono = escribirTelefono.getText();
                    String correo = escribirCorreo.getText();
                    if (nombre.trim().isEmpty() || telefono.trim().isEmpty() || correo.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nombre, telefono y email no puden se vacios.");
                    return;
                    }
                    modeloTabla.setValueAt(nombre, filaSelecionada, 0);
                    modeloTabla.setValueAt(telefono, filaSelecionada, 1);
                    modeloTabla.setValueAt(correo, filaSelecionada, 2);
                    }
                    } else {
                    JOptionPane.showMessageDialog(null, "Porfavor selecione un contacto a editar.");
                    }
                    }
                    });

        
        
        borrarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSelecionada = tablaA.getSelectedRow();
                if (filaSelecionada != -1) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este contacto?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        modeloTabla.removeRow(filaSelecionada);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "porfavor selecione un contatt a eliminar.");
                }
            }
        });

        guardarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int varianbleRetornada = ExploradorDeArchivos.showSaveDialog(AgendaContactos.this);
                if (varianbleRetornada == JFileChooser.APPROVE_OPTION) {
                    File file = ExploradorDeArchivos.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(file);
                        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                            for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                                writer.write((String) modeloTabla.getValueAt(i, j));
                                if (j < modeloTabla.getColumnCount() - 1) {
                                    writer.write(",");
                                }
                            }
                            writer.write("\n");
                        }
                        writer.close();
                        System.out.println();
                        JOptionPane.showMessageDialog(null, "Contactos guardados exitosamente.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar contactos.");
                    }
                }
            }
        });
        
        enviarBtn.addActionListener(new ActionListener() {
        	
			public void actionPerformed(ActionEvent e) {
				JPanel inputPanel= new JPanel();
				inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
				JButton wm=new JButton("Whatssap");
				inputPanel.add(wm);
				JButton em=new JButton("Email");
				inputPanel.add(em);
				
				JFrame mainFrame = new JFrame("Input Panel");
		        JPanel mainPanel = new JPanel();
		        JButton whatsAppButton = new JButton("WhatsApp");
		        JButton emailButton = new JButton("Email");

		        mainPanel.add(whatsAppButton);
		        mainPanel.add(emailButton);
		        mainFrame.add(mainPanel);
		        mainFrame.setSize(400, 200);
		        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        mainFrame.setVisible(true);

		        whatsAppButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                mainFrame.dispose();
		                showWhatsAppInputPanel();
		            }
		        });

		        emailButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                mainFrame.dispose();
		                showEmailInputPanel();
		                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
	                    	//String numx= (String) modeloTabla.getValueAt(i, 1);
	                    	Contact contacto = new Contact((String)modeloTabla.getValueAt(i,0),Integer.parseInt((String)modeloTabla.getValueAt(i,1)) ,(String)modeloTabla.getValueAt(i,2) );
	                    	contactos.add(contacto);
	                        }
	                    //Enviar mensaje gmail
	                    for(Contact c : contactos ) {
	                    	System.out.printf("Estimado "+c.getName());
	                    }
		            }
		            
		        });
			/*	
                String mensajeAEnviar = escribirMensaje.getText();
			
                */
			}
        	
        });

        cargarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = ExploradorDeArchivos.showOpenDialog(AgendaContactos.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = ExploradorDeArchivos.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line;
                        modeloTabla.setRowCount(0);
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(",");
                            modeloTabla.addRow(parts);
                        }
                        reader.close();
                        JOptionPane.showMessageDialog(null, "Contactos cargados exitosamente.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar contatcos.");
                    }
                }
                }
                });
                }
		    private static void showWhatsAppInputPanel() {
		        JFrame frame = new JFrame("WhatsApp Input Panel");
		        JPanel panel = new JPanel();
		        JLabel label = new JLabel("Enter your WhatsApp message:");
		        JTextArea textArea = new JTextArea(5, 20);
		        JScrollPane scrollPane = new JScrollPane(textArea);
		
		        panel.add(label);
		        panel.add(scrollPane);
		        frame.add(panel);
		        frame.setSize(400, 200);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setVisible(true);
		        frame.dispose();
		        int resultado = JOptionPane.showOptionDialog(null, panel, "Enviar Mensaje", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (resultado == JOptionPane.OK_OPTION) {
                	
                }
		    }
		
		    private static void showEmailInputPanel() {
		        JFrame frame = new JFrame("Email Input Panel");
		        JPanel panel = new JPanel();
		        JLabel subjectLabel = new JLabel("Subject:");
		        JTextField subjectField = new JTextField(20);
		        JLabel messageLabel = new JLabel("Message:");
		        JTextArea messageTextArea = new JTextArea(5, 20);
		        JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
		        
		        panel.add(subjectLabel);
		        panel.add(subjectField);
		        panel.add(messageLabel);
		        panel.add(messageScrollPane);
		        frame.add(panel);
		        frame.setSize(400, 200);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setVisible(true);
		        frame.dispose();
		        int resultado = JOptionPane.showOptionDialog(null, panel, "Enviar Correo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
               /* if (resultado == JOptionPane.OK_OPTION) {
                	String asunto=subjectField.getText().trim();
                	String mensaje= messageTextArea.getText().trim();
                	
                }
              */
		    }
    
                public static void main(String[] args) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            AgendaContactos ac = new AgendaContactos();
                            ac.setVisible(true);
                        }
                    });
                }
                }