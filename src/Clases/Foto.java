/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.awt.Image;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.swing.ImageIcon;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Foto extends ImageIcon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Type(type = "org.hibernate.type.PrimitiveByteArrayBlobType")
    private byte[] foto;
    private String URL;
    public Foto() {
    }

    public Foto(String url) {
        super(url);
        foto = Remiseria.toArrayByte(url);
        this.URL = URL;
        Remiseria.persistencia.insert(this);

    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Foto(byte[] foto, String extencion) {
        super(foto);
        this.foto = foto;
        Remiseria.persistencia.insert(this);

    }

    public Object[] toVector() {
        return new Object[]{this};
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (getId() == null || ((Foto) obj).getId() == null) {
            return false;
        }
        return ((Foto) obj).getId().equals(this.getId());
    }

    @Override
    public Image getImage() {

        return new ImageIcon(foto).getImage();


    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
