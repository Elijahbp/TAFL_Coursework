package bin.Tables;

import bin.Enumirations.DescriptionsStates;
import bin.Enumirations.TypesData;
import bin.Items.Index;
import bin.Items.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableIndices implements Tables<Index> {

    private ArrayList<Index> indices;

    public TableIndices(){
        indices = new ArrayList<>();
    }

    public boolean setIndex(String index){
        if (contains(index)) return true;
        if (checkOnIndex(index)){
            indices.add(new Index(index,indices.size()));
            return true;
        }
        return false;
    }

    private boolean checkOnIndex(String index){
        Pattern pattern = Pattern.compile("^[A-Za-z]+[0-9a-zA-Z]*$");
        Matcher matcher = pattern.matcher(index);
        return matcher.matches();
    }
    @Override
    public boolean contains(String index){
        for (Index index1 :indices ) {
            if (index1.getItem().equals(index)){
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Index> getListElements() {
        return indices;
    }

    @Override
    public Index getElement(String item) {
        if (!contains(item)){
            try {
                throw new Exception("Данного значения: "+item+" нет в таблице индексов");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i).getItem().equals(item)){
                return indices.get(i);
            }
        }
        return null;
    }


    //TODO переделать toString!
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Token token:indices ) {
            stringBuilder.append(token.toString()).append("\n");
        }
        return stringBuilder.toString();
    }


}
