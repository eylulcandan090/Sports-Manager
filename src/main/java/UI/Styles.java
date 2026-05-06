package UI;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Styles {

    // ===== BACKGROUND =====
    public static String rootBg() {
        return "-fx-background-color: #0f172a;";
    }

    // ===== PANEL =====
    public static String panelStyle() {
        return panelStyle("#1e293b");
    }

    public static String panelStyle(String color) {
        return "-fx-background-color: " + color + ";" +
                "-fx-padding: 15;" +
                "-fx-background-radius: 10;";
    }

    // ===== BUTTONS =====
    public static void styleButton(Button btn) {
        btn.setStyle(
                "-fx-background-color: #3b82f6;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;"
        );
    }

    public static void styleDangerButton(Button btn) {
        btn.setStyle(
                "-fx-background-color: #ef4444;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;"
        );
    }

    public static void styleGreenButton(Button btn) {
        btn.setStyle(
                "-fx-background-color: #22c55e;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;"
        );
    }

    public static void styleMenuButton(Button btn) {
        btn.setStyle(
                "-fx-background-color: #334155;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-background-radius: 6;"
        );
    }

    // ===== LABELS =====
    public static final String TITLE_LABEL =
            "-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;";

    public static final String SUBTITLE_LABEL =
            "-fx-text-fill: #94a3b8; -fx-font-size: 16px;";

    public static final String TOPBAR_LABEL =
            "-fx-text-fill: #cbd5f5; -fx-font-size: 14px;";

    public static final String TEAM_NAME_LABEL =
            "-fx-text-fill: #facc15; -fx-font-size: 18px; -fx-font-weight: bold;";

    // ===== LAYOUT =====
    public static final String TOPBAR =
            "-fx-background-color: #1e293b; -fx-padding: 10;";

    public static final String SIDEBAR =
            "-fx-background-color: #020617; -fx-padding: 10;";

    // ===== ANIMATION =====
    public static void applyFadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(400), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
}