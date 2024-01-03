//WAJDY ELSOBKY 300317535
import java.util.ArrayList;

public class KNN {

    public static void main(String args[]) {
        int versionNumber = Integer.parseInt(args[0]); //choose which findKNN to run based on version number
        if (versionNumber < 1 || versionNumber > 3) {
            throw new IllegalArgumentException("Version number must be between 1-3");
        }

        int k = Integer.parseInt(args[1]);
        if (k <= 0) {
            throw new IllegalArgumentException("Oops! K should be a positive integer");
        }

        PointSet dataSet = new PointSet(PointSet.read_ANN_SIFT(args[2]));
        ArrayList<LabelledPoint> queryPts = new PointSet(PointSet.read_ANN_SIFT(args[3])).getPointsList();
        long totalTime = 0;

        if (versionNumber == 1) {
            for (LabelledPoint queryPoint : queryPts) {
                PriorityQueue1 priorityQueue = new PriorityQueue1(k);
                long startTime = System.currentTimeMillis();
                priorityQueue.findKNN(queryPoint, dataSet.getPointsList(), k);
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                totalTime += executionTime;
            }
        } else if (versionNumber == 2) {
            for (LabelledPoint queryPoint : queryPts) {
                PriorityQueue2 priorityQueue = new PriorityQueue2(k);
                long startTime = System.currentTimeMillis();
                priorityQueue.findKNN(queryPoint, dataSet.getPointsList(), k);
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                totalTime += executionTime;
            }
        } else if (versionNumber == 3) {
            for (LabelledPoint queryPoint : queryPts) {
                 PriorityQueue3 priorityQueue = new PriorityQueue3(k);
                 long startTime = System.currentTimeMillis();
                 priorityQueue.findKNN(queryPoint, dataSet.getPointsList(), k);
                 long endTime = System.currentTimeMillis();
                 long executionTime = endTime - startTime;
                 totalTime += executionTime;
             }
         }

        System.out.println("Total execution time for all queries: " + totalTime + " milliseconds");
    }
}
