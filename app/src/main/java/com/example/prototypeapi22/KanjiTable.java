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

    KanjiTable(){
        // 漢字データを格納しておくもの（グローバル変数にしたい）
        ArrayList<String[]> kanjiList = new ArrayList<String[]>();
        BufferedReader buffReader = null;

        arr=new ArrayList<Integer>();
        for(int i=0; i<=1971; i++) arr.add(i);
        setNum(min, max, length);

        try {
            FileInputStream fileInput = new FileInputStream("pre1_reading.csv");	// ファイル読み込み
            InputStreamReader inputStream = new InputStreamReader(fileInput);
            buffReader = new BufferedReader(inputStream);

            String currentContent;
            int row = 0;
            //String[] arrayColumnName = null;

            while((currentContent = buffReader.readLine()) != null) {

                if(row == 0) {
                    // ラベル
                    //arrayColumnName = currentContent.split(",");
                    //for(String columnName : arrayColumnName) System.out.print(columnName + " ");
                    //System.out.println();

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

	/*
	void print(int index) {
		// データを取り出す例
        System.out.println("データの大きさ：" + kanjiTable.length + "x" + kanjiTable[0].length);
        int r = index;
        	for(int c=0; c<kanjiTable[0].length; c++) {
        		System.out.print(kanjiTable[r][c] + " ");
        	}
        	System.out.println();

	}


	void print(int min, int max) {
		// データを取り出す例
        System.out.println("データの大きさ：" + kanjiTable.length + "x" + kanjiTable[0].length);
        for(int r=min; r<kanjiTable.length; r++) {
        	for(int c=0; c<kanjiTable[0].length; c++) {
        		System.out.print(kanjiTable[r][c] + " ");
        	}
        	System.out.println();

        	if(r>=max-1) break;// 長いので中断
        }
	}
	*/


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
    }

    boolean equal(int index, String inputStr) {
        if(getYomi(index).equals(inputStr)) return true;
        else if(getYomi(index).equals(inputStr+getOkuri(index))) return true;
        else return false;
    }

	/*
	String get(int r, int c) {
		return kanjiTable[r][c];
	}
	*/

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


    String getNo(int index) {
        return kanjiTable[arr.get(index)][0];
    }


    String getWord(int index) {
        return kanjiTable[arr.get(index)][3];
    }


    String getYomi(int index) {
        return kanjiTable[arr.get(index)][4];
    }


    String getBun(int index) {
        return kanjiTable[arr.get(index)][5];
    }


    String getKanji(int index) {
        return kanjiTable[arr.get(index)][6];
    }


    String getHira(int index) {
        return kanjiTable[arr.get(index)][7];
    }


    String getOkuri(int index) {
        String res;
        res=kanjiTable[arr.get(index)][9];
        if(res.equals("X")) return "";
        else return res;
    }
}

