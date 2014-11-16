/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pw.entity.Elenco;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import pw.entity.Actor;
import pw.jpa.exceptions.IllegalOrphanException;
import pw.jpa.exceptions.NonexistentEntityException;
import pw.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author seront
 */
public class ActorJpaController implements Serializable {

    public ActorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actor actor) throws PreexistingEntityException, Exception {
        if (actor.getElencoList() == null) {
            actor.setElencoList(new ArrayList<Elenco>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();            
            em.persist(actor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActor(actor.getIdActor()) != null) {
                throw new PreexistingEntityException("Actor " + actor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actor actor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            Actor persistentActor = em.find(Actor.class, actor.getIdActor());
//            List<Elenco> elencoListOld = persistentActor.getElencoList();
//            List<Elenco> elencoListNew = actor.getElencoList();
//            List<String> illegalOrphanMessages = null;
//            for (Elenco elencoListOldElenco : elencoListOld) {
//                if (!elencoListNew.contains(elencoListOldElenco)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Elenco " + elencoListOldElenco + " since its actor field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Elenco> attachedElencoListNew = new ArrayList<Elenco>();
//            for (Elenco elencoListNewElencoToAttach : elencoListNew) {
//                elencoListNewElencoToAttach = em.getReference(elencoListNewElencoToAttach.getClass(), elencoListNewElencoToAttach.getElencoPK());
//                attachedElencoListNew.add(elencoListNewElencoToAttach);
//            }
//            elencoListNew = attachedElencoListNew;
//            actor.setElencoList(elencoListNew);
            actor = em.merge(actor);
//            for (Elenco elencoListNewElenco : elencoListNew) {
//                if (!elencoListOld.contains(elencoListNewElenco)) {
//                    Actor oldActorOfElencoListNewElenco = elencoListNewElenco.getActor();
//                    elencoListNewElenco.setActor(actor);
//                    elencoListNewElenco = em.merge(elencoListNewElenco);
//                    if (oldActorOfElencoListNewElenco != null && !oldActorOfElencoListNewElenco.equals(actor)) {
//                        oldActorOfElencoListNewElenco.getElencoList().remove(elencoListNewElenco);
//                        oldActorOfElencoListNewElenco = em.merge(oldActorOfElencoListNewElenco);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = actor.getIdActor();
                if (findActor(id) == null) {
                    throw new NonexistentEntityException("The actor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actor actor;
            try {
                actor = em.getReference(Actor.class, id);
                actor.getIdActor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Elenco> elencoListOrphanCheck = actor.getElencoList();
            for (Elenco elencoListOrphanCheckElenco : elencoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Actor (" + actor + ") cannot be destroyed since the Elenco " + elencoListOrphanCheckElenco + " in its elencoList field has a non-nullable actor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(actor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actor> findActorEntities() {
        return findActorEntities(true, -1, -1);
    }

    public List<Actor> findActorEntities(int maxResults, int firstResult) {
        return findActorEntities(false, maxResults, firstResult);
    }

    private List<Actor> findActorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    
    public Actor findActor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actor.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Actor> buscarPorNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            Query query =em.createNamedQuery("Actor.findByNombre");
            query.setParameter("nombre", "%"+nombre+"%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int getActorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actor> rt = cq.from(Actor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
