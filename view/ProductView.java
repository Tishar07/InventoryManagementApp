package view;

import model.Product;
import utilities.TopBarFactory;
import view.components.SideMenuBar;

import javax.swing.*;
import java.awt.*;
import java.util.List;
// BEERBUL TO MET TO ProductFormViewModel la mo fini drece naming convection dan ProductGrid
public class ProductView extends JFrame {

    private final ProductViewModel viewModel;

    private JPanel MainPanel = new JPanel(new BorderLayout());
    private ProductGridView productGridView = new ProductGridView();
    private TopBarFactory topBar;

    public ProductView(ProductViewModel viewModel) {
        this.viewModel = viewModel;
        initComponents();
    }

    private void initComponents() {

        setTitle("PRODUCT");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
                        "Sort by Category",
                        "Sort by Price",
                        "Sort by Quantity"
                }
        );

        MainPanel.add(topBar, BorderLayout.NORTH);
        MainPanel.add(productGridView, BorderLayout.CENTER);
        add(MainPanel);

        loadProducts(viewModel.getProducts());
        setupActions();
    }

    private void loadProducts(List<Product> products) {
        productGridView.loadProducts(products);
    }

    private void setupActions() {
        //Bour to ggt viewmodel so ban functions ici la ein

        topBar.btnAdd.addActionListener(e -> {
            viewModel.addProduct();
            loadProducts(viewModel.getProducts());
        });

        topBar.btnUpdate.addActionListener(e -> {
            viewModel.updateProduct();
            loadProducts(viewModel.getProducts());
        });

        topBar.btnDelete.addActionListener(e -> {
            viewModel.deleteProduct();
            loadProducts(viewModel.getProducts());
        });

        topBar.btnSearch.addActionListener(e -> {
            String keyword = topBar.txtSearch.getText();
            String sortBy  = (String) topBar.cmbSort.getSelectedItem();
            loadProducts(viewModel.searchAndSortProducts(keyword, sortBy));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new ProductView(new ProductViewModel()).setVisible(true)
        );
    }
}
