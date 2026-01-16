package utilities;

import javax.swing.JFrame;

public class Navigator {

    private static JFrame currentView;

    private static void switchView(JFrame newView) {
        if (currentView != null) {
            currentView.dispose();
        }
        currentView = newView;
        currentView.setVisible(true);
    }

    public static void showLogin() {
        switchView(ViewFactory.createLoginView());
    }

    public static void showRegister() {
        switchView(ViewFactory.createRegisterView());
    }
}
