//WAJDY ELSOBKY 300317535
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueue3 implements PriorityQueueIF<LabelledPoint> {
    private PriorityQueue<LabelledPoint> heap;
    private int maxCapacity;

    public PriorityQueue3(int k) {
        this.heap = new PriorityQueue<>(k, Comparator.comparing(LabelledPoint::getKey).reversed()); //order keys in descending order
        maxCapacity = k;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean offer(LabelledPoint e) {
        if (size() < maxCapacity) {
            heap.offer(e);
            return true;
        } else if (e.getKey() < peek().getKey()) {
            poll(); // Remove the element with the smallest key
            heap.offer(e); // Offer the new element
            return true;
        }
        return false;
    }

    @Override
    public LabelledPoint poll() {
        return heap.poll(); // Poll removes and returns the element with the smallest key
    }

    @Override
    public LabelledPoint peek() {
        return heap.peek(); // Peek returns the element with the smallest key without removing it
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
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