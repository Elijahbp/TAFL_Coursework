package bin.Items;

import bin.Enumirations.IndicesTables;
import bin.Enumirations.TypesData;

public class Token {

    private Object item;
    private TypesData typesData;
    private IndicesTables tableIndex;
    private int tokenIndex;

    public Token(Object item, TypesData typesData, IndicesTables tableIndex,int tokenIndex){
        this.item = item;
        this.typesData = typesData;
        this.tableIndex = tableIndex;
        this.tokenIndex = tokenIndex;
    }
    public Token(Object item, IndicesTables tableIndex,int tokenIndex){
        this(item,TypesData.NON_DATA,tableIndex,tokenIndex);
    }

    public Object getItem() {
        return item;
    }

    public IndicesTables getTableIndex() {
        return tableIndex;
    }

    public int getTokenIndex() {
        return tokenIndex;
    }

    public TypesData getTypeData() {
        return typesData;
    }
    private boolean setTypesData(TypesData typesData){
        this.typesData = typesData;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("( ").append(item).append(" = ")
                .append(tableIndex).append(",").append(tokenIndex).append(")");
        return stringBuilder.toString();
    }

    public String getBriefInformation(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(").append(tableIndex.getIndex()).append(",").append(tokenIndex).append(")");
        return stringBuilder.toString();
    }
}
