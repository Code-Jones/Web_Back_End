package dataaccess;

import com.sun.istack.internal.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.Item;
import models.User;
import org.eclipse.persistence.sessions.Session;
import servlets.InventoryServlet;

public class ItemDB {

    public Item get(int itemId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Item item = em.find(Item.class, itemId);
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
    
    public boolean insert(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(item);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            return false;
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
            Logger.getLogger(InventoryServlet.class).log(Level.SEVERE, "Work god damn it" + item.toString(), ex);
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }
//    public boolean update(Category category, int itemID, String itemName, double price) throws Exception {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        EntityTransaction trans = em.getTransaction();
//        Item item = get(itemID);
//        item.setCategory(category);
//        item.setItemName(itemName);
//        item.setPrice(price);
//        try {
//            trans.begin();
//            em.merge(item);
//            trans.commit();
//            return true;
//        } catch (Exception ex) {
//            trans.rollback();
//            return false;
//        } finally {
//            em.close();
//        }
//    }

    public void delete(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            User user = item.getOwner();
            user.getItemList().remove(item);
            trans.begin();
            em.remove(em.merge(item));
            em.merge(user);
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
