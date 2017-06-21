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
