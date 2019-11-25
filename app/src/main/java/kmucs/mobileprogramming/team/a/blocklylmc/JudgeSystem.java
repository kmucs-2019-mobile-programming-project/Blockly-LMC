// 20181686 장병준, github : sunjbs
package kmucs.mobileprogramming.team.a.blocklylmc;

import java.util.ArrayList;

class WrongAnswerException extends RuntimeException{}
class TimeLimitExceededException extends  RuntimeException{}
class JudgeFinishedListenerNotFoundException extends RuntimeException{}

public class JudgeSystem extends Thread {
    private int judgeLevel;
    private int[] mailBoxes;
    private int timeLimit;
    private int[][] inputData;
    private int[][] outputData;

    private int cycle;
    private boolean correctAnswer;

    private OnJudgeFinishedListener mListener;

    public JudgeSystem(int judgeLevel, int[] mailBoxes){
        this.judgeLevel = judgeLevel;
        this.mailBoxes = mailBoxes;

        correctAnswer = true;

        switch (judgeLevel) {
        case 1:
            setLevel1();
            break;
        case 2:
            setLevel2();
            break;
        case 3:
            setLevel3();
            break;
        case 4:
            setLevel4();
            break;
        case 5:
            setLevel5();
            break;
        default:
            break;
        }
    }

    @Override
    public void run() {
        LMC lmc = new LMC();
        lmc.setMailBoxes(mailBoxes);
        for(int testcase = 0; testcase < inputData.length; testcase++) {
            int time = 0;
            int inputIndex = 0, outputIndex = 0;
            lmc.reset();
            try {
                while (!lmc.isCOB()) {
                    lmc.step();
                    if (lmc.getWaitINP())
                        lmc.setIO(inputData[testcase][inputIndex++]);
                    else if (lmc.getWaitOUT()) {
                        int output = lmc.getIO();
                        if (outputData[testcase][outputIndex++] != output)
                            throw new WrongAnswerException();
                    }

                    time++;
                    if(time > timeLimit)
                        throw new TimeLimitExceededException();
                }
                if(lmc.isErrorOccurred())
                    throw new RuntimeException();
                if(inputIndex < inputData[testcase].length)
                    throw new WrongAnswerException();
                if(outputIndex < outputData[testcase].length)
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
        timeLimit = 49999;
        inputData = input;
        outputData = output;
    }

    // ADD
    private void setLevel2(){
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
        timeLimit = 49999;
        inputData = input;
        outputData = output;
    }

    // SUB
    private void setLevel3(){
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
        timeLimit = 49999;
        inputData = input;
        outputData = output;
    }

    // Check two number is same
    private void setLevel4(){
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
        timeLimit = 49999;
        inputData = input;
        outputData = output;
    }

    // ADD Until 0
    private void setLevel5(){
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
        timeLimit = 49999;
        inputData = input;
        outputData = output;
    }

    /*
        작성자 : 20181619 박종흠
     */
    public interface OnJudgeFinishedListener {
        void onJudgeFinished(int level, int cycle, boolean success);
    }

    /*
        작성자 20181619 박종흠
     */
    public void setOnJudgeFinishedListener(OnJudgeFinishedListener listener){
        mListener = listener;
    }

}

