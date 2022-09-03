package com.epam.rd.autotasks;

import java.math.BigInteger;
import java.util.StringJoiner;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    public boolean shoot(String shot) {
        BigInteger currentShots = BigInteger.valueOf(shots);
        BigInteger currentShips = BigInteger.valueOf(ships);

        Character[] alphabet = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

        // Відбір другої половини вхідної строки в якості індексу рядка
        Integer rowShotIndex = Integer.valueOf(shot.substring(1)) - 1;

        // Знайдений порядковий індекс символу в алфавіті являєтся номером колонки в грі
        int columnShotIndex = 0;
        for(int i = 0; i < alphabet.length; i++){
            if(
                alphabet[i].equals(shot.charAt(0))
            ) columnShotIndex = i;
        }
        //у бітах йде відлік у зворотню сторону
        int shotIndex = 63-(columnShotIndex+(8*rowShotIndex)); //64 - (8*rowShotIndex)+columnShotIndex;
        //запис вистрілу у змінну long
        shots = currentShots.setBit(shotIndex).longValue();

        //System.out.println("rowShotIndex " + rowShotIndex + " columnShotIndex " + columnShotIndex + " shotIndex " + shotIndex);

        if(currentShips.testBit(shotIndex)) return true;
        else return false;

    }

    public String state() {
        BigInteger currentShots = BigInteger.valueOf(shots);
        BigInteger currentShips = BigInteger.valueOf(ships);

        // Вихідна строка
        StringBuffer resString = new StringBuffer();
        int lineBreakIndex = 8;

        for(int index = 63; index >= 0; index--)
        {
            // Порівняння шаблону з кораблями до шаблону з вистрілами
            if(currentShots.testBit(index) && currentShips.testBit(index)) resString.append("\u2612");
            else if (currentShots.testBit(index)) resString.append("×");
            else if(currentShips.testBit(index))  resString.append("\u2610");
            else resString.append(".");


            //перенос строки
            if(index % 8 == 0) {
                resString.insert(lineBreakIndex, "\n");
                lineBreakIndex+=9;
            }

        }
       //System.out.println(resString.toString().strip() + " length" + resString.length());
        return resString.toString();



     /*
        https://www.rapidtables.com/convert/number/decimal-to-binary.html

        реалізація за допомогою строк

         String res = Long.toBinaryString(-1150950973573693440L);
        System.out.println(" Long to binary = " + res);

        StringBuffer j = new StringBuffer(res);

        //StringBuffer j = new StringBuffer(findTwoscomplement(new StringBuffer(res)));
        for(int row = 8; row <= 68; row+=9)
        {
            int i = 0;
           j.insert(row+i, "\n");
           i++;
        }
        System.out.println(j + " length" + j.length());
        return j.toString();*/
    }


}
