package org.example.eventmanagingsystem.models;

public interface CrudManager <T>
{
    void create(T item);
    T read(int id);
    boolean update(int id);
    void delete(int id);
}
