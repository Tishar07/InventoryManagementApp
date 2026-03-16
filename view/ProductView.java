package view;

import model.Product;
import utilities.Navigator;
import utilities.TopBarFactory;
import view.components.SideMenuBar;
import viewModel.ProductViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductView extends JPanel {

    private final ProductViewModel viewModel;
    private List<Product> products;

    private ProductGridView productGridView;
    private TopBarFactory topBar;

    public ProductView(ProductViewModel viewModel) {
        this.viewModel = viewModel;
        this.productGridView = new ProductGridView(viewModel);
        initializeUI();
    }

    private void initializeUI() {

        setLayout(new BorderLayout());

        // ================= LEFT SIDE MENU =================

        SideMenuBar sideMenu = new SideMenuBar();
        sideMenu.SideBarInt();
        add(sideMenu, BorderLayout.WEST);

        // ================= RIGHT PANEL =================

        JPanel rightPanel = new JPanel(new BorderLayout());

        // ================= TOP BAR =================

        topBar = new TopBarFactory(
                "Product Name:",
                new String[]{
                        "Sort by ID",
                        "Sort by Name",
                        "Sort by Price"
                }
                ,""
        );

        rightPanel.add(topBar, BorderLayout.NORTH);

        // ================= GRID =================

        rightPanel.add(productGridView, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        // ================= LOAD DATA =================

        loadAllProducts();

        setupActions();
    }

    private void setupActions() {

        // ADD BUTTON
        topBar.btnAdd.addActionListener(e ->
                Navigator.showProductForm()
        );

        // SEARCH BUTTON
        topBar.btnSearch.addActionListener(e -> {

            String keyword = topBar.txtSearch.getText().trim();

            if (keyword.isEmpty()) {
                loadAllProducts();
            } else {
                products = viewModel.searchProducts(keyword);
            }

            applySorting();
            productGridView.loadProducts(products);
        });

        // SORT COMBO
        topBar.cmbSort.addActionListener(e -> {

            applySorting();
            productGridView.loadProducts(products);
        });
    }

    private void loadAllProducts() {
        products = viewModel.getProducts();
        productGridView.loadProducts(products);
    }

    private void applySorting() {

        int index = topBar.cmbSort.getSelectedIndex();

        if (index != -1 && products != null && !products.isEmpty()) {
            products = viewModel.sortProducts(index, products);
        }
    }
}