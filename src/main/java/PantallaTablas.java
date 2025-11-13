import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PantallaTablas extends JFrame {
    
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "PERS_TABLAS";
    private static final String PASS = "pers_tablas";

    private JTable tablaDatos;
    private DefaultTableModel modelo;
    private JButton botonSiguiente;
    private JLabel etiquetaTitulo;

    
    private List<String> tablas = new ArrayList<>();
    private int indiceActual = 0;

    public PantallaTablas() {
        super("Visualizador de Tablas - Oracle");

       
        setLayout(new BorderLayout());

        etiquetaTitulo = new JLabel("", SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        etiquetaTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        modelo = new DefaultTableModel();
        tablaDatos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaDatos);

        botonSiguiente = new JButton("Siguiente");
        botonSiguiente.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonSiguiente.addActionListener(e -> mostrarSiguienteTabla());

        add(etiquetaTitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botonSiguiente, BorderLayout.SOUTH);

        
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        cargarListaTablas();

        
        if (!tablas.isEmpty()) {
            mostrarTabla(tablas.get(0));
        }
    }

    
    private void cargarListaTablas() {
        tablas.add("zona");
        tablas.add("rol");
        tablas.add("departamento");
        tablas.add("personal");
        tablas.add("proy_tablas.proyecto");
        tablas.add("proy_tablas.asignacion_personal_proyecto");
        tablas.add("activos_tablas.activo");
        tablas.add("activos_tablas.asignacion_activo_proyecto");
    }

    
    private void mostrarSiguienteTabla() {
        indiceActual = (indiceActual + 1) % tablas.size();
        mostrarTabla(tablas.get(indiceActual));
    }

 
    private void mostrarTabla(String nombreTabla) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            
            ResultSet rs = statement.executeQuery("SELECT * FROM " + nombreTabla);

         
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

         
            modelo.setRowCount(0);
            modelo.setColumnCount(0);

           
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(meta.getColumnName(i));
            }

            
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 1; i <= columnas; i++) {
                    fila[i - 1] = rs.getObject(i);
                }
                modelo.addRow(fila);
            }

            etiquetaTitulo.setText("Tabla: " + nombreTabla.toUpperCase());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar " + nombreTabla + ": " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaTablas().setVisible(true));
    }
}
