package pw.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pw.entity.Actor;
import pw.entity.Conex;
import pw.jpa.ActorJpaController;
import pw.jpa.exceptions.NonexistentEntityException;
import pw.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author seront
 */
@WebServlet(name = "ActorAdmin", urlPatterns = {"/ActorAdmin"})
public class ActorAdmin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ActorAdmin</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ActorAdmin at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
        String accion= request.getParameter("accion");
        if(accion!=null){
            switch(accion){
                case "Registrar":
                    registrar(request,response);
                    break;
                case "Editar":
                    editar(request,response);
                    break;
                case "Eliminar":
                    eliminar(request, response);
            }
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Actor actor= new Actor();
        try {
            actor.setIdActor(request.getParameter("id_actor"));
            actor.setNombre(request.getParameter("nombre"));
            actor.setPais(request.getParameter("pais"));
            actor.setSexo(request.getParameter("sexo"));
            int dia,mes,anio;
            dia=Integer.parseInt(request.getParameter("dia"));
            mes=Integer.parseInt(request.getParameter("mes"));
            anio=Integer.parseInt(request.getParameter("anio"));
            Calendar cal= new GregorianCalendar(anio, mes, dia);
            actor.setFechaNacimiento(new Date(cal.getTimeInMillis()));
            actor.setComision(0);
            ActorJpaController ajc= new ActorJpaController(Conex.getEmf());
            ajc.create(actor);
            request.setAttribute("mensaje","Actor "+actor.getNombre()+
                    " ha sido creado satisfactoriamente");
            RequestDispatcher rd= request.getRequestDispatcher("mensaje.jsp");
            rd.forward(request, response);
        }catch(PreexistingEntityException e){
             request.setAttribute("mensaje","El actor "+actor.getNombre()+
                    " ya existe");
            RequestDispatcher rd= request.getRequestDispatcher("mensaje.jsp");
            rd.forward(request, response);
        }catch (Exception e) {
            request.setAttribute("mensaje","ha ocurrido una excepcion");
            RequestDispatcher rd= request.getRequestDispatcher("mensaje.jsp");
            rd.forward(request, response);
        }
         
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         Actor actor= new Actor();
        try {
            actor.setIdActor(request.getParameter("id_actor"));
            actor.setNombre(request.getParameter("nombre"));
            actor.setPais(request.getParameter("pais"));
            actor.setSexo(request.getParameter("sexo"));
            int dia,mes,anio;
            String fechaArray[]= request.getParameter("fechaNacimiento").split("-");
            dia=Integer.parseInt(fechaArray[2]);
            mes=Integer.parseInt(fechaArray[1]);
            anio=Integer.parseInt(fechaArray[0]);
            Calendar cal= new GregorianCalendar(anio, mes, dia);
            actor.setFechaNacimiento(new Date(cal.getTimeInMillis()));
            actor.setComision(0);
            ActorJpaController ajc= new ActorJpaController(Conex.getEmf());
            ajc.edit(actor);
            request.setAttribute("mensaje","Actor "+actor.getNombre()+
                    " ha sido editado satisfactoriamente");
            RequestDispatcher rd= request.getRequestDispatcher("Actores.jsp");
            rd.forward(request, response);
        }catch(NonexistentEntityException e){
             request.setAttribute("mensaje","El actor "+actor.getNombre()+
                    " no existe");
            RequestDispatcher rd= request.getRequestDispatcher("mensaje.jsp");
            rd.forward(request, response);
        }catch (Exception e) {
            request.setAttribute("mensaje","ha ocurrido una excepcion");
            RequestDispatcher rd= request.getRequestDispatcher("mensaje.jsp");
            rd.forward(request, response);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id=request.getParameter("id_actor");
            ActorJpaController ajc= new ActorJpaController(Conex.getEmf());
            ajc.destroy(id);
            request.setAttribute("mensaje","Actor "+id+
                    " ha sido eliminado satisfactoriamente");
            RequestDispatcher rd= request.getRequestDispatcher("Actores.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("mensaje","Actor NO ha sido eliminado satisfactoriamente "+e.getMessage());
            RequestDispatcher rd= request.getRequestDispatcher("Actores.jsp");
            rd.forward(request, response);
        }
    }

}
