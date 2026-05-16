/*
public class Coditas {
    */
/*input AAAZ
output AABA

 input AAZA
output ABAA*//*

    public static void main(String[] args) {
      String input="AAAZ";

      char[] arr= new input.toCharArray();
      for(int i = 0;i < arr.length;i++){
          if(arr[i]=='z'){

              if(i> 0){
                  arr[i-1] = 'b';
              }
              arr[i]='a';
          }
      }
        System.out.println(arr);
    }
}
*/
