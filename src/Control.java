public class Control {
    int programCounter;
    int accumulator;
    int exRegister;
    int memRegister;
    boolean alu;
    boolean memAcc;
    boolean loadOrStore;
    boolean direct; // uggh this is probably a bad way to do it
    boolean halt;
    boolean jump;

    public Control() {
        programCounter = 0;
        accumulator = 0;
        exRegister = 0;
        memRegister = 0;
        alu = false;
        memAcc = false;
        loadOrStore = false;
        direct = false;
        halt = false;
        jump = false;
    }

    public void reset() {
        programCounter = 0;
        accumulator = 0;
        exRegister = 0;
        memRegister = 0;
        alu = false;
        memAcc = false;
        loadOrStore = false;
        direct = false;
        halt = false;
        jump = false;
    }

    public void print() {
        System.out.println("Control: " +
                "PC: " + programCounter + ", " +
                "ACC: " + accumulator + ", " +
                "EX: " + exRegister + ", " +
                "MEM: " + memRegister + ", " +
                "ALU: " + alu + ", " +
                "MEMACC: " + memAcc + ", " +
                "LOAD/STORE: " + loadOrStore + ", " +
                "DIRECT: " + direct + ", " +
                "HALT: " + halt + ", " +
                "JUMP: " + jump);
    }
}