package de.tobias.scanner.viewcontroller;

import java.util.List;

import de.tobias.scanner.FileItem;
import de.tobias.scanner.FileScannerMain;
import de.tobias.scanner.Type;
import de.tobias.utils.nui.NVC;
import de.tobias.utils.nui.NVCStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PieChartViewController extends NVC {

	@FXML private PieChart chart;

	public PieChartViewController(List<FileItem> items, Window owner) {
		load("de/tobias/scanner/assets/view", "pieChartView");
		NVCStage nvcStage = applyViewControllerToStage();
		nvcStage.initOwner(owner);

		FileScannerMain.stageIcon.ifPresent(nvcStage::setImage);

		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

		long classCount = items.stream().filter(i -> i.getType() == Type.CLASS).count();
		long interfaceCount = items.stream().filter(i -> i.getType() == Type.INTERFACE).count();
		long enumCount = items.stream().filter(i -> i.getType() == Type.ENUM).count();

		data.add(new Data("Class", classCount));
		data.add(new Data("Interface", interfaceCount));
		data.add(new Data("Enum", enumCount));

		chart.setData(data);
	}

	@Override
	public void initStage(Stage stage) {
		stage.setTitle("File Scanner - Verteilung");
	}
}
