package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private Long timeUsed;


	public Result() {
    }

    public Result(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", name='" + code + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public Long getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(Long timeUsed) {
		this.timeUsed = timeUsed;
	}
}