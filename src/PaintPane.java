import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import my.model.AFigure;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaintPane extends BorderPane {
    private Map<String, Class<? extends AFigure>> classes;
    private ObservableList<String> toolsArr;
    private Stage stage;
    //private FileChooser fileChooser;

    public PaintPane(Stage stage) {
        this.stage = stage;
        Reflections reflections = new Reflections("my.model", new SubTypesScanner(false));
        classes = reflections.getSubTypesOf(AFigure.class).stream().collect(Collectors.toMap(c -> c.getSimpleName().replaceAll("(?<=\\p{Ll})(?=\\p{Lu})", " "), c -> c));
        toolsArr = classes.keySet().stream().filter(clazz -> !Modifier.isAbstract(classes.get(clazz).getModifiers())).sorted().collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        ComboBox<String> tools = new ComboBox<>(toolsArr);
        tools.setMinWidth(90);
        tools.setValue("Point");
        ComboBox<Integer> sideNumber = new ComboBox<>(FXCollections.observableArrayList(0));
        sideNumber.setMinWidth(90);
        sideNumber.setValue(0);
        ColorPicker cpLine = new ColorPicker(Color.BLACK);
        cpLine.setMinWidth(90);
        ColorPicker cpFill = new ColorPicker(Color.WHITE);
        cpFill.setMinWidth(90);
        CheckBox filled = new CheckBox("Filled");
        filled.setMinWidth(90);
        filled.setSelected(true);
        Slider slider = new Slider(1, 50, 1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMinWidth(90);

        Label figure = new Label("Figure");
        Label sides = new Label("Sides");
        Label lineColor = new Label("Line Color");
        Label fillColor = new Label("Fill Color");
        Label lineWidth = new Label("1.0");

        /*Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button save = new Button("Save");
        Button open = new Button("Open");*/

       /* Button[] basicArr = {undo, redo, save, open};

        for (Button btn : basicArr) {
            btn.setMinWidth(90);
            btn.setCursor(Cursor.HAND);
            btn.setTextFill(Color.WHITE);
            btn.setStyle("-fx-background-color: #666;");
        }
        save.setStyle("-fx-background-color: #170d80;");
        open.setStyle("-fx-background-color: #170d80;");*/

        VBox buttons = new VBox(10);
        buttons.getChildren().addAll(figure, tools, sides, sideNumber, filled);
        buttons.getChildren().addAll(lineColor, cpLine, fillColor, cpFill, lineWidth, slider/*, undo, redo, open, save*/);
        buttons.setPadding(new Insets(5));
        buttons.setStyle("-fx-background-color: #999");
        buttons.setPrefWidth(100);

        PaintArea area = new PaintArea(1500, 800, classes.get("Point"), Color.BLACK, Color.WHITE, 1, true, 0);
        tools.valueProperty().addListener((observable, oldValue, newValue) -> {
            Class<? extends AFigure> c = classes.get(newValue);
            area.setSelectedItem(c);
            try {
                Field field = c.getField("OPTIONS");
                Integer[] options = (Integer[]) field.get(new Integer[]{});
                sideNumber.setItems(FXCollections.observableArrayList(options));
                sideNumber.setValue(options[0]);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        });
        sideNumber.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                area.setSideNumber(newValue);
        });
        filled.selectedProperty().addListener((observable, oldValue, newValue) -> area.setFilled(newValue));
        // color picker
        cpLine.valueProperty().addListener((observable, oldValue, newValue) -> area.setBorderColor(newValue));
        cpFill.valueProperty().addListener((observable, oldValue, newValue) -> area.setFillColor(newValue));

        // slider
        slider.valueProperty().addListener(e -> {
            double width = slider.getValue();
            lineWidth.setText(String.format("%.1f", width));
            area.setBorderWidth(width);
        });


        /*------- Save & Open ------*//*
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        // Open
        open.setOnAction((e) -> {
            fileChooser.setTitle("Open File");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    area.setWidth(img.getWidth());
                    area.setHeight(img.getHeight());
                    //gc.drawImage(img, 0, 0);
                } catch (IOException ex) {
                    dialog(Alert.AlertType.ERROR, "Unable to open file");
                }
            }
        });

        // Save
        save.setOnAction((e) -> {
            fileChooser.setTitle("Save File");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage((int) Math.ceil(area.getWidth()),
                            (int) Math.ceil(area.getHeight()));
                    SnapshotParameters sp = new SnapshotParameters();
                    sp.setFill(Color.TRANSPARENT);
                    //area.tempCanvas.snapshot(sp, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    dialog(Alert.AlertType.ERROR, "Unable to save file");
                }
            }

        });*/
        setPrefSize(1200, 600);
        setLeft(buttons);
        setCenter(new ScrollPane(area));
    }

    private Optional<ButtonType> dialog(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("MyAwesomePaint");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().addAll(new Image(getClass().getResource("Icon.png").toExternalForm()));
        alert.setHeaderText(message);
        alert.getButtonTypes().setAll(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        return alert.showAndWait();
    }
}
