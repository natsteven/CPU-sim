public class CPU {
    private Stage IF, ID, EX, MEM, WB;
    private int cycle;

    public CPU() {
        IF = new IF();
        ID = new ID();
        EX = new EX();
        MEM = new MEM();
        WB = new WB();
        cycle = 0;
    }

    public void clock(){
        cycle++;
    }

}