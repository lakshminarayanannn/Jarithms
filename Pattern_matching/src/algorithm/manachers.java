package algorithm;
import java.util.*;

public class manachers {
    private String name ;
    manachers(String s)
    {
        this.name = s;
    }
    public String get_longest_palindrome()
    {
        char [] originalStr =name.toCharArray();
        char str [] = preprocess(name);
        int len = str.length;
        int val [] = new int [len];
        //start
        int center = 0;
        int right = 0;
        for (int i=1;i<len-1;i++)
        {
            int mirror = center-(i-center);
            if (right>i)
            {
                val[i] = Math.min(right - i, val[mirror]);
            }
            while (str[i + (1 + val[i])] == str[i - (1 + val[i])]) {
                val[i]++;
            }
            if (i + val[i] > right) {
                center = i;
                right = i + val[i];
            }
        }


        // end
        int maxLen = 0;
        int centerIdx = 0;
        for (int i = 1; i < len - 1; i++) {
            if (val[i] > maxLen) {
                maxLen = val[i];
                centerIdx = i;
            }
        }
        return new String(Arrays.copyOfRange(originalStr, (centerIdx - 1 - maxLen) / 2, (centerIdx - 1 + maxLen) / 2));

    }
    private  char[] preprocess(String str) {

        int n = str.length();
        char[] chars = new char[2*n+3];
        chars[0] = '$';
        chars[2*n+2] = '@';
        for (int i=0;i<n;i++)
        {
            chars[2*i+1] = '#';
            chars[2*i+2] = str.charAt(i);
        }
        chars[n*2+1] = '#';
        return chars;
    }
}
