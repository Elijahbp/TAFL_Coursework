package bin.Items;

import bin.Enumirations.DescriptionsStates;
import bin.Enumirations.IndicesTables;
import bin.Enumirations.TypesData;

public class Index extends Token {
    private TypesData typeData;
    private DescriptionsStates descriptionsStates;

    public Index(Object item,  int tokenIndex) {
        this(item,DescriptionsStates.NOT_DESCRIBED,TypesData.NON_DEFINITELY,tokenIndex);
    }


    public Index(Object item,DescriptionsStates descriptionsStates, TypesData typeData, int tokenIndex) {
        super(item, IndicesTables.INDICES, tokenIndex);
        this.descriptionsStates = descriptionsStates;
        this.typeData = typeData;
    }

    public void setTypeData(TypesData typeData) {
        this.typeData = typeData;
    }

    public void setDescriptionsStates(DescriptionsStates descriptionsStates) {
        this.descriptionsStates = descriptionsStates;
    }

    public TypesData getTypeData() {
        return typeData;
    }

    public DescriptionsStates getDescriptionsStates() {
        return descriptionsStates;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("( ").append(this.getTypeData()).append(" ").append(this.getItem())
                .append(" ").append(this.getDescriptionsStates()).append(" = ").append(this.getTableIndex())
                .append(",").append(this.getTokenIndex()).append(")");
        return stringBuilder.toString();
    }
}
