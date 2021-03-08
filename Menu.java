import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu{
   public static boolean boundary;
   public static int difficulty;
   public static int[] high_score = new int[5];
   private static File f1;

   public Menu(String dir){
      f1 = new File(dir + "/highscore.txt");
      if(!f1.exists()){
         try{
            f1.createNewFile();
         }
         catch (IOException e) {
            System.out.println("error: "+e);
         }
      }
      else{
         Path path = Paths.get(dir + "/highscore.txt");
         Charset charset = Charset.forName("ISO-8859-1");
         try {
            List<String> lines = Files.readAllLines(path, charset);
            int i = 0;
            Scanner s1;
            for (String line : lines) {
               if(i >= 5){
                  //System.out.println("error");
                  break;
               }
               s1 = new Scanner(line);
               high_score[i] = s1.nextInt();
               i++;
               //System.out.println(s1.nextInt());
            }
         } 
         catch (IOException e) {
            System.out.println("error: "+e);
         }
         f1.setReadOnly();
      }
      difficulty = 1;
      boundary = true;
   }
   
   public static void save(){
      f1.setWritable(true);
      if(f1.canWrite()){      
         try{
            FileWriter writer = new FileWriter(f1);
            for (int i : high_score) {
               writer.write(i + "\n");
            }
            writer.close();
         }
         catch (IOException e) {
            System.out.println(e);
         }
      }
      f1.setReadOnly();
   }
   
   public static void resetHS(){
      f1.delete();
      try{
         f1.createNewFile();
      }
      catch (IOException e) {
         System.out.println(e);
      }
      Arrays.fill(high_score,0);
   }
}
             
