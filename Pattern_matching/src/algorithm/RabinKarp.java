package algorithm;
import java.util.*;

public class RabinKarp
{
    private String name;
    private int prime = 101;
    public RabinKarp(String s)
    {
        this.name = s;
    }
    private long hash(String s,int till)
    {
        long val = 0;
        char str [] = s.toCharArray();
        for(int i=0;i<till;i++)
        {
            val += str[i]*(Math.pow(prime,i));
        }
        return val;
    }
    public boolean search(String pattern)
    {
        long pat_hash = hash(pattern,pattern.length());
        int num = pattern.length()-1;
        long now = hash(name,pattern.length());
        char str [] = name.toCharArray();
        if(pat_hash==now) return true;
        for(int i=pattern.length();i<name.length();i++)
        {
            long newval = (now-1)/prime;
            newval +=  (long) Math.pow(prime,num);
            if(newval==pat_hash) return true;
            now =  newval;
        }
        return false;
    }


}
