package com.chief.evo.dto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RoadParser {
    private static final String rouletteWinNumberCharList = "0一二三四五六七八九十要而闪诗无牛器把就是药儿山时吴妞起爸救市咬耳散使唔钮";
    static class RS {
        byte[] bytes ;
        String bitString = "" ;
        int pointer = 0;
        public RS(String base64Str) {
            bytes = Base64.getDecoder().decode(base64Str);
            this.bitString = bytesToBinary(bytes);
        }
        public int length() {
            return bitString.length();
        }

        public int getNextInteger(int e) {
            int t = Integer.parseInt(this.bitString.substring(this.pointer, e+this.pointer), 2);
            this.pointer += e;
            return t;
        }


        public static String bytesToBinary(byte[] bytes) {
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes) {
                int val = b;
                for (int i = 0; i < 8; i++) {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
            }
            return binary.toString();
        }
    }
    public static class RouletteParser {
        public static List<Integer> parseBoot(String data) {
            List<Integer> result = new ArrayList<>();
            RS rs = new RS( data);
            int version = rs.getNextInteger(8);
            int size = rs.getNextInteger(8) * rs.getNextInteger(8);

            for (; size > 0; ) {
                Integer e = parseSingle(rs);
                if(e != null)
                    result.add(e);
                size--;
            }
            return result;
        }
        public static Integer parseSingle(RS rs) {
            boolean t = 1 != rs.getNextInteger(1);
            int a = rs.getNextInteger(6);
            return t ? null : a;
        }
    }
    public static class ColorDiskParser{
        public static List<Integer> parseBoot(String data) {
            List<Integer> result = new ArrayList<>();
            RS rs = new RS( data);
            int version = rs.getNextInteger(8);
            int size = rs.getNextInteger(8) * rs.getNextInteger(8);

            for (; size > 0; ) {
                Integer e = parseSingle(rs);
                if(e != null)
                    result.add(e);
                size--;
            }
            return result;
        }
        public static Integer parseSingle(RS rs) {
            boolean t = 1 != rs.getNextInteger(1);
            int a = rs.getNextInteger(3);
            return t ? null : a;
        }
    }

    public static class SicboParser {
        public static List<List<Integer>> parseBoot(String data) {
            List<List<Integer>> result = new ArrayList<>();
            RS rs = new RS( data);
            int version = rs.getNextInteger(8);
            int size = rs.getNextInteger(8) * rs.getNextInteger(8);

            for (; size > 0; ) {
                List<Integer> e = parseSingleColumn(rs);
                if(e != null)
                    result.add(e);
                size--;
            }
            return result;
        }
       static List<Integer> parseSingleColumn(RS e) {
        List<Integer> t = new ArrayList<>();
            if (1 == e.getNextInteger(1)) {
            Integer a = e.getNextInteger(3);
                t.add( a);
            Integer s = e.getNextInteger(3);
                t.add( s);
            Integer i = e.getNextInteger(3);
                t.add( i);
            Integer n = e.getNextInteger(5);
                t.add(n);
            Integer l = e.getNextInteger(2);
                t.add(l);
            Integer o = 0;
                o = a == s && s == i ? 2 : (a + s + i) % 2 == 0 ? 1 : 0;
                        t.add( o);
            }
            return t;
        }
    }
    public static void main(String[] args) {
        String winNumberRoad = "fgYHpY49bBHoQ4V2FVyU8FmvcmULtKnGr4sN2xfvULWOLkw0poAAAAwaBgw";


        // Step 1: Base64 Decode
        List<Integer> result = RouletteParser. parseBoot(winNumberRoad);
        System.out.println("Roulette Road: " + result);

        System.out.println("Color Disk Road : " + ColorDiskParser.parseBoot("lgYGupqqqqqam5q6u5u6upqqsAAAAAAAAAA="));
    }


}

