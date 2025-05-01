package com.demo.model;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;

    @Column(nullable = false)
    private LocalDateTime orderdate;

    @Column(nullable = false)
    private Integer mid;

    @PrePersist
    protected void onCreate() {
        if (this.orderdate == null) {
            this.orderdate = LocalDateTime.now();  // 如果未手動設置 orderdate，則默認使用當前時間
        }
        
    }

}