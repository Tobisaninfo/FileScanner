package de.tobias.scanner.viewcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tobias.scanner.FileItem;
import de.tobias.utils.nui.NVC;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class ChartViewController extends NVC {

	@FXML private BarChart<String, Integer> barChart;

	public ChartViewController(List<FileItem> items) {
		load("de/tobias/scanner/assets/view", "chartView");
		applyViewControllerToStage();

		Map<Integer, Integer> result = map(items);

		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName("Verteilung");
		for (int key : result.keySet()) {
			series.getData().add(new Data<String, Integer>(String.valueOf(key * 100) + "-" + String.valueOf(key * 100 + 99), result.get(key)));
		}
		barChart.getData().add(series);
	}

	private Map<Integer, Integer> map(List<FileItem> input) {
		Map<Integer, Integer> result = new HashMap<>();
		for (FileItem item : input) {
			int cat = item.getLines() / 100;
			if (!result.containsKey(cat)) {
				result.put(cat, 0);
			}
			result.put(cat, result.get(cat) + 1);
		}
		return result;
	}
}
