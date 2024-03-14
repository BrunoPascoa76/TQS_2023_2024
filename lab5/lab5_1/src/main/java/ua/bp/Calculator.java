package ua.bp;

public class Calculator {
    private int val1;
    private int val2;
    private int step; // describes the next step. 0=insert val1, 1=insert val2, 2=insert operation,
                      // 3=get result
    private int result;

    public Calculator() {
        val1 = 0;
        val2 = 0;
        result = 0;
        step = 0;

    }

    public void insert(int val) throws IllegalArgumentException{
        switch(step){
            case 0:
                val1=val;
                break;
            case 1:
                val2=val;
                break;
            default:
                step=0;
                throw new IllegalArgumentException("Number was not expected");
        }
        step+=1;
    }

    public void insert(String op) throws IllegalArgumentException,ArithmeticException{
        if(step!=2){
            step=0;
            throw new IllegalArgumentException("Operation was not expected");
        }else{
            switch(op){
                case "+":
                    result=val1+val2;
                    break;
                case "-":
                    result=val1-val2;
                    break;
                case "*":
                    result=val1*val2;
                    break;
                case "/":
                    if(val2==0){
                        step=0;
                        throw new ArithmeticException("Cannot divide by 0");
                    }
                    result=val1/val2;
                    break;
            }
            step+=1;
        }
    }

    public int result() throws IllegalArgumentException{
        if(step==3){
            step=0;
            return result;
        }else{
            step=0;
            throw new IllegalArgumentException("Result operation not finished yet");
        }
    }
}
