package bin.rules;

import bin.Enumirations.DescriptionsStates;
import bin.Enumirations.IndicesTables;
import bin.Enumirations.TypesData;
import bin.Items.Index;
import bin.Items.Token;
import bin.Tables.TotalInformation;
import bin.analyzers.LexicalAnalyzer;

import java.sql.Types;
import java.util.ArrayList;
import java.util.ListIterator;

public class Rules {
    public Rules(){}


    private ArrayList<Token> tokens;
    private ListIterator<Token> listIteratorsTokens;

    public static void main(String[] args) throws Exception {
        //{ grt,aw3 : integer ; fer : boolean ; for grt ass aw3 to grt do while not true do if (false then fer ass true ; }
        //{ grt,awt,ant : boolean ; if (awt = grt) then grt ass ant; }

    }

    public TotalInformation scanner(TotalInformation totalInformation) throws Exception {
        this.tokens = totalInformation.getTokenList();
        this.listIteratorsTokens = tokens.listIterator();
        if (isProgram()){
            System.out.println("Синтаксическая конструкция и семантические значения верны!");
            return totalInformation ;
        }
        throw new Exception("Синтаксическая конструкция или семантические значения неверны!");
    }
    //ПРоверка на программу
    private boolean isProgram() throws Exception {
        boolean isProgram = false;
        if(!listIteratorsTokens.next().getItem().equals("{")){
            throw new Exception("Программа должна начинаться с символа - { !");
        }
        while (listIteratorsTokens.hasNext()){
            skipLineTranslation();
            if (listIteratorsTokens.next().getItem().equals("}") & isProgram){
                return true;
            }else listIteratorsTokens.previous();
            if ((isDescription() || (isOperator() & listIteratorsTokens.next().getItem().equals(";")))){
                isProgram = true;
            }else {
                isProgram = false;
            }
        }
        //
        return isProgram;
        //конец возможного повтора
    }

    //Проверка на описание
    private boolean isDescription() throws Exception {
        boolean isDescripption = false;
        boolean construction = false;
        //TODO сделать проверку на множественные повторения
        ArrayList<Index> indices = new ArrayList<>();

        skipLineTranslation();
        Token token = listIteratorsTokens.next();
        Token tokenB;
        if (token.getTableIndex() != IndicesTables.INDICES) {
            listIteratorsTokens.previous();
            return isDescripption;
        }
        ((Index) token).setDescriptionsStates(DescriptionsStates.DESCRIBED);
        indices.add((Index) token);

        while (true) {
            token = listIteratorsTokens.next();
            tokenB = listIteratorsTokens.next();
            if (token.getItem().equals(",") & tokenB.getTableIndex() == IndicesTables.INDICES) {
                ((Index)tokenB).setDescriptionsStates(DescriptionsStates.DESCRIBED);
                indices.add((Index) tokenB);
                construction = true;
            }else {
                listIteratorsTokens.previous();
                listIteratorsTokens.previous();
                break;
            }
        }

        if (!listIteratorsTokens.next().getItem().equals(":") ) {
            if (construction){
                throw new Exception("Пропущен знак : перед определением типа данных");
            }else {
                listIteratorsTokens.previous();
                listIteratorsTokens.previous();
                return false;
            }
        }
        Token tokenData = listIteratorsTokens.next();
        if (!Operations.isTypesData(tokenData)) {
            throw new Exception("Тип данных не определен");
        }
        if (!(listIteratorsTokens.next().getItem().equals(";") )) {
            throw new Exception("Пропущен знак конца строки - ;");
        }
        indices.forEach(index -> {
            index.setTypeData(tokenData.getTypeData());
        });
        return true;
    }

    //Проверка на оператор
    private boolean isOperator() throws Exception {
        if (!isOperatorAssignment().equals(TypesData.NON_DATA)){
            return true;
        }
        else if (isConditionalOperator()){
            return true;
        }
        else if (isOperatorLoop()){
            return true;
        }
        else if (isConditionalLoopOperator()){
            return true;
        }
        else if (isInputOperator()){
            return true;
        }
        else if (isOutputOperator()){
            return true;
        }
        else if (isCompositeOperator()){
            return true;
        }
        return false;
    }

    //Проверка на составной оператор
    private boolean isCompositeOperator() throws Exception {
        boolean isCompositeOperator = false;
        if (isOperator()){
            //начало
            while (true) {
                Token token = listIteratorsTokens.next();
                if (!(token.getItem().equals(":") | token.getItem().equals("\n")) ) {
                    listIteratorsTokens.previous();
                    return isCompositeOperator;
                }
                if (!isOperator()) {
                    throw new Exception("Значение после : или \\n не является оператором");
                }
                isCompositeOperator = true;
            }
            //конец
        }
        return false;
    }

    //Проверка на оператор присваивания
    private TypesData isOperatorAssignment() throws Exception {
        Token next = listIteratorsTokens.next();
        TypesData bufTypesData;
        if (next.getTableIndex() != IndicesTables.INDICES ){
            listIteratorsTokens.previous();
            return TypesData.NON_DATA;
        }
        if (((Index)next).getDescriptionsStates().equals(DescriptionsStates.NOT_DESCRIBED)){
            throw new Exception("Идентификатор: " +next.getItem()+" - не инициализирован!");
        }
        if (((Index)next).getTypeData().equals(TypesData.NON_DEFINITELY)){
            throw new Exception("Тип данных идентификатора: "+ next.getItem()+" - не определён!");
        }
        if (!listIteratorsTokens.next().getItem().equals("ass")){
            listIteratorsTokens.previous();
            return TypesData.NON_DATA;
        }

        bufTypesData = isExpression();
        if (next.getTypeData().equals(TypesData.REAL)){
            if (bufTypesData.equals(TypesData.REAL) | bufTypesData.equals(TypesData.INTEGER)){
                return next.getTypeData();
            }else throw new Exception("Нельзя присвоить типу "+next.getTypeData()+" тип " + bufTypesData);
        }else if (next.getTypeData().equals(TypesData.INTEGER)){
            if (bufTypesData.equals(TypesData.INTEGER)){
                return next.getTypeData();
            }else throw new Exception("Нельзя присвоить типу "+next.getTypeData()+" тип " + bufTypesData);
        }else if (next.getTypeData().equals(TypesData.BOOLEAN)){
            if (bufTypesData.equals(TypesData.BOOLEAN)){
                return next.getTypeData();
            }else throw new Exception("Нельзя присвоить типу "+next.getTypeData()+" тип " + bufTypesData);
        }
        return bufTypesData;
    }

    //Проверка на условный оператор
    private boolean isConditionalOperator() throws Exception {
        boolean isConditionalOperator =false;
        if (!listIteratorsTokens.next().getItem().equals("if")){
            listIteratorsTokens.previous();
            return isConditionalOperator;
        }
        skipLineTranslation();
        TypesData typesData = isExpression();
        if (typesData.equals(TypesData.NON_DATA) | !typesData.equals(TypesData.BOOLEAN)){
            throw new Exception("После оператора if должно быть выражение типа boolean!");
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals("then")){
            throw new Exception("После выражения должно быть служебное слово then!");
        }
        skipLineTranslation();
        if (!isOperator()){
            return isConditionalOperator;
        }
        skipLineTranslation();
        isConditionalOperator = true;
        if (listIteratorsTokens.next().getItem().equals("else")){
            skipLineTranslation();
            if (!isOperator()){
                throw new Exception("После служебного слова else должен идти оператор!");
            }
            skipLineTranslation();
        }else {
            listIteratorsTokens.previous();
            return isConditionalOperator;
        }
        return isConditionalOperator;
    }

    //Проверка на оператор цикла
    private boolean isOperatorLoop() throws Exception {
        if (!listIteratorsTokens.next().getItem().equals("for")){
            listIteratorsTokens.previous();
            return false;
        }
        skipLineTranslation();
        TypesData typesData = isOperatorAssignment();
        if (typesData.equals(TypesData.NON_DATA)|typesData.equals(TypesData.BOOLEAN)) {
            throw new Exception("После оператора for должен идти оператор присваивания (и не boolean)!");
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals("to")){
            throw new Exception("После оператора присваивания должно идти служебное слово to!");
        }
        skipLineTranslation();
        typesData = isExpression();
        skipLineTranslation();
        if (typesData.equals(TypesData.NON_DATA)|typesData.equals(TypesData.BOOLEAN)){
            throw new Exception("После служебного слова to должно идти выражение (и не boolean!)!");
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals("do")){
            throw new Exception("После выражения должно идти служебное слово do!");
        }
        skipLineTranslation();
        if (!isOperator()){
            throw new Exception("После служебного слова do должен идти оператор!");
        }
        return true;
    }

    //Проверка на оператор условного цикла
    private boolean isConditionalLoopOperator() throws Exception {
        TypesData typesData;
        if (!listIteratorsTokens.next().getItem().equals("while")){
            listIteratorsTokens.previous();
            return false;
        }
        skipLineTranslation();
        typesData = isExpression();
        if (!typesData.equals(TypesData.BOOLEAN)){
            throw new Exception("После служебного слова while должно идти булево выражение!");
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals("do")){
            throw new Exception("После выражения должно идти служебное слово do!");
        }
        skipLineTranslation();
        if (!isOperator()){
            throw new Exception("После служебного слова do должен идти оператор!");
        }
        return true;
    }

    //Проверка на оператор ввода
    private boolean isInputOperator() throws Exception {
        boolean isInputOperator = false;
        Token next;
        if (!listIteratorsTokens.next().getItem().equals("read")){
            listIteratorsTokens.previous();
            return false;
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals("(")){
            throw new Exception("После служебного слова read должен идти огранечитель - ( !");
        }
        skipLineTranslation();
        next = listIteratorsTokens.next();
        if (next.getTableIndex() != IndicesTables.INDICES){
            throw new Exception("После огранечителя ( должен идти идентификатор!");
        }
        if (((Index)next).getDescriptionsStates().equals(DescriptionsStates.NOT_DESCRIBED)){
            throw new Exception("Идентификатор "+next.getItem()+" не определён!");
        }
        skipLineTranslation();
        //начало
        while (listIteratorsTokens.hasNext()) {
            skipLineTranslation();
            if (!listIteratorsTokens.next().getItem().equals(",")) {
                if (!isInputOperator) {
                    throw new Exception("После идентифкатора должен идти огранечитель - , ");
                }else {
                    listIteratorsTokens.previous();
                    break;
                }
            }
            skipLineTranslation();
            next = listIteratorsTokens.next();
            if (next.getTableIndex() != IndicesTables.INDICES){
                throw new Exception("После огранечителя - , должен идти идентификатор!");
            }
            if (((Index)next).getDescriptionsStates().equals(DescriptionsStates.NOT_DESCRIBED)){
                throw new Exception("Идентификатор "+next.getItem()+" не определён!");
            }
            isInputOperator = true;
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals(")")){
            throw new Exception("После идентифкатора должен идти огранечитель - )");
        }
        return isInputOperator;
    }

    //Проверка на оператор вывода
    private boolean isOutputOperator() throws Exception {
        boolean isOutputOperator = false;
        if (!listIteratorsTokens.next().getItem().equals("write")){
            listIteratorsTokens.previous();
            return false;
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals("(")){
            throw new Exception("После служебного слова write должен идти огранечитель - ( !");
        }
        skipLineTranslation();
        if (isExpression().equals(TypesData.NON_DATA)){
            throw new Exception("После огранечителя ( должно идти выражение!");
        }
        skipLineTranslation();

        while (listIteratorsTokens.hasNext()){
            skipLineTranslation();
            if (!listIteratorsTokens.next().getItem().equals(",")){
                if (!isOutputOperator) {
                    throw new Exception("После выражения должен идти огранечитель - , ");
                }else{
                    listIteratorsTokens.previous();
                    break;
                }
            }
            if (isExpression().equals(TypesData.NON_DATA)){
                throw new Exception("После огранечителя , должно идти выражение!");
            }
            isOutputOperator = true;
        }
        skipLineTranslation();
        if (!listIteratorsTokens.next().getItem().equals(")")){
            throw new Exception("После выражения должен идти огранечитель - )!");
        }
        return isOutputOperator;
    }

    //Проверка на выражение
    private TypesData isExpression() throws Exception {
        TypesData bufType = isOperand();
        Token next;
        if (bufType.equals(TypesData.NON_DATA)){
            throw new Exception("Тип данных не определён");
        }
        while(listIteratorsTokens.hasNext()){
            next = listIteratorsTokens.next();
            if (!Operations.isAttitude(next)){
                listIteratorsTokens.previous();
                return bufType;
            }
            if (bufType.equals(TypesData.INTEGER) | bufType.equals(TypesData.REAL)){
                bufType = isOperand();
                if (bufType.equals(TypesData.REAL)| bufType.equals(TypesData.INTEGER)){
                    return TypesData.BOOLEAN;
                }
                else {
                    throw new Exception("Определён неверный тип данных!");
                }
            }else if (bufType.equals(TypesData.BOOLEAN)){
                bufType = isOperand();
                if (bufType.equals(TypesData.BOOLEAN)){
                    return bufType;
                }
                else {
                    throw new Exception("Определён неверный тип данных!");
                }
            }
        }
        return TypesData.NON_DATA;
    }

    //Проверка на операнд
    private TypesData isOperand() throws Exception {
        TypesData bufType = isTerm();
        Token next;
        if (bufType.equals(TypesData.NON_DATA)){
            throw new Exception("Тип данных не определён!");
        }
        while (listIteratorsTokens.hasNext()) {
            next = listIteratorsTokens.next();
            if (!Operations.isAddition(next)) {
                listIteratorsTokens.previous();
                return bufType;
            }
            if (bufType.equals(TypesData.BOOLEAN)){
                if (next.getItem().equals("and")){
                    bufType = isTerm();
                    if (bufType.equals(TypesData.BOOLEAN)){
                        return bufType;
                    }
                    else {
                        throw new Exception("Определен неверный тип данных!");
                    }
                }
                else{
                    throw new Exception("Определен неправильный знак выражения!");
                }
            }
            else if (bufType.equals(TypesData.INTEGER) | bufType.equals(TypesData.REAL)){
                if (next.getItem().equals("+") | next.getItem().equals("-")){
                    if (bufType.equals(TypesData.INTEGER)){
                        bufType = isTerm();
                        if (bufType.equals(TypesData.INTEGER)){
                            return TypesData.INTEGER;
                        }
                    }else if (bufType.equals(TypesData.REAL)){
                        bufType = isTerm();
                        if (bufType.equals(TypesData.INTEGER) | bufType.equals(TypesData.REAL)){
                            return TypesData.INTEGER;
                        }
                    }
                    throw new Exception("Определен неверный тип данных!");
                }else {
                    throw new Exception("Определен не правильный знак сложения");
                }
            }
        }
        return TypesData.NON_DATA;
    }

    //Проверка на слагаемое
    private TypesData isTerm() throws Exception {
        TypesData bufType = isFactor();
        Token next;
        if (bufType.equals(TypesData.NON_DATA)){
            throw new Exception("Множитель не определён!");
        }
        while (listIteratorsTokens.hasNext()) {
            //if (bufType.equals(TypesData))
            next = listIteratorsTokens.next();
            if (!Operations.isMultiplication(next)) {
                listIteratorsTokens.previous();
                return bufType;
            }
            if (bufType.equals(TypesData.BOOLEAN)){
                if (next.getItem().equals("or")){
                    bufType = isFactor();
                    if (bufType.equals(TypesData.BOOLEAN)){
                        return bufType;
                    }
                    else {
                        throw new Exception("Определен неверный тип данных!");
                    }
                }
                else throw new Exception("Определен неверный знак умножения");
            }
            else if (bufType.equals(TypesData.INTEGER) | bufType.equals(TypesData.REAL)){
                if (next.getItem().equals("*")|next.getItem().equals("/")){
                    if (bufType.equals(TypesData.INTEGER)){
                        bufType = isTerm();
                        if (bufType.equals(TypesData.INTEGER)){
                            return TypesData.INTEGER;
                        }
                    }else if (bufType.equals(TypesData.REAL)){
                        bufType = isTerm();
                        if (bufType.equals(TypesData.INTEGER) | bufType.equals(TypesData.REAL)){
                            return TypesData.INTEGER;
                        }
                    }else{
                        throw new Exception("Определен неверный тип данных!");
                    }
                }
                else throw new Exception("Определён неверный знак умножения!");
            }
        }
        return TypesData.NON_DATA;
    }

    //Проверка на множитель
    private TypesData isFactor() throws Exception {
        Token token = listIteratorsTokens.next();
        TypesData typesData;
        if (token.getTableIndex().equals(IndicesTables.INDICES)){
            if (((Index)token).getDescriptionsStates().equals(DescriptionsStates.NOT_DESCRIBED)){
                throw new Exception("Идентификатор: "+token.getItem()+" - должен быть определен!");
            }else {
                return ((Index) token).getTypeData();
            }
        }
        else if(token.getTableIndex().equals(IndicesTables.NUMBERS)){
            return token.getTypeData();
        }
        else if (Operations.isLogicConstant(token)){
            return token.getTypeData();
        }else if (token.getItem().equals("not") && (isFactor().equals(TypesData.BOOLEAN))){
            return token.getTypeData();
        }else {
            typesData = isExpression();
            if (token.getItem().equals("(")){
                if (!(typesData.equals(TypesData.NON_DATA)|typesData.equals(TypesData.NON_DEFINITELY)))
                    if(listIteratorsTokens.next().getItem().equals(")")){
                        return typesData;
                    }else {
                        throw new Exception("Огранечитель - ) - не найден");
                    }else {
                    throw new Exception("Тип данных не определён");
                }
            }else {
                throw new Exception("Огранечитель - ( - не найден");
            }
        }
    }

    //Переход на следующий  токен, если текущий равен переносу строки
    private boolean skipLineTranslation(){
        if (listIteratorsTokens.next().getItem().equals("\n")){
            return true;
        }else {
            listIteratorsTokens.previous();
            return false;
        }
    }


}
