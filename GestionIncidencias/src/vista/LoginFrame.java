package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnEntrar;

    public LoginFrame() {
        // 1. Configuración básica de la ventana
        setTitle("Control de Incidencias - Login");
        setSize(320, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla
        setLayout(null); // Diseño libre

        // 2. Etiquetas y campos de texto
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 30, 80, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(120, 30, 150, 25);
        add(txtEmail);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(40, 70, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 150, 25);
        add(txtPassword);

        // 3. Botón de entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(120, 120, 100, 30);
        add(btnEntrar);

        // 4. Acción al pulsar el botón: Conexión con la base de datos
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                
                // Usamos el DAO para comprobar si el usuario existe en SQLite
                UsuarioDAO dao = new UsuarioDAO();
                Usuario usuarioLogueado = dao.login(email, password);
                
                if (usuarioLogueado != null) {
                    // Si es correcto, damos la bienvenida
                    JOptionPane.showMessageDialog(null, "¡Bienvenido " + usuarioLogueado.getNombre() + "!");
                    
                    // Abrimos el panel principal pasándole el usuario que ha entrado
                    PrincipalFrame ventana = new PrincipalFrame(usuarioLogueado);
                    ventana.setVisible(true);
                    
                    // Cerramos y destruimos esta ventana de Login
                    dispose();
                } else {
                    // Si falla, mostramos error
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
