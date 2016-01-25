package test.company.com;

import javax.validation.constraints.NotNull;

public class NotNullClass {


    public void doNothing(String aValue) {
    }


    public String getString(String aValue) {
        return aValue;
    }


    public @NotNull
    String getNullString(String aValue) {
        return aValue;
    }


    public @NotNull String doNothingSingleNotNull(@NotNull String aValue, Integer foo) {
        return aValue;
    }


    @NotNull
    public void trickyMethod() {
    }


    public void doNothingEndNotNull(String aStart,String aValue, @NotNull String aEnd) {
    }
}