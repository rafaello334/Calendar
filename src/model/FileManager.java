package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FileManager
{

	public void writeEventsToFile(ArrayList<VBox> boxList)
	{
		String fileName = "Events.dxd";
		String workingDirectory = System.getProperty("user.dir");
		String absoluteFilePath = "";
		absoluteFilePath = workingDirectory + File.separator + fileName;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(absoluteFilePath)))
		{
			for (VBox eventsBox : boxList)
			{
				for (Node node : eventsBox.getChildren())
				{
					if (node instanceof Label)
					{
						Label label = new Label();
						label = (Label) node;

						bw.write(label.getText());
						bw.newLine();
					}
				}
				bw.newLine();
				bw.newLine();
			}
			bw.flush();
			System.out.println("Saved: " + fileName);
		} catch (FileNotFoundException e)
		{
			System.err.println("File not found " + fileName);
		} catch (IOException e)
		{
			System.err.println("Error while saving data into file " + fileName);
		}
	}

}
