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

    public static void showProductFrom() {
        frame.showPanel(ViewFactory.createProductFormView());
    }

    public static void showDashboard() {
        frame.showPanel(ViewFactory.createDashboardView());
    }


}

