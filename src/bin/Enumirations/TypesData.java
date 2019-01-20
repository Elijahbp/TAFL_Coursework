package bin.Enumirations;

public enum TypesData {
    NON_DATA(0,"NON_DATA"),
    NON_DEFINITELY(1,"NON_DEFINITELY"),
    symbINT(2,"%"),
    symbDECIM(3,"$"),
    symbBOOL(4,"!");

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
