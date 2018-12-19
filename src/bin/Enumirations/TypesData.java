package bin.Enumirations;

public enum TypesData {
    NON_DATA(0,"NON_DATA"),
    NON_DEFINITELY(1,"NON_DEFINITELY"),
    INTEGER(2,"INTEGER"),
    REAL(3,"REAL"),
    BOOLEAN(4,"BOOLEAN");

    private int index;
    private String nameType;
    TypesData(int index,String nameType){
        this.index = index;
        this.nameType = nameType;
    }
    public int getIndex() {
        return index;
    }
    public String getNameType(){
        return nameType;
    }
}
