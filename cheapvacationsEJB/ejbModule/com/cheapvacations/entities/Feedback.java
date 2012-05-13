package com.cheapvacations.entities;

import java.io.Serializable;
import javax.persistence.*;
/**
 * @author ccaccia
 *
 */

@MappedSuperclass
@Entity
@Table(name="Feedback")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Feedback implements Serializable {
	

	private static final long serialVersionUID = 2L;
	protected long Id;
	protected String comment;
	protected short mark;
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	public long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
	}
	
	/**
	 * @return the comment
	 */
	@Column(name="COMMENT",nullable=true,length=160)
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * @return the mark
	 */
	@Column(name="MARK",nullable=false)
	public short getMark() {
		return mark;
	}
	/**
	 * @param mark the mark to set
	 */
	public void setMark(short mark) {
		this.mark = mark;
	}
}