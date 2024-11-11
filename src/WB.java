public class WB extends Stage {
    
    public WB(Memory memory) {
        super(memory);
    }

    @Override
    public void process() {
        System.out.println("WB");
        memory.incrementProgramCounter();
        if (memory.getJump()) {
            memory.setProgramCounter(memory.getExRegister());
            memory.setJump(false);
        }else if (memory.getALU()) {
            memory.setAccumulator(memory.getExRegister());
            memory.setALU(false);
        } else if (memory.getMemAcc()) {
            if (memory.getLoadOrStore()) { // Load
                memory.setAccumulator(memory.getMemRegister());
            }
            memory.setMemAcc(false);
        }
    }
}
