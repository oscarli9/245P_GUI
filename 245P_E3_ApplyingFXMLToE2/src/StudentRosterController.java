import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class StudentRosterController implements Initializable {
    private String currentUrl = null;
    private int currentIndex = -1;

    private Stage stage;

    @FXML
    private Button newStudentButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private Button saveChangeButton;
    @FXML
    private Button previousStudentButton;
    @FXML
    private Button nextStudentButton;
    @FXML
    private Button imageButton;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField fnTextField;
    @FXML
    private TextField lnTextField;
    @FXML
    private TextField majorTextField;
    @FXML
    private ChoiceBox gradeList;
    @FXML
    private CheckBox statusCheckBox;
    @FXML
    private TextArea noteArea;

    @FXML
    private ToggleGroup goGroup;
    @FXML
    private RadioButton lg;
    @FXML
    private RadioButton pn;

    @FXML
    private ImageView imageView;

    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private TableView<StudentInfo> studentInfoTable;

    @FXML
    private VBox chartBox;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart barChart;

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

    void setStage(Stage stage) {
        this.stage = stage;
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
        alert.show();
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
            fileChooser.setInitialDirectory(new File("/Users/oscarli/IdeaProjects/Fall2019/245P_E3_ApplyingFXMLToE2"));
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
                fileChooser.setInitialDirectory(new File("/Users/oscarli/IdeaProjects/Fall2019/245P_E3_ApplyingFXMLToE2"));
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
        pieChart.setLegendVisible(false);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gradeList.getItems().addAll(FXCollections.observableArrayList("A", "B", "C", "D", "F", "Pass", "Not Pass"));
        chartBox.getChildren().clear();
        chartBox.getChildren().addAll(setPieChart(studentList), setBarChart(studentList));
        setButtonHandler(stage);
        setTableView(stage);
        createMenu(stage);
    }
}
