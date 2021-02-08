package cq.security.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class PersistentLogins {
	@Column(name = "username", length = 64, nullable = false)
    private String username;
    @Id
	@Column(name = "series", length = 64, nullable = false)
    private String series;
	@Column(name = "token", length = 64, nullable = false)
    private String token;
	@Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsed;
	public String getUsername() {
		return username;
	} 
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(LocalDateTime lastUsed) {
		this.lastUsed = lastUsed;
	}
    
}
