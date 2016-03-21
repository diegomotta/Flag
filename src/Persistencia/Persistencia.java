/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Log_Auditoria.Entidad_Revisada;
import java.io.Serializable;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author Owner
 */
public class Persistencia {
    private SessionFactory sessionFactory;
    private Session session;

    public Persistencia() throws ExceptionInInitializerError {

        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        session = sessionFactory.openSession();


    }
    
    public void insert(Object unObjeto) {
            Transaction tr = session.beginTransaction();
            session.save(unObjeto);
            tr.commit();
    }
    
    public void update(Object unObjeto) {
        Transaction tr = session.beginTransaction();
          session.saveOrUpdate(unObjeto);
        tr.commit();
    }

    public void actualizar(Object unObjeto){
        session.save(unObjeto);
    }
   
    public Object load(String clase,Serializable id) {
        Object obj = session.load(clase,id);
        return obj;
    }
    
    public Object get(Class clase) {
        Object obj = null;
        Transaction tr = session.beginTransaction();
        obj = session.get(clase, 1);
        tr.commit();
        return obj;
    }
    
    public List restaurar(String classname){
        List datos = null;
        Transaction tr = session.beginTransaction();
        datos = session.createQuery("from " + classname).list();
        tr.commit();
        return datos;
    }

    public List find(String sentencia) {
        List obj = session.createQuery(sentencia).list();
        return obj;
    }
    
    public void delete(Object unObjeto) {
        Transaction tr = session.beginTransaction();
        session.delete(unObjeto);
        
        tr.commit();
    }
    
    public void cerrar ()
    {
        System.exit(0);
    }

    public List buscarRevisiones(String tabla) {
        Transaction tr = session.beginTransaction();
        List resultado;
        SQLQuery consultaSQLQuery = session.createSQLQuery("SELECT * from " + tabla);
        consultaSQLQuery.addEntity(Entidad_Revisada.class);
        resultado = consultaSQLQuery.list();

        tr.commit();
        return resultado;
    }



}
