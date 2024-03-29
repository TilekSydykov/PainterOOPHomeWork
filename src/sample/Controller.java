package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final int UNDO_SIZE = 10;
    private Double lastX = -1.0, lastY = -1.0;
    private ArrayList<Line> lines = new ArrayList<>();
    private final Color[] penColors = {
            Color.BLACK,
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.BLUE
    };
    private Color currentPenColor = penColors[0];
    private final int[] penSizes = {1, 3, 5};
    private int currentPenSize = penSizes[0];

    @FXML
    private Pane mainPane;

    @FXML
    private ToggleGroup toggleGroupColor;

    @FXML
    private ToggleGroup toggleGroupPen;

    @FXML
    private RadioButton rbColorBlack;

    @FXML
    private RadioButton rbColorWhite;

    @FXML
    private RadioButton rbColorRed;

    @FXML
    private RadioButton rbColorGreen;

    @FXML
    private RadioButton rbColorBlue;

    @FXML
    private RadioButton rbSizeSmall;

    @FXML
    private RadioButton rbSizeMedium;

    @FXML
    private RadioButton rbSizeLarge;

    @FXML
    void onColorSelected(ActionEvent event) {
        currentPenColor = (Color) toggleGroupColor.getSelectedToggle().getUserData();
    }

    @FXML
    void onMouseDragged(MouseEvent event) {
        drawLine(event.getX(), event.getY());
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        lastX = event.getX();
        lastY = event.getY();
    }

    @FXML
    void onPenSelected(ActionEvent event) {
        currentPenSize = (int) toggleGroupPen.getSelectedToggle().getUserData();
    }

    @FXML
    void onUndo(ActionEvent event) {
        if (lines.size() != 0) {
            List<Line> undoLines = new ArrayList<>();
            for (int i = lines.size() - 1; i >= 0 && lines.size() - i < UNDO_SIZE; i--) {
                undoLines.add(lines.get(i));
            }
            mainPane.getChildren().removeAll(undoLines);
            lines.removeAll(undoLines);
        }
    }

    @FXML
    void onClear(ActionEvent event) {
        mainPane.getChildren().removeAll(lines);
    }

    @FXML
    void initialize() {
        rbColorBlack.setUserData(penColors[0]);
        rbColorWhite.setUserData(penColors[1]);
        rbColorRed.setUserData(penColors[2]);
        rbColorGreen.setUserData(penColors[3]);
        rbColorBlue.setUserData(penColors[4]);

        rbSizeSmall.setUserData(penSizes[0]);
        rbSizeMedium.setUserData(penSizes[1]);
        rbSizeLarge.setUserData(penSizes[2]);
    }

    private void drawLine(double x, double y) {
        if (x < 0) {
            lastX = x;
            return;
        }
        if(lastX < 0){
            lastX = x;
            lastY = y;
        }

        Line line = new Line(lastX, lastY, x, y);
        line.setStrokeWidth(currentPenSize);
        line.setStroke(currentPenColor);
        mainPane.getChildren().add(line);
        lines.add(line);

        lastX = x;
        lastY = y;
    }
}