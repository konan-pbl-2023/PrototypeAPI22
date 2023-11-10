package com.example.prototypeapi22;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class KanjiTable {
    // 参考：https://style.potepan.com/articles/18230.html
    String[][] kanjiTable;
    ArrayList<Integer> arr;
    int min=0;
    int max=1628;
    int length=10;
    String[] kaitoTable;
    int nowIndex=0;


    KanjiTable(){
        // 漢字データを格納しておくもの
        ArrayList<String[]> kanjiList = new ArrayList<String[]>();
        BufferedReader buffReader = null;

        arr=new ArrayList<Integer>();
        for(int i=0; i<=1971; i++) arr.add(i);
        setNum(min, max, length);

        try {
            FileInputStream filekaito = new FileInputStream("pre1_reading.csv");	// ファイル読み込み
            InputStreamReader InputStream = new InputStreamReader(filekaito);
            buffReader = new BufferedReader(InputStream);

            String currentContent;
            int row = 0;
            /*
             * String[] arrayColumnName = null;
             */

            while((currentContent = buffReader.readLine()) != null) {

                if(row == 0) {
                    // ラベル
                    /*
                     * arrayColumnName = currentContent.split(",");
                     * for(String columnName : arrayColumnName) System.out.print(columnName + " ");
                     * System.out.println();
                     */

                } else {
                    // 一行分のデータ
                    String[] arrayColumnData = currentContent.split(",");
                    kanjiList.add(arrayColumnData);
                }

                row++;
            }

            kanjiTable = new String[kanjiList.size()][kanjiList.get(0).length];
            for(int r=0; r<kanjiTable.length; r++) kanjiTable[r] = kanjiList.get(r);

        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try{
                buffReader.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    void setNum(int min, int max, int length) {
        this.min=min;
        this.max=max;
        this.length=length;

        arr=new ArrayList<Integer>();
        for(int i=min; i<=max; i++) arr.add(i);

        this.reload();
    }


    void reload() {
        Collections.shuffle(arr);
        kaitoTable = new String[length];
        nowIndex=0;
    }


    boolean check() {
        if(getYomi(nowIndex).equals(kaitoTable[nowIndex])) return true;
        else if(getYomi(nowIndex).equals(kaitoTable[nowIndex]+getOkuri(nowIndex))) return true;
        else return false;
    }
    boolean check(int index) {
        if(getYomi(index).equals(kaitoTable[index])) return true;
        else if(getYomi(index).equals(kaitoTable[index]+getOkuri(index))) return true;
        else return false;
    }
    boolean check(int index, String kaitoStr) {
        if(getYomi(index).equals(kaitoStr)) return true;
        else if(getYomi(index).equals(kaitoStr+getOkuri(index))) return true;
        else return false;
    }


    /*
     * 0	1		2	3		4			5							6		7			8		9
     *
     * No	Bunrui	Lv	Word	Yomi		Bun							Kanji	Hira				Okuri
     * 59	a音読み	A	造詣	ぞうけい	文化人類学に造詣が深い。	造詣	ぞうけい	X		X
     * 1030	b訓読み	C	摑む	つかむ		まるで雲を摑むような話だ。	摑		つか		X		む
     *
     *
     * No	Bunrui		Lv	Word		Yomi			Bun									Kanji1	Hira1	Kanji2	Hira2
     * 1654	d四字熟語	A	輪廻転生	りんねてんせい	生死が繰り返されるという仏教の教え	輪廻	りんね	転生	てんせい
     */


    void setIndex(int index) {
        nowIndex = index;
    }


    int getIndex() {
        return nowIndex;
    }


    void nextIndex() {
        nowIndex++;
    }


    int getLength() {
        return length;
    }


    void setKaito(String kaito) {
        kaitoTable[nowIndex] = kaito;
    }
    void setKaito(int index, String kaito) {
        kaitoTable[index] = kaito;
    }


    String getKaito() {
        return kaitoTable[nowIndex];
    }
    String getKaito(int index) {
        return kaitoTable[index];
    }


    String getNo() {
        return kanjiTable[arr.get(nowIndex)][0];
    }
    String getNo(int index) {
        return kanjiTable[arr.get(index)][0];
    }


    String getWord() {
        return kanjiTable[arr.get(nowIndex)][3];
    }
    String getWord(int index) {
        return kanjiTable[arr.get(index)][3];
    }


    String getYomi() {
        return kanjiTable[arr.get(nowIndex)][4];
    }
    String getYomi(int index) {
        return kanjiTable[arr.get(index)][4];
    }


    String getBun() {
        return kanjiTable[arr.get(nowIndex)][5];
    }
    String getBun(int index) {
        return kanjiTable[arr.get(index)][5];
    }


    String getKanji() {
        return kanjiTable[arr.get(nowIndex)][6];
    }
    String getKanji(int index) {
        return kanjiTable[arr.get(index)][6];
    }


    String getHira() {
        return kanjiTable[arr.get(nowIndex)][7];
    }
    String getHira(int index) {
        return kanjiTable[arr.get(index)][7];
    }


    String getOkuri() {
        String res;
        res=kanjiTable[arr.get(nowIndex)][9];
        if(res.equals("X")) return "";
        else return res;
    }
    String getOkuri(int index) {
        String res;
        res=kanjiTable[arr.get(index)][9];
        if(res.equals("X")) return "";
        else return res;
    }


}
