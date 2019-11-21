// 20181686 장병준, github : sunjbs
package kmucs.mobileprogramming.team.a.blocklylmc;

import java.util.ArrayList;

class WrongAnswerException extends RuntimeException{}
class TimeLimitExceededException extends  RuntimeException{}
class JudgeFinishedListenerNotFoundException extends RuntimeException{}

public class JudgeSystem extends Thread {
    private int judgeLevel;
    private int[] mailBoxes;
    private int totalLevel;
    private int[] timeLimit;
    private int[][][] inputData;
    private int[][][] outputData;

    private int cycle;
    private boolean correctAnswer;

    private OnJudgeFinishedListener mListener;

    public JudgeSystem(int judgeLevel, int[] mailBoxes){
        this.judgeLevel = judgeLevel;
        this.mailBoxes = mailBoxes;

        correctAnswer = true;
        totalLevel = 5;
        timeLimit = new int[totalLevel];
        inputData = new int[totalLevel][][];
        outputData = new int[totalLevel][][];

        setLevel1();
        setLevel2();
        setLevel3();
        setLevel4();
        setLevel5();
    }

    @Override
    public void run() {
        LMC lmc = new LMC();
        lmc.setMailBoxes(mailBoxes);
        lmc.run();
        int inputIndex = 0, outputIndex = 0;
        for(int testcase = 0; testcase < inputData[judgeLevel].length; testcase++) {
            int time = 0;
            lmc.reset();
            try {
                lmc.execute();
                while (lmc.isRunning()) {
                    if (lmc.getWaitINP())
                        lmc.setIO(inputData[judgeLevel][testcase][inputIndex++]);
                    else if (lmc.getWaitOUT()) {
                        int output = lmc.getIO();
                        if (outputData[judgeLevel][testcase][outputIndex++] != output)
                            throw new WrongAnswerException();
                    }

                    try {
                        sleep(50);
                        time += 50;
                        if(time > timeLimit[judgeLevel])
                            throw new TimeLimitExceededException();
                    } catch (Exception e) {
                    }
                }
                if(lmc.isErrorOccurred())
                    throw new RuntimeException();
                if(inputIndex < inputData[judgeLevel].length)
                    throw new WrongAnswerException();
                if(outputIndex < outputData[judgeLevel].length)
                    throw new WrongAnswerException();
            }catch(Exception e) {
                correctAnswer = false;
            }
            if(!correctAnswer)
                break;
            // cycle is record of last testcase
            cycle = lmc.getCYCLE();
        }
        if(mListener == null)
            throw new JudgeFinishedListenerNotFoundException();
        mListener.onJudgeFinished(judgeLevel, cycle, isCorrectAnswer());
    }

    public boolean isCorrectAnswer(){ return correctAnswer; }
    // return cycle of last testcase
    public int getCycle(){ return cycle; }

    // INP and OUT
    private void setLevel1(){
        int level = 1;
        int input[][] = {
                {3},
                {7},
                {6},
                {888},
                {102}
        };
        int output[][] =  {
                {3},
                {7},
                {6},
                {888},
                {102}
        };
        timeLimit[level] = 4999;
        inputData[level] = input;
        outputData[level] = output;
    }

    // ADD
    private void setLevel2(){
        int level = 2;
        int input[][] = {
                {1, 2},
                {2, 3},
                {2, 4},
                {5, 6},
                {99, 3}
        };
        int output[][] =  {
                {3},
                {5},
                {6},
                {11},
                {102}
        };
        timeLimit[level] = 4999;
        inputData[level] = input;
        outputData[level] = output;
    }

    // SUB
    private void setLevel3(){
        int level = 3;
        int input[][] = {
                {70, 8},
                {50, 3},
                {102, 1},
                {999, 666},
                {99, 3}
        };
        int output[][] =  {
                {62},
                {47},
                {101},
                {333},
                {96}
        };
        timeLimit[level] = 4999;
        inputData[level] = input;
        outputData[level] = output;
    }

    // Check two number is same
    private void setLevel4(){
        int level = 4;
        int input[][] = {
                {4, 4},
                {50, 51},
                {102, 102},
                {999, 666},
                {99, 1}
        };
        int output[][] =  {
                {1},
                {0},
                {1},
                {0},
                {0}
        };
        timeLimit[level] = 4999;
        inputData[level] = input;
        outputData[level] = output;
    }

    // ADD Until 0
    private void setLevel5(){
        int level = 5;
        int input[][] = {
                {1, 2, 3, 4, 5, 0},
                {70, 23, 12, 23, 61, 123, 0},
                {33, 21, 434, 2, 0},
                {111, 222, 333, 0},
                {3, 5, 6, 8, 12, 3, 5, 55, 21, 43, 326, 3, 0}
        };
        int output[][] =  {
                {15},
                {312},
                {490},
                {666},
                {490}
        };
        timeLimit[level] = 4999;
        inputData[level] = input;
        outputData[level] = output;
    }

    public interface OnJudgeFinishedListener {
        void onJudgeFinished(int level, int cycle, boolean success);
    }

    public void setOnJudgeFinishedListener(OnJudgeFinishedListener listener){
        mListener = listener;
    }

}

