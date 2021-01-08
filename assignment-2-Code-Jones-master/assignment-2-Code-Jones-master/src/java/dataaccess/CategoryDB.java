package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Category;

public class CategoryDB {
    
    public Category get(int categoryId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Category cat = em.find(Category.class, categoryId);
            return cat;
        } finally {
            em.close();
        }
    }

    public List<Category> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Category> categorys = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            return categorys;
        } finally {
            em.close();
        }
    }
    
}
