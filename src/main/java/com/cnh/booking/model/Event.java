package com.cnh.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long id;
    @Column(name = "event_name")
    private String name;
    @Column(name = "event_date")
    private LocalDateTime date;
    @Column(name = "event_price")
    private double price;
    @ManyToMany(mappedBy = "events")
    private HashSet<User> users = new HashSet<>();
}
