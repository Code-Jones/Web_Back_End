package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.Item;
import models.User;
import org.eclipse.persistence.sessions.Session;

public class ItemDB {

    public Item get(int itemId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Item item = em.find(Item.class, itemId);
//            List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.itemId = :itemId").setParameter("itemId", thisItem).setMaxResults(1).getResultList();
//            return items.get(0);
            return item;
        } finally {
            em.close();
        }
    }

    public List<Item> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
            return items;
        } finally {
            em.close();
        }
    }

    public List<Item> getAllFromUser(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Item> items = user.getItemList();
//            List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
//            items.stream().filter((item) -> (!item.getOwner().equals(user))).forEachOrdered((item) -> {
//                items.remove(item);
//            });
            return items;
        } finally {
            em.close();
        }
    }

    public boolean addItem(Category category, String itemName, double price, User owner)  {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        Item item = new Item();
        item.setCategory(category);
        item.setItemName(itemName);
        item.setPrice(price);
        item.setOwner(owner);
        try {
            trans.begin();
            em.merge(item);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean update(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(item);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }
    public boolean update(Category category, int itemID, String itemName, double price) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        Item item = get(itemID);
        item.setCategory(category);
        item.setItemName(itemName);
        item.setPrice(price);
        try {
            trans.begin();
            em.merge(item);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public void delete(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.remove(em.merge(item));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public int getNewId() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        return em.unwrap(Session.class).getNextSequenceNumberValue(Item.class).intValue();
    }
}
