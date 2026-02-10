package view;

import DAO.ProductDAO;
import database.DBConnection;
import model.Product;
import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.ProductViewModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ProductView extends JPanel {

    private final ProductViewModel viewModel;
    private List<Product> products;
    private JPanel MainPanel = new JPanel(new BorderLayout());
    private ProductGridView productGridView = new ProductGridView();
    private TopBarFactory topBar;

    public ProductView(ProductViewModel viewModel) {
        this.viewModel = viewModel;
        initComponents();
    }
    private void loadProducts(List<Product> products) {
        productGridView.loadProducts(products);
    }

    private void initComponents() {


        setLayout(new BorderLayout());
        // Side menu
        SideMenuBar sideMenu = new SideMenuBar();
        sideMenu.SideBarInt();
        MainPanel.add(sideMenu, BorderLayout.WEST);

        // Topbar factory
        topBar = new TopBarFactory(
                "Product Name:",
                new String[]{
                        "Sort by ID",
                        "Sort by Name",
                        "Sort by Price"
                }
        );

        MainPanel.add(topBar, BorderLayout.NORTH);
        MainPanel.add(productGridView, BorderLayout.CENTER);

        add(MainPanel, BorderLayout.CENTER);
        products = viewModel.getProducts();
        loadProducts(products);
        setupActions();
    }


    private void setupActions() {


        topBar.btnAdd.addActionListener(e -> {
            Navigator.showProductFrom();
        });

        //topBar.btnUpdate.addActionListener(e -> {

            //viewModel.updateProduct();

        //});

        topBar.btnDelete.addActionListener(e -> {
            //DELETE Product
            //viewModel.deleteProduct();
            loadProducts(viewModel.getProducts());
        });

        topBar.btnSearch.addActionListener(e -> {
            String keyword = topBar.txtSearch.getText();
            if (Objects.equals(keyword, "")){
                products= viewModel.getProducts();
            }else{
                products=viewModel.searchProducts(keyword);
                int index = topBar.cmbSort.getSelectedIndex();
                if (index==-1){
                    loadProducts(viewModel.sortProducts(index,products));
                }
            }
            loadProducts(products);

        });
        topBar.cmbSort.addActionListener(e -> {
            int index = topBar.cmbSort.getSelectedIndex();
            loadProducts(viewModel.sortProducts(index,products));
        });
    }

    public static void main(String[] args) throws SQLException {
        Connection conn= DBConnection.getConnection();
        ProductDAO dao = new ProductDAO(conn);
        ProductViewModel pvm = new ProductViewModel(dao);
       SwingUtilities.invokeLater(() ->
                new ProductView(pvm).setVisible(true)
       );
    }
}
