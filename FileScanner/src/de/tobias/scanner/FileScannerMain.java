package de.tobias.scanner;

import de.tobias.scanner.viewcontroller.MainViewController;
import de.tobias.utils.application.App;
import de.tobias.utils.application.ApplicationUtils;
import de.tobias.utils.nui.NVCStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class FileScannerMain extends Application {

	public static void main(String[] args) throws Exception {
		App app = ApplicationUtils.registerMainApplication(FileScannerMain.class);
		app.start(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainViewController controller = new MainViewController(primaryStage);
		controller.getStageContainer().ifPresent(NVCStage::show);
	}
}
