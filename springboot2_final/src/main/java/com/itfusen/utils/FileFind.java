package com.itfusen.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @desc:
 * @auth: lifusen
 * @time: 2020-5-31 10:43
 */
public class FileFind {

        private static final String PATH = "C:\\Users\\lifusen_pls\\Desktop\\BB";// 检索目录
        private static final List<String> suffixList = new ArrayList<>();// 需要统计的文件名后缀
        private static final Set<File> fileList = new HashSet<>();// 匹配的文件名
        private static final String content = "04A0A0411030189B104Q2DUV";// 需要查找的内容
        private static int fileCount = 0;

        static {
            suffixList.add("xml");// 从后缀为.setting的文件中进行查找
        }

        public static void main(String[] args) {
            long s = System.currentTimeMillis();
            File file = new File(PATH);
            getAllFile(file);
            if (fileList != null && fileList.size() > 0) {
                System.out.println("一共查询 "+fileCount+" 个文件");
                System.out.println("含有 " + content + " 内容的文件如下：");
                for (File f : fileList) {
                    System.out.println(f.getAbsolutePath());
                }
            } else {
                System.err.println("无文件含有 " + content + " 内容");
            }
            long e = System.currentTimeMillis();
            System.out.println("\n总耗时：" + (e - s));
        }

        public static void getAllFile(File file) {
            BufferedReader br = null;
            String s = null;
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    getAllFile(f);
                }
            } else {
                if (file.getName().indexOf(".") != -1) {
                    String suffix = file.getName().substring(
                            file.getName().lastIndexOf(".") + 1);

                    if (suffixList.contains(suffix)) {
                        System.out.println(file.getName());
                        fileCount++;
                        try {
                            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-16"));
                            while ((s = br.readLine()) != null) {
                                if (s.contains(content)) {
                                    fileList.add(file);
                                    break;
                                }
                            }
                            br.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
