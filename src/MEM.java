public class MEM extends Stage {

    public MEM(Memory memory) {
        super(memory);
    }
    
    @Override
    public void process() throws Exception {
        System.out.println("MEM");
        if (memory.getMemAcc()) {
            System.out.println("Memory access!");
            if (memory.getLoadOrStore()) { // Load
                if (memory.getDirect()) {
                    memory.setMemRegister(memory.getExRegister()); // directly load operand
                } else {
                    memory.setMemRegister(memory.readMemory(memory.getExRegister())); // get value from pointer to memory
                }
            } else { // Store
                if (memory.getDirect()) {
                    memory.load(memory.getExRegister(), memory.getAccumulator());
                } else {
                    memory.load(memory.readMemory(memory.getExRegister()), memory.getAccumulator());
                }
            }
        }
        // else no memory access
    }


}
