package com.sivalabs.awsdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_id_generator")
    @SequenceGenerator(name = "msg_id_generator", sequenceName = "msg_id_seq", allocationSize = 10)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String content;
}
