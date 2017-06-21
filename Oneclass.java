import java.util.*;

class Oneclass{
//Initial permutation table.
	private static final byte[] IP = {
			58, 50, 42, 34, 26, 18, 10, 2,
			60, 52, 44, 36, 28, 20, 12, 4,
			62, 54, 46, 38, 30, 22, 14, 6,
			64, 56, 48, 40, 32, 24, 16, 8,
			57, 49, 41, 33, 25, 17, 9,  1,
			59, 51, 43, 35, 27, 19, 11, 3,
			61, 53, 45, 37, 29, 21, 13, 5,
			63, 55, 47, 39, 31, 23, 15, 7
	};
	// E bit selection table.
	private static final byte[] ST = {
			32, 1,  2,  3,  4,  5,
			4,  5,  6,  7,  8,  9,
			8,  9,  10, 11, 12, 13,
			12, 13, 14, 15, 16, 17,
			16, 17, 18, 19, 20, 21,
			20, 21, 22, 23, 24, 25,
			24, 25, 26, 27, 28, 29,
			28, 29, 30, 31, 32, 1
	};
	// S box s1
	private static final byte[][] S = {
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
	}; // end S table array
	
	// permutation table 
	private static final byte[] PT = {
			16, 7,  20, 21,
			29, 12, 28, 17,
			1,  15, 23, 26,
			5,  18, 31, 10,
			2,  8,  24, 14,
			32, 27, 3,  9,
			19, 13, 30, 6,
			22, 11, 4,  25
	};
	// Final permutation table 
	private static final byte[] FP = {
			40, 8, 48, 16, 56, 24, 64, 32,
			39, 7, 47, 15, 55, 23, 63, 31,
			38, 6, 46, 14, 54, 22, 62, 30,
			37, 5, 45, 13, 53, 21, 61, 29,
			36, 4, 44, 12, 52, 20, 60, 28,
			35, 3, 43, 11, 51, 19, 59, 27,
			34, 2, 42, 10, 50, 18, 58, 26,
			33, 1, 41, 9, 49, 17, 57, 25
	};
	// First Permutation table reduces key from 64 bits to 56 bits.
	private static final byte[] KP1 = {
			57, 49, 41, 33, 25, 17, 9,
			1,  58, 50, 42, 34, 26, 18,
			10, 2,  59, 51, 43, 35, 27,
			19, 11, 3,  60, 52, 44, 36,
			63, 55, 47, 39, 31, 23, 15,
			7,  62, 54, 46, 38, 30, 22,
			14, 6,  61, 53, 45, 37, 29,
			21, 13, 5,  28, 20, 12, 4
	};
	//key left shift 
		private static final byte[] shift_table = {
				1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
		};
		
	// second permutation table reduces key from 56 bits to 48 bits. 
	private static final byte[] KP2 = {
			14, 17, 11, 24, 1,  5,
			3,  28, 15, 6,  21, 10,
			23, 19, 12, 4,  26, 8,
			16, 7,  27, 20, 13, 2,
			41, 52, 31, 37, 47, 55,
			30, 40, 51, 45, 33, 48,
			44, 49, 39, 56, 34, 53,
			46, 42, 50, 36, 29, 32
	};
	
	
	//store round key
	private static int[] C = new int[28];
	private static int[] D = new int[28];
	
	public static void main(String args[]){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter 64 bits plaintext:");
		int[] plaintext1 = new int[64];
	// user input store in array plaintext1.
		for(int i = 0; i < plaintext1.length; i++){
			plaintext1[i] = scan.nextInt();
		}// end for loop
	
	// print out content of plaintext.
		System.out.print("{");
		for(int j = 0; j < plaintext1.length; j++){
			
			System.out.print("" + plaintext1[j] + ",");
		}// end for
		System.out.println("}");
	// 
		System.out.println();
		System.out.println("Enter 64 bits key:");
	
		int[] key1 = new int[64];
	// user input store in array key1.
		for(int i = 0; i < key1.length; i++){
			key1[i] = scan.nextInt();
		}// end for loop
		
	// print out contect of key.
		System.out.print("{");
		for(int j = 0; j < key1.length; j++){
			
			System.out.print("" + key1[j] + ",");
		}// end for
		System.out.println("}");
		System.out.println();
	//int[] ciphertext1 = new int[56];
	System.out.println("Encryption process");
	int ciphertext1[] = DES(plaintext1,key1);
	} // end main function
	
	// This method returns a ciphertext.
	private static int[] DES(int[] plaintext, int[] key){
		int[] permuted_bite = new int[plaintext.length];
		// Initial permutation for plaintext, result stored in permuted_bite[];
		for(int i = 0; i < plaintext.length; i++){
			permuted_bite[i] = plaintext[IP[i] - 1];
		}
		// store divided permuted block into array L, R
		int[] L = new int[32];
		int[] R = new int[32];
		int i;
		// Reduce keys from 64 bits to 56 bits using permutation table KP1
		// Store two blocks each 28 bit keys in C[], D[]
		for( i = 0; i < 28; i++){
			C[i] = key[KP1[i]-1];
			}

		for( ; i < 56; i++){
			D[i-28] = key[KP1[i]-1];
			}
				
		//copy each half of the permuted bited[] into L[] and R[]
		System.arraycopy(permuted_bite, 0, L, 0, 32);
		System.arraycopy(permuted_bite, 32, R, 0, 32);
		//display L0 and R0
		System.out.print("\nL0 = ");
		displayBits(L);
		System.out.print("\nR0 = ");
		displayBits(R);
		
		// 16 rounds of iterations occur.
		for(int n=0 ; n < 16 ; n++) {
			System.out.println("\n-------------");
			System.out.println("Round " + (n+1) + ":");
	
		// newR[] is the new R half generate by fiestel function.
		int[] newR = new int[0];
		newR = fiestel(R, keyGenerate(n,key));
		System.out.print("Round key : ");
		displayBits(newR);
		// XOR L and newR[] results newL[]
		//int[] newL = new int[32];
		int newL[] = xor(L, newR);	
		// exchange L and R position for next round.
		L = R;
		R = newL;
		System.out.print("\nL = ");
		displayBits(L);
		System.out.print("\nR = ");
		displayBits(R);	
		System.out.println();
		}// end for 	
		int output[] = new int[64];
		// exchange L and R block, then concatenate them into a single array.
		System.arraycopy(R,0,output,0,R.length);
		System.arraycopy(L,0,output,32,L.length);
		// final permutation on output, then result a ciphertext.
		int[] ciphertext = new int[64];
		for(i = 0; i< output.length; i++){
			ciphertext [i] = output[FP[i]-1];
		}// end for loop
		System.out.println("ciphertext:");
		/*
		System.out.println("{");
		for(i = 0; i< ciphertext.length; i++){
			System.out.print("" + ciphertext[i] + ",");
		}
		System.out.println("}");
		*/
		//displayBits(ciphertext);
		System.out.print("{");
		for(i = 0; i < ciphertext.length; i++){
			
			System.out.print("" + ciphertext[i] + ",");
		}// end for
		System.out.println("}");
	  return ciphertext;	
	} // end DES function
	
		/*generate Rn from fiestel function. Parameter are a key kn of 48 bits and  
		 *a data block of 32 bits.
		 */
		private static int[] fiestel(int[] R, int[] roundKey){
		// first step is expand R[] from 32 bits to 48 bits using E BIT-SELECTION TABLE.
			int[] EBS_R = new int[48];
			for(int i = 0; i < 48; i++){
				EBS_R[i] = R[ST[i]-1];
			}// end for loop
		//second step XOR the ouput E(Rn-1) with the key Kn
			//int[] temp = new int[48];
			int temp[] = xor(EBS_R, roundKey);
		//final step transform 6_bit 8 blocks xor result to 4_bits 8 blocks using S boxes.
			//int[] result = new int[32];
			int result[] = S_Box(temp);
			return result;
		}// end fiestel function
		
		/* xor function does xor calculation in DES algorithm. */
		private static int[] xor (int[] element1, int[] element2){
			int[] xor_output = new int[element1.length];
			for(int i = 0; i < element1.length; i++){
				xor_output[i] = element1[i]^element2[i];
			}
			return xor_output;
		} // end xor function
		
		/* S_box function transform 48 bits xor result to 32 bits. */
		private static int[] S_Box (int[] input){
			int[] output = new int[32];
			// 48 bit input will be divided into 8 blocks and each contains 6 bit. Therefore, there
			// will be 8 iteration.
			for(int i = 0; i< 8; i++){
			// For each 6 bits block input, the 1st and 6th bits will be the row found in S table.
				int row[] = new int [2];
				row[0] = input[6*i];
				row[1] = input[(6*i)+5];
				String sRow = row[0] + "" + row[1];
			// And the 2nd to 4th bits will be the column found in S table.
				int column[] = new int[4];
				column[0] = input[(6*i)+1];
				column[1] = input[(6*i)+2];
				column[2] = input[(6*i)+3];
				column[3] = input[(6*i)+4];
				String sColumn = column[0] +""+ column[1] +""+ column[2] +""+ column[3];
			// Convert binary to decimal value, array as input.
				int iRow = Integer.parseInt(sRow, 2);
				int iColumn = Integer.parseInt(sColumn, 2);
				int x = S[i][(iRow*16) + iColumn];
			// convert decimal to binary value
				String s = Integer.toBinaryString(x);
			// java requires padding.
				while(s.length() < 4) {
					s = "0" + s;
				} // end while
			// The binary bits are appended to the output
				for(int j=0 ; j < 4 ; j++) {
					output[(i*4) + j] = Integer.parseInt(s.charAt(j) + "");
				}
			}// end for loop
			// final permutaiton for one S_box round using table PT[]
			int[] finalOutput = new int[32];
			for(int i = 0; i < PT.length; i++){
				finalOutput[i] = output[PT[i]-1];
			}// end for loop
			
		 return finalOutput;
		}// end S_Box function
	
	/* keyGenerate function generate each round key. */
	private static int[] keyGenerate(int num_round, int key[]){
		int newC[] = new int[28];
		int newD[] = new int[28];
	// num_shift array use to set the number of position in each shift.
		int num_shift = (int) shift_table[num_round];
		newC = left_shift(C, num_shift);
		newD = left_shift(D, num_shift);
	// CnDn stores the concatenation of newC and newD
		int CnDn[] = new int[56];
		System.arraycopy(newC,0,CnDn,0,newC.length);
		System.arraycopy(newD,0,CnDn,28,newD.length);
	// Reduce 56 bits CnDn to 48 bits 
		int Kn[] = new int [48];
		for(int i = 0; i < Kn.length; i++){
			Kn[i] = CnDn[KP2[i]-1];
		}// end for loop
	// renew C and D for next round.	
		C = newC;
		D = newD;
	  return Kn;
	}// end keyGenerate function
			
	
	/*left_shift function rotates two keys array C[]and D[] by using left_shift table.*/
	private static int[] left_shift(int[] input_bits, int n){
		int output[] = new int[input_bits.length];
		System.arraycopy(input_bits, 0, output, 0, input_bits.length);
		for(int i=0 ; i < n ; i++) {
			int temp = output[0];
			for(int j=0 ; j < input_bits.length-1 ; j++) {
				output[j] = output[j+1];
			}// end for
			output[input_bits.length-1] = temp;
		}
		
	  return output;	
		}// end left_shift function
		
	
	/*display bits of real time operation.*/
	private static void displayBits(int[] bits){
		for(int i = 0; i < bits.length; i++){
			System.out.print(bits[i]);
		}
	}// end displayBits function

} // end Oneclass
