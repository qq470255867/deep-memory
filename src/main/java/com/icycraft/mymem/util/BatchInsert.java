package com.icycraft.mymem.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatchInsert {

    public static void main(String[] args) throws Exception{
        ArrayList<String> list = new ArrayList<>();
        String fileName = "C:/Users/ky/Desktop/20210318.txt";
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String text = "";
        while((line = br.readLine()) != null){
            if (line.contains("图片")) continue;
            if (line.contains("Chapter")) continue;
            if (line.contains("CHAPTER")) continue;
            if (line.contains("chapter")) continue;
            // 一行一行地处理...
            Pattern p = Pattern.compile("^.*\\d+.*");
            Matcher m = p.matcher(line);
            if (!m.matches()) {
//            if (!line.contains(".")){
                text+=line;
            }else if (!StringUtils.isEmpty(text)){
                list.add(text);
                text="";
            }
        }
        System.out.println();
    }

}
