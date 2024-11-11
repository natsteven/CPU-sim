public class Memory {

    private final int[] memory;
    private final int memorySize;
    private int programCounter;
    private int accumulator;
    private int exRegister;
    private int memRegister;
    private boolean alu;
    private boolean memAcc;
    private boolean loadOrStore;
    private boolean direct; // uggh this is probably a bad way to do it
    private boolean halt;
    private boolean jump;

    public Memory(int size) {
        memory = new int[size];
        memorySize = size;
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

    /**
     * loads 12 bit value into memory array at address
     * @param address   address to load into
     * @param value     value to load
     * 
     */
    public void load(int address, int value) throws Exception {
        if((address > memorySize - 1) || (address < 0)) {
            throw new Exception("Attempt to load into memory outside of bounds at address: " + address);
        }
        memory[address] = value;
    }

    /**
     * loads array of 12 bit values into memory at startAddress
     * @param startAddress  address to start loading into
     * @param size          size of section to load
     * @param values        array of 12 bit values
     */
    public void loadSection(int startAddress, int size, int[] values) throws Exception {
        for (int i=0; i < size; i++) {
            load(startAddress + i, values[i]);
        }
    }

    /**
     * reads single memory address
     * @param address
     * @return
     * @throws Exception
     */
    public int readMemory(int address) throws Exception {
        if(isOutOfBounds(address, 1)) {
            throw new Exception("Attempt to read memory outside of bounds at address: " + address);
        }
        return memory[address];
    }

    /**
     * 
     * @param address
     * @param length
     * @throws Exception
     */
    public int[] readMemory(int address, int length) throws Exception {
        if(isOutOfBounds(address, length)) {
            throw new Exception("Attempt to read memory outside of bounds at address: " + address + " and length: " + length);
        }
        int[] ret = new int[length];
        System.arraycopy(memory, address, ret, 0, length);
        return ret;
    }

    private boolean isOutOfBounds(int startAddress, int size) {
        return (startAddress < 0) ||
                (startAddress > memorySize - 1) ||
                (startAddress + size > memorySize);
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(int accumulator) {
        this.accumulator = accumulator;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean getJump() {
        return jump;
    }

    public void setExRegister(int exRegister) {
        this.exRegister = exRegister;
    }

    public int getExRegister() {
        return exRegister;
    }

    public void setMemAcc(boolean memAcc) {
        this.memAcc = memAcc;
    }

    public boolean getMemAcc() {
        return memAcc;
    }

    public void setLoadOrStore(boolean loadOrStore) {
        this.loadOrStore = loadOrStore;
    }

    public boolean getLoadOrStore() {
        return loadOrStore;
    }

    public void setHalt(boolean halt) {
        this.halt = halt;
    }

    public boolean getHaltFlag() {
        return halt;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public boolean getDirect() {
        return direct;
    }

   public void setALU(boolean alu) {
        this.alu = alu;
    }

   public boolean getALU() {
       return alu;
  }

   public void setMemRegister(int memRegister) {
        this.memRegister = memRegister;
   }

   public int getMemRegister() {
       return memRegister;
   }

   public void incrementProgramCounter() {
       programCounter++;
   }
}