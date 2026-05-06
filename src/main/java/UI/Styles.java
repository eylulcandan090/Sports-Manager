package UI;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.util.Duration;

public class Styles {

    // ── Colors ────────────────────────────────────────────────────────────────
    public static final String BG_DARK   = "#020c1b";
    public static final String BG_PANEL  = "#0d1f35";
    public static final String BG_SIDEBAR = "#010d1f";
    public static final String BG_TOPBAR = "#010810";
    public static final String BLUE      = "#1565c0";
    public static final String BLUE_LIGHT = "#90caf9";
    public static final String PINK      = "#f48fb1";
    public static final String YELLOW    = "#ffd54f";
    public static final String WHITE     = "#e3f2fd";

    // ── Inline style strings ──────────────────────────────────────────────────
    public static final String TITLE_LABEL =
        "-fx-text-fill: " + PINK + "; -fx-font-size: 38px; -fx-font-weight: bold;" +
        "-fx-effect: dropshadow(gaussian, rgba(244,143,177,0.7), 18, 0, 0, 0);";

    public static final String SUBTITLE_LABEL =
        "-fx-text-fill: " + BLUE_LIGHT + "; -fx-font-size: 14px;";

    public static final String TOPBAR =
        "-fx-background-color: " + BG_TOPBAR + "; -fx-border-color: " + BLUE + "; -fx-border-width: 0 0 1 0;";

    public static final String TEAM_NAME_LABEL =
        "-fx-text-fill: " + YELLOW + "; -fx-font-size: 15px; -fx-font-weight: bold;" +
        "-fx-effect: dropshadow(gaussian, rgba(255,213,79,0.4), 6, 0, 0, 0);";

    public static final String TOPBAR_LABEL =
        "-fx-text-fill: " + WHITE + "; -fx-font-size: 14px; -fx-font-weight: bold;";

    public static final String SIDEBAR =
        "-fx-background-color: " + BG_SIDEBAR + "; -fx-border-color: " + BLUE + "; -fx-border-width: 0 1 0 0;";

    public static final String DEFAULT_LABEL =
        "-fx-text-fill: " + WHITE + ";";

    // ── Button style strings ──────────────────────────────────────────────────
    private static final String BTN_NORMAL =
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1976d2, #1565c0);" +
        "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;" +
        "-fx-padding: 10 24; -fx-background-radius: 25; -fx-cursor: hand;";

    private static final String BTN_HOVER =
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #42a5f5, #1976d2);" +
        "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;" +
        "-fx-padding: 10 24; -fx-background-radius: 25; -fx-cursor: hand;" +
        "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.7), 14, 0, 0, 4);";

    private static final String BTN_DANGER_NORMAL =
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #ad1457, #880e4f);" +
        "-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-cursor: hand;";

    private static final String BTN_DANGER_HOVER =
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #e91e63, #ad1457);" +
        "-fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-cursor: hand;" +
        "-fx-effect: dropshadow(gaussian, rgba(233,30,99,0.5), 10, 0, 0, 3);";

    private static final String BTN_MENU_NORMAL =
        "-fx-background-color: transparent; -fx-text-fill: " + BLUE_LIGHT + "; -fx-font-size: 13px;" +
        "-fx-alignment: CENTER-LEFT; -fx-padding: 10 16; -fx-background-radius: 20;" +
        "-fx-pref-width: 145px; -fx-cursor: hand;";

    private static final String BTN_MENU_HOVER =
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #0d2b5e, #1a3a6e);" +
        "-fx-text-fill: " + PINK + "; -fx-font-size: 13px;" +
        "-fx-alignment: CENTER-LEFT; -fx-padding: 10 16; -fx-background-radius: 20;" +
        "-fx-pref-width: 145px; -fx-cursor: hand;";

    // ── Public helper methods ─────────────────────────────────────────────────

    /** Default blue gradient button with hover effect. */
    public static void styleButton(Button btn) {
        btn.setStyle(BTN_NORMAL);
        btn.setOnMouseEntered(e -> btn.setStyle(BTN_HOVER));
        btn.setOnMouseExited(e  -> btn.setStyle(BTN_NORMAL));
    }

    /** Red/pink danger button (e.g. Back / Quit). */
    public static void styleDangerButton(Button btn) {
        btn.setStyle(BTN_DANGER_NORMAL);
        btn.setOnMouseEntered(e -> btn.setStyle(BTN_DANGER_HOVER));
        btn.setOnMouseExited(e  -> btn.setStyle(BTN_DANGER_NORMAL));
    }

    /** Sidebar menu button with transparent background + pink hover. */
    public static void styleMenuButton(Button btn) {
        btn.setStyle(BTN_MENU_NORMAL);
        btn.setOnMouseEntered(e -> btn.setStyle(BTN_MENU_HOVER));
        btn.setOnMouseExited(e  -> btn.setStyle(BTN_MENU_NORMAL));
    }

    /** Generic dark label. */
    public static void styleLabel(Label lbl) {
        lbl.setStyle(DEFAULT_LABEL);
    }

    /** Dark themed ListView. */
    public static <T> void styleListView(ListView<T> lv) {
        lv.setStyle(
            "-fx-background-color: " + BG_PANEL + "; -fx-border-color: " + BLUE + ";" +
            "-fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;"
        );
    }

    /** Dark themed TableView. */
    public static <T> void styleTableView(TableView<T> tv) {
        tv.setStyle(
            "-fx-background-color: " + BG_PANEL + "; -fx-border-color: " + BLUE + ";" +
            "-fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;"
        );
    }

    /** Root pane background. */
    public static String rootBg() {
        return "-fx-background-color: " + BG_DARK + "; -fx-font-family: 'Segoe UI', Arial, sans-serif;";
    }

    // ── New helpers ───────────────────────────────────────────────────────────

    /** Rounded dark panel with a coloured border. */
    public static String panelStyle(String borderColor) {
        return "-fx-background-color: #0a1628; -fx-border-color: " + borderColor + ";" +
               "-fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";
    }

    // Green "ready" button — Start Match / positive action
    private static final String BTN_GREEN_NORMAL =
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #00897b, #00695c);" +
        "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
        "-fx-padding: 11 28; -fx-background-radius: 22; -fx-cursor: hand;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,200,150,0.4), 12, 0, 0, 3);";

    private static final String BTN_GREEN_HOVER =
        "-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #26a69a, #00897b);" +
        "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
        "-fx-padding: 11 28; -fx-background-radius: 22; -fx-cursor: hand;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,200,150,0.7), 18, 0, 0, 4);";

    // Ghost/outline button — secondary match actions
    private static final String BTN_ACTION_NORMAL =
        "-fx-background-color: transparent;" +
        "-fx-text-fill: #90caf9; -fx-font-size: 13px; -fx-font-weight: bold;" +
        "-fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;" +
        "-fx-border-color: #1565c0; -fx-border-width: 1.5; -fx-border-radius: 8;";

    private static final String BTN_ACTION_HOVER =
        "-fx-background-color: #1565c033;" +
        "-fx-text-fill: #e3f2fd; -fx-font-size: 13px; -fx-font-weight: bold;" +
        "-fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;" +
        "-fx-border-color: #42a5f5; -fx-border-width: 1.5; -fx-border-radius: 8;" +
        "-fx-effect: dropshadow(gaussian, rgba(66,165,245,0.4), 8, 0, 0, 2);";

    // Disabled look for finished-match buttons
    private static final String BTN_DISABLED =
        "-fx-background-color: #1a2535; -fx-text-fill: #3a4a5a; -fx-font-size: 13px;" +
        "-fx-padding: 10 20; -fx-background-radius: 8;" +
        "-fx-border-color: #1a2535; -fx-border-width: 1.5; -fx-border-radius: 8;";

    /** Green action button (Start Match, Back to Menu after win). */
    public static void styleGreenButton(Button btn) {
        btn.setStyle(BTN_GREEN_NORMAL);
        btn.setOnMouseEntered(e -> { if (!btn.isDisabled()) btn.setStyle(BTN_GREEN_HOVER); });
        btn.setOnMouseExited(e  -> { if (!btn.isDisabled()) btn.setStyle(BTN_GREEN_NORMAL); });
    }

    /** Outlined ghost button for secondary match actions. */
    public static void styleActionButton(Button btn) {
        btn.setStyle(BTN_ACTION_NORMAL);
        btn.setOnMouseEntered(e -> { if (!btn.isDisabled()) btn.setStyle(BTN_ACTION_HOVER); });
        btn.setOnMouseExited(e  -> { if (!btn.isDisabled()) btn.setStyle(BTN_ACTION_NORMAL); });
        btn.disabledProperty().addListener((obs, was, now) ->
            btn.setStyle(now ? BTN_DISABLED : BTN_ACTION_NORMAL));
    }

    /** Fade-in animation — call once after building the scene graph. */
    public static void applyFadeIn(Node node) {
        node.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(380), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
}
