public abstract class Stage {
   private boolean locked = false;

   public Stage() {

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

    public abstract void process();
}
