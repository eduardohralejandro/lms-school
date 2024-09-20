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
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(nullable = false)
    private ForumType forumType;
    @OneToMany(mappedBy = "forum")
    private Set<Thread> threads = new HashSet<>();
}
