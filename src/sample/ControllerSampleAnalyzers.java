package sample;

import bin.Tables.TotalInformation;
import bin.analyzers.LexicalAnalyzer;
import bin.rules.Rules;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSampleAnalyzers implements Initializable{


    public Button btnAnalyzer;
    public TextArea txtAreaServiceWords;
    public TextArea txtAreaLimiters;
    public TextArea txtAreaNumbers;
    public TextArea txtAreaIdentificators;
    public TextArea txtAreaResultAnalysis;
    public TextArea txtAreaFieldCode;
    public TextArea txtAreaResultListLexem;


    StringBuilder stringBuilder;

    private TotalInformation totalInformation;

    public ControllerSampleAnalyzers(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalInformation = new TotalInformation();
        txtAreaServiceWords.setText(totalInformation.getTableServiceWords().toString());
        txtAreaLimiters.setText(totalInformation.getTableLimiters().toString());

    }

    public void analyzeProgram(ActionEvent actionEvent) {
      //  ArrayList<> = LexicalAnalyzer.scanner(txtFieldCode.getText());
        txtAreaIdentificators.clear();
        txtAreaNumbers.clear();
        txtAreaResultAnalysis.clear();
        txtAreaResultListLexem.clear();
        stringBuilder = new StringBuilder();
        try {
            totalInformation = new Rules().scanner(LexicalAnalyzer.scanner(txtAreaFieldCode.getText()));
            txtAreaNumbers.setText(totalInformation.getTableNumbers().toString());
            txtAreaIdentificators.setText(totalInformation.getTableIndices().toString());

            totalInformation.getTokenList().forEach(token -> {
                stringBuilder.append(token.getBriefInformation()).append(" ");
            });
            txtAreaResultListLexem.setText(stringBuilder.toString());
            txtAreaResultAnalysis.setText("Проверка прошла успешно!");
        } catch (Exception e) {
            txtAreaResultAnalysis.setText(e.getMessage());
        }
    }



}
