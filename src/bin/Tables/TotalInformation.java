package bin.Tables;

import bin.Enumirations.IndicesTables;
import bin.Items.Token;

import java.util.ArrayList;
import java.util.List;

public class TotalInformation {
    private TableServiceWords tableServiceWords;
    private TableLimiters tableLimiters;
    private TableNumbers tableNumbers;
    private TableIndices tableIndices;
    private ArrayList<Token> tokenList;

    public TotalInformation(){
        tableServiceWords = new TableServiceWords();
        tableLimiters = new TableLimiters();
        tableNumbers = new TableNumbers();
        tableIndices = new TableIndices();
    }

    public TableServiceWords getTableServiceWords() {
        return tableServiceWords;
    }

    public TableLimiters getTableLimiters() {
        return tableLimiters;
    }

    public TableNumbers getTableNumbers() {
        return tableNumbers;
    }

    public TableIndices getTableIndices() {
        return tableIndices;
    }

    public ArrayList<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
