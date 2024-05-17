import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class reclameForm extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    int selectedReclamacaoId; // ID da reclamação selecionada para edição

    public reclameForm() {
        initComponents();
        Connect();
        loadReclamacoes();
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(reclameForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadReclamacoes() {
        try {
            pst = con.prepareStatement("SELECT id, descricao FROM reclama_tbl");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblReclamacoes.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int reclamacaoId = rs.getInt("id");
                String descricao = rs.getString("descricao");
                model.addRow(new Object[]{reclamacaoId, descricao});
            }
        } catch (SQLException ex) {
            Logger.getLogger(reclameForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadReclamacao() {
        int selectedRow = tblReclamacoes.getSelectedRow();

        if (selectedRow != -1) {
            selectedReclamacaoId = (int) tblReclamacoes.getValueAt(selectedRow, 0);

            try {
                pst = con.prepareStatement("SELECT descricao FROM reclama_tbl WHERE id = ?");
                pst.setInt(1, selectedReclamacaoId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    String descricao = rs.getString("descricao");
                    txtDescricao.setText(descricao);
                }
            } catch (SQLException ex) {
                Logger.getLogger(reclameForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initComponents() {
        btnAddReclamacao = new javax.swing.JButton();
        btnDeleteReclamacao = new javax.swing.JButton(); // Botão para deletar reclamação
        btnUpdateReclamacao = new javax.swing.JButton(); // Botão para atualizar reclamação
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReclamacoes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnAddReclamacao.setText("Adicionar Reclamação");
        btnAddReclamacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddReclamacaoActionPerformed(evt);
            }
        });

        // Botão para deletar reclamação
        btnDeleteReclamacao.setText("Deletar Reclamação");
        btnDeleteReclamacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteReclamacaoActionPerformed(evt);
            }
        });

        // Botão para atualizar reclamação
        btnUpdateReclamacao.setText("Atualizar Reclamação");
        btnUpdateReclamacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateReclamacaoActionPerformed(evt);
            }
        });

        tblReclamacoes.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Descrição"}
        ));
        tblReclamacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblReclamacoesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblReclamacoes);

        jLabel1.setText("Descrição:");

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
                                                .addComponent(txtDescricao)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnAddReclamacao)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnDeleteReclamacao)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnUpdateReclamacao)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddReclamacao)
                                        .addComponent(btnDeleteReclamacao)
                                        .addComponent(btnUpdateReclamacao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void btnAddReclamacaoActionPerformed(java.awt.event.ActionEvent evt) {
        // Adicionar reclamação
        String descricao = txtDescricao.getText();

        try {
            pst = con.prepareStatement("INSERT INTO reclama_tbl (descricao) VALUES (?)");
            pst.setString(1, descricao);

            int k = pst.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Reclamação adicionada com sucesso");
                txtDescricao.setText("");
                loadReclamacoes(); // Recarregar reclamações após adição
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível adicionar a reclamação");
            }
        } catch (SQLException ex) {
            Logger.getLogger(reclameForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnDeleteReclamacaoActionPerformed(java.awt.event.ActionEvent evt) {
        // Deletar reclamação selecionada
        int selectedRow = tblReclamacoes.getSelectedRow();

        if (selectedRow != -1) {
            int reclamacaoId = (int) tblReclamacoes.getValueAt(selectedRow, 0);

            try {
                pst = con.prepareStatement("DELETE FROM reclama_tbl WHERE id = ?");
                pst.setInt(1, reclamacaoId);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "Reclamação deletada com sucesso");
                    loadReclamacoes(); // Recarregar reclamações após deleção
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível deletar a reclamação");
                }
            } catch (SQLException ex) {
                Logger.getLogger(reclameForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma reclamação para deletar");
        }
    }

    private void btnUpdateReclamacaoActionPerformed(java.awt.event.ActionEvent evt) {
        // Atualizar reclamação selecionada
        String newDescricao = txtDescricao.getText();

        try {
            pst = con.prepareStatement("UPDATE reclama_tbl SET descricao = ? WHERE id = ?");
            pst.setString(1, newDescricao);
            pst.setInt(2, selectedReclamacaoId);

            int k = pst.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Reclamação atualizada com sucesso");
                txtDescricao.setText("");
                loadReclamacoes(); // Recarregar reclamações após atualização
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível atualizar a reclamação");
            }
        } catch (SQLException ex) {
            Logger.getLogger(reclameForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tblReclamacoesMouseClicked(java.awt.event.MouseEvent evt) {
        loadReclamacao(); // Carregar detalhes da reclamação ao clicar
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new reclameForm().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnAddReclamacao;
    private javax.swing.JButton btnDeleteReclamacao;
    private javax.swing.JButton btnUpdateReclamacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReclamacoes;
    private javax.swing.JTextField txtDescricao;
}