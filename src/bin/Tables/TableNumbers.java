package bin.Tables;


import bin.Enumirations.TypesData;
import bin.Items.Numeric;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Класс Numeric - мой класс по образованию чисел!
//
public class TableNumbers {

    private static List<Number> numbers;
    private static List<Numeric> numericList;
     TableNumbers(){
        numbers = new ArrayList();
        numericList = new ArrayList<>();
    }


    public boolean contains(String number){
        return numbers.contains(number);
    }
    
    public List<Numeric> getListElements() {
        return numericList;
    }

    public static Numeric getElement(String number){
        Number numeric1 = parseNumber(number);
        for (Numeric numeric:numericList) {
            if (numeric.getItem().equals(numeric1)){
                return numeric;
            }
        }
        try {
            throw new Exception("Данного значения: "+number+"нет в таблице чисел");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //
    public boolean setNumber(String number) throws Exception {
        Number n = parseNumber(number);
        if (n != null){
            numbers.add(n);
            if (n instanceof Integer){
                numericList.add(new Numeric(n, TypesData.symbINT, getIndexNumber(n)));
                return true;
            }
            else if (n instanceof Double){
                numericList.add(new Numeric(n,TypesData.symbDECIM, getIndexNumber(n)));
                return true;
            }
        }
        return false;
    }

    private Integer getIndexNumber(Number number) throws Exception {
        if (!numbers.contains(number)){
            throw new Exception("Данного значения:" + number.toString()+" нет в таблице чисел");
        }
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals(number)){
                return i;
            }
        }
        throw new Exception("Данного значения:" + number.toString()+" нет в таблице чисел");

    }

    private static Number parseNumber(String number){
        if (isBinary(number)){
            //System.out.println("Число двоичное");
            number = number.replaceAll("[bB]","");
            return Integer.parseInt(number,2);
        }
        if (isOcta(number)){
            //System.out.println("Число восьмиричное");
            number = number.replaceAll("[oO]","");
            return Integer.parseInt(number,8);
        }
        if (isDecimal(number)){
            //System.out.println("Число десятичное");
            number = number.replaceAll("[dD]","");
            return Integer.parseInt(number,10);
        }
        if (isHex(number)){
            number = number.replaceAll("[hH]","");
            return Integer.parseInt(number,16);
        }
        if (isDouble(number)){
            return Double.parseDouble(number);
        }
        if (isOrder(number)){
            return Double.parseDouble(number);
        }
        else return null;
    }

    private static boolean isBinary(String number){
        Pattern binnaryPattern = Pattern.compile("^[01]+[Bb]$");
        Matcher matcher = binnaryPattern.matcher(number);
        return matcher.matches();
    }

    private static boolean isOcta(String number){
        Pattern octaPatern = Pattern.compile("^[1-7][0-7]*[Oo]$");
        Matcher matcher = octaPatern.matcher(number);
        return matcher.matches();
    }

    private static boolean isDecimal(String number){
        Pattern decimalPatern = Pattern.compile("^[1-9][0-9]*[Dd]?$");
        Matcher matcher = decimalPatern.matcher(number);
        return matcher.matches();
    }

    private static boolean isHex(String number){
        Pattern decimalPatern = Pattern.compile("^[1-9][0-9]*[0-9a-fA-F]+[Hh]$");
        Matcher matcher = decimalPatern.matcher(number);
        return matcher.matches();
    }

    private static boolean isDouble(String number){
        Pattern decimalPatern = Pattern.compile("^[0-9]*\\.[0-9]*([Ee][\\-+]?)?[0-9]*$");
        Matcher matcher = decimalPatern.matcher(number);
        return matcher.matches();
    }

    private static boolean isOrder(String number){
        Pattern decimalPatern = Pattern.compile("^[1-9]?[0-9]*[Ee][\\-+]?[1-9][0-9]*$");
        Matcher matcher = decimalPatern.matcher(number);
        return matcher.matches();
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        numericList.toString();
        for (Numeric token: numericList){
            stringBuilder.append(token.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
