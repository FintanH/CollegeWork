import java.io.*;
public class FileInput {
private String[][] translate;

public String[][] createTable() {
	int i = 0;
    File file = new File("C:\\Users\\Fintan\\Desktop\\MorseCode.txt");
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
    
    try {
      fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

      // dis.available() returns 0 if the file does not have more lines.
      while (dis.available() != 0) {

      // this statement reads the line from the file and print it to
        // the console.
        dis.readLine();
        i++;
      }

      translate = new String[i][2];

      fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);
      String splitter;
      int j = 0;
      while (dis.available() != 0) {

          // this statement reads the line from the file and print it to
            // the console.
            splitter = (dis.readLine());
            translate[j][0] = splitter.substring(0,1);
            if(splitter.length() != 1)
            {
            	translate[j][1] = splitter.substring(2,splitter.length());
            }else{
            	translate[j][1] = "";
            }
            j++;
      }
      
      // dispose all the resources after using them.
      fis.close();
      bis.close();
      dis.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return translate;
  }
}
