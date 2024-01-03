//WAJDY ELSOBKY 300317535
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PriorityQueue2 implements PriorityQueueIF<LabelledPoint> {
    private ArrayList<LabelledPoint> heap;
    private Comparator<Double> comp;
    private int maxCapacity;

    public PriorityQueue2(int k) {
        this.comp = Comparator.naturalOrder(); 
        this.heap = new ArrayList<>(k);
        maxCapacity = k;
    }
    

    @Override
    public int size() {
        return heap.size();
    }
    //SLIGHTLY MODIFIED OFFER METHOD FROM POWERPOINT SLIDE TO REDUCE EXECUTION TIME
    @Override
    public boolean offer(LabelledPoint e) {
        if (size() < maxCapacity) {
            // If the heap size is less than max capacity, add the element to the heap.
            heap.add(e);
            upheap(size() - 1);
            return true;
        } else {
            // If the heap is at max capacity, compare the key of the new element with the root.
            // If the new element's key is smaller, replace the root and downheap it.
            if (e.getKey() < heap.get(0).getKey()) {
                poll(); // Remove the root element
                heap.add(e); // Add the new element
                downheap(0);
                return true;
            }
        }
        return false;
    }
    

    @Override
    public LabelledPoint poll() {
        if (isEmpty()) return null;
        LabelledPoint root = heap.get(0);
    
        if (size() > 1) {
            // Replace the root with the last element and downheap it.
            swap(0, size() - 1);
            heap.remove(size() - 1);
            downheap(0);
        } else {
            // If there's only one element, remove it.
            heap.remove(0);
        }
        return root;
    }
    
    //FROM POWERPOINT SLIDES
    @Override
    public LabelledPoint peek() {
        if (isEmpty()) return null;
        return heap.get(0);
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    protected int parent(int j) {
        return (j - 1) / 2;
    }

    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    public void swap(int i, int j) {
        LabelledPoint temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void upheap(int j) {
        while (j > 0) {
            int p = parent(j);
            if (comp.compare(heap.get(j).getKey(), heap.get(p).getKey()) >= 0) {
                break;
            }
            swap(j, p);
            j = p;
        }
    }

    public void downheap(int j) {
        while (hasLeft(j)) {
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if (hasRight(j)) {
                int rightIndex = right(j);
                if (comp.compare(heap.get(leftIndex).getKey(), heap.get(rightIndex).getKey()) < 0) {
                    smallChildIndex = rightIndex;
                }
            }
            if (comp.compare(heap.get(smallChildIndex).getKey(), heap.get(j).getKey()) <= 0) {
                break;
            }
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }
    public ArrayList<LabelledPoint> findKNN(LabelledPoint query, ArrayList<LabelledPoint> dataset, int k) {
        ArrayList<LabelledPoint> kNN = new ArrayList<>(k);
        for (LabelledPoint datasetPoint : dataset) {
            double distance = datasetPoint.distanceTo(query); 
            datasetPoint.setKey(distance);
            offer(datasetPoint);
        }
    //SAME AS PQ1
        int j = 0;
        while (j < k) {
            if (!isEmpty()) {
                LabelledPoint nearestNeighbor = poll(); 
                kNN.add(nearestNeighbor);
                j++;
            } else {
                break;
            }
        }
        Collections.reverse(kNN);
        System.out.print(query.getLabel() + " : ");
        for (int i = kNN.size() - 1; i >= 0; i--) {
            System.out.print(kNN.get(i).getLabel());
            if (i > 0) {
                System.out.print(", ");
            }
        }
        System.out.println();
    
        return kNN;
    }
    
}