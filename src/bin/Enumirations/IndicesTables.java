package bin.Enumirations;

public enum IndicesTables {
    SERVICE_WORD(1,"SERVICE_WORD"),
    LIMITERS(2,"LIMITERS"),
    NUMBERS(3,"NUMBERS"),
    INDICES(4,"INDICES");

    private int index;
    private String titleTables;
    IndicesTables(int index,String titleTables){
        this.index = index;
        this.titleTables = titleTables;
    }
    public int getIndex(){
        return index;
    }

    public String getTitleTables() {
        return titleTables;
    }
}
