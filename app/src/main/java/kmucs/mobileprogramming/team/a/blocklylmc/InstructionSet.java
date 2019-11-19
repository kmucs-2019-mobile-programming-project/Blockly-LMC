// 20181686 장병준, github : sunjbs
package kmucs.mobileprogramming.team.a.blocklylmc;

public enum InstructionSet{
    ADD(100, 199, 1, "ADD", "ADD"),
    SUB(200, 299, 1, "SUB", "SUBTRACT"),
    STA(300, 399, 1, "STA", "STORE"),
    LDA(400, 499, 1, "LDA", "LOAD"),
    JMP(500, 599, 1, "JMP", "JUMP"),
    JEZ(600, 699, 1, "JEZ", "JUMP IF ZERO"),
    JNZ(700, 799, 1, "JNZ", "JUMP IF NOT ZERO"),
    INP(801, 801, 1, "INP", "INPUT"),
    OUT(802, 802, 1, "OUT", "OUTPUT"),
    COB(900, 900, 1, "COB", "COFFEE BREAK"),
    NOP(000, 000, 1, "NOP", "NO OPERATION"),
    DAT(000, 999, 1, "DAT", "DATA");
    private int minCode;
    private int maxCode;
    private int cycle;
    private String mnemonic;
    private String instruction;
    InstructionSet(int minCode, int maxCode, int cycle, String mnemonic, String instruction){
        this.minCode = minCode;
        this.maxCode = maxCode;
        this.cycle = cycle;
        this.mnemonic = mnemonic;
        this.instruction = instruction;
    }

    public static boolean isADD(int IR){ return IR >= ADD.minCode && IR <= ADD.maxCode; }
    public static boolean isSUB(int IR){ return IR >= SUB.minCode && IR <= SUB.maxCode; }
    public static boolean isSTA(int IR){ return IR >= STA.minCode && IR <= STA.maxCode; }
    public static boolean isLDA(int IR){ return IR >= LDA.minCode && IR <= LDA.maxCode; }
    public static boolean isJMP(int IR){ return IR >= JMP.minCode && IR <= JMP.maxCode; }
    public static boolean isJEZ(int IR){ return IR >= JEZ.minCode && IR <= JEZ.maxCode; }
    public static boolean isJNZ(int IR){ return IR >= JNZ.minCode && IR <= JNZ.maxCode; }
    public static boolean isINP(int IR){ return IR >= INP.minCode && IR <= INP.maxCode; }
    public static boolean isOUT(int IR){ return IR >= OUT.minCode && IR <= OUT.maxCode; }
    public static boolean isCOB(int IR){ return IR >= COB.minCode && IR <= COB.maxCode; }
    public static boolean isNOP(int IR){ return IR >= NOP.minCode && IR <= NOP.maxCode; }

    public static InstructionSet getInstruction(int IR){
        if(isADD(IR)) return ADD;
        else if(isSUB(IR)) return SUB;
        else if(isSTA(IR)) return STA;
        else if(isLDA(IR)) return LDA;
        else if(isJMP(IR)) return JMP;
        else if(isJEZ(IR)) return JEZ;
        else if(isJNZ(IR)) return JNZ;
        else if(isINP(IR)) return INP;
        else if(isOUT(IR)) return OUT;
        else if(isCOB(IR)) return COB;
        else if(isNOP(IR)) return NOP;
        return DAT;
    }

    public static String disassemble(int IR){
        InstructionSet instruction = getInstruction(IR);
        String str = new String(instruction.mnemonic);
        switch (instruction){
            case INP:
            case OUT:
            case COB:
            case NOP:
            case DAT:
                break;
            default:
                str += " " + Integer.toString(IR % 100);
                break;
        }
        return str;
    }

    public int getCycle() { return cycle; }
}