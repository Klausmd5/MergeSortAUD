package net.htlgrieskirchen.aud.mergesort;

import net.htlgrieskirchen.aud.Array;
import net.htlgrieskirchen.aud.IArray;

import java.util.Arrays;

public class Mergesort {

    public static <T extends Comparable<T>> Array<Integer> sort(Array<T> a) {

        int half = (a.size() /2);

        if(a.size() > 2) {
            // Teilen
            Array<Integer> first = (Array<Integer>) a.subArray(0, half);
            Array<Integer> sec = (Array<Integer>) a.subArray(half, a.size()- 1);

            first = sort(first);
            sec = sort(sec);

            first.append(sec);

            return first;
        } else {
            //zusammenbauen
            Integer[] i = (Integer[]) a.getArray();
            Arrays.sort(i);
            return new Array<Integer>(i);
        }
    }

    public static <T extends Comparable<T>> IArray<T> sort2(IArray<T> a) {
        if(a.size() > 2) {
            //Splitten
            int firstLength = a.size() / 2;
            int secondLength = firstLength * 2 == a.size() ? firstLength:firstLength+1;
            assert firstLength+secondLength == a.size();

            IArray<T> firstArray = a.subArray(0, firstLength);
            IArray<T> secondArray = a.subArray(firstLength, secondLength);

            firstArray = sort2(firstArray);
            secondArray = sort2(secondArray);

            assert firstArray.size() == firstLength;
            assert secondArray.size() == secondLength;

            System.out.println("!"+firstArray.size()+"!"+secondArray.size());
            //Mergen
            firstArray.append(secondArray);
            System.out.println("?"+firstArray.size());

            for(int i = 0; i < secondLength; i++) {
                System.out.println("::"+firstArray.size()+"::"+i+" "+(i+firstLength));
                if(i == firstLength) {
                    continue;
                }
                if(firstArray.compare(i,i+firstLength) > 0) {
                    firstArray.swap(i,i+firstLength);
                }
            }

            return firstArray;
        } else {
            if(!(a.size() < 2) && a.compare(0,1) > 0) {
                a.swap(0,1);
            }
            return a;
        }
    }
}
























