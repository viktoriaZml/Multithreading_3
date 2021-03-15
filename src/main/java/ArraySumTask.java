import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Long> {
  private int start;
  private int end;
  private List<Integer> array;

  public ArraySumTask(int start, int end, List<Integer> array) {
    this.start = start;
    this.end = end;
    this.array = array;
  }

  private long forkTasksAndGetResult() {
    final int middle = (end - start) / 2 + start;
    // Создаем задачу для левой части диапазона
    ArraySumTask task1 = new ArraySumTask(start, middle, array);
    // Создаем задачу для правой части диапазона
    ArraySumTask task2 = new ArraySumTask(middle, end, array);
    // Запускаем обе задачи в пуле
    invokeAll(task1, task2);
    // Суммируем результаты выполнения обоих задач
    return task1.join() + task2.join();
  }

  @Override
  protected Long compute() {
    final int diff = end - start;
    switch (diff) {
      case 0:
        return Long.valueOf(0);
      case 1:
        return Long.valueOf(array.get(start));
      case 2:
        return Long.valueOf(array.get(start) + array.get(start + 1));
      default:
        return forkTasksAndGetResult();
    }
  }
}
