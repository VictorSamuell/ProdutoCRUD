import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class categoryForm extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    int productId; // ID do produto ao qual você deseja adicionar a categoria
    int selectedCategoryId; // ID da categoria selecionada para edição

    public categoryForm(int productId) {
        initComponents();
        Connect();
        this.productId = productId;
        loadProducts();
        loadCategories();
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(categoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadProducts() {
        try {
            pst = con.prepareStatement("SELECT id, pname FROM product_tbl");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String productName = rs.getString("pname");
                cmbProducts.addItem(productId + " - " + productName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(categoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadCategories() {
        try {
            pst = con.prepareStatement("SELECT id, category_name FROM category_tbl WHERE product_id = ?");
            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblCategories.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int categoryId = rs.getInt("id");
                String categoryName = rs.getString("category_name");
                model.addRow(new Object[]{categoryId, categoryName});
            }
        } catch (SQLException ex) {
            Logger.getLogger(categoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadCategory() {
        int selectedRow = tblCategories.getSelectedRow();

        if (selectedRow != -1) {
            selectedCategoryId = (int) tblCategories.getValueAt(selectedRow, 0);

            try {
                pst = con.prepareStatement("SELECT category_name FROM category_tbl WHERE id = ?");
                pst.setInt(1, selectedCategoryId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    String categoryName = rs.getString("category_name");
                    txtCategoryName.setText(categoryName);
                }
            } catch (SQLException ex) {
                Logger.getLogger(categoryForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initComponents() {
        cmbProducts = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtCategoryName = new javax.swing.JTextField();
        btnAddCategory = new javax.swing.JButton();
        btnDeleteCategory = new javax.swing.JButton(); // Botão para deletar categoria
        btnUpdateCategory = new javax.swing.JButton(); // Botão para atualizar categoria
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategories = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProductsActionPerformed(evt);
            }
        });

        jLabel2.setText("Nome da Categoria:");

        btnAddCategory.setText("Adicionar Categoria");
        btnAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoryActionPerformed(evt);
            }
        });

        // Botão para deletar categoria
        btnDeleteCategory.setText("Deletar Categoria");
        btnDeleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCategoryActionPerformed(evt);
            }
        });

        // Botão para atualizar categoria
        btnUpdateCategory.setText("Atualizar Categoria");
        btnUpdateCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCategoryActionPerformed(evt);
            }
        });

        tblCategories.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Categorias"}
        ));
        tblCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoriesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCategories);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtCategoryName)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnAddCategory)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnDeleteCategory)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnUpdateCategory)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddCategory)
                                        .addComponent(btnDeleteCategory)
                                        .addComponent(btnUpdateCategory))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void cmbProductsActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedItem = cmbProducts.getSelectedItem().toString();
        String[] parts = selectedItem.split(" - ");

        if (parts.length == 2) {
            productId = Integer.parseInt(parts[0]);
            loadCategories();
        } else {
            // Lidar com erro, se necessário
        }
    }

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {
        String categoryName = txtCategoryName.getText();

        try {
            pst = con.prepareStatement("INSERT INTO category_tbl (product_id, category_name) VALUES (?, ?)");
            pst.setInt(1, productId);
            pst.setString(2, categoryName);

            int k = pst.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Categoria adicionada com sucesso");
                txtCategoryName.setText("");
                loadCategories(); // Recarregar categorias após adição
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível adicionar a categoria");
            }
        } catch (SQLException ex) {
            Logger.getLogger(categoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {
        // Deletar categoria selecionada
        int selectedRow = tblCategories.getSelectedRow();

        if (selectedRow != -1) {
            int categoryId = (int) tblCategories.getValueAt(selectedRow, 0);

            try {
                pst = con.prepareStatement("DELETE FROM category_tbl WHERE id = ?");
                pst.setInt(1, categoryId);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "Categoria deletada com sucesso");
                    loadCategories(); // Recarregar categorias após deleção
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível deletar a categoria");
                }
            } catch (SQLException ex) {
                Logger.getLogger(categoryForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria para deletar");
        }
    }

    private void btnUpdateCategoryActionPerformed(java.awt.event.ActionEvent evt) {
        // Atualizar categoria selecionada
        String newCategoryName = txtCategoryName.getText();

        try {
            pst = con.prepareStatement("UPDATE category_tbl SET category_name = ? WHERE id = ?");
            pst.setString(1, newCategoryName);
            pst.setInt(2, selectedCategoryId);

            int k = pst.executeUpdate();

            if (k == 1) {
                JOptionPane.showMessageDialog(this, "Categoria atualizada com sucesso");
                txtCategoryName.setText("");
                loadCategories(); // Recarregar categorias após atualização
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível atualizar a categoria");
            }
        } catch (SQLException ex) {
            Logger.getLogger(categoryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tblCategoriesMouseClicked(java.awt.event.MouseEvent evt) {
        loadCategory(); // Carregar detalhes da categoria ao clicar
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new categoryForm(1).setVisible(true); // Exemplo: ID do produto 1
            }
        });
    }

    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnDeleteCategory;
    private javax.swing.JButton btnUpdateCategory;
    private javax.swing.JComboBox<String> cmbProducts;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCategories;
    private javax.swing.JTextField txtCategoryName;
}