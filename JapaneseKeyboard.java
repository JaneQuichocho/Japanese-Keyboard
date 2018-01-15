import java.util.*;
import java.io.*;

public class JapaneseKeyboard {
   
   public static void main(String[] args) throws FileNotFoundException {
      Scanner input = new Scanner(new File("data/JapaneseLetters.txt"), "UTF-8");
      Scanner console = new Scanner(System.in);
      Map<String, String> map = new HashMap<String, String>();
      boolean loop = true;
      boolean open = true;
      while (input.hasNextLine()) {
         map.put(input.next(), input.next());
      }
      while (loop) {
         String type = console.nextLine();
         System.out.println(read(map, type, open));
         open = true;
      }
   }
   
   public static String read(Map<String, String> map, String type, boolean open) {
      String translate = "";
      String result = "";
      for (int i = 0; i < type.length(); i++) {
         translate += type.charAt(i);
         if ((translate.equals("n") || translate.equals("N") )&& i != type.length() - 1 && 
             map.containsKey(translate + type.charAt(i + 1))) {
             translate += type.charAt(i + 1);
             i++;    
         }
         if (translate.equals("\"")) {
            if (open) {
               result += "「";
               open = false;
            } else {
               result += "」";
               open = true;
            }
            translate = "";
         } else if (translate.length() == 3 && 
             translate.charAt(0) == translate.charAt(1) && 
             translate.charAt(2) != translate.charAt(0)) {
            if (translate.charAt(1) < 97) {
               result += map.get("STOP");
            } else {
               result += map.get("stop");
            }
            if (map.containsKey(translate.substring(1))) {
               result += map.get(translate.substring(1));
               translate = "";
            } else {
               translate = translate.substring(1);
            }
         } else if (map.containsKey(translate)) {
            result += map.get(translate);
            translate = "";
         } else if (translate.length() == 3) {
            result += translate.substring(0, 1) + read(map, type.substring(i - 1), open);
            i = type.length() - 1;
         } else if (translate.equals(" ")) {
            result += " ";
            translate = "";
         } else if (i == type.length() - 1) {
            result += translate;
         }
      }  
      if (result.equals("")) {
         return type;
      }
      return result;
   }
   
}