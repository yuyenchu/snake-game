import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.ArrayList;






public class Snake{
   public static int len, Xvector , Yvector , points, speed, bonus, super_time;
   public static int[]fruit = new int[2];
   public static final int SIZE = 10;
   public static boolean changed, pause, start_new, is_boundary, is_super, fruit_super;
   public static ArrayList<Integer> xpt, ypt;
   public static Font f1, f2, f3;
   public static DrawingPanel p1 = new DrawingPanel(600,730);
   public static Graphics g1 = p1.getGraphics();
   public static Menu menu = new Menu(System.getProperty("user.dir"));
   public static Setting set = new Setting(p1,g1);
   
   public static void main(String[] args){
      initializeValue();
      p1.addKeyListener(new KeyListener(){
         int xvector = 0, yvector = 0;
         @Override
            public void keyPressed(KeyEvent e) {
               if (changed == false){
                  switch(e.getKeyCode()){
                     case KeyEvent.VK_UP:
                        yvector = -1;
                        break;
                     case KeyEvent.VK_DOWN:
                        yvector = 1;
                        break;
                     case KeyEvent.VK_RIGHT:
                        xvector = 1;
                        break;
                     case KeyEvent.VK_LEFT:
                        xvector = -1;
                        break;
                     case KeyEvent.VK_ESCAPE:
                        //set.drawSetting();
                        len = -1;
                        //pause = true;
                        break;
                     case KeyEvent.VK_SPACE:
                        /*len++;
                        int j = (int)xpt.get(xpt.size()-1);
                        xpt.add(j);
                        j = (int)ypt.get(ypt.size()-1);
                        //System.out.print(i);
                        ypt.add(j);*/
                        start_new = false;
                        //genFruit();
                        //System.out.print(start_new);
                        break;
                     case KeyEvent.VK_S:
                        pause = true;
                        break;
                     case KeyEvent.VK_N:
                        if(!start_new){
                           g1.setFont(f2);
                           g1.setColor(Color.GREEN);
                           g1.drawString("GAME OVER",200,300);
                           g1.setFont(f1);
                           start_new = true;
                        }
                        break;
                  }
                  if(xvector != -1*Xvector && Yvector != 0){
                     Xvector = xvector;
                     Yvector = 0;
                     yvector = 0;
                     changed = true;
                  }
                  else if(yvector != -1*Yvector){
                     Yvector = yvector;
                     Xvector = 0;
                     xvector = 0;
                     changed = true;
                  }
                  //System.out.print(Xvector+":");
                  //System.out.print(Yvector+"\n");
               }
            }
         @Override
                public void keyTyped(KeyEvent e) {
                }

         @Override
                public void keyReleased(KeyEvent e) {
                  //vector = 0;
                  //System.out.print(vector);
                }
      });
      
      int i = 0;
      while(len != -1){
         if(pause){
            changed = true;
            menu = set.drawSetting();
            changed = false;
            pause = false;
            i = 1;
         }
         //System.out.print(i);
         if(i <= 1){
            drawMenu("Press space to start the game!");
            if(i == 0 || start_new)
               i = 2;
         }
         try 
         {
            Thread.sleep(400);
         } 
         catch(InterruptedException e){}
         
         if(len == -1)
            System.exit(0);
         else{
            while(start_new != true){
               if(i > 0){
                  initialization(i);
                  i = 0;
               }
               p1.clear();
               //System.out.println("--"+len+"/x:"+xpt.size()+"/y:"+ypt.size()+"--");
               if(gameOver()){
                  g1.setFont(f2);
                  g1.setColor(Color.GREEN);
                  g1.drawString("GAME OVER",200,300);
                  g1.setFont(f1);
                  start_new = true;
                  newHS(points);
                  //System.out.println(start_new);
                  break;
               }
               else if(len == -1)
                  System.exit(0);
               else if(pause)
                  break;
               drawMenu("");
               drawFruit();
               drawSnake();
               moveSnake();
               /*if(xpt.size() < len && ypt.size() < len){
                  xpt.add(0);
                  ypt.add(0);
               }*/
               eat();
               changed = false;
         
               try 
               {
                  Thread.sleep(speed);
               } 
               catch(InterruptedException e)
               {
                  //System.out.println(e);
                  System.exit(1);
               // this part is executed when an exception (in this example InterruptedException) occurs
               }
               //System.out.print(xpt.get(i)+":"+ypt.get(i)+"\n");
            }
         }
      }
      System.exit(0);
   }
   
   
   public static void drawFruit(){
      if(fruit_super)
         g1.setColor(Color.RED);
      else
         g1.setColor(Color.ORANGE);
      g1.fillRect(fruit[0],fruit[1],SIZE,SIZE);
   }
   
   public static void drawMenu(String message){
      g1.setColor(Color.BLACK);
      g1.fillRect(0,600,600,130);
      g1.setColor(Color.WHITE);
      g1.drawString("Press ESC to quit/ S for setting/ N for new game", 100,625);
      g1.drawString("Points: "+points, 100,670);
      g1.drawString(message, 100,720);
      if(is_super){
         g1.setColor(Color.RED);
         g1.drawString("BONUS: "+bonus+"x", 400,625);
         g1.drawString("Time Left:"+(super_time/1000), 400,670);
      }
   }
  
   /*public static void drawPoint(Graphics g1){
      g1.setColor(Color.WHITE);
      g1.drawString("Points: "+points, 100,700);
   } */

   public static void drawSnake(){
      if(super_time <= 0){
         is_super = false;
         bonus = 1;
      }
      if(is_super){
         g1.setColor(Color.RED);
         super_time -= speed;
      }
      else
         g1.setColor(Color.GREEN);
      for(int i = 0; i<len; i++){
         g1.fillOval((int)xpt.get(i), (int)ypt.get(i), SIZE,SIZE);
      }
   }
   
   public static void eat(){
      int i = 0;
      if(xpt.get(0) == fruit[0] && ypt.get(0) == fruit[1]){
         if(is_super){
            len += 2*bonus;
            points += 10*(int)Math.pow(2,bonus);
            if(fruit_super){
               bonus++;
               super_time += 20000;
            }
         }
         else{
            len ++;
            points += 10;
         }
         for(int j = (int)xpt.size(); (int)xpt.size() <= len - 1; j++){
            i = (int)xpt.get(j-1);
            xpt.add(i);
            i = (int)ypt.get(j-1);
            //System.out.print(i);
            ypt.add(i);
         }
         if(fruit_super && !is_super){
            g1.setFont(f2);
            g1.setColor(Color.RED);
            g1.drawString("SUPER MODE",175,300);
            is_super = true;
            g1.setFont(f1);
            try 
            {
               Thread.sleep(1000);
            } 
            catch(InterruptedException e){}
            super_time += 60000;
         }
         fruit_super = false;
         genFruit();
      }
   }

   public static boolean gameOver(){
      if(len >3){
         for(int i = 1; i < xpt.size(); i++){
            if((int)xpt.get(0) == (int)xpt.get(i) && (int)ypt.get(0) == (int)ypt.get(i))
               return true;
         }
      }
      if(is_boundary == true){
         if((int)xpt.get(0) < 0 || (int)xpt.get(0) >= 600)
            return true;
         else if((int)ypt.get(0) < 0 || (int)ypt.get(0) >= 600)
            return true;
         else
            return false;
      }
      else
         return false;
   }

   public static void genFruit(){
      if((int)(Math.random()*100+1) < (21/menu.difficulty))
         fruit_super = true;
      fruit[0] = (int)(Math.random()*59+1)*10;
      fruit[1] = (int)(Math.random()*59+1)*10;
      for(int i = 0; i < (int)xpt.size() - 1; i++){
         if(fruit[0] == (int)xpt.get(i) && fruit[1] == (int)ypt.get(i))
            genFruit();
         else if(len == 3 && fruit[0] == 0 && fruit[1] == 0)
            genFruit();
      }
      //System.out.print(fruit[0]+"/"+fruit[1]+"\n");
   }
   
   public static void initialization(int i){
      is_boundary = menu.boundary;
      speed = 400/menu.difficulty;
      if(i == 2){
         len = 3;
         Xvector = 1;
         Yvector = 0;
         points = 0;
         bonus = 1;
         super_time = 0;
         is_super = false;
         fruit_super = false;
         xpt = new ArrayList<Integer>(3);
         ypt = new ArrayList<Integer>(3);
         while(xpt.size() < len && ypt.size() < len){
            xpt.add(0);
            ypt.add(0);
         }
         genFruit();
      }
   }
   
   public static void initializeValue(){
      changed = false;
      pause = false;
      start_new = true;
      is_super = false;
      fruit_super = false;
      f1 = g1.getFont();
      f2 = new Font(Font.DIALOG, Font.BOLD, 35);
      f3 = new Font(Font.DIALOG, Font.BOLD, 25);
   }
     
   public static void moveSnake(){
      int i = 0, j=0;
      for(i = len-1; i >= 1; i--){
         j = (int)xpt.get(i-1);
         xpt.set((i),j);
      }// && xpt.get(i) != NULL
      for(i = len-1; i >= 1; i--){
         j = (int)ypt.get(i-1);
         ypt.set((i),j);
      }// && ypt.get(i) != NULL
      i = 0;
      j = (int)xpt.get(i)+Xvector*SIZE;
      xpt.set(i,j);
      j = (int)ypt.get(i)+Yvector*SIZE;
      ypt.set(i,j); 
      if(is_boundary == false){
         if((int)xpt.get(i) >= 600)
            xpt.set(i,0);
         else if((int)xpt.get(i) < 0)
            xpt.set(i,590);
         if((int)ypt.get(i) >= 600)
            ypt.set(i,0);
         else if((int)ypt.get(i) < 0)
            ypt.set(i,590);
      }
   } 
   
   public static void newHS(int score){
      int i,j;
      if(score > menu.high_score[4]){
         for(i = 4; i>0 && score > menu.high_score[i-1]; i--){}
         j = i;
         for(i = 4;i >= j && i >= 1; i--){
            menu.high_score[i] = menu.high_score[i-1];
         }
         menu.high_score[j] = score;
         g1.setColor(Color.WHITE);
         g1.fillRect(200,270,215,35);
         g1.setColor(Color.RED);
         g1.setFont(f2);
         g1.drawString("NEW HIGH SCORE", 150,200);
         g1.fillRect(150,210,315,5);
         g1.setColor(Color.BLACK);
         g1.setFont(f3);
         String s1;
         for(i = 0; i <= 4; i++){
            s1 = "" + menu.high_score[i];
            g1.drawString(s1, 150,245 + i*25);
         }
         g1.setFont(f1);
         menu.save();
      }
   }      
}