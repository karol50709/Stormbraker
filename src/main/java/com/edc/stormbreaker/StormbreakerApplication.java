package com.edc.stormbreaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
public class StormbreakerApplication extends Application{

	private double xOffset = 0;
	private double yOffset = 0;

	private static Stage stage;

	private static final boolean cmdparser=true;

	CmdLineParser p = new CmdLineParser(this);

	@Option(name = "-file", required = true, usage="Required")
	String file;
	@Option(name = "-delimiter", required = true, usage="Required")
	String delimiter;
	@Option(name = "-eof", required = true, usage="Required")
	String endofline;
	@Option(name = "-charset", required = true, usage="Required")
	String charset;


	public StormbreakerApplication (){

	}

	public static void main(String[] args)  {

		//{
		System.setProperty("illegal-access", "deny");
		SpringApplication.run(StormbreakerApplication.class, args);
		StormbreakerApplication stormbreakerApplication= new StormbreakerApplication();


		if(args.length==0){
			Application.launch(args);

		}
		else if (args[0].equals("-help")) {

			System.exit(0);
		}
		else {
			if (cmdparser) {
				try {
					stormbreakerApplication.p.parseArgument(args);
					Logger.getLogger(StormbreakerApplication.class.getName()).log(Level.INFO,stormbreakerApplication.p.getClass().getName()
					+" Wczytano parametrów: "+stormbreakerApplication.p.getOptions().size());
						if(Utils.validfile(stormbreakerApplication.file)){
					if (stormbreakerApplication.file.toLowerCase().endsWith("csv") || stormbreakerApplication.file.toLowerCase().endsWith("txt")) {
						CSVReading csvReading = new CSVReading();
						csvReading.saveNonGui(stormbreakerApplication.file,
								stormbreakerApplication.delimiter,
								stormbreakerApplication.endofline,
								stormbreakerApplication.charset);


					} else {
						ExcelReading excelReading = new ExcelReading();
						excelReading.saveNonGui(stormbreakerApplication.file,
								stormbreakerApplication.delimiter,
								stormbreakerApplication.endofline,
								stormbreakerApplication.charset);
					}
				}
				}
				catch (CmdLineException e){
					e.printStackTrace();
					System.exit(0);
				}
				finally {
					Logger.getLogger(StormbreakerApplication.class.getName()).log(Level.INFO,"koniec działania");
					System.exit(0);
				}



			} else {

				for (int i = 0; i < args.length; i++) {
					System.out.println(args[i]);
				}

				if (Utils.validarguments(args)) {


					String lowerfilename = args[1];
					if (lowerfilename.endsWith("csv") || lowerfilename.endsWith("txt")) {
						CSVReading csvReading = new CSVReading();
						csvReading.saveNonGui(args[1], args[7], args[3], args[5]);
					} else {
						ExcelReading excelReading = new ExcelReading();
						excelReading.saveNonGui(args[1], args[7], args[3], args[5]);
					}
				}


				System.exit(0);
			}
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		stage=primaryStage;

		Parent root = FXMLLoader.load(getClass().getResource("/core.fxml"));
		primaryStage.setTitle("Stormbreaker v1.1");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(new Scene(root, 610, 280));
		//primaryStage.setMaximized(true);
		primaryStage.show();
		primaryStage.setResizable(false);




	}

	public static Stage getStage(){
		return stage;
	}
}
