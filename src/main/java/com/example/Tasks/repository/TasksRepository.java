package com.example.Tasks.repository;

import com.example.Tasks.model.Tasks;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TasksRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Tasks> findAll() {
        return entityManager.createQuery("from Tasks", Tasks.class).getResultList();
    }

    public Tasks findById(Long id) {
        return entityManager.find(Tasks.class, id);
    }

    public Tasks save(Tasks task) {
        if (task.getId() == null) {
            entityManager.persist(task);
            return task;
        } else {
            return entityManager.merge(task);
        }
    }

    public void deleteById(Long id) {
        Tasks task = findById(id);
        if (task != null) {
            entityManager.remove(task);
        }
    }
}
