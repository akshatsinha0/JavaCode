import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        // Input format:
        // line 1: array, any separators, e.g. "1 2 2 3 -1 0"
        // line 2: target, e.g. "3"
        String line=br.readLine();
        String targetLine=br.readLine();
        int[] nums=line==null?new int[]{1,2,2,3,0,-1}:parseInts(line);
        int target=targetLine==null?3:Integer.parseInt(targetLine.trim());

        List<List<Integer>> res=subsetsWithSum(nums,target);
        if(res.isEmpty()){
            System.out.println("No subset");
        }else{
            StringBuilder sb=new StringBuilder();
            for(List<Integer> s:res) sb.append(s).append(System.lineSeparator());
            System.out.print(sb.toString());
        }
    }

    // Finds all unique-value subsets whose sum==target, prints in nondecreasing order.
    public static List<List<Integer>> subsetsWithSum(int[] nums,int target){
        Arrays.sort(nums);
        // compress duplicates: values[] with counts[]
        ArrayList<Integer> vs=new ArrayList<>(), cs=new ArrayList<>();
        for(int i=0;i<nums.length;){
            int v=nums[i], j=i;
            while(j<nums.length&&nums[j]==v) j++;
            vs.add(v); cs.add(j-i);
            i=j;
        }
        int m=vs.size();
        int[] values=new int[m], counts=new int[m];
        for(int i=0;i<m;i++){values[i]=vs.get(i);counts[i]=cs.get(i);}

        // suffix bounds: minimal and maximal sums still achievable from i..end
        long[] sufMin=new long[m+1], sufMax=new long[m+1];
        for(int i=m-1;i>=0;i--){
            long v=values[i], c=counts[i];
            sufMin[i]=sufMin[i+1]+(v<0?v*c:0);
            sufMax[i]=sufMax[i+1]+(v>0?v*c:0);
        }

        List<List<Integer>> out=new ArrayList<>();
        dfs(0,0,values,counts,sufMin,sufMax,new ArrayList<>(),out,target);
        return out;
    }

    private static void dfs(int i,long sum,int[] val,int[] cnt,long[] sufMin,long[] sufMax,
                            ArrayList<Integer> path,List<List<Integer>> out,int target){
        if(i==val.length){
            if(sum==target) out.add(new ArrayList<>(path));
            return;
        }
        // global pruning at this level
        if(sum+sufMin[i]>target) return;
        if(sum+sufMax[i]<target) return;

        int v=val[i], c=cnt[i];
        for(int take=0;take<=c;take++){
            long newSum=sum+(long)v*take;

            // local pruning with remaining bounds
            if(newSum+sufMin[i+1]>target){
                if(v>=0) break;      // adding more only increases newSum
                else continue;       // try taking more negatives to reduce
            }
            if(newSum+sufMax[i+1]<target){
                if(v<0) break;       // taking more negatives makes it worse
                else continue;       // try taking more positives
            }

            for(int k=0;k<take;k++) path.add(v);
            dfs(i+1,newSum,val,cnt,sufMin,sufMax,path,out,target);
            for(int k=0;k<take;k++) path.remove(path.size()-1);
        }
    }

    private static int[] parseInts(String s){
        Matcher m=Pattern.compile("-?\\d+").matcher(s);
        ArrayList<Integer> a=new ArrayList<>();
        while(m.find()) a.add(Integer.parseInt(m.group()));
        int[] r=new int[a.size()];
        for(int i=0;i<a.size();i++) r[i]=a.get(i);
        return r;
    }
}
