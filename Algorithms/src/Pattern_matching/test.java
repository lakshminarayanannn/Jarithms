package Pattern_matching;

public class test {
    public static void main(String [] args)
    {
        /*
        KMP now = new KMP("aabbccdd");
        System.out.println(now.search("abd"));
        System.out.println(now.search("ab"));
        int ar [] = now.get_lps_for("aaaa");
        for(int i=0;i<4;i++)
        {
            System.out.print(ar[i]+" ");
        }
        System.out.println();
        System.out.println(now.search("ab"));
        */
        /*
         RabinKarp rb = new RabinKarp("aabbccdd");
         System.out.println(rb.search("ae"));
         System.out.println(rb.search("aabbcc"));
         */
        /*
        Zfunction temp = new Zfunction("aabbccdd");
        System.out.println(temp.search("aabe"));
        System.out.println(temp.search("aab"));
         */
        manachers temp = new manachers("12331");
        System.out.println(temp.get_longest_palindrome());

    }
}
