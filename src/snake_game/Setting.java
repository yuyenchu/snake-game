import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;






public class Setting{
   public static DrawingPanel p1;
   public static Graphics g1;
   public static Menu m1;
   private static int y = 0;
   private static int[] x = new int[2];
   private static Map<Integer, String> difficulty = new HashMap<Integer, String>(){{  
     put(1, "Easy");
     put(2, "Normal");
     put(3, "Hard");
     put(4, "Hell");
   }};
   private static boolean boundary = true, is_on = false, run; 
 
   
   public Setting(DrawingPanel pp,Graphics gg){
      p1 = pp;
      g1 = gg;
      x[0] = 1;
      x[1] = 1;
   }
   
   /*public static void main(String[] args){
      for(int i = 1; i <= 4;i++){
      System.out.println(difficulty.get(i));}}*/
   
   
   public static Menu drawSetting(){
      boundary = m1.boundary;
      x[0] = m1.difficulty;
      is_on = true;
      p1.addKeyListener(new KeyListener(){
         @Override
            public void keyPressed(KeyEvent e) {
               if(is_on){
                  switch(e.getKeyCode()){
                     case KeyEvent.VK_UP:
                        y--;
                        if(y <= -1)
                           y = 1;
                        break;
                     case KeyEvent.VK_DOWN:
                        y++;
                        if(y >= 2)
                           y = 0;
                        break;
                     case KeyEvent.VK_RIGHT:
                        x[y]++;
                        if(x[1] >= 2)
                           x[1] = 0;
                        else if(x[0] >= 5)
                           x[0] = 1;
                        break;
                     case KeyEvent.VK_LEFT:
                        x[y]--;
                        if(x[0] <= 0)
                           x[0] = 4;
                        else if(x[1] <= -1)
                           x[1] = 1;
                        break;
                     case KeyEvent.VK_Q:
                        run = false;
                        break;
                     case KeyEvent.VK_SPACE:
                        break;
                  }
                  //System.out.println(y+":"+x[y]);
               }
            }
         @Override
             public void keyTyped(KeyEvent e) {
             }

         @Override
             public void keyReleased(KeyEvent e) {
             }
      });
      run = true;
      while(true){
         if(!run)
            break;
         if(x[1] == 1)
            boundary = true;
         else if(x[1] == 0)
            boundary = false;
         draw(x[0],y);
         try 
         {
            Thread.sleep(300);
         } 
         catch(InterruptedException e)
         {
            System.out.println(e);
         }
      }
      //System.out.println(y);
      m1.difficulty = x[0];
      m1.boundary = boundary;
      is_on = false;
      return m1;
   }

   private static void draw(int x,int y){
      g1.setColor(Color.BLACK);
      g1.fillRect(0,600,600,130);
      g1.setColor(Color.GRAY);
      g1.fillRect(217,613+y*45,45,17);
      g1.setColor(Color.WHITE);
      g1.drawString("Difficulty", 150,625);
      g1.drawString(difficulty.get(x), 220,625);
      g1.drawString("Boundary    "+boundary, 150,670);
      g1.drawString("Press Q to quit setting", 150,715);
   }
}