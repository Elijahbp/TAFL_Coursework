package bin.Enumirations;

public enum DescriptionsStates {
    NOT_DESCRIBED(0,"NOT_DESCRIBED"),
    DESCRIBED(1,"DESCRIBED");

    private int index;
    private String title;

    DescriptionsStates(int i, String described) {
        this.index = i;
        this.title = described;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }
}
