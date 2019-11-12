package kmucs.mobileprogramming.team.a.blocklylmc;

// Exception class that when little man faced undefine code
class InvalidInstructionException extends RuntimeException{}
class OutOfMailboxException extends RuntimeException{}

public class LMC {
    private int numOfMailBox = 100;
    private int valueCapacity = 1000;
    private int[] mailBoxes;
    private int PC;
    private int IR;
    private int PSR;
    private int A;
    private boolean runnable;

    public LMC(){
        mailBoxes = new int[numOfMailBox];
    }

    public int[] getMailBoxes(){ return mailBoxes; }
    public int getPC(){ return PC; }
    public int getIR(){ return IR; }
    public int getPSR(){ return PSR; }
    public int getA(){ return A; };

    public void reset(){
        for(int i = 0; i < numOfMailBox; i++)
            mailBoxes[i] = 0;
        PC = PSR = A = IR = 0;
        runnable = true;
    }

    public void run(){
        // Must be filled
        while(runnable){
            step();
        }
    }

    public void stop(){ runnable = false; }

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

    private void inp(){
        // in(A);
    }

    private void out(){
        // out(A)
    }

    private void cob(){ runnable = false; }


    public void step(){
        if(PC == numOfMailBox) throw new OutOfMailboxException();
        IR = mailBoxes[PC];
        int addr = IR % 100;

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

        PC += 1;
        return;
    }
}
