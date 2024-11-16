import java.util.LinkedList;

public class Control {
    int programCounter;
    int accumulator;

    // boolean dataHazard;
    // boolean controlHazard;
    boolean halt;

    // LinkedList<StageControl> stages;

    // // create a pseudo state machine, this is more than what is necessary
    // public static class StageControl {
    //     //boolean STALL;
    //     boolean alu;
    //     boolean memAcc;
    //     boolean loadOrStore;
    //     boolean direct; // uggh this is probably a bad way to do it
    //     boolean halt;
    //     boolean jump;

    //     public StageControl() {
    //         //STALL = false;
    //         alu = false;
    //         memAcc = false;
    //         loadOrStore = false;
    //         direct = false;
    //         halt = false;
    //         jump = false;
    //     }
    // }

    public Control() {
        programCounter = 0;
        accumulator = 0;
        // dataHazard = false;
        // controlHazard = false;
        halt = false;
        // stages = new LinkedList<StageControl>();
        // for (int i = 0; i < 5; i++) {
        //     stages.add(new StageControl());
        // }
    }

    // public boolean isStalling() {
    //     return dataHazard || controlHazard;
    // }

//    public void stall() {
//        stages.getFirst().STALL = true;
//        stages.get(1).STALL = true;
//        stages.get(2).STALL = true;
//    }

    // public void cycleStages() {
    //     if (!isStalling()) {
    //         stages.addFirst(new StageControl());
    //     } else {
    //         stages.add(2, new StageControl()); // insert dummy stage at EX
    //     }
    //     stages.removeLast();
    // }

}