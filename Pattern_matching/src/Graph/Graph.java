package Graph;
import java.util.*;
public class Graph
{
    private final int n;
    public ArrayList<Integer> [] graph;
    public boolean[] visited ;
    public int [] parent;
    public int [] depth;
    public int [] tin;
    public int [] tout;
    public int [] low;
    public static int timer=0;
    ArrayList<pair> bridges;
    public int [][] weights;
    public boolean is_there_cycle;
    public int [][] dp;
    public int [] dsu_parent;
    public ArrayList<ArrayList<Integer>> edges;
    static class pair
    {
        public int x,y;
        pair(int x,int y)
        {
            this.x = x;
            this.y = y;
        }

    }
    Graph(int n)
    {
        this.n = n;
        is_there_cycle = false;
        graph = new ArrayList[n+1];
        visited = new boolean [n+1];
        parent = new int [n+1];
        depth = new int[n+1];
        dsu_parent = new int[n+1];
        weights = new int [n+1][n+1];
        dp = new int [n+1][60];
        edges = new ArrayList<ArrayList<Integer>>();
        bridges = new ArrayList<pair>();
        for(int i=0;i<=n;i++)
        {
            graph[i] =  new ArrayList<Integer>();
            dsu_parent[i] = i;
        }
        for(int i=0;i<=n;i++)
        {
            Arrays.fill(weights[i],(int)1e9);
        }
    }
    public void add_edge(int x,int y)
    {
        graph[x].add(y);
        graph[y].add(x);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(x);
        temp.add(y);
        temp.add(weights[x][y]);
        edges.add(temp);
        ArrayList<Integer> temp1 = new ArrayList<Integer>();
        temp1.add(y);
        temp1.add(x);
        temp1.add(weights[y][x]);
        edges.add(temp1);
    }
    public void add_edge(int x,int y,int w)
    {
        graph[x].add(y);
        graph[y].add(x);
        weights[x][y] = w;
        weights[y][x] = w;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(x);
        temp.add(y);
        temp.add(weights[x][y]);
        edges.add(temp);
        ArrayList<Integer> temp1 = new ArrayList<Integer>();
        temp1.add(y);
        temp1.add(x);
        temp1.add(weights[x][y]);
        edges.add(temp1);
    }
    public void dfs(int u,int p)
    {
        visited[u] = true;
        parent[u] = p;
        depth[u] = depth[p]+1;
        for(Integer v: graph[u])
        {
            if(visited[v]==false)
            {
                dfs(v,u);
            }
            else if(visited[v]==true)
            {
                is_there_cycle = true;
            }
        }
    }
    public void bfs(int u)
    {
        Stack<Integer> stack = new Stack<>();
        stack.push(u);
        while(stack.empty()==false)
        {
            int now = stack.pop();
            visited[now] = true;
            for(Integer then:graph[now])
            {
                if(visited[then]==false)
                {
                    depth[then] = depth[now]+1;
                    parent[then] = now;
                    stack.push(then);
                }
            }
        }
    }
    public int [] dijkstra(int source)
    {
        int [] distance = new int [n+1];
        Arrays.fill(distance,(int)1e9);
        distance[source] = 0;
        PriorityQueue<pair> queue = new PriorityQueue<>();
        queue.add(new pair(source,0));
        while(queue.isEmpty()==false)
        {
            pair temp = queue.poll();
            for(Integer then: graph[temp.x])
            {
                if((weights[then][temp.x]+temp.y) < distance[then]) {
                    distance[then] = weights[then][temp.x] + temp.y;
                    queue.add(new pair(then, distance[then]));
                }
            }
        }
        return distance;
    }
    public void lca_dfs(int x,int p)
    {
        depth[x] = depth[p]+1;
        parent[x] = p;
        dp[x][0] = p;
        for(int i=1;i<60;i++)
        {
            dp[x][i] = dp[dp[x][i-1]][i-1];
        }
        for(Integer y : graph[x])
        {
            if(y!=x)
            {
                dfs(y,x);
            }
        }

    }
    int least_common_ancestor(int a,int b)
    {
        if(depth[a]<depth[b])
        {
            int temp = a;
            a=b;
            b=temp;
        }
        int dif = depth[a] - depth[b];
        for(int i=59;i>=0;i--)
        {
            if(((1<<i)&(dif))>0)
            {
                a=dp[a][i];
            }
        }
        if(a==b) return a;
        for(int i=59;i>=0;i--)
        {
            if(dp[a][i]!=dp[b][i])
            {
                a = dp[a][i];
                b = dp[b][i];
            }

        }
        return  dp[a][0];
    }
    public int [][] floyd_warshal()
    {
        int [][] dist = new int [n+1][n+1];
        for(int i=0;i<=n;i++)
        {
            for(int j=0;j<=n;j++)
            {
                dist[i][j] = weights[i][j];
            }
        }
        for (int k=1;k<=n;k++)
        {
            for (int i=1;i<=n;i++)
            {
                for (int j=1;j<=n;j++)
                {
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
        return dist;
    }
    public int [] Desopo_pape(int source)
    {
        int []  p = parent;
        int [] dist = new int [n+1];
        Arrays.fill(dist,(int)1e9);
        Arrays.fill(p,-1);
        dist[source] = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(source);
        int [] m = new int[n+1];
        Arrays.fill(m,2);
        while(q.isEmpty()==false)
        {
            int u = q.removeFirst();
            m[u] = 0;
            for(Integer v:graph[u])
            {
                if(dist[v]>dist[u]+weights[u][v])
                {
                    dist[v] = dist[u]+weights[u][v];
                    p[v] = u;
                    if(m[v]==2) {
                        m[v] = 1;
                        q.add(v);
                    }
                    else if(m[v]==0)
                    {
                        m[v] = 1;
                        q.add(v);
                    }
                }

            }
        }
        return dist;

    }
    public int find_no_of_comp()
    {
        int total = 0;
        for(int i=1;i<=n;i++)
        {
            if(visited[i]==false)
            {
                dfs(i,0);
                total+=1;
            }
        }
        return total;
    }
    public void bridge_dfs(int v,int p)
    {
        visited[v] =  true;
        tin[v] = low[v] =timer++;
        for(Integer to : graph[v])
        {
            if(to==p) continue;
            if(visited[to]==true)
                low[v] = Math.min(low[v],tin[to]);
            else
            {
                bridge_dfs(to,v);
                low[v] = Math.min(low[v],low[to]);
                if(low[to]>tin[v])
                {
                    bridges.add(new pair(to,v));
                }
            }
        }
    }
    public int  find(int u)
    {
        int x = u;
        while(dsu_parent[x]!=x)
        {
            x = dsu_parent[x];
        }
        return x;
    }
    public void join(int a,int b)
    {
        int x = find(a);
        int y = find(b);
        if(x!=y)
        {
            dsu_parent[x] = dsu_parent[y] = Math.min(x,y);
        }
    }
    public ArrayList<ArrayList<Integer>> kruskal()
    {
        int sum = 0;

        ArrayList<ArrayList<Integer>> edg = edges;
        ArrayList<ArrayList<Integer>> ans =  new ArrayList<>();
        for(ArrayList<Integer> te:edg)
        {
            te.add(weights[te.get(0)][te.get(1)]);
        }
        Collections.sort(edg,Collections.reverseOrder());
        for(ArrayList<Integer> temp: edg)
        {
            int x = temp.get(0);
            int y = temp.get(1);
            if(find(x)!=find(y))
            {
                join(find(x),find(y));
                sum += temp.get(2);
                ArrayList<Integer> now = new ArrayList<>();
                now.add(x);
                now.add(y);
                ans.add(now);
            }

        }
        return ans;

    }

    public boolean is_bipartite()
    {
        boolean ans = true;
        int []color = new int[n+1];
        Arrays.fill(color,-1);
        PriorityQueue<Integer> st = new PriorityQueue<>();
        for(int tem = 1 ; tem<=n ; tem++)
        {
            if(color[tem]==-1)
            {
                st.add(tem);
                color[tem] = 0;
                while(st.size()>0)
                {
                    int v = st.poll();
                    for(Integer u:graph[v])
                    {
                        if(color[u]==-1)
                        {
                            color[u] = 1^color[v];
                            st.add(u);
                        }
                        else
                        {
                            ans  &= color[u]!=color[v];
                        }
                    }

                }
            }
        }
        return ans;
    }

}
