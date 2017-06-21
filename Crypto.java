/*

    Author:         Huiliang
    Crypto.java
 
*/


import java.util.*;

public class Crypto{

    //Initial some requires tables.
    
    //initial the pre-round key table
    private static byte[]  P_key = {
        57, 49, 41, 33, 25, 17, 9,
        1,  58, 50, 42, 34, 26, 18,
        10, 2,  59, 51, 43, 35, 27,
        19, 11, 3,  60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7,  62, 54, 46, 38, 30, 22,
        14, 6,  61, 53, 45, 37, 29,
        21, 13, 5,  28, 20, 12, 4
    };
    
    //initial the left shift table
    private static byte[] L_shift = {
        1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    
    //second round permutation for the 56-bits key to 48 bits
    private static byte[] second_p = {
        14, 17, 11, 24, 1,  5,
        3,  28, 15, 6,  21, 10,
        23, 19, 12, 4,  26, 8,
        16, 7,  27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
    };
    
    //initial permutation IP of 64 bits of plaintext
    private static byte[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9,  1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };
    
    // E selection table
    private static byte[] sel_table = {
        32, 1,  2,  3,  4,  5,
        4,  5,  6,  7,  8,  9,
        8,  9,  10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };
    
    
    // S_box for s1-s8
    private static byte[][] S = {
        {14, 4,  13, 1,  2,  15, 11, 8,  3,  10, 6,  12, 5,  9,  0,  7,
        0,  15, 7,  4,  14, 2,  13, 1,  10, 6,  12, 11, 9,  5,  3,  8,
        4,  1,  14, 8,  13, 6,  2,  11, 15, 12, 9,  7,  3,  10, 5,  0,
        15, 12, 8,  2,  4,  9,  1,  7,  5,  11, 3,  14, 10, 0,  6,  13},
    
        {15, 1,  8,  14, 6,  11, 3,  4,  9,  7,  2,  13, 12, 0,  5,  10,
        3,  13, 4,  7,  15, 2,  8,  14, 12, 0,  1,  10, 6,  9,  11, 5,
        0,  14, 7,  11, 10, 4,  13, 1,  5,  8,  12, 6,  9,  3,  2,  15,
        13, 8,  10, 1,  3,  15, 4,  2,  11, 6,  7,  12, 0,  5,  14, 9},
    
        {10, 0,  9,  14, 6,  3,  15, 5,  1,  13, 12, 7,  11, 4,  2,  8,
        13, 7,  0,  9,  3,  4,  6,  10, 2,  8,  5,  14, 12, 11, 15, 1,
        13, 6,  4,  9,  8,  15, 3,  0,  11, 1,  2,  12, 5,  10, 14, 7,
        1,  10, 13, 0,  6,  9,  8,  7,  4,  15, 14, 3,  11, 5,  2,  12},
    
        {7,  13, 14, 3,  0,  6,  9,  10, 1,  2,  8,  5,  11, 12, 4,  15,
        13, 8,  11, 5,  6,  15, 0,  3,  4,  7,  2,  12, 1,  10, 14, 9,
        10, 6,  9,  0,  12, 11, 7,  13, 15, 1,  3,  14, 5,  2,  8,  4,
        3,  15, 0,  6,  10, 1,  13, 8,  9,  4,  5,  11, 12, 7,  2,  14},
    
        {2,  12, 4,  1,  7,  10, 11, 6,  8,  5,  3,  15, 13, 0,  14, 9,
        14, 11, 2,  12, 4,  7,  13, 1,  5,  0,  15, 10, 3,  9,  8,  6,
        4,  2,  1,  11, 10, 13, 7,  8,  15, 9,  12, 5,  6,  3,  0,  14,
        11, 8,  12, 7,  1,  14, 2,  13, 6,  15, 0,  9,  10, 4,  5,  3},
    
        {12, 1,  10, 15, 9,  2,  6,  8,  0,  13, 3,  4,  14, 7,  5,  11,
        10, 15, 4,  2,  7,  12, 9,  5,  6,  1,  13, 14, 0,  11, 3,  8,
        9,  14, 15, 5,  2,  8,  12, 3,  7,  0,  4,  10, 1,  13, 11, 6,
        4,  3,  2,  12, 9,  5,  15, 10, 11, 14, 1,  7,  6,  0,  8,  13},
    
        {4,  11, 2,  14, 15, 0,  8,  13, 3,  12, 9,  7,  5,  10, 6,  1,
        13, 0,  11, 7,  4,  9,  1,  10, 14, 3,  5,  12, 2,  15, 8,  6,
        1,  4,  11, 13, 12, 3,  7,  14, 10, 15, 6,  8,  0,  5,  9,  2,
        6,  11, 13, 8,  1,  4,  10, 7,  9,  5,  0,  15, 14, 2,  3,  12},
    
        {13, 2,  8,  4,  6,  15, 11, 1,  10, 9,  3,  14, 5,  0,  12, 7,
        1,  15, 13, 8,  10, 3,  7,  4,  12, 5,  6,  11, 0,  14, 9,  2,
        7,  11, 4,  1,  9,  12, 14, 2,  0,  6,  10, 13, 15, 3,  5,  8,
        2,  1,  14, 7,  4,  10, 8,  13, 15, 12, 9,  0,  3,  5,  6,  11}
    };
    
    //permutation table P of f function
    private static byte[] P = {
        16, 7,  20, 21,
        29, 12, 28, 17,
        1,  15, 23, 26,
        5,  18, 31, 10,
        2,  8,  24, 14,
        32, 27, 3,  9,
        19, 13, 30, 6,
        22, 11, 4,  25
    };
    
    //final permutation
    private static byte[] FP = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };
    
    // store left and right half of key
    private static int[] C = new int[28];
    private static int[] D = new int[28];
    //store left and rifht half of plaintext
    private static int[] L = new int[32];
    private static int[] R = new int[32];

    public static void main(String args[]){
        
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println("*********************************************************");
        System.out.println("************************   DES  *************************");
        System.out.println("*********************************************************");
        System.out.println("");

        System.out.println("enter 64 bits plaintext: ");

        String input = scan.nextLine();
        StringTokenizer strToken = new StringTokenizer(input);
        int count = strToken.countTokens();
        System.out.println("count: " + count);
        

        int[] plaintext = new int[count];
        for(int i=0; i < count; i++){
        
            plaintext[i] = Integer.parseInt((String)strToken.nextElement());
        }
        
        System.out.print("{");
        for(int j = 0; j < plaintext.length; j++){
            
            System.out.print("" + plaintext[j] + ",");
        }// end for
        System.out.println("}");
        
        
        
        int[] key = new int[count];
        System.out.println("");
        System.out.println("enter 64 bits key: ");
        
        String key_in = scan.nextLine();
        StringTokenizer strToken1 = new StringTokenizer(key_in);
        int count1 = strToken1.countTokens();
        System.out.println("count: " + count1);
        
        
        for(int i=0; i < count1; i++){
            
            key[i] = Integer.parseInt((String)strToken1.nextElement());
        }
        
        System.out.print("{");
        for(int j = 0; j < key.length; j++){
            System.out.print("" + key[j] + ",");
        }// end for
        System.out.println("}");
        
        System.out.println("Processing DES...");
        System.out.println("...");
        System.out.println("...");
        System.out.println("");

        int ciphertext[] = DES(plaintext,key);
        System.out.println("");
        System.out.println("*********************************************************");
        System.out.println("************************   ECB  *************************");
        System.out.println("*********************************************************");
        System.out.println("");

        
        String s1 = "I LOVE SECURITY";
        String s2 = "ABCDEFGH";
        String IV = "ABCDEFGH";
        int ecb[] = ECB(s1,s2);
        System.out.println("");
        System.out.println("*********************************************************");
        System.out.println("************************   CBC  *************************");
        System.out.println("*********************************************************");
        System.out.println("");
        int cbc[] = CBC(s1,s2,IV);
        System.out.println("");

        

    }//end main
    
    
    
    //*****************************************************************
    //*****************     DES         *******************************
    //*****************************************************************
    
    
    
    private static int[] DES(int[] plaintext, int[] key){
        
        if(plaintext.length == 64 && key.length == 64){
            
            //reduce key from 64 to 56 use P_key table
            for(int i = 0; i < 28; i++){
                C[i] = key[P_key[i] - 1];
            }
            
            //  System.out.println("Processing DES...1");
            
            for(int i = 28; i < 56; i++){
                D[i-28] = key[P_key[i] -1];
            }
            // System.out.println("Processing DES...2");
            
            
            //initial an arry to store the first permutation of plaintext
            int[] pre_plaintext = new int[plaintext.length];
            for(int i =0; i < plaintext.length; i++){
                pre_plaintext[i] = plaintext[IP[i] - 1];
            }
            // System.out.println("Processing DES...3");
            
            // divide plaintext in left and right array
            System.arraycopy(pre_plaintext, 0, L, 0, 32);
            System.arraycopy(pre_plaintext, 32, R, 0, 32);
            
            // System.out.println("Processing DES...4");
            
            //f_function
            
            for(int i = 0; i < 16; i++){
                // System.out.println("Processing DES...5");
                
                //expand from 32 to 48 bits
                int[] expand = new int[48];
                for(int j = 0; j < 48; j++){
                    expand[j] = R[sel_table[j] - 1];
                    
                }
                // System.out.println("Processing DES...6");
                
                int[] K = new int[48];
                K = key_cal(key, i);
                
                int E_xor[] = xor(expand,K);
                //System.out.println("Processing DES...7");
                
                int cal[] = S_box(E_xor);
                // System.out.println("Processing DES...8");
                
                int[] new_R = new int[0];
                new_R = xor(L, cal);
                //System.out.println("Processing DES...9");
                
                L = R;
                R = new_R;
            }
            
            int plaintext_final[] = new int[64];
            System.arraycopy(R,0,plaintext_final,0,R.length);
            System.arraycopy(L,0,plaintext_final,32,L.length);
            
            
            int ciphertext1[] = new int[64];
            for(int i = 0; i < plaintext_final.length; i++){
                
                ciphertext1[i] = plaintext_final[FP[i] - 1];
            }
            
            //displayBits(ciphertext);
            System.out.println("");
            System.out.println("Ciphertext: ");
            System.out.print("{");
            for(int i = 0; i < ciphertext1.length; i++){
                
                System.out.print("" + ciphertext1[i] + ",");
            }// end for
            System.out.println("}");
            System.out.println("");

            return ciphertext1;
            
        }
        
        else
        {
            throw new ArrayIndexOutOfBoundsException("Array size wrong");
        }
        
    };//end DES
    
    private static int[] key_cal (int key[], int iter){
        //C and D left shift for 16 rounds
        //CD[] combine from C0D0 ~ C15D15
        int CD[] = new int[56];
        int tem_C[] = new int[28];
        int tem_D[] = new int[28];
        
        int i = (int)L_shift[iter];
        tem_C = left_shift(C, i);
        tem_D = left_shift(D, i);
        
        //CD store the combine of temC and temD
        System.arraycopy(tem_C,0,CD,0,tem_C.length);
        System.arraycopy(tem_D,0,CD,28,tem_D.length);
        
        //use Second_p table to reduce to 48bits
        int tem_K[] = new int[48];
        for(int j = 0; j < tem_K.length; j++){
            tem_K[j] = CD[second_p[j] - 1];
        }
        
        C = tem_C;
        D = tem_D;
        
        return tem_K;
    }//end key_cal
    
    private static int[] S_box (int[] input){
        int[] output = new int[32];
        for(int i = 0; i < 8; i++){
            
            int row[] = new int[2];
            int column[] = new int[4];
            
            // the first and the last of every 6 bits block are the Row locations
            row[0] = input[6*i];
            row[1] = input[(6*i) + 5];
            String s1 = row[0] + "" + row[1];
            // the 2nd to the 4th of every 6 bits block are the Column locations
            column[0] = input[(6*i)+1];
            column[1] = input[(6*i)+2];
            column[2] = input[(6*i)+3];
            column[3] = input[(6*i)+4];
            String s2 =  column[0] +""+ column[1] +""+ column[2] +""+ column[3];
            
            int x = Integer.parseInt(s1, 2);
            int y = Integer.parseInt(s2, 2);
            int z = S[i][(x*16) + y];
            
            String b = Integer.toBinaryString(z);
            
            while(b.length() < 4){
                b = "0" + b;
            }
            
            for(int j = 0; j < 4; j++){
                output[(i*4) + j] = Integer.parseInt(b.charAt(j) + "");
            }
        }
        
        int[] result = new int[32];
        for(int i =0; i < P.length; i++){
            result[i]  = output[P[i] - 1 ];
        }
        
        return result;
        
    }//end S_box
    
    
    private static int[] xor (int[] input1, int[] intput2){
        int[] output = new int[input1.length];
        
        for(int i = 0; i < input1.length; i++){
            output[i] = (input1[i]+intput2[i]) % 2;
        }
        return output;
    }// end xor
    
    
    private static int[] left_shift(int[] input, int n){
        
        int temp[] = new int[input.length];
        
        if(n == 1){
            temp[input.length - 1] = input[0];
            for(int i = 0; i < input.length - 1; i++){
                temp[i] = input[i+1];
            }
        }
        else
        {
            temp[input.length - 2] = input[0];
            temp[input.length - 1] = input[1];
            for(int i = 0; i < input.length - 2; i++){
                temp[i] = input[i+2];
            }
        }
        
        return temp;
    }//end left Shift
    

    
    //*****************************************************************
    //*****************     ECB         *******************************
    //*****************************************************************
    
    private static int[] ECB(String plaintext, String key){
        
        char[] plaintext_array = plaintext.toCharArray();
        char[] key_array = key.toCharArray();
        
        int[] num = new int[plaintext_array.length];
        int[] num2 = new int[key_array.length];
        
        //convert plaintext to int[] num
        for(int i = 0; i < plaintext_array.length; i++){
            num[i] = plaintext_array[i];
        }
        
        //convert key to int[] num2
        for(int i = 0; i < key_array.length; i++){
            num2[i] = key_array[i];
        }
        
        String s = "";
        String temps;
        String s_key = "";
        String temps_key;
        
        //expand each character in plaintext to 8 bits ASCII code
        for(int i = 0; i < num.length; i++){
            int tem = num[i];
            temps = Integer.toBinaryString(tem);
            
            while(temps.length() < 8){
                temps = "0" + temps;
                if(temps.length() < 8)
                    temps = "0" + temps;
            }
            s = s + temps;
            
        }
        System.out.println("");
        System.out.println("Plaintext convert to binary: " + s);
        
        //expand each character in key to 8 bits ASCII code
        for(int i = 0; i < num2.length; i++){
            int tem = num2[i];
            temps_key = Integer.toBinaryString(tem);
            while(temps_key.length() < 8){
                temps_key = "0" + temps_key;
                if(temps_key.length() < 8)
                    temps_key = "0" + temps_key;
                
            }
            s_key = s_key + temps_key;
        }
        
        if( s.length() < 64){
            throw new ArrayIndexOutOfBoundsException("Array size wrong");
        }
        else
        {
            // get the number of blocks
            int numofblock = ((s.length() - (s.length() % 64)) / 64);
            
            //padding 0 to plaintext to have equal 64 bits blocks
            int n = 64 - (s.length() - 64 * numofblock);
            for(int i = 0; i < n; i++){
                s = s + "0";
            }
            
            //store binary text in the int[]
            int[] text_result = new int[s.length()];
            
            for(int i = 0; i < s.length(); i++){
                
                text_result[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
                
            }
            
            
            //store binary key in the int[]
            int[] key_result = new int[s_key.length()];
            for(int i = 0; i < s_key.length(); i++){
                
                key_result[i] = Integer.parseInt(String.valueOf(s_key.charAt(i)));
                
            }
            
            System.out.println("");
            System.out.println("The orginal plaintext: ");
            System.out.println("{");
            for(int i = 0; i < text_result.length; i++){
                
                System.out.print("" + text_result[i] + ",");
            }// end for
            System.out.println("}" + " ---- Count : " + text_result.length);
            System.out.println("");

            // divide plaintext in every 64 bits blocks
            int[] n1 = new int[64];
            int[] n2 = new int[64];
            
            for(int i = 0 ; i < 64; i++){
                n1[i] = text_result[i];
            }
            for(int i = 0; i < 64; i++){
                n2[i] = text_result[i+64];
            }
            
            // System.out.println(" n1 L*****  " + n1.length);
            // System.out.println(" n2 L*****  " + n2.length);
            System.out.println("");
            System.out.println(" 1st block cipher: ");
            int[] ciphertext1 = DES(n1,key_result);
            System.out.println("");
            System.out.println(" 2nd block cipher: ");
            int[] ciphertext2 = DES(n2,key_result);
            //System.out.println(" c1 L*****  " + ciphertext1.length);
            //System.out.println(" c2 L*****  " + ciphertext2.length);
            
            
            int[] finaltext = new int[ciphertext1.length + ciphertext2.length];
            
            for(int i = 0; i < ciphertext1.length; i++){
                finaltext[i] = ciphertext1[i];
            }
            for(int i = 0; i < ciphertext2.length; i++){
                finaltext[i + 64] = ciphertext2[i];
            }
            
            
            int numofdex = finaltext.length / 8;
            String binarystring = "";
            int[] final_output = new int[numofdex];
            
            for(int i = 0; i < numofdex; i ++){
                
                for(int j = i*8; j <= i*8 +7; j++){
                    
                    binarystring = binarystring + finaltext[j];
                }
                
                int numtemp;
                numtemp = Integer.parseInt(binarystring,2);
                final_output[i] = numtemp;
                binarystring = "";
            }
            
            System.out.println("");
            System.out.println("Processing ECB......");
            System.out.println("......");
            System.out.println("......");

            System.out.println("Ciphertext of ECB: ");
            System.out.print("{");
            for(int i = 0; i < final_output.length; i++){
                
                System.out.print("" + final_output[i] + ",");
            }// end for
            System.out.println("}");
            System.out.println("");

            
            return final_output;
            
        }//end if
        
    }//end ECB
    
    
    
    //*****************************************************************
    //*****************     CBC         *******************************
    //*****************************************************************
    private static int[] CBC(String plaintext, String key, String IV){
        
        char[] plaintext_array = plaintext.toCharArray();
        char[] key_array = key.toCharArray();
        char[] iv_array = IV.toCharArray();
        
        int[] num = new int[plaintext_array.length];
        int[] num2 = new int[key_array.length];
        int[] num3 = new int[iv_array.length];
        
        //convert plaintext to int[] num
        for(int i = 0; i < plaintext_array.length; i++){
            num[i] = plaintext_array[i];
        }
        
        //convert key to int[] num2
        for(int i = 0; i < key_array.length; i++){
            num2[i] = key_array[i];
        }
        
        //convert IV to int[] num3
        for(int i = 0; i < iv_array.length; i++){
            num3[i] = iv_array[i];
        }
        
        String stext = "";
        String temps;
        String skey = "";
        String temps_key;
        String siv = "";
        String temps_iv;
        
        //expand each character in plaintext to 8 bits ASCII code
        for(int i = 0; i < num.length; i++){
            int tem = num[i];
            temps = Integer.toBinaryString(tem);
            
            while(temps.length() < 8){
                temps = "0" + temps;
                if(temps.length() < 8)
                    temps = "0" + temps;
            }
            stext = stext + temps;
        }
        
        System.out.println("Plaintext convert to binary: " + stext);
        System.out.println("");

        //expand each character in key to 8 bits ASCII code
        for(int i = 0; i < num2.length; i++){
            int tem = num2[i];
            temps_key = Integer.toBinaryString(tem);
            while(temps_key.length() < 8){
                temps_key = "0" + temps_key;
                if(temps_key.length() < 8)
                    temps_key = "0" + temps_key;
                
            }
            skey = skey + temps_key;
        }
        
        System.out.println("key convert to binary: " + skey);
        System.out.println("");

        //expand each character in IV to 8 bits ASCII code
        for(int i = 0; i < num3.length; i++){
            int tem = num3[i];
            temps_iv = Integer.toBinaryString(tem);
            while(temps_iv.length() < 8){
                temps_iv = "0" + temps_iv;
                if(temps_iv.length() < 8)
                    temps_iv = "0" + temps_iv;
                
            }
            siv = siv + temps_iv;
        }
        
        System.out.println("IV convert to binary: " + siv);
        System.out.println("");

        if( stext.length() < 64){
            throw new ArrayIndexOutOfBoundsException("Array size wrong");
        }
        else
        {
            // get the number of blocks
            int numofblock = ((stext.length() - (stext.length() % 64)) / 64);
            
            //padding 0 to plaintext to have equal 64 bits blocks
            int n = 64 - (stext.length() - 64 * numofblock);
            for(int i = 0; i < n; i++){
                stext = stext + "0";
            }
            
            //store binary text in the int[]
            int[] text_result = new int[stext.length()];
            
            for(int i = 0; i < stext.length(); i++){
                
                text_result[i] = Integer.parseInt(String.valueOf(stext.charAt(i)));
                
            }
            
            //store binary key in the int[]
            int[] key_result = new int[skey.length()];
            for(int i = 0; i < skey.length(); i++){
                
                key_result[i] = Integer.parseInt(String.valueOf(skey.charAt(i)));
            }
            
            //store binary IV in the int[]
            int[] iv_result = new int[siv.length()];
            for(int i = 0; i < siv.length(); i++){
                
                iv_result[i] = Integer.parseInt(String.valueOf(siv.charAt(i)));
                
            }
            
            // divide plaintext in every 64 bits blocks
            int[] n1 = new int[64];
            int[] n2 = new int[64];
            
            for(int i = 0 ; i < 64; i++){
                n1[i] = text_result[i];
            }
            for(int i = 0; i < 64; i++){
                n2[i] = text_result[i+64];
            }
            
            System.out.println("");
            System.out.println(" 1st block cipher: ");
            int[] first = xor(n1, iv_result);
            int[] c1 = DES(first,key_result);
            System.out.println("");
            System.out.println(" 2nd block cipher: ");
            int[] second = xor(n2,c1);
            int[] c2 = DES(second,key_result);
            System.out.println("");

            
            int[] finaltext = new int[c1.length + c2.length];
            
            for(int i = 0; i < c1.length; i++){
                finaltext[i] = c1[i];
            }
            for(int i = 0; i < c2.length; i++){
                finaltext[i + 64] = c2[i];
            }
            
            
            int numofdex = finaltext.length / 8;
            String binarystring = "";
            int[] final_output = new int[numofdex];
            
            for(int i = 0; i < numofdex; i ++){
                
                for(int j = i*8; j <= i*8 +7; j++){
                    
                    binarystring = binarystring + finaltext[j];
                }
                
                int numtemp;
                numtemp = Integer.parseInt(binarystring,2);
                final_output[i] = numtemp;
                binarystring = "";
            }
            
            System.out.println("Processing CBC....");
            System.out.println("......");

            System.out.println("Ciphertext of CBC: ");
            System.out.print("{");
            for(int i = 0; i < final_output.length; i++){
                
                System.out.print("" + final_output[i] + ",");
            }// end for
            System.out.println("}");
            System.out.println("");

            
            return final_output;
            
        }//end if
        
    };//end CBC
    

    

}//end Crypto




