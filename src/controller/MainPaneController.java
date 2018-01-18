package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import model.FileManager;

public class MainPaneController implements Initializable
{

	@FXML
	private GridPane gridPane;

	@FXML
	private Label leftWeekNumber4;

	@FXML
	private Label leftWeekNumber3;

	@FXML
	private Label leftWeekNumber2;

	@FXML
	private Label leftWeekNumber1;

	@FXML
	private Label rightWeekNumber1;

	@FXML
	private Label rightWeekNumber2;

	@FXML
	private Label rightWeekNumber3;

	@FXML
	private Label rightWeekNumber4;

	ArrayList<VBox> vboxList;
	MainPaneController mainPaneController;
	FileManager fileManager;

	public GridPane getGridPane()
	{
		return gridPane;
	}

	public void setGridPane(GridPane gridPane)
	{
		this.gridPane = gridPane;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		mainPaneController = this;
		fileManager = new FileManager();
		Calendar calendar = Calendar.getInstance();
		int dayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		// Dodawanie przycisków
		for (int i = 1; i <= 4; i++)
		{
			for (int j = 1; j <= 7; j++)
			{
				List<String> eventsList = new ArrayList<>();
				List<Label> labelList = new ArrayList<>();
				String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK);
				String data = String.valueOf(day) + " " + month;
				VBox mainVbox = new VBox();
				mainVbox.setSpacing(1);
				mainVbox.getStyleClass().add("main-vbox");
				gridPane.add(mainVbox, j, i);
				mainVbox.getChildren().add(addLabel(data, eventsList, labelList, mainVbox));

				if (day >= dayInMonth)
				{
					day = 1;
					calendar.add(Calendar.MONTH, 1);
				} else
				{
					day++;
				}
			}
		}

		configureLabels(week, year);
	}

	private Label addLabel(String data, List<String> eventsList, List<Label> labelList, VBox mainVbox)
	{
		Label label = new Label();

		// Pozycjonowanie i stylowanie

		label.getStyleClass().add("label-day");
		label.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHalignment(label, HPos.CENTER);
		GridPane.setValignment(label, VPos.TOP);

		// Ustawianie daty
		label.setText(data);

		label.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{

				String newEvent = "Add new event";
				Stage newEventStage = new Stage();

				try
				{
					VBox eventVbox = new VBox();
					eventVbox.setAlignment(Pos.CENTER);
					eventVbox.setSpacing(10);

					TextField eventHourTextField = new TextField();
					eventHourTextField.setPromptText("Time");
					eventHourTextField.setMaxWidth(250);
					eventHourTextField.setMaxHeight(20);

					TextField eventNameTextField = new TextField();
					eventNameTextField.setPromptText("Event Name");
					eventNameTextField.setMaxWidth(250);
					eventNameTextField.setMaxHeight(20);

					Button addEventButton = new Button("Add");
					addEventButton.setPrefWidth(80);
					addEventButton.setPrefHeight(60);

					eventVbox.getChildren().add(new Label("Event Time"));
					eventVbox.getChildren().add(eventHourTextField);
					eventVbox.getChildren().add(new Label("Event Name"));
					eventVbox.getChildren().add(eventNameTextField);
					eventVbox.getChildren().add(addEventButton);

					Scene scene = new Scene(eventVbox);
					newEventStage.setScene(scene);
					newEventStage.setTitle(newEvent);
					newEventStage.setHeight(300);
					newEventStage.setWidth(450);
					newEventStage.setResizable(false);
					newEventStage.show();

					addEventButton.setOnAction(new EventHandler<ActionEvent>()
					{
						@Override
						public void handle(ActionEvent event)
						{
							boolean isFormOk = false;

							if (eventHourTextField.getText().equals("") || eventNameTextField.getText().equals(""))
							{
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning Dialog");
								alert.setContentText("Cannot add the event, because forms are filled incorrect");
								alert.showAndWait();
							} else
							{
								isFormOk = true;
							}
							if (isFormOk)
							{
								Event newEvent = new Event(eventHourTextField.getText(), eventNameTextField.getText(),
										data);
								eventsList.add(newEvent.toString());
								Collections.sort(eventsList);

								for (Label label : labelList)
								{
									mainVbox.getChildren().remove(label);
								}

								labelList.clear();

								for (String labelText : eventsList)
								{
									Label eventLabel = new Label();
									eventLabel.setMaxWidth(Double.MAX_VALUE);
									eventLabel.getStyleClass().add("event-label");
									eventLabel.setText(labelText);

									eventLabel.setOnMouseClicked(new EventHandler<MouseEvent>()
									{

										@Override
										public void handle(MouseEvent event)
										{
											String newEvent = "Modify or Delete Event";
											Stage newEventStage = new Stage();
											String[] eventData = eventLabel.getText().split("  -  ");

											if (eventLabel.getText().equals(""))
											{
												try
												{
													VBox eventVbox = new VBox();
													eventVbox.setAlignment(Pos.CENTER);
													eventVbox.setSpacing(10);

													TextField eventHourTextField = new TextField();
													eventHourTextField.setPromptText("Time");
													eventHourTextField.setMaxWidth(250);
													eventHourTextField.setMaxHeight(20);

													TextField eventNameTextField = new TextField();
													eventNameTextField.setPromptText("Event Name");
													eventNameTextField.setMaxWidth(250);
													eventNameTextField.setMaxHeight(20);

													Button addEventButton = new Button("Add");
													addEventButton.setPrefWidth(80);
													addEventButton.setPrefHeight(60);

													eventVbox.getChildren().add(new Label("Event Time"));
													eventVbox.getChildren().add(eventHourTextField);
													eventVbox.getChildren().add(new Label("Event Name"));
													eventVbox.getChildren().add(eventNameTextField);
													eventVbox.getChildren().add(addEventButton);

													Scene scene = new Scene(eventVbox);
													newEventStage.setScene(scene);
													newEventStage.setTitle(newEvent);
													newEventStage.setHeight(300);
													newEventStage.setWidth(450);
													newEventStage.setResizable(false);
													newEventStage.show();
													addEventButton.setOnAction(new EventHandler<ActionEvent>()
													{

														@Override
														public void handle(ActionEvent event)
														{
															boolean isChangeOk = false;

															if (eventHourTextField.getText().equals("")
																	|| eventNameTextField.getText().equals(""))
															{
																Alert alert = new Alert(AlertType.WARNING);
																alert.setTitle("Warning Dialog");
																alert.setContentText(
																		"Cannot add this event, because forms are filled incorrect");
																alert.showAndWait();
															} else
															{
																isChangeOk = true;
															}

															if (isChangeOk)
															{

																eventLabel.setText(eventHourTextField.getText()
																		+ "  -  " + eventNameTextField.getText());
																newEventStage.close();

																Collections.sort(eventsList);
																saving(mainPaneController);
																fileManager.writeEventsToFile(vboxList);

															}
														}
													});

												} catch (Exception e)
												{
													e.printStackTrace();
												}

											} else
											{
												try
												{
													VBox eventVbox = new VBox();
													eventVbox.setAlignment(Pos.CENTER);
													eventVbox.setSpacing(10);

													TextField eventHourTextField = new TextField();
													eventHourTextField.setText(eventData[0]);
													eventHourTextField.setMaxWidth(250);
													eventHourTextField.setMaxHeight(20);

													TextField eventNameTextField = new TextField();
													eventNameTextField.setText(eventData[1]);
													eventNameTextField.setMaxWidth(250);
													eventNameTextField.setMaxHeight(20);

													Button changeEventButton = new Button("Change");
													changeEventButton.setPrefWidth(80);
													changeEventButton.setPrefHeight(60);

													Button deleteEventButton = new Button("Delete");
													deleteEventButton.setPrefWidth(80);
													deleteEventButton.setPrefHeight(60);

													eventVbox.getChildren().add(new Label("Event Time"));
													eventVbox.getChildren().add(eventHourTextField);
													eventVbox.getChildren().add(new Label("Event Name"));
													eventVbox.getChildren().add(eventNameTextField);
													eventVbox.getChildren().add(changeEventButton);
													eventVbox.getChildren().add(new Label(""));
													eventVbox.getChildren().add(deleteEventButton);

													Scene scene = new Scene(eventVbox);
													newEventStage.setScene(scene);
													newEventStage.setTitle(newEvent);
													newEventStage.setHeight(300);
													newEventStage.setWidth(450);
													newEventStage.setResizable(false);
													newEventStage.show();
													changeEventButton.setOnAction(new EventHandler<ActionEvent>()
													{

														@Override
														public void handle(ActionEvent event)
														{
															boolean isChangeOk = false;

															if (eventHourTextField.getText().equals("")
																	|| eventNameTextField.getText().equals(""))
															{
																Alert alert = new Alert(AlertType.WARNING);
																alert.setTitle("Warning Dialog");
																alert.setContentText(
																		"Cannot change this event, because forms are filled incorrect");
																alert.showAndWait();
															} else
															{
																isChangeOk = true;
															}

															if (isChangeOk)
															{
																labelList.remove(eventLabel);
																eventLabel.setText(eventHourTextField.getText()
																		+ "  -  " + eventNameTextField.getText());

																labelList.add(eventLabel);

																Collections.sort(eventsList);
																newEventStage.close();
																saving(mainPaneController);
																fileManager.writeEventsToFile(vboxList);
															}
														}
													});

													deleteEventButton.setOnAction(new EventHandler<ActionEvent>()
													{

														@Override
														public void handle(ActionEvent event)
														{
															eventsList.remove(newEvent);
															eventLabel.setText("");
															newEventStage.close();
															saving(mainPaneController);
															fileManager.writeEventsToFile(vboxList);
														}
													});

												} catch (Exception e)
												{

												}
											}
										}
									});

									labelList.add(eventLabel);

								}

								for (Label label : labelList)
								{
									mainVbox.getChildren().add(label);
								}

								saving(mainPaneController);
								fileManager.writeEventsToFile(vboxList);
								newEventStage.close();
							}
						}
					});

				} catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});

		return label;
	}

	private void configureLabels(int week, int year)
	{

		leftWeekNumber1.setText("W" + week + "\n" + year);
		leftWeekNumber2.setText("W" + (week + 1) + "\n" + year);
		leftWeekNumber3.setText("W" + (week + 2) + "\n" + year);
		leftWeekNumber4.setText("W" + (week + 3) + "\n" + year);

		rightWeekNumber1.setText("W" + week + "\n" + year);
		rightWeekNumber2.setText("W" + (week + 1) + "\n" + year);
		rightWeekNumber3.setText("W" + (week + 2) + "\n" + year);
		rightWeekNumber4.setText("W" + (week + 3) + "\n" + year);

	}

	private void saving(MainPaneController mainPaneController)
	{
		vboxList = new ArrayList<>();
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
}
