package br.com.finansys.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Category Entity
 *
 * @author Higor Gardin
 * @version 1.0
 * @since 11/06/2022
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "cd_category", nullable = false)
    private String cdCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_user")
    private User user;

    @Column(name = "ds_name", nullable = false, length = 100)
    private String dsName;

    @Column(name = "ds_description", nullable = false, length = 300)
    private String dsDescription;

}
