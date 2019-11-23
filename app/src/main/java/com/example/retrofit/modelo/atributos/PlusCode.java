package com.example.retrofit.modelo.atributos;

public class PlusCode {
    private String compoundCode;
    private String globalCode;

    /**
     * No args constructor for use in serialization
     *
     */
    public PlusCode() {
    }

    /**
     *
     * @param globalCode
     * @param compoundCode
     */
    public PlusCode(String compoundCode, String globalCode) {
        super();
        this.compoundCode = compoundCode;
        this.globalCode = globalCode;
    }

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

}
