package restaurant.muserver.pojo;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Jorge J Arcila Santiago
 */
@Data
public class QualifierDate {
    private Date fromDate;
    private Date toDate;
    
    @Override
    public String toString() {
        return "from: " + fromDate.toString() + " to:" + toDate.toString();
    }
}
