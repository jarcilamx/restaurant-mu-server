package restaurant.muserver.pojo;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Jorge J Arcila Santiago
 */
@Data
public class InputJson {

    private String cusip;
    private int secSID;
    private int issueFactor;
    private String commonCode;
    private String blockedBy;
    private String minorType;
    private String shortcode;
    private String sedol;
    private String ric;
    private String descr;
    private int valueFactor;
    private String quickcode;
    private String securityType;
    private Date maturityDate;
    private String ccy;
    private String reservedBy;
    private String tradedIND;
    private String ctryId;
    private String wertpapierCode;
    private String isin;
}
