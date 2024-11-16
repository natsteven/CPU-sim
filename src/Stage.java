public abstract class Stage {
   //  private boolean locked = false;
    protected Memory memory;
   //  protected int inReg;
   //  protected int outReg;
   //  protected Control.StageControl stageControl;

    public Stage(Memory memory) {
        this.memory = memory;
      //   this.inReg = 0xE00; // init to a NOOP
      //   this.outReg = 0xE00;
    }

   //  public void lock() {
   //      locked = true;
   //  }

   //  public void unlock() {
   //      locked = false;
   //  }

    public abstract void process() throws Exception;
}
