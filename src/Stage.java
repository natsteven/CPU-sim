public abstract class Stage {
   private boolean locked = false;
   protected Memory memory;

   public Stage(Memory memory) {
      this.memory = memory;
   }

    public void lock() {
      locked = true;
   }

   public void unlock() {
      locked = false;
   }

    public boolean isLocked() {
        return locked;
    }

    public abstract void process() throws Exception;
}
