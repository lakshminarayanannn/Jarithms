package Pattern_matching;
import java.util.*;

public class Zfunction {
    private String name;
    private int [] zvalue;
    Zfunction(String name)
    {
        this.name=name;
        this.zvalue = new int [100000];
        Arrays.fill(zvalue,0);
    }
    public boolean search(String pattern)
    {
        int [] val = calculate_value(pattern);
        for(int i=pattern.length()+1;i<name.length();i++)
        {
            if(val[i]>=pattern.length()) return true;
        }
        return false;
    }
    public int [] get_zval_for(String s)
    {
        int [] val = calculate_value(s);
        return val;
    }
    private int [] calculate_value(String pattern)
    {
        String now = pattern+"$"+this.name;
        char [] str = now.toCharArray();
        int [] val = new int[str.length];
        val[0] = 0;
        int l = 0;
        int r = 0;
        for(int i=1;i<str.length;i++)
        {
            if(i>=l && i<=r)
            {
                val[i] = Math.min(val[i-l],r-l+1);
            }
            while(i+val[i]<str.length && str[val[i]]==str[i+val[i]])
            {
                val[i]+=1;
            }
            if(i+val[i]-1<str.length && i+val[i]-1>r)
            {
                l=i;
                r=i+val[i]-1;
            }
        }
        return val;
    }
}
