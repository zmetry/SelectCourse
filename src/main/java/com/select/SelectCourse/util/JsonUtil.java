package com.select.SelectCourse.util;


import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;


import java.io.*;

@Data
@Component
public class JsonUtil {

    private static final String CoursefileName = "selectCourse.json";
    private static final String SelectorfileName = "CourserSelctor.json";


    private final static String path = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\";


    private String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(path + fileName);

            if (!jsonFile.exists())
                jsonFile.createNewFile();

            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void WriteJsonFile(String str,String fileName){
        try {
            File jsonFile = new File(path + fileName);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonFile), "UTF-8");
            osw.write(str);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //管理员界面
    public String getSelectCourseJson(){
        return readJsonFile(CoursefileName);
    }

    public void WriteSelectCourseJson(String str){
        WriteJsonFile(str,CoursefileName);
    }

    //学生界面
    //写入学生管理的选课的<Course,<Teacher,List<Student>> 这个hashmap
    public String GetSelector(){
        return readJsonFile(SelectorfileName);
    }

    public void WriterSelectorJson(String str){
        WriteJsonFile(str,SelectorfileName);
    }


}
