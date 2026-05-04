/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;
import aplicacion.Animal;
import java.time.LocalDate;
import aplicacion.HistorialMedico;
import aplicacion.Trabajador;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alumnogreibd
 */
public class VGestionAnimales extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VGestionAnimales.class.getName());

    private FachadaGui fgui;
    private aplicacion.FachadaAplicacion fa;
    
    private List<Animal> animalesActuales = new java.util.ArrayList<>();
    private List<HistorialMedico> historialActual = new java.util.ArrayList<>();
    private DefaultListModel<String> modeloDisponibles = new DefaultListModel<>();
    private DefaultListModel<String> modeloAsignados = new DefaultListModel<>();
    private List<String> todosLosCuidadoresDni = new java.util.ArrayList<>();
    
    /**
     * Constructor adaptado para integrarse en la arquitectura
     */
    public VGestionAnimales(java.awt.Frame parent, boolean modal, FachadaGui fgui) {
        super(parent, modal);
        this.fgui = fgui;
        // Inicializamos la Fachada de Aplicación ANTES de llamar a cargarZonas()
        this.fa = fgui.getFachadaAplicacion(); 
        
        initComponents();
        
        // Evitamos que al cerrar esta ventana se cierre toda la aplicación
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
        
        // Inicializamos los estados de conservación
        String[] estados = {"Preocupacion Menor", "Domesticado", "En Peligro", "Vulnerable"};
        estadoConservaComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(estados));
        
        cargarZonas(); // Carga las zonas de la BD
        // Cargamos todos los animales al abrir
        buscarAnimales("", ""); 
        
        cuidadoresDisponiblesList2.setModel(modeloDisponibles);
        cuidadoresNoDisponiblesList1.setModel(modeloAsignados);
        cargarTodosLosCuidadores();
    }

    VGestionAnimales() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Pide la lista a la BD delegando el filtro principal y rellena la tabla
     */
    private void buscarAnimales(String filtroNombre, String filtroZona) {
        animalesActuales = fa.obtenerAnimales(filtroNombre, filtroZona); 

        DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();
        modeloTabla.setRowCount(0);

        for (Animal a : animalesActuales) {
            Object[] fila = new Object[5];
            fila[0] = a.getIdAnimal();
            fila[1] = a.getNombreComun();
            fila[2] = a.getNombreCientifico();
            fila[3] = a.getNombreZona();
            fila[4] = a.getEstadoConservacion();
            modeloTabla.addRow(fila);
        }
    }
    
    private void buscarHistorial(int idAnimal) {
        historialActual = fa.obtenerHistorial(idAnimal);

        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        modeloTabla.setRowCount(0);

        for (HistorialMedico h : historialActual) {
            Object[] fila = new Object[4];
            fila[0] = h.getCodigo(); 
            fila[1] = h.getFecha(); 
            fila[2] = h.getDiagnostico();
            fila[3] = h.getDniVeterinario(); 
            modeloTabla.addRow(fila);
        }
    }
    
    private void cargarTodosLosCuidadores() {
        // Pedimos los DNIS a la fachada, que ahora sí tiene el método
        todosLosCuidadoresDni = fa.obtenerTodosLosCuidadores();
        
        modeloDisponibles.clear();
        for (String c : todosLosCuidadoresDni) {
            modeloDisponibles.addElement(c);
        }
    }

    private void buscarCuidadoresAnimal(int idAnimal) {
        modeloAsignados.clear();
        modeloDisponibles.clear();
        
        // Pedimos a la BD los cuidadores asignados a ESTE animal
        List<String> asignados = fa.obtenerCuidadoresPorAnimal(idAnimal);
        
        // Rellenamos la lista de asignados (derecha)
        for (String asig : asignados) {
            modeloAsignados.addElement(asig);
        }
        
        // Rellenamos la lista de disponibles (izquierda) con todos los que NO estén ya asignados
        for (String cuid : todosLosCuidadoresDni) {
            if (!asignados.contains(cuid)) {
                modeloDisponibles.addElement(cuid);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        animalPanel1 = new javax.swing.JPanel();
        nombreComunLabel2 = new javax.swing.JLabel();
        nombreCientificoTextField1 = new javax.swing.JTextField();
        idTextField1 = new javax.swing.JTextField();
        nombreCientificoLabel1 = new javax.swing.JLabel();
        idLabel2 = new javax.swing.JLabel();
        idTextField2 = new javax.swing.JTextField();
        buscarButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        idLabel1 = new javax.swing.JLabel();
        nombreCientificoTextField2 = new javax.swing.JTextField();
        nombreComunLabel1 = new javax.swing.JLabel();
        nombreComunTextField2 = new javax.swing.JTextField();
        nombreCientificoLabel3 = new javax.swing.JLabel();
        nombreComunTextField1 = new javax.swing.JTextField();
        zonaLabel1 = new javax.swing.JLabel();
        estadoConservaLabel1 = new javax.swing.JLabel();
        ZonaComboBox1 = new javax.swing.JComboBox<>();
        alimentacionLabel1 = new javax.swing.JLabel();
        estadoConservaComboBox2 = new javax.swing.JComboBox<>();
        descripcionLabel1 = new javax.swing.JLabel();
        alimentacionTextField1 = new javax.swing.JTextField();
        descripcionTextField1 = new javax.swing.JTextField();
        nuevoAnimalButton2 = new javax.swing.JButton();
        guardarAnimalButton2 = new javax.swing.JButton();
        eliminarAnimalButton4 = new javax.swing.JButton();
        salirAnimalButton1 = new javax.swing.JButton();
        historialPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        codigoTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        diagnosticoTextField1 = new javax.swing.JTextField();
        veterinarioComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        fechaTextField1 = new javax.swing.JTextField();
        nuevoHistorialButton1 = new javax.swing.JButton();
        guardarHistorialButton1 = new javax.swing.JButton();
        borrarHistorialButton1 = new javax.swing.JButton();
        salirHistorialButton1 = new javax.swing.JButton();
        cuidadoresPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cuidadoresNoDisponiblesList1 = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        cuidadoresDisponiblesList2 = new javax.swing.JList<>();
        insertarNoDisponiblesButton1 = new javax.swing.JButton();
        insertarDisponiblesButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        actualizarButton1 = new javax.swing.JButton();
        salirCuidadoresButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        nombreComunLabel2.setText("Nombre común");

        nombreCientificoLabel1.setText("Nombre científico");

        idLabel2.setText("ID");

        idTextField2.setColumns(5);

        buscarButton1.setLabel("Buscar");
        buscarButton1.addActionListener(this::buscarButton1ActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre común", "Nombre científico", "Zona", "Estado de Conserva"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setText("Datos del animal seleccionado:");

        idLabel1.setText("ID");

        nombreCientificoTextField2.setColumns(5);

        nombreComunLabel1.setText("Nombre común");

        nombreComunTextField2.setColumns(5);

        nombreCientificoLabel3.setText("Nombre científico");

        zonaLabel1.setText("Zona");

        estadoConservaLabel1.setText("Estado de conserva");

        ZonaComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        alimentacionLabel1.setText("Alimentación");

        estadoConservaComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vulnerable", "En Peligro", "Preocupacion Menor", "Domesticado" }));

        descripcionLabel1.setText("Descripción");

        alimentacionTextField1.setColumns(5);

        descripcionTextField1.setColumns(5);

        nuevoAnimalButton2.setText("Nuevo");
        nuevoAnimalButton2.addActionListener(this::nuevoAnimalButton2ActionPerformed);

        guardarAnimalButton2.setText("Guardar");
        guardarAnimalButton2.addActionListener(this::guardarAnimalButton2ActionPerformed);

        eliminarAnimalButton4.setText("Eliminar");
        eliminarAnimalButton4.addActionListener(this::eliminarAnimalButton4ActionPerformed);

        salirAnimalButton1.setForeground(new java.awt.Color(255, 0, 51));
        salirAnimalButton1.setText("Salir");
        salirAnimalButton1.addActionListener(this::salirAnimalButton1ActionPerformed);

        javax.swing.GroupLayout animalPanel1Layout = new javax.swing.GroupLayout(animalPanel1);
        animalPanel1.setLayout(animalPanel1Layout);
        animalPanel1Layout.setHorizontalGroup(
            animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animalPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombreComunLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreComunTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nombreCientificoLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreCientificoTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(idLabel1)
                .addGap(18, 18, 18)
                .addComponent(idTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(buscarButton1)
                .addContainerGap())
            .addGroup(animalPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(animalPanel1Layout.createSequentialGroup()
                        .addComponent(nuevoAnimalButton2)
                        .addGap(18, 18, 18)
                        .addComponent(guardarAnimalButton2)
                        .addGap(18, 18, 18)
                        .addComponent(eliminarAnimalButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(salirAnimalButton1)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, animalPanel1Layout.createSequentialGroup()
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, animalPanel1Layout.createSequentialGroup()
                                    .addComponent(estadoConservaLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(estadoConservaComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, animalPanel1Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nombreCientificoLabel3)
                                        .addComponent(zonaLabel1))
                                    .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(animalPanel1Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(nombreCientificoTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, animalPanel1Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(ZonaComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(animalPanel1Layout.createSequentialGroup()
                                        .addComponent(idLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(idTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(animalPanel1Layout.createSequentialGroup()
                                        .addComponent(nombreComunLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(nombreComunTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))))
                        .addGap(29, 29, 29)
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(descripcionLabel1)
                            .addComponent(alimentacionLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descripcionTextField1)
                            .addComponent(alimentacionTextField1))
                        .addContainerGap())))
            .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(animalPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        animalPanel1Layout.setVerticalGroup(
            animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animalPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreComunLabel1)
                    .addComponent(nombreComunTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreCientificoLabel1)
                    .addComponent(nombreCientificoTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idLabel1)
                    .addComponent(idTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(animalPanel1Layout.createSequentialGroup()
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(alimentacionTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alimentacionLabel1))
                        .addGap(24, 24, 24)
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(descripcionTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(descripcionLabel1))
                        .addGap(13, 13, 13))
                    .addGroup(animalPanel1Layout.createSequentialGroup()
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idLabel2)
                            .addComponent(idTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombreComunLabel2)
                            .addComponent(nombreComunTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreCientificoTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreCientificoLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(animalPanel1Layout.createSequentialGroup()
                                .addComponent(zonaLabel1)
                                .addGap(0, 39, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, animalPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(ZonaComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(estadoConservaLabel1)
                                    .addComponent(estadoConservaComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)))
                .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevoAnimalButton2)
                    .addComponent(guardarAnimalButton2)
                    .addComponent(eliminarAnimalButton4)
                    .addComponent(salirAnimalButton1))
                .addGap(11, 11, 11))
            .addGroup(animalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, animalPanel1Layout.createSequentialGroup()
                    .addContainerGap(77, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(240, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Animales", animalPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Fecha", "Diagnóstico", "Veterinario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        jLabel4.setText("Nuevo / Editar Registro");

        jLabel5.setText("Código");

        jLabel6.setText("Diagnóstico");

        jLabel7.setText("Veterinario");

        veterinarioComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Fecha");

        nuevoHistorialButton1.setText("Nuevo registro");
        nuevoHistorialButton1.addActionListener(this::nuevoHistorialButton1ActionPerformed);

        guardarHistorialButton1.setText("Guardar registro");
        guardarHistorialButton1.addActionListener(this::guardarHistorialButton1ActionPerformed);

        borrarHistorialButton1.setText("Borrar");
        borrarHistorialButton1.addActionListener(this::borrarHistorialButton1ActionPerformed);

        salirHistorialButton1.setForeground(new java.awt.Color(255, 0, 51));
        salirHistorialButton1.setText("Salir");
        salirHistorialButton1.addActionListener(this::salirHistorialButton1ActionPerformed);

        javax.swing.GroupLayout historialPanel2Layout = new javax.swing.GroupLayout(historialPanel2);
        historialPanel2.setLayout(historialPanel2Layout);
        historialPanel2Layout.setHorizontalGroup(
            historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historialPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(historialPanel2Layout.createSequentialGroup()
                        .addGroup(historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addGroup(historialPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(codigoTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(222, 222, 222)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fechaTextField1))
                            .addGroup(historialPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diagnosticoTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(historialPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(veterinarioComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 314, Short.MAX_VALUE))
                    .addGroup(historialPanel2Layout.createSequentialGroup()
                        .addComponent(nuevoHistorialButton1)
                        .addGap(18, 18, 18)
                        .addComponent(guardarHistorialButton1)
                        .addGap(18, 18, 18)
                        .addComponent(borrarHistorialButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(salirHistorialButton1)))
                .addContainerGap())
        );
        historialPanel2Layout.setVerticalGroup(
            historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historialPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(codigoTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(fechaTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(diagnosticoTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addGroup(historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(veterinarioComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(historialPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevoHistorialButton1)
                    .addComponent(guardarHistorialButton1)
                    .addComponent(borrarHistorialButton1)
                    .addComponent(salirHistorialButton1))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Historial Médico", historialPanel2);

        cuidadoresNoDisponiblesList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(cuidadoresNoDisponiblesList1);

        cuidadoresDisponiblesList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(cuidadoresDisponiblesList2);

        insertarNoDisponiblesButton1.setText("---->");
        insertarNoDisponiblesButton1.addActionListener(this::insertarNoDisponiblesButton1ActionPerformed);

        insertarDisponiblesButton2.setText("<----");
        insertarDisponiblesButton2.addActionListener(this::insertarDisponiblesButton2ActionPerformed);

        jLabel1.setText("Cuidadores disponibles");

        jLabel2.setText("Cuidadores no disponibles");

        actualizarButton1.setText("Actualizar");
        actualizarButton1.addActionListener(this::actualizarButton1ActionPerformed);

        salirCuidadoresButton3.setForeground(new java.awt.Color(255, 0, 51));
        salirCuidadoresButton3.setText("Salir");
        salirCuidadoresButton3.addActionListener(this::salirCuidadoresButton3ActionPerformed);

        javax.swing.GroupLayout cuidadoresPanel3Layout = new javax.swing.GroupLayout(cuidadoresPanel3);
        cuidadoresPanel3.setLayout(cuidadoresPanel3Layout);
        cuidadoresPanel3Layout.setHorizontalGroup(
            cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cuidadoresPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cuidadoresPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cuidadoresPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(insertarNoDisponiblesButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cuidadoresPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(insertarDisponiblesButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))))
                    .addGroup(cuidadoresPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cuidadoresPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actualizarButton1)
                .addGap(36, 36, 36)
                .addComponent(salirCuidadoresButton3)
                .addGap(28, 28, 28))
        );
        cuidadoresPanel3Layout.setVerticalGroup(
            cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cuidadoresPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cuidadoresPanel3Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(insertarNoDisponiblesButton1)
                        .addGap(18, 18, 18)
                        .addComponent(insertarDisponiblesButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(cuidadoresPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actualizarButton1)
                    .addComponent(salirCuidadoresButton3))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Cuidadores", cuidadoresPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirAnimalButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirAnimalButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_salirAnimalButton1ActionPerformed

    private void eliminarAnimalButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarAnimalButton4ActionPerformed
        if (idTextField2.getText().isEmpty()) {
            fgui.muestraAviso("Selecciona un animal de la tabla para eliminarlo.");
            return;
        }

        boolean confirmado = fgui.pideConfirmacion("¿Estás seguro de eliminar este animal?");
        if (confirmado) {
            try {
                int id = Integer.parseInt(idTextField2.getText());
                fa.borrarAnimal(id);
                fgui.muestraAviso("Animal eliminado.");
                
                buscarAnimales("", "");
                nuevoAnimalButton2ActionPerformed(evt);
            } catch (Exception e) {
                fgui.muestraExcepcion("Error al eliminar: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_eliminarAnimalButton4ActionPerformed

    private void guardarAnimalButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarAnimalButton2ActionPerformed
        if (nombreComunTextField2.getText().isEmpty() || nombreCientificoTextField2.getText().isEmpty()) {
            fgui.muestraAviso("El nombre común y científico son obligatorios.");
            return;
        }

        int id = idTextField2.getText().equals("Auto") ? 0 : Integer.parseInt(idTextField2.getText()); 
        String nombreComun = nombreComunTextField2.getText();
        String nombreCientifico = nombreCientificoTextField2.getText();
        String alimentacion = alimentacionTextField1.getText();
        String descripcion = descripcionTextField1.getText();
        String zona = ZonaComboBox1.getSelectedItem() != null ? ZonaComboBox1.getSelectedItem().toString() : "";
        String estado = estadoConservaComboBox2.getSelectedItem() != null ? estadoConservaComboBox2.getSelectedItem().toString() : "";

        try {
     
            Animal animal;

            if (jTable1.getSelectedRow() == -1) {
                // Animal nuevo - no pasamos el id
                animal = new Animal(0, nombreCientifico, nombreComun, alimentacion, estado, descripcion, zona, null);
                fa.insertarAnimal(animal);
                fgui.muestraAviso("Animal registrado correctamente.");
            } else {
                // Modificar - sí necesitamos el id
                animal = new Animal(id, nombreCientifico, nombreComun, alimentacion, estado, descripcion, zona, null);
                fa.modificarAnimal(animal);
                fgui.muestraAviso("Animal actualizado correctamente.");
            }
            
            buscarAnimales("", "");
            nuevoAnimalButton2ActionPerformed(evt);
            
        } catch (Exception ex) {
            fgui.muestraExcepcion("Error al guardar el animal: " + ex.getMessage());
        }
    }//GEN-LAST:event_guardarAnimalButton2ActionPerformed

    private void nuevoAnimalButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoAnimalButton2ActionPerformed
        idTextField2.setText("Auto");
        idTextField2.setEditable(false);
        nombreComunTextField2.setText("");
        nombreCientificoTextField2.setText("");
        alimentacionTextField1.setText("");
        descripcionTextField1.setText("");
        jTable1.clearSelection();
        nombreComunTextField2.requestFocus();
    }//GEN-LAST:event_nuevoAnimalButton2ActionPerformed

    private void buscarButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarButton1ActionPerformed
        String filtroComun = nombreComunTextField1.getText().trim();
        String filtroCientifico = nombreCientificoTextField1.getText().trim().toLowerCase();
        String filtroId = idTextField1.getText().trim();

        // 1. Buscamos por la base de datos
        animalesActuales = fa.obtenerAnimales(filtroComun, "");

        // 2. Filtramos localmente por ID y nombre científico si hace falta
        if (!filtroCientifico.isEmpty() || !filtroId.isEmpty()) {
            java.util.List<Animal> animalesFiltrados = new java.util.ArrayList<>();
            for (Animal a : animalesActuales) {
                boolean coincideCientifico = filtroCientifico.isEmpty() || a.getNombreCientifico().toLowerCase().contains(filtroCientifico);
                boolean coincideId = filtroId.isEmpty() || String.valueOf(a.getIdAnimal()).equals(filtroId);

                if (coincideCientifico && coincideId) {
                    animalesFiltrados.add(a);
                }
            }
            animalesActuales = animalesFiltrados; 
        }

        // 3. Volcamos a la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();
        modeloTabla.setRowCount(0);

        for (Animal a : animalesActuales) {
            Object[] fila = new Object[5];
            fila[0] = a.getIdAnimal();
            fila[1] = a.getNombreComun();
            fila[2] = a.getNombreCientifico();
            fila[3] = a.getNombreZona();
            fila[4] = a.getEstadoConservacion();
            modeloTabla.addRow(fila);
        }
    }//GEN-LAST:event_buscarButton1ActionPerformed

    private void insertarNoDisponiblesButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarNoDisponiblesButton1ActionPerformed
        // Cogemos los elementos seleccionados en la lista de Disponibles (izquierda)
        List<String> seleccionados = cuidadoresDisponiblesList2.getSelectedValuesList();

        // Los pasamos a la de Asignados y los borramos de Disponibles
        for (String c : seleccionados) {
            modeloAsignados.addElement(c);
            modeloDisponibles.removeElement(c);
            }
    }//GEN-LAST:event_insertarNoDisponiblesButton1ActionPerformed

    private void insertarDisponiblesButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarDisponiblesButton2ActionPerformed
        // Cogemos los elementos seleccionados en la lista de Asignados (derecha)
        List<String> seleccionados = cuidadoresNoDisponiblesList1.getSelectedValuesList();
        
        // Los devolvemos a Disponibles y los quitamos de Asignados
        for (String c : seleccionados) {
            modeloDisponibles.addElement(c);
            modeloAsignados.removeElement(c);
        }
    }//GEN-LAST:event_insertarDisponiblesButton2ActionPerformed

    private void actualizarButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarButton1ActionPerformed
        if (idTextField2.getText().isEmpty()) {
            fgui.muestraAviso("Primero debes seleccionar un animal en la pestaña principal.");
            return;
        }

        try {
            int idAnimal = Integer.parseInt(idTextField2.getText());
            
            // Cogemos todos los elementos que han quedado en la lista de Asignados
            List<String> cuidadoresFinales = new java.util.ArrayList<>();
            for (int i = 0; i < modeloAsignados.getSize(); i++) {
                cuidadoresFinales.add(modeloAsignados.getElementAt(i));
            }
            
            // Le pasamos la lista completa a la Fachada para que borre los antiguos y ponga los nuevos
            fa.actualizarCuidadoresAnimal(idAnimal, cuidadoresFinales);
            fgui.muestraAviso("Cuidadores actualizados correctamente.");
            
        } catch (Exception e) {
            fgui.muestraExcepcion("Error al actualizar cuidadores: " + e.getMessage());
        }
    }//GEN-LAST:event_actualizarButton1ActionPerformed

    private void salirCuidadoresButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirCuidadoresButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_salirCuidadoresButton3ActionPerformed

    private void nuevoHistorialButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoHistorialButton1ActionPerformed
        // Verificamos que hay un animal seleccionado en la pestaña 1
        if (idTextField2.getText().isEmpty()) {
            fgui.muestraAviso("Primero debes seleccionar un animal en la pestaña 'Animales'.");
            return;
        }

        codigoTextField1.setText("");
        codigoTextField1.setEditable(true); // Si quieres que se escriba a mano, si es automático ponlo a false
        fechaTextField1.setText("");
        diagnosticoTextField1.setText("");
        jTable2.clearSelection();
        fechaTextField1.requestFocus();
    }//GEN-LAST:event_nuevoHistorialButton1ActionPerformed

    private void guardarHistorialButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarHistorialButton1ActionPerformed
        if (idTextField2.getText().isEmpty()) {
            fgui.muestraAviso("Selecciona un animal en la pestaña principal primero.");
            return;
        }
        if (diagnosticoTextField1.getText().isEmpty() || fechaTextField1.getText().isEmpty()) {
            fgui.muestraAviso("La fecha y el diagnóstico son obligatorios.");
            return;
        }

        try {
            int idAnimal = Integer.parseInt(idTextField2.getText());
            int codigo = codigoTextField1.getText().isEmpty() ? 0 : Integer.parseInt(codigoTextField1.getText());
            
            // 1. Corregimos el tipo de fecha a LocalDate
            java.time.LocalDate fecha = java.time.LocalDate.parse(fechaTextField1.getText()); 
            String diagnostico = diagnosticoTextField1.getText();
            String veterinario = veterinarioComboBox1.getSelectedItem() != null ? veterinarioComboBox1.getSelectedItem().toString() : null;

            // 2. Corregimos el orden: idAnimal va antes que veterinario
            HistorialMedico h = new HistorialMedico(codigo, fecha, diagnostico, idAnimal, veterinario);

            if (jTable2.getSelectedRow() == -1) {
                fa.insertarHistorial(h);
                fgui.muestraAviso("Historial registrado.");
            } else {
                fa.modificarHistorial(h);
                fgui.muestraAviso("Historial actualizado.");
            }
            
            buscarHistorial(idAnimal); // Refrescamos la tabla
            nuevoHistorialButton1ActionPerformed(evt);
            
        } catch (java.time.format.DateTimeParseException ex) {
            // Cambiamos la excepción que se captura al fallar el parseo de LocalDate
            fgui.muestraExcepcion("Formato de fecha incorrecto. Usa AAAA-MM-DD (Ejemplo: 2024-05-20).");
        } catch (Exception ex) {
            fgui.muestraExcepcion("Error al guardar: " + ex.getMessage());
        }
    }//GEN-LAST:event_guardarHistorialButton1ActionPerformed

    private void borrarHistorialButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarHistorialButton1ActionPerformed
        if (codigoTextField1.getText().isEmpty()) {
            fgui.muestraAviso("Selecciona un registro médico para borrar.");
            return;
        }

        if (fgui.pideConfirmacion("¿Seguro que deseas borrar este registro médico?")) {
            try {
                int codigo = Integer.parseInt(codigoTextField1.getText());
                fa.borrarHistorial(codigo);
                fgui.muestraAviso("Registro eliminado.");
                
                int idAnimal = Integer.parseInt(idTextField2.getText());
                buscarHistorial(idAnimal);
                nuevoHistorialButton1ActionPerformed(evt);
            } catch (Exception e) {
                fgui.muestraExcepcion("Error al borrar: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_borrarHistorialButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int filaSeleccionada = jTable1.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            Animal a = animalesActuales.get(filaSeleccionada);
            
            idTextField2.setText(String.valueOf(a.getIdAnimal()));
            idTextField2.setEditable(false); // Bloqueamos el ID para evitar sobreescrituras
            
            nombreComunTextField2.setText(a.getNombreComun());
            nombreCientificoTextField2.setText(a.getNombreCientifico());
            alimentacionTextField1.setText(a.getAlimentacion());
            descripcionTextField1.setText(a.getDescripcion());
            
            if (a.getNombreZona() != null) ZonaComboBox1.setSelectedItem(a.getNombreZona());
            if (a.getEstadoConservacion() != null){
                estadoConservaComboBox2.setSelectedItem(jTable1.getValueAt(filaSeleccionada, 3).toString());
                if (estadoConservaComboBox2.getSelectedIndex() == -1 || 
                    !estadoConservaComboBox2.getSelectedItem().equals(a.getEstadoConservacion().trim())) {
                    estadoConservaComboBox2.addItem(a.getEstadoConservacion().trim());
                    estadoConservaComboBox2.setSelectedItem(a.getEstadoConservacion().trim());
                }
            }
            
            buscarHistorial(a.getIdAnimal());
            
            buscarCuidadoresAnimal(a.getIdAnimal());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int filaSeleccionada = jTable2.getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            HistorialMedico h = historialActual.get(filaSeleccionada);
            
            codigoTextField1.setText(String.valueOf(h.getCodigo()));
            codigoTextField1.setEditable(false); // Bloqueamos el código
            
            // Asumiendo que getFecha devuelve algo que se puede pasar a String
            fechaTextField1.setText(h.getFecha().toString()); 
            diagnosticoTextField1.setText(h.getDiagnostico());
            
            if (h.getDniVeterinario() != null) veterinarioComboBox1.setSelectedItem(h.getDniVeterinario());
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void salirHistorialButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirHistorialButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_salirHistorialButton1ActionPerformed

    private void cargarZonas() {
        ZonaComboBox1.removeAllItems();
        for (String zona : fa.obtenerNombresZonas()) {
            ZonaComboBox1.addItem(zona);
        }
    }  
    
    private void cargarVeterinarios() {
        veterinarioComboBox1.removeAllItems();
        // Asumo que tienes un método que devuelve los nombres o IDs de los veterinarios
        for (String vet : fa.obtenerNombresVeterinarios()) { 
            veterinarioComboBox1.addItem(vet);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ZonaComboBox1;
    private javax.swing.JButton actualizarButton1;
    private javax.swing.JLabel alimentacionLabel1;
    private javax.swing.JTextField alimentacionTextField1;
    private javax.swing.JPanel animalPanel1;
    private javax.swing.JButton borrarHistorialButton1;
    private javax.swing.JButton buscarButton1;
    private javax.swing.JTextField codigoTextField1;
    private javax.swing.JList<String> cuidadoresDisponiblesList2;
    private javax.swing.JList<String> cuidadoresNoDisponiblesList1;
    private javax.swing.JPanel cuidadoresPanel3;
    private javax.swing.JLabel descripcionLabel1;
    private javax.swing.JTextField descripcionTextField1;
    private javax.swing.JTextField diagnosticoTextField1;
    private javax.swing.JButton eliminarAnimalButton4;
    private javax.swing.JComboBox<String> estadoConservaComboBox2;
    private javax.swing.JLabel estadoConservaLabel1;
    private javax.swing.JTextField fechaTextField1;
    private javax.swing.JButton guardarAnimalButton2;
    private javax.swing.JButton guardarHistorialButton1;
    private javax.swing.JPanel historialPanel2;
    private javax.swing.JLabel idLabel1;
    private javax.swing.JLabel idLabel2;
    private javax.swing.JTextField idTextField1;
    private javax.swing.JTextField idTextField2;
    private javax.swing.JButton insertarDisponiblesButton2;
    private javax.swing.JButton insertarNoDisponiblesButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel nombreCientificoLabel1;
    private javax.swing.JLabel nombreCientificoLabel3;
    private javax.swing.JTextField nombreCientificoTextField1;
    private javax.swing.JTextField nombreCientificoTextField2;
    private javax.swing.JLabel nombreComunLabel1;
    private javax.swing.JLabel nombreComunLabel2;
    private javax.swing.JTextField nombreComunTextField1;
    private javax.swing.JTextField nombreComunTextField2;
    private javax.swing.JButton nuevoAnimalButton2;
    private javax.swing.JButton nuevoHistorialButton1;
    private javax.swing.JButton salirAnimalButton1;
    private javax.swing.JButton salirCuidadoresButton3;
    private javax.swing.JButton salirHistorialButton1;
    private javax.swing.JComboBox<String> veterinarioComboBox1;
    private javax.swing.JLabel zonaLabel1;
    // End of variables declaration//GEN-END:variables
}
