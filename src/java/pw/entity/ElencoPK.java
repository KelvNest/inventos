/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author seront
 */
@Embeddable
public class ElencoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_actor")
    private String idActor;
    @Basic(optional = false)
    @Column(name = "id_pelicula")
    private String idPelicula;

    public ElencoPK() {
    }

    public ElencoPK(String idActor, String idPelicula) {
        this.idActor = idActor;
        this.idPelicula = idPelicula;
    }

    public String getIdActor() {
        return idActor;
    }

    public void setIdActor(String idActor) {
        this.idActor = idActor;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActor != null ? idActor.hashCode() : 0);
        hash += (idPelicula != null ? idPelicula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElencoPK)) {
            return false;
        }
        ElencoPK other = (ElencoPK) object;
        if ((this.idActor == null && other.idActor != null) || (this.idActor != null && !this.idActor.equals(other.idActor))) {
            return false;
        }
        if ((this.idPelicula == null && other.idPelicula != null) || (this.idPelicula != null && !this.idPelicula.equals(other.idPelicula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pw.entity.ElencoPK[ idActor=" + idActor + ", idPelicula=" + idPelicula + " ]";
    }
    
}
