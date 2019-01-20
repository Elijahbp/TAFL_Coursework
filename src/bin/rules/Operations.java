package bin.rules;

import bin.Enumirations.TypesData;
import bin.Items.Token;
import bin.Tables.TableLimiters;
import bin.Tables.TableServiceWords;

public class Operations {
    private final static Token[] Attitude = {
            TableLimiters.getElement("<>"),
            TableLimiters.getElement("<="),
            TableLimiters.getElement("="),
            TableLimiters.getElement(">"),
            TableLimiters.getElement("<"),
            TableLimiters.getElement(">="),
    };

    private final static Token[] Addition = {
            TableLimiters.getElement("+"),
            TableLimiters.getElement("-"),
            TableServiceWords.getElement("or")
    };

    private final static Token[] Multiplication = {
            TableLimiters.getElement("*"),
            TableLimiters.getElement("/"),
            TableServiceWords.getElement("and")
    };

    //Группа унарных операторов
    private final static Token[] unaryOperation = {
            TableServiceWords.getElement("not")
    };

    //Группа типов данных
    private final static Token[] typesData = {
            TableLimiters.getElement("%"),
            TableLimiters.getElement("!"),
            TableLimiters.getElement("$")
    };

    //Группа логических констант
    private final static Token[] logicConstant = {
            TableServiceWords.getElement("true"),
            TableServiceWords.getElement("false")
    };

    //Проверка на группы отношения
    public static boolean isAttitude(Token token){
        for (int i = 0; i < Attitude.length; i++) {
            if (Attitude[i].equals(token)){
                return true;
            }
        }
        return false;
    }

    //Проверка на группы сложения
    public static boolean isAddition(Token token){
        for (int i = 0; i < Addition.length; i++) {
            if (Addition[i].equals(token)){
                return true;
            }
        }
        return false;
    }

    //Проверка на группу умножения
    public static boolean isMultiplication(Token token){
        for (int i = 0; i < Multiplication.length; i++) {
            if (Multiplication[i].equals(token)){
                return true;
            }
        }
        return false;
    }

    public static boolean isUnaryOperation(Token token){
        for (int i = 0; i < unaryOperation.length; i++) {
            if (unaryOperation[i].equals(token)){
                return true;
            }
        }
        return false;
    }
    public static boolean isTypesData(Token token){
        for (int i = 0; i < typesData.length; i++) {
            if (typesData[i].equals(token)){
                return true;
            }
        }
        return false;
    }
    public static boolean isLogicConstant(Token token){
        for (int i = 0; i < logicConstant.length; i++) {
            if (logicConstant[i].equals(token)){
                return true;
            }
        }

        return false;
    }

    public TypesData getTypeData(Token token) throws Exception {
        for (int i = 0; i < typesData.length; i++) {
            if (typesData[i].getTypeData().equals(token.getTypeData())){
                return typesData[i].getTypeData();
            }
        }
        throw new Exception("Данного типа данных не существует");
    }

}
