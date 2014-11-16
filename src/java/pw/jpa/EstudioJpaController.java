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
import pw.entity.Pelicula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import pw.entity.Estudio;
import pw.jpa.exceptions.IllegalOrphanException;
import pw.jpa.exceptions.NonexistentEntityException;
import pw.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author seront
 */
public class EstudioJpaController implements Serializable {

    public EstudioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudio estudio) throws PreexistingEntityException, Exception {
        if (estudio.getPeliculaList() == null) {
            estudio.setPeliculaList(new ArrayList<Pelicula>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pelicula> attachedPeliculaList = new ArrayList<Pelicula>();
            for (Pelicula peliculaListPeliculaToAttach : estudio.getPeliculaList()) {
                peliculaListPeliculaToAttach = em.getReference(peliculaListPeliculaToAttach.getClass(), peliculaListPeliculaToAttach.getIdPelicula());
                attachedPeliculaList.add(peliculaListPeliculaToAttach);
            }
            estudio.setPeliculaList(attachedPeliculaList);
            em.persist(estudio);
            for (Pelicula peliculaListPelicula : estudio.getPeliculaList()) {
                Estudio oldIdEstudioOfPeliculaListPelicula = peliculaListPelicula.getIdEstudio();
                peliculaListPelicula.setIdEstudio(estudio);
                peliculaListPelicula = em.merge(peliculaListPelicula);
                if (oldIdEstudioOfPeliculaListPelicula != null) {
                    oldIdEstudioOfPeliculaListPelicula.getPeliculaList().remove(peliculaListPelicula);
                    oldIdEstudioOfPeliculaListPelicula = em.merge(oldIdEstudioOfPeliculaListPelicula);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudio(estudio.getIdEstudio()) != null) {
                throw new PreexistingEntityException("Estudio " + estudio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudio estudio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudio persistentEstudio = em.find(Estudio.class, estudio.getIdEstudio());
            List<Pelicula> peliculaListOld = persistentEstudio.getPeliculaList();
            List<Pelicula> peliculaListNew = estudio.getPeliculaList();
            List<String> illegalOrphanMessages = null;
            for (Pelicula peliculaListOldPelicula : peliculaListOld) {
                if (!peliculaListNew.contains(peliculaListOldPelicula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pelicula " + peliculaListOldPelicula + " since its idEstudio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pelicula> attachedPeliculaListNew = new ArrayList<Pelicula>();
            for (Pelicula peliculaListNewPeliculaToAttach : peliculaListNew) {
                peliculaListNewPeliculaToAttach = em.getReference(peliculaListNewPeliculaToAttach.getClass(), peliculaListNewPeliculaToAttach.getIdPelicula());
                attachedPeliculaListNew.add(peliculaListNewPeliculaToAttach);
            }
            peliculaListNew = attachedPeliculaListNew;
            estudio.setPeliculaList(peliculaListNew);
            estudio = em.merge(estudio);
            for (Pelicula peliculaListNewPelicula : peliculaListNew) {
                if (!peliculaListOld.contains(peliculaListNewPelicula)) {
                    Estudio oldIdEstudioOfPeliculaListNewPelicula = peliculaListNewPelicula.getIdEstudio();
                    peliculaListNewPelicula.setIdEstudio(estudio);
                    peliculaListNewPelicula = em.merge(peliculaListNewPelicula);
                    if (oldIdEstudioOfPeliculaListNewPelicula != null && !oldIdEstudioOfPeliculaListNewPelicula.equals(estudio)) {
                        oldIdEstudioOfPeliculaListNewPelicula.getPeliculaList().remove(peliculaListNewPelicula);
                        oldIdEstudioOfPeliculaListNewPelicula = em.merge(oldIdEstudioOfPeliculaListNewPelicula);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estudio.getIdEstudio();
                if (findEstudio(id) == null) {
                    throw new NonexistentEntityException("The estudio with id " + id + " no longer exists.");
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
            Estudio estudio;
            try {
                estudio = em.getReference(Estudio.class, id);
                estudio.getIdEstudio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pelicula> peliculaListOrphanCheck = estudio.getPeliculaList();
            for (Pelicula peliculaListOrphanCheckPelicula : peliculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudio (" + estudio + ") cannot be destroyed since the Pelicula " + peliculaListOrphanCheckPelicula + " in its peliculaList field has a non-nullable idEstudio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estudio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudio> findEstudioEntities() {
        return findEstudioEntities(true, -1, -1);
    }

    public List<Estudio> findEstudioEntities(int maxResults, int firstResult) {
        return findEstudioEntities(false, maxResults, firstResult);
    }

    private List<Estudio> findEstudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudio.class));
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

    public Estudio findEstudio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudio> rt = cq.from(Estudio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
