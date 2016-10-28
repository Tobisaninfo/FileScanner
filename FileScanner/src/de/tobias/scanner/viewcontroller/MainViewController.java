package de.tobias.scanner.viewcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import de.tobias.scanner.FileItem;
import de.tobias.scanner.Scanner;
import de.tobias.scanner.Type;
import de.tobias.utils.nui.NVC;
import de.tobias.utils.nui.NVCStage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainViewController extends NVC {

	@FXML private TextField pathField;
	@FXML private Button chooseButton;

	@FXML private Label lineLabel;
	@FXML private Label classLabel;
	@FXML private Label interfaceLabel;

	@FXML private TableView<FileItem> tableView;
	@FXML private TableColumn<FileItem, Path> pathColumn;
	@FXML private TableColumn<FileItem, Type> typeColumn;
	@FXML private TableColumn<FileItem, Integer> lineColumn;

	@FXML private Button diagramButton;

	private List<FileItem> items;

	public MainViewController(Stage stage) {
		load("de/tobias/scanner/assets/view", "MainView");
		applyViewControllerToStage(stage);
	}

	@Override
	public void init() {
		pathColumn.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPath().getFileName()));
		typeColumn.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getType()));
		lineColumn.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getLines()));
	}

	private void updateItems() {
		tableView.getItems().setAll(items);
		int lineCount = items.stream().mapToInt(FileItem::getLines).sum();
		long classCount = items.stream().filter(i -> i.getType() == Type.CLASS).count();
		long interfaceCount = items.stream().filter(i -> i.getType() == Type.INTERFACE).count();

		lineLabel.setText(String.valueOf(lineCount));
		classLabel.setText(String.valueOf(classCount));
		interfaceLabel.setText(String.valueOf(interfaceCount));
	}

	@FXML
	private void chooseHandler(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		File folder = chooser.showDialog(getContainingWindow());
		if (folder != null) {
			try {
				pathField.setText(folder.toString());
				items = Scanner.scan(folder.toPath(), entry -> entry.toString().endsWith("java") || Files.isDirectory(entry));
				updateItems();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void diagramHandler(ActionEvent event) {
		ChartViewController controller = new ChartViewController(items);
		controller.getStageContainer().ifPresent(NVCStage::show);
	}
}
