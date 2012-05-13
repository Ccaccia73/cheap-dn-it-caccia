package com.cheapvacations.entities;


import javax.persistence.*;


@MappedSuperclass
@Entity
@Table(name="User")
// caso single table
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING,length=1)
// caso table per class
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User implements java.io.Serializable{
	

	private static final long serialVersionUID = 12L;
	/**
	 * defines the status of the user
	 * ACTIVE = 1: the user can access to the application
	 * INACTIVE = 0: the user has unsubscribed, he/she cannot access anymore
	 * but his data is maintained in the system
	 */
	
	protected long Id;
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;
	protected String email;
	protected String address;
	protected int status;
	
	
	/* Getters and Setters */
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_USER")
	public long getId() {
		return Id;
	}
	/**
	 * @param pk the id to set
	 */
	public void setId(long pk) {
		Id = pk;
	}

	/**
	 * @return the firstName
	 */
	@Column(name="FIRSTNAME", length=45, nullable=false)
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param name the firstName to set
	 */
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	/**
	 * @return the lastName
	 */
	@Column(name="LASTNAME", length=45, nullable=false)
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param name the lastName to set
	 */
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	/**
	 * @return the username
	 */
	@Column(name="USERNAME", length=20, nullable=false)
	public String getUsername() {
		return username;
	}
	/**
	 * @param user the username to set
	 */
	public void setUsername(String user) {
		this.username = user;
	}
	
	/**
	 * @return the password
	 */
	@Column(name="PASSWORD", length=20, nullable=false)
	public String getPassword() {
		return password;
	}
	/**
	 * @param pw the password to set
	 */
	public void setPassword(String pw) {
		this.password = pw;
	}
	
	/**
	 * @return the email
	 */
	@Column(name="EMAIL",length=45,nullable=false)
	public String getEmail() {
		return email;
	}
	/**
	 * @param mail the email to set
	 */
	public void setEmail(String mail) {
		this.email = mail;
	}
	
	/**
	 * @return the address
	 */
	@Column(name="ADDRESS", length=90, nullable=true)
	public String getAddress() {
		return address;
	}
	/**
	 * @param addr the address to set
	 */
	public void setAddress(String addr) {
		this.address = addr;
	}
	
	/**
	 * @return the status
	 */
	@Column(name="STATUS",nullable=false)
	public int getStatus() {
		return status;
	}
	/**
	 * @param st the status to set
	 */
	public void setStatus(int st) {
		this.status = st;
	}
}