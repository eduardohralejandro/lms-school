package com.lms_schoolapp.greenteam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;
    @OneToMany(mappedBy = "thread")
    Set<Post> posts = new HashSet<>();
}
