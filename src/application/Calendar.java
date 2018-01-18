package application;

import java.util.ArrayList;

import controller.MainPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.FileManager;

public class Calendar extends Application
{
	MainPaneController mainPaneController;
	ArrayList<VBox> vboxList = new ArrayList<>();

	@Override
	public void start(Stage primaryStage)
	{
		final String appName = "Calendar";
		try
		{
			FileManager fileManager = new FileManager();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPane.fxml"));
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle(appName);
			primaryStage.show();
			primaryStage.setMinHeight(500);
			primaryStage.setMinWidth(850);
			mainPaneController = (MainPaneController) loader.getController();
			primaryStage.setOnCloseRequest((WindowEvent we) -> {
				Alert a = new Alert(Alert.AlertType.CONFIRMATION);
				a.setTitle("Ostrze¿enie");
				a.setHeaderText("Czy na pewno chcesz zakoñczyæ program?");
				a.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK)
					{
						saving(mainPaneController);
						fileManager.writeEventsToFile(vboxList);

					} else
					{
						we.consume();
					}
				});
			});

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void saving(MainPaneController mainPaneController)
	{
		for (int i = 1; i <= 4; i++)
		{
			for (int j = 1; j <= 7; j++)
			{
				vboxList.add((VBox) getNodeFromGridPane(mainPaneController.getGridPane(), j, i));
			}
		}
	}

	private Node getNodeFromGridPane(GridPane gridPane, int col, int row)
	{
		for (Node node : gridPane.getChildren())
		{
			try
			{
				if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
				{
					return node;
				}
			} catch (Exception e)
			{
				
			}
		}
		return null;
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
