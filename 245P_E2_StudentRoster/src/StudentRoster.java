import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;

public class StudentRoster extends Application {
    private String currentUrl = null;
    private int currentIndex = -1;

    private Rectangle rect = new Rectangle(100, 60, Color.TRANSPARENT);
    private Text text = new Text("SR");
    private StackPane logo = new StackPane(rect, text);
    private Text title = new Text("Student Roster");
    private HBox titlePane = new HBox(20, logo, title);
    private AnchorPane anchorPaneTop = new AnchorPane(titlePane);

    private Rectangle rect1 = new Rectangle(100, 60, Color.TRANSPARENT);
    private Text text1 = new Text("SR");
    private StackPane logo1 = new StackPane(rect1, text1);
    private Text title1 = new Text("Student Roster");
    private HBox titlePane1 = new HBox(20, logo1, title1);
    private AnchorPane anchorPaneTop1 = new AnchorPane(titlePane1);

    private Tab rosterTab = new Tab("Roster");
    private Tab chartTab = new Tab("Stats");
    private TabPane modePane = new TabPane(rosterTab, chartTab);

    private MenuBar menuBar = new MenuBar();
    private TableView<StudentInfo> studentInfoTable = new TableView<>();
    private ScrollPane tablePane = new ScrollPane(studentInfoTable);
    private BorderPane centerPaneTop = new BorderPane();

    private Button newStudentButton = new Button("New Student");
    private Button deleteStudentButton = new Button("Delete Student");
    private Button saveChangeButton = new Button("Save Changes");
    private Button nextStudentButton = new Button("Next Student >>");
    private Button previousStudentButton = new Button("<< Previous Student");
    private HBox buttonBox = new HBox(4, newStudentButton, deleteStudentButton, saveChangeButton, previousStudentButton, nextStudentButton);
    private AnchorPane anchorPaneBottom = new AnchorPane(buttonBox);

    private Label idLabel = new Label("ID Number");
    private TextField idTextField = new TextField();
    private Label fnLabel = new Label("First Name");
    private TextField fnTextField = new TextField();
    private Label lnLabel = new Label("Last Name");
    private TextField lnTextField = new TextField();
    private Label majorLabel = new Label("Major");
    private TextField majorTextField = new TextField();
    private Label gradeLabel = new Label("Current Grade");
    private ChoiceBox gradeList = new ChoiceBox(FXCollections.observableArrayList("A", "B", "C", "D", "F", "Pass", "Not Pass"));
    private Label goLabel = new Label("Grade Option");
    private ToggleGroup goGroup = new ToggleGroup();
    private RadioButton lg = new RadioButton("Letter Grade");
    private RadioButton pn = new RadioButton("P/NP");
    private CheckBox statusCheckBox = new CheckBox("Honor Student");
    private TextArea noteArea = new TextArea("Notes");

    private HBox goBox = new HBox(5, lg, pn);
    private VBox labelBox = new VBox(5, idLabel, fnLabel, lnLabel, majorLabel, gradeLabel, goLabel);
    private VBox textFieldBox = new VBox(5, idTextField, fnTextField, lnTextField, majorTextField, gradeList, goBox);
    private HBox labelTextFieldBox = new HBox(5, labelBox, textFieldBox);
    private VBox bigBox = new VBox(5, labelTextFieldBox, statusCheckBox, noteArea);
    private ScrollPane scrollPane = new ScrollPane(bigBox);

    private ImageView imageView = new ImageView();
    private Button imageButton = new Button("Choose an Image...");
    private StackPane ibContainer = new StackPane(imageButton);
    private BorderPane imageBox = new BorderPane(imageView);

    private BorderPane centerPane = new BorderPane(scrollPane);

    private BorderPane root = new BorderPane();

    private StudentInfo stu1 = new StudentInfo("2019001", "Harry", "Potter", "MSWE",
            "A", "Letter Grade", "Yes", "xxx",
            "file:/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster/photos/HarryPotter.jpg");
    private StudentInfo stu2 = new StudentInfo("2019002", "Hermione", "Granger", "MSWE",
            "A", "Letter Grade", "Yes", "xxx",
            "file:/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster/photos/HermioneGranger.jpg");
    private StudentInfo stu3 = new StudentInfo("2019003", "Ron", "Weasley", "MSWE",
            "C", "Letter Grade", "No", "xxx",
            "file:/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster/photos/RonWeasley.jpg");
    private StudentInfo stu4 = new StudentInfo("2019004", "Neville", "LongBottom", "MSCS",
            "C", "Letter Grade", "No", "xxx",
            "file:/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster/photos/NevilleLongBottom.jpg");
    private StudentInfo stu5 = new StudentInfo("2019005", "Draco", "Malfoy", "MSCS",
            "A", "Letter Grade", "Yes", "xxx",
            "file:/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster/photos/DracoMalfoy.jpg");

    private ObservableList<StudentInfo> studentList = FXCollections.observableArrayList(stu1, stu2, stu3, stu4, stu5);

    private PieChart pieChart = setPieChart(studentList);
    private BarChart barChart = setBarChart(studentList);

    private VBox chartBox = new VBox(10, pieChart, barChart);
    private ScrollPane chartScrollPane = new ScrollPane(chartBox);
    private BorderPane chartPane = new BorderPane(chartScrollPane);

    private void setRosterTab() {
        rosterTab.setClosable(false);
        chartTab.setClosable(false);
        rosterTab.setContent(root);
        modePane.setSide(Side.TOP);

        rect.setStroke(Color.rgb(172, 189, 194));
        rect.setStrokeWidth(8);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        Shadow shadow = new Shadow(10, Color.BLACK);
        shadow.setHeight(21);
        shadow.setWidth(21);
        Light.Distant light = new Light.Distant();
        light.setElevation(45);
        light.setAzimuth(225);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(8);
        lighting.setSpecularConstant(0.3);
        lighting.setSpecularExponent(10);
        lighting.setDiffuseConstant(1);
        lighting.setBumpInput(shadow);
        rect.setEffect(lighting);

        text.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        text.setFill(Color.rgb(166, 191, 24));
        text.setTextAlignment(TextAlignment.CENTER);
        InnerShadow innerShadow = new InnerShadow(10, 1, 1, Color.rgb(107, 107, 107));
        innerShadow.setHeight(5);
        innerShadow.setWidth(10);
        text.setEffect(innerShadow);

        logo.setMaxSize(0, 0);
        Reflection reflection = new Reflection();
        reflection.setBottomOpacity(0);
        reflection.setTopOpacity(0.7);
        reflection.setFraction(0.5);
        logo.setEffect(reflection);

        title.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        title.setFill(Color.rgb(108, 58, 58));
        title.setTextAlignment(TextAlignment.CENTER);
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.8);
        title.setEffect(bloom);

        titlePane.setAlignment(Pos.TOP_CENTER);
        titlePane.setPadding(new Insets(5));
        titlePane.setSpacing(20);
        titlePane.setPrefWidth(600);
        titlePane.setPrefHeight(100);

        AnchorPane.setTopAnchor(titlePane, 0.);
        AnchorPane.setLeftAnchor(titlePane, 0.);
        AnchorPane.setBottomAnchor(buttonBox, 0.);
        AnchorPane.setLeftAnchor(buttonBox, 0.);

        buttonBox.setPadding(new Insets(5));
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        lg.setToggleGroup(goGroup);
        pn.setToggleGroup(goGroup);

        idLabel.setPrefHeight(30);
        lnLabel.setPrefHeight(30);
        fnLabel.setPrefHeight(30);
        majorLabel.setPrefHeight(30);
        gradeLabel.setPrefHeight(30);
        goLabel.setPrefHeight(30);

        idTextField.setPrefHeight(30);
        lnTextField.setPrefHeight(30);
        fnTextField.setPrefHeight(30);
        majorTextField.setPrefHeight(30);
        gradeList.setPrefHeight(30);
        goBox.setPrefHeight(30);
        goBox.setPadding(new Insets(5));

        noteArea.setWrapText(true);
        noteArea.setPrefSize(180, 120);
        noteArea.setPadding(new Insets(10));

        labelBox.setAlignment(Pos.CENTER_RIGHT);
        labelBox.setPadding(new Insets(10));
        textFieldBox.setAlignment(Pos.CENTER_LEFT);
        textFieldBox.setPadding(new Insets(10));

        bigBox.setAlignment(Pos.CENTER);

        studentInfoTable.setPrefSize(700, 200);
        tablePane.setPrefSize(600, 200);
        centerPaneTop.setTop(menuBar);
        centerPaneTop.setBottom(tablePane);
        imageButton.setAlignment(Pos.CENTER);
        imageBox.setPrefWidth(280);
        imageBox.setBottom(ibContainer);
        centerPane.setRight(imageBox);
        centerPane.setTop(centerPaneTop);

        root.setTop(anchorPaneTop);
        root.setBottom(anchorPaneBottom);
        root.setCenter(centerPane);
    }

    private void clearContent() {
        idTextField.setText("");
        fnTextField.setText("");
        lnTextField.setText("");
        majorTextField.setText("");
        lg.setSelected(false);
        pn.setSelected(false);
        gradeList.getSelectionModel().clearSelection();
        statusCheckBox.setSelected(false);
        noteArea.setText("Notes");
        currentUrl = null;
        imageView.setImage(null);
    }

    private void setCurrentInfo() throws IndexOutOfBoundsException {
        idTextField.setText(studentList.get(currentIndex).getId());
        fnTextField.setText(studentList.get(currentIndex).getFirstName());
        lnTextField.setText(studentList.get(currentIndex).getLastName());
        majorTextField.setText(studentList.get(currentIndex).getMajor());
        gradeList.setValue(studentList.get(currentIndex).getCurrentGrade());
        noteArea.setText(studentList.get(currentIndex).getNote());
        if (studentList.get(currentIndex).getHonorStatus().equals("Yes")) {
            statusCheckBox.setSelected(true);
        } else {
            statusCheckBox.setSelected(false);
        }
        if (studentList.get(currentIndex).getGradeOption().equals("Letter Grade")) {
            lg.setSelected(true);
        } else {
            pn.setSelected(true);
        }
        currentUrl = studentList.get(currentIndex).getPhotoSrc();
        imageView.setImage(new Image(currentUrl, 240, 320, true, true));
    }

    private boolean checkLegalChange() {
        boolean isLegalChange = true;
        if (idTextField.getText().equals("")) {
            isLegalChange = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Must assign an ID number!");
            alert.showAndWait();
        }
        else {
            if (lg.isSelected()) {
                if (gradeList.getValue().equals("Pass") || gradeList.getValue().equals("Not Pass")) {
                    isLegalChange = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Grade and grade option are not matched!");
                    alert.showAndWait();
                }
            } else if (pn.isSelected()) {
                if (!(gradeList.getValue().equals("Pass") || gradeList.getValue().equals("Not Pass"))) {
                    isLegalChange = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Grade and grade option are not matched!");
                    alert.showAndWait();
                }
            }
            if (!(majorTextField.getText().equals("MSWE") || majorTextField.getText().equals("MSCS"))) {
                isLegalChange = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("This major does not exist!");
                alert.showAndWait();
            }
        }
        return isLegalChange;
    }

    private void setListItemInfo(StudentInfo student) {
        student.setFirstName(fnTextField.getText());
        student.setLastName(lnTextField.getText());
        student.setMajor(majorTextField.getText());
        if (lg.isSelected()) {
            student.setGradeOption("Letter Grade");
        } else {
            student.setGradeOption("P/NP");
        }
        student.setCurrentGrade(gradeList.getValue().toString());
        if (statusCheckBox.isSelected()) {
            student.setHonorStatus("Yes");
        } else {
            student.setHonorStatus("No");
        }
        student.setNote(noteArea.getText());
        student.setPhotoSrc(currentUrl);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Change saved!");
        alert.showAndWait();
    }

    private boolean save() {
        boolean succeed = checkLegalChange();
        boolean exist = false;
        int existIndex = -1;
        for (int i=0; i<studentList.size(); i++) {
            if (studentList.get(i).getId().equals(idTextField.getText())) {
                exist = true;
                existIndex = i;
            }
        }
        if (exist) {
            if (succeed) {
                setListItemInfo(studentList.get(existIndex));
                chartBox.getChildren().clear();
                chartBox.getChildren().addAll(setPieChart(studentList), setBarChart(studentList));
            }
        } else {
            if (succeed) {
                String gradeOption;
                String honorStudentStatus;
                if (lg.isSelected()) {
                    gradeOption = "Letter Grade";
                } else {
                    gradeOption = "P/NP";
                }
                if (statusCheckBox.isSelected()) {
                    honorStudentStatus = "Yes";
                } else {
                    honorStudentStatus = "No";
                }
                StudentInfo newStudent = new StudentInfo(
                        idTextField.getText(),
                        fnTextField.getText(),
                        lnTextField.getText(),
                        majorTextField.getText(),
                        gradeList.getValue().toString(),
                        gradeOption,
                        honorStudentStatus,
                        noteArea.getText(),
                        currentUrl
                );
                studentList.add(newStudent);
                currentIndex = studentList.size()-1;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("New student added!");
                alert.show();

                chartBox.getChildren().clear();
                chartBox.getChildren().addAll(setPieChart(studentList), setBarChart(studentList));
            }
        }
        return succeed;
    }

    private void setButtonHandler(Stage stage) {
        imageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster/photos"));
                fileChooser.setTitle("Choose an Image...");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("GIF", "*.gif"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                        new FileChooser.ExtensionFilter("PNG", "*.png"),
                        new FileChooser.ExtensionFilter("All Images", "*.*")
                );
                File file = fileChooser.showOpenDialog(stage);

                try {
                    currentUrl = file.toURI().toURL().toString();
                    Image image = new Image(currentUrl, 240, 320, true, true);
                    imageView.setImage(image);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        newStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearContent();
            }
        });

        deleteStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                studentList.remove(currentIndex);
                chartBox.getChildren().clear();
                chartBox.getChildren().addAll(setPieChart(studentList), setBarChart(studentList));

                try {
                    setCurrentInfo();
                } catch (IndexOutOfBoundsException ex) {
                    currentIndex = -1;
                    clearContent();
                }
            }
        });

        saveChangeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                save();
            }
        });

        previousStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentIndex > 0) {
                    currentIndex--;
                    setCurrentInfo();
                }
            }
        });

        nextStudentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentIndex < studentList.size()-1) {
                    currentIndex++;
                    setCurrentInfo();
                }
            }
        });
    }

    private void createMenu(Stage stage) {
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("Create a New File");
        MenuItem openMenuItem = new MenuItem("Open an Existing File");
        MenuItem saveMenuItem = new MenuItem("Save The File");
        MenuItem saveAsMenuItem = new MenuItem("Save The File As");
        MenuItem closeMenuItem = new MenuItem("Close The File");
        MenuItem exitMenuItem = new MenuItem("Exit The Application");

        newMenuItem.setOnAction(actionEvent -> Platform.runLater(() -> {
            if (currentIndex >= 0 && currentIndex < studentList.size()) {
                String fileName = idTextField.getText() + "_" + fnTextField.getText() + "_" + lnTextField.getText() + ".txt";
                File fout = new File(fileName);
                boolean find = false;
                StudentInfo student = null;

                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout)));

                    for (StudentInfo studentInfo : studentList) {
                        if (idTextField.getText().equals(studentInfo.getId())) {
                            find = true;
                            student = studentInfo;
                        }
                    }
                    if (find) {
                        bufferedWriter.write(student.getId());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getFirstName());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getLastName());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getMajor());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getCurrentGrade());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getGradeOption());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getHonorStatus());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getNote());
                        bufferedWriter.newLine();
                        bufferedWriter.write(student.getPhotoSrc());
                        bufferedWriter.flush();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("File Created!");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("File Creation Failed!");
                        alert.showAndWait();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));

        openMenuItem.setOnAction(actionEvent -> Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster"));
            fileChooser.setTitle("Choose Text File...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            File file = fileChooser.showOpenDialog(stage);
            int index;

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                idTextField.setText(bufferedReader.readLine());
                for (int i=0; i<studentList.size(); i++) {
                    if (studentList.get(i).getId().equals(idTextField.getText())) {
                        index = i;
                        currentIndex = index;
                        break;
                    }
                }
                fnTextField.setText(bufferedReader.readLine());
                lnTextField.setText(bufferedReader.readLine());
                majorTextField.setText(bufferedReader.readLine());
                gradeList.setValue(bufferedReader.readLine());
                String gradeOption = bufferedReader.readLine();
                if (gradeOption.equals("Letter Grade")) {
                    lg.setSelected(true);
                    pn.setSelected(false);
                } else {
                    lg.setSelected(false);
                    pn.setSelected(true);
                }
                String status = bufferedReader.readLine();
                if (status.equals("Yes")) statusCheckBox.setSelected(true);
                else statusCheckBox.setSelected(false);
                noteArea.setText(bufferedReader.readLine());
                currentUrl = bufferedReader.readLine();
                Image image = new Image(currentUrl, 240, 320, true, true);
                imageView.setImage(image);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }));

        saveMenuItem.setOnAction(actionEvent -> Platform.runLater(() -> {
            if (save()) {
                if (currentIndex >= 0 && currentIndex < studentList.size()) {
                    String fileName = idTextField.getText() + "_" + fnTextField.getText() + "_" + lnTextField.getText() + ".txt";
                    File fout = new File(fileName);
                    boolean existBefore = fout.delete();
                    boolean find = false;
                    StudentInfo student = null;

                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout)));

                        for (StudentInfo studentInfo : studentList) {
                            if (idTextField.getText().equals(studentInfo.getId())) {
                                find = true;
                                student = studentInfo;
                            }
                        }
                        if (find) {
                            bufferedWriter.write(student.getId());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getFirstName());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getLastName());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getMajor());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getCurrentGrade());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getGradeOption());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getHonorStatus());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getNote());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getPhotoSrc());
                            bufferedWriter.flush();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            if (existBefore) alert.setContentText("File Saved!");
                            else alert.setContentText("File Created!");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            if (existBefore) alert.setContentText("File Save Failed!");
                            else alert.setContentText("File Creation Failed!");
                            alert.showAndWait();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }));

        saveAsMenuItem.setOnAction(actionEvent -> Platform.runLater(() -> {
            if (save()) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("/Users/oscarli/IdeaProjects/Fall2019/245P_E2_StudentRoster"));
                fileChooser.setTitle("Save Text File As");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("TXT", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*")
                );
                boolean find = false;
                StudentInfo student = null;

                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

                        for (StudentInfo studentInfo : studentList) {
                            if (idTextField.getText().equals(studentInfo.getId())) {
                                find = true;
                                student = studentInfo;
                            }
                        }
                        if (find) {
                            bufferedWriter.write(student.getId());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getFirstName());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getLastName());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getMajor());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getCurrentGrade());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getGradeOption());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getHonorStatus());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getNote());
                            bufferedWriter.newLine();
                            bufferedWriter.write(student.getPhotoSrc());
                            bufferedWriter.flush();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("File Created!");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("File Creation Failed!");
                            alert.showAndWait();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }));

        closeMenuItem.setOnAction(actionEvent -> Platform.runLater(() -> {
            stage.close();
        }));

        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, closeMenuItem, exitMenuItem);
        menuBar.getMenus().add(fileMenu);
    }

    private void setTableView(Stage stage) {
        studentInfoTable.setEditable(true);
        studentInfoTable.setItems(studentList);

        TableColumn<StudentInfo, String> colId = new TableColumn<>("Student ID");
        colId.setCellFactory(TextFieldTableCell.forTableColumn());
        colId.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setId(event.getNewValue());
            idTextField.setText(event.getNewValue());
        });
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<StudentInfo, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        colFirstName.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setFirstName(event.getNewValue());
            fnTextField.setText(event.getNewValue());
        });
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<StudentInfo, String> colLastName = new TableColumn<>("Last Name");
        colLastName.setCellFactory(TextFieldTableCell.forTableColumn());
        colLastName.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setLastName(event.getNewValue());
            lnTextField.setText(event.getNewValue());
        });
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        ImageView thumbnailView = new ImageView();
        TableColumn<StudentInfo, String> colPhoto = new TableColumn<>("Photo");
        colPhoto.setCellFactory((column) -> {
            TableCell<StudentInfo, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        ImageView edit = new ImageView(this.getItem());
                        edit.setFitWidth(50);
                        edit.setPreserveRatio(true);
                        Button editBtn = new Button("Edit", edit);
                        this.setGraphic(editBtn);
                        editBtn.setOnMouseClicked((me) -> {
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Choose Image");
                            fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))
                            );
                            fileChooser.getExtensionFilters().addAll(
                                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                                    new FileChooser.ExtensionFilter("GIF", "*.gif"),
                                    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                                    new FileChooser.ExtensionFilter("All Images", "*.*")
                            );
                            File newPic = fileChooser.showOpenDialog(stage);
                            try {
                                String url = newPic.toURI().toURL().toString();
                                currentUrl = url;
                                this.getTableView().getItems().get(this.getIndex()).setPhotoSrc(url);
                                Image image=new Image(url, 30, 40, true, true);
                                edit.setImage(image);
                                thumbnailView.setImage(image);
                            }
                            catch(Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                }
            };
            return cell;
        });
        colPhoto.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setPhotoSrc(event.getNewValue());
        });
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photoSrc"));

        TableColumn<StudentInfo, String> colMajor = new TableColumn<>("Major");
        colMajor.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList("MSWE", "MSCS")));
        colMajor.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            if (!(event.getNewValue().equals("MSWE") || event.getNewValue().equals("MSCS"))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("This major does not exist!");
                alert.showAndWait();
            } else {
                event.getRowValue().setMajor(event.getNewValue());
                majorTextField.setText(event.getNewValue());
                chartBox.getChildren().clear();
                chartBox.getChildren().addAll(setPieChart(studentList), setBarChart(studentList));
            }
        });
        colMajor.setCellValueFactory(new PropertyValueFactory<>("major"));

        TableColumn<StudentInfo, String> colGrade = new TableColumn<>("Current Grade");
        colGrade.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList("A", "B", "C", "D", "F", "Pass", "Not Pass")));
        colGrade.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setCurrentGrade(event.getNewValue());
            gradeList.setValue(event.getNewValue());
            chartBox.getChildren().clear();
            chartBox.getChildren().addAll(setPieChart(studentList), setBarChart(studentList));
        });
        colGrade.setCellValueFactory(new PropertyValueFactory<>("currentGrade"));

        TableColumn<StudentInfo, String> colGradeOption = new TableColumn<>("Grade Option");
        colGradeOption.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList("Letter Grade", "P/NP")));
        colGradeOption.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setGradeOption(event.getNewValue());
            if (event.getNewValue().equals("Letter Grade")) {
                lg.setSelected(true);
                pn.setSelected(false);
            } else {
                lg.setSelected(false);
                pn.setSelected(true);
            }
        });
        colGradeOption.setCellValueFactory(new PropertyValueFactory<>("gradeOption"));

        TableColumn<StudentInfo, String> colStatus = new TableColumn<>("Honor Student");
        colStatus.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList("Yes", "No")));
        colStatus.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setHonorStatus(event.getNewValue());
            if (event.getNewValue().equals("Yes")) {
                statusCheckBox.setSelected(true);
            } else {
                statusCheckBox.setSelected(false);
            }
        });
        colStatus.setCellValueFactory(new PropertyValueFactory<>("honorStatus"));

        TableColumn<StudentInfo, String> colNotes = new TableColumn<>("Notes");
        colNotes.setCellFactory(TextFieldTableCell.forTableColumn());
        colNotes.setOnEditCommit((TableColumn.CellEditEvent<StudentInfo, String> event) -> {
            event.getRowValue().setNote(event.getNewValue());
            noteArea.setText(event.getNewValue());
        });
        colNotes.setCellValueFactory(new PropertyValueFactory<>("note"));

        studentInfoTable.getColumns().addAll(colId, colFirstName, colLastName, colPhoto, colMajor, colGrade, colGradeOption, colStatus, colNotes);

        studentInfoTable.setRowFactory(outEvent -> {
            TableRow<StudentInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    currentIndex = row.getIndex();
                    setCurrentInfo();
                }
            });
            return row;
        });
    }

    private PieChart setPieChart(ObservableList<StudentInfo> list) {
        int MSWECount = 0;
        int MSCSCount = 0;
        for (StudentInfo info : list) {
            if (info.getMajor().equals("MSWE")) MSWECount++;
            else MSCSCount++;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        double percentageMSWE = (double) MSWECount/list.size()*100;
        double percentageMSCS = (double) MSCSCount/list.size()*100;
        String vMSWE = df.format(percentageMSWE);
        String vMSCS = df.format(percentageMSCS);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("MSWE: " + MSWECount + " student(s), " + vMSWE + "%", MSWECount),
                new PieChart.Data("MSCS: " + MSCSCount + " student(s), " + vMSCS + "%", MSCSCount)
        );
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setLegendSide(Side.LEFT);
        pieChart.setTitle("Major Stat");
        return pieChart;
    }

    private BarChart setBarChart(ObservableList<StudentInfo> list) {
        int ACount = 0;
        int BCount = 0;
        int CCount = 0;
        int DCount = 0;
        int FCount = 0;
        for (StudentInfo info : list) {
            switch (info.getCurrentGrade()) {
                case "A": {
                    ACount++;
                    break;
                }
                case "B": {
                    BCount++;
                    break;
                }
                case "C": {
                    CCount++;
                    break;
                }
                case "D": {
                    DCount++;
                    break;
                }
                case "F": {
                    FCount++;
                    break;
                }
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(
                new XYChart.Data<>("A", ACount),
                new XYChart.Data<>("B", BCount),
                new XYChart.Data<>("C", CCount),
                new XYChart.Data<>("D", DCount),
                new XYChart.Data<>("F", FCount)
        );

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Grade");
        yAxis.setLabel("Count");
        barChart.getData().add(series);
        barChart.setTitle("Grade Stat");
        barChart.setLegendVisible(false);
        return barChart;
    }

    private void setChartTab() {
        rect1.setStroke(Color.rgb(172, 189, 194));
        rect1.setStrokeWidth(8);
        rect1.setArcWidth(10);
        rect1.setArcHeight(10);
        Shadow shadow = new Shadow(10, Color.BLACK);
        shadow.setHeight(21);
        shadow.setWidth(21);
        Light.Distant light = new Light.Distant();
        light.setElevation(45);
        light.setAzimuth(225);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(8);
        lighting.setSpecularConstant(0.3);
        lighting.setSpecularExponent(10);
        lighting.setDiffuseConstant(1);
        lighting.setBumpInput(shadow);
        rect1.setEffect(lighting);

        text1.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        text1.setFill(Color.rgb(166, 191, 24));
        text1.setTextAlignment(TextAlignment.CENTER);
        InnerShadow innerShadow = new InnerShadow(10, 1, 1, Color.rgb(107, 107, 107));
        innerShadow.setHeight(5);
        innerShadow.setWidth(10);
        text1.setEffect(innerShadow);

        logo1.setMaxSize(0, 0);
        Reflection reflection = new Reflection();
        reflection.setBottomOpacity(0);
        reflection.setTopOpacity(0.7);
        reflection.setFraction(0.5);
        logo1.setEffect(reflection);

        title1.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        title1.setFill(Color.rgb(108, 58, 58));
        title1.setTextAlignment(TextAlignment.CENTER);
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.8);
        title1.setEffect(bloom);

        titlePane1.setAlignment(Pos.TOP_CENTER);
        titlePane1.setPadding(new Insets(5));
        titlePane1.setSpacing(20);
        titlePane1.setPrefWidth(600);
        titlePane1.setPrefHeight(100);

        chartBox.setAlignment(Pos.CENTER);
        chartPane.setTop(anchorPaneTop1);
        chartTab.setContent(chartPane);
        AnchorPane.setTopAnchor(anchorPaneTop1, 0.);
    }

    @Override
    public void start(Stage stage) {
        setRosterTab();
        setButtonHandler(stage);
        setTableView(stage);
        createMenu(stage);
        setChartTab();

        Scene scene = new Scene(modePane, 600, 780);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}