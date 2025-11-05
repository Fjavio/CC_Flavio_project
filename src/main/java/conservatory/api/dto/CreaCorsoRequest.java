package conservatory.api.dto;

public class CreaCorsoRequest {
    private String code;
    private String name;
    private int cfu;
    private String preOf;
    private String preFor;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCfu() { return cfu; }
    public void setCfu(int cfu) { this.cfu = cfu; }
    public String getPreOf() { return preOf; }
    public void setPreOf(String preOf) { this.preOf = preOf; }
    public String getPreFor() { return preFor; }
    public void setPreFor(String preFor) { this.preFor = preFor; }
}