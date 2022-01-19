package com.icycraft.mymem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MymemApplicationTests {




    @Test
    void contextLoads() throws IOException {

        ArrayList<String> list = new ArrayList<>();
        String fileName = "C:/Users/ky/Desktop/test.txt";
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String text = "";
        while((line = br.readLine()) != null){
            if (line.contains("图片")) continue;
            if (line.contains("Chapter")) continue;
            // 一行一行地处理...
            if (!line.contains("\\.")){
                text+=line;
            }else {
                list.add(text);
                text="";
            }
        }
        System.out.println();


    }

}
