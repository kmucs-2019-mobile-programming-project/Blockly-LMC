// 20181686 장병준, github : sunjbg
package kmucs.mobileprogramming.team.a.blocklylmc;

// Exception class that when little man faced undefine code
class InvalidInstructionException extends RuntimeException{}
class OutOfMailboxException extends RuntimeException{}

public class LMC extends Thread {
    private int numOfMailBox = 100;
    private int valueCapacity = 1000;
    private int[] mailBoxes;

    private int CYCLE;

    // Register
    private int PC;
    private int IR;
    private int PSR;
    private int A;

    // I/O
    private int IO;

    // State of current Instruction is INP and wait for user response
    private boolean waitINP;
    // State of current Instruction is OUT and wait for user response
    private boolean waitOUT;


    private boolean state;
    // State of whether little man working now
    private boolean running;
    private boolean COB;
    private boolean errorOccurred;

    private int instructionDelay = 50;

    public LMC(){
        mailBoxes = new int[numOfMailBox];
        resetAll();
    }

    public int getCYCLE(){ return CYCLE; }
    public int[] getMailBoxes(){ return mailBoxes; }
    public void setMailBoxes(int[] mailBoxes){ this.mailBoxes = mailBoxes; }
    public int getPC(){ return PC; }
    public int getIR(){ return IR; }
    public int getPSR(){ return PSR; }
    public int getA(){ return A; }

    public boolean getWaitINP(){ return waitINP;}
    public boolean getWaitOUT(){ return waitOUT; }

    public int getIO(){
        if(!waitOUT) throw new RuntimeException();
        waitOUT = false;
        return IO;
    }

    public void setIO(int IO){
        if(!waitINP) throw new RuntimeException();
        waitINP = false;
        this.IO = IO;
        A = IO;
    }

    // Set delay of between instrcution
    public void setInstructionDelay(int instructionDelay){ this.instructionDelay = instructionDelay; }

    public void execute(){
        resetRegister();
        resetFlag();
        running = true;
    }

    // public void pause(){}

    public void reset(){
        CYCLE = 0;
        resetRegister();
        resetFlag();
        running = false;
    }

    public void resetMailBox(){
        for(int i = 0; i < numOfMailBox; i++)
            mailBoxes[i] = 0;
    }

    public void resetRegister(){
        PC = PSR = A = IR = IO = CYCLE = 0;
    }

    public void resetFlag(){
        running = true;
        waitINP = waitOUT = COB = false;
        errorOccurred = false;
    }

    public void resetAll(){
        CYCLE = 0;
        resetMailBox();
        resetRegister();
        resetFlag();
    }

    @Override
    public void run(){
        reset();
        state = true;
        while(state){
            if(running) {
                try {
                    step();
                } catch (Exception e) {
                    running = false;
                    errorOccurred = true;
                }
            }
            try {
                sleep(instructionDelay);
            }catch(Exception e){}
        }
    }

    public void halt(){ state = false; }

    public boolean isRunning() { return running; }

    public boolean isErrorOccurred() { return errorOccurred; }

    private boolean isZero(){ return PSR % 10 == 0; }

    private void setPSR() {
        if(A > 0) PSR = 1;
        else PSR = 0;
    }

    private void add(int addr){
        A = (A + mailBoxes[addr]) % valueCapacity;
        setPSR();
    }

    private void sub(int addr){
        A = (A - mailBoxes[addr]) % valueCapacity;
        setPSR();
    }

    private void lda(int addr){ A = mailBoxes[addr]; }

    private void sta(int addr){ mailBoxes[addr] = A; }

    private void jmp(int addr){ PC = addr; }

    private void jez(int addr){ if(isZero()) PC = addr; }

    private void jnz(int addr){ if(!isZero()) PC = addr; }

    private void inp(){ waitINP = true; }

    private void out(){
        IO = A;
        waitOUT = true;
    }

    private void cob(){
        running = false;
        COB = true;
    }


    public void step(){
        if(COB) return;
        if(waitINP || waitOUT) return;
        if(PC == numOfMailBox) throw new OutOfMailboxException();
        IR = mailBoxes[PC];
        int addr = IR % 100;
        InstructionSet instruction = InstructionSet.getInstruction(IR);

        PC += 1;
        CYCLE += instruction.getCycle();

        if(InstructionSet.isADD(IR)) add(addr);
        else if(InstructionSet.isSUB(IR)) sub(addr);
        else if(InstructionSet.isSTA(IR)) sta(addr);
        else if(InstructionSet.isLDA(IR)) lda(addr);
        else if(InstructionSet.isJMP(IR)) jmp(addr);
        else if(InstructionSet.isJEZ(IR)) jez(addr);
        else if(InstructionSet.isJNZ(IR)) jnz(addr);
        else if(InstructionSet.isINP(IR)) inp();
        else if(InstructionSet.isOUT(IR)) out();
        else if(InstructionSet.isCOB(IR)) cob();
        else if(InstructionSet.isNOP(IR)) ;
        else throw new InvalidInstructionException();
        return;
    }
}