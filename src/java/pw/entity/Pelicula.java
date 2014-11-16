/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author seront
 */
@Entity
@Table(name = "pelicula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pelicula.findAll", query = "SELECT p FROM Pelicula p"),
    @NamedQuery(name = "Pelicula.findByIdPelicula", query = "SELECT p FROM Pelicula p WHERE p.idPelicula = :idPelicula"),
    @NamedQuery(name = "Pelicula.findByTitulo", query = "SELECT p FROM Pelicula p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Pelicula.findByAnio", query = "SELECT p FROM Pelicula p WHERE p.anio = :anio"),
    @NamedQuery(name = "Pelicula.findByDuracion", query = "SELECT p FROM Pelicula p WHERE p.duracion = :duracion"),
    @NamedQuery(name = "Pelicula.findByCostoproduccion", query = "SELECT p FROM Pelicula p WHERE p.costoproduccion = :costoproduccion"),
    @NamedQuery(name = "Pelicula.findByGanancia", query = "SELECT p FROM Pelicula p WHERE p.ganancia = :ganancia")})
public class Pelicula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pelicula")
    private String idPelicula;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "anio")
    private int anio;
    @Basic(optional = false)
    @Column(name = "duracion")
    @Temporal(TemporalType.TIME)
    private Date duracion;
    @Basic(optional = false)
    @Column(name = "costoproduccion")
    private double costoproduccion;
    @Basic(optional = false)
    @Column(name = "ganancia")
    private double ganancia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pelicula")
    private List<Elenco> elencoList;
    @JoinColumn(name = "id_estudio", referencedColumnName = "id_estudio")
    @ManyToOne(optional = false)
    private Estudio idEstudio;

    public Pelicula() {
    }

    public Pelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Pelicula(String idPelicula, String titulo, int anio, Date duracion, double costoproduccion, double ganancia) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.costoproduccion = costoproduccion;
        this.ganancia = ganancia;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getDuracion() {
        return duracion;
    }

    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }

    public double getCostoproduccion() {
        return costoproduccion;
    }

    public void setCostoproduccion(double costoproduccion) {
        this.costoproduccion = costoproduccion;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

    @XmlTransient
    public List<Elenco> getElencoList() {
        return elencoList;
    }

    public void setElencoList(List<Elenco> elencoList) {
        this.elencoList = elencoList;
    }

    public Estudio getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(Estudio idEstudio) {
        this.idEstudio = idEstudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPelicula != null ? idPelicula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pelicula)) {
            return false;
        }
        Pelicula other = (Pelicula) object;
        if ((this.idPelicula == null && other.idPelicula != null) || (this.idPelicula != null && !this.idPelicula.equals(other.idPelicula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pw.entity.Pelicula[ idPelicula=" + idPelicula + " ]";
    }
    
}
