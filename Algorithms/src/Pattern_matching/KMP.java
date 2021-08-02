package Pattern_matching;
import java.util.*;

public class KMP
{
    private String name;
    private int [] lps;
    public KMP(String s)
    {
        this.name = s;
        lps = new int[1000000];
        Arrays.fill(lps,0);

    }
    public boolean search(String pattern)
    {
        int index = 0;
        char [] pat = pattern.toCharArray();
        for(int i=1; i < pattern.length();){
            if(pat[i] == pat[index]){
                lps[i] = index + 1;
                index++;
                i++;
            }else{
                if(index != 0){
                    index = lps[index-1];
                }else{
                    lps[i] =0;
                    i++;
                }
            }
        }
        return algorithm(pattern);
    }
    public int [] get_lps_for(String s) {
        int index = 0;
        int[] templps = new int[s.length()];
        templps[0]=0;
        char[] pat = s.toCharArray();
        for (int i = 1; i < s.length(); ) {
            if (pat[i] == pat[index]) {
                templps[i] = index + 1;
                index++;
                i++;
            } else {
                if (index != 0) {
                    index = templps[index - 1];
                } else {
                    templps[i] = 0;
                    i++;
                }

            }
        }
        return templps;
    }
    private boolean algorithm(String pattern)
    {
        char [] pat = pattern.toCharArray();
        char [] str = name.toCharArray();
        int i = 0;
        int j = 0;
        while(i<str.length && j<pat.length)
        {
            if(str[i]==pat[j])
            {
                i+=1;
                j+=1;
            }
            else {
                if(j!=0)
                {
                    j=lps[j-1];
                }
                else {
                    i+=1;
                }
            }
        }
        if(j==pat.length) return true;
        return false;
    }


}
