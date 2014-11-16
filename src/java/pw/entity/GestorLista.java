/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.entity;

import java.util.List;
import pw.jpa.ActorJpaController;

/**
 *
 * @author seront
 */
public class GestorLista {
    
    public List<Actor> listarActores(){
        ActorJpaController ajc =new ActorJpaController(Conex.getEmf());
        return ajc.findActorEntities();
    }
    
    public Actor buscarActor(String id){
        ActorJpaController ajc =new ActorJpaController(Conex.getEmf());
        return ajc.findActor(id);
    }
    
     public List<Actor> listarActores(String nombre){
        ActorJpaController ajc =new ActorJpaController(Conex.getEmf());
        return ajc.buscarPorNombre(nombre);
    }
}
