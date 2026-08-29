package vista;

import dao.EquipoDAO;
import dao.IncidenciaDAO;
import modelo.Equipo;
import modelo.Incidencia;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PrincipalFrame extends JFrame {
    private Usuario usuarioLogueado;
    private EquipoDAO equipoDAO;
    private IncidenciaDAO incidenciaDAO;
    
    // Elementos de Equipos
    private JTable tablaEquipos;
    private DefaultTableModel modeloTablaEquipos;
    
    // Elementos de Incidencias
    private JTable tablaIncidencias;
    private DefaultTableModel modeloTablaIncidencias;
    private JComboBox<Equipo> cmbEquiposIncidencia; // Desplegable inteligente

    public PrincipalFrame(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.equipoDAO = new EquipoDAO();
        this.incidenciaDAO = new IncidenciaDAO();
        
        setTitle("Panel Principal - Usuario: " + usuarioLogueado.getNombre());
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Gestión de Equipos", crearPanelEquipos());
        pestañas.addTab("Gestión de Incidencias", crearPanelIncidencias());
        
        add(pestañas);
    }

    // --- PANEL DE EQUIPOS (El que ya teníamos, intacto) ---
    private JPanel crearPanelEquipos() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 60, 25);
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(80, 20, 120, 25);
        panel.add(txtNombre);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(210, 20, 40, 25);
        panel.add(lblTipo);

        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Portátil", "Sobremesa", "Servidor", "Impresora", "Móvil"});
        cmbTipo.setBounds(250, 20, 100, 25);
        panel.add(cmbTipo);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(360, 20, 50, 25);
        panel.add(lblEstado);

        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Operativo", "En reparación", "Baja"});
        cmbEstado.setBounds(410, 20, 100, 25);
        panel.add(cmbEstado);

        JButton btnAñadir = new JButton("Añadir");
        btnAñadir.setBounds(530, 20, 100, 25);
        panel.add(btnAñadir);

        modeloTablaEquipos = new DefaultTableModel(new String[]{"ID", "Nombre", "Tipo", "Estado"}, 0);
        tablaEquipos = new JTable(modeloTablaEquipos);
        JScrollPane scrollPane = new JScrollPane(tablaEquipos);
        scrollPane.setBounds(20, 60, 680, 350);
        panel.add(scrollPane);

        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.setBounds(20, 430, 180, 25);
        panel.add(btnEliminar);

        cargarTablaEquipos();

        btnAñadir.addActionListener(e -> {
            String nombre = txtNombre.getText();
            if (!nombre.trim().isEmpty()) {
                Equipo nuevo = new Equipo(0, nombre, cmbTipo.getSelectedItem().toString(), cmbEstado.getSelectedItem().toString());
                if (equipoDAO.registrar(nuevo)) {
                    JOptionPane.showMessageDialog(null, "Equipo añadido");
                    txtNombre.setText("");
                    cargarTablaEquipos();
                    cargarComboEquipos(); // Actualizar desplegable de incidencias
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaEquipos.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloTablaEquipos.getValueAt(fila, 0);
                if (equipoDAO.eliminar(id)) {
                    JOptionPane.showMessageDialog(null, "Equipo eliminado");
                    cargarTablaEquipos();
                    cargarComboEquipos(); // Actualizar desplegable de incidencias
                }
            }
        });

        return panel;
    }

    // --- NUEVO PANEL DE INCIDENCIAS ---
    private JPanel crearPanelIncidencias() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblDesc = new JLabel("Problema:");
        lblDesc.setBounds(20, 20, 70, 25);
        panel.add(lblDesc);

        JTextField txtDesc = new JTextField();
        txtDesc.setBounds(90, 20, 200, 25);
        panel.add(txtDesc);

        JLabel lblEquipo = new JLabel("Equipo:");
        lblEquipo.setBounds(310, 20, 50, 25);
        panel.add(lblEquipo);

        // Desplegable que carga objetos Equipo enteros
        cmbEquiposIncidencia = new JComboBox<>();
        cmbEquiposIncidencia.setBounds(360, 20, 150, 25);
        cargarComboEquipos();
        panel.add(cmbEquiposIncidencia);

        JButton btnRegistrar = new JButton("Registrar Incidencia");
        btnRegistrar.setBounds(530, 20, 160, 25);
        panel.add(btnRegistrar);

        // Tabla de incidencias
        modeloTablaIncidencias = new DefaultTableModel(new String[]{"ID", "Descripción", "Estado", "ID Equipo", "ID Usuario"}, 0);
        tablaIncidencias = new JTable(modeloTablaIncidencias);
        JScrollPane scrollPane = new JScrollPane(tablaIncidencias);
        scrollPane.setBounds(20, 60, 680, 350);
        panel.add(scrollPane);

        JButton btnResolver = new JButton("Marcar como Resuelta");
        btnResolver.setBounds(20, 430, 180, 25);
        panel.add(btnResolver);

        cargarTablaIncidencias();

        // Acción al registrar incidencia
        btnRegistrar.addActionListener(e -> {
            String desc = txtDesc.getText();
            Equipo equipoSelec = (Equipo) cmbEquiposIncidencia.getSelectedItem();
            
            if (!desc.trim().isEmpty() && equipoSelec != null) {
                // Creamos la incidencia asociando el ID del equipo y el ID del usuario logueado
                Incidencia inc = new Incidencia(0, desc, "Pendiente", usuarioLogueado.getId(), equipoSelec.getId());
                if (incidenciaDAO.registrar(inc)) {
                    JOptionPane.showMessageDialog(null, "Incidencia registrada correctamente");
                    txtDesc.setText("");
                    cargarTablaIncidencias();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Escribe una descripción y selecciona un equipo.");
            }
        });

        // Acción al marcar como resuelta
        btnResolver.addActionListener(e -> {
            int fila = tablaIncidencias.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloTablaIncidencias.getValueAt(fila, 0);
                if (incidenciaDAO.cambiarEstado(id, "Resuelta")) {
                    JOptionPane.showMessageDialog(null, "¡Incidencia solucionada!");
                    cargarTablaIncidencias();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una incidencia de la tabla.");
            }
        });

        return panel;
    }

    // --- MÉTODOS AUXILIARES ---
    private void cargarTablaEquipos() {
        modeloTablaEquipos.setRowCount(0);
        List<Equipo> lista = equipoDAO.listar();
        for (Equipo e : lista) {
            modeloTablaEquipos.addRow(new Object[]{e.getId(), e.getNombre(), e.getTipo(), e.getEstado()});
        }
    }

    private void cargarTablaIncidencias() {
        modeloTablaIncidencias.setRowCount(0);
        List<Incidencia> lista = incidenciaDAO.listar();
        for (Incidencia i : lista) {
            modeloTablaIncidencias.addRow(new Object[]{i.getId(), i.getDescripcion(), i.getEstado(), i.getIdEquipo(), i.getIdUsuario()});
        }
    }

    private void cargarComboEquipos() {
        cmbEquiposIncidencia.removeAllItems();
        List<Equipo> lista = equipoDAO.listar();
        for (Equipo e : lista) {
            cmbEquiposIncidencia.addItem(e); // Añade el objeto entero (gracias al toString de Equipo se ve bien)
        }
    }
}