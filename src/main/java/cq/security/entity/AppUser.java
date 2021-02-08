package cq.security.entity;


import javax.persistence.*;


@Entity
@Inheritance
@Table(name = "App_User", //
		uniqueConstraints = { //
				@UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "User_Id", nullable = false)

	private Long userId;

	@Column(name = "User_Name", length = 36, nullable = false)
	private String userName;

	@Column(name = "Encrypted_Password", length = 128, nullable = false)
	private String encryptedPassword;

	@Column(name = "Enabled", length = 1, nullable = false)
	private int enabled;



	public int getEnabled() {
		return enabled;
	}



	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public int isEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}


	public AppUser(Long userId, String userName, String encryptedPassword) {
		this.userId = userId;
		this.userName = userName;
		this.encryptedPassword = encryptedPassword;
	}

	public AppUser() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof AppUser))
			return false;
		AppUser employee = (AppUser) obj;
		return employee.getUserId() == this.getUserId();
	}

}
