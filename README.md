# DES-ECB-CBC

# Goal
  The goal of this project is to write a simple encryption algorithm library to implement DES,ECB and CBC. The programming language is Java.
  
# Initial API
int[] DES(int[] plaintext, int[] key) <br/>
int[] ECB(String plaintext, String key) <br/>
int[] CBC(String plaintext, String key, String IV) <br/>

# Test data
(1) DES <br/>
  Input: <br/>
  int[] plaintext = { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0,
     0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1,
     0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1,
     1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1}; <br/>
  
  int[] key = { 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0,
     0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0,
     1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1,
     1, 1, 1, 1, 1, 0, 0, 0, 1 }; <br/>
     
  Output: <br/>
  int[] = ciphertext = { 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1,
     0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0,
     0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0,
     1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1} <br/>
     
(2) ECB Input: <br/>
     String plaintext = “I LOVE SECURITY”;<br/>
     String key = “ABCDEFGH”;<br/>
     Output:<br/>
     Int[] ciphertext = {198, 252, 213, 112, 106, 165, 23, 145,
     29, 52, 125, 61, 85, 217, 102, 155};
      <br/>
     Input:<br/>
     String plaintext = “GO GATORS!”<br/>
     String key = “ABCDEFGH”<br/>
     Output:<br/>
     Int[] ciphertext = {86, 100, 180, 248, 126, 142, 38, 5, 255,
     224, 149, 93, 149, 189, 237, 2};<br/>
     <br/>
