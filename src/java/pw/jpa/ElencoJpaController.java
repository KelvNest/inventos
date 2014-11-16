/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pw.entity.Pelicula;
import pw.entity.Actor;
import pw.entity.Elenco;
import pw.entity.ElencoPK;
import pw.jpa.exceptions.NonexistentEntityException;
import pw.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author seront
 */
public class ElencoJpaController implements Serializable {

    public ElencoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Elenco elenco) throws PreexistingEntityException, Exception {
        if (elenco.getElencoPK() == null) {
            elenco.setElencoPK(new ElencoPK());
        }
        elenco.getElencoPK().setIdPelicula(elenco.getPelicula().getIdPelicula());
        elenco.getElencoPK().setIdActor(elenco.getActor().getIdActor());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelicula pelicula = elenco.getPelicula();
            if (pelicula != null) {
                pelicula = em.getReference(pelicula.getClass(), pelicula.getIdPelicula());
                elenco.setPelicula(pelicula);
            }
            Actor actor = elenco.getActor();
            if (actor != null) {
                actor = em.getReference(actor.getClass(), actor.getIdActor());
                elenco.setActor(actor);
            }
            em.persist(elenco);
            if (pelicula != null) {
                pelicula.getElencoList().add(elenco);
                pelicula = em.merge(pelicula);
            }
            if (actor != null) {
                actor.getElencoList().add(elenco);
                actor = em.merge(actor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findElenco(elenco.getElencoPK()) != null) {
                throw new PreexistingEntityException("Elenco " + elenco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Elenco elenco) throws NonexistentEntityException, Exception {
        elenco.getElencoPK().setIdPelicula(elenco.getPelicula().getIdPelicula());
        elenco.getElencoPK().setIdActor(elenco.getActor().getIdActor());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elenco persistentElenco = em.find(Elenco.class, elenco.getElencoPK());
            Pelicula peliculaOld = persistentElenco.getPelicula();
            Pelicula peliculaNew = elenco.getPelicula();
            Actor actorOld = persistentElenco.getActor();
            Actor actorNew = elenco.getActor();
            if (peliculaNew != null) {
                peliculaNew = em.getReference(peliculaNew.getClass(), peliculaNew.getIdPelicula());
                elenco.setPelicula(peliculaNew);
            }
            if (actorNew != null) {
                actorNew = em.getReference(actorNew.getClass(), actorNew.getIdActor());
                elenco.setActor(actorNew);
            }
            elenco = em.merge(elenco);
            if (peliculaOld != null && !peliculaOld.equals(peliculaNew)) {
                peliculaOld.getElencoList().remove(elenco);
                peliculaOld = em.merge(peliculaOld);
            }
            if (peliculaNew != null && !peliculaNew.equals(peliculaOld)) {
                peliculaNew.getElencoList().add(elenco);
                peliculaNew = em.merge(peliculaNew);
            }
            if (actorOld != null && !actorOld.equals(actorNew)) {
                actorOld.getElencoList().remove(elenco);
                actorOld = em.merge(actorOld);
            }
            if (actorNew != null && !actorNew.equals(actorOld)) {
                actorNew.getElencoList().add(elenco);
                actorNew = em.merge(actorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ElencoPK id = elenco.getElencoPK();
                if (findElenco(id) == null) {
                    throw new NonexistentEntityException("The elenco with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ElencoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elenco elenco;
            try {
                elenco = em.getReference(Elenco.class, id);
                elenco.getElencoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The elenco with id " + id + " no longer exists.", enfe);
            }
            Pelicula pelicula = elenco.getPelicula();
            if (pelicula != null) {
                pelicula.getElencoList().remove(elenco);
                pelicula = em.merge(pelicula);
            }
            Actor actor = elenco.getActor();
            if (actor != null) {
                actor.getElencoList().remove(elenco);
                actor = em.merge(actor);
            }
            em.remove(elenco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Elenco> findElencoEntities() {
        return findElencoEntities(true, -1, -1);
    }

    public List<Elenco> findElencoEntities(int maxResults, int firstResult) {
        return findElencoEntities(false, maxResults, firstResult);
    }

    private List<Elenco> findElencoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Elenco.class));
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

    public Elenco findElenco(ElencoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Elenco.class, id);
        } finally {
            em.close();
        }
    }

    public int getElencoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Elenco> rt = cq.from(Elenco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
