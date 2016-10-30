package de.tobias.scanner;

import java.util.Optional;

import de.tobias.scanner.viewcontroller.MainViewController;
import de.tobias.utils.application.App;
import de.tobias.utils.application.ApplicationUtils;
import de.tobias.utils.nui.NVCStage;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FileScannerMain extends Application {

	private static final String iconPath = "icon.png";
	public static Optional<Image> stageIcon = Optional.empty();

	public static void main(String[] args) throws Exception {
		App app = ApplicationUtils.registerMainApplication(FileScannerMain.class);
		app.start(args);
	}

	@Override
	public void init() throws Exception {
		try {
			Image stageIcon = new Image(iconPath);
			FileScannerMain.stageIcon = Optional.of(stageIcon);
		} catch (Exception e) {}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainViewController controller = new MainViewController(primaryStage);
		controller.getStageContainer().ifPresent(NVCStage::show);
	}
}
