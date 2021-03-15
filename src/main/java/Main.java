import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
  public static void main(String[] args) {
    //на небольших массивах эффективнее однопоточный подсчет
    long beginTime = System.currentTimeMillis();
    List<Integer> list1 = genListOfInteger(1000, 1000);
    long sum = sumOfList(list1);
    System.out.println("Сумма элементов массива = " + sum +
            ". Среднее арифметическое = " + sum / list1.size());
    long endTime = System.currentTimeMillis();
    System.out.println("Время = " + (endTime - beginTime) + " мс.");
    beginTime = System.currentTimeMillis();
    long forkSum = new ForkJoinPool().invoke(new ArraySumTask(0, list1.size(), list1));
    System.out.println("Сумма элементов массива = " + forkSum +
            ". Среднее арифметическое = " + forkSum / list1.size());
    endTime = System.currentTimeMillis();
    System.out.println("Время = " + (endTime - beginTime) + " мс.");

    //на больших массивах эффективнее многопоточный подсчет
    beginTime = System.currentTimeMillis();
    List<Integer> list2 = genListOfInteger(10000000, 1000);
    sum = sumOfList(list2);
    System.out.println("Сумма элементов массива = " + sum +
            ". Среднее арифметическое = " + sum / list2.size());
    endTime = System.currentTimeMillis();
    System.out.println("Время = " + (endTime - beginTime) + " мс.");
    beginTime = System.currentTimeMillis();
    forkSum = new ForkJoinPool().invoke(new ArraySumTask(0, list2.size(), list2));
    System.out.println("Сумма элементов массива = " + forkSum +
            ". Среднее арифметическое = " + forkSum / list2.size());
    endTime = System.currentTimeMillis();
    System.out.println("Время = " + (endTime - beginTime) + " мс.");
  }

  private static long sumOfList(List<Integer> list) {
    long result = 0;
    for (Integer i : list)
      result += i;
    return result;
  }

  public static List<Integer> genListOfInteger(int size, int max) {
    List<Integer> list = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < size; i++) {
      list.add(random.nextInt(max));
    }
    return list;
  }
}
