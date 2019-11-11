package kmucs.mobileprogramming.team.a.blocklylmc;

public enum InstructionSet{
    ADD(100, 199, "ADD", "ADD"),
    SUB(200, 299, "SUB", "SUBTRACT"),
    STA(300, 399, "STA", "STORE"),
    LDA(400, 499, "LDA", "LOAD"),
    JMP(500, 599, "JMP", "JUMP"),
    JEZ(600, 699, "JEZ", "JUMP IF ZERO"),
    JNZ(700, 799, "JNZ", "JUMP IF NOT ZERO"),
    INP(801, 801, "INP", "INPUT"),
    OUT(802, 802, "OUT", "OUTPUT"),
    COB(900, 900, "COB", "COFFEE BREAK"),
    NOP(000, 000, "NOP", "NO OPERATION"),
    DAT(000, 999, "DAT", "DATA");
    private int minCode;
    private int maxCode;
    private String mnemonic;
    private String instruction;
    InstructionSet(int minCode, int maxCode, String mnemonic, String instruction){
        this.minCode = minCode;
        this.maxCode = maxCode;
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
}