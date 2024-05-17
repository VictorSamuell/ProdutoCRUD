import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class clienteForm extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    int selectedClienteId; // ID do cliente selecionado para atualização

    public clienteForm() {
        initComponents();
        conectar();
        carregarClientes();
    }

    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(clienteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void carregarClientes() {
        try {
            pst = con.prepareStatement("SELECT id, nome_cliente FROM cliente_tbl");
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int clienteId = rs.getInt("id");
                String nomeCliente = rs.getString("nome_cliente");
                model.addRow(new Object[]{clienteId, nomeCliente});
            }
        } catch (SQLException ex) {
            Logger.getLogger(clienteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() {
        btnAddCliente = new javax.swing.JButton();
        btnUpdateCliente = new javax.swing.JButton(); // Botão para atualizar cliente
        btnDeleteCliente = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnAddCliente.setText("Adicionar Cliente");
        btnAddCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClienteActionPerformed(evt);
            }
        });

        // Botão para atualizar cliente
        btnUpdateCliente.setText("Atualizar Cliente");
        btnUpdateCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateClienteActionPerformed(evt);
            }
        });

        btnDeleteCliente.setText("Deletar Cliente");
        btnDeleteCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClienteActionPerformed(evt);
            }
        });

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome do Cliente"}
        ) {
            // Desabilitar a edição da tabela
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel1.setText("Nome do Cliente:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtNomeCliente)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnAddCliente)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnUpdateCliente) // Adicionando o botão de atualizar cliente
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnDeleteCliente)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddCliente)
                                        .addComponent(btnUpdateCliente) // Adicionando o botão de atualizar cliente
                                        .addComponent(btnDeleteCliente))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void btnAddClienteActionPerformed(java.awt.event.ActionEvent evt) {
        // Adicionar cliente
        String nomeCliente = txtNomeCliente.getText();

        try {
            pst = con.prepareStatement("INSERT INTO cliente_tbl (nome_cliente) VALUES (?)");
            pst.setString(1, nomeCliente);

            int k = pst.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso");
                txtNomeCliente.setText("");
                carregarClientes(); // Recarregar clientes após adição
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível adicionar o cliente");
            }
        } catch (SQLException ex) {
            Logger.getLogger(clienteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnUpdateClienteActionPerformed(java.awt.event.ActionEvent evt) {
        // Atualizar cliente
        if (selectedClienteId != 0) {
            String nomeAtualizado = txtNomeCliente.getText();

            try {
                pst = con.prepareStatement("UPDATE cliente_tbl SET nome_cliente = ? WHERE id = ?");
                pst.setString(1, nomeAtualizado);
                pst.setInt(2, selectedClienteId);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso");
                    txtNomeCliente.setText("");
                    carregarClientes(); // Recarregar clientes após atualização
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível atualizar o cliente");
                }
            } catch (SQLException ex) {
                Logger.getLogger(clienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar");
        }
    }

    private void btnDeleteClienteActionPerformed(java.awt.event.ActionEvent evt) {
        // Deletar cliente selecionado
        int selectedRow = tblClientes.getSelectedRow();

        if (selectedRow != -1) {
            int clienteId = (int) tblClientes.getValueAt(selectedRow, 0);

            try {
                pst = con.prepareStatement("DELETE FROM cliente_tbl WHERE id = ?");
                pst.setInt(1, clienteId);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso");
                    carregarClientes(); // Recarregar clientes após deleção
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível deletar o cliente");
                }
            } catch (SQLException ex) {
                Logger.getLogger(clienteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para deletar");
        }
    }

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {
        // Ação ao clicar em uma linha da tabela
        int selectedRow = tblClientes.getSelectedRow();

        if (selectedRow != -1) {
            selectedClienteId = (int) tblClientes.getValueAt(selectedRow, 0);
            String nomeCliente = (String) tblClientes.getValueAt(selectedRow, 1);

            // Preencher o campo de texto com o nome do cliente selecionado
            txtNomeCliente.setText(nomeCliente);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new clienteForm().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnAddCliente;
    private javax.swing.JButton btnUpdateCliente;
    private javax.swing.JButton btnDeleteCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtNomeCliente;
}