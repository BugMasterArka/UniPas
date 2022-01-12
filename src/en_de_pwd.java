import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

class encrypt{
    final private String normal_txt;
    private String count_txt;
    private int ci_count;
    private int conv_count;
    private int upperCount;
    private String out;
    final private String timeStamp_str;
    final private String dateStamp_str;
    public encrypt(String normal_txt) {
        this.normal_txt = normal_txt;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();
        timeStamp_str=dtf.format(now);
        dateStamp_str=dtf2.format(now);
        //System.out.println("string time "+timeStamp_str);
        //System.out.println("string date "+dateStamp_str);
    }
    public void counterAssignment(){
        int timeStamp_int=Integer.parseInt(timeStamp_str);
        int dateStamp_int=Integer.parseInt(dateStamp_str);
        //System.out.println("int time "+timeStamp_int);
        //System.out.println("int date "+dateStamp_int);
        int sumTime=0;
        while(timeStamp_int!=0){
            sumTime+=timeStamp_int%10;
            timeStamp_int/=10;
        }
        //System.out.println("sum of time stamp digits "+sumTime);
        int tenSum=sumTime/10;
        //System.out.println("ten sum "+tenSum);
        int unitSum=sumTime%10;
        //System.out.println("unit sum "+unitSum);
        ci_count=((int)(Math.random()*(tenSum+1)*10)%10);
        if(ci_count==0){
            int rand = 0;
            while (true){
                Random random=new Random();
                rand = random.nextInt(8);
                if(rand !=0) break;
            }
            ci_count=(dateStamp_int/(int)Math.pow(10,rand))%10;
        }
        System.out.println("cipher count "+ci_count);
        upperCount=((int)(Math.random()*(unitSum+1)*10)%10);
        if(upperCount==0){
            int rand = 0;
            while (true){
                Random random=new Random();
                rand = random.nextInt(8);
                if(rand !=0) break;
            }
            upperCount=((dateStamp_int/(int)Math.pow(10,rand))%10)/2;
        }
        System.out.println("upper count "+upperCount);
        conv_count=(ci_count+upperCount)%3;
        if((ci_count+upperCount)%3==0)
            conv_count=(ci_count+upperCount)%4; // TODO: 12-01-2022 change this method of choosing conversion count because it is creating too many numbers in the final output ;
        System.out.println("Conversion count "+conv_count);
    }
    public void encrypt_txt(){
        int[] cipherInc=new int[normal_txt.length()];
        char[] txt_char=normal_txt.toCharArray();
//        System.out.print("char array before cipher ");
//        for(char n:txt_char)
//            System.out.print(n+" ");
//        System.out.println();
        int count=0;
        for(char ch:txt_char){
            if(Character.isUpperCase(ch))
                cipherInc[count]=((int)ch)-65;
            else if (Character.isLowerCase(ch))
                cipherInc[count]=((int)ch)-97;
            else if (Character.isWhitespace(ch))
                cipherInc[count]=256;
            count++;
        }
//        System.out.print("int array before cypher ");
//        for(int n:cipherInc)
//            System.out.print(n+" ");
//        System.out.println();
        count=0;
        for(int n:cipherInc){
            cipherInc[count]=(cipherInc[count]+ci_count)%26;
            count++;
        }
//        System.out.print("int array after cypher ");
//        for(int n:cipherInc)
//            System.out.print(n+" ");
//        System.out.println();
        count=0;
        String[] outputChar=new String[cipherInc.length];
        for(int n:cipherInc){
            if(count%conv_count==0){
                if (count==upperCount)
                    outputChar[count]=(Character.toString((char) (n+65))).toUpperCase();
                else
                    outputChar[count]=(Character.toString((char) (n+97))).toLowerCase();
            }
            else
                outputChar[count]=Integer.toString(n);
            count++;
        }
        for(int i=0;i<outputChar.length;i++){
            if(i==0)
                out=outputChar[i];
            else
                out=out.concat(outputChar[i]);
        }
    }
    public String getOut() {
        return out;
    }
}
public class en_de_pwd {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter String");
        String input=sc.nextLine();
        encrypt obj=new encrypt(input);
        obj.counterAssignment();
        obj.encrypt_txt();
        String output=obj.getOut();
        System.out.println(output);
    }
}
