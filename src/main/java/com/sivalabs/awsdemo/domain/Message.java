package com.sivalabs.awsdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
