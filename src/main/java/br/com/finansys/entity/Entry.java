package br.com.finansys.entity;

import br.com.finansys.enumerations.EntryType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entry Entity
 *
 * @author Higor Gardin
 * @version 1.0
 * @since 11/06/2022
 */
@Entity
@Table(name = "entry")
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "cd_entry", nullable = false)
    private Integer cdEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_category")
    private Category category;

    @Column(name = "ds_name", nullable = false, length = 100)
    private String dsName;

    @Column(name = "ds_description", nullable = false, length = 200)
    private String dsDescription;

    @Column(name = "st_paid", nullable = false)
    private Boolean stPaid = false;

    @Column(name = "dt_date", nullable = false)
    private Timestamp dtDate;

    @Column(name = "vl_amount", nullable = false)
    private BigDecimal vlAmount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tp_type", nullable = false)
    private EntryType tpType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getCdEntry() {
        return cdEntry;
    }

    public void setCdEntry(Integer cdEntry) {
        this.cdEntry = cdEntry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsDescription() {
        return dsDescription;
    }

    public void setDsDescription(String dsDescription) {
        this.dsDescription = dsDescription;
    }

    public Boolean getStPaid() {
        return stPaid;
    }

    public void setStPaid(Boolean stPaid) {
        this.stPaid = stPaid;
    }

    public Timestamp getDtDate() {
        return dtDate;
    }

    public void setDtDate(Timestamp dtDate) {
        this.dtDate = dtDate;
    }

    public BigDecimal getVlAmount() {
        return vlAmount;
    }

    public void setVlAmount(BigDecimal vlAmount) {
        this.vlAmount = vlAmount;
    }

    public EntryType getTpType() {
        return tpType;
    }

    public void setTpType(EntryType tpType) {
        this.tpType = tpType;
    }
}
