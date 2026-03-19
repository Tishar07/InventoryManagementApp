package utilities;

import view.components.MainFrame;
public class Navigator {

    private static MainFrame frame;
    public static void init(MainFrame mainFrame) {
        frame = mainFrame;
    }

    public static void showLogin() {
        frame.showPanel(ViewFactory.createLoginView());
    }

    public static void showRegister() {
        frame.showPanel(ViewFactory.createRegisterView());
    }

    public static void showProduct() {
        frame.showPanel(ViewFactory.createProductView());
    }

    public static void showProductForm() {
        frame.showPanel(ViewFactory.createProductFormView());
    }

    public static void showProductFormEditView(int ProductID) {
        frame.showPanel(ViewFactory.createProductFormViewEdit(ProductID));
    }

    public static void showDashboard() {
        frame.showPanel(ViewFactory.createDashboardView());
    }

    public static void showRetailer() {
        frame.showPanel(ViewFactory.createRetailerView());
    }

    public static void showRetailerForm(){
        frame.showPanel(ViewFactory.createRetailerViewForm());
    }

    public static void showRetailerFormViewEdit(int RetailerID){
        frame.showPanel(ViewFactory.createRetailerViewFormEditView(RetailerID));
    }

    public static void showSupplier() {
        frame.showPanel(ViewFactory.createSupplierView());
    }

    public static void showSupplierForm(){
        frame.showPanel(ViewFactory.createSupplierViewForm());
    }

    public static void showSupplierFormViewEdit(int SupplierID){
        frame.showPanel(ViewFactory.createSupplierViewFormEditView(SupplierID));
    }

    public static void showHistory() {
        frame.showPanel(ViewFactory.createHistoryView());
    }

    public static void showStockOut() {
        frame.showPanel(ViewFactory.createStockOutView());
    }

    public static void showStockOutForm() {
        frame.showPanel(ViewFactory.createStockOutFormView());
    }

    public static void showStockOutFormViewEdit(int TransactionID) {
        frame.showPanel(ViewFactory.createStockOutFormViewEdit(TransactionID));
    }

    public static void showUserFormEdit() {
        frame.showPanel(ViewFactory.createUserFormEditView(Session.getUsername()));
    }

}

