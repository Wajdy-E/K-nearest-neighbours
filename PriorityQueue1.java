//WAJDY ELSOBKY 300317535
import java.util.ArrayList;

public class PriorityQueue1 implements PriorityQueueIF<LabelledPoint> {

    private int maxCapacity;
    private ArrayList<LabelledPoint> priorityQueue;

    public PriorityQueue1(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.priorityQueue = new ArrayList<>(maxCapacity);
    }

    public int findMaxValueIndex() { //as the name suggests, this method finds the index of the max value in the array.
        if (isEmpty()) {
            return -1;
        }
    
        double max = priorityQueue.get(0).getKey();
        int maxIndex = 0;
        for (int i = 1; i < size(); i++) {
            if (priorityQueue.get(i).getKey() > max) {
                max = priorityQueue.get(i).getKey();
                maxIndex = i;
            }
        }
    
        return maxIndex;
    }
    
    
    @Override
    public boolean offer(LabelledPoint e) { 
        if(size()<maxCapacity){
            priorityQueue.add(e);
            return true;
        }else if(size()>=maxCapacity){
            int max = findMaxValueIndex();
            if(e.getKey()<priorityQueue.get(max).getKey()){ //if the key of the element we're trying to add is less than key of the max value, then replace.
                poll();
                priorityQueue.add(max, e);
                return true;
            }
        }
        return false;
    }

    @Override
    public LabelledPoint poll() {
        if (isEmpty()) {
            return null;
        }
        return priorityQueue.remove(findMaxValueIndex());
    }

    @Override
    public LabelledPoint peek() {
        if (isEmpty()) {
            return null;
        }
        return priorityQueue.get(findMaxValueIndex());
    }

    @Override
    public int size() {
        return priorityQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    public void findKNN(LabelledPoint query, ArrayList<LabelledPoint> dataset, int k) {
        ArrayList<LabelledPoint> kNN = new ArrayList<>(k);
        for (LabelledPoint datasetPoint : dataset) { //iterate through every dataset point and set its key as its distance.
            double distance = datasetPoint.distanceTo(query);
            datasetPoint.setKey(distance);
            offer(datasetPoint); 
        }

        System.out.print(query.getLabel() + " : "); //print out result.
        for (int i = 0; i < k; i++) {
            if (!isEmpty()) {
                LabelledPoint nearestNeighbor = poll();
                kNN.add(nearestNeighbor);
                System.out.print(nearestNeighbor.getLabel());
                
                if (i < k - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println();
    }

    
}
