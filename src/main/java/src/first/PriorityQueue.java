package src.first;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
public interface PriorityQueue<T> {
    public void add(T t);

    public T del();

    public Boolean isEmpty();

    public Integer size();

    public T peek();
}
