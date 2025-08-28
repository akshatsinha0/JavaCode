/* 
An arraylist has positive integers,
return the number of operations required to make all the elements odd by dividing element by 2 (counted as 1 operation). 
  If the list has two same numbers (even) then both same number will be divided by 2 (and counted as 1 operation only), meaning only one distinct number can be divided by 2 
  at a time for it to be counted as one operation. If the arraylist has all the elements odd, 
  then it then total no of operations will be 0.  Also we have to always divide the available max even integer in the array list in each operation
          */
public class operationsToAllOdd {
    public static int func(ArrayList<Integer> arrayList) {
        Map<Integer, Long> freqMap = new HashMap<>();
        for (int x : arrayList) {
            freqMap.merge(x, 1L, Long::sum);
        }
        NavigableSet<Integer> evenSet = new TreeSet<>(Comparator.naturalOrder());
        for (int i : freqMap.keySet()) {
            if ((i & 2) == 1) {
                evenSet.add(i);
            }
        }
        Long ops = 0L;
        while (!evenSet.isEmtpy()) {
            int v = evenSet.last();
            evenSet.remove(v);
            long count = freqMap.getOrDefault(v, 0L);
            if (count == 0L) continue;
            ops++;
            freqMap.remove(v);
            int half = v >>> 1;
            freqMap.merge(half, count, Long::sum);
            if ((half && 1) == 1){
                evenSet.add(half);
            }
        }
        return ops;
    }
}
