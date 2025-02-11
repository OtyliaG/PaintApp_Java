package com.example.paint2;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class HelloController implements Initializable {

    boolean toolSelected =false;
    GraphicsContext brushTool;

    @FXML
    private ColorPicker colorpicker;

    @FXML
    private TextField bsize;

    @FXML
    private Canvas canvas;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        brushTool = canvas.getGraphicsContext2D();
        brushTool.setFill(javafx.scene.paint.Color.WHITE); // kolor tła
        brushTool.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // biała canva

        bsize.setText("Size");
        bsize.setStyle("-fx-text-fill: grey;"); //prompt, który znika gdy klikniesz na pole tekstowe
        bsize.setOnMouseClicked(event -> {
            if (bsize.getText().equals("Size")) { //jeśli text w polu tekstowym == Size, to:
                bsize.clear();
                bsize.setStyle("-fx-text-fill: black;");
            }
        });
        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(bsize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if(toolSelected && !bsize.getText().isEmpty()) {
                brushTool.setFill(colorpicker.getValue());
                brushTool.fillRoundRect(x, y, size, size, size, size);
            }
        });
    }
    @FXML
    public void toolSelected(javafx.event.ActionEvent actionEvent) {
        toolSelected = true;
    }
    @FXML
    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                // Utwórz obraz z canvas
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);

                // Zapisz obraz jako plik PNG
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void clearCanvas() {
        // Wyczyść canvas
        brushTool.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Ustaw białe tło
        brushTool.setFill(javafx.scene.paint.Color.WHITE);
        brushTool.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    @FXML
    public void undo() {}

    @FXML
    public void redo() {}
}