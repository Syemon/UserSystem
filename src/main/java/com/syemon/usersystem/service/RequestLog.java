package com.syemon.usersystem.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class RequestLog {
    @Id
    @SequenceGenerator(name = "requestlog_id_generator", sequenceName = "requestlog_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "requestlog_id_generator", strategy = GenerationType.SEQUENCE)
    private Long id;
    private long requestCount;
    private String login;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

    public long incrementRequestCount() {
        this.requestCount += 1;
        return this.requestCount;
    }
}
