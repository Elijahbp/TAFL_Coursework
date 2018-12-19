package bin.Items;

import bin.Enumirations.IndicesTables;
import bin.Enumirations.TypesData;

public class Numeric extends Token {

    public Numeric(Object item, TypesData typesData, int tokenIndex) {
        super(item,typesData, IndicesTables.NUMBERS, tokenIndex);;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("( ").append(this.getTypeData()).append(" ").append(this.getItem()).append(" = ")
                .append(this.getTableIndex()).append(",").append(this.getTokenIndex()).append(")");
        return stringBuilder.toString();
    }

}
