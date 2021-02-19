package service;

public interface iService<T> {
    T find(int id);
    void add(T t);
}
