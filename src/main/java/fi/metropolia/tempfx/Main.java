package fi.metropolia.tempfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.List;

/**
 * JavaFX front end for the Temperature Converter.
 * Enter a value, pick a unit, convert to Celsius and save it to the database;
 * the table shows every saved record. Demonstrates GUI + Images + Database.
 */
public class Main extends Application {

    private final TemperatureUnitDAO unitDAO = new TemperatureUnitDAO();
    private final TempRecordDAO recordDAO = new TempRecordDAO();
    private final TempCalculator calculator = new TempCalculator();

    private final ComboBox<TemperatureUnit> unitBox = new ComboBox<>();
    private final TextField valueField = new TextField();
    private final Label resultLabel = new Label();
    private final TableView<TempRecord> table = new TableView<>();
    private final ObservableList<TempRecord> records = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // --- Header with an image (GUI + Images requirement) ---
        ImageView logo = new ImageView();
        try (InputStream is = getClass().getResourceAsStream("/images/logo.png")) {
            if (is != null) {
                logo.setImage(new Image(is));
                logo.setFitHeight(64);
                logo.setPreserveRatio(true);
            }
        } catch (Exception ignored) {
            // the image is optional; the app still works without it
        }
        Label title = new Label("Temperature Converter (JavaFX + MariaDB)");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        HBox header = new HBox(12, logo, title);
        header.setAlignment(Pos.CENTER_LEFT);

        // --- Input form ---
        valueField.setPromptText("Enter a temperature value");
        unitBox.setPromptText("Unit");
        Button convertBtn = new Button("Convert & Save");
        convertBtn.setOnAction(e -> onConvertAndSave());
        HBox form = new HBox(10, valueField, unitBox, convertBtn);
        form.setAlignment(Pos.CENTER_LEFT);

        resultLabel.setStyle("-fx-font-size: 14px;");

        // --- Table of saved records ---
        TableColumn<TempRecord, Number> cValue = new TableColumn<>("Input");
        cValue.setCellValueFactory(new PropertyValueFactory<>("inputValue"));
        TableColumn<TempRecord, String> cUnit = new TableColumn<>("Unit");
        cUnit.setCellValueFactory(new PropertyValueFactory<>("unitCode"));
        TableColumn<TempRecord, Number> cCelsius = new TableColumn<>("Celsius");
        cCelsius.setCellValueFactory(new PropertyValueFactory<>("celsiusValue"));
        TableColumn<TempRecord, String> cWhen = new TableColumn<>("Saved at");
        cWhen.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        table.getColumns().add(cValue);
        table.getColumns().add(cUnit);
        table.getColumns().add(cCelsius);
        table.getColumns().add(cWhen);
        table.setItems(records);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox root = new VBox(14, header, new Separator(), form, resultLabel,
                new Label("Saved records:"), table);
        root.setPadding(new Insets(16));
        VBox.setVgrow(table, Priority.ALWAYS);

        loadUnits();
        loadRecords();

        stage.setScene(new Scene(root, 660, 500));
        stage.setTitle("In-class 6 - Temperature FX - Jose Noel");
        stage.show();
    }

    private void loadUnits() {
        List<TemperatureUnit> units = unitDAO.getAll();
        unitBox.setItems(FXCollections.observableArrayList(units));
        if (!units.isEmpty()) {
            unitBox.getSelectionModel().selectFirst();
        } else {
            showWarning("No units loaded. Is the MariaDB 'tempfx' database running and seeded?");
        }
    }

    private void loadRecords() {
        records.setAll(recordDAO.getAll());
    }

    private void onConvertAndSave() {
        TemperatureUnit unit = unitBox.getSelectionModel().getSelectedItem();
        if (unit == null) {
            showWarning("Please select a unit.");
            return;
        }
        double value;
        try {
            value = Double.parseDouble(valueField.getText().trim());
        } catch (NumberFormatException ex) {
            showWarning("Please enter a valid number.");
            return;
        }

        double celsius = calculator.toCelsius(value, unit.getCode());
        resultLabel.setText(String.format("%.2f %s = %.2f C", value, unit.getCode(), celsius));

        int id = recordDAO.create(new TempRecord(value, unit.getId(), unit.getCode(), celsius));
        if (id > 0) {
            loadRecords();
        } else {
            showWarning("Converted, but the record could not be saved to the database.");
        }
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
