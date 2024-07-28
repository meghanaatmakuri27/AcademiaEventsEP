package com.klef.project.models;

import java.io.Serializable;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="winner_table")
public class Winner implements Serializable {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="winner_id")
	    private int id;
	    
	    @Column(name="winner_name")
	    private String name;
        
	    @Column(name="event_name")
	    private String eventname;
	    
	    @Lob
	    @Column(name ="img")
	    private byte[] pic;
	    
	    public String getBase64Image() {
	        if (pic != null) {
	            return Base64.getEncoder().encodeToString(pic);
	        }
	        return null;
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEventname() {
			return eventname;
		}

		public void setEventname(String eventname) {
			this.eventname = eventname;
		}

		public byte[] getPic() {
			return pic;
		}

		public void setPic(byte[] pic) {
			this.pic = pic;
		}
}
